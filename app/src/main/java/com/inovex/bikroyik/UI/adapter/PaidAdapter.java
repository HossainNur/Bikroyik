package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.DueListActivity;
import com.inovex.bikroyik.UI.activity.OrderDetailsViewActivity;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.SellsModel;
import com.inovex.bikroyik.model.DueData;
import com.inovex.bikroyik.utils.ApiConstants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PaidAdapter extends RecyclerView.Adapter<PaidAdapter.MyViewHolder> {
    private static PaidAdapter sellsAdapter = null;
    private List<SellsModel> list;
    private Context mContext;
    private SharedPreference sharedPreference;

    public PaidAdapter(Context mContext,List<SellsModel> list) {

        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.paid_fragment_view, parent, false);
        return new PaidAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        SellsModel data = list.get(position);




        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            String grandTotal = data.getGrandTotal();
            Double grandTotalValue = Double.valueOf(grandTotal);
            String due_amount = data.getDue();
            Double dueValue = Double.valueOf(due_amount);

            holder.paid_amount.setText("পরিশোধিত:  "+Math.round(grandTotalValue - dueValue)+"৳");

        }

        else
        {
            String grandTotal = data.getGrandTotal();
            Double grandTotalValue = Double.valueOf(grandTotal);
            String due_amount = data.getDue();
            Double dueValue = Double.valueOf(due_amount);

            holder.paid_amount.setText("Paid:  "+Math.round(grandTotalValue - dueValue)+"৳");
        }



        if (!TextUtils.isEmpty(data.getMobile())){
            holder.mobile.setText(data.getMobile());
        }
        else{
            holder.mobile.setText("None");
        }

        if (!TextUtils.isEmpty(data.getName())){
            holder.name.setText(data.getName());
        }
        else{
            holder.name.setText("Walking Customer");
        }
        if (!TextUtils.isEmpty(data.getOrderId())){
            holder.orderId.setText("#"+data.getOrderId());
        }
        if (!TextUtils.isEmpty(data.getOrderDate())){
            holder.orderDate.setText(data.getOrderDate());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String orderId = data.getOrderId();
                Context context = v.getContext();
                Intent intent = new Intent(context, OrderDetailsViewActivity.class);
                intent.putExtra("sendOrderId",orderId);
                context.startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    public void filterList(ArrayList<SellsModel> filteredList) {
        list = filteredList;
        notifyDataSetChanged();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder{
        TextView orderDate,grandTotal,orderId,name,mobile,paid_amount,due_amount;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDate = itemView.findViewById(R.id.dateId);
            grandTotal = itemView.findViewById(R.id.grandTotal);
            orderId = itemView.findViewById(R.id.orderId);
            name = itemView.findViewById(R.id.name);
            mobile = itemView.findViewById(R.id.mobile);
            paid_amount = itemView.findViewById(R.id.paid_amount);
            due_amount = itemView.findViewById(R.id.due_amount);
            sharedPreference = SharedPreference.getInstance(mContext);
        }
    }


}
