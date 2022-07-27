package com.inovex.bikroyik.AppUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Isfahani on 28-Mei-16.
 * Modified from AndroidHive.info
 */
public class SessionManager {
    private static String TAG = "paiker";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    String user_id;
    String attendanceStatus = "--";
    String token;
    String emp_category;
    long timeStamp;

    private static final String PREF_NAME = "TOKEN";
    private static final String KEY_IS_SEND_TOKEN = "send-token";
    private static final String KEY_USER_ID = "user-id";
    private static final String KEY_FCM_TOKEN = "token_fcm";
    private static final String KEY_TIME_STAMP = "time_stamp";
    private static final String KEY_firstTime = "storage_at";
    private static final String KEY_EMPLOYEE_CATEGORY = "employee-category";
    private static final String KEY_BACKGROUND_PERMISSION_CHECKED = "CHECKED_TIMES";

    private static final String  KEY_CONTEXT = "wContextKey";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void isTokenSend(boolean isSend) {
        editor.putBoolean(KEY_IS_SEND_TOKEN, isSend);
        editor.commit();
        Log.d(TAG, "User login session modified!");
    }

    public boolean isTokenSend(){

        return pref.getBoolean(KEY_IS_SEND_TOKEN, false);
    }

    public void setUserId(String userId) {
        editor.putString(KEY_USER_ID, userId);
        editor.commit();
        Log.d(TAG, "User ID modified!");
    }
    public String getUserId(){
        return pref.getString(KEY_USER_ID, user_id);
    }


    public void setEmployeeCategory(String category) {
        editor.putString(KEY_EMPLOYEE_CATEGORY, category);
        editor.commit();
        Log.d(TAG, "User ID modified!");
    }
    public String getEmployeeCategory(){
        return pref.getString(KEY_EMPLOYEE_CATEGORY, emp_category);
    }

    public void setFcmToken(String fcmToken) {
        editor.putString(KEY_FCM_TOKEN, fcmToken);
        editor.commit();
        Log.d(TAG, "User ID modified!");
    }
    public String getFcmToken(){
        return pref.getString(KEY_FCM_TOKEN, token);
    }

    public void setTimeInMilliSec(long timeStamp) {
        editor.putLong(KEY_TIME_STAMP, timeStamp);
        editor.commit();
        Log.d(TAG, "timeStamp modified!");
    }
    public long getTimeInMilliSec(){
        return pref.getLong(KEY_TIME_STAMP, timeStamp);
    }

    public void isFirstTimeStore(boolean firstCall) {
        editor.putBoolean(KEY_firstTime, firstCall);
        editor.commit();
        Log.d(TAG, "timeStamp modified!");
    }
    public boolean isFirstTimeStore(){
        return pref.getBoolean(KEY_firstTime, false);
    }


    public void setTodayINAttendanceStatus(String attendance_status) {
        editor.putString(KEY_USER_ID, attendance_status);
        editor.commit();
    }
    public String setTodayOUTAttendanceStatus(){
        return pref.getString(KEY_BACKGROUND_PERMISSION_CHECKED, attendanceStatus);
    }

}