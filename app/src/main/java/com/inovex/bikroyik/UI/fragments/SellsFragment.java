package com.inovex.bikroyik.UI.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.DueClientAdapter;
import com.inovex.bikroyik.UI.adapter.SellsAdapter;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.SellsModel;
import com.inovex.bikroyik.model.DueData;
import com.inovex.bikroyik.utils.ApiConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SellsFragment extends Fragment {

    RecyclerView recyclerView;
    SellsAdapter sellsAdapter;
    private SharedPreference sharedPreference;
    private ArrayList<SellsModel> arrayList = new ArrayList<SellsModel>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_sells, container, false);
       init(view);
       getServerData();
       return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.sellsFragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        sharedPreference = SharedPreference.getInstance(getContext());

    }

    private void getServerData() {
        //String urlGetServerData = ApiConstants.DUE_LIST+sharedPreferenceClass.getSubscriberId()+"/"+sharedPreferenceClass.getStoreId();

                /*ApiConstants.DUE_LIST+sharedPreferenceClass.getSubscriberId()+
                "/"+sharedPreferenceClass.getStoreId();;*/

        String urlGetServerData=ApiConstants.SELLS_LIST+sharedPreference.getSubscriberId()+"/"+sharedPreference.getStoreId();

        //System.out.print(urlGetServerData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetServerData,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                SellsModel data = gson.fromJson(String.valueOf(jsonObject), SellsModel.class);
                                arrayList.add(data);
                            }
                            sellsAdapter = new SellsAdapter(getContext(),arrayList);
                            recyclerView.setAdapter(sellsAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}