package rba.card_creation.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private String code;
    private String id;
    private String description;

    public ErrorResponse(String code, String id, String description) {
        this.code = code;
        this.id = id;
        this.description = description;
    }
}
