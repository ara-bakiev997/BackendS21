package edu.backend.controllers.address.v1;

import edu.backend.dtos.address.v1.AddressV1DTO;
import edu.backend.dtos.address.v1.AddressesV1DTO;
import edu.backend.services.address.AddressService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Адреса", description = "Действия возможные с адресом")
@Slf4j
@RequestMapping(value = "/api/v${application.api.version}/addresses", produces = MediaType.APPLICATION_JSON_VALUE)
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
public class AddressControllerV1 {
    private final AddressService addressService;

    @Operation(summary = "Получение списка адресов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список адресов"),
    })
    @GetMapping
    public ResponseEntity<AddressesV1DTO> getAllAddresses() {
        return ResponseEntity.ok().body(addressService.getAllAddresses());
    }

    @Operation(summary = "Добавление нового адреса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Адресс добавлен успешно"),
    })
    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestBody final AddressV1DTO addressDTO) {
        addressService.createAddress(addressDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Обновление существующего адреса по address_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Адресс успешно обновлен"),
    })
    @PutMapping("/{address_id}")
    public ResponseEntity<Void> updateById(
            @PathVariable("address_id") final long addressId,
            @RequestBody final AddressV1DTO addressDTO
    ) {
        addressService.updateById(addressId, addressDTO);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Частичное обновление существующего адреса по address_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адресс успешно обновлен"),
    })
    @PatchMapping("/{address_id}")
    public ResponseEntity<Void> updateStreet(
            @PathVariable("address_id") final long addressId,
            @RequestBody @Schema(description = "Мапа для обновления только некоторых значений адреса")
            final Map<String, Object> updates
    ) {
        addressService.updateById(addressId, updates);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удаление адреса по address_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адресс успешно удален"),
    })
    @DeleteMapping("/{address_id}")
    public ResponseEntity<Void> deleteById(@PathVariable("address_id") final long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.noContent().build();
    }
}
