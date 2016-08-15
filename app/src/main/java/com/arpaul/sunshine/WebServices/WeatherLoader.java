package com.arpaul.sunshine.webServices;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.parser.WeatherParser;
import com.arpaul.sunshine.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ARPaul on 05-04-2016.
 */
public class WeatherLoader extends AsyncTaskLoader<ArrayList<WeatherDataDO>> {

    private final static int STATUS_SUCCESS             = 200;
    private final static int STATUS_FAILED              = 500;
    private final int TIMEOUT = 10000;

    public WeatherLoader(Context context){
        super(context);
    }
    @Override
    public ArrayList<WeatherDataDO> loadInBackground() {
        ArrayList<WeatherDataDO> arr = new ArrayList<>();
        String URL = getURL();
        String response = executeRequest(URL);
        arr = new WeatherParser().parseWeatherData(response);
        return arr;
    }

    private String getURL(){
        return "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&mode=json&units=metric&cnt=7&APPID="+getContext().getResources().getString(R.string.api_key);
    }

    protected String executeRequest(String request) {
        HttpURLConnection httpClient = null;
        StringBuilder sb = null;
        try {
            URL url = new URL(request);
            httpClient = (HttpURLConnection) url.openConnection();
            httpClient.setRequestMethod("GET");
            httpClient.setRequestProperty("Content-length","0");
            httpClient.setUseCaches(false);
            httpClient.setAllowUserInteraction(false);
            httpClient.setConnectTimeout(TIMEOUT);
            httpClient.setReadTimeout(TIMEOUT);
            httpClient.connect();

            int status = httpClient.getResponseCode();
            switch (status) {
                case STATUS_SUCCESS :
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
                    sb = new StringBuilder();
                    String line;
                    while((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
            }
        } catch(MalformedURLException ex) {
            ex.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            if(httpClient != null) {
                httpClient.disconnect();
            }
        }
        if(sb != null)
            return sb.toString();

        return null;
    }

}
