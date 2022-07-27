package com.inovex.bikroyik.model;

import java.io.Serializable;

public class DueCollection extends Retailer implements Serializable {

    String orderId, orderDate;
    long totalAmount, grandTotal, discount, totalDue;

    public DueCollection(String marketId, String marketName, String retailerId, String retailerName,
                         String orderId, String orderDate, long totalAmount, long grandTotal, long discount, long totalDue) {
        super(marketId, marketName, retailerId, retailerName);
        this.totalDue = totalDue;
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.grandTotal = grandTotal;
        this.discount = discount;
    }
    public DueCollection(){}

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(long grandTotal) {
        this.grandTotal = grandTotal;
    }

    public long getDiscount() {
        return discount;
    }

    public void setDiscount(long discount) {
        this.discount = discount;
    }

    public long getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(long totalDue) {
        this.totalDue = totalDue;
    }
}
