package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.model.OrderedProductModel;
import com.inovex.bikroyik.data.model.ProductModel;

import java.util.List;

public class FinalOrderAdapter  extends RecyclerView.Adapter<FinalOrderAdapter.FinalOrderViewHolder>{

    private List<OrderedProductModel> productOrders;
    private DatabaseSQLite databaseSQLite;
    Context context;
    private ProductModel productModel = null;


    public FinalOrderAdapter(Context context, List<OrderedProductModel> productOrders) {
        this.productOrders = productOrders;
        this.context = context;
        this.databaseSQLite = new DatabaseSQLite(context);
    }


    @Override
    public FinalOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.final_order_sample_view,parent,false);
        context = parent.getContext();
        return new FinalOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FinalOrderViewHolder holder, final int position) {

        final OrderedProductModel productOrder = productOrders.get(position);

        holder.name.setText(productOrder.getProductName());
        holder.quantity.setText(String.valueOf(productOrder.getQuantity()));
        holder.total.setText(String.valueOf(productOrder.getTotalPrice())+" ৳");
        AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(context);

        this.productModel = databaseSQLite.getSpecificProductData(productOrder.getProductId());
        holder.price.setText(productModel.getMrp()+ " ৳");
        if(Boolean.parseBoolean(productModel.getAvailableDiscount())){
            holder.offer.setText(String.valueOf(productModel.getDiscount()));
        } else {
            holder.offer.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if (productOrders != null){
            return productOrders.size();
        }
        return 0;
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
