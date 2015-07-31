package com.alefernandez.pointsofinterest.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alejandro on 19/06/2015.
 */
public class Internet {

    public Activity activity;

    public Internet(Activity activity){
        this.activity = activity;
    }


    // Return if there is connexion to internet
    public boolean isConnectionAvailable() {
        ConnectivityManager gestorConectividad = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infoRed = gestorConectividad.getActiveNetworkInfo();

        return (infoRed != null && infoRed.isConnected());
    }

}