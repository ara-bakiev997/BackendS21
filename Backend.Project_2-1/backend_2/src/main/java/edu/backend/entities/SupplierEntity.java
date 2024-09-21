package edu.backend.entities;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "suppliers")
public class SupplierEntity {
    @Id
    @Column(name = "supplier_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long supplierId;

    @Column(name = "name")
    @Nonnull
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    @Nonnull
    private AddressEntity address;

    @Column(name = "phone_number")
    @Nonnull
    private String phoneNumber;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.PERSIST)
    private Set<ProductEntity> products;
}
