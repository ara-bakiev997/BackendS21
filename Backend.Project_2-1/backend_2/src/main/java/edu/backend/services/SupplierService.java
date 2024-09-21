package edu.backend.services;


import edu.backend.repositories.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;


}

