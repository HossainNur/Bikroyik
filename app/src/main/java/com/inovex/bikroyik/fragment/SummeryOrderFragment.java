package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.widget.ArcProgress;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import static com.inovex.bikroyik.activity.SplashActivity.MyPREFERENCES;
import static com.inovex.bikroyik.activity.SplashActivity.USER_ID;

public class SummeryOrderFragment extends Fragment {


    TextView targetQty, targetValue, deliveredQty, deliveredValue;

    String target_qty = "0", target_value = "0", delivered_qty = "0", delivered_value = "0";

    Context context;

    String employeeId;

    SharedPreferences sharedPreferences;

    ArcProgress arcStore;

    double percent = 0.0;

    private Timer timer;

    public SummeryOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        sharedPreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        employeeId = sharedPreferences.getString(USER_ID,null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_summery_order, container, false);

         targetQty = view.findViewById(R.id.tvTargetQty);
         targetValue = view.findViewById(R.id.tvTargetValue);
         deliveredQty = view.findViewById(R.id.tvDeliveredQty);
         deliveredValue = view.findViewById(R.id.tvDeliveredValue);
         arcStore = view.findViewById(R.id.arc_store_order);

        arcStore.invalidate();

         callDeliveryManTargetApi();

         targetQty.setText(target_qty);
         targetValue.setText(target_value);
         deliveredQty.setText(delivered_qty);
         deliveredValue.setText(delivered_value);

         double target = Double.valueOf(target_value);
         double delivered = Double.valueOf(delivered_value);


        if(target != 0.0 )
        {
            percent = ((delivered * 100) / target);
        }

         Log.d("workforce_order", "onResponse: "+percent);

         filldata();

        return view;
    }

    private void filldata() {

        timer = null;
        timer = new Timer();
        arcStore.setProgress(0);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            if (arcStore.getProgress() >=  percent) {
                                timer.cancel();
                            } else {
                                arcStore.setProgress(arcStore.getProgress() + 1);
                            }

                        }
                    });
                }

            }
        }, 50, 20);
        arcStore.setProgress((int) percent);

    }

    private void callDeliveryManTargetApi() {

        String URL = APIConstants.DELIVERYMAN_TARGET+employeeId;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length() > 0){

                    try {

                        JSONObject jsonObject = new JSONObject(response);



                        JSONObject object = jsonObject.getJSONObject("data");
                        target_qty = object.getString("tagetedOrderQuantity");
                        target_value = object.getString("tagetedOrderValue");
                        delivered_qty = object.getString("deliveredOrderQuantity");
                        delivered_value = object.getString("deliveredOrderValue");

                        targetQty.setText(target_qty);
                        targetValue.setText(target_value);
                        deliveredQty.setText(delivered_qty);
                        deliveredValue.setText(delivered_value);


                        //Log.d("workforce_order", "onResponse: "+target_qty + " "+ target_value);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("workforce_summery", "onErrorResponse: "+error);

            }
        });

        queue.add(request);


    }
}