package com.inovex.bikroyik.UI.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.ClientAdapter;
import com.inovex.bikroyik.UI.adapter.ProductAdapterForLandscapeMode;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.ClientListResponseModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.interfaces.ClientCallback;
import com.inovex.bikroyik.interfaces.ProductItemCallback;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;
import com.inovex.bikroyik.viewmodel.SalesActivityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.net.CacheRequest;
import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {
    Button btnAllContact, btnSupplier, btnCustomer;
    ImageView btnBack, btnSearch;
    LinearLayout addNewContact;
    VolleyMethods volleyMethods;
    RecyclerView recyclerView;
    ClientAdapter clientAdapter;
    ClientCallback clientCallback;
    TextView toolbar_title,addButtonText;
    EditText et_search_contact;
    private List<ClientListModel> gClientListModel;
    List<ClientListModel> customerListModels = new ArrayList<>();
    List<ClientListModel> supplierListModels = new ArrayList<>();
    public static boolean fromMakePayment = false;
    OrderActivityViewModel orderActivityViewModel;
    SalesActivityViewModel mProductViewModel;
    SharedPreference sharedPreference;
    boolean flag = false;
    private DatabaseSQLite databaseSQLite;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        init();
        toolbar_title = findViewById(R.id.toolbar_title);
        addButtonText = findViewById(R.id.addButtonText);
        if(sharedPreference.getLanguage().equals("English"))
        {
            toolbar_title.setText("Contact List");
            btnAllContact.setText("contact");
            btnCustomer.setText("customer");
            btnSupplier.setText("supplier");
            addButtonText.setText("Add New");
            et_search_contact.setHint("contact list");

        }


        volleyMethods = new VolleyMethods();
        databaseSQLite = new DatabaseSQLite(getApplicationContext());


        clientCallback = new ClientCallback() {
            @Override
            public void callback(ClientListModel client) {
                if (fromMakePayment){
                    sendToMakePaymentActivity(client);
                    finish();
                }
            }
        };
        if(isNetworkAvailable())
        {
            sendLoginRequestToServer(getApplicationContext());
        }
        else{

            if (databaseSQLite.getClientData()!= null){
                gClientListModel = databaseSQLite.getClientData();

                setAdapter(gClientListModel, fromMakePayment);
            }
        }


        // setAdapter(databaseSQLite.getClientData(), fromMakePayment);



        orderActivityViewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(getApplicationContext(), sharedPreference.getCurrentOrderId());

        mProductViewModel = new ViewModelProvider(this).get(SalesActivityViewModel.class);
        mProductViewModel.init(getApplicationContext());


        btnAllContact.setBackground(getDrawable(R.drawable.button_in_contact_list));
        btnAllContact.setTextColor(getResources().getColor(R.color.white));




        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactListActivity.this, MainActivity.class));
            }
        });

        addNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactListActivity.this, NewContact.class));
            }
        });

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


                if (gClientListModel != null){
                    setAdapter(gClientListModel, fromMakePayment);
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

                if (gClientListModel != null) {
                    customerListModels.clear();
                    for (ClientListModel clientModel: gClientListModel) {
                        if (clientModel.getType() != null && clientModel.getType().equals("customer")){
                            customerListModels.add(clientModel);
                        }
                    }
                    setAdapter(customerListModels, fromMakePayment);
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

                if (gClientListModel != null) {
                    supplierListModels.clear();
                    for (ClientListModel clientModel: gClientListModel) {
                        if (clientModel.getType() != null && clientModel.getType().equals("supplier")){
                            supplierListModels.add(clientModel);
                        }
                    }
                    setAdapter(supplierListModels, fromMakePayment);
                }

            }
        });


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



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void sendToMakePaymentActivity(ClientListModel client){
        Intent intent = new Intent();
        intent.setAction(MakePaymentActivity.MyBroadcastReceiver.ACTION);
        intent.putExtra("dataToPass", client);
        getApplicationContext().sendBroadcast(intent);
    }



    private void init(){
        btnAllContact = (Button) findViewById(R.id.btn_allContact);
        btnSupplier = (Button) findViewById(R.id.btn_supplier);
        btnCustomer = (Button) findViewById(R.id.btn_customer);
        btnBack = (ImageView) findViewById(R.id.btn_imageBack);
        btnSearch = (ImageView) findViewById(R.id.btn_search);
        addNewContact = (LinearLayout) findViewById(R.id.btn_add_ll);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        et_search_contact = (EditText) findViewById(R.id.et_search_contact);
    }

    private void setAdapter(List<ClientListModel> clientList, boolean fromPayment){
        Log.d("client_model",""+clientList);
        clientAdapter = ClientAdapter.getInstance();

        //clientList = databaseSQLite.getClientData();
        clientAdapter.init(getApplicationContext(), clientList, clientCallback);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(clientAdapter);
    }




   /* private void setAdapter(List<ClientListModel> clientList, boolean fromPayment){
        clientAdapter = ClientAdapter.getInstance();
        clientAdapter.init(getApplicationContext(), clientList, clientCallback);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(clientAdapter);
        Log.d("tag", "success");

    }*/



    private void filter(String text) {
        ArrayList<ClientListModel> filteredList = new ArrayList<>();

        if (gClientListModel != null){
            for (ClientListModel item : gClientListModel) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }

            clientAdapter.filterList(filteredList);
        }
    }



    private void sendLoginRequestToServer(Context context){
        volleyMethods.sendGetRequest(context, ApiConstants.CLIENT_LIST_URL +
                sharedPreference.getSubscriberId(), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_signin_", ""+result);

                if (!TextUtils.isEmpty(result) && !result.equals("error")){
                    Gson gson = new Gson();


                    ClientListResponseModel clientResponse = gson.fromJson(result, ClientListResponseModel.class);

                    if (clientResponse != null && clientResponse.getData().size() > 0) {
                        databaseSQLite.deleteClientData();
                        for (ClientListModel clientListModel : clientResponse.getData()) {
                            databaseSQLite.insertClientData(clientListModel);
                            Toast.makeText(getApplicationContext(),"update",Toast.LENGTH_LONG).show();
                        }
                    }

                    /*setAdapter(databaseSQLite.getClientData(), fromMakePayment);
                    Log.d("client-data",""+clientResponse.getData());*/

                    if (clientResponse.getData() != null){
                        gClientListModel = clientResponse.getData();
                        setAdapter(clientResponse.getData(), fromMakePayment);
                    }

                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ContactListActivity.fromMakePayment = false;
    }



}