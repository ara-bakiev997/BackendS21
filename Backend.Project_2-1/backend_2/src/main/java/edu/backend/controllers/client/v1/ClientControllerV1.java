package edu.backend.controllers.client.v1;

import edu.backend.dto.address.v1.AddressV1DTO;
import edu.backend.dto.client.v1.ClientV1DTO;
import edu.backend.dto.client.v1.ClientsV1DTO;
import edu.backend.services.ClientService;
import edu.backend.util.ErrorResponseDTO;
import edu.backend.util.ExampleResponseError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Клиент", description = "Действия возможные с клиентом")
@Slf4j
@RequestMapping("/api/v${application.api.version}/clients")
@RestController
@AllArgsConstructor
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400", description = "Bad request",
                content = @Content(
                        examples = @ExampleObject(value = ExampleResponseError.BAD_REQUEST),
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
public class ClientControllerV1 {
    private final ClientService clientService;

    @Operation(summary = "Добавление нового клиента")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент добавлен успешно"),
    })
    @PostMapping
    public void createClient(@RequestBody final ClientV1DTO clientDTO) {
        clientService.createClient(clientDTO);
    }

    @Operation(summary = "Удаление клиента по client_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клиент успешно удален"),
    })
    @DeleteMapping("/{client_id}")
    public void deleteById(@PathVariable("client_id") final long clientId) {
        clientService.deleteById(clientId);
    }

    @Operation(summary = "Получение клиента по имени и фамилии")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список адресов"),
    })
    @GetMapping("/getByNameAndSurname")
    public ClientsV1DTO getByNameAndSurname(
            @RequestParam("client_name") final String clientName,
            @RequestParam(value = "client_surname", required = false) final String clientSurname
    ) {
        return clientService.getByNameAndSurname(clientName, clientSurname);
    }

    @Operation(summary = "Получение всех клиентов")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список клиентов"),
    })
    @GetMapping
    public ClientsV1DTO getAllClients(
            @RequestParam(value = "limit", required = false) final Integer limit,
            @RequestParam(value = "offset", required = false) final Integer offset
    ) {
        return clientService.getAllClients(offset, limit);
    }

    @Operation(summary = "Изменение адреса клиента по client_id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Адресс клиента успешно обновлен"),
    })
    @PatchMapping("/{client_id}")
    public void updateAddressById(
            @PathVariable("client_id") final long clientId,
            @RequestBody final AddressV1DTO addressDTO
    ) {
        clientService.updateAddressById(clientId, addressDTO);
    }
}
