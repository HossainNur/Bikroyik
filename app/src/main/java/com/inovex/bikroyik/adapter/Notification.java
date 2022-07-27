package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 8/2/2018.
 */

public class Notification {
    String notificationType;
    String notificationText;
    String notificationDate;
    boolean isChecked;
    String notificationId;
    String priority;

    public Notification() {
    }

    public Notification(String notificationType, String notificationText, String notificatinDate, boolean isChecked) {
        this.notificationType = notificationType;
        this.notificationText = notificationText;
        this.notificationDate = notificatinDate;
        this.isChecked = isChecked;
    }

    public boolean isChecked() {

        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        this.notificationDate = notificationDate;
    }
}
