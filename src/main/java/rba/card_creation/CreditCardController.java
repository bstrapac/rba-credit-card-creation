package rba.card_creation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rba.card_creation.exception.CardApiException;
import rba.card_creation.model.Client;
import rba.card_creation.model.CreateCardResponse;
import rba.card_creation.model.ErrorResponse;
import rba.card_creation.model.NewCardRequest;

import java.util.UUID;

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

    @GetMapping()
    public Client findClientByOIB(String OIB) {
        return creditCardService.findClientByOIB(OIB);
    }

    @DeleteMapping()
    public void deleteCardRequest(String OIB) {
        creditCardService.deleteCardRequest(OIB);
    }

    @ExceptionHandler(CardApiException.class)
    public ResponseEntity<ErrorResponse> handleCardApiException(CardApiException ex) {
        ErrorResponse error = new ErrorResponse(
                String.valueOf(ex.getStatusCode()),
                UUID.randomUUID().toString(),
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.valueOf(ex.getStatusCode()));
    }
}
