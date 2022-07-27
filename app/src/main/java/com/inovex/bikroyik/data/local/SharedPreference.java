package com.inovex.bikroyik.data.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreference {
    private static final String tag = "pref";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "merchant_pref";
    int PRIVATE_MODE = 0;

    //variables
    String userId;
    String currentOrderId, posId, posPin;
    boolean isListView;

    Context mContext;
    private static SharedPreference newSharedPreference;

    //preferences
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_IS_LIST_VIEW = "list_view";
    private static final String KEY_IS_QUICK_CAL_SELECTED = "quick_calculator";
    private static final String KEY_CURRENT_ORDER_ID = "current_order_id";
    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_CLIENT_NAME = "client_name";
    private static final String KEY_CLIENT_CONTACT = "client_contact";
    private static final String KEY_STORE_NAME = "store_name";
    private static final String KEY_STORE_CONTACT = "store_contact";
    private static final String KEY_STORE_ADDRESS = "store_address";
    private static final String KEY_HELPLINE = "help_line";
    private static final String KEY_IS_SCREEN_SHOOT_CREATED_SUCCESSFULLY = "screen_shoot";
    private static final String KEY_IS_SCREEN_SHOOT_CREATED_UNSUCCESSFULLY = "unsuccessful_screen_shoot";
    private static final String KEY_CURRENT_ORDER_ID_FOR_PRINTER = "current_order_id_printer";
    private static final String KEY_STORE_ID = "current_store_id";
    private static final String KEY_SUBSCRIBER_ID = "current_subscriber_id";
    private static final String KEY_POS_ID = "pos_id";
    private static final String KEY_POS_PIN = "pos_pin";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_DUE_AMOUNT = "due_amount";

    public static SharedPreference getInstance(Context context) {
        if (newSharedPreference == null){
            newSharedPreference = new SharedPreference(context);
            return newSharedPreference;
        }
        return newSharedPreference;
    }

    private SharedPreference(Context context){
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_MULTI_PROCESS);
        editor = pref.edit();
    }

    public void setLanguage(String language) {
        editor.putString(KEY_LANGUAGE, language);
        editor.commit();
        editor.apply();
        Log.d(tag, "User Language "+language);

    }
    public String getLanguage(){
        return pref.getString(KEY_LANGUAGE, null);
    }

    public void setUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
        Log.d(tag, "User ID modified!");
    }
    public String getUserId(){
        return pref.getString(KEY_USER_ID, userId);
    }

    public void setClientId(String clientId) {
        editor.putString(KEY_CLIENT_ID, String.valueOf(clientId));
        editor.commit();
    }
    public String getClientId(){
        return pref.getString(KEY_CLIENT_ID, null);
    }

    public void setClientName(String clientName) {
        editor.putString(KEY_CLIENT_NAME, clientName);
        editor.commit();
    }
    public String getClientName(){
        return pref.getString(KEY_CLIENT_NAME, null);
    }

    public void setClientContact(String clientContact) {
        editor.putString(KEY_CLIENT_CONTACT, clientContact);
        editor.commit();
    }
    public String getClientContact(){
        return pref.getString(KEY_CLIENT_CONTACT, null);
    }


    //it will be triggered oneTime(default) then change onButtonClick
    public void setIsListView(boolean isListView){
        editor.putBoolean(KEY_IS_LIST_VIEW, isListView);
        editor.commit();
    }

    public boolean getIsListView(){
        return pref.getBoolean(KEY_IS_LIST_VIEW, isListView);
    }

    public void setIsQuickCalSelected(boolean isListView){
        editor.putBoolean(KEY_IS_QUICK_CAL_SELECTED, isListView);
        editor.commit();
    }

    public boolean getIsQuickCalSelected(){
        return pref.getBoolean(KEY_IS_QUICK_CAL_SELECTED, false);
    }

    public void setCurrentOrderId(String orderId) {
        editor.putString(KEY_CURRENT_ORDER_ID, orderId);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }
    public String getCurrentOrderId(){
        return pref.getString(KEY_CURRENT_ORDER_ID, currentOrderId);
    }

    public void setOrderIdForPrint(String orderId) {
        editor.putString(KEY_CURRENT_ORDER_ID_FOR_PRINTER, orderId);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }
    public String getOrderIdForPrint(){
        return pref.getString(KEY_CURRENT_ORDER_ID_FOR_PRINTER, null);
    }

    public void setStoreId(String orderId) {
        editor.putString(KEY_STORE_ID, orderId);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }
    public String getStoreId(){
        return pref.getString(KEY_STORE_ID, null);
    }

    public void setDueAmount(float dueAmount) {
        editor.putFloat(KEY_DUE_AMOUNT, dueAmount);
        editor.commit();
    }
    public float getDueAmount(){
        return pref.getFloat(KEY_DUE_AMOUNT, 0);
    }


    public void setStoreName(String storeName) {
        editor.putString(KEY_STORE_NAME, storeName);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }
    public String getStoreName(){
        return pref.getString(KEY_STORE_NAME, null);
    }

    public void setStoreContact(String storeContact) {
        editor.putString(KEY_STORE_CONTACT, storeContact);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }
    public String getStoreContact(){
        return pref.getString(KEY_STORE_CONTACT, null);
    }

    public String getStoreAddress(){
        return pref.getString(KEY_STORE_ADDRESS, null);
    }

    public void setStoreAddress(String storeAddress) {
        editor.putString(KEY_STORE_ADDRESS, storeAddress);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }



    public void setHelpline(String storeContact) {
        editor.putString(KEY_HELPLINE, storeContact);
        editor.commit();
    }
    public String getHelpline(){
        return pref.getString(KEY_HELPLINE, null);
    }

    public void setSubscriberId(String subscriberId) {
        editor.putString(KEY_SUBSCRIBER_ID, subscriberId);
        editor.commit();
        Log.d(tag, "Order ID modified!");
    }
    public String getSubscriberId(){
        return pref.getString(KEY_SUBSCRIBER_ID, null);
    }

    public void setCurrentPosId(String posId) {
        editor.putString(KEY_POS_ID, posId);
        editor.commit();
        Log.d(tag, "Pos ID modified!");
    }
    public String getCurrentPosId(){
        return pref.getString(KEY_POS_ID, posId);
    }

    public void setPosPin(){
        editor.putString(KEY_POS_PIN, posPin);
        editor.commit();
        Log.d(tag, "Pos Pin modified!");
    }
    public String getCurrentPosPin(){
        return pref.getString(KEY_POS_ID, posPin);
    }

    public void setIsLoggedIn(boolean isLoggedIn){
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);
        editor.commit();
    }

    public boolean getIsLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setHasScreenshootOfReceipt(boolean isCreated){
        editor.putBoolean(KEY_IS_SCREEN_SHOOT_CREATED_SUCCESSFULLY, isCreated);
        editor.commit();
    }

    public boolean getHasScreenShootOfReceipt(){
        return pref.getBoolean(KEY_IS_SCREEN_SHOOT_CREATED_SUCCESSFULLY, false);
    }

    public void setUnsuccessfulScreenShoot(boolean isCreated){
        editor.putBoolean(KEY_IS_SCREEN_SHOOT_CREATED_UNSUCCESSFULLY, isCreated);
        editor.commit();
    }

    public boolean isUnsuccessfulScreenShoot(){
        return pref.getBoolean(KEY_IS_SCREEN_SHOOT_CREATED_UNSUCCESSFULLY, false);
    }


}
