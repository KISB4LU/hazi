package org.example;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class HistoricalData
{
    private String API_KEY = "PKRCBG5UH107R9QUT41V";
    private String API_SECRET = "pK10CZABYWE5HTppdLFiqHcSev9qNUomW2CrY66g";

    public String GetData(String symbol, String timeframe, String start, String end ,String feed) throws IOException {
        OkHttpClient client = new OkHttpClient();

        //String url = String.format("https://data.alpaca.markets/v2/stocks/bars?symbols=%s&timeframe=%s&limit=%s&adjustment=raw&feed=%s&sort=asc", symbol, timeframe,limit, feed);
        String url2 = String.format("https://data.alpaca.markets/v2/stocks/bars?symbols=%s&timeframe=%s&start=%s&end=%s&adjustment=raw&feed=iex&sort=asc",symbol,timeframe,start,end);
        Request request = new Request.Builder()
                .url(url2)//"https://data.alpaca.markets/v2/stocks/bars?symbols=AAPL&timeframe=1Min&limit=1000&adjustment=raw&feed=sip&sort=asc")
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
    public Chart[] GetChart(String symbol, String timeframe, String start, String end ,String feed) {
        String jsonResponse = null;
        try {
            jsonResponse = GetData(symbol, timeframe, start, end ,feed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Historical Data"+jsonResponse);
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
    public Asset[] GetAsset() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://paper-api.alpaca.markets/v2/assets?status=active&attributes=")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", "PKRCBG5UH107R9QUT41V")
                .addHeader("APCA-API-SECRET-KEY", "pK10CZABYWE5HTppdLFiqHcSev9qNUomW2CrY66g")
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

        }finally {
            jsonResponse = response.body().string();
        }
        JsonArray assets = JsonParser.parseString(jsonResponse).getAsJsonArray();
        Asset[] res = new Asset[assets.size()];
        int i = 0;
        for (JsonElement asset : assets) {
            JsonObject a = asset.getAsJsonObject();
            String name = a.get("name").getAsString();
            String symbol = a.get("symbol").getAsString();
            //System.out.println("name: "+name + " symbol: "+symbol);
            Asset NewAsset = new Asset(symbol,name);
            res[i++] = NewAsset;

        }
        return res;
    }
}
