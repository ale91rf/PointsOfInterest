package com.alefernandez.pointsofinterest.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alefernandez.pointsofinterest.R;
import com.alefernandez.pointsofinterest.utils.Constants;

public class EmailActivity extends Activity implements View.OnClickListener{

    private Bundle mBundle;
    private String mEmail;
    private TextView lblEmail;
    private EditText txtBody;
    private EditText txtSubject;
    private Button btnSend;
    private String mBody;
    private String mSubject;
    private Intent mEmailIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mBundle = getIntent().getExtras();

        if(mBundle != null){
            mEmail = mBundle.getString(Constants.TAG_EMAIL);
        }

        mBody = "";
        mSubject = "";

        getView();
    }

    private void getView() {
        lblEmail = (TextView) findViewById(R.id.lbl_email);
        lblEmail.setText(mEmail);
        txtSubject = (EditText) findViewById(R.id.txt_subject);
        txtBody = (EditText) findViewById(R.id.txt_body);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_send){
            if(!txtBody.getText().toString().equals("")){
                if(!txtSubject.getText().toString().equals("")){
                    mSubject = txtSubject.getText().toString();
                }
                mBody = txtBody.getText().toString();
                sendEmail();
            }else{
                showToast(getString(R.string.you_need));
            }
        }
    }

    private void sendEmail() {

        mEmailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", mEmail, null));
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, mSubject);
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, mBody);
        startActivity(Intent.createChooser(mEmailIntent, "Send email..."));

    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
