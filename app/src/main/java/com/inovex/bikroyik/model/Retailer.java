package com.inovex.bikroyik.model;

public class Retailer extends Market {
    String retailerId, retailerName;

    public Retailer(String marketId, String marketName, String retailerId, String retailerName){
        super(marketId, marketName);
        this.retailerId = retailerId;
        this.retailerName = retailerName;
    }

    public Retailer(){}

    public String getRetailerId() {
        return retailerId;
    }

    public void setRetailerId(String retailerId) {
        this.retailerId = retailerId;
    }

    public String getRetailerName() {
        return retailerName;
    }

    public void setRetailerName(String retailerName) {
        this.retailerName = retailerName;
    }
}
