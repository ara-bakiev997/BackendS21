package edu.backend.services.product;

import edu.backend.dtos.product.v1.ProductV1DTO;
import edu.backend.dtos.product.v1.ProductsV1DTO;
import jakarta.annotation.Nonnull;

public interface ProductService {
    void createProduct(@Nonnull final ProductV1DTO productDTO);

    void quantityDecrementById(final long productId, final long quantityDecrement);

    @Nonnull
    ProductV1DTO getById(final long productId);

    @Nonnull
    ProductsV1DTO getAllProducts();

    void deleteById(final long productId);
}
