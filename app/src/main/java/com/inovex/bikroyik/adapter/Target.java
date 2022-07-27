package com.inovex.bikroyik.adapter;

public class Target {

    String products,target_qty, sale_qty, target_values, sale_vales;

    public Target(String products, String target_qty, String sale_qty, String target_values, String sale_vales) {
        this.products = products;
        this.target_qty = target_qty;
        this.sale_qty = sale_qty;
        this.target_values = target_values;
        this.sale_vales = sale_vales;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getTarget_qty() {
        return target_qty;
    }

    public void setTarget_qty(String target_qty) {
        this.target_qty = target_qty;
    }

    public String getSale_qty() {
        return sale_qty;
    }

    public void setSale_qty(String sale_qty) {
        this.sale_qty = sale_qty;
    }

    public String getTarget_values() {
        return target_values;
    }

    public void setTarget_values(String target_values) {
        this.target_values = target_values;
    }

    public String getSale_vales() {
        return sale_vales;
    }

    public void setSale_vales(String sale_vales) {
        this.sale_vales = sale_vales;
    }
}
