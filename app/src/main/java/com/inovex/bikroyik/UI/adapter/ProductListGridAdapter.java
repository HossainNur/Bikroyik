package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.interfaces.ProductItemCallback;
import com.squareup.picasso.Picasso;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class ProductListGridAdapter extends RecyclerView.Adapter<ProductListGridAdapter.ProductListGridViewHolder> {
    private static ProductListGridAdapter adapter = null;
    private Context mContext;
    private List<ProductModel> productModelList;
    private boolean isListView;
    private ProductItemCallback callback;
    private CallbackOrderItem callbackOrderItem;
    private SharedPreference sharedPreference;
    public static ProductListGridAdapter getInstance(){
        if (adapter == null){
            adapter = new ProductListGridAdapter();
            return adapter;
        }
        return adapter;
    }
    public void init(Context context, List<ProductModel> productModelList, boolean isListView,
                     ProductItemCallback callback, CallbackOrderItem callbackOrderItem){
        this.mContext = context;
        this.productModelList = productModelList;
        this.isListView = isListView;
        this.callback = callback;
        this.callbackOrderItem = callbackOrderItem;

        sharedPreference = SharedPreference.getInstance(context);
    }
    private ProductListGridAdapter(){}

    @NonNull
    @Override
    public ProductListGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_grid_product_view, parent, false);
        return new ProductListGridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListGridViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);



        holder.expandedItemTextTwo.setText(productModel.getProductName());
        String productName = holder.expandedItemTextTwo.getText().toString();
        holder.expandedItemTextThree.setText(String.valueOf(productModel.getOnHand()));

        /*String value = holder.expandedItemTextThree.getText().toString();
        Integer list = Integer.parseInt(value);

        if((list < 0))
        {
            holder.expandedItemTextThree.setTextColor(Color.RED);
            holder.expandedItemTextThree.setText("stock out");
        }*/

        holder.expandedItemTextFour.setText(String.valueOf(productModel.getMrp()));


        String url = productModel.getProductImage();

        Log.d("imageProduct",url);

        Picasso.get().load(url).into(holder.ivProduct);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sharedPreference.getCurrentOrderId()) || sharedPreference.getCurrentOrderId() == null){
                    String orderId = String.valueOf(System.currentTimeMillis()/2);
                    sharedPreference.setCurrentOrderId(orderId);
                }

                int onHand = Integer.parseInt(holder.expandedItemTextThree.getText().toString());
                if(onHand > 0)
                {
                    productModel.setOnHand(onHand);
                    holder.expandedItemTextThree.setText(String.valueOf(onHand-1));
                    callback.success(productModel, 1);
                    callbackOrderItem.onCallback(mContext, productModel, 1);
                }
                if(onHand == 0)
                {
                    Toasty.info(mContext, productName+" are not available in our stock", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    protected class ProductListGridViewHolder extends RecyclerView.ViewHolder {
        TextView expandedItemTextTwo, expandedItemTextThree, expandedItemTextFour;
        ImageView ivProduct;
        public ProductListGridViewHolder(@NonNull View itemView) {
            super(itemView);

            expandedItemTextTwo = (TextView) itemView.findViewById(R.id.expandedItemTextTwo);
            expandedItemTextThree = (TextView) itemView.findViewById(R.id.expandedItemTextThree);
            expandedItemTextFour = (TextView) itemView.findViewById(R.id.expandedItemTextFour);
            ivProduct = (ImageView) itemView.findViewById(R.id.ivProduct);
        }
    }
}
