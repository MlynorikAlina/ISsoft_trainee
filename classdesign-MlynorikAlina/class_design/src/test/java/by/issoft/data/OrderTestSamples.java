package by.issoft.data;

import by.issoft.domain.Order;
import by.issoft.domain.OrderItem;
import by.issoft.domain.UserID;
import by.issoft.domain.money.USD;

public class OrderTestSamples {
    public static Order anyOrder(String userId) {
        return new Order(new UserID(userId), "0110", new OrderItem[]{anyOrderItem("book"), anyOrderItem("pen")});
    }

    public static OrderItem anyOrderItem(String name) {
        return new OrderItem(name, 0, USD.of(15));
    }

}
