package com.inovex.bikroyik.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.DeviceLocation;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Attendance;
import com.inovex.bikroyik.adapter.AttendanceAdapter;
import com.inovex.bikroyik.model.LocationData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.inovex.bikroyik.activity.MainActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.MainActivity.USER_ID;


/**
 * Created by DELL on 8/1/2018.
 */

public class AttendanceFragment extends Fragment {

    private List<Attendance> attendanceList = new ArrayList<>();

    RecyclerView attendanceRecycler;
    AttendanceAdapter attendanceAdapter;
    TextView tvAttendanceInSubmit;
    TextView tvAttendanceOutSubmit;
    TextView tvTopMonthText;
    TextView summery;
    AppDatabaseHelper appDatabaseHelper;
    String syncStatus = "pending";
    FloatingActionButton add_comment;
    String comment = "";
    SharedPreferences sharedpreferences;
    String empId = "";
    String inLatLon = " ", outLatLon = " ";
    int flag;

    private FusedLocationProviderClient fusedLocationClient;
    LocationData locationData = null;
    boolean changedCurrently = false;


    private ProgressDialog progressDialog;

    public AttendanceFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationData = new LocationData();

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendence, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        appDatabaseHelper = new AppDatabaseHelper(getContext());
        attendanceRecycler = (RecyclerView) view.findViewById(R.id.attendance_recycler);
        tvAttendanceInSubmit = view.findViewById(R.id.tvAttendanceInSubmit);
        tvAttendanceOutSubmit = view.findViewById(R.id.tvAttendanceOutSubmit);
        attendanceAdapter = new AttendanceAdapter(attendanceList);
        tvTopMonthText = view.findViewById(R.id.tvTopMonthText);
        add_comment = view.findViewById(R.id.floating_comment);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        empId = sharedpreferences.getString(USER_ID, null);


        add_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);

                alert.setTitle("Comment");
                alert.setMessage("Please type your comment...");

                DeviceLocation deviceLocation = new DeviceLocation();
                Location cLocation = deviceLocation.myCurrentLocation(getContext());

                final EditText input = new EditText(getContext());
                alert.setView(input);
//                getCurrentLocation(getContext());

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        // Do something with value!
                        comment = input.getText().toString();

                        Calendar instance = Calendar.getInstance();
                        int day = instance.get(Calendar.DAY_OF_MONTH);

                        // not yet done check in, so do it
                        String checkInAddress = "a";
                        if (cLocation != null) {
                            checkInAddress = getCheckInAddress(cLocation, getContext());
                        }

                        if (checkInAddress.length() > 1) {
                            appDatabaseHelper.updateAttendanceComment(comment, attendanceList.get(day - 1).getAttendanceId());
                            attendanceAdapter.notifyDataSetChanged();

                        }

                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();

            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        attendanceRecycler.setLayoutManager(mLayoutManager);
        attendanceRecycler.setItemAnimator(new DefaultItemAnimator());
        attendanceRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        attendanceRecycler.setAdapter(attendanceAdapter);
        // prepareAttendanceData();


        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        int year = cal.get(Calendar.YEAR);
        String topString = month_name + " " + year;
        tvTopMonthText.setText(topString);


        int month = cal.get(Calendar.MONTH);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        prepareAttendanceDataFromDb(days, getContext());


        tvAttendanceInSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                DeviceLocation deviceLocation = new DeviceLocation();
                Location location = deviceLocation.myCurrentLocation(getContext());

                Calendar instance = Calendar.getInstance();
                int day = instance.get(Calendar.DAY_OF_MONTH);
                if (attendanceList.get(day - 1).getAttendanceIn().equalsIgnoreCase("--")) {
                    // not yet done check in, so do it
                    String checkInAddress = getCheckInAddress(location, getContext());
                    if(location != null){
                        inLatLon = location.getLatitude() +" , " + location.getLongitude();
                        outLatLon = inLatLon;
                        Log.d("_atf_", "outLatLon: "+outLatLon);
                    }
                    if (checkInAddress.length() > 1) {
                        attendanceList.get(day - 1).setAttendanceIn(instance.get(Calendar.HOUR_OF_DAY) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND));
                        appDatabaseHelper.updateInAttendance(attendanceList.get(day - 1).getAttendanceId(), instance.get(Calendar.HOUR_OF_DAY) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND), checkInAddress, comment, inLatLon, outLatLon);
                        attendanceAdapter.notifyDataSetChanged();
                        String attendanceId = attendanceList.get(day - 1).getAttendanceId();
                        String date = "";
                        int currentMonth = instance.get(Calendar.MONTH);
                        int currentYear = instance.get(Calendar.YEAR);
                        int month = currentMonth + 1;
                        if (month < 10) {
                            //  monthAndYear = "0" + month + "-" + currentYear;
                            if(day > 10){
                                date = currentYear + "-" + "0" + month + "-" + day;
                            } else {
                                date = currentYear + "-" + "0" + month + "-" + "0" + day;

                            }
                        } else {
                            if(day > 10){
                                date = currentYear + "-" + month + "-" + day;
                            } else {
                                date = currentYear + "-"  + month + "-" + "0" + day;

                            }
                        }

                        String inTime = date + " "+ instance.get(Calendar.HOUR_OF_DAY) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND);


                        appDatabaseHelper.updateAttendanceSendingStatus(attendanceId,"in-pending");
                        attendanceList.get(day - 1).setStatus("in-pending");
                        if (Constants.isNetworkAvailable(getContext())){
                            sendingAttendanceApi(empId,date, inTime, checkInAddress,inTime,checkInAddress,inLatLon, outLatLon,comment, "in", attendanceId);
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "please check your network connection!", Toast.LENGTH_SHORT).show();
                        }

                    }
                    progressDialog.dismiss();

                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Already done earlier ! ", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvAttendanceOutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeviceLocation deviceLocation = new DeviceLocation();
                Location location = deviceLocation.myCurrentLocation(getContext());


                Calendar instance = Calendar.getInstance();
                int day = instance.get(Calendar.DAY_OF_MONTH);
                // not yet done check in, so do it

                // not yet done check in, so do it
                if(!attendanceList.get(day - 1).getAttendanceIn().equalsIgnoreCase("--")){
//                    getCurrentLocation(getContext());
                    final String checkOutAddress = getCheckInAddress(location, getContext());

                    if(location != null){
                        outLatLon = location.getLatitude() +" , " + location.getLongitude();
                        Log.d("_atn_frag_", outLatLon);
                    }else {
                        Log.d("_atn_frag_", "doesn't got location");
                    }
                    if (checkOutAddress.length() > 1) {

                        final String attendanceId = attendanceList.get(day - 1).getAttendanceId();
                        String date = "";
                        int currentMonth = instance.get(Calendar.MONTH);
                        int currentYear = instance.get(Calendar.YEAR);
                        int month = currentMonth + 1;
                        if (month < 10) {
                            //  monthAndYear = "0" + month + "-" + currentYear;
                            if(day > 10){
                                date = currentYear + "-" + "0" + month + "-" + day;
                            } else {
                                date = currentYear + "-" + "0" + month + "-" + "0" + day;

                            }
                        } else {
                            if(day > 10){
                                date = currentYear + "-" + month + "-" + day;
                            } else {
                                date = currentYear + "-"  + month + "-" + "0" + day;

                            }
                        }
                        final  String finalDate = date;

                        final String inTime = date + " "+ instance.get(Calendar.HOUR_OF_DAY) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND);

                        final String checkInAddress = appDatabaseHelper.getAttendanceList().get(day-1).get(AppDatabaseHelper.COLUMN_ATTENDANCE_IN_ADDRESS);
                        attendanceList.get(day - 1).setAttendanceOut(instance.get(Calendar.HOUR_OF_DAY) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND));




                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_attendance);
                        final Window window = dialog.getWindow();
                        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                        final EditText anyComment = dialog.findViewById(R.id.dialog_comment_attendance);
                        final Button submit = dialog.findViewById(R.id.dialog_submit_attendance);
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sendingAttendanceApi(empId,finalDate, inTime, checkInAddress,inTime,checkOutAddress,inLatLon, outLatLon,comment, "out", attendanceId);
                                dialog.dismiss();
                            }
                        });

                        if(flag == 2) {
                            appDatabaseHelper.updateOutAttendance(attendanceList.get(day - 1).getAttendanceId(), instance.get(Calendar.HOUR_OF_DAY) + ":" + instance.get(Calendar.MINUTE) + ":" + instance.get(Calendar.SECOND), checkOutAddress, comment, inLatLon, outLatLon);
                        }

                        dialog.show();
                    }
                }




            }
        });


        return view;
    }




    public boolean sendingAttendanceApi(final String employeeId, final String date, final String inTime, final String inAddress,
                                        final String outTime, final String outAddress, final String inLatLon, final String outLatLon, final String comment, final String attendance_type, final String attendanceId) {

        final Calendar calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        Log.d("workforce", "sendingAttendanceApi: ");

        String URL = APIConstants.ATTENDANCE_API;
        RequestQueue retailerQueue = Volley.newRequestQueue(getContext());
        // prepare the Request
        flag = 0;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.d("workforce_attendance", "getBody: "+response);

                long statusCode = 0;
                String message = "";
                String status = "";
                try {
                    status = response.getString("status");
                    statusCode = response.getLong("statusCode");
                    message = response.getString("message");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (statusCode == 200 || statusCode == 201) {
                    Toast.makeText(getContext(), "Successfully Done.", Toast.LENGTH_SHORT).show();

                    appDatabaseHelper.updateAttendanceSendingStatus(attendanceId, status);
                    attendanceList.get(day).setStatus(status);

                    attendanceAdapter.notifyDataSetChanged();

                    if(attendance_type.equalsIgnoreCase("in"))
                        flag = 1;
                    else
                        flag = 2;


                }else if (statusCode == 500){
                    Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof NetworkError) {
                    Log.d("attendance response err", "attendance  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("attendance response", "attendance response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("attendance response", "attendance response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("attendance response", "attendance response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("attendance response", "attendance response: timeout error");
                }

                Log.d("attendance response", "attendance responseError:" + error.toString());
                error.printStackTrace();

            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject params = new JSONObject();
                String body = null;
                try {
                    params.put(APIConstants.API_KEY_ATTENDANCE_EMPLOYEE_ID, employeeId);
                    params.put(APIConstants.API_KEY_ATTENDANCE_IN_TIME, inTime);
                    params.put(APIConstants.API_KEY_ATTENDANCE_OUT_TIME, outTime);
                    params.put(APIConstants.API_KEY_ATTENDANCE_IN_ADDRESS, inAddress);
                    params.put(APIConstants.API_KEY_ATTENDANCE_OUT_ADDRESS, outAddress);
                    params.put(APIConstants.API_KEY_ATTENDANCE_DATE, date);
                    params.put(APIConstants.API_KEY_ATTENDANCE_COMMENT, comment);
                    params.put("attendanceType",attendance_type);
                    params.put("lat",inLatLon);
                    params.put("lng",outLatLon);



                    body = params.toString();
                    Log.d("workforce", "getBody: "+body);

                }catch (JSONException e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }


                return body.getBytes(StandardCharsets.UTF_8);            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<String, String>();
                header.put("Content-Type", "application/json");
                return super.getHeaders();            }
        };
        retailerQueue.add(jsonObjectRequest);

        if(flag == 0)
            return false;
        else
            return true;


    }



    private String getCheckInAddress(Location gps, Context mContext) {
        String locationAddress = "a";
        if (gps != null) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();


            Log.v("_sf", "lat: " + latitude + " long: " + longitude);


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(mContext, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 5); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                if (addresses.size() > 0) {
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName(); // On
                    Log.v("_sf", "address : " + address + "\n city: " + city + " state: " + state + " country: " + country + " knownName: " + knownName);

                    locationAddress = address;

                } else {
                    Toast.makeText(mContext, "Location not found, Try again  ", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                progressDialog.dismiss();
                e.printStackTrace();
                Toast.makeText(mContext, "Location not found, Try again  ", Toast.LENGTH_SHORT).show();

            }


        }

        return locationAddress;
    }


    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("address", strReturnedAddress.toString());
            } else {
                Log.w("address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("address", "Canont get Address!");
        }
        return strAdd;
    }

    public static void getAddressFromLocation(final double latitude, final double longitude, final Context context) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 5);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)); //.append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                        Log.v("Location Address ", result);
                    }
                } catch (IOException e) {
                    Log.e("Location Address Loader", "Unable connect to Geocoder", e);
                } finally {
                }
            }
        };
        thread.start();
    }

    private void prepareAttendanceDataFromDb(int days, Context mContext) {

        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);
        String monthAndYear = getMonthAndYear();

        //  populate attendance from database
        ArrayList<HashMap<String, String>> attendanceDbList = new ArrayList<>();
        attendanceDbList = appDatabaseHelper.getAttendanceList();
        attendanceList.clear();
        for (int x = 0; x < attendanceDbList.size(); x++) {

            String attendanceId = attendanceDbList.get(x).get(AppDatabaseHelper.COLUMN_ATTENDANCE_ID);
            String attendanceInTime = attendanceDbList.get(x).get(AppDatabaseHelper.COLUMN_ATTENDANCE_IN_TIME);
            String attendanceOutTime = attendanceDbList.get(x).get(appDatabaseHelper.COLUMN_ATTENDANCE_OUT_TIME);
            String attendanceStatus = attendanceDbList.get(x).get(AppDatabaseHelper.COLUMN_ATTENDANCE_STATUS);
            String attendanceDate = attendanceDbList.get(x).get(AppDatabaseHelper.COLUMN_ATTENDANCE_DATE);
            Attendance attendance = new Attendance(attendanceDate, attendanceInTime, attendanceOutTime, attendanceStatus, attendanceId);
            attendanceList.add(attendance);
        }
        attendanceAdapter.notifyDataSetChanged();

    }


    private void prepareAttendanceData() {
        Random rand = new Random();
        attendanceList.clear();
        Attendance attendance;
        String monthAndYear = getMonthAndYear();
        for (int x = 1; x <= 30; x++) {
            String date;
            if (x < 10) {
                date = "0" + x + "-" + monthAndYear;
            } else {
                date = x + "-" + monthAndYear;
            }

            Calendar instance = Calendar.getInstance();
            int day = instance.get(Calendar.DAY_OF_MONTH);
            if (day > x) {
                if (x % 7 == 0) {
                    attendance = new Attendance(date, "holiday", "holiday", "status", "a" + x);
                    attendanceList.add(attendance);
                } else {
                    attendance = new Attendance(date, "09:" + rand.nextInt(39) + ":" + rand.nextInt(59), "18:" + rand.nextInt(22) + ":" + rand.nextInt(59), "status", "a" + x);
                    attendanceList.add(attendance);
                }

            } else {
                attendance = new Attendance(date, "--", "--", "status", "5");
                attendanceList.add(attendance);
            }

        }
        attendanceAdapter.notifyDataSetChanged();

    }

    private String getMonthAndYear() {

        String monthAndYear = "";
        Calendar instance = Calendar.getInstance();
        int currentMonth = instance.get(Calendar.MONTH);
        int currentYear = instance.get(Calendar.YEAR);
        int month = currentMonth + 1;
        if (month < 10) {
            //  monthAndYear = "0" + month + "-" + currentYear;
            monthAndYear = currentYear + "-" + "0" + month;
        } else {
            //  monthAndYear = month + "-" + currentYear;
            monthAndYear = currentYear + "-" + month;
        }

        return monthAndYear;
    }

//
//    private Location getCurrentLocation(Context mContext) {
//        DeviceLocation.LocationResult locationResult = new DeviceLocation.LocationResult() {
//            @Override
//            public void gotLocation(Location location) {
//                if (location != null) {
//                    Log.d("latlong", "got location!");
//                    Log.d("latlong", "  counter OK, location result lat: " + location.getLatitude() + " long: " + location.getLongitude());
//
//                    mLocation = location;
//
//                    myCurrentLocation(location);
//                    String address = getCheckInAddress(location, getContext());
//                    Log.d("latlong", "address : " + address);
//
//                } else {
//                    // adding for test purpose
//                    Log.v("latlong", "  counter , location null ");
//                }
//            }
//        };
//
//        Log.d("latlong", "getCurrentLocation function is called!");
//        DeviceLocation myLocation = new DeviceLocation();
//        myLocation.getLocation(mContext, locationResult);
//
//        return mLocation;
//    }

    private void myCurrentLocation(Location location){
        if (location != null){
            locationData = new LocationData(location.getLatitude(), location.getLongitude(), location);
            changedCurrently = true;
        }
    }
}


