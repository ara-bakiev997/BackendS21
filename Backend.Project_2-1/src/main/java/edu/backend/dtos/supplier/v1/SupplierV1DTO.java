package edu.backend.dtos.supplier.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Поставщик")
public class SupplierV1DTO {
    @Schema(description = "Id поставщика", example = "null", nullable = true)
    private Long supplierId;

    @Schema(description = "Название поставщика", example = "Leroy Merlin")
    private String name;

    @Schema(description = "Id адреса поставщика", example = "null", nullable = true)
    private Long addressId;

    @Schema(description = "Телефонный номер поставщика", example = "8-800-700-00-99")
    private String phoneNumber;
}
