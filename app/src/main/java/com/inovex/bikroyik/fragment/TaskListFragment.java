package com.inovex.bikroyik.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


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
import com.inovex.bikroyik.adapter.Notification;
import com.inovex.bikroyik.adapter.TaskListAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by DELL on 8/1/2018.
 */


public class TaskListFragment extends Fragment {

    private List<Notification> notificationList = new ArrayList<>();
    RecyclerView noticeRecyclerView;
    TaskListAdapter taskListAdapter;
    //TextView tvAddTaskList;
    FloatingActionButton fabAddTask;

    AppDatabaseHelper databaseHelper;

    public TaskListFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new AppDatabaseHelper(getContext());
        notificationList = databaseHelper.getAllTaskData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);


        onBackPressed(view);
        noticeRecyclerView = (RecyclerView) view.findViewById(R.id.taskList_recycler);
        taskListAdapter = new TaskListAdapter(notificationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        noticeRecyclerView.setLayoutManager(mLayoutManager);
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        noticeRecyclerView.setAdapter(taskListAdapter);

        // tvAddTaskList = view.findViewById(R.id.tvAddTaskList);
        fabAddTask = view.findViewById(R.id.fabAddTask);


        fabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Notification notification = new Notification();
                final TextView tvAddTaskSelectDate;
                TextView tvCancelNewTaskDialog;
                TextView tvDoneAddTaskDialog;
                final EditText etTaskDetails;
                final int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // show a dialog to select product
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_task_dialog);
                MaterialSpinner materialSpinner = dialog.findViewById(R.id.taskPrioritySpinner);
                tvAddTaskSelectDate = dialog.findViewById(R.id.tvAddTaskSelectDate);
                etTaskDetails = dialog.findViewById(R.id.etTaskDetails);
                tvCancelNewTaskDialog = dialog.findViewById(R.id.tvCancelNewTaskDialog);
                tvDoneAddTaskDialog = dialog.findViewById(R.id.tvDoneAddTaskDialog);

                tvDoneAddTaskDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        etTaskDetails.getText();
                        if (!TextUtils.isEmpty(etTaskDetails.getText()) && etTaskDetails.getText().length() > 0) {

                            notification.setNotificationText(etTaskDetails.getText().toString());

                            if (!TextUtils.isEmpty(notification.getNotificationType())) {
                                if (!TextUtils.isEmpty(notification.getNotificationType()) && notification.getNotificationType().length() > 0 &&
                                       !TextUtils.isEmpty(notification.getNotificationDate()) && notification.getNotificationDate().length() > 0 &&
                                        !TextUtils.isEmpty(notification.getNotificationText()) && notification.getNotificationText().length() > 0) {
                                    notification.setChecked(false);
                                    long timeInMilli = System.currentTimeMillis();
                                    String taskId = String.valueOf(timeInMilli);
                                    databaseHelper.insertTaskData(taskId, notification.getNotificationType(), notification.getNotificationDate(),
                                            notification.getNotificationText(), String.valueOf(notification.isChecked()));

                                    notificationList.add(notification);
                                    taskListAdapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(getContext(), "please select date and write details!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "select task priority", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                tvCancelNewTaskDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                tvAddTaskSelectDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                notification.setNotificationDate(dayOfMonth + "-" + (month + 1) + "-" + year);
                                tvAddTaskSelectDate.setText("date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);

                        datePickerDialog.show();

                    }
                });


              materialSpinner.setDropdownMaxHeight(450);
              materialSpinner.setItems("Select task priority ", "Priority normal", "Priority high", "Urgent task");

                materialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                        if (position > 0)
                            notification.setNotificationType(item);
                    }
                });

                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.show();
            }
        });

//        tvAddTaskList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                // window.setBackgroundDrawableResource(R.color.colorTransparent);
//            }
//        });

        prepareNotificationData();
        return view;
    }


    private void prepareNotificationData() {
//        Notification notification = new Notification("Urgent task ", "Sun Pharma and Ranbaxy Medical Representative will not promote ....", "02-08-2018", false);
//        notificationList.add(notification);
//        Notification notification2 = new Notification("priority normal", "The undersigned is owner or corporate officer of the owner of ........", "12-07-2018", false);
//        notificationList.add(notification2);
//        Notification notification3 = new Notification("priority normal", "Sun Pharma and Ranbaxy Medical Representative will not promote ....", "27-08-2018", false);
//        notificationList.add(notification3);
//        Notification notification4 = new Notification("priority normal", "If you are changing your job, you are required by law to .......", "09-07-2018", false);
//        notificationList.add(notification4);
//        taskListAdapter.notifyDataSetChanged();
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