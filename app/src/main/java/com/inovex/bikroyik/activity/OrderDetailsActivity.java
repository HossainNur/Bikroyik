package com.inovex.bikroyik.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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
import com.inovex.bikroyik.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by DELL on 8/14/2018.
 */

public class OrderDetailsActivity extends Activity {
    TextView tvOrderDetailsOrderId;
    public Toolbar mToolbar;
    TextView tvHomeToolbarTitle,due;
    ImageView ivBackIcon;
    Context mContext;
    LinearLayout llBack;
    TextView tvOrderConfirmOrderId;
    TextView tvOrderConfirmOrderDate;
    TextView tvOrderConfirmOrderRetailer;
    TextView tvOrderConfirmPOrderRetailerContact;
    TextView tvOrderConfirmRetailerAddress;
    TextView tvOrderConfirmOrderTotal;
    EditText etOrderConfirmTotalPrice;
    EditText etOrderConfirmCollection;
    EditText etOrderConfirmDueAmount;
    TextView tvOrderConfirmSubmit;
    ProgressDialog progressDialog;
    String orderId;
    String orderDate;
    String orderRetailerName;
    String orderRetailerContact;
    String orderRetailerAddress;
    String orderToatlAmount;
    String dueAmount = "";


    String duevalueAmount;
    String receivedAmount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        tvOrderConfirmOrderId = findViewById(R.id.tvOrderConfirmOrderId);
        tvOrderConfirmOrderDate = findViewById(R.id.tvOrderConfirmOrderDate);
        tvOrderConfirmOrderRetailer = findViewById(R.id.tvOrderConfirmRetailerName);
        tvOrderConfirmPOrderRetailerContact = findViewById(R.id.tvOrderConfirmRetailerContact);
        tvOrderConfirmRetailerAddress = findViewById(R.id.tvOrderConfirmRetailerAddress);
        tvOrderConfirmOrderTotal = findViewById(R.id.tvOrderConfirmOrderAmount);
        etOrderConfirmTotalPrice = findViewById(R.id.etOrderConfirmTotalPrice);
        etOrderConfirmCollection = findViewById(R.id.etOrderConfirmCollection);
        etOrderConfirmDueAmount = findViewById(R.id.etOrderConfirmDueAmount);
        tvOrderConfirmSubmit = findViewById(R.id.tvOrderConfirmSubmit);
        due = findViewById(R.id.tvOrderConfirmOrderDueAmount);

        mContext = this;

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);


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


        Intent intent = getIntent();
        orderId = intent.getExtras().getString("order_id");
        orderDate = intent.getExtras().getString("order_date");
        orderRetailerName = intent.getExtras().getString("order_retailer");
        orderRetailerContact = intent.getExtras().getString("order_contact");
        orderRetailerAddress = intent.getExtras().getString("order_address");
        orderToatlAmount = intent.getExtras().getString("order_total");
        dueAmount = intent.getStringExtra("order_due");

        Log.d("workforce_due", "onCreate: "+dueAmount);


        tvOrderConfirmOrderId.setText("Order #" + orderId);
        tvOrderConfirmOrderDate.setText("Order date: " + orderDate);
        tvOrderConfirmOrderRetailer.setText(orderRetailerName);
        tvOrderConfirmPOrderRetailerContact.setText("Contact: " + orderRetailerContact);
        tvOrderConfirmRetailerAddress.setText("Address:" + orderRetailerAddress);
        tvOrderConfirmOrderTotal.setText("Order:" + orderToatlAmount + " tk");
        etOrderConfirmTotalPrice.setText(orderToatlAmount);
        due.setText("Due: "+dueAmount+" tk");

        etOrderConfirmCollection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() != 0) {
                    String confirmCollection = etOrderConfirmCollection.getText().toString();
                    double confirmCollectionValue = Double.parseDouble(confirmCollection);
                    receivedAmount = confirmCollectionValue + "";

                    String confirmTotal = due.getText().toString();
                    double confirmTotalValue = Double.parseDouble(dueAmount);

                    double dueValue = confirmTotalValue - confirmCollectionValue;
                    duevalueAmount = dueValue + "";
                    etOrderConfirmDueAmount.setText("" + dueValue);


                } else {
                    etOrderConfirmDueAmount.setText("0.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        tvOrderConfirmSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String collectionAmount = etOrderConfirmCollection.getText().toString();

                if (collectionAmount.length() > 0) {
                    double collection = Double.parseDouble(collectionAmount);
                    progressDialog.show();
                    callCollectionApi(orderId, orderToatlAmount, receivedAmount, duevalueAmount, getCuurrentDate());

                  //  Toast.makeText(mContext, "Collection submitted ", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(mContext, "Enter collection amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void callCollectionApi(final String orderId, final String orderToatlAmount, final String receivedAmount, final String duevalueAmount, final String cuurrentDate) {
        String URL = APIConstants.COLLECTION_UPDATE_API;

        RequestQueue retailerQueue = Volley.newRequestQueue(mContext);
        // prepare the Request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d("workforce_due", "onResponse: "+response);
                Log.d("collection update ", " response:" + response.toString());
                if (response.toString().contains("success")) {
                    Toast.makeText(mContext, "Order submitted succesfully", Toast.LENGTH_SHORT).show();

                    finish();
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
            public byte[] getBody() {
                JSONObject params = new JSONObject();
                String body = null;
                try {
                    params.put(APIConstants.COLLECTION_UPDATE_API_KEY_ORDER_ID, orderId);
                    params.put(APIConstants.COLLECTION_UPDATE_API_KEY_TOTAL, orderToatlAmount);
                    params.put(APIConstants.COLLECTION_UPDATE_API_KEY_RECEIVE_AMOUNT, receivedAmount);
                    params.put(APIConstants.COLLECTION_UPDATE_API_KEY_DUE_AMOUNT, duevalueAmount);


                    body = params.toString();
                    Log.d("workforce", "getBody: "+body);

                }catch (JSONException e){
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

        retailerQueue.add(stringRequest);
    }

    private String getCuurrentDate() {
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        return date;
    }


    private void callTimerThread(final String message) {


        final int interval = 4000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                progressDialog.dismiss();
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

    }
}
