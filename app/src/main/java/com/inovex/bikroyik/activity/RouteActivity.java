package com.inovex.bikroyik.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.fragment.DayRouteFragment;

public class RouteActivity extends AppCompatActivity {
    Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;
    Context mContext;
    TextView tvRouteDay;
    String day;
    AppDatabaseHelper appDatabaseHelper;
    ViewPager topViewPager;
    protected View view;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    RouteViewPagerAdapter routeViewPagerAdapter;
    private FragmentManager fragmentManager;
    public int pos = 0;
    String[] dayString = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);



        mContext = this;
        appDatabaseHelper = new AppDatabaseHelper(mContext);


        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Visit Plan");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        topViewPager = findViewById(R.id.vpTopViewPager);
        tvRouteDay = findViewById(R.id.tvRouteDay);
        //day = "Saturday" + " VISIT";
        //tvRouteDay.setText(day.toUpperCase());
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        fragmentManager = getSupportFragmentManager();
        routeViewPagerAdapter = new RouteViewPagerAdapter(fragmentManager);
        topViewPager.setAdapter(routeViewPagerAdapter);
        //topViewPager.setCurrentItem(0);
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
                        day = "Saturday Visit";
                        pos = 0;
                        break;
                    case 1:
                        day = "Sunday Visit";
                        pos = 1;
                        break;
                    case 2:
                        day = "Monday Visit";
                        pos = 2;
                        break;
                    case 3:
                        day = "Tuesday Visit";
                        pos = 3;
                        break;
                    case 4:
                        day = "Wednesday Visit";
                        pos = 4;
                        break;
                    case 5:
                        day = "Thursday Visit";
                        pos = 5;
                        break;
                    case 6:
                        day = "Friday Visit";
                        pos = 6;
                        break;

                }


                tvRouteDay.setText(day.toUpperCase());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();


        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    private void setUiPageViewController() {

//        dotsCount = mAdapter.getCount();
        dotsCount = 7;
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


    public class RouteViewPagerAdapter extends FragmentPagerAdapter {

        public RouteViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            DayRouteFragment dayRouteFragment = new DayRouteFragment();
            Bundle data = new Bundle();

            Log.d("workforce_roue_visit", "getItem: "+position);

            /** Show a Fragment based on the position of the current screen */
            data.putString("pos", String.valueOf(position));
            data.putString("day_name", dayString[position]);
            dayRouteFragment.setArguments(data);
            return dayRouteFragment;
        }

        @Override
        public int getCount() {
            return 7;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}