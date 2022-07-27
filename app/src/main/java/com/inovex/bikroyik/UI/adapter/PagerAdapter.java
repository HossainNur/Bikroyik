package com.inovex.bikroyik.UI.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.inovex.bikroyik.UI.fragments.DueFragment;
import com.inovex.bikroyik.UI.fragments.PaidFragment;
import com.inovex.bikroyik.UI.fragments.SellsFragment;
import com.inovex.bikroyik.data.local.SharedPreference;

public class PagerAdapter extends FragmentPagerAdapter {

    private SharedPreference sharedPreference;

    String[] text = {"Sales","Paid","Due"};
    String[] text2 = {"সেলস ","পরিশোধিত ","বাকি "};


    //={"সেলস ","পরিশোধিত ","বাকি "};

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0)
        {
            return new SellsFragment();
        }
        else if(position == 1)
        {
            return new PaidFragment();
        }
        else if(position == 2)
        {
            return new DueFragment();
        }



        return null;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return text[position];
    }
}
