package rba.card_creation;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rba.card_creation.client.CardRequestClient;
import rba.card_creation.model.CardStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Disabled
class RequestIntegrationTest {

    @Mock
    private MockMvc mockMvc;

    @Mock
    private CreditCardRepository creditCardRepository;

    @Mock
    private CardRequestClient cardRequestClient;

    @AfterEach
    void cleanUp() {
        creditCardRepository.deleteAll();
        reset(cardRequestClient);
    }

    @Test
    void createsCardRequestAndMocksExternalCardApi() throws Exception {
        /*
        * Http client is using an imaginary endpoint:
        * https://stackoverflow.com/questions/20542361/mocking-apache-httpclient-using-mockito
        * https://stackoverflow.com/questions/43900186/how-to-mock-a-http-response
        * https://stackoverflow.com/questions/20542361/mocking-apache-httpclient-using-mockito
        * https://www.mock-server.com/
        * */
        mockMvc.perform(get("/api/v1/card-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk()); /* Status is null -> api.something is imaginary */

        assertThat(creditCardRepository.findByOib(oib))
                .hasValueSatisfying(client -> {
                    assertThat(client.getFirstName()).isEqualTo(firstName);
                    assertThat(client.getLastName()).isEqualTo(lastName);
                    assertThat(client.getCardStatus()).isEqualTo(CardStatus.PENDING);
                });

        verify(cardRequestClient).sendNewCardRequest(argThat(request ->
                firstName.equals(request.getFirstName())
                        && lastName.equals(request.getLastName())
                        && oib.equals(request.getOib())
        ));
    }

    @TestConfiguration
    static class CardRequestClientMockConfiguration {

        @Bean
        @Primary
        CardRequestClient cardRequestClient() {
            return mock(CardRequestClient.class);
        }
    }

    String content = """
                        {
                          "firstName": "Ana",
                          "lastName": "Horvat",
                          "oib": "12345678901"
                    }""";
    String firstName = "Ana";
    String lastName = "Horvat";
    String oib = "12345678901";
}
