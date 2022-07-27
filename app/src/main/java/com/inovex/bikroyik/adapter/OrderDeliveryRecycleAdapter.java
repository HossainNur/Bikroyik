package com.inovex.bikroyik.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.OrderDetailsActivity;

import java.util.List;

/**
 * Created by DELL on 8/14/2018.
 */



public class OrderDeliveryRecycleAdapter extends RecyclerView.Adapter<OrderDeliveryRecycleAdapter.OrderDeliveryViewHolder> {

    private List<OrderDelivery> orderDeliveryList;
    Context mContext;

    public static final int resultCode=125;

    public OrderDeliveryRecycleAdapter(List<OrderDelivery> orderDeliveryList, Context context) {
        this.orderDeliveryList = orderDeliveryList;
        mContext=context;
    }


    @Override
    public OrderDeliveryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_delivery_list_row, parent, false);

        return new OrderDeliveryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderDeliveryViewHolder holder, final int position) {
        OrderDelivery orderDelivery = orderDeliveryList.get(position);

        holder.orderDeliveryId.setText("Order id: "+orderDelivery.getOrderId());
        holder.orderDeliveryDate.setText("Date: "+orderDelivery.getOrderDate());
        holder.orderDeliveryRetailerName.setText(orderDelivery.getRetailerName());
        holder.orderDeliveryRetailerContact.setText("Contact: "+orderDelivery.getRetailerContact());
        holder.orderDeliveryRetailerAddress.setText("Address: "+orderDelivery.getRetailerAddress());
        holder.orderDeliveryPrice.setText("Order: "+orderDelivery.getOrdertotalPrice()+" Tk");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("delivery onclick",""+ orderDeliveryList.get(position).getRetailerName());

                Intent intent=new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("order_id",orderDeliveryList.get(position).getOrderId());
                intent.putExtra("order_date",orderDeliveryList.get(position).getOrderDate());
                intent.putExtra("order_retailer",orderDeliveryList.get(position).getRetailerName());
                intent.putExtra("order_contact",orderDeliveryList.get(position).getRetailerContact());
                intent.putExtra("order_address",orderDeliveryList.get(position).getRetailerAddress());
                intent.putExtra("order_total",orderDeliveryList.get(position).getOrdertotalPrice());
                intent.putExtra("order_due",orderDeliveryList.get(position).getOrderTotalDue());


                //  mContext.startActivity(intent);
                ((Activity) mContext).startActivityForResult(intent, resultCode);

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderDeliveryList.size();
    }

    public class OrderDeliveryViewHolder extends RecyclerView.ViewHolder {
        public TextView orderDeliveryId, orderDeliveryDate, orderDeliveryRetailerName,orderDeliveryRetailerContact,orderDeliveryRetailerAddress,orderDeliveryPrice;
        LinearLayout llAttendanceRow;

        public OrderDeliveryViewHolder(View view) {
            super(view);

            orderDeliveryId = (TextView) view.findViewById(R.id.tvOrderDeliveryId);
            orderDeliveryDate = (TextView) view.findViewById(R.id.tvOrderDeliveryDate);
            orderDeliveryRetailerName = (TextView) view.findViewById(R.id.tvOrderDeliveryRetailer);
            orderDeliveryRetailerContact = (TextView) view.findViewById(R.id.tvOrderDeliveryContact);
            orderDeliveryRetailerAddress = (TextView) view.findViewById(R.id.tvOrderDeliveryRetailerAddress);
            orderDeliveryPrice = (TextView) view.findViewById(R.id.tvOrderDeliveryPrice);

        }
    }
}



