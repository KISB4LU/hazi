package org.example;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;


public class HistoricalData
{
    private String API_KEY = "IKL118V0BEMPS6Y6";
    public String GetData(String symbol, String FUNCTION, String INTERVAL) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String url = String.format("https://www.alphavantage.co/query?function=%s&symbol=%s&interval=%s&apikey=%s", FUNCTION, symbol,INTERVAL, API_KEY);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

        }finally {
            return response.body().string();
        }
    }
}
