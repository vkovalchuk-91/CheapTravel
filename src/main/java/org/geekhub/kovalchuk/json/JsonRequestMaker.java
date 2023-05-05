package org.geekhub.kovalchuk.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geekhub.kovalchuk.json.entity.MonthPriceJsonRequest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class JsonRequestMaker {
    public static final String X_API_KEY = "prtl6749387986743898559646983194";

    public static String getLocationsJson() throws IOException {
        URL url = new URL("https://partners.api.skyscanner.net/apiservices/v3/geo/hierarchy/flights/uk-UA");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("x-api-key", X_API_KEY);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getCurrenciesJson() throws IOException {
        URL url = new URL("https://partners.api.skyscanner.net/apiservices/v3/culture/currencies");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        httpConn.setRequestProperty("x-api-key", X_API_KEY);

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static String getMonthPricesJson(
            String currency, long originPlaceId, long destinationPlaceId, int year, int month) throws IOException {
        URL url = new URL("https://partners.api.skyscanner.net/apiservices/v3/flights/indicative/search");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");

        httpConn.setRequestProperty("x-api-key", X_API_KEY);
        httpConn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());

        MonthPriceJsonRequest monthPriceJsonRequest = new MonthPriceJsonRequest(
                currency, originPlaceId, destinationPlaceId, year, month);
        String jsonRequest = new ObjectMapper().writeValueAsString(monthPriceJsonRequest);

        writer.write(jsonRequest);
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
