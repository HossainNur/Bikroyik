package com.inovex.bikroyik.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.OrderRecyclerAdapter;
import com.inovex.bikroyik.adapter.ProductOrder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderList  extends AppCompatActivity {

    ProductOrder productOrder;
    AppDatabaseHelper appDatabaseHelper;
    Context context;
    public static ArrayList<HashMap<String, String>> orderListDetails;
    ArrayList<ProductOrder> productOrders = new ArrayList<ProductOrder>();
    RecyclerView recycler_order_list;
    Toolbar mToolbar;
    TextView tvHomeToolbarTitle, proceed;
    public static TextView total_price_tv, total_discount_tv, total_price_without_discount;
    ImageView ivBackIcon;
    public double ground_total = 0, total_discount = 0;
    ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();
    HashMap<String, String> retailerListForMarket = new HashMap<String, String>();
    ArrayList<String> retailersName = new ArrayList<>();
    ArrayList<String> retailersId = new ArrayList<>();
    ArrayList<String> retailerNameList = new ArrayList<>();
    ArrayList<String> marketNameList = new ArrayList<>();
    ArrayList<String> marketIDList = new ArrayList<>();
    String market_name= null, retailer_name, retailer_id, payment_method = "Cash on delivery", advanced_paid = "0.0";
    public ArrayList<String> selected_id = new ArrayList<String>();
    String orderDetails = "";
    ProgressDialog progressDialog;




    public OrderList() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Order List");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        total_price_without_discount = findViewById(R.id.tv_Total_Price_without_discount);
        selected_id = null;

        context = this;
        appDatabaseHelper = new AppDatabaseHelper(context);
        retailersName.add("--- Select Retailer ---");
        retailersId.add("--- Select Retailer's ID ---");

        retailerList = appDatabaseHelper.getRetailerData();
        getMarketDetails();


        orderListDetails = appDatabaseHelper.getAllPerOrder();
        recycler_order_list = findViewById(R.id.order_list);

        ListForAdapter();

        OrderRecyclerAdapter orderRecyclerAdapter = new OrderRecyclerAdapter(productOrders,this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_order_list.setLayoutManager(layoutManager);
        recycler_order_list.setItemAnimator(new DefaultItemAnimator());
        recycler_order_list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recycler_order_list.setAdapter(orderRecyclerAdapter);


        total_price_tv = findViewById(R.id.tv_Total_Price);
        total_discount_tv = findViewById(R.id.tv_Total_Discount);
        Log.d("workforce", "ListForAdapter: "+ ground_total);
        Log.d("workforce", "grand total: "+ ground_total+", total discount: "+ total_discount+", grand_total+total_discount ="+(ground_total+total_discount));
        total_price_tv.setText(String.valueOf(ground_total));
        total_discount_tv.setText(String.valueOf(total_discount));
        total_price_without_discount.setText(String.valueOf(ground_total+total_discount));

        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProductsDirectoryActivity.class));
                //finish();
            }
        });

        proceed = findViewById(R.id.proceed);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(orderListDetails.size() == 0){
                    Toast.makeText(getApplicationContext(),"Please, add products first.", Toast.LENGTH_SHORT).show();
                }else {
                    finalizeOrder();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ProductsDirectoryActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }

    private void getMarketDetails() {

        marketIDList.add("-- Select Market ID --");
        marketNameList.add("Select Market Name ---");
        for(int i = 0; i < retailerList.size(); i++){
            String id = retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);
            if(!marketIDList.contains(id)) {
                marketIDList.add(id);
                marketNameList.add(retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME));
            }

        }

    }

    private void finalizeOrder() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_retailer_payment);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final TextView submit_order = dialog.findViewById(R.id.submit_order);



        Spinner retailerSpinner, market_spinner;
        retailerSpinner = dialog.findViewById(R.id.retailer_spinner);
        market_spinner = dialog.findViewById(R.id.market_spinner);


        ArrayAdapter <String> market_name_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,marketNameList);
        market_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter <String> retailer_name_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,retailersName);
        retailer_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        retailerSpinner.setAdapter(retailer_name_adapter);
        market_spinner.setAdapter(market_name_adapter);


        market_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                market_name = marketNameList.get(i);
                retailerListForMarket = appDatabaseHelper.getRetailerNameByMarket(market_name);
                retailersId.clear();
                retailersName.clear();
                getRetailerDetails();
                retailer_name_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        retailerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                retailer_name = retailersName.get(i);
                retailer_id = retailersId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialog.show();
        RadioGroup radioGroup = dialog.findViewById(R.id.radioGroup);
        final RadioButton advanced, delivery;
        advanced = dialog.findViewById(R.id.advanced);
        delivery = dialog.findViewById(R.id.cash_on_delivery);
        final LinearLayout ifAdvanced = dialog.findViewById(R.id.if_advanced);
        final TextView advanced_paid_editText = dialog.findViewById(R.id.advanced_paid);
        delivery.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(advanced.isChecked()) {
                    delivery.setChecked(false);
                    ifAdvanced.setVisibility(View.VISIBLE);
                }
                else {
                    advanced.setChecked(false);
                    ifAdvanced.setVisibility(View.GONE);
                }
            }
        });

        submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((advanced.isChecked() &&  advanced_paid_editText.getText().toString().length() == 0) || retailer_name.equals(retailersName.get(0))){
                    advanced_paid_editText.setError("Can't be blank.");
                } else if(advanced.isChecked() && Double.parseDouble(advanced_paid_editText.getText().toString()) > ground_total){
                    advanced_paid_editText.setError("Paying more than total. Please recheck.");
                } else {
                    if(advanced.isChecked()) {
                        payment_method = "Advanced";
                        advanced_paid = advanced_paid_editText.getText().toString();
                    } else {
                        advanced.setChecked(false);
                        ifAdvanced.setVisibility(View.GONE);
                    }
                    HashMap<String, String> retailers = new HashMap<String, String>();
                    retailers = appDatabaseHelper.getRetailerInfo(retailer_id);
                    String orderId = makeOrderId(retailer_id);
                    String retailerAddress = retailers.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);
                    String distributorId = retailers.get(AppDatabaseHelper.COLUMN_RETAILER_DISTRIBUTOR_ID);
                    String distributorName = retailers.get(AppDatabaseHelper.COLUMN_RETAILER_DISTRIBUTOR_NAME);
                    String market_id = retailers.get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);
                    String phone = retailers.get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);
                    String total = String.valueOf(ground_total+total_discount);

                    String deliveryDate = getTodayString();
                    String employeeID = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_ID);
                    progressDialog = new ProgressDialog(OrderList.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();

                    callSubmitOrderAPI(orderId, retailerAddress, distributorId, distributorName,
                            market_id,phone,total, deliveryDate,employeeID);

                }


            }
            private void callSubmitOrderAPI(final String orderId, final String retailerAddress, final String distributorId,
                                            final String distributorName, final String market_id, final String phone, final String total,
                                            final String deliveryDate, final String employeeID) {

                String URL = APIConstants.SUBMIT_ORDER_API;
                dialog.dismiss();
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("sales force", response.toString());


                        if(response.toString().contains("success")){
                            appDatabaseHelper.deleteAllPerProduct();
                            Log.d("workforce", "onResponse: success");
                            Intent intent = new Intent(new Intent(getApplicationContext(), FinalOrder.class));
                            intent.putExtra("details",orderDetails);
                            startActivity(intent);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError) {
                            Log.d("sales force", "location sending response: Network error");
                            Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ServerError) {
                            Log.d("sales force", "location sending response: Server error");
                            Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof AuthFailureError) {
                            Log.d("sales force", "location sending response: Auth. error");
                            Toast.makeText(getApplicationContext(), "Auth Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof ParseError) {
                            Log.d("sales force", "location sending response: Parse error");
                            Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                        } else if (error instanceof TimeoutError) {
                            Log.d("sales force", "location sending response: timeout error");
                            Toast.makeText(getApplicationContext(), "Timeout Error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        }

                        Log.d("sending_error", "location sending responseError:" + error.toString());
                        error.printStackTrace();
                        progressDialog.dismiss();
                        dialog.dismiss();


                    }
                }) {
                    @Override
                    public byte[] getBody() {
                        JSONObject jsonObject = new JSONObject();
                        String body = null;
                        try {
                            jsonObject.put(APIConstants.API_KEY_ORDER_ID,orderId);
                            jsonObject.put(APIConstants.API_KEY_ORDER_RETAILER_ID,retailer_id);
                            jsonObject.put(APIConstants.API_KEY_ORDER_RETAILER_NAME, retailer_name);
                            jsonObject.put(APIConstants.API_KEY_ORDER_RETAILER_ADDRESS, retailerAddress);
                            jsonObject.put(APIConstants.API_KEY_ORDER_DISTRIBUTOR_ID,distributorId);
                            jsonObject.put(APIConstants.API_KEY_ORDER_RETAILER_DISTRIBUTOR_NAME, distributorName);
                            jsonObject.put(APIConstants.API_KEY_ORDER_MARKET_ID, market_id);
                            jsonObject.put(APIConstants.API_KEY_ORDER_MARKET_NAME,market_name);
                            jsonObject.put(APIConstants.API_KEY_ORDER_RETAILER_PHONE, phone);
                            jsonObject.put(APIConstants.API_KEY_ORDER_TOTAL,Double.parseDouble(total));
                            jsonObject.put(APIConstants.API_KEY_ORDER_DISCOUNT,total_discount);
                            jsonObject.put(APIConstants.API_KEY_ORDER_GRAND_TOTAL,ground_total);
                            jsonObject.put(APIConstants.API_KEY_ORDER_PAYMENT_METHOD,payment_method);
                            jsonObject.put(APIConstants.API_KEY_ORDER_ADVANCED_PAYMENT,Double.parseDouble(advanced_paid));
                            jsonObject.put(APIConstants.API_KEY_ORDER_DELIVERY_DATE,deliveryDate);
                            jsonObject.put(APIConstants.API_KEY_ORDER_EMPLOYEE_ID,employeeID);
                            JSONArray productsArray = new JSONArray();
                            String id, name, quantity, price;

                            for(int i = 0; i < orderListDetails.size(); i++){

                                JSONObject productJson = new JSONObject();

                                id = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_ID);
                                name = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_TITLE);
                                quantity = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_QUANTITY);
                                price = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRICE);

                                try {
                                    productJson.put(APIConstants.API_KEY_ORDER_PRODUCT_ID,id);
                                    productJson.put(APIConstants.API_KEY_ORDER_PRODUCT_NAME, name);
                                    productJson.put(APIConstants.API_KEY_ORDER_PRODUCT_PRICE,Double.parseDouble(price));
                                    productJson.put(APIConstants.API_KEY_ORDER_PRODUCT_QUANTITY,Double.parseDouble(quantity));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                productsArray.put(productJson);

                            }
                            jsonObject.put(APIConstants.API_KEY_ORDER_DETAILS,productsArray);

                            body = jsonObject.toString();
                            orderDetails = body;
                            Log.d("Fake", "getBody: "+body);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        return body.getBytes(StandardCharsets.UTF_8);
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> header = new LinkedHashMap<String, String>();
                        header.put("Content-Type", "application/json");
                        return super.getHeaders();
                    }
                };

                requestQueue.add(jsonObjectRequest);



            }

        });


    }





    private String getTodayString() {

        String todayDateString = "";
        Calendar calendar = Calendar.getInstance(); // Returns instance with current date and time set
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        todayDateString = formatter.format(calendar.getTime());
        Log.d("workforce", "onClick: "+todayDateString);

        return todayDateString;
    }

    private void getRetailerDetails() {

        retailersId.add("--- Select Retailer ID ---");
        retailersName.add("--- Select Retailer Name ---");


        Iterator iterator = retailerListForMarket.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry me2 = (Map.Entry) iterator.next();
            retailersName.add(me2.getKey().toString());
            retailersId.add(me2.getValue().toString());
        }

    }

    private void ListForAdapter() {

        String id, name, quantity, price;

        for(int i = 0; i < orderListDetails.size(); i++){
            id = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_ID);
            name = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_TITLE);
            quantity = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_QUANTITY);
            price = orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_PRICE);

            ground_total += Double.parseDouble(price);
            total_discount += Double.parseDouble(orderListDetails.get(i).get(AppDatabaseHelper.COLUMN_ORDER_DISCOUNT));

            ProductOrder productOrder = new ProductOrder(id,name,quantity,price);
            productOrders.add(productOrder);
            Log.d("workforce", "ListForAdapter: "+price+"   "+ ground_total);
        }

    }

    private String makeOrderId(String retailerId) {


        String todayDateString = "";
        Calendar calendar = Calendar.getInstance(); // Returns instance with current date and time set
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        todayDateString = formatter.format(calendar.getTime());
        String order = "or-";

        return order+retailerId+ todayDateString;
    }



}
