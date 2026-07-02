package rba.card_creation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rba.card_creation.model.Client;

import java.util.Optional;

@Repository
public interface CreditCardRepository extends JpaRepository<Client, Long> {

    void deleteByOib(String oib);

    Optional<Client> findByOib(String oib);
}
