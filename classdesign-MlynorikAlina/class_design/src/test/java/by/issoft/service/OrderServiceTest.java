package by.issoft.service;

import by.issoft.data.OrderTestSamples;
import by.issoft.domain.Order;
import by.issoft.persistence.*;
import by.issoft.service.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    private OrderService orderService;
    @Mock
    private OrderStorage orderStorage;
    @Mock
    private OrderItemStorage orderItemStorage;

    @BeforeEach
    public void before() {
        MockitoAnnotations.initMocks(this);

        /*orderItemStorage = mock(OrderItemStorage.class);
        orderStorage = mock(OrderStorage.class);*/

        orderService = new OrderService(orderStorage, orderItemStorage);
    }
    void orderItemStorageContainsOrderItem(){
        when(orderItemStorage.containsOrderItemWithName(any())).thenReturn(true);
    }
    void makeReturnOrderFromOrderStorage_findOrderByOrderID(Optional<Order> toReturn){
        when(orderStorage.findOrderByOrderID(any())).thenReturn(toReturn);
    }

    @Test
    void placeOrder_invalidItemPersist() {
        final String userId = "vasya";
        Order someOrder = OrderTestSamples.anyOrder(userId);

        when(orderItemStorage.persistOrderItem(any())).thenReturn(false);

        assertThrows(OrderItemPersistException.class, () -> orderService.placeOrder(someOrder));
    }

    @Test
    void placeOrder_invalidAddress() {
        final String userId = "vasya";
        Order someOrder = OrderTestSamples.anyOrder(userId);
        someOrder.setAddress("");

        assertThrows(InvalidOrderAddressException.class, () -> orderService.placeOrder(someOrder));
    }

    @Test
    void placeOrder_duplicateOrderPlacing() {
        final String userId = "vasya";
        Order someOrder = OrderTestSamples.anyOrder(userId);

        orderItemStorageContainsOrderItem();
        makeReturnOrderFromOrderStorage_findOrderByOrderID(Optional.of(someOrder));

        assertThrows(DuplicateOrderPlacingException.class, () -> orderService.placeOrder(someOrder));
    }

    @Test
    void placeOrder_success() {
        final String userId = "vasya";
        Order someOrder = OrderTestSamples.anyOrder(userId);

        orderItemStorageContainsOrderItem();
        makeReturnOrderFromOrderStorage_findOrderByOrderID(Optional.empty());

        orderService.placeOrder(someOrder);

        verify(orderStorage).persistOrder(someOrder);
    }
}