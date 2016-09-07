package com.arpaul.sunshine.fragments;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arpaul.customalertlibrary.popups.statingDialog.CustomPopupType;
import com.arpaul.gpslibrary.fetchLocation.GPSCallback;
import com.arpaul.gpslibrary.fetchLocation.GPSErrorCode;
import com.arpaul.gpslibrary.fetchLocation.GPSUtills;
import com.arpaul.sunshine.activity.BaseActivity;
import com.arpaul.sunshine.adapter.WeatherAdapter;
import com.arpaul.sunshine.common.AppConstants;
import com.arpaul.sunshine.common.ApplicationInstance;
import com.arpaul.sunshine.dataAccess.SSCPConstants;
import com.arpaul.sunshine.dataObjects.LocationDO;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.R;
import com.arpaul.sunshine.dataObjects.WeatherDescriptionDO;
import com.arpaul.sunshine.webServices.WeatherLoader;
import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.LogUtils;
import com.arpaul.utilitieslib.NetworkUtility;
import com.arpaul.utilitieslib.PermissionUtils;
import com.arpaul.utilitieslib.StringUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by ARPaul on 04-04-2016.
 */
public class ForecastFragment extends Fragment implements LoaderManager.LoaderCallbacks, GPSCallback {

    private TextView tvLocation, tvWeatherCondition, tvCurrentTemp, tvDay, tvMaxTemp, tvMinTemp;
    private RecyclerView rvWeather, rvTodayTemp;
    private WeatherAdapter adapterWeather;

    private GPSUtills gpsUtills;
    private boolean ispermissionGranted = false;
    private boolean isGpsEnabled;
    private LatLng currentLatLng = null;

//    https://blog.branch.io/how-to-set-up-android-m-6.0-marshmallow-app-links-with-deep-linking?bmp=fb&utm_campaign=Q316-Sept-Dev&utm_medium=cpc&utm_source=facebook&utm_term=androidM

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initializeControls(rootView);

        bindControls();

        return rootView;
    }

    private void bindControls(){
        gpsUtills = GPSUtills.getInstance(getActivity());
        gpsUtills.setLogEnable(true);
        gpsUtills.setPackegeName(getActivity().getPackageName());
        gpsUtills.setListner(this);

        if(new PermissionUtils().checkPermission(getActivity(),new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}) != 0){

            new PermissionUtils().verifyLocation(getActivity(),new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION});
        }
        else{
            createGPSUtils();
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle bundle) {
        switch (id){
            case ApplicationInstance.LOADER_FETCH_DAILY_FORECAST_API:
                return new WeatherLoader(getActivity(), bundle);
            case ApplicationInstance.LOADER_FETCH_DAILY_FORECAST_DB:
                return new CursorLoader(getActivity(), SSCPConstants.CONTENT_URI_WEATHER,
                        null,
                        null,
                        null,
                        null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()){
            case ApplicationInstance.LOADER_FETCH_DAILY_FORECAST_API:
                if(data instanceof ArrayList){
                    ArrayList<WeatherDataDO> arrWeather = (ArrayList<WeatherDataDO>) data;
                    adapterWeather.refresh(arrWeather);
                }
                break;
            case ApplicationInstance.LOADER_FETCH_DAILY_FORECAST_DB:
                if(data instanceof Cursor){
                    Cursor cursor = (Cursor) data;
                    WeatherDataDO objWeatherDO = null;
                    if (cursor != null && cursor.moveToFirst()) {
                        objWeatherDO = new WeatherDataDO();
                        ArrayList<WeatherDataDO> arrWeather = new ArrayList<>();
                        do {
                            objWeatherDO.saveData(cursor.getString(cursor.getColumnIndex(WeatherDataDO.DATE)), WeatherDataDO.WEATHERDATA.TYPE_DATE);
                            objWeatherDO.saveData(StringUtils.getLong(cursor.getString(cursor.getColumnIndex(WeatherDataDO.DATE_MILLIS))), WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.TEMP_DAY))), WeatherDataDO.WEATHERDATA.TYPE_TEMP);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.TEMP_MINIMUM))), WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.TEMP_MAXIMUM))), WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.TEMP_NIGHT))), WeatherDataDO.WEATHERDATA.TYPE_TEMP_NIGHT);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.TEMP_EVE))), WeatherDataDO.WEATHERDATA.TYPE_TEMP_EVE);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.TEMP_MORN))), WeatherDataDO.WEATHERDATA.TYPE_TEMP_MORN);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.PRESSURE))), WeatherDataDO.WEATHERDATA.TYPE_PRESSURE);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.SEA_LEVEL))), WeatherDataDO.WEATHERDATA.TYPE_SEA_LEVEL);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.GRN_LEVEL))), WeatherDataDO.WEATHERDATA.TYPE_GRN_LEVEL);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.HUMIDITY))), WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.WIND))), WeatherDataDO.WEATHERDATA.TYPE_WIND);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.DEG))), WeatherDataDO.WEATHERDATA.TYPE_DEG);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.CLOUDS))), WeatherDataDO.WEATHERDATA.TYPE_CLOUDS);
                            objWeatherDO.saveData(StringUtils.getDouble(cursor.getString(cursor.getColumnIndex(WeatherDataDO.RAIN))), WeatherDataDO.WEATHERDATA.TYPE_RAIN);

                            arrWeather.add(objWeatherDO);
                        } while (cursor.moveToNext());
                        adapterWeather.refresh(arrWeather);

                        setData(arrWeather);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        //adapterWeather.refresh(new ArrayList<WeatherDataDO>());
    }

    @Override
    public void onResume() {
        super.onResume();

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        String calTimeZone = tz.getDisplayName();

//        String timeZone = TimeZone.getDefault().getDisplayName();
        String timeZone = getActivity().getResources().getConfiguration().locale.getCountry();

        String timezoneID = TimeZone.getDefault().getID();

        String message = "calTimeZone: "+calTimeZone+"\n"+"timeZone: "+timeZone+"\n"+"timezoneID: "+timezoneID;
//        ((BaseActivity)getActivity()).showCustomDialog(getString(R.string.alert),message,getString(R.string.ok),null,getString(R.string.unable_to_fetch_your_current_location), CustomPopupType.DIALOG_ALERT,false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            ispermissionGranted = true;
            gpsUtills.connectGoogleApiClient();
            createGPSUtils();

            getCurrentLocation();
        }
    }

    @Override
    public void gotGpsValidationResponse(Object response, GPSErrorCode code)
    {
        if(code == GPSErrorCode.EC_GPS_PROVIDER_NOT_ENABLED) {
            isGpsEnabled = false;
            ((BaseActivity)getActivity()).showCustomDialog(getString(R.string.gpssettings),getString(R.string.gps_not_enabled),getString(R.string.settings),getString(R.string.cancel),getString(R.string.settings), CustomPopupType.DIALOG_ALERT,false);
        }
        else if(code == GPSErrorCode.EC_GPS_PROVIDER_ENABLED) {
            isGpsEnabled = true;
            gpsUtills.getCurrentLatLng();
        }
        else if(code == GPSErrorCode.EC_UNABLE_TO_FIND_LOCATION) {
            currentLatLng = (LatLng) response;

            ((BaseActivity)getActivity()).showCustomDialog(getString(R.string.alert),getString(R.string.unable_to_fetch_your_current_location),getString(R.string.ok),null,getString(R.string.unable_to_fetch_your_current_location), CustomPopupType.DIALOG_ALERT,false);
        }
        else if(code == GPSErrorCode.EC_LOCATION_FOUND) {
            currentLatLng = (LatLng) response;
            LogUtils.debugLog("GPSTrack", "Currrent latLng :"+currentLatLng.latitude+" \n"+currentLatLng.longitude);

            LocationDO objLocationDO = new LocationDO();
            objLocationDO.saveData(currentLatLng.latitude, LocationDO.LOCATIONDATA.TYPE_COORD_LAT);
            objLocationDO.saveData(currentLatLng.longitude, LocationDO.LOCATIONDATA.TYPE_COORD_LON);

            Bundle bundle = new Bundle();
            bundle.putSerializable(WeatherLoader.BUNDLE_WEATHERLOADER, objLocationDO);

            if(NetworkUtility.isConnectionAvailable(getActivity()))
                getActivity().getSupportLoaderManager().initLoader(ApplicationInstance.LOADER_FETCH_DAILY_FORECAST_API, bundle, this).forceLoad();
            else
                getActivity().getSupportLoaderManager().initLoader(ApplicationInstance.LOADER_FETCH_DAILY_FORECAST_DB, null, this).forceLoad();

            gpsUtills.stopLocationUpdates();
        }
        else if(code == GPSErrorCode.EC_CUSTOMER_LOCATION_IS_VALID) {
        }
        else if(code == GPSErrorCode.EC_CUSTOMER_lOCATION_IS_INVAILD) {
        }
        else if(code == GPSErrorCode.EC_DEVICE_CONFIGURED_PROPERLY) {
        }
    }

    private void getCurrentLocation(){
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                gpsUtills.getCurrentLatLng();
            }
        }, 2 * 1000);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(gpsUtills != null && ispermissionGranted){
            gpsUtills.connectGoogleApiClient();

            getCurrentLocation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(gpsUtills != null)
            gpsUtills.disConnectGoogleApiClient();
    }

    private void createGPSUtils(){
        gpsUtills.isGpsProviderEnabled();
    }

    private void setData(ArrayList<WeatherDataDO> arrWeather){
        if(arrWeather != null && arrWeather.size() > 0){
            for (WeatherDataDO objWeatherDO : arrWeather){
                String date = (String) objWeatherDO.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE);
                if(date.equalsIgnoreCase(CalendarUtils.getDateinPattern(CalendarUtils.DATE_FORMAT1))) {
                    String weather = "";
                    if(objWeatherDO.arrWeatheDescp != null && objWeatherDO.arrWeatheDescp.size() > 0)
                        weather = (String) objWeatherDO.arrWeatheDescp.get(0).getData(WeatherDescriptionDO.WEATHER_DESC_DATA.TYPE_DESCRIPTION);

                    tvWeatherCondition.setText(weather);
                    tvCurrentTemp.setText((double) objWeatherDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP)+"");

                    String dateToday = CalendarUtils.getDatefromTimeinMilliesPattern((long) objWeatherDO.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), AppConstants.DATE_PATTERN_WEEKNAME_FORMAT)+" ";
                    tvDay.setText(dateToday);
                    tvMaxTemp.setText((double) objWeatherDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX)+"");
                    tvMinTemp.setText((double) objWeatherDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN)+"");
                }
            }
        }
    }

    private void initializeControls(View rootView){

        tvLocation = (TextView) rootView.findViewById(R.id.tvLocation);
        tvWeatherCondition = (TextView) rootView.findViewById(R.id.tvWeatherCondition);

        tvCurrentTemp = (TextView) rootView.findViewById(R.id.tvCurrentTemp);
        tvDay = (TextView) rootView.findViewById(R.id.tvDay);
        tvMaxTemp = (TextView) rootView.findViewById(R.id.tvMaxTemp);
        tvMinTemp = (TextView) rootView.findViewById(R.id.tvMinTemp);

        rvTodayTemp = (RecyclerView) rootView.findViewById(R.id.rvTodayTemp);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvTodayTemp.setLayoutManager(layoutManager);

        rvWeather = (RecyclerView) rootView.findViewById(R.id.rvWeather);
        adapterWeather = new WeatherAdapter(getActivity(),new ArrayList<WeatherDataDO>());
        rvWeather.setAdapter(adapterWeather);
    }
}
