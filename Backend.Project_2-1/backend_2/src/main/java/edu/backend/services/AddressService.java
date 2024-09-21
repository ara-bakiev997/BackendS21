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

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void createAddress(@Nonnull final AddressDTO addressDTO) {
        final AddressEntity addressEntity = modelMapper.map(addressDTO, AddressEntity.class);

        addressRepository.save(addressEntity);
    }

    @Nonnull
    public List<AddressDTO> getAllAddresses() {
        final List<AddressEntity> addressEntities = addressRepository.findAll();

        return addressEntities.stream().map(entity -> modelMapper.map(entity, AddressDTO.class)).toList();
    }
}
