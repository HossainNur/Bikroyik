package com.inovex.bikroyik.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

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
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.AppUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL on 8/12/2018.
 */

public class SalesForceService extends Service {

    GPSTracker gps;
    Context mContext;
    SalesForceServiceBinder salesForceServiceBinder = new SalesForceServiceBinder();
    int counter = 0;
    private SharedPreferences mSharedPrefs;
    ArrayList<HashMap<String, String>> locationDataList;
    AppDatabaseHelper appDatabaseHelper;
    ArrayList<HashMap<String, String>> scheduleDetails = new ArrayList<HashMap<String, String>>();
    ArrayList<String> timeList = new ArrayList<String>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return salesForceServiceBinder;
    }

    @Override
    public void onCreate() {
        mContext = getApplicationContext();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();
        final ScheduledExecutorService locationScheduler =
                Executors.newSingleThreadScheduledExecutor();

        locationDataList = new ArrayList<>();
        if (appDatabaseHelper == null) {
            appDatabaseHelper = new AppDatabaseHelper(mContext);
        }
        gps = new GPSTracker(getApplicationContext());
        scheduleDetails = appDatabaseHelper.getCallSchedulerData();


        locationScheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //***************************************************************
                // save location data then try to send location data from database
                //**************************************************************
                Log.v("sales force", "location Counter value :" + counter++);


                String userId=appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_ID);
                int permissionCheck = ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.READ_PHONE_STATE);
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {

                    if (userId.length()>0) {
                        //   new LocationDataTask().execute();
                        // new LocationTask().execute();

                        // check if GPS enabled
                        if (gps.canGetLocation()) {

                            double latitude = gps.getLatitude();
                            double longitude = gps.getLongitude();

                            // \n is for new line
                            Log.v("workforce", "lat: " + latitude + " long: " + longitude);
                            saveLocationDataToDatabase(mContext, latitude + "", longitude + "",userId);
                            if (AppUtil.isNetworkAvailable(mContext)) {
                                //sendLocationDataToServer();
                                collectAndSendLocationData();
                            }

                            // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                        } else {
                            // can't get location
                            // GPS or Network is not enabled
                            // Ask user to enable GPS/network in settings
                            gps.showSettingsAlert();
                        }
                    }
                }
            }

        }, 0, 5, TimeUnit.MINUTES);

        /*scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.v("workforce", "schedule : " + scheduleDetails);

            }
        }, 0, 5, TimeUnit.SECONDS);
*/

//        // code for listening settings app launch
//        boolean exit = false;
//        while(!exit)
//        {
//            ActivityManager am= (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//            ComponentName componentInfo = taskInfo.get(0).topActivity;
//            if(componentInfo.getPackageName().equals("com.android.settings")||componentInfo.getPackageName().equals("com.android.providers.settings"))
//            {
//                //Do your work here
//                Toast.makeText(getApplicationContext(),"settings launched",Toast.LENGTH_SHORT).show();
//
//            }
//        }

        return START_STICKY;
    }


    private void collectAndSendLocationData() {
        locationDataList = appDatabaseHelper.getSendLocationData();


        String allLocationString = "";


        for (int a = 0; a < locationDataList.size(); a++) {


            String singleAppString = locationDataList.get(a).get("user_id");
            singleAppString += ",";
            singleAppString += locationDataList.get(a).get("date");
            singleAppString += ",";
            singleAppString += locationDataList.get(a).get("latitude");
            singleAppString += ",";
            singleAppString += locationDataList.get(a).get("longitude");
            allLocationString += singleAppString;
            allLocationString += "#";
        }
        Log.v("sales force", allLocationString);


        // send location data to server
        // check if internet connection is available
        if (AppUtil.isNetworkAvailable(mContext)) {
            sendLocationDataToServer(mContext, allLocationString);

        }

    }


    private void sendLocationDataToServer(Context mContext, final String allLocationString) {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String URL = APIConstants.LOCATION_SENDING_API;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("updated") || response.equalsIgnoreCase("success")) {
                    if (response.equalsIgnoreCase("success")) {

                        // delete all data from location table
                        appDatabaseHelper.deleteAllDataFromLocationTable();
                    }

                }
                Log.d("sales force", "sending location data response:" + response.toString());

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

                Log.d("sending_error", "location sending responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("locationinfo", allLocationString);
                Log.v("sales force", allLocationString);


                return params;
            }
        };
        queue.add(stringRequest);
    }


    private void saveLocationDataToDatabase(Context mContext, String locationLatitude, String locationLongitude,String userId) {

        String todayDateString = getTodayDateString();
        appDatabaseHelper.insertSendLocationData(todayDateString, locationLatitude, locationLongitude,userId);

    }

    public String getTodayDateString() {
        String todayDateString = "";
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        todayDateString = year + "-" + month + "-" + day;
        return todayDateString;

    }

    private String checkLocationEnabledOrNot(Context mContext) {
        String locationStatus = "";
        boolean GpsStatus = false;

        try {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            GpsStatus = false;
        }

        if (GpsStatus) {
            locationStatus = "Enabled";
        } else {
            locationStatus = "Disabled";
        }
        return locationStatus;
    }


    public class SalesForceServiceBinder extends Binder {

        public SalesForceService getService() {
            return SalesForceService.this;
        }
    }
}
