package com.noumanch.selalf.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.noumanch.selalf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntroFragment extends Fragment implements ISlideBackgroundColorHolder {


    public IntroFragment() {
        // Required empty public constructor
    }


    TextView textView;
    ImageView imageView;

    String title;
    int drawable;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Bundle b = getArguments();
        title  = b.getString("title");
        drawable = b.getInt("image");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_intro, container, false);

        textView = (TextView)   v.findViewById(R.id.text_intro);
        imageView = (ImageView) v.findViewById(R.id.image_main);

        textView.setText(title);
        Glide.with(getActivity())
                .load(drawable)
                .into(imageView);


        return v;
    }


    @Override
    public int getDefaultBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {

    }
}
