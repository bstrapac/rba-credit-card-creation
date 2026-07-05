package rba.card_creation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rba.card_creation.client.CardRequestClient;
import rba.card_creation.model.CardStatus;
import rba.card_creation.model.Client;
import rba.card_creation.model.NewCardRequest;
import rba.card_creation.model.RequestResponse;
import rba.card_creation.utils.Validation;

import java.util.List;

@Service
@Slf4j
public class RequestService {

    private final CreditCardRepository creditCardRepository;
    private final CardRequestClient cardRequestClient;
    private final Validation validation;

    public RequestService(CreditCardRepository creditCardRepository, CardRequestClient cardRequestClient, Validation validation) {
        this.creditCardRepository = creditCardRepository;
        this.cardRequestClient = cardRequestClient;
        this.validation = validation;
    }

    public RequestResponse createNewRequest(NewCardRequest request) throws Exception {
        validation.validateRequest(request);

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setOib(request.getOib());
        client.setCardStatus(CardStatus.PENDING);
        creditCardRepository.save(client);

        cardRequestClient.sendNewCardRequest(request);

        log.info("New card request created for client: {}", request.getOib());
        return new RequestResponse();
    }

    public void deleteRequest(String oib) {
        validation.validateOib(oib);
        creditCardRepository.findByOib(oib).ifPresent(client -> {
            if (client.getCardStatus() == CardStatus.PENDING) {
                creditCardRepository.deleteByOib(oib);
                log.info("Card request deleted for client: {}", oib);
            } else {
                throw new IllegalStateException("Cannot delete card request with status: " + client.getCardStatus());
            }
        });
    }

    public Client findRequestByOib(String oib) {
        validation.validateOib(oib);
        return creditCardRepository.findByOib(oib).orElse(null);
    }

    public List<Client> getAllRequests() {
        return creditCardRepository.findAll();
    }
}
