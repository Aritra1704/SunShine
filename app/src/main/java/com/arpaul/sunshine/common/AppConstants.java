package com.arpaul.sunshine.common;


import com.arpaul.sunshine.R;

/**
 * Created by Aritra on 5/17/2016.
 */
public class AppConstants {

    public static final String ACTION_CLOSE_FARMSEARCH  = "ACTION_CLOSE_FARMSEARCH";
    public static final String ACTION_TOUR_REFRESH      = "ACTION_TOUR_REFRESH";
    public static final String ACTION_TOUR_UPDATE       = "ACTION_TOUR_UPDATE";

    public final static String BUNDLE_CREATE_TOUR       = "BUNDLE_CREATE_TOUR";

    public final static String EXTERNAL_FOLDER          = "/.SunShine/";

    public final static String TEXT_NULL                = "null";

    public final static String DAY_PATTERN_WEATHER_LIST = "EEEE, MMM dd";
    public final static String DATE_PATTERN_WEATHER_DETAIL = "MMM dd";
    public final static String DATE_PATTERN_WEEKNAME_FORMAT = "EEEE";


    /**
     * Helper method to provide the art resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding icon. -1 if no relation is found.
     */
    public static int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId < 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }
}
