package edu.backend.services.product;

import edu.backend.dtos.product.v1.ProductV1DTO;
import edu.backend.dtos.product.v1.ProductsV1DTO;
import edu.backend.entities.ProductEntity;
import edu.backend.repositories.ImageRepository;
import edu.backend.repositories.ProductRepository;
import edu.backend.repositories.SupplierRepository;
import edu.backend.utils.error_handler.ErrorResponseException;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createProduct(@Nonnull final ProductV1DTO productDTO) {
        final var productEntity = modelMapper.map(productDTO, ProductEntity.class);

        Optional.ofNullable(productEntity.getProductId())
                .ifPresent(productId -> {
                    if (productRepository.findById(productId).isPresent()) {
                        throw new ErrorResponseException(
                                String.format("Продукт по указанном productId = %d уже существует", productId),
                                HttpStatus.NOT_ACCEPTABLE
                        );
                    }
                });

        Optional.of(productDTO.getSupplierId())
                .flatMap(supplierRepository::findById)
                .ifPresentOrElse(
                        productEntity::setSupplier,
                        () -> {
                            throw new ErrorResponseException(
                                    String.format(
                                            "Указанный поставщик не существует supplierId = %d",
                                            productDTO.getSupplierId()
                                    ),
                                    HttpStatus.NOT_FOUND
                            );
                        }
                );

        Optional.ofNullable(productDTO.getImageId())
                .flatMap(imageRepository::findById)
                .ifPresent(productEntity::setImage);

        productRepository.save(productEntity);
    }

    @Override
    @Transactional
    public void quantityDecrementById(final long productId, final long quantityDecrement) {
        productRepository.findById(productId)
                         .flatMap(productEntity ->
                                 Optional.ofNullable(productEntity.getAvailableStock())
                                         .map(availableStock -> {
                                             if (quantityDecrement <= 0 || quantityDecrement > availableStock) {
                                                 throw new ErrorResponseException(
                                                         String.format(
                                                                 "На указанное количество нельзя уменьшить quantityDecrement = %d",
                                                                 availableStock
                                                         ),
                                                         HttpStatus.BAD_REQUEST
                                                 );
                                             }
                                             return availableStock - quantityDecrement;
                                         })
                                         .map(currentStock -> {
                                             productEntity.setAvailableStock(currentStock);
                                             return productEntity;
                                         }))
                         .ifPresentOrElse(
                                 productRepository::save,
                                 () -> {
                                     throw new ErrorResponseException(
                                             String.format(
                                                     "Продукт по указанному productId = %d не существует",
                                                     productId
                                             ),
                                             HttpStatus.NOT_FOUND
                                     );
                                 }
                         );
    }

    @Nonnull
    @Override
    public ProductV1DTO getById(final long productId) {
        return productRepository.findById(productId)
                                .map(productEntity -> modelMapper.map(productEntity, ProductV1DTO.class))
                                .orElseThrow(() ->
                                        new ErrorResponseException(
                                                String.format(
                                                        "Продукт по указанному productId = %d не существует",
                                                        productId
                                                ),
                                                HttpStatus.NOT_FOUND
                                        )
                                );
    }

    @Nonnull
    @Override
    public ProductsV1DTO getAllProducts() {
        return new ProductsV1DTO(
                productRepository
                        .findAll()
                        .stream()
                        .map(productEntity -> modelMapper.map(productEntity, ProductV1DTO.class))
                        .toList()
        );
    }

    @Override
    @Transactional
    public void deleteById(final long productId) {
        productRepository.findById(productId)
                         .ifPresentOrElse(
                                 productRepository::delete,
                                 () -> {
                                     throw new ErrorResponseException(
                                             String.format(
                                                     "Продукт по указанному productId = %d не существует",
                                                     productId
                                             ),
                                             HttpStatus.NOT_FOUND
                                     );
                                 }
                         );
    }
}
