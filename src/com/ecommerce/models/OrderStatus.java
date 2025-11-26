package com.ecommerce.models;

public enum OrderStatus {
    NEW("Newly Created"),
    PAID("Paid by Customer"),
    SHIPPED("Shipped by manufacturer"),
    CANCELLED("Cancelled By Customer");

    private final String displayName;
    OrderStatus(String displayName) {
        this.displayName = this.name();
    }

    public String getDisplayName() {
        return displayName;
    }
}
