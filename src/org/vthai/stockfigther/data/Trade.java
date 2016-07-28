package org.vthai.stockfighter.data;

import com.google.gson.JsonObject;

import org.vthai.stockfighter.data.Direction;
import org.vthai.stockfighter.data.OrderType;

public class Trade {
    private String account;

    private String venue;

    private String stock;

    private Trade (Builder builder) {
        this.account = builder.account;
        this.venue = builder.venue;
        this.stock = builder.stock;
    }

    public String getVenue() {
        return venue;
    }

    public String getAccount() {
        return account;
    }

    public String getStock() {
        return stock;
    }
 
    public JsonObject createTrade(int qty, Direction direction, int price, OrderType orderType) {
        JsonObject data = new JsonObject();
        data.addProperty("account", account);
        data.addProperty("venue", venue);
        data.addProperty("stock", stock);
        data.addProperty("qty", qty);
        data.addProperty("direction", direction.value());
        data.addProperty("price", price);
        data.addProperty("orderType", orderType.value());
        return data;
    }

    public static class Builder {
        private String account;

        private String venue;

        private String stock;

        public Builder account(String account) {
            this.account = account;
            return this;
        }

        public Builder venue(String venue) {
            this.venue = venue;
            return this;
        }

        public Builder stock(String stock) {
            this.stock = stock;
            return this;
        }

        public Trade build() {
            return new Trade(this);
        }
    }
}