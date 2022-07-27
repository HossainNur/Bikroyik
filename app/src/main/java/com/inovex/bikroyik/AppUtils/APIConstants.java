package com.inovex.bikroyik.AppUtils;

/**
 * Created by DELL on 8/9/2018.
 */

public class APIConstants {
    //http://43.224.110.67:8080
   // public static final String SIGN_IN_API = "http://192.168.15.150:8080/api/employee";
   // public static final String SIGN_IN_API = "http://192.168.43.79:8080/api/employee";

    public static final String BASE_URL_PREF_KEY = "server_url";
    public static final String SIGN_IN_API = "http://43.224.110.67:8080/workforce/api/employee";

    public static final String SIGN_IN_API_KEY_EMPLOYEE_ID = "employeeId";
    public static final String SIGN_IN_API_KEY_EMPLOYEE_PASSWORD = "password";

    // sign in api employee json object keys
    public static final String EMPLOYEE_JSON_KEY_ID = "employeeId";
    public static final String EMPLOYEE_JSON_KEY_NAME = "empName";

    public static final String EMPLOYEE_JSON_KEY_ADDRESS = "empAddress";

    public static final String EMPLOYEE_JSON_KEY_PHONE = "empPhone";

    public static final String EMPLOYEE_JSON_KEY_CATEGORY = "empCategory";

    public static final String EMPLOYEE_JSON_KEY_REPORTING_ID = "reportingId";
    public static final String EMPLOYEE_JSON_KEY_REPORTING_NAME = "reportingName";
    public static final String EMPLOYEE_JSON_KEY_DISTRIBUTOR_ID = "distributorId";
    public static final String EMPLOYEE_JSON_KEY_DISTRIBUTOR_NAME = "distributorName";
    public static final String EMPLOYEE_JSON_KEY_DISTRIBUTOR_ADDRESS = "distributorAddress";
    public static final String EMPLOYEE_JSON_KEY_REPORTING_MOBILE = "reportingMobile";
    public static final String EMPLOYEE_JSON_KEY_TERRITORY_NAME = "territoryName";
    public static final String EMPLOYEE_JSON_KEY_AREA_NAME = "areaName";
    public static final String EMPLOYEE_JSON_KEY_REGION_NAME = "regionName";
    public static final String EMPLOYEE_JSON_KEY_RETAILS = "retails";
    public static final String EMPLOYEE_JSON_KEY_MARKET_ID = "marketId";
    public static final String EMPLOYEE_JSON_KEY_MARKET_NAME = "marketName";
    public static final String EMPLOYEE_JSON_KEY_DISTRIBUTOR_MOBILE = "distributorMobile";




 // public static final String LOCATION_SENDING_API = "http://192.168.15.150:8080/api/savelocationsinfo";
    //public static final String LOCATION_SENDING_API = "http://192.168.43.79:8080/api/savelocationsinfo";
    public static final String LOCATION_SENDING_API = "http://43.224.110.67:8080/salesforce/api/savelocationsinfo";


    // API CONSTANT for retailer
  //  public static final String RETAILER_API = "http://192.168.15.150:8080/api/retail-list";
   // public static final String RETAILER_API = "http://192.168.43.79:8080/api/retail-list";
   public static final String RETAILER_API = "http://43.224.110.67:8080/salesforce/api/retail-list";
    public static final String RETAILER_API_MULTI_IMAGE = "http://43.224.110.67:8080/salesforce/api/retail-list";


    public static final String API_KEY_REPORTING_ID = "srId";
    public static final String API_KEY_RETAILER_ID = "retailId";
    public static final String API_KEY_RETAILER_NAME = "retailName";
    public static final String API_KEY_RETAILER_ADDRESS = "retailAddress";
    public static final String API_KEY_RETAILER_OWNER = "retailOwner";
    public static final String API_KEY_RETAILER_PHONE = "retailPhone";
    public static final String API_KEY_RETAILER_LATITUDE = "retailLat";
    public static final String API_KEY_RETAILER_LONGITUDE = "retailLong";
    public static final String API_KEY_RETAILER_DISTRIBUTOR_NAME = "distributorName";
    public static final String API_KEY_RETAILER_FILE_NAME = "fileName";



    public static final String ADD_RETAILER_API = "http://43.224.110.67:8080/workforce/api/save-retails";
    public static final String ADD_RETAILER_IMAGE_API = "http://43.224.110.67:8080/workforce/api/";
    public static final String API_KEY_ADD_RETAILER_SUBMITTED_BY = "submittedBy";
    public static final String API_KEY_ADD_RETAILER_NAME = "retailName";
    public static final String API_KEY_ADD_RETAILER_ADDRESS = "retailAddress";
    public static final String API_KEY_ADD_RETAILER_OWNER = "retailOwner";
    public static final String API_KEY_ADD_RETAILER_PHONE = "retailPhone";
    public static final String API_KEY_ADD_RETAILER_LAT = "retailLat";
    public static final String API_KEY_ADD_RETAILER_LONG = "retailLong";
    public static final String API_KEY_ADD_RETAILER_DISTRIBUTOR_ID = "distributorId";
    public static final String API_KEY_ADD_RETAILER_NATIONAL_ID = "nationalId";
    public static final String API_KEY_ADD_RETAILER_MARKET_ID = "marketId";
    public static final String API_KEY_ADD_RETAILER_IMAGE = "storeImage";




    // api for product list
  //  public static final String PRODUCT_API = "http://192.168.15.150:8080/api/product-details";
   // public static final String PRODUCT_API = "http://192.168.43.79:8080/api/product-details";
    //public static final String PRODUCT_API = "http://43.224.110.67:8080/salesforce/api/product-details";
    public static final String PRODUCT_API = "http://43.224.110.67:8080/workforce/api/product-details";
    public static final String PRODUCT_IMAGE = "http://43.224.110.67:8080/workforce/api/downloadFile/";

    public static final String API_KEY_DISTRIBUTOR_ID = "distId";
    public static final String API_KEY_PRODUCT_DATA = "data";

    public static final String API_KEY_PRODUCT_ID = "productId";
    public static final String API_KEY_PRODUCT_NAME = "productName";
    public static final String API_KEY_SKU = "sku";
    public static final String API_KEY_PRODUCT_LABEL = "productLabel";
    public static final String API_KEY_PRODUCT_CATEGORY = "productCategory";
    public static final String API_KEY_PRODUCT_CATEGORY_NAME = "productCategoryName";
    public static final String API_KEY_PRODUCT_SHORT_DESC = "shortDiscription";
    public static final String API_KEY_PRODUCT_MEASURING_TYPE = "mesuringType";
    public static final String API_KEY_PRODUCT_PRICE = "productPrice";
    public static final String API_KEY_PRODUCT_MRP = "productMrpPrice";
    public static final String API_KEY_PRODUCT_STARTING_STOCK = "startingStock";
    public static final String API_KEY_PRODUCT_SAFETY_STOCK = "safetyStock";
    public static final String API_KEY_PRODUCT_DISCOUNT_TYPE = "discountType";
    public static final String API_KEY_PRODUCT_AVAILABLE_DISCOUNT = "availableDiscount";
    public static final String API_KEY_PRODUCT_DISCOUNT = "discount";
    public static final String API_KEY_PRODUCT_AVAILABLE_OFFER = "availableOffer";
    public static final String API_KEY_PRODUCT_OFFER = "offer";
    public static final String API_KEY_PRODUCT_IMAGE = "productImage";
    public static final String API_KEY_PRODUCT_ON_HAND = "onHand";




    // api for submitting order
   // public static final String ORDER_API = "http://192.168.15.150:8080/api/order-details";
   // public static final String ORDER_API = "http://192.168.43.79:8080/api/order-details";

    public static final String ORDER_API = "http://43.224.110.67:8080/salesforce/api/order-details";
    public static final String SUBMIT_ORDER_API  = "http://43.224.110.67:8080/workforce/api/save-order";
    public static final String API_KEY_ORDER_ID = "orderId";
    public static final String API_KEY_ORDER_RETAILER_ID = "retailId";
    public static final String API_KEY_ORDER_RETAILER_NAME = "retailName";
    public static final String API_KEY_ORDER_RETAILER_ADDRESS = "retailAddress";
    public static final String API_KEY_ORDER_RETAILER_DISTRIBUTOR_NAME = "distributorName";
    public static final String API_KEY_ORDER_RETAILER_PHONE = "contactPhone";
    public static final String API_KEY_ORDER_TOTAL = "total";
    public static final String API_KEY_ORDER_DISCOUNT = "discount";
    public static final String API_KEY_ORDER_GRAND_TOTAL = "grandTotal";
    public static final String API_KEY_ORDER_DELIVERY_DATE = "deliveryDate";
    public static final String API_KEY_ORDER_DETAILS = "orderDetails";
    public static final String API_KEY_ORDER_PRODUCT_ID = "productId";
    public static final String API_KEY_ORDER_PRODUCT_NAME = "productName";
    public static final String API_KEY_ORDER_PRODUCT_PRICE = "totalPrice";
    public static final String API_KEY_ORDER_PRODUCT_QUANTITY = "productQuantity";
    public static final String API_KEY_ORDER_DISTRIBUTOR_ID = "distributorId";
    public static final String API_KEY_ORDER_MARKET_NAME = "marketName";
    public static final String API_KEY_ORDER_MARKET_ID = "marketId";
    public static final String API_KEY_ORDER_EMPLOYEE_ID = "employeeId";
    public static final String API_KEY_ORDER_PAYMENT_METHOD = "paymentMethod";
    public static final String API_KEY_ORDER_ADVANCED_PAYMENT = "advancedPayment";

    public static final String GET_ORDER_API = "http://43.224.110.67:8080/workforce/api/get-orders-by-sr/";






    public static final String ORDER_HISTORY_API = "";


    // api for route list
  //  public static final String ROUTE_API = "http://192.168.15.150:8080/api/assigned-routes-api";
   // public static final String ROUTE_API = "http://192.168.43.79:8080/api/assigned-routes-api";
    public static final String ROUTE_API = "http://43.224.110.67:8080/workforce/api/route-list/";
   //public static final String ROUTE_API = "http://43.224.110.67:8080/salesforce/api/assigned-routes-api";
    public static final String API_KEY_ROUTE_DAY = "dayName";
    public static final String API_KEY_ROUTE_NAME = "routeName";
    public static final String API_KEY_ROUTE_MARKET_NAME = "marketName";
    public static final String API_KEY_ROUTE_MARKET_ADDRESS = "address";
    public static final String API_KEY_ROUTE_RETAIL = "retails";



    // api for attendance

  //  public static final String ATTENDANCE_API = "http://192.168.15.150:8080/api/emp-attendance";
   // public static final String ATTENDANCE_API = "http://192.168.43.79:8080/api/emp-attendance";

   //public static final String ATTENDANCE_API = "http://43.224.110.67:8080/salesforce/api/emp-attendance";
    public static final String ATTENDANCE_API = "http://43.224.110.67:8080/workforce/api/save-attendance";
    public static final String ATTENDANCE_API_GET = "http://43.224.110.67:8080/workforce/api/employee-attendance?empId=";

    public static final String TOKEN_API = "http://43.224.110.67:8080/workforce/api/save-firebase-token";
    public static final String RESET_PASSWORD_API = "http://43.224.110.67:8080/workforce/api/reset-employee-password";



    public static final String API_KEY_ATTENDANCE_EMPLOYEE_ID = "employeeId";
    public static final String API_KEY_ATTENDANCE_IN_TIME = "inTime";
    public static final String API_KEY_ATTENDANCE_IN_ADDRESS = "checkInAddress";
    public static final String API_KEY_ATTENDANCE_OUT_TIME = "outTime";
    public static final String API_KEY_ATTENDANCE_OUT_ADDRESS = "checkOutAddress";
    public static final String API_KEY_ATTENDANCE_DATE = "logDate";
    public static final String API_KEY_ATTENDANCE_COMMENT = "comment";

  //  public static final String ROUTE_VISIT_SUBMIT_API = "http://192.168.15.150:8080/api/retail-visit";
    //public static final String ROUTE_VISIT_SUBMIT_API = "http://192.168.43.79:8080/api/retail-visit";

    public static final String ROUTE_VISIT_SUBMIT_API = "http://43.224.110.67:8080/salesforce/api/retail-visit";

    public static final String ROUTE_VISIT_KEY_RETAIL_ID = "retail_id";
    public static final String ROUTE_VISIT_KEY_RETAIL_ADDRESS = "retail_address";
    public static final String ROUTE_VISIT_KEY_RETAIL_LAT = "retail_lat";
    public static final String ROUTE_VISIT_KEY_RETAIL_LONG = "retal_long";
    public static final String ROUTE_VISIT_KEY_SR_ID = "srId";
    public static final String ROUTE_VISIT_KEY_VISIT_DATE = "visitDate";


    public static final String COLLECTION_ORDER_API = "http://43.224.110.67:8080/workforce/api/get-shipped-order-by-emp/";



   public static final String COLLECTION_UPDATE_API = "http://43.224.110.67:8080/workforce/api/save-collection";

    public static final String COLLECTION_UPDATE_API_KEY_ORDER_ID = "orderId";
    public static final String COLLECTION_UPDATE_API_KEY_TOTAL = "total";
    public static final String COLLECTION_UPDATE_API_KEY_RECEIVE_AMOUNT = "recieveAmount";
    public static final String COLLECTION_UPDATE_API_KEY_DUE_AMOUNT = "dueAmount";
    public static final String COLLECTION_UPDATE_API_KEY_COLLECTION_DATE = "collectionDate";




    public static final String MONTHLY_TARGET_API =  "http://43.224.110.67:8080/workforce/api/get-monthly-target/";

    public static final String EXPENSE_TYPE_API =  "http://43.224.110.67:8080/workforce/api/get-expense-type/";
    public static final String EXPENSE_API ="http://43.224.110.67:8080/workforce/api/get-expense-by-emp/";
    public static final String EXPENSE_SAVE_API ="http://43.224.110.67:8080/workforce/api/expense/";

    public static final String EXPENSE_SAVE_API_EXPENSE_TYPE ="expenseType";
    public static final String EXPENSE_SAVE_API_EXPENSE_AMOUNT ="amount";
    public static final String EXPENSE_SAVE_API_EXPENSE_NOTE ="note";
    public static final String EXPENSE_SAVE_API_EXPENSE_BY ="expenseBy";
    public static final String EXPENSE_SAVE_API_EXPENSE_APPROVED_AMOUNT ="approvedAmount";
    public static final String EXPENSE_SAVE_API_EXPENSE_ATTACHMENT ="attachment";

    public static final String RETAILER_TYPE = "http://43.224.110.67:8080/workforce/api/retail-type/";


    public static final String CONTACTS_API = "http://43.224.110.67:8080/workforce/api/get-contact-list" ;
    public static final String BRAND_API = "http://43.224.110.67:8080/workforce/api/brand-list";

    public static final String SAVE_COMPLAIN = "http://43.224.110.67:8080/workforce/api/save-retail-complain";
    public static final String SAVE_COMPLAIN_RETAILER = "retailId";
    public static final String SAVE_COMPLAIN_TITLE = "title";
    public static final String SAVE_COMPLAIN_DETAILS = "note";

    public static final String SAVE_LEAVE = "http://43.224.110.67:8080/workforce/api/save-leave";
    public static final String SAVE_LEAVE_EMPLOYEE_ID = "employeeId";
    public static final String SAVE_LEAVE_TYPE = "leaveType";
    public static final String SAVE_LEAVE_COMMENT = "comment";
    public static final String SAVE_LEAVE_FROM = "fromDate";
    public static final String SAVE_LEAVE_TO = "toDate";

    public static final String LEAVE_API = "http://43.224.110.67:8080/workforce/api/leave-list/";


    public static final String ATTENDANCE_SUMMERY = "http://43.224.110.67:8080/workforce/api/get-attendance-summery/";
    public static final String ADDED_RETAIL_API = "http://43.224.110.67:8080/workforce/api/get-retails-by-sr/";
    public static final String GRADE_API = "http://43.224.110.67:8080/workforce/api/get-employee-grade";
    public static final String DAILY_SUMMERY_API = "http://43.224.110.67:8080/workforce/api/get-today-order-by-emp/";
    public static final String MONTHLY_SUMMERY_API = "http://43.224.110.67:8080/workforce/api/get-monthly-order-by-emp/";


    public static final String DUE_ORDER_API ="http://43.224.110.67:8080/workforce/api/get-non-paid-retail";
    public static final String SAVE_ROUTE_VISIT = "http://43.224.110.67:8080/workforce/api/save-retail-visit";
    public static final String SAVE_ROUTE_VISIT_RETAILER_ID = "retailId";
    public static final String SAVE_ROUTE_VISIT_RETAILER_ADDRESS = "retailAddress";
    public static final String SAVE_ROUTE_VISIT_RETAILER_LATITUDE = "lat";
    public static final String SAVE_ROUTE_VISIT_RETAILER_LONGITUDE = "retailLong";
    public static final String SAVE_ROUTE_VISIT_SR_ID = "srId";
    public static final String SAVE_ROUTE_VISIT_DATE = "visitDate";

    public static final String COMPLAIN_API = "http://43.224.110.67:8080/workforce/api/get-retail-complain/";
    public static final String VISITED_RETAIL = "http://43.224.110.67:8080/workforce/api/get-visited-retail/";

    public static final String TARGET_VISIT_API = "http://43.224.110.67:8080/workforce/api/get-visit-target-summery/";
    public static final String DELIVERYMAN_TARGET = "http://43.224.110.67:8080/workforce/api/get-deliveryman-order-target/";
    //public static final String VISITED_RETAIL = "http://43.224.110.67:8080/workforce/api/get-visited-retail/";
    //public static final String VISITED_RETAIL = "http://43.224.110.67:8080/workforce/api/get-visited-retail/";
    public static final String LOCATION_API = "http://43.224.110.67:8080/workforce/api/save-location";

    public static final String LOCATION_API_FOR_LIST_OF_DATA = "http://43.224.110.67:8080/workforce/api/save-location-list";


}
