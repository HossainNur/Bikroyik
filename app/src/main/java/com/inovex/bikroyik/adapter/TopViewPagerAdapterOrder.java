package com.inovex.bikroyik.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.inovex.bikroyik.fragment.KPIFragment;
import com.inovex.bikroyik.fragment.SummeryOrderFragment;

/**
 * Created by DELL on 8/7/2018.
 */

public class TopViewPagerAdapterOrder extends FragmentPagerAdapter {

    public TopViewPagerAdapterOrder(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        /** Show a Fragment based on the position of the current screen */
        if (position == 0) {
            return new SummeryOrderFragment();
        } else {
            return new KPIFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}

