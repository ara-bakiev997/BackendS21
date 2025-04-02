package edu.backend.services.client;

import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.client.v1.ClientV1DTO;
import edu.backend.dtos.client.v1.ClientsV1DTO;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public interface ClientService {


    void createClient(@Nonnull final ClientV1DTO clientDTO);

    void deleteById(final long clientId);

    @Nonnull
    ClientsV1DTO getByNameAndSurname(@Nonnull final String clientName, @Nullable final String clientSurname);

    @Nonnull
    ClientsV1DTO getAllClients(@Nullable final Integer pageNumber, @Nullable final Integer pageSize);

    void updateAddressById(final long clientId, @Nonnull final AddressV1DTO addressDTO);
}
