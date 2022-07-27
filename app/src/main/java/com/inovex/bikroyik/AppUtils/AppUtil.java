package com.inovex.bikroyik.AppUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DELL on 8/12/2018.
 */

public class AppUtil {


    public static final String USER_TYPE_SALES = "sr";
    public static final String USER_TYPE_ORDER = "de";
    public static final String USER_TYPE_MR = "mr";


    public static String ORDER_DISCOUNT = "0";


    public static final String LOG_TAG = "AppUtil ";
    public static final String DIR_IMAGE = "image";
    public static final String DIR_AUDIO = "audio";
    public static final String DIR_VIDEO = "video";
    public static final String DIR_DOC = "pdf";


    // method for getting Dir using dirName
    public static File getFileStorageDir(Context context, String fileType) {

        String directoryName = getDirName(fileType);
        File file = new File(context.getExternalFilesDir(
                directoryName), directoryName);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(LOG_TAG, "Directory not created");
            }
        }
        return file;
    }

    public static String getDirName(String fileType) {

        if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg")
                || fileType.equalsIgnoreCase("png")) {
            return  Environment.DIRECTORY_PICTURES;
        } else if (fileType.equalsIgnoreCase("mp3")) {
            return  Environment.DIRECTORY_MUSIC;
        } else if (fileType.equalsIgnoreCase("mp4")) {
            return  Environment.DIRECTORY_MOVIES;
        } else if (fileType.equalsIgnoreCase("apk")) {
            return  Environment.DIRECTORY_DOCUMENTS;
        } else {
            return  Environment.DIRECTORY_DOCUMENTS;
        }
    }

    public static String getIntentViewType(String fileType) {

        if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg")
                || fileType.equalsIgnoreCase("png")) {
            return  "image/*";
        } else if (fileType.equalsIgnoreCase("mp3")) {
            return  "audio/*";
        } else if (fileType.equalsIgnoreCase("mp4")) {
            return  "video/*";
        }
        else if (fileType.equalsIgnoreCase("pdf")) {
            return  "application/*";
        }else {
            return  "*/*";
        }
    }


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


    public static String convertStringToDateTimeString(String timeStamp) {
        String dateTime = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = Long.parseLong(timeStamp);
        Date date = new Date(timestamp);
        dateTime = dateFormat.format(date);
        return dateTime;

    }
}
