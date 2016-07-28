package org.vthai.stockfighter.games;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import java.util.Random;

import org.vthai.stockfighter.api.RestApi;
import org.vthai.stockfighter.data.Trade;
import org.vthai.stockfighter.data.Direction;
import org.vthai.stockfighter.data.OrderType;

/**
Stock figther level 1
*/
public class ChockABlock extends StockTradeTemplate {

    public static void main(String[] args) {
        ChockABlock task = new ChockABlock();
        task.execute("AAS17532253", "YPZEX", "IVN");
    }

    @Override
    public void doTask() {
        Random random = new Random(System.currentTimeMillis());

        int goal = 0;
        while (goal != 100000) {
            JsonObject quote = api.quote(trade.getVenue(), trade.getStock());
            JsonElement bid = quote.get("bid");
            int gain = 0;

            if (bid != null) {
                int price = bid.getAsInt();
                price = price + random.nextInt(20) + 11;
                
                int qty = random.nextInt(1000) + 70;

                JsonObject data = trade.createTrade(qty, Direction.BUY, price, OrderType.IOC);
                JsonObject response = api.order(trade.getVenue(), trade.getStock(), data);
                gain = response.get("totalFilled").getAsInt();
                goal = goal + gain;
            }

            int chance = random.nextInt(7);
            if (chance == 3 && goal != 0) {
                sellSneaky(random.nextInt(120) + 50, random);
            }

            System.out.println(goal + " - " + gain);
            int wait = random.nextInt(TimeUnit.H_QUARTER_HOUR) + TimeUnit.H_H_QUARTER_HOUR;
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sellSneaky(int qty, Random random) {
        JsonObject quote = api.quote(trade.getVenue(), trade.getStock());
        JsonElement ask = quote.get("ask");
        if (ask != null) {
            int price = ask.getAsInt();
            price = price - (random.nextInt(100) + 75);

            JsonObject data = trade.createTrade(qty, Direction.SELL, price, OrderType.IOC);
            JsonObject response = api.order(trade.getVenue(), trade.getStock(), data);
            int sold = response.get("totalFilled").getAsInt();
            System.out.println("Selled " + sold);
        }
    }
}