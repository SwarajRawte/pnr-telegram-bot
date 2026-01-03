package com.swaraj.pnrbot;

import okhttp3.*;
import com.fasterxml.jackson.databind.*;

public class PNRService {

    private static final String RAPID_API_KEY = "7dc43a2238msh98fa9563b3e4f6cp19378cjsnefa899585f57";
    private static final String PNR_NUMBER = "2247324860S"; // your PNR

    public static String fetchPNRStatus() throws Exception {

        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse(
                        "https://irctc1.p.rapidapi.com/api/v3/getPNRStatus")
                .newBuilder()
                .addQueryParameter("pnrNumber", PNR_NUMBER)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("X-RapidAPI-Key", RAPID_API_KEY)
                .addHeader("X-RapidAPI-Host", "irctc1.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();
        String json = response.body().string();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        if (!root.get("status").asBoolean()) {
            return "❌ Unable to fetch PNR status.";
        }

        JsonNode data = root.get("data");

        String train = data.get("trainName").asText();
        String date = data.get("dateOfJourney").asText();
        String currentStatus =
                data.get("passengerList").get(0).get("currentStatus").asText();

        return "PNR Status Update\n"
                + "Train: " + train + "\n"
                + "Date: " + date + "\n"
                + "Status: " + currentStatus;

    }
}
