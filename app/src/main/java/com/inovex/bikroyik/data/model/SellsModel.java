package com.inovex.bikroyik.data.model;

public class SellsModel {

    String orderId,orderDate,name,mobile,grandTotal,paid_amount,due;

    public SellsModel(String orderId, String orderDate, String name, String mobile, String grandTotal, String paid_amount, String due) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.name = name;
        this.mobile = mobile;
        this.grandTotal = grandTotal;
        this.paid_amount = paid_amount;
        this.due = due;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(String paid_amount) {
        this.paid_amount = paid_amount;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }
}
