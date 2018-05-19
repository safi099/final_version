package com.noumanch.selalf.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.noumanch.selalf.First;
import com.noumanch.selalf.R;
import com.noumanch.selalf.utils.StaticVariables;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.util.Locale;


public class NavigationDrawerActivity extends AppCompatActivity {


    public Toolbar toolbar;                              // Declaring the Toolbar Object


    ActionBarDrawerToggle mDrawerToggle;
    Context context;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private TextView txt_logout;
    private TextView language;
    private ImageView selctCounty;
    private AppBarLayout tb;

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
    }

    protected boolean useToolbar() {
        return true;
    }


    @SuppressLint("ResourceType")
    @Override
    public void setContentView(int layoutResID) {
        context = this;

        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.drawer_main, null);
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.nav_header_main2, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.frame);

        getLayoutInflater().inflate(layoutResID, activityContainer, true);

        super.setContentView(fullView);
        //toolbar = (Toolbar) fullView.findViewById(R.id.tool_bar);
        tb = (AppBarLayout) fullView.findViewById(R.id.toolbar);
        toolbar=tb.findViewById(R.id.tool_bar);
        txt_logout = (TextView) findViewById(R.id.logout);
        if(StaticVariables.isIndicatorOfLogin(NavigationDrawerActivity.this))
            txt_logout.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        /*getSupportActionBar().setTitle("");
        toolbar.setTitle("");
        this.getSupportActionBar().setElevation(0);

        getSupportActionBar().setLogo(R.drawable.mini_logo);*/
        //  toolbar.setLogo(R.drawable.ic_main);
        if (useToolbar()) {
            setSupportActionBar(toolbar);
            setTitle("");
        } else {
            toolbar.setVisibility(View.GONE);
        }

        txt_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(StaticVariables.isIndicatorOfLogin(NavigationDrawerActivity.this))) {
                    startActivity(new Intent(NavigationDrawerActivity.this, Login.class));
                } else {
                    txt_logout.setVisibility(View.GONE);
                    Toast.makeText(NavigationDrawerActivity.this, "Your are already Logged In", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();
        // find MenuItem you want to change
        MenuItem nav_camara = menu.findItem(R.id.logout);
        // set new title to the MenuItem
        if (StaticVariables.isIndicatorOfLogin(NavigationDrawerActivity.this))
            nav_camara.setTitle(getResources().getString(R.string.sign_out));
        else
            nav_camara.setTitle(getResources().getString(R.string.sign_in));
        // This method will trigger on item Click of navigation menu
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                        return true;
                    case R.id.update_info:
                        startActivity(new Intent(getApplicationContext(), UpdateUser.class));
                        return true;
                    case R.id.add_address:
                        return true;
                    case R.id.order:
                        startActivity(new Intent(getApplicationContext(), UserOrders.class));
                        return true;
                    case R.id.reminder:
                        startActivity(new Intent(getApplicationContext(), UserReminders.class));
                        return true;
                    case R.id.help:
                        return true;
                    case R.id.contact:
                        return true;
                    case R.id.logout:
                        if (menuItem.getTitle().equals("Sign Out"))
                            signoutAction();
                        else
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Work in progress", Toast.LENGTH_SHORT).show();
                        return true;
                }

            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        View header = navigationView.getHeaderView(0);
        language = (TextView) header.findViewById(R.id.language);
        selctCounty = (ImageView) header.findViewById(R.id.select_country);
    /*    TextView tvName = (TextView) header.findViewById(R.id.name);
        TextView tvEmail = (TextView) header.findViewById(R.id.email);
        String name = Preferences.getDataFromStringPreferences(context,Constants.USER_DETAILS, Constants.USER_NAME);

        if (name != null) {
            tvName.setText(name);
        }*/
        Glide.with(NavigationDrawerActivity.this)
                .load(R.drawable.kuwait)
                .apply(RequestOptions.circleCropTransform())
                .into(selctCounty);
        selctCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(NavigationDrawerActivity.this, selctCounty);
                // Add Item with icon
                DroppyMenuItem item = new DroppyMenuItem("Turkey", R.drawable.turkey);
                Drawable d = new BitmapDrawable(getResources(), resize(R.drawable.turkey));

                item.setIcon(d);
                droppyBuilder.addMenuItem(item);
                // Add Item with icon
                droppyBuilder.addMenuItem(new DroppyMenuItem("UAE", R.drawable.uae));
                // Add Item with icon
                droppyBuilder.addMenuItem(new DroppyMenuItem("Saudi", R.drawable.saudi));
                // Set Callback handler
                droppyBuilder.setOnClick(new DroppyClickCallbackInterface() {
                    @Override
                    public void call(View v, int id) {
                        Log.d("Clicked on ", String.valueOf(id));
                        if (id == 0) {
                            //turkey
                            selctCounty.setImageResource(R.drawable.turkey);
                        } else if (id == 1) {
                            selctCounty.setImageResource(R.drawable.uae);
                        } else if (id == 2) {
                            selctCounty.setImageResource(R.drawable.saudi);
                        }
                    }
                });
                DroppyMenuPopup droppyMenu = droppyBuilder.build();

                droppyMenu.show();
                /*// Inflate the popup_layout.xml
                //LinearLayout viewGroup = (LinearLayout) findViewById(R.id.llStatusChangePopup);
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.popup_layout, null);

                // Creating the PopupWindow
                PopupWindow changeStatusPopUp = new PopupWindow(First.this);
                changeStatusPopUp.setContentView(layout);
                changeStatusPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
                changeStatusPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
                changeStatusPopUp.setFocusable(true);

                // Some offset to align the popup a bit to the left, and a bit down, relative to button's position.
                int OFFSET_X = -20;
                int OFFSET_Y = 50;

                //Clear the default translucent background
                changeStatusPopUp.setBackgroundDrawable(new BitmapDrawable());

                // Displaying the popup at the specified location_icon, + offsets.
                changeStatusPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, (int)selctCounty.getX() + OFFSET_X, ((int)selctCounty.getY() + OFFSET_Y));*/
            }
        });
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawerActivity.this);
                builder.setMessage(getResources().getString(R.string.language_selection));
                builder.setPositiveButton("ENGLISH", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StaticVariables.language = false;
                        StaticVariables.setLanguage(NavigationDrawerActivity.this, "eng");
                        startActivity(new Intent(NavigationDrawerActivity.this, Dashboard.class));
                        finish();
                    }
                });
                builder.setNegativeButton("ARABIC", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StaticVariables.language = false;
                        StaticVariables.setLanguage(NavigationDrawerActivity.this, "ara");
                        startActivity(new Intent(NavigationDrawerActivity.this, Dashboard.class));
                        finish();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private Bitmap resize(int image) {
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), image);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 36, 36, true);
        return bMapScaled;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return mDrawerToggle.onOptionsItemSelected(item);
    }

    public void signoutAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NavigationDrawerActivity.this);
        builder.setMessage("Are you sure you want to signout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StaticVariables.setIndicatorOfLogin(getApplicationContext(), false);
                StaticVariables.deleteCache(getApplicationContext());
                Toast.makeText(getApplicationContext(), "Sign out sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
   /*     new MaterialDialog.Builder(getApplicationContext())
                .title("Message")
                .content("Are you sure you want to signout?")
                .positiveText("Yes")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        StaticVariables.setIndicatorOfLogin(getApplicationContext(),false);
                        StaticVariables.deleteCache(getApplicationContext());
                        Toast.makeText(getApplicationContext(), "Sign out sucessfully", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                    }
                })
                .show();*/
    }
}