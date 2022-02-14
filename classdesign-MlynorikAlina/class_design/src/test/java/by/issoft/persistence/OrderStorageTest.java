package by.issoft.persistence;

import by.issoft.data.OrderTestSamples;
import by.issoft.domain.Order;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderStorageTest {

    private OrderStorage orderStorage = new OrderStorage();

    @org.junit.jupiter.api.Test
    void persistOrder() {
        final String userId = "vasya";
        Order someOrder = OrderTestSamples.anyOrder(userId);

        final boolean placeResult = orderStorage.persistOrder(someOrder);
        assertThat(placeResult, is(true));

        Optional<Order> returnedOrder = orderStorage.findOrderByOrderID(someOrder.getId());
        assertThat(returnedOrder.isPresent(), is(true));
        assertEquals(someOrder, returnedOrder.get());
    }

    @org.junit.jupiter.api.Test
    void persistOrder_null() {
        assertThrows(NullPointerException.class, () -> orderStorage.persistOrder(null));
    }
}