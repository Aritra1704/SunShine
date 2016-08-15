package com.arpaul.sunshine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

import com.arpaul.sunshine.R;
import com.arpaul.utilitieslib.UnCaughtException;

/**
 * Created by Aritra on 15-08-2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public LayoutInflater baseInflater;
    public LinearLayout llBody;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(BaseActivity.this,"aritra1704@gmail.com",getString(R.string.app_name)));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_base);

        initialiseBaseControls();

        bindBaseControls();

        initialize(savedInstanceState);
    }

    public abstract void initialize(Bundle savedInstanceState);

    private void bindBaseControls(){

    }

    private void initialiseBaseControls(){
        baseInflater            = 	this.getLayoutInflater();
        llBody                  =   (LinearLayout) findViewById(R.id.llBody);
    }
}
