package com.inovex.bikroyik.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Store implements Serializable {
    @SerializedName("storeId")
    @Expose
    private Integer storeId;
    @SerializedName("storeName")
    @Expose
    private String storeName;
    @SerializedName("storeAddress")
    @Expose
    private String storeAddress;
    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;

    @SerializedName("helpLine")
    @Expose
    private String helpLine;

    @SerializedName("poses")
    @Expose
    private List<Pos> poses = null;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getHelpLine() {
        return helpLine;
    }

    public void setHelpLine(String helpLine) {
        this.helpLine = helpLine;
    }

    public List<Pos> getPoses() {
        return poses;
    }

    public void setPoses(List<Pos> poses) {
        this.poses = poses;
    }

}
