package com.swaraj.pnrbot;

public class MessageFormatter {

    public static String format(String pnr, String status) {

        return """
        🚆 *PNR Status Update*

        *PNR:* %s
        *Status:* %s

        ⏰ Checked just now
        """.formatted(pnr, status);
    }
}
