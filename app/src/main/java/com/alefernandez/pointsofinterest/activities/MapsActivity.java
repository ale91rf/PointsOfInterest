package com.alefernandez.pointsofinterest.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.alefernandez.pointsofinterest.R;
import com.alefernandez.pointsofinterest.bbdd.DAO;
import com.alefernandez.pointsofinterest.models.PointOfInterest;
import com.alefernandez.pointsofinterest.utils.Constants;
import com.alefernandez.pointsofinterest.utils.Internet;
import com.alefernandez.pointsofinterest.volley.App;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private RequestQueue mRequestQueue;
    private Internet mConnection;
    private ArrayList<PointOfInterest> mPointsOfInterest;
    private Intent i;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mRequestQueue = App.getRequestQueue();
        mConnection = new Internet(this);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);

                // Get LocationManager object from System Service LOCATION_SERVICE
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                // Create a criteria object to retrieve provider
                Criteria criteria = new Criteria();

                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                // Get Current Location
                Location myLocation = locationManager.getLastKnownLocation(provider);

                // set map type
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                // Get latitude of the current location
                double latitude = myLocation.getLatitude();

                // Get longitude of the current location
                double longitude = myLocation.getLongitude();

                // Create a LatLng object for the current location
                LatLng latLng = new LatLng(latitude, longitude);
                // Show the current location in Google Map
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                // Zoom in the Google Map
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                if(mConnection != null & mConnection.isConnectionAvailable()){
                    sendRequest();
                }else{
                    setUpMap();
                }
            }
        }
    }



    private void sendRequest() {
        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                mPointsOfInterest = parseJSON(response);


                setUpMap();

            }

        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR VOLLEY", error.toString());
            }
        };


        JsonObjectRequest request = new JsonObjectRequest(Constants.POINTS_URL, listener, errorListener);

        request.setTag(Constants.TAG_REQUESTS);
        // the request is added to the RequestQueue.
        mRequestQueue.add(request);


    }

    //Without Gson

    private ArrayList<PointOfInterest> parseJSON(JSONObject jsonObject){
        ArrayList<PointOfInterest> pointOfInterestList = new ArrayList<>();

        JSONArray list = null;

        try {
            list = jsonObject.getJSONArray(Constants.LIST);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(list != null){

            for(int i = 0; i < list.length(); i++){
                try {
                    JSONObject mChild = list.getJSONObject(i);

                    PointOfInterest mPoi = new PointOfInterest();
                    mPoi.setId(mChild.getInt(Constants.ID));
                    mPoi.setTitle(mChild.getString(Constants.TITLE));
                    mPoi.setGeocoordinates(mChild.getString(Constants.GEOCOORDINATES));

                    pointOfInterestList.add(mPoi);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }



        return  pointOfInterestList;
    }


    private void setUpMap() {

        if(mPointsOfInterest != null){
            for(PointOfInterest point : mPointsOfInterest){
                mMap.addMarker(new MarkerOptions().position(point.getGeocoordinatesReal())
                        .title(point.getTitle()).snippet(String.valueOf(point.getId())));

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        i = new Intent(getApplicationContext(), DetailPoi.class);
                        i.putExtra(Constants.ID, Integer.valueOf(marker.getSnippet()));
                        startActivity(i);
                    }
                });

            }
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(Constants.TAG_REQUESTS);
        }
    }
}
