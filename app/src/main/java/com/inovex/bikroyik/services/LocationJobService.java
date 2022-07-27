package com.inovex.bikroyik.services;

import android.Manifest;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.DeviceLocationForJobService;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.inovex.bikroyik.AppUtils.Constants.isNetworkAvailable;


public class LocationJobService extends JobService {

    AppDatabaseHelper appDatabaseHelper;
    String imeiOne = "";
    String imeiTwo = "";
    Context mContext;
    final String DEVICE_INFO_SENT_KEY_STRING = "device_info";



    @Override
    public boolean onStartJob(JobParameters params) {
        Log.v("_sf_", " LocationJobService onStartJob  called ");

        mContext = getApplicationContext();
        appDatabaseHelper = new AppDatabaseHelper(getApplicationContext());
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String userID =  sessionManager.getUserId();
        DeviceLocationForJobService.LocationResult locationResult = new DeviceLocationForJobService.LocationResult() {
            @Override
            public void gotLocation(Location location) {
                Log.d("_sf_", "location : "+location+"");
                long minDifference = Constants.compareTwoTimeStamps(new Timestamp(System.currentTimeMillis()), new Timestamp(sessionManager.getTimeInMilliSec()));
                Log.d("_sf_", "location after :"+minDifference+"minute");
                if (location != null) {

                    if (minDifference > 13 || sessionManager.isFirstTimeStore()){
                        sessionManager.isFirstTimeStore(false);
                        Log.d("_sf_", "location count after :"+minDifference+"minute");
                        Log.v("_sf_", "  counter OK, location result lat: " + location.getLatitude() + " long: " + location.getLongitude());

                        String date = Constants.getTodayDateString();

                        appDatabaseHelper.insertBackgroundLocationData(userID, location.getLatitude() + "", location.getLongitude() + "", getCheckInAddress(location, getApplicationContext()));
                        new AsyncTaskRunner().execute();
                    }

                } else {
                    if (minDifference > 13 || sessionManager.isFirstTimeStore()){
                        sessionManager.isFirstTimeStore(false);
                        Log.d("_sf_", "location count after :"+minDifference+"minute");
                        // adding for test purpose
                        Log.v("_sf_", "  counter , location null ");

                        String date = Constants.getTodayDateString();
                        String timestamp = new Date().getTime() + "";
//                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        Log.d("_sf_", "timestamp : "+timestamp);
//                    Log.d("_mdm_", "timestampToTime : "+timestamp.getTime());
                        if (imeiOne.length() > 5) {
                            appDatabaseHelper.insertSendLocationData(userID,"0000000", "00000", "null");

                        }
                        new AsyncTaskRunner().execute();
                    }

                }
            }
        };


        if (sessionManager.getTimeInMilliSec() <= 10){
            sessionManager.isFirstTimeStore(true);
            sessionManager.setTimeInMilliSec(System.currentTimeMillis());
        }

        DeviceLocationForJobService myLocation = new DeviceLocationForJobService();
        myLocation.getLocation(getApplicationContext(), locationResult);

        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                Looper.loop();
            }
        }).start();

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.v("_sf_", " LocationJobService onStopJob  called ");
        return false;
    }


    public class AsyncTaskRunner extends AsyncTask<String, String, String> {

        SessionManager sessionManager = new SessionManager(getApplicationContext());

        @Override
        protected String doInBackground(String... strings) {
            Log.v("_sf_", "do in background, Network: " + isNetworkAvailable(getApplicationContext()));


            if (isNetworkAvailable(getApplicationContext())) {

                // if network available then collect from database and send data to server

                String userID =  sessionManager.getUserId();
                ArrayList<HashMap<String, String>> locationDataList = appDatabaseHelper.getBackgroundLocationData();
                if (locationDataList != null && locationDataList.size() > 0) {
                    // sendLocationData(arrayList);

                    Log.v("_sf_", "getSendLocationData not null");
                    String requestBody = prepareBackgroundLocationJson(locationDataList);

                    if (!TextUtils.isEmpty(requestBody)){
                        Log.v("_sf_", "json body not null");
                        sendLocationDataToServer(getApplicationContext(), requestBody);

                    }else {
                        Log.v("_sf_", "json body not null");
                        sendLocationDataToServer(getApplicationContext(), requestBody);
                    }



                } else {
                    Log.v("_sf_", "getSendLocationData is null");
                }

            }


            return "nothing";
        }

        @Override
        protected void onPreExecute() {
            Log.v("_sf_", "onPre execute called");


        }

        @Override
        protected void onPostExecute(String s) {

            Log.v("_sf_", "onPre execute called");

        }

        private String prepareBackgroundLocationJson(ArrayList<HashMap<String, String>> locationDataList){

            JSONArray jsonArray = new JSONArray();

            for (int a = 0; a < locationDataList.size(); a++) {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("empId", locationDataList.get(a).get("user_id"));
                    jsonObject.put("latitude", locationDataList.get(a).get("latitude"));
                    jsonObject.put("longitude", locationDataList.get(a).get("longitude"));
                    jsonObject.put("address", locationDataList.get(a).get("address"));

                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            if (jsonArray != null && jsonArray.length() > 0){
                return jsonArray.toString();
            }else {
                return null;
            }
        }

    }


    private void sendLocationDataToServer(Context mContext, final String requestBody) {

        Log.d("_sf_", "background location jsonBody : "+ requestBody);

        String LOCATION_API = APIConstants.LOCATION_API_FOR_LIST_OF_DATA;

        VolleyMethods volleyMethods = new VolleyMethods();

        volleyMethods.sendPostRequestToServer(mContext, LOCATION_API, requestBody, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_sf_", "LocationJobService ---->>> background send location response :"+result);
                JSONObject jsonObject = null;
                try {
                     jsonObject = new JSONObject(result);

                    if (jsonObject != null){
                        long statusCode = jsonObject.getLong("statusCode");

                        if (statusCode == 201){
                            appDatabaseHelper.deleteAllDataFromBackgroundLocationTable();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    private String getCheckInAddress(Location location, Context mContext) {
        String locationAddress = "a";
        if (location != null) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();


            Log.v("_sf", "lat: " + latitude + " long: " + longitude);


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(mContext, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // On
                    Log.v("_sf", "address : " + address + "\n city: " + city + " state: " + state + " country: " + country + " knownName: " + knownName);

                    locationAddress = address;

                } else {
                    Toast.makeText(mContext, "Location not found, Try again  ", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Location not found, Try again  ", Toast.LENGTH_SHORT).show();

            }


        }

        return locationAddress;
    }

}

