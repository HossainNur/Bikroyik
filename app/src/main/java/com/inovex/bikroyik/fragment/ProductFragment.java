package com.inovex.bikroyik.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.ProductExpandableListAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.inovex.bikroyik.activity.MainActivity.brand;


/**
 * Created by DELL on 11/25/2018.
 */


public class ProductFragment extends Fragment {


    ExpandableListView expandableListView;
    ProductExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    AppDatabaseHelper appDatabaseHelper;

    ArrayList<HashMap<String, String>> dataListOne;
    List<String> category_name_list = new ArrayList<>();

    public ProductFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        appDatabaseHelper = new AppDatabaseHelper(getContext());
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<>();
        expandableListDetail = new HashMap<String, List<String>>();


        dataListOne = new ArrayList<>();
        dataListOne = appDatabaseHelper.getProductData();
        Log.d("workforce", "onCreateView: "+dataListOne);
        populateDataForExpandableListView(dataListOne);
        Log.d("workforce", "onCreateView: "+category_name_list);
        productByCategory(dataListOne,category_name_list);
        expandableListAdapter = new ProductExpandableListAdapter(getContext(), category_name_list, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String products = expandableListDetail.get(category_name_list.get(groupPosition)).get(childPosition);
                showDetails(products);

                return false;
            }
        });


        return view;
    }

    private void showDetails(String products) {

        String[] splitString = products.split("#");

        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.product_details);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        TextView expandedItemTextOne = (TextView) dialog
                .findViewById(R.id.product_id);
        expandedItemTextOne.setText(splitString[0]);

        TextView expandedItemTextTwo = (TextView) dialog
                .findViewById(R.id.product_name);
        expandedItemTextTwo.setText(splitString[1]);

        TextView expandedItemTextThree = (TextView) dialog
                .findViewById(R.id.product_price);
        expandedItemTextThree.setText(splitString[2]);

        ImageView img = dialog.findViewById(R.id.image);


        String image_name = splitString[3];

        String url = APIConstants.PRODUCT_IMAGE+image_name;

//        Picasso.with(getContext()).load(url).into(img);
        Picasso.get().load(url).into(img);



        TextView discount = (TextView) dialog
                .findViewById(R.id.product_discount);


        if(splitString[7].equals("true")){
            if(splitString[6].equals("BDT")){
                discount.setText("Discount : "+splitString[5] +" TK");
            } else {
                discount.setText("Discount : "+splitString[5] +"%");

            }
        } else {
            discount.setVisibility(View.GONE);
        }


        TextView offer = (TextView) dialog
                .findViewById(R.id.product_offer);

        if(splitString[9].equals("true")){
            offer.setText("Offer : "+splitString[8]);
        } else {
            offer.setVisibility(View.GONE);
        }

        TextView desc = (TextView) dialog
                .findViewById(R.id.product_desc);
        desc.setText("Description : "+splitString[4]);

        dialog.show();




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
                if(brand.equalsIgnoreCase(dataList.get(x).get(AppDatabaseHelper.COLUMN_PRODUCT_BRAND_NAME))){

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
                                String image = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_IMAGE_NAME);
                                String desc = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_DESCRIPTION);
                                String discount_available = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT_AVAILABLE);
                                String discount_type = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT_TYPE);
                                String discount = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT);
                                String offer_available = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_AVAILABLE_OFFER);
                                String offer = dataValueListCopy.get(y).get(AppDatabaseHelper.COLUMN_PRODUCT_OFFER);


                                targetList.add("ID : " + productId + "#" + productName + "#" + "Price : " + price + "#" + image + "#" + desc + "#" + discount_available + "#" + discount_type + "#" + discount + "#" + offer_available + "#" + offer);
                            }
                        }
                        expandableListDetail.put(productCategory, targetList);
                    }

                }


            }

        }

        //

    }



}

