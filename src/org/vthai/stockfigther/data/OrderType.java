package org.vthai.stockfighter.data;

public enum OrderType {
    LIMIT ("limit"),
    MARKET ("market"),
    FOK ("fill-or-kill"),
    IOC ("immediate-or-cancel");

    private String orderType;

    private OrderType(String orderType) {
        this.orderType = orderType;
    }

    public String value() {
        return orderType;
    }
}