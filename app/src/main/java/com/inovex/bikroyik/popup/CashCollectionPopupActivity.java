package com.inovex.bikroyik.popup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.model.DueCollection;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class CashCollectionPopupActivity extends Activity{

    private static final String tag = "_ccpa_";

    Context mContext;
    TextView tv_orderId, tv_retailerName, tv_orderDate, tv_totalAmount, tv_grandTotal, tv_discount, tv_collection, tv_due;
    EditText et_totalOrderAmount, et_receivedAmount, et_currentDue;
    Button btn_cancel, btn_submit;

    VolleyMethods volleyMethods;

    String orderId, total, receiveAmount, dueAmount;

    private ProgressDialog progressDialog;
    String currentDue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash_collection_popup_activity);

        viewInitialize();

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        volleyMethods = new VolleyMethods();

        //define size of the layout
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*0.95), (int)(height*0.7));


        if (getIntent().getSerializableExtra("dueCollectionObj") != null){
            DueCollection dueCollection = (DueCollection) getIntent().getSerializableExtra("dueCollectionObj");

            tv_retailerName.setText(""+dueCollection.getRetailerName());
            if (getIntent().getStringExtra("retailerName") != null){
                String retailerName = getIntent().getStringExtra("retailerName");
                tv_retailerName.setText(""+retailerName);
            }

            orderId =  String.valueOf(dueCollection.getOrderId());
            total = String.valueOf(dueCollection.getTotalAmount());

            dueAmount = String.valueOf(dueCollection.getTotalDue());
            Log.d("_due_adp_", "Due Collection popup -->> onClick : "+getIntent().getStringExtra("retailerName"));
            Log.d("_ccpopup_", "Retailer Name: "+dueCollection.getRetailerName());
            tv_orderId.setText("Order Id: "+orderId);
            tv_orderDate.setText("Order Date: "+dueCollection.getOrderDate());
            tv_totalAmount.setText("Total: "+total+" tk");
            tv_grandTotal.setText("Grand Total: "+dueCollection.getGrandTotal()+" tk");
            tv_discount.setText("Discount: "+dueCollection.getDiscount()+" tk");
            tv_due.setText("Due: "+dueAmount+" tk");

            long totalCollection = dueCollection.getGrandTotal() - dueCollection.getTotalDue();

            tv_collection.setText("Collection: "+totalCollection+" tk");

            et_totalOrderAmount.setText(""+dueCollection.getTotalAmount());
            et_currentDue.setText(""+dueCollection.getTotalDue());
        }else {
            Log.d("_ccpopup_", "orderId: 0000000");
            tv_orderId.setText("Order Id: 0000000");
            tv_retailerName.setText("Unknown");
            tv_orderDate.setText("Order Date: 10-8-2021");
            tv_totalAmount.setText("Total: 0000 tk");
            tv_grandTotal.setText("Grand Total: 0000 tk");
            tv_discount.setText("Discount: 0000 tk");
            tv_due.setText("Due: 0000 tk");

            dueAmount = "0.0";

            tv_collection.setText("Collection: 0000 tk");

            et_totalOrderAmount.setText("0.0");
            et_currentDue.setText("0.0");
        }

        et_receivedAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0){
                    String confirmReceive = et_receivedAmount.getText().toString().trim();
                    double confirmReceiveValue = Double.parseDouble(confirmReceive);
                    receiveAmount = ((long)confirmReceiveValue) + "";

                    double confirmTotalValue = Double.parseDouble(dueAmount);

                    long dueValue =(long) (confirmTotalValue - confirmReceiveValue);
                    currentDue = dueValue + "";
                    Log.d(tag, "Cash Collection Popup ---->> current due "+ currentDue);
                    et_currentDue.setText("" + dueValue);

                } else {
                    currentDue = dueAmount;
                    et_currentDue.setText("0.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(receiveAmount) && total != null && orderId != null){

                    if (Long.parseLong(receiveAmount) > 0){ //&& Double.parseDouble(dueAmount) >= 0.0
                        progressDialog.show();
                        String requestBody = prepareRequestBody(orderId, total, receiveAmount, currentDue);
                        sendCashCollectionDataToServer(getApplicationContext(), requestBody);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "please submit valid info", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void viewInitialize(){
        tv_orderId =(TextView) findViewById(R.id.tv_orderId_cashCollection);
        tv_retailerName = (TextView) findViewById(R.id.tvOrderConfirmRetailerName_cashCollection);
        tv_orderDate =  (TextView) findViewById(R.id.tvOrderConfirmOrderDate_cashCollection);
        tv_totalAmount = (TextView) findViewById(R.id.tvTotalAmount_cashCollection);
        tv_grandTotal = (TextView) findViewById(R.id.tvGrandTotal_cashCollection);
        tv_discount = (TextView) findViewById(R.id.tvDiscount_cashCollection);
        tv_due = (TextView) findViewById(R.id.tvTotalDue_cashCollection);
        tv_collection = (TextView) findViewById(R.id.tvTotalCollection_cashCollection);

        et_totalOrderAmount = (EditText) findViewById(R.id.etOrderConfirmTotalPrice_cashCollection);
        et_receivedAmount = (EditText) findViewById(R.id.etOrderConfirmCollection_cashCollection);
        et_currentDue = (EditText) findViewById(R.id.etOrderConfirmDueAmount_cashCollection);

        btn_submit = (Button) findViewById(R.id.btn_submit_cashCollection);
        btn_cancel =(Button) findViewById(R.id.btn_cancel_cashCollection);
    }

    private String prepareRequestBody(String orderId, String total, String receivedAmount, String dueAmount){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("orderId", orderId);
            jsonObject.put("total", total);
            jsonObject.put("recieveAmount", receivedAmount);
            jsonObject.put("dueAmount", dueAmount);
        } catch (JSONException e) {
            progressDialog.dismiss();
            e.printStackTrace();
        }

        Log.d(tag, "Cash Collection Popup ---->> requestBody "+ jsonObject.toString());
        return jsonObject.toString();
    }

    private void sendCashCollectionDataToServer(Context mContext, String requestBody){

        String url = APIConstants.COLLECTION_UPDATE_API;

        if (TextUtils.isEmpty(requestBody)){
            progressDialog.dismiss();
            return;
        }

        volleyMethods.sendPostRequestToServer(mContext, url, requestBody, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d(tag, "Cash Collection Popup ---->> volley callback result "+ result);
                et_currentDue.setText(String.valueOf(currentDue));

                progressDialog.dismiss();
            }
        });
    }
}
