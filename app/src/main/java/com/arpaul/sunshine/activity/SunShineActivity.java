package com.arpaul.sunshine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.arpaul.sunshine.fragments.ForecastFragment;
import com.arpaul.sunshine.R;

public class SunShineActivity extends BaseActivity {

    private View llSunShineActivity;

    @Override
    public void initialize(Bundle savedInstanceState) {
        llSunShineActivity = baseInflater.inflate(R.layout.activity_sun_shine,null);
        llBody.addView(llSunShineActivity, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }

        //ForecastFragment forecastFragment =  ((ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast));
        //getSupportFragmentManager().beginTransaction().attach(forecastFragment).commit();
    }
}
