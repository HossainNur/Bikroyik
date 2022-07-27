package com.inovex.bikroyik.AppUtils;

import android.app.AppOpsManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Constants {
    public static final String FIREBASE_TOKEN = "firebase_token";
    public static final String TOKEN_SHOULD_SENT = "token_status";
    public static final String APP_PREFERENCE = "PaikerAppPrefs";
    public static final int LOCATION_JOB_INFO_ID = 1234;
    public static final int CONTACT_PERMISSION = 9122;
    public static final int PERMISSION_REQUEST_BACKGROUND_LOCATION = 190821;
    public static final int PERMISSION_REQUEST_LOCATION = 210821;

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static String getTodayDateString() {
        String todayDateString = "";
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        todayDateString = year + "-" + month + "-" + day;
        return todayDateString;

    }

    public static boolean hasNetworkStatPermission(Context context){

        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());
        if (mode == AppOpsManager.MODE_ALLOWED) {
            return true;
        }else {
            return false;
        }
    }

    public static String getFileNameByTimeStamp(Timestamp timeStamp){
        Date date = timeStamp;
        String[] dateSplit = date.toString().split(" ")[0].split("-");
        String[] timeSplit = date.toString().split(" ")[1].split(":");


        final String fileName = dateSplit[0]+"_"+dateSplit[1]+"_"+dateSplit[2]+"_"+timeSplit[0]+"_"+timeSplit[1];

        return fileName;
    }

    public static String notificationDateTimeFormate(Timestamp timestamp){
        //2021-8-01 02:36:57.204 y-m-d h:m:s
        Date date = timestamp;

        String[] dateSplit = date.toString().split(" ")[0].split("-");
        String[] timeSplit = date.toString().split(" ")[1].split(":");

        final String dateTime = timeSplit[0]+":"+timeSplit[1]+" "+dateSplit[2]+"/"+dateSplit[1];
        return dateTime;
    }


    public static String getAddressFromLocation(Location location, Context mContext) {
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


    public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime)
    {
        long milliseconds1 = oldTime.getTime();
        long milliseconds2 = currentTime.getTime();

        long diff = milliseconds2 - milliseconds1;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (24 * 60 * 60 * 1000);

        return diffMinutes;
    }

    public static boolean isJobIdRunning( Context context, int JobId) {
        final JobScheduler jobScheduler = (JobScheduler) context.getSystemService( Context.JOB_SCHEDULER_SERVICE ) ;

        for ( JobInfo jobInfo : jobScheduler.getAllPendingJobs() ) {
            if ( jobInfo.getId() == JobId ) {
                return true;
            }
        }

        return false;
    }

    public static boolean moveHomeFragment(Fragment fragment , FragmentActivity activity, Context mContext){

        if (fragment != null){
            MainActivity.tvHomeToolbarTitle.setText(mContext.getResources().getString(R.string.title_home_fragment));
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }


}
