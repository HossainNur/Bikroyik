package com.inovex.bikroyik.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.FinalOrderDetailsAdapter;
import com.inovex.bikroyik.adapter.ProductOrder;

import java.util.ArrayList;
import java.util.HashMap;


public class FinalOrderDetailsActivity extends AppCompatActivity {

    String details = "";
    String orderId ;
    String retailerAddress;
    String distributorId;
    String distributorName;
    String market_id ;
    String phone ;
    String total ;
    String retailId;
    String retailName;
    String marketName;
    String contact;
    String discount;
    String grandTotal;
    String paymentMethod;
    String advancedPaid;
    String deliveryDate;
    String employeeID;
    String orders;
    String dues;
    String status;


    ArrayList<ProductOrder> productOrderArrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> productArrayList = new ArrayList<HashMap<String, String>>();


    TextView name, address, owner,delivery, order, totals, discounts,grandTotals, advanced,due, contactPhone, helpline, market_name, order_status;
    FinalOrderDetailsAdapter finalOrderAdapter;
    RecyclerView recyclerView;

    AppDatabaseHelper appDatabaseHelper;
    HashMap<String, String> employeeInfo = new HashMap<String, String>();
    Context context;
    HashMap<String, String> orderDetails = new HashMap<String, String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_orders_details);
        //setTitle("Order's Details");
        context = this;
        orderId = getIntent().getStringExtra("order");

        name = findViewById(R.id.retailer_name);
        address = findViewById(R.id.tv_storeContact);
        delivery = findViewById(R.id.tv_date);
        order = findViewById(R.id.order_id);
        recyclerView = findViewById(R.id.final_recycler);
        totals = findViewById(R.id.tv_Total_Price_without_discount);
        discounts = findViewById(R.id.tv_Total_Discount);
        grandTotals = findViewById(R.id.tv_Total_Price);
        advanced = findViewById(R.id.tv_totalPaid);
        due = findViewById(R.id.tv_Due);
        owner = findViewById(R.id.retail_owner);
        contactPhone = findViewById(R.id.tv_userContact);
        helpline = findViewById(R.id.tv_helpline);
        market_name = findViewById(R.id.market_name);
        order_status = findViewById(R.id.order_status);


        appDatabaseHelper = new AppDatabaseHelper(context);

        orderDetails = appDatabaseHelper.getFinalOrderDataByOrderId(orderId);

        total = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_TOTAL);
        discount = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_DISCOUNT);
        grandTotal = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_GRAND_TOTAL);
        paymentMethod  = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_PAYMENT_METHOD);
        advancedPaid = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_ADVANCED);
        deliveryDate = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_DELIVERY_DATE);
        dues = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_DUE);
        retailId = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_RETAIL_ID);
        status = orderDetails.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_STATUS);

        order_status.setText("Status : "+status);





        employeeInfo = appDatabaseHelper.getEmployeeInfo();
        String reporting_mobile = employeeInfo.get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE);
        helpline.setText(reporting_mobile);
        order.setText(orderId);

        HashMap<String, String> retailerInfo = appDatabaseHelper.getRetailerInfo(retailId);
        String owners = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
        String phones = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);
        retailName = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
        distributorId = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_DISTRIBUTOR_ID);
        distributorName = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_DISTRIBUTOR_NAME);
        market_id = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);
        marketName = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME);
        contact = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);
        retailerAddress  = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);

        Log.d("finalize_order", "onCreate: "+retailName+ " "+owners);


        name.setText(retailName);
        address.setText(retailerAddress);
        owner.setText(owners);
        contactPhone.setText(phones);
        delivery.setText(deliveryDate);
        order.setText(orderId);
        totals.setText(total+" ৳");
        discounts.setText(discount+" ৳");
        grandTotals.setText(grandTotal+" ৳");
        advanced.setText(advancedPaid+" ৳");
        due.setText(dues+" ৳");
        market_name.setText(marketName);

        productArrayList = appDatabaseHelper.getFinalOrderProductData(orderId);


        for(int i = 0; i < productArrayList.size(); i++ ){

            HashMap<String, String> product = new HashMap<String, String>();
            product = productArrayList.get(i);

            String productName = product.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_PRODUCT_NAME);
            String product_qty = product.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_QUANTITY);
            String price = product.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_PRODUCT_PRICE);
            String id = product.get(AppDatabaseHelper.COLUMN_FINAL_ORDER_PRODUCT_ID);
            ProductOrder productOrder = new ProductOrder(id,productName,product_qty,price);
            productOrderArrayList.add(productOrder);


        }

        finalOrderAdapter = new FinalOrderDetailsAdapter(productOrderArrayList, context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(finalOrderAdapter);




    }



}
