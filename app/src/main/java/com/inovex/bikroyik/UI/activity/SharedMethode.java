package com.inovex.bikroyik.UI.activity;

import android.content.Context;

final class SharedMethode {

    private static Context mContext;

    private static SharedMethode sharedMethode = new SharedMethode();

    private SharedMethode() {
        super();
    }

    public static SharedMethode getInstance() {
        return sharedMethode;
    }

    public void setContext(Context context) {
        if (mContext != null)
            return;

        mContext = context;
    }

    public boolean contextAssigned() {
        return mContext != null;
    }

    public Context getContext() {
        return mContext;
    }

    public static void freeContext() {
        mContext = null;
    }
}
