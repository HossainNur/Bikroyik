package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.DiscountAdapter;
import com.inovex.bikroyik.UI.adapter.OrderListAdapter;
import com.inovex.bikroyik.UI.adapter.TaxAdapter;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.interfaces.DiscountCallBack;
import com.inovex.bikroyik.interfaces.TaxCallBack;
import com.inovex.bikroyik.viewmodel.SingleProductActivityViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class SingleOrderItemActivity extends AppCompatActivity {
    private ImageView iv_close, iv_decrease, iv_increase, btn_decrease_iv_sie, btn_add_iv_sie;
    private TextView tv_save,tv_deleteProduct,productText;
    private EditText et_quantity, et_comment;
    private RecyclerView discount_recycler, tax_recycler;

    Map<Integer, DiscountDetails> discountMapping = new HashMap<>();
    Map<Integer, Tax> taxMapping = new HashMap<>();
    List<DiscountDetails> discountList = new ArrayList<>();
    List<Tax> taxList = new ArrayList<>();

    OrderedProductModel orderedProductModel;
    SharedPreference sharedPreference;
    SingleProductActivityViewModel productActivityViewModel;

    DiscountAdapter discountAdapter;
    TaxAdapter taxAdapter;
    private OrderListAdapter mOrderAdapter;
    DiscountCallBack discountCallBack;
    TaxCallBack taxCallBack;
    private DatabaseSQLite databaseSQLite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_item_edit);

        Context mContext = getApplicationContext();
        init(mContext);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        databaseSQLite = new DatabaseSQLite(getApplicationContext());
        productActivityViewModel = new ViewModelProvider(this).get(SingleProductActivityViewModel.class);
        productActivityViewModel.init(mContext);



        Intent intent = getIntent();
        String productId = intent.getStringExtra("key_product_id");

        if ( productId != null){
            discountAdapter = DiscountAdapter.getInstance();
            taxAdapter = TaxAdapter.getInstance();

            orderedProductModel = productActivityViewModel.
                    getOrderProductModel(sharedPreference.getCurrentOrderId(), productId);

            if (orderedProductModel != null){
                int quantity = orderedProductModel.getQuantity();
                et_quantity.setText(String.valueOf(quantity), TextView.BufferType.EDITABLE);
                buttonActivity(mContext, sharedPreference.getCurrentOrderId(), productId);

                productActivityViewModel.queryForAllDiscountLiveData(sharedPreference.getCurrentOrderId(), productId);
                productActivityViewModel.queryAllTaxLiveData(sharedPreference.getCurrentOrderId(), productId);


                productActivityViewModel.getLiveDiscountList().observe(this, new Observer<List<DiscountDetails>>() {
                    @Override
                    public void onChanged(List<DiscountDetails> discountDetailsList) {
                        setDiscountAdapter(mContext, discountDetailsList, 1, discountCallBack);
                    }
                });

                productActivityViewModel.getLiveTaxList().observe(this, new Observer<List<Tax>>() {
                    @Override
                    public void onChanged(List<Tax> taxList) {
                        setTaxAdapter(mContext, taxList, 1, taxCallBack);
                    }
                });

                productText.setText("("+orderedProductModel.getProductName()+")");


            }

        }

        btn_decrease_iv_sie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_quantity.getText().toString())){
                    et_quantity.setText("0");
                }else {
                    int quantity = Integer.parseInt(et_quantity.getText().toString())-1;
                    if (quantity <= 0){
                        et_quantity.setText(String.valueOf("0"));
                    }else {
                        et_quantity.setText(String.valueOf(quantity));
                    }
                }
            }
        });

        btn_add_iv_sie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(et_quantity.getText().toString())){
                    et_quantity.setText("1");
                }else {
                    int quantity = Integer.parseInt(et_quantity.getText().toString())+1;
                    et_quantity.setText(String.valueOf(quantity));
                }
            }
        });

    }

    private void init(Context context){
        iv_close = (ImageView) findViewById(R.id.img_close_esi);
        btn_decrease_iv_sie = (ImageView) findViewById(R.id.btn_decrease_iv_sie);
        btn_add_iv_sie = (ImageView) findViewById(R.id.btn_add_iv_sie);
        tv_save = (TextView) findViewById(R.id.tv_save_esi);
        tv_deleteProduct = (TextView) findViewById(R.id.tv_deleteProduct);
        iv_decrease = (ImageView) findViewById(R.id.btn_decrease_iv_sie);
        iv_increase = (ImageView) findViewById(R.id.btn_add_iv_sie);
        et_quantity = (EditText) findViewById(R.id.et_quantity_sie);
        et_comment = (EditText) findViewById(R.id.et_comment_sie);
        discount_recycler = (RecyclerView) findViewById(R.id.recycler_discount_sie);
        tax_recycler = (RecyclerView) findViewById(R.id.recycler_tax_sie);
        productText = (TextView) findViewById(R.id.productText);
    }

    private void buttonActivity(Context mContext, String orderId, String productId){

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (!discountList.isEmpty()){
                    productActivityViewModel.updateDiscountAvailability(discountList);
                }

                if (!taxList.isEmpty()){
                    productActivityViewModel.updateTaxAvailability(taxList);
                }

                if (isNumber(et_quantity.getText().toString())){
                    int quantity = Integer.parseInt(et_quantity.getText().toString());
                    productActivityViewModel.updateProductAndOrder(orderedProductModel.getProductId(),
                            orderedProductModel, quantity);

                    Toast.makeText(mContext, "Save successfully!", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        });

        tv_deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseSQLite.deleteProductData(productId);
                //int quantity = 0;
                //productActivityViewModel.updateProductNameData(orderedProductModel.getProductId(),orderedProductModel,quantity);
                int quantity = Integer.parseInt(et_quantity.getText().toString());
                productActivityViewModel.updateProductNameData(orderedProductModel.getProductId(),
                        orderedProductModel, quantity);
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);

                //orderLiremoveAt();
                Toasty.warning(getApplicationContext(), orderedProductModel.getProductName() + " Deleted!! ", Toast.LENGTH_SHORT).show();
                finish();

            }
        });



        discountCallBack = new DiscountCallBack() {
            @Override
            public void callback(DiscountDetails discountDetails, boolean shouldApplyDiscount) {
                int discountId = discountDetails.getId();

                if (!discountMapping.containsKey(discountId)){
                    discountDetails.setOrderId(orderId);
                    discountDetails.setProductId(productId);
                    discountDetails.setDiscountApplied(shouldApplyDiscount);
                    discountMapping.put(discountId, discountDetails);
                    discountList.add(discountDetails);
                }else{
                    DiscountDetails discountDetails1 = discountMapping.get(discountId);
                    discountDetails1.setDiscountApplied(shouldApplyDiscount);
                }

            }
        };

        taxCallBack = new TaxCallBack() {
            @Override
            public void callback(Tax tax, boolean shouldApplyTax) {
                int taxId = tax.getId();

                if (!taxMapping.containsKey(taxId)){
                    tax.setOrderId(orderId);
                    tax.setProductId(productId);
                    tax.setIsTaxApplied(shouldApplyTax);
                    taxMapping.put(taxId, tax);
                    taxList.add(tax);
                }else{
                    Tax tax1 = taxMapping.get(taxId);
                    tax1.setIsTaxApplied(shouldApplyTax);
                }

            }
        };

        iv_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iv_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isNumber(String value){
        boolean numeric = true;

        try {
            int num = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            numeric = false;
        }

       return numeric;
    }

    private void setDiscountAdapter(Context context, List<DiscountDetails> discountDetailsList,
                                    int totalColumn,  DiscountCallBack callBack){
        discountAdapter.init(context, discountDetailsList, callBack);
        GridLayoutManager layoutManager = new GridLayoutManager(context, totalColumn);
        discount_recycler.setLayoutManager(layoutManager);
        discount_recycler.setAdapter(discountAdapter);
    }

    private void setTaxAdapter(Context context, List<Tax> taxList, int totalColumn, TaxCallBack callBack){
        taxAdapter.init(context, taxList, callBack);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, totalColumn);
        tax_recycler.setLayoutManager(gridLayoutManager);
        tax_recycler.setAdapter(taxAdapter);
    }


}