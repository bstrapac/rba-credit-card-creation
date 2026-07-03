package rba.card_creation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestResponse {
    String oib;
    CardStatus cardStatus;
}
