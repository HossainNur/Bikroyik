package com.inovex.bikroyik.data.model;

import java.io.Serializable;
import java.util.List;

public class OrderedProductModel implements Serializable {
    String productId="null", productName="null", offerItemId = "null", offerName= "null";
    double totalDiscount = 0.0, totalPrice= 0.0, grandTotal= 0.0, totalTax= 0.0;

    int quantity=0, offerQuantity=0;
    List<Tax> tax = null;
    List<DiscountDetails> discountDetails = null;

    public OrderedProductModel(String productId, String productName, int quantity, String offerItemId, String offerName,
                               int offerQuantity, double discountAmount, double totalPrice, double grandTotal, double totalTax) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.offerItemId = offerItemId;
        this.offerName = offerName;
        this.offerQuantity = offerQuantity;
        this.totalDiscount = discountAmount;
        this.totalPrice = totalPrice;
        this.grandTotal = grandTotal;
        this.totalTax = totalTax;
    }
    public OrderedProductModel(String productId, String productName, int quantity, String offerItemId, String offerName,
                               int offerQuantity, double discountAmount, double totalPrice, double grandTotal, double totalTax,
                               List<Tax> taxDetailsList, List<DiscountDetails> discountDetailsList) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.offerItemId = offerItemId;
        this.offerName = offerName;
        this.offerQuantity = offerQuantity;
        this.totalDiscount = discountAmount;
        this.discountDetails = discountDetailsList;
        this.totalPrice = totalPrice;
        this.grandTotal = grandTotal;
        this.tax = taxDetailsList;
        this.totalTax = totalTax;
    }



    public OrderedProductModel(){}

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOfferItemId() {
        return offerItemId;
    }

    public void setOfferItemId(String offerItemId) {
        this.offerItemId = offerItemId;
    }

    public int getOfferQuantity() {
        return offerQuantity;
    }

    public void setOfferQuantity(int offerQuantity) {
        this.offerQuantity = offerQuantity;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public List<Tax> getTax() {
        return tax;
    }

    public void setTax(List<Tax> tax) {
        this.tax = tax;
    }

    public List<DiscountDetails> getDiscountDetails() {
        return discountDetails;
    }

    public void setDiscountDetails(List<DiscountDetails> discountDetails) {
        this.discountDetails = discountDetails;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }
}
