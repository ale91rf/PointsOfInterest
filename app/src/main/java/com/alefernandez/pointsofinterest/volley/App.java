package com.alefernandez.pointsofinterest.volley;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by alejandro on 19/06/2015.
 */

//utilization of the Singleton pattern
public class App extends Application {

    private static RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
    }

    // Return the RequestQueue
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue uninitialized");
        }
    }

}
