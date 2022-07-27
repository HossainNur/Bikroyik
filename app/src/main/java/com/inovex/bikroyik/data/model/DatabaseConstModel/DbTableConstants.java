package com.inovex.bikroyik.data.model.DatabaseConstModel;

public class  DbTableConstants {
    public static class ProductConst{
        public static final String TABLE_PRODUCT = "product_table";
        public static final String productName = "productName";
        public static final String productLabel = "productLabel";
        public static final String category = "category";
        public static final String categoryName = "categoryName";
        public static final String subcategoryName = "subcategoryName";
        public static final String sku = "sku";
        public static final String barcode= "barcode";
        public static final String supplier = "supplier";
        public static final String startStock = "startStock";
        public static final String safetyStock = "safetyStock";
        public static final String color = "color";
        public static final String size = "size";
        public static final String discountId = "discountId";
        public static final String discount = "discount";
        public static final String discountType = "discountType";
        public static final String availableDiscount = "availableDiscount";
        public static final String offerItemId = "offerItemId";
        public static final String availableOffer = "availableOffer";
        public static final String freeItemName = "freeItemName";
        public static final String requiredQuantity = "requiredQuantity";
        public static final String freeQuantity = "freeQuantity";
        public static final String taxId = "taxId";
        public static final String taxName = "taxName";
        public static final String isExcludedTax = "isExcludedTax";
        public static final String tax = "tax";
        public static final String desc = "product_desc";
        public static final String storeName = "storeName";
        public static final String productImage = "productImage";
        public static final String createdBy = "createdBy";
        public static final String updatedBy = "updatedBy";
        public static final String createdAt = "createdAt";
        public static final String updatedAt = "updatedAt";
        public static final String subscriberId = "subscriberId";
        public static final String brand = "brand";
        public static final String onHand = "onHand";
        public static final String mrp = "mrp";
        public static final String measuringType = "measuringType";
        public static final String price = "price";
        public static final String purchaseDate = "purchaseDate";
        public static final String productId = "productId";
    }



    public static class ClientConst{
        public static final String TABLE_CLIENT = "client_information";
        public static final String ID = "id";
        public static final String mobile = "mobile";
        public static final String name = "name";
        public static final String type = "type";
        public static final String email = "email";
        public static final String address = "address";
        public static final String note = "note";
        public static final String storeId = "storeId";
        public static final String image = "image";
        public static final String createdAt = "created_at";
        public static final String createdBy = "created_by";
        public static final String updated = "updated_at";
        public static final String ByUpdated = "updated_by";
        public static final String subscriberId = "subscriber_id";


    }


    public static class PaymentTypeConst{

        public static final String TABLE_PAYMENT_TYPE = "TABLE_PAYMENT_TYPE";
        public static final String payment_id = "id";
        public static final String payment_type = "paymentType";

    }
}
