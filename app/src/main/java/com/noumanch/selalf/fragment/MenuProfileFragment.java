package com.noumanch.selalf.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.noumanch.selalf.R;
import com.noumanch.selalf.activities.Dashboard;
import com.noumanch.selalf.activities.UpdateUser;
import com.noumanch.selalf.activities.UserAddresses;
import com.noumanch.selalf.activities.UserOrders;
import com.noumanch.selalf.activities.UserReminders;
import com.noumanch.selalf.utils.StaticVariables;

public class MenuProfileFragment extends Fragment {


    private TextView userInfoTV,addressesTV,reminderTV,orderTV,signoutTV;
    private ImageView userInfoIV,addressesIV,reminderIV,orderIV, signoutIV;

    HandleMenuClicks handleMenuClicks;
    public static MenuProfileFragment newInstance() {

        return new MenuProfileFragment();
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
        signoutTV   =  v.findViewById(R.id.signout_tct);

        userInfoIV = (ImageView) v.findViewById(R.id.user_image);
        addressesIV= (ImageView) v.findViewById(R.id.all_addresses_image);
        orderIV    = (ImageView) v.findViewById(R.id.order_history_image);
        reminderIV = (ImageView) v.findViewById(R.id.reminder_image);
        signoutIV  = v.findViewById(R.id.signout);


        signoutTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signoutAction();
            }
        });
        signoutIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signoutAction();
            }
        });
        userInfoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //handleMenuClicks.updateUserInfo();
                //Toast.makeText(getActivity(), "user Info text", Toast.LENGTH_SHORT).show();
                //ProfileFragment f = (ProfileFragment) getActivity().getSupportFragmentManager().findFragmentByTag("profile");
                //ProfileFragment f = ((Dashboard) getActivity()).getProfileFragmnet();
                //f.testMethod();
                startActivity(new Intent(getActivity(), UpdateUser.class));

            }
        });
        userInfoIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UpdateUser.class));
            }
        });
        orderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //handleMenuClicks.orderHistory();
                startActivity(new Intent(getActivity(), UserOrders.class));

            }
        });
        orderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserOrders.class));

                //handleMenuClicks.orderHistory();
            }
        });
        addressesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserAddresses.class));


                //handleMenuClicks.allAddresses();
            }
        });
        addressesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), UserAddresses.class));

                //handleMenuClicks.allAddresses();
            }
        });
        reminderIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //handleMenuClicks.myReminders();
                startActivity(new Intent(getActivity(), UserReminders.class));
            }
        });
        reminderTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //handleMenuClicks.myReminders();
                startActivity(new Intent(getActivity(), UserReminders.class));
            }
        });

    }

    private void signoutAction() {

        signoutAction(null);
    }

    public void signoutAction(View v){
        new MaterialDialog.Builder(getActivity())
                .title("Message")
                .content("Are you sure you want to signout?")
                .positiveText("Yes")
                .negativeText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        StaticVariables.setIndicatorOfLogin(getContext(),false);
                        StaticVariables.deleteCache(getActivity());
                        Toast.makeText(getActivity(), "Sign out sucessfully", Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                    }
                })
                .show();
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
