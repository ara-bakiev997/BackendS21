package edu.backend.services.image;

import edu.backend.dtos.image.v1.ImageV1DTO;
import edu.backend.dtos.image.v1.ImagesV1DTO;
import jakarta.annotation.Nonnull;

import java.util.UUID;

public interface ImageService {
    void createImageForProductId(final byte[] image, final long productId);

    void updateImageById(@Nonnull final UUID imageId, @Nonnull final byte[] image);

    void deleteById(@Nonnull final UUID imageId);

    @Nonnull
    ImagesV1DTO getAllImages();

    @Nonnull
    ImageV1DTO getByProductId(final long productId);

    @Nonnull
    ImageV1DTO getById(@Nonnull final UUID imageId);

}
