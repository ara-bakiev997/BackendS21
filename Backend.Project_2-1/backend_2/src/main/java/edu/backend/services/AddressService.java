package edu.backend.services;

import edu.backend.entities.AddressEntity;
import edu.backend.models.AddressDTO;
import edu.backend.repositories.AddressRepository;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Nonnull
    public List<AddressDTO> getAllAddresses() {
        final List<AddressEntity> addressEntities = addressRepository.findAll();

        return addressEntities.stream().map(entity -> modelMapper.map(entity, AddressDTO.class)).toList();
    }

    @Transactional
    public void createAddress(@Nonnull final AddressDTO addressDTO) {
        final AddressEntity addressEntity = modelMapper.map(addressDTO, AddressEntity.class);
        addressRepository.save(addressEntity);
    }

    @Transactional
    public void updateById(final long addressId, @Nonnull final AddressDTO addressDTO) {
        final AddressEntity addressEntity = modelMapper.map(addressDTO, AddressEntity.class);
        addressEntity.setAddressId(addressId);
        addressRepository.save(addressEntity);
    }

    @Transactional
    public void updateById(final long addressId, @Nonnull final Map<String, Object> updates) {
        final Optional<AddressEntity> optAddress = addressRepository.findById(addressId);
        optAddress.map(address -> {
            address.setCountry(Objects.toString(updates.getOrDefault("country", address.getCountry())));
            address.setCity(Objects.toString(updates.getOrDefault("city", address.getCity())));
            address.setStreet(Objects.toString(updates.getOrDefault("street", address.getStreet())));

            return address;
        }).ifPresent(addressRepository::save);
    }

    @Transactional
    public void deleteById(final long addressId) {
        addressRepository.deleteById(addressId);
    }
}
