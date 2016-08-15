package com.arpaul.sunshine.parser;

import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.dataObjects.WeatherDescriptionDO;

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
        WeatherDataDO objMovieDetailDO = null;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(WeatherDataDO.TAG_LIST);

            for (int i = 0; i < result.length(); i++) {
                JSONObject body = result.getJSONObject(i);

                objMovieDetailDO = new WeatherDataDO();

                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.DAY_OF_MONTH,cal.get(Calendar.DAY_OF_MONTH)+i);
                objMovieDetailDO.savedt(cal.getTimeInMillis());
                //objMovieDetailDO.savedt(body.getLong(WeatherDataDO.TAG_DT));
                JSONObject tempObject = body.getJSONObject(WeatherDataDO.TAG_TEMP);

                    objMovieDetailDO.saveTemperatureDay(tempObject.getDouble(WeatherDataDO.TAG_DAY));
                    objMovieDetailDO.saveTemperatureMin(tempObject.getDouble(WeatherDataDO.TAG_TEMP_MIN));
                    objMovieDetailDO.saveTemperatureMax(tempObject.getDouble(WeatherDataDO.TAG_TEMP_MAX));
                    objMovieDetailDO.saveTemperatureNight(tempObject.getDouble(WeatherDataDO.TAG_TEMP_NIGHT));
                    objMovieDetailDO.saveTemperatureEve(tempObject.getDouble(WeatherDataDO.TAG_TEMP_EVE));
                    objMovieDetailDO.saveTemperatureMorn(tempObject.getDouble(WeatherDataDO.TAG_TEMP_MORN));

                objMovieDetailDO.savePressure(body.getDouble(WeatherDataDO.TAG_PRESSURE));
                objMovieDetailDO.saveHumidity(body.getDouble(WeatherDataDO.TAG_HUMIDITY));

                JSONArray weatherresult = body.getJSONArray(WeatherDataDO.TAG_WEATHER);
                for (int j = 0; j < weatherresult.length(); j++) {
                    JSONObject weatherObject = weatherresult.getJSONObject(j);

                    WeatherDescriptionDO weatherDescp = new WeatherDescriptionDO();

                    weatherDescp.saveMain(weatherObject.getString(WeatherDescriptionDO.TAG_MAIN));
                    weatherDescp.saveDescription(weatherObject.getString(WeatherDescriptionDO.TAG_DESCRIPTION));
                    weatherDescp.saveIcon(weatherObject.getString(WeatherDescriptionDO.TAG_ICON));

                    objMovieDetailDO.arrWeatheDescp.add(weatherDescp);
                }

                objMovieDetailDO.saveSpeed(body.getDouble(WeatherDataDO.TAG_SPEED));
                objMovieDetailDO.saveDeg(body.getDouble(WeatherDataDO.TAG_DEG));
                objMovieDetailDO.saveClouds(body.getDouble(WeatherDataDO.TAG_CLOUDS));
                if(body.has(WeatherDataDO.TAG_RAIN))
                    objMovieDetailDO.saveRain(body.getDouble(WeatherDataDO.TAG_RAIN));

                arrWeather.add(objMovieDetailDO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return arrWeather;
    }
}
