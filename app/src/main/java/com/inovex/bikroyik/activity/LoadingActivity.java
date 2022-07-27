package com.inovex.bikroyik.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.facebook.ads.AdView;
import com.inovex.bikroyik.activity.AddOrder;
import com.inovex.bikroyik.activity.MainActivity;
import com.inovex.bikroyik.activity.OrderList;

public class LoadingActivity  extends Activity {

    private AdView adView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String activityName = getIntent().getStringExtra("activity");
        if (activityName.equalsIgnoreCase("main")){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        else if(activityName.equalsIgnoreCase("products")){

            Intent intent = new Intent(getApplicationContext(), AddOrder.class);
            intent.putExtra("brand_name", getIntent().getStringExtra("brand_name"));
            startActivity(intent);
            finish();
        }
        else if(activityName.equalsIgnoreCase("orderList")){

            Intent intent = new Intent(getApplicationContext(), OrderList.class);
            startActivity(intent);
            finish();
        }
        else {
            startActivity(new Intent(getApplicationContext(),OrderList.class));
            finish();
        }



    }
}
