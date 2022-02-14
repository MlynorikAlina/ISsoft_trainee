package by.issoft.persistence;

import by.issoft.domain.Order;
import by.issoft.domain.UserID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderStorage {
    public boolean persistOrder(Order order) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public Optional<Order> findOrderByOrderID(UUID id) {
        throw new UnsupportedOperationException("not implemented yet");
    }

    public List<Order> findOrdersByUserID(UserID id) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
