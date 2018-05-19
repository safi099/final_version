package com.noumanch.selalf.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.noumanch.selalf.R;
import com.noumanch.selalf.model.Product;
import com.noumanch.selalf.utils.Database;
import com.noumanch.selalf.utils.PayPalConfig;
import com.noumanch.selalf.utils.StaticVariables;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SelectDevlieryType extends AppCompatActivity {

    RadioButton deliveryPick, storePick;
    Button shop_now;
    private AHBottomNavigation bottomNavigation;
    ArrayList<Product> orderArrayList = new ArrayList<>();
    private float price;
    public static final int PAYPAL_REQUEST_CODE = 123;
    //Payment Amount
    private String paymentAmount;
    private Object payment;
    private static PayPalConfiguration config ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_devliery_type);
        deliveryPick = findViewById(R.id.deliery_pick);
        storePick = findViewById(R.id.store_pick);
        shop_now = findViewById(R.id.shopnow);

        shop_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Database d = new Database(SelectDevlieryType.this);
                ArrayList<Product> products = d.getProducts();
                if (products != null) {

                    if (products.size()==0) {
                        Toast.makeText(getApplicationContext(), "No Products in Cart", Toast.LENGTH_SHORT).show();
                    } else {
                        price=0;
                        for (int i = 0; i < products.size(); i++) {
                            orderArrayList.add(products.get(i));
                            price += Float.parseFloat(products.get(i).getPrice());
                        }
                      //Toast.makeText(getApplicationContext(), "You total Bills is" + price, Toast.LENGTH_SHORT).show();
                        if(StaticVariables.isIndicatorOfLogin(SelectDevlieryType.this))
                        getPayment();

                        else{
                        Toast.makeText(getApplicationContext(), "Need to Login to continue Payment", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(SelectDevlieryType.this,Login.class);
                        startActivity(intent);
                        }
                    }
                }
            }
        });
        setupBottomNavigation();


    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void getPayment() {
        config = new PayPalConfiguration()
                // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
                // or live (ENVIRONMENT_PRODUCTION)
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(PayPalConfig.PAYPAL_CLIENT_ID);
        //Getting the amount from editText
        //paymentAmount = editTextAmount.getText().toString();
        Intent intent1 = new Intent(this, PayPalService.class);
        intent1.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent1);
        //Creating a paypalpayment

        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(price*3.34)), "USD","Selal Total Bill",
                PayPalPayment.PAYMENT_INTENT_SALE);

        //Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        //Puting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //Starting the intent activity for result
        //the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    public void deliveryClick(View view) {

        storePick.setChecked(false);
        deliveryPick.setChecked(true);

    }



    public void storePickClick(View view) {

        storePick.setChecked(true);
        deliveryPick.setChecked(false);
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
        if (new Database(SelectDevlieryType.this).getProducts() != null) {
            int v = new Database(SelectDevlieryType.this).getProducts().size();
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
                        Intent i1 = new Intent();
                        i1.putExtra("goTo", "home");
                        setResult(RESULT_OK, i1);
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

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    try {
                        //Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        paymentAmount= String.valueOf(new BigDecimal(String.valueOf(price*3.34)));
                        Log.i("pay", paymentDetails);

                        //Starting a new activity for the payment details and also putting the payment details with intent
                        startActivity(new Intent(this, ConfirmationActivity.class)
                                .putExtra("PaymentDetails", paymentDetails)
                                .putExtra("PaymentAmount", paymentAmount));

                    } catch (JSONException e) {
                        Log.i("paymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }
}
