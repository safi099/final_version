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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.ybq.android.spinkit.SpinKitView;
import com.noumanch.selalf.R;
import com.noumanch.selalf.fragment.ProductDetail;
import com.noumanch.selalf.model.Order;
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

public class UserOrders extends NavigationDrawerActivity {


    ArrayList<Order> orderArrayList= new ArrayList<>();
    RecyclerView recyclerView ;
    private SimpleStringRecyclerViewAdapter adapter;
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
        setContentView(R.layout.activity_user_orders);
        spinKitView = findViewById(R.id.spin_kit);
        recyclerView = (RecyclerView) findViewById(R.id.address_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserOrders.this));

        adapter = new SimpleStringRecyclerViewAdapter(UserOrders.this,orderArrayList);
        recyclerView.setAdapter(adapter);
        doTask(StaticVariables.getUserId(UserOrders.this));
        setupBottomNavigation();
    }

    /** send the attendence*/
    public void doTask(String  customerId){
/*        final ProgressDialog progressDialog = new ProgressDialog(UserOrders.this) ;
        progressDialog.setMessage("Getting Previous Orders...");
        progressDialog.setIndeterminate(true);*/

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);
        if (customerId==null){
            Toast.makeText(this, "You are not logged in ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (customerId.equals("")){
            Toast.makeText(this, "You are not logged in ", Toast.LENGTH_SHORT).show();
            return;

        }
        spinKitView.setVisibility(View.VISIBLE);
        String url=getResources().getString(R.string.orders_list)+customerId+"&display=full&ws_key=11111111111111111111111111111111&output_format=JSON";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        try {
                            JSONObject array = new JSONObject(response);
                            JSONArray orders = array.getJSONArray("orders");
                            ArrayList<Product> productsArrayList = new ArrayList<>();
                            for (int i = 0; i < orders.length(); i++) {
                                JSONObject order = orders.getJSONObject(i);
                                String  delivery_date = order.getString("delivery_date");
                                JSONObject associations = order.getJSONObject("associations");
                                JSONArray  ordersRow    = associations.getJSONArray("order_rows");
                                for (int j = 0; j < ordersRow.length(); j++) {
                                    JSONObject dump = ordersRow.getJSONObject(j);
                                    String product_name=  dump.getString("product_name");
                                    String product_quantity=  dump.getString("product_quantity");
                                    String product_price   =  dump.getString("product_price");
                                    productsArrayList.add(new Product(product_name,"",product_price,product_quantity));
                                }
                                Order order1 = new Order("",delivery_date,productsArrayList);
                                orderArrayList.add(order1);
                                adapter.notifyDataSetChanged();

                            }

                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);

                            Toast.makeText(UserOrders.this, "No order history found ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (spinKitView!=null)
                            spinKitView.setVisibility(View.GONE);
                        Toast.makeText(UserOrders.this,"Internet is Not working correctly",Toast.LENGTH_SHORT).show();

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
        public ArrayList<Order> mValues;
        private static final int MENU_ITEM_VIEW_TYPE = 0;


        private Context context;




        @Override
        public int getItemViewType(int position) {

            if (mValues.get(position) instanceof Order) {
                return MENU_ITEM_VIEW_TYPE;
            }
            return position;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView delieryDate,totalItems,totalPrice;
            public final ProgressBar progressBar1;
            public ItemViewHolder(View view) {
                super(view);
                mView = view;
                delieryDate = (TextView) view.findViewById(R.id.deliery_date);
                totalItems = (TextView) view.findViewById(R.id.total_items);
                totalPrice = (TextView) view.findViewById(R.id.total_price);
                progressBar1 = (ProgressBar) view.findViewById(R.id.progress_bar1);
            }
        }
        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Order> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;
        }
        @Override
        public SimpleStringRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e("Info", "view type is menu");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_order, parent, false);
            return new SimpleStringRecyclerViewAdapter.ItemViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final SimpleStringRecyclerViewAdapter.ItemViewHolder holder, int position) {
            try {
                holder.totalPrice.setText(mValues.get(position).getProducts().get(0).getPrice());
                holder.totalItems.setText(mValues.get(position).getProducts().get(0).getName());
                //holder.address.setText(mValues.get(position).getAddresses());
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductDetail.class));
                }
            });
            holder.progressBar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),ProductDetail.class));
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
        if (new Database(UserOrders.this).getProducts()!=null){
            int v  =new Database(UserOrders.this).getProducts().size();
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
                    case R.id.action_profile:
                        Intent i4 = new Intent();
                        i4.putExtra("goTo","profile");
                        setResult(RESULT_OK,i4);
                        finish();
                        break;
                    case R.id.action_cart:
                        Intent iii = new Intent();
                        iii.putExtra("goTo","cart");
                        setResult(RESULT_OK,iii);
                        finish();
                        break;
                    case R.id.action_baskets:
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
