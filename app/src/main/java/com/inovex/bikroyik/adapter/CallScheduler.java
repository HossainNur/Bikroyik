package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 11/11/2018.
 */

public class CallScheduler {

    String contactsName;
    String contactsNumber;
    String scheduleDate;
    String schedulerTime;

    public CallScheduler() {
    }

    public CallScheduler(String contactsName, String contactsNumber, String scheduleDate, String schedulerTime) {
        this.contactsName = contactsName;
        this.contactsNumber = contactsNumber;
        this.scheduleDate = scheduleDate;
        this.schedulerTime = schedulerTime;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsNumber() {
        return contactsNumber;
    }

    public void setContactsNumber(String contactsNumber) {
        this.contactsNumber = contactsNumber;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getSchedulrTime() {
        return schedulerTime;
    }

    public void setSchedulrTime(String schedulrTime) {
        this.schedulerTime = schedulrTime;
    }
}
