package fr.backendtest.testapi.controller;

import fr.backendtest.testapi.dto.ApiError;
import fr.backendtest.testapi.dto.OrderRequest;
import fr.backendtest.testapi.dto.OrderResponse;
import fr.backendtest.testutils.testcontainer.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {
    "classpath:sql/clean_db.sql",
    "classpath:sql/customer.sql",
    "classpath:sql/item.sql"
})
public class OrderControllerIT extends AbstractIntegrationTest {

    @Test
    void shouldPlaceOrderWithDiscount() throws Exception {
        // GIVEN
        OrderRequest request = new OrderRequest(1L, List.of(101L, 102L));

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();

        OrderResponse orderResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderResponse.class);

        assertEquals(1L, orderResponse.id());
        assertEquals(request.customerId(), orderResponse.customerId());
        assertEquals(request.itemIds(), orderResponse.itemIds());
        assertEquals(945.0, orderResponse.total()); // 10% discount
    }

    @Test
    void shouldPlaceOrderWithoutDiscount() throws Exception {
        // GIVEN
        OrderRequest request = new OrderRequest(2L, List.of(101L, 102L));

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();

        OrderResponse orderResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), OrderResponse.class);

        assertEquals(1L, orderResponse.id());
        assertEquals(request.customerId(), orderResponse.customerId());
        assertEquals(request.itemIds(), orderResponse.itemIds());
        assertEquals(1050, orderResponse.total());
    }

    @Test
    void shouldThrowExceptionWhenItemsAreMissing() throws Exception {
        // GIVEN
        OrderRequest request = new OrderRequest(1L, List.of(101L, 202L));

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn();

        ApiError apiError = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ApiError.class);

        assertEquals(BAD_REQUEST.value(), apiError.status());
        assertEquals(BAD_REQUEST.getReasonPhrase(), apiError.error());
        assertTrue(apiError.message().contains("Missing item(s): [202]"));
        assertEquals(
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
            apiError.timestamp().truncatedTo(ChronoUnit.MINUTES)
        );
    }

    @Test
    void shouldThrowCustomerNotFoundException() throws Exception {
        // GIVEN
        OrderRequest request = new OrderRequest(999L, List.of(101L));

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound())
            .andReturn();

        ApiError apiError = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ApiError.class);

        assertEquals(NOT_FOUND.value(), apiError.status());
        assertEquals(NOT_FOUND.getReasonPhrase(), apiError.error());
        assertTrue(apiError.message().contains("Customer not found"));
        assertEquals(
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
            apiError.timestamp().truncatedTo(ChronoUnit.MINUTES)
        );
    }
}
