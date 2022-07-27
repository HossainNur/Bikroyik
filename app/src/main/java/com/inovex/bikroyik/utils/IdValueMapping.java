package com.inovex.bikroyik.utils;

public class IdValueMapping {
    int id;
    boolean value;

    public IdValueMapping(int id, boolean value){
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
