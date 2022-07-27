package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.OrderHistoryAdapter;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.interfaces.CallbackOrderModel;
import com.inovex.bikroyik.viewmodel.OrderHistoryViewModel;

import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    RecyclerView orderList_recycler;
    OrderHistoryAdapter orderHistoryAdapter;

    CallbackOrderModel callbackOrderModel;
    OrderHistoryViewModel orderHistoryViewModel;
    TextView toolbar_title;
    ImageView btn_imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_order_history);

        orderList_recycler = (RecyclerView) findViewById(R.id.orderList_recycler);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        btn_imageBack = (ImageView) findViewById(R.id.btn_imageBack);
        toolbar_title.setText("Saved Orders");

        orderHistoryAdapter = new OrderHistoryAdapter();

        orderHistoryViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        orderHistoryViewModel.init(getApplicationContext());

        orderHistoryViewModel.getAllSavedOrder().observe(this, new Observer<List<OrderJsonModel>>() {
            @Override
            public void onChanged(List<OrderJsonModel> orderList) {
                if (orderList != null){
                    setAdapter(getApplicationContext(), orderList);
                }else {
                    orderHistoryViewModel.queryInAllSavedOrder();
                }
            }
        });

        btn_imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        callbackOrderModel = new CallbackOrderModel() {
            @Override
            public void callback(OrderJsonModel orderJsonModel) {
                Log.d("_tag_", "order id: "+orderJsonModel.getOrderId());
            }
        };


    }

    private void setAdapter(Context context, List<OrderJsonModel> orderList){
        orderHistoryAdapter.init(context, orderList, callbackOrderModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        orderList_recycler.setLayoutManager(layoutManager);
        orderList_recycler.setAdapter(orderHistoryAdapter);
    }
}