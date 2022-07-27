package com.inovex.bikroyik.UI.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                WifiManager.WIFI_STATE_UNKNOWN);

        switch(extraWifiState) {
            case WifiManager.WIFI_STATE_DISABLED:
                //do something
                break;
            case WifiManager.WIFI_STATE_ENABLED:
                //do something
                break;
            case WifiManager.WIFI_STATE_ENABLING:
                //do something
                break;
            case WifiManager.WIFI_STATE_DISABLING:
                //do something
                break;
            case WifiManager.WIFI_STATE_UNKNOWN:
                //do something with data if you desire so, I found it unreliable until now so i've done nothing with it
        }
    }
}