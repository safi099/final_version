package com.noumanch.selalf.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.bumptech.glide.Glide;
import com.noumanch.selalf.R;
import com.noumanch.selalf.activities.NavigationDrawerActivity;
import com.noumanch.selalf.callbacks.RecentCallback;
import com.noumanch.selalf.model.Product;
import com.noumanch.selalf.utils.Database;

import java.util.Calendar;

//import me.zhanghai.android.materialprogressbar.MaterialProgressBar;


public class ProductDetail extends NavigationDrawerActivity implements RecentCallback, DatePickerDialog.OnDateSetListener {


    private LinearLayoutManager mLinearLayoutManager;
    private boolean userScrolled = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private View rv;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeview;
    //private MaterialProgressBar materialProgressBar, centralProgressBar;

    FloatingActionButton fab;
    public static final int ITEMS_PER_AD = 10;
    public static final int HEIGHT = 300;

    Product product;
    LinearLayout product_attribute;
    ImageButton increaseNum, decreaseNum;
    ImageView mainImage, image1, image2, image3;
    Button smallButton, mediumButton, largeButton, quantity_value;
    LinearLayout arabic_btn;

    TextView select_date, select_time, product_price;
    EditText gift_text;
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        product = getIntent().getParcelableExtra("data");

        mainImage = findViewById(R.id.main);
        image1 = findViewById(R.id.img1);
        image2 = findViewById(R.id.img2);
        image3 = findViewById(R.id.img3);
        gift_text = findViewById(R.id.gift_text);
        product_price = findViewById(R.id.product_price);

        mainImage.requestFocus();
        smallButton = findViewById(R.id.small_selection);
        mediumButton = findViewById(R.id.medium_selection);
        largeButton = findViewById(R.id.large_selection);

        increaseNum = findViewById(R.id.increase_num);
        decreaseNum = findViewById(R.id.decrese_num);
        quantity_value = findViewById(R.id.quantity_value);
        select_date = findViewById(R.id.select_date);
        select_time = findViewById(R.id.select_time);

        arabic_btn = findViewById(R.id.arabic_btn);
        product_attribute = findViewById(R.id.product_attribute);


        product_price.setText("KWD " + product.getPrice());

        select_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean wrapInScrollView = true;
                final MaterialDialog dialog = new MaterialDialog.Builder(ProductDetail.this)
                        .title("Select delivery time")
                        .customView(R.layout.time_layaout, wrapInScrollView)

                        .show();
                View viewv = dialog.getCustomView();
                Button b1 = viewv.findViewById(R.id.first);
                Button b2 = viewv.findViewById(R.id.second);
                Button b3 = viewv.findViewById(R.id.third);
                Button b4 = viewv.findViewById(R.id.fourth);
                Button b5 = viewv.findViewById(R.id.fifth);
                Button b6 = viewv.findViewById(R.id.sixth);
                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_time.setText("8:00 AM - 10:00 AM");
                        dialog.dismiss();
                    }
                });
                b2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_time.setText("10:00 AM - 12:00 PM");
                        dialog.dismiss();
                    }
                });
                b3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_time.setText("12:00 PM - 2:00 PM");
                        dialog.dismiss();
                    }
                });
                b4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_time.setText("2:00 PM - 4:00 PM");
                        dialog.dismiss();
                    }
                });
                b5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_time.setText("4:00 PM - 6:00 PM");
                        dialog.dismiss();
                    }
                });
                b6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        select_time.setText("6:00 PM - 8:00 PM");
                        dialog.dismiss();
                    }
                });
            }
        });
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(


                        ProductDetail.this, ProductDetail.this, year, month, day);
                datePickerDialog.show();
            }
        });
        smallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (smallButton.isSelected()) {
                    smallButton.setSelected(false);
                    smallButton.setTextColor(Color.BLACK);
                    mediumButton.setSelected(false);
                    mediumButton.setTextColor(Color.BLACK);
                    largeButton.setTextColor(Color.BLACK);
                    largeButton.setSelected(false);
                } else {
                    smallButton.setSelected(true);
                    mediumButton.setSelected(false);
                    smallButton.setTextColor(Color.WHITE);
                    mediumButton.setTextColor(Color.BLACK);
                    largeButton.setTextColor(Color.BLACK);
                    largeButton.setSelected(false);
                }
            }
        });
        mediumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediumButton.isSelected()) {
                    smallButton.setSelected(false);
                    mediumButton.setSelected(false);
                    largeButton.setSelected(false);
                    smallButton.setTextColor(Color.BLACK);
                    mediumButton.setTextColor(Color.BLACK);
                    largeButton.setTextColor(Color.BLACK);
                } else {
                    smallButton.setSelected(false);
                    mediumButton.setSelected(true);
                    largeButton.setSelected(false);
                    smallButton.setTextColor(Color.BLACK);
                    mediumButton.setTextColor(Color.WHITE);
                    largeButton.setTextColor(Color.BLACK);
                }
            }
        });
        largeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (largeButton.isSelected()) {
                    smallButton.setSelected(false);
                    mediumButton.setSelected(false);
                    largeButton.setSelected(false);
                    smallButton.setTextColor(Color.BLACK);
                    mediumButton.setTextColor(Color.BLACK);
                    largeButton.setTextColor(Color.BLACK);
                } else {
                    smallButton.setSelected(false);
                    mediumButton.setSelected(false);
                    largeButton.setSelected(true);
                    smallButton.setTextColor(Color.BLACK);
                    mediumButton.setTextColor(Color.BLACK);
                    largeButton.setTextColor(Color.WHITE);
                }
            }
        });
        increaseNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = Integer.valueOf(quantity_value.getText().toString());
                i++;
                quantity_value.setText(i + "");
            }
        });
        decreaseNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int i = Integer.valueOf(quantity_value.getText().toString());
                i--;
                quantity_value.setText(i + "");
            }
        });
        setupBottomNavigation();
        if (!isFinishing())
            Glide.with(ProductDetail.this)
                    .load(product.getImage().get(0))
                    .into(mainImage);

        for (int i = 0; i < product.getImage().size(); i++) {
            if (i == 0) {
                if (product.getImage().get(0) != null) {
                    Glide.with(ProductDetail.this)
                            .load(product.getImage().get(0))
                            .into(image1);
                    image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Glide.with(ProductDetail.this)
                                    .load(product.getImage().get(0))
                                    .into(mainImage);
                        }
                    });
                }
            }
            if (i == 1) {
                if (product.getImage().get(1) != null) {
                    Glide.with(ProductDetail.this)
                            .load(product.getImage().get(1))
                            .into(image2);
                    image2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Glide.with(ProductDetail.this)
                                    .load(product.getImage().get(1))
                                    .into(mainImage);
                        }
                    });
                }
            }
            if (i == 2) {
                if (product.getImage().get(2) != null) {


                    Glide.with(ProductDetail.this)
                            .load(product.getImage().get(2))
                            .into(image3);
                    image3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Glide.with(ProductDetail.this)
                                    .load(product.getImage().get(2))
                                    .into(mainImage);
                        }
                    });
                }
            }
        }


        if (product.getId_product_attribute().size() > 1) {
            product_attribute.setVisibility(View.VISIBLE);
        } else {
            product_attribute.setVisibility(View.GONE);
        }
        arabic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (product.getId_product_attribute().size() > 1) {
                    if (smallButton.isSelected() || mediumButton.isSelected() || largeButton.isSelected()) {

                        if (!select_date.getText().equals("Select") && !select_time.getText().equals("Select")) {


                            product.setQuantity(quantity_value.getText().toString());
                            Database database = new Database(ProductDetail.this);
                            int attributeValue = -1;
                            if (smallButton.isSelected())
                                attributeValue = 1;
                            if (mediumButton.isSelected())
                                attributeValue = 2;
                            if (largeButton.isSelected())
                                attributeValue = 3;
                            product.setGiftText(gift_text.getText().toString());
                            int i = database.addProduct(product, attributeValue);
                            Log.wtf("test", "onClick: " + product.toString());
                            database.close();
                            if (i != -1) {
                                Toast.makeText(ProductDetail.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(ProductDetail.this, "Select delivery date and time", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        Toast.makeText(ProductDetail.this, "Select product size", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!select_date.getText().equals("Select") && !select_time.getText().equals("Select")) {
                        product.setQuantity(quantity_value.getText().toString());
                        Database database = new Database(ProductDetail.this);
                        product.setGiftText(gift_text.getText().toString());
                        int i = database.addProduct(product, 0);
                        Log.wtf("test", "onClick: " + product.toString());
                        database.close();
                        if (i != -1) {
                            Toast.makeText(ProductDetail.this, "Product added to cart", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(ProductDetail.this, "Select delivery date and time", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });
    }


    @Override
    public void onSwipeSucess() {

        stopSwipe();
    }

    @Override
    public void onLimited() {

    }

    public void stopSwipe() {

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        select_date.setText(i2 + " / " + (i1 + 1) + " / "
                + i);

    }


    @TargetApi(Build.VERSION_CODES.M)
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
        bottomNavigation.setInactiveColor(Color.parseColor("#E30614"));
        bottomNavigation.setAccentColor(Color.parseColor("#000000"));
        bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));
// Add or remove notification for each item
        if (new Database(ProductDetail.this).getProducts() != null) {
            int v = new Database(ProductDetail.this).getProducts().size();
            if (v != 0) {
                bottomNavigation.setNotification("" + v, 2);
            }
        }

        // Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                switch (position) {
                    case 0:
                        finish();
                        break;
                    case 1:
                        Intent i4 = new Intent();
                        i4.putExtra("goTo", "profile");
                        setResult(RESULT_OK, i4);
                        finish();
                        break;
                    case 2:
                        Intent iii = new Intent();
                        iii.putExtra("goTo", "cart");
                        setResult(RESULT_OK, iii);
                        finish();
                        break;
                    case 3:
                        Intent ii = new Intent();
                        ii.putExtra("goTo", "basket");
                        setResult(RESULT_OK, ii);
                        finish();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
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

/*    @Override
    public void onBackPressed() {
        onBackPressed();
        Intent intent  = new Intent(ProductDetail.this,Dashboard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }*/
}
