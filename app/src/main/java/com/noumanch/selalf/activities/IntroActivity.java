package com.noumanch.selalf.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.noumanch.selalf.First;
import com.noumanch.selalf.R;
import com.noumanch.selalf.fragment.IntroFragment;

import static android.support.v7.appcompat.R.id.image;

public class IntroActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Selal", "Order a basket", R.drawable.order_a_basket, getColor(android.R.color.white)));
        addSlide(AppIntroFragment.newInstance("Selal", "Write a note with the basket", R.drawable.write_a_note_with_the_basket, getColor(android.R.color.white)));
        addSlide(AppIntroFragment.newInstance("Selal", "We will deliver the basket", R.drawable.we_will_deliver_the_basket, getColor(android.R.color.white)));

        // OPTIONAL METHODS
        // Override bar/separator color.
         //setBarColor(Color.parseColor("#ffffff"));
        //setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);

        setProgressButtonEnabled(false);

        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permission in Manifest.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        startActivity(new Intent(IntroActivity.this, First.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        startActivity(new Intent(IntroActivity.this, First.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}