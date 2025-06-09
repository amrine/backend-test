package fr.backendtest.testapi.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.backendtest.testapi.dto.ApiError;
import fr.backendtest.testapi.dto.OrderRequest;
import fr.backendtest.testapi.dto.OrderResponse;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderSteps {

    @LocalServerPort
    private int port;

    private final RestTemplate restTemplate = new RestTemplate();

    @Inject
    private ObjectMapper objectMapper;

    private OrderRequest orderRequest;
    private ResponseEntity<?> response;

    @Given("un client avec l'id {int} et les articles [{int}, {int}]")
    public void un_client_avec_l_id_et_les_articles(Integer customerId, Integer itemId1, Integer itemId2) {
        this.orderRequest = new OrderRequest(Long.valueOf(customerId), List.of(Long.valueOf(itemId1), Long.valueOf(itemId2)));
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public boolean hasError(@NotNull ClientHttpResponse response) {
                return false; // Ne jamais throw d’exception HTTP
            }
        });
    }

    @When("je passe une commande via l'API")
    public void jePasseCommande() {
        response =
            restTemplate.postForEntity(
              "http://localhost:" + port + "/api/v1/orders", orderRequest, Object.class);
    }

    @Then("la réponse doit avoir un statut {int}")
    public void verifierStatut(int expectedStatus) {
        assertEquals(expectedStatus, response.getStatusCode().value());
    }

    @And("le total doit être égal à {double}")
    public void verifierTotal(Double expectedTotal) {
        OrderResponse orderResponse = objectMapper.convertValue(response.getBody(), OrderResponse.class);
        assertEquals(expectedTotal, orderResponse.total());
        assertEquals(1L, orderResponse.id());
        assertEquals(orderRequest.customerId(), orderResponse.customerId());
        assertEquals(orderRequest.itemIds(), orderResponse.itemIds());
    }

    @And("l'erreur {string} et le message d'erreur est : {string}")
    public void verifierErreur(String expectedErreur, String expectedMessage) {
        ApiError apiError = objectMapper.convertValue(response.getBody(), ApiError.class);

        assertEquals(expectedErreur, apiError.error());
        assertEquals(expectedMessage, apiError.message());
        assertEquals("/api/v1/orders", apiError.path());
        assertEquals(
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
            apiError.timestamp().truncatedTo(ChronoUnit.MINUTES)
        );
    }
}
