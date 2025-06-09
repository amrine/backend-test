package fr.backendtest.testapi.mapper;

import fr.backendtest.testapi.dto.OrderResponse;
import fr.backendtest.testapi.entity.Item;
import fr.backendtest.testapi.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customerId", source = "customer.id")
    @Mapping(target = "itemIds",    expression = "java( itemIds(order.getItems()) )")
    OrderResponse toDto(Order order);

    default List<Long> itemIds(List<Item> items) {
        return items.stream().map(Item::getId).toList();
    }
}
