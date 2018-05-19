package com.noumanch.selalf.fragment;

 import android.content.Intent;
 import android.os.Bundle;
import android.support.annotation.Nullable;
 import android.support.v4.app.Fragment;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.ImageView;
 import android.widget.LinearLayout;
 import android.widget.RelativeLayout;
 import android.widget.TextView;

 import com.noumanch.selalf.R;
 import com.noumanch.selalf.activities.ChooseCategory;
 import com.noumanch.selalf.activities.SelectDesignBasket;

public class BasketFragment extends Fragment {

    public static BasketFragment newInstance() {
        return new BasketFragment();
    }

    TextView firstTExtView,secondTextV;
    ImageView firstIV,secondIV;
    LinearLayout firstL,secondL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v        = inflater.inflate(R.layout.fragment_basket, container, false);
        firstTExtView = v.findViewById(R.id.first_text);
        secondTextV   = v.findViewById(R.id.second_txt);
        secondIV      = v.findViewById(R.id.secondIV);
        firstIV       = v.findViewById(R.id.first_image);
        firstL        = v.findViewById(R.id.first);
        secondL       = v.findViewById(R.id.second);
        firstL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChooseCategory.class));
            }
        });
        firstTExtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChooseCategory.class));
            }
        });
        firstIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChooseCategory.class));
            }
        });
        secondIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectDesignBasket.class));
            }
        });
        secondL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectDesignBasket.class));
            }
        });
        secondTextV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SelectDesignBasket.class));
            }
        });
        return v;
    }
}