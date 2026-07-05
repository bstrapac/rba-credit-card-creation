package rba.card_creation.utils;

import lombok.extern.slf4j.Slf4j;
import org.h2.util.StringUtils;
import org.springframework.stereotype.Component;
import rba.card_creation.model.NewCardRequest;

@Component
@Slf4j
public class Validation {
    public void validateRequest(NewCardRequest request) {
        if(request.getLastName() == null || request.getFirstName() == null) {
            log.error("First name and/or last name cannot be null");
            throw new IllegalArgumentException("First name and/or last name cannot be null");
        }
        if(request.getLastName().isEmpty() || request.getFirstName().isEmpty()) {
            log.error("First name and/or last name cannot be empty");
            throw new IllegalArgumentException("First name and/or last name cannot be empty");
        }
        validateOib(request.getOib());
    }

    public void validateOib(String oib) {
        if (oib == null) {
            log.error("OIB cannot be null");
            throw new IllegalArgumentException("OIB cannot be null");
        }
        if(!StringUtils.isNumber(oib)) {
            log.error("OIB must be a number");
            throw new IllegalArgumentException("OIB must be a number");
        }
        if (oib.length() != 11) {
            log.error("OIB must be 11 characters long");
            throw new IllegalArgumentException("OIB must be 11 characters long");
        }
    }
}
