package rba.card_creation.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "oib", unique = true, nullable = false)
    String oib;
    @Enumerated(EnumType.STRING)
    @Column(name = "card_status")
    CardStatus cardStatus;
}
