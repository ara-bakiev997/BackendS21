package edu.backend.entities;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "addresses")
public class AddressEntity {
    @Id
    @Column(name = "address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long addressId;

    @Column(name = "country")
    @Nonnull
    private String country;

    @Column(name = "city")
    @Nonnull
    private String city;

    @Column(name = "street")
    @Nonnull
    private String street;

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private Set<SupplierEntity> suppliers;

    @OneToMany(mappedBy = "address", cascade = CascadeType.PERSIST)
    private Set<ClientEntity> clients;
}
