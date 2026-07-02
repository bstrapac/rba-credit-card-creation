package rba.card_creation.model;


import lombok.Data;
import rba.card_creation.utils.CardStatus;

@Data
public class Client {
    String firstName;
    String lastName;
    String OIB;
    CardStatus cardStatus;
}
