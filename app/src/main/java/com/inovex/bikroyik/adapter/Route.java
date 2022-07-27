package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 11/25/2018.
 */

public class Route {
    String routeRetailerId;
    String routeRetailerName;
    String routeRetailerOwner;
    String routeRetailerAddress;
    String routeVisitStatus;

    public Route() {
    }

    public Route(String routeRetailerId, String routeRetailerName, String routeRetailerOwner, String routeRetailerAddress, String routeVisitStatus) {
        this.routeRetailerId = routeRetailerId;
        this.routeRetailerName = routeRetailerName;
        this.routeRetailerOwner = routeRetailerOwner;
        this.routeRetailerAddress = routeRetailerAddress;
        this.routeVisitStatus = routeVisitStatus;
    }

    public String getRouteRetailerId() {
        return routeRetailerId;
    }

    public void setRouteRetailerId(String routeRetailerId) {
        this.routeRetailerId = routeRetailerId;
    }

    public String getRouteRetailerName() {
        return routeRetailerName;
    }

    public void setRouteRetailerName(String routeRetailerName) {
        this.routeRetailerName = routeRetailerName;
    }

    public String getRouteRetailerOwner() {
        return routeRetailerOwner;
    }

    public void setRouteRetailerOwner(String routeRetailerOwner) {
        this.routeRetailerOwner = routeRetailerOwner;
    }

    public String getRouteRetailerAddress() {
        return routeRetailerAddress;
    }

    public void setRouteRetailerAddress(String routeRetailerAddress) {
        this.routeRetailerAddress = routeRetailerAddress;
    }

    public String getRouteVisitStatus() {
        return routeVisitStatus;
    }

    public void setRouteVisitStatus(String routeVisitStatus) {
        this.routeVisitStatus = routeVisitStatus;
    }
}
