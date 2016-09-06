package com.arpaul.sunshine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;

import com.arpaul.customalertlibrary.popups.statingDialog.CustomPopup;
import com.arpaul.customalertlibrary.popups.statingDialog.CustomPopupType;
import com.arpaul.sunshine.common.ApplicationInstance;
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            Fragment frg = null;
            frg = getSupportFragmentManager().findFragmentById(R.id.container);
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commit();
        }
    }

    @Override
    public void dialogYesClick(String from) {
        super.dialogYesClick(from);
        if(from.equalsIgnoreCase(getString(R.string.settings))){
            Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
            hideCustomDialog();
        }
    }
}
