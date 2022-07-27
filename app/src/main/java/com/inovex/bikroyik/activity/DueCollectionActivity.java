package com.inovex.bikroyik.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.DueCollectionAdapter;
import com.inovex.bikroyik.model.DueCollection;
import com.inovex.bikroyik.model.Market;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by DELL on 11/15/2018.
 */

public class DueCollectionActivity extends AppCompatActivity {

    private static final String tag = "_due_";

    Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;

    Context mContext;

    ImageView ivSyncDueCollection;

    private ArrayList<DueCollection> dueArrayList = new ArrayList<>();
    private ArrayList<DueCollection> dueCollections = new ArrayList<>();

    AppDatabaseHelper appDatabaseHelper;

    RecyclerView dueOrderRecycler;
    DueCollectionAdapter dueOrderAdapter;
    MaterialSpinner spinner;

    ArrayList<Market> marketList = new ArrayList<>();
    ArrayList<String> marketNameList = new ArrayList<String>();

    String selectedMarketName = "Select Market";
    protected View view;

    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_collection);

        mContext = this;
        appDatabaseHelper = new AppDatabaseHelper(mContext);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        dueCollectionJsonParser(mContext);


        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Due Collection");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        ivSyncDueCollection =(ImageView) findViewById(R.id.ivSyncDueCollection_due_connection);


        ivSyncDueCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                dueCollectionJsonParser(getApplicationContext());

                for (int i=0; i<marketNameList.size(); i++){
                    if (marketNameList.get(i).equals(selectedMarketName)){
                        spinner.setSelectedIndex(i);
//                        getAllDueOfTheMarket(selectedMarketName);
//                        dueOrderAdapter.notifyDataSetChanged();
                    }
                }

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Sync button presed, Selected :"+selectedMarketName, Toast.LENGTH_SHORT).show();
            }
        });

        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });



        dueOrderRecycler = (RecyclerView) findViewById(R.id.due_collection_recycler);
        ivSyncDueCollection = findViewById(R.id.ivSyncOrderCollection);


        spinner = (MaterialSpinner) findViewById(R.id.spinnerDueCollection);
        spinner.setDropdownMaxHeight(450);
        spinner.setSelectedIndex(0);


        dueArrayList.clear();
        getAllDueOfTheMarket("Select Name");
        dueOrderAdapter = new DueCollectionAdapter(this, dueCollections );
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        dueOrderRecycler.setLayoutManager(mLayoutManager);
        dueOrderRecycler.setItemAnimator(new DefaultItemAnimator());
        dueOrderRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        dueOrderRecycler.setAdapter(dueOrderAdapter);


        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(position == 0){
                    String market_name = marketNameList.get(position);
                    getAllDueOfTheMarket(market_name);
                    selectedMarketName = market_name;
//                    dueOrderAdapter.setItems(dueCollections);
                    dueOrderAdapter.notifyDataSetChanged();
                } else{
                    String market_name = marketNameList.get(position);
                    getAllDueOfTheMarket(market_name);
                    selectedMarketName = market_name;
//                    dueOrderAdapter.setItems(dueCollections);
                    dueOrderAdapter.notifyDataSetChanged();
                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareMarketNameList(marketList);
        spinner.setItems(marketNameList);
    }

    private void dueCollectionJsonParser(Context context){
        String URL = APIConstants.DUE_ORDER_API;

        VolleyMethods volleyMethods = new VolleyMethods();
        volleyMethods.sendGetRequest(context, URL, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d(tag, "result :"+result.toString());
                try {
                    JSONObject rootJsonObject = new JSONObject(result);
                    long statusCode = rootJsonObject.getLong("statusCode");

                    if (statusCode == 201){
                        JSONArray data = rootJsonObject.getJSONArray("data");

                        if (data != null && data.length() > 0){
                            dueArrayList.clear();
                            marketList.clear();

                            Log.d(tag, "data array length : "+data.length());
                            for (int i=0; i<data.length(); i++){
                                JSONObject jsonObject = data.getJSONObject(i);

                                String market_id = jsonObject.getString("marketId");
                                String retailer_id = jsonObject.getString("retailId");
                                String market_name = jsonObject.getString("marketName");
                                String retailer_name = jsonObject.getString("retailName");

                                //make a marketList
                                if (i > 0){
                                    boolean marketNOTExistInList = true;
                                    for (int x = 0; x < marketList.size(); x++){
                                        if (market_id.equals(marketList.get(x).getMarketId())){
                                            marketNOTExistInList = false;
                                        }
                                    }
                                    if (marketNOTExistInList){
                                        Market market = new Market(market_id, market_name);
                                        marketList.add(market);
                                    }
                                }else {
                                    Market market = new Market(market_id, market_name);
                                    marketList.add(market);
                                }


                                JSONArray ordersArray = jsonObject.getJSONArray("orders");

                                if (ordersArray != null && ordersArray.length() > 0){
                                    for (int j=0; j<ordersArray.length(); j++){
                                        JSONObject dueOrder = ordersArray.getJSONObject(i);

                                        if (dueOrder != null && dueOrder.length() > 0){
                                            long dueAmount = dueOrder.getLong("due");
                                            if (dueAmount >= 0){
                                                String orderId = dueOrder.getString("orderId");
                                                long total = dueOrder.getLong("total");
                                                long discount = dueOrder.getLong("discount");
                                                long grandTotal = dueOrder.getLong("grandTotal");
                                                String oerderDate = dueOrder.getString("oerderDate");

                                                DueCollection dueCollection = new DueCollection(market_id, market_name, retailer_id, retailer_name, orderId,
                                                        oerderDate, total, grandTotal, discount, dueAmount );
                                                dueArrayList.add(dueCollection);
                                            }
                                        }
                                    }
                                }
                            }
                            prepareMarketNameList(marketList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("error>>" , "error json parsing -->>>"+e.toString());
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void prepareMarketNameList(ArrayList<Market> marketList){
        marketNameList.clear();
        marketNameList.add(0, "Select Market");
        if (marketList != null && marketList.size() > 0){
            Log.d(tag, "get list of markets");
            for (int i =0; i<marketList.size(); i++){
                marketNameList.add(marketList.get(i).getMarketName());
            }
        }
    }

    private void  getAllDueOfTheMarket(String marketName){
        dueCollections.clear();
        for (int i=0; i<dueArrayList.size(); i++){
            if (dueArrayList.get(i).getMarketName().equals(marketName)){
                dueCollections.add(dueArrayList.get(i));
            }
        }
        Log.d(tag, "dueCollection size : "+dueCollections.size());
        Log.d(tag, "due collection array : "+dueCollections.toString());
    }


}
