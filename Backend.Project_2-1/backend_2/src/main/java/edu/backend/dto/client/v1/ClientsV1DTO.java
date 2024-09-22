package edu.backend.dto.client.v1;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Schema(description = "Клиенты")
public class ClientsV1DTO {
    @Schema(description = "Список клиентов")
    private List<ClientV1DTO> clients;
}
