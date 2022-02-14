package by.issoft.domain;

public enum OrderStatus {
    PENDING, AWAITING_PAYMENT, AWAITING_SHIPMENT,
    PARTIALLY_SHIPPED, SHIPPED, COMPLETED, CANCELLED;
}