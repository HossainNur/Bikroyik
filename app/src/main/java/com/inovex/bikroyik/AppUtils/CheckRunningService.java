package com.inovex.bikroyik.AppUtils;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by DELL on 8/12/2018.
 */
public class CheckRunningService {


    private Context mContext=null;
    private static CheckRunningService myServiceRunningSingleton = null;

    /*
     * A private Constructor prevents any other class from instantiating.
     */
    public CheckRunningService(Context _mContext) {
        this.mContext = _mContext;
    }

    /* Static 'instance' method */
    public static CheckRunningService getInstance(Context _mContext) {
        return (myServiceRunningSingleton == null ? myServiceRunningSingleton = new CheckRunningService(
                _mContext) : myServiceRunningSingleton);
    }

    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager
                .getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

