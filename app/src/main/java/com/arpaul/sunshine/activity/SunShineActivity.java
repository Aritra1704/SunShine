package com.arpaul.sunshine.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.arpaul.sunshine.fragments.ForecastFragment;
import com.arpaul.sunshine.R;

public class SunShineActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_shine);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }

        //ForecastFragment forecastFragment =  ((ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast));
        //getSupportFragmentManager().beginTransaction().attach(forecastFragment).commit();
    }
}
