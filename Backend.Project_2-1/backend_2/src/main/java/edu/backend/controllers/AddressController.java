package edu.backend.controllers;

import edu.backend.models.AddressDTO;
import edu.backend.services.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/addresses")
@RestController
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping
    public void createAddress(@RequestBody final AddressDTO addressDTO) {
        addressService.createAddress(addressDTO);
    }

    @GetMapping
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

}
