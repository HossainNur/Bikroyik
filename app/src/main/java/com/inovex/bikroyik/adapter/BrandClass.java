package com.inovex.bikroyik.adapter;

public class BrandClass {

    String name, origin, image;

    public BrandClass(String name, String origin, String image) {
        this.name = name;
        this.origin = origin;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
