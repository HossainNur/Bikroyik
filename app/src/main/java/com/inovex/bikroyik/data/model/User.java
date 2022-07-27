package com.inovex.bikroyik.data.model;

public class User {
    String orgId, userId, password, userName, userAddress, userPhone;
    String userCategory, userReportingId, reportingName, distributorId, distributorName, distributorAddress;
    String reportingMobile, territoryName, areaName, regionName, imageName, distributorMobile;

    public User(String orgId, String userId, String password, String userName, String userAddress, String userPhone,
                String userCategory, String userReportingId, String reportingName, String distributorId,
                String distributorName, String distributorAddress, String reportingMobile, String territoryName,
                String areaName, String regionName, String imageName, String distributorMobile)
    {
        this.orgId = orgId;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userPhone = userPhone;
        this.userCategory = userCategory;
        this.userReportingId = userReportingId;
        this.reportingName = reportingName;
        this.distributorId = distributorId;
        this.distributorName = distributorName;
        this.distributorAddress = distributorAddress;
        this.reportingMobile = reportingMobile;
        this.territoryName = territoryName;
        this.areaName = areaName;
        this.regionName = regionName;
        this.imageName = imageName;
        this.distributorMobile = distributorMobile;
    }



    public User(){}


    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(String userCategory) {
        this.userCategory = userCategory;
    }

    public String getUserReportingId() {
        return userReportingId;
    }

    public void setUserReportingId(String userReportingId) {
        this.userReportingId = userReportingId;
    }

    public String getReportingName() {
        return reportingName;
    }

    public void setReportingName(String reportingName) {
        this.reportingName = reportingName;
    }

    public String getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(String distributorId) {
        this.distributorId = distributorId;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getDistributorAddress() {
        return distributorAddress;
    }

    public void setDistributorAddress(String distributorAddress) {
        this.distributorAddress = distributorAddress;
    }

    public String getReportingMobile() {
        return reportingMobile;
    }

    public void setReportingMobile(String reportingMobile) {
        this.reportingMobile = reportingMobile;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDistributorMobile() {
        return distributorMobile;
    }

    public void setDistributorMobile(String distributorMobile) {
        this.distributorMobile = distributorMobile;
    }
}
