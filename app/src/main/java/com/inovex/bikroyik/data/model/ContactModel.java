package com.inovex.bikroyik.data.model;

public class ContactModel {
    String  mobile, name, type, email, address, note, image, subscriberId;
    String storeId;

    public ContactModel(String subscriberId, String mobile, String name, String type, String email,
                        String address, String note, String image) {
        this.subscriberId = subscriberId;
        this.mobile = mobile;
        this.name = name;
        this.type = type;
        this.email = email;
        this.address = address;
        this.note = note;
        this.image = image;
    }

    public ContactModel(){}

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
