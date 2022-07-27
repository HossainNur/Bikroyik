package com.inovex.bikroyik.services;

import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.inovex.bikroyik.AppUtils.Constants;

import static com.inovex.bikroyik.AppUtils.Constants.LOCATION_JOB_INFO_ID;


public class AppService extends Service {

    private Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        //create job scheduler for 15 min interval task
        Log.v("_mdm_","AppService onCreate called ");

        mContext = this.getApplicationContext();

        if (!Constants.isJobIdRunning(mContext, LOCATION_JOB_INFO_ID)){
            schedulingJob(mContext);
        }else {
            Log.d("_sf_", "location job service scheduler is already active.");
        }


    }

    public void schedulingJob(Context mContext){
        // job for location
        ComponentName componentName = new ComponentName(mContext, LocationJobService.class);
        JobInfo jobInfo = new JobInfo.Builder(LOCATION_JOB_INFO_ID, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)  // for periodic schedule add this line after this line  : .setPeriodic(60 * 60 * 1000)
                .setPeriodic(15 * 60 * 1000)
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(jobInfo);
        if(resultCode == scheduler.RESULT_SUCCESS){
            Log.d("_sf_", "location job successfully scheduled");
        }else {
            Log.d("_sf_", "location job schedule failed!");
        }



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("_mdm_", "AppService --> onStartCommand is called");
        schedulingJob(getApplicationContext());
        onDestroy();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("_mdm_", "AppService --> onDestroy Called");

//        try {
//            startService(new Intent(mContext, AppService.class));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}

