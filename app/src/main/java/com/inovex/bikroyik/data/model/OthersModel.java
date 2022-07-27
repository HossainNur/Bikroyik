package com.inovex.bikroyik.data.model;

public class OthersModel {
    String  expenseType, date, note, image, subscriberId,submittedBy;
    String storeId;
    int amount;

    public OthersModel(String expenseType, String date, String note, String image, String subscriberId,String submittedBy, int amount) {
        this.expenseType = expenseType;
        this.date = date;
        this.note = note;
        this.image = image;
        this.subscriberId = subscriberId;
        this.submittedBy = submittedBy;
        this.amount = amount;
    }

    public OthersModel(){}

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
