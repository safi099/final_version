package com.noumanch.selalf.activities;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.noumanch.selalf.R;
import com.noumanch.selalf.adapter.CustomPagerAdapter;
import com.noumanch.selalf.fragment.ProfileFragment;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.NonSwipeableViewPager;
import com.noumanch.selalf.utils.StaticVariables;
import java.util.Locale;


public class Dashboard extends NavigationDrawerActivity {


    private NonSwipeableViewPager viewPager;
    private CustomPagerAdapter mAdapter;
    private TextView headingTV;
    private int badgeCount=2;
    private AHBottomNavigation bottomNavigation;
    public static final int REQUEST_CODE = 1;


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
        setContentView(R.layout.activity_dashboard);
        setContentView(R.layout.activity_dashboard);

        viewPager =  findViewById(R.id.viewpager);
        headingTV = (TextView) findViewById(R.id.heading);
        mAdapter  = new CustomPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);


        viewPager.setOffscreenPageLimit(4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){

                    //bottomNavigation.setCurrentItem(0);//mBottomNavigationView.setSelectedItemId(R.id.action_home);
                    headingTV.setText(getResources().getString(R.string.all_products));
                }

                if (position==1){

//                    bottomNavigation.setCurrentItem(1);//mBottomNavigationView.setSelectedItemId(R.id.action_baskets);
                    headingTV.setText(getResources().getString(R.string.choose_category));
                }
                if (position==2){

                    //bottomNavigation.setCurrentItem(2);//.setSelectedItemId(R.id.action_cart);
                    headingTV.setText(getResources().getString(R.string.shopping_cart));

                }
                if (position==3){

                    //bottomNavigation.setCurrentItem(3);//.setSelectedItemId(R.id.action_profile);
                    headingTV.setText(getResources().getString(R.string.my_account));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (savedInstanceState == null) {

            loadHomeFragment();
        }

    }

    private void setupBottomNavigation() {
        bottomNavigation =  findViewById(R.id.bottom_navigation);


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

        //bottomNavigation.setAccentColor(getColor(R.color.colorPrimary));

// Disable the translation inside the CoordinatorLayout
        bottomNavigation.setBehaviorTranslationEnabled(false);
// Enable the translation of the FloatingActionButton
//        bottomNavigation.manageFloatingActionButtonBehavior(floatingActionButton);
        bottomNavigation.setTranslucentNavigationEnabled(true);
        bottomNavigation.setInactiveColor(Color.parseColor("#E30614"));
        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#E30614"));
// Add or remove notification for each item
        if (new Database(Dashboard.this).getProducts()!=null){
            int v  =new Database(Dashboard.this).getProducts().size();
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
                        loadHomeFragment();
                        break;
                    case 1:
                        loadBasketsFragment();

                        break;
                    case 2:

                        loadCartFragment();
                        break;
                    case 3:
                        loadProfileFragment();
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

    private void loadBasketsFragment() {
        viewPager.setCurrentItem(1);
    }

    private void loadHomeFragment() {

        viewPager.setCurrentItem(0);
    }

    private void loadProfileFragment() {

        viewPager.setCurrentItem(3);
    }

    private void loadCartFragment() {

        viewPager.setCurrentItem(2);
    }


    public static ProfileFragment getProfileFragmnet(){
        return  ProfileFragment.newInstance();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Toast.makeText(this, "return", Toast.LENGTH_SHORT).show();
        if (resultCode==RESULT_OK){

            String s = data.getStringExtra("goTo");
            if (s.equals("profile")){

            }else if (s.equals("profile")){

                //Toast.makeText(this, "profile", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(3);
            }else if (s.equals("cart")){
                //Toast.makeText(this, "cart", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(2);

            }else if (s.equals("basket")){
                viewPager.setCurrentItem(1);
                //Toast.makeText(this, "basket", Toast.LENGTH_SHORT).show();

            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBottomNavigation();
    }
}

