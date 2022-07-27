package com.inovex.bikroyik.data.model;

public class TaxModel extends Tax{
    private String orderId, productId;

    public TaxModel(int taxId, String taxPurpose, double taxInPercentage, String orderId, String productId) {
        super(taxId, taxPurpose, taxInPercentage);
        this.orderId = orderId;
        this.productId = productId;
    }

    public TaxModel(){
        super();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
