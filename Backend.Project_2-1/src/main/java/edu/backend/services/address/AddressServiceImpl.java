package edu.backend.services.address;

import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.address.v1.AddressesV1DTO;
import edu.backend.entities.AddressEntity;
import edu.backend.repositories.AddressRepository;
import edu.backend.utils.error_handler.ErrorResponseException;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Nonnull
    @Override
    public AddressesV1DTO getAllAddresses() {
        final var addressEntities = addressRepository.findAll();

        return new AddressesV1DTO(
                addressEntities
                        .stream()
                        .map(entity -> modelMapper.map(entity, AddressV1DTO.class))
                        .toList()
        );
    }

    @Override
    @Transactional
    public void createAddress(@Nonnull final AddressV1DTO addressDTO) {
        final var addressEntity = modelMapper.map(addressDTO, AddressEntity.class);
        Optional.ofNullable(addressEntity.getAddressId())
                .ifPresent(addressId -> {
                    if (addressRepository.findById(addressId).isPresent()) {
                        throw new ErrorResponseException(
                                String.format("Адрес по указанному address_id = %d уже существует", addressId),
                                HttpStatus.NOT_ACCEPTABLE
                        );
                    }
                });

        addressRepository.save(addressEntity);
    }

    @Override
    @Transactional
    public void updateById(final long addressId, @Nonnull final AddressV1DTO addressDTO) {
        addressRepository.findById(addressId)
                         .map(addressEntity -> {
                             final var updatableEntity = modelMapper.map(addressDTO, AddressEntity.class);
                             updatableEntity.setAddressId(addressEntity.getAddressId());
                             return updatableEntity;
                         }).ifPresentOrElse(
                                 addressRepository::save,
                                 () -> {
                                     throw new ErrorResponseException(
                                             String.format("Адрес по указанному address_id = %d не существует", addressId),
                                             HttpStatus.NOT_FOUND
                                     );
                                 }
                         );
    }

    @Override
    @Transactional
    public void updateById(final long addressId, @Nonnull final Map<String, Object> updates) {
        final var optAddress = addressRepository.findById(addressId);
        optAddress.map(address -> {
            address.setCountry(Objects.toString(updates.getOrDefault("country", address.getCountry())));
            address.setCity(Objects.toString(updates.getOrDefault("city", address.getCity())));
            address.setStreet(Objects.toString(updates.getOrDefault("street", address.getStreet())));

            return address;
        }).ifPresentOrElse(
                addressRepository::save,
                () -> {
                    throw new ErrorResponseException(
                            String.format("Адрес по указанному address_id = %d не существует", addressId),
                            HttpStatus.NOT_FOUND
                    );
                }
        );
    }

    @Override
    @Transactional
    public void deleteById(final long addressId) {
        addressRepository.findById(addressId)
                         .ifPresentOrElse(
                                 addressRepository::delete,
                                 () -> {
                                     throw new ErrorResponseException(
                                             String.format(
                                                     "Адрес по указанному address_id = %d не существует",
                                                     addressId
                                             ),
                                             HttpStatus.NOT_FOUND
                                     );
                                 }
                         );
    }
}
