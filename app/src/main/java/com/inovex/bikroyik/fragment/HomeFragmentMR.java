package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.AddRetailer;
import com.inovex.bikroyik.activity.PartyMeetingActivity;
import com.inovex.bikroyik.adapter.TopViewPagerAdapterMR;
import com.inovex.bikroyik.adapter.ViewPagerAdapter;


/**
 * Created by DELL on 8/1/2018.
 */

public class HomeFragmentMR extends Fragment implements View.OnClickListener{
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
    TopViewPagerAdapterMR topViewPagerAdapterMR;
    Context mContext;
    RelativeLayout partyMeetingCard;
    RelativeLayout checkInCard;
    RelativeLayout reportCard;
    RelativeLayout noticeCard;

    public HomeFragmentMR() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_mr, container, false);
        Log.v("_sf_","onCreateView called");


        mContext=getContext();
        topViewPager=view.findViewById(R.id.vpTopViewPager);
        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        //mAdapter = new ViewPagerAdapter(HomeScreenMR.this, mImageResources);
        // topViewPager.setAdapter(mAdapter);

        // topViewPager.setOnPageChangeListener(this);
        partyMeetingCard = view.findViewById(R.id.card1);
        checkInCard = view.findViewById(R.id.card2);
        reportCard = view.findViewById(R.id.card3);
        noticeCard = view.findViewById(R.id.card4);
        partyMeetingCard.setOnClickListener(this);
        checkInCard.setOnClickListener(this);
        reportCard.setOnClickListener(this);
        noticeCard.setOnClickListener(this);


        topViewPagerAdapterMR =new TopViewPagerAdapterMR(getChildFragmentManager());
        topViewPager.setAdapter(topViewPagerAdapterMR);
        topViewPager.setCurrentItem(0);
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


        return  view;
    }




    private void setUiPageViewController() {

//        dotsCount = mAdapter.getCount();
        dotsCount = 4;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.card1:
                startActivity(new Intent(getActivity(), PartyMeetingActivity.class));
                break;
            case R.id.card2:
                break;
            case R.id.card3:
                break;
            case R.id.card4:
                startActivity(new Intent(getActivity(), AddRetailer.class));
                break;
        }
    }
}
