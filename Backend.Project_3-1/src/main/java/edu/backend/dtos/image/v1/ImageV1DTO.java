package edu.backend.dtos.image.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Изображение")
public class ImageV1DTO {
    @Schema(description = "Id изображения", example = "27b45de2-1d91-49db-a9ff-7bfe8702aac1", nullable = true)
    private UUID imageId;

    @Schema(description = "Изображение в виде массива байтов")
    private byte[] image;
}
