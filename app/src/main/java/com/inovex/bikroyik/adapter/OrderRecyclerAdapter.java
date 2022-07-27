package com.inovex.bikroyik.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.LoadingActivity;
import com.inovex.bikroyik.activity.OrderList;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder>{

    private ArrayList<ProductOrder> productOrders;
    Context mContext, context;

    boolean isEnabled = false;
    boolean isSelected = false;
    OrderList orderList = new OrderList();

    public OrderRecyclerAdapter(ArrayList<ProductOrder> productOrders, Context mContext) {
        this.productOrders = productOrders;
        this.mContext = mContext;
    }


    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recylclerview,parent,false);
        context = parent.getContext();
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderViewHolder holder, final int position) {

        final ProductOrder productOrder = productOrders.get(position);

        final OrderViewHolder orderViewHolder = holder;

        holder.id.setText(productOrder.getProduct_id());
        holder.name.setText(productOrder.getProduct_name());
        holder.quantity.setText(productOrder.product_quantity);
        holder.price.setText(productOrder.total_price);

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_alert_dialog);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                final TextView id, name, stock, discount, offer,price, total_price, done;
                final EditText quantity;
                final int intPrice; final int intQuantity; final int intDiscount = 0; final int intTotal;

                id = dialog.findViewById(R.id.dialog_id);
                name = dialog.findViewById(R.id.dialog_name);
                stock = dialog.findViewById(R.id.dialog_stock);
                discount = dialog.findViewById(R.id.dialog_discount);
                offer = dialog.findViewById(R.id.dialog_offer);
                quantity = dialog.findViewById(R.id.dialog_quantity);
                total_price = dialog.findViewById(R.id.dialog_total_price);
                price = dialog.findViewById(R.id.dialog_price);
                done = dialog.findViewById(R.id.dialog_done);
                final AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

                HashMap<String, String>  specificProduct = new HashMap<String, String>();

                specificProduct = appDatabaseHelper.getSpecificProductData(productOrder.getProduct_id()) ;

                final String product_id = productOrder.getProduct_id();
                final String product_name = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_NAME);
                final String product_stock = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_STARTING_STOCK);
                final String product_discount = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT);
                final String discount_type = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT_TYPE);
                final String available_discount = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT_AVAILABLE);
                final String product_offer = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_OFFER);
                final String available_offer = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_AVAILABLE_OFFER);
                final String product_price = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE);

                id.setText("Product ID: "+product_id);
                name.setText("Product Name: "+product_name);
                stock.setText("Stock: "+product_stock);
                price.setText("Price: "+product_price);


                if(product_offer.equalsIgnoreCase("true")){
                    offer.setText(available_offer);
                } else {
                    offer.setText("N/A");

                }

                if(product_discount.equalsIgnoreCase("true")){
                    if(discount_type.equalsIgnoreCase("Percentage")) {
                        discount.setText("Discount: "+available_discount+"%");

                    } else {
                        discount.setText("Discount: "+available_discount+" TK");
                    }
                } else {
                    discount.setText("Discount: "+"N/A");

                }


                Log.d("Okay", "Deletion: "+specificProduct);


                TextView delete;
                delete = dialog.findViewById(R.id.dialog_delete);
                ImageView cross = dialog.findViewById(R.id.dismiss_dialog);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        productOrders.remove(holder.getAdapterPosition());

                        appDatabaseHelper.deleteSingleOrderPerProducts(productOrder.getProduct_id());
                        String total_price = appDatabaseHelper.getNewTotal();
                        TextView total = OrderList.total_price_tv;
                        ArrayList<HashMap<String, String>> orderListDetail = new ArrayList<HashMap<String, String>>();
                        orderListDetail = appDatabaseHelper.getAllPerOrder();
                        OrderList.orderListDetails = orderListDetail;
                        total.setText(total_price);
                        OrderRecyclerAdapter.this.notifyDataSetChanged();
                        /*final Intent intent = new Intent(mContext, LoadingActivity.class);
                        intent.putExtra("activity", "orderList");
                        mContext.startActivity(intent);*/
                        dialog.dismiss();

                    }
                });

                cross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(total_price.getText().toString().length() == 0) {
                            total_price.setError("Please fill it.");
                        } else if(quantity.getText().toString().length() == 0 ) {
                            quantity.setError("Please, fill it.");
                        }
                        else {
                            HashMap<String, String> product_order = new HashMap<String, String>();
                            product_order = appDatabaseHelper.getPerOrderById(product_id);

                            double total = Double.parseDouble(product_order.get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE));
                            int  quantity1 = Integer.parseInt(product_order.get(AppDatabaseHelper.COLUMN_ORDER_PRODUCT_QUANTITY));
                            double discount = Double.parseDouble(product_order.get(AppDatabaseHelper.COLUMN_PRODUCT_DISCOUNT));

                            double new_total = Double.parseDouble(total_price.getText().toString());
                            int new_quantity = Integer.parseInt(quantity.getText().toString());
                            double new_discount =  discount;

                            appDatabaseHelper.updatePerOrder(String.valueOf(new_quantity),String.valueOf(new_total), product_id, String.valueOf(new_discount));
                            String total_price = appDatabaseHelper.getNewTotal();
                            TextView totals = OrderList.total_price_tv;
                            ArrayList<HashMap<String, String>> orderListDetail = new ArrayList<HashMap<String, String>>();
                            orderListDetail = appDatabaseHelper.getAllPerOrder();
                            OrderList.orderListDetails = orderListDetail;
                            totals.setText(total_price);
                            OrderRecyclerAdapter.this.notifyDataSetChanged();

                            orderViewHolder.quantity.setText(String.valueOf(new_quantity));
                            final Intent intent = new Intent(mContext, LoadingActivity.class);
                            intent.putExtra("activity", "orderList");
                            mContext.startActivity(intent);
                            dialog.dismiss();


                        }



                    }


                });

                dialog.show();

                quantity.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        double intTotal = 0, total_discount = 0;

                        if(quantity.getText().toString().length() > 0){
                            double intQuantity = Double.parseDouble(quantity.getText().toString());
                            double intPrice = Double.parseDouble(product_price);
                            if(product_discount.equals("true")) {
                                double intDiscount = Double.parseDouble(available_discount);
                                if(discount_type.equals("Percentage")) {

                                    intTotal = (intQuantity * intPrice) * (1 - (intDiscount / 100));
                                    total_discount = (intQuantity * intPrice) * (intDiscount / 100);


                                } else {

                                    intTotal = intQuantity * (intPrice - intDiscount);
                                    total_discount = intQuantity * intDiscount;

                                }

                            }
                            else {
                                intTotal = intQuantity * intPrice ;
                            }
                            String product_total_price = String.valueOf(intTotal);

                            total_price.setText(product_total_price);
                        } else {
                            quantity.setError("This field should be filled.");
                            total_price.setText("");
                        }


                    }

                    @Override
                    public void afterTextChanged(Editable s) {


                    }
                });

            }
        });

    }






    @Override
    public int getItemCount() {
        return productOrders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder{

        public TextView id, name,quantity,price, edit;
        public CheckBox select;

        public OrderViewHolder(View view) {
            super(view);
            id = view.findViewById(R.id.dialog_id);
            name = view.findViewById(R.id.dialog_name);
            quantity = view.findViewById(R.id.dialog_quantity);
            price = view.findViewById(R.id.dialog_price);
            edit = view.findViewById(R.id.editOrder);
        }
    }

}
