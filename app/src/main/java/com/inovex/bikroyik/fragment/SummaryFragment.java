package com.inovex.bikroyik.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.widget.ArcProgress;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.inovex.bikroyik.activity.MainActivity.LAST_CHECKED_IN;
import static com.inovex.bikroyik.activity.SplashActivity.dailySummery;

/**
 * Created by DELL on 7/31/2018.
 */

public class SummaryFragment extends Fragment {

    ArcProgress arcStore;
    private Timer timer;
    TextView doneByTotalTextView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERR_TYPE = "user";

    TextView tvThisMonthOne;
    TextView tvThisMonthTwo;
    TextView tvTodayOne;
    TextView tvTodayTwo;
    TextView lastCheckedIn;
    TextView targetVisit;
    TextView visitedPoint;
    double percent = 0.0;

    public SummaryFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        arcStore = view.findViewById(R.id.arc_store);
        doneByTotalTextView = view.findViewById(R.id.capacity);

        tvThisMonthOne = view.findViewById(R.id.tvThisMonthOne);
        tvThisMonthTwo = view.findViewById(R.id.tvThisMonthTwo);
        tvTodayOne = view.findViewById(R.id.tvTodayOne);
        tvTodayTwo = view.findViewById(R.id.tvTodayTwo);
        lastCheckedIn = view.findViewById(R.id.summery_checked_in_time);
        targetVisit = view.findViewById(R.id.tvTargetVisit);
        visitedPoint = view.findViewById(R.id.tvVisitedPoint);




        Log.d("workforce_daily_", "onCreateView: "+dailySummery);


        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String userType = sharedpreferences.getString(USERR_TYPE, AppUtil.USER_TYPE_SALES);
        String last = sharedpreferences.getString(LAST_CHECKED_IN,null);
        Log.d("workforce_daily", "onCreateView: "+dailySummery);

        arcStore.invalidate();

        if(last != null){
            lastCheckedIn.setText("Last check in : "+last);
        }
        else {

            DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
            String lastTime = dateFormat2.format(new Date()).toString();

            lastCheckedIn.setText("Last check in : "+lastTime);
        }

        fillData();
        if (userType.equalsIgnoreCase(AppUtil.USER_TYPE_MR)) {
            tvThisMonthOne.setText("Visit done : 57");
            tvThisMonthTwo.setText("Visit target : 85");
            tvTodayOne.setText("Visit done : 5");
            tvTodayTwo.setText("Visit target : 10");
        } else if (userType.equalsIgnoreCase(AppUtil.USER_TYPE_SALES)) {


            HashMap<String, String> dashboradData = new HashMap<String, String>();

            dashboradData = new AppDatabaseHelper(getContext()).getDashboardData();

            tvTodayOne.setText("Order done: "+dashboradData.get(AppDatabaseHelper.COLUMN_DAILY_NO_OF_ORDERS));
            tvTodayTwo.setText("Grand total: "+dashboradData.get(AppDatabaseHelper.COLUMN_DAILY_GRAND_TOTALS));
            tvThisMonthOne.setText("Order done: "+dashboradData.get(AppDatabaseHelper.COLUMN_MONTHLY_NO_OF_ORDERS));
            tvThisMonthTwo.setText("grand Total: "+dashboradData.get(AppDatabaseHelper.COLUMN_MONTHLY_GRAND_TOTALS));

            targetVisit.setText("Target Visit : "+dashboradData.get(AppDatabaseHelper.COLUMN_TARGETED_VISIT));
            visitedPoint.setText("Visited Point : "+dashboradData.get(AppDatabaseHelper.COLUMN_VISITED_POINT));




        } else if (userType.equalsIgnoreCase(AppUtil.USER_TYPE_ORDER)) {
            tvThisMonthOne.setText("Collection: 1,20,000");
            tvThisMonthTwo.setText("Target: 5,00,000");
            tvTodayOne.setText("Collection: 25,000");
            tvTodayTwo.setText("Target: 85,000");
        }

        return view;
    }

    @Override
    public void onResume() {
        try {

            // arcProcess.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onResume();
    }

    private void fillData() {

        HashMap<String, String> targetCalc = new HashMap<String, String>();

        targetCalc = new AppDatabaseHelper(getContext()).getTargetCalculation();

        Double total_target_value = Double.parseDouble(targetCalc.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_TARGET_VALUE));
        Double total_sale_value = Double.parseDouble(targetCalc.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_SALE_VALUE));
        int total_target_qty = Integer.parseInt(targetCalc.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_TARGET_QTY));
        int total_sale_qty = Integer.parseInt(targetCalc.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_SALE_QTY));






        double percentStore = ((total_sale_value / total_target_value) * 100);
        if(total_sale_value == 0.0 && total_target_qty == 0.0){
            percentStore = 0.0;
        }
        percent = percentStore;
        Log.d("workforce_summery", "fillData: "+total_target_qty+" "+total_sale_qty+" "+total_target_value+" "+total_sale_value+" "+(int)percentStore);



        timer = null;
        timer = new Timer();
        String job = " "+total_sale_qty+"/"+total_target_qty+" Qty";
        doneByTotalTextView.setText(job);
        arcStore.setProgress(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    if (arcStore.getProgress() >=  percent) {
                        timer.cancel();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (arcStore.getProgress() >=  percent) {
                                timer.cancel();
                            } else {
                                arcStore.setProgress(arcStore.getProgress() + 1);
                            }

                        }
                    });
                }

            }
        }, 50, 20);
        arcStore.setProgress((int) percent);
    }
}
