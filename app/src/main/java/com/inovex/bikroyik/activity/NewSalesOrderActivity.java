
package com.inovex.bikroyik.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.NewOrderItem;
import com.inovex.bikroyik.adapter.NewOrderRecyclerAdapter;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_PRODUCT_ID;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_PRODUCT_NAME;
import static com.inovex.bikroyik.AppUtils.AppDatabaseHelper.COLUMN_PRODUCT_PRICE;


/**
 * Created by DELL on 8/8/2018.
 */

public class NewSalesOrderActivity extends AppCompatActivity {


    Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;
    Context mContext;
    LinearLayout llBack;
    MaterialSpinner spinner;
    Spinner retailerSpinner,market_spinner;
    LinearLayout llRetailerPointInfo;
    TextView tvClearList;
    TextView tvAddProduct;
    TextView tvTotalPrice;
    TextView tvGrandTotalPrice;
    TextView tvSubmitOrder;
    TextView tvSyncProduct;

    RecyclerView rvNewOrder;
    private List<NewOrderItem> newOrderList = new ArrayList<>();
    NewOrderRecyclerAdapter newOrderRecyclerAdapter;
    ArrayList<HashMap<String, String>> retailerList = new ArrayList<>();
    HashMap<String, String> retailerListForMarket = new HashMap<String, String>();
    ArrayList<String> retailersName = new ArrayList<>();
    ArrayList<String> retailersId = new ArrayList<>();
    AppDatabaseHelper appDatabaseHelper;
    ArrayList<String> retailerNameList = new ArrayList<>();
    ArrayList<String> marketNameList = new ArrayList<>();
    ArrayList<String> marketIDList = new ArrayList<>();



    ArrayList<HashMap<String, String>> productList = new ArrayList<>();
    ArrayList<String> productNameList = new ArrayList<>();



    double productPrice = 0;
    double productTotalPrice = 0;
    int productQuantity = 0;

    int selectedProductPositionInList = 0;
    int discount = 5;
    String retailerId;
    String retailerName;
    String retailerAddress;
    String distributorName;
    String contactPhone;
    int retailerSelectedPosition = 0;
    String market_name= null;


    String retailerLatitude = "";
    String retailerLongitude = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sales_order);


        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCancelable(false);
        if (appDatabaseHelper == null) {
            appDatabaseHelper = new AppDatabaseHelper(mContext);
        }
        retailersName.add("--- Select Retailer ---");
        retailersId.add("--- Select Retailer's ID ---");

        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("New Sales Order");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        llBack = mToolbar.findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvClearList = findViewById(R.id.tvClearList);
        tvAddProduct = findViewById(R.id.tvAddProduct);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvGrandTotalPrice = findViewById(R.id.tvGrandTotalPrice);
        tvSubmitOrder = findViewById(R.id.tvSubmitOrder);
        tvSyncProduct = findViewById(R.id.tvSyncProduct);
        retailerSpinner = findViewById(R.id.retailer_spinner);
        market_spinner = findViewById(R.id.market_spinner);


        rvNewOrder = findViewById(R.id.rvNewOrder);
        newOrderRecyclerAdapter = new NewOrderRecyclerAdapter(newOrderList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvNewOrder.setLayoutManager(mLayoutManager);
        rvNewOrder.setItemAnimator(new DefaultItemAnimator());
        rvNewOrder.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvNewOrder.setAdapter(newOrderRecyclerAdapter);
        // prepareNewOrderData();


        retailerList = appDatabaseHelper.getRetailerData();
        getMarketDetails();
        ArrayAdapter <String> market_name_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,marketNameList);
        market_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter <String> retailer_name_adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item,retailersName);
        retailer_name_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        retailerSpinner.setAdapter(retailer_name_adapter);



        market_spinner.setAdapter(market_name_adapter);

        market_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                market_name = marketNameList.get(i);
                retailerListForMarket = appDatabaseHelper.getRetailerNameByMarket(market_name);
                Log.d("workforce", "onItemSelected: "+retailerListForMarket);
                retailersId.clear();
                retailersName.clear();
                getRetailerDetails();
                retailer_name_adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });









        productList = appDatabaseHelper.getProductData();
        productNameList = getProductNameList(productList);

        //callProductApi();

        tvClearList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newOrderList.clear();
                newOrderRecyclerAdapter.notifyDataSetChanged();

            }
        });
        tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show a dialog to select product
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.add_product_dialog);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                // window.setBackgroundDrawableResource(R.color.colorTransparent);

                final TextView tvDialogProductId = dialog.findViewById(R.id.tvDialogProductId);
                final TextView tvDialogProductName = dialog.findViewById(R.id.tvDialogProductName);
                final TextView tvDialogProductStock = dialog.findViewById(R.id.tvDialogProductStock);
                final TextView tvDialogProductPrice = dialog.findViewById(R.id.tvDialogProductPrice);
                final TextView tvDialogProductDiscount = dialog.findViewById(R.id.tvDialogProductDiscount);
                TextView tvCancelDialog = dialog.findViewById(R.id.tvCancelDialog);

                final EditText etDialogProductQuantity = dialog.findViewById(R.id.etDialogProductQuantity);
                final EditText etDialogTotalPrice = dialog.findViewById(R.id.etDialogTotalPrice);
                etDialogProductQuantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        if (s.length() != 0) {
                            String quantity = etDialogProductQuantity.getText().toString();

                            productQuantity = Integer.parseInt(quantity);
                            productTotalPrice = productPrice * productQuantity;
                            etDialogTotalPrice.setText(productTotalPrice + "");

                        } else {
                            productTotalPrice = 0;
                            etDialogTotalPrice.setText(productTotalPrice + "");
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                tvCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                TextView tvDoneAddProductDialog = dialog.findViewById(R.id.tvDoneAddProductDialog);
                tvDoneAddProductDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Toast.makeText(mContext, "" + selectedProductPositionInList, Toast.LENGTH_SHORT).show();

                        if (selectedProductPositionInList > 0) {
                            if (etDialogProductQuantity.getText().toString().length() > 0) {

                                String productId = productList.get(selectedProductPositionInList - 1).get(COLUMN_PRODUCT_ID);
                                String productName = productList.get(selectedProductPositionInList - 1).get(COLUMN_PRODUCT_NAME);


                                NewOrderItem newOrderItem = new NewOrderItem();
                                newOrderItem.setNewOrderItemId(productId);
                                newOrderItem.setName(productName);
                                Log.v("_sf", "product total price: " + productTotalPrice);
                                newOrderItem.setTotalPrice(productTotalPrice);
                                newOrderItem.setQuantity(productQuantity);
                                newOrderItem.setProductPositionInList(newOrderList.size() + 1);
                                newOrderList.add(newOrderItem);
                                newOrderRecyclerAdapter = new NewOrderRecyclerAdapter(newOrderList);
                                rvNewOrder.setAdapter(newOrderRecyclerAdapter);
                                rvNewOrder.invalidate();
                                newOrderRecyclerAdapter.notifyDataSetChanged();
                                dialog.dismiss();

                                // change total and grand total textView
                                updateTotalPriceCalculation();


                            } else {
                                Toast.makeText(mContext, "Product quantity is empty !!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });


                MaterialSpinner productSpinner = dialog.findViewById(R.id.productSpinner);
                productSpinner.setDropdownMaxHeight(450);
                productSpinner.setItems(productNameList);
                productSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {


                        if (position > 0) {


                            int pos = position - 1;
                            selectedProductPositionInList = position;
                            tvDialogProductId.setText(getString(R.string.dialog_product_id) + productList.get(pos).get(COLUMN_PRODUCT_ID));
                            tvDialogProductName.setText(getString(R.string.dialog_product_name) + productList.get(pos).get(COLUMN_PRODUCT_NAME));
                            tvDialogProductPrice.setText(getString(R.string.dialog_product_price) + productList.get(pos).get(COLUMN_PRODUCT_PRICE));
                            tvDialogProductDiscount.setText(getString(R.string.dialog_product_discount) + productList.get(pos).get(COLUMN_PRODUCT_DISCOUNT));
                            productPrice = Double.parseDouble(productList.get(pos).get(COLUMN_PRODUCT_PRICE));

                        } else {
                            tvDialogProductId.setText(getString(R.string.dialog_product_id));
                            tvDialogProductName.setText(getString(R.string.dialog_product_name));
                            tvDialogProductStock.setText(getString(R.string.dialog_product_stock));
                            tvDialogProductPrice.setText(getString(R.string.dialog_product_price));
                            tvDialogProductDiscount.setText(getString(R.string.dialog_product_discount));
                            productPrice = 0;
                            selectedProductPositionInList = position;


                        }

                    }
                });


                window.setGravity(Gravity.CENTER);
                dialog.setCancelable(true);

                LinearLayout ivCancelDialog = dialog.findViewById(R.id.ivCancelDialog);
                ivCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });




        llRetailerPointInfo = findViewById(R.id.llRetailerPointInfo);
        llRetailerPointInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.contact_point_dialog);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                // window.setBackgroundDrawableResource(R.color.colorTransparent);
                window.setGravity(Gravity.CENTER);
                dialog.setCancelable(true);

                LinearLayout ivCancelDialog = dialog.findViewById(R.id.ivCancelDialog);
                TextView tvName = dialog.findViewById(R.id.tvDialogRetailerName);
                TextView tvMobile = dialog.findViewById(R.id.tvDialogRetailerMobile);
                TextView tvAddress = dialog.findViewById(R.id.tvDialogRetailerAddress);
                TextView tvCallContractPoint = dialog.findViewById(R.id.tvCallContractPoint);
                TextView tvLocationContactPoint = dialog.findViewById(R.id.tvLocationContactPoint);


                // get retailer data with selected position

                tvName.setText(retailerName);
                final String mobile = "Mobile: " + contactPhone;
                tvMobile.setText(mobile);
                String address = "Address :" + retailerAddress;
                tvAddress.setText(address);


                tvCallContractPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + contactPhone));
                        startActivity(intent);


                    }
                });
                tvLocationContactPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(retailerLatitude), Double.parseDouble(retailerLongitude));
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                });


                ivCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                if (retailerName != null && contactPhone != null && retailerAddress != null) {
                    dialog.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Select a retailer ", Toast.LENGTH_SHORT).show();
                }


            }
        });


        tvSyncProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AppUtil.isNetworkAvailable(getApplicationContext())) {
                    progressDialog.show();

                    callProductApi();

                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvSubmitOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (retailerId != null && retailerName != null) {


                    if (newOrderList.size() > 0) {
                        final String srId = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_ID);
                        final double total = getTotalOrderPrice();
                        final String discount = AppUtil.ORDER_DISCOUNT;
                        final double discountAmount = Double.parseDouble(discount);
                        final double grandTotal = total - (total * (discountAmount / 100));
                        final String orderDate = getTodayDateString();
                        final String orderDetails = getOrderDetailsString();
                        final String orderTime = getCurrentTime();
                        final String orderId = makeOrderId(srId);
                        final String orderStatusOnline = "Submitted";
                        final String orderStatusOffline = "Pending";


                        if (AppUtil.isNetworkAvailable(getApplicationContext())) {
                            // internet connection
                            progressDialog.show();
                            callApiForNewOrderSubmit(orderId, orderDate, orderTime, orderStatusOnline, total + "", discount, grandTotal + "", srId, retailerId, retailerName, retailerAddress, contactPhone, distributorName, orderDetails);

                        } else {

                            Toast.makeText(getApplicationContext(), "No internet connection, sending failed", Toast.LENGTH_SHORT).show();
                            // no internet connection, save in local data with status : Not submitted
                            progressDialog.show();

                            saveOrderInLocalDatabase(orderId, orderDate, orderTime, orderStatusOffline, total + "", discount, grandTotal + "", srId, retailerId, retailerName, retailerAddress, contactPhone, distributorName);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Add at least one product ", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(getApplicationContext(), "Select a retailer ", Toast.LENGTH_SHORT).show();
                }


            }


        });

    }

    private void getRetailerDetails() {

        retailersId.add("--- Select Retailer ID ---");
        retailersName.add("--- Select Retailer Name ---");


        Iterator iterator = retailerListForMarket.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry me2 = (Map.Entry) iterator.next();
            retailersName.add(me2.getKey().toString());
            retailersId.add(me2.getValue().toString());
        }


    }

    private void getMarketDetails() {

        marketIDList.add("-- Select Market ID --");
        marketNameList.add("Select Market Name ---");
        for(int i = 0; i < retailerList.size(); i++){
            String id = retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_ID);
            if(!marketIDList.contains(id)) {
                marketIDList.add(id);
                marketNameList.add(retailerList.get(i).get(AppDatabaseHelper.COLUMN_RETAILER_MARKET_NAME));
            }

        }

    }

    private void saveOrderInLocalDatabase(String orderId, String orderDate, String orderTime, String orderStatus, String total, String discount,
                                          String grandTotal, String srId, String retailerId, String retailerName,
                                          String retailerAddress, String contactPhone, String distributorName) {


        appDatabaseHelper.insertOrder(orderId, orderDate, orderTime, orderStatus, total, discount, grandTotal, orderDate, srId, retailerId, retailerName, retailerAddress, contactPhone, distributorName);
        for (int x = 0; x < newOrderList.size(); x++) {
            NewOrderItem newOrderItem = newOrderList.get(x);
            Log.v("_sf", "  newSales Actvt saveOrderInLocalDatabase product price: " + newOrderItem.getTotalPrice());
            appDatabaseHelper.insertOrderProduct(orderId, newOrderItem.getNewOrderItemId(), newOrderItem.getName(), newOrderItem.getQuantity() + "", newOrderItem.getTotalPrice() + "");

        }
        callTimerThread("order saved to  database");
        progressDialog.dismiss();
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }


    private void callProductApi() {


        final String distributorId = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_REPORTING_ID);


        String URL = APIConstants.PRODUCT_API;
        RequestQueue callProductApiQueue = Volley.newRequestQueue(mContext);
        // prepare the Request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("workforce", " response:" + response.toString());
                try {
                    JSONArray jsonArr = new JSONArray(response.toString());
                    if (jsonArr.length() > 0) {
                        appDatabaseHelper.deleteAllProductData();
                        for (int i = 0; i < jsonArr.length(); i++) {
                            JSONArray childArray = jsonArr.getJSONArray(i);
                            System.out.println(childArray);

                            String productID = childArray.get(0).toString();
                            String productTitle = childArray.get(1).toString();
                            String productStock = childArray.get(2).toString();
                            String productPrice = childArray.get(3).toString();
                            String productDiscount = childArray.get(4).toString();

                            //appDatabaseHelper.insertProductData(productID, productTitle, productStock, productPrice, productDiscount);


                        }
                        progressDialog.dismiss();
                        callTimerThread("product list updated.");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();


                if (error instanceof NetworkError) {
                    Log.d("product response err", "product  response: Network error");
                } else if (error instanceof ServerError) {
                    Log.d("product response", "product response: Server error");
                } else if (error instanceof AuthFailureError) {
                    Log.d("product response", "product response: Auth. error");
                } else if (error instanceof ParseError) {
                    Log.d("product response", "product response: Parse error");
                } else if (error instanceof TimeoutError) {
                    Log.d("product response", "product response: timeout error");
                }

                Log.d("product response", "product responseError:" + error.toString());
                error.printStackTrace();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put(APIConstants.API_KEY_DISTRIBUTOR_ID, distributorId);

                return params;
            }
        };

        callProductApiQueue.add(stringRequest);
    }

    private void callApiForNewOrderSubmit(final String orderId, final String orderDate, final String orderTime, final String orderStatus, final String total,
                                          final String discount, final String grandTotal, final String srId, final String retailerId, final String retailerName,
                                          final String retailerAddress, final String contactPhone, final String distributorName, final String orderDetails
    ) {
        // commenting for demo data


        Log.d("order retailer name", " retailer id:" + retailerName + " order size: " + newOrderList.size());
        if (retailerId.length() > 0 && newOrderList.size() > 0) {
            progressDialog.show();
            String URL = APIConstants.ORDER_API;
            RequestQueue retailerQueue = Volley.newRequestQueue(mContext);
            // prepare the Request

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    Log.d("order response", " response:" + response.toString());
                    if (response.toString().contains("success")) {
                        // save in local data with status: submitted
                        saveOrderInLocalDatabase(orderId, orderDate, orderTime, orderStatus, total + "", discount, grandTotal, srId, retailerId, retailerName, retailerAddress, contactPhone, distributorName);
                        Toast.makeText(mContext, "Order submitted succesfully", Toast.LENGTH_SHORT).show();
//                        Intent i = new Intent();
//                        setResult(RESULT_OK, i);
//                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();


                    if (error instanceof NetworkError) {
                        Log.d("order response err", "order  response: Network error");
                    } else if (error instanceof ServerError) {
                        Log.d("order response", "order response: Server error");
                    } else if (error instanceof AuthFailureError) {
                        Log.d("order response", "order response: Auth. error");
                    } else if (error instanceof ParseError) {
                        Log.d("order response", "order response: Parse error");
                    } else if (error instanceof TimeoutError) {
                        Log.d("order response", "order response: timeout error");
                    }

                    Log.d("order response", "order responseError:" + error.toString());
                    error.printStackTrace();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(APIConstants.API_KEY_ORDER_ID, orderId);
                    params.put(APIConstants.API_KEY_ORDER_RETAILER_ID, retailerId);
                    params.put(APIConstants.API_KEY_ORDER_RETAILER_NAME, retailerName);
                    params.put(APIConstants.API_KEY_ORDER_RETAILER_ADDRESS, retailerAddress);
                    params.put(APIConstants.API_KEY_ORDER_RETAILER_DISTRIBUTOR_NAME, distributorName);
                    params.put(APIConstants.API_KEY_ORDER_RETAILER_PHONE, contactPhone);

                    params.put(APIConstants.API_KEY_ORDER_TOTAL, total + "");
                    params.put(APIConstants.API_KEY_ORDER_DISCOUNT, discount);
                    params.put(APIConstants.API_KEY_ORDER_GRAND_TOTAL, grandTotal + "");
                    params.put(APIConstants.API_KEY_ORDER_DELIVERY_DATE, orderDate);
                    params.put(APIConstants.API_KEY_ORDER_DETAILS, orderDetails);

                    return params;
                }
            };

            retailerQueue.add(stringRequest);


        }


        // end commenting for demo data
    }

    private String getCurrentTime() {

        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        return df.format(dateobj);
    }

    private String makeOrderId(String retailerId) {

        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        Random rand = new Random();
        int random = rand.nextInt(1000);
        return retailerId + year + month + day + random;
    }


    private void callTimerThread(final String message) {


        final int interval = 2000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                progressDialog.dismiss();
                productList = appDatabaseHelper.getProductData();
                productNameList = getProductNameList(productList);

                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

    }

    private String getOrderDetailsString() {


        String orderDetails = "";

        for (int x = 0; x < newOrderList.size(); x++) {
            NewOrderItem newOrderItem = newOrderList.get(x);
            String singleOrder = newOrderItem.getNewOrderItemId() + ",";
            singleOrder += newOrderItem.getQuantity() + ",";
            singleOrder += newOrderItem.getTotalPrice() + ",";
            singleOrder += newOrderItem.getName();
            singleOrder += "###";
            orderDetails += singleOrder;

        }
        return orderDetails;
    }


    public String getTodayDateString() {
        String todayDateString = "";
        Calendar calendar = Calendar.getInstance();
        String year = calendar.get(Calendar.YEAR) + "";
        String month = (calendar.get(Calendar.MONTH) + 1) + "";
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        todayDateString = year + "-" + month + "-" + day;
        return todayDateString;

    }

    private double getTotalOrderPrice() {

        double totalPrice = 0;
        for (int x = 0; x < newOrderList.size(); x++) {
            totalPrice = totalPrice + newOrderList.get(x).getTotalPrice();
        }
        return totalPrice;
    }

    private void updateTotalPriceCalculation() {


        final String discount = AppUtil.ORDER_DISCOUNT;
        final double discountAmount = Double.parseDouble(discount);

        double totalPrice = 0;
        for (int x = 0; x < newOrderList.size(); x++) {
            totalPrice = totalPrice + newOrderList.get(x).getTotalPrice();
        }

        double grandPrice = totalPrice - (totalPrice * (discountAmount / 100));
        tvTotalPrice.setText("Total: " + totalPrice);
        tvGrandTotalPrice.setText(grandPrice + " TK");
    }

    private ArrayList<String> getRetailerNameList(ArrayList<HashMap<String, String>> retailerList) {

        ArrayList<String> retailNameList = new ArrayList<>();
        retailNameList.add("Select Retailer");
        Log.v("retailer size:", retailerList.size() + " **********");
        for (int x = 0; x < retailerList.size(); x++) {
            retailNameList.add(retailerList.get(x).get(AppDatabaseHelper.COLUMN_RETAILER_NAME));
        }
        return retailNameList;
    }

    private ArrayList<String> getProductNameList(ArrayList<HashMap<String, String>> productList) {


        ArrayList<String> productNameList = new ArrayList<>();
        productNameList.add("Select product");
        Log.v("retailer size:", retailerList.size() + " ********");

        for (int x = 0; x < productList.size(); x++) {
            productNameList.add(productList.get(x).get(COLUMN_PRODUCT_NAME));
        }
        return productNameList;


    }

    private void prepareNewOrderData() {
        newOrderList.clear();
        NewOrderItem newOrderItem = new NewOrderItem("1", "Napa extra 500mg", 50.00, 10, 0, 50.00, 500.00);
        newOrderItem.setProductPositionInList(1);
        newOrderList.add(newOrderItem);
        NewOrderItem newOrderItem1 = new NewOrderItem("2", "Alprazolam 20mg", 250.00, 25, 0, 50.00, 2500.00);
        newOrderList.add(newOrderItem1);
        newOrderItem.setProductPositionInList(2);
        NewOrderItem newOrderItem2 = new NewOrderItem("3", "Darzalex tablet 500mg", 95.00, 80, 0, 50.00, 18500.00);
        newOrderList.add(newOrderItem2);
        newOrderItem.setProductPositionInList(0);
        NewOrderItem newOrderItem3 = new NewOrderItem("4", "Acetaminophen  100mg", 75.00, 50, 0, 50.00, 9850.00);
        newOrderList.add(newOrderItem3);
        newOrderItem.setProductPositionInList(3);
        NewOrderItem newOrderItem4 = new NewOrderItem("5", "Napa extra 500mg", 50.00, 80, 0, 50.00, 5500.00);
        newOrderList.add(newOrderItem4);
        newOrderItem.setProductPositionInList(4);

        newOrderRecyclerAdapter.notifyDataSetChanged();
    }
}
