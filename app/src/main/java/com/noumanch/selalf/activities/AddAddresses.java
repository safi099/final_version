package com.noumanch.selalf.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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
import com.noumanch.selalf.model.Country;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.StaticVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Locale;

public class AddAddresses extends AppCompatActivity {




    String customerId;
    EditText aliasTV,firstName,lastName,addressTV,phone;
    Spinner coutrySpinner;

    Button add_address_btn;
    private AHBottomNavigation bottomNavigation;
    ArrayList<String> countryArrayList = new ArrayList<>();
    ArrayList<Country> countryItems = new ArrayList<>();
    ArrayAdapter<String>   countryAdapter ;
    SpinKitView progressDialog;
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
        setContentView(R.layout.add_address);

        customerId = getIntent().getStringExtra("customerId");

        aliasTV = findViewById(R.id.alias);
        lastName = findViewById(R.id.last_name);
        progressDialog = findViewById(R.id.spin_kit);
        firstName = findViewById(R.id.first_name);
        addressTV = findViewById(R.id.address);
        coutrySpinner  = findViewById(R.id.country);
        phone   = findViewById(R.id.phone);
        add_address_btn   = (Button)findViewById(R.id.add_address_btn);
        add_address_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTask("");
            }
        });

        countryAdapter  = new ArrayAdapter<String>(AddAddresses.this,android.R.layout.simple_list_item_1,
                countryArrayList);
        coutrySpinner.setAdapter(countryAdapter);



        //doTask("71");
        getCounty("");
        setupBottomNavigation();

    }

    /** send the attendence*/
    public void doTask(final String  customerId){

        progressDialog.setIndeterminate(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            progressDialog.setIndeterminateDrawable(drawable);
        }


        progressDialog.setVisibility(View.VISIBLE);

        String url=getResources().getString(R.string.addresses_new_url);
        Log.wtf("TEst", "doTask: "+url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        try {
                            JSONObject array = new JSONObject(response);
                            JSONObject  addresses  = array.getJSONObject("address");
                            if (addresses!=null){
                                Toast.makeText(AddAddresses.this, "Address Added", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(AddAddresses.this, "Fail to add Address", Toast.LENGTH_SHORT).show();
                            }
                            //adapter.notifyDataSetChanged();

                            if (progressDialog!=null)
                                progressDialog.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialog!=null)
                                progressDialog.setVisibility(View.GONE);

                            Toast.makeText(AddAddresses.this, "Error due to JSON ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.setVisibility(View.GONE);
                        Toast.makeText(AddAddresses.this,"Internet is Not working correctly"+error.toString(),Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            public String getBodyContentType() {
                return "application/xml; charset=" +
                        getParamsEncoding();
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String postData ="<prestashop xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                        "<address>\n" +
                        "<id_customer>"+ StaticVariables.getUserId(AddAddresses.this)+"</id_customer>\n" +
                        "\n" +
                        "<id_country>"+countryItems.get(coutrySpinner.getSelectedItemPosition()).getId()+"</id_country>\n" +
                        "\n" +
                        "<alias>"+aliasTV.getText().toString()+"</alias>\n" +
                        "<lastname>"+lastName.getText().toString()+"</lastname>\n" +
                        "<firstname>"+firstName.getText().toString()+"</firstname>\n" +
                        "<address1>"+addressTV.getText().toString()+"</address1>\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "</address>\n" +
                        "</prestashop>";
                Log.wtf("TEst", "getBody: "+postData );
                try {
                    return postData == null ? null :
                            postData.getBytes(getParamsEncoding());
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }
            /*@Override
            public String getBodyContentType() {

                //return "application/json; charset=utf-8";

            }*/

            /*<prestashop xmlns:xlink="http://www.w3.org/1999/xlink">
<address>
<id_customer>10</id_customer>
<address1>Rehman Plaza, University Road Sargodha</address1>
<id_country>122</id_country>
<alias> My Address</alias>
<lastname>TTI</lastname>
<firstname>Xyz</firstname>
<phone>03137021123</phone>
</address>
</prestashop>*/
/*            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                super.getParams();
                Map<String,String > value = new HashMap<>();
                //value.put("email",email.getText().toString());
                //value.put("password",password.getText().toString());
                return value;
            }*/

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return  ;
    }

    public void getCounty(final String json) {

        //progressDialog.setMessage("Getting country list...");
        progressDialog.setIndeterminate(true);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);
        progressDialog.setVisibility(View.VISIBLE);
        String url = getResources().getString(R.string.get_countires_url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE", "" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("countries");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject dupm = jsonArray.getJSONObject(i);
                                String id   = dupm.getString("id");
                                JSONArray names = dupm.getJSONArray("name");
                                String name = names.getJSONObject(0).getString("value");
                                Country c = new Country(id,name,"");
                                countryArrayList.add(c.getName());
                                countryItems.add(c);
                            }
                                countryAdapter.notifyDataSetChanged();
                            if (progressDialog != null)
                                progressDialog.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (progressDialog != null)
                                progressDialog.setVisibility(View.GONE);
                            Toast.makeText(AddAddresses.this, "Fail to get country", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.setVisibility(View.GONE);
                        Toast.makeText(AddAddresses.this, "Internet is Not working correctly", Toast.LENGTH_SHORT).show();
                    }
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return;
    }

    @TargetApi(Build.VERSION_CODES.M)
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
        bottomNavigation.setInactiveColor(Color.parseColor("#E30614"));
        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
// Add or remove notification for each item
        if (new Database(AddAddresses.this).getProducts()!=null){
            int v  =new Database(AddAddresses.this).getProducts().size();
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
