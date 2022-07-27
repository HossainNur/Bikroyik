package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Orders;
import com.inovex.bikroyik.adapter.OrdersDetailsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class OrderDetailsFragment extends Fragment {

    RecyclerView recyclerView;
    AppDatabaseHelper appDatabaseHelper;
    Context context;

    ArrayList<HashMap<String, String>> orderDetailsList = new ArrayList<HashMap<String, String>>();
    List<Orders> ordersList = new ArrayList<Orders>();
    OrdersDetailsAdapter ordersDetailsAdapter;




    public OrderDetailsFragment() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        appDatabaseHelper = new AppDatabaseHelper(context);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_details, container, false);
        recyclerView  = view.findViewById(R.id.recycler_orders_details);

        onBackPressed(view);
        populateOrdersData();

        ordersDetailsAdapter = new OrdersDetailsAdapter(ordersList,context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(ordersDetailsAdapter);




        return view;
    }

    private void populateOrdersData() {

        orderDetailsList = appDatabaseHelper.getFinalOrderData();
        ArrayList<HashMap<String, String>> orderDetailsListCopy = new ArrayList<HashMap<String, String>>();
        orderDetailsListCopy  = orderDetailsList;

        for(int i = 0; i < orderDetailsListCopy.size(); i++){
            String id = orderDetailsListCopy.get(i).get(AppDatabaseHelper.COLUMN_FINAL_ORDER_ID);
            String deliveryDate = orderDetailsListCopy.get(i).get(AppDatabaseHelper.COLUMN_FINAL_ORDER_DELIVERY_DATE);
            String retailId = orderDetailsListCopy.get(i).get(AppDatabaseHelper.COLUMN_FINAL_ORDER_RETAIL_ID);
            String total = orderDetailsListCopy.get(i).get(AppDatabaseHelper.COLUMN_FINAL_ORDER_GRAND_TOTAL);
            String due = orderDetailsListCopy.get(i).get(AppDatabaseHelper.COLUMN_FINAL_ORDER_DUE);

            HashMap<String, String> retailerInfo = new HashMap<String, String>();
            retailerInfo = appDatabaseHelper.getRetailerInfo(retailId);

            String retailerName = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
            String retailer_owner = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
            String retailer_image = retailerInfo.get(AppDatabaseHelper.COLUMN_RETAILER_STORE_IMAGE);

            Orders orders = new Orders(id,retailId,retailerName,retailer_owner,total,due,deliveryDate,retailer_image);



            ordersList.add(orders);

        }


    }

    private void onBackPressed(View view){

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("_back_", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("_back_", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    SessionManager sessionManager = new SessionManager(getContext());
                    Fragment fragment = null;
                    if (sessionManager.getEmployeeCategory().equals("SR")){
                        fragment = new HomeFragmentSR();

                    }else if (sessionManager.getEmployeeCategory().equalsIgnoreCase("DE")){
                        fragment = new HomeFragmentSR();
                    }
                    Constants.moveHomeFragment(fragment, getActivity(), getContext());
                    return true;
                }
                return false;
            }
        });
    }
}