package rba.card_creation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rba.card_creation.client.CardRequestClient;
import rba.card_creation.model.CardStatus;
import rba.card_creation.model.Client;
import rba.card_creation.model.NewCardRequest;
import rba.card_creation.model.RequestResponse;
import rba.card_creation.utils.Validation;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceTest {

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private CardRequestClient cardRequestClient;

    @Mock
    private Validation validation;

    @InjectMocks
    private RequestService requestService;

    @Test
    void createNewRequestSavesPendingClientAndCallsCardRequestClient() throws Exception {
        NewCardRequest request = newRequest("Ana", "Horvat", "12345678901");
        ArgumentCaptor<Client> clientCaptor = ArgumentCaptor.forClass(Client.class);

        RequestResponse response = requestService.createNewRequest(request);

        assertThat(response).isNotNull();
        verify(validation).validateRequest(request);
        verify(creditCardRepository).save(clientCaptor.capture());
        verify(cardRequestClient).sendNewCardRequest(request);

        Client savedClient = clientCaptor.getValue();
        assertThat(savedClient.getFirstName()).isEqualTo("Ana");
        assertThat(savedClient.getLastName()).isEqualTo("Horvat");
        assertThat(savedClient.getOib()).isEqualTo("12345678901");
        assertThat(savedClient.getCardStatus()).isEqualTo(CardStatus.PENDING);
    }

    @Test
    void createNewRequestDoesNotPersistOrCallClientWhenValidationFails() {
        NewCardRequest request = newRequest("Ana", "Horvat", "12345678901");
        doThrow(new IllegalArgumentException("OIB must be 11 characters long"))
                .when(validation)
                .validateRequest(request);

        assertThatThrownBy(() -> requestService.createNewRequest(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("OIB must be 11 characters long");

        verifyNoInteractions(creditCardRepository, cardRequestClient);
    }

    @Test
    void deleteRequestDeletesPendingClient() {
        String oib = "12345678901";
        Client client = client(oib, CardStatus.PENDING);
        when(creditCardRepository.findByOib(oib)).thenReturn(Optional.of(client));

        requestService.deleteRequest(oib);

        verify(validation).validateOib(oib);
        verify(creditCardRepository).findByOib(oib);
        verify(creditCardRepository).deleteByOib(oib);
    }

    @Test
    void deleteRequestRejectsNonPendingClient() {
        String oib = "12345678901";
        Client client = client(oib, CardStatus.APPROVED);
        when(creditCardRepository.findByOib(oib)).thenReturn(Optional.of(client));

        assertThatThrownBy(() -> requestService.deleteRequest(oib))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Cannot delete card request with status: APPROVED");

        verify(validation).validateOib(oib);
        verify(creditCardRepository).findByOib(oib);
        verifyNoMoreInteractions(creditCardRepository);
    }

    private static NewCardRequest newRequest(String firstName, String lastName, String oib) {
        NewCardRequest request = new NewCardRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setOib(oib);
        return request;
    }

    private static Client client(String oib, CardStatus cardStatus) {
        Client client = new Client();
        client.setFirstName("Ana");
        client.setLastName("Horvat");
        client.setOib(oib);
        client.setCardStatus(cardStatus);
        return client;
    }
}
