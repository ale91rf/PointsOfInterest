package com.alefernandez.pointsofinterest.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alejandro on 31/07/2015.
 */
public class Helper extends SQLiteOpenHelper {

    public Helper(Context context){
        super(context,PoiContrat.BD_NAME, null, PoiContrat.BD_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL Table creation
        db.execSQL(TABLE_POI_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //future versions
    }

    private static final String TABLE_POI_CREATE = "create table " +
            PoiContrat.PointOfInterest.TABLE + "(" + PoiContrat.PointOfInterest.ID + " integer, " +
            PoiContrat.PointOfInterest.TITLE + " text, " + PoiContrat.PointOfInterest.ADDRESS +
            " text, " + PoiContrat.PointOfInterest.TRANSPORT + " text, " +
            PoiContrat.PointOfInterest.EMAIL + " text, " + PoiContrat.PointOfInterest.GEOCOORDINATES +
            " text, " + PoiContrat.PointOfInterest.DESCRIPTION + " text, " +
            PoiContrat.PointOfInterest.PHONE + " text)";
}
