
package com.inovex.bikroyik.data.model.response_model;

import com.google.gson.annotations.SerializedName;


public class MobilePaymentModel {

    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("id")
    private Long mId;
    @SerializedName("paymentType")
    private String mPaymentType;
    @SerializedName("subscriber_id")
    private Long mSubscriberId;
    @SerializedName("updated_at")
    private Object mUpdatedAt;

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getPaymentType() {
        return mPaymentType;
    }

    public void setPaymentType(String paymentType) {
        mPaymentType = paymentType;
    }

    public Long getSubscriberId() {
        return mSubscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        mSubscriberId = subscriberId;
    }

    public Object getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        mUpdatedAt = updatedAt;
    }

}
