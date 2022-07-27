package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.model.DueCollection;
import com.inovex.bikroyik.popup.CashCollectionPopupActivity;

import java.util.ArrayList;

public class DueCollectionAdapter extends RecyclerView.Adapter<DueCollectionAdapter.DueOrderViewHolder> {
    private ArrayList<DueCollection> dueCollectionList = new ArrayList<>();
    private Context mContext;

    private static final int resultCode = 1231;
    public void setItems(ArrayList<DueCollection> dueList) {
        this.dueCollectionList.clear();
        this.dueCollectionList = dueList;
    }

    public DueCollectionAdapter(Context context, ArrayList<DueCollection> dueList){
        this.dueCollectionList = dueList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public DueOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.due_iteam, parent, false);
        return new DueOrderViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull DueOrderViewHolder holder, int position) {
        DueCollection dueCollection = this.dueCollectionList.get(position);

        holder.orderId.setText("Order Id :"+dueCollection.getOrderId());
        holder.orderDate.setText("Date : "+dueCollection.getOrderDate());
        holder.retailerName.setText(dueCollection.getRetailerName());
        holder.totalAmount.setText("Total : "+dueCollection.getTotalAmount()+" tk");
        holder.grandTotal.setText("Grand Total : "+dueCollection.getGrandTotal()+" tk");
        holder.discount.setText("Discount : "+dueCollection.getDiscount()+" tk");
        holder.dueAmount.setText("Due : "+dueCollection.getTotalDue()+" tk");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("_due_adp_", "Due Collection Adapter -->> onClick : "+dueCollectionList.get(position).getRetailerName());

                Intent intent = new Intent(mContext, CashCollectionPopupActivity.class);
                intent.putExtra("retailerName", dueCollectionList.get(position).getRetailerName());
                intent.putExtra("dueCollectionObj", dueCollectionList.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dueCollectionList.size();
    }


    public class DueOrderViewHolder extends RecyclerView.ViewHolder{
        TextView orderId, orderDate, retailerName, totalAmount, dueAmount, discount, grandTotal;
        public DueOrderViewHolder(@NonNull View itemView) {
            super(itemView);

            orderId = (TextView) itemView.findViewById(R.id.tvOrderId_dueCollection);
            orderDate = (TextView) itemView.findViewById(R.id.tvOrderDate_dueCollection);
            retailerName = (TextView) itemView.findViewById(R.id.tvRetailerName_dueCollection);
            totalAmount = (TextView) itemView.findViewById(R.id.tvTotalPrice_dueCollection);
            grandTotal = (TextView) itemView.findViewById(R.id.tvGrandTotalPrice_dueCollection);
            discount = (TextView) itemView.findViewById(R.id.tvDiscount_dueCollection);
            dueAmount = (TextView) itemView.findViewById(R.id.tvTotalDue_dueCollection);
        }
    }
}
