package com.inovex.bikroyik.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class SalesActivityViewModel extends ViewModel {


    private static SalesActivityViewModel viewModel;
    private Context mContext;



    private ProductRepository salesOrderRepository;
    private List<ProductModel> productModelList = new ArrayList<>();
    private List<ProductModel> totalSales_arrayList = new ArrayList<>(); //only can use total sales item functions
    private double totalCharge;

    public MutableLiveData<List<ProductModel>> mProductDate_MutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<String>> mAllCategory_MutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ProductModel>> totalAddedProducts = new MutableLiveData<>();

    private MutableLiveData<Boolean> isProductButtonClicked_mu = new MutableLiveData<>();
    private MutableLiveData<Integer> totalSalesItem_mu = new MutableLiveData<>();
    private MutableLiveData<Double> totalPrice_mu = new MutableLiveData<>();



    public static SalesActivityViewModel getInstance(){
        if (viewModel == null){
            viewModel = new SalesActivityViewModel();
            return viewModel;
        }
        return viewModel;
    }

    public void init(Context context){
        this.mContext = context;
        salesOrderRepository = ProductRepository.getInstance(context);
        salesOrderRepository.init(context);

        if (mProductDate_MutableLiveData != null){
            productModelList = salesOrderRepository.getAllProduct_list(context);
            mProductDate_MutableLiveData.setValue(productModelList);

        }else {
            productModelList = salesOrderRepository.getAllProduct_list(context);
            mProductDate_MutableLiveData.setValue(productModelList);
        }

    }

    public LiveData<List<ProductModel>> getProductData(){
        return mProductDate_MutableLiveData;
    }

    public void queryForGetAllProductData(Context context){
        mProductDate_MutableLiveData.setValue(salesOrderRepository.getAllProduct_list(context));
    }

    public void queryForGetAllCategoryProduct(String category){
        mProductDate_MutableLiveData = salesOrderRepository.getCategoryWiseProduct(category);
    }


    public void getProductByCategory(String category){
        List<ProductModel> productModelList_1 = salesOrderRepository.getAllProduct_list(mContext);
        List<ProductModel> productModels = new ArrayList<>();

        for (ProductModel productModel : productModelList_1){
            if (productModel.getCategoryName().equals(category)){
                productModels.add(productModel);
            }
        }
        mProductDate_MutableLiveData.setValue(productModels);
    }

    public void getProductName(String category){
        List<ProductModel> productModelList_1 = salesOrderRepository.getAllProduct_list(mContext);
        List<ProductModel> productModels = new ArrayList<>();

        for (ProductModel productModel : productModelList_1){
            if (productModel.getProductName().equals(category)){
                productModels.add(productModel);
            }
        }
        mProductDate_MutableLiveData.setValue(productModels);
    }

    public void queryForGetAllCategory(Context context){
        mAllCategory_MutableLiveData = salesOrderRepository.getAllCategory(context);
    }

    public LiveData<List<String>> getAllCategory(){
        mAllCategory_MutableLiveData = salesOrderRepository.getAllCategory(mContext);
        return mAllCategory_MutableLiveData;
    }

    public void queryForGetAllProductName(Context context){
        mAllCategory_MutableLiveData = salesOrderRepository.getAllProductName(context);
    }

    public LiveData<List<String>> getAllProductName(){
        mAllCategory_MutableLiveData = salesOrderRepository.getAllProductName(mContext);
        return mAllCategory_MutableLiveData;
    }



    public LiveData<Boolean> isProductButtonClicked(){
        return isProductButtonClicked_mu;
    }

    public void setIsProductButtonClicked_mu(boolean isClicked){
        isProductButtonClicked_mu.setValue(isClicked);
    }

    public void addSellsProduct(ProductModel productModelValue, int totalItems){
//        productModelList_1.clear();
        if (totalAddedProducts.getValue() != null && totalAddedProducts.getValue().size() > 0){
            totalSales_arrayList = totalAddedProducts.getValue();
        }

        for (int i=0; i<totalItems; i++){
            totalSales_arrayList.add(productModelValue);
            totalCharge += productModelValue.getMrp();
        }

        totalAddedProducts.setValue(totalSales_arrayList);
        totalSalesItem_mu.setValue(totalAddedProducts.getValue().size());
        totalPrice_mu.setValue(totalCharge);
    }

    public LiveData<List<ProductModel>> gatSellsProducts(){
        return totalAddedProducts;
    }

    public LiveData<Integer> getTotalSalesItem_mu(){
        return totalSalesItem_mu;
    }

    public LiveData<Double> getTotalPrice_mu(){
        return totalPrice_mu;
    }

    public void updateProductData(String productId, int currentQuantity){
        salesOrderRepository.updateProductQuantityInProductTable(productId, currentQuantity);

    }


}
