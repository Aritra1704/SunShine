package com.arpaul.sunshine.webServices;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;
import android.support.v4.content.AsyncTaskLoader;

import com.arpaul.sunshine.dataAccess.SSCPConstants;
import com.arpaul.sunshine.dataObjects.LocationDO;
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
//        String response = executeRequest(URL);
        WebServiceResponse responseDO = new RestServiceCalls(URL, null, WEBSERVICE_TYPE.GET).getData();
        if(responseDO.getResponseCode() == WebServiceResponse.ResponseType.SUCCESS)
            arrWeather = new WeatherParser().parseWeatherData(responseDO.getResponseMessage());

        Cursor cursor = context.getContentResolver().query(SSCPConstants.CONTENT_URI_LOCATION,
                new String[]{LocationDO.LOCATION_ID},
                LocationDO.CITY_NAME + SSCPConstants.TABLE_QUES,
                new String[]{(String) new LocationDO().getData(LocationDO.LOCATIONDATA.TYPE_CITY_NAME)},
                null);
        if(cursor != null && cursor.moveToFirst()){

            String location_id = cursor.getColumnName(cursor.getColumnIndex(LocationDO.LOCATION_ID));

            ContentProviderResult[] insertWeather = new ContentProviderResult[arrWeather.size()];

            ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<ContentProviderOperation>();

            ContentValues contentValues = null;
            ContentProviderOperation.Builder builder = null;
            for(WeatherDataDO productId: arrWeather){

                builder = ContentProviderOperation.newInsert(SSCPConstants.CONTENT_URI_WEATHER);
                contentValues = new ContentValues();

                contentValues.put(WeatherDataDO.LOCATION_ID, location_id);
                contentValues.put(WeatherDataDO.DATE_MILLIS, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS));
                String date = CalendarUtils.getDatefromTimeinMmiliesPattern((long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), CalendarUtils.DATE_FORMAT1)+" ";
                contentValues.put(WeatherDataDO.DATE, date);
                contentValues.put(WeatherDataDO.TEMP_DAY, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DAY));
                contentValues.put(WeatherDataDO.TEMP_MINIMUM, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN));
                contentValues.put(WeatherDataDO.TEMP_MAXIMUM, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX));
                contentValues.put(WeatherDataDO.TEMP_MORN, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MORN));
                contentValues.put(WeatherDataDO.TEMP_EVE, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_EVE));
                contentValues.put(WeatherDataDO.TEMP_NIGHT, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_NIGHT));

                contentValues.put(WeatherDataDO.PRESSURE, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_PRESSURE));
                contentValues.put(WeatherDataDO.HUMIDITY, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY));
                contentValues.put(WeatherDataDO.WIND, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_WIND));
                contentValues.put(WeatherDataDO.DEG, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_DEG));
                contentValues.put(WeatherDataDO.CLOUDS, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_CLOUDS));
                contentValues.put(WeatherDataDO.RAIN, (long) productId.getData(WeatherDataDO.WEATHERDATA.TYPE_RAIN));

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
