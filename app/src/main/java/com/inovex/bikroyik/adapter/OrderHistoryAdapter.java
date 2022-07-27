package com.inovex.bikroyik.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.SalesOrderDetailActivity;

import java.util.List;

/**
 * Created by DELL on 8/7/2018.
 */


public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private static final int REQUEST_FOR_UPDATE_ORDER = 457;
    private List<OrderHistory> orderHistoryList;
    Context nContext;

    public OrderHistoryAdapter(Context mcontext, List<OrderHistory> orderHistoryList) {
        this.orderHistoryList = orderHistoryList;
        nContext=mcontext;
    }


    @Override
    public OrderHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_history_list_row, parent, false);

        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderHistoryViewHolder holder, int position) {
        final OrderHistory orderHistory = orderHistoryList.get(position);

        holder.tvOrderHistoryId.setText("Order # " + orderHistory.getOrderId());
        holder.orderHistoryDate.setText("Date: " + orderHistory.getOrderDate());
        holder.orderHistoryStatus.setText(orderHistory.getOrderStatus());
        holder.orderHistoryRetailer.setText(orderHistory.getRetailerName());
        holder.orderHistoryAmount.setText("Amount: " + orderHistory.getOrderAmount() + " Tk");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Toast.makeText(nContext,orderHistory.getOrderId(),Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(nContext, SalesOrderDetailActivity.class);
                intent.putExtra("order_id",orderHistory.getOrderId());
                intent.putExtra("retailer_id",orderHistory.getRetailerId());
                ((Activity) nContext).startActivityForResult(intent,REQUEST_FOR_UPDATE_ORDER);
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView orderHistoryDate, orderHistoryStatus, orderHistoryRetailer, orderHistoryAmount, tvOrderHistoryId;
        LinearLayout llAttendanceRow;

        public OrderHistoryViewHolder(View view) {
            super(view);

            tvOrderHistoryId = (TextView) view.findViewById(R.id.tvOrderHistoryId);
            orderHistoryDate = (TextView) view.findViewById(R.id.tvOrderHistoryDate);
            orderHistoryStatus = (TextView) view.findViewById(R.id.tvOrderHistoryStatus);
            orderHistoryRetailer = (TextView) view.findViewById(R.id.tvOrderHistoryRetailer);
            orderHistoryAmount = (TextView) view.findViewById(R.id.tvOrderHistoryAmount);

        }


    }
}


