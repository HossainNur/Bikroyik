package com.inovex.bikroyik.data.model;

public class PaymentTypeModel {

    private String orderId,type_payment,mfsId,clientId,clientName;
    private Double cash,card,type_amount,total_amount,due;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType_payment() {
        return type_payment;
    }

    public void setType_payment(String type_payment) {
        this.type_payment = type_payment;
    }

    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getCard() {
        return card;
    }

    public String getMfsId() {
        return mfsId;
    }

    public void setMfsId(String mfsId) {
        this.mfsId = mfsId;
    }

    public void setCard(Double card) {
        this.card = card;
    }

    public Double getType_amount() {
        return type_amount;
    }

    public void setType_amount(Double type_amount) {
        this.type_amount = type_amount;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getDue() {
        return due;
    }

    public void setDue(Double due) {
        this.due = due;
    }
}
