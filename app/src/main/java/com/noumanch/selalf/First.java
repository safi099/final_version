package com.noumanch.selalf;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.noumanch.selalf.activities.Dashboard;
import com.noumanch.selalf.activities.Login;
import com.noumanch.selalf.utils.StaticVariables;
import com.shehabic.droppy.DroppyClickCallbackInterface;
import com.shehabic.droppy.DroppyMenuItem;
import com.shehabic.droppy.DroppyMenuPopup;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class First extends AppCompatActivity {


    private static final String TAG = "hashkey";
    ImageView selctCounty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //printHashKey();
        selctCounty = (ImageView) findViewById(R.id.select_country);

        Glide.with(First.this)
                .load(R.drawable.kuwait)
                .apply(RequestOptions.circleCropTransform())
                .into(selctCounty);
        selctCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DroppyMenuPopup.Builder droppyBuilder = new DroppyMenuPopup.Builder(First.this, selctCounty);
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
        Button b = (Button) findViewById(R.id.english_btn);
        Button arabicButton = (Button) findViewById(R.id.arabic_btn);

        arabicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StaticVariables.language = false;
                StaticVariables.setLanguage(First.this, "ara");
                /*if (StaticVariables.getUser(First.this)==null) {
                    startActivity(new Intent(First.this, Login.class));
                    //finish();
                }else {*/
                startActivity(new Intent(First.this, Dashboard.class));
                finish();
                /*}*/
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StaticVariables.language = true;
                StaticVariables.setLanguage(First.this, "eng");
                /*if (StaticVariables.getUser(First.this)==null) {
                    startActivity(new Intent(First.this, Login.class));
                    //finish();
                }else {*/
                startActivity(new Intent(First.this, Dashboard.class));
                finish();
                    /*}*/

            }
        });

    }

    private Bitmap resize(int image) {
        Bitmap bMap = BitmapFactory.decodeResource(getResources(), image);
        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 36, 36, true);
        return bMapScaled;
    }
    /*public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }*/
}
