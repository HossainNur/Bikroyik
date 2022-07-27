package com.inovex.bikroyik.data;

import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.PaymentTypeForOrderJson;
import com.inovex.bikroyik.data.model.Tax;

import java.util.List;

public class OrderDetails {
    private String offerItemId,offerName,productId,productName,orderId;
    private Double grandTotal,offerQuantity,quantity,totalDiscount,totalPrice,totalTax;
    List<DiscountDetails> discountDetails = null;
    List<Tax> taxes = null;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOfferItemId() {
        return offerItemId;
    }

    public void setOfferItemId(String offerItemId) {
        this.offerItemId = offerItemId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Double getOfferQuantity() {
        return offerQuantity;
    }

    public void setOfferQuantity(Double offerQuantity) {
        this.offerQuantity = offerQuantity;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Double totalTax) {
        this.totalTax = totalTax;
    }

    public List<DiscountDetails> getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(List<DiscountDetails> discountDetails) {
        this.discountDetails = discountDetails;
    }

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }
}
