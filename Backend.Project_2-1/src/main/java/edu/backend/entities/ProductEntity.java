package edu.backend.entities;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "products")
public class ProductEntity {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(name = "name")
    @Nonnull
    private String name;

    @Column(name = "category")
    @Nonnull
    private String category;

    @Column(name = "price")
    @Nonnull
    private BigDecimal price;

    @Column(name = "available_stock")
    @Nullable
    private Long availableStock;

    @Column(name = "last_update_date")
    @Nullable
    private LocalDate lastUpdateDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id")
    private SupplierEntity supplier;

    @ManyToOne
    @JoinColumn(name = "image_id", referencedColumnName = "image_id")
    private ImageEntity image;
}
