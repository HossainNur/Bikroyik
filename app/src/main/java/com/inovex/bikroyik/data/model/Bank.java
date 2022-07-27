package com.inovex.bikroyik.data.model;

public class Bank {
    String name, type, logo;


    public Bank(String name, String type, String logo) {
        this.name = name;
        this.type = type;
        this.logo = logo;
    }

    public Bank(){}



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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
