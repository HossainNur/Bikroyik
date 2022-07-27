package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.UI.activity.MainActivity.REQUEST_PERMISSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.OrderModel;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.Client;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.DueSubmit;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.PaymentTypeForOrderJson;
import com.inovex.bikroyik.data.model.PaymentTypeModel;
import com.inovex.bikroyik.data.model.response_model.MobileBankingApiResponse;
import com.inovex.bikroyik.data.model.response_model.MobilePaymentModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MakePaymentActivity extends AppCompatActivity {
    TextView tv_totalAmount, tv_contactName, tv_phoneNumber, addClientText, tv_dueText;
    EditText et_cashAmount;
    EditText et_cardPayment;
    EditText et_mobileBankingPayment;
    Button btn_charge;
    ImageView backButton, profile_image;
    LinearLayout ll_btn_cardPayment, ll_btn_cashPayment;
    MaterialSpinner spinner_mobileBanking;
    private ClientListModel client = null;
    int cid = 0;
    private MyBroadcastReceiver receiver;
    private List<MobilePaymentModel> mobilePaymentModelList = new ArrayList<>();

    private double gTotalAmount = 0.0;
    private double gCashAmount = 0.0;
    private double gCashValue = 0.0;
    private double gCardAmount = 0.0;
    private double gMoBankingAmount = 0.0;
    private double gDueAmount = 0.0;
    private List<String> mobileBankList = new ArrayList<>();
    private PaymentTypeForOrderJson paymentTypeForOrderJson = new PaymentTypeForOrderJson();
    DatabaseSQLite databaseSQLite;
    SharedPreference sharedPreference;
    private ConstraintLayout btn_addClient, client_view, btnCustomer_view;
    private LinearLayout add_client;

    private DueSubmit dueSubmit = new DueSubmit();
    private boolean clientView = false;
    private PaymentTypeModel paymentTypeModel;
    private MobilePaymentModel mobilePaymentModel;
    private OrderModel orderModelNew;
    private OrderedProductModel orderModel;
    String payment_type;

    OrderActivityViewModel orderActivityViewModel;
    VolleyMethods volleyMethods;

    Calendar cal = Calendar.getInstance();
    SimpleDateFormat due = new SimpleDateFormat("yyyy-MM-dd");
    String due_payment = due.format(cal.getTime());
    Object selected,index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        init();
        receiveClient();

        databaseSQLite = new DatabaseSQLite(getApplicationContext());
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        volleyMethods = new VolleyMethods();

        sharedPreference.setClientName(null);
        sharedPreference.setClientContact(null);
        sharedPreference.setDueAmount(0);

        String a = getIntent().getStringExtra(Constants.KEY_INTENT_EXTRA_TOTAL_CHARGE);

        tv_totalAmount.setText(a);
        et_cashAmount.setText(a);

        orderActivityViewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(getApplicationContext(), sharedPreference.getCurrentOrderId());

        if (sharedPreference.getLanguage().equals("Bangla")) {
            addClientText.setText("ক্লায়েন্ট যোগ করুন");
            tv_dueText.setText("সর্বমোট বাকির পরিমাণ");
            btn_charge.setText("বিল");
        }

        paymentTypeModel = new PaymentTypeModel();
        orderModelNew = new OrderModel();
        orderModel = new OrderedProductModel();
        mobilePaymentModel = new MobilePaymentModel();

        if (!TextUtils.isEmpty(tv_totalAmount.getText().toString())) {
            gTotalAmount = Double.parseDouble(tv_totalAmount.getText().toString());
        }

        if (!TextUtils.isEmpty(et_cashAmount.getText().toString())) {
            /*DecimalFormat df = new DecimalFormat("#.##");
            Double.parseDouble(df.format(gCashAmount));*/
            gCashAmount = Double.parseDouble(et_cashAmount.getText().toString());
        }

        if (!TextUtils.isEmpty(et_cardPayment.getText().toString())) {
            gCardAmount = Double.parseDouble(et_cardPayment.getText().toString());
        }
        if (!TextUtils.isEmpty(et_mobileBankingPayment.getText().toString())) {
            gMoBankingAmount = Double.parseDouble(et_mobileBankingPayment.getText().toString());
        }


        et_cardPayment.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double cashAmount = 0.0;

                if (!TextUtils.isEmpty(tv_totalAmount.getText().toString())
                        && Double.parseDouble(String.valueOf(tv_totalAmount.getText().toString())) <= 0.0) {
                    cashAmount = 0.0;

                } else {
                    if (gCashAmount >= 0.0) {
                        double currentCardAmount = 0.0;
                        if (!TextUtils.isEmpty(et_cardPayment.getText().toString())) {
                            currentCardAmount = Double.parseDouble(et_cardPayment.getText().toString());
                        }

                        if (gCardAmount > currentCardAmount) {
                            double lessAmount = (gCardAmount - currentCardAmount);
                            gCashAmount += lessAmount;

                            /*DecimalFormat df = new DecimalFormat("#.##");
                            Double.parseDouble(df.format(gCashAmount));*/


                            et_cashAmount.setText(String.valueOf(gCashAmount));

                            gCardAmount = currentCardAmount;
                        } else {
                            double addedAmount = (currentCardAmount - gCardAmount);
                            gCashAmount -= addedAmount;

                            if (gCashAmount >= 0.0) {
                                gCardAmount = currentCardAmount;
                                et_cashAmount.setText(String.valueOf(gCashAmount));
                            } else {
                                et_cardPayment.setText(String.valueOf(gCardAmount));
                                gCashAmount = 0.0;
                                gCashAmount = gTotalAmount - (gDueAmount + gCardAmount + gCashAmount + gMoBankingAmount);
                                et_cashAmount.setText(String.valueOf(gCashAmount));
                            }
                        }
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_mobileBankingPayment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double cashAmount = 0.0;


                if (!TextUtils.isEmpty(tv_totalAmount.getText().toString())
                        && Double.parseDouble(String.valueOf(tv_totalAmount.getText().toString())) <= 0.0) {
                    cashAmount = 0.0;

                } else {
                    if (gCashAmount >= 0.0) {
                        double currentMoAmount = 0.0;
                        if (!TextUtils.isEmpty(et_mobileBankingPayment.getText().toString())) {
                            currentMoAmount = Double.parseDouble(et_mobileBankingPayment.getText().toString());
                        }
                        if (gMoBankingAmount > currentMoAmount) {
                            double lessAmount = (gMoBankingAmount - currentMoAmount);
                            gCashAmount += lessAmount;

                            et_cashAmount.setText(String.valueOf(gCashAmount));

                            gMoBankingAmount = currentMoAmount;
                        } else {
                            double addedAmount = (currentMoAmount - gMoBankingAmount);
                            gCashAmount -= addedAmount;

                            if (gCashAmount >= 0.0) {
                                gMoBankingAmount = currentMoAmount;
                                et_cashAmount.setText(String.valueOf(gCashAmount));
                            } else {
                                et_mobileBankingPayment.setText(String.valueOf(gMoBankingAmount));
                                gCashAmount = 0.0;
                                gCashAmount = gTotalAmount - (gDueAmount + gCardAmount + gCashAmount + gMoBankingAmount);
                                et_cashAmount.setText(String.valueOf(gCashAmount));
                            }

                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


       /* public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            if (s.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (IsNetworkAvailable(context)) {
                    // update your online data-base and delete all rows from SQlite
                }
                return;
            }
        }

        public static boolean isNetworkAvailable(Context mContext) {
            Context context = mContext.getApplicationContext();
            ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED){
                            return true;
                        }
                    }
                }
            }
            return false;
        }*/


        btn_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkPermission()) {

                    String currentOrderId = sharedPreference.getCurrentOrderId();
                    OrderJsonModel orderJsonModel = databaseSQLite.getOrderDataWithAllOrderedProducts(currentOrderId);


                    if (orderJsonModel != null) {
                        String jsonBody = prepareJsonBody(orderJsonModel);

                        if (isCorrectCalculation()) {
                            if (isPayAll()) {
                                sendOrderToServer(getApplicationContext(), orderJsonModel, jsonBody, null);
                            } else {
                                //open a popup for make bakir khata
                                if (add_client.getVisibility() == View.VISIBLE) {
                                    showPopupAddClient(getApplicationContext());
                                } else {

                                    String jsonBodyBaki = duePaymentRequestBody(dueSubmit);
                                    if (jsonBodyBaki != null) {
                                        sendOrderToServer(getApplicationContext(), orderJsonModel, jsonBody, jsonBodyBaki);
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "unexpected error to submit baki!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "calculation is incorrect!!", Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "order not found!", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        orderActivityViewModel.getClientLiveData().observe(MakePaymentActivity.this, new Observer<Client>() {
            @Override
            public void onChanged(Client client) {
                Log.d("_client_", "client id: " + client.getName());
            }
        });


        btnCustomer_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactListActivity.fromMakePayment = true;
                Intent intent = new Intent(MakePaymentActivity.this, ContactListActivity.class);
                startActivity(intent);
            }
        });

        mobileBankList.clear();

//        mobileBankList.add("BKash");
//        mobileBankList.add("Nagad");

//        setMaterialSpinner(getApplicationContext(), mobileBankList);

        getMobileBankingMethods(getApplicationContext());
    }

    private void init() {
        backButton = (ImageView) findViewById(R.id.img_btn_back_makePayment);
        tv_totalAmount = (TextView) findViewById(R.id.tv_total_amount);
        et_cashAmount = (EditText) findViewById(R.id.et_totalAmount);
        ll_btn_cardPayment = (LinearLayout) findViewById(R.id.ll_btn_cardPayment);
        ll_btn_cashPayment = (LinearLayout) findViewById(R.id.ll_btn_cashPayment);
        btn_charge = (Button) findViewById(R.id.btn_charge_makePayment);
        et_cardPayment = (EditText) findViewById(R.id.et_cardPayment);
        et_mobileBankingPayment = (EditText) findViewById(R.id.et_mobileBankingPayment);
        spinner_mobileBanking = (MaterialSpinner) findViewById(R.id.spinner_mobileBanking);
        tv_contactName = (TextView) findViewById(R.id.tv_contactName);
        tv_phoneNumber = (TextView) findViewById(R.id.tv_phoneNumber);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        btn_addClient = (ConstraintLayout) findViewById(R.id.btn_addClient);
        btnCustomer_view = (ConstraintLayout) findViewById(R.id.customer_view);
        add_client = (LinearLayout) findViewById(R.id.add_client);
        client_view = (ConstraintLayout) findViewById(R.id.client_view);
        addClientText = (TextView) findViewById(R.id.addClientText);
        tv_dueText = (TextView) findViewById(R.id.tv_dueText);
    }

    private boolean checkPermission() {
        // ask for permissions
        int permissionCheck1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);


        if (permissionCheck1
                != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        } else {
            return true;
        }

        return false;
    }
    public class MyBroadcastReceiver extends BroadcastReceiver {
        public static final String ACTION = "com.inovex.merchant.ACTION_SOMETHING";

        @Override
        public void onReceive(Context context, Intent intent) {
            client = (ClientListModel) intent.getSerializableExtra("dataToPass");
            if (client != null) {
                Log.d("_client_", "data: " + client.getName());
                if (add_client.getVisibility() == View.VISIBLE) {
                    add_client.setVisibility(View.GONE);
                    client_view.setVisibility(View.VISIBLE);
                    btn_addClient.setBackgroundColor(getResources().getColor(R.color.white_4));
                }

                TextDrawable drawable = TextDrawable.builder().beginConfig()
                        .width(40)  // width in px
                        .height(40) // height in px
                        .endConfig()
                        .buildRound(client.getName()
                                .trim().substring(0, 1), getResources().getColor(R.color.light_coral));
                profile_image.setImageDrawable(drawable);
                tv_contactName.setText(client.getName());
                tv_phoneNumber.setText(client.getMobile());

                sharedPreference.setClientName(client.getName());
                sharedPreference.setClientContact(client.getMobile());
                sharedPreference.setClientId(String.valueOf(client.getId()));


                Log.d("clientName_new",""+sharedPreference.getClientId());
            }
        }
    }

    private String prepareJsonBody(OrderJsonModel orderJsonModel) {
        if (client != null) {
            orderJsonModel.setClientId(client.getId());
            orderJsonModel.setClientName(client.getName());

        }
        orderJsonModel.setStoreId(sharedPreference.getStoreId());

        orderJsonModel.setSubscriberId(sharedPreference.getSubscriberId());
        orderJsonModel.setSalesBy(sharedPreference.getUserId());
        List<PaymentTypeForOrderJson> paymentTypeForOrderJsons = new ArrayList<>();

        if (paymentTypeForOrderJson != null && (!TextUtils.isEmpty(et_mobileBankingPayment.getText().toString())
                && Double.parseDouble(et_mobileBankingPayment.getText().toString()) > 0.0)) {

            if (mobilePaymentModelList != null &&
                    TextUtils.isEmpty(paymentTypeForOrderJson.getId()) &&
                    mobilePaymentModelList.size() > 0) {
                paymentTypeForOrderJson.setId(String.valueOf(mobilePaymentModelList.get(0).getId()));
                paymentTypeForOrderJson.setType(String.valueOf(mobilePaymentModelList.get(0).getPaymentType()));


            } else if (TextUtils.isEmpty(paymentTypeForOrderJson.getId()) && mobilePaymentModelList != null) {
                paymentTypeForOrderJson.setId("0000");
                paymentTypeForOrderJson.setType("unknown");
            }
            paymentTypeForOrderJson.setAmount(Double.parseDouble(et_mobileBankingPayment.getText().toString()));

            paymentTypeForOrderJsons.add(paymentTypeForOrderJson);
        }

        if (!TextUtils.isEmpty(et_cardPayment.getText().toString())
                && Double.parseDouble(et_cardPayment.getText().toString()) > 0.0) {
            PaymentTypeForOrderJson paymentTypeForOrderJsonCard = new PaymentTypeForOrderJson("0000", "card",
                    Double.parseDouble(et_cardPayment.getText().toString()));
            paymentTypeForOrderJsons.add(paymentTypeForOrderJsonCard);
        }

        if (!TextUtils.isEmpty(et_cashAmount.getText().toString())
                && Double.parseDouble(et_cashAmount.getText().toString()) > 0.0) {
            PaymentTypeForOrderJson paymentTypeForOrderJsonCash = new PaymentTypeForOrderJson("0000", "cash",
                    Double.parseDouble(et_cashAmount.getText().toString()));
            paymentTypeForOrderJsons.add(paymentTypeForOrderJsonCash);
        }


        orderJsonModel.setPaymentDetails(paymentTypeForOrderJsons);
        orderJsonModel.setOrderDate(Constants.getTodayDateString());

        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonBody = gson.toJson(orderJsonModel);

        return jsonBody;
    }



    private void getMobileBankingMethods(Context context) {




        if(isNetworkAvailable())
        {
            String URL = ApiConstants.MOBILE_BANKING_PAYMENTS + sharedPreference.getSubscriberId();
            Log.d("merchant_product", "call discount: " + URL);


            volleyMethods.sendGetRequest(getApplicationContext(), URL, new VolleyCallBack() {
                @Override
                public void onSuccess(String result) {
                    Log.d("_discount_", "Discount: " + result);
                    Gson gson = new Gson();
                    MobileBankingApiResponse mobileBankingApiResponse = gson.fromJson(result.toString(), MobileBankingApiResponse.class);

                    if (mobileBankingApiResponse != null && mobileBankingApiResponse.getData().size() > 0) {
                        databaseSQLite.deletePaymentTypeData();
                        for (MobilePaymentModel mobilePaymentModel : mobileBankingApiResponse.getData()) {
                            databaseSQLite.insertPaymentTypeData(mobilePaymentModel);
                        }
                    }

               /* if(databaseSQLite.getPaymentTypeData()!= null)
                {
                    mobilePaymentModelList =
                }*/



                if (mobileBankingApiResponse.getStatusCode() == 200 ||
                        mobileBankingApiResponse.getStatusCode() == 201) {
                    mobilePaymentModelList = mobileBankingApiResponse.getData();

                    mobileBankList.clear();
                    for (MobilePaymentModel paymentModel : mobilePaymentModelList) {
                        mobileBankList.add(paymentModel.getPaymentType());
                    }
                    setMaterialSpinner(context, mobileBankList);
                }



                }
            });
        }

        else{

            if (databaseSQLite.getPaymentTypeData()!= null){
                mobilePaymentModelList = databaseSQLite.getPaymentTypeData();
                mobileBankList.clear();
                for (MobilePaymentModel paymentModel : mobilePaymentModelList) {
                    mobileBankList.add(paymentModel.getPaymentType());
                    //mobileBankList.add(String.valueOf(paymentModel.getId()));

                }
                setMaterialSpinner(context, mobileBankList);
            }




        }


    }





    private void setMaterialSpinner(Context mContext, List<String> bankList) {
        ArrayAdapter spinner = new ArrayAdapter(mContext, R.layout.spinner_layout, bankList);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_mobileBanking.setAdapter(spinner);
        spinner_mobileBanking.setDropdownMaxHeight(450);




        spinner_mobileBanking.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {


                selected = spinner_mobileBanking.getItems().get(position);
                index = spinner_mobileBanking.getSelectedIndex();
                Log.d("select",""+selected);

                if (mobilePaymentModelList != null && mobilePaymentModelList.size() >= position) {
                    MobilePaymentModel mobilePaymentModel = mobilePaymentModelList.get(position);




                    if (paymentTypeForOrderJson != null) {
                        paymentTypeForOrderJson.setType(mobilePaymentModel.getPaymentType());
                        paymentTypeForOrderJson.setId(String.valueOf(mobilePaymentModel.getId()));
                        payment_type = mobilePaymentModel.getPaymentType();
                        Log.d("payment", "" + payment_type);

                    } else {
                        paymentTypeForOrderJson = new PaymentTypeForOrderJson();
                        paymentTypeForOrderJson.setType(mobilePaymentModel.getPaymentType());
                        paymentTypeForOrderJson.setId(String.valueOf(mobilePaymentModel.getId()));
                        //payment_type = mobilePaymentModel.getPaymentType();
                        //Log.d("payment",""+payment_type);
                    }
                }
            }
        });

    }


    private String duePaymentRequestBody(DueSubmit dueSubmit) {
        if (dueSubmit != null) {
            dueSubmit.setSubscriberId(sharedPreference.getSubscriberId());
            dueSubmit.setUserId(sharedPreference.getUserId());
            sharedPreference.setDueAmount(Float.parseFloat(String.valueOf(dueSubmit.getDue_amount())));
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.toJson(dueSubmit);
        }
        return null;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void sendOrderToServer(Context context, OrderJsonModel orderJsonModel, String requestBody, String jsonBodyBaki) {
        String url = ApiConstants.ORDER_SUBMIT;

        VolleyMethods volleyMethods = new VolleyMethods();
        Log.d("_response_", "OrderPostApi RequestBody: " + requestBody);


        if (isNetworkAvailable()) {

            volleyMethods.sendPostRequestToServer(context, url, requestBody, new VolleyCallBack() {
                @Override
                public void onSuccess(String result) {


                    if (!TextUtils.isEmpty(result) && !"error".equals(result)) {
                        databaseSQLite.updateOrderCompleted(orderJsonModel, true);
                        sharedPreference.setOrderIdForPrint(sharedPreference.getCurrentOrderId());
                        sharedPreference.setCurrentOrderId(null);

                        Log.d("due_Json", "due" + jsonBodyBaki);

                        if (jsonBodyBaki != null) {
                            sendBakiToServer(getApplicationContext(), jsonBodyBaki);
                        }

                        afterPayments();
                        showPopupForGotoPrint(context);




                        /*paymentTypeModel.setCash(gCashAmount);
                        paymentTypeModel.setCard(gCardAmount);
                        paymentTypeModel.setType_amount(gMoBankingAmount);
                        paymentTypeModel.setTotal_amount(gTotalAmount);
                        paymentTypeModel.setOrderId(orderJsonModel.getOrderId());
                        //paymentTypeModel.setOrderId(sharedPreference.getCurrentOrderId());
                        paymentTypeModel.setType_payment(String.valueOf(selected));
                        //paymentTypeModel.setMfsId("00");
                        paymentTypeModel.setMfsId("00");
                        paymentTypeModel.setDue(dueSubmit.getDue_amount());

                        orderModelNew.setGrand_total(orderJsonModel.getGrandTotal());
                        orderModelNew.setDiscount(orderJsonModel.getTotalDiscount());
                        orderModelNew.setTax(orderJsonModel.getTotalTax());*/

                        /*Log.d("due_new", "" + orderModelNew.getGrand_total());

                        long rowId = databaseSQLite.paymentInsert(paymentTypeModel);

                        if (rowId > 0) {
                            Toast.makeText(context, "Successfully insert", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Unsuccessfully insert", Toast.LENGTH_SHORT).show();
                        }
*/
                        /*int value = databaseSQLite.deleteOrderData(orderJsonModel.getOrderId());
                        if (value > 0) {
                            Toast.makeText(getApplicationContext(), "OrderData is deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "OrderData is not deleted", Toast.LENGTH_SHORT).show();
                        }

                        *//*int paymentValue = databaseSQLite.deletePaymentData(orderJsonModel.getOrderId());
                        if (paymentValue > 0) {
                            Toast.makeText(getApplicationContext(), "PaymentData is deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "PaymentData is not deleted", Toast.LENGTH_SHORT).show();
                        }*//*

                        int productOrderValue = databaseSQLite.deleteOrderProductData(orderJsonModel.getOrderId());
                        if (productOrderValue > 0) {
                            Toast.makeText(getApplicationContext(), "OrderProduct Data is deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "OrderProduct is not deleted", Toast.LENGTH_SHORT).show();
                        }*/


                    }

                   /* else {
                        sharedPreference.setOrderIdForPrint(sharedPreference.getCurrentOrderId());
                        showPopupForGotoPrint(context);
                        Log.d("_response_", "OrderPostApi Response: "+result);
                        Toast.makeText(context, "Error found!!!", Toast.LENGTH_SHORT).show();
                    }*/





                }
            });

        } else {

            if(et_cardPayment.getText().toString().isEmpty())
            {
                paymentTypeModel.setCash(0.0);
            }
            else{
                paymentTypeModel.setCash(Double.valueOf(et_cardPayment.getText().toString()));
            }

            paymentTypeModel.setCard(gCardAmount);
            paymentTypeModel.setType_amount(gMoBankingAmount);
            paymentTypeModel.setTotal_amount(gTotalAmount);
            paymentTypeModel.setOrderId(orderJsonModel.getOrderId());
            //paymentTypeModel.setOrderId(sharedPreference.getCurrentOrderId());
            paymentTypeModel.setType_payment(String.valueOf(selected));
            paymentTypeModel.setMfsId(String.valueOf(index));
            paymentTypeModel.setDue(dueSubmit.getDue_amount());
            paymentTypeModel.setClientId(String.valueOf(orderJsonModel.getClientId()));
            paymentTypeModel.setClientName(orderJsonModel.getClientName());

            orderModelNew.setGrand_total(orderJsonModel.getGrandTotal());
            orderModelNew.setDiscount(orderJsonModel.getTotalDiscount());
            orderModelNew.setTax(orderJsonModel.getTotalTax());


            Log.d("due_new", "" + orderModelNew.getGrand_total());

            long rowId = databaseSQLite.paymentInsert(paymentTypeModel);

            if (rowId > 0) {
                Toast.makeText(context, "Successfully insert", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Unsuccessfully insert", Toast.LENGTH_SHORT).show();
            }


            sharedPreference.setOrderIdForPrint(sharedPreference.getCurrentOrderId());
            afterPayments();
            showPopupForGotoPrint(context);
            databaseSQLite.updateOrderCompleted(orderJsonModel, true);

        }


    }


    private void sendBakiToServer(Context context, String requestBody) {
        String url = ApiConstants.PAYMENT_SALE;

        VolleyMethods volleyMethods = new VolleyMethods();
        Log.d("_due_", "DuePayment RequestBody: " + requestBody);
        volleyMethods.sendPostRequestToServer(context, url, requestBody, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {

                if (!TextUtils.isEmpty(result) && !"error".equals(result)) {
                    Toast.makeText(context, "due added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Error found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void gotoPrintActivity() {
        Intent intent = new Intent(MakePaymentActivity.this, OrderPrintActivity.class);
        startActivity(intent);
    }

    private void afterPayments() {
        sharedPreference.setCurrentOrderId(null);
        tv_totalAmount.setText("0.0");
        et_cashAmount.setText("");
        et_cardPayment.setText("");
        et_mobileBankingPayment.setText("");
        orderActivityViewModel.makeMapEmpty();
        orderActivityViewModel.orderJsonModelMutableLiveData.setValue(null);
        orderActivityViewModel.setTotalOrderItem(0);

        if (add_client.getVisibility() != View.VISIBLE) {
            add_client.setVisibility(View.VISIBLE);
            client_view.setVisibility(View.GONE);
        }
    }

    private List<PaymentTypeForOrderJson> paymentTypeList() {
        double payment = 0;
        List<PaymentTypeForOrderJson> paymentTypesList = new ArrayList<>();

        if (!TextUtils.isEmpty(et_cashAmount.getText().toString())) {
            payment = Double.parseDouble(et_cashAmount.getText().toString());
            PaymentTypeForOrderJson paymentTypeForOrderJson = new PaymentTypeForOrderJson("1", "cash",
                    payment);

            paymentTypesList.add(paymentTypeForOrderJson);
        }

        if (!TextUtils.isEmpty(et_cardPayment.getText().toString())) {
            payment = Double.parseDouble(et_cardPayment.getText().toString());
            PaymentTypeForOrderJson paymentTypeForOrderJson = new PaymentTypeForOrderJson("1", "card",
                    payment);

            paymentTypesList.add(paymentTypeForOrderJson);
        }

        if (!TextUtils.isEmpty(et_mobileBankingPayment.getText().toString())) {
            payment = Double.parseDouble(et_mobileBankingPayment.getText().toString());
            PaymentTypeForOrderJson paymentTypeForOrderJson = new PaymentTypeForOrderJson("1", "mobile-banking",
                    payment);

            paymentTypesList.add(paymentTypeForOrderJson);
        }

        return paymentTypesList;

    }

    private boolean isCorrectCalculation() {
        double totalBill = 0, billPayment = 0;

        if (!TextUtils.isEmpty(tv_totalAmount.getText().toString())) {
            totalBill = Double.parseDouble(tv_totalAmount.getText().toString());
        }

        if (!TextUtils.isEmpty(et_cashAmount.getText().toString())) {
            billPayment += Double.parseDouble(et_cashAmount.getText().toString());
        } else {
            billPayment += 0;
        }

        if (!TextUtils.isEmpty(et_cardPayment.getText().toString())) {
            billPayment += Double.parseDouble(et_cardPayment.getText().toString());
        } else {
            billPayment += 0;
        }

        if (!TextUtils.isEmpty(et_mobileBankingPayment.getText().toString())) {
            billPayment += Double.parseDouble(et_mobileBankingPayment.getText().toString());
        } else {
            billPayment += 0;
        }

        return (totalBill >= billPayment);
    }

    private boolean isPayAll() {

        double totalBill = 0, billPayment = 0;

        if (!TextUtils.isEmpty(tv_totalAmount.getText().toString())) {
            totalBill = Double.parseDouble(tv_totalAmount.getText().toString());
            dueSubmit.setTotal(totalBill);
        }

        if (!TextUtils.isEmpty(et_cashAmount.getText().toString())) {
            billPayment += Double.parseDouble(et_cashAmount.getText().toString());
            dueSubmit.setCash(Double.parseDouble(et_cashAmount.getText().toString()));
        } else {
            billPayment += 0;
            dueSubmit.setCash(0);
        }

        if (!TextUtils.isEmpty(et_cardPayment.getText().toString())) {
            billPayment += Double.parseDouble(et_cardPayment.getText().toString());
            dueSubmit.setCard(Double.parseDouble(et_cardPayment.getText().toString()));
        } else {
            billPayment += 0;
            dueSubmit.setCard(0);
        }

        if (!TextUtils.isEmpty(et_mobileBankingPayment.getText().toString())) {
            billPayment += Double.parseDouble(et_mobileBankingPayment.getText().toString());
            dueSubmit.setMobile_bank(Double.parseDouble(et_mobileBankingPayment.getText().toString()));
        } else {
            billPayment += 0;
            dueSubmit.setMobile_bank(0);
        }

        if (totalBill > billPayment) {
            dueSubmit.setDue_amount(totalBill - billPayment);
        }

        dueSubmit.setStoreId(sharedPreference.getStoreId());
        dueSubmit.setDate(due_payment);

        if (client != null) {
            dueSubmit.setClientId(String.valueOf(client.getId()));

        } else {
            dueSubmit.setClientId("0");
        }


        return totalBill > 0 && (totalBill == billPayment);
    }

    private void receiveClient() {
        receiver = new MyBroadcastReceiver();
        this.registerReceiver(receiver, new IntentFilter(MyBroadcastReceiver.ACTION));
    }

    private void showPopupAddClient(Context context) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        ContactListActivity.fromMakePayment = true;
                        Intent intent = new Intent(MakePaymentActivity.this, ContactListActivity.class);
                        startActivity(intent);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MakePaymentActivity.this,
                R.style.AlertDialog);
        builder.setMessage(getResources().getString(R.string.do_you_want_add_due))
                .setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no),
                        dialogClickListener).show();

    }

    private void showPopupForGotoPrint(Context context) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        Intent intent = new Intent(MakePaymentActivity.this, OrderPrintActivity.class);
                        startActivity(intent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.cancel();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MakePaymentActivity.this,
                R.style.AlertDialog);
        builder.setMessage(getResources().getString(R.string.do_you_want_print))
                .setPositiveButton(getResources().getString(R.string.yes), dialogClickListener)
                .setNegativeButton(getResources().getString(R.string.no),
                        dialogClickListener).show();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MakePaymentActivity.this, OrderActivity.class));
        //startActivity(new Intent(MakePaymentActivity.this, OrderItemActivity.class));
        finish();
    }
}