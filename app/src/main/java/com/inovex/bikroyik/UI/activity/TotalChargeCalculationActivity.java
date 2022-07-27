package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.OrderListAdapter;
import com.inovex.bikroyik.UI.fragments.FragmentProducts;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;
import com.inovex.bikroyik.viewmodel.SalesActivityViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TotalChargeCalculationActivity extends AppCompatActivity {
    private LinearLayout llBtnTotalCharge;
    TextView tv_total_charge, tittle_ett,cancelButtonText,tv_charge;
    private Button demoBtn;
    LinearLayout llBtnSaveOrder;
    private ImageView img_backArrow;
    private MaterialSpinner spinner_totalCharge;
    CallbackOrderItem callbackOrderItem;
    private RecyclerView orderItemsRecycler;
    private TextView tv_totalSalesItem;
    OrderActivityViewModel orderActivityViewModel;
    SalesActivityViewModel mProductViewModel;
    private SalesActivityViewModel mSalesViewModel;

    LinearLayout bottom_layout, btn_items_ll_sales;
    private boolean shouldSetOnItemListener = true;

    //private List<ProductModel> productModel = new ArrayList<>();
    ProductModel productModel;
    int itemSize;

    Context mContext;
    private OrderListAdapter mOrderAdapter;

    private SharedPreference sharedPreference;
    String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_charge_calculation);

        mContext = getApplicationContext();
        init();

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            tittle_ett.setText("বিক্রয়");
            cancelButtonText.setText("বাতিল ");
            tv_charge.setText("বিল");

        }

        Intent intent = getIntent();

        if (!TextUtils.isEmpty(intent.getStringExtra(Constants.KEY_ORDER_ID))){
            orderId = sharedPreference.getCurrentOrderId();
            tittle_ett.setText("Order Details");
            bottom_layout.setVisibility(View.GONE);
            btn_items_ll_sales.setVisibility(View.GONE);
            spinner_totalCharge.setVisibility(View.GONE);
            shouldSetOnItemListener = false;
        }else {
            orderId = sharedPreference.getCurrentOrderId();
            shouldSetOnItemListener = true;
        }
/*
        demoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOrderAdapter.removeAt(0);
            }
        })*/;



        orderActivityViewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(mContext, orderId);

        mSalesViewModel = new ViewModelProvider(this).get(SalesActivityViewModel.class);
        mSalesViewModel.init(mContext);



        mProductViewModel = new ViewModelProvider(this).get(SalesActivityViewModel.class);
        mProductViewModel.init(mContext);


        callbackOrderItem = new CallbackOrderItem() {
            @Override
            public void onCallback(Context context, ProductModel productModel, int quantity) {
            }
        };

        orderActivityViewModel.getTotalOrderItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tv_totalSalesItem.setText(String.valueOf(integer));
            }
        });


        orderActivityViewModel.getOrderedProductList().observe(TotalChargeCalculationActivity.this, new Observer<OrderJsonModel>() {
            @Override
            public void onChanged(OrderJsonModel orderJsonModel) {
               /*if (mOrderAdapter != null){
                    mOrderAdapter.notifyDataSetChanged();
              }*/

                if (orderJsonModel != null){
                    OrderedProductModel orderedProductModel_forTotal = new OrderedProductModel();
                    orderedProductModel_forTotal.setProductName("Total");
                    /*orderedProductModel_forTotal.setTotalPrice(orderJsonModel.getTotal());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotal);*/
                    Double totalValue = orderJsonModel.getTotal();
                    DecimalFormat df = new DecimalFormat("#.##");
                    orderedProductModel_forTotal.setTotalPrice(Double.parseDouble(df.format(totalValue)));
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotal);


                    OrderedProductModel orderedProductModel_forTotalDiscount = new OrderedProductModel();
                    orderedProductModel_forTotalDiscount.setProductName("Total Discount");
                    /*orderedProductModel_forTotalDiscount.setTotalPrice(orderJsonModel.getTotalDiscount());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalDiscount);*/
                    Double totalDiscount = orderJsonModel.getTotalDiscount();
                    df = new DecimalFormat("#.##");
                    orderedProductModel_forTotalDiscount.setTotalPrice(Double.parseDouble(df.format(totalDiscount)));
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalDiscount);


                    OrderedProductModel orderedProductModel_forTotalTax = new OrderedProductModel();
                    orderedProductModel_forTotalTax.setProductName("Total Tax");
                    /*orderedProductModel_forTotalTax.setTotalPrice(orderJsonModel.getTotalTax());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalTax);*/
                    Double totalTax = orderJsonModel.getTotalTax();
                    df = new DecimalFormat("#.##");
                    orderedProductModel_forTotalTax.setTotalPrice(Double.parseDouble(df.format(totalTax)));
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalTax);

                    OrderedProductModel orderedProductModel_forTotalGrandTotal = new OrderedProductModel();
                    orderedProductModel_forTotalGrandTotal.setProductName("Grand Total");
                    /*orderedProductModel_forTotalGrandTotal.setTotalPrice(orderJsonModel.getGrandTotal());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalGrandTotal);*/
                    Double totalGrandTotal = orderJsonModel.getGrandTotal();
                    df = new DecimalFormat("#.##");
                    orderedProductModel_forTotalGrandTotal.setTotalPrice(Double.parseDouble(df.format(totalGrandTotal)));
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalGrandTotal);

                    setAdapterForOrder(mContext, orderJsonModel.getOrderDetails(), callbackOrderItem, shouldSetOnItemListener);
                    Double grandValue = orderJsonModel.getGrandTotal();
                     df = new DecimalFormat("#.##");
                    tv_total_charge.setText("bdt "+df.format(grandValue));
                }else {
                    setAdapterForOrder(mContext, null, callbackOrderItem, shouldSetOnItemListener);
                    tv_total_charge.setText("bdt 0.0");
                }

            }
        });

        llBtnTotalCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] totalCharge = tv_total_charge.getText().toString().split(" ");
                Intent intent = new Intent(TotalChargeCalculationActivity.this, MakePaymentActivity.class);
                intent.putExtra(Constants.KEY_INTENT_EXTRA_TOTAL_CHARGE, totalCharge[1]);
                startActivity(intent);
                finish();
            }
        });

        llBtnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreference.setCurrentOrderId(null);
                orderActivityViewModel.makeMapEmpty();
                orderActivityViewModel.orderJsonModelMutableLiveData.setValue(null);
                orderActivityViewModel.setTotalOrderItem(0);
               // setAdapterForOrder(mContext, null, callbackOrderItem, shouldSetOnItemListener);
                /*int onHand;
                productModel.setOnHand(onHand);*/
                /*ProductModel productModel;
                int itemSize = 0;*/

                /*int currentQuantity = productModel.getOnHand() - itemSize;
                mSalesViewModel.updateProductData(productModel.getProductId(), currentQuantity);*/

                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                //Intent intent = new Intent(getApplicationContext(), OrderItemActivity.class);
                startActivity(intent);

            }
        });
        img_backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }


    private void init(){
        tv_total_charge = (TextView) findViewById(R.id.tv_charge_amount);
        tv_totalSalesItem = (TextView) findViewById(R.id.total_sales_items);
        spinner_totalCharge = (MaterialSpinner) findViewById(R.id.spinner_totalCharge);
        orderItemsRecycler = (RecyclerView) findViewById(R.id.itemCharge_recycler_totalCharge);
        llBtnSaveOrder = (LinearLayout) findViewById(R.id.ll_btn_save_order);
        llBtnTotalCharge = (LinearLayout) findViewById(R.id.ll_btn_charge_order);
        bottom_layout = (LinearLayout) findViewById(R.id.bottom_layout);
        btn_items_ll_sales = (LinearLayout) findViewById(R.id.btn_items_ll_sales);
        tittle_ett = (TextView) findViewById(R.id.tittle_ett);
        img_backArrow = (ImageView) findViewById(R.id.img_backArrow);
        tv_charge = (TextView) findViewById(R.id.tv_charge);
        cancelButtonText = (TextView) findViewById(R.id.cancelButtonText);
        //demoBtn = (Button) findViewById(R.id.demoBtn);

    }

    private void setAdapterForOrder(Context context, List<OrderedProductModel> orderedProductModelList,
                                    CallbackOrderItem callback, boolean shouldSetOnItemListener){
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mOrderAdapter = OrderListAdapter.getInstance();
        mOrderAdapter.init(context, orderedProductModelList, callback, shouldSetOnItemListener);
        orderItemsRecycler.setLayoutManager(linearLayoutManager);
        orderItemsRecycler.setAdapter(mOrderAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (orderActivityViewModel != null){
            orderActivityViewModel.init(mContext, sharedPreference.getCurrentOrderId());
        }

    }



}