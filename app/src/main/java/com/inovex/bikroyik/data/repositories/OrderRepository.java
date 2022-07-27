//package com.inovex.merchant.data.repositories;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.Log;
//
//import androidx.lifecycle.MutableLiveData;
//
//import com.google.gson.Gson;
//import com.inovex.merchant.data.local.DatabaseSQLite;
//import com.inovex.merchant.data.model.DiscountDatabaseModel;
//import com.inovex.merchant.data.model.TaxModel;
//import com.inovex.merchant.utils.Constants;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//public class OrderRepository {
//    private Context mContext;
//    private DatabaseSQLite database;
//    private static  OrderRepository orderRepository = null;
//    private MutableLiveData<List<OrderModel>> allOrderedProduct_mutableLiveData = new MutableLiveData<>();
//    private List<OrderModel> orderModelList = new ArrayList<>();
//    List<DiscountDatabaseModel> discountDatabaseModelList = new ArrayList<>();
//    List<TaxModel> taxModelList = new ArrayList<>();
//
//    public static OrderRepository getInstance(Context context){
//        if (orderRepository == null){
//            orderRepository = new OrderRepository(context);
//
//            return orderRepository;
//        }
//        return orderRepository;
//    }
//
//    private OrderRepository(Context context){
//        this.mContext = context;
//        database = new DatabaseSQLite(context);
//    }
//
//
//    public MutableLiveData<List<OrderModel>> getAllOrderData(String orderId){
//        Log.d("complete", "get start!");
//        orderModelList.clear();
//        orderModelList = database.getAllOrderData(orderId);
//
//        Log.d("complete", "get completed!");
//        if (orderModelList != null && orderModelList.size()>0){
//            allOrderedProduct_mutableLiveData.setValue(orderModelList);
//        }
//
//        Gson gson = new Gson();
//        String orderProductJson = gson.toJson(orderModelList);
//        Log.d("_tag_", orderProductJson);
//        return allOrderedProduct_mutableLiveData;
//    }
//
//    public void insertOrderForOneProduct(OrderModel orderModel){
//        HashMap<String, Integer> quantityMap = database.getQuantityForAProduct(orderModel.getOrderId(), orderModel.getProductId());
//        int totalProductQuantity = 0, totalOfferQuantity = 0;
//        if (quantityMap != null){
//            totalProductQuantity = quantityMap.get(Constants.KEY_PRODUCT_QUANTITY);
//            totalOfferQuantity = quantityMap.get(Constants.KEY_OFFER_QUANTITY);
//        }
//
//        if (totalProductQuantity == 0){
//            database.insertNewOrderData(orderModel);
//        }else {
//            totalProductQuantity += orderModel.getQuantity();
//            totalOfferQuantity += orderModel.getOfferQuantity();
//
//            double totalDiscount;
//            double totalTax;
//            double totalPrice;
//
//            if (!TextUtils.isEmpty(orderModel.getTotalDiscount())){
//                totalDiscount = Double.parseDouble(orderModel.getTotalDiscount())* totalProductQuantity;
//            }else {
//                totalDiscount = 0;
//            }
//            if (!TextUtils.isEmpty(orderModel.getTotalTax())){
//                totalTax = Double.parseDouble(orderModel.getTotalTax()) * totalProductQuantity;
//            }else {
//                totalTax = 0;
//            }
//            if (!TextUtils.isEmpty(orderModel.getTotalPrice())){
//                totalPrice = Double.parseDouble(orderModel.getTotalPrice())*totalProductQuantity;
//            }else {
//                totalPrice = 0;
//            }
//
//            orderModel.setQuantity(totalProductQuantity);
//            orderModel.setTotalDiscount(String.valueOf(totalDiscount));
//            orderModel.setTotalTax(String.valueOf(totalTax));
//            orderModel.setTotalPrice(String.valueOf(totalPrice));
//            orderModel.setOfferQuantity(totalOfferQuantity);
//
//            database.updateOrderData(orderModel);
//            Log.d("complete", "update completed!");
//        }
//    }
//
//
//    private void insertDiscountInDatabase(Context context, List<DiscountDatabaseModel> discountDatabaseModelList){
//        int n = 0;
//        while (n < discountDatabaseModelList.size()){
//            database.insertDiscountData(discountDatabaseModelList.get(n));
//            n++;
//        }
//    }
//    private List<DiscountDatabaseModel> getDiscountFromDatabase(){
//        return database.getAllDiscount();
//    }
//
//    private void insertTaxInDatabase(Context context, List<TaxModel> taxModelList){
//        int n = 0;
//        while (n < taxModelList.size()){
//            database.insertTaxInfo(taxModelList.get(n));
//            n++;
//        }
//    }
//    private List<TaxModel> getTaxFromDatabase(){
//        return database.getAllTaxInfo();
//    }
//
//    private void insertOngoingSellDiscount(Context context, List<DiscountDatabaseModel> discountDatabaseModelList){
//        int n = 0;
//        while (n < discountDatabaseModelList.size()){
//            database.insertDiscount(discountDatabaseModelList.get(n));
//            n++;
//        }
//    }
//    private List<DiscountDatabaseModel> getOngoingSellDiscount(String orderId, String productId){
//        if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(productId)){
//            discountDatabaseModelList.clear();
//            discountDatabaseModelList = database.getOnGoingOrderDiscount(orderId);
//        }
//
//        return discountDatabaseModelList;
//    }
//
//    private void insertOngoingSellTax(Context context, List<TaxModel> taxModelList){
//        int n = 0;
//        while (n < taxModelList.size()){
//            database.insertTaxInfo(taxModelList.get(n));
//            n++;
//        }
//    }
//
//    private List<TaxModel> getOngoingSellingTax(String orderId, String productId){
//        if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(productId)){
//            taxModelList.clear();
//            taxModelList = database.getOnGoingOrderTax(orderId, productId);
//        }
//
//        return taxModelList;
//    }
//
//
//}
