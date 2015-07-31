package com.alefernandez.pointsofinterest.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by alejandro on 30/07/2015.
 */
public class Constants {
    public static final String POINTS_URL = "http://t21services.herokuapp.com/points";
    public static final String TAG_REQUESTS = "requests";
    public static final String LIST = "list";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String GEOCOORDINATES = "geocoordinates";
    public static final String TAG_EMAIL = "email";


    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
