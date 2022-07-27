package com.inovex.bikroyik.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 8/8/2018.
 */

public class NewOrderRecyclerAdapter extends RecyclerView.Adapter<NewOrderRecyclerAdapter.NewOrderViewHolder> {

    private List<NewOrderItem> newOrderItemsList;

    public NewOrderRecyclerAdapter(List<NewOrderItem> newOrderItemsList) {
        this.newOrderItemsList = newOrderItemsList;
    }


    @Override
    public NewOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_order_list_row, parent, false);

        return new NewOrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewOrderViewHolder holder, final int position) {
        final NewOrderItem newOrderItem = newOrderItemsList.get(position);
        holder.tvSerial.setText(newOrderItem.getProductPositionInList() + "");
        holder.tvName.setText(newOrderItem.getName());
        holder.tvQuantity.setText(newOrderItem.getQuantity() + "");
        holder.tvPrice.setText(newOrderItem.getTotalPrice() + "");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                newOrderItem.setQuantity(11);
//                newOrderItem.setTotalPrice(newOrderItem.getCurrentPrice() * 11);
//                // open
//                newOrderItemsList.remove(position);
//                newOrderItemsList.add(position, new NewOrderItem(newOrderItem.getNewOrderItemId(), newOrderItem.getName(),
//                        newOrderItem.getPrice(), newOrderItem.getQuantity(), newOrderItem.getDiscount(), newOrderItem.getCurrentPrice(),
//                        newOrderItem.getTotalPrice()));
//                notifyDataSetChanged();
            }
        });


    }

    @Override
    public int getItemCount() {
        return newOrderItemsList.size();
    }

    public class NewOrderViewHolder extends RecyclerView.ViewHolder {
        public TextView tvSerial, tvName, tvQuantity, tvPrice;

        public NewOrderViewHolder(View view) {
            super(view);
            tvSerial = (TextView) view.findViewById(R.id.tvSerialNUmber);
            tvName = (TextView) view.findViewById(R.id.tvProductName);
            tvQuantity = (TextView) view.findViewById(R.id.tvQuantity);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        }
    }
}


