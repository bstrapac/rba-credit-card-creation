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
class RequestControllerTest {

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
        mockMvc.perform(get("/api/v1/card-request")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "Ana",
                                  "lastName": "Horvat",
                                  "oib": "12345678901"
                                }
                                """))
                .andExpect(status().isOk());

        assertThat(creditCardRepository.findByOib("12345678901"))
                .hasValueSatisfying(client -> {
                    assertThat(client.getFirstName()).isEqualTo("Ana");
                    assertThat(client.getLastName()).isEqualTo("Horvat");
                    assertThat(client.getCardStatus()).isEqualTo(CardStatus.PENDING);
                });

        verify(cardRequestClient).sendNewCardRequest(argThat(request ->
                "Ana".equals(request.getFirstName())
                        && "Horvat".equals(request.getLastName())
                        && "12345678901".equals(request.getOib())
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
}
