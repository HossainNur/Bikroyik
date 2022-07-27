package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.inovex.bikroyik.R;

import java.util.ArrayList;

public class FinalOrderDetailsAdapter extends RecyclerView.Adapter<FinalOrderDetailsAdapter.FinalOrderDetailsViewHolder>{

    private ArrayList<ProductOrder> productOrders;
    Context context;

    public FinalOrderDetailsAdapter(ArrayList<ProductOrder> productOrders, Context context) {
        this.productOrders = productOrders;
        this.context = context;
    }

    @Override
    public FinalOrderDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_order_details_sample,parent,false);
        context = parent.getContext();
        return new FinalOrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FinalOrderDetailsViewHolder holder, int position) {

        final ProductOrder productOrder = productOrders.get(position);

        holder.name.setText(productOrder.getProduct_name());
        holder.quantity.setText(productOrder.getProduct_quantity());
        holder.total.setText(productOrder.getTotal_price()+" ৳");

       /* if(position % 2 == 0){
            holder.sample.setBackgroundColor(context.getColor(R.color.colorAccent));
        }*/


    }

    @Override
    public int getItemCount() {
        return productOrders.size();
    }

    public class FinalOrderDetailsViewHolder extends RecyclerView.ViewHolder{

        TextView name, quantity, total;
        LinearLayout sample;

        public FinalOrderDetailsViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            quantity = view.findViewById(R.id.quantity);
            total = view.findViewById(R.id.price);
            sample = view.findViewById(R.id.final_order_details);


        }
    }
}
