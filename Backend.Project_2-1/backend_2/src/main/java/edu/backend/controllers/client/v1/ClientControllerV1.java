package edu.backend.controllers.client.v1;

import edu.backend.dto.client.v1.ClientV1DTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
