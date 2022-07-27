package com.inovex.bikroyik.data.model;

import com.google.gson.annotations.Expose;

public class Tax {
    private int id;
    private String taxName;
    double taxAmount;

    @Expose(serialize = false, deserialize = false)
    String orderId;
    @Expose(serialize = false, deserialize = false)
    String productId;
    @Expose(serialize = false, deserialize = false)
    boolean isTaxApplied = false;


    public Tax(int id, String taxPurpose, double taxInPercentage) {
        this.id = id;
        this.taxName = taxPurpose;
        this.taxAmount = taxInPercentage;
    }

    public Tax(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String  getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public boolean getIsTaxApplied() {
        return isTaxApplied;
    }

    public void setIsTaxApplied(boolean isTaxApplied) {
        this.isTaxApplied = isTaxApplied;
    }
}
