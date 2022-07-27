package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.OrderActivity;
import com.inovex.bikroyik.UI.activity.OrderItemActivity;
import com.inovex.bikroyik.UI.activity.TotalChargeCalculationActivity;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.OrderJsonModel;
import com.inovex.bikroyik.interfaces.CallbackOrderModel;
import com.inovex.bikroyik.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private List<OrderJsonModel> orderModelList = new ArrayList<>();
    private Context mContext;
    private CallbackOrderModel callback;
    private static OrderHistoryAdapter orderHistoryAdapter = null;
    private SharedPreference sharedPreference;

    public static OrderHistoryAdapter getInstance(){
        if (orderHistoryAdapter == null){
            orderHistoryAdapter = new OrderHistoryAdapter();
            return orderHistoryAdapter;
        }else {
            return orderHistoryAdapter;
        }
    }
    public void init(Context context, List<OrderJsonModel> orderModelList, CallbackOrderModel callback){
        this.mContext = context;
        this.orderModelList = orderModelList;
        this.callback = callback;

        sharedPreference = SharedPreference.getInstance(context);
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_order_history, parent, false);
        return new OrderHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {
        OrderJsonModel orderModel = orderModelList.get(position);

        String dateTime = Constants.getDateFromTimestamp(Long.parseLong(orderModel.getOrderId()));
        holder.tv_date.setText(dateTime);

        if (!TextUtils.isEmpty(String.valueOf(orderModel.getClientId()))){
            holder.tv_client_name.setText("null");
            holder.tv_client_name.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }

        holder.tv_order_id.setText(orderModel.getOrderId());

        holder.btn_gotoOrderArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreference.setCurrentOrderId(orderModel.getOrderId());
                mContext.startActivity(new Intent(mContext, OrderActivity.class));
                //mContext.startActivity(new Intent(mContext, OrderItemActivity.class));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callback.callback(orderModel);
                Intent intent = new Intent(mContext, TotalChargeCalculationActivity.class);
                intent.putExtra(Constants.KEY_ORDER_ID, orderModel.getOrderId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (orderModelList == null){
            return 0;
        }
        return orderModelList.size();
    }

    protected class OrderHistoryViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date, tv_client_name, tv_order_id;
        ImageView btn_gotoOrderArrow;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            tv_client_name = (TextView) itemView.findViewById(R.id.tv_client_name);
            tv_order_id = (TextView) itemView.findViewById(R.id.tv_order_id);
            btn_gotoOrderArrow = (ImageView) itemView.findViewById(R.id.btn_gotoOrderArrow);

        }
    }
}
