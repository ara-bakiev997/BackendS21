package edu.backend.dto.client.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Клиент")
public class ClientV1DTO {
    @Schema(description = "Id клиента", example = "1", nullable = true)
    private Long clientId;

    @Schema(description = "Имя клиента", example = "Ivan")
    private String clientName;

    @Schema(description = "Фамилия клиента", example = "Ivanov")
    private String clientSurname;

    @Schema(description = "Дата рождения клиента", example = "1870-04-22")
    private LocalDate birthday;

    @Schema(description = "Пол клиента", examples = {"female", "male"}, example = "male")
    private String gender;

    @Schema(description = "Дата регистрации клиента, если не указанно то текущая дата", example = "1870-04-22", nullable = true)
    private LocalDate registrationDate;

    @Schema(description = "Id адреса клиента", example = "1")
    private long addressId;
}
