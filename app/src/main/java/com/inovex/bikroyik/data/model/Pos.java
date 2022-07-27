package com.inovex.bikroyik.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pos implements Serializable {

    @SerializedName("posId")
    @Expose
    private Integer posId;
    @SerializedName("posName")
    @Expose
    private String posName;
    @SerializedName("posStatus")
    @Expose
    private String posStatus;
    @SerializedName("posPin")
    @Expose
    private String posPin;
    @SerializedName("store_id")
    @Expose
    private Integer storeId;

    public Integer getPosId() {
        return posId;
    }

    public void setPosId(Integer posId) {
        this.posId = posId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getPosStatus() {
        return posStatus;
    }

    public void setPosStatus(String posStatus) {
        this.posStatus = posStatus;
    }

    public String getPosPin() {
        return posPin;
    }

    public void setPosPin(String posPin) {
        this.posPin = posPin;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
