package edu.backend.dtos.product.v1;

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
@Schema(description = "Товары")
public class ProductsV1DTO {
    @Schema(description = "Список товаров")
    private List<ProductV1DTO> products;
}
