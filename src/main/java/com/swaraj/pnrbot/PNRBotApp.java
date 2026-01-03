package com.swaraj.pnrbot;

public class PNRBotApp {

    public static void main(String[] args) throws Exception {

        // 1️⃣ Test Telegram only (first run)
        TelegramService.sendMessage("✅ Telegram bot working!");

        // 2️⃣ PNR Status + Telegram
        String status = PNRService.fetchPNRStatus();
        TelegramService.sendMessage(status);
    }
}
