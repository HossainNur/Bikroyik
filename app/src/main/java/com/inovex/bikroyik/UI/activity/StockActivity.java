package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.activity.MainActivity.btsocket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.OrderListAdapter;
import com.inovex.bikroyik.UI.adapter.ProductAdapterForLandscapeMode;
import com.inovex.bikroyik.UI.adapter.ProductAdapterForLandscapeModeGridView;
import com.inovex.bikroyik.UI.fragments.FragmentStock;
import com.inovex.bikroyik.UI.fragments.FragmentProducts;
import com.inovex.bikroyik.UI.fragments.FragmentQuickSale;
import com.inovex.bikroyik.UI.fragments.FragmentStock;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.interfaces.ProductItemCallback;
import com.inovex.bikroyik.interfaces.SharedCallback;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;
import com.inovex.bikroyik.viewmodel.SalesActivityViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class StockActivity extends AppCompatActivity implements SharedCallback{

    //............for temporary...................
    private DatabaseSQLite databaseSQLite;
    //............for temporary...................

    private LinearLayout btn_salesCalculator, btn_listGridView, btn_salesItem;
    private ImageView iv_back, img_backArrow;
    private RecyclerView orderItemsRecycler;
    private ImageView iv_quickSales, iv_gridList, iv_search_land, iv_clear_text;
    private TextView tv_gridList_products, tv_quickSales, tv_total_price_sales;
    private EditText et_search_text;
    private TextView tv_totalSalesItem;
    private LinearLayout llBtnTotalCharge;
    TextView tv_total_charge;
    LinearLayout llBtnSaveOrder;
    private ViewPager viewPager;
    Context mContext;
    private MaterialSpinner spinner_totalCharge;
    //    List<String> categoryList = new ArrayList<>();
    private MaterialSpinner categoryList_spinner;
    private RecyclerView products_recycler, products_recycler_grid;

    SalesViewPager viewPagerAdapter;
    SharedPreference sharedPreference;
    boolean flag = false;

    LinearLayout ll_totalBill;





    SalesActivityViewModel mProductViewModel;
    OrderActivityViewModel orderActivityViewModel;

    private ProductAdapterForLandscapeMode mAdapter;
    private ProductAdapterForLandscapeModeGridView mAdapterGridView;
    private List<ProductModel> productModelList = new ArrayList<>();


    private OrderListAdapter mOrderAdapter;
    private List<OrderJsonModel> orderModelList = new ArrayList<>();
    private TextView tittle_ett;

    CallbackOrderItem callbackOrderItem;
    ProductItemCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        sharedPreference = SharedPreference.getInstance(StockActivity.this);
        mContext = getApplicationContext();

        sharedPreference.setHasScreenshootOfReceipt(false);
        sharedPreference.setUnsuccessfulScreenShoot(false);

        databaseSQLite = new DatabaseSQLite(mContext);

        mProductViewModel = new ViewModelProvider(this).get(SalesActivityViewModel.class);
        mProductViewModel.init(mContext);


        orderActivityViewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(mContext, sharedPreference.getCurrentOrderId());


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;



        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        /*if(height < width && screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            //for landscape
            setContentView(R.layout.activity_order_large_landscape);

            initLandScope();
            commonElement();
            onCreateColorSetting();

            setInitialLandscapeActivity();
            setCommonButtonActivity(true);

        } else {*/
        setContentView(R.layout.activity_stock);
        initPortrait();
        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            tittle_ett.setText("স্টক ");
        }

        commonElement();


        setFragmentView();
        onCreateColorSetting();


        setCommonButtonActivity(false);
        setPortraitModeButtonActivity();




//        mSalesViewModel.getProductData().observe(this, new Observer<List<ProductModel>>() {
//            @Override
//            public void onChanged(List<ProductModel> productModelList) {
//                Log.d("_pf_", "got data: "+productModelList.toString());
////                setAdapter(getContext(), productModelList);
//            }
//        });

//        mSalesViewModel.queryForGetAllProductData(this);

    }

    private void initLandScope() {
        spinner_totalCharge = (MaterialSpinner) findViewById(R.id.spinner_totalCharge);
        categoryList_spinner = (MaterialSpinner) findViewById(R.id.spinner_store);
        /*iv_search_land = (ImageView) findViewById(R.id.iv_search_large_landscape);
        et_search_text = (EditText) findViewById(R.id.et_search_land);
        et_search_text.setBackground(null);
        iv_clear_text = (ImageView) findViewById(R.id.iv_clear_text_btn_land);*/
        iv_back = (ImageView) findViewById(R.id.img_backArrow_large);
        products_recycler = (RecyclerView) findViewById(R.id.product_recycler_landscape);
        products_recycler_grid = (RecyclerView) findViewById(R.id.product_recycler_landscape_grid);
        orderItemsRecycler = (RecyclerView) findViewById(R.id.itemCharge_recycler_totalCharge);
        tv_total_charge = (TextView) findViewById(R.id.tv_charge_amount);
        llBtnSaveOrder = (LinearLayout) findViewById(R.id.ll_btn_save_order);
        llBtnTotalCharge = (LinearLayout) findViewById(R.id.ll_btn_charge_order);
    }

    private void commonElement(){
        btn_salesCalculator = (LinearLayout) findViewById(R.id.ll_btn_salesCal_sales);
        btn_listGridView = (LinearLayout) findViewById(R.id.ll_btn_listGrid_sales);
        iv_gridList = (ImageView) findViewById(R.id.img_listGrid_sales);
        iv_quickSales = (ImageView) findViewById(R.id.img_quick_sales);
        tv_quickSales = (TextView) findViewById(R.id.tv_salesCal_sales);
        tv_gridList_products = (TextView) findViewById(R.id.tv_listGrid_products_sales);
    }

    private void initPortrait(){
        viewPager = (ViewPager) findViewById(R.id.viewPager_sales);
        ll_totalBill = (LinearLayout) findViewById(R.id.ll_totalBill);
        tv_totalSalesItem = (TextView) findViewById(R.id.total_sales_items);
        img_backArrow = (ImageView) findViewById(R.id.img_backArrow);
        tv_total_price_sales = (TextView) findViewById(R.id.tv_total_price_sales);
        btn_salesItem = (LinearLayout) findViewById(R.id.btn_items_ll_sales);
        tittle_ett = (TextView) findViewById(R.id.tittle_ett);

    }
    @Override
    public String getSharedText(String barcode) {
        orderActivityViewModel.setBarcodeLiveData(barcode);

        return null;
    }

    private void playMe() {
        orderActivityViewModel.setOnScannerButtonClickListener(false);
        SharedMethode.getInstance().setContext(this);
        Intent intent = new Intent(this, ScanActivity.class);

        startActivity(intent);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void onCreateColorSetting(){
        //set gridList button as green
        if (sharedPreference.getIsListView()){
            iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_green_24));
        }else {
            iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_green_24));
        }
        tv_gridList_products.setTextColor(getResources().getColor(R.color.green_list_grid_cal));


        //set quick sales button as default
        tv_quickSales.setTextColor(getResources().getColor(R.color.black));
        iv_quickSales.setImageDrawable(getResources().getDrawable(R.drawable.ic_calculate_24));
    }


    private void colorForSalesCalculatorSelected(){
        //set quick sales button as green
        tv_quickSales.setTextColor(getResources().getColor(R.color.green_list_grid_cal));
        iv_quickSales.setImageDrawable(getResources().getDrawable(R.drawable.ic_calculate_green_24));


        //set gridList button as default
        tv_gridList_products.setTextColor(getResources().getColor(R.color.black));
        if (sharedPreference.getIsListView()){
            iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_format_list_24));
        }else {
            iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_24));
        }
    }

    private void setCommonButtonActivity(boolean isLandscape){

        btn_salesCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLandscape){
                    flag = false;
                    viewPager.setCurrentItem(0);
                }else {
                    flag = false;
                    colorForSalesCalculatorSelected();
                }

            }
        });

        btn_listGridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag && sharedPreference.getIsListView()){
                    //second time clicked and show grid view
                    sharedPreference.setIsListView(false);
                    if (!isLandscape){
                        viewPager.setCurrentItem(1);
                    }else {
                        setAdapter(getApplicationContext(), callback, callbackOrderItem);
                    }

                }else if (flag && !sharedPreference.getIsListView()){
                    //second time clicked and show linear view
                    sharedPreference.setIsListView(true);
                    if (!isLandscape){
                        viewPager.setCurrentItem(1);
                    }else {
                        setAdapter(getApplicationContext(), callback, callbackOrderItem);
                    }

                }else {
                    //first time click and previous view
                    if (!isLandscape){
                        viewPager.setCurrentItem(1);
                    }
                }

                flag = true;
                onCreateColorSetting();
                mProductViewModel.setIsProductButtonClicked_mu(true);
            }
        });
    }

    private void setInitialLandscapeActivity(){
        //spinner in all order product
        List<String> spinnerItemList= new ArrayList<>();
        spinnerItemList.add("abc");
        spinnerItemList.add("bca");
        spinnerItemList.add("cba");
        spinnerInTotalPrice(spinnerItemList);


        callbackOrderItem = new CallbackOrderItem() {
            @Override
            public void onCallback(Context context, ProductModel productModel, int quantity) {
                orderActivityViewModel.makeOrder(context, productModel, quantity);
//                mOrderViewModel.queryForAllOrderData();
            }
        };


        orderActivityViewModel.getOrderedProductList().observe(StockActivity.this, new Observer<OrderJsonModel>() {
            @Override
            public void onChanged(OrderJsonModel orderJsonModel) {
//                if (mOrderAdapter != null){
//                    mOrderAdapter.notifyDataSetChanged();
//                }

                if (orderJsonModel != null){
                    OrderedProductModel orderedProductModel_forTotal = new OrderedProductModel();
                    orderedProductModel_forTotal.setProductName("Total");
                    orderedProductModel_forTotal.setTotalPrice(orderJsonModel.getTotal());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotal);

                    OrderedProductModel orderedProductModel_forTotalDiscount = new OrderedProductModel();
                    orderedProductModel_forTotalDiscount.setProductName("Total Discount");
                    orderedProductModel_forTotalDiscount.setTotalPrice(orderJsonModel.getTotalDiscount());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalDiscount);

                    OrderedProductModel orderedProductModel_forTotalTax = new OrderedProductModel();
                    orderedProductModel_forTotalTax.setProductName("Total Tax");
                    orderedProductModel_forTotalTax.setTotalPrice(orderJsonModel.getTotalTax());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalTax);

                    OrderedProductModel orderedProductModel_forTotalGrandTotal = new OrderedProductModel();
                    orderedProductModel_forTotalGrandTotal.setProductName("Grand Total");
                    orderedProductModel_forTotalGrandTotal.setTotalPrice(orderJsonModel.getGrandTotal());
                    orderJsonModel.getOrderDetails().add(orderedProductModel_forTotalGrandTotal);

                    setAdapterForOrder(mContext, orderJsonModel.getOrderDetails(), callbackOrderItem);
                    tv_total_charge.setText("bdt "+orderJsonModel.getGrandTotal());
                }else {
                    tv_total_charge.setText("bdt 0.0");
                    setAdapterForOrder(mContext, null, callbackOrderItem);
                }

            }
        });


        llBtnSaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.setCurrentOrderId(null);
                orderActivityViewModel.makeMapEmpty();
                orderActivityViewModel.orderJsonModelMutableLiveData.setValue(null);
                orderActivityViewModel.setTotalOrderItem(0);
                setAdapterForOrder(mContext, null, callbackOrderItem);
            }
        });


        llBtnTotalCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] totalCharge = tv_total_charge.getText().toString().split(" ");
                Intent intent = new Intent(StockActivity.this, MakePaymentActivity.class);
                intent.putExtra(Constants.KEY_INTENT_EXTRA_TOTAL_CHARGE, totalCharge[1]);
                startActivity(intent);
                finish();
            }
        });


        callback = new ProductItemCallback() {
            @Override
            public void success(ProductModel productModel, int itemSize) {
                int currentQuantity = productModel.getOnHand() - itemSize;
                mProductViewModel.updateProductData(productModel.getProductId(), currentQuantity);
            }
        };



        iv_search_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryList_spinner.setVisibility(View.GONE);
                iv_search_land.setVisibility(View.GONE);

                et_search_text.setVisibility(View.VISIBLE);
                iv_clear_text.setVisibility(View.VISIBLE);
            }
        });


        iv_clear_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search_text.setText("");
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryList_spinner.getVisibility() != View.VISIBLE){
                    categoryList_spinner.setVisibility(View.VISIBLE);
                    iv_search_land.setVisibility(View.VISIBLE);

                    et_search_text.setVisibility(View.GONE);
                    iv_clear_text.setVisibility(View.GONE);
                }else {
                    finish();
                }

            }
        });

        mProductViewModel.getAllCategory().observe(StockActivity.this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categoryList) {
                categoryList.add(0, "All Item");
                categorySpinner(categoryList);
            }
        });



        mProductViewModel.getProductData().observe(StockActivity.this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModelList) {

//                mAdapter.notifyDataSetChanged();
                setAdapter(mContext, callback, callbackOrderItem);
            }
        });


        mProductViewModel.isProductButtonClicked().observe(StockActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (flag){
//                    setAdapter(mContext, callback, callbackOrderItem);
                }
                flag = true;
            }
        });
    }

    private void setPortraitModeButtonActivity(){

        orderActivityViewModel.getTotalOrderItem().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                tv_totalSalesItem.setText(String.valueOf(integer));
            }
        });

        orderActivityViewModel.getTotalChargeLiveData().observe(StockActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_total_price_sales.setText(s);
            }
        });

        orderActivityViewModel.getOrderedProductList().observe(this, new Observer<OrderJsonModel>() {
            @Override
            public void onChanged(OrderJsonModel orderJsonModel) {
                if (orderJsonModel != null){
                    tv_total_price_sales.setText(String.valueOf(orderJsonModel.getGrandTotal()));
                }else {
                    tv_total_price_sales.setText("0.0");
                }

            }
        });

        ll_totalBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(tv_total_price_sales.getText())){
                    String totalCharge = tv_total_price_sales.getText().toString();
                    Intent intent = new Intent(StockActivity.this, MakePaymentActivity.class);
                    intent.putExtra(Constants.KEY_INTENT_EXTRA_TOTAL_CHARGE, totalCharge);
                    startActivity(intent);
                    finish();
                }

            }
        });

        orderActivityViewModel.getBarcode().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String barcode) {
                if (!TextUtils.isEmpty(barcode) && !barcode.equals("0000000")){
                    ProductModel productModel = orderActivityViewModel.getProductByBarcode(barcode);
                    if (productModel != null){
                        orderActivityViewModel.makeOrder(getApplicationContext(), productModel, 1);
                        int currentQuantity = productModel.getOnHand() - 1;
                        mProductViewModel.updateProductData(productModel.getProductId(), currentQuantity);

                        if(FragmentStock.currentCategorySelectedPosition == 0) {
                            mProductViewModel.queryForGetAllProductData(getApplicationContext());
                        } else {
//                        orderActivityViewModel.setProductCategorySelectedPosition(position);
                            mProductViewModel.getProductByCategory(String.valueOf(FragmentStock.currentCategorySelectedPosition));
                        }
                    }
                    orderActivityViewModel.setBarcodeLiveData("0000000");
                }

            }
        });

        orderActivityViewModel.geOnScannerButtonClickListener().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    playMe();
                }
            }
        });

        btn_salesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockActivity.this, TotalChargeCalculationActivity.class);
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

    private void setFragmentView(){
        viewPagerAdapter = new SalesViewPager(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    //set quick sales button as green
                    tv_quickSales.setTextColor(getResources().getColor(R.color.green_list_grid_cal));
                    iv_quickSales.setImageDrawable(getResources().getDrawable(R.drawable.ic_calculate_green_24));


                    //set gridList button as default
                    tv_gridList_products.setTextColor(getResources().getColor(R.color.black));
                    if (sharedPreference.getIsListView()){
                        iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_format_list_24));
                    }else {
                        iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_24));
                    }

                }else if (position == 1){
                    //set gridList button as green
                    tv_gridList_products.setTextColor(getResources().getColor(R.color.green_list_grid_cal));
                    if (sharedPreference.getIsListView()){
                        iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_list_green_24));
                    }else {
                        iv_gridList.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid_green_24));
                    }

                    //set quick sales button as default
                    tv_quickSales.setTextColor(getResources().getColor(R.color.black));
                    iv_quickSales.setImageDrawable(getResources().getDrawable(R.drawable.ic_calculate_24));

                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }




    public static class SalesViewPager extends FragmentPagerAdapter {

        public SalesViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            /** Show a Fragment based on the position of the current screen */
            /*if (position == 0) {
                return new FragmentQuickSale();
            }*/  if (position == 1) {
                return new FragmentStock();
            } else {
                return new FragmentStock();
            }
        }

        @Override
        public int getCount() {
            return 1;
        }
    }

    private void spinnerInTotalPrice(List<String> itemList){
        ArrayAdapter spinner = new ArrayAdapter(mContext, R.layout.spinner_layout, itemList);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_totalCharge.setAdapter(spinner);
        spinner_totalCharge.setDropdownMaxHeight(450);


        spinner_totalCharge.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position == 0) {
//                    mSalesViewModel.queryForGetAllProductData(mContext);
                }
                else {
//                    mSalesViewModel.getProductByCategory(itemList.get(position));
                }
            }
        });
    }


    private void categorySpinner(List<String> categoryList){
        ArrayAdapter spinner = new ArrayAdapter(mContext, R.layout.spinner_layout, categoryList);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categoryList_spinner.setAdapter(spinner);
        categoryList_spinner.setDropdownMaxHeight(450);


        categoryList_spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(position == 0) {
                    mProductViewModel.queryForGetAllProductData(mContext);
                }
                else {
                    mProductViewModel.getProductByCategory(categoryList.get(position));
                }
            }
        });
    }

    private void setAdapterForOrder(Context context, List<OrderedProductModel> orderedProductModelList, CallbackOrderItem callback){
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mOrderAdapter = OrderListAdapter.getInstance();
        mOrderAdapter.init(context, orderedProductModelList, callback, true);
        orderItemsRecycler.setLayoutManager(linearLayoutManager);
        orderItemsRecycler.setAdapter(mOrderAdapter);
        if (orderedProductModelList != null && orderedProductModelList.size() > 2){
            orderItemsRecycler.smoothScrollToPosition(orderedProductModelList.size()-1);
        }

    }

    private void linearViewRecyclerAdapter(Context context, ProductItemCallback callback,
                                           CallbackOrderItem callbackOrderItem){
        if (products_recycler.getVisibility() != View.VISIBLE){
            products_recycler_grid.setVisibility(View.GONE);
            products_recycler.setVisibility(View.VISIBLE);
        }
        List<ProductModel> productModelList = mProductViewModel.mProductDate_MutableLiveData.getValue();
        mAdapter = ProductAdapterForLandscapeMode.getInstance();
        mAdapter.init(context, productModelList, sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        products_recycler.setLayoutManager(linearLayoutManager);
        products_recycler.setAdapter(mAdapter);

        Log.d("tag", "success");
    }


    private void gridViewLayout(Context context, ProductItemCallback callback,
                                CallbackOrderItem callbackOrderItem, int totalNumberOfGrid){
        if (products_recycler_grid.getVisibility() != View.VISIBLE){
            products_recycler.setVisibility(View.GONE);
            products_recycler_grid.setVisibility(View.VISIBLE);

        }

        List<ProductModel> productModelList = mProductViewModel.mProductDate_MutableLiveData.getValue();
        mAdapterGridView = ProductAdapterForLandscapeModeGridView.getInstance();
        mAdapterGridView.init(context, productModelList, sharedPreference.getIsListView(), callback, callbackOrderItem);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(context, totalNumberOfGrid);
        products_recycler_grid.setLayoutManager(gridLayoutManager);
        products_recycler_grid.setAdapter(mAdapterGridView);

        Log.d("tag", "success");
    }

    private void setAdapter(Context context, ProductItemCallback callback, CallbackOrderItem callbackOrderItem){
        if (sharedPreference.getIsListView()){
            //listView adapter
            linearViewRecyclerAdapter(context,callback, callbackOrderItem);
        }else {
            //gridView adapter
            gridViewLayout(context, callback, callbackOrderItem, 3);
        }
    }






    private int screenSize(){

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        String msg;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                msg = "large";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                msg = "normal";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                msg = "small";
                break;
            default:
                msg = "Screen size is neither large, normal or small";
        }
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        return screenSize;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (orderActivityViewModel != null) {
            orderActivityViewModel.init(mContext, sharedPreference.getCurrentOrderId());
        }

        if (mProductViewModel != null){
            mProductViewModel.init(mContext);
        }
    }



}