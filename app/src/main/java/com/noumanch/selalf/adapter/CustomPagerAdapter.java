package com.noumanch.selalf.adapter;

/**
 * Created by macy on 11/11/17.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.noumanch.selalf.fragment.BasketFragment;
import com.noumanch.selalf.fragment.CartFragment;
import com.noumanch.selalf.fragment.HomeFragment;
import com.noumanch.selalf.fragment.ProfileFragment;


public class CustomPagerAdapter extends FragmentPagerAdapter{

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new HomeFragment();
            case 1:
                // Games fragment activity
                return new BasketFragment();
            case 2:
                // Movies fragment activity
                return new CartFragment();
            case 3:
                // Movies fragment activity
                return new ProfileFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}