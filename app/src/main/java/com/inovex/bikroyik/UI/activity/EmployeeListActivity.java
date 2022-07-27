package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.DueClientAdapter;
import com.inovex.bikroyik.UI.adapter.EmployeeListAdapater;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.EmployeeListModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.interfaces.EmployeeCallback;
import com.inovex.bikroyik.utils.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListActivity extends AppCompatActivity {


    private ImageView btnBack, btnSearch;
    private RecyclerView recyclerView;
    private ArrayList<EmployeeListModel> arrayList = new ArrayList<EmployeeListModel>();
    private List<EmployeeListModel> gEmployeeListModel;
    private DueClientAdapter dueClientAdapter;
    private EmployeeListAdapater employeeListAdapater;
    private Context context;
    SharedPreference sharedPreferenceClass;
    TextView toolbar_title;
    EditText et_search_contact;
    public static boolean fromMakePayment = false;
    SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_employee_list);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        btnBack = (ImageView) findViewById(R.id.btn_imageBack);
        btnSearch = (ImageView) findViewById(R.id.btn_search);
        et_search_contact = (EditText) findViewById(R.id.et_search_contact);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        context = getApplicationContext();
        sharedPreferenceClass = SharedPreference.getInstance(context);
        recyclerView = (RecyclerView) findViewById(R.id.employeeList_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<EmployeeListModel>();
        if(sharedPreference.getLanguage().equals("English"))
        {
            toolbar_title.setText("Employee List");
            et_search_contact.setHint("employee list");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(intent);
            }
        });

        loadData();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (et_search_contact.getVisibility() == View.VISIBLE){
                    btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_black_search_32));
                    toolbar_title.setVisibility(View.VISIBLE);
                    et_search_contact.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(et_search_contact.getText().toString())){
                        et_search_contact.getText().clear();
                    }
                }else{
                    toolbar_title.setVisibility(View.GONE);
                    et_search_contact.setVisibility(View.VISIBLE);
                    btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_black_32));
                }



            }
        });

        et_search_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });




    }

    private void filter(String text) {
        ArrayList<EmployeeListModel> filteredList = new ArrayList<>();

        if (arrayList != null){
            for (EmployeeListModel item : arrayList) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            employeeListAdapater.filterList(filteredList);
        }
    }

    public void loadData()
    {
        String urlGetServerData = ApiConstants.EMPLOYEE_LIST_URL+sharedPreferenceClass.getSubscriberId();

                /*ApiConstants.DUE_LIST+sharedPreferenceClass.getSubscriberId()+
                "/"+sharedPreferenceClass.getStoreId();;*/

        System.out.print(urlGetServerData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetServerData,null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("employees");

                            for (int p=0; p<jsonArray.length(); p++){
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                EmployeeListModel employeeListModel = gson.fromJson(String.valueOf(jsonObject), EmployeeListModel.class);
                                arrayList.add(employeeListModel);
                            }
                            employeeListAdapater = new EmployeeListAdapater(getApplicationContext(),arrayList);
                            recyclerView.setAdapter(employeeListAdapater);

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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }




}
