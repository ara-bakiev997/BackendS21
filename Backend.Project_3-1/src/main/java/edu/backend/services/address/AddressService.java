package edu.backend.services.address;

import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.address.v1.AddressesV1DTO;
import jakarta.annotation.Nonnull;

import java.util.Map;

public interface AddressService {
    @Nonnull
    AddressesV1DTO getAllAddresses();

    void createAddress(@Nonnull final AddressV1DTO addressDTO);

    void updateById(final long addressId, @Nonnull final AddressV1DTO addressDTO);

    void updateById(final long addressId, @Nonnull final Map<String, Object> updates);

    void deleteById(final long addressId);
}
