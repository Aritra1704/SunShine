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

    public void saveData(Object data, WEATHERDATA type){
        switch (type){
            case TYPE_DATE:
                dt = (long) data;
                break;
            case TYPE_TEMP:
                temperatureDay = (double) data;
                break;
            case TYPE_TEMP_MIN:
                temperatureMin = (double)data;
                break;
            case TYPE_TEMP_MAX:
                temperatureMax = (double)data;
                break;
            case TYPE_TEMP_NIGHT:
                temperatureNight = (double)data;
                break;
            case TYPE_TEMP_EVE:
                temperatureEve = (double)data;
                break;
            case TYPE_TEMP_MORN:
                temperatureMorn = (double)data;
                break;
            case TYPE_PRESSURE:
                pressure = (double)data;
                break;
            case TYPE_HUMIDITY:
                humidity = (double)data;
                break;
            case TYPE_SPEED:
                speed = (double)data;
                break;

            case TYPE_DEG:
                deg = (double)data;
                break;
            case TYPE_CLOUDS:
                clouds = (double)data;
                break;
            case TYPE_RAIN:
                rain = (double)data;
                break;
        }
    }

    public Object getData(WEATHERDATA type){
        switch (type){
            case TYPE_DATE:
                return dt;
            case TYPE_TEMP:
                return temperatureDay;
            case TYPE_TEMP_MIN:
                return temperatureMin;
            case TYPE_TEMP_MAX:
                return temperatureMax;
            case TYPE_TEMP_NIGHT:
                return temperatureNight;
            case TYPE_TEMP_EVE:
                return temperatureEve;
            case TYPE_TEMP_MORN:
                return temperatureMorn;
            case TYPE_PRESSURE:
                return pressure;
            case TYPE_HUMIDITY:
                return humidity;
            case TYPE_SPEED:
                return speed;
            case TYPE_DEG:
                return deg;
            case TYPE_CLOUDS:
                return clouds;
            case TYPE_RAIN:
                return rain;
            default:
                return dt;
        }
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

    public enum WEATHERDATA {
        TYPE_DATE,
        TYPE_TEMP,
        TYPE_DAY,
        TYPE_TEMP_MIN,
        TYPE_TEMP_MAX,
        TYPE_TEMP_NIGHT,
        TYPE_TEMP_EVE,
        TYPE_TEMP_MORN,
        TYPE_PRESSURE,
        TYPE_HUMIDITY,
        TYPE_SPEED,
        TYPE_DEG,
        TYPE_CLOUDS,
        TYPE_RAIN
    }
}
