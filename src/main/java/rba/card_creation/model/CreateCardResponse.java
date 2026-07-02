package rba.card_creation.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import rba.card_creation.utils.CardStatus;

@Data
@NoArgsConstructor
public class CreateCardResponse {
    String OIB;
    CardStatus cardStatus;

    public CreateCardResponse(String oib, CardStatus cardStatus) {}
}
