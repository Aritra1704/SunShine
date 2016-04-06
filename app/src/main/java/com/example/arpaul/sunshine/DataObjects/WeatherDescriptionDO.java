package com.example.arpaul.sunshine.DataObjects;

/**
 * Created by ARPaul on 06-04-2016.
 */
public class WeatherDescriptionDO {
    private String main = "";
    private String description = "";
    private String icon = "";

    public void saveMain(String temp){
        main = temp;
    }
    public void saveDescription(String temp){
        description = temp;
    }
    public void saveIcon(String temp){
        icon = temp;
    }

    public static final String TAG_MAIN = "main";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_ICON = "icon";
}
