package com.inovex.bikroyik.UI.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inovex.bikroyik.NetworkStateReceiver;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.OrderPendingAdapter;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.DueSubmit;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.PaymentTypeForOrderJson;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class OrderPendingActivity extends AppCompatActivity {

    ListView listView;
    DatabaseSQLite databaseSQLite;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat month_date = new SimpleDateFormat("dd - MMMM - yyyy");
    String month = month_date.format(cal.getTime());
    TextView syncText;
    SharedPreference sharedPreference;
    RecyclerView recyclerView;
    OrderPendingAdapter orderPendingAdapter;
    ArrayList<String> order_id;
    ArrayList<String> total_price;
    ArrayList<String> grand_total;
    ArrayList<String> total_discount;
    ArrayList<String> product_id;
    ArrayList<String> orderId;
    private ClientListModel client = null;
    AirplaneModeChangeReceiver airplaneModeChangeReceiver = new AirplaneModeChangeReceiver();
    private Switch wifiSwitch;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("Order Pending");
        setContentView(R.layout.activity_order_pending);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseSQLite = new DatabaseSQLite(getApplicationContext());
        SQLiteDatabase sqLiteDatabase = databaseSQLite.getWritableDatabase();
        recyclerView = findViewById(R.id.order_pending_rv);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        wifiSwitch = findViewById(R.id.wifi_switch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        order_id = new ArrayList<>();
        total_price = new ArrayList<>();
        grand_total = new ArrayList<>();
        total_discount = new ArrayList<>();
        product_id = new ArrayList<>();
        orderId = new ArrayList<>();
        // listView = findViewById(R.id.listViewId);
        loadData();
        orderPendingAdapter = new OrderPendingAdapter(OrderPendingActivity.this, order_id, total_price, total_discount, total_discount);
        recyclerView.setAdapter(orderPendingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderPendingActivity.this));

        /*wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("WiFi is ON");
                    Toast.makeText(getApplicationContext(),"WiFi is ON",Toast.LENGTH_LONG).show();
                } else {
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("WiFi is OFF");
                    Toast.makeText(getApplicationContext(),"WiFi is OFF",Toast.LENGTH_LONG).show();
                }
            }
        });*/

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_sync_all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        if (item.getItemId() == R.id.sync_allId) {

            syncallData();
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadData() {

        Cursor cursor = databaseSQLite.showData();

        if (cursor.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                order_id.add(cursor.getString(0));
                total_price.add(cursor.getString(6));
                total_discount.add(cursor.getString(4));
                grand_total.add(cursor.getString(5));
                //product_id.add(cursor.getString(6));
                // grand_total.add(cursor.getString(8));
            }
        }


    }

    private void syncallData() {
        if (isNetworkAvailable()) {

            if (order_id.size() > 0) {
                for (int i = 0; i < order_id.size(); i++) {
                    OrderJsonModel orderJsonModel = new OrderJsonModel();
                    DueSubmit dueSubmit = null;
                    orderJsonModel.setOrderDate(Constants.getTodayDateString());
                    orderJsonModel.setOrderId(String.valueOf(order_id.get(i)));
                    orderJsonModel.setSalesBy(sharedPreference.getUserId());
                    orderJsonModel.setStoreId(sharedPreference.getStoreId());
                    orderJsonModel.setSubscriberId(sharedPreference.getSubscriberId());

                    Cursor cursor = databaseSQLite.showOrderData(String.valueOf(order_id.get(i)));

                    if (cursor.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                    } else {
                        while (cursor.moveToNext()) {
                            orderJsonModel.setTotal(Double.parseDouble(cursor.getString(3)));
                            orderJsonModel.setTotalDiscount(Double.parseDouble(cursor.getString(4)));
                            orderJsonModel.setTotalTax(Double.parseDouble(cursor.getString(5)));
                            orderJsonModel.setGrandTotal(Double.parseDouble(cursor.getString(6)));
                        }
                    }
                    List<PaymentTypeForOrderJson> paymentList = new ArrayList<>();
                    PaymentTypeForOrderJson mfsPay = new PaymentTypeForOrderJson();
                    PaymentTypeForOrderJson cashPay = new PaymentTypeForOrderJson();
                    PaymentTypeForOrderJson cardPay = new PaymentTypeForOrderJson();


                    Cursor cursor2 = databaseSQLite.showPaymentData(String.valueOf(order_id.get(i)));

                    if (cursor2.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                    } else {
                        while (cursor2.moveToNext()) {
                            Log.d("id_value", "" + cursor2.getString(3));

                            mfsPay.setId(cursor2.getString(3));
                            mfsPay.setType(cursor2.getString(4));
                            mfsPay.setAmount(Double.parseDouble(cursor2.getString(5)));
                            paymentList.add(mfsPay);

                            cashPay.setId("0000");
                            cashPay.setType("Cash");
                            cashPay.setAmount(Double.parseDouble(cursor2.getString(1)));
                            paymentList.add(cashPay);

                            cardPay.setId("0000");
                            cardPay.setType("Card");
                            cardPay.setAmount(Double.parseDouble(cursor2.getString(2)));
                            paymentList.add(cardPay);

                            orderJsonModel.setPaymentDetails(paymentList);
                            orderJsonModel.setClientId(Integer.parseInt(cursor2.getString(8)));
                            orderJsonModel.setClientName(cursor2.getString(9));

                        }
                    }

                    List<DiscountDetails> discountDetailsList = new ArrayList<>();
                    List<Tax> taxList = new ArrayList<>();
                    List<OrderedProductModel> orderDetailsList = new ArrayList<>();
                    Cursor cursor5 = databaseSQLite.showProductDetailsData(String.valueOf(order_id.get(i)));

                    if (cursor5.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                    } else {
                        while (cursor5.moveToNext()) {
                            OrderedProductModel orderDetails = new OrderedProductModel();
                            orderDetails.setProductId(cursor5.getString(1));
                            orderDetails.setProductName(cursor5.getString(2));
                            orderDetails.setQuantity(Integer.parseInt(cursor5.getString(3)));
                            orderDetails.setOfferItemId(cursor5.getString(4));
                            orderDetails.setOfferName(cursor5.getString(5));
                            orderDetails.setOfferQuantity(Integer.parseInt(cursor5.getString(6)));
                            orderDetails.setTotalDiscount(Double.valueOf(cursor5.getString(7)));
                            orderDetails.setTotalTax(Double.valueOf(cursor5.getString(8)));
                            orderDetails.setTotalPrice(Double.valueOf(cursor5.getString(9)));
                            orderDetails.setGrandTotal(Double.valueOf(cursor5.getString(10)));
                            List<DiscountDetails> list1 = getDiscountDetails(String.valueOf(order_id.get(i)), cursor5.getString(1));
                            List<Tax> list2 = getTaxDetails(String.valueOf(order_id.get(i)), cursor5.getString(1));

                            orderDetails.setDiscountDetails(list1);
                            orderDetails.setTax(list2);
                            orderDetailsList.add(orderDetails);

                        }
                    }

                    orderJsonModel.setOrderDetails(orderDetailsList);

                    sendClientInfoToServer(getApplicationContext(), ApiConstants.ORDER_SUBMIT, orderJsonModel);

                    Cursor cursor9 = databaseSQLite.showPaymentData(String.valueOf(order_id.get(i)));

                    if (cursor9.getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
                    } else {
                        while (cursor9.moveToNext()) {
                            Double dueValue = Double.valueOf(cursor9.getString(7));

                            if (dueValue > 0) {

                                dueSubmit = new DueSubmit();
                                dueSubmit.setCard(Double.parseDouble(cursor9.getString(2)));
                                dueSubmit.setCash(Double.parseDouble(cursor9.getString(1)));
                                dueSubmit.setClientId(String.valueOf(order_id.get(i)));
                                dueSubmit.setDate(Constants.getTodayDateString());
                                dueSubmit.setDue_amount(Double.parseDouble(cursor9.getString(7)));
                                dueSubmit.setMobile_bank(Double.parseDouble(cursor9.getString(5)));
                                dueSubmit.setStoreId(sharedPreference.getStoreId());
                                dueSubmit.setTotal(Double.parseDouble(cursor9.getString(6)));
                                dueSubmit.setSubscriberId(sharedPreference.getSubscriberId());
                                dueSubmit.setUserId(sharedPreference.getUserId());
                                sendClientInfoToServerDue(getApplicationContext(), ApiConstants.PAYMENT_SALE, dueSubmit);

                            }

                        }

                    }


                    int value = databaseSQLite.deleteOrderData(orderJsonModel.getOrderId());
                    if (value > 0) {
                        Toast.makeText(getApplicationContext(), "OrderData is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "OrderData is not deleted", Toast.LENGTH_SHORT).show();
                    }

                    int paymentValue = databaseSQLite.deletePaymentData(orderJsonModel.getOrderId());
                    if (paymentValue > 0) {
                        Toast.makeText(getApplicationContext(), "PaymentData is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "PaymentData is not deleted", Toast.LENGTH_SHORT).show();
                    }

                    int productOrderValue = databaseSQLite.deleteOrderProductData(orderJsonModel.getOrderId());
                    if (productOrderValue > 0) {
                        Toast.makeText(getApplicationContext(), "OrderProduct Data is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "OrderProduct is not deleted", Toast.LENGTH_SHORT).show();
                    }

                }

            }


        } else {
            Toast.makeText(getApplicationContext(), "Please Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public List<DiscountDetails> getDiscountDetails(String orderId, String productId) {
        List<DiscountDetails> discountDetailsList = new ArrayList<>();
        Cursor cursor3 = databaseSQLite.showDiscountDetailsData(orderId, productId);
        if (cursor3.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor3.moveToNext()) {
                DiscountDetails newDiscountDetails = new DiscountDetails();
                newDiscountDetails.setId(Integer.parseInt(cursor3.getString(0)));
                newDiscountDetails.setDiscountName(cursor3.getString(1));
                newDiscountDetails.setDiscountType(cursor3.getString(2));
                newDiscountDetails.setDiscount(Double.parseDouble(cursor3.getString(3)));
                newDiscountDetails.setDiscountApplied(Boolean.parseBoolean(cursor3.getString(4)));
                newDiscountDetails.setOrderId(cursor3.getString(5));
                newDiscountDetails.setProductId(cursor3.getString(6));
                discountDetailsList.add(newDiscountDetails);
            }
        }
        return discountDetailsList;
    }

    public List<Tax> getTaxDetails(String orderId, String productId) {
        List<Tax> taxList = new ArrayList<>();
        Cursor cursor4 = databaseSQLite.showTaxDetailsData(orderId, productId);
        if (cursor4.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor4.moveToNext()) {
                Tax tax = new Tax();
                tax.setId(Integer.parseInt(cursor4.getString(0)));
                tax.setTaxName(cursor4.getString(1));
                tax.setTaxAmount(Double.parseDouble(cursor4.getString(2)));
                tax.setIsTaxApplied(Boolean.parseBoolean(cursor4.getString(3)));
                tax.setOrderId(cursor4.getString(4));
                tax.setProductId(cursor4.getString(5));
                taxList.add(tax);

            }
        }
        return taxList;
    }

    private String prepareJson(OrderJsonModel orderJsonModel) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(orderJsonModel);
    }

    private String prepareJsonDue(DueSubmit dueSubmit) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(dueSubmit);
    }

    private void sendClientInfoToServer(Context context, String url, OrderJsonModel orderJsonModel) {
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson(orderJsonModel);
        Log.d("_order_submit_", "json: " + reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson(orderJsonModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: " + result);


                /*Intent intent = new Intent(context, Expense.class);
                context.startActivity(intent);*/
                if (!TextUtils.isEmpty(result) && "error".equals(result)) {
                    Log.d("_tag_", "response: " + result);
                    /*Toast toast = Toasty.error(context, "Error", Toast.LENGTH_SHORT);
                    toast.show();*/

                } else {
                    /*Toast toast=Toast.makeText(BillActivity.this,result.toString(),Toast.LENGTH_SHORT);
                    toast.show();*/

                    //Toasty.success(context, "Order added Successfully!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    private void sendClientInfoToServerDue(Context context, String url, DueSubmit dueSubmit) {
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJsonDue(dueSubmit);
        Log.d("_due_submit_", "json: " + reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJsonDue(dueSubmit), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: " + result);


                if (!TextUtils.isEmpty(result) && "error".equals(result)) {
                    Log.d("_tag_", "response: " + result);

                    /*Toast toast = Toasty.error(context, "Error", Toast.LENGTH_SHORT);
                    toast.show();*/
                    //Toasty.success(context, "Order added Successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    /*Toast toast=Toast.makeText(BillActivity.this,result.toString(),Toast.LENGTH_SHORT);
                    toast.show();*/

                    //

                }

            }
        });
    }




    /*@Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(airplaneModeChangeReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(airplaneModeChangeReceiver);
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }


    public void OrderPendingDataToServer()
    {
        if(isNetworkAvailable())
        {
            Cursor cursor = databaseSQLite.showData();

            if (cursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "No Order Found", Toast.LENGTH_SHORT).show();
            } else {
                while (cursor.moveToNext()) {
                    orderId.add(cursor.getString(0));
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Check network connection", Toast.LENGTH_SHORT).show();
        }

    }



    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    /*wifiSwitch.setChecked(true);
                    wifiSwitch.setText("WiFi is ON");*/
                    OrderPendingDataToServer();
                    //Toast.makeText(getApplicationContext(), "WiFi is ON", Toast.LENGTH_LONG).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    /*wifiSwitch.setChecked(false);
                    wifiSwitch.setText("WiFi is OFF");*/
                    Toast.makeText(getApplicationContext(), "WiFi is OFF", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };


}