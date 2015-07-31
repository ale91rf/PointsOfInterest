package com.alefernandez.pointsofinterest.models;

import android.util.Log;

import com.alefernandez.pointsofinterest.utils.Constants;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alejandro on 30/07/2015.
 */
public class List {

    public ArrayList<PointOfInterest> mPointsOfInterest;

    public List(JSONObject listObject) {

        Log.d("OBJECT: ", listObject.toString());

        JSONArray list = null;
        try {
            list = listObject.getJSONArray(Constants.LIST);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        mPointsOfInterest = new ArrayList<PointOfInterest>();

        if(list != null){

            for(int i = 0; i < list.length(); i++){
                try {
                    JSONObject mChild = list.getJSONObject(i);

                    PointOfInterest mPoi = new PointOfInterest();
                    mPoi.setId(mChild.getInt(Constants.ID));
                    mPoi.setTitle(mChild.getString(Constants.TITLE));

                    String latLong = mChild.getString(Constants.GEOCOORDINATES);
                    String latLonSeparate[] = latLong.split(",");
                    LatLng mGeoCoor = new LatLng(Double.parseDouble(latLonSeparate[0]), Double.parseDouble(latLonSeparate[1]));

                    mPoi.setGeocoordinates(mGeoCoor);

                    mPointsOfInterest.add(mPoi);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public ArrayList<PointOfInterest> getmPointsOfInterest() {
        return mPointsOfInterest;
    }

    public void setmPointsOfInterest(ArrayList<PointOfInterest> mPointsOfInterest) {
        this.mPointsOfInterest = mPointsOfInterest;
    }
}
