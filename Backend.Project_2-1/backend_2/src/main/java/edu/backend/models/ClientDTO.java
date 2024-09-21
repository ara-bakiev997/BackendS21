package edu.backend.models;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClientDTO {
    @Nullable
    private Long clientId;

    @Nonnull
    private String clientName;

    @Nullable
    private String clientSurname;

    @Nonnull
    private LocalDate birthday;

    @Nonnull
    private String gender;

    @Nonnull
    private LocalDate registrationDate;

    private long addressId;
}
