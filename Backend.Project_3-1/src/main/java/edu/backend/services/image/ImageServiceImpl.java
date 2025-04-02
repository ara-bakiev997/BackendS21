package edu.backend.services.image;

import edu.backend.dtos.image.v1.ImageV1DTO;
import edu.backend.dtos.image.v1.ImagesV1DTO;
import edu.backend.entities.ImageEntity;
import edu.backend.entities.ProductEntity;
import edu.backend.repositories.ImageRepository;
import edu.backend.repositories.ProductRepository;
import edu.backend.utils.error_handler.ErrorResponseException;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createImageForProductId(@Nonnull final byte[] image, final long productId) {
        productRepository.findById(productId)
                         .map(product -> {
                             final var savedImage = imageRepository.save(ImageEntity.builder().image(image).build());
                             product.setImage(savedImage);
                             return product;
                         }).ifPresentOrElse(
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


    @Override
    @Transactional
    public void updateImageById(@Nonnull final UUID imageId, @Nonnull final byte[] image) {
        imageRepository.findById(imageId)
                       .map(imageEntity -> {
                           imageEntity.setImage(image);
                           return imageEntity;
                       })
                       .ifPresentOrElse(
                               imageRepository::save,
                               () -> {
                                   throw new ErrorResponseException(
                                           String.format(
                                                   "Изображение по указанному imageId = %s не существует",
                                                   imageId
                                           ),
                                           HttpStatus.NOT_FOUND
                                   );
                               }
                       );
    }

    @Override
    @Transactional
    public void deleteById(@Nonnull final UUID imageId) {
        imageRepository.findById(imageId)
                       .ifPresentOrElse(
                               imageRepository::delete,
                               () -> {
                                   throw new ErrorResponseException(
                                           String.format(
                                                   "Изображение по указанному imageId = %s не существует",
                                                   imageId
                                           ),
                                           HttpStatus.NOT_FOUND
                                   );
                               }
                       );
    }

    @Nonnull
    @Override
    public ImagesV1DTO getAllImages() {
        return new ImagesV1DTO(imageRepository
                .findAll()
                .stream()
                .map(imageEntity -> modelMapper.map(imageEntity, ImageV1DTO.class))
                .toList());
    }

    @Nonnull
    @Override
    public ImageV1DTO getByProductId(final long productId) {
        return productRepository.findById(productId)
                                .map(ProductEntity::getImage)
                                .map(imageEntity -> modelMapper.map(imageEntity, ImageV1DTO.class))
                                .orElseThrow(() -> new ErrorResponseException(
                                        String.format(
                                                "Продукт по указанному productId = %s не существует",
                                                productId
                                        ),
                                        HttpStatus.NOT_FOUND
                                ));
    }

    @Nonnull
    @Override
    public ImageV1DTO getById(@Nonnull final UUID imageId) {
        return imageRepository.findById(imageId)
                              .map(imageEntity -> modelMapper.map(imageEntity, ImageV1DTO.class))
                              .orElseThrow(() -> new ErrorResponseException(
                                      String.format(
                                              "Изображение по указанному imageId = %s не существует",
                                              imageId
                                      ),
                                      HttpStatus.NOT_FOUND
                              ));
    }
}
