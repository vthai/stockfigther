package org.vthai.stockfighter.games;

import com.google.gson.JsonObject;

import org.vthai.stockfighter.data.Trade;
import org.vthai.stockfighter.api.RestApi;

public abstract class StockTradeTemplate {
    protected Trade trade;

    protected RestApi api;

    public StockTradeTemplate() {
        api = new RestApi();
    }

    public void execute(String account, String venue, String stock) {
        trade = new Trade.Builder()
            .account(account)
            .venue(venue)
            .stock(stock)
            .build();

        if(checkApiStatus() && checkVenueStatus()) {
            doTask();
        }
    }

    public boolean checkApiStatus() {
        JsonObject json = api.apiStatus();
        return json.get("ok").getAsBoolean();
    }

    public boolean checkVenueStatus() {
        JsonObject json = api.venueStatus(trade.getVenue());
        return json.get("ok").getAsBoolean();
    }

    public abstract void doTask();
}