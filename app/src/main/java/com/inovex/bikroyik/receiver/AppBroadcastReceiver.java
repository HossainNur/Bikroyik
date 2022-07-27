package com.inovex.bikroyik.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.inovex.bikroyik.services.AppService;
import com.inovex.bikroyik.services.ForegroundService;


public class AppBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.v("_mdm_", "AppBroadcastReceiver firebase mdm called");
//        Toast.makeText(context, "Time Up... Now Vibrating !!!",
//                Toast.LENGTH_LONG).show();
//        Vibrator vibrator = (Vibrator) context
//                .getSystemService(Context.VIBRATOR_SERVICE);
//        vibrator.vibrate(2000);

        int backgroundLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            if (backgroundLocationPermission >= 0){
                Intent serviceIntent = new Intent(context, ForegroundService.class);
                ContextCompat.startForegroundService(context,serviceIntent);
            }

        }else {
            if (backgroundLocationPermission >= 0){
                Intent aIntent = new Intent(context, AppService.class);
                context.startService(aIntent);
            }
        }

    }
}
