package fr.backendtest.goldentest.mapper;

import fr.backendtest.goldentest.dto.OrderResponse;
import fr.backendtest.goldentest.entity.Item;
import fr.backendtest.goldentest.entity.Order;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "itemIds",    expression = "java( itemIds(order.getItems()) )")
    OrderResponse toDto(Order order);

    default List<Long> itemIds(List<Item> items) {
        return items.stream().map(Item::getId).toList();
    }
}
