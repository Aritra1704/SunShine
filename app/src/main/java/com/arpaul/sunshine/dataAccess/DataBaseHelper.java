package com.arpaul.sunshine.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arpaul.sunshine.dataObjects.LocationDO;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;

/**
 * Created by ARPaul on 09-05-2016.
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;

    static final String CREATE_LOCATION_DB_TABLE =
            " CREATE TABLE IF NOT EXISTS " + SSCPConstants.LOCATION_TABLE_NAME +
                    " (" + LocationDO.LOCATION_ID       + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LocationDO.LOCATION_SETTING         + " VARCHAR  NOT NULL, " +
                    LocationDO.CITY_NAME                + " VARCHAR  NOT NULL, " +
                    LocationDO.COORD_LAT                + " DOUBLE  NOT NULL, " +
                    LocationDO.COORD_LONG               + " DOUBLE  NOT NULL);";

    static final String CREATE_WEATHER_DB_TABLE =
            " CREATE TABLE IF NOT EXISTS " + SSCPConstants.WEATHER_TABLE_NAME +
                    " (" + WeatherDataDO.WEATHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WeatherDataDO.LOCATION_ID       + " VARCHAR NOT NULL, " +
                    WeatherDataDO.DATE              + " DATETIME , " +
                    WeatherDataDO.DATE_MILLIS       + " LONG , " +
                    WeatherDataDO.TEMP_DAY          + " DOUBLE , " +
                    WeatherDataDO.TEMP_MINIMUM      + " DOUBLE , " +
                    WeatherDataDO.TEMP_MAXIMUM      + " DOUBLE , " +
                    WeatherDataDO.TEMP_NIGHT        + " DOUBLE , " +
                    WeatherDataDO.TEMP_EVE          + " DOUBLE , " +
                    WeatherDataDO.TEMP_MORN         + " DOUBLE , " +
                    WeatherDataDO.PRESSURE          + " DOUBLE , " +
                    WeatherDataDO.HUMIDITY          + " DOUBLE," +
                    WeatherDataDO.WIND              + " DOUBLE," +
                    WeatherDataDO.DEG               + " DOUBLE," +
                    WeatherDataDO.CLOUDS            + " DOUBLE," +
                    WeatherDataDO.RAIN              + " DOUBLE );";

    DataBaseHelper(Context context){
        super(context, SSCPConstants.DATABASE_NAME, null, SSCPConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOCATION_DB_TABLE);
        db.execSQL(CREATE_WEATHER_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SSCPConstants.LOCATION_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SSCPConstants.WEATHER_TABLE_NAME);
        onCreate(db);
    }
}
