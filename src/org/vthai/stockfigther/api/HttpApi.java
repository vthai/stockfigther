package org.vthai.stockfighter.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.Gson;

public class HttpApi {
    public static final String GET = "GET";

    public static final String POST = "POST";

    public static final String DELETE = "DELETE";

    private static final String stockfighterUrl = "https://api.stockfighter.io/ob/api";

    private final String persistentAPIKey = "dacf7b2b6392d98efab3fe28cf445b45c386a1a1";

    public JsonObject request(String method, String command, JsonObject... data) {
        System.out.println("[Request] command: " + command);
        try {
            URL url = new URL(stockfighterUrl + command);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Accept", "application/json");

            if (method.equals(POST)) {
                connection.setRequestProperty("X-Stockfighter-Authorization", persistentAPIKey);
                connection.setRequestProperty("Content-Type", "application/json");

                connection.setDoOutput(true);
                Gson gson = new Gson();
                writeToStream(gson.toJson(data[0]), connection);
            }

            if (connection.getResponseCode() != 200) {
                String reason = readToString(connection.getInputStream());
                throw new RuntimeException("Got a HTTP " + connection.getResponseCode() + " because:\n" + reason);
            }
            String str = readToString(connection.getInputStream());
            connection.disconnect();

            JsonParser parser = new JsonParser();
            return parser.parse(str).getAsJsonObject();

        } catch (MalformedURLException e) {
            throw new RuntimeException("HTTP request fail:\n", e);
        } catch (IOException e) {
            throw new RuntimeException("HTTP request fail:\n", e);
        }
    }

    private void writeToStream(String json, HttpURLConnection connection) {
        System.out.println("\t[POST] json data:\n" + json);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(json.getBytes("UTF-8"));
            os.flush();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException("Fail to write post data ", e);
        }
    }

    private String readToString(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder str = new StringBuilder();
            String output;
            System.out.println("[Response] data:");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                str.append(output);
            }
            return str.toString();
        } catch (IOException e) {
            throw new RuntimeException("Fail to convert stream to string ", e);
        }
    }
}