package com.inovex.bikroyik.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.fragment.AddRetailerFragment;
import com.inovex.bikroyik.fragment.SubmittedRetailerFragment;

//import com.nahid.inovexpharma.adapter.ViewPagerAdapter;


/**
 * Created by DELL on 8/16/2018.
 */

public class AddRetailer extends AppCompatActivity {

    Context mContext;
    TextView tvAddRetailer;
    String title;
    AppDatabaseHelper appDatabaseHelper;
    ViewPager topViewPager;
    protected View view;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    AddRetailerViewPagerAdapter addRetailerViewPagerAdapter;
    private FragmentManager fragmentManager;
    public int pos = 0;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_retailer);
        setTitle("Add New Retailer");
        mContext = this;
        appDatabaseHelper = new AppDatabaseHelper(mContext);
        tvAddRetailer = findViewById(R.id.tvAddRetailer);
        topViewPager = findViewById(R.id.addRetailerVpTopViewPager);
        pager_indicator = findViewById(R.id.addRetailerViewPagerCountDots);

        fragmentManager = getSupportFragmentManager();
        addRetailerViewPagerAdapter = new AddRetailerViewPagerAdapter(fragmentManager);
        topViewPager.setAdapter(addRetailerViewPagerAdapter);



        topViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

                switch (position) {
                    case 0:
                        title = "Add Retailer";
                        pos = 0;
                        break;
                    case 1:
                        title = "Submitted Retailer";
                        pos = 1;
                        break;
                }
                tvAddRetailer.setText(title);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setUiPageViewController();


    }

    private void setUiPageViewController() {

        dotsCount = 2;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(mContext);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    public class AddRetailerViewPagerAdapter extends FragmentPagerAdapter {

        public AddRetailerViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            if(position == 0){
                AddRetailerFragment fragment = new AddRetailerFragment();
                return  fragment;
            }
            else {
                SubmittedRetailerFragment fragment = new SubmittedRetailerFragment();
                return fragment;
            }

        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
