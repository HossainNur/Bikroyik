
package com.inovex.bikroyik.data.model.response_model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MobileBankingApiResponse {

    @SerializedName("data")
    private List<MobilePaymentModel> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("role")
    private Object mRole;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("statusCode")
    private Long mStatusCode;

    public List<MobilePaymentModel> getData() {
        return mData;
    }

    public void setData(List<MobilePaymentModel> data) {
        mData = data;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Object getRole() {
        return mRole;
    }

    public void setRole(Object role) {
        mRole = role;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(Long statusCode) {
        mStatusCode = statusCode;
    }

}
