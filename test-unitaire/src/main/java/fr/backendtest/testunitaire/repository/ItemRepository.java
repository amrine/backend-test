package fr.backendtest.testunitaire.repository;

import fr.backendtest.testunitaire.entity.Item;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemRepository {

    private List<Item> items = List.of(
        new Item(1L, "Laptop", 1000.00),
        new Item(2L, "Mouse", 50.00),
        new Item(3L, "Keyboard", 80.00),
        new Item(4L, "Book", 30.00)
    );

    public List<Item> findAllById(List<Long> itemIds) {
        return items.stream()
                .filter(item -> itemIds.contains(item.getId()))
                .toList();
    }
}
