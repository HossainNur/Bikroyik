package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.SubmittedRetailerAdapter;

import java.util.ArrayList;
import java.util.HashMap;


public class SubmittedRetailerFragment extends Fragment {

    ArrayList< String> addedRetailerListCopy = new ArrayList<>();
    ArrayList<HashMap<String, String>> addedRetailerList = new ArrayList<HashMap<String, String>>();
    AppDatabaseHelper appDatabaseHelper;
    Context context;
    RecyclerView recyclerView;

    public SubmittedRetailerFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        appDatabaseHelper = new AppDatabaseHelper(context);

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_submitted_retailer, container, false);
        recyclerView = view.findViewById(R.id.submitted_retail_recycler);
        SubmittedRetailerAdapter submittedRetailerAdapter = new SubmittedRetailerAdapter(context,addedRetailerListCopy);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(submittedRetailerAdapter);


        return view;
    }
}