package rba.card_creation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewCardRequest {
    String firstName;
    String lastName;
    String oib;
}
