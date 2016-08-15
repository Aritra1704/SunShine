package com.arpaul.sunshine.parser;

import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.dataObjects.WeatherDescriptionDO;
import com.arpaul.utilitieslib.JSONUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ARPaul on 05-04-2016.
 */
public class WeatherParser {
    public ArrayList<WeatherDataDO> parseWeatherData(String response){
        ArrayList<WeatherDataDO> arrWeather = new ArrayList<>();
        WeatherDataDO objWeatherDO = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(WeatherDataDO.TAG_LIST);

            for (int i = 0; i < result.length(); i++) {
                JSONObject body = result.getJSONObject(i);

                objWeatherDO = new WeatherDataDO();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+i);
                objWeatherDO.saveData(cal.getTimeInMillis(), WeatherDataDO.WEATHERDATA.TYPE_DATE);
                //objWeatherDO.savedt(body.getLong(WeatherDataDO.TAG_DT));

                if(JSONUtils.hasJSONtag(body, WeatherDataDO.TAG_TEMP)){
                    JSONObject tempObject = body.getJSONObject(WeatherDataDO.TAG_TEMP);

                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_DAY))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_DAY), WeatherDataDO.WEATHERDATA.TYPE_TEMP);

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
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_HUMIDITY))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_HUMIDITY), WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY);

                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_SPEED))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_SPEED), WeatherDataDO.WEATHERDATA.TYPE_SPEED);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_DEG))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_DEG), WeatherDataDO.WEATHERDATA.TYPE_DEG);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_CLOUDS))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_CLOUDS), WeatherDataDO.WEATHERDATA.TYPE_CLOUDS);
                    if(JSONUtils.hasJSONtag(tempObject, WeatherDataDO.TAG_CLOUDS))
                        objWeatherDO.saveData(tempObject.getDouble(WeatherDataDO.TAG_RAIN), WeatherDataDO.WEATHERDATA.TYPE_RAIN);
                }

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
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return arrWeather;
    }
}
