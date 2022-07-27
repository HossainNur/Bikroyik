package com.inovex.bikroyik.data.model;

public class BrandModel {
    private String brandName, brandLogo, brandOrigin;

    public BrandModel(String brandName, String brandLogo, String brandOrigin) {
        this.brandName = brandName;
        this.brandLogo = brandLogo;
        this.brandOrigin = brandOrigin;
    }

    public BrandModel(){}

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandOrigin() {
        return brandOrigin;
    }

    public void setBrandOrigin(String brandOrigin) {
        this.brandOrigin = brandOrigin;
    }
}
