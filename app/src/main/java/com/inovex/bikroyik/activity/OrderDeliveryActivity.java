package com.inovex.bikroyik.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.OrderDelivery;
import com.inovex.bikroyik.adapter.OrderDeliveryRecycleAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.inovex.bikroyik.activity.SplashActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.SplashActivity.USER_ID;

/**
 * Created by DELL on 8/14/2018.
 */

public class OrderDeliveryActivity extends Activity {

    public Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;
    Context mContext, context;
    LinearLayout llBack;
    ImageView ivSyncOrderCollection;

    public static final int requestResultCode = 125;

    private List<OrderDelivery> orderDeliveryList = new ArrayList<>();
    private List<OrderDelivery> orderDeliveryListAll = new ArrayList<>();
    RecyclerView orderDeliveryRecycler;
    OrderDeliveryRecycleAdapter orderDeliveryRecycleViewAdapter;
    MaterialSpinner spinner;
    ArrayList<String> distributorNameList = new ArrayList<>();

    private ProgressDialog progressDialog;

    Map<String, String> marketList = new HashMap<String, String>();
    List<String> marketName = new ArrayList<String>();
    List<String> marketId = new ArrayList<String>();
    AppDatabaseHelper appDatabaseHelper;

    SharedPreferences sharedPreferences;
    String employeeId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_delivery);

        mContext = this;
        context = this;
        appDatabaseHelper = new AppDatabaseHelper(context);
        sharedPreferences = getSharedPreferences(MyPREFERENCES,MODE_PRIVATE);
        employeeId = sharedPreferences.getString(USER_ID,null);

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        prepareDataForMarketList();

        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Order delivery");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        llBack = mToolbar.findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        orderDeliveryRecycler = (RecyclerView) findViewById(R.id.pending_delivery_recycler);
        ivSyncOrderCollection = findViewById(R.id.ivSyncOrderCollection);


        spinner = (MaterialSpinner) findViewById(R.id.spinnerOrderDelivery);
        spinner.setDropdownMaxHeight(450);
        spinner.setItems(marketName);
        spinner.setSelectedIndex(0);
        progressDialog.dismiss();

        // prepareOrderDeliveryData();


        /// call api for data
        distributorNameList.clear();
        orderDeliveryList.clear();
        orderDeliveryListAll.clear();
        orderDeliveryRecycleViewAdapter = new OrderDeliveryRecycleAdapter(orderDeliveryList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        orderDeliveryRecycler.setLayoutManager(mLayoutManager);
        orderDeliveryRecycler.setItemAnimator(new DefaultItemAnimator());
        orderDeliveryRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        orderDeliveryRecycler.setAdapter(orderDeliveryRecycleViewAdapter);



        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                if(position == 0){
                    orderDeliveryList.clear();
                    orderDeliveryRecycleViewAdapter.notifyDataSetChanged();
                } else{
                    String market_id = marketId.get(position);
                    callOrderListApi(market_id);
                }

            }
        });


        ivSyncOrderCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                distributorNameList.clear();
                orderDeliveryList.clear();
                orderDeliveryListAll.clear();



            }
        });

    }

    private void callOrderListApi(String market_id) {

        progressDialog.show();

        String URL = APIConstants.COLLECTION_ORDER_API+employeeId+"/"+market_id;
        Log.d("workforce_order", "callOrderListApi: "+URL);
        RequestQueue retailerQueue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.length() > 0){

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        if(jsonArray != null && jsonArray.length() > 0){

                            appDatabaseHelper.deleteAllOrderDetails();;



                            for(int i = 0; i < jsonArray.length(); i++){

                                JSONObject object = jsonArray.getJSONObject(i);

                                String order_id = object.getString(APIConstants.API_KEY_ORDER_ID);
                                String retailId = object.getString(APIConstants.API_KEY_ORDER_RETAILER_ID);
                                String total = object.getString(APIConstants.API_KEY_ORDER_TOTAL);
                                String discount = object.getString(APIConstants.API_KEY_ORDER_DISCOUNT);
                                String grandTotal = object.getString(APIConstants.API_KEY_ORDER_GRAND_TOTAL);
                                String deliveryDate = object.getString(APIConstants.API_KEY_ORDER_DELIVERY_DATE);
                                String paymentMethod = object.getString(APIConstants.API_KEY_ORDER_PAYMENT_METHOD);
                                String advanced = object.getString(APIConstants.API_KEY_ORDER_ADVANCED_PAYMENT);
                                String due = object.getString("dueAmount");
                                String status = object.getString("orderStatus");
                                String deliveryMan = object.getString("deliveryMan");
                                String retailerAddress = object.getString("retailAddress");
                                String distributorId = object.getString("distributorId");
                                String distributor = object.getString("distributorName");
                                JSONArray array = object.getJSONArray("processedOrderHistory");
                                String orderDetails = array.toString();

                                HashMap<String, String> retailerInfo = new HashMap<String, String>();

                                retailerInfo = appDatabaseHelper.getRetailerInfo(retailId);

                                String lat = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_LATITUDE);
                                String lon = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_LONGITUDE);
                                String retailname = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
                                String retailOwner = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
                                String phone = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);

                                appDatabaseHelper.insertOrderDeliveryData(order_id,orderDetails,grandTotal,deliveryDate,total,
                                        discount,advanced,due,retailId,retailname,retailOwner,
                                        retailerAddress,lat,lon,phone,distributorId,distributor);

                                OrderDelivery orderDelivery = new OrderDelivery(order_id,deliveryDate,retailname,retailOwner,
                                        retailerAddress,status,phone,grandTotal,due,distributor);

                                orderDeliveryList.add(orderDelivery);

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                orderDeliveryRecycleViewAdapter.notifyDataSetChanged();
                progressDialog.dismiss();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NetworkError) {
                    Log.d("collection order err", "collection order  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("collection order", "collection order response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("collection order", "collection order response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("collection order", "collection order response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("collection order", "collection order response: timeout error");
                }

                Log.d("collection order", "collection order responseError:" + error.toString());
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });

        retailerQueue.add(stringRequest);

    }

    private void prepareDataForMarketList() {



        ArrayList<HashMap<String, String>> retailerInfo = appDatabaseHelper.getRetailerData();
        marketName.add("Select Market");
        marketId.add("---");
        for(int  i = 0; i < retailerInfo.size(); i++){

            String market_name = retailerInfo.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME);
            String market_id = retailerInfo.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);

            if(!marketId.contains(market_id)){
                marketId.add(market_id);
                marketName.add(market_name);
            }

        }

        //Log.d("workforce_order", "prepareDataForMarketList: "+marketId+"     "+marketName);


    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {

            case RESULT_OK:
                if (resultCode == requestResultCode) {
                    //  Toast.makeText(mContext, "request result", Toast.LENGTH_SHORT).show();
                    // callOrderApi();
                }

                break;

            case RESULT_CANCELED:

                // ... Handle this situation
                break;
        }
    }


}

