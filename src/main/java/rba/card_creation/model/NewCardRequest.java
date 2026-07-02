package rba.card_creation.model;

import lombok.Data;

@Data
public class NewCardRequest {
    String firstName;
    String lastName;
    String OIB;
}
