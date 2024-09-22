package edu.backend.models.address.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Адреса")
public class AddressesV1DTO {
    @Schema(description = "Список адресов")
    private List<AddressV1DTO> addresses;
}
