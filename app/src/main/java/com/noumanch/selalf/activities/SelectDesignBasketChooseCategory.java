package com.noumanch.selalf.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.model.Product;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.StaticVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SelectDesignBasketChooseCategory extends AppCompatActivity {


    RecyclerView recyclerView ;
    ArrayList<Product> baskets= new ArrayList<>();
    private SimpleStringRecyclerViewAdapter adapter;
    TextView add_address;
    TextView selectCategoryTextV;
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
        setContentView(R.layout.activity_select_design_basket);
        spinKitView = findViewById(R.id.spin_kit);
        recyclerView = (RecyclerView) findViewById(R.id.address_recylerview);
        add_address = (TextView) findViewById(R.id.add_address);
        add_address.setText(R.string.choose_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectDesignBasketChooseCategory.this));

/*        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SelectDesignBasket.this,AddAddresses.class);
                i.putExtra("customerId","73");
                startActivity(i);
            }
        });*/
        Product health = new Product("","27","","");
        health.setImagDrawableForCAtegory(R.drawable.health);
        baskets.add(health);
        Product sweet = new Product("","28","","");
        sweet.setImagDrawableForCAtegory(R.drawable.sweet);
        baskets.add(sweet);
        Product flower = new Product("","29","","");
        flower.setImagDrawableForCAtegory(R.drawable.flower);
        baskets.add(flower);
         adapter = new SimpleStringRecyclerViewAdapter(SelectDesignBasketChooseCategory.this,baskets);
        recyclerView.setAdapter(adapter);


        //doTask("");
        setupBottomNavigation();
    }

    /** send the attendence*/
    public void doTask(String  customerId){
        /*final ProgressDialog progressDialog = new ProgressDialog(SelectDesignBasketChooseCategory.this) ;
        progressDialog.setMessage("Getting baskets...");
        progressDialog.setIndeterminate(true);*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);

        spinKitView.setVisibility(View.VISIBLE);

        String url=getResources().getString(R.string.design_baskets_url);
        Log.wtf("TEst", "get design baskets: "+url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        try {
                            JSONObject array = new JSONObject(response);
                            JSONArray  addresses  = array.getJSONArray("products");
                            for (int i = 0; i < addresses.length(); i++) {
                                JSONObject dump = addresses.getJSONObject(i);

                                ArrayList<String > imag = new ArrayList<>();
                                String  s=getResources().getString(R.string.image_base_url)+""+dump.getString("id")+"/"+dump.getString("id_default_image")+"/?ws_key=11111111111111111111111111111111";
                                imag.add(s);

                                JSONArray names= dump.getJSONArray("name");
                                JSONObject ob = names.getJSONObject(0);
                                String nam = ob.getString("value");
                                Product addresses1 = new Product(nam,dump.getString("id"),dump.getString("price"),imag);
                                baskets.add(addresses1);

                            }
                            adapter.notifyDataSetChanged();

                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);

                            Toast.makeText(SelectDesignBasketChooseCategory.this, "Error due to JSON ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        spinKitView.setVisibility(View.GONE);
                        Toast.makeText(SelectDesignBasketChooseCategory.this,"Internet is Not working correctly",Toast.LENGTH_SHORT).show();

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
                //value.put("email",email.getText().toString());
                //value.put("password",password.getText().toString());
                return value;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        return  ;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ItemViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        public ArrayList<Product> mValues;
        private static final int MENU_ITEM_VIEW_TYPE = 0;


        private Context context;




        @Override
        public int getItemViewType(int position) {

            if (mValues.get(position) instanceof Product) {
                return MENU_ITEM_VIEW_TYPE;
            }
            return position;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {


            public final View mView;

            public final TextView price,name;
            public final ImageView image;
            public final AppCompatRatingBar rating;

            public ItemViewHolder(View view) {
                super(view);
                mView = view;
                name = (TextView) view.findViewById(R.id.product_name);
                price = (TextView) view.findViewById(R.id.product_price);
                image = (ImageView) view.findViewById(R.id.image);
                rating = (AppCompatRatingBar) view.findViewById(R.id.rating);
                rating.setVisibility(View.GONE);
                price.setVisibility(View.GONE);
                name.setVisibility(View.GONE);

            }
        }


        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Product> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;

        }

        @Override
        public SimpleStringRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            Log.e("Info", "view type is menu");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_product, parent, false);
            return new SimpleStringRecyclerViewAdapter.ItemViewHolder(view);



        }


        @Override
        public void onBindViewHolder(final SimpleStringRecyclerViewAdapter.ItemViewHolder holder, final int position) {
            try {


                Glide.with(holder.image.getContext())
                        .load(mValues.get(position).getImagDrawableForCAtegory())
                        .into(holder.image);
                Log.wtf("TAG", "url become: "+mValues.get(position).getImage().get(0));


            } catch (Exception e) {

                e.printStackTrace();
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(holder.image.getContext(),ShowCategoryItems.class);
                    i.putExtra("category_id",mValues.get(position).getId());
                    holder.itemView.getContext().startActivity(i);

                }
            });


        }



        @Override
        public int getItemCount() {
            return mValues.size();
        }

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
        if (new Database(SelectDesignBasketChooseCategory.this).getProducts()!=null){
            int v  =new Database(SelectDesignBasketChooseCategory.this).getProducts().size();
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
