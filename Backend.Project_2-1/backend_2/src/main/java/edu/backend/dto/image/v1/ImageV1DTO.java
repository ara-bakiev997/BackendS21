package edu.backend.dto.image.v1;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ImageV1DTO {
    @Nullable
    private UUID imageId;

    @Nonnull
    private byte[] image;
}
