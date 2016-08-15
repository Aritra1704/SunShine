package com.arpaul.sunshine.common;

import android.app.Application;

/**
 * Created by Aritra on 27-07-2016.
 */
public class ApplicationInstance extends Application {

    public static final int LOADER_TOUR_HEADER          = 1;
    public static final int LOADER_TOUR_DETAIL          = 2;
    public static final int LOADER_DELETE_TOUR          = 3;
    public static final int LOADER_CREATE_TOUR          = 4;
    public static final int LOADER_TOUR_DATE = 5;
    public static final int LOADER_UPDATE_TOUR          = 6;
    public static final int LOADER_FARM_LIST            = 7;
    public static final int LOADER_TOUR_LIST            = 8;
    public static final int LOADER_INSERT_FARM          = 9;
    public static final int LOADER_DELETE_TOUR_DETAIL   = 10;
    public static final int LOADER_FETCH_ADDRESS        = 11;
    public static final int LOADER_FETCH_LOCATION       = 12;
    public static final int LOADER_SAVE_LOCATION        = 13;
    public static final int LOADER_LOCATION_ID          = 14;
    public static final int LOADER_LOCATION_ALL         = 15;

    public static final int LOADER_FETCH_ALL_DATA   = 101;
    public static final int LOADER_DELETE_ALL_DATA  = 102;

//    public static final String REST_URL                 = "http://localhost:1234";
    private static final String REST_URL                = "http://dfb53180.ngrok.io";
    public static final String REST_API_URL             = REST_URL + "/api/v1/";
    public static final String IMAGE_URL                = REST_URL;

    public static final String URL_PARTICIPANT          = "participants/tour_participants";
    public static final String URL_PRODUCTS             = "products/participant_products";
    public static final String URL_TOURS                = "tour/current_tour";
    public static final String URL_FEATURES             = "features/participant_features";
    public static final String URL_NEARBY_PARTICIPANT   = "participants/nearby";

    public static final String LOCK_APP_DB              = "LOCK_APP_DB";
}
