package by.issoft.domain;

import by.issoft.domain.money.USD;

import static com.google.common.base.Preconditions.checkNotNull;

public class OrderItem {
    private final String name;
    private int number;
    private USD price;

    public OrderItem(String name, int number, USD price) {
        checkNotNull(name, "name should not be null");
        this.name = name;
        this.number = number;
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPrice(USD price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public USD getPrice() {
        return price;
    }
}
