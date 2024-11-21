package org.example;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


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
    public BarSeries GetChart(String symbol, String timeframe, String start, String end , String feed) {
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
        BarSeries ret = new BaseBarSeries();
        for (JsonElement chart : charts) {
            JsonObject chartObj = chart.getAsJsonObject();
            double close = chartObj.get("c").getAsDouble();
            double high = chartObj.get("h").getAsDouble();
            double low = chartObj.get("l").getAsDouble();
            double open = chartObj.get("o").getAsDouble();
            int volume = chartObj.get("v").getAsInt();
            String Date = chartObj.get("t").getAsString();
            ret.addBar(ZonedDateTime.parse(Date, DateTimeFormatter.ISO_ZONED_DATE_TIME), close, high, low, open, volume);
        }
        return ret;
    }
    public Asset[] GetAsset() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://paper-api.alpaca.markets/v2/assets?status=active&attributes=")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", API_KEY)
                .addHeader("APCA-API-SECRET-KEY", API_SECRET)
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
            Asset NewAsset = new Asset(symbol,name);
            res[i++] = NewAsset;

        }
        return res;
    }
    public Quote GetQuote(String Symbol) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = String.format("https://data.alpaca.markets/v2/stocks/quotes/latest?symbols=%s&feed=iex&currency=USD",Symbol);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("APCA-API-KEY-ID", API_KEY)
                .addHeader("APCA-API-SECRET-KEY", API_SECRET)
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
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonObject symbolQuote = jsonObject.getAsJsonObject("quotes").getAsJsonObject(Symbol);

        double ask = symbolQuote.get("ap").getAsDouble();
        double bid = symbolQuote.get("bp").getAsDouble();

        return new Quote(ask, bid);
    }
}
