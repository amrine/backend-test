package fr.backendtest.testintegration.service.impl;

import fr.backendtest.testintegration.dto.OrderRequest;
import fr.backendtest.testintegration.dto.OrderResponse;
import fr.backendtest.testintegration.entity.Customer;
import fr.backendtest.testintegration.entity.Item;
import fr.backendtest.testintegration.entity.Order;
import fr.backendtest.testintegration.exception.CustomerNotFoundException;
import fr.backendtest.testintegration.exception.ItemNotFoundException;
import fr.backendtest.testintegration.repository.CustomerRepository;
import fr.backendtest.testintegration.repository.ItemRepository;
import fr.backendtest.testintegration.repository.OrderRepository;
import fr.backendtest.testintegration.service.OrderService;
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
        if (Boolean.TRUE.equals(customer.getIsVIP())) {
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
