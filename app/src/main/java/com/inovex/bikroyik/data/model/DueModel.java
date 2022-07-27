package com.inovex.bikroyik.data.model;

public class DueModel {

    String type,id,name,mobile,payment_method,storeId,subscriberId,userId,depositDate,image,note;
    Double paid_amount,due_amount;

    public DueModel(){}

    public DueModel(String type, String id, String name, String mobile, String payment_method, String storeId, String subscriberId, String userId, String depositDate, String image, String note, Double paid_amount, Double due_amount) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.payment_method = payment_method;
        this.storeId = storeId;
        this.subscriberId = subscriberId;
        this.userId = userId;
        this.depositDate = depositDate;
        this.image = image;
        this.note = note;
        this.paid_amount = paid_amount;
        this.due_amount = due_amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepositDate() {
        return depositDate;
    }

    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Double getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(Double paid_amount) {
        this.paid_amount = paid_amount;
    }

    public Double getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(Double due_amount) {
        this.due_amount = due_amount;
    }
}
