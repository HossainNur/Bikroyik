package com.inovex.bikroyik.data.model;

public class PaymentMethod {
    String id,paymentType,created_at,updated_at,subscriber_id;

    public PaymentMethod(String id, String paymentType, String created_at, String updated_at, String subscriber_id) {

        this.id = id;
        this.paymentType = paymentType;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.subscriber_id = subscriber_id;
    }
    public PaymentMethod(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }
}
