package by.issoft.service;

import by.issoft.domain.Order;
import by.issoft.persistence.OrderItemStorage;
import by.issoft.service.exceptions.InvalidOrderAddressException;
import by.issoft.service.exceptions.OrderItemPersistException;

public class OrderValidator {
    private final OrderItemStorage orderItemStorage;

    public OrderValidator(OrderItemStorage orderItemStorage) {
        this.orderItemStorage = orderItemStorage;
    }

    public void validateForPlacing(Order order) {
        if (order.getId() == null) {
            throw new NullPointerException("orderId should not be null");
        }
        if (order.getDate() == null) {
            throw new NullPointerException("date should not be null");
        }
        if (order.getAddress() == null || "".equals(order.getAddress())) {
            throw new InvalidOrderAddressException("adress should be not empty");
        }
        order.getItems().forEach((orderItem) -> {
            if (!orderItemStorage.containsOrderItemWithName(orderItem.getName())) {
                throw new OrderItemPersistException("item with name " + orderItem.getName() + " was not found");
            }
        });
    }
}
