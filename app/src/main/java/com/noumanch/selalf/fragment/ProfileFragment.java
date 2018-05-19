package com.noumanch.selalf.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.noumanch.selalf.R;
import com.noumanch.selalf.utils.StaticVariables;

public class ProfileFragment extends Fragment implements MenuProfileFragment.HandleMenuClicks {



    public static ProfileFragment newInstance() {

        return new ProfileFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile_content_main, container, false);

        MenuProfileFragment fragment = MenuProfileFragment.newInstance();
        Bundle b = new Bundle();

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment,"profile");
        ft.commit();
        return v;

    }


    @Override
    public void updateUserInfo() {


        if (StaticVariables.getUserId(getActivity())==null){
            Toast.makeText(getActivity(), "you are not logged in ", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getActivity(), "userinfo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void allAddresses() {
        Toast.makeText(getActivity(), "addresses", Toast.LENGTH_SHORT).show();

    }
    public void testMethod(){

            Log.wtf("TEST", "testMethod: we are done with testMethod ");
            UpdateCustomer fragment = UpdateCustomer.newInstance();
            Bundle b = new Bundle();

            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment,"u_c").addToBackStack(null);
            ft.commit();

            }

    @Override
    public void orderHistory() {
        Toast.makeText(getActivity(), "orderHistory", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void myReminders() {
        Toast.makeText(getActivity(), "my reminder", Toast.LENGTH_SHORT).show();
    }

}
