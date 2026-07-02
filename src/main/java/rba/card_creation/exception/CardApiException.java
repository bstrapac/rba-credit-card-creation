package rba.card_creation.exception;

public class CardApiException extends RuntimeException {
    private final int statusCode;
    private final String responseBody;

    public CardApiException(int statusCode, String responseBody, String message) {
        super(message);
        this.statusCode = statusCode;
        this.responseBody = responseBody;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
