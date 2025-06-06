package fr.backendtest.testunitaire.service.impl;

import fr.backendtest.testunitaire.dto.OrderRequest;
import fr.backendtest.testunitaire.dto.OrderResponse;
import fr.backendtest.testunitaire.entity.Customer;
import fr.backendtest.testunitaire.entity.Item;
import fr.backendtest.testunitaire.entity.Order;
import fr.backendtest.testunitaire.exception.CustomerNotFoundException;
import fr.backendtest.testunitaire.exception.ItemNotFoundException;
import fr.backendtest.testunitaire.repository.CustomerRepository;
import fr.backendtest.testunitaire.repository.ItemRepository;
import fr.backendtest.testunitaire.repository.OrderRepository;
import fr.backendtest.testunitaire.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    public OrderServiceImpl(CustomerRepository customerRepository,
                            OrderRepository orderRepository,
                            ItemRepository itemRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        // 1. Récupérer le client
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        // 2. Récupérer les items
        List<Item> items = itemRepository.findAllById(request.itemIds());

        // 3. Vérifier que tous les items existent
        if (items.size() != request.itemIds().size()) {
            List<Long> foundIds = items.stream().map(Item::getId).toList();
            List<Long> missing = request.itemIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw new ItemNotFoundException("Missing item(s): " + missing);
        }

        // 4. Calcul du total
        double total = items.stream().mapToDouble(Item::getPrice).sum();
        if (customer.isVIP()) {
            total *= 0.9; // réduction VIP
        }

        // 5. Création de l'order
        Order order = new Order();
        order.setCustomer(customer);
        order.setItems(items);
        order.setTotal(total);

        Order savedOrder = orderRepository.save(order);
        return new OrderResponse(savedOrder.getId());
    }
}
