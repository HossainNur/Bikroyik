package com.inovex.bikroyik.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.ViewPagerAdapter;
import com.inovex.bikroyik.fragment.KPIFragment;
import com.inovex.bikroyik.fragment.NoticeFragment;
import com.inovex.bikroyik.fragment.SummaryFragment;


/**
 * Created by DELL on 7/31/2018.
 */

public class HomeScreenMR extends AppCompatActivity implements View.OnClickListener {

    ViewPager topViewPager;
    protected View view;
    private ImageButton btnNext, btnFinish;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    private int[] mImageResources = {
            R.drawable.abc,
            R.drawable.abc,
            R.drawable.abc
    };
    TopViewPagerAdapter topViewPagerAdapter;
    RelativeLayout partyMeetingCard;
    RelativeLayout checkInCard;
    RelativeLayout reportCard;
    RelativeLayout noticeCard;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen_hr);
        topViewPager = findViewById(R.id.vpTopViewPager);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        partyMeetingCard = findViewById(R.id.card1);
        checkInCard = findViewById(R.id.card2);
        reportCard = findViewById(R.id.card3);
        noticeCard = findViewById(R.id.card4);
        partyMeetingCard.setOnClickListener(this);
        checkInCard.setOnClickListener(this);
        reportCard.setOnClickListener(this);
        noticeCard.setOnClickListener(this);

        //mAdapter = new ViewPagerAdapter(HomeScreenMR.this, mImageResources);
        // topViewPager.setAdapter(mAdapter);
        topViewPager.setCurrentItem(0);
        // topViewPager.setOnPageChangeListener(this);


        topViewPagerAdapter = new TopViewPagerAdapter(getSupportFragmentManager());
        topViewPager.setAdapter(topViewPagerAdapter);
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


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_retailer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return (super.onOptionsItemSelected(item));
    }


    private void setUiPageViewController() {

//        dotsCount = mAdapter.getCount();
        dotsCount = 3;
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
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

    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "Data sync started......", Toast.LENGTH_SHORT).show();
        switch (v.getId()) {

            case R.id.card1:
                Toast.makeText(getApplicationContext(), "Data sync started......", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeScreenMR.this, PartyMeetingActivity.class));
                break;
            case R.id.card2:
                break;
            case R.id.card3:
                break;
            case R.id.card4:
                break;
        }

    }


    public class TopViewPagerAdapter extends FragmentPagerAdapter {

        public TopViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /** Show a Fragment based on the position of the current screen */
            if (position == 0) {
                return new SummaryFragment();
            } else if (position == 1) {
                return new NoticeFragment();
            } else if (position==2) {
                return new KPIFragment();
            } else {
                return new KPIFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


}
