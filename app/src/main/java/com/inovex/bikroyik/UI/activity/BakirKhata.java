package com.inovex.bikroyik.UI.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.DueAdapter;
import com.inovex.bikroyik.UI.adapter.DueClientAdapter;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.interfaces.ClientCallback;
import com.inovex.bikroyik.interfaces.DueClientCallback;
import com.inovex.bikroyik.model.DueData;
import com.inovex.bikroyik.utils.ApiConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class BakirKhata extends AppCompatActivity {


    ImageView imageView;
    TextView toolbar_title, titleText, addButtonText,
            totalDueText, tv_totalReceiveAmount, seeReportText, tv_totalBaki, tv_totalReceive;
    ImageView btnBack, btnSearch;
    Animation animationRight;
    LinearLayout btn_addDenaPawna;
    ImageView backButton;
    EditText et_search_contact;
    private Context context;
    Button btnAllContact, btnSupplier, btnCustomer;
    SharedPreference sharedPreferenceClass;
    VolleyMethods volleyMethods;
    SharedPreference sharedPreference;
    boolean flag = false;
    private List<DueData> gClientListModel;
    List<DueData> customerListModels = new ArrayList<>();
    List<DueData> supplierListModels = new ArrayList<>();
    public static boolean fromMakePayment = false;
    private static final String TAG = "BakirKhata";

    private ArrayList<DueData> arrayList = new ArrayList<DueData>();
    private RecyclerView recyclerView;
    private DueClientAdapter dueClientAdapter;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_bakir_khata);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        toolbar_title = findViewById(R.id.toolbar_title);
        titleText = findViewById(R.id.text);
        addButtonText = findViewById(R.id.addButtonText);
        totalDueText = findViewById(R.id.totalDueText);
        tv_totalReceiveAmount = findViewById(R.id.tv_totalReceiveAmount);
        seeReportText = findViewById(R.id.seeReportText);
        tv_totalBaki = findViewById(R.id.tv_totalBaki);
        tv_totalReceive = findViewById(R.id.tv_totalReceive);

        volleyMethods = new VolleyMethods();
        context = getApplicationContext();
        //sendLoginRequestToServer(getApplicationContext());
        sharedPreferenceClass = SharedPreference.getInstance(context);
        btnSearch = (ImageView) findViewById(R.id.btn_search);
        et_search_contact = (EditText) findViewById(R.id.dueListSearch);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        imageView = (ImageView) findViewById(R.id.rightArrow);
        btnAllContact = (Button) findViewById(R.id.btn_allContact);
        btnSupplier = (Button) findViewById(R.id.btn_supplier);
        btnCustomer = (Button) findViewById(R.id.btn_customer);
        btn_addDenaPawna = (LinearLayout) findViewById(R.id.btn_addCustomer);

        recyclerView = (RecyclerView) findViewById(R.id.dueList_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();

        getLoadData();

        if (sharedPreference.getLanguage().equals("English")) {
            toolbar_title.setText("Due");
            titleText.setText("Create New Customer");
            btnAllContact.setText("contact");
            btnSupplier.setText("supplier");
            btnCustomer.setText("customer");
            addButtonText.setText("Add New");
            et_search_contact.setHint("contact list");
            seeReportText.setText("see report");
            tv_totalReceive.setText("Total Deposit");
            tv_totalBaki.setText("Total Due");

        }

        seeReportText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BakirKhata.this, ScreenNavigation.class);
                startActivity(intent);
            }
        });

        animationRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_animation_bakir_khata);
        imageView.startAnimation(animationRight);
        btnAllContact.setBackground(getDrawable(R.drawable.button_in_contact_list));
        btnAllContact.setTextColor(getResources().getColor(R.color.white));
        getServerData();

        btnAllContact.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                btnAllContact.setBackground(getDrawable(R.drawable.button_in_contact_list));
                btnAllContact.setTextColor(getResources().getColor(R.color.white));

                btnCustomer.setBackground(getDrawable(R.drawable.button_backgroud));
                btnCustomer.setTextColor(getResources().getColor(R.color.black));

                btnSupplier.setBackground(getDrawable(R.drawable.button_backgroud));
                btnSupplier.setTextColor(getResources().getColor(R.color.black));

                if (arrayList.size() > 0) {
                    filterAllContact();
                }

            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCustomer.setBackground(getDrawable(R.drawable.button_in_contact_list));
                btnCustomer.setTextColor(getResources().getColor(R.color.white));

                btnAllContact.setBackground(getDrawable(R.drawable.button_backgroud));
                btnAllContact.setTextColor(getResources().getColor(R.color.black));

                btnSupplier.setBackground(getDrawable(R.drawable.button_backgroud));
                btnSupplier.setTextColor(getResources().getColor(R.color.black));
                if (arrayList.size() > 0) {
                    filterCustomer();
                }

            }
        });

        btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //color management
                btnSupplier.setBackground(getDrawable(R.drawable.button_in_contact_list));
                btnSupplier.setTextColor(getResources().getColor(R.color.white));

                btnAllContact.setBackground(getDrawable(R.drawable.button_backgroud));
                btnAllContact.setTextColor(getResources().getColor(R.color.black));

                btnCustomer.setBackground(getDrawable(R.drawable.button_backgroud));
                btnCustomer.setTextColor(getResources().getColor(R.color.black));

                if (arrayList.size() > 0) {
                    filterSupplier();
                }

            }
        });


        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_addDenaPawna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BakirKhata.this, NewContact.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                if (et_search_contact.getVisibility() == View.VISIBLE) {
                    btnSearch.setImageDrawable(getResources().getDrawable(R.drawable.ic_black_search_24));
                    //toolbar_title.setVisibility(View.VISIBLE);
                    et_search_contact.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(et_search_contact.getText().toString())) {
                        et_search_contact.getText().clear();
                    }
                } else {
                    //toolbar_title.setVisibility(View.GONE);
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

    private void getLoadData() {


        String url = ApiConstants.SALES_SUMMERY + sharedPreferenceClass.getSubscriberId() +
                "/" + sharedPreferenceClass.getStoreId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String totalDueValue = jsonObject.getString("totalDue");
                            if (totalDueValue.equals("null")) {
                                totalDueText.setText("0৳");
                            } else {
                                Double totalDueAmount = Double.valueOf(totalDueValue);
                                totalDueText.setText(Math.round(totalDueAmount) + "৳");
                            }

                            String totalDepositValue = jsonObject.getString("totalDeposit");
                            if (totalDepositValue.equals("null")) {
                                tv_totalReceiveAmount.setText("0৳");
                            } else {
                                Double totalDepositAmount = Double.valueOf(totalDepositValue);
                                tv_totalReceiveAmount.setText(Math.round(totalDepositAmount) + "৳");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BakirKhata.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void filterSupplier() {
        ArrayList<DueData> filteredList = new ArrayList<>();

        if (arrayList != null) {
            for (DueData item : arrayList) {
                if (item.getType() != null && item.getType().equals("supplier")) {
                    filteredList.add(item);
                }
            }

            dueClientAdapter.updateList(filteredList);
        }
    }

    private void filterAllContact() {

        dueClientAdapter = new DueClientAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(dueClientAdapter);
    }

    private void filterCustomer() {
        ArrayList<DueData> filteredList = new ArrayList<>();

        if (arrayList != null) {
            for (DueData item : arrayList) {
                if (item.getType() != null && item.getType().equals("customer")) {
                    filteredList.add(item);
                }
            }

            dueClientAdapter.updateList(filteredList);
        }

    }


    private void filter(String text) {
        ArrayList<DueData> filteredList = new ArrayList<>();

        if (arrayList != null) {
            for (DueData item : arrayList) {
                if (item.getClientName()
                        .toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            dueClientAdapter.filterList(filteredList);
        }
    }

    private void getServerData() {
        String urlGetServerData = ApiConstants.DUE_LIST + sharedPreferenceClass.getSubscriberId() + "/" + sharedPreferenceClass.getStoreId();

        System.out.print(urlGetServerData);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetServerData, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);

                        try {
                            Gson gson = new Gson();
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int p = 0; p < jsonArray.length(); p++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(p);
                                DueData data = gson.fromJson(String.valueOf(jsonObject), DueData.class);
                                arrayList.add(data);
                            }
                            if (arrayList.size() > 0) {
                                dueClientAdapter = new DueClientAdapter(getApplicationContext(), arrayList);
                                recyclerView.setAdapter(dueClientAdapter);
                            }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContactListActivity.fromMakePayment = false;
    }

}

