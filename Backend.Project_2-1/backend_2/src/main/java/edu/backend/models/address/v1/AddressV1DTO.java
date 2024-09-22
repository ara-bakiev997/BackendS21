package edu.backend.models.address.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Адрес")
public class AddressV1DTO {
    @Schema(description = "Id адреса", example = "1", nullable = true)
    private Long addressId;

    @Schema(description = "Название страны", example = "Russia")
    private String country;

    @Schema(description = "Название города", example = "Kazan")
    private String city;

    @Schema(description = "Название улицы", example = "Bauman")
    private String street;
}
