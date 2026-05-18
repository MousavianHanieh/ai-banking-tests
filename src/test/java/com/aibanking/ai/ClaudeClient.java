package com.aibanking.ai;

import com.aibanking.config.Config;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ClaudeClient {

    private static final MediaType JSON_TYPE =
            MediaType.get("application/json; charset=utf-8");

    private final OkHttpClient httpClient;

    public ClaudeClient() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public String sendMessage(String userMessage) {
        JsonObject requestBody = buildRequestBody(userMessage);

        Request request = new Request.Builder()
                .url(Config.CLAUDE_API_URL)
                .addHeader("x-api-key", Config.CLAUDE_API_KEY)
                .addHeader("anthropic-version", "2023-06-01")
                .addHeader("content-type", "application/json")
                .post(RequestBody.create(requestBody.toString(), JSON_TYPE))
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            String responseBody = response.body().string();
            return extractText(responseBody);
        } catch (IOException e) {
            throw new RuntimeException("Failed to call Claude API: " + e.getMessage());
        }
    }

    private JsonObject buildRequestBody(String userMessage) {
        JsonObject body = new JsonObject();
        body.addProperty("model", Config.CLAUDE_MODEL);
        body.addProperty("max_tokens", 1024);

        JsonArray messages = new JsonArray();
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", userMessage);
        messages.add(message);

        body.add("messages", messages);
        return body;
    }

    private String extractText(String responseBody) {
        System.out.println("Claude response: " + responseBody);
        JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();
        return json.getAsJsonArray("content")
                .get(0)
                .getAsJsonObject()
                .get("text")
                .getAsString();
    }
}