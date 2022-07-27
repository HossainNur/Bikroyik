package com.inovex.bikroyik.model;

public class DueData {
    String clientId,clientName,mobile,type,email,address,note,storeId,image,subscriber_id,totalDue;

    public DueData(String clientId, String clientName, String mobile, String type, String email, String address, String note, String storeId, String image, String subscriber_id, String totalDue) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.mobile = mobile;
        this.type = type;
        this.email = email;
        this.address = address;
        this.note = note;
        this.storeId = storeId;
        this.image = image;
        this.subscriber_id = subscriber_id;
        this.totalDue = totalDue;
    }

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubscriber_id() {
        return subscriber_id;
    }

    public void setSubscriber_id(String subscriber_id) {
        this.subscriber_id = subscriber_id;
    }

    public String getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(String totalDue) {
        this.totalDue = totalDue;
    }
}
