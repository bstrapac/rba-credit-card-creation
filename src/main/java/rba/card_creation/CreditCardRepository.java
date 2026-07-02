package rba.card_creation;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import rba.card_creation.model.Client;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public class CreditCardRepository {
    @Transactional
    public Client save(Client client) { return client; }

    public void deleteByOIB(String oib) {}

    public Optional<Client> findByOIB(String oib) {
        return Optional.empty();
    }
}
