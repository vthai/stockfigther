package org.vthai.stockfighter.data;

public enum Direction {
    BUY ("buy"),
    SELL ("sell");

    private String direction;

    private Direction(String direction) {
        this.direction = direction;
    }

    public String value() {
        return direction;
    }
}