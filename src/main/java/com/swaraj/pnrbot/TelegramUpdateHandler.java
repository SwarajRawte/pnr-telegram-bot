package com.swaraj.pnrbot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TelegramUpdateHandler {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String TELEGRAM_API =
            "https://api.telegram.org/bot" + BOT_TOKEN + "/getUpdates";

    private static long offset = 0;

    public static void start() {

        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        while (true) {
            try {
                String url = TELEGRAM_API + "?timeout=60&offset=" + offset;

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                String json = response.body().string();

                JsonNode root = mapper.readTree(json);
                JsonNode results = root.get("result");

                if (results != null) {
                    for (JsonNode update : results) {

                        offset = update.get("update_id").asLong() + 1;

                        JsonNode message = update.get("message");
                        if (message == null) continue;

                        String text = message.get("text").asText();
                        String chatId = message.get("chat").get("id").asText();

                        handleMessage(chatId, text);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void handleMessage(String chatId, String text) {

        if (text.equalsIgnoreCase("/start")) {
            TelegramService.sendMessage(chatId,
                    "👋 Welcome!\n\nSend a *10-digit PNR number* to get live status.",
                    true);
            return;
        }

        if (!text.matches("\\d{10}")) {
            TelegramService.sendMessage(chatId,
                    "❌ Invalid input.\nPlease send a *10-digit PNR number*.",
                    true);
            return;
        }

        String status = PNRService.fetchPNRStatus(text);
        String formatted = MessageFormatter.format(text, status);

        TelegramService.sendMessage(chatId, formatted, true);
    }
}
