package com.noumanch.selalf.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.noumanch.selalf.First;
import com.noumanch.selalf.R;
import com.noumanch.selalf.fragment.CartFragment;
import com.noumanch.selalf.utils.StaticVariables;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.logo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (!StaticVariables.getFirstLogin(SplashScreen.this)){
                    StaticVariables.setFirstLogin(SplashScreen.this,true);
                    //startActivity(new Intent(SplashScreen.this, First.class));
                    startActivity(new Intent(SplashScreen.this, Dashboard.class));
                    //startActivity(new Intent(SplashScreen.this, Login.class));
                    finish();
                }else {
                    StaticVariables.getLang(SplashScreen.this);
                    startActivity(new Intent(SplashScreen.this, Dashboard.class));
                    finish();

                }

            }
        },3000);
        Glide.with(SplashScreen.this)
                .load(R.drawable.splash)
                .into(logo);
    }
}
