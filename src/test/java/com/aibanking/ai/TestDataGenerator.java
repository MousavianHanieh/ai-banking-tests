package com.aibanking.ai;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class TestDataGenerator {

    private final ClaudeClient claudeClient;

    public TestDataGenerator() {
        this.claudeClient = new ClaudeClient();
    }

    public List<String[]> generateLoginTestData() {
        String prompt = """
                Generate exactly 4 login test cases for a banking app.
                Return ONLY a JSON array, no explanation.
                Each item must have: username, password, expectedResult (success or fail).
                Use username "john" and password "demo" for the valid case.
                Example format:
                [
                  {"username": "john", "password": "demo", "expectedResult": "success"},
                  {"username": "wrong", "password": "wrong", "expectedResult": "fail"}
                ]
                """;

        String response = claudeClient.sendMessage(prompt);
        System.out.println("📋 Generated test cases:\n" + response);
        return parseLoginData(response);
    }

    public List<String> generateTransferAmounts() {
        String prompt = """
                Generate exactly 4 transfer amount test cases for a banking app.
                Return ONLY a JSON array of strings, no explanation.
                Include valid and invalid amounts.
                Example format:
                ["100", "0", "-50", "abc"]
                """;

        String response = claudeClient.sendMessage(prompt);
        return parseAmounts(response);
    }

    private List<String[]> parseLoginData(String json) {
        List<String[]> result = new ArrayList<>();
        try {
            String clean = json.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
            JsonArray array = JsonParser.parseString(clean).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonObject obj = array.get(i).getAsJsonObject();
                result.add(new String[]{
                        obj.get("username").getAsString(),
                        obj.get("password").getAsString(),
                        obj.get("expectedResult").getAsString()
                });
            }
        } catch (Exception e) {
            System.out.println("Parse error: " + e.getMessage());
        }
        return result;
    }

    private List<String> parseAmounts(String json) {
        List<String> result = new ArrayList<>();
        try {
            String clean = json.replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();
            JsonArray array = JsonParser.parseString(clean).getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                result.add(array.get(i).getAsString());
            }
        } catch (Exception e) {
            System.out.println("Parse error: " + e.getMessage());
        }
        return result;
    }
}