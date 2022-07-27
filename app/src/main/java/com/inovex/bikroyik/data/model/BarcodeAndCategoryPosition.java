package com.inovex.bikroyik.data.model;

public class BarcodeAndCategoryPosition {
    private String barCode;
    private int categoryPosition;

    public BarcodeAndCategoryPosition(String barCode, int categoryPosition) {
        this.barCode = barCode;
        this.categoryPosition = categoryPosition;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public int getCategoryPosition() {
        return categoryPosition;
    }

    public void setCategoryPosition(int categoryPosition) {
        this.categoryPosition = categoryPosition;
    }
}
