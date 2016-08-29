package com.arpaul.sunshine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arpaul.sunshine.R;
import com.arpaul.sunshine.common.AppConstants;
import com.arpaul.sunshine.dataObjects.WeatherDataDO;
import com.arpaul.utilitieslib.CalendarUtils;

/**
 * Created by Aritra on 15-08-2016.
 */
public class WeatherDetailActivity extends BaseActivity {

    private View llWeatherDetailActivity;
    private TextView tvDayTitle, tvDayDate, tvDayTempMax, tvDayTempMin, tvDayHumidity, tvDayWind, tvDayPressure;
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
        tvDayTitle.setText(CalendarUtils.getDatefromTimeinMmiliesPattern((long) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), AppConstants.DATE_PATTERN_WEEKNAME_FORMAT));
        tvDayDate.setText(CalendarUtils.getDatefromTimeinMmiliesPattern((long) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_DATE_MILIS), AppConstants.DATE_PATTERN_WEATHER_DETAIL));
        tvDayTempMax.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MAX) + "");
        tvDayTempMin.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_TEMP_MIN) + "");

        tvDayHumidity.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_HUMIDITY) + "");
        tvDayWind.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_WIND) + "");
        tvDayPressure.setText((double) objWeatherDataDO.getData(WeatherDataDO.WEATHERDATA.TYPE_PRESSURE) + "");
    }

    private void initialiseControls(){
        tvDayTitle      = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayTitle);
        tvDayDate       = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayDate);
        tvDayTempMax    = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayTempMax);
        tvDayTempMin    = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayTempMin);
        tvDayHumidity   = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayHumidity);
        tvDayWind       = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayWind);
        tvDayPressure   = (TextView) llWeatherDetailActivity.findViewById(R.id.tvDayPressure);

        ivDayWeather    = (ImageView) llWeatherDetailActivity.findViewById(R.id.ivDayWeather);
    }
}
