package com.inovex.bikroyik.model;

public class PaymentNewModel {

    private String amount,id,type;

    public PaymentNewModel(String amount, String id, String type) {
        this.amount = amount;
        this.id = id;
        this.type = type;
    }
    public PaymentNewModel(){}

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
