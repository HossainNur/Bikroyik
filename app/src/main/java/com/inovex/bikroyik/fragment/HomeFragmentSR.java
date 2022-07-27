package com.inovex.bikroyik.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.EditHomepageActivity;
import com.inovex.bikroyik.UI.activity.OrderActivity;
import com.inovex.bikroyik.activity.AddRetailer;
import com.inovex.bikroyik.activity.MainActivity;
import com.inovex.bikroyik.activity.ProductsDirectoryActivity;
import com.inovex.bikroyik.activity.RouteActivity;
import com.inovex.bikroyik.activity.TargetActivity;
import com.inovex.bikroyik.adapter.ViewPagerAdapter;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.utils.FeaturesNameConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by DELL on 8/7/2018.
 */


public class HomeFragmentSR extends Fragment implements View.OnClickListener {

    AppDatabaseHelper appDatabaseHelper;

//    ViewPager topViewPager;
    protected View view;
    private ImageButton btnNext, btnFinish;
//    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;
    private int[] mImageResources = {
            R.drawable.abc,
            R.drawable.abc,
            R.drawable.abc
    };
//    TopViewPagerAdapterMR topViewPagerAdapterMR;
    Context mContext;
    RelativeLayout salesOrderCard;
    RelativeLayout checkInCard;
    RelativeLayout reportCard;
    RelativeLayout noticeCard;
    RelativeLayout editingCard;
    RelativeLayout salesCard;
    ArrayList<HashMap<String, String>> attendanceList = new ArrayList<>();

    DatabaseSQLite sqLite;

    public HomeFragmentSR() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLite = new DatabaseSQLite(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_sr, container, false);
        Log.v("_sf_", "onCreateView called");

        appDatabaseHelper = new AppDatabaseHelper(getContext());

        mContext = getContext();

//        topViewPager = view.findViewById(R.id.vpTopViewPager);
//        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        //mAdapter = new ViewPagerAdapter(HomeScreenMR.this, mImageResources);
        // topViewPager.setAdapter(mAdapter);

        // topViewPager.setOnPageChangeListener(this);

        salesOrderCard = view.findViewById(R.id.card1);
        checkInCard = view.findViewById(R.id.card2);
        reportCard = view.findViewById(R.id.card3);
        noticeCard = view.findViewById(R.id.card4);
        editingCard = view.findViewById(R.id.card5);
        salesCard = view.findViewById(R.id.card6);

        salesOrderCard.setOnClickListener(this);
        checkInCard.setOnClickListener(this);
        reportCard.setOnClickListener(this);
        noticeCard.setOnClickListener(this);
        editingCard.setOnClickListener(this);
        salesCard.setOnClickListener(this);
        checkAttendance();



//        topViewPagerAdapterMR = new TopViewPagerAdapterMR(getChildFragmentManager());
//        topViewPager.setAdapter(topViewPagerAdapterMR);
//        topViewPager.setCurrentItem(0);
//        topViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//                for (int i = 0; i < dotsCount; i++) {
//                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
//                }
//                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        setUiPageViewController();


        return view;
    }



    private void checkAttendance() {

        attendanceList = appDatabaseHelper.getAttendanceList();
        Calendar instance = Calendar.getInstance();
        int day = instance.get(Calendar.DAY_OF_MONTH);


        if(attendanceList.size() > 0){
            if (attendanceList.get(day - 1).get(AppDatabaseHelper.COLUMN_ATTENDANCE_IN_TIME).equalsIgnoreCase("--")) {
                // not yet done check in, so do it
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                builder.setMessage(getResources().getString(R.string.attendance_dialog_subtitle_main_activity))
                        .setCancelable(false)
                        .setPositiveButton(getResources().getString(R.string.alert_dialog_positive), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Fragment fragment = new AttendanceFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container_body, fragment);
                                fragmentTransaction.commit();
                                MainActivity.tvHomeToolbarTitle.setText(getString(R.string.title_attendence));


                            }
                        })
                        .setNegativeButton(getResources().getString(R.string.alert_dialog_negative), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setIcon(R.drawable.attendence);
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = alert.getWindow();
                window.setGravity(Gravity.CENTER);
                //alert.setTitle(getResources().getString(R.string.attendance_dialog_title_main_activity));
                alert.show();


            }












        }


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

//            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.card1:
                startActivity(new Intent(getActivity(), ProductsDirectoryActivity.class));
                break;
            case R.id.card2:
                startActivity(new Intent(getActivity(), RouteActivity.class));
                break;
            case R.id.card3:
                startActivity(new Intent(getActivity(), TargetActivity.class));
                break;
            case R.id.card4:
                startActivity(new Intent(getActivity(), AddRetailer.class));
                break;
            case R.id.card5:
                startActivity(new Intent(getActivity(), EditHomepageActivity.class));
                break;

            case R.id.card6:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
        }
    }



}
