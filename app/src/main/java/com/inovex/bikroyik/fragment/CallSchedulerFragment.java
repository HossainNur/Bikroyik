package com.inovex.bikroyik.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.CallScheduler;
import com.inovex.bikroyik.adapter.CallSchedulerAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 8/1/2018.
 */


public class CallSchedulerFragment extends Fragment {

    private List<CallScheduler> callSchedulerList = new ArrayList<>();
    ArrayList<HashMap<String, String>> contactsList = new ArrayList<>();
    ArrayList<String> contactsNameList = new ArrayList<>();
    RecyclerView schedulerRecyclerView;
    CallSchedulerAdapter scheduleListAdapter;
    //TextView tvAddTaskList;
    FloatingActionButton fabAddSchedule;
    Context mContext;
    AppDatabaseHelper appDatabaseHelper;
    int selectedContactsPositionInList = 0;
    public String selectedName = "";
    public String selectedNumber = "";
    public String scheduleDate = "";
    public String scheduleTime = "";
    public static final String CHANNEL_ID = "12348";

    public CallSchedulerFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_scheduler, container, false);

        onBackPressed(view);

        mContext = getContext();


        schedulerRecyclerView = (RecyclerView) view.findViewById(R.id.scheduleList_recycler);
        scheduleListAdapter = new CallSchedulerAdapter(callSchedulerList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        schedulerRecyclerView.setLayoutManager(mLayoutManager);
        schedulerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        schedulerRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        schedulerRecyclerView.setAdapter(scheduleListAdapter);
        fabAddSchedule = view.findViewById(R.id.fabAddSchedule);


        appDatabaseHelper = new AppDatabaseHelper(mContext);
        contactsList = appDatabaseHelper.getContactsList();
        contactsNameList = getContactNameList(contactsList);




        fabAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_schedule_dialog);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);


                final TextView tvContactsName = dialog.findViewById(R.id.tvDialogContactsName);
                final TextView tvContactsNumber = dialog.findViewById(R.id.tvDialogContactsNumber);
                final LinearLayout llScheduleDateTime = dialog.findViewById(R.id.llScheduleDateTime);
                final TextView tvDateTime = dialog.findViewById(R.id.tvDateTime);

                MaterialSpinner contactsSpinner = dialog.findViewById(R.id.callScheduleSpinner);
                contactsSpinner.setDropdownMaxHeight(450);
                contactsSpinner.setItems(contactsNameList);
                contactsSpinner.setSelectedIndex(0);


                llScheduleDateTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        final Calendar currentDate = Calendar.getInstance();
                        final Calendar date = Calendar.getInstance();
                        new DatePickerDialog(mContext, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.set(year, monthOfYear, dayOfMonth);

                                if(monthOfYear < 10 && dayOfMonth < 10) {
                                    scheduleDate = "0"+dayOfMonth + "-0" + (monthOfYear+1)+ "-" + year;

                                } else if(monthOfYear < 10) {
                                    scheduleDate = dayOfMonth + "-0" + (monthOfYear+1)+ "-" + year;

                                } else if(dayOfMonth < 10){
                                    scheduleDate = "0"+dayOfMonth + "-" + (monthOfYear+1)+ "-" + year;
                                } else {
                                    scheduleDate = dayOfMonth + "-" + (monthOfYear+1)+ "-" + year;
                                }

                                new TimePickerDialog(mContext, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {



                                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                        date.set(Calendar.MINUTE, minute);

                                        if (minute < 10 && hourOfDay < 10){
                                            scheduleTime = "0"+hourOfDay + ":0" + minute;
                                        } else if(minute < 10) {
                                            scheduleTime = hourOfDay + ":0" + minute;

                                        } else if(hourOfDay < 10) {
                                            scheduleTime = "0"+hourOfDay + ":" + minute;

                                        } else {
                                            scheduleTime = hourOfDay + ":" + minute;

                                        }

                                        tvDateTime.setText(scheduleDate + " " + scheduleTime);

                                    }
                                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                            }
                        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();


                    }
                });


                contactsSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        if (position != 0) {

                            selectedName = contactsList.get(position - 1).get(AppDatabaseHelper.COLUMN_CONTACTS_TITLE);
                            selectedNumber = contactsList.get(position - 1).get(AppDatabaseHelper.COLUMN_CONTACTS_NUMBER);
                            tvContactsName.setText(selectedName);
                            tvContactsNumber.setText(selectedNumber);
                        } else {
                            selectedName = "";
                            selectedNumber = "";
                            tvContactsName.setText(selectedName);
                            tvContactsNumber.setText(selectedNumber);
                        }

                    }
                });


                window.setGravity(Gravity.CENTER);
                dialog.setCancelable(true);

                TextView tvCancelDialog = dialog.findViewById(R.id.tvCancelDialog);
                tvCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                TextView tvDoneScheduleDialog = dialog.findViewById(R.id.tvDoneScheduleDialog);
                tvDoneScheduleDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (selectedName.length() > 0 && selectedNumber.length() > 0 && scheduleDate.length() > 0 && scheduleTime.length() > 0) {
                            CallScheduler callScheduler = new CallScheduler(selectedName,selectedNumber, scheduleDate, scheduleTime);
                            callSchedulerList.add(callScheduler);
                            appDatabaseHelper.insertCallSchedule(selectedName,selectedNumber,scheduleDate,scheduleTime);
                            scheduleListAdapter.notifyDataSetChanged();
                            dialog.dismiss();

                        }

                    }
                });

                dialog.show();


            }
        });

        prepareScheduleListData();

        return view;
    }





    private ArrayList<HashMap<String, String>> getContactsListFromDataBase(Context mContext) {
        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);


        return null;
    }

    private ArrayList<String> getContactNameList(ArrayList<HashMap<String, String>> contactsList) {

        ArrayList<String> contactsNameList = new ArrayList<>();
        String hintText = "Select a contact";
        contactsNameList.add(hintText);
        for (int x = 0; x < contactsList.size(); x++) {
            String contactsName = contactsList.get(x).get(AppDatabaseHelper.COLUMN_CONTACTS_TITLE);
            contactsNameList.add(contactsName);
        }

        return contactsNameList;
    }

    private void prepareScheduleListData() {

        ArrayList<HashMap<String,String>> callSchedulerLists = new ArrayList<HashMap<String, String>>();
        callSchedulerLists = appDatabaseHelper.getCallSchedulerData();

        for (int i = 0; i < callSchedulerLists.size(); i++) {

            String name = callSchedulerLists.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_NAME);
            String phone = callSchedulerLists.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_PHONE);
            String date = callSchedulerLists.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_DATE);
            String time = callSchedulerLists.get(i).get(AppDatabaseHelper.COLUMN_CALL_SCHEDULER_TIME);

            CallScheduler callScheduler = new CallScheduler(name, phone, date, time);
            callSchedulerList.add(callScheduler);

        }

        scheduleListAdapter.notifyDataSetChanged();
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