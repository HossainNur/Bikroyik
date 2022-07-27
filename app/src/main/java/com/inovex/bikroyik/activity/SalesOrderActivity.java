package com.inovex.bikroyik.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.OrderHistory;
import com.inovex.bikroyik.adapter.OrderHistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_DATE;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_GRAND_TOTAL;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_ID;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_RETAILER_ID;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_RETAILER_NAME;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_STATUS;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_SALES_ORDER_TIME;

/**
 * Created by DELL on 8/7/2018.
 */


public class SalesOrderActivity extends AppCompatActivity {

    private static final int ADD_NEW_REQUEST_CODE = 139;
    private static final int UPDATE_REQUEST_CODE = 457;
    public Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;
    Context mContext;
    LinearLayout llBack;
    RelativeLayout rlNewSalesOrder;
    LinearLayout llSyncOrderList;


    private List<OrderHistory> orderHistoryList;
    RecyclerView orderHistoryRecycler;
    OrderHistoryAdapter orderHistoryAdapter;
    ProgressDialog progressDialog;
    String sRID;
    String distributorId;
    AppDatabaseHelper appDatabaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_order);
        mContext = this;


        if (appDatabaseHelper == null) {
            appDatabaseHelper = new AppDatabaseHelper(mContext);
        }
        sRID = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_ID);
        distributorId = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_REPORTING_ID);

        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Sales Order");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        llBack = mToolbar.findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Syncing data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        orderHistoryList = new ArrayList<>();


        rlNewSalesOrder = findViewById(R.id.rlNewSalesOrder);
        llSyncOrderList = findViewById(R.id.llSyncOrderList);
        orderHistoryRecycler = (RecyclerView) findViewById(R.id.sales_history_recycler);
        Log.v("_sf", " order List size " + orderHistoryList.size());
        orderHistoryAdapter = new OrderHistoryAdapter(mContext, orderHistoryList);
        orderHistoryRecycler.setAdapter(orderHistoryAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        orderHistoryRecycler.setLayoutManager(mLayoutManager);
        orderHistoryRecycler.setItemAnimator(new DefaultItemAnimator());
        orderHistoryRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        prepareOrderHistoryData();


        //progressDialog.show();
        callRetailerApi(sRID, distributorId);
        rlNewSalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesOrderActivity.this, NewSalesOrderActivity.class);
                startActivityForResult(intent, ADD_NEW_REQUEST_CODE);
            }
        });

        llSyncOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // send the order to server if not sent (status is pending)

                //  prepareOrderHistoryData();
                // if net connected then do :
                if (AppUtil.isNetworkAvailable(mContext)) {
                    //  progressDialog.show();
                    // callApiForOrderHistory(mContext, sRID);

                    // search all pending order from database and send to server


                } else {
                    Toast.makeText(mContext, "No internet connection ", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private void callApiForOrderHistory(Context mContext, String sRID) {
        final String sRid = sRID;

        String URL = APIConstants.ORDER_HISTORY_API;
        RequestQueue orderHistoryRequestQueue = Volley.newRequestQueue(mContext);
        // prepare the Request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("product response", " response:" + response.toString());
                try {
                    JSONArray jsonArr = new JSONArray(response.toString());
                    if (jsonArr.length() > 0) {
                        orderHistoryList.clear();
                    }

                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONArray childArray = jsonArr.getJSONArray(i);
                        System.out.println(childArray);

                        String orderId = childArray.get(0).toString();
                        String orderDate = childArray.get(1).toString();
                        String orderTime = childArray.get(2).toString();
                        String orderStatus = childArray.get(3).toString();
                        String orderProductQuantity = childArray.get(4).toString();
                        String orderAmount = childArray.get(4).toString();


                        // insert to database
                        // OrderHistory orderHistory=new OrderHistory(orderId,)


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


                if (error instanceof NetworkError) {
                    Log.d("product response err", "product  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("product response", "product response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("product response", "product response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("product response", "product response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("product response", "product response: timeout error");
                }

                Log.d("product response", "product responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APIConstants.API_KEY_DISTRIBUTOR_ID, sRid);

                return params;
            }
        };

        orderHistoryRequestQueue.add(stringRequest);
    }


    private void prepareOrderHistoryData() {
        orderHistoryList.clear();
        OrderHistory orderHistory;
        ArrayList<HashMap<String, String>> orderHistoryDBList = appDatabaseHelper.getOrderData();

        if (orderHistoryDBList != null) {
            Log.v("_sf", "db order List size " + orderHistoryDBList.size());
            for (int x = 0; x < orderHistoryDBList.size(); x++) {
                HashMap<String, String> orderMap = orderHistoryDBList.get(x);
                String orderId = orderMap.get(COLUMN_SALES_ORDER_ID);
                String orderDate = orderMap.get(COLUMN_SALES_ORDER_DATE);
                String orderTime = orderMap.get(COLUMN_SALES_ORDER_TIME);
                String orderStatus = orderMap.get(COLUMN_SALES_ORDER_STATUS);
                Double orderAmount = Double.parseDouble(orderMap.get(COLUMN_SALES_ORDER_GRAND_TOTAL));
                String retailerName = orderMap.get(COLUMN_SALES_ORDER_RETAILER_NAME);
                String retailerId = orderMap.get(COLUMN_SALES_ORDER_RETAILER_ID);
                orderHistory = new OrderHistory(orderId, retailerName, orderDate, orderTime, orderStatus, orderAmount, retailerId);
                orderHistoryList.add(orderHistory);

            }

            Log.v("_sf", "order history List size " + orderHistoryList.size());
            orderHistoryAdapter.notifyDataSetChanged();
        } else {
            Log.v("_sf", "db order List null ");
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {

                prepareOrderHistoryData();

            }
        } else if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                prepareOrderHistoryData();
            }
        }
    }


    private void callRetailerApi(final String employeeId, final String distributorId) {

        //  final String distributorId = employeeId;

        String URL = APIConstants.RETAILER_API;
        RequestQueue retailerQueue = Volley.newRequestQueue(mContext);
        // prepare the Request
        //appDatabaseHelper.deleteAllRetailerData();
        StringRequest retailerStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("retailer api response", " response:" + response.toString());
                try {
                    JSONArray jsonArr = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);

                        String retailerId = jsonObj.getString(APIConstants.API_KEY_RETAILER_ID);
                        String retailerName = jsonObj.getString(APIConstants.API_KEY_RETAILER_NAME);
                        String retailerAddress = jsonObj.getString(APIConstants.API_KEY_RETAILER_ADDRESS);
                        String retailerPhone = jsonObj.getString(APIConstants.API_KEY_RETAILER_PHONE);
                        String retailerOwner = jsonObj.getString(APIConstants.API_KEY_RETAILER_OWNER);
                        String retailerLatitude = jsonObj.getString(APIConstants.API_KEY_RETAILER_LATITUDE);
                        String retailerLongitude = jsonObj.getString(APIConstants.API_KEY_RETAILER_LONGITUDE);
                        String retailerDistributorName = jsonObj.getString(APIConstants.API_KEY_RETAILER_DISTRIBUTOR_NAME);

                        //appDatabaseHelper.insertRetailerData(retailerId, retailerName, retailerAddress, retailerPhone, retailerOwner, retailerLatitude, retailerLongitude, retailerDistributorName);

                        System.out.println(jsonObj);

                    }

                    //call product api
                    //callProductAPI(distributorId);

                    //  progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Log.d("retailer response err", "retailer  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("retailer response", "retailer response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("retailer response", "retailer response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("retailer response", "retailer response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("retailer response", "retailer response: timeout error");
                }

                Log.d("retailer response", "retailer responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APIConstants.API_KEY_REPORTING_ID, employeeId);

                return params;
            }
        };

        retailerStringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        retailerQueue.getCache().clear();
        retailerQueue.add(retailerStringRequest);

    }

    private void callProductAPI(final String distributorId) {


        // final String distributorId = "DIS2209767504";
        appDatabaseHelper.deleteAllProductData();

        String URL = APIConstants.PRODUCT_API;
        RequestQueue retailerQueue = Volley.newRequestQueue(mContext);
        // prepare the Request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("product response", " response:" + response.toString());
                try {
                    JSONArray jsonArr = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONArray childArray = jsonArr.getJSONArray(i);
                        System.out.println(childArray);

                        String productID = childArray.get(0).toString();
                        String productTitle = childArray.get(1).toString();
                        String productStock = childArray.get(2).toString();
                        String productPrice = childArray.get(3).toString();
                        String productDiscount = childArray.get(4).toString();

                        //appDatabaseHelper.insertProductData(productID, productTitle, productStock, productPrice, productDiscount);
                    }

                    //callRouteApi("3936266807", mContext);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


                if (error instanceof NetworkError) {
                    Log.d("product response err", "product  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("product response", "product response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("product response", "product response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("product response", "product response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("product response", "product response: timeout error");
                }

                Log.d("product response", "product responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APIConstants.API_KEY_DISTRIBUTOR_ID, distributorId);

                return params;
            }
        };

        retailerQueue.add(stringRequest);
    }

    private void callApiForNewOrderSubmit(final String orderId, final String orderDate, final String orderTime, final String orderStatus, final String total,
                                          final String discount, final String grandTotal, final String srId, final String retailerId, final String retailerName,
                                          final String retailerAddress, final String contactPhone, final String distributorName, final String orderDetails
    ) {


        progressDialog.show();
        String URL = APIConstants.ORDER_API;
        RequestQueue retailerQueue = Volley.newRequestQueue(mContext);
        // prepare the Request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                Log.d("order response", " response:" + response.toString());
                if (response.toString().contains("success")) {
                    // save in local data with status: submitted
                    //    saveOrderInLocalDatabase(orderId, orderDate, orderTime, orderStatus, total + "", discount, grandTotal, srId, retailerId, retailerName, retailerAddress, contactPhone, distributorName);
                    Toast.makeText(mContext, "Order submitted succesfully", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent();
//                        setResult(RESULT_OK, i);
//                        finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


                if (error instanceof NetworkError) {
                    Log.d("order response err", "order  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("order response", "order response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("order response", "order response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("order response", "order response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("order response", "order response: timeout error");
                }

                Log.d("order response", "order responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put(APIConstants.API_KEY_ORDER_ID, orderId);
                params.put(APIConstants.API_KEY_ORDER_RETAILER_ID, retailerId);
                params.put(APIConstants.API_KEY_ORDER_RETAILER_NAME, retailerName);
                params.put(APIConstants.API_KEY_ORDER_RETAILER_ADDRESS, retailerAddress);
                params.put(APIConstants.API_KEY_ORDER_RETAILER_DISTRIBUTOR_NAME, distributorName);
                params.put(APIConstants.API_KEY_ORDER_RETAILER_PHONE, contactPhone);

                params.put(APIConstants.API_KEY_ORDER_TOTAL, total + "");
                params.put(APIConstants.API_KEY_ORDER_DISCOUNT, discount);
                params.put(APIConstants.API_KEY_ORDER_GRAND_TOTAL, grandTotal + "");
                params.put(APIConstants.API_KEY_ORDER_DELIVERY_DATE, orderDate);
                params.put(APIConstants.API_KEY_ORDER_DETAILS, orderDetails);

                return params;
            }
        };

        retailerQueue.add(stringRequest);


    }


//    private void saveOrderInLocalDatabase(String orderId, String orderDate, String orderTime, String orderStatus, String total, String discount,
//                                          String grandTotal, String srId, String retailerId, String retailerName,
//                                          String retailerAddress, String contactPhone, String distributorName) {
//
//
//        appDatabaseHelper.insertOrder(orderId, orderDate, orderTime, orderStatus, total, discount, grandTotal, orderDate, srId, retailerId, retailerName, retailerAddress, contactPhone, distributorName);
//        for (int x = 0; x < newOrderList.size(); x++) {
//            NewOrderItem newOrderItem = newOrderList.get(x);
//            Log.v("_sf", "  newSales Actvt saveOrderInLocalDatabase product price: " + newOrderItem.getTotalPrice());
//            appDatabaseHelper.insertOrderProduct(orderId, newOrderItem.getNewOrderItemId(), newOrderItem.getName(), newOrderItem.getQuantity() + "", newOrderItem.getTotalPrice() + "");
//
//        }
//        callTimerThread("order saved to  database");
//        progressDialog.dismiss();
//
//    }


//    private void callTimerThread(final String message) {
//
//
//        final int interval = 2000; // 1 Second
//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            public void run() {
//                progressDialog.dismiss();
//                productList = appDatabaseHelper.getProductData();
//                productNameList = getProductNameList(productList);
//
//                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//            }
//        };
//        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
//        handler.postDelayed(runnable, interval);
//
//    }
}

