package rba.card_creation.utils;

import org.springframework.stereotype.Component;
import rba.card_creation.model.NewCardRequest;

@Component
public class Validation {
    public void validateRequest(NewCardRequest request) {
        if(request.getLastName() == null || request.getFirstName() == null) {
            throw new IllegalArgumentException("First name and/or last name cannot be null");
        }

        if(request.getLastName().isEmpty() || request.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name and/or last name cannot be empty");
        }

        validateOib(request.getOib());
    }

    public void validateOib(String oib) {
        if (oib == null) {
            throw new IllegalArgumentException("OIB cannot be null");
        }
        if (oib.length() != 11) {
            throw new IllegalArgumentException("OIB must be 11 characters long");
        }
    }
}
