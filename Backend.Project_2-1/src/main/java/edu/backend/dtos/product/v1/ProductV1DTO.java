package edu.backend.dtos.product.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Товар")
public class ProductV1DTO {
    @Schema(description = "Id товара", example = "null", nullable = true)
    private Long productId;

    @Schema(description = "Имя товара", example = "Cement")
    private String name;

    @Schema(description = "Категория товара", example = "Construction products")
    private String category;

    @Schema(description = "Стоимость товара", example = "590.00")
    private BigDecimal price;

    @Schema(description = "Число закупленных экземпляров товара", example = "100", nullable = true)
    private Long availableStock;

    @Schema(description = "Число последней закупки", example = "2024-09-24", nullable = true)
    private LocalDate lastUpdateDate;

    @Schema(description = "Id поставщика", example = "1")
    private long supplierId;

    @Schema(description = "Id изображения", example = "null", nullable = true)
    private UUID imageId;
}
