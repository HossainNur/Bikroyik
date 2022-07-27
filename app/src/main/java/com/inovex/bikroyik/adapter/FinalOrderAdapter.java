    package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;

import java.util.ArrayList;
import java.util.HashMap;

    public class FinalOrderAdapter  extends RecyclerView.Adapter<FinalOrderAdapter.FinalOrderViewHolder>{

    private ArrayList<ProductOrder> productOrders;
    Context context;


    public FinalOrderAdapter(ArrayList<ProductOrder> productOrders, Context context) {
        this.productOrders = productOrders;
        this.context = context;
    }


    @Override
    public FinalOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_order_sample_view,parent,false);
        context = parent.getContext();
        return new FinalOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FinalOrderViewHolder holder, final int position) {

        final ProductOrder productOrder = productOrders.get(position);

        holder.name.setText(productOrder.getProduct_name());
        holder.quantity.setText(productOrder.getProduct_quantity());
        holder.total.setText(productOrder.getTotal_price()+" ৳");
        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

        HashMap<String, String> specificProduct = appDatabaseHelper.getSpecificProductData(productOrder.getProduct_id());
        String unit = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_PRICE);
        String offer = specificProduct.get(AppDatabaseHelper.COLUMN_PRODUCT_AVAILABLE_OFFER);
        holder.price.setText(unit+ " ৳");
        if(offer.length() > 0){
            holder.offer.setText(offer);
        } else {
            holder.offer.setVisibility(View.GONE);
        }





    }

    @Override
    public int getItemCount() {
        return productOrders.size();

    }

    public class FinalOrderViewHolder extends RecyclerView.ViewHolder{

        TextView name, offer, quantity, price, total;

        public FinalOrderViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.name);
            offer = view.findViewById(R.id.offer);
            quantity = view.findViewById(R.id.quantity);
            price  =view.findViewById(R.id.unit_price);
            total = view.findViewById(R.id.price);


        }
    }

}
