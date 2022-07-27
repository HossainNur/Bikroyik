package com.inovex.bikroyik.data.local;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.inovex.bikroyik.data.model.BrandModel;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.DatabaseConstModel.DbTableConstants;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.PaymentDetails;
import com.inovex.bikroyik.data.model.PaymentMethod;
import com.inovex.bikroyik.data.model.PaymentModel;
import com.inovex.bikroyik.data.model.PaymentTypeModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.data.model.User;
import com.inovex.bikroyik.data.model.response_model.MobilePaymentModel;
import com.inovex.bikroyik.model.PaymentNewModel;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseSQLite extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 18;
    // Database Name
    private static final String DATABASE_NAME = "merchant";


    List<OrderedProductModel> orderedProductModelList = new ArrayList<>();
    //homepage view settings
    private static final String TABLE_HOMEPAGE_SETTINGS = "homePage_table";

    private static final String COLUMN_USER_ID_hTable = "user_id";
    private static final String COLUMN_IS_IN_HOMEPAGE = "status";
    private static final String COLUMN_FEATURE_NAME = "featureName";
    private static final String COLUMN_HOMEPAGE_POSITION = "option";
    private static final String COLUMN_FEATURE_DRAWABLE = "drawableString";


    //user table
    private static final String TABLE_USER = "user_table";

    private static final String COLUMN_ORGANIZATION_ID = "orgId";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_ADDRESS = "user_address";
    private static final String COLUMN_USER_PHONE = "user_phone";
    private static final String COLUMN_USER_CATEGORY = "user_category";
    private static final String COLUMN_USER_REPORTING_ID = "reporting_id";
    private static final String COLUMN_USER_REPORTING_NAME = "reporting_name";
    private static final String COLUMN_USER_DISTRIBUTOR_ID = "distributor_id";
    private static final String COLUMN_USER_DISTRIBUTOR_NAME = "distributor_name";
    private static final String COLUMN_USER_DISTRIBUTOR_ADDRESS = "distributor_address";
    private static final String COLUMN_USER_DISTRIBUTOR_MOBILE = "distributor_mobile";
    private static final String COLUMN_USER_REPORTING_MOBILE = "reporting_mobile";
    private static final String COLUMN_USER_TERRITORY_NAME = "territory_name";
    private static final String COLUMN_USER_AREA_NAME = "area_name";
    private static final String COLUMN_USER_REGION_NAME = "region_name";
    private static final String COLUMN_USER_IMAGE = "image_name";


    //brand table
    private static final String TABLE_BRAND = "table_brand";

    private static final String COLUMN_BRAND_NAME = "name";
    private static final String COLUMN_BRAND_ORIGIN = "origin";
    private static final String COLUMN_BRAND_LOGO = "logo";


    //table payment system
    private static final String TABLE_PAYMENT = "table_payment";
    private static final String COLUMN_PAYMENT_ID = "payment_id";
    private static final String COLUMN_PAYMENT_NAME = "name";
    private static final String COLUMN_PAYMENT_COMPANY_NAME = "company_name";
    private static final String COLUMN_PAYMENT_LOGO = "logo";

    //table tax
    private static final String TABLE_TAX = "table_tax";
    private static final String COLUMN_TAX_ID = "vat_id";
    private static final String COLUMN_TAX_NAME = "name";
    private static final String COLUMN_TAX_IN_PERCENTAGE = "value";
    private static final String COLUMN_IS_TAX_AVAILABLE = "availability";
    private static final String COLUMN_TAX_ORDER_ID = "order_id";
    private static final String COLUMN_TAX_PRODUCT_ID = "product_id";

    //table discount
    private static final String TABLE_DISCOUNT = "table_discount";
    private static final String COLUMN_DISCOUNT_ID = "discount_id";
    private static final String COLUMN_DISCOUNT_NAME = "discount_name";
    private static final String COLUMN_DISCOUNT_TYPE = "discount_type";
    private static final String COLUMN_DISCOUNT_AMOUNT = "discount";
    private static final String COLUMN_IS_DISCOUNT_AVAILABLE = "availability";
    private static final String COLUMN_DISCOUNT_ORDER_ID = "order_id";
    private static final String COLUMN_DISCOUNT_PRODUCT_ID = "product_id";

    //order table
    private static final String TABLE_ORDER = "order_table";
    private static final String COLUMN_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_CLIENT_ID = "client_id";
    private static final String COLUMN_ORDER_CLIENT_NAME = "client_name";
    private static final String COLUMN_TOTAL_PRICE = "total_price";
    private static final String COLUMN_TOTAL_DISCOUNT = "total_discount";
    private static final String COLUMN_TOTAL_TAX = "total_tax";
    private static final String COLUMN_GRAND_TOTAL = "grand_total";
    private static final String COLUMN_ORDER_DATE = "order_date";
    private static final String COLUMN_IS_ORDER_COMPLETED = "is_order_completed";



    //ordered product ORDERED
    private static final String TABLE_ORDERED_PRODUCT = "table_ordered_product";
    private static final String COLUMN_ORDERED_ORDER_ID = "order_id";
    private static final String COLUMN_ORDERED_PRODUCT_ID = "product_id";
    private static final String COLUMN_ORDERED_PRODUCT_NAME = "product_name";
    private static final String COLUMN_ORDERED_PRODUCT_QUANTITY = "quantity";
    private static final String COLUMN_ORDERED_OFFER_ITEM_ID = "offer_item_id";
    private static final String COLUMN_ORDERED_OFFER_QUANTITY = "offer_quantity";
    private static final String COLUMN_ORDERED_OFFER_NAME = "offer_name";
    private static final String COLUMN_ORDERED_PRODUCT_TOTAL_DISCOUNT = "total_discount";
    private static final String COLUMN_ORDERED_TOTAL_TAX = "total_tax";
    private static final String COLUMN_ORDERED_TOTAL_PRICE = "total_price";
    private static final String COLUMN_ORDERED_GRAND_TOTAL = "grand_total";


//    //table vat --ongoing
//    private static final String TABLE_ONGOING_TAX = "table_ongoing_tax";
//    private static final String COLUMN_ONGOING_TAX_ID = "tax_id";
//    private static final String COLUMN_ONGOING_TAX_ORDERED_ID = "order_id";
//    private static final String COLUMN_ONGOING_TAX_PRODUCT_ID = "product_id";
//
//
//    //table discount --ongoing
//    private static final String TABLE_ONGOING_DISCOUNT = "table_ongoing_discount";
//    private static final String COLUMN_ONGOING_DISCOUNT_ID = "discount_no";
//    private static final String COLUMN_ONGOING_DISCOUNT_ORDER_ID = "id";
//    private static final String COLUMN_ONGOING_DISCOUNT_PRODUCT_ID = "product_id";

    //table payment --ongoing

    private static final String TABLE_ONGOING_PAYMENT = "table_ongoing_payment";
    private static final String COLUMN_ONGOING_PAYMENT_ID = "payment_id";
    private static final String COLUMN_ONGOING_PAYMENT_ORDERED_ID = "order_id";
    private static final String COLUMN_ONGOING_PAYMENT_TOTAL_PAY = "total_payment";


    private static final String TABLE_PAYMENT_METHOD = "table_payment_order";
    private static final String COLUMN_CASH = "cash";
    private static final String COLUMN_MFS_ID = "MFS_ID";
    private static final String COLUMN_TYPE_MFS = "MFS";
    private static final String COLUMN_DUE = "due";
    private static final String COLUMN_ORDER_ID_CURRENT = "orderId";
    private static final String COLUMN_CARD = "card";
    private static final String COLUMN_MFS_AMOUNT = "mfs_amount";
    private static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    private static final String COLUMN_CLIENT_ID = "client_id";
    private static final String COLUMN_CLIENT_NAME= "client_name";



    private static final String TABLE_PAYMENT_TYPE = "table_payment_type";
    private static final String COLUMN_TYPE_PAYMENT_ID = "id";
    private static final String COLUMN_TYPE_PAYMENT = "paymentType";



    /*private static final String COLUMN_ONGOING_PAYMENT_ID = "id";
    private static final String COLUMN_ONGOING_PAYMENT_AMOUNT = "amount";
    private static final String COLUMN_ONGOING_PAYMENT_TYPE= "type";*/

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_HOMEPAGE_SETTINGS + "(" +
                    COLUMN_USER_ID_hTable + " LONGTEXT," +
                    COLUMN_FEATURE_NAME + " TEXT," +
                    COLUMN_FEATURE_DRAWABLE + " TEXT," +
                    COLUMN_IS_IN_HOMEPAGE + " TEXT," +
                    COLUMN_HOMEPAGE_POSITION + " INTEGER" +
                    ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PAYMENT_METHOD + "(" +
                    COLUMN_ORDER_ID_CURRENT + " LONGTEXT," +
                    COLUMN_CASH + " LONGTEXT," +
                    COLUMN_CARD + " LONGTEXT," +
                    COLUMN_MFS_ID + " LONGTEXT," +
                    COLUMN_TYPE_MFS + " LONGTEXT," +
                    COLUMN_MFS_AMOUNT + " LONGTEXT," +
                    COLUMN_TOTAL_AMOUNT + " LONGTEXT," +
                    COLUMN_DUE+ " LONGTEXT," +
                    COLUMN_CLIENT_ID+ " LONGTEXT," +
                    COLUMN_CLIENT_NAME+ " LONGTEXT" +
                    ");");


            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USER + "(" +
                    COLUMN_ORGANIZATION_ID + " LONGTEXT," +
                    COLUMN_USER_ID + " LONGTEXT," +
                    COLUMN_PASSWORD + " LONGTEXT," +
                    COLUMN_USER_NAME + " TEXT," +
                    COLUMN_USER_ADDRESS + " TEXT," +
                    COLUMN_USER_PHONE + " TEXT," +
                    COLUMN_USER_CATEGORY + " TEXT," +
                    COLUMN_USER_REPORTING_ID + " LONGTEXT," +
                    COLUMN_USER_REPORTING_NAME + " TEXT," +
                    COLUMN_USER_DISTRIBUTOR_ID + " LONGTEXT," +
                    COLUMN_USER_DISTRIBUTOR_NAME + " TEXT," +
                    COLUMN_USER_DISTRIBUTOR_ADDRESS + " TEXT," +
                    COLUMN_USER_DISTRIBUTOR_MOBILE + " TEXT," +
                    COLUMN_USER_REPORTING_MOBILE + " TEXT," +
                    COLUMN_USER_TERRITORY_NAME + " TEXT," +
                    COLUMN_USER_AREA_NAME + " TEXT," +
                    COLUMN_USER_IMAGE + " TEXT," +
                    COLUMN_USER_REGION_NAME + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BRAND + "(" +
                    COLUMN_BRAND_NAME + " TEXT," +
                    COLUMN_BRAND_ORIGIN + " TEXT," +
                    COLUMN_BRAND_LOGO + " TEXT " +
                    ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + DbTableConstants.ProductConst.TABLE_PRODUCT + "(" +
                    DbTableConstants.ProductConst.productId + " TEXT," +
                    DbTableConstants.ProductConst.productName + " TEXT," +
                    DbTableConstants.ProductConst.productImage + " TEXT," +
                    DbTableConstants.ProductConst.brand + " TEXT," +
                    DbTableConstants.ProductConst.subcategoryName + " TEXT," +
                    DbTableConstants.ProductConst.sku + " TEXT," +
                    DbTableConstants.ProductConst.barcode + " TEXT," +
                    DbTableConstants.ProductConst.supplier + " TEXT," +
                    DbTableConstants.ProductConst.productLabel + " TEXT," +
                    DbTableConstants.ProductConst.category + " TEXT," +
                    DbTableConstants.ProductConst.categoryName + " TEXT," +
                    DbTableConstants.ProductConst.desc + " TEXT," +
                    DbTableConstants.ProductConst.measuringType + " TEXT," +
                    DbTableConstants.ProductConst.startStock + " INTEGER," +
                    DbTableConstants.ProductConst.safetyStock + " INTEGER," +
                    DbTableConstants.ProductConst.onHand + " INTEGER," +
                    DbTableConstants.ProductConst.price + " REAL," +
                    DbTableConstants.ProductConst.mrp + " REAL," +
                    DbTableConstants.ProductConst.discountId+" TEXT,"+
                    DbTableConstants.ProductConst.discount + " REAL," +
                    DbTableConstants.ProductConst.discountType + " TEXT," +
                    DbTableConstants.ProductConst.availableDiscount + " TEXT," +
                    DbTableConstants.ProductConst.offerItemId + " TEXT," +
                    DbTableConstants.ProductConst.availableOffer + " TEXT," +
                    DbTableConstants.ProductConst.freeItemName + " TEXT," +
                    DbTableConstants.ProductConst.requiredQuantity + " INTEGER," +
                    DbTableConstants.ProductConst.freeQuantity + " INTEGER," +
                    DbTableConstants.ProductConst.taxId + " TEXT," +
                    DbTableConstants.ProductConst.taxName + " TEXT," +
                    DbTableConstants.ProductConst.isExcludedTax + " TEXT," +
                    DbTableConstants.ProductConst.tax + " REAL," +
                    DbTableConstants.ProductConst.storeName + " TEXT," +
                    DbTableConstants.ProductConst.createdBy + " TEXT," +
                    DbTableConstants.ProductConst.createdAt + " TEXT," +
                    DbTableConstants.ProductConst.updatedBy + " TEXT," +
                    DbTableConstants.ProductConst.updatedAt + " TEXT," +
                    DbTableConstants.ProductConst.purchaseDate + " TEXT," +
                    DbTableConstants.ProductConst.color + " TEXT," +
                    DbTableConstants.ProductConst.size + " TEXT," +
                    DbTableConstants.ProductConst.subscriberId + " INTEGER" +
                    ");");



            db.execSQL("CREATE TABLE IF NOT EXISTS " + DbTableConstants.ClientConst.TABLE_CLIENT + "(" +
                    DbTableConstants.ClientConst.ID + " TEXT," +
                    DbTableConstants.ClientConst.mobile + " TEXT," +
                    DbTableConstants.ClientConst.name + " TEXT," +
                    DbTableConstants.ClientConst.type + " TEXT," +
                    DbTableConstants.ClientConst.email + " TEXT," +
                    DbTableConstants.ClientConst.address + " TEXT," +
                    DbTableConstants.ClientConst.note + " TEXT," +
                    DbTableConstants.ClientConst.storeId + " TEXT," +
                    DbTableConstants.ClientConst.image + " TEXT," +
                    DbTableConstants.ClientConst.createdAt + " TEXT," +
                    DbTableConstants.ClientConst.createdBy + " TEXT," +
                    DbTableConstants.ClientConst.updated + " TEXT," +
                    DbTableConstants.ClientConst.ByUpdated + " TEXT," +
                    DbTableConstants.ClientConst.subscriberId + " INTEGER" +
                    ");");





            db.execSQL("CREATE TABLE IF NOT EXISTS " + DbTableConstants.PaymentTypeConst.TABLE_PAYMENT_TYPE + "(" +
                    DbTableConstants.PaymentTypeConst.payment_id + " INTEGER," +
                    DbTableConstants.PaymentTypeConst.payment_type + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_PAYMENT + "(" +
                    COLUMN_PAYMENT_ID + " LONGTEXT," +
                    COLUMN_PAYMENT_NAME + " LONGTEXT," +
                    COLUMN_PAYMENT_COMPANY_NAME + " LONGTEXT," +
                    COLUMN_PAYMENT_LOGO + " LONGTEXT" +
                    ");");


            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_DISCOUNT + "(" +
                    COLUMN_DISCOUNT_ID + " INTEGER," +
                    COLUMN_DISCOUNT_NAME + " LONGTEXT," +
                    COLUMN_DISCOUNT_TYPE + " LONGTEXT," +
                    COLUMN_DISCOUNT_AMOUNT + " REAL," +
                    COLUMN_IS_DISCOUNT_AVAILABLE+ " TEXT DEFAULT 'false',"+
                    COLUMN_TAX_ORDER_ID + " LONGTEXT,"+
                    COLUMN_TAX_PRODUCT_ID +" LONGTEXT"+
                    ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TAX + "(" +
                    COLUMN_TAX_ID + " INTEGER," +
                    COLUMN_TAX_NAME + " LONGTEXT," +
                    COLUMN_TAX_IN_PERCENTAGE + " REAL," +
                    COLUMN_IS_TAX_AVAILABLE + " TEXT,"+
                    COLUMN_DISCOUNT_ORDER_ID + " LONGTEXT,"+
                    COLUMN_DISCOUNT_PRODUCT_ID +" LONGTEXT"+
                    ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDER + "(" +
                    COLUMN_ORDER_ID + " LONGTEXT ," +
                    COLUMN_ORDER_CLIENT_ID + " INTEGER," +
                    COLUMN_ORDER_CLIENT_NAME + " TEXT,"+
                    COLUMN_TOTAL_PRICE+ " REAL,"+
                    COLUMN_TOTAL_DISCOUNT + " REAL, " +
                    COLUMN_TOTAL_TAX + " REAL,"+
                    COLUMN_GRAND_TOTAL + " REAL," +
                    COLUMN_ORDER_DATE + " LONGTEXT," +
                    COLUMN_IS_ORDER_COMPLETED + " TEXT NOT NULL DEFAULT 'false'"+
                    ");");

            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ORDERED_PRODUCT + "(" +
                    COLUMN_ORDERED_ORDER_ID + " LONGTEXT ," +
                    COLUMN_ORDERED_PRODUCT_ID + " LONGTEXT," +
                    COLUMN_ORDERED_PRODUCT_NAME+ " LONGTEXT,"+
                    COLUMN_ORDERED_PRODUCT_QUANTITY + " INTEGER, " +
                    COLUMN_ORDERED_OFFER_ITEM_ID + " LONGTEXT,"+
                    COLUMN_ORDERED_OFFER_NAME+ " LONGTEXT,"+
                    COLUMN_ORDERED_OFFER_QUANTITY + " INTEGER," +
                    COLUMN_ORDERED_PRODUCT_TOTAL_DISCOUNT + " REAL, " +
                    COLUMN_ORDERED_TOTAL_TAX + " REAL,"+
                    COLUMN_ORDERED_TOTAL_PRICE+ " REAL,"+
                    COLUMN_ORDERED_GRAND_TOTAL + " REAL"+
                    ");");



//            //WORKING ON
//            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ONGOING_DISCOUNT + "(" +
//                    COLUMN_ONGOING_DISCOUNT_ORDER_ID + " LONGTEXT," +
//                    COLUMN_ONGOING_DISCOUNT_PRODUCT_ID + " LONGTEXT,"+
//                    COLUMN_ONGOING_DISCOUNT_ID + " LONGTEXT"+
//                    ");");
//
//            //WORKING ON
//            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ONGOING_TAX + "(" +
//                    COLUMN_ONGOING_TAX_ORDERED_ID + " LONGTEXT," +
//                    COLUMN_ONGOING_TAX_PRODUCT_ID + " LONGTEXT," +
//                    COLUMN_ONGOING_TAX_ID + " LONGTEXT" +
//                    ");");

            //WORKING ON
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ONGOING_PAYMENT + "(" +
                    COLUMN_ONGOING_PAYMENT_ID + " LONGTEXT," +
                  /*  COLUMN_ONGOING_PAYMENT_AMOUNT  + " LONGTEXT ," +
                    COLUMN_ONGOING_PAYMENT_TYPE + " LONGTEXT" +*/
                    COLUMN_ONGOING_PAYMENT_ORDERED_ID + " LONGTEXT ," +
                    COLUMN_ONGOING_PAYMENT_TOTAL_PAY + " LONGTEXT" +
                    ");");


        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("_database_", "onCreate error --->> Table creation error");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PAYMENT_METHOD);
        onCreate(db);
    }


    public void insertFeatures(String userId, String featureName, String featureDrawableName,
                               boolean isInHomePage, int positionInHomepage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        try {
            contentValues.put(COLUMN_USER_ID_hTable, userId);
            contentValues.put(COLUMN_FEATURE_NAME, featureName);
            contentValues.put(COLUMN_FEATURE_DRAWABLE, featureDrawableName);
            contentValues.put(COLUMN_IS_IN_HOMEPAGE, String.valueOf(isInHomePage));
            contentValues.put(COLUMN_HOMEPAGE_POSITION, positionInHomepage);

            db.insert(TABLE_HOMEPAGE_SETTINGS, null, contentValues);
            Log.d("_sql_", "insert successfully!");
        } catch (SQLiteException exception) {
            Log.d("_sql_", "insert failed!");
            exception.printStackTrace();
        }


    }

    @SuppressLint("Range")
    public List<Features> getFeatures(String userId) {
        List<Features> allFeatures = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HOMEPAGE_SETTINGS + " WHERE " + COLUMN_USER_ID_hTable +
                " = '" + userId + "'", null);

        cursor.moveToFirst();

        int n = 0;
        while (!cursor.isAfterLast()) {
            Features feature = new Features();

            feature.setUserId(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID_hTable)));
            feature.setFeatureName(cursor.getString(cursor.getColumnIndex(COLUMN_FEATURE_NAME)));
            feature.setDrawableFileName(cursor.getString(cursor.getColumnIndex(COLUMN_FEATURE_DRAWABLE)));
            feature.setIsInHomepage(Boolean.parseBoolean(cursor.getString(cursor.
                    getColumnIndex(COLUMN_IS_IN_HOMEPAGE))));
            feature.setFeaturePosition(cursor.getInt(cursor.getColumnIndex(COLUMN_HOMEPAGE_POSITION)));

            allFeatures.add(feature);
            cursor.moveToNext();
            Log.d("_getsql_", "" + allFeatures.size());

            n++;
            if (n >= 13) {
                cursor.close();
                break;
            }


        }

        return allFeatures;
    }

    public boolean updateFeatureContent(String userId, String featureName, boolean shouldBeInHomepage, int position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {
            contentValues.put(COLUMN_IS_IN_HOMEPAGE, String.valueOf(shouldBeInHomepage));
            contentValues.put(COLUMN_HOMEPAGE_POSITION, position);

            db.update(TABLE_HOMEPAGE_SETTINGS, contentValues,
                    COLUMN_USER_ID_hTable + " = ? AND " + COLUMN_FEATURE_NAME + " = ?",
                    new String[]{userId, featureName});

        } catch (SQLiteException exception) {
            exception.getStackTrace();
            return false;
        }


        return true;
    }


    public int featureSize(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_HOMEPAGE_SETTINGS + " WHERE " + COLUMN_USER_ID_hTable + " = '" + userId + "'", null);

        cursor.moveToFirst();
        int iCount = cursor.getInt(0);

        if (iCount > 0) {
            return iCount;
        }

        return 0;
    }

    public long paymentInsert(PaymentTypeModel paymentTypeModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ORDER_ID_CURRENT,paymentTypeModel.getOrderId());
        contentValues.put(COLUMN_CARD,paymentTypeModel.getCard());
        contentValues.put(COLUMN_CASH,paymentTypeModel.getCash());
        contentValues.put(COLUMN_MFS_ID,paymentTypeModel.getMfsId());
        contentValues.put(COLUMN_MFS_AMOUNT,paymentTypeModel.getType_amount());
        contentValues.put(COLUMN_TYPE_MFS,paymentTypeModel.getType_payment());
        contentValues.put(COLUMN_TOTAL_AMOUNT,paymentTypeModel.getTotal_amount());
        contentValues.put(COLUMN_CLIENT_NAME,paymentTypeModel.getClientName());
        contentValues.put(COLUMN_CLIENT_ID,paymentTypeModel.getClientId());
        contentValues.put(COLUMN_DUE,paymentTypeModel.getDue());

        long rowId = db.insert(TABLE_PAYMENT_METHOD, null, contentValues);
        return rowId;

    }


    public long paymentTypeInsert(MobilePaymentModel mobilePaymentModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TYPE_PAYMENT_ID,mobilePaymentModel.getId());
        contentValues.put(COLUMN_TYPE_PAYMENT,mobilePaymentModel.getPaymentType());

        long rowId = db.insert(TABLE_PAYMENT_TYPE, null, contentValues);
        return rowId;

    }


    public Integer deleteOrderData(String orderId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_ORDER,COLUMN_ORDER_ID+"=?", new String[]{orderId});
    }

    public Integer deletePaymentData(String orderId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_PAYMENT_METHOD,COLUMN_ORDER_ID_CURRENT+"=?", new String[]{orderId});
    }

    public Integer deleteOrderProductData(String orderId)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return  sqLiteDatabase.delete(TABLE_ORDERED_PRODUCT,COLUMN_ORDERED_ORDER_ID+"=?", new String[]{orderId});
    }

    /*public boolean deleteAOrderData(String orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER,COLUMN_ORDER_ID+"=?", new String[]{orderId});
        db.close();
        //COLUMN_ORDERED_PRODUCT_ID+" ?", new String[]{productId})
        return true;

    }*/


    // insert employee data
    public boolean insertEmployeeData(String orgId, String userId, String password, String userName, String userAddress, String userPhone,
                                      String userCategory, String userReportingId, String reportingName, String distributorId, String distributorName, String distributorAddress,
                                      String reportingMobile, String territoryName, String areaName, String regionName, String imageName, String distributorMobile) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ORGANIZATION_ID, orgId);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_USER_ID, userId);
        contentValues.put(COLUMN_USER_NAME, userName);
        contentValues.put(COLUMN_USER_ADDRESS, userAddress);
        contentValues.put(COLUMN_USER_PHONE, userPhone);
        contentValues.put(COLUMN_USER_CATEGORY, userCategory);
        contentValues.put(COLUMN_USER_REPORTING_ID, userReportingId);
        contentValues.put(COLUMN_USER_REPORTING_NAME, reportingName);
        contentValues.put(COLUMN_USER_DISTRIBUTOR_ID, distributorId);
        contentValues.put(COLUMN_USER_DISTRIBUTOR_NAME, distributorName);
        contentValues.put(COLUMN_USER_DISTRIBUTOR_ADDRESS, distributorAddress);
        contentValues.put(COLUMN_USER_REPORTING_MOBILE, reportingMobile);
        contentValues.put(COLUMN_USER_TERRITORY_NAME, territoryName);
        contentValues.put(COLUMN_USER_AREA_NAME, areaName);
        contentValues.put(COLUMN_USER_REGION_NAME, regionName);
        contentValues.put(COLUMN_USER_IMAGE, imageName);
        contentValues.put(COLUMN_USER_DISTRIBUTOR_MOBILE, distributorMobile);


        Log.d("workforce_employee", "insertEmployeeData: " + contentValues);
        db.insert(TABLE_USER, null, contentValues);
        return true;
    }

    // get employee  data

    @SuppressLint("Range")
    public List<User> getEmployeeInfo() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_USER, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            User user = new User();

            user.setOrgId(res.getString(res.getColumnIndex(COLUMN_ORGANIZATION_ID)));
            user.setPassword(res.getString(res.getColumnIndex(COLUMN_PASSWORD)));
            user.setUserId(res.getString(res.getColumnIndex(COLUMN_USER_ID)));
            user.setUserName(res.getString(res.getColumnIndex(COLUMN_USER_NAME)));
            user.setUserAddress(res.getString(res.getColumnIndex(COLUMN_USER_ADDRESS)));
            user.setUserPhone(res.getString(res.getColumnIndex(COLUMN_USER_PHONE)));
            user.setUserCategory(res.getString(res.getColumnIndex(COLUMN_USER_CATEGORY)));
            user.setUserReportingId(res.getString(res.getColumnIndex(COLUMN_USER_REPORTING_ID)));
            user.setReportingName(res.getString(res.getColumnIndex(COLUMN_USER_REPORTING_NAME)));
            user.setDistributorId(res.getString(res.getColumnIndex(COLUMN_USER_DISTRIBUTOR_ID)));
            user.setDistributorName(res.getString(res.getColumnIndex(COLUMN_USER_DISTRIBUTOR_NAME)));
            user.setDistributorAddress(res.getString(res.getColumnIndex(COLUMN_USER_DISTRIBUTOR_ADDRESS)));
            user.setReportingMobile(res.getString(res.getColumnIndex(COLUMN_USER_REPORTING_MOBILE)));
            user.setAreaName(res.getString(res.getColumnIndex(COLUMN_USER_AREA_NAME)));
            user.setTerritoryName(res.getString(res.getColumnIndex(COLUMN_USER_TERRITORY_NAME)));
            user.setRegionName(res.getString(res.getColumnIndex(COLUMN_USER_REGION_NAME)));
            user.setImageName(res.getString(res.getColumnIndex(COLUMN_USER_IMAGE)));
            user.setDistributorMobile(res.getString(res.getColumnIndex(COLUMN_USER_DISTRIBUTOR_MOBILE)));


            userList.add(user);

            res.moveToNext();
        }

        return userList;
    }

    // delete all employee data
    public boolean deleteAllEmployeeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_USER);
        return true;

    }



    public boolean isUserExistInLocalDatabase(String userId, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_USER + " WHERE " + COLUMN_USER_ID + " = '" + userId + "' AND " +
                COLUMN_PASSWORD + " = '" + password + "'", null);

        cursor.moveToFirst();
        int iCount = cursor.getInt(0);

        if (iCount > 0) {
            return true;
        }

        return false;
    }


    public boolean insertBrands(String name, String origin, String logo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_BRAND_NAME, name);
        contentValues.put(COLUMN_BRAND_ORIGIN, origin);
        contentValues.put(COLUMN_BRAND_LOGO, logo);

        db.insert(TABLE_BRAND, null, contentValues);

        return true;
    }

    @SuppressLint("Range")
    public ArrayList<BrandModel> getBrandData() {

        ArrayList<BrandModel> brandList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_BRAND, null);
        res.moveToFirst();
        while (!res.isAfterLast()) {
            BrandModel brandModel = new BrandModel();
            brandModel.setBrandName(res.getString(res.getColumnIndex(COLUMN_BRAND_NAME)));
            brandModel.setBrandOrigin(res.getString(res.getColumnIndex(COLUMN_BRAND_ORIGIN)));
            brandModel.setBrandLogo(res.getString(res.getColumnIndex(COLUMN_BRAND_LOGO)));


            brandList.add(brandModel);

            res.moveToNext();
        }

        return brandList;
    }

    public void deleteBrandData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BRAND, null, null);
    }

    // insert product data
    public boolean insertProductData(ProductModel productModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbTableConstants.ProductConst.productId, productModel.getProductId());
        contentValues.put(DbTableConstants.ProductConst.productName, productModel.getProductName());
        contentValues.put(DbTableConstants.ProductConst.productImage, ApiConstants.PRODUCT_IMAGE_BASE_LINK
                +productModel.getProductImage());
        contentValues.put(DbTableConstants.ProductConst.brand, productModel.getBrand());
        contentValues.put(DbTableConstants.ProductConst.subcategoryName, productModel.getSubcategoryName());
        contentValues.put(DbTableConstants.ProductConst.sku, productModel.getSku());
        contentValues.put(DbTableConstants.ProductConst.barcode, productModel.getBarcode());
        contentValues.put(DbTableConstants.ProductConst.supplier, productModel.getSupplier());
        contentValues.put(DbTableConstants.ProductConst.productLabel, productModel.getProductLabel());
        contentValues.put(DbTableConstants.ProductConst.category, productModel.getCategory());
        contentValues.put(DbTableConstants.ProductConst.categoryName, productModel.getCategoryName());
        contentValues.put(DbTableConstants.ProductConst.desc, productModel.getDesc());
        contentValues.put(DbTableConstants.ProductConst.measuringType, productModel.getMeasuringType());
        contentValues.put(DbTableConstants.ProductConst.startStock, productModel.getStartStock());
        contentValues.put(DbTableConstants.ProductConst.safetyStock, productModel.getSafetyStock());
        contentValues.put(DbTableConstants.ProductConst.onHand, productModel.getOnHand());
        contentValues.put(DbTableConstants.ProductConst.price, productModel.getPrice());
        contentValues.put(DbTableConstants.ProductConst.mrp, productModel.getMrp());
        contentValues.put(DbTableConstants.ProductConst.discountId, productModel.getDiscountId());
        contentValues.put(DbTableConstants.ProductConst.discount, productModel.getDiscount());
        contentValues.put(DbTableConstants.ProductConst.discountType, productModel.getDiscountType());
        contentValues.put(DbTableConstants.ProductConst.availableDiscount, productModel.getAvailableDiscount());
        contentValues.put(DbTableConstants.ProductConst.offerItemId, productModel.getOfferItemId());
        contentValues.put(DbTableConstants.ProductConst.availableOffer, productModel.getAvailableOffer());
        contentValues.put(DbTableConstants.ProductConst.freeItemName, productModel.getFreeItemName());
        contentValues.put(DbTableConstants.ProductConst.requiredQuantity, productModel.getRequiredQuantity());
        contentValues.put(DbTableConstants.ProductConst.freeQuantity, productModel.getFreeQuantity());
        contentValues.put(DbTableConstants.ProductConst.taxId, productModel.getTaxId());
        contentValues.put(DbTableConstants.ProductConst.taxName, productModel.getTaxName());
        contentValues.put(DbTableConstants.ProductConst.isExcludedTax, productModel.getIsExcludedTax());
        contentValues.put(DbTableConstants.ProductConst.tax, productModel.getTax());
        contentValues.put(DbTableConstants.ProductConst.storeName, productModel.getStoreName());
        contentValues.put(DbTableConstants.ProductConst.createdBy, productModel.getCreatedBy());
        contentValues.put(DbTableConstants.ProductConst.createdAt, productModel.getCreatedAt());
        contentValues.put(DbTableConstants.ProductConst.updatedBy, productModel.getUpdatedBy());
        contentValues.put(DbTableConstants.ProductConst.updatedAt, productModel.getUpdatedAt());
        contentValues.put(DbTableConstants.ProductConst.purchaseDate, productModel.getPurchaseDate());
        contentValues.put(DbTableConstants.ProductConst.color, productModel.getColor());
        contentValues.put(DbTableConstants.ProductConst.size, productModel.getSize());
        contentValues.put(DbTableConstants.ProductConst.subscriberId, productModel.getSubscriberId());



        db.insert(DbTableConstants.ProductConst.TABLE_PRODUCT, null, contentValues);
        return true;
    }




    public boolean insertClientData(ClientListModel clientListModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbTableConstants.ClientConst.ID, clientListModel.getId());
        contentValues.put(DbTableConstants.ClientConst.mobile, clientListModel.getMobile());
        contentValues.put(DbTableConstants.ClientConst.name, clientListModel.getName());
        contentValues.put(DbTableConstants.ClientConst.type , clientListModel.getType());
        contentValues.put(DbTableConstants.ClientConst.address, clientListModel.getAddress());
        contentValues.put(DbTableConstants.ClientConst.email, String.valueOf(clientListModel.getEmail()));
        contentValues.put(DbTableConstants.ClientConst.note, clientListModel.getId());
        contentValues.put(DbTableConstants.ClientConst.storeId, clientListModel.getStoreId());
        contentValues.put(DbTableConstants.ClientConst.image, clientListModel.getImage());
        contentValues.put(DbTableConstants.ClientConst.createdAt, clientListModel.getCreatedAt());
        contentValues.put(DbTableConstants.ClientConst.createdBy, clientListModel.getCreatedBy());
        contentValues.put(DbTableConstants.ClientConst.updated, clientListModel.getUpdatedAt());
        contentValues.put(DbTableConstants.ClientConst.ByUpdated, String.valueOf(clientListModel.getUpdatedBy()));
        contentValues.put(DbTableConstants.ClientConst.subscriberId, clientListModel.getSubscriberId());




        db.insert(DbTableConstants.ClientConst.TABLE_CLIENT, null, contentValues);
        return true;
    }

    public boolean insertPaymentTypeData(MobilePaymentModel mobilePaymentModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbTableConstants.PaymentTypeConst.payment_id, mobilePaymentModel.getId());
        contentValues.put(DbTableConstants.PaymentTypeConst.payment_type, mobilePaymentModel.getPaymentType());
        db.insert(DbTableConstants.PaymentTypeConst.TABLE_PAYMENT_TYPE, null, contentValues);
        return true;
    }




    public boolean insertPaymentData(PaymentNewModel paymentNewModel) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ONGOING_PAYMENT_ID, paymentNewModel.getId());
       /* contentValues.put(COLUMN_ONGOING_PAYMENT_AMOUNT, paymentNewModel.getAmount());
        contentValues.put(COLUMN_ONGOING_PAYMENT_TYPE, paymentNewModel.getType());*/

        db.insert(TABLE_ONGOING_PAYMENT, null, contentValues);
        return true;
    }





    @SuppressLint("Range")
    public ArrayList<ProductModel> getProductData() {
        ArrayList<ProductModel> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.ProductConst.TABLE_PRODUCT
                + " ORDER BY " + DbTableConstants.ProductConst.categoryName, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            ProductModel productModel = new ProductModel();

            productModel.setProductId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productId)));
            productModel.setProductName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productName)));
            productModel.setSku(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.sku)));
            productModel.setProductLabel(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productLabel)));
            productModel.setCategory(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.category)));
            productModel.setCategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.categoryName)));
            productModel.setDesc(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.desc)));
            productModel.setMeasuringType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.measuringType)));
            productModel.setStartStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.startStock)));
            productModel.setSafetyStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.safetyStock)));
            productModel.setPrice(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.price)));
            productModel.setMrp(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.mrp)));
            productModel.setDiscountId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountId)));
            productModel.setDiscount(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.discount)));
            productModel.setDiscountType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountType)));
            productModel.setAvailableDiscount(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableDiscount)));
            productModel.setAvailableOffer(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableOffer)));
            productModel.setOfferItemId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.offerItemId)));
            productModel.setFreeItemName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.freeItemName)));
            productModel.setFreeQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.freeQuantity)));
            productModel.setRequiredQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.requiredQuantity)));
            productModel.setOnHand(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.onHand)));
            productModel.setBrand(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.brand)));
            productModel.setProductImage(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productImage)));
            productModel.setTaxId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxId)));
            productModel.setTaxName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxName)));
            productModel.setTax(res.getFloat(res.getColumnIndex(DbTableConstants.ProductConst.tax)));
            productModel.setIsExcludedTax(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.isExcludedTax)));
            productModel.setSubcategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.subcategoryName)));
            productModel.setBarcode(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.barcode)));
            productModel.setSupplier(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.supplier)));
            productModel.setStoreName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.storeName)));
            productModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdBy)));
            productModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdAt)));
            productModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedBy)));
            productModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedAt)));
            productModel.setPurchaseDate(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.purchaseDate)));
            productModel.setColor(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.color)));
            productModel.setSize(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.size)));
            productModel.setSubscriberId(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.subscriberId)));


            productList.add(productModel);
            res.moveToNext();
        }

        return productList;
    }

    @SuppressLint("Range")
    public ArrayList<ClientListModel> getClientData() {
        ArrayList<ClientListModel> clientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.ClientConst.TABLE_CLIENT
                + " ORDER BY " + DbTableConstants.ClientConst.type, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            ClientListModel clientListModel = new ClientListModel();

            clientListModel.setId(res.getInt(res.getColumnIndex(DbTableConstants.ClientConst.ID)));
            clientListModel.setMobile(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.mobile)));
            clientListModel.setName(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.name)));
            clientListModel.setType(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.type)));
            clientListModel.setEmail(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.email)));
            clientListModel.setAddress(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.address)));
            clientListModel.setNote(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.note)));
            clientListModel.setStoreId(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.storeId)));
            clientListModel.setImage(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.image)));
            clientListModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.createdAt)));
            clientListModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.createdBy)));
            clientListModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.updated)));
            clientListModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.ByUpdated)));
            clientListModel.setSubscriberId(res.getString(res.getColumnIndex(DbTableConstants.ClientConst.subscriberId)));


            clientList.add(clientListModel);
            res.moveToNext();
        }

        return clientList;
    }




    @SuppressLint("Range")
    public ArrayList<MobilePaymentModel> getPaymentTypeData() {
        ArrayList<MobilePaymentModel> mobilePaymentModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.PaymentTypeConst.TABLE_PAYMENT_TYPE
                + " ORDER BY " + DbTableConstants.PaymentTypeConst.payment_type, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            MobilePaymentModel mobilePaymentModel = new MobilePaymentModel();

            mobilePaymentModel.setId(Long.valueOf(res.getInt(res.getColumnIndex(DbTableConstants.PaymentTypeConst.payment_id))));
            mobilePaymentModel.setPaymentType(res.getString(res.getColumnIndex(DbTableConstants.PaymentTypeConst.payment_type)));


            mobilePaymentModels.add(mobilePaymentModel);
            res.moveToNext();
        }

        return mobilePaymentModels;
    }

    public Cursor showPaymentTypeData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DbTableConstants.PaymentTypeConst.TABLE_PAYMENT_TYPE
                + " ORDER BY " + DbTableConstants.PaymentTypeConst.payment_type, null);
        return cursor;
    }



    @SuppressLint("Range")
    public Cursor getClientShowData() {
        //ArrayList<ClientListModel> clientList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.ClientConst.TABLE_CLIENT,
                null);

        return res;
    }









    @SuppressLint("Range")
    public ProductModel getSpecificProductData(String productId) {

        ProductModel productModel = new ProductModel();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + DbTableConstants.ProductConst.TABLE_PRODUCT + " WHERE " +
                DbTableConstants.ProductConst.productId + " =?", new String[]{productId});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            productModel.setProductId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productId)));
            productModel.setProductName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productName)));
            productModel.setSku(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.sku)));
            productModel.setProductLabel(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productLabel)));
            productModel.setCategory(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.category)));
            productModel.setCategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.categoryName)));
            productModel.setDesc(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.desc)));
            productModel.setMeasuringType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.measuringType)));
            productModel.setStartStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.startStock)));
            productModel.setSafetyStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.safetyStock)));
            productModel.setPrice(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.price)));
            productModel.setMrp(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.mrp)));
            productModel.setDiscountId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountId)));
            productModel.setDiscount(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.discount)));
            productModel.setDiscountType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountType)));
            productModel.setAvailableDiscount(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableDiscount)));
            productModel.setAvailableOffer(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableOffer)));
            productModel.setOfferItemId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.offerItemId)));
            productModel.setFreeItemName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.freeItemName)));
            productModel.setFreeQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.freeQuantity)));
            productModel.setRequiredQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.requiredQuantity)));
            productModel.setOnHand(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.onHand)));
            productModel.setBrand(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.brand)));
            productModel.setProductImage(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productImage)));
            productModel.setTaxId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxId)));
            productModel.setTaxName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxName)));
            productModel.setTax(res.getFloat(res.getColumnIndex(DbTableConstants.ProductConst.tax)));
            productModel.setIsExcludedTax(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.isExcludedTax)));
            productModel.setSubcategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.subcategoryName)));
            productModel.setBarcode(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.barcode)));
            productModel.setSupplier(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.supplier)));
            productModel.setStoreName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.storeName)));
            productModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdBy)));
            productModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdAt)));
            productModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedBy)));
            productModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedAt)));
            productModel.setPurchaseDate(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.purchaseDate)));
            productModel.setColor(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.color)));
            productModel.setSize(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.size)));
            productModel.setSubscriberId(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.subscriberId)));


            res.moveToNext();
        }
        return productModel;
    }

    @SuppressLint("Range")
    public ProductModel getSpecificProductDataByBarcode(String barcode){

        ProductModel productModel = null;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + DbTableConstants.ProductConst.TABLE_PRODUCT + " WHERE " +
                DbTableConstants.ProductConst.barcode + " =?", new String[]{barcode});

        res.moveToFirst();

        while (!res.isAfterLast()) {

            productModel = new ProductModel();
            productModel.setProductId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productId)));
            productModel.setProductName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productName)));
            productModel.setSku(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.sku)));
            productModel.setProductLabel(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productLabel)));
            productModel.setCategory(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.category)));
            productModel.setCategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.categoryName)));
            productModel.setDesc(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.desc)));
            productModel.setMeasuringType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.measuringType)));
            productModel.setStartStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.startStock)));
            productModel.setSafetyStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.safetyStock)));
            productModel.setPrice(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.price)));
            productModel.setMrp(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.mrp)));
            productModel.setDiscountId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountId)));
            productModel.setDiscount(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.discount)));
            productModel.setDiscountType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountType)));
            productModel.setAvailableDiscount(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableDiscount)));
            productModel.setAvailableOffer(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableOffer)));
            productModel.setOfferItemId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.offerItemId)));
            productModel.setFreeItemName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.freeItemName)));
            productModel.setFreeQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.freeQuantity)));
            productModel.setRequiredQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.requiredQuantity)));
            productModel.setOnHand(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.onHand)));
            productModel.setBrand(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.brand)));
            productModel.setProductImage(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productImage)));
            productModel.setTaxId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxId)));
            productModel.setTaxName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxName)));
            productModel.setTax(res.getFloat(res.getColumnIndex(DbTableConstants.ProductConst.tax)));
            productModel.setIsExcludedTax(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.isExcludedTax)));
            productModel.setSubcategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.subcategoryName)));
            productModel.setBarcode(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.barcode)));
            productModel.setSupplier(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.supplier)));
            productModel.setStoreName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.storeName)));
            productModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdBy)));
            productModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdAt)));
            productModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedBy)));
            productModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedAt)));
            productModel.setPurchaseDate(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.purchaseDate)));
            productModel.setColor(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.color)));
            productModel.setSize(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.size)));
            productModel.setSubscriberId(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.subscriberId)));

            res.moveToNext();

        }
        return productModel;
    }

    @SuppressLint("Range")
    public ArrayList<ProductModel> getProductDataByBrandName(String brandName) {

        ArrayList<ProductModel> productList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.ProductConst.TABLE_PRODUCT
                + " WHERE " + DbTableConstants.ProductConst.brand + "= '" + brandName + "' ORDER BY " +
                DbTableConstants.ProductConst.categoryName, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            ProductModel productModel = new ProductModel();

            productModel.setProductId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productId)));
            productModel.setProductName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productName)));
            productModel.setSku(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.sku)));
            productModel.setProductLabel(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productLabel)));
            productModel.setCategory(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.category)));
            productModel.setCategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.categoryName)));
            productModel.setDesc(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.desc)));
            productModel.setMeasuringType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.measuringType)));
            productModel.setStartStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.startStock)));
            productModel.setSafetyStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.safetyStock)));
            productModel.setPrice(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.price)));
            productModel.setMrp(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.mrp)));
            productModel.setDiscountId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountId)));
            productModel.setDiscount(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.discount)));
            productModel.setDiscountType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountType)));
            productModel.setAvailableDiscount(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableDiscount)));
            productModel.setAvailableOffer(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableOffer)));
            productModel.setOfferItemId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.offerItemId)));
            productModel.setFreeItemName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.freeItemName)));
            productModel.setFreeQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.freeQuantity)));
            productModel.setRequiredQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.requiredQuantity)));
            productModel.setOnHand(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.onHand)));
            productModel.setBrand(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.brand)));
            productModel.setProductImage(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productImage)));
            productModel.setTaxId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxId)));
            productModel.setTaxName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxName)));
            productModel.setTax(res.getFloat(res.getColumnIndex(DbTableConstants.ProductConst.tax)));
            productModel.setIsExcludedTax(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.isExcludedTax)));
            productModel.setSubcategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.subcategoryName)));
            productModel.setBarcode(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.barcode)));
            productModel.setSupplier(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.supplier)));
            productModel.setStoreName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.storeName)));
            productModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdBy)));
            productModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdAt)));
            productModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedBy)));
            productModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedAt)));
            productModel.setPurchaseDate(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.purchaseDate)));
            productModel.setColor(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.color)));
            productModel.setSize(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.size)));
            productModel.setSubscriberId(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.subscriberId)));


            productList.add(productModel);

            res.moveToNext();
        }

        return productList;
    }


    @SuppressLint("Range")
    public ArrayList<ProductModel> getProductDataByBrandNameAndCategory(String brandName, String categoryName) {

        ArrayList<ProductModel> productList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.ProductConst.TABLE_PRODUCT + " WHERE "
                + DbTableConstants.ProductConst.brand + "= '" + brandName +
                "' AND " + DbTableConstants.ProductConst.categoryName + "='" + categoryName + "' ORDER BY " +
                DbTableConstants.ProductConst.categoryName, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            ProductModel productModel = new ProductModel();

            productModel.setProductId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productId)));
            productModel.setProductName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productName)));
            productModel.setSku(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.sku)));
            productModel.setProductLabel(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productLabel)));
            productModel.setCategory(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.category)));
            productModel.setCategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.categoryName)));
            productModel.setDesc(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.desc)));
            productModel.setMeasuringType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.measuringType)));
            productModel.setStartStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.startStock)));
            productModel.setSafetyStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.safetyStock)));
            productModel.setPrice(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.price)));
            productModel.setMrp(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.mrp)));
            productModel.setDiscountId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountId)));
            productModel.setDiscount(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.discount)));
            productModel.setDiscountType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountType)));
            productModel.setAvailableDiscount(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableDiscount)));
            productModel.setAvailableOffer(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableOffer)));
            productModel.setOfferItemId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.offerItemId)));
            productModel.setFreeItemName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.freeItemName)));
            productModel.setFreeQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.freeQuantity)));
            productModel.setRequiredQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.requiredQuantity)));
            productModel.setOnHand(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.onHand)));
            productModel.setBrand(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.brand)));
            productModel.setProductImage(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productImage)));
            productModel.setTaxId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxId)));
            productModel.setTaxName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxName)));
            productModel.setTax(res.getFloat(res.getColumnIndex(DbTableConstants.ProductConst.tax)));
            productModel.setIsExcludedTax(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.isExcludedTax)));
            productModel.setSubcategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.subcategoryName)));
            productModel.setBarcode(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.barcode)));
            productModel.setSupplier(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.supplier)));
            productModel.setStoreName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.storeName)));
            productModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdBy)));
            productModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdAt)));
            productModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedBy)));
            productModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedAt)));
            productModel.setPurchaseDate(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.purchaseDate)));
            productModel.setColor(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.color)));
            productModel.setSize(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.size)));
            productModel.setSubscriberId(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.subscriberId)));

            productList.add(productModel);

            res.moveToNext();
        }

        return productList;
    }


    @SuppressLint("Range")
    public ArrayList<ProductModel> getProductDataByCategory(String categoryName) {

        ArrayList<ProductModel> productList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + DbTableConstants.ProductConst.TABLE_PRODUCT
                + " WHERE " + DbTableConstants.ProductConst.categoryName
                + "='" + categoryName + "' ORDER BY '" + DbTableConstants.ProductConst.categoryName+"'", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            ProductModel productModel = new ProductModel();

            productModel.setProductId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productId)));
            productModel.setProductName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productName)));
            productModel.setSku(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.sku)));
            productModel.setProductLabel(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productLabel)));
            productModel.setCategory(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.category)));
            productModel.setCategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.categoryName)));
            productModel.setDesc(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.desc)));
            productModel.setMeasuringType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.measuringType)));
            productModel.setStartStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.startStock)));
            productModel.setSafetyStock(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.safetyStock)));
            productModel.setPrice(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.price)));
            productModel.setMrp(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.mrp)));
            productModel.setDiscountId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountId)));
            productModel.setDiscount(res.getDouble(res.getColumnIndex(DbTableConstants.ProductConst.discount)));
            productModel.setDiscountType(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.discountType)));
            productModel.setAvailableDiscount(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableDiscount)));
            productModel.setAvailableOffer(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.availableOffer)));
            productModel.setOfferItemId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.offerItemId)));
            productModel.setFreeItemName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.freeItemName)));
            productModel.setFreeQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.freeQuantity)));
            productModel.setRequiredQuantity(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.requiredQuantity)));
            productModel.setOnHand(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.onHand)));
            productModel.setBrand(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.brand)));
            productModel.setProductImage(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.productImage)));
            productModel.setTaxId(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxId)));
            productModel.setTaxName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.taxName)));
            productModel.setTax(res.getFloat(res.getColumnIndex(DbTableConstants.ProductConst.tax)));
            productModel.setIsExcludedTax(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.isExcludedTax)));
            productModel.setSubcategoryName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.subcategoryName)));
            productModel.setBarcode(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.barcode)));
            productModel.setSupplier(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.supplier)));
            productModel.setStoreName(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.storeName)));
            productModel.setCreatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdBy)));
            productModel.setCreatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.createdAt)));
            productModel.setUpdatedBy(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedBy)));
            productModel.setUpdatedAt(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.updatedAt)));
            productModel.setPurchaseDate(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.purchaseDate)));
            productModel.setColor(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.color)));
            productModel.setSize(res.getString(res.getColumnIndex(DbTableConstants.ProductConst.size)));
            productModel.setSubscriberId(res.getInt(res.getColumnIndex(DbTableConstants.ProductConst.subscriberId)));

            productList.add(productModel);

            res.moveToNext();
        }

        return productList;
    }

    public void updateProductQuantity(String productId, int currentQuantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbTableConstants.ProductConst.onHand, currentQuantity);

        db.update(DbTableConstants.ProductConst.TABLE_PRODUCT,contentValues,
                DbTableConstants.ProductConst.productId+" = ?", new String[]{productId});
    }

    public List<String> categoriesList() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT DISTINCT " + DbTableConstants.ProductConst.categoryName + " FROM " + DbTableConstants.ProductConst.TABLE_PRODUCT, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            categories.add(res.getString(0));
            res.moveToNext();
        }
        return categories;
    }

    public List<String> productNameList() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT DISTINCT " + DbTableConstants.ProductConst.productName + " FROM " + DbTableConstants.ProductConst.TABLE_PRODUCT, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            categories.add(res.getString(0));
            res.moveToNext();
        }
        return categories;
    }


    public boolean deleteAllProductData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DbTableConstants.ProductConst.TABLE_PRODUCT);
        return true;
    }

    public boolean deleteClientData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DbTableConstants.ClientConst.TABLE_CLIENT);
        return true;
    }

    public boolean deletePaymentTypeData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DbTableConstants.PaymentTypeConst.TABLE_PAYMENT_TYPE);
        return true;
    }


    public void insertDiscountData(DiscountDetails discount){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_DISCOUNT + " WHERE "
                + COLUMN_DISCOUNT_ID + " = '" + discount.getId() +"' AND "+ COLUMN_DISCOUNT_ORDER_ID
                + "='" + discount.getOrderId() + "' AND " + COLUMN_DISCOUNT_PRODUCT_ID + "='" +
                discount.getProductId() +"'", null);

        cursor.moveToFirst();
        int iCount = cursor.getInt(0);
        if (iCount > 0) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DISCOUNT_ID, discount.getId());
        contentValues.put(COLUMN_DISCOUNT_NAME, discount.getDiscountName());
        contentValues.put(COLUMN_DISCOUNT_TYPE, discount.getDiscountType());
        contentValues.put(COLUMN_DISCOUNT_AMOUNT, discount.getDiscount());
        contentValues.put(COLUMN_DISCOUNT_ORDER_ID, discount.getOrderId());
        contentValues.put(COLUMN_DISCOUNT_PRODUCT_ID, discount.getProductId());
        contentValues.put(COLUMN_IS_DISCOUNT_AVAILABLE, String.valueOf(discount.isDiscountApplied()));

        db.insert(TABLE_DISCOUNT, null, contentValues);
    }

//    public boolean isDiscountExistForThisOrderedProduct(String orderId, String productId, String discountId){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_ONGOING_DISCOUNT + " WHERE "
//                + COLUMN_ONGOING_DISCOUNT_ORDER_ID + " = '" + orderId + COLUMN_ONGOING_DISCOUNT_PRODUCT_ID + " = '" + productId
//                +COLUMN_ONGOING_DISCOUNT_ID + " = '" + discountId +"'", null);
//
//        cursor.moveToFirst();
//        int iCount = cursor.getInt(0);
//
//        if (iCount > 0) {
//            return true;
//        }
//
//        return false;
//    }

    @SuppressLint("Range")
    public List<OrderJsonModel> getAllSavedOrderData(){
        List<OrderJsonModel> orderJsonModelList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor orderCursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_IS_ORDER_COMPLETED
                + " = '" + false +"'", null);
        orderCursor.moveToFirst();

        while (!orderCursor.isAfterLast()){
            String order_id = orderCursor.getString(orderCursor.getColumnIndex(COLUMN_ORDER_ID));
            int client_id = orderCursor.getInt(orderCursor.getColumnIndex(COLUMN_ORDER_CLIENT_ID));
            String client_name = orderCursor.getString(orderCursor.getColumnIndex(COLUMN_ORDER_CLIENT_NAME));
            double total_price = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_TOTAL_PRICE));
            double total_discount = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_TOTAL_DISCOUNT));
            double total_tax = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_TOTAL_TAX));
            double grand_total = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_GRAND_TOTAL));
            String order_date = orderCursor.getString(orderCursor.getColumnIndex(COLUMN_ORDER_DATE));


            List<OrderedProductModel> productModelList = getAllOrderProduct(order_id);
            OrderJsonModel orderJsonModel = new OrderJsonModel(order_id, client_id, client_name, total_price, total_discount, total_tax,
                    grand_total, order_date, productModelList);

            orderJsonModelList.add(orderJsonModel);
            orderCursor.moveToNext();
        }


        return orderJsonModelList;
    }

    public Cursor showData()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_IS_ORDER_COMPLETED
                + " = '" + true +"'", null);
        return cursor;
    }


    public Cursor showPaymentData(String orderId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PAYMENT_METHOD + " WHERE " + COLUMN_ORDER_ID_CURRENT
                + " = '" + orderId +"'", null);
        return cursor;
    }

    public Cursor showOrderData(String orderId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_ORDER_ID
                + " = '" + orderId +"'", null);
        return cursor;
    }


    public Cursor showDiscountDetailsData(String orderId,String productId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT + " WHERE " + COLUMN_DISCOUNT_ORDER_ID
                + " = '" + orderId +"' AND "+ COLUMN_DISCOUNT_PRODUCT_ID+ " = '" + productId  +"'", null);
        return cursor;
    }

    public Cursor showTaxDetailsData(String orderId,String productId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ORDER_ID
                + " = '" + orderId +"' AND "+ COLUMN_TAX_PRODUCT_ID+ " = '" + productId  +"'", null);
        return cursor;
    }

    public Cursor showProductDetailsData(String orderId)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDERED_PRODUCT + " WHERE " + COLUMN_ORDERED_ORDER_ID
                + " = '" + orderId +"'", null);
        return cursor;
    }





    @SuppressLint("Range")
    public OrderJsonModel getOrderDataWithAllOrderedProducts(String orderId){

       SQLiteDatabase db = this.getWritableDatabase();
       Cursor orderCursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_ORDER_ID
               + " = '" + orderId +"'", null);
       orderCursor.moveToFirst();

       if (!orderCursor.isAfterLast()){
           String order_id = orderCursor.getString(orderCursor.getColumnIndex(COLUMN_ORDER_ID));
           int client_id = orderCursor.getInt(orderCursor.getColumnIndex(COLUMN_ORDER_CLIENT_ID));
           String client_name = orderCursor.getString(orderCursor.getColumnIndex(COLUMN_ORDER_CLIENT_NAME));
           double total_price = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_TOTAL_PRICE));
           double total_discount = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_TOTAL_DISCOUNT));
           double total_tax = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_TOTAL_TAX));
           double grand_total = orderCursor.getDouble(orderCursor.getColumnIndex(COLUMN_GRAND_TOTAL));
           String order_date = orderCursor.getString(orderCursor.getColumnIndex(COLUMN_ORDER_DATE));


           List<OrderedProductModel> productModelList = getAllOrderProduct(orderId);
           OrderJsonModel orderJsonModel = new OrderJsonModel(orderId, client_id, client_name, total_price, total_discount, total_tax,
                   grand_total, order_date, productModelList);

           return orderJsonModel;
       }


       return null;
   }

    @SuppressLint("Range")
    public boolean updateOrderCompleted(OrderJsonModel orderModel, boolean isOrderCompleted){
        SQLiteDatabase db = this.getWritableDatabase();
        if (!TextUtils.isEmpty(orderModel.getOrderId())){
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_IS_ORDER_COMPLETED, String.valueOf(isOrderCompleted));

            db.update(TABLE_ORDER, contentValues,
                    COLUMN_ORDER_ID+" = ?", new String[]{orderModel.getOrderId()});

            return true;
        }
        return false;
    }

    public boolean updateCoreOrderData(OrderJsonModel orderModel){
        SQLiteDatabase db = this.getWritableDatabase();
        if (!TextUtils.isEmpty(orderModel.getOrderId())){
            ContentValues contentValues = new ContentValues();


            contentValues.put(COLUMN_ORDER_CLIENT_ID, orderModel.getClientId());
            contentValues.put(COLUMN_ORDER_CLIENT_NAME, orderModel.getClientName());
            contentValues.put(COLUMN_TOTAL_PRICE, orderModel.getTotal());
            contentValues.put(COLUMN_TOTAL_DISCOUNT, orderModel.getTotalDiscount());
            contentValues.put(COLUMN_TOTAL_TAX, orderModel.getTotalTax());
            contentValues.put(COLUMN_GRAND_TOTAL, orderModel.getGrandTotal());
            contentValues.put(COLUMN_ORDER_DATE, orderModel.getOrderDate());

            db.update(TABLE_ORDER, contentValues,
                    COLUMN_ORDER_ID+" = ?", new String[]{orderModel.getOrderId()});

            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public OrderedProductModel getOrderProduct(String orderId, String productId){
        OrderedProductModel orderedProductModel = null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ORDERED_PRODUCT + " WHERE " + COLUMN_ORDERED_ORDER_ID
                + " = '" + orderId +"' AND "+ COLUMN_ORDERED_PRODUCT_ID+ " = '" + productId  +"'", null);
        res.moveToFirst();

        if (!res.isAfterLast()){
            String order_id = res.getString(res.getColumnIndex(COLUMN_ORDERED_ORDER_ID));
            String product_id = res.getString(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_ID));
            String product_name = res.getString(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_NAME));
            int quantity = res.getInt(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_QUANTITY));
            double totalPrice_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_TOTAL_PRICE));
            double totalDiscount_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_TOTAL_DISCOUNT));
            double totalTax_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_TOTAL_TAX));
            double grandTotalPrice_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_GRAND_TOTAL));
            String offerItemId = res.getString(res.getColumnIndex(COLUMN_ORDERED_OFFER_ITEM_ID));
            String offerName = res.getString(res.getColumnIndex(COLUMN_ORDERED_OFFER_NAME));
            int offerQuantity = res.getInt(res.getColumnIndex(COLUMN_ORDERED_OFFER_QUANTITY));

            List<DiscountDetails> discountDetailsList = getAllOrderedProductDiscount(orderId, product_id);
            List<Tax> taxList = getAllOnGoingTaxForASingleProduct(orderId, product_id);

            orderedProductModel = new OrderedProductModel(product_id, product_name, quantity, offerItemId,
                    offerName, offerQuantity, totalDiscount_str, totalPrice_str, grandTotalPrice_str, totalTax_str,
                    taxList, discountDetailsList);
        }

        return orderedProductModel;
    }

    @SuppressLint("Range")
   public List<OrderedProductModel> getAllOrderProduct(String orderId){
        orderedProductModelList.clear();
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ORDERED_PRODUCT + " WHERE " + COLUMN_ORDERED_ORDER_ID
               + " = '" + orderId +"'", null);
       res.moveToFirst();


       while (!res.isAfterLast()) {
           //get current values
           String order_id = res.getString(res.getColumnIndex(COLUMN_ORDERED_ORDER_ID));
           String product_id = res.getString(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_ID));
           String product_name = res.getString(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_NAME));
           int quantity = res.getInt(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_QUANTITY));
           double totalPrice_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_TOTAL_PRICE));
           double totalDiscount_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_TOTAL_DISCOUNT));
           double totalTax_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_TOTAL_TAX));
           double grandTotalPrice_str = res.getDouble(res.getColumnIndex(COLUMN_ORDERED_GRAND_TOTAL));
           String offerItemId = res.getString(res.getColumnIndex(COLUMN_ORDERED_OFFER_ITEM_ID));
           String offerName = res.getString(res.getColumnIndex(COLUMN_ORDERED_OFFER_NAME));
           int offerQuantity = res.getInt(res.getColumnIndex(COLUMN_ORDERED_OFFER_QUANTITY));

           List<DiscountDetails> discountDetailsList = getAllOrderedProductDiscount(orderId, product_id);
           List<Tax> taxList = getAllOnGoingTaxForASingleProduct(orderId, product_id);

           OrderedProductModel orderedProductModel = new OrderedProductModel(product_id, product_name, quantity, offerItemId,
                   offerName, offerQuantity, totalDiscount_str, totalPrice_str, grandTotalPrice_str, totalTax_str,
                   taxList, discountDetailsList);
           orderedProductModelList.add(orderedProductModel);

           res.moveToNext();
       }

       return orderedProductModelList;

   }

   @SuppressLint("Range")
   public HashMap<String, Integer> getQuantityForAProduct(String orderId, String productId){
       HashMap<String, Integer> quantity = new HashMap<>();

       SQLiteDatabase db = this.getReadableDatabase();
       Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ORDERED_PRODUCT + " WHERE " + COLUMN_ORDERED_ORDER_ID
               + "='" + orderId + "' AND "+COLUMN_ORDERED_PRODUCT_ID+" ='" + productId+"'", null);

       if (res.moveToFirst()){
           quantity.put(Constants.KEY_PRODUCT_QUANTITY, res.getInt(res.getColumnIndex(COLUMN_ORDERED_PRODUCT_QUANTITY)));
           quantity.put(Constants.KEY_OFFER_QUANTITY, res.getInt(res.getColumnIndex(COLUMN_ORDERED_OFFER_QUANTITY)));
           return quantity;
       }else {
           quantity.put(Constants.KEY_PRODUCT_QUANTITY, 0);
           quantity.put(Constants.KEY_OFFER_QUANTITY, 0);
           return quantity;
       }
   }

   public void insertOrder(OrderJsonModel orderJsonModel){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();

       contentValues.put(COLUMN_ORDER_ID, orderJsonModel.getOrderId());
       contentValues.put(COLUMN_ORDER_CLIENT_ID, orderJsonModel.getClientId());
       contentValues.put(COLUMN_ORDER_CLIENT_NAME, orderJsonModel.getClientName());
       contentValues.put(COLUMN_TOTAL_PRICE, orderJsonModel.getTotal());
       contentValues.put(COLUMN_TOTAL_DISCOUNT, orderJsonModel.getTotalDiscount());
       contentValues.put(COLUMN_TOTAL_TAX, orderJsonModel.getTotalTax());
       contentValues.put(COLUMN_GRAND_TOTAL, orderJsonModel.getGrandTotal());
       contentValues.put(COLUMN_ORDER_DATE, orderJsonModel.getOrderDate());

       db.insert(TABLE_ORDER, null, contentValues);
   }

   public boolean isItExistingOrder(String orderId){
       SQLiteDatabase db = this.getReadableDatabase();

       Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_ORDER + " WHERE " + COLUMN_ORDER_ID + " = '" + orderId + "'", null);

       cursor.moveToFirst();
       int iCount = cursor.getInt(0);

       if (iCount > 0) {
           return true;
       }

       return false;
   }

   public boolean insertOrderProduct(String orderId, OrderedProductModel orderedProductModel){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();

       contentValues.put(COLUMN_ORDERED_ORDER_ID, orderId);
       contentValues.put(COLUMN_ORDERED_PRODUCT_ID, orderedProductModel.getProductId());
       contentValues.put(COLUMN_ORDERED_PRODUCT_NAME, orderedProductModel.getProductName());
       contentValues.put(COLUMN_ORDERED_PRODUCT_QUANTITY, orderedProductModel.getQuantity());
       contentValues.put(COLUMN_ORDERED_OFFER_ITEM_ID, orderedProductModel.getOfferItemId());
       contentValues.put(COLUMN_ORDERED_OFFER_QUANTITY, orderedProductModel.getOfferQuantity());
       contentValues.put(COLUMN_ORDERED_OFFER_NAME, orderedProductModel.getOfferName());
       contentValues.put(COLUMN_ORDERED_PRODUCT_TOTAL_DISCOUNT, orderedProductModel.getTotalDiscount());
       contentValues.put(COLUMN_ORDERED_TOTAL_TAX, orderedProductModel.getTotalTax());
       contentValues.put(COLUMN_ORDERED_TOTAL_PRICE, orderedProductModel.getTotalPrice());
       contentValues.put(COLUMN_ORDERED_GRAND_TOTAL, orderedProductModel.getGrandTotal());

       db.insert(TABLE_ORDERED_PRODUCT, null, contentValues);

       return true;
   }

   public boolean updateOrderedProduct(OrderedProductModel productModel, String orderId){
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues contentValues = new ContentValues();


       contentValues.put(COLUMN_ORDERED_PRODUCT_QUANTITY, productModel.getQuantity());
       contentValues.put(COLUMN_ORDERED_TOTAL_PRICE, productModel.getTotalPrice());
       contentValues.put(COLUMN_ORDERED_PRODUCT_TOTAL_DISCOUNT, productModel.getTotalDiscount());
       contentValues.put(COLUMN_ORDERED_TOTAL_TAX, productModel.getTotalTax());
       contentValues.put(COLUMN_ORDERED_GRAND_TOTAL, productModel.getGrandTotal());
       contentValues.put(COLUMN_ORDERED_OFFER_QUANTITY, productModel.getOfferQuantity());

       db.update(TABLE_ORDERED_PRODUCT, contentValues,
               COLUMN_ORDERED_ORDER_ID+" = ? AND "+COLUMN_ORDERED_PRODUCT_ID+" = ?",
               new String[]{orderId, productModel.getProductId()});

       return true;
   }




    public boolean deleteProductData(String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.delete(TABLE_ORDER, COLUMN_ORDER_ID+"=?", new String[]{orderId});
        db.delete(TABLE_ORDERED_PRODUCT,COLUMN_ORDERED_PRODUCT_ID+"=?", new String[]{productId});
        db.close();
        return true;
    }
    //deleteAOrder
    public boolean deleteAOrder(String orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER,COLUMN_ORDER_ID+"=?", new String[]{orderId});
        db.close();
        //COLUMN_ORDERED_PRODUCT_ID+" ?", new String[]{productId})
        return true;

    }

//    public void insertDiscount(DiscountDetails discountDetails){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(COLUMN_DISCOUNT_ID, discountDetails.getDiscountId());
//        contentValues.put(COLUMN_DISCOUNT_NAME, discountDetails.getDiscountName());
//        contentValues.put(COLUMN_DISCOUNT_TYPE, discountDetails.getDiscountType());
//        contentValues.put(COLUMN_DISCOUNT_AMOUNT, discountDetails.getDiscount());
//
//        db.insert(TABLE_DISCOUNT, null, contentValues);
//    }

    @SuppressLint("Range")
    public List<Integer> getAllDiscountIdForAProduct(String orderId, String productId){
        List<Integer> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT+ " WHERE " + COLUMN_DISCOUNT_ORDER_ID
                + " = '" + orderId +"' AND "+ COLUMN_DISCOUNT_PRODUCT_ID+ " = '" + productId +"'", null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            int id = res.getInt(res.getColumnIndex(COLUMN_DISCOUNT_ID));
            list.add(id);
            res.moveToNext();
        }
        return  list;
    }

    @SuppressLint("Range")
    public boolean isDiscountAvailable(String orderId, String productId, int discountId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT+ " WHERE " + COLUMN_DISCOUNT_ORDER_ID
                + " = '" + orderId +"' AND "+ COLUMN_DISCOUNT_PRODUCT_ID+ " = '" + productId
                +"' AND "+ COLUMN_DISCOUNT_ID+ " = " + discountId+" AND "
                + COLUMN_IS_DISCOUNT_AVAILABLE +" = 'true'", null);

        res.moveToFirst();
        if (!res.isAfterLast()){
            boolean b = Boolean.parseBoolean(res.getString(res.getColumnIndex(COLUMN_IS_DISCOUNT_AVAILABLE)));
            return b;
        }

        return false;
    }

    @SuppressLint("Range")
    public List<DiscountDetails> getAllDiscount(String orderId, String productId){
        List<DiscountDetails> discountDetailsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT+ " WHERE " + COLUMN_DISCOUNT_ORDER_ID
                + " = '" + 0 +"' AND "+ COLUMN_DISCOUNT_PRODUCT_ID+ " = '" + 0 +"'", null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            DiscountDetails discountDetails = new DiscountDetails();
            int discountId = res.getInt(res.getColumnIndex(COLUMN_DISCOUNT_ID));
            discountDetails.setId(discountId);
            discountDetails.setDiscountName(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_NAME)));
            discountDetails.setDiscountType(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_TYPE)));
            discountDetails.setDiscount(res.getDouble(res.getColumnIndex(COLUMN_DISCOUNT_AMOUNT)));

            discountDetails.setDiscountApplied(isDiscountAvailable(orderId, productId, discountId));


            discountDetailsList.add(discountDetails);
            res.moveToNext();
        }

        return discountDetailsList;
    }

    @SuppressLint("Range")
    private DiscountDetails getSingleDiscount(String discountId){
        DiscountDetails discountDetails = new DiscountDetails();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = null;

        try {
            res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT + " WHERE " + COLUMN_DISCOUNT_ID
                    + "='" + discountId + "' AND "+ COLUMN_DISCOUNT_ORDER_ID
                    + " = '" + 0 +"' AND "+ COLUMN_DISCOUNT_PRODUCT_ID+ " = '" + 0  +"'", null);
            res.moveToFirst();

            if (!res.isAfterLast()) {
                discountDetails.setId(res.getInt(res.getColumnIndex(COLUMN_DISCOUNT_ID)));
                discountDetails.setDiscountName(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_NAME)));
                discountDetails.setDiscountType(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_TYPE)));
                discountDetails.setDiscount(res.getDouble(res.getColumnIndex(COLUMN_DISCOUNT_AMOUNT)));
            }
        }finally {
            if (res != null){
                res.close();
            }
        }




        return discountDetails;
    }

    public boolean updateDiscountAvailability(DiscountDetails discountDetails){
        SQLiteDatabase db = this.getWritableDatabase();
        if (discountDetails != null && !TextUtils.isEmpty(discountDetails.getOrderId())
                && !TextUtils.isEmpty(discountDetails.getProductId()) && Long.parseLong(discountDetails.getOrderId()) > 0){

            Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT + " WHERE " + COLUMN_DISCOUNT_ID
                    + "='" + discountDetails.getId() + "' AND "+ COLUMN_DISCOUNT_ORDER_ID
                    + " = '" + discountDetails.getOrderId()+"' AND "+ COLUMN_DISCOUNT_PRODUCT_ID+ " = '" + discountDetails.getProductId() +"'", null);
            res.moveToFirst();

            if (!res.isAfterLast()){
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_IS_DISCOUNT_AVAILABLE, discountDetails.isDiscountApplied());

                db.update(TABLE_DISCOUNT, contentValues,
                        COLUMN_DISCOUNT_ORDER_ID+" = ? AND "+
                                COLUMN_DISCOUNT_PRODUCT_ID+" = ? AND "+COLUMN_DISCOUNT_ID+" = ? " ,
                        new String[]{discountDetails.getOrderId(), discountDetails.getProductId(),
                                String.valueOf(discountDetails.getId())});
            }else {
                insertDiscountData(discountDetails);
            }

            return true;
        }
        return false;
    }


    public boolean deleteDiscount(String orderId, String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_DISCOUNT+ " WHERE " + COLUMN_DISCOUNT_ORDER_ID + "= '" + orderId + "' AND "
                + COLUMN_DISCOUNT_PRODUCT_ID + "= '" + productId + "'");
        return true;
    }

    public boolean deleteAllDiscount(String orderId, String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_DISCOUNT);
        return true;
    }

//    public void insertOnGoingOrderDiscount(DiscountDetails discount){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT + " WHERE " + COLUMN_DISCOUNT_ORDER_ID
//                + "='" + discount.getOrderId() + "' AND " + COLUMN_DISCOUNT_PRODUCT_ID + "='" +
//                discount.getProductId() +"'", null);
//
//        if (res.moveToFirst()){
//            Log.d("sqlite", "already exist this discount for this productId in same order");
//        }else {
//            ContentValues contentValues = new ContentValues();
//
//            contentValues.put(COLUMN_ONGOING_DISCOUNT_ORDER_ID, orderId);
//            contentValues.put(COLUMN_ONGOING_DISCOUNT_PRODUCT_ID, productId);
//            contentValues.put(COLUMN_ONGOING_DISCOUNT_ID, discountId);
//
//            db.insert(TABLE_ONGOING_DISCOUNT, null, contentValues);
//        }
//
//    }

    @SuppressLint("Range")
    public List<DiscountDetails> getAllOrderedProductDiscount(String orderId, String productId){
        List<DiscountDetails> discountDetailsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_DISCOUNT + " WHERE " + COLUMN_DISCOUNT_ORDER_ID
                + "='" + orderId+ "' AND "+ COLUMN_DISCOUNT_PRODUCT_ID + "='" + productId +"'", null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            DiscountDetails discountDetails = new DiscountDetails();
            discountDetails.setId(res.getInt(res.getColumnIndex(COLUMN_DISCOUNT_ID)));
            discountDetails.setDiscountName(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_NAME)));
            discountDetails.setDiscount(res.getDouble(res.getColumnIndex(COLUMN_DISCOUNT_AMOUNT)));
            discountDetails.setDiscountType(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_TYPE)));
            discountDetails.setOrderId(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_ORDER_ID)));
            discountDetails.setProductId(res.getString(res.getColumnIndex(COLUMN_DISCOUNT_PRODUCT_ID)));
            discountDetails.setDiscountApplied(Boolean.parseBoolean(res.
                    getString(res.getColumnIndex(COLUMN_IS_DISCOUNT_AVAILABLE))));

            discountDetailsList.add(discountDetails);
            res.moveToNext();
        }
        return discountDetailsList;
    }

    public boolean deleteSingleDiscountFrom_OnGoingDiscount(String orderId, String productId, String discountId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISCOUNT, COLUMN_DISCOUNT_ORDER_ID+"=? AND "+
                        COLUMN_DISCOUNT_PRODUCT_ID + "=?"+COLUMN_DISCOUNT_ID + "=?",
                new String[]{orderId, productId, discountId});
        db.close();
        return true;
    }

//    public List<DiscountDatabaseModel> getOnGoingOrderDiscount(String orderId){
//        List<DiscountDatabaseModel> discountDatabaseModelList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ONGOING_DISCOUNT + " WHERE " + COLUMN_ONGOING_DISCOUNT_ORDER_ID
//                + "='" + orderId +"'", null);
//
//        res.moveToFirst();
//
//        while (res.isAfterLast()){
//            DiscountDatabaseModel discountDatabaseModel = new DiscountDatabaseModel();
//
//            discountDatabaseModel.setOrderId(res.getString(res.getColumnIndex(COLUMN_ONGOING_DISCOUNT_ORDER_ID)));
//            discountDatabaseModel.setProductId(res.getString(res.getColumnIndex(COLUMN_ONGOING_DISCOUNT_PRODUCT_ID)));
//            discountDatabaseModel.setDiscountId(res.getString(res.getColumnIndex(COLUMN_ONGOING_DISCOUNT_ID)));
//
//            discountDatabaseModelList.add(discountDatabaseModel);
//            res.moveToNext();
//        }
//
//        return discountDatabaseModelList;
//    }

//
//    public List<DiscountDatabaseModel> getOnGoingOrderDiscount(String orderId, String productId){
//        List<DiscountDatabaseModel> discountDatabaseModelList = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_ONGOING_DISCOUNT + " WHERE " + COLUMN_ONGOING_DISCOUNT_ORDER_ID
//                + "='" + orderId + COLUMN_ONGOING_DISCOUNT_PRODUCT_ID + "='" + productId +"'", null);
//
//        res.moveToFirst();
//
//        while (res.isAfterLast()){
//            DiscountDatabaseModel discountDatabaseModel = new DiscountDatabaseModel();
//
//            discountDatabaseModel.setOrderId(res.getString(res.getColumnIndex(COLUMN_ONGOING_DISCOUNT_ORDER_ID)));
//            discountDatabaseModel.setProductId(res.getString(res.getColumnIndex(COLUMN_ONGOING_DISCOUNT_PRODUCT_ID)));
//            discountDatabaseModel.setDiscountId(res.getString(res.getColumnIndex(COLUMN_ONGOING_DISCOUNT_ID)));
//
//            discountDatabaseModelList.add(discountDatabaseModel);
//            res.moveToNext();
//        }
//
//        return discountDatabaseModelList;
//    }


    public boolean shouldTaxApply(String orderId, String productId, String taxId){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_TAX + " WHERE "
                + COLUMN_TAX_ORDER_ID + " = '" + orderId + COLUMN_TAX_PRODUCT_ID + " = '" + productId
                +COLUMN_TAX_ID + " = '" + taxId +"'", null);

        cursor.moveToFirst();
        int iCount = cursor.getInt(0);

        if (iCount > 0) {
            return true;
        }

        return false;
    }

    public void insertTaxInfo(Tax tax){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM " + TABLE_TAX + " WHERE "
                + COLUMN_TAX_ID + " = '" + tax.getId() +"' AND "+ COLUMN_TAX_ORDER_ID
                + "='" + tax.getOrderId() +"' AND " + COLUMN_TAX_PRODUCT_ID + "='" + tax.getProductId() +"'", null);

        cursor.moveToFirst();
        int iCount = cursor.getInt(0);
        if (iCount > 0) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TAX_ID, tax.getId());
        contentValues.put(COLUMN_TAX_NAME, tax.getTaxName());
        contentValues.put(COLUMN_TAX_IN_PERCENTAGE, tax.getTaxAmount());
        contentValues.put(COLUMN_IS_TAX_AVAILABLE, String.valueOf(tax.getIsTaxApplied()));
        contentValues.put(COLUMN_TAX_ORDER_ID, tax.getOrderId());
        contentValues.put(COLUMN_TAX_PRODUCT_ID, tax.getProductId());


        db.insert(TABLE_TAX, null, contentValues);
    }

    @SuppressLint("Range")
    private boolean checkTaxAvailability(String orderId, String productId, int taxId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ORDER_ID
                + " = '" + orderId +"' AND "+ COLUMN_TAX_PRODUCT_ID+ " = '" + productId  +"' AND "
                + COLUMN_TAX_ID+ " = '" + taxId  +"'", null);

        res.moveToFirst();

        if (!res.isAfterLast()){
            if (!TextUtils.isEmpty(res.getString(res.getColumnIndex(COLUMN_IS_TAX_AVAILABLE)))){
                return  Boolean.parseBoolean(res.getString(res.getColumnIndex(COLUMN_IS_TAX_AVAILABLE)));
            }
        }
        return false;
    }

    @SuppressLint("Range")
    public List<Tax> getAllTaxInfoForSingleProduct(String orderId, String productId){
        List<Tax> taxModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ORDER_ID
                + " = '" + 0 +"' AND "+ COLUMN_TAX_PRODUCT_ID+ " = '" + 0  +"'", null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            Tax taxModel = new Tax();

            taxModel.setId(res.getInt(res.getColumnIndex(COLUMN_TAX_ID)));
            taxModel.setTaxName(res.getString(res.getColumnIndex(COLUMN_TAX_NAME)));
            taxModel.setTaxAmount(res.getDouble(res.getColumnIndex(COLUMN_TAX_IN_PERCENTAGE)));
            taxModel.setOrderId(res.getString(res.getColumnIndex(COLUMN_TAX_ORDER_ID)));
            taxModel.setIsTaxApplied(checkTaxAvailability(orderId, productId, taxModel.getId()));
            taxModel.setProductId(res.getString(res.getColumnIndex(COLUMN_TAX_PRODUCT_ID)));


            taxModelList.add(taxModel);
            res.moveToNext();
        }

        return taxModelList;
    }

    @SuppressLint("Range")
    public List<Tax> getAllTaxInfo(){
        List<Tax> taxModelList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ORDER_ID
                + " = '" + 0 +"' AND "+ COLUMN_TAX_PRODUCT_ID+ " = '" + 0  +"'", null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            Tax taxModel = new Tax();

            taxModel.setId(res.getInt(res.getColumnIndex(COLUMN_TAX_ID)));
            taxModel.setTaxName(res.getString(res.getColumnIndex(COLUMN_TAX_NAME)));
            taxModel.setTaxAmount(res.getDouble(res.getColumnIndex(COLUMN_TAX_IN_PERCENTAGE)));
            taxModel.setOrderId(res.getString(res.getColumnIndex(COLUMN_TAX_ORDER_ID)));
            taxModel.setIsTaxApplied(Boolean.parseBoolean(res.getString(res.getColumnIndex(COLUMN_IS_TAX_AVAILABLE))));
            taxModel.setProductId(res.getString(res.getColumnIndex(COLUMN_TAX_PRODUCT_ID)));


            taxModelList.add(taxModel);
            res.moveToNext();
        }

        return taxModelList;
    }

    @SuppressLint("Range")
    private Tax getSingleTaxDetails(int taxId){
        Tax tax = new Tax();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ID
                + "='" + taxId +"' AND "+ COLUMN_TAX_ORDER_ID + "='" + 0 +"'", null);
        res.moveToFirst();

        if (!res.isAfterLast()){
            tax.setId(res.getInt(res.getColumnIndex(COLUMN_TAX_ID)));
            tax.setTaxName(res.getString(res.getColumnIndex(COLUMN_TAX_NAME)));
            tax.setTaxAmount(res.getDouble(res.getColumnIndex(COLUMN_TAX_IN_PERCENTAGE)));
        }

        return tax;
    }

    public boolean deleteTaxTableData(String orderId, String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_TAX+ " WHERE " + COLUMN_TAX_ORDER_ID + "= '" + orderId + "' AND "
                + COLUMN_TAX_PRODUCT_ID + "= '" + productId + "'");
        return true;
    }


   /* public boolean deleteProductData(String orderId, String productId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ORDERED_PRODUCT+ " WHERE " + COLUMN_ORDERED_ORDER_ID + "= '" + orderId + "' AND "
                + COLUMN_ORDERED_PRODUCT_ID + "= '" + productId + "'");
        return true;
    }*/


//    public void insertOnGoingOrderTax(Tax tax){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ID
//                + "='" + tax.getTaxId() +"'"+ COLUMN_TAX_ORDER_ID
//                + "='" + tax.getOrderId() +"' AND " + COLUMN_TAX_PRODUCT_ID + "='" + tax.getProductId() +"'", null);
//
//
//        if (res.moveToFirst()){
//            Log.d("sqlite", "already exist this tax for this productId in same order");
//        }else {
//            insertTaxInfo(tax);
//        }
//
//    }

    @SuppressLint("Range")
    public List<Tax> getAllOnGoingTaxForASingleProduct(String orderId, String productId){
        List<Tax> taxList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ORDER_ID
                + "= '" + orderId +"' AND "+ COLUMN_TAX_PRODUCT_ID + "= '" + productId +"'", null);

        res.moveToFirst();

        while (!res.isAfterLast()){
            Tax tax = new Tax();
            tax.setId(res.getInt(res.getColumnIndex(COLUMN_TAX_ID)));
            tax.setOrderId(orderId);
            tax.setProductId(productId);
            tax.setTaxName(res.getString(res.getColumnIndex(COLUMN_TAX_NAME)));
            tax.setTaxAmount(res.getDouble(res.getColumnIndex(COLUMN_TAX_IN_PERCENTAGE)));
            tax.setIsTaxApplied(Boolean.parseBoolean(res.getString(res.getColumnIndex(COLUMN_IS_TAX_AVAILABLE))));

            taxList.add(tax);
            res.moveToNext();
        }

        return taxList;
    }


    public boolean deleteAOnGoingOrderTax(String orderId, String productId, String taxId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAX, COLUMN_TAX_ORDER_ID +"=?, "+
                COLUMN_TAX_PRODUCT_ID + "=? AND "+COLUMN_TAX_ID + "=?", new String[]{orderId, productId, taxId});
        db.close();
        return true;
    }

    public boolean updateTaxAvailability(Tax tax){
        SQLiteDatabase db = this.getWritableDatabase();
        if (tax != null && !TextUtils.isEmpty(tax.getOrderId())
                && !TextUtils.isEmpty(tax.getProductId()) && Long.parseLong(tax.getOrderId()) > 0){

            Cursor res = db.rawQuery("SELECT * FROM " + TABLE_TAX + " WHERE " + COLUMN_TAX_ID
                    + "='" + tax.getId() + "' AND "+ COLUMN_TAX_ORDER_ID
                    + " = '" + tax.getOrderId()+"' AND "+ COLUMN_TAX_PRODUCT_ID+ " = '" + tax.getProductId() +"'", null);
            res.moveToFirst();

            if (!res.isAfterLast()){
                ContentValues contentValues = new ContentValues();
                contentValues.put(COLUMN_IS_TAX_AVAILABLE, String.valueOf(tax.getIsTaxApplied()));


                db.update(TABLE_TAX, contentValues,
                        COLUMN_TAX_ORDER_ID+" = ? AND "+
                                COLUMN_TAX_PRODUCT_ID+" = ? AND "+COLUMN_TAX_ID+" = ? " ,
                        new String[]{tax.getOrderId(), tax.getProductId(),
                                String.valueOf(tax.getId())});
            }else {
                insertTaxInfo(tax);
            }

            return true;
        }
        return false;
    }


}