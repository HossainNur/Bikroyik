package com.inovex.bikroyik.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.UI.activity.ContactListActivity;
import com.inovex.bikroyik.UI.activity.EditHomepageActivity;
import com.inovex.bikroyik.UI.activity.MainActivity;
import com.inovex.bikroyik.UI.activity.PasswordUpdatedActivity;
import com.inovex.bikroyik.UI.activity.ProfileInfoActivity;
import com.inovex.bikroyik.fragment.FragmentDrawer;
import com.inovex.bikroyik.UI.activity.Expense;


import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.BillActivity;
import com.inovex.bikroyik.UI.activity.Expense;
import com.inovex.bikroyik.activity.PasswordChangeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 8/1/2018.
 */

public class FragmentDrawer extends Fragment implements View.OnClickListener {
    private static String TAG = FragmentDrawer.class.getSimpleName();

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private NavigationDrawerAdapter adapter;
    private View containerView;
    private static String[] titles = null;
    private FragmentDrawerListener drawerListener;

    private TextView btn_changePassword;

    LinearLayout dashboard;
    LinearLayout attendance;
    LinearLayout messaging;
    LinearLayout taskList;
    LinearLayout callScheduler;
    LinearLayout profile;
    LinearLayout contacts;
    LinearLayout syncData;
    LinearLayout settings;
    LinearLayout expenses;
    LinearLayout leave;
    LinearLayout complain;
    LinearLayout orders;
    LinearLayout notifications;
    LinearLayout setting;


    public FragmentDrawer() {

    }



    public void setDrawerListener(FragmentDrawerListener listener) {
        this.drawerListener = listener;
    }

    public static List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();


        // preparing navigation drawer items
        for (int i = 0; i < titles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(titles[i]);
            data.add(navItem);
        }
        return data;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // drawer labels
        titles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_nav_drawer, container, false);

        dashboard = layout.findViewById(R.id.lLHome);
        attendance = layout.findViewById(R.id.lLAttendance);
        messaging = layout.findViewById(R.id.lLMessaging);
        notifications = layout.findViewById(R.id.notifications);
        taskList = layout.findViewById(R.id.lLToDo);
        callScheduler = layout.findViewById(R.id.lLCallSchedule);
        profile = layout.findViewById(R.id.lLProfile);
        contacts = layout.findViewById(R.id.lLContacts);
        syncData = layout.findViewById(R.id.lLSyncData);
        settings = layout.findViewById(R.id.lLConnect);
        expenses = layout.findViewById(R.id.expensesId);
        leave = layout.findViewById(R.id.lLLeave);
        complain = layout.findViewById(R.id.lLRetailComplain);
        orders = layout.findViewById(R.id.llOrders);
        setting = layout.findViewById(R.id.llSettings);

        btn_changePassword = layout.findViewById(R.id.btn_change_password);

        btn_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PasswordUpdatedActivity.class);
                startActivity(intent);
            }
        });

        dashboard.setOnClickListener(this);
        /*attendance.setOnClickListener(this);
        messaging.setOnClickListener(this);
        notifications.setOnClickListener(this);
        taskList.setOnClickListener(this);*/
        settings.setOnClickListener(this);
        callScheduler.setOnClickListener(this);
        profile.setOnClickListener(this);
        contacts.setOnClickListener(this);
        syncData.setOnClickListener(this);
        settings.setOnClickListener(this);
        expenses.setOnClickListener(this);
        leave.setOnClickListener(this);
        complain.setOnClickListener(this);
        orders.setOnClickListener(this);
        setting.setOnClickListener(this);


        return layout;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onClick(View v) {
        changeAllLayoutForeGround();
        ColorDrawable colorDrawable = new ColorDrawable(0xFFe6e5e6);
        switch (v.getId()) {

            case R.id.lLHome:
                /*mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 0);
                dashboard.setBackground(colorDrawable);*/
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

                break;

            case R.id.lLProfile:
                intent = new Intent(getContext(), ProfileInfoActivity.class);
                startActivity(intent);
                break;

            /*case R.id.lLAttendance:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 2);
                attendance.setBackground(colorDrawable);
                break;*/

            /*case R.id.lLToDo:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 3);
                taskList.setBackground(colorDrawable);

                break;

            case R.id.notifications:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 4);
                notifications.setBackground(colorDrawable);
                break;

            case R.id.lLMessaging:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 5);
                messaging.setBackground(colorDrawable);
                break;*/


            case R.id.expensesId:

               //
//
                intent = new Intent(getContext(), Expense.class);
                startActivity(intent);
                //mDrawerLayout.closeDrawers();
                //drawerListener.onDrawerItemSelected(v, 6);*/
                //expenses.setBackground(colorDrawable);

                break;
            /*case R.id.lLCallSchedule:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 7);
                callScheduler.setBackground(colorDrawable);

                break;*/
            case R.id.lLContacts:
                /*mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 8);
                contacts.setBackground(colorDrawable);*/
                intent = new Intent(getContext(), ContactListActivity.class);
                startActivity(intent);

                break;
            /*case R.id.lLSyncData:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 9);
                syncData.setBackground(colorDrawable);
                break;
            case R.id.lLConnect:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 10);
                settings.setBackground(colorDrawable);
                break;
            case R.id.lLRetailComplain:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 11);
                complain.setBackground(colorDrawable);
                break;

            case R.id.lLLeave:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 12);
                leave.setBackground(colorDrawable);
                break;

            case R.id.llOrders:
                mDrawerLayout.closeDrawer(containerView);
                drawerListener.onDrawerItemSelected(v, 13);
                orders.setBackground(colorDrawable);
                break;*/

            case R.id.llSettings:

                intent = new Intent(getContext(), EditHomepageActivity.class);
                startActivity(intent);
                break;


        }

    }

    private void changeAllLayoutForeGround() {

        ColorDrawable colorDrawable = new ColorDrawable(0xFFFFFFFF);
        dashboard.setBackground(colorDrawable);
        attendance.setBackground(colorDrawable);
        messaging.setBackground(colorDrawable);
        notifications.setBackground(colorDrawable);
        taskList.setBackground(colorDrawable);
        callScheduler.setBackground(colorDrawable);
        profile.setBackground(colorDrawable);
        contacts.setBackground(colorDrawable);
        syncData.setBackground(colorDrawable);
        settings.setBackground(colorDrawable);
        expenses.setBackground(colorDrawable);
        leave.setBackground(colorDrawable);
        complain.setBackground(colorDrawable);
        orders.setBackground(colorDrawable);
    }

//    public static interface ClickListener {
//        public void onClick(View view, int position);
//
//        public void onLongClick(View view, int position);
//    }

//    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
//
//        private GestureDetector gestureDetector;
//        private ClickListener clickListener;
//
//        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
//            this.clickListener = clickListener;
//            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
//                @Override
//                public boolean onSingleTapUp(MotionEvent e) {
//                    return true;
//                }
//
//                @Override
//                public void onLongPress(MotionEvent e) {
//                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
//                    if (child != null && clickListener != null) {
//                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
//                    }
//                }
//            });
//        }
//
//        @Override
//        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
//
//            View child = rv.findChildViewUnder(e.getX(), e.getY());
//            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
//                clickListener.onClick(child, rv.getChildPosition(child));
//            }
//            return false;
//        }
//
//        @Override
//        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
//        }
//
//        @Override
//        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//        }
//
//
//    }

    public interface FragmentDrawerListener {
        public void onDrawerItemSelected(View view, int position);
    }


}


