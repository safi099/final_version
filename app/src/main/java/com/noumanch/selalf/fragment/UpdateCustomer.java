package com.noumanch.selalf.fragment;

 import android.os.Bundle;
import android.support.annotation.Nullable;
 import android.support.v4.app.Fragment;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noumanch.selalf.R;
import com.noumanch.selalf.activities.Dashboard;

public class UpdateCustomer extends Fragment {


    private TextView userInfoTV,addressesTV,reminderTV,orderTV;
    private ImageView userInfoIV,addressesIV,reminderIV,orderIV;

    HandleMenuClicks handleMenuClicks;
    public static UpdateCustomer newInstance() {

        return new UpdateCustomer();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        initializeViews(v);




        return v;

    }

    private void initializeViews(View v ) {

        userInfoTV  = (TextView) v.findViewById(R.id.user_image_txt);
        addressesTV = (TextView) v.findViewById(R.id.all_addresses_txt);
        orderTV     = (TextView) v.findViewById(R.id.order_history_txt);
        reminderTV  = (TextView) v.findViewById(R.id.reminder_txt);

        userInfoIV = (ImageView) v.findViewById(R.id.user_image);
        addressesIV = (ImageView) v.findViewById(R.id.all_addresses_image);
        orderIV = (ImageView) v.findViewById(R.id.order_history_image);
        reminderIV = (ImageView) v.findViewById(R.id.reminder_image);


        userInfoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //handleMenuClicks.updateUserInfo();
                Toast.makeText(getActivity(), "user Info text", Toast.LENGTH_SHORT).show();
                //ProfileFragment f = (ProfileFragment) getActivity().getSupportFragmentManager().findFragmentByTag("profile");
                ProfileFragment f = ((Dashboard) getActivity()).getProfileFragmnet();
                f.testMethod();

            }
        });
        userInfoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMenuClicks.updateUserInfo();
            }
        });
        orderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleMenuClicks.orderHistory();
            }
        });
        orderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleMenuClicks.orderHistory();
            }
        });
        addressesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleMenuClicks.allAddresses();
            }
        });
        addressesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleMenuClicks.allAddresses();
            }
        });
        reminderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleMenuClicks.myReminders();
            }
        });
        reminderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                handleMenuClicks.myReminders();
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


    public interface HandleMenuClicks{
        public void updateUserInfo();
        public void allAddresses();
        public void orderHistory();
        public void myReminders();
    }
}
