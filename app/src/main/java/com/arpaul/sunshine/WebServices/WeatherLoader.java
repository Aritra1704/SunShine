package com.arpaul.sunshine.webServices;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.support.v4.content.AsyncTaskLoader;

import com.arpaul.sunshine.dataAccess.SSCPConstants;
import com.arpaul.sunshine.dataObjects.LocationDO;
import com.arpaul.sunshine.dataObjects.ParserDO;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.parser.WeatherParser;
import com.arpaul.sunshine.R;
import com.arpaul.utilitieslib.CalendarUtils;

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
        ArrayList<WeatherDataDO> arrWeather = new ArrayList<>();
        String URL = getURL();
        LocationDO objLocationDO = null;
//        String response = executeRequest(URL);
        WebServiceResponse responseDO = new RestServiceCalls(URL, null, WEBSERVICE_TYPE.GET).getData();
        if(responseDO.getResponseCode() == WebServiceResponse.ResponseType.SUCCESS) {
            ParserDO parseDO = new WeatherParser().parseWeatherData(responseDO.getResponseMessage());

            ContentProviderResult[] insertWeather = new ContentProviderResult[arrWeather.size()];
            ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();
            ContentValues contentValues = null;
            ContentProviderOperation.Builder builder = null;

            if(parseDO.linkHash.containsKey(ParserDO.ParseType.TYPE_LOCATION)){
                objLocationDO = (LocationDO) parseDO.linkHash.get(ParserDO.ParseType.TYPE_LOCATION);
                if(objLocationDO != null){
                    contentValues = new ContentValues();
                    contentValues.put(LocationDO.LOCATION_ID, objLocationDO.location_ID);
                    contentValues.put(LocationDO.CITY_NAME, objLocationDO.city_name);
                    contentValues.put(LocationDO.COORD_LONG, objLocationDO.coord_long);
                    contentValues.put(LocationDO.COORD_LAT, objLocationDO.coord_lat);

                    builder = ContentProviderOperation.newInsert(SSCPConstants.CONTENT_URI_LOCATION);
                    builder.withValues(contentValues);
                    contentProviderOperations.add(builder.build());
                }
            }

            /*Cursor cursor = context.getContentResolver().query(SSCPConstants.CONTENT_URI_LOCATION,
                    new String[]{LocationDO.LOCATION_ID},
                    LocationDO.CITY_NAME + SSCPConstants.TABLE_QUES,
                    new String[]{(String) new LocationDO().getData(LocationDO.LOCATIONDATA.TYPE_CITY_NAME)},
                    null);*/

            arrWeather = (ArrayList<WeatherDataDO>) parseDO.linkHash.get(ParserDO.ParseType.TYPE_WEATHER);
            if(arrWeather != null && arrWeather.size() > 0){

                String location_id = objLocationDO.location_ID;

                for(WeatherDataDO productId: arrWeather){

                    builder = ContentProviderOperation.newInsert(SSCPConstants.CONTENT_URI_WEATHER);
                    contentValues = new ContentValues();

                    contentValues.put(WeatherDataDO.LOCATION_ID, location_id);
                    contentValues.put(WeatherDataDO.DATE_MILLIS, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS));
                    String date = CalendarUtils.getDatefromTimeinMmiliesPattern((long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), CalendarUtils.DATE_FORMAT1)+" ";
                    contentValues.put(WeatherDataDO.DATE, date);
                    contentValues.put(WeatherDataDO.TEMP_DAY, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP));
                    contentValues.put(WeatherDataDO.TEMP_MINIMUM, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN));
                    contentValues.put(WeatherDataDO.TEMP_MAXIMUM, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX));
                    contentValues.put(WeatherDataDO.TEMP_MORN, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MORN));
                    contentValues.put(WeatherDataDO.TEMP_EVE, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_EVE));
                    contentValues.put(WeatherDataDO.TEMP_NIGHT, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_NIGHT));

                    contentValues.put(WeatherDataDO.PRESSURE, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_PRESSURE));
                    contentValues.put(WeatherDataDO.HUMIDITY, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY));
                    contentValues.put(WeatherDataDO.WIND, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_WIND));
                    contentValues.put(WeatherDataDO.DEG, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DEG));
                    contentValues.put(WeatherDataDO.CLOUDS, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_CLOUDS));
                    contentValues.put(WeatherDataDO.RAIN, (double) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_RAIN));

                    builder.withValues(contentValues);
                    contentProviderOperations.add(builder.build());
                }

                try {
                    insertWeather = context.getContentResolver().applyBatch(SSCPConstants.CONTENT_AUTHORITY, contentProviderOperations);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }

                return arrWeather;
            }
        }

        return null;
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
