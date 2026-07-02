package rba.card_creation;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rba.card_creation.model.Client;
import rba.card_creation.model.CreateCardResponse;
import rba.card_creation.model.NewCardRequest;

@RestController
@RequestMapping("api/v1/card-request")
public class CreditCardController {

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping()
    public CreateCardResponse createCard(NewCardRequest request) throws Exception {
        return creditCardService.createNewCard(request);
    }

    @GetMapping("/find")
    public Client findClientByOIB(String OIB) {
        return creditCardService.findClientByOIB(OIB);
    }

    @DeleteMapping()
    public void deleteCardRequest(String OIB) {
        creditCardService.deleteCardRequest(OIB);
    }
}
