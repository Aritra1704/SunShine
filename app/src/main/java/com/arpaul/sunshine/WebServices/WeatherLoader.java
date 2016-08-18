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
public class WeatherLoader extends AsyncTaskLoader {

    private Context context;

    public WeatherLoader(Context context){
        super(context);
        this.context = context;
    }
    @Override
    public ArrayList<WeatherDataDO> loadInBackground() {
        ArrayList<WeatherDataDO> arr = new ArrayList<>();
        String URL = getURL();
//        String response = executeRequest(URL);
        WebServiceResponse responseDO = new RestServiceCalls(URL, null, WEBSERVICE_TYPE.GET).getData();
        if(responseDO.getResponseCode() == WebServiceResponse.ResponseType.SUCCESS)
            arr = new WeatherParser().parseWeatherData(responseDO.getResponseMessage());
        return arr;
    }

    private String getURL(){
        return ParamBuilder.getDailyForecastParam(WebServiceConstant.URL_OPENWEATHERMAP+WebServiceConstant.URL_DAILY_FORECAST,
                "35",
                "139",
                "json",
                "metric",
                "7",
                getContext().getResources().getString(R.string.api_key));
//        return "lat=35&lon=139&mode=json&units=metric&cnt=7&APPID="+getContext().getResources().getString(R.string.api_key);
    }
}
