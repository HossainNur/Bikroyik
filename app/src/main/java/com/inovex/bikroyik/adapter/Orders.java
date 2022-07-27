package com.inovex.bikroyik.adapter;

public class Orders {

    String orderId, retailId, retailName, owner, total, due, deliveryDate, image;

    public Orders(String orderId, String retailId, String retailName, String owner, String total, String due, String deliveryDate, String image) {
        this.orderId = orderId;
        this.retailId = retailId;
        this.retailName = retailName;
        this.owner = owner;
        this.total = total;
        this.due = due;
        this.deliveryDate = deliveryDate;
        this.image = image;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRetailId() {
        return retailId;
    }

    public void setRetailId(String retailId) {
        this.retailId = retailId;
    }

    public String getRetailName() {
        return retailName;
    }

    public void setRetailName(String retailName) {
        this.retailName = retailName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
