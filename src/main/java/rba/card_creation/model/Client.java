package rba.card_creation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import rba.card_creation.utils.CardStatus;

@Data
@NoArgsConstructor
public class Client {
    String firstName;
    String lastName;
    String oib;
    CardStatus cardStatus;
}
