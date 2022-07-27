package com.inovex.bikroyik.services;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.NotificationActivity;
import com.inovex.bikroyik.model.NoticeModel;
import com.inovex.bikroyik.model.NotificationFile;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.inovex.bikroyik.AppUtils.Constants.APP_PREFERENCE;
import static com.inovex.bikroyik.activity.LoginActivity.USER_ID;

/**
 * Created by ashik on 127/7/2021.
 */

public class NotificationFMService extends FirebaseMessagingService {
    private static final String TAG = "NotificationFMService";
    private static final String NOTIFICATION_ID_EXTRA = "notificationId";
    private static final String IMAGE_URL_EXTRA = "imageUrl";
    private static final String ADMIN_CHANNEL_ID = "admin_channel";
    private NotificationManager notificationManager;
    String imeiOne = "";
    String imeiTwo = "";

    public static final String CHANNEL_ID = "sf_service_channel";
    public static final String CHANNEL_ID_FOR_MESSAGE = "sf_message_channel";

    Bitmap bitmap;
    SessionManager sessionManager;

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        Log.d("_fcm_", "onNewToken is called");

        Log.d("_fcm_", "AppMessagingService onNewToken called and newToken is :"+s);
        String refreshedToken = s;
        Log.d(TAG, "New token: " + refreshedToken);
        Context mContext = getApplicationContext();


        SharedPreferences preferences = mContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.FIREBASE_TOKEN, refreshedToken);
        editor.commit();

        editor.putBoolean(Constants.TOKEN_SHOULD_SENT, true);
        editor.commit();

        String employeeId = preferences.getString(USER_ID,null);

        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setFcmToken(refreshedToken);
        sessionManager.isTokenSend(false);

//        String employeeId = sessionManager.getUserId();

        if ((!TextUtils.isEmpty(employeeId) || employeeId != null) && (!TextUtils.isEmpty(refreshedToken) || refreshedToken != null)){
            sendFcmTokenUsingCallBack(mContext, employeeId, refreshedToken);
        }

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.v(TAG, "AppMessagingService onMessageReceived called");
        createNotificationChannel();
        createNotificationChannelForMessage();


        Log.v(TAG, "remoteMessage : "+remoteMessage);
        sessionManager = new SessionManager(getApplicationContext());
        String userId = sessionManager.getUserId();

        String notice_type = remoteMessage.getData().get("notice_type");
        if (notice_type.equalsIgnoreCase("notice") || notice_type.equalsIgnoreCase("message") || notice_type.equalsIgnoreCase("notification")) {
            Log.v("_ns_", "got an "+ notice_type);


            String notice_title = remoteMessage.getData().get("notice_title");
            String description = remoteMessage.getData().get("notice_description");
            String noticeType = remoteMessage.getData().get("notice_type");
//            String imageUrl = remoteMessage.getData().get("image_url");
            String date = remoteMessage.getData().get("date");
            String priority = remoteMessage.getData().get("priority");
            String fileUrl = remoteMessage.getData().get("file_url");
            String fileType = remoteMessage.getData().get("file_type");
            String fileSize = remoteMessage.getData().get("file_size");
            String noticeId = date;
            if (noticeId == null ){
                noticeId = getCurrentTimeStamp();
            }
            String isDownload = "false";
            Timestamp ts=new Timestamp(Long.parseLong(noticeId));
            Date date1 = ts;

            AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(getApplicationContext());
            appDatabaseHelper.insertMessageData(noticeId, notice_title, noticeType, description, fileUrl, fileType,fileSize, date,priority, isDownload);


            if (!TextUtils.isEmpty(fileUrl) && !fileUrl.equalsIgnoreCase("null") && fileUrl.length() > 0){
                NoticeModel noticeModel = new NoticeModel();
                prepareFileNotification(fileType, noticeId, notice_title, description, fileUrl, date);
            }else {
                // show a simple notification
                showNotification(noticeId, notice_title, description, date);
            }
        }else {
            Log.d("_ns_", "got an unknown type message!");
        }

    }

    private void showFileNotification(String fileId, String title, String description, String fileUrl, String fileType, Bitmap bitmap) {
        Log.d("_ns_", "please show a fileNotification");
        NotificationFile notificationFile  = new NotificationFile();
        notificationFile.setFileName(title);
        notificationFile.setFileId(fileId);
        notificationFile.setDescription(description);
        notificationFile.setDownloadUrl(fileUrl);
        notificationFile.setFileType(fileType);


        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("From", "fileFrag");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_FOR_MESSAGE)
                .setSmallIcon(R.drawable.notification_64)
                .setContentTitle(title)
                .setContentText(description)
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1234, builder.build());
    }

    private String getCurrentTimeStamp() {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupChannels() {
        CharSequence adminChannelName = getString(R.string.notifications_admin_channel_name);
        String adminChannelDescription = getString(R.string.notifications_admin_channel_description);

        NotificationChannel adminChannel;
        adminChannel = new NotificationChannel(ADMIN_CHANNEL_ID, adminChannelName, NotificationManager.IMPORTANCE_LOW);
        adminChannel.setDescription(adminChannelDescription);
        adminChannel.enableLights(true);
        adminChannel.setLightColor(Color.RED);
        adminChannel.enableVibration(true);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(adminChannel);
        }
    }

    private void prepareFileNotification(String fileType, String fileId, String title, String description, String fileUrl, String date){
        if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("jpeg")
        || fileType.equalsIgnoreCase("png")) {
            Bitmap mybitmap = getBitmapFromURL(fileUrl);
            generateImageNotification(title, description, date, fileUrl, fileType, mybitmap);
        } else if (fileType.equalsIgnoreCase("pdf")) {
            Bitmap typeImg = BitmapFactory.decodeResource(getResources(), R.drawable.pdf_color);
            showFileNotification(fileId, title, description, fileUrl, fileType, typeImg);
        } else if (fileType.equalsIgnoreCase("mp3")) {
            Bitmap typeImg = BitmapFactory.decodeResource(getResources(), R.drawable.mp3_color);
            showFileNotification(fileId, title, description, fileUrl, fileType, typeImg);
        } else if (fileType.equalsIgnoreCase("mp4")) {
            Bitmap typeImg = BitmapFactory.decodeResource(getResources(), R.drawable.mp4_color);
            showFileNotification(fileId, title, description, fileUrl, fileType, typeImg);
        }else {
            showNotification(fileId, title, description, date);
        }
    }

    public void showNotification(String notice_id, String notificationTitle,  String notificationBody, String date){
//        NotificationCompat.Builder mBuilder =   new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.home_64) // notification icon
//                .setContentTitle(notificationTitle) // title for notification
//                .setAutoCancel(true); // clear notification after click
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        mBuilder.setContentIntent(pi);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(0, mBuilder.build());
//
        NoticeModel noticeModel_serializable = new NoticeModel();
        noticeModel_serializable.setNotice_id(notice_id);
        noticeModel_serializable.setNotice_title(notificationTitle);
        noticeModel_serializable.setNotice_description(notificationBody);
        noticeModel_serializable.setDateTime(date);
        noticeModel_serializable.setFileUrl("");


        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notification", noticeModel_serializable);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_FOR_MESSAGE)
                .setSmallIcon(R.drawable.notification_64)
                .setContentTitle(notificationTitle)
                .setContentText(notificationBody)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(200123, builder.build());
    }


    private String jsonFormatFcmToken(Context context, String employeeId, String token){

        HashMap<String, String> params = new HashMap<>();

        params.put("employeeId", employeeId);
        params.put("token", token);

        JSONObject jsonObject = new JSONObject(params);

        Log.d("_fcm_", "jsobBody : "+jsonObject.toString());

        return jsonObject.toString();
    }

    private void sendFcmTokenUsingCallBack(Context mContext, String employeeId, String token){
        Log.d(TAG, "******* sendFCMTokenToServer called : AppMessagingService ******");
        String jsonBody = jsonFormatFcmToken(mContext, employeeId, token);

        String TOKEN_API = APIConstants.TOKEN_API;
        VolleyMethods volleyMethods = new VolleyMethods();

        volleyMethods.sendPostRequestToServer(mContext, TOKEN_API, jsonBody, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_fcm_", result);
                sessionManager.isTokenSend(true);
            }
        });
    }

    private void sendFCMTokenToServer(final Context applicationContext, final String token) {

        Log.d(TAG, "******* sendFCMTokenToServer called : AppMessagingService ******");

       SessionManager sessionManager = new SessionManager(getApplicationContext());
       String uid = sessionManager.getUserId();

        if (uid != null && token.length() > 0) {

            Log.d(TAG, "sendFCMTokenToServer called condition ok");
            RequestQueue queue = Volley.newRequestQueue(applicationContext);
            String TOKEN_API = APIConstants.TOKEN_API;
            Log.d(TAG, "FCM  api address : " + TOKEN_API);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, TOKEN_API, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "sending token api response ** :" + response);
                    if (response.equalsIgnoreCase("updated") || response.equalsIgnoreCase("success")
                            || response.equalsIgnoreCase("already exist")) {
                        Log.d(TAG, "FCM  api: " + response);

                        SharedPreferences preferences = applicationContext.getSharedPreferences(APP_PREFERENCE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(Constants.TOKEN_SHOULD_SENT, false);
                        editor.commit();

                        sessionManager.isTokenSend(true);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    if (error instanceof NetworkError) {
                        Log.d(TAG, "sending token response: Network error");
                    } else if (error instanceof ServerError) {
                        Log.d(TAG, "sending token response: Server error");
                    } else if (error instanceof AuthFailureError) {
                        Log.d(TAG, "sending token response: Auth. error");
                    } else if (error instanceof ParseError) {
                        Log.d(TAG, "sending token response: Parse error");
                    } else if (error instanceof TimeoutError) {
                        Log.d(TAG, "sending token response: timeout error");
                    }
                    Log.d(TAG, "sending token responseError:" + error.toString());
                    error.printStackTrace();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("employeeId", uid);
                    params.put("token", token);

                    return params;
                }
            };
            stringRequest.setShouldCache(false);
            queue.add(stringRequest);
        }

    }
//    private String getSerialNumber() {
//        String serialNumber = "";
//        if (android.os.Build.VERSION.SDK_INT == 26) {
//
//            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//
//                return serialNumber;
//            }
//            serialNumber = Build.getSerial();
//        } else if (android.os.Build.VERSION.SDK_INT < 26) {
//            serialNumber = Build.SERIAL;
//        } else {
//            serialNumber = "111111";
//        }
//
//        Log.v(TAG, "device  serial *** : " + serialNumber);
//        return serialNumber;
//    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void generateImageNotification(String contentTitle, String contentBody, String date, String imgUrl, String imgType, Bitmap bitmap){

        NoticeModel noticeModel_serializable = new NoticeModel();
        noticeModel_serializable.setNotice_title(contentTitle);
        noticeModel_serializable.setNotice_description(contentBody);
        noticeModel_serializable.setDateTime(date);
        noticeModel_serializable.setNotice_id(date);
        noticeModel_serializable.setFileUrl(imgUrl);
        noticeModel_serializable.setFileType(imgType);

        Intent intent = new Intent(this, NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("notification", noticeModel_serializable);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID_FOR_MESSAGE)
                .setSmallIcon(R.drawable.notification_64)
                .setContentTitle(contentTitle)
                .setContentText(contentBody)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1234, builder.build());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mdmServiceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "MDM Service Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(mdmServiceChannel);
        } else {

        }
    }

    private void createNotificationChannelForMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mdmServiceChannel = new NotificationChannel(
                    CHANNEL_ID_FOR_MESSAGE,
                    "MDM Message",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(mdmServiceChannel);
        } else {

        }
    }
}
