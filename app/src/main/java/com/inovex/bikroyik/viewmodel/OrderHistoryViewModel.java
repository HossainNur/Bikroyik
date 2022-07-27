package com.inovex.bikroyik.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.repositories.OrderHistoryRepo;

import java.util.List;

public class OrderHistoryViewModel extends ViewModel {
    private static OrderHistoryViewModel viewModel = null;
    private Context mContext;
    private OrderHistoryRepo repo;
    private MutableLiveData<List<OrderJsonModel>> orderJsonModelListMuLive = new MutableLiveData<>();
    private SharedPreference sharedPreference;

    List<OrderJsonModel> orderJsonModelList = null;

    public static OrderHistoryViewModel getInstance(){
        if (viewModel == null){
            viewModel = new OrderHistoryViewModel();
            return viewModel;
        }
        return viewModel;
    }

    public void init(Context context){
        mContext = context;
        repo = OrderHistoryRepo.getInstance(context);
        repo.init(context);

        if (orderJsonModelListMuLive != null){
            orderJsonModelList = repo.getSavedOrdersList();
            if (orderJsonModelList != null){
                orderJsonModelListMuLive.setValue(orderJsonModelList);
            }

        }else {
            orderJsonModelListMuLive = new MutableLiveData<>();
            orderJsonModelList = repo.getSavedOrdersList();
            if (orderJsonModelList != null){
                orderJsonModelListMuLive.setValue(orderJsonModelList);
            }
        }
    }

    public void queryInAllSavedOrder(){
        orderJsonModelList = repo.getSavedOrdersList();
        if (orderJsonModelList != null){
            orderJsonModelListMuLive.setValue(orderJsonModelList);
        }
    }

    public LiveData<List<OrderJsonModel>> getAllSavedOrder(){
        return orderJsonModelListMuLive;
    }
}
