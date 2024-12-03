package org.example;

import com.google.gson.JsonArray;
import okhttp3.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class HistoricalDataTest {

    OkHttpClient client = Mockito.mock(OkHttpClient.class);
    Call call = Mockito.mock(Call.class);
    Response response = Mockito.mock(Response.class);
    ResponseBody responseBody = Mockito.mock(ResponseBody.class);

    HistoricalData historicalData = new HistoricalData();

    @AfterEach
    public void tearDown() {
        Mockito.reset(client, call, response, responseBody);
    }

    @Test
    public void testGetAsset_singleAsset() throws IOException {
        String assetJson = "[{\"name\": \"first asset\", \"symbol\": \"FA\"}]";
        setMockedResponse(assetJson);

        Asset[] assets = historicalData.GetAsset();
        assertEquals(1, assets.length);
        assertEquals("FA", assets[0].getSymbol());
        assertEquals("first asset", assets[0].getName());
    }

    @Test
    public void testGetAsset_noAssets() throws IOException {
        String assetJson = "[]";
        setMockedResponse(assetJson);

        Asset[] assets = historicalData.GetAsset();
        assertArrayEquals(new Asset[0], assets);
    }

    private void setMockedResponse(String jsonResponse) throws IOException {
        when(client.newCall(Mockito.any(Request.class))).thenReturn(call);
        when(call.execute()).thenReturn(response);
        when(response.isSuccessful()).thenReturn(true);
        when(response.body()).thenReturn(responseBody);
        when(responseBody.string()).thenReturn(jsonResponse);
        historicalData.client = client;
    }
}

@Test
public void testGetChart_validInput() {
    String chartJson = "{\"bars\" : { \"SYMBOL\" : [ {\"c\" : 150.0, \"h\" : 155.0, \"l\" : 145.0, \"o\" : 146.0, \"v\" : 1000, \"t\" : \"2024-02-15T14:30:00Z\"}]}}";
    try {
        setMockedResponse(chartJson);
        BarSeries chart = historicalData.GetChart("SYMBOL", "1Min", "2024-02-15", "2024-02-15");
        assertEquals(1, chart.getBarCount());
        assertEquals(150.0, chart.getBar(0).getClosePrice().doubleValue());
        assertEquals(155.0, chart.getBar(0).getMaxPrice().doubleValue());
        assertEquals(145.0, chart.getBar(0).getMinPrice().doubleValue());
        assertEquals(146.0, chart.getBar(0).getOpenPrice().doubleValue());
        assertEquals(1000, chart.getBar(0).getVolume().intValue());
    } catch (IOException e) {
        fail(e.getMessage());
    }
}

@Test
public void testGetChart_emptyInput() {
    String chartJson = "{\"bars\" : {\"SYMBOL\" : []}}";
    try {
        setMockedResponse(chartJson);
        BarSeries chart = historicalData.GetChart("SYMBOL", "1Min", "2024-02-15", "2024-02-15");
        assertEquals(0, chart.getBarCount());
    } catch (IOException e) {
        fail(e.getMessage());
    }
}

@Test
public void testGetData_validInput() {
    String dataJson = "{ \"data\": \"valid\" }";
    try {
        setMockedResponse(dataJson);
        String data = historicalData.GetData("SYMBOL", "1Min", "2024-02-15", "2024-02-15");
        assertEquals("{ \"data\": \"valid\" }", data);
    } catch (IOException e) {
        fail(e.getMessage());
    }
}
}

@Test
public void testGetData_emptyInput() {
    String dataJson = "";
    try {
        setMockedResponse(dataJson);
        String data = historicalData.GetData("SYMBOL", "1Min", "2024-02-15", "2024-02-15");
        assertEquals("", data);
    } catch (IOException e) {
        fail(e.getMessage());
    }
}
}

@Test
public void testGetData_invalidInput() {
    String dataJson = "{ \"data\": \"invalid\" }";
    try {
        setMockedResponse(dataJson);
        String data = historicalData.GetData("SYMBOL", "1Min", "2024-02-15", "2024-02-15");
        assertEquals("{ \"data\": \"invalid\" }", data);
    } catch (IOException e) {
        fail(e.getMessage());
    }
}

@Test
public void testGetQuote_validInput() throws IOException {
    String quoteJson = "{\"quotes\":{\"SYMBOL\":{\"ap\":232.14,\"bp\":231.89}}}";
    setMockedResponse(quoteJson);

    Quote quote = historicalData.GetQuote("SYMBOL");
    assertEquals(232.14, quote.getAsk());
    assertEquals(231.89, quote.getBid());
}

@Test
public void testGetQuote_invalidInput() throws IOException {
    String quoteJson = "{\"quotes\":{\"SYMBOL\":{\"ap\":0,\"bp\":0}}}";
    setMockedResponse(quoteJson);

    Quote quote = historicalData.GetQuote("SYMBOL");
    assertEquals(0, quote.getAsk());
    assertEquals(0, quote.getBid());
}
}
