package com.inovex.bikroyik.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constants {
    public static final String KEY_PRODUCT_QUANTITY = "PRODUCT_QUANTITY";
    public static final String KEY_ORDER_ID = "ORDER_ID";
    public static final String KEY_OFFER_QUANTITY = "ORDER_QUANTITY";
    public static final String KEY_INTENT_EXTRA_TOTAL_CHARGE = "key_total_charge";
    public static final String KEY_STRING_EXTRA = "STRING_EXTRA";
    public static final String KEY_OBJECT_EXTRA = "OBJECT_EXTRA";

    public static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int CAMERA_REQUEST = 1888;


    public static String getTodayDateString() {
        String todayDateString = "";
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        todayDateString = year + "-" + month + "-" + day;
        return todayDateString;
    }

    public static String getDateFromTimestamp(long timeInMillis){


        Timestamp timestamp = new Timestamp(timeInMillis);
        Date date = new Date(timestamp.getTime());

        // S is the millisecond
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM' 'HH:mm");

//        System.out.println(simpleDateFormat.format(timestamp));
//        System.out.println(simpleDateFormat.format(date));
        return simpleDateFormat.format(timestamp);
    }

    public static String convertBnToEn(String data) {
        return data.replaceAll("০", "0")
                .replaceAll("১", "1")
                .replaceAll("২", "2")
                .replaceAll("৩", "3")
                .replaceAll("৪", "4")
                .replaceAll("৫", "5")
                .replaceAll("৬", "6")
                .replaceAll("৭", "7")
                .replaceAll("৮", "8")
                .replaceAll("৯", "9");
    }

    public static String convertEnToBn(String data) {
        return data.replaceAll("0","০")
                .replaceAll("1","১" )
                .replaceAll("2","২" )
                .replaceAll("3","৩" )
                .replaceAll("4","৪")
                .replaceAll("5","৫")
                .replaceAll("6","৬")
                .replaceAll("7","৭")
                .replaceAll("8","৮")
                .replaceAll("9","৯");
    }

}
