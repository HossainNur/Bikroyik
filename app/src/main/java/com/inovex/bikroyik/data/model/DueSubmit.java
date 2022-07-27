package com.inovex.bikroyik.data.model;

public class DueSubmit {
    String clientId;
    String userId,storeId,date;
    String subscriberId;
    double total, cash, card, mobile_bank, due_amount;

    public DueSubmit(String clientId, String userId, String storeId, String date, String subscriberId, double total, double cash, double card, double mobile_bank, double due_amount) {
        this.clientId = clientId;
        this.userId = userId;
        this.storeId = storeId;
        this.date = date;
        this.subscriberId = subscriberId;
        this.total = total;
        this.cash = cash;
        this.card = card;
        this.mobile_bank = mobile_bank;
        this.due_amount = due_amount;
    }

    public DueSubmit(){}

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getCard() {
        return card;
    }

    public void setCard(double card) {
        this.card = card;
    }

    public double getMobile_bank() {
        return mobile_bank;
    }

    public void setMobile_bank(double mobile_bank) {
        this.mobile_bank = mobile_bank;
    }

    public double getDue_amount() {
        return due_amount;
    }

    public void setDue_amount(double due_amount) {
        this.due_amount = due_amount;
    }
}
