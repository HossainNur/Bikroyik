package com.inovex.bikroyik.data.repositories;

import android.content.Context;

import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.model.OrderJsonModel;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryRepo {
    private static OrderHistoryRepo orderHistoryRepo = null;
    private static Context mContext;
    private  DatabaseSQLite databaseSQLite;
    private List<OrderJsonModel> orderJsonModelList;


    public static OrderHistoryRepo getInstance(Context context){
        if (orderHistoryRepo == null){
            orderHistoryRepo = new OrderHistoryRepo();
            return orderHistoryRepo;
        }

        return orderHistoryRepo;
    }

    public void init(Context context){
        this.mContext = context;
        this.databaseSQLite = new DatabaseSQLite(context);
        orderJsonModelList = new ArrayList<>();
    }

    public List<OrderJsonModel> getSavedOrdersList(){
        orderJsonModelList.clear();
        orderJsonModelList = databaseSQLite.getAllSavedOrderData();

        return orderJsonModelList;
    }
}
