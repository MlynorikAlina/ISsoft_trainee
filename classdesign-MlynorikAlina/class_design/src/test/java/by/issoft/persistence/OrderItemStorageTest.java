package by.issoft.persistence;

import by.issoft.data.OrderTestSamples;
import by.issoft.domain.OrderItem;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemStorageTest {
    private OrderItemStorage orderItemStorage = new OrderItemStorage();

    @org.junit.jupiter.api.Test
    void persistOrderItem() {
        final String itemName = "book";
        OrderItem someItem = OrderTestSamples.anyOrderItem(itemName);

        final boolean persistResult = orderItemStorage.persistOrderItem(someItem);
        assertThat(persistResult, is(true));

        final boolean storageContainsItem = orderItemStorage.containsOrderItemWithName(itemName);
        assertThat(storageContainsItem, is(true));
    }

    @org.junit.jupiter.api.Test
    void persistOrderItem_null() {
        assertThrows(NullPointerException.class, () -> orderItemStorage.persistOrderItem(null));
    }
}