package edu.backend.services;

import edu.backend.dto.address.v1.AddressV1DTO;
import edu.backend.dto.client.v1.ClientV1DTO;
import edu.backend.dto.client.v1.ClientsV1DTO;
import edu.backend.entities.AddressEntity;
import edu.backend.entities.ClientEntity;
import edu.backend.repositories.ClientRepository;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ClientService {
    private final AddressService addressService;
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public void createClient(@Nonnull final ClientV1DTO clientDTO) {
        final ClientEntity clientEntity = this.convertDtoToEntity(clientDTO);
        Optional.ofNullable(clientEntity.getClientId())
                .ifPresent(clientId -> {
                    if (clientRepository.findById(clientId).isPresent()) {
                        throw new IllegalArgumentException(String.format(
                                "Клиент по указанном client_id = %d уже существует",
                                clientId
                        ));
                    }
                });

        clientEntity.setAddress(modelMapper.map(addressService.getById(clientDTO.getAddressId()), AddressEntity.class));
        clientRepository.save(clientEntity);
    }

    @Transactional
    public void deleteById(final long clientId) {
        clientRepository.deleteById(clientId);
    }

    @Nonnull
    public ClientsV1DTO getByNameAndSurname(@Nonnull final String clientName, @Nullable final String clientSurname) {
        return new ClientsV1DTO(
                clientRepository.findByClientNameAndClientSurname(clientName, clientSurname)
                                .stream()
                                .map(this::convertEntityToDTO)
                                .toList()
        );
    }

    @Nonnull
    public ClientsV1DTO getAllClients(
            @Nullable final Integer pageNumber,
            @Nullable final Integer pageSize
    ) {
        if (pageNumber == null || pageSize == null) {
            return new ClientsV1DTO(convertEntityToDTO(clientRepository.findAll()));
        }

        return new ClientsV1DTO(clientRepository
                .findAll(PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "client_id")))
                .get().map(this::convertEntityToDTO)
                .toList()
        );
    }

    @Transactional
    public void updateAddressById(final long clientId, @Nonnull final AddressV1DTO addressDTO) {
        final AddressV1DTO addressDtoFromDb =
                Optional.ofNullable(addressDTO.getAddressId())
                        .map(addressService::getById)
                        .orElseGet(() -> addressService.createAddress(addressDTO));

        clientRepository.findById(clientId)
                        .map(clientEntity -> {
                            clientEntity.setAddress(modelMapper.map(addressDtoFromDb, AddressEntity.class));

                            return clientEntity;
                        })
                        .ifPresent(clientRepository::save);
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
