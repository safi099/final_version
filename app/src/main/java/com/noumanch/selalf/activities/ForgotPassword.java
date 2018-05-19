package com.noumanch.selalf.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.utils.StaticVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ForgotPassword extends AppCompatActivity {


    EditText email;
    Button forgotPassButton;
    SpinKitView spinKitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!StaticVariables.language) {


            String languageToLoad = "ar"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

        }
        setContentView(R.layout.activity_forgot_password);

        spinKitView = findViewById(R.id.spin_kit);
        email = findViewById(R.id.email);
        forgotPassButton = findViewById(R.id.signin_btn);

        forgotPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(email.getText().toString())){

                    doTask(email.getText().toString());
                }else{
                    Toast.makeText(ForgotPassword.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /** send the attendence*/
    public void doTask(String  json){
/*        final ProgressDialog progressDialog = new ProgressDialog(ForgotPassword.this) ;
        progressDialog.setMessage("Resetting password...");
        progressDialog.setIndeterminate(true);*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);

        //progressDialog.show();
        spinKitView.setVisibility(View.VISIBLE);

        String url=getResources().getString(R.string.fogot_pass_url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        try {
                            JSONObject array = new JSONObject(response);
                            boolean status = array.getBoolean("status");
                            if (status){
                                Toast.makeText(ForgotPassword.this, "New Password send to your email", Toast.LENGTH_SHORT).show();
                                if (spinKitView!=null)
                                    spinKitView.setVisibility(View.GONE);
                                 finish();
                            }else{
                                Toast.makeText(ForgotPassword.this, "Fail to reset password", Toast.LENGTH_SHORT).show();
                            }
                            Log.wtf("Test", "onResponse: "+array.toString());
                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);

                            Toast.makeText(ForgotPassword.this, "Error due to JSON ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKitView.setVisibility(View.GONE);
                        Toast.makeText(ForgotPassword.this,"Internet is Not working correctly",Toast.LENGTH_SHORT).show();

                    }
                }){
            /*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*/

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String > value = new HashMap<>();
                Log.wtf("TEST", "getParams: "+email.getText().toString() );
                value.put("email",email.getText().toString());
                //value.put("password",password.getText().toString());
                return value;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return  ;
    }

}
