package com.inovex.bikroyik.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.LeaveAdapter;
import com.inovex.bikroyik.adapter.Leaves;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.inovex.bikroyik.activity.MainActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.MainActivity.USER_ID;


public class Leave extends Fragment {

    FloatingActionButton add;
    SharedPreferences sharedPreferences;
    String leave_type_str;
    AppDatabaseHelper appDatabaseHelper;
    String empId;
    public  ArrayList<Leaves> leavesArrayList = new ArrayList<>();
    RecyclerView leave_recycler;
    LeaveAdapter leaveAdapter;
    public Leave() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leave, container, false);
        // Inflate the layout for this fragment

        onBackPressed(view);
        populatedData();

        add = view.findViewById(R.id.fabAddLeave);
        sharedPreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        empId = sharedPreferences.getString(USER_ID, null);
        leave_recycler = view.findViewById(R.id.leave_recycler);
        appDatabaseHelper = new AppDatabaseHelper(getContext());



        leaveAdapter = new LeaveAdapter(leavesArrayList, getContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        leave_recycler.setLayoutManager(mLayoutManager);
        leave_recycler.setItemAnimator(new DefaultItemAnimator());
        leave_recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        leave_recycler.setAdapter(leaveAdapter);





        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLeave();
            }
        });


        return view ;
    }

    private void populatedData() {

        ArrayList<HashMap<String, String>> leavesLists = new ArrayList<HashMap<String, String>>();

        leavesLists = new AppDatabaseHelper(getContext()).getLeaveData();

        Log.d("workforce_leave_list", "onCreateView: "+leavesLists);


        for(int i = 0; i < leavesLists.size(); i++){
            String leave_type = leavesLists.get(i).get(AppDatabaseHelper.COLUMN_LEAVE_TYPE);
            String leave_from = leavesLists.get(i).get(AppDatabaseHelper.COLUMN_LEAVE_FROM);
            String leave_to = leavesLists.get(i).get(AppDatabaseHelper.COLUMN_LEAVE_TO);
            String leave_note = leavesLists.get(i).get(AppDatabaseHelper.COLUMN_LEAVE_COMMENT);
            String leave_status = leavesLists.get(i).get(AppDatabaseHelper.COLUMN_LEAVE_STATUS);
            Leaves leaves = new Leaves(leave_type, leave_from, leave_to, leave_status, leave_note);
            leavesArrayList.add(leaves);
        }



    }




    private void requestLeave() {

        final TextView tvStartDate, tvEndDate;

        final TextView cancel, submit;
        Spinner leave_type;
        final EditText etComment;
        final int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        final String from = "", to = "";


        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.leave_dialog);

        tvStartDate = dialog.findViewById(R.id.leaveStartDate);
        tvEndDate = dialog.findViewById(R.id.leaveEndDate);
        etComment = dialog.findViewById(R.id.leaveComment);
        leave_type = dialog.findViewById(R.id.leave_spinner);
        submit = dialog.findViewById(R.id.tvSubmitAddLeaveDialog);
        cancel = dialog.findViewById(R.id.tvCancelLeaveDialog);

        final List<String> type = new ArrayList<String>();

        type.add("Earned Leave");
        type.add("Casual Leave");
        type.add("Sick Leave");
        type.add("Leave Without Pay");
        type.add("Maternity Leave");
        type.add("Paternity Leave");
        type.add("Special Leave");
        type.add("Compensatory Leave");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_layout,type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        leave_type.setAdapter(adapter);

        leave_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                leave_type_str = type.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String month_str = String.valueOf(month+1), day_str = String.valueOf(dayOfMonth), year_str = String.valueOf(year);
                        if(dayOfMonth < 10){
                            day_str = "0"+dayOfMonth;
                        }
                        if(month < 10){
                            month_str = "0"+ (month+1);
                        }
                        String from_date = year_str + "-" + month_str + "-" + day_str;
                        tvStartDate.setText(from_date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String month_str = String.valueOf(month+1), day_str = String.valueOf(dayOfMonth), year_str = String.valueOf(year);
                        if(dayOfMonth < 10){
                            day_str = "0"+dayOfMonth;
                        }
                        if(month < 10){
                            month_str = "0"+ (month+1);
                        }
                        String to_date = year_str + "-" + month_str + "-" + day_str;
                        tvEndDate.setText(to_date);
                    }
                }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String start = tvStartDate.getText().toString();
                String end = tvEndDate.getText().toString();
                String comment_str = etComment.getText().toString();
                if(start.length() == 0 || end.length() == 0 || comment_str.length() == 0){
                    submit.setError("Please, fill all the fields properly.");
                } else {
                    requestLeaveApi(empId, leave_type_str, start, end, comment_str);
                    dialog.dismiss();
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();




    }

    private void requestLeaveApi(final String empId, final String leave_type_str, final String start, final String end, final String comment_str) {

        String URL = APIConstants.SAVE_LEAVE;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("workforce_save_leave", "onResponse: "+response);
                try {
                    String msg = response.getString("message");

                    if(msg.equalsIgnoreCase("leave added successful")){
                        appDatabaseHelper.insertLeaves(leave_type_str,start,end,comment_str,"Pending");
                        Leaves leaves = new Leaves(leave_type_str,start,end,"Pending",comment_str);
                        leavesArrayList.add(leaves);
                        leaveAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                String body = null;
                try {
                    jsonObject.put(APIConstants.SAVE_LEAVE_EMPLOYEE_ID,empId);
                    jsonObject.put(APIConstants.SAVE_LEAVE_TYPE,leave_type_str);
                    jsonObject.put(APIConstants.SAVE_LEAVE_COMMENT,comment_str);
                    jsonObject.put(APIConstants.SAVE_LEAVE_FROM,start);
                    jsonObject.put(APIConstants.SAVE_LEAVE_TO,end);


                    body = jsonObject.toString();
                    Log.d("workforce", "getBody: "+body);

                }catch (JSONException e){
                    e.printStackTrace();
                }


                return body.getBytes(StandardCharsets.UTF_8);
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<String, String>();
                header.put("Content-Type", "application/json");
                return super.getHeaders();
            }

        };

        requestQueue.add(jsonObjectRequest);






    }

    private void onBackPressed(View view){

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("_back_", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("_back_", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    SessionManager sessionManager = new SessionManager(getContext());
                    Fragment fragment = null;
                    if (sessionManager.getEmployeeCategory().equals("SR")){
                        fragment = new HomeFragmentSR();

                    }else if (sessionManager.getEmployeeCategory().equalsIgnoreCase("DE")){
                        fragment = new HomeFragmentSR();
                    }
                    Constants.moveHomeFragment(fragment, getActivity(), getContext());
                    return true;
                }
                return false;
            }
        });
    }
}