package by.issoft.domain;

import by.issoft.domain.money.USD;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Order {
    private final UUID id;
    private UserID userId;
    private OrderStatus status;
    private final LocalDate date;
    private String address;
    private List<OrderItem> items;

    public Order(UserID userId, String address, OrderItem[] items) {
        this.userId = userId;
        this.address = address;
        this.items = Arrays.asList(items);
        this.id = UUID.randomUUID();
        this.status = OrderStatus.PENDING;
        this.date = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public UserID getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setItems(OrderItem[] items) {
        this.items = Arrays.asList(items);
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public void addItems(OrderItem... items) {
        this.items.addAll(Arrays.asList(items));
    }

    public void addItems(List<OrderItem> items) {
        this.items.addAll(items);
    }

    private USD summaryPrice() {
        USD summaryPrice = USD.of(0);
        items.forEach((orderItem) -> summaryPrice.add(orderItem.getPrice()));
        return summaryPrice;
    }
}
