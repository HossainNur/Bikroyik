package com.inovex.bikroyik.AppUtils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.Timer;
import java.util.TimerTask;

public class DeviceLocation {

    Timer timer1;
    LocationManager lm;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context mContext;

    Location currentLocation = null;
    boolean isTimerTaskShouldCall = false;
    boolean isTimerTaskFinished = false;


    public boolean getLocation(Context context) {
        //I use LocationResult callback class to pass location value from MyLocation to user code.
        mContext = context;
        if (lm == null)
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //exceptions will be thrown if provider is not permitted.
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
//            Toast.makeText(context, "please enable your network services", Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(context, gps_enabled+" "+network_enabled,     Toast.LENGTH_LONG).show();

        //don't start listeners if no provider is enabled
        if (!gps_enabled && !network_enabled) {
            Toast.makeText(context, "please enable your location services", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gps_enabled)
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return true;
            }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
        if (network_enabled)
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListenerNetwork);

        if (this.isTimerTaskShouldCall){
            timer1 = new Timer();
            timer1.schedule(new GetLastLocation(), 1000);
            this.isTimerTaskShouldCall = false;
        }

        return true;
    }

    LocationListener locationListenerGps = new LocationListener() {
        public void onLocationChanged(Location location) {
            //   Log.v("_sell"," GetLastLocation: locationListener gps onLocationChanged");
            timer1.cancel();
            setCurrentLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerNetwork);
            isTimerTaskFinished = true;
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            Log.v("_sf_", " GetLastLocation: locationListenerNetwork onLocationChanged");
            timer1.cancel();
            setCurrentLocation(location);
            lm.removeUpdates(this);
            lm.removeUpdates(locationListenerGps);
            isTimerTaskFinished = true;
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };

    class GetLastLocation extends TimerTask {
        @Override

        public void run() {
            Log.v("_sf_", " GetLastLocation: run method called");

            //Context context = getClass().getgetApplicationContext();
            Location net_loc = null, gps_loc = null;
            if (gps_enabled) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                gps_loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            }
            if (network_enabled) {
                net_loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            }

            //if there are both values use the latest one
            if (gps_loc != null && net_loc != null) {
                if (gps_loc.getTime() > net_loc.getTime()) {
                    setCurrentLocation(gps_loc);

                    Log.v("_sf_", "My gps location: lat:" + gps_loc.getLatitude() + " long: " + gps_loc.getLatitude());


                } else {
                    setCurrentLocation(net_loc);
                    Log.v("_sf_", "My net location: lat:" + net_loc.getLatitude() + " long: " + net_loc.getLatitude());
                }
                isTimerTaskFinished = true;
                timer1.cancel();
                timer1.purge();
                return;
            }

            if (gps_loc != null) {
                setCurrentLocation(gps_loc);
                Log.v("_sf_", "My * gps location: lat:" + gps_loc.getLatitude() + " long: " + gps_loc.getLatitude());
                isTimerTaskFinished = true;
                timer1.cancel();
                timer1.purge();
                return;
            }
            if (net_loc != null) {
                setCurrentLocation(net_loc);
                Log.v("_sf_", "My *net location: lat:" + net_loc.getLatitude() + " long: " + net_loc.getLatitude());
                isTimerTaskFinished = true;
                timer1.cancel();
                timer1.purge();
                return;
            }
            setCurrentLocation(null);
            isTimerTaskFinished = true;
            Log.v("_sf_", "location result got null");
            timer1.cancel();
            timer1.purge();
        }
    }

    public Location myCurrentLocation(Context context){
        this.isTimerTaskFinished = false;
        this.isTimerTaskShouldCall = true;
        getLocation(context);

        if (!isTimerTaskShouldCall){
            while (_running()){
                getLocation(context);
            }
            return this.currentLocation;
        }
        return this.currentLocation;
    }
    private boolean _running(){
        return !isTimerTaskFinished;
    }

    private void setCurrentLocation(Location location){
        this.currentLocation = location;
    }
}



