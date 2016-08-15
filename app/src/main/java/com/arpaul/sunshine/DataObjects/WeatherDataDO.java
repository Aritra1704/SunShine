package com.arpaul.sunshine.dataObjects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ARPaul on 04-04-2016.
 */
public class WeatherDataDO implements Serializable {

    private long dt = 0;
    private double temperatureDay = 0;
    private double temperatureMin = 0;
    private double temperatureMax = 0;
    private double temperatureNight = 0;
    private double temperatureEve = 0;
    private double temperatureMorn = 0;
    private double pressure = 0;
    private double humidity = 0;

    private double speed = 0;
    private double deg = 0;
    private double clouds = 0;
    private double rain = 0;

    public ArrayList<WeatherDescriptionDO> arrWeatheDescp = new ArrayList<>();

    public void savedt(long dtvalue){
        dt = dtvalue;
    }
    public void saveTemperatureDay(double temp){
        temperatureDay = temp;
    }

    public void saveTemperatureMin(double temp){
        temperatureMin = temp;
    }
    public void saveTemperatureMax(double temp){
        temperatureMax = temp;
    }
    public void saveTemperatureNight(double temp){
        temperatureNight = temp;
    }
    public void saveTemperatureEve(double temp){
        temperatureEve = temp;
    }
    public void saveTemperatureMorn(double temp){
        temperatureMorn = temp;
    }
    public void savePressure(double temp){
        pressure = temp;
    }
    public void saveHumidity(double temp){
        humidity = temp;
    }
    public void saveSpeed(double temp){
        speed = temp;
    }
    public void saveDeg(double temp){
        deg = temp;
    }
    public void saveClouds(double temp){
        clouds = temp;
    }
    public void saveRain(double temp){
        rain = temp;
    }

    public String getTemperatureDay(){
        return ""+temperatureDay;
    }
    public long getdt(){
        return dt;
    }
    public String getTemperatureMin(){
        return ""+temperatureMin;
    }
    public String getTemperatureMax(){
        return ""+temperatureMax;
    }

    private double convertTempFromKelvin(double temperature){
        return 273-temperature;
    }

    public static final String TAG_LIST = "list";
    public static final String TAG_WEATHER = "weather";

    public static final String TAG_DT = "dt";
    public static final String TAG_TEMP = "temp";
    public static final String TAG_DAY = "day";
    public static final String TAG_TEMP_MIN = "min";
    public static final String TAG_TEMP_MAX = "max";
    public static final String TAG_TEMP_NIGHT = "night";
    public static final String TAG_TEMP_EVE = "eve";
    public static final String TAG_TEMP_MORN = "morn";
    public static final String TAG_PRESSURE = "pressure";
    public static final String TAG_HUMIDITY = "humidity";
    public static final String TAG_SPEED = "speed";
    public static final String TAG_DEG = "deg";
    public static final String TAG_CLOUDS = "clouds";
    public static final String TAG_RAIN = "rain";
}
