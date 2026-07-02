package rba.card_creation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import rba.card_creation.utils.CardStatus;

@Data
@NoArgsConstructor
public class RequestResponse {
    String oib;
    CardStatus cardStatus;
}
