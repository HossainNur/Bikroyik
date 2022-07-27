package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.UI.activity.MainActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.LoginActivity.DIST_ID;
import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.inovex.bikroyik.AppUtils.APICall;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USER_TYPE = "user";
    public static final String USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash2);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
       thread.start();

    }

}