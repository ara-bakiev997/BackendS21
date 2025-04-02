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
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;


@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "images")
public class ImageEntity {
    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    @Nullable
    private UUID imageId;

    @Column(name = "image")
    @Nonnull
    private byte[] image;

    @OneToMany(mappedBy = "image", cascade = CascadeType.PERSIST)
    private Set<ProductEntity> products;
}
