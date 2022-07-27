package com.inovex.bikroyik.model;

import android.location.Location;

public class LocationData {
    double Latitude, Longitude;
    Location location;

    public LocationData(double latitude, double longitude, Location location) {
        Latitude = latitude;
        Longitude = longitude;
        this.location = location;
    }

    public LocationData(){}

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
