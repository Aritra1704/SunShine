package com.arpaul.sunshine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arpaul.sunshine.R;
import com.arpaul.sunshine.common.AppConstants;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.sunshine.dataObjects.WeatherDescriptionDO;
import com.arpaul.utilitieslib.CalendarUtils;
import com.arpaul.utilitieslib.StringUtils;

/**
 * Created by Aritra on 15-08-2016.
 */
public class WeatherDetailActivity extends BaseActivity {

    private View llWeatherDetailActivity;
    private TextView tvDayTitle, tvDayDate, tvDayTempMax, tvDayTempMin, tvDayHumidity, tvDayWind, tvDayPressure, tvDayWeather;
    private ImageView ivDayWeather;

    private WeatherDataDO objWeatherDataDO;

    @Override
    public void initialize(Bundle savedInstanceState) {
        llWeatherDetailActivity = baseInflater.inflate(R.layout.activity_weather_detail,null);
        llBody.addView(llWeatherDetailActivity, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if(getIntent().hasExtra("WEATHER_DETAIL"))
            objWeatherDataDO = (WeatherDataDO) getIntent().getExtras().get("WEATHER_DETAIL");

        initialiseControls();

        bindControls();
    }

    private void bindControls(){
        tvDayTitle.setText(CalendarUtils.getDatefromTimeinMilliesPattern((long) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), AppConstants.DATE_PATTERN_WEEKNAME_FORMAT));
        tvDayDate.setText(CalendarUtils.getDatefromTimeinMilliesPattern((long) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), AppConstants.DATE_PATTERN_WEATHER_DETAIL));
        tvDayTempMax.setText(degreeFormat.format((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX)) + (char) 0x00B0);
        tvDayTempMin.setText(degreeFormat.format((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN)) + (char) 0x00B0);

        tvDayHumidity.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY) + "");
        tvDayWind.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_WIND) + "");
        tvDayPressure.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_PRESSURE) + "");

        if(objWeatherDataDO.arrWeatheDescp != null && objWeatherDataDO.arrWeatheDescp.size() > 0){
            String weather = (String) objWeatherDataDO.arrWeatheDescp.get(0).getData(WeatherDescriptionDO.WEATHER_DESC_DATA.TYPE_MAIN);
            tvDayWeather.setText(weather);

            String icon = (String) objWeatherDataDO.arrWeatheDescp.get(0).getData(WeatherDescriptionDO.WEATHER_DESC_DATA.TYPE_ICON);
            ivDayWeather.setImageResource(AppConstants.getArtResourceForWeatherCondition(StringUtils.getInt(icon)));
        }
    }

    private void initialiseControls(){
        tvDayTitle      = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayTitle);
        tvDayDate       = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayDate);
        tvDayTempMax    = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayTempMax);
        tvDayTempMin    = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayTempMin);
        tvDayHumidity   = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayHumidity);
        tvDayWind       = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayWind);
        tvDayPressure   = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayPressure);
        tvDayWeather    = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayWeather);

        ivDayWeather    = (ImageView) llWeatherDetailActivity.findViewById(R.id.ivDayWeather);
    }
}
