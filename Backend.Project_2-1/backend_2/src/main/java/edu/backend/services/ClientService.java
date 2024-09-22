package edu.backend.services;

import edu.backend.dto.client.v1.ClientV1DTO;
import edu.backend.entities.ClientEntity;
import edu.backend.repositories.ClientRepository;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ClientService {
    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public void createClient(@Nonnull final ClientV1DTO clientDTO) {
        final ClientEntity clientEntity = modelMapper.map(clientDTO, ClientEntity.class);
        Optional.ofNullable(clientEntity.getClientId())
                .ifPresent(clientId -> {
                    if (clientRepository.findById(clientId).isPresent()) {
                        throw new IllegalArgumentException(String.format(
                                "Клиент по указанном client_id = %d уже существует",
                                clientId
                        ));
                    }
                });

        clientRepository.save(clientEntity);
    }
}
