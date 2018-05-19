package com.noumanch.selalf.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.noumanch.selalf.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        //Getting Intent
        Intent intent = getIntent();


        try {

            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            //Displaying payment details 
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus = (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);
        btn = (Button) findViewById(R.id.button2);
        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount + " USD");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ConfirmationActivity.this, Dashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
