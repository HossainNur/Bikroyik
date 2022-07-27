package com.inovex.bikroyik.data.model;

import java.util.List;

public class SalesBusinessModel {
    String categoryName;
    List<ProductModel> productModelList;

    public SalesBusinessModel(String categoryName, List<ProductModel> productModelList){
        this.categoryName = categoryName;
        this.productModelList = productModelList;
    }

    public SalesBusinessModel(){}//default

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductModel> getProductModelList() {
        return productModelList;
    }

    public void setProductModelList(List<ProductModel> productModelList) {
        this.productModelList = productModelList;
    }
}
