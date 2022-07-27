package com.inovex.bikroyik.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.inovex.bikroyik.AppUtils.CheckRunningService;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;


public class ForegroundService extends Service {

    public static final String CHANNEL_ID = "sf_service_channel";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("_mdm_","ForegroundService onCreate called ");

        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("DEVICE MANAGER")
                .setSmallIcon(R.drawable.notification_64)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        CheckRunningService checkRunningService = new CheckRunningService(getApplicationContext());

        if (!checkRunningService.isServiceRunning(AppService.class)){
            Intent serviceIntent = new Intent(getApplicationContext(), AppService.class);
            startService(serviceIntent);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("_mdm_","ForegroundService onDestroy called");
        stopForeground(true);
        stopSelf();
//        stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mdmServiceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "MDM Service Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(mdmServiceChannel);
        } else {

        }
    }
}