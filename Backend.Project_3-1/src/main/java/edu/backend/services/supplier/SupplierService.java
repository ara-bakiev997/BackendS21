package edu.backend.services.supplier;


import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.supplier.v1.SupplierV1DTO;
import edu.backend.dtos.supplier.v1.SuppliersV1DTO;
import jakarta.annotation.Nonnull;

public interface SupplierService {

    void createSupplier(@Nonnull final SupplierV1DTO supplierDTO);

    void updateAddressById(final long supplierId, @Nonnull final AddressV1DTO addressDTO);

    void deleteById(final long supplierId);

    @Nonnull
    SuppliersV1DTO getAllSuppliers();

    @Nonnull
    SupplierV1DTO getById(final long supplierId);
}

