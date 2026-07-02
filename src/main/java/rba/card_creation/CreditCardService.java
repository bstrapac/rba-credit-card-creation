package rba.card_creation;

import org.springframework.stereotype.Service;
import rba.card_creation.model.Client;
import rba.card_creation.model.CreateCardResponse;
import rba.card_creation.model.NewCardRequest;
import rba.card_creation.utils.CardRequestClient;
import rba.card_creation.utils.CardStatus;

import java.util.List;

@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CardRequestClient clientClient;

    public CreditCardService(CreditCardRepository creditCardRepository, CardRequestClient clientClient) {
        this.creditCardRepository = creditCardRepository;
        this.clientClient = clientClient;
    }

    public CreateCardResponse createNewCard(NewCardRequest request) throws Exception {
        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setOIB(request.getOIB());
        client.setCardStatus(null);
        creditCardRepository.save(client);

        clientClient.sendNewCardRequest(request);

        return new CreateCardResponse(client.getOIB(), CardStatus.PENDING);
    }

    public void deleteCardRequest(String oib) {
        creditCardRepository.findByOIB(oib).ifPresent(client -> {
            if (client.getCardStatus() == CardStatus.PENDING) {
                creditCardRepository.deleteByOIB(oib);
            } else {
                throw new IllegalStateException("Cannot delete card request with status: " + client.getCardStatus());
            }
        });
    }

    public Client findClientByOIB(String oib) {
        return creditCardRepository.findByOIB(oib).orElse(null);

    }

    public List<Client> getAllClients() {
        return creditCardRepository.findAll();
    }
}
