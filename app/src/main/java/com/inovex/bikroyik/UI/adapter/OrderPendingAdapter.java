package com.inovex.bikroyik.UI.adapter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.BillActivity;
import com.inovex.bikroyik.UI.activity.Expense;
import com.inovex.bikroyik.UI.activity.MakePaymentActivity;
import com.inovex.bikroyik.data.OrderDetails;
import com.inovex.bikroyik.data.OrderModel;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.BillModel;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.data.model.DueSubmit;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.PaymentTypeForOrderJson;
import com.inovex.bikroyik.data.model.PaymentTypeModel;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class OrderPendingAdapter extends RecyclerView.Adapter<OrderPendingAdapter.MyViewHolder> {

    private Context context;
    private ArrayList order_id, total_price, total_discount, grand_total,product_id;
    DatabaseSQLite databaseSQLite;
    SharedPreference sharedPreference;

    public OrderPendingAdapter(Context context, ArrayList order_id, ArrayList total_price, ArrayList total_discount, ArrayList grand_total) {
        this.context = context;
        this.order_id = order_id;
        this.total_price = total_price;
        this.total_discount = total_discount;
        this.grand_total = grand_total;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.order_id_txt.setText("Order Id: " + String.valueOf(order_id.get(position)));
        holder.total_price_txt.setText("Grand Total: " + String.valueOf(total_price.get(position)));
        holder.discount_txt.setText("Discount: " + String.valueOf(total_discount.get(position)));
//        holder.grand_txt.setText("Grand Total: "+String.valueOf(grand_total.get(position)));
        Log.d("order_id",""+order_id.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isNetworkAvailable())
                {
                    OrderJsonModel orderJsonModel = new OrderJsonModel();
                    DueSubmit dueSubmit = null;

                    orderJsonModel.setOrderDate(Constants.getTodayDateString());
                    orderJsonModel.setOrderId(String.valueOf(order_id.get(position)));
                    orderJsonModel.setSalesBy(sharedPreference.getUserId());
                    orderJsonModel.setStoreId(sharedPreference.getStoreId());
                    orderJsonModel.setSubscriberId(sharedPreference.getSubscriberId());


                    Cursor cursor = databaseSQLite.showOrderData(String.valueOf(order_id.get(position)));

                    if(cursor.getCount() == 0)
                    {
                        Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
                    }
                    else {
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


                    Cursor cursor2 = databaseSQLite.showPaymentData(String.valueOf(order_id.get(position)));

                    if(cursor2.getCount() == 0)
                    {
                        Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
                    }else{
                        while (cursor2.moveToNext())
                        {
                            Log.d("id_value",""+cursor2.getString(3));

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
                    Cursor cursor5 = databaseSQLite.showProductDetailsData(String.valueOf(order_id.get(position)));

                    if(cursor5.getCount() == 0)
                    {
                        Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        while (cursor5.moveToNext())
                        {
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
                            List<DiscountDetails> list1 = getDiscountDetails(String.valueOf(order_id.get(position)), cursor5.getString(1));
                            List<Tax> list2 = getTaxDetails(String.valueOf(order_id.get(position)), cursor5.getString(1));

                            orderDetails.setDiscountDetails(list1);
                            orderDetails.setTax(list2);

                            orderDetailsList.add(orderDetails);

                        }
                    }

                    orderJsonModel.setOrderDetails(orderDetailsList);

                    sendClientInfoToServer(context.getApplicationContext(), ApiConstants.ORDER_SUBMIT, orderJsonModel);

                    Cursor cursor9 = databaseSQLite.showPaymentData(String.valueOf(order_id.get(position)));

                        if(cursor9.getCount() == 0)
                        {
                            Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            while (cursor9.moveToNext()) {
                                Double dueValue = Double.valueOf(cursor9.getString(7));

                                if(dueValue > 0) {

                                    dueSubmit = new DueSubmit();
                                    dueSubmit.setCard(Double.parseDouble(cursor9.getString(2)));
                                    dueSubmit.setCash(Double.parseDouble(cursor9.getString(1)));
                                    dueSubmit.setClientId(String.valueOf(order_id.get(position)));
                                    dueSubmit.setDate(Constants.getTodayDateString());
                                    dueSubmit.setDue_amount(Double.parseDouble(cursor9.getString(7)));
                                    dueSubmit.setMobile_bank(Double.parseDouble(cursor9.getString(5)));
                                    dueSubmit.setStoreId(sharedPreference.getStoreId());
                                    dueSubmit.setTotal(Double.parseDouble(cursor9.getString(6)));
                                    dueSubmit.setSubscriberId(sharedPreference.getSubscriberId());
                                    dueSubmit.setUserId(sharedPreference.getUserId());
                                    sendClientInfoToServerDue(context.getApplicationContext(), ApiConstants.PAYMENT_SALE, dueSubmit);

                                }

                            }

                        }

                    int value = databaseSQLite.deleteOrderData(orderJsonModel.getOrderId());
                    if (value > 0) {
                        Toast.makeText(context, "OrderData is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "OrderData is not deleted", Toast.LENGTH_SHORT).show();
                    }

                    int paymentValue = databaseSQLite.deletePaymentData(orderJsonModel.getOrderId());
                    if (paymentValue > 0) {
                        Toast.makeText(context, "PaymentData is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "PaymentData is not deleted", Toast.LENGTH_SHORT).show();
                    }

                    int productOrderValue = databaseSQLite.deleteOrderProductData(orderJsonModel.getOrderId());
                    if (productOrderValue > 0) {
                        Toast.makeText(context, "OrderProduct Data is deleted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "OrderProduct is not deleted", Toast.LENGTH_SHORT).show();
                    }

                    }else{

                    Toast.makeText(context,"Please check network connection",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return order_id.size();
    }
    public  List<DiscountDetails> getDiscountDetails(String orderId,String productId)
    {
        List<DiscountDetails> discountDetailsList=new ArrayList<>();
        Cursor cursor3 = databaseSQLite.showDiscountDetailsData(orderId,productId);
        if(cursor3.getCount() == 0)
        {
            Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
        }
        else {
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


    public  List<Tax> getTaxDetails(String orderId,String productId)
    {
        List<Tax> taxList =new ArrayList<>();
        Cursor cursor4 = databaseSQLite.showTaxDetailsData(orderId,productId);
        if(cursor4.getCount() == 0)
        {
            Toast.makeText(context,"No data found",Toast.LENGTH_SHORT).show();
        }
        else {
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
        Log.d("_order_submit_", "json: " + reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJsonDue(dueSubmit), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_tag_", "response: " + result);


                /*Intent intent = new Intent(context, Expense.class);
                context.startActivity(intent);*/
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

    private String prepareJson(OrderJsonModel orderJsonModel) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(orderJsonModel);
    }

    private String prepareJsonDue(DueSubmit dueSubmit) {
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(dueSubmit);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView order_id_txt, total_price_txt, discount_txt, grand_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            order_id_txt = itemView.findViewById(R.id.orderIdTxt);
            total_price_txt = itemView.findViewById(R.id.totalPriceTxt);
            discount_txt = itemView.findViewById(R.id.discountTxt);
            databaseSQLite = new DatabaseSQLite(context.getApplicationContext());
            sharedPreference = SharedPreference.getInstance(context.getApplicationContext());


            //grand_txt = itemView.findViewById(R.id.grandTotalTxt);
        }
    }
}
