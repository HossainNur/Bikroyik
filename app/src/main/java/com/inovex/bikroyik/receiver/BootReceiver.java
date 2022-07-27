package com.inovex.bikroyik.receiver;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.inovex.bikroyik.AppUtils.CheckRunningService;
import com.inovex.bikroyik.services.AppService;
import com.inovex.bikroyik.services.ForegroundService;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class BootReceiver extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    private AlarmManager manager;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("_mdm_", "BootReceiver onReceive called");

        CheckRunningService checkRunningService = new CheckRunningService(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
                if (checkRunningService.isServiceRunning(AppService.class)){
                    Intent serviceIntent = new Intent(context, ForegroundService.class);
                    ContextCompat.startForegroundService(context,serviceIntent);
                }

            }else {

                if (!checkRunningService.isServiceRunning(AppService.class)){
                    Intent aIntent = new Intent(context, AppService.class);
                    context.startService(aIntent);
                }

            }
        }

        int timeInterval = 15*60*1000;
        startAlert(context, timeInterval);
    }


    public void startAlert(Context context, int intervalSec) {
        Intent intent = new Intent(context, AppBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + (2 * 1000), intervalSec
                , pendingIntent);

        Log.d("_mdm_", "(BootReceiver.java) Alarm will set in " + intervalSec + " seconds");

//            alarmManager.cancel(pendingIntent);
    }
}
