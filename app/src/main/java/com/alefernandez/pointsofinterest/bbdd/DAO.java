package com.alefernandez.pointsofinterest.bbdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.alefernandez.pointsofinterest.models.PointOfInterest;

import java.util.ArrayList;

/**
 * Created by alejandro on 31/07/2015.
 */
public class DAO {

    private Context mContext;
    private Helper mHelper;
    private SQLiteDatabase mBd;

    public DAO(Context context)	{
        this.mContext = context;
        mHelper = new Helper(mContext);
    }

    public DAO open() throws SQLException {
        mBd = mHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mHelper.close();
    }


    public PointOfInterest queryGetPoi(int id){
        Cursor cursor = mBd.query(true, PoiContrat.PointOfInterest.TABLE, PoiContrat.PointOfInterest.ALL,
                PoiContrat.PointOfInterest.ID + " = " + id, null, null, null, null, null);


        if(cursor.moveToFirst()){
            return cursorToPoi(cursor);
        }else{
            return null;
        }
    }


    private Cursor queryAllPoi(){
        return mBd.query(PoiContrat.PointOfInterest.TABLE, PoiContrat.PointOfInterest.ALL, null, null, null, null, null);
    }

    public ArrayList<PointOfInterest> getAllPois(){
        ArrayList<PointOfInterest> list = new ArrayList<PointOfInterest>();

        Cursor cursor = this.queryAllPoi();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            PointOfInterest mPoi = cursorToPoi(cursor);
            list.add(mPoi);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private PointOfInterest cursorToPoi(Cursor cursor){
        PointOfInterest mPoi = new PointOfInterest();

        mPoi.setId(cursor.getInt(0));
        mPoi.setTitle(cursor.getString(1));
        mPoi.setAddress(cursor.getString(2));
        mPoi.setTransport(cursor.getString(3));
        mPoi.setEmail(cursor.getString(4));
        mPoi.setGeocoordinates(cursor.getString(5));
        mPoi.setDescription(cursor.getString(6));
        mPoi.setPhone(cursor.getString(7));


        return mPoi;
    }

    public void insertPoi(PointOfInterest mPoi){
        ContentValues values = new ContentValues();

        values.put(PoiContrat.PointOfInterest.ID, mPoi.getId());
        values.put(PoiContrat.PointOfInterest.TITLE, mPoi.getTitle());
        values.put(PoiContrat.PointOfInterest.ADDRESS, mPoi.getAddress());
        values.put(PoiContrat.PointOfInterest.TRANSPORT, mPoi.getTransport());
        values.put(PoiContrat.PointOfInterest.EMAIL, mPoi.getEmail());
        values.put(PoiContrat.PointOfInterest.GEOCOORDINATES, mPoi.getGeocoordinates());
        values.put(PoiContrat.PointOfInterest.DESCRIPTION, mPoi.getDescription());
        values.put(PoiContrat.PointOfInterest.PHONE, mPoi.getPhone());

        mBd.insert(PoiContrat.PointOfInterest.TABLE, null, values);

    }


}
