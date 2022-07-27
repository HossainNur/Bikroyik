package com.inovex.bikroyik.data.model;

public class DiscountDatabaseModel extends DiscountDetails {
    String orderId, productId;

    public DiscountDatabaseModel(String orderId, String productId, int discountId,
                                 String discountName, String discountType, double discount, boolean isApplied) {

        super(discountId, discountName, discountType, discount, orderId, productId, isApplied);
        this.orderId = orderId;
        this.productId = productId;
    }

    public DiscountDatabaseModel(){
        super();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setId(int id) {
        this.id = id;
    }

}
