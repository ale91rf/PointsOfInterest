package com.alefernandez.pointsofinterest.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alefernandez.pointsofinterest.R;
import com.alefernandez.pointsofinterest.bbdd.DAO;
import com.alefernandez.pointsofinterest.models.PointOfInterest;
import com.alefernandez.pointsofinterest.utils.Constants;
import com.alefernandez.pointsofinterest.utils.Internet;
import com.alefernandez.pointsofinterest.volley.App;
import com.alefernandez.pointsofinterest.volley.GsonArrayRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 31/07/2015.
 */
public class DetailPoi extends Activity {

    private RequestQueue mRequestQueue;
    private Internet mConnection;
    private int id;
    private DAO mDao;
    private PointOfInterest mPointOfInterestBD;
    private boolean mStored;
    private TextView lblTitle;
    private TextView lblAddress;
    private TextView lblPhone;
    private TextView lblTransport;
    private TextView lblDescription;
    private Button btnEmail;
    private PointOfInterest mPointOfInterest;
    private ProgressBar pbLoading;
    private Intent mIntent;
    private Bundle mBundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        getActionBar().setDisplayHomeAsUpEnabled(true);

        mRequestQueue = App.getRequestQueue();
        mConnection = new Internet(this);

        id = getIntent().getExtras().getInt(Constants.ID);

        getView();

        checkBD(id);
    }


    private void getView() {
        lblTitle = (TextView) findViewById(R.id.lbl_title);
        lblAddress =  (TextView) findViewById(R.id.lbl_address);
        lblPhone = (TextView) findViewById(R.id.lbl_phone);
        lblTransport = (TextView) findViewById(R.id.lbl_transport);
        lblDescription = (TextView) findViewById(R.id.lbl_description);

        btnEmail = (Button) findViewById(R.id.btn_email);

        pbLoading = (ProgressBar) findViewById(R.id.pb_loading);
    }

    private void checkBD(int id) {
        pbLoading.setVisibility(View.VISIBLE);
        mDao = new DAO(getApplicationContext()).open();
        mPointOfInterestBD = mDao.queryGetPoi(id);
        mDao.close();

        if(mPointOfInterestBD != null){
            mStored = true;
            //If it was stored, paint it.
            paintView(mPointOfInterestBD);
        }else{
            //If it was not stored, we send the request
            mStored = false;

            if(mConnection != null & mConnection.isConnectionAvailable()){
                sendRequest(id);
            }else{
                Constants.showToast(getApplicationContext(), getString(R.string.cannot));
                pbLoading.setVisibility(View.GONE);
            }
        }
    }

    public void sendRequest(int id){

        Response.Listener<PointOfInterest> listener = new Response.Listener<PointOfInterest>() {

            @Override
            public void onResponse(PointOfInterest mPoi) {
                paintView(mPoi);
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR VOLLEY", error.toString());
                pbLoading.setVisibility(View.GONE);
            }
        };

        // With Gson
        Gson gson = new Gson();
        Type tipo = new TypeToken<PointOfInterest>() {
        }.getType();



        GsonArrayRequest<PointOfInterest> request = new GsonArrayRequest<PointOfInterest>(
                Request.Method.GET, Constants.POINTS_URL+"/"+String.valueOf(id), tipo, listener, errorListener,
                gson);

        request.setTag(Constants.TAG_REQUESTS);
        mRequestQueue.add(request);

    }

    private void paintView(PointOfInterest mPoi) {
        pbLoading.setVisibility(View.GONE);
        if(mPoi != null){
            mPointOfInterest = mPoi;

            if(!mPointOfInterest.getTitle().toString().equals("null")){
                lblTitle.setText(mPointOfInterest.getTitle().toString());
            }
            lblTitle.setVisibility(View.VISIBLE);


            if(!mPointOfInterest.getAddress().toString().equals("null")){
                lblAddress.setText(mPointOfInterest.getAddress().toString());
            }
            lblAddress.setVisibility(View.VISIBLE);

            if(!mPointOfInterest.getPhone().toString().equals("null")){
                lblPhone.setText(mPointOfInterest.getPhone().toString());
            }
            lblPhone.setVisibility(View.VISIBLE);

            if(!mPointOfInterest.getTransport().toString().equals("null")){
                lblTransport.setText(mPointOfInterest.getTransport().toString());
            }
            lblTransport.setVisibility(View.VISIBLE);

            if(!mPointOfInterest.getDescription().toString().equals("null")){
                lblDescription.setText(mPointOfInterest.getDescription().toString());
            }
            lblDescription.setVisibility(View.VISIBLE);


                btnEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mPointOfInterest.getEmail().toString().equals("null")) {
                            openEmail();
                        }else{
                            Constants.showToast(getApplicationContext(), getString(R.string.no_email));
                        }
                    }
                });

            //We save the data in the DB
            if(!mStored){
                if(mDao == null){
                    mDao = new DAO(getApplicationContext());
                }
                mDao.open();

                mDao.insertPoi(mPointOfInterest);

                mDao.close();
            }

        }
    }

    private void openEmail() {
        mIntent = new Intent(getApplicationContext(), EmailActivity.class);
        mBundle = new Bundle();
        mBundle.putString(Constants.TAG_EMAIL, mPointOfInterest.getEmail().toString());
        mIntent.putExtras(mBundle);
        startActivity(mIntent);

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(Constants.TAG_REQUESTS);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
