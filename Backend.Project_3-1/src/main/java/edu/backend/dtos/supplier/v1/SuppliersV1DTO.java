package edu.backend.dtos.supplier.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Поставщики")
public class SuppliersV1DTO {
    @Schema(description = "Список поставщиков")
    private List<SupplierV1DTO> suppliers;
}
