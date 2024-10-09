package edu.backend.repositories;

import edu.backend.entities.ProductEntity;
import edu.backend.entities.SupplierEntity;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Nonnull
    List<ProductEntity> findBySupplier(@Nonnull final SupplierEntity supplierEntity);
}
