package com.inovex.bikroyik.UI.activity;

import static com.google.android.gms.vision.L.TAG;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.HomepageAdapter;

import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.data.model.ProductJsonGetter;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.fragment.FragmentDrawer;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.viewmodel.EditHomePageViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 175;
    private static final int REQUEST_ENABLE_BT = 175;
    boolean doubleBackToExitPressedOnce = false;
    public int requestCode = 1;
    public static final String USER_TYPE = "user";
    public static final String USER_ID = "user_id";
    public static final String USER_PASS = "user_pass";
    private static final String LAST_CHECKED_IN = "last_checked_in";
    public static final String DIST_ID = "distId";
    public static final String LAST_ACTIVITY = "last_activity";
    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences sharedpreferences;
    SharedPreference sharedPreferenceClass;
    private SharedPreference sharedPreference;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public static Bitmap receipt_photoBitmap;
    private HomepageAdapter mAdapter;
    private TextView salesAmountToday,
            salesMonthly, salesToday, dueToday, expenseToday, tv_saleToday, tv_monthlySale,
            tv_today, tv_salesToday, tv_dueToday, tv_expenseToday;
    private EditHomePageViewModel mFeatureActivityViewModel;
    private RecyclerView recyclerView;
    ImageView imageView;
    private DatabaseSQLite databaseSQLite;
    private Context context;
    public Toolbar mToolbar;
    public static BluetoothSocket btsocket;
    public static BluetoothSocket bt;
    private VolleyMethods volleyMethods;
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> pairedDevices;
    LinearLayout connection;
    private RequestQueue mQueue;
    String userId, usePassword;
    private static final String PREF_NAME = "merchant_pref";
    int PRIVATE_MODE = 0;
    public boolean hasRun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


       // showsplash();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bikroyik);

        context = getApplicationContext();
        sharedPreferenceClass = SharedPreference.getInstance(context);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        userId = sharedpreferences.getString(USER_ID, null);
        usePassword = sharedpreferences.getString(USER_PASS, null);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        imageView = (ImageView) findViewById(R.id.dashboardIcon);
        salesAmountToday = (TextView) findViewById(R.id.todaySales);
        salesMonthly = (TextView) findViewById(R.id.tv_saleAmountMonthly);
        salesToday = (TextView) findViewById(R.id.tv_salesTodayAmount);
        dueToday = (TextView) findViewById(R.id.tv_dueAmountToday);
        expenseToday = (TextView) findViewById(R.id.tv_expenseAmountToday);
        tv_saleToday = (TextView) findViewById(R.id.tv_saleToday);
        tv_monthlySale = (TextView) findViewById(R.id.tv_monthlySale);
        tv_today = (TextView) findViewById(R.id.tv_today);
        tv_salesToday = (TextView) findViewById(R.id.tv_salesToday);
        tv_dueToday = (TextView) findViewById(R.id.tv_dueToday);
        tv_expenseToday = (TextView) findViewById(R.id.tv_expenseToday);
        if (sharedPreference.getLanguage() == null) {
            sharedPreference.setLanguage("English");
        }
        if (sharedPreference.getLanguage().equals("Bangla")) {
            tv_saleToday.setText("সর্বমোট বিক্রি");
            tv_monthlySale.setText("মাসের বিক্রি");
            tv_today.setText("আজকের ");
            tv_salesToday.setText("বিক্রি");
            tv_dueToday.setText("বাকি");
            tv_expenseToday.setText("ব্যয়");
            this.setTitle("বিক্রয়িক");
        }

        mQueue = Volley.newRequestQueue(this);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        checkPermission();
        init();
        printer();
        databaseSQLite = new DatabaseSQLite(getApplicationContext());
        volleyMethods = new VolleyMethods();
        mFeatureActivityViewModel = new ViewModelProvider(this).get(EditHomePageViewModel.class);
        mFeatureActivityViewModel.init(this);
        initRecyclerView();
        mFeatureActivityViewModel.getHomeFeatures().observe(this, new Observer<List<Features>>() {
            @Override
            public void onChanged(List<Features> featuresList) {
                initRecyclerView();
            }
        });

        callMerchantProduct();
        callMerchantDiscountApi();
        callMerchantTaxApi();

        connection = findViewById(R.id.lLConnect);
        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btsocket == null) {
                    connectPrinter();
                }
            }
        });

        /*drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);*/

        /*imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Fragment fragment = new FragmentDrawer();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainBikroyik,fragment).commit();

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = displayMetrics.heightPixels;
                int width = displayMetrics.widthPixels;


            }
        });*/

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


       /* tv_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                *//*String language = getResources().getString(R.string.language);
                changeLocale(language);*//*
         *//*
                String language = getResources().getString(R.string.language);
                //changeLocale(language);
                setLocale(language);*//*



            }
        });*/

        loadData();

        /*Log.d("subscriber_Id",""+sharedPreference.getSubscriberId());
        Log.d("store_Id",""+sharedPreference.getStoreId());*/


    }


    private void openPopUpWindow() {
        Intent popupWindow = new Intent(MainActivity.this, PopUpWindowActivitiy.class);
        startActivity(popupWindow);
    }



    /*private void setLocale(String language) {

        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = new Locale(language);
        resources.updateConfiguration(configuration,metrics);
        onConfigurationChanged(configuration);


    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        tv_language.setText(R.string.change_language);
        password.setText(R.string.title_change_password);
        dashboard.setText(R.string.title_home_fragment);
        profile.setText(R.string.title_profile);
        expense.setText(R.string.expenses);
        contact.setText(R.string.title_contacts);
        product.setText(R.string.products);
        printer.setText(R.string.printer);
        order.setText(R.string.sales_order);
        settings.setText(R.string.title_settings);
        changelanguage.setText(R.string.find_language);
       // String title = getString(R.string.app_name);
        *//*tvHomeToolbarTitle.setText(title);
        homepageText.setText(R.string.setting_menu_tittle);
        homepageDesc.setText(R.string.setting_menu_description);*//*
        setTitle("বিক্রয়িক");


    }*/

    private void loadData() {

        String url = ApiConstants.SALES_SUMMERY + sharedPreferenceClass.getSubscriberId() +
                "/" + sharedPreferenceClass.getStoreId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);


                            String salesTodayValue = jsonObject.getString("totalSales");

                            if (salesTodayValue.equals("null")) {
                                salesAmountToday.setText("৳: " + 0);
                            } else {
                                Double salesT = Double.valueOf(salesTodayValue);
                                salesAmountToday.setText("৳: " + Math.round(salesT));
                            }


                            String monthlySale = jsonObject.getString("monthSales");
                            Double monthS = Double.valueOf(monthlySale);
                            salesMonthly.setText("৳: " + Math.round(monthS));

                            String totalSale = jsonObject.getString("todaySales");
                            Double totalS = Double.valueOf(totalSale);
                            salesToday.setText("৳: " + Math.round(totalS));

                            String totalDue = jsonObject.getString("todayDue");

                            if (totalDue.equals("null")) {
                                dueToday.setText("৳: " + 0);
                            } else {
                                Double totalD = Double.valueOf(totalDue);
                                dueToday.setText("৳: " + Math.round(totalD));
                            }


                            String todayExpense = jsonObject.getString("todayExpense");
                            if (todayExpense.equals("null")) {
                                expenseToday.setText("৳: " + 0);
                            } else {
                                Double totalEX = Double.valueOf(todayExpense);
                                expenseToday.setText("৳: " + Math.round(totalEX));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void connectPrinter() {
        Gson gson = new Gson();
        String json = getSharedPreferences("Printer", MODE_PRIVATE).getString("name", null);
        btsocket = gson.fromJson(json, BluetoothSocket.class);
        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), DeviceList.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);

            if (btsocket != null) {
                bt = btsocket;
            }

        } else {
            Toast.makeText(getApplicationContext(), "Already connected.", Toast.LENGTH_SHORT).show();
            Log.d("workforce", "connectPrinter: " + btsocket);
        }

    }

    /*private void changeLocale(String language) {
//        if (language == "bn"){
//            tv_language.setText("বাংলা");
//        }else {
//            tv_language.setText("English");
//        }

        Log.d("change", "onClick: "+language);
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());

        Intent intent = new Intent(context, LoadingActivity.class);
        intent.putExtra("activity", "main");
        startActivity(intent);


    }*/

    /*private static final String[] BLE_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @RequiresApi(api = Build.VERSION_CODES.S)
    private static final String[] ANDROID_12_BLE_PERMISSIONS = new String[]{
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_CONNECT
    };*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFeatureActivityViewModel.queryHomepageFeatures();
        hasRun=false;
    }


    private void init() {
        recyclerView = findViewById(R.id.homeFeature_recycler);
    }

    private void printer() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pairedDevices = bluetoothAdapter.getBondedDevices();

        if (bluetoothAdapter == null) {
            Log.d("workforce", "onCreate: Not Support");
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

            /*IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(receiver, filter);*/


            if (pairedDevices != null) {
                Log.d("_device_", "device size: " + pairedDevices.size());
            }


        }
    }

    private void initRecyclerView() {
        mAdapter = HomepageAdapter.getInstance();
        mAdapter.init(MainActivity.this, mFeatureActivityViewModel.getHomeFeatures().getValue());
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    private void checkPermission() {


        // ask for permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck3 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck1
                != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED
                || permissionCheck3 != PackageManager.PERMISSION_GRANTED
        ) {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Permission disclosure");
            alert.setMessage("Bikroyik collects location data to discover Bluetooth devices and Bluetooth scan results  even when the app is closed or not in use.");
            alert.setCancelable(false);
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    ActivityCompat.requestPermissions(MainActivity.this,

                            new String[]{Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.BLUETOOTH_SCAN,
                                    Manifest.permission.BLUETOOTH_CONNECT,
                                    Manifest.permission.BLUETOOTH_ADMIN,
                                    Manifest.permission.BLUETOOTH_ADVERTISE,
                                    Manifest.permission.BLUETOOTH,
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.CAMERA}, REQUEST_PERMISSION);
                }
            });
            alert.create().show();
        }

    }




    private void callMerchantProduct() {
        String URL = ApiConstants.PRODUCT_LIST + sharedPreferenceClass.getSubscriberId() +
                "/" + sharedPreferenceClass.getStoreId();

        //Log.d("ProductSubscriberID", sharedPreferenceClass.getSubscriberId());
        //Log.d("ProductStoreID", sharedPreferenceClass.getStoreId());

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        Log.d("merchant_product", "callProductAPI: " + URL);

        volleyMethods.sendGetRequest(getApplicationContext(), URL, new VolleyCallBack() {
            @Override
            public void onSuccess(String response) {
                Log.d("workforce_product", "callProductAPI: " + response);
                if (response.length() > 0 && !"error".equals(response)) {

                    Gson gson = new Gson();
                    ProductJsonGetter productJsonGetter = gson.fromJson(response, ProductJsonGetter.class);

                    if (productJsonGetter != null && productJsonGetter.getData().size() > 0) {
                        databaseSQLite.deleteAllProductData();
                        for (ProductModel productModel : productJsonGetter.getData()) {
                            databaseSQLite.insertProductData(productModel);
                        }
                    }
                }
            }
        });

    }

    private void callMerchantDiscountApi() {
        String URL = ApiConstants.DISCOUNT_LIST + sharedPreferenceClass.getSubscriberId() + "/" + sharedPreferenceClass.getStoreId();
        Log.d("merchant_discount", "call discount: " + URL);

        volleyMethods.sendGetRequest(getApplicationContext(), URL, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_discount_", "Discount: " + result);

                try {
                    databaseSQLite.deleteDiscount("0", "0");
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (jsonArray.length() > 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Log.d("value", String.valueOf(object));
                            //boolean isRestricted = Boolean.parseBoolean(object.getString("isRestricted"));

                            if (object != null) {
                                Gson gson = new Gson();
                                DiscountDetails discountDetails = gson.fromJson(object.toString(), DiscountDetails.class);
                                discountDetails.setOrderId("0");
                                discountDetails.setProductId("0");
                                discountDetails.setDiscountApplied(false);
                                databaseSQLite.insertDiscountData(discountDetails);

                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void callMerchantTaxApi() {

        String URL = ApiConstants.TAX_LIST + sharedPreferenceClass.getSubscriberId();
        Log.d("merchant_product_tax", "call vat: " + URL);

        VolleyMethods volleyMethods = new VolleyMethods();
        volleyMethods.sendGetRequest(getApplicationContext(), URL, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tax_", "Tax: " + result);
                databaseSQLite.deleteTaxTableData("0", "0");

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                            if (object != null) {
                                Gson gson = new Gson();
                                Tax tax = gson.fromJson(object.toString(), Tax.class);
                                tax.setOrderId("0");
                                tax.setProductId("0");
                                databaseSQLite.insertTaxInfo(tax);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            //super.onBackPressed();
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Click twice to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy hh.mm aa");
                String lastTime = dateFormat2.format(new Date()).toString();
                SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(LAST_CHECKED_IN, lastTime);
                editor.commit();
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {


            }

        }
    }

    public void logOut(View view) {
        SharedPreferences pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        SharedPreferences.Editor editor = pref.edit();
        //editor.clear();
        editor.apply();
        editor.commit();
        Intent intent = new Intent(MainActivity.this, SigninActivity.class);
        startActivity(intent);
        finish();
    }



    public void showsplash() {

        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_splash2);
        dialog.setCancelable(true);
        dialog.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                {
                    dialog.dismiss();
                }
            }
        };
        handler.postDelayed(runnable, 3000);
    }


}

