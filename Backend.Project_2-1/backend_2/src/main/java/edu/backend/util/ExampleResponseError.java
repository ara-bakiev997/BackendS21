package edu.backend.util;

/**
 * Класс генерации примеров ошибок для сваггера
 */
public final class ExampleResponseError {
    public static final String BAD_REQUEST = """
            {
                "message": "Limit must be positive number"
            }
            """;
    public static final String NOT_FOUND = """
            {
                "message": "Not found"
            }
            """;

    public static final String INTERNAL_SERVER_ERROR = """
            {
                "message": "Internal Server Error"
            }
            """;

    private ExampleResponseError() {
    }
}