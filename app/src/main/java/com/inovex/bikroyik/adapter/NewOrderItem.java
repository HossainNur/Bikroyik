package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 8/8/2018.
 */

public class NewOrderItem {
    String newOrderItemId;
    String name;
    double price;
    int quantity;
    int discount;
    double currentPrice;
    int productPositionInList;

    public int getProductPositionInList() {
        return productPositionInList;
    }

    public void setProductPositionInList(int productPositionInList) {
        this.productPositionInList = productPositionInList;
    }



 public NewOrderItem(){

 }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    double totalPrice;

    public String getNewOrderItemId() {
        return newOrderItemId;
    }

    public void setNewOrderItemId(String newOrderItemId) {
        this.newOrderItemId = newOrderItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public NewOrderItem(String orderId, String name, double price, int quantity, int discount, double currentPrice,double totalPrice) {

        this.newOrderItemId = orderId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.currentPrice = currentPrice;
        this.totalPrice=totalPrice;
    }
}
