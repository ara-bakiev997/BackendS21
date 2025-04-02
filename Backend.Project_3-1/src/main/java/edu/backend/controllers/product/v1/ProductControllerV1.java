package edu.backend.controllers.product.v1;

import edu.backend.dtos.product.v1.ProductV1DTO;
import edu.backend.dtos.product.v1.ProductsV1DTO;
import edu.backend.services.product.ProductService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Товары", description = "Действия возможные с товарами")
@Slf4j
@RequestMapping("/api/v${application.api.version}/products")
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
public class ProductControllerV1 {
    private final ProductService productService;

    @Operation(summary = "Добавление товара")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Товар добавлен успешно"),
    })
    @PostMapping
    public ResponseEntity<Void> createProduct(@RequestBody final ProductV1DTO productDTO) {
        productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Уменьшение количества товара product_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Количества товара успешно уменьшено"),
    })
    @PatchMapping("/{product_id}")
    public ResponseEntity<Void> quantityDecrementById(
            @PathVariable("product_id") final long productId,
            @RequestParam("quantity_decrement") final long quantityDecrement
    ) {
        productService.quantityDecrementById(productId, quantityDecrement);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получение товара по product_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Товар успешно получен"),
    })
    @GetMapping("/{product_id}")
    public ResponseEntity<ProductV1DTO> getById(
            @PathVariable("product_id") final long productId
    ) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @Operation(summary = "Получение всех доступных товаров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список товаров"),
    })
    @GetMapping
    public ResponseEntity<ProductsV1DTO> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Удаление товара по product_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Товар успешно удален"),
    })
    @DeleteMapping("/{product_id}")
    public ResponseEntity<Void> deleteById(@PathVariable("product_id") final long productId) {
        productService.deleteById(productId);
        return ResponseEntity.noContent().build();
    }
}
