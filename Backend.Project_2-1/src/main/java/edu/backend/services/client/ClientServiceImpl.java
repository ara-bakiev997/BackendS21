package edu.backend.services.client;

import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.client.v1.ClientV1DTO;
import edu.backend.dtos.client.v1.ClientsV1DTO;
import edu.backend.entities.AddressEntity;
import edu.backend.entities.ClientEntity;
import edu.backend.repositories.AddressRepository;
import edu.backend.repositories.ClientRepository;
import edu.backend.utils.error_handler.ErrorResponseException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createClient(@Nonnull final ClientV1DTO clientDTO) {
        final var clientEntity = this.convertDtoToEntity(clientDTO);
        Optional.ofNullable(clientEntity.getClientId())
                .ifPresent(clientId -> {
                    if (clientRepository.findById(clientId).isPresent()) {
                        throw new ErrorResponseException(
                                String.format("Клиент по указанном client_id = %d уже существует", clientId),
                                HttpStatus.NOT_ACCEPTABLE
                        );
                    }
                });

        Optional.ofNullable(clientDTO.getAddressId())
                .flatMap(addressRepository::findById)
                .ifPresent(clientEntity::setAddress);

        clientRepository.save(clientEntity);
    }

    @Override
    @Transactional
    public void deleteById(final long clientId) {
        clientRepository.findById(clientId)
                        .ifPresentOrElse(
                                clientRepository::delete,
                                () -> {
                                    throw new ErrorResponseException(
                                            String.format(
                                                    "Клиент по указанному clientId = %d не существует",
                                                    clientId
                                            ),
                                            HttpStatus.NOT_FOUND
                                    );
                                }
                        );
    }

    @Nonnull
    @Override
    public ClientsV1DTO getByNameAndSurname(@Nonnull final String clientName, @Nullable final String clientSurname) {
        return new ClientsV1DTO(
                Optional.ofNullable(clientSurname)
                        .map(surname -> clientRepository.findByClientNameAndClientSurnameIgnoreCase(
                                clientName.trim(),
                                surname.trim()
                        ))
                        .orElseGet(() -> clientRepository.findByClientNameIgnoreCase(clientName.trim()))
                        .stream()
                        .map(this::convertEntityToDTO)
                        .toList()
        );
    }

    @Nonnull
    @Override
    public ClientsV1DTO getAllClients(
            @Nullable final Integer pageNumber,
            @Nullable final Integer pageSize
    ) {
        if (pageNumber == null || pageSize == null) {
            return new ClientsV1DTO(convertEntityToDTO(clientRepository.findAll()));
        }

        return new ClientsV1DTO(clientRepository
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "clientId")))
                .get().map(this::convertEntityToDTO)
                .toList()
        );
    }

    @Override
    @Transactional
    public void updateAddressById(final long clientId, @Nonnull final AddressV1DTO addressDTO) {
        clientRepository.findById(clientId)
                        .map(clientEntity -> {
                            final var addressDtoFromDb =
                                    Optional.ofNullable(addressDTO.getAddressId())
                                            .flatMap(addressRepository::findById)
                                            .orElseGet(() -> addressRepository.save(modelMapper.map(
                                                    addressDTO,
                                                    AddressEntity.class
                                            )));

                            clientEntity.setAddress(addressDtoFromDb);

                            return clientEntity;
                        })
                        .ifPresentOrElse(
                                clientRepository::save,
                                () -> {
                                    throw new ErrorResponseException(
                                            String.format(
                                                    "Клиент по указанному clientId = %d не существует",
                                                    clientId
                                            ),
                                            HttpStatus.NOT_FOUND
                                    );
                                }
                        );
    }

    @Nonnull
    private ClientV1DTO convertEntityToDTO(@Nonnull final ClientEntity clientEntity) {
        return modelMapper.map(clientEntity, ClientV1DTO.class);
    }

    @Nonnull
    private List<ClientV1DTO> convertEntityToDTO(@Nonnull final List<ClientEntity> clientEntities) {
        return clientEntities.stream().map(this::convertEntityToDTO).toList();
    }

    @Nonnull
    private ClientEntity convertDtoToEntity(@Nonnull final ClientV1DTO clientDTO) {
        return modelMapper.map(clientDTO, ClientEntity.class);
    }
}
