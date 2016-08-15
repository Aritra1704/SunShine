package com.arpaul.sunshine.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Aritra on 5/17/2016.
 */
public class AppPreference {
    private SharedPreferences preferences;
    private SharedPreferences.Editor edit;

    public static final String USERID                  =	"USERID";
    public static final String USERNAME                =	"USERNAME";
    public static final String PASSWORD 			   =	"PASSWORD";
    public static final String PREF_LOC 			   =	"PREF_LOC";

    public static final String ALL_DATA_FETCH 		   =	"ALL_DATA_FETCH";

    public AppPreference(Context context)
    {
        preferences		=	PreferenceManager.getDefaultSharedPreferences(context);
        edit			=	preferences.edit();
    }

    public void saveStringInPreference(String strKey,String strValue)
    {
        edit.putString(strKey, strValue);
        commitPreference();
    }

    public void removeFromPreference(String strKey)
    {
        edit.remove(strKey);
    }

    public void commitPreference()
    {
        edit.commit();
    }

    public String getStringFromPreference(String strKey,String defaultValue )
    {
        return preferences.getString(strKey, defaultValue);
    }
}
