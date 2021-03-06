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
import com.noumanch.selalf.model.Reminder;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.StaticVariables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserReminders extends NavigationDrawerActivity {


    RecyclerView recyclerView ;
    ArrayList<Reminder> reminderArrayList = new ArrayList<>();
    private SimpleStringRecyclerViewAdapter adapter;
    TextView addReminderButton;
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
        setContentView(R.layout.activity_user_reminders);
        spinKitView = findViewById(R.id.spin_kit);
        recyclerView = (RecyclerView) findViewById(R.id.address_recylerview);
        addReminderButton = (TextView) findViewById(R.id.add_reminder);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserReminders.this));
          adapter = new SimpleStringRecyclerViewAdapter(UserReminders.this,reminderArrayList);
        recyclerView.setAdapter(adapter);
        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserReminders.this,AddReminder.class);
                //i.putExtra("customerId","7");
                startActivity(i);
            }
        });
        doTask(StaticVariables.getUserId(UserReminders.this));
        setupBottomNavigation();
    }

    /** send the attendence*/
    public void doTask(String  customerId){
       /* final ProgressDialog progressDialog = new ProgressDialog(UserReminders.this) ;
        progressDialog.setMessage("Getting Reminders...");
        progressDialog.setIndeterminate(true);*/
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            //progressDialog.setIndeterminateDrawable(drawable);
        }
        //progressDialog.setCancelable(false);
        if (customerId==null){
            Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();
            return;
        }if (customerId.equals("")){
            Toast.makeText(this, "You are not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        spinKitView.setVisibility(View.VISIBLE);
        String url=getResources().getString(R.string.reminders_list)+customerId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("RESPONSE",""+response);
                        try {
                            JSONArray array = new JSONArray(response);
                            if (array.length()==0){
                                Toast.makeText(UserReminders.this, "No reminders found", Toast.LENGTH_SHORT).show();
                                spinKitView.setVisibility(View.GONE);
                                return;
                            }

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject dump =   array.getJSONObject(i);
                                Log.wtf("Test", "onResponse: "+dump.toString());
                                Reminder reminder = new Reminder(dump.getString("id"),dump.getString("name"),
                                        dump.getString("occasion_name"),dump.getString("dayid"),dump.getString("monthid"),
                                        dump.getString("yearid"),dump.getString("relation_name"));
                                        reminderArrayList.add(reminder);
                            }
                            adapter.notifyDataSetChanged();
                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (spinKitView!=null)
                                spinKitView.setVisibility(View.GONE);

                            Toast.makeText(UserReminders.this, "Error due to JSON ", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (spinKitView!=null)
                            spinKitView.setVisibility(View.GONE);
                        Toast.makeText(UserReminders.this,"Internet is Not working correctly "+error.getMessage(),Toast.LENGTH_SHORT).show();

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
        public ArrayList<Reminder> mValues;
        private static final int MENU_ITEM_VIEW_TYPE = 0;


        private Context context;




        @Override
        public int getItemViewType(int position) {

            if (mValues.get(position) instanceof Reminder) {
                return MENU_ITEM_VIEW_TYPE;
            }
            return position;
        }

        public static class ItemViewHolder extends RecyclerView.ViewHolder {


            public final View mView;

            public final TextView occasionName,date,name,relation;
            public final ProgressBar progressBar1;

            public ItemViewHolder(View view) {
                super(view);
                mView = view;
                occasionName = (TextView) view.findViewById(R.id.occasion_name);
                date = (TextView) view.findViewById(R.id.date);
                name = (TextView) view.findViewById(R.id.name_reminder);
                relation = (TextView) view.findViewById(R.id.relation_reminder);
                progressBar1 = (ProgressBar) view.findViewById(R.id.progress_bar1);
            }
        }


        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<Reminder> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
            this.context = context;

        }

        @Override
        public SimpleStringRecyclerViewAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            Log.e("Info", "view type is menu");
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_reminder, parent, false);
            return new SimpleStringRecyclerViewAdapter.ItemViewHolder(view);



        }


        @Override
        public void onBindViewHolder(final SimpleStringRecyclerViewAdapter.ItemViewHolder holder, int position) {
            try {

                holder.occasionName.setText(mValues.get(position).getOccasion_name());
                holder.relation.setText(mValues.get(position).getRelation_name());
                holder.date.setText(mValues.get(position).getDayid()+"-"+mValues.get(position).getMonthid()+"-"+mValues.get(position).getYearid());
                holder.name.setText(mValues.get(position).getName());

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
       // AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.person, android.R.color.black);
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
        if (new Database(UserReminders.this).getProducts()!=null){
            int v  =new Database(UserReminders.this).getProducts().size();
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
