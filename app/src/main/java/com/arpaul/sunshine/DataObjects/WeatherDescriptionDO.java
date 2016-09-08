package com.arpaul.sunshine.dataObjects;

import java.io.Serializable;

/**
 * Created by ARPaul on 06-04-2016.
 */
public class WeatherDescriptionDO extends BaseDO {
    private String main = "";
    private String description = "";
    private String icon = "";

    public void saveData(Object data, WEATHER_DESC_DATA type){
        switch (type){
            case TYPE_DESCRIPTION:
                description = (String) data;
                break;
            case TYPE_ICON:
                icon = (String) data;
                break;
            case TYPE_MAIN:
                main = (String)data;
                break;
        }
    }

    public Object getData(WEATHER_DESC_DATA type){
        switch (type){
            case TYPE_DESCRIPTION:
                return description;
            case TYPE_ICON:
                return icon;
            case TYPE_MAIN:
            default:
                return main;
        }
    }

    public static final String TAG_MAIN = "main";
    public static final String TAG_DESCRIPTION = "description";
    public static final String TAG_ICON = "icon";

    public enum WEATHER_DESC_DATA {
        TYPE_DESCRIPTION,
        TYPE_ICON,
        TYPE_MAIN
    }

    public static final String WEATHER_DESCRIP_ID       = "WEATHER_DESCRIP_ID";
    public static final String WEATHER_ID               = "WEATHER_ID";
    public static final String MAIN                     = "MAIN";
    public static final String DESCRIPTION              = "DESCRIPTION";
    public static final String ICON                     = "ICON";
}
