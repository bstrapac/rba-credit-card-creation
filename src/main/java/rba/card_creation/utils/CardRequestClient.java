package rba.card_creation.utils;

import org.springframework.stereotype.Component;
import rba.card_creation.exception.CardApiException;
import rba.card_creation.model.NewCardRequest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Simple client that posts NewCardRequest payload to the API described in task.yaml.
 * Uses java.net.http.HttpClient to avoid adding extra dependencies.
 * Throws CardApiException on non-2xx status codes.
 */
@Component
public class CardRequestClient {

    private final HttpClient httpClient;

    public CardRequestClient() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public void sendNewCardRequest(NewCardRequest request) throws Exception {
        String json = buildJson(request);
        String baseUrl = "https://api.something.com/v1";
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/v1/card-request"))
                .timeout(Duration.ofSeconds(10))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new CardApiException(response.statusCode(), response.body(),
                    "API request failed with status " + response.statusCode());
        }
    }

    private String buildJson(NewCardRequest request) {
        String status = CardStatus.PENDING.toString();
        String fn = safe(request.getFirstName());
        String ln = safe(request.getLastName());
        String oib = safe(request.getOIB());
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"firstName\":\"").append(fn).append("\",");
        sb.append("\"lastName\":\"").append(ln).append("\",");
        sb.append("\"status\":").append(status==null?"null":"\""+safe(status)+"\"").append(",");
        sb.append("\"oib\":\"").append(oib).append("\"");
        sb.append("}");
        return sb.toString();
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
