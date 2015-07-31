package com.alefernandez.pointsofinterest.bbdd;

import android.provider.BaseColumns;

/**
 * Created by alejandro on 31/07/2015.
 */
public class PoiContrat {

    //private so that it can not be instantiated
    private PoiContrat(){};

    //general constants
    public static final String BD_NAME = "poi_bbdd";
    public static final int BD_VERSION = 1;

    //Table Points of interest
    public static abstract class PointOfInterest implements BaseColumns{
        public static final String TABLE = "PointOfInterest";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String ADDRESS = "address";
        public static final String TRANSPORT = "transport";
        public static final String EMAIL = "email";
        public static final String GEOCOORDINATES = "geocoordinates";
        public static final String DESCRIPTION = "description";
        public static final String PHONE = "phone";

        public static final String[] ALL = new String[]{
                ID, TITLE, ADDRESS, TRANSPORT, EMAIL, GEOCOORDINATES, DESCRIPTION, PHONE
        };
    }
}
