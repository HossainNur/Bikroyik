package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.SingleOrderItemActivity;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private List<OrderedProductModel> orderModelList = new ArrayList<>();
    private Context mContext;
    private CallbackOrderItem callback;
    private static OrderListAdapter orderListAdapter = null;
    private boolean shouldSetOnItemListener;

    public static OrderListAdapter getInstance(){
        if (orderListAdapter == null){
            orderListAdapter = new OrderListAdapter();
            return orderListAdapter;
        }else {
            return orderListAdapter;
        }
    }
    public void init(Context context, List<OrderedProductModel> orderModelList,
                     CallbackOrderItem callback, boolean shouldSetOnItemListener){
        this.mContext = context;
        this.orderModelList = orderModelList;
        this.callback = callback;
        this.shouldSetOnItemListener = shouldSetOnItemListener;
    }

    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ordered_item_child_view, parent, false);
        return new OrderListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        boolean offOnClick = true;
        OrderedProductModel orderModel = orderModelList.get(position);

        holder.horizontal_line.setVisibility(View.GONE);
        holder.tv_productPrice.setTypeface(null, Typeface.NORMAL);
        holder.tv_productName.setTypeface(null, Typeface.NORMAL);

        if (orderModel.getProductName() != null){
            //means this is order product
            holder.tv_productName.setText(orderModel.getProductName());

            if (orderModel.getProductId() != null && !"null".equals(orderModel.getProductId())){
                //means its not total price calculation
                offOnClick = false;
                holder.tv_productQuantity.setText("x"+orderModel.getQuantity());
            }else if ("Total".equalsIgnoreCase(orderModel.getProductName()) &&
                    (orderModel.getProductId() == null || "null".equals(orderModel.getProductId()))){
                //means its total price/discount/tax calculation
                holder.tv_productQuantity.setText("");
                holder.horizontal_line.setVisibility(View.VISIBLE);
                holder.tv_productPrice.setTypeface(null, Typeface.BOLD);
                holder.tv_productName.setTypeface(null, Typeface.BOLD);
            }else if ("Total Discount".equalsIgnoreCase(orderModel.getProductName()) &&
                    (orderModel.getProductId() == null || "null".equals(orderModel.getProductId()))){
                //means its total price/discount/tax calculation
                holder.tv_productQuantity.setText("");
                holder.horizontal_line.setVisibility(View.VISIBLE);
                holder.tv_productPrice.setTypeface(null, Typeface.BOLD);
                holder.tv_productName.setTypeface(null, Typeface.BOLD);
            }else if ("Total Tax".equalsIgnoreCase(orderModel.getProductName()) &&
                    (orderModel.getProductId() == null || "null".equals(orderModel.getProductId()))){
                //means its total price/discount/tax calculation
                holder.tv_productQuantity.setText("");
                holder.horizontal_line.setVisibility(View.VISIBLE);
                holder.tv_productPrice.setTypeface(null, Typeface.BOLD);
                holder.tv_productName.setTypeface(null, Typeface.BOLD);
            }else if ("Grand Total".equalsIgnoreCase(orderModel.getProductName()) &&
                    (orderModel.getProductId() == null || "null".equals(orderModel.getProductId()))){
                //means its total price/discount/tax calculation
                holder.tv_productQuantity.setText("");
                holder.horizontal_line.setVisibility(View.VISIBLE);
                holder.tv_productPrice.setTypeface(null, Typeface.BOLD);
                holder.tv_productName.setTypeface(null, Typeface.BOLD);
            }

            Double productPrice = orderModel.getTotalPrice();
            DecimalFormat df = new DecimalFormat("#.##");

            holder.tv_productPrice.setText(df.format(productPrice));

            if (orderModel.getOfferQuantity() <= 0){
                holder.tv_offerProductName.setVisibility(View.GONE);
                holder.tv_offerProductQuantity.setVisibility(View.GONE);
//                holder.tv_offerProductPrice.setVisibility(View.GONE);
            }else {
                //for temporary...........because offer table not ready
                holder.tv_offerProductName.setText(orderModel.getOfferName());
                holder.tv_offerProductQuantity.setText("x"+String.valueOf(orderModel.getOfferQuantity()));
//                holder.tv_offerProductPrice.setText("0");
            }
        }else {
            //discount, tax or total price
        }


        if (shouldSetOnItemListener && !offOnClick){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Intent intent = new Intent(mContext, SingleOrderItemActivity.class);
                    intent.putExtra("key_product_id", orderModel.getProductId());
                    mContext.startActivity(intent);*/
                    Context context = v.getContext();
                    Intent intent = new Intent(context,SingleOrderItemActivity.class);
                    intent.putExtra("key_product_id", orderModel.getProductId());
                    context.startActivity(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (orderModelList == null){
            return 0;
        }
        return orderModelList.size();
    }

    public void removeAt(int position) {
        orderModelList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderModelList.size());
    }



        protected class OrderListViewHolder extends RecyclerView.ViewHolder{
        TextView tv_productName, tv_productQuantity, tv_productPrice;
        TextView tv_offerProductName, tv_offerProductQuantity, tv_offerProductPrice;
        LinearLayout horizontal_line, horizontal_line_bellow;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_productName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_productQuantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            tv_productPrice = (TextView) itemView.findViewById(R.id.tv_product_totalPrice);

            tv_offerProductName = (TextView) itemView.findViewById(R.id.tv_offer_productName);
            tv_offerProductQuantity = (TextView) itemView.findViewById(R.id.tv_offer_productQuantity);
//            tv_offerProductPrice = (TextView) itemView.findViewById(R.id.tv_offerProductPrice);
            horizontal_line = (LinearLayout) itemView.findViewById(R.id.horizontalLine);
            horizontal_line_bellow = (LinearLayout) itemView.findViewById(R.id.horizontalLine2);
        }
    }
}
