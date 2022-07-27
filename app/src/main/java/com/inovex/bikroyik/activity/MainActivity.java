package com.inovex.bikroyik.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inovex.bikroyik.AppUtils.APICall;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.AppUtils.CheckRunningService;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.SigninActivity;
import com.inovex.bikroyik.adapter.DeviceList;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.ProductJsonGetter;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.fragment.AttendanceDetailsFragment;
import com.inovex.bikroyik.fragment.BrandDirectoryFragment;
import com.inovex.bikroyik.fragment.CallSchedulerFragment;
import com.inovex.bikroyik.fragment.ComplainFragment;
import com.inovex.bikroyik.fragment.ContactsFragment;
import com.inovex.bikroyik.fragment.ExpensesFragment;
import com.inovex.bikroyik.fragment.FragmentDrawer;
import com.inovex.bikroyik.fragment.HomeFragmentMR;
import com.inovex.bikroyik.fragment.HomeFragmentOrder;
import com.inovex.bikroyik.fragment.HomeFragmentSR;
import com.inovex.bikroyik.fragment.Leave;
import com.inovex.bikroyik.fragment.MessagingFragment;
import com.inovex.bikroyik.fragment.NotificationsFragment;
import com.inovex.bikroyik.fragment.OrderDetailsFragment;
import com.inovex.bikroyik.fragment.ProfileFragment;
import com.inovex.bikroyik.fragment.SettingsFragment;
import com.inovex.bikroyik.fragment.TaskListFragment;
import com.inovex.bikroyik.receiver.AppBroadcastReceiver;
import com.inovex.bikroyik.services.AlarmService;
import com.inovex.bikroyik.services.AppService;
import com.inovex.bikroyik.services.GPSTracker;
import com.inovex.bikroyik.services.SalesForceService;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.inovex.bikroyik.AppUtils.Constants.APP_PREFERENCE;
import static com.inovex.bikroyik.AppUtils.Constants.PERMISSION_REQUEST_BACKGROUND_LOCATION;
import static com.inovex.bikroyik.activity.LoginActivity.DIST_ID;
import static com.inovex.bikroyik.activity.LoginActivity.LAST_ACTIVITY;
import static com.inovex.bikroyik.activity.SplashActivity.dailySummery;
import static com.inovex.bikroyik.activity.SplashActivity.monthlySummery;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static final int REQUEST_PERMISSION = 174;
    private static final int REQUEST_ENABLE_BT = 175;
    public FragmentDrawer drawerFragment;
    boolean doubleBackToExitPressedOnce = false;
    public Toolbar mToolbar;
    public static TextView tvHomeToolbarTitle;
    ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();

    ImageView ivSyncIcon;
    Context context;
    public CheckRunningService checkRunningService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USER_TYPE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_PASS = "user_pass";
    public static final String LAST_CHECKED_IN = "last_checked_in";

    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    byte FONT_TYPE;
    public static BluetoothSocket btsocket;
    public static BluetoothSocket bt;
    public static ArrayList<HashMap<String, String>> contactsList = new ArrayList<HashMap<String, String>>();
    public static String brand;


    ArrayList<HashMap<String, String>> attendanceList = new ArrayList<>();
    public static ArrayList<String> retailerType = new ArrayList<String>();
    public static ArrayList<HashMap<String, String>> expandableListDetailsRoute = new ArrayList<HashMap<String, String>>();


    public TextView tv_language;
    private TextView tv_changePassword;
    LoginActivity loginActivity;
    LinearLayout connection;
    APICall apiCall;
    int cnt = 1;
    AppDatabaseHelper appDatabaseHelper;
    String distId, empId;

    private Activity mActivity;
    private DatabaseSQLite databaseSQLite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mActivity = this;

        apiCall = new APICall(context);
        databaseSQLite = new DatabaseSQLite(this);

        int timeInterval = 15 * 60 * 1000;
        startAlert(timeInterval);

        if (savedInstanceState == null) {
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            distId = sharedpreferences.getString(DIST_ID, null);
            empId = sharedpreferences.getString(USER_ID, null);

            apiCall.callOrderDetailApi(context, empId);
            //callDailySummeryApi();
            //callMonthlySummeryApi();
            Log.d("workforce_daily", "onCreate: " + dailySummery + " " + monthlySummery);
            getRetailerType();

            Log.d("workforce", "onCreate: " + retailerType);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
            ivSyncIcon = mToolbar.findViewById(R.id.ivSyncIcon);
            tv_language = findViewById(R.id.btn_change_language);
            tv_changePassword = findViewById(R.id.btn_change_password);
            connection = findViewById(R.id.lLConnect);

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                Log.d("workforce", "onCreate: Not Support");
            } else {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }

            /*IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);*/


                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                pairedDevices = bluetoothAdapter.getBondedDevices();


            }

            appDatabaseHelper = new AppDatabaseHelper(context);
            apiCall = new APICall(context);



            apiCall.RouteAPI(context, empId);
            callBrandAPI();
            callProductAPI();
            callLeaveListApi();
            callSRGradeApi();
            callTargetApi();
            expandableListDetailsRoute = appDatabaseHelper.getAllRouteVisitLists();
            //Log.d("workforce_route_visit", "onCreate: "+expandableListDetailsRoute);
            contactsList = appDatabaseHelper.getAllContact();
            //Log.d("workforce", "onCreate: "+contactsList);


            tv_language.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String language = getResources().getString(R.string.language);
                    changeLocale(language);


                }
            });

//            tv_changePassword.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(MainActivity.this, PasswordChangeActivity.class);
//                    startActivity(intent);
//                }
//            });

            connection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (btsocket == null) {
                        connectPrinter();
                    }
                }
            });


            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

            drawerFragment = (FragmentDrawer)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
            drawerFragment.setDrawerListener(this);



            checkRunningService = new CheckRunningService(getApplicationContext());
            // ask for permissions
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int permissionCheck4 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
            int permissionCheck5 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int backgroundLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);

            if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED ||
                    permissionCheck3 != PackageManager.PERMISSION_GRANTED || permissionCheck4 != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA}, REQUEST_PERMISSION);


            }
            if (permissionCheck2 == PackageManager.PERMISSION_GRANTED || permissionCheck5 == PackageManager.PERMISSION_GRANTED) {

                if (!checkRunningService.isServiceRunning(GPSTracker.class)) {
                    startService(new Intent(getApplicationContext(), GPSTracker.class));
                }
                if (!checkRunningService.isServiceRunning(SalesForceService.class)) {
                    startService(new Intent(getApplicationContext(), SalesForceService.class));
                }
                if (!checkRunningService.isServiceRunning(AlarmService.class)) {
                    Log.d("workforce", "onCreate: "+checkRunningService.isServiceRunning(AlarmService.class));
                    startService(new Intent(getApplicationContext(), AlarmService.class));
                }

            }

            if (permissionCheck5 == PackageManager.PERMISSION_GRANTED && backgroundLocationPermission == PackageManager.PERMISSION_GRANTED){
                    if (!checkRunningService.isServiceRunning(AppService.class)){
                        Intent serviceIntent = new Intent(getApplicationContext(), AppService.class);
                        startService(serviceIntent);
                    }

            }else {
                requestBackgroundLocationPermission();
            }

            callContactsAPI(context);
            getAddedRetailAPI(context, empId);




            // changing above code for demo
            // display the first navigation drawer view on app launch
            Intent getIntent = getIntent();

            if (getIntent != null) {
                int fragmentPosition = getIntent.getIntExtra("fragment_position", 0);
                displayView(fragmentPosition);
            } else {

                displayView(2);
            }


            // end of change


            ivSyncIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Data sync started......", Toast.LENGTH_SHORT).show();

                }
            });


            AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(this);


            appDatabaseHelper = new AppDatabaseHelper(context);


            // callRouteApi("3936266807", this);




            getFirebaseNotificationInstance();
        }

        sendFcmToServer();
    }


    @Override
    protected void onStart() {
        super.onStart();
        requestBackGroundLocation();
    }

    public void getAddedRetailAPI(final Context context, String empId)
    {

        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);


        retailerList = appDatabaseHelper.getRetailerData();
        final Map<String, String> marketDetails = new HashMap<String, String>();


        for(int i = 0; i < retailerList.size(); i++) {
            String name = retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME);
            String id = retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);
            if(!marketDetails.containsKey(id)){
                marketDetails.put(id,name);
            }

        }

        String URL = APIConstants.ADDED_RETAIL_API+empId;



        ArrayList<HashMap<String,String>> retailList = new ArrayList<HashMap<String, String>>();

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.length() > 0) {
                    appDatabaseHelper.deleteAllSubmittedRetailerData();

                    try {
                        JSONObject jsonObjects = new JSONObject(response);
                        String data = jsonObjects.getString("data");
                        JSONArray jsonArray = new JSONArray(data);

                        HashMap<String,String> retailMap = new HashMap<String, String>();
                        for (int i = 0; i < jsonArray.length(); i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String retailName = jsonObject.getString("retailName");
                            String retailAddress = jsonObject.getString("retailAddress");
                            String retailType = jsonObject.getString("retailType");
                            String retailOwner = jsonObject.getString("retailOwner");
                            String retailPhone = jsonObject.getString("retailPhone");
                            String marketId = jsonObject.getString("marketId");

                            String marketName = marketDetails.get(marketId);

                            appDatabaseHelper.insertSubmittedRetailerData(retailName,retailOwner,retailAddress,retailPhone,retailType,marketId,marketName);
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("workforce_add_retailer", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("workforce_add_retailer", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("workforce_add_retailer", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("workforce_add_retailer", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("workforce_add_retailer", "location sending response: timeout error");
                }

                Log.d("workforce_add_retailer", "responseError:" + error.toString());
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    private void callMonthlySummeryApi() {

        final String URL = APIConstants.MONTHLY_SUMMERY_API+empId;
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
                        monthlySummery.add(object.getString("numberOfOrder"));
                        monthlySummery.add(object.getString("grandTotal"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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


        RequestQueue requestQueue = Volley.newRequestQueue(context);

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

    private void callTargetApi() {

        final String URL = APIConstants.MONTHLY_TARGET_API+empId;
        Log.d("workforce_target", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){

                    appDatabaseHelper.deleteTargetTable();
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        for(int i = 0; i < jsonArray.length(); i++ ){
                            String id = jsonArray.getJSONObject(i).getString("productId");
                            String name = jsonArray.getJSONObject(i).getString("productName");
                            String saleValues = jsonArray.getJSONObject(i).getString("saleTotalValue");
                            String target_values = jsonArray.getJSONObject(i).getString("targetTotalValue");
                            String sale_qty = jsonArray.getJSONObject(i).getString("salequantity");
                            String target_qty = jsonArray.getJSONObject(i).getString("targetquantity");
                            Log.d("workforce_target", "onResponse: "+id+" "+jsonArray.length());


                            appDatabaseHelper.insertTarget(id,name,target_qty,sale_qty,target_values,saleValues);



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

    private void callSRGradeApi() {
        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);
        appDatabaseHelper.deleteGradeTable();

        final String URL = APIConstants.GRADE_API;
        Log.d("workforce_leave_list", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        for(int i = 0; i < jsonArray.length(); i++ ){
                            String id = jsonArray.getJSONObject(i).getString("empId");
                            String name = jsonArray.getJSONObject(i).getString("empName");
                            String saleValues = jsonArray.getJSONObject(i).getString("saleValue");
                            String grade = jsonArray.getJSONObject(i).getString("grade");

                            appDatabaseHelper.insertGrades(id, name,saleValues,grade);



                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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

    private void callLeaveListApi() {

        appDatabaseHelper.deleteLeaveData();

        final String URL = APIConstants.LEAVE_API+empId;
        Log.d("workforce_leave_list", "onResponse: "+URL);


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        for(int i = 0; i < jsonArray.length(); i++ ){
                            String leave_type = jsonArray.getJSONObject(i).getString("leaveType");
                            String leave_from = jsonArray.getJSONObject(i).getString("fromDate");
                            String leave_to = jsonArray.getJSONObject(i).getString("toDate");
                            String leave_note = jsonArray.getJSONObject(i).getString("comment");
                            String leave_status = jsonArray.getJSONObject(i).getString("status");



                            appDatabaseHelper.insertLeaves(leave_type,leave_from,leave_to,leave_note,leave_status);
                            /*Leaves leaves = new Leaves(leave_type, leave_from, leave_to, leave_status, leave_note);


                            leavesArrayList.add(leaves);
*/


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }


    private void callBrandAPI() {

        String URL  = APIConstants.BRAND_API;
        new AppDatabaseHelper(context).deleteBrandData();
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length() > 0){
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        DatabaseSQLite sqLite = new DatabaseSQLite(MainActivity.this);
                        for (int i = 0; i < jsonArray.length(); i++ ){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("brandName");
                            String origin = object.getString("brandOrigin");
                            String logo = object.getString("logo");



                            sqLite.insertBrands(name, origin, logo);
                            new AppDatabaseHelper(context).insertBrands(name,origin,logo);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);



    }

    private void callContactsAPI(final Context mContext) {

        String URl = APIConstants.CONTACTS_API;
        new AppDatabaseHelper(mContext).deleteAllContacts();
        Log.d("workforce", "callContactsAPI: 1");

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length() > 0){
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");

                        for (int i = 0; i < jsonArray.length(); i++ ){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name");
                            String address = object.getString("address");
                            String phone = object.getString("phone");
                            String type = object.getString("type");

                            new AppDatabaseHelper(mContext).insertContact(name,phone,address,type);


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    private void getRetailerType() {
        retailerType.clear();
        retailerType.add("--- Select Retailer Type ---");

        String URL = APIConstants.RETAILER_TYPE+distId;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.length() > 0) {

                    try {
                        JSONObject jsonObj = new JSONObject(response);

                        String data = jsonObj.getString("data");


                        JSONArray jsonArray = new JSONArray(data);

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String retailType = jsonObject.getString("retailType");

                            retailerType.add(retailType);

                        }
                        Log.d("workforce", "onResponse: "+retailerType);


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("workforce", "getRetailerType : "+e);

                    }


                } else {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);



    }

    private void connectPrinter() {
        Gson gson = new Gson();
        String json = getSharedPreferences("Printer", MODE_PRIVATE).getString("name",null);
        btsocket = gson.fromJson(json,BluetoothSocket.class);
        if(btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);

            if (btsocket != null ){
                bt = btsocket;
            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Already connected.",Toast.LENGTH_SHORT).show();
            Log.d("workforce", "connectPrinter: "+btsocket);
        }

    }





    public  void callProductAPI(){


        appDatabaseHelper.deleteAllProductData();
        databaseSQLite.deleteAllProductData();

        final String distributor_id = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_ID);

        String URL = APIConstants.PRODUCT_API+"?distId="+distributor_id;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Log.d("workforce_product", "callProductAPI: "+URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("workforce_product", "callProductAPI: "+response);
                if(response.length() > 0){
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String data  = jsonObject.getString(APIConstants.API_KEY_PRODUCT_DATA);
                        JSONArray jsonArray = new JSONArray(data);
                        Log.d("workforce_product", "callProductAPI: "+jsonArray);


                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString(APIConstants.API_KEY_PRODUCT_ID);
                            String name = object.getString(APIConstants.API_KEY_PRODUCT_NAME);
                            String sku = object.getString(APIConstants.API_KEY_SKU);
                            String label = object.getString(APIConstants.API_KEY_PRODUCT_LABEL);
                            String category = object.getString(APIConstants.API_KEY_PRODUCT_CATEGORY);
                            String category_name = object.getString(APIConstants.API_KEY_PRODUCT_CATEGORY_NAME);
                            String desc = object.getString(APIConstants.API_KEY_PRODUCT_SHORT_DESC);
                            String measuring_type = object.getString(APIConstants.API_KEY_PRODUCT_MEASURING_TYPE);
                            String price = object.getString(APIConstants.API_KEY_PRODUCT_PRICE);
                            String mrp = object.getString(APIConstants.API_KEY_PRODUCT_MRP);
                            String starting_stock = object.getString(APIConstants.API_KEY_PRODUCT_STARTING_STOCK);
                            String safety_stock = object.getString(APIConstants.API_KEY_PRODUCT_SAFETY_STOCK);
                            String discount_type = object.getString(APIConstants.API_KEY_PRODUCT_DISCOUNT_TYPE);
                            String available_discount = object.getString(APIConstants.API_KEY_PRODUCT_AVAILABLE_DISCOUNT);
                            String discount = object.getString(APIConstants.API_KEY_PRODUCT_DISCOUNT);
                            String available_offer = object.getString(APIConstants.API_KEY_PRODUCT_AVAILABLE_OFFER);
                            String offer = object.getString(APIConstants.API_KEY_PRODUCT_OFFER);
                            String image = object.getString(APIConstants.API_KEY_PRODUCT_IMAGE);
                            String on_hand = object.getString(APIConstants.API_KEY_PRODUCT_ON_HAND);
                            String brandName = object.getString("brandName");




                            appDatabaseHelper.insertProductData(id,name,sku,label,category,category_name,desc,
                                    measuring_type,price,mrp,starting_stock,safety_stock,discount_type,available_discount,
                                    discount,available_offer,offer,image, on_hand, brandName);


                            cnt++;
                            //attendanceInitialization();

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                //appDatabaseHelper.insertAttendance(attendanceId, attendanceDate, inTime, inAddress, outTime, outAddress, status, sentStatus, comment);
            }


        }
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
        apiCall.getAttendanceAPI(context,uid,fromDate,toDate);

        //checkAttendance();


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



    private void checkAttendance() {

        attendanceList = appDatabaseHelper.getAttendanceList();
        Calendar instance = Calendar.getInstance();
        int day = instance.get(Calendar.DAY_OF_MONTH);




        if (attendanceList.get(day - 1).get(AppDatabaseHelper.COLUMN_ATTENDANCE_IN_TIME).equalsIgnoreCase("--")) {
           // not yet done check in, so do it
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

            //Uncomment the below code to Set the message and title from the strings.xml file

            //Setting message manually and performing action on button click
            builder.setMessage(getResources().getString(R.string.attendance_dialog_title_main_activity))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.alert_dialog_positive), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //startActivity(new Intent(getApplicationContext(),Attendance.class));
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.alert_dialog_negative), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            AlertDialog alert = builder.create();
            alert.setIcon(R.drawable.call_scheduling);
            alert.setTitle(getResources().getString(R.string.attendance_dialog_title_main_activity));
            alert.show();


        }


    }

    private void changeLocale(String language) {
//        if (language == "bn"){
//            tv_language.setText("বাংলা");
//        }else {
//            tv_language.setText("English");
//        }

        Log.d("change", "onClick: "+language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(context,LoadingActivity.class);
        intent.putExtra("activity", "main");
        startActivity(intent);


    }

    private void getFirebaseNotificationInstance() {
//        FirebaseInstanceId.getInstance().getToken();
        // Get token

    }




    private void displayView(int position) {
        Log.d("displayView", "onCreate: "+position);
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                String userType = sharedpreferences.getString(USER_TYPE, AppUtil.USER_TYPE_ORDER);
                if (userType.equalsIgnoreCase(AppUtil.USER_TYPE_MR)) {
                    fragment = new HomeFragmentMR();
                } else if (userType.equalsIgnoreCase(AppUtil.USER_TYPE_SALES)) {
                    fragment = new HomeFragmentSR();
                } else if (userType.equalsIgnoreCase(AppUtil.USER_TYPE_ORDER)) {
                    fragment = new HomeFragmentOrder();
                }

                title = getString(R.string.title_home_fragment);

                break;
            case 1:

                title = getString(R.string.title_profile);
                fragment = new ProfileFragment();
                break;

            case 2:
                fragment = new AttendanceDetailsFragment();
                title = getString(R.string.title_attendence);

                break;

            case 3:
                fragment = new TaskListFragment();
                title = getString(R.string.title_task_list);

                break;

            case 4:
                fragment = new NotificationsFragment();
                title = getString(R.string.title_notifications);

                break;

            case 5:
                fragment = new MessagingFragment();
                title = getString(R.string.title_messaging);

                break;
            case 6:
                fragment = new ExpensesFragment();
                title = getString(R.string.expenses);

                break;
            case 7:
                fragment = new CallSchedulerFragment();
                title = getString(R.string.title_call_scheduler);

                break;

            case 8:
                fragment = new ContactsFragment();
                title = getString(R.string.title_contacts);

                break;
            case 9:
                // fragment = new SyncDataFragment();

                //fragment = new ProductFragment();
                fragment = new BrandDirectoryFragment();
                title = getString(R.string.title_product_directory);

                break;
            case 10:
                fragment = new SettingsFragment();
                title = getString(R.string.title_settings);

                break;

            case 11:
                fragment = new ComplainFragment();
                title = getString(R.string.title_complain);

                break;

            case 12:
                fragment = new Leave();
                title = getString(R.string.title_leave);
                break;

            case 13:
                fragment = new OrderDetailsFragment();
                title = getString(R.string.orders_list);
                break;




            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title

            // getSupportActionBar().setTitle(title);

            tvHomeToolbarTitle.setText(title);

        }
    }


    @Override
    public void onDrawerItemSelected(View view, int position) {

        displayView(position);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("_azaz", "on destroy ");

    }


    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
                String lastTime = dateFormat2.format(new Date()).toString();
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(LAST_CHECKED_IN, lastTime);
                editor.commit();
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Intent serviceIntent = new Intent(getApplicationContext(), AppService.class);
                        startService(serviceIntent);

                        requestBackgroundLocationPermission();
                    }else {
                        requestBackgroundLocationPermission();
                    }

                    // permission was granted
                    if (!checkRunningService.isServiceRunning(GPSTracker.class)) {
                        startService(new Intent(getApplicationContext(), GPSTracker.class));
                    }
                    if (!checkRunningService.isServiceRunning(SalesForceService.class)) {
                        startService(new Intent(getApplicationContext(), SalesForceService.class));
                    }

                    break;
                } else {
                    break;
                    // permission denied,
                }

            }


            case PERMISSION_REQUEST_BACKGROUND_LOCATION: {
                boolean foreground = false, background = false;

                for (int i = 0; i < permissions.length; i++) {
                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //foreground permission allowed
                        if (grantResults[i] >= 0) {
                            foreground = true;
                            Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                            continue;
                        } else {
                            Toast.makeText(getApplicationContext(), "Location Permission denied onRequestPermissionResult", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                        if (grantResults[i] >= 0) {
                            foreground = true;
                            background = true;
                            Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
//                            Toast.makeText(getApplicationContext(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                            break;
                        }

                    }
                    break;
                }

                break;

//                if (foreground) {
//                    if (background) {
//                        handleLocationUpdates(getApplicationContext());
//                    } else {
//                        handleForegroundLocationUpdates(getApplicationContext());
//                    }
//                }
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void logOut(View view) {
        SharedPreference sharedPreference = SharedPreference.getInstance(getApplicationContext());
        sharedPreference.setIsLoggedIn(false);

        SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USER_TYPE, null);
        editor.putString(USER_ID, null);
        editor.putString(USER_PASS,null);
        editor.putString(LAST_CHECKED_IN, null);
        editor.putString(DIST_ID, null);
        editor.putString(LAST_ACTIVITY, null);
        editor.commit();
        Intent intent = new Intent(MainActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }
    private String jsonFormatFcmToken(Context context, String employeeId, String token){

        HashMap<String, String> params = new HashMap<>();

        params.put("employeeId", employeeId);
        params.put("token", token);

        JSONObject jsonObject = new JSONObject(params);

        Log.d("_fcm_", "jsobBody : "+jsonObject.toString());

        return jsonObject.toString();
    }

    private void sendFcmToServer(){
        Log.d("_fcm_", "******* sendFCMTokenToServer called : MainActivity ******");
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if(!sessionManager.isTokenSend()){
            SharedPreferences preferences = getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
            String token = preferences.getString(Constants.FIREBASE_TOKEN, null);
//            String employeeId = preferences.getString(USER_ID,null);
            String employeeId = sessionManager.getUserId();

            if ((!TextUtils.isEmpty(token)) && (!TextUtils.isEmpty(employeeId))){
                String jsonBody = jsonFormatFcmToken(getApplicationContext(), employeeId, token);

                String TOKEN_API = APIConstants.TOKEN_API;
                VolleyMethods volleyMethods = new VolleyMethods();

                volleyMethods.sendPostRequestToServer(getApplicationContext(), TOKEN_API, jsonBody, new VolleyCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("_fcm_", "fcm token send response : "+result);
                        sessionManager.isTokenSend(true);
                    }
                });
            }
        }
    }



    public void requestBackgroundLocationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
            boolean foreground = ActivityCompat.checkSelfPermission(mActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (foreground) {

                boolean background = ActivityCompat.checkSelfPermission(mActivity,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

                if (background) {
                    handleLocationUpdates(context);
                } else {
                    ActivityCompat.requestPermissions(mActivity,
                            new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION);
                }

            } else {
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q){
//
//            }
                ActivityCompat.requestPermissions(mActivity,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION);

            }
        }
    }

    public void handleLocationUpdates(Context mContext) {
        //foreground and background
        Toast.makeText(mContext,"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
    }

    public void handleForegroundLocationUpdates(Context mContext) {
        //handleForeground Location Updates
        Toast.makeText(mContext,"Start foreground location updates", Toast.LENGTH_SHORT).show();
    }



    public void startAlert(int intervalSec) {
        Intent intent = new Intent(this, AppBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + (2 * 1000), intervalSec
                , pendingIntent);
//
//            Toast.makeText(this, "Alarm will set in " + intervalSec + " seconds",
//                    Toast.LENGTH_LONG).show();

//            alarmManager.cancel(pendingIntent);
    }

    public void requestBackGroundLocation(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, PERMISSION_REQUEST_BACKGROUND_LOCATION);
        }
    }

}
