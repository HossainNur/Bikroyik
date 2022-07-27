package com.inovex.bikroyik.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.SubmittedRetailerAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SubmittedRetailers extends AppCompatActivity {

    ArrayList< String> addedRetailerListCopy = new ArrayList<>();
    ArrayList<HashMap<String, String>> addedRetailerList = new ArrayList<HashMap<String, String>>();
    AppDatabaseHelper appDatabaseHelper;
    Context context;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submitted_retailers);
        this.setTitle("Submitted Retailers");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;
        appDatabaseHelper = new AppDatabaseHelper(context);
        recyclerView = findViewById(R.id.submitted_retail_recycler);

        addedRetailerList = appDatabaseHelper.getSubmittedRetailerData();
        Log.d("workforce_add_retailer", "onOptionsItemSelected: "+addedRetailerList);

        for(int y = 0; y < addedRetailerList.size(); y++){
            String market_name = addedRetailerList.get(y).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME);
            String retailer_name = addedRetailerList.get(y).get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
            String retailer_owner = addedRetailerList.get(y).get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
            String retailer_phone = addedRetailerList.get(y).get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);
            String retailer_address = addedRetailerList.get(y).get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);
            String retailer_type = addedRetailerList.get(y).get(AppDatabaseHelper.COLUMN_RETAILER_TYPE);

            addedRetailerListCopy.add("Retailer Type : "+retailer_type+"#"+getResources().getString(R.string.store_name)+" : "+retailer_name+"#"+
                    getResources().getString(R.string.retail_owner)+" : "+retailer_owner+"#"+getResources().getString(R.string.retail_phone)+" : "+
                    retailer_phone+"#"+"Market Name : "+" : "+market_name+"#"+getResources().getString(R.string.retail_address)+" : "+retailer_address);


        }
        Log.d("workforce_submitted", "onCreate: "+addedRetailerListCopy);

        SubmittedRetailerAdapter submittedRetailerAdapter = new SubmittedRetailerAdapter(context,addedRetailerListCopy);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(submittedRetailerAdapter);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), AddRetailer.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), AddRetailer.class));
        finish();
        return true;
    }


}
