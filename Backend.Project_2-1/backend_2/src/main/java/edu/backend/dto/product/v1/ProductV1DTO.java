package edu.backend.dto.product.v1;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductV1DTO {
    @Nullable
    private Long productId;

    @Nonnull
    private String name;

    @Nonnull
    private String category;

    @Nonnull
    private BigDecimal price;

    @Nullable
    private Long availableStock;

    @Nullable
    private LocalDate lastUpdateDate;

    private long supplierId;

    @Nonnull
    private UUID imageId;
}
