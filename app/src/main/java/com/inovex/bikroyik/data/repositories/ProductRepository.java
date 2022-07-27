package com.inovex.bikroyik.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private static ProductRepository repository = null;
    private DatabaseSQLite database;
    private Context mContext;

    List<ProductModel> productModelList = new ArrayList<>();

    List<ClientListModel> clientListModelList = new ArrayList<>();

    List<String> categoryList = new ArrayList<>();
    List<String> productNameList = new ArrayList<>();
    MutableLiveData<List<ProductModel>> productModelMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<ProductModel>> categoryWiseProductModelMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<String>> categoryListModelMutableLiveData = new MutableLiveData<>();
    MutableLiveData<List<String>> productNameListModelMutableLiveData = new MutableLiveData<>();

    public static ProductRepository getInstance(Context context){
        if (repository == null){
            repository = new ProductRepository();
            return repository;
        }
        return repository;
    }

    public void init(Context context){
        this.mContext = context;

        database = new DatabaseSQLite(context);
    }

    public List<ProductModel> getAllProduct_list(Context context){
        productModelList.clear();
        productModelList = database.getProductData();

//        productModelMutableLiveData.setValue(productModelList);
        return productModelList;
    }


    public List<ClientListModel> getAllClient_list(Context context){
        clientListModelList.clear();
        clientListModelList = database.getClientData();

//        productModelMutableLiveData.setValue(productModelList);
        return clientListModelList;
    }

    public MutableLiveData<List<ProductModel>> getAllProduct(Context context){
        productModelList.clear();
        productModelList = database.getProductData();

        productModelMutableLiveData.setValue(productModelList);
        return productModelMutableLiveData;
    }

    public MutableLiveData<List<ProductModel>> getCategoryWiseProduct(String categoryName){
        productModelList.clear();
        productModelList = database.getProductDataByCategory(categoryName);

        categoryWiseProductModelMutableLiveData.setValue(productModelList);
        return categoryWiseProductModelMutableLiveData;
    }

    public MutableLiveData<List<ProductModel>> getSpecificBrandProduct(String brandName, String categoryName){
        productModelList.clear();
        productModelList = database.getProductDataByBrandNameAndCategory(brandName, categoryName);

        categoryWiseProductModelMutableLiveData.setValue(productModelList);
        return categoryWiseProductModelMutableLiveData;
    }


    public MutableLiveData<List<String>> getAllCategory(Context context){
        categoryList = database.categoriesList();

        if (categoryList != null && categoryList.size() > 0){
            categoryListModelMutableLiveData.setValue(categoryList);
            return categoryListModelMutableLiveData;
        }

        return categoryListModelMutableLiveData;
    }

    public MutableLiveData<List<String>> getAllProductName(Context context){
        productNameList = database.productNameList();

        if (productNameList != null && productNameList.size() > 0){
            productNameListModelMutableLiveData.setValue(productNameList);
            return productNameListModelMutableLiveData;
        }

        return productNameListModelMutableLiveData;
    }

    public void updateProductQuantityInProductTable(String productId, int currentQuantity){
        database.updateProductQuantity(productId, currentQuantity);
    }
}
