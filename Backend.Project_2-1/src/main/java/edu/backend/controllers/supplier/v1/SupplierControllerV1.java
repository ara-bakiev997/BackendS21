package edu.backend.controllers.supplier.v1;

import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.supplier.v1.SupplierV1DTO;
import edu.backend.dtos.supplier.v1.SuppliersV1DTO;
import edu.backend.services.supplier.SupplierService;
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
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Поставщики", description = "Действия возможные с поставщиками")
@Slf4j
@RequestMapping("/api/v${application.api.version}/suppliers")
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
public class SupplierControllerV1 {
    private final SupplierService supplierService;

    @Operation(summary = "Добавление поставщика")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Поставщик добавлен успешно"),
    })
    @PostMapping
    public ResponseEntity<Void> createSupplier(@RequestBody final SupplierV1DTO supplierDTO) {
        supplierService.createSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Изменение адреса поставщика по supplier_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Адресс поставщика успешно обновлен"),
    })
    @PatchMapping("/{supplier_id}")
    public ResponseEntity<Void> updateAddressById(
            @PathVariable("supplier_id") final long supplierId,
            @RequestBody final AddressV1DTO addressDTO
    ) {
        supplierService.updateAddressById(supplierId, addressDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удаление поставщика по supplier_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Поставщик успешно удален"),
    })
    @DeleteMapping("/{supplier_id}")
    public ResponseEntity<Void> deleteById(@PathVariable("supplier_id") final long supplierId) {
        supplierService.deleteById(supplierId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получение всех доступных поставщиков")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список поставщиков"),
    })
    @GetMapping
    public ResponseEntity<SuppliersV1DTO> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @Operation(summary = "Получение поставщика по supplier_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Поставщик успешно получен"),
    })
    @GetMapping("/{supplier_id}")
    public ResponseEntity<SupplierV1DTO> getById(
            @PathVariable("supplier_id") final long supplierId
    ) {
        return ResponseEntity.ok(supplierService.getById(supplierId));
    }
}
