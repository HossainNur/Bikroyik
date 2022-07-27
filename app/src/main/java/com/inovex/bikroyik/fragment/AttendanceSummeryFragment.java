package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static com.inovex.bikroyik.activity.SplashActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.SplashActivity.USER_ID;


public class AttendanceSummeryFragment extends Fragment {


    TextView present, absent, early, late, leave, not;
    String empId;
    HashMap<String, String> summeryDetails = new HashMap<String, String>();

    public AttendanceSummeryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        empId = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString(USER_ID, null);
        Log.d("workforce_attendance", "onCreate: ");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendance_summery, container, false);
        Log.d("workforce_attendance", "onCreateView: "+summeryDetails);
        present = view.findViewById(R.id.present);
        absent = view.findViewById(R.id.absent);
        early = view.findViewById(R.id.early_out);
        late = view.findViewById(R.id.late_in);
        leave = view.findViewById(R.id.leave);
        not = view.findViewById(R.id.attendance_null);


        callAttendanceSummeryAPi();

            /*String type = null, count = null;
            for (Map.Entry<String,String> entry : summeryDetails.entrySet()){
                type = entry.getKey();
                count = entry.getValue();
            }*/



        return view;
    }

    private void callAttendanceSummeryAPi() {

        String URL = APIConstants.ATTENDANCE_SUMMERY+empId;

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if ( response.length() > 0){
                    try {

                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        for(int i = 0; i < jsonArray.length(); i++ ){
                            String count = jsonArray.getJSONObject(i).getString("count");
                            String attendance_type = jsonArray.getJSONObject(i).getString("attendanceType");
                            Log.d("workforce_attendance", "onResponse: "+count+" "+attendance_type);
                            if(attendance_type.equalsIgnoreCase("present")){
                                present.setText(count);
                            } else if(attendance_type.equalsIgnoreCase("absent")){
                                absent.setText(count);
                            } else if(attendance_type.equalsIgnoreCase("Early Out")){
                                early.setText(count);
                            } else if(attendance_type.equalsIgnoreCase("Late In")){
                                late.setText(count);
                            } else if(attendance_type.equalsIgnoreCase("Leave")){
                                leave.setText(count);
                            } else {
                                not.setText(count);
                            }


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        requestQueue.add(stringRequest);



    }
}