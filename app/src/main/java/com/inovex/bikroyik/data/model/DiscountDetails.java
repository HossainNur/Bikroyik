package com.inovex.bikroyik.data.model;

import com.google.gson.annotations.Expose;

public class DiscountDetails {
    int id;
    String discountName, discountType;
    double discount;

    @Expose(serialize = false, deserialize = false)
    String orderId;
    @Expose(serialize = false, deserialize = false)
    String productId;
    @Expose(serialize = false, deserialize = false)
    boolean isDiscountApplied = false;

    public DiscountDetails(int id, String name, String type, double discount, String orderId,
                           String productId, boolean isDiscountApplied) {
        this.id = id;
        this.discountName = name;
        this.discountType = type;
        this.discount = discount;
        this.orderId = orderId;
        this.productId = productId;
        this.isDiscountApplied = isDiscountApplied;
    }
    public DiscountDetails(){}



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getOrderId() {
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

    public boolean isDiscountApplied() {
        return isDiscountApplied;
    }

    public void setDiscountApplied(boolean discountApplied) {
        isDiscountApplied = discountApplied;
    }
}
