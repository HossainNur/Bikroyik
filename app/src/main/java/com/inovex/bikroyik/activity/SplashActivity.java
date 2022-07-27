package com.inovex.bikroyik.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APICall;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.MainActivity;
import com.inovex.bikroyik.UI.activity.SigninActivity;
import com.inovex.bikroyik.data.local.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.inovex.bikroyik.AppUtils.Constants.PERMISSION_REQUEST_BACKGROUND_LOCATION;
import static com.inovex.bikroyik.activity.LoginActivity.DIST_ID;

/**
 * Created by DELL on 7/30/2018.
 */

public class SplashActivity extends Activity {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USER_TYPE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_PASS = "user_pass";
    public static final String LAST_ACTIVITY = "last_activity";
    public static final String VERSION_NAME = "version_name";
    SharedPreferences sharedpreferences;
    APICall apiCall;
    String distId;
    String uid;
    public static final String VERSION = "1.1";
    String  version = null;
    String monthlyNoOrders = "0" ;
    String monthlyGrand = "0" ;
    String dailyNoOrders = "0" ;
    String dailyGrand = "0" ;
    String targetedVisit = "0";
    String visitedPoint = "0";
    SharedPreference sharedPreference;

    Context context;
    public static ArrayList<String> expenseType = new ArrayList<String>();
    public static ArrayList<String> dailySummery = new ArrayList<String>();
    public static ArrayList<String> monthlySummery = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //showsplash();

        sharedPreference = SharedPreference.getInstance(getApplicationContext());

        context = this;
        apiCall = new APICall(context);


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        version = sharedpreferences.getString(VERSION_NAME,null);

        Log.d("workforce_version", "onCreate: "+version);

        if( version == null){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(USER_TYPE, null);
            editor.putString(USER_ID, null);
            editor.putString(USER_PASS,null);
            editor.putString("last_checked_in", null);
            editor.putString(DIST_ID, null);
            editor.putString(VERSION_NAME, String.valueOf(VERSION));
            editor.putString(LAST_ACTIVITY, null);
            editor.commit();
        } else if(!version.equalsIgnoreCase(VERSION)){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(USER_TYPE, null);
            editor.putString(USER_ID, null);
            editor.putString(USER_PASS,null);
            editor.putString("last_checked_in", null);
            editor.putString(DIST_ID, null);
            editor.putString(VERSION_NAME, String.valueOf(VERSION));
            editor.putString(LAST_ACTIVITY, null);
            editor.commit();
        }


        uid = sharedpreferences.getString(USER_ID,null);
        String passwords = sharedpreferences.getString(USER_PASS, null);
        expenseType.add("--- Select Expense Type ---");

        if(uid != null && passwords != null){
            Log.d("workforce", "onCreate: "+uid+" "+passwords);

            SignApi(context, uid,passwords);

            //sigIn(context,uid,passwords);


        }else if (sharedPreference.getIsLoggedIn()){
            //for selecting user layout
            SharedPreferences sharedpreferences;
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("user", "sr");
            editor.commit();

            startActivity(new Intent(this, com.inovex.bikroyik.UI.activity.MainActivity.class));
            finish();
        }else {
            startActivity(new Intent(this, SigninActivity.class));
            finish();
        }


    }

    public void showsplash() {

        final Dialog dialog = new Dialog(SplashActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_splash2);
        dialog.setCancelable(true);
        dialog.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                {
                    dialog.dismiss();
                }
            }
        };
        handler.postDelayed(runnable, 3000);
    }



    private void SignApi(final Context context, final String user, final String pass){

        String URL = APIConstants.SIGN_IN_API;


        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

        final RequestQueue login_queue = Volley.newRequestQueue(context);
        appDatabaseHelper.deleteAllRetailerData();

        // prepare the Request

        StringRequest signStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("_pInfo_", "profile info : "+response);

                if (response.length() > 0) {

                    try {

                        JSONArray jsonArr = new JSONArray(response.toString());
                        String retailerId = null;
                        String retailerName = null;
                        String retailerAddress = null;
                        String retailerPhone = null;
                        String retailerOwner = null;
                        String retailerLatitude = null;
                        String retailerLongitude = null;
                        String marketName = null, national_id = null, submitted_by = null, markets_id = null;
                        //appDatabaseHelper.deleteAllRetailerData();
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONObject jsonObj = jsonArr.getJSONObject(i);


                            String employeeId = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_ID);
                            String employeeName = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_NAME);
                            String employeeAddress = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_ADDRESS);
                            String employeePhone = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_PHONE);
                            String employeeCategory = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_CATEGORY);
                            String employeeReportingId = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_REPORTING_ID);
                            String reportingName = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_REPORTING_NAME);
                            String distributorID = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_DISTRIBUTOR_ID);
                            String distributorName = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_DISTRIBUTOR_NAME);
                            String distributorAddress = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_DISTRIBUTOR_ADDRESS);
                            String reportingMobile = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_REPORTING_MOBILE);
                            String territoryName = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_TERRITORY_NAME);
                            String areaName = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_AREA_NAME);
                            String regionName = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_REGION_NAME);
                            String imageName = jsonObj.getString("empImage");
                            String distributorMobile = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_DISTRIBUTOR_MOBILE);
                            JSONArray jsonArray = jsonObj.getJSONArray("markets");
                            //Log.d("workforce_splash", "onResponse: "+jsonArray);

                            distId = distributorID;

                            //market = new LinkedHashSet<String>();

                            for(int j = 0; j < jsonArray.length(); j++){
                                JSONObject jsonObject = jsonArray.getJSONObject(j);
                                String retails = jsonObject.getString(APIConstants.EMPLOYEE_JSON_KEY_RETAILS);
                                JSONArray retailsArray = new JSONArray(retails);
                                marketName = jsonObject.getString(APIConstants.EMPLOYEE_JSON_KEY_MARKET_NAME);
                                for(int k = 0; k < retailsArray.length(); k++){
                                    JSONObject object = retailsArray.getJSONObject(k);
                                    //market.add(object.getString(APIConstants.EMPLOYEE_JSON_KEY_MARKET_ID));
                                    retailerId = object.getString("id");
                                    retailerName = object.getString(APIConstants.API_KEY_RETAILER_NAME);
                                    retailerAddress = object.getString(APIConstants.API_KEY_RETAILER_ADDRESS);
                                    retailerPhone = object.getString(APIConstants.API_KEY_RETAILER_PHONE);
                                    retailerOwner = object.getString(APIConstants.API_KEY_RETAILER_OWNER);
                                    retailerLatitude = object.getString(APIConstants.API_KEY_RETAILER_LATITUDE);
                                    retailerLongitude = object.getString(APIConstants.API_KEY_RETAILER_LONGITUDE);
                                    national_id = object.getString("nationalId");
                                    submitted_by = object.getString("submittedBy");
                                    markets_id = object.getString(APIConstants.EMPLOYEE_JSON_KEY_MARKET_ID);


                                    appDatabaseHelper.insertRetailerData(retailerId,retailerName,retailerOwner,retailerAddress,retailerPhone,
                                            national_id,retailerLatitude,retailerLongitude,distributorID,distributorName,distributorAddress,
                                            markets_id,marketName,submitted_by);



                                }


                            }
                            appDatabaseHelper.deleteAllEmployeeData();
                            appDatabaseHelper.insertEmployeeData(employeeId, employeeName, employeeAddress, employeePhone, employeeCategory,
                                    employeeReportingId,reportingName,distributorID,distributorName,distributorAddress,reportingMobile,territoryName,areaName,regionName,imageName, distributorMobile);





                        }
                        attendanceInitialization();

                        String uid = sharedpreferences.getString(USER_ID,null);
                        String today = getMonthAndYear();
                        Calendar instance = Calendar.getInstance();
                        int currentMonth = instance.get(Calendar.MONTH);
                        int currentYear = instance.get(Calendar.YEAR);
                        instance.set(Calendar.YEAR, currentYear);
                        instance.set(Calendar.MONTH, currentMonth);
                        int numDays = instance.getActualMaximum(Calendar.DATE);



                        String toDate = today+"-"+String.valueOf(numDays);
                        String fromDate  = today+"-01";
                        new APICall(context).RouteAPI(context,uid);
                        getAttendanceAPI(context,uid,fromDate,toDate);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("URL", "callSignInAPI: "+e);

                    }


                } else {
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Log.d("workforce_splash", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("workforce_splash", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("workforce_splash", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("workforce_splash", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("workforce_splash", "location sending response: timeout error");
                }

                Log.d("workforce_splash", "location sending responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APIConstants.SIGN_IN_API_KEY_EMPLOYEE_ID, user);
                params.put(APIConstants.SIGN_IN_API_KEY_EMPLOYEE_PASSWORD, pass);


                return params;
            }
        };

        signStringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        login_queue.getCache().clear();
        login_queue.add(signStringRequest);


    }

    private void attendanceInitialization() {

        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(this);
        String monthAndYear = getMonthAndYear();
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (appDatabaseHelper.getAttendanceList().size() <= 0) {
            for (int x = 1; x <= days; x++) {


                String attendanceDate;
                if (x < 10) {
                    //  attendanceDate = "0" + x + "-" + monthAndYear;
                    attendanceDate = monthAndYear + "-" + "0" + x;
                } else {
                    //  attendanceDate = x + "-" + monthAndYear;
                    attendanceDate = monthAndYear + "-" + x;
                }


                String attendanceId = x + "";

                String inTime = "--";
                String inAddress = "";
                String outTime = "--";
                String outAddress = "";
                String status = "--";
                String sentStatus = "--";
                String comment = "";
                String inLatLon = "";
                String outLatLon = "";
                appDatabaseHelper.insertAttendance(attendanceId, attendanceDate, inTime, inAddress, outTime, outAddress, status, sentStatus, comment, inLatLon, outLatLon);
            }


        }

        //checkAttendance();


    }


    private void getAttendanceAPI(final Context context, final String empId, final String from, final String to) {
        //http://43.224.110.67:8080/workforce/api/employee-attendance?empId=1234&fromDate=2021-01-01&toDate=2021-05-30

        String URL = APIConstants.ATTENDANCE_API_GET+empId+"&fromDate="+from+"&toDate="+to;


        RequestQueue attendanceQueue = Volley.newRequestQueue(context);

        JsonObjectRequest attendanceRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response.length() > 0) {

                    try {
                        String data = response.getString("data");
                        JSONArray jsonArray = new JSONArray(data);

                        Log.d("attendanceAPI", "onResponse: "+jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String inTimes = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_IN_TIME);
                            String inAddress = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_IN_ADDRESS);
                            String outTimes = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_OUT_TIME);
                            String outAddress = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_OUT_ADDRESS);
                            String comment = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_COMMENT);
                            String id = jsonObject.getString("id");
                            String status = jsonObject.getString("status");

                            String inTime = null;
                            String date = null;
                            String outTime = null;

                            Log.d("workforese", "in response json --->> intimes : "+inTimes +" outTimes : "+outTimes);
                            Log.d("workforese", "in response json --->> intimes : "+TextUtils.isEmpty(inTimes) +" outTimes : "+TextUtils.isEmpty(outTimes));

                            if (inTimes.length() <= 0 || outTimes.length() <= 0 || inTimes.matches("null")|| outTimes.matches("null")){

                            }else {
                                String[] splitString  = inTimes.split(" ");
                                inTime = splitString[1];
                                date = splitString[0];
                                splitString = outTimes.split(" ");
                                outTime = splitString[1];
                            }

                            String inLatLon = jsonObject.getString("lat");
                            String outLatLon = jsonObject.getString("lng");
                            String type = jsonObject.getString("attendanceType");

//                            String status = "success";
//                            if(type.equalsIgnoreCase("in")){
//                                status = "pending";
//                            }
                            AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);
                            appDatabaseHelper.updateAttendance(id,date,inTime,inAddress,outTime, outAddress, status,comment,inLatLon,outLatLon);
                        }

                        getExpenseType();



                    } catch (JSONException e) {
                        Log.d("attendanceAPI", "onResponseAttendance: "+e);

                        e.printStackTrace();
                        getExpenseType();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("sales force", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("sales force", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("sales force", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("sales force", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("sales force", "location sending response: timeout error");
                }

                Log.d("attendanceAPI", "responseError:" + error.toString());
                error.printStackTrace();
            }
        });

        attendanceQueue.add(attendanceRequest);

    }

    public void getExpenseType()
    {

        String URL = APIConstants.EXPENSE_TYPE_API+distId;


        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("workforce", "attendance response" + response);

                if (response.length() > 0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i < jsonArray.length(); i++ ){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String exType = jsonObject.getString("expenseType");
                            expenseType.add(exType);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                callComplainAPI();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("sales force", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("sales force", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("sales force", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("sales force", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("sales force", "location sending response: timeout error");
                }

                Log.d("workforce", "responseError:" + error.toString());
                error.printStackTrace();
            }
        });

        queue.add(request);

    }

    private void callComplainAPI() {

        String URL  = APIConstants.COMPLAIN_API+uid;
        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    appDatabaseHelper.deleteComplainTable();

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray object1 = jsonObject.getJSONArray("data");
                        for(int i  = 0; i< object1.length(); i++){
                            JSONObject object = object1.getJSONObject(i);
                            String id = object.getString(APIConstants.SAVE_COMPLAIN_RETAILER);
                            String title = object.getString(APIConstants.SAVE_COMPLAIN_TITLE);
                            String note = object.getString(APIConstants.SAVE_COMPLAIN_DETAILS);

                            appDatabaseHelper.insertComplain(id,title,note);
                        }

                        callExpenseAPI();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("workforce_complain", "onErrorResponse: "+error);
            }
        });
        queue.add(stringRequest);
    }

    private void callVisitedRetails(final String uid) {




        String URL = APIConstants.VISITED_RETAIL+uid;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    String retailId = null;
                    if(response.length() > 0){
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            new AppDatabaseHelper(context).deleteVisitedRetailTableData();

                            for(int i = 0; i < jsonArray.length(); i++){

                                retailId = jsonArray.getJSONObject(i).getString("id");
                                new AppDatabaseHelper(context).insertVisitedRetail(retailId);

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callTargetVisit(uid);


                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);


    }

    private void callTargetVisit(String uid) {

        String URL = APIConstants.TARGET_VISIT_API+uid;

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){

                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject object = jsonObject.getJSONObject("data");

                        new AppDatabaseHelper(context).deleteDashboardTableData();
                        String targeted_visit = object.getString("targetedVisit");
                        String visitedPoint = object.getString("vistedpoint");


                        new AppDatabaseHelper(context).insertDashboardData(dailyNoOrders,dailyGrand,monthlyNoOrders,monthlyGrand,targeted_visit,visitedPoint);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fragment_position",0);
                    startActivity(intent);
                    finish();


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);


    }

    private String getMonthAndYear() {

        String monthAndYear = "";
        Calendar instance = Calendar.getInstance();
        int currentMonth = instance.get(Calendar.MONTH);
        int currentYear = instance.get(Calendar.YEAR);
        int month = currentMonth + 1;
        if (month < 10) {
            //  monthAndYear = "0" + month + "-" + currentYear;
            monthAndYear = currentYear + "-" + "0" + month;
        } else {
            //  monthAndYear = month + "-" + currentYear;
            monthAndYear = currentYear + "-" + month;
        }

        return monthAndYear;
    }

    private void callExpenseAPI() {

        String URL  = APIConstants.EXPENSE_API+uid;
        new AppDatabaseHelper(context).deleteAllExpenseData();
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length() > 0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String date_str = jsonObject.getString("createdAt");
                            String[] split_str = date_str.split(" ");
                            String date = split_str[0];
                            String type = jsonObject.getString("expenseType");
                            String amount = jsonObject.getString("amount");
                            String note = jsonObject.getString("note");
                            String by = jsonObject.getString("expenseBy");
                            String statusCode = jsonObject.getString("status");
                            String status = "pending";
                            Log.d("workforce_expanse", "onResponse: "+statusCode);
                            if(statusCode.equalsIgnoreCase("1")){
                                Log.d("workforce_expanse", "onResponse1: "+statusCode);
                                status = "approved";
                            } else if(statusCode.equalsIgnoreCase("0")){
                                Log.d("workforce_expanse", "onResponse0: "+statusCode);
                                status ="pending";
                            } else if(statusCode.equalsIgnoreCase("2")){
                                Log.d("workforce_expanse", "onResponse2: "+statusCode);
                                status = "declined";
                            }
                            String approved = jsonObject.getString("approvedAmount");
                            String imageName = jsonObject.getString("attachment");

                            AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);
                            //Log.d("workforce", "onResponse: "+type+" "+amount+" " +note+" "+by+" "+status+" "+approved+" "+imageName+" "+date);
                            appDatabaseHelper.insertExpenseData(type,amount,note,by,status,approved,imageName,date);


                        }
                        callDailySummeryApi();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("workforce", "onErrorResponse: "+error);
            }
        });

        queue.add(stringRequest);


    }

    private void callMonthlySummeryApi() {

        final String URL = APIConstants.MONTHLY_SUMMERY_API+uid;
        Log.d("workforce_daily", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    monthlySummery.clear();
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject object = jsonObject.getJSONObject("data");
                        monthlyNoOrders = object.getString("numberOfOrder");
                        monthlySummery.add(monthlyNoOrders);
                        monthlyGrand = object.getString("grandTotal");
                        monthlySummery.add(monthlyGrand);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callVisitedRetails(uid);



                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);


    }

    private void callDailySummeryApi() {

        final String URL = APIConstants.DAILY_SUMMERY_API+uid;
        Log.d("workforce_daily", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    dailySummery.clear();
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject object = jsonObject.getJSONObject("data");
                        dailyNoOrders = object.getString("numberOfOrder");
                        dailySummery.add(dailyNoOrders);
                        dailyGrand = object.getString("grandTotal");
                        dailySummery.add(dailyGrand);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    callMonthlySummeryApi();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);


    }



//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == PERMISSION_REQUEST_BACKGROUND_LOCATION) {
//
//            boolean foreground = false, background = false;
//
//            for (int i = 0; i < permissions.length; i++) {
//                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
//                    //foreground permission allowed
//                    if (grantResults[i] >= 0) {
//                        foreground = true;
//                        Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
//                        continue;
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                }
//
//                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
//                    if (grantResults[i] >= 0) {
//                        foreground = true;
//                        background = true;
//                        Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
//                    }
//
//                }
//            }
//
//            if (foreground) {
//                if (background) {
//                    handleLocationUpdates(getApplicationContext());
//                } else {
//                    handleForegroundLocationUpdates(getApplicationContext());
//                }
//            }
//        }
//    }
//
//


    private void backgroundLocation(Activity mActivity){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("This app needs background location access");
        builder.setMessage("Please grant location access so this app can detect beacons in the background.");
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION);
            }
        });
        builder.show();
    }


}


