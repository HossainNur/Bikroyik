package com.inovex.bikroyik.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.inovex.bikroyik.fragment.CallSchedulerFragment.CHANNEL_ID;

/**
 * Created by DELL on 8/12/2018.
 */

public class AlarmService extends Service {

    GPSTracker gps;
    Context mContext;
    AlarmServiceBinder alarmServiceBinder = new AlarmServiceBinder();
    int counter = 0;
    private SharedPreferences mSharedPrefs;
    ArrayList<HashMap<String, String>> locationDataList;
    AppDatabaseHelper appDatabaseHelper;
    ArrayList<HashMap<String, String>> scheduleDetails = new ArrayList<HashMap<String, String>>();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return alarmServiceBinder;
    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        final ScheduledExecutorService locationScheduler =
                Executors.newSingleThreadScheduledExecutor();

        locationDataList = new ArrayList<>();
        if (appDatabaseHelper == null) {
            appDatabaseHelper = new AppDatabaseHelper(mContext);
        }
        gps = new GPSTracker(getApplicationContext());



        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                scheduleDetails = appDatabaseHelper.getCallSchedulerData();
                ArrayList<String> timeList = new ArrayList<String>();
                for(int i = 0; i < scheduleDetails.size(); i++) {
                    String date = scheduleDetails.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_DATE);
                    String time = scheduleDetails.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_TIME);
                    String dateTime = date+" "+time;
                    timeList.add(dateTime);
                }

                String time_str = getTodayDateString();


                if(timeList.contains(time_str)){
                    String[] splitString = time_str.split(" ");
                    appDatabaseHelper = new AppDatabaseHelper(getApplicationContext());
                    ArrayList<HashMap<String, String>> callDetails =  appDatabaseHelper.getSingleScheduledCall(splitString[1]);
                    for(int i = 0; i < callDetails.size(); i++){
                        String name = callDetails.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_NAME);
                        String phone = callDetails.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_PHONE);

                        createNotificationChannel();
                        createNotifications(name,phone);

                        appDatabaseHelper.deleteSingleScheduledCall(phone);
                    }




                }


            }
        }, 0, 60, TimeUnit.SECONDS);



        return START_STICKY;
    }







    public String getTodayDateString() {
        String todayDateString = "";
        /*Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        todayDateString = year + "-" + month + "-" + day;*/

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = new Date(System.currentTimeMillis());
        todayDateString = formatter.format(date);

        return todayDateString;

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    private void createNotifications(String name, String phone) {

        //Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        Intent notificationIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.call_scheduling)
                .setContentTitle(name)
                .setContentText(phone)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());



    }


    public class AlarmServiceBinder extends Binder {

        public AlarmService getService() {
            return AlarmService.this;

        }
    }
}
