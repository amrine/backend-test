package fr.backendtest.testunitaire.repository;

import fr.backendtest.testunitaire.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerRepository {

    private List<Customer> customers = List.of(
            new Customer(1L, "Alice", true),
            new Customer(2L, "Bob", false)
    );

    public Optional<Customer> findById(Long customerId) {
        return customers.stream()
                .filter(c -> c.getId().equals(customerId))
                .findFirst();
    }
}
