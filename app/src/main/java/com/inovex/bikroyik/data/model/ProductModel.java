package com.inovex.bikroyik.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductModel {
    @SerializedName("productName")
    @Expose
    private String productName;
    @SerializedName("productLabel")
    @Expose
    private String productLabel;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("subcategory_name")
    @Expose
    private String subcategoryName;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("supplier")
    @Expose
    private String supplier;
    @SerializedName("start_stock")
    @Expose
    private Integer startStock;
    @SerializedName("safety_stock")
    @Expose
    private Integer safetyStock;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("discount_id")
    @Expose
    private String discountId;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("discount_type")
    @Expose
    private String discountType;
    @SerializedName("available_discount")
    @Expose
    private String availableDiscount;
    @SerializedName("offerItemId")
    @Expose
    private String offerItemId;
    @SerializedName("available_offer")
    @Expose
    private String availableOffer;
    @SerializedName("freeItemName")
    @Expose
    private String freeItemName;
    @SerializedName("requiredQuantity")
    @Expose
    private Integer requiredQuantity;
    @SerializedName("freeQuantity")
    @Expose
    private Integer freeQuantity;
    @SerializedName("taxId")
    @Expose
    private String taxId;
    @SerializedName("taxName")
    @Expose
    private String taxName;
    @SerializedName("isExcludedTax")
    @Expose
    private String isExcludedTax;
    @SerializedName("tax")
    @Expose
    private Double tax;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("store_name")
    @Expose
    private String storeName;
    @SerializedName("productImage")
    @Expose
    private String productImage;
    @SerializedName("created_by")
    @Expose
    private String createdBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("subscriber_id")
    @Expose
    private Integer subscriberId;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("onHand")
    @Expose
    private Integer onHand;
    @SerializedName("mrp")
    @Expose
    private Double mrp;
    @SerializedName("measuringType")
    @Expose
    private String measuringType;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("productId")
    @Expose
    private String productId;

    public ProductModel(){}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSubcategoryName() {
        return subcategoryName;
    }

    public void setSubcategoryName(String subcategoryName) {
        this.subcategoryName = subcategoryName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Integer getStartStock() {
        return startStock;
    }

    public void setStartStock(Integer startStock) {
        this.startStock = startStock;
    }

    public Integer getSafetyStock() {
        return safetyStock;
    }

    public void setSafetyStock(Integer safetyStock) {
        this.safetyStock = safetyStock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDiscountId() {
        return discountId;
    }

    public void setDiscountId(String discountId) {
        this.discountId = discountId;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getAvailableDiscount() {
        return availableDiscount;
    }

    public void setAvailableDiscount(String availableDiscount) {
        this.availableDiscount = availableDiscount;
    }

    public String getOfferItemId() {
        return offerItemId;
    }

    public void setOfferItemId(String offerItemId) {
        this.offerItemId = offerItemId;
    }

    public String getAvailableOffer() {
        return availableOffer;
    }

    public void setAvailableOffer(String availableOffer) {
        this.availableOffer = availableOffer;
    }

    public String getFreeItemName() {
        return freeItemName;
    }

    public void setFreeItemName(String freeItemName) {
        this.freeItemName = freeItemName;
    }

    public Integer getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(Integer requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public Integer getFreeQuantity() {
        return freeQuantity;
    }

    public void setFreeQuantity(Integer freeQuantity) {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getOnHand() {
        return onHand;
    }

    public void setOnHand(Integer onHand) {
        this.onHand = onHand;
    }

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public String getMeasuringType() {
        return measuringType;
    }

    public void setMeasuringType(String measuringType) {
        this.measuringType = measuringType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
