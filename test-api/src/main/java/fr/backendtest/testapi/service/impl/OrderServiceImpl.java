package fr.backendtest.testapi.service.impl;

import fr.backendtest.testapi.dto.OrderRequest;
import fr.backendtest.testapi.dto.OrderResponse;
import fr.backendtest.testapi.entity.Customer;
import fr.backendtest.testapi.entity.Item;
import fr.backendtest.testapi.entity.Order;
import fr.backendtest.testapi.exception.CustomerNotFoundException;
import fr.backendtest.testapi.exception.ItemNotFoundException;
import fr.backendtest.testapi.mapper.OrderMapper;
import fr.backendtest.testapi.repository.CustomerRepository;
import fr.backendtest.testapi.repository.ItemRepository;
import fr.backendtest.testapi.repository.OrderRepository;
import fr.backendtest.testapi.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(CustomerRepository customerRepository,
                            OrderRepository orderRepository,
                            ItemRepository itemRepository,
                            OrderMapper orderMapper) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.orderMapper = orderMapper;
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

        return orderMapper.toDto(orderRepository.save(order));
    }
}
