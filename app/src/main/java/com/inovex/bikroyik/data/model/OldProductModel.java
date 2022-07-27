package com.inovex.bikroyik.data.model;

public class OldProductModel {
    String productId, brand, productName;
    int onHand;
    String sku, productLabel, category, productImage;
    String category_name, desc, measuringType;
    int start_stock, safety_stock;
    double price, mrp;
    String discount_id, discount_type, available_discount;
    double discount;
    String available_offer, offerItemId, freeItemName;
    int requiredQuantity, freeQuantity;
    String taxId, taxName, isExcludedTax;
    double tax;


    public OldProductModel(String productId, String brand, String productName, int onHand, String sku,
                        String productLabel, String category, String productImage, String category_name, String desc, String discount_id,
                        String measuringType, int start_stock, int safety_stock, double price, double mrp,
                        String discount_type, String available_discount, double discount,  String available_offer, String offerItemId,
                        String freeItemName, int requiredQuantity,
                        int freeQuantity, String taxId, String taxName, String isExcludedTax, double tax) {
        this.productId = productId;
        this.brand = brand;
        this.productName = productName;
        this.onHand = onHand;
        this.sku = sku;
        this.productLabel = productLabel;
        this.category = category;
        this.productImage = productImage;
        this.category_name = category_name;
        this.desc = desc;
        this.measuringType = measuringType;
        this.start_stock = start_stock;
        this.safety_stock = safety_stock;
        this.price = price;
        this.mrp = mrp;
        this.discount_id = discount_id;
        this.discount_type = discount_type;
        this.available_discount = available_discount;
        this.discount = discount;
        this.available_offer = available_offer;
        this.offerItemId = offerItemId;
        this.freeItemName = freeItemName;
        this.requiredQuantity = requiredQuantity;
        this.freeQuantity = freeQuantity;
        this.taxId = taxId;
        this.taxName = taxName;
        this.isExcludedTax = isExcludedTax;
        this.tax = tax;
    }

    public OldProductModel(){}

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getOnHand() {
        return onHand;
    }

    public void setOnHand(int onHand) {
        this.onHand = onHand;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMeasuringType() {
        return measuringType;
    }

    public void setMeasuringType(String measuringType) {
        this.measuringType = measuringType;
    }

    public int getStart_stock() {
        return start_stock;
    }

    public void setStart_stock(int start_stock) {
        this.start_stock = start_stock;
    }

    public int getSafety_stock() {
        return safety_stock;
    }

    public void setSafety_stock(int safety_stock) {
        this.safety_stock = safety_stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public String getDiscount_id() {
        return discount_id;
    }

    public void setDiscount_id(String discount_id) {
        this.discount_id = discount_id;
    }

    public String getDiscount_type() {
        return discount_type;
    }

    public void setDiscount_type(String discount_type) {
        this.discount_type = discount_type;
    }

    public String getAvailable_discount() {
        return available_discount;
    }

    public void setAvailable_discount(String available_discount) {
        this.available_discount = available_discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getAvailable_offer() {
        return available_offer;
    }

    public void setAvailable_offer(String available_offer) {
        this.available_offer = available_offer;
    }

    public String getOfferItemId() {
        return offerItemId;
    }

    public void setOfferItemId(String offerItemId) {
        this.offerItemId = offerItemId;
    }

    public String getFreeItemName() {
        return freeItemName;
    }

    public void setFreeItemName(String freeItemName) {
        this.freeItemName = freeItemName;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public int getFreeQuantity() {
        return freeQuantity;
    }

    public void setFreeQuantity(int freeQuantity) {
        this.freeQuantity = freeQuantity;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getIsExcludedTax() {
        return isExcludedTax;
    }

    public void setIsExcludedTax(String isExcludedTax) {
        this.isExcludedTax = isExcludedTax;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }
}
