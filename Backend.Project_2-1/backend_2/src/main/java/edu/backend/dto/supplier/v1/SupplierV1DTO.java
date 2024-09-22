package edu.backend.dto.supplier.v1;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SupplierV1DTO {
    @Nullable
    private Long supplierId;

    @Nonnull
    private String name;

    private long addressId;

    @Nonnull
    private String phoneNumber;
}
