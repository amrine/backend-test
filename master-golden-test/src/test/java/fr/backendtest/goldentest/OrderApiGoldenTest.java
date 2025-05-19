package fr.backendtest.goldentest;

import fr.backendtest.goldentest.dto.OrderRequest;
import fr.backendtest.goldentest.entity.Customer;
import fr.backendtest.goldentest.entity.Item;
import fr.backendtest.goldentest.repository.CustomerRepository;
import fr.backendtest.goldentest.repository.ItemRepository;
import fr.backendtest.goldentest.service.OrderService;
import fr.backendtest.testutils.testcontainer.AbstractIntegrationTest;
import jakarta.inject.Inject;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.settings.Keys;
import org.instancio.settings.Settings;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderApiGoldenTest extends AbstractIntegrationTest {

    private static final Settings settings = Settings.defaults()
        .set(Keys.BEAN_VALIDATION_ENABLED, true)
        .set(Keys.STRING_NULLABLE, true)
        .set(Keys.INTEGER_NULLABLE, true)
        .set(Keys.DOUBLE_NULLABLE, true)
        .set(Keys.BOOLEAN_NULLABLE, true)
        .set(Keys.COLLECTION_MIN_SIZE, 0);

    private static final InstancioApi<Customer> customerGenerator =
        Instancio.of(Customer.class)
            .withSettings(settings)
            .ignore(field(Customer::getId))
            .supply(field(Customer::getName), random -> random.mixedCaseAlphabetic(8))
            .generate(field(Customer::getIsVIP), generators -> generators.oneOf(true, false));

    private static final InstancioApi<List<Item>> itemsGenerator =
        Instancio.ofList(Item.class)
            .size(3)
            .withSettings(settings)
            .ignore(field(Item::getId))
            .supply(field(Item::getName), random -> random.mixedCaseAlphabetic(10))
            .supply(field(Item::getPrice), random -> random.doubleRange(0.0, 1000.0));

    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private ItemRepository itemRepository;
    @Inject
    private OrderService orderService;

    private static Stream<Integer> generateIndiceGoldenTest() {
        return IntStream.rangeClosed(1, 20).boxed();
    }

    @ParameterizedTest
    @MethodSource("generateIndiceGoldenTest")
    void goldenTest_cas_passant(Integer indiceGoldenTest) throws Exception {
        // GIVEN
        Customer customer = customerRepository.save(customerGenerator.withSeed(indiceGoldenTest).create());
        List<Item> items = itemRepository.saveAll(itemsGenerator.withSeed(indiceGoldenTest).create());
        List<Long> itemIds = items.stream().map(Item::getId).toList();
        OrderRequest request = new OrderRequest(customer.getId(), itemIds);
        Path expectedPath = Path.of("src/test/resources/goldenmaster/order_%02d.json".formatted(indiceGoldenTest));

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andReturn();
        String actualJson = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        if (!Files.exists(expectedPath)) {
            Files.writeString(expectedPath, actualJson);
        } else {
            String expectedJson = Files.readString(expectedPath);
            JSONAssert.assertEquals(expectedJson, actualJson, true);
        }
    }

    @Test
    void goldenTest_unknownCustomer() throws Exception {
        // GIVEN
        long seed = 999L; // seed arbitraire
        List<Item> items = itemRepository.saveAll(itemsGenerator.withSeed(seed).create());
        // Client ID inexistant volontairement
        long unknownCustomerId = 1111L;
        List<Long> itemIds = items.stream().map(Item::getId).toList();
        OrderRequest request = new OrderRequest(unknownCustomerId, itemIds);
        Path expectedPath = Path.of("src/test/resources/goldenmaster/order_unknown_customer.json");

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isNotFound())
            .andReturn();
        String actualJson = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        if (!Files.exists(expectedPath)) {
            Files.writeString(expectedPath, actualJson);
        } else {
            String expectedJson = Files.readString(expectedPath);
            JSONAssert.assertEquals(expectedJson, actualJson,
                new CustomComparator(JSONCompareMode.STRICT,
                    new Customization("timestamp", (o1, o2) -> true) // ignore le champ
                )
            );
        }
    }

    @Test
    void goldenTest_missingItems() throws Exception {
        // GIVEN
        long seed = 999L; // seed arbitraire
        Customer customer = customerRepository.save(customerGenerator.withSeed(seed).create());
        List<Long> missingItemIds = List.of(1000L, 1001L, 1002L);
        OrderRequest request = new OrderRequest(customer.getId(), missingItemIds);
        Path expectedPath = Path.of("src/test/resources/goldenmaster/order_missing_items.json");

        // WHEN & THEN
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andReturn();
        String actualJson = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        if (!Files.exists(expectedPath)) {
            Files.writeString(expectedPath, actualJson);
        } else {
            String expectedJson = Files.readString(expectedPath);
            JSONAssert.assertEquals(expectedJson, actualJson,
                new CustomComparator(JSONCompareMode.STRICT,
                    new Customization("timestamp", (o1, o2) -> true) // ignore le champ
                )
            );
        }
    }

}
