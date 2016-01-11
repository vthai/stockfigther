package org.vthai.stockfighter.api;

import com.google.gson.JsonObject;

public class RestApi {
    private final String CMD_API_STATUS = "/heartbeat";

    private final String CMD_VENUE_STATUS = "/venues/:venue/heartbeat";

    private final String CMD_STOCK_QUOTE = "/venues/:venue/stocks/:stock/quote";

    private final String CMD_STOCK_ORDER = "/venues/:venue/stocks/:stock/orders";

    private final String PARAM_VENUE = ":venue";

    private final String PARAM_STOCK = ":stock";

    private HttpApi http = new HttpApi();

    public JsonObject apiStatus() {
        return http.request(HttpApi.GET, CMD_API_STATUS);
    }

    public JsonObject venueStatus(String venue) {
        String command = CMD_VENUE_STATUS.replace(PARAM_VENUE, venue);

        return http.request(HttpApi.GET, command);
    }

    public JsonObject quote(String venue, String stock) {
        String command = CMD_STOCK_QUOTE.replace(PARAM_VENUE, venue)
            .replace(PARAM_STOCK, stock);

        return http.request(HttpApi.GET, command);

    }

    public JsonObject order(String venue, String stock, JsonObject data) {
        String command = CMD_STOCK_ORDER.replace(PARAM_VENUE, venue)
            .replace(PARAM_STOCK, stock);

        return http.request(HttpApi.POST, command, data);
    }

}