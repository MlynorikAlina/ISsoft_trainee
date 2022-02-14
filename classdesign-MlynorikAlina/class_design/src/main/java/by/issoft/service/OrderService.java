package by.issoft.service;

import by.issoft.domain.Order;
import by.issoft.domain.OrderStatus;
import by.issoft.domain.UserID;
import by.issoft.persistence.OrderItemStorage;
import by.issoft.persistence.OrderStorage;
import by.issoft.service.exceptions.DuplicateOrderPlacingException;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public class OrderService {
    private final OrderStorage orderStorage;
    private final OrderItemStorage orderItemStorage;
    private final OrderValidator orderValidator;

    public OrderService(OrderStorage orderStorage, OrderItemStorage orderItemStorage) {
        this.orderStorage = orderStorage;
        this.orderItemStorage = orderItemStorage;
        this.orderValidator = new OrderValidator(orderItemStorage);
    }

    public void placeOrder(Order order) {
        order.getItems().forEach(orderItemStorage::persistOrderItem);
        orderValidator.validateForPlacing(order);

        Optional<Order> foundById = orderStorage.findOrderByOrderID(order.getId());
        if (foundById.isPresent()) {
            throw new DuplicateOrderPlacingException("order with such id was already placed");
        }

        order.setStatus(OrderStatus.PENDING);
        orderStorage.persistOrder(order);
    }

    public List<Order> loadAllByUserId(UserID userId) {
        List<Order> ordersByUserId = orderStorage.findOrdersByUserID(userId);
        checkState(!ordersByUserId.isEmpty(), "no orders with such userId");
        return ordersByUserId;
    }
}
