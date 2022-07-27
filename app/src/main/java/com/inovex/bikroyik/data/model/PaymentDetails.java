package com.inovex.bikroyik.data.model;

public class PaymentDetails {
    String id, name, companyName, logo;

    public PaymentDetails(String id, String name, String companyName, String logo) {
        this.id = id;
        this.name = name;
        this.companyName = companyName;
        this.logo = logo;
    }

    public PaymentDetails(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
