package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 8/5/2018.
 */

public class Attendance {
    String attendanceDate;
    String attendanceIn;
    String attendanceOut;
    String status;

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    String attendanceId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Attendance(String attendanceDate, String attendanceIn, String attendanceOut, String status,String attendanceId) {
        this.attendanceDate = attendanceDate;
        this.attendanceIn = attendanceIn;
        this.attendanceOut = attendanceOut;
        this.status = status;
        this.attendanceId=attendanceId;
    }

    public String getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getAttendanceIn() {
        return attendanceIn;
    }

    public void setAttendanceIn(String attendanceIn) {
        this.attendanceIn = attendanceIn;
    }

    public String getAttendanceOut() {
        return attendanceOut;
    }

    public void setAttendanceOut(String attendanceOut) {
        this.attendanceOut = attendanceOut;
    }
}
