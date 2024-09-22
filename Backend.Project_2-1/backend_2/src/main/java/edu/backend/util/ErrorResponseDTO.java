package edu.backend.util;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.io.Serial;
import java.io.Serializable;

/**
 * @param errorMessage текст сообщения ошибки
 */

@Schema(description = "ErrorResponseDTO")
@Builder
public record ErrorResponseDTO(
        @Schema(description = "message")
        @JsonProperty(ERROR_MESSAGE_FIELD_NAME) String errorMessage) implements Serializable
{

    @Serial
    private static final long serialVersionUID = 9091798944177713542L;

    public static final String ERROR_MESSAGE_FIELD_NAME = "message";

    @JsonCreator
    public ErrorResponseDTO {
    }
}
