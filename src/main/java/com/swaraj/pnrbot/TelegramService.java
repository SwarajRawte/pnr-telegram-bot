package com.swaraj.pnrbot;

import okhttp3.*;

public class TelegramService {

    private static final String BOT_TOKEN = "7994361141:AAE4xRTCNj6mQJHqhU8lEkAwmA03h_711OU";
    private static final String CHAT_ID = "1036633702";

    public static void sendMessage(String message) throws Exception {

        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("chat_id", CHAT_ID)
                .add("text", message)
                .build();

        Request request = new Request.Builder()
                .url("https://api.telegram.org/bot" + BOT_TOKEN + "/sendMessage")
                .post(body)
                .build();

        client.newCall(request).execute();
    }
}
