package com.inovex.bikroyik.model;

public class Data {
    String title;
    String message;
    String image;

    public Data(String title, String message) {

        this.title = title;
        this.message = message;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
