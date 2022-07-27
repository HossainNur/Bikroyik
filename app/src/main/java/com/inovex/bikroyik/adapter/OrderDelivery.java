package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 8/14/2018.
 */

public class OrderDelivery {
    String orderId;
    String orderDate;
    String retailerName;
    String retailerOwner;
    String retailerAddress;
    String orderStatus;
    String retailerContact;
    String ordertotalPrice;
    String orderTotalDue;

    String distributorName;

    public OrderDelivery(String orderId, String orderDate, String retailerName, String retailerOwner, String retailerAddress, String orderStatus, String retailerContact, String ordertotalPrice, String orderTotalDue, String distributorName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.retailerName = retailerName;
        this.retailerOwner = retailerOwner;
        this.retailerAddress = retailerAddress;
        this.orderStatus = orderStatus;
        this.retailerContact = retailerContact;
        this.ordertotalPrice = ordertotalPrice;
        this.orderTotalDue = orderTotalDue;
        this.distributorName = distributorName;
    }

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

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }

    public String getRetailerOwner() {
        return retailerOwner;
    }

    public void setRetailerOwner(String retailerOwner) {
        this.retailerOwner = retailerOwner;
    }

    public String getRetailerAddress() {
        return retailerAddress;
    }

    public void setRetailerAddress(String retailerAddress) {
        this.retailerAddress = retailerAddress;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRetailerContact() {
        return retailerContact;
    }

    public void setRetailerContact(String retailerContact) {
        this.retailerContact = retailerContact;
    }

    public String getOrdertotalPrice() {
        return ordertotalPrice;
    }

    public void setOrdertotalPrice(String ordertotalPrice) {
        this.ordertotalPrice = ordertotalPrice;
    }

    public String getOrderTotalDue() {
        return orderTotalDue;
    }

    public void setOrderTotalDue(String orderTotalDue) {
        this.orderTotalDue = orderTotalDue;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }
}
