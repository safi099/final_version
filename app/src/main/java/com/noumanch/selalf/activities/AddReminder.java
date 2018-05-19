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
import android.widget.ArrayAdapter;
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
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.model.GeneralHash;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.StaticVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddReminder extends AppCompatActivity {

    EditText lastname, firstname, email, passwd;
    Button signup;
    ArrayList<GeneralHash> spinnerItems = new ArrayList<>();
    ArrayList<String>  ocaionNames = new ArrayList<>();
    ArrayList<GeneralHash> relationsItems = new ArrayList<>();
    ArrayList<String>  relationNames= new ArrayList<>();
    ArrayAdapter<String> occasionsAdapter,dayAdapter,monthAdapter,yearAdapter, relationAdapter ;
    Spinner occasionSpinner,yearSpinner,monthSpinner,daySpinner,relationSpinner;
     String[] months = new String[]{
        "January","February","March","April","June","July","August","September","October","November","December"

    };
    String[] year = new String[]{

            "2017","2018","2019","2020"

    };
    String[] days = new String[]{
            "1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"
            ,"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"
            ,"31"
    };
    String customerId;
    private SpinKitView spinKit;
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
        setContentView(R.layout.activity_add_reminder);


        //customerId = getIntent().getStringExtra("customerId");
        spinKit = findViewById(R.id.spin_kit);
        occasionSpinner = findViewById(R.id.occasion_spinner);
        relationSpinner = findViewById(R.id.relation_spinner);
        yearSpinner = findViewById(R.id.year);
        monthSpinner = findViewById(R.id.month);
        daySpinner = findViewById(R.id.date);

        dayAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, days);
        monthAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, year);
        yearAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, months);
        monthSpinner.setAdapter(monthAdapter);
        yearSpinner.setAdapter(yearAdapter);
        daySpinner.setAdapter(dayAdapter);
         occasionsAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ocaionNames);
        relationAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, relationNames);
        occasionSpinner.setAdapter(occasionsAdapter);
        relationSpinner.setAdapter(relationAdapter);

        firstname = findViewById(R.id.first_name);

        signup = findViewById(R.id.add_reminder_btn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(firstname.getText().toString())){
                    doTask("");
                }else {
                    Toast.makeText(AddReminder.this, "Enter the Data ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getOccasions("");
        getRelations("");
        setupBottomNavigation();
    }

    /**
     * send the attendence
     */
    /*public void doTask(final String json) {
        final ProgressDialog progressDialog = new ProgressDialog(AddReminder.this);
        progressDialog.setMessage("Adding new reminder...");
        progressDialog.setIndeterminate(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            progressDialog.setIndeterminateDrawable(drawable);
        }
        progressDialog.setCancelable(false);

        progressDialog.show();

        String url = getResources().getString(R.string.add_reminder_url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", "" + response);

                        try {

                            JSONObject status = new JSONObject(response);
                            if (status.getBoolean("status")){
                                Toast.makeText(AddReminder.this, "Added Sucessfully ", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(AddReminder.this, "Fail to add Reminder", Toast.LENGTH_SHORT).show();
                            }



                            if (progressDialog != null)
                                progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            Toast.makeText(AddReminder.this, "Fail to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(AddReminder.this, "Internet is Not working correctly", Toast.LENGTH_SHORT).show();

                    }
                }) {
            *//*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*//*





            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String > value = new HashMap<>();
                Log.wtf("", "getParams: "+firstname.getText().toString()+StaticVariables.getUserId(AddReminder.this)+getOccassion()+getMonth()+
                getYear()+getDay()+getRelation());
                value.put("name",firstname.getText().toString());
                value.put("customerid", StaticVariables.getUserId(AddReminder.this));
                value.put("occassionid",getOccassion());
                value.put("monthid",getMonth());
                value.put("dayid",getDay());
                value.put("yearid",getYear());
                value.put("relationid",getRelation());

                return value;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return;

        }*/

    public void getOccasions(final String json) {


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        ///progressDialog.setCancelable(false);

        spinKit.setVisibility(View.VISIBLE);

        String url = getResources().getString(R.string.occasions_list_url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", "" + response);

                        try {

                            //JSONObject array = new JSONObject(jsonObject);
                            //boolean status = array.getBoolean("status");
                            //JSONObject json = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject dupm = jsonArray.getJSONObject(i);
                                GeneralHash d = new GeneralHash(dupm.getString("id"),dupm.getString("name"));
                                spinnerItems.add(d);
                                ocaionNames.add(dupm.getString("name"));
                            }
                            occasionsAdapter.notifyDataSetChanged();


                            if (spinKit!= null)
                                spinKit.setVisibility(View.GONE);

                            if (spinKit!= null)
                                spinKit.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (spinKit!= null)
                                spinKit.setVisibility(View.GONE);
                            Toast.makeText(AddReminder.this, "Fail to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKit.setVisibility(View.GONE);
                        Toast.makeText(AddReminder.this, "Internet is Not working correctly", Toast.LENGTH_SHORT).show();

                    }
                }) ;
            /*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*/


            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String > value = new HashMap<>();
                //value.put("email",email.getText().toString());
                //value.put("password",password.getText().toString());
                return value;
            }*/


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return;

    }


    public void getRelations(final String json) {
/*        final ProgressDialog progressDialog = new ProgressDialog(AddReminder.this);
        progressDialog.setMessage("Creating New User...");
        progressDialog.setIndeterminate(true);*/

        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            progressDialog.setIndeterminateDrawable(drawable);
        }*/
        //progressDialog.setCancelable(false);

        spinKit.setVisibility(View.VISIBLE);

        String url = getResources().getString(R.string.relations_list_url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", "" + response);

                        try {

                            //JSONObject array = new JSONObject(jsonObject);
                            //boolean status = array.getBoolean("status");
                            //JSONObject json = new JSONObject(response);
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject dupm = jsonArray.getJSONObject(i);
                                GeneralHash d = new GeneralHash(dupm.getString("id"),dupm.getString("name"));
                                relationsItems.add(d);
                                relationNames.add(dupm.getString("name"));
                            }
                            relationAdapter.notifyDataSetChanged();


                            if (spinKit!= null)
                                spinKit.setVisibility(View.GONE);

                            if (spinKit!= null)
                                spinKit.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (spinKit!= null)
                                spinKit.setVisibility(View.GONE);
                            Toast.makeText(AddReminder.this, "Fail to Create Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKit.setVisibility(View.GONE);
                        Toast.makeText(AddReminder.this, "Internet is Not working correctly", Toast.LENGTH_SHORT).show();

                    }
                }) ;
            /*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*/


            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String > value = new HashMap<>();
                //value.put("email",email.getText().toString());
                //value.put("password",password.getText().toString());
                return value;
            }*/


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return;

    }


    public String getMonth(){


        return monthSpinner.getSelectedItemId()+"";
    }
    public String getYear(){
        return  yearSpinner.getSelectedItemPosition()+"";
    }
    public String getDay(){
        return daySpinner.getSelectedItemPosition()+"";

    }
    public String getRelation(){

        return relationSpinner.getSelectedItemPosition()+"";
    }
    public String getOccassion(){

        return occasionSpinner.getSelectedItemPosition()+"";
    }

    public void doTask(String  json){
/*        final ProgressDialog progressDialog = new ProgressDialog(AddReminder.this) ;
        progressDialog.setMessage("Adding Reminder...");
        progressDialog.setIndeterminate(true);*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);

        spinKit.setVisibility(View.VISIBLE);

        String url=getResources().getString(R.string.add_reminder_url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        try {
                            JSONObject array = new JSONObject(response);
                            boolean status = array.getBoolean("status");
                            if (status){
                                Toast.makeText(AddReminder.this, "Reminder Added Sucessfully", Toast.LENGTH_SHORT).show();

                                //String id = array.getString("id");
                                if (spinKit!=null)
                                    spinKit.setVisibility(View.GONE);
                                finish();
                                //StaticVariables.setCurrentUserId(id);

                            }else{
                                Toast.makeText(AddReminder.this, "Fail to add Reminder", Toast.LENGTH_SHORT).show();
                            }
                            Log.wtf("Test", "onResponse: "+array.toString());
                            if (spinKit!=null)
                                spinKit.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (spinKit!=null)
                                spinKit.setVisibility(View.GONE);

                            Toast.makeText(AddReminder.this, "Error due to JSON ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKit.setVisibility(View.GONE);
                        Toast.makeText(AddReminder.this,"Internet is Not working correctly",Toast.LENGTH_SHORT).show();

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

                value.put("name",firstname.getText().toString());
                value.put("customerid", StaticVariables.getUserId(AddReminder.this));
                value.put("occassionid",getOccassion());
                value.put("monthid",getMonth());
                value.put("dayid",getDay());
                value.put("yearid",getYear());
                value.put("relationid",getRelation());

                Log.wtf("TEST", "getParams: "+firstname.getText().toString()+StaticVariables.getUserId(AddReminder.this)+getOccassion()+getMonth()+
                        getYear()+getDay()+getRelation());

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
        if (new Database(AddReminder.this).getProducts()!=null){
            int v  =new Database(AddReminder.this).getProducts().size();
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
                /*switch (y) {
                    case 0:

                        loadHomeFragment();
                        break;
                    case 1:
                        loadProfileFragment();
     break;
                    case 2:

                        loadCartFragment();
                        break;
                    case 3:
                        loadBasketsFragment();
                        break;
                }*/
            }
        });

        //you can add some logic (hide it if the count == 0)
/*        if (badgeCount > 0) {
            ActionItemBadge.update(this, mBottomNavigationView.getMenu().findItem(R.id.action_cart), getDrawable(R.drawable.cart_bottom), ActionItemBadge.BadgeStyles.DARK_GREY, badgeCount);
        } else {
            ActionItemBadge.hide(mBottomNavigationView.getMenu().findItem(R.id.action_cart));
        }*/

        /*//If you want to add your ActionItem programmatically you can do this too. You do the following:
        new ActionItemBadgeAdder().act(this).menu(mBottomNavigationView.getMenu()).title("tsst").itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(bigStyle, 1);*/
        ;
/*        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_home:

                        loadHomeFragment();
                        return true;
                    case R.id.action_profile:
                        loadProfileFragment();
                        return true;
                    case R.id.action_cart:
                        int badgeCount= 2;
                        badgeCount--;
                        ActionItemBadge.update(item, badgeCount);
                        loadCartFragment();
                        return true;
                    case R.id.action_baskets:
                        loadBasketsFragment();
                        return true;
                }
                return false;
            }
        });*/
    }
    }

