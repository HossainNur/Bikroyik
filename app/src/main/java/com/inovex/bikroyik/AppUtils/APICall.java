package com.inovex.bikroyik.AppUtils;

import android.content.Context;
import android.util.Log;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APICall {

    private Context context;

    public APICall(Context context) {
        this.context = context;
    }



    public void RouteAPI(final Context context, String empId){
        String URL = APIConstants.ROUTE_API+empId;
        Log.d("workforce_routeAPI", "RouteAPI: "+URL);



        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("workforce_routeAPI", "RouteAPI api response : "+response);

                AppDatabaseHelper appDatabaseHelper1 = new AppDatabaseHelper(context);




                if (response.length() > 0) {

                    appDatabaseHelper1.deleteAllRouteVisit();

                    try {
                            JSONObject jsonObj = new JSONObject(response);

                            String data = jsonObj.getString("data");

                            JSONArray jsonArray = new JSONArray(data);

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String dayName = jsonObject.getString(APIConstants.API_KEY_ROUTE_DAY).toLowerCase();
                                JSONObject routeObject = jsonObject.getJSONObject("route");
                                String routeName = routeObject.getString(APIConstants.API_KEY_ROUTE_NAME);
                                JSONArray marketJSONArray = routeObject.getJSONArray("markets");

                                for(int j = 0; j < marketJSONArray.length(); j++){
                                    JSONObject marketObject = marketJSONArray.getJSONObject(j);
                                    String marketName = marketObject.getString(APIConstants.API_KEY_ROUTE_MARKET_NAME);
                                    String marketAddress = marketObject.getString(APIConstants.API_KEY_ROUTE_MARKET_ADDRESS);
                                    JSONArray retailJSONArray = marketObject.getJSONArray(APIConstants.API_KEY_ROUTE_RETAIL);


                                    for (int  k = 0; k < retailJSONArray.length(); k++){

                                        JSONObject retailJSONObject = retailJSONArray.getJSONObject(k);
                                        String retailId = retailJSONObject.getString("id");
                                        String status = retailJSONObject.getString("status");

                                        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);
                                        appDatabaseHelper.insertRouteVisits(dayName,routeName,marketName,marketAddress,retailId, status);

                                    }

                                }



                            }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("URL", "callRouteAPI: "+e);

                    }


                } else {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);

    }



    public void getAttendanceAPI(final Context context, String empId, String from, String to)
    {
        //http://43.224.110.67:8080/workforce/api/employee-attendance?empId=1234&fromDate=2021-01-01&toDate=2021-05-30

        String URL = APIConstants.ATTENDANCE_API_GET;


        RequestQueue attendanceQueue = Volley.newRequestQueue(context);

        JsonObjectRequest attendanceRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response.length() > 0) {

                    try {
                        String data = response.getString("data");
                        JSONArray jsonArray = new JSONArray(data);

                        Log.d("workforce", "onResponse: "+jsonArray.length());

                        for (int i = 0; i < jsonArray.length(); i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String inTimes = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_IN_TIME);
                            String inAddress = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_IN_ADDRESS);
                            String outTimes = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_OUT_TIME);
                            String outAddress = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_OUT_ADDRESS);
                            String comment = jsonObject.getString(APIConstants.API_KEY_ATTENDANCE_COMMENT);
                            String id = jsonObject.getString("id");
                            String[] splitString  = inTimes.split(" ");
                            String inTime = splitString[1];
                            String date = splitString[0];
                            splitString = outTimes.split(" ");
                            String outTime = splitString[1];
                            String inLatLon = jsonObject.getString("lat");
                            String outLatLon = jsonObject.getString("lng");
                            String type = jsonObject.getString("attendanceType");
                            String status = jsonObject.getString("status");
//                            String status = "success";
//                            if(type.equalsIgnoreCase("in")){
//                                status = "pending";
//                            }
                            AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);
                            appDatabaseHelper.updateAttendance(id,date,inTime,inAddress,outTime, outAddress, status,comment, inLatLon, outLatLon);


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    Log.d("sales force", "location sending response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("sales force", "location sending response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("sales force", "location sending response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("sales force", "location sending response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("sales force", "location sending response: timeout error");
                }

                Log.d("workforce", "responseError:" + error.toString());
                error.printStackTrace();
            }
        });

        attendanceQueue.add(attendanceRequest);

    }

    public void callOrderDetailApi(Context context, String employeeId ){

        String URL = APIConstants.GET_ORDER_API+employeeId;

        final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.length() > 0){

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        JSONArray jsonArray = jsonObject.getJSONArray("data");


                        appDatabaseHelper.deleteFinalOrderProductTableData();;
                        appDatabaseHelper.deleteFinalOrderTableData();


                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject object = jsonArray.getJSONObject(i);

                            String order_id = object.getString(APIConstants.API_KEY_ORDER_ID);
                            String retailId = object.getString(APIConstants.API_KEY_ORDER_RETAILER_ID);
                            String total = object.getString(APIConstants.API_KEY_ORDER_TOTAL);
                            String discount = object.getString(APIConstants.API_KEY_ORDER_DISCOUNT);
                            String grandTotal = object.getString(APIConstants.API_KEY_ORDER_GRAND_TOTAL);
                            String deliveryDate = object.getString(APIConstants.API_KEY_ORDER_DELIVERY_DATE);
                            String paymentMethod = object.getString(APIConstants.API_KEY_ORDER_PAYMENT_METHOD);
                            String advanced = object.getString(APIConstants.API_KEY_ORDER_ADVANCED_PAYMENT);
                            String due = object.getString("dueAmount");
                            String status = object.getString("orderStatus");
                            String deliveryMan = object.getString("deliveryMan");

                            appDatabaseHelper.insertFinalOrder(order_id,retailId,total,discount,
                                    grandTotal,deliveryDate,paymentMethod,advanced,due,status,deliveryMan);

                            JSONArray array = object.getJSONArray("orderDetails");


                            for(int j = 0; j < array.length(); j++){

                                JSONObject object1 = array.getJSONObject(j);

                                String productId = object1.getString(APIConstants.API_KEY_ORDER_PRODUCT_ID);
                                String product_name = object1.getString(APIConstants.API_KEY_ORDER_PRODUCT_NAME);
                                String qty = object1.getString(APIConstants.API_KEY_ORDER_PRODUCT_QUANTITY);
                                String total_price = object1.getString(APIConstants.API_KEY_ORDER_PRODUCT_PRICE);


                                appDatabaseHelper.insertFinalOrderForProduct(order_id,productId,product_name,qty,total_price);
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

            }
        });

        requestQueue.add(stringRequest);
    }






}
