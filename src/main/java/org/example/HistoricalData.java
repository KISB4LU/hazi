package org.example;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class HistoricalData
{
    private String API_KEY = "AKXLECPRBPD4ZTEO98X2";
    private String API_SECRET = "DkZSAPaoWPk1MpDgmZ2hfdAjjviCosOOSYQVIxPt";
    public String GetData(String symbol, String timeframe, String limit ,String feed) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = String.format("https://data.alpaca.markets/v2/stocks/bars?symbols=%s&timeframe=%s&limit=%s&adjustment=raw&feed=%s&sort=asc", symbol, timeframe,limit, feed);

        Request request = new Request.Builder()
                .url(url)//"https://data.alpaca.markets/v2/stocks/bars?symbols=AAPL&timeframe=1Min&limit=1000&adjustment=raw&feed=sip&sort=asc")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", API_KEY)
                .addHeader("APCA-API-SECRET-KEY", API_SECRET)
                .build();

        Response response = client.newCall(request).execute();

        //Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

        }finally {
            return response.body().string();
        }
    }
    public Chart[] GetChart(String symbol, String timeframe, String limit ,String feed) throws IOException {
        String jsonResponse = GetData(symbol, timeframe, limit ,feed);
        JsonObject JsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject bars =  JsonObject.get("bars").getAsJsonObject();
        JsonArray charts = bars.get(symbol).getAsJsonArray();
        Chart[] res = new Chart[charts.size()];
        int i = 0;
        for (JsonElement chart : charts) {
            JsonObject chartObj = chart.getAsJsonObject();
            double close = chartObj.get("c").getAsDouble();
            double high = chartObj.get("h").getAsDouble();
            double low = chartObj.get("l").getAsDouble();
            double open = chartObj.get("o").getAsDouble();
            int volume = chartObj.get("v").getAsInt();
            String Date = chartObj.get("t").getAsString();
            res[i] = new Chart(Date,open,high,low,close,volume);
            i++;
        }
        return res;
    }
}
