package com.inovex.bikroyik.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;
import com.inovex.bikroyik.adapter.Route;
import com.inovex.bikroyik.adapter.VisitCardAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



/**
 * Created by DELL on 11/14/2018.
 */


public class RouteVisitCardFragment extends Fragment {

    private List<Route> recyclerRouteList = new ArrayList<>();
    RecyclerView routeRecycler;
    public static VisitCardAdapter visitListAdapter;
    ArrayList<String> bottomList = new ArrayList<>();
    TextView tvVisitCardTitle;
    LinearLayout llSyncVisitTask;
    private ProgressDialog progressDialog;
    String dayName = "";
    ArrayList<HashMap<String,String>> expandableListDetailsCopy = new ArrayList<HashMap<String, String>>();

    public RouteVisitCardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route_card, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        routeRecycler = (RecyclerView) view.findViewById(R.id.route_recycler);
        tvVisitCardTitle = view.findViewById(R.id.tvVisitCardTitle);
        llSyncVisitTask = view.findViewById(R.id.llSyncVisitTask);
        tvVisitCardTitle.setText(setVisitCardTitle());
        getRouteData(getContext());
        visitListAdapter = new VisitCardAdapter(recyclerRouteList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        routeRecycler.setLayoutManager(mLayoutManager);
        routeRecycler.setItemAnimator(new DefaultItemAnimator());
        routeRecycler.setAdapter(visitListAdapter);




        return view;
    }

    private void getRouteData(Context context) {


        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dayString = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());


        for(int  i = 0; i < MainActivity.expandableListDetailsRoute.size(); i++){
            if(MainActivity.expandableListDetailsRoute.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_DAY).equalsIgnoreCase(dayString)){
                String route_name = MainActivity.expandableListDetailsRoute.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_NAME);
                String retailer_id = MainActivity.expandableListDetailsRoute.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ID);
                String market_name = MainActivity.expandableListDetailsRoute.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_MARKET_NAME);
                String market_address = MainActivity.expandableListDetailsRoute.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_MARKET_ADDRESS);
                String status = MainActivity.expandableListDetailsRoute.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS);
                HashMap<String, String> routeMap = new HashMap<String, String>();
                routeMap.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ID, retailer_id);
                routeMap.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_NAME, route_name);
                routeMap.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_MARKET_ADDRESS, market_address);
                routeMap.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_MARKET_NAME, market_name);
                routeMap.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS, status);

                expandableListDetailsCopy.add(routeMap);

            }
        }


        ArrayList<HashMap<String, String>> routeList = expandableListDetailsCopy;
        ArrayList<HashMap<String, String>> routeListCopy = expandableListDetailsCopy;
        ArrayList<HashMap<String, String>> visitedList = new AppDatabaseHelper(getContext()).getVisitedRetail();




        Log.d("workforce_visit_card", "expandableListDetailsCopy: "+expandableListDetailsCopy.size());


        if(routeListCopy.size() > 0){

            for (int i = 0; i < routeListCopy.size(); i++){

                String retailerId = routeListCopy.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ID);
                HashMap<String, String> retailerDetails = new AppDatabaseHelper(context).getRetailerInfo(retailerId);
                String retailer_name = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
                String retailer_owner = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
                String retailer_address = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);


                //Log.d("workforce_visit_card", "getRouteData: "+status);



                Route route = new Route(retailerId,retailer_name,retailer_owner,retailer_address,"Visited");

                recyclerRouteList.add(route);


            }

        }



/*        if (routeList.size() > 0) {


            for (int i = 0; i < routeList.size(); i++) {


                String retailerId = routeListCopy.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ID);
                HashMap<String, String> retailerDetails = new AppDatabaseHelper(context).getRetailerInfo(retailerId);
                String retailer_name = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
                String retailer_owner = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
                String retailer_address = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);
                String status = routeList.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS);

                //Log.d("workforce_visit_card", "getRouteData: "+status);



                Route route = new Route(retailerId,retailer_name,retailer_owner,retailer_address,status);

                recyclerRouteList.add(route);


            }



        }*/



    }


    private void callRouteVisitApi(final String retailerId, final String retailerAddress, final String latitude, final String longitude, final String srId, final String visitDate) {
        String URL = APIConstants.ROUTE_VISIT_SUBMIT_API;
        RequestQueue retailerQueue = Volley.newRequestQueue(getContext());


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("visit top response", " :" + response.toString());
                if (response.toString().contains("success")) {

                    // update route visit status success with retailer id
                    AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(getContext());
                    appDatabaseHelper.updateRouteVisit(retailerId, latitude, longitude, visitDate, "success");
                    recyclerRouteList.clear();
                    prepareRouteData(getContext());
                    progressDialog.dismiss();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof NetworkError) {
                    Log.d("visit response err", "visit  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("visit response", "visit response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("visit response", "visit response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("visit response", "visit response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("visit response", "visit response: timeout error");
                }

                Log.d("visit response", "visit responseError:" + error.toString());
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put(APIConstants.ROUTE_VISIT_KEY_RETAIL_ID, retailerId);
                params.put(APIConstants.ROUTE_VISIT_KEY_RETAIL_ADDRESS, retailerAddress);
                params.put(APIConstants.ROUTE_VISIT_KEY_RETAIL_LAT, latitude);
                params.put(APIConstants.ROUTE_VISIT_KEY_RETAIL_LONG, longitude);
                params.put(APIConstants.ROUTE_VISIT_KEY_SR_ID, srId);
                params.put(APIConstants.ROUTE_VISIT_KEY_VISIT_DATE, visitDate);

                return params;
            }
        };

        retailerQueue.add(stringRequest);


    }


    private String setVisitCardTitle() {
        String title = "";
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        dayName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
        title = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()) + " Visit";
        return title.toUpperCase();
    }


    private void prepareRouteData(Context mContext) {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String day = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());



        ArrayList<HashMap<String, String>> routeList = new ArrayList<>();
        routeList.clear();
        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);
        if (appDatabaseHelper.getRouteVisitList().size() > 0) {
            // check the date, if previous delete all and insert today data
            routeList = appDatabaseHelper.getRouteVisitList();
            Log.v("visit", "if  visit size :" + routeList.size());

            if (routeList.get(0).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_ROUTE_DATE).equalsIgnoreCase(todayDate())) {
                // populate data to listview
                populateListView(routeList);

            } else {
                appDatabaseHelper.deleteAllRouteVisit();
                ArrayList<HashMap<String, String>> todayRouteList = new ArrayList<>();
                todayRouteList = appDatabaseHelper.getRouteList(day.toLowerCase());
                insertRouteDataToDatabase(todayRouteList, getContext());

                // populate the list view

                ArrayList<HashMap<String, String>> newRouteList = new ArrayList<>();
                newRouteList = appDatabaseHelper.getRouteVisitList();
                populateListView(newRouteList);

            }


        } else {

            Log.v("visit", " else visit size :" + routeList.size());
            // insert  today data to route visit table and populate to list
            appDatabaseHelper.deleteAllRouteVisit();
            ArrayList<HashMap<String, String>> todayRouteList = new ArrayList<>();
            todayRouteList = appDatabaseHelper.getRouteList(day.toLowerCase());
            Log.v("visit", "else  today visit size :" + routeList.size());

            insertRouteDataToDatabase(todayRouteList, getContext());
            // populate listview
            ArrayList<HashMap<String, String>> newRouteList = new ArrayList<>();
            newRouteList = appDatabaseHelper.getRouteVisitList();
            populateListView(newRouteList);

        }

        visitListAdapter.notifyDataSetChanged();

    }

    private void populateListView(ArrayList<HashMap<String, String>> routeList) {
        recyclerRouteList.clear();
        ArrayList<HashMap<String, String>> routeListData = routeList;

        Log.v("visit", "today visit size :" + routeList.size());
        if (routeListData.size() > 0) {
            for (int x = 0; x < routeListData.size(); x++) {
                Route route = new Route();
                route.setRouteRetailerId(routeListData.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ID));
                route.setRouteRetailerName(routeListData.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_NAME));
                route.setRouteRetailerOwner(routeListData.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_OWNER));
                route.setRouteRetailerAddress(routeListData.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ADDRESS));
                route.setRouteVisitStatus(routeListData.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS));
                recyclerRouteList.add(route);


            }
        }
        visitListAdapter.notifyDataSetChanged();

    }

    private void insertRouteDataToDatabase(ArrayList<HashMap<String, String>> todayRouteList, Context mContext) {
        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);
        if (todayRouteList.size() > 0) {
            for (int x = 0; x < todayRouteList.size(); x++) {

                String retailerId = todayRouteList.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_RETAIL_ID);
                String retailerName = todayRouteList.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_RETAIL_NAME);
                String retailerOwner = todayRouteList.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_RETAIL_OWNER);
                String retailerAddress = todayRouteList.get(x).get(AppDatabaseHelper.COLUMN_ROUTE_RETAIL_ADDRESS);
                String visitLatitude = "";
                String visitLongitude = "";
                String visitDate = "";
                String visitStatus = "Undone";
                String routeDate = todayDate();
                appDatabaseHelper.insertRouteVisit(retailerId, retailerName, retailerOwner, retailerAddress, visitLatitude, visitLongitude,
                        visitDate, visitStatus, routeDate);


            }
        }


    }


    public String todayDate() {
        String todayDateString = "";
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        todayDateString = day + "-" + month + "-" + year;
        return todayDateString;
    }
}
