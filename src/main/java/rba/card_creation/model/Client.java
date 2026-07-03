package rba.card_creation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    private Long id;
    String firstName;
    String lastName;
    @Column(unique = true, nullable = false)
    String oib;
    @Enumerated(EnumType.STRING)
    CardStatus cardStatus;
}
