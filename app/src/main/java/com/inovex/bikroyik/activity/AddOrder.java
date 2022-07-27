package com.inovex.bikroyik.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.inovex.bikroyik.AppUtils.APICall;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.AddOrderAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddOrder extends AppCompatActivity {


    //Toolbar mToolbar;
    TextView tvHomeToolbarTitle, done, cancel;
    //ImageView ivBackIcon;
    AddOrderAdapter expandableListAdapter;
    APICall apiCall;
    Context context;
    AppDatabaseHelper appDatabaseHelper;
    ExpandableListView expandableListView;
    List<String> expandableListTitle = new ArrayList<>();
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    ArrayList<HashMap<String, String>> dataListOne;
    List<String> category_name_list = new ArrayList<>();
    Button orders;
    double total_discount;
    String brand;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

       // mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        //tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
       // tvHomeToolbarTitle.setText("New Sales Order");
        //ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        setTitle(getString(R.string.new_sales_order));
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button


        context = this;

        brand = getIntent().getStringExtra("brand_name");

        Log.d("workforce_product", "onCreate: "+brand);

        appDatabaseHelper = new AppDatabaseHelper(context);

        dataListOne = appDatabaseHelper.getProductData();
        expandableListView = findViewById(R.id.expandableListViewOfProduct);
        populateDataForExpandableListView(dataListOne);

        productByCategory(dataListOne,category_name_list);
        expandableListAdapter = new AddOrderAdapter(getApplicationContext(),category_name_list,expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                HashMap<String, String> specificProduct = new HashMap<String, String>();
                String productDetails = expandableListDetail.get(category_name_list.get(groupPosition)).get(childPosition);
                String[] splitString = productDetails.split("#");


                specificProduct = appDatabaseHelper.getSpecificProductData(splitString[5]);

                addOrder(specificProduct);

                return false;
            }
        });


        /*ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProductsDirectoryActivity.class));
                finish();
            }
        });*/
        orders = findViewById(R.id.see_orders);

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),OrderList.class));
                finish();
            }
        });




    }

    private void addOrder(HashMap<String, String> specificProduct) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.add_product_order);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        final String product_id = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_ID);
        final String product_name = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_NAME);
        final String product_stock = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_STARTING_STOCK);
        final String product_discount = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT);
        final String discount_type = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT_TYPE);
        final String available_discount = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT_AVAILABLE);
        final String product_offer = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_OFFER);
        final String available_offer = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_AVAILABLE_OFFER);
        final String product_price = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE);

        final TextView id, name, stock, discount, offer,price, total_price;
        final EditText quantity;
        final int intPrice; final int intQuantity; final int intDiscount = 0; final int intTotal;

        id = dialog.findViewById(R.id.dialog_id);
        name = dialog.findViewById(R.id.dialog_name);
        stock = dialog.findViewById(R.id.dialog_stock);
        discount = dialog.findViewById(R.id.dialog_discount);
        offer = dialog.findViewById(R.id.dialog_offer);
        quantity = dialog.findViewById(R.id.dialog_quantity);
        total_price = dialog.findViewById(R.id.dialog_total_price);
        price = dialog.findViewById(R.id.dialog_price);

        id.setText("Product ID: "+product_id);
        name.setText("Product Name: "+product_name);
        stock.setText("Stock: "+product_stock);

        if(product_offer.equalsIgnoreCase("true")){
            offer.setText(available_offer);
        } else {
            offer.setText("N/A");

        }

        if(product_discount.equalsIgnoreCase("true")){
            if(discount_type.equalsIgnoreCase("Percentage")) {
                discount.setText("Discount: "+available_discount+"%");

            } else {
                discount.setText("Discount: "+available_discount+" TK");
            }
        } else {
            discount.setText("Discount: "+"N/A");

        }

        price.setText("Price: "+product_price);

        dialog.show();

        done = dialog.findViewById(R.id.dialog_done);
        cancel = dialog.findViewById(R.id.dialog_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean result = appDatabaseHelper.productExist(product_id);
                if(total_price.getText().toString().length() == 0) {
                    total_price.setError("Please fill it.");
                }
                else {
                    if(result == true){
                        HashMap<String, String> product_order = new HashMap<String, String>();
                        product_order = appDatabaseHelper.getPerOrderById(product_id);

                        double total = Double.parseDouble(product_order.get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE));
                        int  quantity1 = Integer.parseInt(product_order.get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_QUANTITY));
                        double discount = Double.parseDouble(product_order.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT));

                        double new_total = Double.parseDouble(total_price.getText().toString())+ total;
                        int new_quantity = Integer.parseInt(quantity.getText().toString()) + quantity1;
                        double new_discount = total_discount + discount;

                        appDatabaseHelper.updatePerOrder(String.valueOf(new_quantity),String.valueOf(new_total), product_id, String.valueOf(new_discount));

                    }
                    else {
                        appDatabaseHelper.insertOrderPerProduct(product_id, product_name, quantity.getText().toString(), total_price.getText().toString(), String.valueOf(total_discount));
                    }

                    dialog.dismiss();
                }

            }
        });


        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double intTotal = 0;
                /*if(total_price.getText().toString().length() == 0) {
                    total_price.setError("Please fill it.");
                } else*/ if(quantity.getText().toString().length() == 0 ) {
                    quantity.setError("Please, fill it.");
                    total_price.setText("");
                } else {
                    double intQuantity = Double.parseDouble(quantity.getText().toString());
                    double intPrice = Double.parseDouble(product_price);
                    if(product_discount.equals("true")) {
                        double intDiscount = Double.parseDouble(available_discount);
                        if(discount_type.equals("Percentage")) {

                            intTotal = (intQuantity * intPrice) * (1 - (intDiscount / 100));
                            total_discount = (intQuantity * intPrice) * (intDiscount / 100);


                        } else {

                            intTotal = intQuantity * (intPrice - intDiscount);
                            total_discount = intQuantity * intDiscount;

                        }

                    }
                    else {
                        intTotal = intQuantity * intPrice ;
                    }
                    String product_total_price = String.valueOf(intTotal);

                    total_price.setText(product_total_price);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });




    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ProductsDirectoryActivity.class));
        finish();
        super.onBackPressed();
    }

    private void productByCategory(ArrayList<HashMap<String, String>> dataListOne, List<String> category_name_list) {

        for(int i = 0; i < category_name_list.size(); i++) {
            String category = category_name_list.get(i);



        }

    }


    private void populateDataForExpandableListView(ArrayList<HashMap<String, String>> dataList) {


        ArrayList<HashMap<String, String>> dataValueList = dataList;
        ArrayList<HashMap<String, String>> dataValueListCopy = dataList;




        if(dataValueList.size() > 0) {
            String previousCategoryName = "";

            for (int x = 0; x < dataValueList.size(); x++) {

                Log.d("workforce_product", "onCreate: "+brand+" "+dataList.get(x).get(AppDatabaseHelper.COLUMN_PRODUCT_BRAND_NAME));


                if(dataList.get(x).get(AppDatabaseHelper.COLUMN_PRODUCT_BRAND_NAME).equalsIgnoreCase(brand)){
                    String productCategory = dataList.get(x).get(AppDatabaseHelper.COLUMN_PRODUCT_CATEGORY_NAME);
                    //Log.d("dataList", "populateDataForExpandableListView: "+productCategory);

                    if (!productCategory.equalsIgnoreCase(previousCategoryName)) {
                        // no day inserted previously with this name
                        previousCategoryName = productCategory;
                        category_name_list.add(productCategory);
                        List<String> targetList = new ArrayList<>();
                        for (int y = 0; y < dataValueListCopy.size(); y++) {
                            String productCategoryCopy = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_CATEGORY_NAME);
                            //Log.d("dataList", "populateDataForExpandableListView: "+productCategoryCopy);
                            if (productCategory.equalsIgnoreCase(productCategoryCopy)) {
                                String productName = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_NAME);
                                String productId = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_ID);
                                String price = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE);
                                String onHand = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_ON_HAND);
                                String image = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_IMAGE_NAME);

                                targetList.add(" Id: " + productId + "#" + productName + "#" + "On Hand : " + onHand + "#" + "Price: " + price + "#" + image+ "#" + productId);
                            }
                        }
                        expandableListDetail.put(productCategory, targetList);
                    }
                }


            }

        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_product_search);
        SearchView mSearchView = (SearchView) searchViewItem.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchData(s);
                if(s.length() > 0){
                    for (int i = 0; i < expandableListView.getExpandableListAdapter().getGroupCount(); i++) {
                        //Expand group
                        expandableListView.expandGroup(i);
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchData(s);
                if(s.length() > 0){
                    for (int i = 0; i < expandableListView.getExpandableListAdapter().getGroupCount(); i++) {
                        //Expand group
                        expandableListView.expandGroup(i);
                    }
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void searchData(String s) {

        ArrayList<HashMap<String, String>> dataValueList = dataListOne;
        ArrayList<HashMap<String, String>> dataValueListCopy = dataListOne;


        category_name_list.clear();

        if(dataValueList.size() > 0) {
            String previousCategoryName = "";

            for (int x = 0; x < dataValueList.size(); x++) {

                if(dataListOne.get(x).get(AppDatabaseHelper.COLUMN_PRODUCT_BRAND_NAME).equalsIgnoreCase(brand)){
                    String productCategory = dataListOne.get(x).get(AppDatabaseHelper.COLUMN_PRODUCT_CATEGORY_NAME);

                    if (!productCategory.equalsIgnoreCase(previousCategoryName)) {
                        // no day inserted previously with this name
                        previousCategoryName = productCategory;
                        //category_name_list.add(productCategory);
                        List<String> targetList = new ArrayList<>();
                        for (int y = 0; y < dataValueListCopy.size(); y++) {
                            String productCategoryCopy = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_CATEGORY_NAME);
                            //Log.d("dataList", "populateDataForExpandableListView: "+productCategoryCopy);
                            if (productCategory.equalsIgnoreCase(productCategoryCopy)) {
                                String productName = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_NAME);
                                String productId = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_ID);
                                String price = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE);
                                String onHand = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_ON_HAND);
                                String image = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_IMAGE_NAME);
                                String target_str = productName.toLowerCase();
                                if(s.length() == 0){
                                    if(!category_name_list.contains(productCategory)){
                                        category_name_list.add(productCategory);
                                    }
                                    targetList.add(" Id: " + productId + "#" + productName + "#" + "On Hand : " + onHand + "#" + "Price: " + price + "#" + image+ "#" + productId);
                                }else {
                                    if(target_str.toLowerCase().contains(s.toLowerCase())){
                                        if(!category_name_list.contains(productCategory)){
                                            category_name_list.add(productCategory);
                                        }
                                        Log.d("workforce_search", "searchData: "+s+"  target "+target_str);

                                        targetList.add(" Id: " + productId + "#" + productName + "#" + "On Hand : " + onHand + "#" + "Price: " + price + "#" + image+ "#" + productId);
                                    } else {
                                        Log.d("workforce_search", "searchData: "+s+"  target missed "+target_str);

                                    }
                                }


                            }
                        }
                        expandableListDetail.put(productCategory, targetList);
                    }
                }


            }

        }

        Log.d("workforce_search", "searchData: "+s+"   "+category_name_list);

        expandableListAdapter.notifyDataSetChanged();
    }
}
