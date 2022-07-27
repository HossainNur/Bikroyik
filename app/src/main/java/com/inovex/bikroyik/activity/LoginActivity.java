package com.inovex.bikroyik.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.popup.PermissionGuideline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static com.inovex.bikroyik.AppUtils.Constants.PERMISSION_REQUEST_BACKGROUND_LOCATION;
import static com.inovex.bikroyik.AppUtils.Constants.isNetworkAvailable;
import static com.inovex.bikroyik.activity.SplashActivity.dailySummery;
import static com.inovex.bikroyik.activity.SplashActivity.monthlySummery;

/**
 * Created by DELL on 7/31/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 111199;
    private EditText emailEditText;
    private EditText passEditText;
    Context mContext;
    AppDatabaseHelper appDatabaseHelper;
    private ProgressDialog progressDialog;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USER_TYPE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_PASS = "user_pass";
    public static final String DIST_ID = "distId";
    public static final String LAST_ACTIVITY = "last_activity";
    String distId;
    String monthlyNoOrders = "0" ;
    String monthlyGrand = "0" ;
    String dailyNoOrders = "0" ;
    String dailyGrand = "0" ;
    String targetedVisit = "0";
    String visitedPoint = "0";
    Context context;

    private AdView adView;

    LinkedHashSet<String> market;
    HashMap<String, String> marketDetails = new HashMap<String, String>();
    String password;
    String empId;

    Activity mActivity;
    int checkTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        mContext = this;
        context = mContext;
        mActivity = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        if (appDatabaseHelper == null) {
            appDatabaseHelper = new AppDatabaseHelper(mContext);
        }
        emailEditText = (EditText) findViewById(R.id.username);
        passEditText = (EditText) findViewById(R.id.password);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String uid = sharedpreferences.getString(USER_ID,null);
        String passwords = sharedpreferences.getString(USER_PASS, null);

        if(uid != null && passwords != null){
            Log.d("workforce", "onCreate: "+uid+" "+passwords);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("id",uid);
            intent.putExtra("password",passwords);
            startActivity(intent);
            finish();
        }

        int coarseLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int backgroundLocationPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);


        if (coarseLocationPermission != PackageManager.PERMISSION_GRANTED && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)) {

            requestBackgroundLocationPermission();
            Intent intent = new Intent(LoginActivity.this, PermissionGuideline.class);
            startActivity(intent);

        }else if(coarseLocationPermission != PackageManager.PERMISSION_GRANTED &&
                (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)){
            ActivityCompat.requestPermissions(
                    LoginActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION
            );
        }

        if (backgroundLocationPermission != PackageManager.PERMISSION_GRANTED){
            requestBackgroundLocationPermission();
        }

//        adManager();

    }

    public void checkLogin(View arg0) {

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        final String email = emailEditText.getText().toString();
        empId = email;

        if (email.length() < 1) {
            emailEditText.setError("Invalid Email");
        }

        final String pass = passEditText.getText().toString();
        password = pass;
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passEditText.setError("Password cannot be empty");
        }

        if (isNetworkAvailable(getApplicationContext())){
            if (email.length() > 1 && isValidPassword(pass)) {

                progressDialog.show();

                clearApplicationData();

                callSignInAPI(email, pass);



            }
        }else {
            Toast.makeText(getApplicationContext(), "please check your network!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void clearApplicationData() {

        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (s.equals("databases")) {
                    deleteDir(new File(appDir, s));
                    Log.i("workforce", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }

    }
    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    private void callSignInAPI(final String email, final String pass) {
        String URL = APIConstants.SIGN_IN_API;
        final RequestQueue login_queue = Volley.newRequestQueue(mContext);
        /*appDatabaseHelper.deleteAllRetailerData();
        appDatabaseHelper.deleteAllPerProduct();
        appDatabaseHelper.deleteAllAttendanceData();*/

        // prepare the Request

        StringRequest signStringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("_pInfo_", "response: "+response);
                if (response.length() > 0) {
                    try {
                        SessionManager sessionManager = new SessionManager(getApplicationContext());
                        sessionManager.setUserId(email);


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

                            if (employeeId == null || TextUtils.isEmpty(employeeId) || employeeId.equals("null")){
                                Toast.makeText(getApplicationContext(), "Incorrect UserName or Password! Please Try Again!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                break;
                            }
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
                            String distributorMobile = jsonObj.getString(APIConstants.EMPLOYEE_JSON_KEY_DISTRIBUTOR_MOBILE);
                            String imageName = jsonObj.getString("empImage");
                            String markets = jsonObj.getString("markets");
                            JSONArray jsonArray = new JSONArray(markets);
                            distId = distributorID;
                            
                             market = new LinkedHashSet<String>();

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
                                    addRetailer(retailerId,retailerName,retailerOwner,retailerAddress,retailerPhone,
                                            national_id,retailerLatitude,retailerLongitude,distributorID,distributorName,distributorAddress,
                                            markets_id,marketName,submitted_by);

                                }


                            }

                            sessionManager.setEmployeeCategory(employeeCategory);
                            insertEmployeeDataToDatabase(employeeId, employeeName, employeeAddress, employeePhone, employeeCategory, employeeReportingId,
                                    reportingName,distributorID,distributorName,distributorAddress,reportingMobile,territoryName,areaName,regionName, imageName,distributorMobile);



                            System.out.println(jsonObj);


                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(USER_TYPE, employeeCategory);
                            editor.putString(USER_ID, email);
                            editor.putString(USER_PASS,pass);
                            editor.putString(DIST_ID,distId);
                            editor.putString(LAST_ACTIVITY, "log_in");
                            editor.commit();
                            //appDatabaseHelper.deleteAllRetailerData();

                            SharedPreference sharedPreference = SharedPreference.getInstance(getApplicationContext());
                            sharedPreference.setUserId(empId);
                            sessionManager.setUserId(email);
                            progressDialog.dismiss();

                            attendanceInitialization();

                            String uid = email;
                            String today = getMonthAndYear();
                            Calendar instance = Calendar.getInstance();
                            int currentMonth = instance.get(Calendar.MONTH);
                            int currentYear = instance.get(Calendar.YEAR);
                            instance.set(Calendar.YEAR, currentYear);
                            instance.set(Calendar.MONTH, currentMonth);
                            int numDays = instance.getActualMaximum(Calendar.DATE);



                            String toDate = today+"-"+String.valueOf(numDays);
                            String fromDate  = today+"-01";
                            getAttendanceAPI(mContext,uid,fromDate,toDate);



                            //callRouteApi(employeeId, mContext);

                        }


                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        e.printStackTrace();
                        Log.d("URL", "callSignInAPI: "+e);

                    }


                } else {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Log in error.....", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
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

                Log.d("sending_error", "location sending responseError:" + error.toString());
                error.printStackTrace();
                closeApp();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APIConstants.SIGN_IN_API_KEY_EMPLOYEE_ID, email);
                params.put(APIConstants.SIGN_IN_API_KEY_EMPLOYEE_PASSWORD, pass);


                return params;
            }
        };

        signStringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        login_queue.getCache().clear();
        login_queue.add(signStringRequest);



    }

    private void addRetailer(String retailerId, String retailerName, String retailerOwner,
                             String retailerAddress, String retailerPhone, String national_id,
                             String retailerLatitude, String retailerLongitude, String distributorID,
                             String distributorName, String distributorAddress, String markets_id,
                             String marketName, String submitted_by) {

        //appDatabaseHelper.deleteAllRetailerData();
        appDatabaseHelper.insertRetailerData(retailerId,retailerName,retailerOwner,retailerAddress,retailerPhone,
                national_id,retailerLatitude,retailerLongitude,distributorID,distributorName,distributorAddress,
                markets_id,marketName,submitted_by);



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


    private void getAttendanceAPI(final Context context, final String empId, final String from, final String to)
    {
        //http://43.224.110.67:8080/workforce/api/employee-attendance?empId=1234&fromDate=2021-01-01&toDate=2021-05-30

        String URL = APIConstants.ATTENDANCE_API_GET+empId+"&fromDate="+from+"&toDate="+to;
        Log.d("workforce_log_in", "attendance: "+URL);


        RequestQueue attendanceQueue = Volley.newRequestQueue(context);

        JsonObjectRequest attendanceRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response.length() > 0) {

                    try {
                        String data = response.getString("data");
                        JSONArray jsonArray = new JSONArray(data);

                        Log.d("workforce", "resposne json : "+response.toString());
                        Log.d("workforce", "onResponse: "+jsonArray.length());

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

                            if (inTimes.length() <= 0 || outTimes.length() <= 0 || inTimes.matches("null")|| outTimes.matches("null") || inTimes == null ||outTimes == null){

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
                            appDatabaseHelper.updateAttendance(id,date,inTime,inAddress,outTime, outAddress, status,comment, inLatLon, outLatLon);
                        }

                        getExpenseType();
                    } catch (JSONException e) {
                        Log.d("workforce_log_in", "onResponseAttendance: "+e);
                        e.printStackTrace();
                        //closeApp();
                        getExpenseType();


                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("workforce_log_in", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("workforce_log_in", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("workforce_log_in", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("workforce_log_in", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("workforce_log_in", "location sending response: timeout error");
                }

                Log.d("workforce_log_in", "location sending responseError:" + error.toString());
                error.printStackTrace();
                closeApp();
            }
        });

        attendanceQueue.add(attendanceRequest);

    }

    private void callComplainAPI() {

        String URL  = APIConstants.COMPLAIN_API+empId;
        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);

        RequestQueue queue = Volley.newRequestQueue(mContext);

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

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String retailId = null;
                if(response.length() > 0){
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        new AppDatabaseHelper(mContext).deleteVisitedRetailTableData();

                        for(int i = 0; i < jsonArray.length(); i++){

                            retailId = jsonArray.getJSONObject(i).getString("id");
                            new AppDatabaseHelper(mContext).insertVisitedRetail(retailId);

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


    public void getExpenseType()
    {

        String URL = APIConstants.EXPENSE_TYPE_API+distId;


        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("workforce_log_in", "attendance response" + response);

                if (response.length() > 0){
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i < jsonArray.length(); i++ ){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String exType = jsonObject.getString("expenseType");
                            SplashActivity.expenseType.add(exType);
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
                    Log.d("workforce_log_in", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("workforce_log_in", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("workforce_log_in", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("workforce_log_in", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("workforce_log_in", "location sending response: timeout error");
                }

                Log.d("workforce_log_in", "location sending responseError:" + error.toString());
                error.printStackTrace();
                closeApp();
            }
        });

        queue.add(request);

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

        String URL  = APIConstants.EXPENSE_API+empId;
        new AppDatabaseHelper(mContext).deleteAllExpenseData();
        RequestQueue queue = Volley.newRequestQueue(mContext);

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
                            if(statusCode == "1"){
                                status = "approved";
                            } else if(statusCode.equals("0")){
                                status ="pending";
                            } else if(statusCode.equals("2")){
                                status = "declined";
                            }
                            String approved = jsonObject.getString("approvedAmount");
                            String imageName = jsonObject.getString("attachment");

                            AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);
                            //Log.d("workforce", "onResponse: "+type+" "+amount+" " +note+" "+by+" "+status+" "+approved+" "+imageName+" "+date);
                            appDatabaseHelper.insertExpenseData(type,amount,note,by,status,approved,imageName,date);


                        }

                        callDailySummeryApi();

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("workforce_log_in", "location sending responseError:" + error.toString());

            }
        });

        queue.add(stringRequest);


    }

    private void callMonthlySummeryApi() {

        final String URL = APIConstants.MONTHLY_SUMMERY_API+empId;
        Log.d("workforce_daily", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    monthlySummery.clear();
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject object = jsonObject.getJSONObject("data");
                        monthlySummery.add(object.getString("numberOfOrder"));
                        monthlySummery.add(object.getString("grandTotal"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    callVisitedRetails(empId);


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

        final String URL = APIConstants.DAILY_SUMMERY_API+empId;
        Log.d("workforce_daily", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    dailySummery.clear();
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONObject object = jsonObject.getJSONObject("data");
                        dailySummery.add(object.getString("numberOfOrder"));
                        dailySummery.add(object.getString("grandTotal"));


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



    private void insertEmployeeDataToDatabase(String employeeId, String employeeName,
                                              String employeeAddress, String employeephone, String employeeCategory, String employeeReportingId,
                                              String reportingName, String distributorID, String distributorName, String distributorAddress,
                                              String reportingMobile, String territoryName, String areaName, String regionName, String imageName, String distributorMobile) {
        appDatabaseHelper.deleteAllEmployeeData();
        appDatabaseHelper.insertEmployeeData(employeeId, employeeName, employeeAddress, employeephone, employeeCategory,
                employeeReportingId,reportingName,distributorID,distributorName,distributorAddress,reportingMobile,territoryName,areaName,regionName, imageName, distributorMobile);




    }



    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }

    public void closeApp(){

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            }
        }, 2000);


    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i("WoosmapGeofencing", "Displaying permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            Log.i("WoosmapGeofencing", "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(LoginActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("WoosmapGeofencing", "onRequestPermissionResult");
        switch (requestCode){
            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
                boolean foreground = false, background = false;

                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //foreground permission allowed
                        if (grantResults[i] >= 0) {
                            foreground = true;
                            Log.d("permission", "Foreground location permission allowed");
//                            Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                            continue;
                        } else {
                            Log.d("permission", "Location Permission denied onRequestPermissionResult");
//                            Toast.makeText(getApplicationContext(), "Location Permission denied onRequestPermissionResult", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        if (grantResults[i] >= 0) {
                            foreground = true;
                            background = true;
                            Log.d("permission", "Background location permission allowed");
                            Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            Log.d("permission", "Background location permission denied");
                            break;
                        }

                    }
                    break;
                }

                break;
        }
        }
    }


    public void requestBackgroundLocationPermission() {
        boolean foreground = ActivityCompat.checkSelfPermission(mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (foreground) {

            boolean background = ActivityCompat.checkSelfPermission(mActivity,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
            } else {
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION);
            }

        } else {
            ActivityCompat.requestPermissions(mActivity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION);

        }
    }

    private void adManager(){
        adView = new AdView(this, "IMG_16_9_APP_INSTALL#1453443504734328_4776586412420004", AdSize.BANNER_HEIGHT_50);

// Find the Ad Container
        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

// Add the ad view to your activity layout
        adContainer.addView(adView);



        AdListener adListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
// Ad error callback
                Toast.makeText(
                        LoginActivity.this,
                        "Error: " + adError.getErrorMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
// Ad loaded callback
            }

            @Override
            public void onAdClicked(Ad ad) {
// Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
// Ad impression logged callback
            }
        };

// Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(adListener).build());
    }



    @Override
    protected void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
