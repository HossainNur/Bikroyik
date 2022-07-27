package com.inovex.bikroyik.data.network.volly_method;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class VolleyMethods {
    public RequestQueue queue;

    public void sendPostRequestToServer(final Context context, String URL, final String requestBoddy, final VolleyCallBack callback){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onSuccess("error");
                Log.e("error>>",error.toString()+"");
            }
        }) {
            @Override
            public String getBodyContentType() {

                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return  requestBoddy == null ? null : requestBoddy.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBoddy, "utf-8");
                    return null;
                }
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                String message = ""+response;
                Log.e("error> ",response.toString());
                if (response != null) {
                    // responseString = String.valueOf(response.data);
                    // can get more details such as response.headers
                    message = new String(response.data);

                }
                return Response.success(message, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }



    public void postPasswordParamData(final Context context, String URL, final String empId, final String password, final VolleyCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //    Utility.dialog.closeProgressDialog();
                callback.onSuccess("error");
                Log.e("error>>", error.toString() + "");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("empId", empId);
                params.put("new-password", password);
                Log.e("_sf_", "postPasswordParamData --->> empId:"+empId+" password:"+password );

                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }

    public void postTwoParamData(final Context context, String URL, final String storeId, final String routeId, final VolleyCallBack callback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response.toString());
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //    Utility.dialog.closeProgressDialog();

                Log.e("error>>", error.toString() + "");
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("storeId", storeId);
                params.put("routeId", routeId);
                Log.e("params: ","storeId: "+storeId+" routeId: "+routeId);

                return params;
            }



        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        stringRequest.setShouldCache(false);
        queue.add(stringRequest);

    }

    public void sendImageToServer(final Context context, String URL, final String jsonBody, final VolleyCallBack callback){
        JSONObject jsonObject = null;
        try {
            jsonObject= new JSONObject(jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

//       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//           @Override
//           public void onResponse(String response) {
//               callback.onSuccess(response.toString());
//               Toast.makeText(context, "Successfully uploaded!!", Toast.LENGTH_SHORT).show();
//           }
//       }, new Response.ErrorListener() {
//           @Override
//           public void onErrorResponse(VolleyError error) {
//               Log.d("imageUpload", "images uploaded failed!!");
//           }
//       }){
//           @Override
//           protected Map<String, String> getParams() throws AuthFailureError {
//               Map<String, String> map = new HashMap<>();
//               map.put("upload", jsonBody);
//               return map;
//           }
//       };
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        jsonObjectRequest.setShouldCache(false);
        queue.add(jsonObjectRequest);
    }


    public void sendGetRequest(final Context context, String URL, final VolleyCallBack callback){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    Utility.dialog.closeProgressDialog();
                Toast.makeText(context, "can't access to the server!!", Toast.LENGTH_SHORT).show();
                Log.e("error>>",error.toString()+"");
            }
        }) ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }

    public void sendPutRequest(final Context context, String URL, final VolleyCallBack callback){

        Log.d("url", "update password url : "+URL);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //    Utility.dialog.closeProgressDialog();
                Toast.makeText(context, "can't access to the server!!", Toast.LENGTH_SHORT).show();
                Log.e("error>>",error.toString()+"");
                callback.onSuccess("error");
            }
        }) ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }


    public void sendGETRequestToServer(final Context context, String URL, final VolleyCallBack callback){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                callback.onSuccess(response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error>>",error.toString()+"");
                Toast.makeText(context, "Can't Access To The Server!!", Toast.LENGTH_SHORT).show();
            }
        }) {

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue = Volley.newRequestQueue(context);
        queue.getCache().clear();
        queue.add(stringRequest);
    }









}
