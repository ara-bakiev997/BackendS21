package edu.backend.models;

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
public class SupplierDTO {
    @Nullable
    private Long supplierId;

    @Nonnull
    private String name;

    private long addressId;

    @Nonnull
    private String phoneNumber;
}
