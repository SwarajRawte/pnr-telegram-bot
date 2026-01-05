package com.swaraj.pnrbot;

public class PNRBotApp {

    public static void main(String[] args) {
        System.out.println("🤖 PNR Bot started...");
        TelegramUpdateHandler.start();
    }
}
