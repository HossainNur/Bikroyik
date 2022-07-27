package com.inovex.bikroyik.adapter;

/**
 * Created by DELL on 11/15/2018.------
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.DeviceLocation;
import com.inovex.bikroyik.R;

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


public class RouteExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle = new ArrayList<>();
    private HashMap<String, List<String>> expandableListDetail = new HashMap<>();
    String currentAddress = null;

    public RouteExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.route_list_item, null);
        }

        String[] splitString = expandedListText.split("#");
        final String retail_id = splitString[0];
        String retail_name = splitString[1];
        String retail_owner =splitString[2];
        String phone = splitString[3];
        String route = splitString[4];
        String address = splitString[5];
        final String status_str = splitString[6];

        final TextView rName, rOwner, rPhone, routeName, marketAddress, status;

        rName = convertView.findViewById(R.id.tvRouteRetailerName);
        rOwner = convertView.findViewById(R.id.tvRouteRetailerOwner);
        rPhone = convertView.findViewById(R.id.tvRouteRetailerPhone);
        routeName = convertView.findViewById(R.id.tvRouteName);
        marketAddress = convertView.findViewById(R.id.tvRouteMarketAddress);
        status = convertView.findViewById(R.id.visitStatus);

        rName.setText(retail_name);
        rOwner.setText(retail_owner);
        rPhone.setText(phone);
        routeName.setText(route);
        marketAddress.setText(address);
        status.setText(status_str);


        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                builder.setMessage(context.getResources().getString(R.string.route_vist_alert))
                        .setCancelable(false)
                        .setPositiveButton(context.getResources().getString(R.string.alert_dialog_positive), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();


                                routeVisitSaveApi(retail_id, context,view, status);



                            }
                        })
                        .setNegativeButton(context.getResources().getString(R.string.alert_dialog_negative), new DialogInterface.OnClickListener() {
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
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        if (this.expandableListDetail.get(this.expandableListTitle.get(listPosition)) != null){
            return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
        }else {
            return 0;
        }

    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.route_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(("Visit : " + listTitle).toUpperCase());


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    private void routeVisitSaveApi(final String retailId, final Context context, final View view, final TextView statusView) {

        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

        HashMap<String, String> retailData = appDatabaseHelper.getRetailerInfo(retailId);
        Double lat = 0.0;
        Double lon = 0.0;


        DeviceLocation deviceLocation = new DeviceLocation();
        Location location = deviceLocation.myCurrentLocation(context);
        if(location != null){
            currentAddress = Constants.getAddressFromLocation(location, context);
            lat = location.getLatitude();
            lon = location.getLongitude();
        }

        if (location == null){
            DeviceLocation mDeviceLocation = new DeviceLocation();
            Location mLocation = mDeviceLocation.myCurrentLocation(context);
            if(mLocation != null){
                currentAddress = Constants.getAddressFromLocation(location, context);
                lat = mLocation.getLatitude();
                lon = mLocation.getLongitude();
            }
        }

        final String latitude = String.valueOf(lat);
        final String longitude = String.valueOf(lon);


        final String retailer_address = retailData.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);
        final String empId = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString(USER_ID,null);

        Date currentDate = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        final String visitDate = df.format(currentDate);

        Log.d("workforce_route_visit", "routeVisitSaveApi: "+retailId+" "+currentAddress+" "+ latitude+" "+longitude+" "+empId+" "+visitDate);

        String URL = APIConstants.SAVE_ROUTE_VISIT;

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("workforce_route_visit", "onResponse: "+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("success")){

                        Toast.makeText(context, context.getString(R.string.succesfull_message), Toast.LENGTH_SHORT).show();
                        /*appDatabaseHelper.UpdateStatusRouteVisit(retailId);
                        statusView.setText("Visit");*/
                        Dialog dialog = new Dialog(context);
                        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        dialog.setContentView(layoutInflater.inflate(R.layout.done_dialog,null));
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();




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
                    jsonObject.put(APIConstants.SAVE_ROUTE_VISIT_RETAILER_ADDRESS,currentAddress);
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

}
