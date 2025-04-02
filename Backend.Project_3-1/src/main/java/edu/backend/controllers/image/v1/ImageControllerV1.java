package edu.backend.controllers.image.v1;

import edu.backend.dtos.image.v1.ImagesV1DTO;
import edu.backend.services.image.ImageService;
import edu.backend.utils.error_handler.ErrorResponseDTO;
import edu.backend.utils.error_handler.ExampleResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Tag(name = "Изображения", description = "Действия возможные с изображениями")
@Slf4j
@RequestMapping("/api/v${application.api.version}/images")
@RestController
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400", description = "Bad request",
                content = @Content(
                        examples = @ExampleObject(value = ExampleResponseError.BAD_REQUEST),
                        schema = @Schema(implementation = ErrorResponseDTO.class)
                )),
        @ApiResponse(
                responseCode = "404", description = "Not found",
                content = @Content(
                        examples = @ExampleObject(value = ExampleResponseError.NOT_FOUND),
                        schema = @Schema(implementation = ErrorResponseDTO.class)
                )),
        @ApiResponse(
                responseCode = "406", description = "Not Acceptable",
                content = @Content(
                        examples = @ExampleObject(value = ExampleResponseError.NOT_ACCEPTABLE),
                        schema = @Schema(implementation = ErrorResponseDTO.class)
                )),
        @ApiResponse(responseCode = "429", description = "Too Many Requests", content = @Content),
        @ApiResponse(
                responseCode = "500", description = "Internal Server Error",
                content = @Content(
                        examples = @ExampleObject(value = ExampleResponseError.INTERNAL_SERVER_ERROR),
                        schema = @Schema(implementation = ErrorResponseDTO.class)
                ))
})
public class ImageControllerV1 {
    private final ImageService imageService;

    @Operation(summary = "Добавление изображения")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Изображение добавлено успешно"),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createImage(
            @RequestParam("productId") final long productId,
            @RequestPart("image") final MultipartFile image
    ) throws IOException {
        imageService.createImageForProductId(image.getBytes(), productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Изменение изображения по image_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Изображение успешно изменено"),
    })
    @PutMapping(value = "/{image_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateImageById(
            @PathVariable("image_id") final UUID imageId,
            @RequestPart("image") final MultipartFile image
    ) throws IOException {
        imageService.updateImageById(imageId, image.getBytes());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удаление изображения по image_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Изображение успешно удалено"),
    })
    @DeleteMapping("/{image_id}")
    public ResponseEntity<Void> deleteById(@PathVariable("image_id") final UUID imageId) {
        imageService.deleteById(imageId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получение всех загруженных изображений")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список изображений"),
    })
    @GetMapping
    public ResponseEntity<ImagesV1DTO> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @Operation(summary = "Получение изображения конкретного товара по product_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно получено"),
    })
    @GetMapping(value = "/getByProductId", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getByProductId(
            @RequestParam("product_id") final long productId
    ) {
        final byte[] image = imageService.getByProductId(productId).getImage();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @Operation(summary = "Получение изображения по image_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Изображение успешно получено"),
    })
    @GetMapping(value = "/{image_id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> getById(
            @PathVariable("image_id") final UUID imageId
    ) {
        final byte[] image = imageService.getById(imageId).getImage();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
