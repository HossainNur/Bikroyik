package com.inovex.bikroyik.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Target;
import com.inovex.bikroyik.adapter.TargetAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DELL on 12/11/2018.
 */


public class TargetActivity extends AppCompatActivity {


    Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;
    Context context;
    LinearLayout llBack;
    AppDatabaseHelper appDatabaseHelper;
    protected View view;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    TextView tvTargetQTy, tvSaleQty, tvTargetValues, tvSaleValues;

    ArrayList<HashMap<String, String>> dataListOne;
    ArrayList<Target> targetList = new ArrayList<Target>();
    HashMap<String, String> total_data = new HashMap<String, String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);


        context = this;
        appDatabaseHelper = new AppDatabaseHelper(context);
        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Target Info");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        llBack = mToolbar.findViewById(R.id.llBack);
        recyclerView = findViewById(R.id.target_recycler);
        tvTargetQTy = findViewById(R.id.tvTargetQty);
        tvSaleQty = findViewById(R.id.tvSaleQty);
        tvTargetValues = findViewById(R.id.tvTargetValues);
        tvSaleValues = findViewById(R.id.tvSaleValues);

        dataListOne = new ArrayList<>();

        dataListOne = appDatabaseHelper.getTargetData();
        populateData();

        TargetAdapter targetAdapter = new TargetAdapter(context, targetList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(targetAdapter);

        total_data = appDatabaseHelper.getTargetCalculation();

        double target_values = Double.parseDouble(total_data.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_TARGET_VALUE));
        double sale_values = Double.parseDouble(total_data.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_SALE_VALUE));


        int target_values1 = (int) target_values;
        int sale_values1 = (int) sale_values;

        String target_val = "Target Values : "+target_values1+"/-";
        String sale_val = "Sale Values : "+sale_values1+"/-";

        tvTargetQTy.setText("Target Qty : "+total_data.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_TARGET_QTY));
        tvSaleQty.setText("Sales Qty : "+total_data.get(AppDatabaseHelper.TARGET_PRODUCT_TOTAL_SALE_QTY));
        tvTargetValues.setText(target_val);
        tvSaleValues.setText(sale_val);







        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    private void populateData() {

        for(int i = 0; i < dataListOne.size(); i++){
            String name = dataListOne.get(i).get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_NAME);
            String target_qty = dataListOne.get(i).get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_TARGET_QTY);
            String sale_qty = dataListOne.get(i).get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_SALE_QTY);
            String target_values = dataListOne.get(i).get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_TARGET_VALUE);
            String sale_values = dataListOne.get(i).get(AppDatabaseHelper.COLUMN_TARGET_PRODUCT_SALE_VALUE);

            Target target = new Target(name,target_qty,sale_qty,target_values,sale_values);

            targetList.add(target);

        }

    }

}

