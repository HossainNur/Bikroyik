package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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
import com.inovex.bikroyik.services.GPSTracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.inovex.bikroyik.activity.MainActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.MainActivity.USER_ID;

/**
 * Created by DELL on 11/25/2018.
 */

public class VisitCardAdapter extends RecyclerView.Adapter<VisitCardAdapter.VisitListViewHolder> {
    private List<Route> routeList;
    Context context;
    GPSTracker gps;
    AppDatabaseHelper appDatabaseHelper;

    public VisitCardAdapter(List<Route> routeList, Context mContext) {
        this.routeList = routeList;
        context = mContext;
        gps = new GPSTracker(mContext);
        appDatabaseHelper = new AppDatabaseHelper(mContext);
    }


    @Override
    public VisitListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.route_list_row, parent, false);

        return new VisitListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VisitListViewHolder holder, int position) {
        final Route route = routeList.get(position);
        holder.retailerName.setText(route.getRouteRetailerName());
        holder.retailerOwner.setText("Owner: " + route.getRouteRetailerOwner());
        holder.retailerAddress.setText("Address: " + route.getRouteRetailerAddress());
        holder.status.setText(route.getRouteVisitStatus());

        final String retailId = route.getRouteRetailerId();
        final String retailerName = route.getRouteRetailerName();
        final String retailerOwner = route.getRouteRetailerOwner();
        final String retailerAddress = route.getRouteRetailerAddress();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String, String> retailData = appDatabaseHelper.getRetailerInfo(retailId);
                GPSTracker gps;
                gps = new GPSTracker(context);
                Double lat = 0.0;
                Double lon = 0.0;

                if(gps.canGetLocation()){
                    lat = gps.getLatitude();
                    lon = gps.getLongitude();
                }

                final String latitude = String.valueOf(lat);
                final String longitude = String.valueOf(lon);

                final String retailer_address = retailData.get(AppDatabaseHelper.COLUMN_RETAILER_ADDRESS);

                final String empId = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).getString(USER_ID,null);
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

                                Toast.makeText(context, context.getString(R.string.succesfull_message), Toast.LENGTH_SHORT).show();
                                appDatabaseHelper.UpdateStatusRouteVisit(retailId);
                                holder.status.setText("Visited");


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
        });

    }


    @Override
    public int getItemCount() {
        return routeList.size();
    }


    public class VisitListViewHolder extends RecyclerView.ViewHolder {
        public TextView retailerName, retailerOwner, retailerAddress, status;

        public VisitListViewHolder(View view) {
            super(view);
            retailerName = (TextView) view.findViewById(R.id.tvRetailerName);
            retailerOwner = (TextView) view.findViewById(R.id.tvRetailerOwner);
            retailerAddress = (TextView) view.findViewById(R.id.tvRetailerAddress);
            status = (TextView) view.findViewById(R.id.visitStatus);
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
