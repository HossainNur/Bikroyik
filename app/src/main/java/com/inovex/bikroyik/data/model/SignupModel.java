package com.inovex.bikroyik.data.model;

public class SignupModel {

    private  String name,storeName,storeCount,businessType,mobile,packageName,address;

    public SignupModel(String name, String storeName, String storeCount, String businessType, String mobile, String packageName, String address) {
        this.name = name;
        this.storeName = storeName;
        this.storeCount = storeCount;
        this.businessType = businessType;
        this.mobile = mobile;
        this.packageName = packageName;
        this.address = address;
    }

    public SignupModel(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(String storeCount) {
        this.storeCount = storeCount;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
