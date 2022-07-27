package com.inovex.bikroyik.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DueResponseModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("totalStoreDue")
    @Expose
    private Integer totalStoreDue;
    @SerializedName("storeId")
    @Expose
    private String storeId;
    @SerializedName("data")
    @Expose
    private List<DueModel> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Integer getTotalStoreDue() {
        return totalStoreDue;
    }

    public void setTotalStoreDue(Integer totalStoreDue) {
        this.totalStoreDue = totalStoreDue;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public List<DueModel> getData() {
        return data;
    }

    public void setData(List<DueModel> data) {
        this.data = data;
    }

    /* public class DuePaymentModel{
        @SerializedName("total")
        @Expose
        private String total;
        @SerializedName("due")
        @Expose
        private String due;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }*/

}
