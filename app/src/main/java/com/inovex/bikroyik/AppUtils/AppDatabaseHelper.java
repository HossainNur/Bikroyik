package com.inovex.bikroyik.AppUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.inovex.bikroyik.adapter.Notification;
import com.inovex.bikroyik.model.NoticeModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DELL on 8/9/2018.
 */

public class AppDatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 7;
    // Database Name
    private static final String DATABASE_NAME = "workforce";


    public static final String TABLE_NOTICE = "notifications";


    // COLUMN NAMES OF EMPLOYEE TABLE

    public static final String TABLE_NAME_EMPLOYEE = "employee";

    public static final String COLUMN_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_EMPLOYEE_NAME = "employee_name";
    public static final String COLUMN_EMPLOYEE_ADDRESS = "employee_address";
    public static final String COLUMN_EMPLOYEE_PHONE = "employee_phone";
    public static final String COLUMN_EMPLOYEE_CATEGORY = "employee_category";
    public static final String COLUMN_EMPLOYEE_REPORTING_ID = "reporting_id";
    public static final String COLUMN_EMPLOYEE_REPORTING_NAME = "reporting_name";
    public static final String COLUMN_EMPLOYEE_DISTRIBUTOR_ID = "distributor_id";
    public static final String COLUMN_EMPLOYEE_DISTRIBUTOR_NAME = "distributor_name";
    public static final String COLUMN_EMPLOYEE_DISTRIBUTOR_ADDRESS = "distributor_address";
    public static final String COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE = "distributor_mobile";
    public static final String COLUMN_EMPLOYEE_REPORTING_MOBILE = "reporting_mobile";
    public static final String COLUMN_EMPLOYEE_TERRITORY_NAME = "territory_name";
    public static final String COLUMN_EMPLOYEE_AREA_NAME = "area_name";
    public static final String COLUMN_EMPLOYEE_REGION_NAME = "region_name";
    public static final String COLUMN_EMPLOYEE_IMAGE = "image_name";

    public static final String TABLE_SUBMITTED_RETAIL = "table_submitted_retail";
    public static final String COLUMN_RETAILER_TYPE = "retailer_type";




    //notification
//    public static final String COLUMN_NOTICE_FOR_USER_ID = "user_id";
    public static final String COLUMN_NOTICE_ID = "id";
    public static final String COLUMN_NOTICE_TYPE = "notic_type";
    public static final String COLUMN_NOTICE_TITLE = "title";

    public static final String COLUMN_NOTICE_DESCRIPTION = "description";
    public static final String COLUMN_NOTICE_FILE_TYPE = "file_type";
    public static final String COLUMN_NOTICE_FILE_SIZE = "file_size";

    public static final String COLUMN_NOTICE_FILE_URL = "file_url";
    public static final String COLUMN_NOTICE_IMAGE_URL = "img_url";
    public static final String COLUMN_NOTICE_DATE = "notice_date";

    public static final String COLUMN_IS_FILE_DOWNLOAD = "isDownloaded";
    public static final String COLUMN_NOTICE_PRIORITY = "priority";






    //task list
    public static final String TABLE_NAME_TASK = "task_table";
    public static final String COLUMN_TASK_ID = "id";
    public static final String COLUMN_TASK_PRIORITY = "priority";
    public static final String COLUMN_TASK_DATE = "date";

    public static final String COLUMN_TASK_DETAILS = "details";
    public static final String COLUMN_TASK_COMPLETE = "file_type";








    // COLUMN NAMES OF RETAILER TABLE
    public static final String TABLE_NAME_RETAILER = "retailer";
    public static final String TABLE_NAME_NEW_RETAILER = "new_retailer";

    public static final String COLUMN_RETAILER_ID = "retailer_id";
    public static final String COLUMN_RETAILER_NAME = "retailer_name";
    public static final String COLUMN_RETAILER_ADDRESS = "retailer_address";
    public static final String COLUMN_RETAILER_OWNER = "retailer_owner";
    public static final String COLUMN_RETAILER_PHONE = "retailer_phone";
    public static final String COLUMN_RETAILER_LATITUDE = "retailer_latitude";
    public static final String COLUMN_RETAILER_LONGITUDE = "retailer_longitude";
    public static final String COLUMN_RETAILER_DISTRIBUTOR_NAME = "distributor_name";
    public static final String COLUMN_RETAILER_DISTRIBUTOR_ID = "distributor_id";
    public static final String COLUMN_RETAILER_NATIONAL_ID = "national_id";
    public static final String COLUMN_RETAILER_MARKET_ID = "market_id";
    public static final String COLUMN_RETAILER_MARKET_NAME = "market_name";
    public static final String COLUMN_RETAILER_STORE_IMAGE = "store_image";
    public static final String COLUMN_RETAILER_SUBMITTED_BY = "submitted_by";
    public static final String COLUMN_RETAILER_DISTRIBUTOR_ADDRESS = "distributor_address";

    public static final String TABLE_CALL_SCHEDULER = "call_scheduler";
    public static final String COLUMN_CALL_SCHEDULER_NAME = "contact_name";
    public static final String COLUMN_CALL_SCHEDULER_PHONE = "contact_phone";
    public static final String COLUMN_CALL_SCHEDULER_DATE = "schedule_date";
    public static final String COLUMN_CALL_SCHEDULER_TIME = "schedule_time";

    public static final String TABLE_CALL_LEAVE = "table_leave";
    public static final String COLUMN_LEAVE_TYPE = "type";
    public static final String COLUMN_LEAVE_FROM = "from_date";
    public static final String COLUMN_LEAVE_TO = "to_date";
    public static final String COLUMN_LEAVE_COMMENT = "comment";
    public static final String COLUMN_LEAVE_STATUS = "status";



    public static final String TABLE_EXPENSE = "expenses";

    public static final String COLUMN_EXPENSE_AMOUNT = "amount";
    public static final String COLUMN_EXPENSE_NOTE = "note";
    public static final String COLUMN_EXPENSE_BY = "expense_by";
    public static final String COLUMN_EXPENSE_STATUS = "status";
    public static final String COLUMN_EXPENSE_APPROVED_AMOUNT = "approved_amount";
    public static final String COLUMN_EXPENSE_ATTACHMENT = "attachment";
    public static final String COLUMN_EXPENSE_TYPE = "type";
    public static final String COLUMN_EXPENSE_DATE = "date";


    static final String TABLE_SEND_LOCATION = "send_location";
    static final String TABLE_SEND_LOCATION_BACKGROUND = "send_location_background";


    public static final String TABLE_PRODUCT = "product";
    public static final String COLUMN_PRODUCT_ID = "id";
    public static final String COLUMN_PRODUCT_NAME = "product_name";
    public static final String COLUMN_PRODUCT_STARTING_STOCK = "starting_stock";
    public static final String COLUMN_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_PRODUCT_DISCOUNT = "product_discount";
    public static final String COLUMN_PRODUCT_DISCOUNT_TYPE = "discount_type";
    public static final String COLUMN_PRODUCT_DISCOUNT_AVAILABLE = "discount_available";
    public static final String COLUMN_PRODUCT_OFFER = "offer";
    public static final String COLUMN_PRODUCT_AVAILABLE_OFFER = "available_offer";
    public static final String COLUMN_PRODUCT_ON_HAND = "product_on_hand";
    public static final String COLUMN_PRODUCT_SAFETY_STOCK = "safety_stock";
    public static final String COLUMN_PRODUCT_SKU = "product_sku";
    public static final String COLUMN_PRODUCT_LABEL = "product_label";
    public static final String COLUMN_PRODUCT_CATEGORY = "product_category";
    public static final String COLUMN_PRODUCT_DESCRIPTION = "product_description";
    public static final String COLUMN_PRODUCT_MEASURE = "product_measure";
    public static final String COLUMN_PRODUCT_MRP = "product_mrp";
    public static final String COLUMN_PRODUCT_IMAGE_NAME = "product_image_name";
    public static final String COLUMN_PRODUCT_CATEGORY_NAME = "productCategoryName";
    public static final String COLUMN_PRODUCT_BRAND_NAME = "productBrandName";


    public static final String TABLE_BRAND = "table_brand";
    public static final String COLUMN_BRAND_NAME = "name";
    public static final String COLUMN_BRAND_ORIGIN = "origin";
    public static final String COLUMN_BRAND_LOGO = "logo";

    public static final  String TABLE_TARGET = "table_target";
    public static final String COLUMN_TARGET_PRODUCT_NAME = "product_name";
    public static final String COLUMN_TARGET_PRODUCT_ID = "product_id";
    public static final String COLUMN_TARGET_PRODUCT_TARGET_QTY = "target_qty";
    public static final String COLUMN_TARGET_PRODUCT_SALE_QTY = "sale_qty";
    public static final String COLUMN_TARGET_PRODUCT_TARGET_VALUE = "target_value";
    public static final String COLUMN_TARGET_PRODUCT_SALE_VALUE = "sale_value";

    public static final String TARGET_PRODUCT_TOTAL_SALE_VALUE = "total_sale_value";
    public static final String TARGET_PRODUCT_TOTAL_TARGET_VALUE = "total_target_value";
    public static final String TARGET_PRODUCT_TOTAL_SALE_QTY = "total_sale_qty";
    public static final String TARGET_PRODUCT_TOTAL_TARGET_QTY = "total_target_qty";


    public static final String TABLE_DELIVERY_TARGET = "table_delivery_target";
    public static final String COLUMN_DELIVERY_TARGET_QTY = "target_qty";
    public static final String COLUMN_DELIVERY_TARGET_VALUE = "target_value";
    public static final String COLUMN_DELIVERY_TARGET_DELIVERED_QTY = "delivered_qty";
    public static final String COLUMN_DELIVERY_TARGET_DELIVERED_VALUE = "delivered_value";







    //table order_delivery
    public static final String TABLE_ORDER_DELIVERY = "order_delivery";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_DETAILS = "order_details";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_TOTAL_PRICE = "order_total_price";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_PRICE = "order_price";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_DISCOUNT = "order_discount";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_DATE = "order_date";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_COLLECTION = "order_collection";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_DUE = "order_due";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ID = "retailer_id";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_NAME = "retailer_name";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_OWNER = "retailer_owner";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ADDRESS = "retailer_address";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_PHONE = "retailer_phone";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LATITUDE = "retailer_lat";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LONGITUDE = "retailer_long";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_ID = "distributor_id";
    public static final String COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_NAME = "distributor_name";

    public static final String TABLE_FINAL_ORDER = "table_final_order";

    public static final String COLUMN_FINAL_ORDER_ID = "order_id";
    public static final String COLUMN_FINAL_ORDER_RETAIL_ID = "retail_id";
    public static final String COLUMN_FINAL_ORDER_TOTAL = "order_total";
    public static final String COLUMN_FINAL_ORDER_DISCOUNT = "order_discount";
    public static final String COLUMN_FINAL_ORDER_GRAND_TOTAL = "order_grand_total";
    public static final String COLUMN_FINAL_ORDER_DELIVERY_DATE = "delivery_date";
    public static final String COLUMN_FINAL_ORDER_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_FINAL_ORDER_ADVANCED = "advanced";
    public static final String COLUMN_FINAL_ORDER_DUE = "due";
    public static final String COLUMN_FINAL_ORDER_STATUS = "status";
    public static final String COLUMN_FINAL_ORDER_DELIVERY_MAN = "delivery_man";

    public static final String TABLE_FINAL_ORDER_PRODUCT = "table_final_order_product";
    public static final String COLUMN_FINAL_ORDER_PRODUCT_ID = "product_id";
    public static final String COLUMN_FINAL_ORDER_PRODUCT_NAME = "product_name";
    public static final String COLUMN_FINAL_ORDER_PRODUCT_PRICE = "product_price";
    public static final String COLUMN_FINAL_ORDER_QUANTITY = "product_qty";









    // table sales order
    public static final String TABLE_SALES_ORDER = "sales_order";
    public static final String COLUMN_SALES_ORDER_ID = "order_id";
    public static final String COLUMN_SALES_ORDER_DATE = "order_date";
    public static final String COLUMN_SALES_ORDER_TIME = "order_time";
    public static final String COLUMN_SALES_ORDER_STATUS = "sales_order_status";
    public static final String COLUMN_SALES_ORDER_TOTAL = "order_amount";
    public static final String COLUMN_SALES_ORDER_DISCOUNT = "order_discount";
    public static final String COLUMN_SALES_ORDER_GRAND_TOTAL = "order_grand_total";
    public static final String COLUMN_SALES_ORDER_DELIVERY_DATE = "order_delivery_date";

    public static final String COLUMN_SALES_ORDER_SR_ID = "order_sr_id";
    public static final String COLUMN_SALES_ORDER_RETAILER_ID = "order_retailer_id";
    public static final String COLUMN_SALES_ORDER_RETAILER_NAME = "order_retailer_name";
    public static final String COLUMN_SALES_ORDER_RETAILER_ADDRESS = "order_retailer_address";
    public static final String COLUMN_SALES_ORDER_RETAILER_PHONE = "order_retailer_phone";
    public static final String COLUMN_SALES_ORDER_DISTRIBUTOR = "order_distributor";
    public static final String COLUMN_SALES_ORDER_SR = "order_sr";


    // table order_product
    public static final String TABLE_ORDER_PRODUCT = "order_product";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_PRODUCT_ID = "product_id";
    public static final String COLUMN_ORDER_PRODUCT_TITLE = "product_title";
    public static final String COLUMN_ORDER_PRODUCT_QUANTITY = "product_quantity";
    public static final String COLUMN_ORDER_PRICE = "product_price";
    public static final String COLUMN_ORDER_DISCOUNT = "product_discount";


    //table to save per product order
    public static final String TABLE_PER_PRODUCT = "order_per_product";

    public static final String TABLE_GRADE = "table_grade";
    public static final String COLUMN_GRADE_EMPLOYEE_ID = "employee_id";
    public static final String COLUMN_GRADE_EMPLOYEE_NAME = "employee_name";
    public static final String COLUMN_GRADE_SALES = "sale_values";
    public static final String COLUMN_GRADE = "grade";




    // table contacts
    public static final String TABLE_CONTACTS = "table_contacts";
    public static final String COLUMN_CONTACTS_TITLE = "contacts_title";
    public static final String COLUMN_CONTACTS_NUMBER = "contacts_number";
    public static final String COLUMN_CONTACTS_ADDRESS = "contacts_address";
    public static final String COLUMN_CONTACTS_TYPE = "contacts_type";



    // table route_retailer
    public static final String TABLE_ROUTE_RETAILER = "table_route_retailer";

    public static final String TABLE_ROUTE_VISITS = "table_route_visits";
    public static final String COLUMN_ROUTE_VISIT_DAY = "route_visit_day";
    public static final String COLUMN_ROUTE_VISIT_NAME = "route_visit_name";
    public static final String COLUMN_ROUTE_VISIT_MARKET_NAME = "route_visit_market";
    public static final String COLUMN_ROUTE_VISIT_MARKET_ADDRESS = "route_visit_market_address";


    public static final String COLUMN_ROUTE_RETAIL_ID = "route_retailer_id";
    public static final String COLUMN_ROUTE_RETAIL_NAME = "route_retailer_name";
    public static final String COLUMN_ROUTE_RETAIL_OWNER = "route_retailer_owner";
    public static final String COLUMN_ROUTE_RETAIL_ADDRESS = "route_retailer_address";
    public static final String COLUMN_ROUTE_RETAIL_LATITUDE = "route_retailer_latitude";
    public static final String COLUMN_ROUTE_RETAIL_LONGITUDE = "route_retailer_longitude";
    public static final String COLUMN_ROUTE_NAME = "route_retailer_route_name";


    // table day_route
    public static final String TABLE_DAY_ROUTE = "table_day_route";
    public static final String COLUMN_DAY_ROUTE_ID = "day_route_id";
    public static final String COLUMN_DAY_ROUTE_DAY = "day_route_day";
    public static final String COLUMN_DAY_ROUTE_ROUTE = "day_route_name";

    public static final String COLUMN_DAY_ROUTE_TERRITORY = "day_route_teritory";
    public static final String COLUMN_DAY_ROUTE_EMPLOYEE_ID = "day_route_emp_id";
    public static final String COLUMN_DAY_ROUTE_AREA = "day_route_area";
    public static final String COLUMN_DAY_ROUTE_REGION = "day_route_region";


    // table route_visit
    public static final String TABLE_ROUTE_VISIT = "table_route_visit";

    public static final String COLUMN_ROUTE_VISIT_RETAILER_ID = "route_retail_id";
    public static final String COLUMN_ROUTE_VISIT_RETAILER_NAME = "route_visit_name";
    public static final String COLUMN_ROUTE_VISIT_RETAILER_OWNER = "route_visit_owner";
    public static final String COLUMN_ROUTE_VISIT_RETAILER_ADDRESS = "route_visit_address";

    public static final String COLUMN_ROUTE_VISIT_LATITUDE = "route_visit_latitude";
    public static final String COLUMN_ROUTE_VISIT_LONGITUDE = "route_visit_longitude";
    public static final String COLUMN_ROUTE_VISIT_DATE = "route_visit_date";
    public static final String COLUMN_ROUTE_VISIT_STATUS = "route_visit_status";
    public static final String COLUMN_ROUTE_VISIT_ROUTE_DATE = "visit_route_date";


    // table attendance
    public static final String TABLE_ATTENDANCE = "table_attendance";

    public static final String COLUMN_ATTENDANCE_ID = "attendance_id";
    public static final String COLUMN_ATTENDANCE_DATE = "attendance_date";
    public static final String COLUMN_ATTENDANCE_IN_TIME = "attendance_in_time";
    public static final String COLUMN_ATTENDANCE_IN_ADDRESS = "attendance_in_address";
    public static final String COLUMN_ATTENDANCE_OUT_TIME = "attendance_out_time";
    public static final String COLUMN_ATTENDANCE_OUT_ADDRESS = "attendance_out_address";
    public static final String COLUMN_ATTENDANCE_STATUS = "attendance_status";
    public static final String COLUMN_ATTENDANCE_SENDING_STATUS = "attendance_send_status";
    public static final String COLUMN_ATTENDANCE_NOTES = "attendance_note";
    public static final String COLUMN_ATTENDANCE_IN_LAT_LON = "attend_lat_lon";
    public static final String COLUMN_ATTENDANCE_OUT_LAT_LON = "outgoing_lat_lon";

    public static final String TABLE_COMPLAIN = "table_complain";

    public static final String COLUMN_COMPLAIN_RETAIL_ID = "retail_id";
    public static final String COLUMN_COMPLAIN_TITLE = "title";
    public static final String COLUMN_COMPLAIN_NOTES = "notes";

    public static final String TABLE_VISITED_RETAIL = "table_visited_retail";
    public static final String COLUMN_VISITED_RETAIL_ID = "retail_id";

    public static final String TABLE_DASHBOARD = "table_dashboard";
    public static final String COLUMN_DAILY_NO_OF_ORDERS = "daily_no_of_orders";
    public static final String COLUMN_DAILY_GRAND_TOTALS = "daily_grand_totals";
    public static final String COLUMN_MONTHLY_NO_OF_ORDERS = "monthly_no_of_orders";
    public static final String COLUMN_MONTHLY_GRAND_TOTALS = "monthly_grand_totals";
    public static final String COLUMN_TARGETED_VISIT = "targeted_visit";
    public static final String COLUMN_VISITED_POINT = "visited_point";










    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL("CREATE TABLE if not exists " + TABLE_NAME_EMPLOYEE + "(" +
                    COLUMN_EMPLOYEE_ID + " LONGTEXT," +
                    COLUMN_EMPLOYEE_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_ADDRESS + " TEXT," +
                    COLUMN_EMPLOYEE_PHONE + " TEXT," +
                    COLUMN_EMPLOYEE_CATEGORY + " TEXT," +
                    COLUMN_EMPLOYEE_REPORTING_ID + " LONGTEXT," +
                    COLUMN_EMPLOYEE_REPORTING_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_DISTRIBUTOR_ID + " LONGTEXT," +
                    COLUMN_EMPLOYEE_DISTRIBUTOR_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_DISTRIBUTOR_ADDRESS + " TEXT," +
                    COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE + " TEXT," +
                    COLUMN_EMPLOYEE_REPORTING_MOBILE + " TEXT," +
                    COLUMN_EMPLOYEE_TERRITORY_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_AREA_NAME + " TEXT," +
                    COLUMN_EMPLOYEE_IMAGE + " TEXT," +
                    COLUMN_EMPLOYEE_REGION_NAME + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_COMPLAIN + "(" +
                    COLUMN_COMPLAIN_RETAIL_ID + " TEXT," +
                    COLUMN_COMPLAIN_TITLE + " TEXT," +
                    COLUMN_COMPLAIN_NOTES + " TEXT" +
                    ");");


            /*db.execSQL("CREATE TABLE if not exists " + TABLE_DELIVERY_TARGET + "(" +
                    COLUMN_DELIVERY_TARGET_QTY + " TEXT," +
                    COLUMN_DELIVERY_TARGET_VALUE + " TEXT," +
                    COLUMN_DELIVERY_TARGET_DELIVERED_QTY + " TEXT," +
                    COLUMN_DELIVERY_TARGET_DELIVERED_VALUE + " TEXT" +
                    ");");*/


            db.execSQL("CREATE TABLE if not exists " + TABLE_DASHBOARD + "(" +
                    COLUMN_DAILY_NO_OF_ORDERS + " TEXT," +
                    COLUMN_DAILY_GRAND_TOTALS + " TEXT," +
                    COLUMN_MONTHLY_NO_OF_ORDERS + " TEXT," +
                    COLUMN_MONTHLY_GRAND_TOTALS + " TEXT," +
                    COLUMN_TARGETED_VISIT + " TEXT," +
                    COLUMN_VISITED_POINT + " TEXT" +
                    ");");



            db.execSQL("CREATE TABLE if not exists " + TABLE_VISITED_RETAIL + "(" +
                    COLUMN_VISITED_RETAIL_ID + " TEXT" +
                    ");");






            db.execSQL("CREATE TABLE if not exists " + TABLE_NAME_RETAILER + "(" +
                    COLUMN_RETAILER_ID + " LONGTEXT," +
                    COLUMN_RETAILER_SUBMITTED_BY + " LONGTEXT," +
                    COLUMN_RETAILER_NAME + " TEXT," +
                    COLUMN_RETAILER_ADDRESS + " TEXT," +
                    COLUMN_RETAILER_OWNER + " TEXT," +
                    COLUMN_RETAILER_PHONE + " TEXT," +
                    COLUMN_RETAILER_LATITUDE + " TEXT," +
                    COLUMN_RETAILER_LONGITUDE + " TEXT," +
                    COLUMN_RETAILER_DISTRIBUTOR_ID + " LONGTEXT," +
                    COLUMN_RETAILER_NATIONAL_ID + " TEXT," +
                    COLUMN_RETAILER_DISTRIBUTOR_NAME + " TEXT," +
                    COLUMN_RETAILER_DISTRIBUTOR_ADDRESS + " TEXT," +
                    COLUMN_RETAILER_MARKET_ID + " LONGTEXT, " +
                    COLUMN_RETAILER_MARKET_NAME + " LONGTEXT " +

                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_SUBMITTED_RETAIL + "(" +
                    COLUMN_RETAILER_NAME + " TEXT," +
                    COLUMN_RETAILER_ADDRESS + " TEXT," +
                    COLUMN_RETAILER_OWNER + " TEXT," +
                    COLUMN_RETAILER_PHONE + " TEXT," +
                    COLUMN_RETAILER_TYPE + " TEXT," +
                    COLUMN_RETAILER_MARKET_ID + " LONGTEXT, " +
                    COLUMN_RETAILER_MARKET_NAME + " LONGTEXT " +

                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_CALL_SCHEDULER + "(" +
                    COLUMN_CALL_SCHEDULER_NAME + " TEXT," +
                    COLUMN_CALL_SCHEDULER_PHONE + " TEXT," +
                    COLUMN_CALL_SCHEDULER_DATE + " TEXT," +
                    COLUMN_CALL_SCHEDULER_TIME + " TEXT " +

                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_CALL_LEAVE + "(" +
                    COLUMN_LEAVE_TYPE + " TEXT," +
                    COLUMN_LEAVE_FROM + " TEXT," +
                    COLUMN_LEAVE_TO + " TEXT," +
                    COLUMN_LEAVE_COMMENT + " TEXT," +
                    COLUMN_LEAVE_STATUS + " TEXT " +

                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_GRADE + "(" +
                    COLUMN_GRADE_EMPLOYEE_ID + " TEXT," +
                    COLUMN_GRADE_EMPLOYEE_NAME + " TEXT," +
                    COLUMN_GRADE_SALES + " TEXT," +
                    COLUMN_GRADE + " TEXT " +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_EXPENSE + "(" +
                    COLUMN_EXPENSE_TYPE + " TEXT," +
                    COLUMN_EXPENSE_AMOUNT + " TEXT," +
                    COLUMN_EXPENSE_DATE + " TEXT," +
                    COLUMN_EXPENSE_STATUS + " TEXT," +
                    COLUMN_EXPENSE_APPROVED_AMOUNT + " TEXT," +
                    COLUMN_EXPENSE_NOTE + " TEXT," +
                    COLUMN_EXPENSE_BY + " TEXT," +
                    COLUMN_EXPENSE_ATTACHMENT + " TEXT " +

                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_TARGET + "(" +
                    COLUMN_TARGET_PRODUCT_ID + " TEXT," +
                    COLUMN_TARGET_PRODUCT_NAME + " TEXT," +
                    COLUMN_TARGET_PRODUCT_TARGET_QTY + " TEXT," +
                    COLUMN_TARGET_PRODUCT_SALE_QTY + " TEXT," +
                    COLUMN_TARGET_PRODUCT_TARGET_VALUE + " TEXT," +
                    COLUMN_TARGET_PRODUCT_SALE_VALUE + " TEXT " +

                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_BRAND + "(" +
                    COLUMN_BRAND_NAME + " TEXT," +
                    COLUMN_BRAND_ORIGIN + " TEXT," +
                    COLUMN_BRAND_LOGO + " TEXT " +
                    ");");




            db.execSQL("CREATE TABLE if not exists " + TABLE_NAME_TASK + "(" +
                    COLUMN_TASK_ID + " TEXT," +
                    COLUMN_TASK_PRIORITY + " TEXT," +
                    COLUMN_TASK_DATE + " TEXT," +
                    COLUMN_TASK_DETAILS + " TEXT," +
                    COLUMN_TASK_COMPLETE + " TEXT" +

                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_NAME_NEW_RETAILER + "(" +
                    COLUMN_RETAILER_SUBMITTED_BY + " LONGTEXT," +
                    COLUMN_RETAILER_NAME + " TEXT," +
                    COLUMN_RETAILER_ADDRESS + " TEXT," +
                    COLUMN_RETAILER_OWNER + " TEXT," +
                    COLUMN_RETAILER_PHONE + " TEXT," +
                    COLUMN_RETAILER_LATITUDE + " TEXT," +
                    COLUMN_RETAILER_LONGITUDE + " TEXT," +
                    COLUMN_RETAILER_DISTRIBUTOR_ID + " LONGTEXT," +
                    COLUMN_RETAILER_NATIONAL_ID + " TEXT," +
                    COLUMN_RETAILER_MARKET_ID + " LONGTEXT," +
                    COLUMN_RETAILER_STORE_IMAGE + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_SEND_LOCATION + "(" +
                    "date TEXT," +
                    "latitude TEXT," +
                    "longitude TEXT," +
                    "user_id TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_SEND_LOCATION_BACKGROUND + "(" +
                    "user_id TEXT," +
                    "latitude TEXT," +
                    "longitude TEXT," +
                    "address TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_PRODUCT + "(" +
                    COLUMN_PRODUCT_ID + " TEXT," +
                    COLUMN_PRODUCT_NAME + " TEXT," +
                    COLUMN_PRODUCT_BRAND_NAME + " TEXT," +
                    COLUMN_PRODUCT_SKU + " TEXT," +
                    COLUMN_PRODUCT_LABEL + " TEXT," +
                    COLUMN_PRODUCT_CATEGORY + " TEXT," +
                    COLUMN_PRODUCT_CATEGORY_NAME + " TEXT," +
                    COLUMN_PRODUCT_DESCRIPTION + " TEXT," +
                    COLUMN_PRODUCT_MEASURE + " TEXT," +
                    COLUMN_PRODUCT_STARTING_STOCK + " TEXT," +
                    COLUMN_PRODUCT_SAFETY_STOCK + " TEXT,"+
                    COLUMN_PRODUCT_PRICE + " TEXT,"+
                    COLUMN_PRODUCT_MRP + " TEXT," +
                    COLUMN_PRODUCT_DISCOUNT + " TEXT," +
                    COLUMN_PRODUCT_DISCOUNT_TYPE+ " TEXT," +
                    COLUMN_PRODUCT_DISCOUNT_AVAILABLE + " TEXT," +
                    COLUMN_PRODUCT_OFFER + " TEXT," +
                    COLUMN_PRODUCT_AVAILABLE_OFFER + " TEXT," +
                    COLUMN_PRODUCT_IMAGE_NAME + " TEXT," +
                    COLUMN_PRODUCT_ON_HAND + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_ORDER_DELIVERY + "(" +
                    "order_id TEXT," +
                    "order_details TEXT," +
                    "order_total_price TEXT," +
                    "order_date TEXT," +
                    "order_price TEXT," +
                    "order_discount TEXT," +
                    "order_collection TEXT," +
                    "order_due TEXT," +
                    "retailer_id TEXT," +
                    "retailer_name TEXT," +
                    "retailer_owner TEXT," +
                    "retailer_address TEXT," +
                    "retailer_phone TEXT," +
                    "retailer_lat TEXT," +
                    "retailer_long TEXT," +
                    "distributor_id TEXT," +
                    "distributor_name TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_SALES_ORDER + "(" +
                    COLUMN_SALES_ORDER_ID + " TEXT," +
                    COLUMN_SALES_ORDER_DATE + " TEXT," +
                    COLUMN_SALES_ORDER_TIME + " TEXT," +
                    COLUMN_SALES_ORDER_STATUS + " TEXT," +
                    COLUMN_SALES_ORDER_TOTAL + " TEXT," +
                    COLUMN_SALES_ORDER_DISCOUNT + " TEXT," +
                    COLUMN_SALES_ORDER_GRAND_TOTAL + " TEXT," +
                    COLUMN_SALES_ORDER_DELIVERY_DATE + " TEXT," +
                    COLUMN_SALES_ORDER_SR_ID + " TEXT," +
                    COLUMN_SALES_ORDER_RETAILER_ID + " TEXT," +
                    COLUMN_SALES_ORDER_RETAILER_NAME + " TEXT," +
                    COLUMN_SALES_ORDER_RETAILER_ADDRESS + " TEXT," +
                    COLUMN_SALES_ORDER_RETAILER_PHONE + " TEXT," +
                    COLUMN_SALES_ORDER_DISTRIBUTOR + " TEXT," +
                    COLUMN_SALES_ORDER_SR + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_ROUTE_RETAILER + "(" +
                    COLUMN_ROUTE_RETAIL_ID + " TEXT," +
                    COLUMN_ROUTE_RETAIL_NAME + " TEXT," +
                    COLUMN_ROUTE_RETAIL_OWNER + " TEXT," +
                    COLUMN_ROUTE_RETAIL_ADDRESS + " TEXT," +
                    COLUMN_ROUTE_RETAIL_LATITUDE + " TEXT," +
                    COLUMN_ROUTE_RETAIL_LONGITUDE + " TEXT," +
                    COLUMN_ROUTE_NAME + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_ROUTE_VISIT + "(" +
                    COLUMN_ROUTE_VISIT_RETAILER_ID + " TEXT," +
                    COLUMN_ROUTE_VISIT_RETAILER_NAME + " TEXT," +
                    COLUMN_ROUTE_VISIT_RETAILER_OWNER + " TEXT," +
                    COLUMN_ROUTE_VISIT_RETAILER_ADDRESS + " TEXT," +
                    COLUMN_ROUTE_VISIT_LATITUDE + " TEXT," +
                    COLUMN_ROUTE_VISIT_LONGITUDE + " TEXT," +
                    COLUMN_ROUTE_VISIT_DATE + " TEXT," +
                    COLUMN_ROUTE_VISIT_STATUS + " TEXT," +
                    COLUMN_ROUTE_VISIT_ROUTE_DATE + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_ROUTE_VISITS + "(" +
                    COLUMN_ROUTE_VISIT_RETAILER_ID + " TEXT," +
                    COLUMN_ROUTE_VISIT_DAY + " TEXT," +
                    COLUMN_ROUTE_VISIT_NAME + " TEXT," +
                    COLUMN_ROUTE_VISIT_MARKET_ADDRESS + " TEXT," +
                    COLUMN_ROUTE_VISIT_STATUS + " TEXT," +
                    COLUMN_ROUTE_VISIT_MARKET_NAME + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_DAY_ROUTE + "(" +
                    COLUMN_DAY_ROUTE_ID + " TEXT," +
                    COLUMN_DAY_ROUTE_DAY + " TEXT," +
                    COLUMN_DAY_ROUTE_ROUTE + " TEXT," +
                    COLUMN_DAY_ROUTE_TERRITORY + " TEXT," +
                    COLUMN_DAY_ROUTE_EMPLOYEE_ID + " TEXT," +
                    COLUMN_DAY_ROUTE_AREA + " TEXT," +
                    COLUMN_DAY_ROUTE_REGION + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_ORDER_PRODUCT + "(" +
                    COLUMN_ORDER_ID + " TEXT," +
                    COLUMN_ORDER_PRODUCT_ID + " TEXT," +
                    COLUMN_ORDER_PRODUCT_TITLE + " TEXT," +
                    COLUMN_ORDER_PRODUCT_QUANTITY + " TEXT," +
                    COLUMN_ORDER_PRICE + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_PER_PRODUCT + "(" +
                    COLUMN_ORDER_PRODUCT_ID + " TEXT," +
                    COLUMN_ORDER_PRODUCT_TITLE + " TEXT," +
                    COLUMN_ORDER_PRODUCT_QUANTITY + " TEXT," +
                    COLUMN_ORDER_DISCOUNT + " TEXT," +
                    COLUMN_ORDER_PRICE + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_CONTACTS + "(" +
                    COLUMN_CONTACTS_TITLE + " TEXT," +
                    COLUMN_CONTACTS_TYPE + " TEXT,"+
                    COLUMN_CONTACTS_ADDRESS + " TEXT,"+
                    COLUMN_CONTACTS_NUMBER + " TEXT" +
                    ");");

            db.execSQL("CREATE TABLE if not exists " + TABLE_ATTENDANCE + "(" +
                    COLUMN_ATTENDANCE_ID + " TEXT," +
                    COLUMN_ATTENDANCE_DATE + " TEXT," +
                    COLUMN_ATTENDANCE_IN_TIME + " TEXT," +
                    COLUMN_ATTENDANCE_IN_ADDRESS + " TEXT," +
                    COLUMN_ATTENDANCE_OUT_TIME + " TEXT," +
                    COLUMN_ATTENDANCE_OUT_ADDRESS + " TEXT," +
                    COLUMN_ATTENDANCE_STATUS + " TEXT," +
                    COLUMN_ATTENDANCE_NOTES + " TEXT," +
                    COLUMN_ATTENDANCE_IN_LAT_LON + " TEXT," +
                    COLUMN_ATTENDANCE_OUT_LAT_LON + " TEXT," +
                    COLUMN_ATTENDANCE_SENDING_STATUS + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_FINAL_ORDER + "(" +
                    COLUMN_FINAL_ORDER_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_RETAIL_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_TOTAL + " TEXT," +
                    COLUMN_FINAL_ORDER_DISCOUNT + " TEXT," +
                    COLUMN_FINAL_ORDER_GRAND_TOTAL + " TEXT," +
                    COLUMN_FINAL_ORDER_DELIVERY_DATE + " TEXT," +
                    COLUMN_FINAL_ORDER_PAYMENT_METHOD + " TEXT," +
                    COLUMN_FINAL_ORDER_ADVANCED + " TEXT," +
                    COLUMN_FINAL_ORDER_DUE + " TEXT," +
                    COLUMN_FINAL_ORDER_STATUS + " TEXT," +
                    COLUMN_FINAL_ORDER_DELIVERY_MAN + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_FINAL_ORDER_PRODUCT + "(" +
                    COLUMN_FINAL_ORDER_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_PRODUCT_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_PRODUCT_NAME + " TEXT," +
                    COLUMN_FINAL_ORDER_QUANTITY + " TEXT," +
                    COLUMN_FINAL_ORDER_PRODUCT_PRICE + " TEXT" +
                    ");");


//            COLUMN_NOTICE_IMAGE_URL+" TEXT," +
            db.execSQL("CREATE TABLE if not exists " + TABLE_NOTICE + "(" +
                    COLUMN_NOTICE_ID+" TEXT," +
                    COLUMN_NOTICE_TYPE+" TEXT,"+
                    COLUMN_NOTICE_TITLE+" TEXT," +
                    COLUMN_NOTICE_DESCRIPTION+" TEXT," +
                    COLUMN_NOTICE_FILE_URL+" TEXT," +
                    COLUMN_NOTICE_FILE_SIZE+" TEXT," +
                    COLUMN_NOTICE_FILE_TYPE+" TEXT," +
                    COLUMN_IS_FILE_DOWNLOAD+" TEXT," +
                    COLUMN_NOTICE_DATE+" TEXT," +
                    COLUMN_NOTICE_PRIORITY+" TEXT" +
                    ");");


        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e("Table creation error", "onCreate error");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > 1){
            if(newVersion > 2){
                db.execSQL("CREATE TABLE if not exists " + TABLE_COMPLAIN + "(" +
                        COLUMN_COMPLAIN_RETAIL_ID + " TEXT," +
                        COLUMN_COMPLAIN_TITLE + " TEXT," +
                        COLUMN_COMPLAIN_NOTES + " TEXT" +
                        ");");
            }else{
                db.execSQL("ALTER TABLE " + TABLE_NAME_EMPLOYEE+" ADD COLUMN "+COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE+" TEXT");
                db.execSQL("ALTER TABLE " + TABLE_NAME_EMPLOYEE+" ADD COLUMN "+COLUMN_EMPLOYEE_IMAGE+" TEXT");
            }

        }
        if(newVersion > 3){

            db.execSQL("CREATE TABLE if not exists " + TABLE_VISITED_RETAIL + "(" +
                    COLUMN_VISITED_RETAIL_ID + " TEXT" +
                    ");");


        }
        if(newVersion > 4){

            db.execSQL("CREATE TABLE if not exists " + TABLE_DASHBOARD + "(" +
                    COLUMN_DAILY_NO_OF_ORDERS + " TEXT," +
                    COLUMN_DAILY_GRAND_TOTALS + " TEXT," +
                    COLUMN_MONTHLY_NO_OF_ORDERS + " TEXT," +
                    COLUMN_MONTHLY_GRAND_TOTALS + " TEXT," +
                    COLUMN_TARGETED_VISIT + " TEXT," +
                    COLUMN_VISITED_POINT + " TEXT" +
                    ");");

        }
        if(newVersion > 5){
            db.execSQL("CREATE TABLE if not exists " + TABLE_FINAL_ORDER + "(" +
                    COLUMN_FINAL_ORDER_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_RETAIL_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_TOTAL + " TEXT," +
                    COLUMN_FINAL_ORDER_DISCOUNT + " TEXT," +
                    COLUMN_FINAL_ORDER_GRAND_TOTAL + " TEXT," +
                    COLUMN_FINAL_ORDER_DELIVERY_DATE + " TEXT," +
                    COLUMN_FINAL_ORDER_PAYMENT_METHOD + " TEXT," +
                    COLUMN_FINAL_ORDER_ADVANCED + " TEXT," +
                    COLUMN_FINAL_ORDER_DUE + " TEXT," +
                    COLUMN_FINAL_ORDER_STATUS + " TEXT," +
                    COLUMN_FINAL_ORDER_DELIVERY_MAN + " TEXT" +
                    ");");


            db.execSQL("CREATE TABLE if not exists " + TABLE_FINAL_ORDER_PRODUCT + "(" +
                    COLUMN_FINAL_ORDER_PRODUCT_ID + " TEXT," +
                    COLUMN_FINAL_ORDER_PRODUCT_NAME + " TEXT," +
                    COLUMN_FINAL_ORDER_QUANTITY + " TEXT," +
                    COLUMN_FINAL_ORDER_PRODUCT_PRICE + " TEXT" +
                    ");");

        }
        if(newVersion > 6){
            db.execSQL("ALTER TABLE " + TABLE_FINAL_ORDER_PRODUCT+" ADD COLUMN "+COLUMN_FINAL_ORDER_ID+" TEXT");
        }


        onCreate(db);
    }

    public void insertFinalOrder(String id, String retailId, String total, String discount, String grandTotal,
                                 String deliveryDate, String paymentMethod, String advanced, String due,
                                 String status, String deliveryMan){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FINAL_ORDER_ID , id);
        contentValues.put(COLUMN_FINAL_ORDER_RETAIL_ID , retailId);
        contentValues.put(COLUMN_FINAL_ORDER_TOTAL , total);
        contentValues.put(COLUMN_FINAL_ORDER_DISCOUNT , discount);
        contentValues.put(COLUMN_FINAL_ORDER_GRAND_TOTAL , grandTotal);
        contentValues.put(COLUMN_FINAL_ORDER_DELIVERY_DATE , deliveryDate);
        contentValues.put(COLUMN_FINAL_ORDER_PAYMENT_METHOD , paymentMethod);
        contentValues.put(COLUMN_FINAL_ORDER_ADVANCED , advanced);
        contentValues.put(COLUMN_FINAL_ORDER_DUE , due);
        contentValues.put(COLUMN_FINAL_ORDER_STATUS , status);
        contentValues.put(COLUMN_FINAL_ORDER_DELIVERY_MAN , deliveryMan);

        db.insert(TABLE_FINAL_ORDER, null,contentValues);




    }

    public void insertFinalOrderForProduct(String id, String productId, String productName, String productQuantity,
                                 String totalPrice){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FINAL_ORDER_ID , id);
        contentValues.put(COLUMN_FINAL_ORDER_PRODUCT_ID , productId);
        contentValues.put(COLUMN_FINAL_ORDER_PRODUCT_NAME , productName);
        contentValues.put(COLUMN_FINAL_ORDER_QUANTITY , productQuantity);
        contentValues.put(COLUMN_FINAL_ORDER_PRODUCT_PRICE , totalPrice);

        Log.d("workforce_orders", "onResponse: "+contentValues);


        db.insert(TABLE_FINAL_ORDER_PRODUCT, null,contentValues);




    }

    public ArrayList<HashMap<String, String>> getFinalOrderData(){

        ArrayList<HashMap<String, String>> finalOrderList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_FINAL_ORDER, null);

        res.moveToFirst();
        while (!res.isAfterLast()) {
            HashMap<String, String> orderMap = new HashMap<String, String>();

            orderMap.put(COLUMN_FINAL_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_ID)));
            orderMap.put(COLUMN_FINAL_ORDER_RETAIL_ID, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_RETAIL_ID)));
            orderMap.put(COLUMN_FINAL_ORDER_TOTAL, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_TOTAL)));
            orderMap.put(COLUMN_FINAL_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DISCOUNT)));
            orderMap.put(COLUMN_FINAL_ORDER_GRAND_TOTAL, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_GRAND_TOTAL)));
            orderMap.put(COLUMN_FINAL_ORDER_DELIVERY_DATE, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DELIVERY_DATE)));
            orderMap.put(COLUMN_FINAL_ORDER_PAYMENT_METHOD, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_PAYMENT_METHOD)));
            orderMap.put(COLUMN_FINAL_ORDER_ADVANCED, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_ADVANCED)));
            orderMap.put(COLUMN_FINAL_ORDER_DUE, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DUE)));
            orderMap.put(COLUMN_FINAL_ORDER_STATUS, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_STATUS)));
            orderMap.put(COLUMN_FINAL_ORDER_DELIVERY_MAN, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DELIVERY_MAN)));

            finalOrderList.add(orderMap);

            res.moveToNext();
        }






        return  finalOrderList;
    }


    public HashMap<String, String> getFinalOrderDataByOrderId(String OrderId){


        HashMap<String, String> orderMap = new HashMap<String, String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_FINAL_ORDER + " WHERE " + COLUMN_FINAL_ORDER_ID + " =?", new String[]{OrderId});

        res.moveToFirst();
        while (res.isAfterLast() == false) {


            orderMap.put(COLUMN_FINAL_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_ID)));
            orderMap.put(COLUMN_FINAL_ORDER_RETAIL_ID, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_RETAIL_ID)));
            orderMap.put(COLUMN_FINAL_ORDER_TOTAL, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_TOTAL)));
            orderMap.put(COLUMN_FINAL_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DISCOUNT)));
            orderMap.put(COLUMN_FINAL_ORDER_GRAND_TOTAL, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_GRAND_TOTAL)));
            orderMap.put(COLUMN_FINAL_ORDER_DELIVERY_DATE, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DELIVERY_DATE)));
            orderMap.put(COLUMN_FINAL_ORDER_PAYMENT_METHOD, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_PAYMENT_METHOD)));
            orderMap.put(COLUMN_FINAL_ORDER_ADVANCED, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_ADVANCED)));
            orderMap.put(COLUMN_FINAL_ORDER_DUE, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DUE)));
            orderMap.put(COLUMN_FINAL_ORDER_STATUS, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_STATUS)));
            orderMap.put(COLUMN_FINAL_ORDER_DELIVERY_MAN, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_DELIVERY_MAN)));



            res.moveToNext();
        }






        return  orderMap;
    }

    public ArrayList<HashMap<String, String>> getFinalOrderProductData(String orderId){

        ArrayList<HashMap<String, String>> finalOrderList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_FINAL_ORDER_PRODUCT + " WHERE " + COLUMN_FINAL_ORDER_ID + " =?", new String[]{orderId});

        res.moveToFirst();
        while (res.isAfterLast() == false) {
            HashMap<String, String> orderMap = new HashMap<String, String>();

            orderMap.put(COLUMN_FINAL_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_ID)));
            orderMap.put(COLUMN_FINAL_ORDER_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_PRODUCT_ID)));
            orderMap.put(COLUMN_FINAL_ORDER_PRODUCT_NAME, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_PRODUCT_NAME)));
            orderMap.put(COLUMN_FINAL_ORDER_QUANTITY, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_QUANTITY)));
            orderMap.put(COLUMN_FINAL_ORDER_PRODUCT_PRICE, res.getString(res.getColumnIndex(COLUMN_FINAL_ORDER_PRODUCT_PRICE)));

            finalOrderList.add(orderMap);

            res.moveToNext();
        }




        return  finalOrderList;
    }




    public void deleteFinalOrderTableData(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FINAL_ORDER,null, null);

    }

    public void deleteFinalOrderProductTableData(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_FINAL_ORDER_PRODUCT,null, null);

    }





    public void insertDashboardData(String dailyOrders, String dailyGrandTotals, String monthlyOrders, String monthlyGrandTotals, String targetVisit, String visitedPoint){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_DAILY_NO_OF_ORDERS, dailyOrders);
        contentValues.put(COLUMN_DAILY_GRAND_TOTALS, dailyGrandTotals);
        contentValues.put(COLUMN_MONTHLY_NO_OF_ORDERS, monthlyOrders);
        contentValues.put(COLUMN_MONTHLY_GRAND_TOTALS, monthlyGrandTotals);
        contentValues.put(COLUMN_TARGETED_VISIT, targetVisit);
        contentValues.put(COLUMN_VISITED_POINT, visitedPoint);

        db.insert(TABLE_DASHBOARD, null,contentValues);

    }

    public HashMap<String, String > getDashboardData(){

        HashMap<String, String> dashboard = new HashMap<String, String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_DASHBOARD, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            dashboard.put(COLUMN_DAILY_NO_OF_ORDERS, res.getString(res.getColumnIndex(COLUMN_DAILY_NO_OF_ORDERS)));
            dashboard.put(COLUMN_DAILY_GRAND_TOTALS, res.getString(res.getColumnIndex(COLUMN_DAILY_GRAND_TOTALS)));
            dashboard.put(COLUMN_MONTHLY_NO_OF_ORDERS, res.getString(res.getColumnIndex(COLUMN_MONTHLY_NO_OF_ORDERS)));
            dashboard.put(COLUMN_MONTHLY_GRAND_TOTALS, res.getString(res.getColumnIndex(COLUMN_MONTHLY_GRAND_TOTALS)));
            dashboard.put(COLUMN_TARGETED_VISIT, res.getString(res.getColumnIndex(COLUMN_TARGETED_VISIT)));
            dashboard.put(COLUMN_VISITED_POINT, res.getString(res.getColumnIndex(COLUMN_VISITED_POINT)));


            res.moveToNext();
        }

        return dashboard;
    }



    public void deleteDashboardTableData(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_DASHBOARD,null, null);

    }





    public void insertTaskData(String taskId, String taskPriority, String taskDate,
                               String taskDetails, String isTaskComplete){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TASK_ID, taskId);
        contentValues.put(COLUMN_TASK_PRIORITY, taskPriority);
        contentValues.put(COLUMN_TASK_DATE, taskDate);
        contentValues.put(COLUMN_TASK_DETAILS, taskDetails);
        contentValues.put(COLUMN_TASK_COMPLETE, isTaskComplete);


        db.insert(TABLE_NAME_TASK, null,contentValues);

    }

    public ArrayList<Notification> getAllTaskData(){

        ArrayList<Notification> taskList = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_TASK, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Notification task = new Notification();
            task.setNotificationId(res.getString(res.getColumnIndex(COLUMN_TASK_ID)));
            task.setNotificationType(res.getString(res.getColumnIndex(COLUMN_TASK_PRIORITY)));
            task.setNotificationDate(res.getString(res.getColumnIndex(COLUMN_TASK_DATE)));
            task.setNotificationText(res.getString(res.getColumnIndex(COLUMN_TASK_DETAILS)));
            task.setChecked(Boolean.parseBoolean(res.getString(res.getColumnIndex(COLUMN_TASK_COMPLETE))));


            taskList.add(task);
            res.moveToNext();
        }

        return taskList;
    }

    public boolean deleteSingeTask(String taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_TASK + " where date !='"+taskId+"'");
        return true;
    }

    public void insertVisitedRetail(String retailId){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_VISITED_RETAIL_ID, retailId);


        db.insert(TABLE_VISITED_RETAIL, null,contentValues);


    }

    public ArrayList<HashMap<String, String>> getVisitedRetail(){
        ArrayList<HashMap<String, String>> retailList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_VISITED_RETAIL, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> retailMap = new HashMap<String, String>();

            retailMap.put(COLUMN_VISITED_RETAIL_ID, res.getString(res.getColumnIndex(COLUMN_VISITED_RETAIL_ID)));


            retailList.add(retailMap);

            res.moveToNext();
        }

        return retailList;
    }

    public void deleteVisitedRetailTableData(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_VISITED_RETAIL,null, null);


    }



    public void insertComplain(String id, String title, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_COMPLAIN_RETAIL_ID, id);
        contentValues.put(COLUMN_COMPLAIN_TITLE, title);
        contentValues.put(COLUMN_COMPLAIN_NOTES, notes);

        db.insert(TABLE_COMPLAIN, null,contentValues);

    }

    public ArrayList<HashMap<String, String>> getComplain(){
        ArrayList<HashMap<String, String>> complainList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_COMPLAIN, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> complainMap = new HashMap<String, String>();

            complainMap.put(COLUMN_COMPLAIN_RETAIL_ID, res.getString(res.getColumnIndex(COLUMN_COMPLAIN_RETAIL_ID)));
            complainMap.put(COLUMN_COMPLAIN_TITLE, res.getString(res.getColumnIndex(COLUMN_COMPLAIN_TITLE)));
            complainMap.put(COLUMN_COMPLAIN_NOTES, res.getString(res.getColumnIndex(COLUMN_COMPLAIN_NOTES)));

            complainList.add(complainMap);

            res.moveToNext();
        }

        return complainList;
    }

    public void deleteComplainTable(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_COMPLAIN,null, null);

    }

    public boolean insertTarget(String id, String name, String target_qty, String sale_qty,
                                String target_value, String sale_value){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TARGET_PRODUCT_ID, id);
        contentValues.put(COLUMN_TARGET_PRODUCT_NAME, name);
        contentValues.put(COLUMN_TARGET_PRODUCT_TARGET_QTY, target_qty);
        contentValues.put(COLUMN_TARGET_PRODUCT_SALE_QTY, sale_qty);
        contentValues.put(COLUMN_TARGET_PRODUCT_TARGET_VALUE, target_value);
        contentValues.put(COLUMN_TARGET_PRODUCT_SALE_VALUE, sale_value);


        db.insert(TABLE_TARGET, null,contentValues);

        return true;
    }



    public ArrayList<HashMap<String, String>> getTargetData() {


        ArrayList<HashMap<String, String>> TargetList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TARGET, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> TargetMap = new HashMap<String, String>();

            TargetMap.put(COLUMN_TARGET_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_ID)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_NAME, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_NAME)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_TARGET_QTY, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_TARGET_QTY)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_SALE_QTY, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_SALE_QTY)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_TARGET_VALUE, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_TARGET_VALUE)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_SALE_VALUE, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_SALE_VALUE)));


            TargetList.add(TargetMap);

            res.moveToNext();
        }

        return TargetList;
    }
    // get a retailer data with retailer id
    public HashMap<String, String> getProductInfoForTarget(String productId) {


        HashMap<String, String> TargetMap = new HashMap<String, String>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_TARGET + " WHERE " + COLUMN_TARGET_PRODUCT_ID + " =?", new String[]{productId});
        res.moveToFirst();

        while (!res.isAfterLast()) {

            TargetMap.put(COLUMN_TARGET_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_ID)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_NAME, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_NAME)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_SALE_QTY, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_SALE_QTY)));
            TargetMap.put(COLUMN_TARGET_PRODUCT_SALE_VALUE, res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_SALE_VALUE)));

            res.moveToNext();
        }

        return TargetMap;
    }
    public void deleteTargetTable(){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_TARGET,null, null);

    }

    public void UpdateTargetTable(String productId, String sale_qty, String sale_values ){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COLUMN_TARGET_PRODUCT_SALE_QTY, sale_qty);
        contentValues.put(COLUMN_TARGET_PRODUCT_SALE_VALUE, sale_values);

        db.update(TABLE_TARGET,contentValues,"product_id = ?",new String[]{productId});



    }



    public HashMap<String, String> getTargetCalculation() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_TARGET,null);
        res.moveToFirst();
        int total_qty = 0;
        double total_value = 0;
        int sale_qty = 0;
        double sale_value = 0;
        HashMap<String, String> details = new HashMap<String, String>();
        while (!res.isAfterLast()) {

            total_qty += Double.parseDouble(res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_TARGET_QTY)));
            total_value += Double.parseDouble(res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_TARGET_VALUE)));
            sale_qty += Double.parseDouble(res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_SALE_QTY)));
            sale_value += Double.parseDouble(res.getString(res.getColumnIndex(COLUMN_TARGET_PRODUCT_SALE_VALUE)));

            res.moveToNext();
        }

        details.put(TARGET_PRODUCT_TOTAL_TARGET_QTY, String.valueOf(total_qty));
        details.put(TARGET_PRODUCT_TOTAL_SALE_QTY, String.valueOf(sale_qty));
        details.put(TARGET_PRODUCT_TOTAL_TARGET_VALUE, String.valueOf(total_value));
        details.put(TARGET_PRODUCT_TOTAL_SALE_VALUE, String.valueOf(sale_value));

        return details;

    }


    public boolean insertLeaves(String type, String from, String to, String comment, String status){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_LEAVE_TYPE, type);
        contentValues.put(COLUMN_LEAVE_FROM, from);
        contentValues.put(COLUMN_LEAVE_TO, to);
        contentValues.put(COLUMN_LEAVE_COMMENT, comment);
        contentValues.put(COLUMN_LEAVE_STATUS, status);

        db.insert(TABLE_CALL_LEAVE, null,contentValues);

        return true;
    }

    public ArrayList<HashMap<String, String>> getLeaveData() {


        ArrayList<HashMap<String, String>> leaveList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CALL_LEAVE, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> leaveMap = new HashMap<String, String>();

            leaveMap.put(COLUMN_LEAVE_TYPE, res.getString(res.getColumnIndex(COLUMN_LEAVE_TYPE)));
            leaveMap.put(COLUMN_LEAVE_FROM, res.getString(res.getColumnIndex(COLUMN_LEAVE_FROM)));
            leaveMap.put(COLUMN_LEAVE_TO, res.getString(res.getColumnIndex(COLUMN_LEAVE_TO)));
            leaveMap.put(COLUMN_LEAVE_COMMENT, res.getString(res.getColumnIndex(COLUMN_LEAVE_COMMENT)));
            leaveMap.put(COLUMN_LEAVE_STATUS, res.getString(res.getColumnIndex(COLUMN_LEAVE_STATUS)));


            leaveList.add(leaveMap);

            res.moveToNext();
        }

        return leaveList;
    }

    public void deleteLeaveData(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALL_LEAVE,null, null);
    }

    public boolean insertGrades(String id, String name, String sales, String grade){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_GRADE_EMPLOYEE_NAME, name);
        contentValues.put(COLUMN_GRADE_EMPLOYEE_ID, id);
        contentValues.put(COLUMN_GRADE_SALES, sales);
        contentValues.put(COLUMN_GRADE, grade);


        db.insert(TABLE_GRADE, null,contentValues);

        return true;
    }


    public ArrayList<HashMap<String, String>> getGradeData() {


        ArrayList<HashMap<String, String>> brandList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_GRADE, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> brandMap = new HashMap<String, String>();
            brandMap.put(COLUMN_GRADE_EMPLOYEE_ID, res.getString(res.getColumnIndex(COLUMN_GRADE_EMPLOYEE_ID)));
            brandMap.put(COLUMN_GRADE_EMPLOYEE_NAME, res.getString(res.getColumnIndex(COLUMN_GRADE_EMPLOYEE_NAME)));
            brandMap.put(COLUMN_GRADE_SALES, res.getString(res.getColumnIndex(COLUMN_GRADE_SALES)));
            brandMap.put(COLUMN_GRADE, res.getString(res.getColumnIndex(COLUMN_GRADE)));

            brandList.add(brandMap);

            res.moveToNext();
        }

        return brandList;
    }

    public void deleteGradeTable(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GRADE, null, null);
    }


    public boolean insertBrands(String name, String origin, String logo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_BRAND_NAME, name);
        contentValues.put(COLUMN_BRAND_ORIGIN, origin);
        contentValues.put(COLUMN_BRAND_LOGO, logo);

        db.insert(TABLE_BRAND, null,contentValues);

        return true;
    }



    public ArrayList<HashMap<String, String>> getBrandData() {


        ArrayList<HashMap<String, String>> brandList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_BRAND, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> brandMap = new HashMap<String, String>();

            brandMap.put(COLUMN_BRAND_NAME, res.getString(res.getColumnIndex(COLUMN_BRAND_NAME)));
            brandMap.put(COLUMN_BRAND_ORIGIN, res.getString(res.getColumnIndex(COLUMN_BRAND_ORIGIN)));
            brandMap.put(COLUMN_BRAND_LOGO, res.getString(res.getColumnIndex(COLUMN_BRAND_LOGO)));

            brandList.add(brandMap);

            res.moveToNext();
        }

        return brandList;
    }

    public void deleteBrandData(){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BRAND,null, null);

    }



    public boolean insertCallSchedule(String name, String phone, String date, String time){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CALL_SCHEDULER_NAME, name);
        contentValues.put(COLUMN_CALL_SCHEDULER_PHONE, phone);
        contentValues.put(COLUMN_CALL_SCHEDULER_DATE, date);
        contentValues.put(COLUMN_CALL_SCHEDULER_TIME, time);

        db.insert(TABLE_CALL_SCHEDULER, null,contentValues);

        return true;
    }


    public ArrayList<HashMap<String, String>> getCallSchedulerData() {


        ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CALL_SCHEDULER, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> retilaerMap = new HashMap<String, String>();

            retilaerMap.put(COLUMN_CALL_SCHEDULER_NAME, res.getString(res.getColumnIndex(COLUMN_CALL_SCHEDULER_NAME)));
            retilaerMap.put(COLUMN_CALL_SCHEDULER_PHONE, res.getString(res.getColumnIndex(COLUMN_CALL_SCHEDULER_PHONE)));
            retilaerMap.put(COLUMN_CALL_SCHEDULER_DATE, res.getString(res.getColumnIndex(COLUMN_CALL_SCHEDULER_DATE)));
            retilaerMap.put(COLUMN_CALL_SCHEDULER_TIME, res.getString(res.getColumnIndex(COLUMN_CALL_SCHEDULER_TIME)));

            retailerList.add(retilaerMap);

            res.moveToNext();
        }

        return retailerList;
    }

    public boolean deleteSingleScheduledCall(String phone) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CALL_SCHEDULER, COLUMN_CALL_SCHEDULER_PHONE + " = ?", new String[]{phone});
        return true;

    }

    public ArrayList<HashMap<String, String>> getSingleScheduledCall(String time) {

        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> callList = new ArrayList<HashMap<String, String>>();

        Cursor res = db.rawQuery("select * from " + TABLE_CALL_SCHEDULER + " WHERE " + COLUMN_CALL_SCHEDULER_TIME + " =?", new String[]{time});
        res.moveToFirst();

        while (!res.isAfterLast()) {
            HashMap<String, String> call = new HashMap<String, String>();
            call.put(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_NAME, res.getString(res.getColumnIndex(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_NAME)));
            call.put(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_PHONE, res.getString(res.getColumnIndex(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_PHONE)));
            callList.add(call);
            res.moveToNext();
        }

        return callList;

    }




    // insert employee data
    public boolean insertEmployeeData(String employeeId, String employeeName, String employeeAddress, String employeePhone,
                                      String employeeCategory, String employeeReportingId, String reportingName, String distributorId, String distributorName,
                                      String distributorAddress,
                                      String reportingMobile, String territoryName, String areaName, String regionName, String imageName, String distributorMobile)


    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_EMPLOYEE_ID, employeeId);
        contentValues.put(COLUMN_EMPLOYEE_NAME, employeeName);
        contentValues.put(COLUMN_EMPLOYEE_ADDRESS, employeeAddress);
        contentValues.put(COLUMN_EMPLOYEE_PHONE, employeePhone);
        contentValues.put(COLUMN_EMPLOYEE_CATEGORY, employeeCategory);
        contentValues.put(COLUMN_EMPLOYEE_REPORTING_ID, employeeReportingId);
        contentValues.put(COLUMN_EMPLOYEE_REPORTING_NAME, reportingName);
        contentValues.put(COLUMN_EMPLOYEE_DISTRIBUTOR_ID, distributorId);
        contentValues.put(COLUMN_EMPLOYEE_DISTRIBUTOR_NAME, distributorName);
        contentValues.put(COLUMN_EMPLOYEE_DISTRIBUTOR_ADDRESS, distributorAddress);
        contentValues.put(COLUMN_EMPLOYEE_REPORTING_MOBILE, reportingMobile);
        contentValues.put(COLUMN_EMPLOYEE_TERRITORY_NAME, territoryName);
        contentValues.put(COLUMN_EMPLOYEE_AREA_NAME, areaName);
        contentValues.put(COLUMN_EMPLOYEE_REGION_NAME, regionName);
        contentValues.put(COLUMN_EMPLOYEE_IMAGE, imageName);
        contentValues.put(COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE, distributorMobile);


        Log.d("workforce_employee", "insertEmployeeData: "+contentValues);
        db.insert(TABLE_NAME_EMPLOYEE, null, contentValues);
        return true;
    }


    // get employee  data
    public HashMap<String, String> getEmployeeInfo() {


        HashMap<String, String> employeeHashMap = new HashMap<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_EMPLOYEE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            employeeHashMap.put(COLUMN_EMPLOYEE_ID, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_ID)));
            employeeHashMap.put(COLUMN_EMPLOYEE_NAME, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_NAME)));
            employeeHashMap.put(COLUMN_EMPLOYEE_ADDRESS, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_ADDRESS)));
            employeeHashMap.put(COLUMN_EMPLOYEE_PHONE, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_PHONE)));
            employeeHashMap.put(COLUMN_EMPLOYEE_CATEGORY, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_CATEGORY)));
            employeeHashMap.put(COLUMN_EMPLOYEE_REPORTING_ID, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_REPORTING_ID)));
            employeeHashMap.put(COLUMN_EMPLOYEE_REPORTING_NAME, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_REPORTING_NAME)));
            employeeHashMap.put(COLUMN_EMPLOYEE_DISTRIBUTOR_ID, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_DISTRIBUTOR_ID)));
            employeeHashMap.put(COLUMN_EMPLOYEE_DISTRIBUTOR_NAME, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_DISTRIBUTOR_NAME)));
            employeeHashMap.put(COLUMN_EMPLOYEE_DISTRIBUTOR_ADDRESS, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_DISTRIBUTOR_ADDRESS)));
            employeeHashMap.put(COLUMN_EMPLOYEE_REPORTING_MOBILE, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_REPORTING_MOBILE)));
            employeeHashMap.put(COLUMN_EMPLOYEE_AREA_NAME, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_AREA_NAME)));
            employeeHashMap.put(COLUMN_EMPLOYEE_TERRITORY_NAME, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_TERRITORY_NAME)));
            employeeHashMap.put(COLUMN_EMPLOYEE_REGION_NAME, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_REGION_NAME)));
            employeeHashMap.put(COLUMN_EMPLOYEE_IMAGE, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_IMAGE)));
            employeeHashMap.put(COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE, res.getString(res.getColumnIndex(COLUMN_EMPLOYEE_DISTRIBUTOR_MOBILE)));




            res.moveToNext();
        }

        return employeeHashMap;
    }


    // delete all employee data

    public boolean deleteAllEmployeeData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_EMPLOYEE);
        return true;

    }

    // insert retailer data
    public boolean insertRetailerData(String retailerId, String retailerName, String retailerOwner, String retailerAddress,
                                      String retailerPhone, String nationalId, String retailerLatitude, String retailerLongitude,
                                      String distributorId,String distributorName, String distributorAddress,
                                      String marketID, String marketName, String submittedBy)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_RETAILER_ID, retailerId);
        contentValues.put(COLUMN_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_RETAILER_OWNER, retailerOwner);
        contentValues.put(COLUMN_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_RETAILER_PHONE, retailerPhone);
        contentValues.put(COLUMN_RETAILER_NATIONAL_ID, nationalId);
        contentValues.put(COLUMN_RETAILER_LATITUDE, retailerLatitude);
        contentValues.put(COLUMN_RETAILER_LONGITUDE, retailerLongitude);
        contentValues.put(COLUMN_RETAILER_DISTRIBUTOR_ID, distributorId);
        contentValues.put(COLUMN_RETAILER_DISTRIBUTOR_NAME, distributorName);
        contentValues.put(COLUMN_RETAILER_DISTRIBUTOR_ADDRESS,distributorAddress);
        contentValues.put(COLUMN_RETAILER_MARKET_ID,marketID);
        contentValues.put(COLUMN_RETAILER_MARKET_NAME, marketName);
        contentValues.put(COLUMN_RETAILER_SUBMITTED_BY,submittedBy);
        db.insert(TABLE_NAME_RETAILER, null, contentValues);
        return true;
    }

    // insert retailer data
    public boolean insertSubmittedRetailerData(String retailerName, String retailerOwner, String retailerAddress,
                                      String retailerPhone, String retailType,
                                      String marketID, String marketName)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COLUMN_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_RETAILER_OWNER, retailerOwner);
        contentValues.put(COLUMN_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_RETAILER_PHONE, retailerPhone);
        contentValues.put(COLUMN_RETAILER_MARKET_ID,marketID);
        contentValues.put(COLUMN_RETAILER_MARKET_NAME, marketName);
        contentValues.put(COLUMN_RETAILER_TYPE,retailType);
        //Log.d("workforce_add_retailer", "insertSubmittedRetailerData: "+contentValues);
        db.insert(TABLE_SUBMITTED_RETAIL, null, contentValues);

        return true;
    }




    public boolean insertNewRetailerData(String retailerName, String retailerAddress, String retailerPhone,
                                         String retailerOwner, String retailerLatitude, String retailerLongitude,
                                         String nationalID, String image)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_RETAILER_PHONE, retailerPhone);
        contentValues.put(COLUMN_RETAILER_OWNER, retailerOwner);
        contentValues.put(COLUMN_RETAILER_LATITUDE, retailerLatitude);
        contentValues.put(COLUMN_RETAILER_LONGITUDE, retailerLongitude);
        contentValues.put("national_id", nationalID);
        contentValues.put("retailer_image",image);

        //Log.d("GetData", "insertNewRetailerData: "+ contentValues);

        db.insert(TABLE_NAME_NEW_RETAILER, null, contentValues);
        return true;
    }



    // get retailer data
    public ArrayList<HashMap<String, String>> getRetailerData() {


        ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME_RETAILER, null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {

            HashMap<String, String> retilaerMap = new HashMap<String, String>();

            retilaerMap.put(COLUMN_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_RETAILER_ID)));
            retilaerMap.put(COLUMN_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_RETAILER_NAME)));
            retilaerMap.put(COLUMN_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_RETAILER_ADDRESS)));
            retilaerMap.put(COLUMN_RETAILER_OWNER, res.getString(res.getColumnIndex(COLUMN_RETAILER_OWNER)));
            retilaerMap.put(COLUMN_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_RETAILER_PHONE)));
            retilaerMap.put(COLUMN_RETAILER_LATITUDE, res.getString(res.getColumnIndex(COLUMN_RETAILER_LATITUDE)));
            retilaerMap.put(COLUMN_RETAILER_LONGITUDE, res.getString(res.getColumnIndex(COLUMN_RETAILER_LONGITUDE)));
            retilaerMap.put(COLUMN_RETAILER_DISTRIBUTOR_NAME, res.getString(res.getColumnIndex(COLUMN_RETAILER_DISTRIBUTOR_NAME)));
            retilaerMap.put(COLUMN_RETAILER_DISTRIBUTOR_ID,res.getString(res.getColumnIndex(COLUMN_RETAILER_DISTRIBUTOR_ID)));
            retilaerMap.put(COLUMN_RETAILER_DISTRIBUTOR_ADDRESS,res.getString(res.getColumnIndex(COLUMN_RETAILER_DISTRIBUTOR_ADDRESS)));
            retilaerMap.put(COLUMN_RETAILER_MARKET_ID,res.getString(res.getColumnIndex(COLUMN_RETAILER_MARKET_ID)));
            retilaerMap.put(COLUMN_RETAILER_MARKET_NAME,res.getString(res.getColumnIndex(COLUMN_RETAILER_MARKET_NAME)));
            retilaerMap.put(COLUMN_RETAILER_SUBMITTED_BY,res.getString(res.getColumnIndex(COLUMN_RETAILER_SUBMITTED_BY)));

            retailerList.add(retilaerMap);

            res.moveToNext();
        }

        return retailerList;
    }


    public ArrayList<HashMap<String, String>> getSubmittedRetailerData() {


        ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SUBMITTED_RETAIL, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {

            HashMap<String, String> retilaerMap = new HashMap<String, String>();

            retilaerMap.put(COLUMN_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_RETAILER_NAME)));
            retilaerMap.put(COLUMN_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_RETAILER_ADDRESS)));
            retilaerMap.put(COLUMN_RETAILER_OWNER, res.getString(res.getColumnIndex(COLUMN_RETAILER_OWNER)));
            retilaerMap.put(COLUMN_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_RETAILER_PHONE)));
            retilaerMap.put(COLUMN_RETAILER_MARKET_ID, res.getString(res.getColumnIndex(COLUMN_RETAILER_MARKET_ID)));
            retilaerMap.put(COLUMN_RETAILER_MARKET_NAME, res.getString(res.getColumnIndex(COLUMN_RETAILER_MARKET_NAME)));
            retilaerMap.put(COLUMN_RETAILER_TYPE, res.getString(res.getColumnIndex(COLUMN_RETAILER_TYPE)));


            retailerList.add(retilaerMap);

            res.moveToNext();
        }

        return retailerList;
    }





    // get a retailer data with retailer id
    public HashMap<String, String> getRetailerInfo(String retailerId) {


        HashMap<String, String> retilaerMap = new HashMap<String, String>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_NAME_RETAILER + " WHERE " + COLUMN_RETAILER_ID + " =?", new String[]{retailerId});
        res.moveToFirst();

        while (!res.isAfterLast()) {


            retilaerMap.put(COLUMN_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_RETAILER_ID)));
            retilaerMap.put(COLUMN_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_RETAILER_NAME)));
            retilaerMap.put(COLUMN_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_RETAILER_ADDRESS)));
            retilaerMap.put(COLUMN_RETAILER_OWNER, res.getString(res.getColumnIndex(COLUMN_RETAILER_OWNER)));
            retilaerMap.put(COLUMN_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_RETAILER_PHONE)));
            retilaerMap.put(COLUMN_RETAILER_LATITUDE, res.getString(res.getColumnIndex(COLUMN_RETAILER_LATITUDE)));
            retilaerMap.put(COLUMN_RETAILER_LONGITUDE, res.getString(res.getColumnIndex(COLUMN_RETAILER_LONGITUDE)));
            retilaerMap.put(COLUMN_RETAILER_DISTRIBUTOR_NAME, res.getString(res.getColumnIndex(COLUMN_RETAILER_DISTRIBUTOR_NAME)));
            retilaerMap.put(COLUMN_RETAILER_DISTRIBUTOR_ID,res.getString(res.getColumnIndex(COLUMN_RETAILER_DISTRIBUTOR_ID)));
            retilaerMap.put(COLUMN_RETAILER_DISTRIBUTOR_ADDRESS,res.getString(res.getColumnIndex(COLUMN_RETAILER_DISTRIBUTOR_ADDRESS)));
            retilaerMap.put(COLUMN_RETAILER_MARKET_ID,res.getString(res.getColumnIndex(COLUMN_RETAILER_MARKET_ID)));
            retilaerMap.put(COLUMN_RETAILER_MARKET_NAME,res.getString(res.getColumnIndex(COLUMN_RETAILER_MARKET_NAME)));
            retilaerMap.put(COLUMN_RETAILER_SUBMITTED_BY,res.getString(res.getColumnIndex(COLUMN_RETAILER_SUBMITTED_BY)));


            res.moveToNext();
        }

        return retilaerMap;
    }

    // delete all employee data

    public boolean deleteAllRetailerData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME_RETAILER);
        return true;

    }


    public boolean deleteAllSubmittedRetailerData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SUBMITTED_RETAIL);
        return true;

    }



    public HashMap<String,String>  getRetailerNameByMarket(String marketName){

        HashMap<String, String> retailerList = new HashMap<String, String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+COLUMN_RETAILER_ID+", "+ COLUMN_RETAILER_NAME +" from " + TABLE_NAME_RETAILER + " WHERE " + COLUMN_RETAILER_MARKET_NAME + " =?", new String[]{marketName});

        res.moveToFirst();

        while (!res.isAfterLast()){
            String id = res.getString(res.getColumnIndex(COLUMN_RETAILER_ID));
            String name = res.getString(res.getColumnIndex(COLUMN_RETAILER_NAME));
            retailerList.put(name, id);
            res.moveToNext();

        }

        return retailerList;
    }





    // insert sending device info data to send_device_info table
    public boolean insertSendLocationData(String date, String latitude,
                                          String longitude, String userId)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", date);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("user_id", userId);
        db.insert(TABLE_SEND_LOCATION, null, contentValues);
        return true;
    }


    // get data from send_location table
    public ArrayList<HashMap<String, String>> getSendLocationData() {

        ArrayList<HashMap<String, String>> sendLocation = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SEND_LOCATION, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("date", res.getString(res.getColumnIndex("date")));
            map.put("latitude", res.getString(res.getColumnIndex("latitude")));
            map.put("longitude", res.getString(res.getColumnIndex("longitude")));
            map.put("user_id", res.getString(res.getColumnIndex("user_id")));
            sendLocation.add(map);
            res.moveToNext();

        }

        return sendLocation;
    }


    public boolean deleteAllDataFromLocationTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SEND_LOCATION);
        return true;
    }













    // insert sending device info data to send_device_info table
    public boolean insertBackgroundLocationData(String userId, String latitude,
                                          String longitude, String address)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", userId);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);
        contentValues.put("address", address);
        db.insert(TABLE_SEND_LOCATION_BACKGROUND, null, contentValues);
        return true;
    }


    // get data from send_location table
    public ArrayList<HashMap<String, String>> getBackgroundLocationData() {

        ArrayList<HashMap<String, String>> sendLocation = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SEND_LOCATION_BACKGROUND, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("user_id", res.getString(res.getColumnIndex("user_id")));
            map.put("latitude", res.getString(res.getColumnIndex("latitude")));
            map.put("longitude", res.getString(res.getColumnIndex("longitude")));
            map.put("address", res.getString(res.getColumnIndex("address")));
            sendLocation.add(map);
            res.moveToNext();

        }

        return sendLocation;
    }


    public boolean deleteAllDataFromBackgroundLocationTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SEND_LOCATION_BACKGROUND);
        return true;
    }














    // insert product data
    public boolean insertProductData(String productId, String productName, String sku, String label, String category,
                                     String category_name, String desc, String measuringType,
                                     String price, String mrp, String start_stock, String safety_stock,
                                     String discount_type, String available_discount, String discount,
                                     String available_offer, String offer, String image, String onHand, String brand)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_ID, productId);
        contentValues.put(COLUMN_PRODUCT_NAME, productName);
        contentValues.put(COLUMN_PRODUCT_STARTING_STOCK, start_stock);
        contentValues.put(COLUMN_PRODUCT_PRICE, price);
        contentValues.put(COLUMN_PRODUCT_DISCOUNT, discount);
        contentValues.put(COLUMN_PRODUCT_SKU, sku);
        contentValues.put(COLUMN_PRODUCT_LABEL, label);
        contentValues.put(COLUMN_PRODUCT_CATEGORY, category);
        contentValues.put(COLUMN_PRODUCT_CATEGORY_NAME, category_name);
        contentValues.put(COLUMN_PRODUCT_DESCRIPTION, desc);
        contentValues.put(COLUMN_PRODUCT_MEASURE, measuringType);
        contentValues.put(COLUMN_PRODUCT_MRP, mrp);
        contentValues.put(COLUMN_PRODUCT_SAFETY_STOCK, safety_stock);
        contentValues.put(COLUMN_PRODUCT_DISCOUNT_TYPE, discount_type);
        contentValues.put(COLUMN_PRODUCT_DISCOUNT_AVAILABLE, available_discount);
        contentValues.put(COLUMN_PRODUCT_AVAILABLE_OFFER, available_offer);
        contentValues.put(COLUMN_PRODUCT_OFFER, offer);
        contentValues.put(COLUMN_PRODUCT_IMAGE_NAME, image);
        contentValues.put(COLUMN_PRODUCT_ON_HAND, onHand);
        contentValues.put(COLUMN_PRODUCT_BRAND_NAME, brand);


        db.insert(TABLE_PRODUCT, null, contentValues);
        return true;
    }


    public ArrayList<HashMap<String, String>> getProductData() {


        ArrayList<HashMap<String, String>> productList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PRODUCT+" order by "+COLUMN_PRODUCT_CATEGORY_NAME, null);
        res.moveToFirst();

        while (!res.isAfterLast()) {

            HashMap<String, String> productMap = new HashMap<String, String>();

            productMap.put(COLUMN_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_PRODUCT_ID)));
            productMap.put(COLUMN_PRODUCT_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_NAME)));
            productMap.put(COLUMN_PRODUCT_SKU, res.getString(res.getColumnIndex(COLUMN_PRODUCT_SKU)));
            productMap.put(COLUMN_PRODUCT_LABEL, res.getString(res.getColumnIndex(COLUMN_PRODUCT_LABEL)));
            productMap.put(COLUMN_PRODUCT_CATEGORY, res.getString(res.getColumnIndex(COLUMN_PRODUCT_CATEGORY)));
            productMap.put(COLUMN_PRODUCT_CATEGORY_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_CATEGORY_NAME)));
            productMap.put(COLUMN_PRODUCT_DESCRIPTION, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION)));
            productMap.put(COLUMN_PRODUCT_MEASURE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_MEASURE)));
            productMap.put(COLUMN_PRODUCT_STARTING_STOCK, res.getString(res.getColumnIndex(COLUMN_PRODUCT_STARTING_STOCK)));
            productMap.put(COLUMN_PRODUCT_SAFETY_STOCK, res.getString(res.getColumnIndex(COLUMN_PRODUCT_SAFETY_STOCK)));
            productMap.put(COLUMN_PRODUCT_PRICE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_PRICE)));
            productMap.put(COLUMN_PRODUCT_MRP, res.getString(res.getColumnIndex(COLUMN_PRODUCT_MRP)));
            productMap.put(COLUMN_PRODUCT_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DISCOUNT)));
            productMap.put(COLUMN_PRODUCT_DISCOUNT_TYPE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DISCOUNT_TYPE)));
            productMap.put(COLUMN_PRODUCT_DISCOUNT_AVAILABLE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DISCOUNT_AVAILABLE)));
            productMap.put(COLUMN_PRODUCT_OFFER, res.getString(res.getColumnIndex(COLUMN_PRODUCT_OFFER)));
            productMap.put(COLUMN_PRODUCT_AVAILABLE_OFFER, res.getString(res.getColumnIndex(COLUMN_PRODUCT_AVAILABLE_OFFER)));
            productMap.put(COLUMN_PRODUCT_ON_HAND, res.getString(res.getColumnIndex(COLUMN_PRODUCT_ON_HAND)));
            productMap.put(COLUMN_PRODUCT_BRAND_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_BRAND_NAME)));
            productMap.put(COLUMN_PRODUCT_IMAGE_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_IMAGE_NAME)));


            productList.add(productMap);

            res.moveToNext();
        }

        return productList;
    }

    public HashMap<String, String> getSpecificProductData(String productId) {

        HashMap<String, String> productMap = new HashMap<String, String>();

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("select * from " + TABLE_PRODUCT + " WHERE " + COLUMN_PRODUCT_ID + " =?", new String[]{productId});
        res.moveToFirst();

        while (!res.isAfterLast()){

            productMap.put(COLUMN_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_PRODUCT_ID)));
            productMap.put(COLUMN_PRODUCT_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_NAME)));
            productMap.put(COLUMN_PRODUCT_SKU, res.getString(res.getColumnIndex(COLUMN_PRODUCT_SKU)));
            productMap.put(COLUMN_PRODUCT_LABEL, res.getString(res.getColumnIndex(COLUMN_PRODUCT_LABEL)));
            productMap.put(COLUMN_PRODUCT_CATEGORY, res.getString(res.getColumnIndex(COLUMN_PRODUCT_CATEGORY)));
            productMap.put(COLUMN_PRODUCT_CATEGORY_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_CATEGORY_NAME)));
            productMap.put(COLUMN_PRODUCT_DESCRIPTION, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DESCRIPTION)));
            productMap.put(COLUMN_PRODUCT_MEASURE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_MEASURE)));
            productMap.put(COLUMN_PRODUCT_STARTING_STOCK, res.getString(res.getColumnIndex(COLUMN_PRODUCT_STARTING_STOCK)));
            productMap.put(COLUMN_PRODUCT_SAFETY_STOCK, res.getString(res.getColumnIndex(COLUMN_PRODUCT_SAFETY_STOCK)));
            productMap.put(COLUMN_PRODUCT_PRICE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_PRICE)));
            productMap.put(COLUMN_PRODUCT_MRP, res.getString(res.getColumnIndex(COLUMN_PRODUCT_MRP)));
            productMap.put(COLUMN_PRODUCT_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DISCOUNT)));
            productMap.put(COLUMN_PRODUCT_DISCOUNT_TYPE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DISCOUNT_TYPE)));
            productMap.put(COLUMN_PRODUCT_DISCOUNT_AVAILABLE, res.getString(res.getColumnIndex(COLUMN_PRODUCT_DISCOUNT_AVAILABLE)));
            productMap.put(COLUMN_PRODUCT_OFFER, res.getString(res.getColumnIndex(COLUMN_PRODUCT_OFFER)));
            productMap.put(COLUMN_PRODUCT_AVAILABLE_OFFER, res.getString(res.getColumnIndex(COLUMN_PRODUCT_AVAILABLE_OFFER)));
            productMap.put(COLUMN_PRODUCT_ON_HAND, res.getString(res.getColumnIndex(COLUMN_PRODUCT_ON_HAND)));
            productMap.put(COLUMN_PRODUCT_BRAND_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_BRAND_NAME)));
            productMap.put(COLUMN_PRODUCT_IMAGE_NAME, res.getString(res.getColumnIndex(COLUMN_PRODUCT_IMAGE_NAME)));

            res.moveToNext();

        }


        return productMap;
    }


    public boolean deleteAllProductData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_PRODUCT);
        return true;

    }


    // order delivery
    // insert retailer data
    public boolean insertOrderDeliveryData(String orderId, String orderDetails, String orderTotalPrice, String orderDate,
                                           String orderPrice, String orderDiscount, String orderCollection, String orderDue,
                                           String retailerId, String retailerName, String retailerOwner, String retailerAddress,
                                           String retailerLatitude, String retailerLongitude, String retailerPhone, String distributorId, String distributorName)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_ID, orderId);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_DETAILS, orderDetails);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_TOTAL_PRICE, orderTotalPrice);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_DATE, orderDate);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_PRICE, orderPrice);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_DISCOUNT, orderDiscount);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_COLLECTION, orderCollection);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_DUE, orderDue);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ID, retailerId);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_OWNER, retailerOwner);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LATITUDE, retailerLatitude);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LONGITUDE, retailerLongitude);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_PHONE, retailerPhone);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_ID, distributorId);
        contentValues.put(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_NAME, distributorName);


        db.insert(TABLE_ORDER_DELIVERY, null, contentValues);
        return true;
    }


    // get retailer data
    public ArrayList<HashMap<String, String>> getOrderDeliveryData() {


        ArrayList<HashMap<String, String>> orderDeliveryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ORDER_DELIVERY, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> orderDeliveryMap = new HashMap<String, String>();

            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_ID)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DETAILS, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DETAILS)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_TOTAL_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_TOTAL_PRICE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DATE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DATE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_PRICE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DISCOUNT)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_COLLECTION, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_COLLECTION)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DUE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DUE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ID)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_NAME)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_OWNER, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_OWNER)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ADDRESS)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LATITUDE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LATITUDE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LONGITUDE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LONGITUDE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_PHONE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_ID)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_NAME, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_NAME)));


            orderDeliveryList.add(orderDeliveryMap);

            res.moveToNext();
        }

        return orderDeliveryList;
    }


    // get order_delivery for a distributor
    public ArrayList<HashMap<String, String>> getOrderDeliveryDataForDistributor(String distributorId) {


        ArrayList<HashMap<String, String>> orderDeliveryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM order_delivery WHERE distributor_id = ?", new String[]{distributorId});

        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> orderDeliveryMap = new HashMap<String, String>();

            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_ID)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DETAILS, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DETAILS)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_TOTAL_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_TOTAL_PRICE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DATE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DATE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_PRICE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DISCOUNT)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_COLLECTION, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_COLLECTION)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DUE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DUE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ID)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_NAME)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_OWNER, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_OWNER)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_ADDRESS)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LATITUDE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LATITUDE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LONGITUDE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_LONGITUDE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_RETAILER_PHONE)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_ID)));
            orderDeliveryMap.put(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_NAME, res.getString(res.getColumnIndex(COLUMN_ORDER_DELIVERY_ORDER_DISTRIBUTOR_NAME)));


            orderDeliveryList.add(orderDeliveryMap);

            res.moveToNext();
        }

        return orderDeliveryList;
    }


    public boolean deleteAllOrderDetails() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ORDER_DELIVERY);
        return true;

    }


    // *************** order history


    // insert order history
    public boolean insertOrder(String orderId, String orderDate, String orderTime, String orderStatus,
                               String orderTotal, String orderDiscount, String grandTotal, String deliveryDate, String srId,
                               String retailerId, String retailerName, String retailerAddress, String retailerPhone, String distributor)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SALES_ORDER_ID, orderId);
        contentValues.put(COLUMN_SALES_ORDER_DATE, orderDate);
        contentValues.put(COLUMN_SALES_ORDER_TIME, orderTime);
        contentValues.put(COLUMN_SALES_ORDER_STATUS, orderStatus);
        contentValues.put(COLUMN_SALES_ORDER_TOTAL, orderTotal);
        contentValues.put(COLUMN_SALES_ORDER_DISCOUNT, orderDiscount);
        contentValues.put(COLUMN_SALES_ORDER_GRAND_TOTAL, grandTotal);
        contentValues.put(COLUMN_SALES_ORDER_DELIVERY_DATE, deliveryDate);
        contentValues.put(COLUMN_SALES_ORDER_SR_ID, orderTime);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_ID, retailerId);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_PHONE, retailerPhone);
        contentValues.put(COLUMN_SALES_ORDER_DISTRIBUTOR, distributor);
        contentValues.put(COLUMN_SALES_ORDER_SR, srId);

        db.insert(TABLE_SALES_ORDER, null, contentValues);

        return true;
    }


    // update order data


    public boolean updateOrder(String orderId, String orderDate, String orderTime, String orderStatus,
                               String orderTotal, String orderDiscount, String grandTotal, String deliveryDate, String srId,
                               String retailerId, String retailerName, String retailerAddress, String retailerPhone, String distributor)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // contentValues.put(COLUMN_SALES_ORDER_ID, orderId);
        contentValues.put(COLUMN_SALES_ORDER_DATE, orderDate);
        contentValues.put(COLUMN_SALES_ORDER_TIME, orderTime);
        contentValues.put(COLUMN_SALES_ORDER_STATUS, orderStatus);
        contentValues.put(COLUMN_SALES_ORDER_TOTAL, orderTotal);
        contentValues.put(COLUMN_SALES_ORDER_DISCOUNT, orderDiscount);
        contentValues.put(COLUMN_SALES_ORDER_GRAND_TOTAL, grandTotal);
        contentValues.put(COLUMN_SALES_ORDER_DELIVERY_DATE, deliveryDate);
        contentValues.put(COLUMN_SALES_ORDER_SR_ID, orderTime);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_ID, retailerId);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_SALES_ORDER_RETAILER_PHONE, retailerPhone);
        contentValues.put(COLUMN_SALES_ORDER_DISTRIBUTOR, distributor);
        contentValues.put(COLUMN_SALES_ORDER_SR, srId);

        db.update(TABLE_SALES_ORDER, contentValues, COLUMN_SALES_ORDER_ID + " = ?", new String[]{orderId});

        return true;
    }

    // get Order History
    public ArrayList<HashMap<String, String>> getOrderData() {


        ArrayList<HashMap<String, String>> orderHistoryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SALES_ORDER, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> orderMap = new HashMap<String, String>();

            orderMap.put(COLUMN_SALES_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_ID)));
            orderMap.put(COLUMN_SALES_ORDER_DATE, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DATE)));
            orderMap.put(COLUMN_SALES_ORDER_TIME, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_TIME)));
            orderMap.put(COLUMN_SALES_ORDER_STATUS, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_STATUS)));
            orderMap.put(COLUMN_SALES_ORDER_TOTAL, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_TOTAL)));
            orderMap.put(COLUMN_SALES_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DISCOUNT)));
            orderMap.put(COLUMN_SALES_ORDER_GRAND_TOTAL, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_GRAND_TOTAL)));
            orderMap.put(COLUMN_SALES_ORDER_DELIVERY_DATE, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DELIVERY_DATE)));
            orderMap.put(COLUMN_SALES_ORDER_SR_ID, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_SR_ID)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_ID)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_NAME)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_ADDRESS)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_PHONE)));
            orderMap.put(COLUMN_SALES_ORDER_DISTRIBUTOR, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DISTRIBUTOR)));
            orderMap.put(COLUMN_SALES_ORDER_SR, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_SR)));

            orderHistoryList.add(orderMap);

            res.moveToNext();
        }

        return orderHistoryList;
    }


    // get order data with order id
    // get Order History
    public ArrayList<HashMap<String, String>> getOrderInfo(String orderid) {


        ArrayList<HashMap<String, String>> orderHistoryList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_SALES_ORDER + " WHERE " + COLUMN_SALES_ORDER_ID + " =?", new String[]{orderid});

        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> orderMap = new HashMap<String, String>();

            orderMap.put(COLUMN_SALES_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_ID)));
            orderMap.put(COLUMN_SALES_ORDER_DATE, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DATE)));
            orderMap.put(COLUMN_SALES_ORDER_TIME, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_TIME)));
            orderMap.put(COLUMN_SALES_ORDER_STATUS, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_STATUS)));
            orderMap.put(COLUMN_SALES_ORDER_TOTAL, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_TOTAL)));
            orderMap.put(COLUMN_SALES_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DISCOUNT)));
            orderMap.put(COLUMN_SALES_ORDER_GRAND_TOTAL, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_GRAND_TOTAL)));
            orderMap.put(COLUMN_SALES_ORDER_DELIVERY_DATE, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DELIVERY_DATE)));
            orderMap.put(COLUMN_SALES_ORDER_SR_ID, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_SR_ID)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_ID)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_NAME)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_ADDRESS)));
            orderMap.put(COLUMN_SALES_ORDER_RETAILER_PHONE, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_RETAILER_PHONE)));
            orderMap.put(COLUMN_SALES_ORDER_DISTRIBUTOR, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_DISTRIBUTOR)));
            orderMap.put(COLUMN_SALES_ORDER_SR, res.getString(res.getColumnIndex(COLUMN_SALES_ORDER_SR)));

            orderHistoryList.add(orderMap);

            res.moveToNext();
        }

        return orderHistoryList;
    }

    // delete order
    public boolean deleteAllOrder() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_SALES_ORDER);
        return true;

    }


    // insert order_product

    public boolean insertOrderProduct(String orderId, String productId, String productTitle, String quantity,
                                      String price)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORDER_ID, orderId);
        contentValues.put(COLUMN_ORDER_PRODUCT_ID, productId);
        contentValues.put(COLUMN_ORDER_PRODUCT_TITLE, productTitle);
        contentValues.put(COLUMN_ORDER_PRODUCT_QUANTITY, quantity);
        contentValues.put(COLUMN_ORDER_PRICE, price);
        Log.v("_sf", "insert  product total price: " + price);

        db.insert(TABLE_ORDER_PRODUCT, null, contentValues);

        return true;
    }


    public boolean insertOrderPerProduct(String productId, String productTitle, String quantity,
                                         String price, String discount)

    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ORDER_PRODUCT_ID, productId);
        contentValues.put(COLUMN_ORDER_PRODUCT_TITLE, productTitle);
        contentValues.put(COLUMN_ORDER_PRODUCT_QUANTITY, quantity);
        contentValues.put(COLUMN_ORDER_DISCOUNT, discount);
        contentValues.put(COLUMN_ORDER_PRICE, price);

        db.insert(TABLE_PER_PRODUCT, null, contentValues);
        Log.d("workforce", "insert  product total price: " + contentValues);


        return true;
    }



    // get product with order id


    public ArrayList<HashMap<String, String>> getOrderProductList(String orderId) {

        ArrayList<HashMap<String, String>> orderProductList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ORDER_PRODUCT + " WHERE " + COLUMN_ORDER_ID + " =?", new String[]{orderId});
        res.moveToFirst();

        while (!res.isAfterLast()) {

            HashMap<String, String> orderProductMap = new HashMap<String, String>();
            orderProductMap.put(COLUMN_ORDER_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_ID)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_ID)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_TITLE, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_TITLE)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_QUANTITY, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_QUANTITY)));

            Log.v("_sf", "get product total price: " + res.getString(res.getColumnIndex(COLUMN_ORDER_PRICE)));
            orderProductMap.put(COLUMN_ORDER_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_PRICE)));
            orderProductList.add(orderProductMap);
            res.moveToNext();
        }
        return orderProductList;
    }

    public ArrayList<HashMap<String, String>> getAllPerOrder() {

        ArrayList<HashMap<String, String>> orderProductList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PER_PRODUCT, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> orderProductMap = new HashMap<String, String>();
            orderProductMap.put(COLUMN_ORDER_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_ID)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_TITLE, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_TITLE)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_QUANTITY, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_QUANTITY)));
            orderProductMap.put(COLUMN_ORDER_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_PRICE)));
            orderProductMap.put(COLUMN_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_ORDER_DISCOUNT)));

            orderProductList.add(orderProductMap);
            res.moveToNext();
        }
        return orderProductList;
    }

    public boolean productExist(String productId) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PER_PRODUCT + " WHERE " + COLUMN_ORDER_PRODUCT_ID + " =?", new String[]{productId});
        int no = res.getCount();
        if( no > 0) {
            return true;
        }
        else{
            return false;
        }
    }

    public HashMap<String, String> getPerOrderById(String id) {

        HashMap<String, String> orderProductMap = new HashMap<String, String>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PER_PRODUCT + " WHERE " + COLUMN_ORDER_PRODUCT_ID + " =?", new String[]{id});
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            orderProductMap.put(COLUMN_ORDER_PRODUCT_ID, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_ID)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_TITLE, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_TITLE)));
            orderProductMap.put(COLUMN_ORDER_PRODUCT_QUANTITY, res.getString(res.getColumnIndex(COLUMN_ORDER_PRODUCT_QUANTITY)));
            orderProductMap.put(COLUMN_ORDER_PRICE, res.getString(res.getColumnIndex(COLUMN_ORDER_PRICE)));
            orderProductMap.put(COLUMN_ORDER_DISCOUNT, res.getString(res.getColumnIndex(COLUMN_ORDER_DISCOUNT)));
            res.moveToNext();
        }
        return orderProductMap;
    }

    public void updatePerOrder(String quantity, String price, String id, String discount){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_PER_PRODUCT+ " SET "+ COLUMN_ORDER_PRODUCT_QUANTITY+"= "+quantity + " WHERE " + COLUMN_ORDER_PRODUCT_ID + " =?", new String[]{id});
        db.execSQL("UPDATE " + TABLE_PER_PRODUCT+ " SET "+ COLUMN_ORDER_PRICE+"= "+price +  " WHERE " + COLUMN_ORDER_PRODUCT_ID + " =?", new String[]{id});
        db.execSQL("UPDATE " + TABLE_PER_PRODUCT+ " SET "+ COLUMN_ORDER_DISCOUNT+"= "+discount +  " WHERE " + COLUMN_ORDER_PRODUCT_ID + " =?", new String[]{id});

    }


    // delete order_product with order_id
    public boolean deleteSingleOrderProducts(String orderId) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PER_PRODUCT, COLUMN_ORDER_ID + " = ?", new String[]{orderId});
        return true;

    }
    public boolean deleteSingleOrderPerProducts(String productId) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PER_PRODUCT, COLUMN_ORDER_PRODUCT_ID + " = ?", new String[]{productId});
        return true;

    }
    public String getNewTotal() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_PER_PRODUCT,null);
        res.moveToFirst();
        double total = 0;
        while (!res.isAfterLast()) {

            total += Double.parseDouble(res.getString(res.getColumnIndex(COLUMN_ORDER_PRICE)));
            res.moveToNext();
        }

        return String.valueOf(total);

    }


    // delete order_product
    public boolean deleteAllOrderProduct() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ORDER_PRODUCT);
        return true;

    }

    public boolean deleteAllPerProduct() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_PER_PRODUCT);
        return true;

    }


    // insert contacts
    public boolean insertContact( String contactsTitle, String contactsNumber, String contactsAddress, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CONTACTS_TITLE, contactsTitle);
        contentValues.put(COLUMN_CONTACTS_NUMBER, contactsNumber);
        contentValues.put(COLUMN_CONTACTS_ADDRESS, contactsAddress);
        contentValues.put(COLUMN_CONTACTS_TYPE, type);
        db.insert(TABLE_CONTACTS, null, contentValues);

        return true;
    }

    // get contacts numbers
    public ArrayList<HashMap<String, String>> getContactsList() {


        ArrayList<HashMap<String, String>> contactsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CONTACTS, null);
        res.moveToFirst();
        Log.d("workforce", "getContactsList: "+res.getCount());

        while (res.isAfterLast() == false) {

            HashMap<String, String> contactsMap = new HashMap<String, String>();

            contactsMap.put(COLUMN_CONTACTS_TITLE, res.getString(res.getColumnIndex(COLUMN_CONTACTS_TITLE)));
            contactsMap.put(COLUMN_CONTACTS_NUMBER, res.getString(res.getColumnIndex(COLUMN_CONTACTS_NUMBER)));
            contactsMap.put(COLUMN_CONTACTS_ADDRESS, res.getString(res.getColumnIndex(COLUMN_CONTACTS_ADDRESS)));
            contactsMap.put(COLUMN_CONTACTS_TYPE, res.getString(res.getColumnIndex(COLUMN_CONTACTS_TYPE)));
            contactsList.add(contactsMap);
            Log.d("workforce", "getContactsList: "+contactsMap);

            res.moveToNext();
        }

        return contactsList;
    }

    public ArrayList<HashMap<String, String>> getAllContact() {

        ArrayList<HashMap<String, String>> contactsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_CONTACTS, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> contactMap = new HashMap<String, String>();
            contactMap.put(COLUMN_CONTACTS_TITLE, res.getString(res.getColumnIndex(COLUMN_CONTACTS_TITLE)));
            contactMap.put(COLUMN_CONTACTS_NUMBER, res.getString(res.getColumnIndex(COLUMN_CONTACTS_NUMBER)));
            contactMap.put(COLUMN_CONTACTS_ADDRESS, res.getString(res.getColumnIndex(COLUMN_CONTACTS_ADDRESS)));
            contactMap.put(COLUMN_CONTACTS_TYPE, res.getString(res.getColumnIndex(COLUMN_CONTACTS_TYPE)));


            contactsList.add(contactMap);
            res.moveToNext();
        }
        return contactsList;
    }

    // delete all contacts
    public boolean deleteAllContacts() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_CONTACTS);
        return true;

    }


    // insert route_retailer
    public boolean insertRouteRetailer(String routeRetailerId, String routeRetailerName, String routeRetailerOwner,
                                       String routeRetailerAddress, String routeRetailerLatitude, String routeRetailerLongitude, String routeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ROUTE_RETAIL_ID, routeRetailerId);
        contentValues.put(COLUMN_ROUTE_RETAIL_NAME, routeRetailerName);
        contentValues.put(COLUMN_ROUTE_RETAIL_OWNER, routeRetailerOwner);
        contentValues.put(COLUMN_ROUTE_RETAIL_ADDRESS, routeRetailerAddress);
        contentValues.put(COLUMN_ROUTE_RETAIL_LATITUDE, routeRetailerLatitude);
        contentValues.put(COLUMN_ROUTE_RETAIL_LONGITUDE, routeRetailerLongitude);
        contentValues.put(COLUMN_ROUTE_NAME, routeName);
        db.insert(TABLE_ROUTE_RETAILER, null, contentValues);

        return true;
    }

    // delete all contacts
    public boolean deleteAllRouteRetailer() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ROUTE_RETAILER);
        return true;

    }

    // insert day_route
    public boolean insertDayRoute(String dayRouteId, String dayRouteDay, String dayRouteRoute,
                                  String territory, String employeeId, String area, String region) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_DAY_ROUTE_ID, dayRouteId);
        contentValues.put(COLUMN_DAY_ROUTE_DAY, dayRouteDay);
        contentValues.put(COLUMN_DAY_ROUTE_ROUTE, dayRouteRoute);
        contentValues.put(COLUMN_DAY_ROUTE_TERRITORY, territory);
        contentValues.put(COLUMN_DAY_ROUTE_EMPLOYEE_ID, employeeId);
        contentValues.put(COLUMN_DAY_ROUTE_AREA, area);
        contentValues.put(COLUMN_DAY_ROUTE_REGION, region);

        db.insert(TABLE_DAY_ROUTE, null, contentValues);

        return true;
    }

    public boolean deleteAllDayRoute() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_DAY_ROUTE);
        return true;

    }

    // get contacts numbers
    public ArrayList<HashMap<String, String>> getRouteDayList() {


        ArrayList<HashMap<String, String>> routeDayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_DAY_ROUTE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> routeDayMap = new HashMap<String, String>();

            routeDayMap.put(COLUMN_DAY_ROUTE_DAY, res.getString(res.getColumnIndex(COLUMN_DAY_ROUTE_DAY)));
            routeDayList.add(routeDayMap);

            res.moveToNext();
        }

        return routeDayList;
    }


    // get contacts numbers
    public ArrayList<HashMap<String, String>> getRouteList(String day) {

        Log.d("getRouteList", "getRouteList: "+day);
        String dayName = day;
        ArrayList<HashMap<String, String>> routeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        //    table_day_route.day_route_day,table_day_route.day_route_name,table_route_retailer.route_retailer_id,table_route_retailer.route_retailer_name,table_route_retailer.route_retailer_owner,table_route_retailer.route_retailer_address
        String query = "select table_route_retailer.route_retailer_name,table_day_route.day_route_name,table_route_retailer.route_retailer_id,table_day_route.day_route_day,table_route_retailer.route_retailer_owner,table_route_retailer.route_retailer_address"
                + " from table_day_route , table_route_retailer " +
                "where table_day_route.day_route_name = table_route_retailer.route_retailer_route_name and table_day_route.day_route_day = " + "'" + dayName + "'" + " group by table_route_retailer.route_retailer_name";
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> routeMap = new HashMap<String, String>();

            // day, route,retiler id,name ,owner, address
            Log.v("_sf %%%%%", "query values " + "  " + routeList.size());

            routeMap.put(COLUMN_DAY_ROUTE_DAY, res.getString(res.getColumnIndex(COLUMN_DAY_ROUTE_DAY)));
            routeMap.put(COLUMN_DAY_ROUTE_ROUTE, res.getString(res.getColumnIndex(COLUMN_DAY_ROUTE_ROUTE)));
            routeMap.put(COLUMN_ROUTE_RETAIL_ID, res.getString(res.getColumnIndex(COLUMN_ROUTE_RETAIL_ID)));
            routeMap.put(COLUMN_ROUTE_RETAIL_NAME, res.getString(res.getColumnIndex(COLUMN_ROUTE_RETAIL_NAME)));
            routeMap.put(COLUMN_ROUTE_RETAIL_OWNER, res.getString(res.getColumnIndex(COLUMN_ROUTE_RETAIL_OWNER)));
            routeMap.put(COLUMN_ROUTE_RETAIL_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ROUTE_RETAIL_ADDRESS)));
            routeList.add(routeMap);


            Log.e("_sf %%%%%", DatabaseUtils.dumpCurrentRowToString(res));
            res.moveToNext();
        }


        //  Log.v("_sf %%%%%"," "+dayName+"  "+routeList.size());

        return routeList;
    }


    public boolean getAlltable_day_route() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM table_day_route", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Log.e("_sf %%%%%", DatabaseUtils.dumpCurrentRowToString(res));
            res.moveToNext();

        }

        return true;
    }
    public boolean getAlltable_route_retailer() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM table_day_route", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Log.e("_sf %%%%%", DatabaseUtils.dumpCurrentRowToString(res));
            res.moveToNext();

        }

        return true;
    }

    // insert route visit data on daily basis

    public boolean insertRouteVisit(String retailerId, String retailerName, String retailerOwner,
                                    String retailerAddress, String visitLatitude, String visitLongitude, String visitDate, String visitStatus, String routeDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ROUTE_VISIT_RETAILER_ID, retailerId);
        contentValues.put(COLUMN_ROUTE_VISIT_RETAILER_NAME, retailerName);
        contentValues.put(COLUMN_ROUTE_VISIT_RETAILER_OWNER, retailerOwner);
        contentValues.put(COLUMN_ROUTE_VISIT_RETAILER_ADDRESS, retailerAddress);
        contentValues.put(COLUMN_ROUTE_VISIT_LATITUDE, visitLatitude);
        contentValues.put(COLUMN_ROUTE_VISIT_LONGITUDE, visitLongitude);
        contentValues.put(COLUMN_ROUTE_VISIT_DATE, visitDate);
        contentValues.put(COLUMN_ROUTE_VISIT_STATUS, visitStatus);
        contentValues.put(COLUMN_ROUTE_VISIT_ROUTE_DATE, routeDate);


        db.insert(TABLE_ROUTE_VISIT, null, contentValues);

        return true;
    }

    public boolean insertRouteVisits(String dayName, String routeName, String marketName, String marketAddress, String retailerId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ROUTE_VISIT_RETAILER_ID, retailerId);
        contentValues.put(COLUMN_ROUTE_VISIT_NAME, routeName);
        contentValues.put(COLUMN_ROUTE_VISIT_MARKET_NAME, marketName);
        contentValues.put(COLUMN_ROUTE_VISIT_MARKET_ADDRESS, marketAddress);
        contentValues.put(COLUMN_ROUTE_VISIT_DAY, dayName);
        contentValues.put(COLUMN_ROUTE_VISIT_STATUS, status);


        db.insert(TABLE_ROUTE_VISITS, null, contentValues);

        return true;
    }




    // get route list
    public ArrayList<HashMap<String, String>> getRouteVisitList() {


        ArrayList<HashMap<String, String>> routeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ROUTE_VISIT, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> routeMap = new HashMap<String, String>();

            routeMap.put(COLUMN_ROUTE_VISIT_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_RETAILER_ID)));
            routeMap.put(COLUMN_ROUTE_VISIT_RETAILER_NAME, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_RETAILER_NAME)));
            routeMap.put(COLUMN_ROUTE_VISIT_RETAILER_OWNER, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_RETAILER_OWNER)));
            routeMap.put(COLUMN_ROUTE_VISIT_RETAILER_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_RETAILER_ADDRESS)));
            routeMap.put(COLUMN_ROUTE_VISIT_LATITUDE, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_LATITUDE)));
            routeMap.put(COLUMN_ROUTE_VISIT_LONGITUDE, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_LONGITUDE)));
            routeMap.put(COLUMN_ROUTE_VISIT_DATE, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_DATE)));
            routeMap.put(COLUMN_ROUTE_VISIT_STATUS, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_STATUS)));
            routeMap.put(COLUMN_ROUTE_VISIT_ROUTE_DATE, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_ROUTE_DATE)));


            routeList.add(routeMap);

            res.moveToNext();
        }

        return routeList;
    }

    // get route list
    public ArrayList<HashMap<String, String>> getAllRouteVisitLists() {


        ArrayList<HashMap<String, String>> routeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ROUTE_VISITS  + " order by "+ COLUMN_ROUTE_VISIT_DAY+", "+COLUMN_ROUTE_VISIT_MARKET_NAME,null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> routeMap = new HashMap<String, String>();
            routeMap.put(COLUMN_ROUTE_VISIT_DAY, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_DAY)));
            routeMap.put(COLUMN_ROUTE_VISIT_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_RETAILER_ID)));
            routeMap.put(COLUMN_ROUTE_VISIT_NAME, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_NAME)));
            routeMap.put(COLUMN_ROUTE_VISIT_MARKET_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_MARKET_ADDRESS)));
            routeMap.put(COLUMN_ROUTE_VISIT_MARKET_NAME, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_MARKET_NAME)));
            routeMap.put(COLUMN_ROUTE_VISIT_STATUS, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_STATUS)));


            routeList.add(routeMap);

            res.moveToNext();
        }

        return routeList;
    }

    public void UpdateStatusRouteVisit(String retailerId){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS, "Visited");


        db.update(TABLE_ROUTE_VISITS, contentValues, COLUMN_ROUTE_VISIT_RETAILER_ID + " = ?", new String[]{retailerId});


    }

    // get route list
    public ArrayList<HashMap<String, String>> getAllRouteVisitListByDay(String dayName) {


        ArrayList<HashMap<String, String>> routeList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ROUTE_VISITS  + " WHERE " + COLUMN_ROUTE_VISIT_DAY + " =? order by "+ COLUMN_ROUTE_VISIT_MARKET_NAME, new String[]{dayName});
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> routeMap = new HashMap<String, String>();

            routeMap.put(COLUMN_ROUTE_VISIT_RETAILER_ID, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_RETAILER_ID)));
            routeMap.put(COLUMN_ROUTE_VISIT_NAME, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_NAME)));
            routeMap.put(COLUMN_ROUTE_VISIT_MARKET_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_MARKET_ADDRESS)));
            routeMap.put(COLUMN_ROUTE_VISIT_MARKET_NAME, res.getString(res.getColumnIndex(COLUMN_ROUTE_VISIT_MARKET_NAME)));

            routeList.add(routeMap);

            res.moveToNext();
        }

        return routeList;
    }



    // delete all contacts
    public boolean deleteAllRouteVisit() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ROUTE_VISITS);
        return true;

    }

    // delete all contacts
    public boolean deleteAllRouteVisitTable() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ROUTE_VISITS);
        return true;

    }

    public boolean updateRouteVisit(String retailerId, String latitude, String longitude, String visitDate, String visitStatus) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ROUTE_VISIT_LATITUDE, latitude);
        contentValues.put(COLUMN_ROUTE_VISIT_LONGITUDE, longitude);
        contentValues.put(COLUMN_ROUTE_VISIT_DATE, visitDate);
        contentValues.put(COLUMN_ROUTE_VISIT_STATUS, visitStatus);

        db.update(TABLE_ROUTE_VISIT, contentValues, COLUMN_ROUTE_VISIT_RETAILER_ID + " = ?", new String[]{retailerId});

        return true;

    }


    public boolean insertAttendance(String attendanceId, String attendanceDate, String inTime,
                                    String inAddress, String outTime, String outAddress, String status, String sendStatus, String comment, String inLatLon, String outLatLon) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        contentValues.put(COLUMN_ATTENDANCE_SENDING_STATUS, sendStatus);
        contentValues.put(COLUMN_ATTENDANCE_NOTES, comment);
        contentValues.put(COLUMN_ATTENDANCE_IN_LAT_LON, inLatLon);
        contentValues.put(COLUMN_ATTENDANCE_OUT_LAT_LON, outLatLon);

        db.insert(TABLE_ATTENDANCE, null, contentValues);

        return true;
    }


    // get attendance
    public ArrayList<HashMap<String, String>> getAttendanceList() {


        ArrayList<HashMap<String, String>> attendanceList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_ATTENDANCE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> attendanceMap = new HashMap<String, String>();

            attendanceMap.put(COLUMN_ATTENDANCE_ID, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_ID)));
            attendanceMap.put(COLUMN_ATTENDANCE_DATE, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_DATE)));
            attendanceMap.put(COLUMN_ATTENDANCE_IN_TIME, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_IN_TIME)));
            attendanceMap.put(COLUMN_ATTENDANCE_IN_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_IN_ADDRESS)));
            attendanceMap.put(COLUMN_ATTENDANCE_OUT_TIME, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_OUT_TIME)));
            attendanceMap.put(COLUMN_ATTENDANCE_OUT_ADDRESS, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_OUT_ADDRESS)));
            attendanceMap.put(COLUMN_ATTENDANCE_STATUS, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_STATUS)));
            attendanceMap.put(COLUMN_ATTENDANCE_SENDING_STATUS, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_SENDING_STATUS)));
            attendanceMap.put(COLUMN_ATTENDANCE_IN_LAT_LON, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_IN_LAT_LON)));
            attendanceMap.put(COLUMN_ATTENDANCE_OUT_LAT_LON, res.getString(res.getColumnIndex(COLUMN_ATTENDANCE_OUT_LAT_LON)));


            attendanceList.add(attendanceMap);

            res.moveToNext();
        }

        return attendanceList;
    }


    // update attendance inTime and inTime address

    public boolean updateAttendance(String attendanceId, String attendanceDate, String inTime,
                                    String inAddress, String outTime, String outAddress, String status, String comment, String inLatLon, String outLatLon) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Log.d("update", "updateAttendance: "+ attendanceId+ attendanceDate + inTime);
        contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        contentValues.put(COLUMN_ATTENDANCE_NOTES, comment);
        contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        contentValues.put(COLUMN_ATTENDANCE_IN_LAT_LON, inLatLon);
        contentValues.put(COLUMN_ATTENDANCE_OUT_LAT_LON, outLatLon);


        db.update(TABLE_ATTENDANCE, contentValues, COLUMN_ATTENDANCE_DATE + " = ?", new String[]{attendanceDate});

        return true;

    }


    public boolean updateInAttendance(String attendanceId, String inTime, String inAddress, String comment, String inLatLon, String outLatLon) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        //contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        contentValues.put(COLUMN_ATTENDANCE_NOTES, comment);
        // contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        // contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        // contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
//        contentValues.put(COLUMN_ATTENDANCE_SENDING_STATUS, sendStatus);
        contentValues.put(COLUMN_ATTENDANCE_IN_LAT_LON, inLatLon);
        contentValues.put(COLUMN_ATTENDANCE_OUT_LAT_LON, outLatLon);


        db.update(TABLE_ATTENDANCE, contentValues, COLUMN_ATTENDANCE_ID + " = ?", new String[]{attendanceId});

        return true;

    }


    // update attendance outtime and out time address


    public boolean updateOutAttendance(String attendanceId, String outTime, String outAddress, String sendStatus, String comment, String outLatLon) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        //contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        // contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        // contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        //  contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        contentValues.put(COLUMN_ATTENDANCE_STATUS, sendStatus);
        contentValues.put(COLUMN_ATTENDANCE_NOTES, comment);
        contentValues.put(COLUMN_ATTENDANCE_OUT_LAT_LON, outLatLon);

        db.update(TABLE_ATTENDANCE, contentValues, COLUMN_ATTENDANCE_ID + " = ?", new String[]{attendanceId});

        return true;

    }


// update attendance status

    public boolean updateAttendanceSendingStatus(String attendanceId, String sendStatus) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        //contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        // contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        // contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        // contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        // contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        //  contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        contentValues.put(COLUMN_ATTENDANCE_STATUS, sendStatus);

        db.update(TABLE_ATTENDANCE, contentValues, COLUMN_ATTENDANCE_ID + " = ?", new String[]{attendanceId});

        return true;

    }

    // update attendance
    public boolean updateAttendance(String attendanceId, String inTime, String inAddress, String outTime, String outAddress, String status, String sendStatus) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        //contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        contentValues.put(COLUMN_ATTENDANCE_SENDING_STATUS, sendStatus);

        db.update(TABLE_ATTENDANCE, contentValues, COLUMN_ATTENDANCE_ID + " = ?", new String[]{attendanceId});

        return true;

    }

    public boolean updateAttendanceComment(String comment, String attendanceId) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //contentValues.put(COLUMN_ATTENDANCE_ID, attendanceId);
        //contentValues.put(COLUMN_ATTENDANCE_DATE, attendanceDate);
        //contentValues.put(COLUMN_ATTENDANCE_IN_TIME, inTime);
        //contentValues.put(COLUMN_ATTENDANCE_IN_ADDRESS, inAddress);
        //contentValues.put(COLUMN_ATTENDANCE_OUT_TIME, outTime);
        //contentValues.put(COLUMN_ATTENDANCE_OUT_ADDRESS, outAddress);
        //contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        contentValues.put(COLUMN_ATTENDANCE_NOTES, comment);

        db.update(TABLE_ATTENDANCE, contentValues, COLUMN_ATTENDANCE_ID + " = ?", new String[]{attendanceId});

        return true;

    }


    public boolean deleteAllAttendanceData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_ATTENDANCE);
        return true;

    }


    public boolean insertExpenseData(String type, String amount, String note, String expenseBy,
                                     String status, String approved, String imageName, String date){

        //Log.d("workforce", "insertExpenseData: ");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_EXPENSE_TYPE, type);
        contentValues.put(COLUMN_EXPENSE_AMOUNT, amount);
        contentValues.put(COLUMN_EXPENSE_NOTE, note);
        contentValues.put(COLUMN_EXPENSE_BY, expenseBy);
        contentValues.put(COLUMN_EXPENSE_STATUS, status);
        contentValues.put(COLUMN_EXPENSE_APPROVED_AMOUNT, approved);
        contentValues.put(COLUMN_EXPENSE_ATTACHMENT, imageName);
        contentValues.put(COLUMN_EXPENSE_DATE, date);


        db.insert(TABLE_EXPENSE, null,contentValues);

        return true;
    }

    public ArrayList<HashMap<String, String>> getExpanseData(){

        ArrayList<HashMap<String, String>> expanseList = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_EXPENSE, null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            HashMap<String, String> expense = new HashMap<String, String>();

            expense.put(COLUMN_EXPENSE_ATTACHMENT, res.getString(res.getColumnIndex(COLUMN_EXPENSE_ATTACHMENT)));
            expense.put(COLUMN_EXPENSE_APPROVED_AMOUNT, res.getString(res.getColumnIndex(COLUMN_EXPENSE_APPROVED_AMOUNT)));
            expense.put(COLUMN_EXPENSE_STATUS, res.getString(res.getColumnIndex(COLUMN_EXPENSE_STATUS)));
            expense.put(COLUMN_EXPENSE_BY, res.getString(res.getColumnIndex(COLUMN_EXPENSE_BY)));
            expense.put(COLUMN_EXPENSE_NOTE, res.getString(res.getColumnIndex(COLUMN_EXPENSE_NOTE)));
            expense.put(COLUMN_EXPENSE_AMOUNT, res.getString(res.getColumnIndex(COLUMN_EXPENSE_AMOUNT)));
            expense.put(COLUMN_EXPENSE_TYPE, res.getString(res.getColumnIndex(COLUMN_EXPENSE_TYPE)));
            expense.put(COLUMN_EXPENSE_DATE, res.getString(res.getColumnIndex(COLUMN_EXPENSE_DATE)));

            expanseList.add(expense);

            res.moveToNext();
        }


        return expanseList;
    }

    public boolean deleteAllExpenseData() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_EXPENSE);
        return true;

    }

    public boolean insertMessageData(String notice_id, String notice_title,String notice_type, String notice_description,
                                     String fileUrl, String fileType, String fileSize, String date, String priority, String isDownload) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NOTICE_ID, notice_id);
        contentValues.put(COLUMN_NOTICE_TITLE, notice_title);
        contentValues.put(COLUMN_NOTICE_TYPE, notice_type);
        contentValues.put(COLUMN_NOTICE_DESCRIPTION, notice_description);
//        contentValues.put(COLUMN_NOTICE_IMAGE_URL, imageUrl);
        contentValues.put(COLUMN_NOTICE_FILE_URL, fileUrl);
        contentValues.put(COLUMN_NOTICE_FILE_TYPE, fileType);
        contentValues.put(COLUMN_NOTICE_FILE_SIZE, fileSize);
        contentValues.put(COLUMN_NOTICE_DATE, date);
        contentValues.put(COLUMN_NOTICE_PRIORITY, priority);
        contentValues.put(COLUMN_IS_FILE_DOWNLOAD, isDownload);
        db.insert(TABLE_NOTICE, null, contentValues);
        return true;
    }


    // get all message from the list
    public ArrayList<NoticeModel> getAllMessageDataByType(String noticeType) {

        ArrayList<NoticeModel> noticeModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NOTICE +" where "+COLUMN_NOTICE_TYPE+" = '"+noticeType+"'", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setNotice_id(res.getString(res.getColumnIndex(COLUMN_NOTICE_ID)));
            noticeModel.setNotice_type(res.getString(res.getColumnIndex(COLUMN_NOTICE_TYPE)));
            noticeModel.setNotice_title(res.getString(res.getColumnIndex(COLUMN_NOTICE_TITLE)));
            noticeModel.setNotice_description(res.getString(res.getColumnIndex(COLUMN_NOTICE_DESCRIPTION)));
            noticeModel.setFileUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_URL)));
            noticeModel.setFileType(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_TYPE)));
            noticeModel.setFileSize(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_SIZE)));
            noticeModel.setIsDownload(res.getString(res.getColumnIndex(COLUMN_IS_FILE_DOWNLOAD)));
//            noticeModel.setImageUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_IMAGE_URL)));
            noticeModel.setDateTime(res.getString(res.getColumnIndex(COLUMN_NOTICE_DATE)));
            noticeModel.setPriority(res.getString(res.getColumnIndex(COLUMN_NOTICE_PRIORITY)));
            noticeModelArrayList.add(noticeModel);
            res.moveToNext();
        }
        return noticeModelArrayList;
    }

    // get all message from the list
    public NoticeModel getSingleFileData(String fileUrl) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NOTICE +" where "+COLUMN_NOTICE_FILE_URL+" = '"+fileUrl+"'", null);
        res.moveToFirst();

        NoticeModel noticeModel = new NoticeModel();
         if(res.moveToFirst()) {

             noticeModel.setNotice_id(res.getString(res.getColumnIndex(COLUMN_NOTICE_ID)));
             noticeModel.setNotice_type(res.getString(res.getColumnIndex(COLUMN_NOTICE_TYPE)));
             noticeModel.setNotice_title(res.getString(res.getColumnIndex(COLUMN_NOTICE_TITLE)));

             noticeModel.setNotice_description(res.getString(res.getColumnIndex(COLUMN_NOTICE_DESCRIPTION)));
             noticeModel.setFileUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_URL)));
             noticeModel.setFileType(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_TYPE)));

             noticeModel.setFileSize(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_SIZE)));
             noticeModel.setIsDownload(res.getString(res.getColumnIndex(COLUMN_IS_FILE_DOWNLOAD)));
//             noticeModel.setImageUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_IMAGE_URL)));

             noticeModel.setDateTime(res.getString(res.getColumnIndex(COLUMN_NOTICE_DATE)));
             noticeModel.setPriority(res.getString(res.getColumnIndex(COLUMN_NOTICE_PRIORITY)));
        }
        return noticeModel;
    }

    public ArrayList<NoticeModel> getAllFileData(String userId) {

        ArrayList<NoticeModel> noticeModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NOTICE +" where "+COLUMN_NOTICE_FILE_URL+" not null", null);
        res.moveToFirst();

        while (!res.isAfterLast()) {
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setNotice_id(res.getString(res.getColumnIndex(COLUMN_NOTICE_ID)));
            noticeModel.setNotice_type(res.getString(res.getColumnIndex(COLUMN_NOTICE_TYPE)));
            noticeModel.setNotice_title(res.getString(res.getColumnIndex(COLUMN_NOTICE_TITLE)));

            noticeModel.setNotice_description(res.getString(res.getColumnIndex(COLUMN_NOTICE_DESCRIPTION)));
            noticeModel.setFileUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_URL)));
            noticeModel.setFileType(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_TYPE)));

            noticeModel.setFileSize(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_SIZE)));
            noticeModel.setIsDownload(res.getString(res.getColumnIndex(COLUMN_IS_FILE_DOWNLOAD)));
//            noticeModel.setImageUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_IMAGE_URL)));

            noticeModel.setDateTime(res.getString(res.getColumnIndex(COLUMN_NOTICE_DATE)));
            noticeModel.setPriority(res.getString(res.getColumnIndex(COLUMN_NOTICE_PRIORITY)));
            noticeModelArrayList.add(noticeModel);
            res.moveToNext();
        }
        return noticeModelArrayList;
    }

    public NoticeModel getSingleImageData(String imgUrl) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NOTICE  +" where "+COLUMN_NOTICE_IMAGE_URL+" ='"+imgUrl+"'", null);
        res.moveToFirst();

        NoticeModel noticeModel = new NoticeModel();
        if(res.moveToFirst()) {

            noticeModel.setNotice_id(res.getString(res.getColumnIndex(COLUMN_NOTICE_ID)));
            noticeModel.setNotice_type(res.getString(res.getColumnIndex(COLUMN_NOTICE_TYPE)));
            noticeModel.setNotice_title(res.getString(res.getColumnIndex(COLUMN_NOTICE_TITLE)));

            noticeModel.setNotice_description(res.getString(res.getColumnIndex(COLUMN_NOTICE_DESCRIPTION)));
            noticeModel.setFileUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_URL)));
            noticeModel.setFileType(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_TYPE)));

            noticeModel.setFileSize(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_SIZE)));
            noticeModel.setIsDownload(res.getString(res.getColumnIndex(COLUMN_IS_FILE_DOWNLOAD)));
//            noticeModel.setImageUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_IMAGE_URL)));

            noticeModel.setDateTime(res.getString(res.getColumnIndex(COLUMN_NOTICE_DATE)));
            noticeModel.setPriority(res.getString(res.getColumnIndex(COLUMN_NOTICE_PRIORITY)));
        }
        return noticeModel;
    }

    public ArrayList<NoticeModel> getAllNotification() {
        ArrayList<NoticeModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NOTICE, null);
        res.moveToFirst();


        while (!res.isAfterLast()) {
            NoticeModel noticeModel = new NoticeModel();
            noticeModel.setNotice_id(res.getString(res.getColumnIndex(COLUMN_NOTICE_ID)));
            noticeModel.setNotice_type(res.getString(res.getColumnIndex(COLUMN_NOTICE_TYPE)));
            noticeModel.setNotice_title(res.getString(res.getColumnIndex(COLUMN_NOTICE_TITLE)));

            noticeModel.setNotice_description(res.getString(res.getColumnIndex(COLUMN_NOTICE_DESCRIPTION)));
            noticeModel.setFileUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_URL)));
            noticeModel.setFileType(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_TYPE)));

            noticeModel.setFileSize(res.getString(res.getColumnIndex(COLUMN_NOTICE_FILE_SIZE)));
            noticeModel.setIsDownload(res.getString(res.getColumnIndex(COLUMN_IS_FILE_DOWNLOAD)));
//            noticeModel.setImageUrl(res.getString(res.getColumnIndex(COLUMN_NOTICE_IMAGE_URL)));

            noticeModel.setDateTime(res.getString(res.getColumnIndex(COLUMN_NOTICE_DATE)));
            noticeModel.setPriority(res.getString(res.getColumnIndex(COLUMN_NOTICE_PRIORITY)));
            arrayList.add(noticeModel);
            res.moveToNext();
        }
        return arrayList;
    }

}
