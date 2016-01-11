package org.vthai.stockfighter.games;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import java.util.Random;

import org.vthai.stockfighter.api.RestApi;

public class BlockBuy {
    private String account = "HHH45446325";
    private String venue = "WRGBEX";
    private String stock = "AEL";

    private final int ONE_DAY = 5000; // 5 secs = 1 day

    private final int ONE_HOUR = 208;

    private final int HALF_HOUR = 104;

    private final int QUARTER_HOUR = 52;

    private final int H_QUARTER_HOUR = 26;

    RestApi api = new RestApi();

    private String[] direction = new String[] {"sell", "buy"};

    public static void main(String[] args) {
        BlockBuy task = new BlockBuy();
        task.run();
    }

    public void run() {
        if(checkApiStatus() && checkVenueStatus()) {
            buySneaky();
            //sellSneaky(2000);
        }
    }

    public boolean checkApiStatus() {
        JsonObject json = api.apiStatus();
        return json.get("ok").getAsBoolean();
    }

    public boolean checkVenueStatus() {
        JsonObject json = api.venueStatus(venue);
        return json.get("ok").getAsBoolean();
    }

    public void buySneaky() {
        Random random = new Random(System.currentTimeMillis());

        int goal = 0;
        while (goal != 100000) {
            JsonObject quote = api.quote(venue, stock);
            JsonElement bid = quote.get("bid");
            int gain = 0;

            if (bid != null) {
                int buy = bid.getAsInt();
                buy = buy + random.nextInt(20) + 11;
                
                int qty = random.nextInt(1000) + 60;

                JsonObject data = new JsonObject();
                data.addProperty("account", account);
                data.addProperty("venue", venue);
                data.addProperty("stock", stock);
                data.addProperty("qty", qty);
                data.addProperty("direction", direction[1]);
                data.addProperty("price", buy);
                data.addProperty("orderType", "immediate-or-cancel");

                JsonObject response = api.order(venue, stock, data);
                gain = response.get("totalFilled").getAsInt();
                goal = goal + gain;
            }

            int chance = random.nextInt(5);
            if (chance == 3 && goal != 0) {
                sellSneaky(random.nextInt(200) + 50, random);
            }

            System.out.println(goal + " - " + gain);
            int wait = random.nextInt(QUARTER_HOUR) + H_QUARTER_HOUR;
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            
        }
    }

    public void sellSneaky(int qty, Random random) {
        JsonObject quote = api.quote(venue, stock);
        JsonElement ask = quote.get("ask");
        if (ask != null) {
            int sell = ask.getAsInt();
            sell = sell - (random.nextInt(100) + 75);

            JsonObject data = new JsonObject();
            data.addProperty("account", account);
            data.addProperty("venue", venue);
            data.addProperty("stock", stock);
            data.addProperty("qty", qty);
            data.addProperty("direction", direction[0]);
            data.addProperty("price", sell);
            data.addProperty("orderType", "immediate-or-cancel");

            JsonObject response = api.order(venue, stock, data);
            int sold = response.get("totalFilled").getAsInt();
            System.out.println("Selled " + sold);
        }
    }
}