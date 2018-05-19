package com.noumanch.selalf.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.model.User;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.StaticVariables;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateUser extends NavigationDrawerActivity {

    EditText firstName,lastName,emailTV,passwd,previousPasswod;

    Button updateButton;
    SpinKitView spinKitView;
    private AHBottomNavigation bottomNavigation;

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
        else{
            String languageToLoad = "eng"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        setContentView(R.layout.activity_update_user);
        spinKitView = findViewById(R.id.spin_kit);
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        passwd = findViewById(R.id.passwd);
        previousPasswod = findViewById(R.id.passwd_previous);
        emailTV = findViewById(R.id.email);
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(emailTV.getText().toString())||TextUtils.isEmpty(passwd.getText().toString())||
                        TextUtils.isEmpty(previousPasswod.getText().toString())||TextUtils.isEmpty(firstName.getText().toString())||
                        TextUtils.isEmpty(lastName.getText().toString())){
                    Toast.makeText(UpdateUser.this, "Fill data first", Toast.LENGTH_SHORT).show();

                }else
                    doTask("");
            }
        });
        setupBottomNavigation();

        putPreviousValues();
    }

    public void putPreviousValues(){
        User u = StaticVariables.getUser(UpdateUser.this);
        if (u!=null){
            firstName.setText(u.getFirstname()+"");
            lastName.setText(u.getLastname()+"");
            emailTV.setText(u.getEmail()+"");

        }
    }
    /** send the attendence*/
    public void doTask(String  json){
    /*    final ProgressDialog progressDialog = new ProgressDialog(UpdateUser.this) ;
        progressDialog.setMessage("updating attendence...");
        progressDialog.setIndeterminate(true);*/


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);

        spinKitView.setVisibility(View.VISIBLE);

        String url=getResources().getString(R.string.customer_update_url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        if (response  != null) {


                            try {
                                JSONObject array = new JSONObject(response);
                                boolean status = array.getBoolean("status");
                                if (status) {
                                    Toast.makeText(UpdateUser.this, "Info Updated", Toast.LENGTH_SHORT).show();
                                    if (spinKitView != null)
                                        spinKitView.setVisibility(View.GONE);
                                    finish();

                                } else {
                                    Toast.makeText(UpdateUser.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }
                                Log.wtf("Test", "onResponse: " + array.toString());
                                if (spinKitView != null)
                                    spinKitView.setVisibility(View.GONE);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (spinKitView != null)
                                    spinKitView.setVisibility(View.GONE);

                                Toast.makeText(UpdateUser.this, "Error due to JSON ", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(UpdateUser.this, "Fail to update info", Toast.LENGTH_SHORT).show();
                            if (spinKitView != null)
                                spinKitView.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (spinKitView != null)
                            spinKitView.setVisibility(View.GONE);
                        Toast.makeText(UpdateUser.this,"Internet is Not working correctly",Toast.LENGTH_SHORT).show();

                    }
                }){
            /*@Override
            public String getBodyContentType() {
                return "application/xml; charset=" +
                        getParamsEncoding();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {*/
                /*String postData = *//*"<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +*//*
                        "<prestashop xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                                "<customer>\n" +
                                "<id>" + customerId +
                                "<passwd>" + passwd.getText().toString() +
                                "</passwd>\n" +
                                "<lastname>" + lastname.getText().toString() +
                                "</lastname>\n" +
                                "<firstname>" + firstname.getText().toString() +
                                "</firstname>\n" +
                                "<email>" + email.getText().toString() +
                                "</email>\n" +
                                "</customer>\n" +
                                "</prestashop>";
                Log.wtf("TEst", "getBody: "+postData );
                try {
                    return postData == null ? null :
                            postData.getBytes(getParamsEncoding());
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }
            *//*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*//**/

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String > value = new HashMap<>();
                String em = emailTV.getText().toString();
                em        = em.replace(" ","");
                value.put("email",em);
                value.put("password",passwd.getText().toString());
                value.put("previous_password",previousPasswod.getText().toString());
                value.put("lastname",lastName.getText().toString());
                value.put("firstname",firstName.getText().toString());
                value.put("id", StaticVariables.getUserId(UpdateUser.this));
                Log.wtf("TEST", "getParams: "+value.toString() );
                return value;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return  ;
    }



    private void setupBottomNavigation() {
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);


//        bottomNavigation.setItemIconTintList(null);
// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.eee, android.R.color.black);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.basket_bottm, android.R.color.black);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.cart_bottom, android.R.color.black);
        //AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.person, android.R.color.black);
        bottomNavigation.removeAllItems();
// Add items
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        //bottomNavigation.addItem(item4);
// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        bottomNavigation.setAccentColor(getColor(R.color.colorPrimary));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);

// Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setInactiveColor(Color.parseColor("#E30614"));
        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
// Add or remove notification for each item
        if (new Database(UpdateUser.this).getProducts()!=null){
            int v  =new Database(UpdateUser.this).getProducts().size();
            if (v!=0){

                bottomNavigation.setNotification(""+v, 2);
            }
        }

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        Intent i1 = new Intent();
                        i1.putExtra("goTo","home");
                        setResult(RESULT_OK,i1);
                        finish();
                        break;
                    case 1:
                        Intent i4 = new Intent();
                        i4.putExtra("goTo","profile");
                        setResult(RESULT_OK,i4);
                        finish();
                        break;
                    case 2:
                        Intent iii = new Intent();
                        iii.putExtra("goTo","cart");
                        setResult(RESULT_OK,iii);
                        finish();
                        break;
                    case 3:
                        Intent ii = new Intent();
                        ii.putExtra("goTo","basket");
                        setResult(RESULT_OK,ii);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position

            }
        });


    }

}
