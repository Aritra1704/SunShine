package com.arpaul.sunshine.parser;

import com.arpaul.sunshine.common.AppConstants;
import com.arpaul.sunshine.dataObjects.LocationDO;
import com.arpaul.sunshine.dataObjects.ParserDO;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.dataObjects.WeatherDescriptionDO;
import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.JSONUtils;
import com.arpaul.utilitieslib.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ARPaul on 05-04-2016.
 */
public class WeatherParser {
    public ParserDO parseWeatherData(String response){
        ParserDO parseDO = new ParserDO();
        ArrayList<WeatherDataDO> arrWeather = new ArrayList<>();
        WeatherDataDO objWeatherDO = null;
        LocationDO objLocationDO = new LocationDO();
        try {
            JSONObject jsonResponse = new JSONObject(response);

            //Location parsing
            JSONObject resultCity = jsonResponse.getJSONObject(WeatherDataDO.TAG_CITY);
            if(JSONUtils.hasJSONtag(resultCity, LocationDO.TAG_ID)){
                objLocationDO.saveData(resultCity.getString(LocationDO.TAG_ID), LocationDO.LOCATIONDATA.TYPE_LOCATION_ID);
            }
            if(JSONUtils.hasJSONtag(resultCity, LocationDO.TAG_NAME)){
                objLocationDO.saveData(resultCity.getString(LocationDO.TAG_NAME), LocationDO.LOCATIONDATA.TYPE_CITY_NAME);
            }
            if(JSONUtils.hasJSONtag(resultCity, LocationDO.TAG_COORD)){
                JSONObject jsonCoord = resultCity.getJSONObject(LocationDO.TAG_COORD);
                if(JSONUtils.hasJSONtag(jsonCoord, LocationDO.TAG_LON)){
                    objLocationDO.saveData(StringUtils.getDouble(jsonCoord.getString(LocationDO.TAG_LON)), LocationDO.LOCATIONDATA.TYPE_COORD_LON);
                }
                if(JSONUtils.hasJSONtag(jsonCoord, LocationDO.TAG_LAT)){
                    objLocationDO.saveData(StringUtils.getDouble(jsonCoord.getString(LocationDO.TAG_LAT)), LocationDO.LOCATIONDATA.TYPE_COORD_LAT);
                }
            }

            //Weather parsing
            JSONArray result = jsonResponse.getJSONArray(WeatherDataDO.TAG_LIST);
            for (int i = 0; i < result.length(); i++) {
                JSONObject body = result.getJSONObject(i);

                objWeatherDO = new WeatherDataDO();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+i);
                objWeatherDO.saveData(CalendarUtils.getDatefromTimeinMilliesPattern(cal.getTimeInMillis(), CalendarUtils.DATE_FORMAT1), WeatherDataDO.WEATHERDATA.TYPE_DATE);
                objWeatherDO.saveData(cal.getTimeInMillis(), WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS);
                //objWeatherDO.savedt(body.getLong(WeatherDataDO.TAG_DT));

                //Temperature, pressure, humidity
                if(JSONUtils.hasJSONtag(body, WeatherDataDO.TAG_MAIN)){
                    JSONObject tempObject = body.getJSONObject(WeatherDataDO.TAG_MAIN);

                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_TEMP))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_TEMP), WeatherDataDO.WEATHERDATA.TYPE_TEMP);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_TEMP_MIN))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_TEMP_MIN), WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_TEMP_MAX))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_TEMP_MAX), WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_TEMP_NIGHT))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_TEMP_NIGHT), WeatherDataDO.WEATHERDATA.TYPE_TEMP_NIGHT);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_TEMP_EVE))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_TEMP_EVE), WeatherDataDO.WEATHERDATA.TYPE_TEMP_EVE);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_TEMP_MORN))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_TEMP_MORN), WeatherDataDO.WEATHERDATA.TYPE_TEMP_MORN);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_PRESSURE))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_PRESSURE), WeatherDataDO.WEATHERDATA.TYPE_PRESSURE);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_SEA_LEVEL))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_SEA_LEVEL), WeatherDataDO.WEATHERDATA.TYPE_SEA_LEVEL);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_GRND_LEVEL))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_GRND_LEVEL), WeatherDataDO.WEATHERDATA.TYPE_GRN_LEVEL);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_HUMIDITY))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_HUMIDITY), WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY);
                }

                //Wind
                if(JSONUtils.hasJSONtag(body, WeatherDataDO.TAG_WIND)){
                    JSONObject jsonWind = body.getJSONObject(WeatherDataDO.TAG_WIND);
                    if(JSONUtils.hasJSONtag(jsonWind, WeatherDataDO.TAG_SPEED))
                        objWeatherDO.saveData(jsonWind.getDouble(WeatherDataDO.TAG_SPEED), WeatherDataDO.WEATHERDATA.TYPE_WIND);
                    if(JSONUtils.hasJSONtag(jsonWind, WeatherDataDO.TAG_DEG))
                        objWeatherDO.saveData(jsonWind.getDouble(WeatherDataDO.TAG_DEG), WeatherDataDO.WEATHERDATA.TYPE_DEG);
                }

                //Clouds
                if(JSONUtils.hasJSONtag(body, WeatherDataDO.TAG_CLOUDS)){
                    JSONObject jsonCloud = body.getJSONObject(WeatherDataDO.TAG_CLOUDS);
                    if(JSONUtils.hasJSONtag(jsonCloud, WeatherDataDO.TAG_CLOUDS_ALL)){
                        objWeatherDO.saveData(jsonCloud.getDouble(WeatherDataDO.TAG_CLOUDS_ALL), WeatherDataDO.WEATHERDATA.TYPE_CLOUDS);
                    }
                }

                //Rain
                if(JSONUtils.hasJSONtag(body, WeatherDataDO.TAG_RAIN)){
                    JSONObject jsonRain = body.getJSONObject(WeatherDataDO.TAG_RAIN);
                    if(JSONUtils.hasJSONtag(jsonRain, WeatherDataDO.TAG_RAIN_3H)){
                        objWeatherDO.saveData(jsonRain.getDouble(WeatherDataDO.TAG_RAIN_3H), WeatherDataDO.WEATHERDATA.TYPE_RAIN);
                    }
                }

                //Weather
                JSONArray weatherresult = body.getJSONArray(WeatherDataDO.TAG_WEATHER);
                for (int j = 0; j < weatherresult.length(); j++) {
                    JSONObject weatherObject = weatherresult.getJSONObject(j);

                    WeatherDescriptionDO weatherDescp = new WeatherDescriptionDO();

                    weatherDescp.saveData(weatherObject.getString(WeatherDescriptionDO.TAG_MAIN),WeatherDescriptionDO.WEATHER_DESC_DATA.TYPE_MAIN);
                    weatherDescp.saveData(weatherObject.getString(WeatherDescriptionDO.TAG_DESCRIPTION),WeatherDescriptionDO.WEATHER_DESC_DATA.TYPE_DESCRIPTION);
                    weatherDescp.saveData(weatherObject.getString(WeatherDescriptionDO.TAG_ICON),WeatherDescriptionDO.WEATHER_DESC_DATA.TYPE_ICON);

                    objWeatherDO.arrWeatheDescp.add(weatherDescp);
                }

                arrWeather.add(objWeatherDO);
            }

            if(objLocationDO != null)
                parseDO.linkHash.put(ParserDO.ParseType.TYPE_LOCATION, objLocationDO);
            if(arrWeather != null && arrWeather.size() > 0)
                parseDO.linkHash.put(ParserDO.ParseType.TYPE_WEATHER, arrWeather);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return parseDO;
    }
}
