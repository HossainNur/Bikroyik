package com.inovex.bikroyik.data.model;

import java.util.List;

public class OrderJsonModel {
    String orderId="null", orderDate="null", clientName;
    int clientId=0;
    double total= 0.0, totalDiscount= 0.0, totalTax= 0.0, grandTotal= 0.0;
    List<OrderedProductModel> orderDetails = null;
    List<PaymentTypeForOrderJson> paymentDetails = null;
    String storeId, subscriberId, salesBy;

    public OrderJsonModel(String orderId, int clientId, String clientName, double total, double totalDiscount, double totalTax,
                          double grandTotal, String orderDate, List<OrderedProductModel> productModelList) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.total = total;
        this.totalDiscount = totalDiscount;
        this.totalTax = totalTax;
        this.grandTotal = grandTotal;
        this.orderDate = orderDate;
        this.orderDetails = productModelList;
    }
    public OrderJsonModel(String orderId, int
            clientId, double total, double totalDiscount, double totalTax,
                          double grandTotal, String orderDate, List<OrderedProductModel> productModelList, List<PaymentTypeForOrderJson> paymentList) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.total = total;
        this.totalDiscount = totalDiscount;
        this.totalTax = totalTax;
        this.grandTotal = grandTotal;
        this.paymentDetails = paymentList;
        this.orderDate = orderDate;
        this.orderDetails = productModelList;
    }

    public OrderJsonModel(String orderId, String orderDate, String clientName, int clientId, double total,
                          double totalDiscount, double totalTax, double grandTotal, List<OrderedProductModel> orderDetails,
                          List<PaymentTypeForOrderJson> paymentDetails, String storeId, String subscriberId, String salesBy) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.clientId = clientId;
        this.total = total;
        this.totalDiscount = totalDiscount;
        this.totalTax = totalTax;
        this.grandTotal = grandTotal;
        this.orderDetails = orderDetails;
        this.paymentDetails = paymentDetails;
        this.storeId = storeId;
        this.subscriberId = subscriberId;
        this.salesBy = salesBy;
    }

    public OrderJsonModel(){}

    public String getOrderId() {
        return orderId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSalesBy() {
        return salesBy;
    }

    public void setSalesBy(String salesBy) {
        this.salesBy = salesBy;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public List<PaymentTypeForOrderJson> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentTypeForOrderJson> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderedProductModel> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderedProductModel> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
