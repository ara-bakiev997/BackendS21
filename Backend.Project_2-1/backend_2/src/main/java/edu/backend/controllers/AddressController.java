package edu.backend.controllers;

import edu.backend.models.AddressDTO;
import edu.backend.services.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1/addresses")
@RestController
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PostMapping
    public void createAddress(@RequestBody final AddressDTO addressDTO) {
        addressService.createAddress(addressDTO);
    }

    @PutMapping("/{address_id}")
    public void updateById(
            @PathVariable("address_id") final long addressId,
            @RequestBody final AddressDTO addressDTO
    ) {
        addressService.updateById(addressId, addressDTO);
    }

    @PatchMapping("/{address_id}")
    public void updateStreet(
            @PathVariable("address_id") final long addressId,
            @RequestBody final Map<String, Object> updates
    ) {
        addressService.updateById(addressId, updates);
    }

    @DeleteMapping("/{address_id}")
    public void deleteById(@PathVariable("address_id") final long addressId) {
        addressService.deleteById(addressId);
    }
}
