//package com.inovex.merchant.viewmodel;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.inovex.merchant.data.local.SharedPreference;
//import com.inovex.merchant.data.repositories.OrderRepository;
//
//import java.util.List;
//
//
//public class OrderViewModel extends ViewModel {
//    private static OrderViewModel orderViewModel = null;
//    OrderRepository orderRepository;
//    SharedPreference sharedPreference;
//
//    public MutableLiveData<List<OrderModel>> orderModel_mutableList = new MutableLiveData<>();
//
//
//    public static OrderViewModel getInstance(){
//        if (orderViewModel == null){
//            orderViewModel = new OrderViewModel();
//            return orderViewModel;
//        }
//        return orderViewModel;
//    }
//
//    public void init(Context context){
//        orderRepository = OrderRepository.getInstance(context);
//        sharedPreference = SharedPreference.getInstance(context);
//
//
//        if (orderModel_mutableList != null){
//            orderModel_mutableList = orderRepository.getAllOrderData(sharedPreference.getCurrentOrderId());
//        }else {
//            Log.d("_pos_", "orderModel_mutableList not empty!");
//        }
//    }
//
//    public void makeANewOrderForOneProduct(OrderModel orderModel){
//        orderRepository.insertOrderForOneProduct(orderModel);
//        queryForAllOrderData();
//    }
//
//    public void queryForAllOrderData(){
//        orderModel_mutableList = orderRepository.getAllOrderData(sharedPreference.getCurrentOrderId());
//    }
//
//    public LiveData<List<OrderModel>> getAllOrderData(){
//        return orderModel_mutableList;
//    }
//}
