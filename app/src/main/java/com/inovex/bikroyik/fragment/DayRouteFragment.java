package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;
import com.inovex.bikroyik.adapter.RouteExpandableListAdapter;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.inovex.bikroyik.activity.MainActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.MainActivity.USER_ID;

/**
 * Created by DELL on 11/15/2018.
 */

public class DayRouteFragment extends Fragment {

    public static final String tag = "_ordDRF_";
    ExpandableListView expandableListView;
    RouteExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle = new ArrayList<>();
    List<String> marketList = new ArrayList<>();
    ArrayList<HashMap<String,String>> expandableListDetails = new ArrayList<HashMap<String, String>>();
//    ArrayList<HashMap<String,String>> expandableListDetailsCopy = new ArrayList<HashMap<String, String>>();

    String dayString;
    AppDatabaseHelper appDatabaseHelper;
    String pos;
    HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
    int i = 0;


    public DayRouteFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dueOrderJsonParser(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_route, container, false);

        appDatabaseHelper = new AppDatabaseHelper(getContext());
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        dayString = getArguments().getString("day_name").toLowerCase();
        pos = getArguments().getString("pos");


        populateDataForExpandableListView(dayString);


        expandableListAdapter = new RouteExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        /*if (!expandableListAdapter.isEmpty()) {
            expandableListView.expandGroup(0);
        }*/


        /*expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(final ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(getContext(), expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition) + "", Toast.LENGTH_SHORT).show();
                final String[] split_str = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).split("#");
                String status = split_str[6];
                if(status.equalsIgnoreCase("Approved")){
                    Toast.makeText(getContext(), getString(R.string.already_visited) , Toast.LENGTH_SHORT).show();

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                    builder.setMessage(getResources().getString(R.string.route_vist_alert))
                            .setCancelable(false)
                            .setPositiveButton(getResources().getString(R.string.alert_dialog_positive), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                    String retailId = split_str[0];

                                    routeVisitSaveApi(retailId, getContext());



                                }
                            })
                            .setNegativeButton(getResources().getString(R.string.alert_dialog_negative), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();

                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    Window window = alert.getWindow();
                    window.setGravity(Gravity.CENTER);
                    alert.show();

                }



                return false;
            }
        });*/



        return view;
    }

    private void dueOrderJsonParser(Context context){
        String URL = APIConstants.DUE_ORDER_API;

        VolleyMethods volleyMethods = new VolleyMethods();
        volleyMethods.sendGetRequest(context, URL, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d(tag, "result :"+result.toString());

            }
        });
    }

    private void routeVisitSaveApi(final String retailId, final Context context) {

        HashMap<String, String> retailData = appDatabaseHelper.getRetailerInfo(retailId);

        final String retailer_address = retailData.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);
        final String latitude = retailData.get(AppDatabaseHelper.COLUMN_RETAILER_LATITUDE);
        final String longitude = retailData.get(AppDatabaseHelper.COLUMN_RETAILER_LONGITUDE);
        final String empId = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString(USER_ID,null);
        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final String visitDate = df.format(currentDate);

        Log.d("workforce_route_visit", "routeVisitSaveApi: "+retailId+" "+retailer_address+" "+ latitude+" "+longitude+" "+empId+" "+visitDate);

        String URL = APIConstants.SAVE_ROUTE_VISIT;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("workforce_route_visit", "onResponse: "+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("success")){

                        Toast.makeText(context, getString(R.string.succesfull_message), Toast.LENGTH_SHORT).show();
                        appDatabaseHelper.UpdateStatusRouteVisit(retailId);




                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                    Log.d("route response err", "route  response: Network error");
                    Toast.makeText(context,"Network error",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Log.d("route response", "route response: Server error");
                    Toast.makeText(context,"Server error",Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Log.d("route response", "route response: Auth. error");
                    Toast.makeText(context,"Auth. error",Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Log.d("route response", "route response: Parse error");
                    Toast.makeText(context,"Parse error",Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Log.d("route response", "route response: timeout error");
                    Toast.makeText(context,"Timeout error",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,error.toString(),Toast.LENGTH_SHORT).show();

                }

                Log.d("route response", "route responseError:" + error.toString());
                error.printStackTrace();

            }
        }) {
            @Override
            public byte[] getBody() {
                JSONObject jsonObject = new JSONObject();
                String body = null;
                try {
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_RETAILER_ID,retailId);
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_RETAILER_ADDRESS,retailer_address);
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_RETAILER_LATITUDE,latitude);
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_RETAILER_LONGITUDE,longitude);
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_SR_ID,empId);
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_DATE,visitDate);

                    body = jsonObject.toString();
                    Log.d("workforce_route_visit", "getBody: "+body);

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

        queue.add(jsonObjectRequest);




    }

    private ArrayList<HashMap<String,String>> getRoutVisitListByDay(String dayString) {

        ArrayList<HashMap<String,String>> routeVisitDataList = new ArrayList<>();

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
                routeMap.put(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS,"Visit");
//                expandableListDetailsCopy.add(routeMap);
                routeVisitDataList.add(routeMap);
            }

            Log.d("_sf_", "routeVisitDataList : "+"("+dayString+")"+routeVisitDataList.toString());
        }

        //Log.d("workforce_route_visit", "populateDataForExpandableListView1: "+dayString+""+expandableListDetailsCopy);

        return routeVisitDataList;
    }

    @Override
    public void onDestroyView() {
        expandableListDetail.clear();
        expandableListTitle.clear();
        expandableListDetails.clear();
        super.onDestroyView();
    }

    private boolean populateDataForExpandableListView(String dayString) {
        expandableListDetail.clear();
        ArrayList<HashMap<String, String>> routeList = getRoutVisitListByDay(dayString);
//        ArrayList<HashMap<String, String>> routeListCopy = routeList;


        if(routeList.size() > 0) {
            for(int i = 0; i < routeList.size(); i++){
                String marketName = routeList.get(i).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_MARKET_NAME);
                if(!expandableListTitle.contains(marketName)){
                    expandableListTitle.add(marketName);
                }
            }


            for(int i = 0; i < expandableListTitle.size(); i++){
                String market_name = expandableListTitle.get(i);
                List<String> retailerList = new ArrayList<>();


                for(int j = 0; j < routeList.size(); j++){

                    String marketName = routeList.get(j).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_MARKET_NAME);

                    if(market_name.equalsIgnoreCase(marketName)){
                        String retailerId = routeList.get(j).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_RETAILER_ID);
                        String route_name = routeList.get(j).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_NAME);
                        String status = routeList.get(j).get(AppDatabaseHelper.COLUMN_ROUTE_VISIT_STATUS);
                        HashMap<String, String> retailerDetails = appDatabaseHelper.getRetailerInfo(retailerId);

                        String retailer_name = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_NAME);
                        String retailer_owner = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_OWNER);
                        String retailer_phone = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_PHONE);
                        String retailer_address = retailerDetails.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);



                        retailerList.add(retailerId+"#"+retailer_name+"#"+
                                getResources().getString(R.string.retail_owner)+" : "+retailer_owner+"#"+getResources().getString(R.string.retail_phone)+" : "+
                                retailer_phone+"#"+getResources().getString(R.string.route_name)+" : "+route_name+"#"+getResources().getString(R.string.retailer_address)+" : "+retailer_address+"#"+status);

                    } else {
                        expandableListDetail.put(market_name,retailerList);
                    }

                }

            }
            return true;
        }
        return false;
    }

}
