package com.inovex.bikroyik.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.inovex.bikroyik.fragment.KPIFragment;
import com.inovex.bikroyik.fragment.NoticeFragment;
import com.inovex.bikroyik.fragment.RouteVisitCardFragment;
import com.inovex.bikroyik.fragment.SummaryFragment;

/**
 * Created by DELL on 8/1/2018.
 */

public class TopViewPagerAdapterMR extends FragmentPagerAdapter {

    public TopViewPagerAdapterMR(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        /** Show a Fragment based on the position of the current screen */
        if (position == 0) {
            return new SummaryFragment();
        } else if (position==1){
            return new NoticeFragment();
        }else if (position==2){
            return new RouteVisitCardFragment();
        }else {
            return new KPIFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

