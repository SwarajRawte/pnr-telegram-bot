package com.swaraj.pnrbot;

import okhttp3.*;

public class TelegramService {

    private static final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    private static final String TELEGRAM_SEND =
            "https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage";

    private static final OkHttpClient client = new OkHttpClient();

    public static void sendMessage(String chatId, String text, boolean markdown) {

        RequestBody body = new FormBody.Builder()
                .add("chat_id", chatId)
                .add("text", text)
                .add("parse_mode", markdown ? "Markdown" : "")
                .build();

        Request request = new Request.Builder()
                .url(TELEGRAM_SEND)
                .post(body)
                .build();

        try {
            client.newCall(request).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
