package com.inovex.bikroyik.adapter;

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
import com.inovex.bikroyik.activity.FinalOrderDetailsActivity;

import java.util.List;

public class OrdersDetailsAdapter extends RecyclerView.Adapter<OrdersDetailsAdapter.OrderDetailsViewHolder>{

    private List<Orders> ordersList;
    Context context;

    public OrdersDetailsAdapter(List<Orders> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
    }

    @Override
    public OrderDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.final_order_list_row, parent, false);

        return new OrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderDetailsViewHolder holder, int position) {


        //holder.sample.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_transition));
        final Orders orders = ordersList.get(position);
        holder.orderId.setText("order Id: "+orders.getOrderId());
        holder.retail.setText("Retailer : "+orders.getRetailName());
        holder.owner.setText(orders.getOwner());
        holder.total.setText("Total : "+orders.getTotal()+"/-");
        holder.due.setText("Due : "+orders.getDue()+"/-");
        holder.delivery.setText("Delivery Date : "+orders.getDeliveryDate());
        String retailId = orders.getRetailId();
        Log.d("workforce_orders", "populateOrdersData: "+ordersList.size());


        holder.sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FinalOrderDetailsActivity.class);
                intent.putExtra("order", orders.getOrderId());
                context.startActivity(intent);

            }
        });





    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        public TextView orderId, retail, owner, total, due, delivery;
        LinearLayout sample;


        public OrderDetailsViewHolder(View view) {
            super(view);

            orderId = view.findViewById(R.id.order_details_orderId);
            retail = view.findViewById(R.id.order_details_retail);
            owner = view.findViewById(R.id.order_details_retail_owner);
            total = view.findViewById(R.id.order_details_total);
            due = view.findViewById(R.id.order_details_due);
            delivery = view.findViewById(R.id.tvOrderDeliveryDate);
            sample = view.findViewById(R.id.see_details_orders);



        }
    }

}
