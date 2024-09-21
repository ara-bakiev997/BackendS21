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
public class AddressDTO {
    @Nullable
    private Long addressId;

    @Nonnull
    private String country;

    @Nonnull
    private String city;

    @Nonnull
    private String street;
}
