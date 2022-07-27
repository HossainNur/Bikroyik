package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
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

public class ProductAdapterForLandscapeMode extends
        RecyclerView.Adapter<ProductAdapterForLandscapeMode.ProductListLandscapeViewHolder> {
    private static ProductAdapterForLandscapeMode adapter = null;
    private Context mContext;
    private List<ProductModel> productModelList;
    private boolean isListView;
    private ProductItemCallback callback;
    private CallbackOrderItem callbackOrderItem;
    private SharedPreference sharedPreference;


    public static ProductAdapterForLandscapeMode getInstance(){
        if (adapter == null){
            adapter = new ProductAdapterForLandscapeMode();
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

    @NonNull
    @Override
    public ProductListLandscapeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, parent, false);
        return new ProductListLandscapeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListLandscapeViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);



//        holder.expandedItemTextOne.setText(productModel.getProductId());
        holder.expandedItemTextTwo.setText(productModel.getProductName());
        String productName = holder.expandedItemTextTwo.getText().toString();
        holder.expandedItemTextThree.setText(String.valueOf(productModel.getOnHand()));
        /*String value = holder.expandedItemTextThree.getText().toString();
        Integer list = Integer.parseInt(value);*/
        holder.expandedItemTextFour.setText(String.valueOf(productModel.getMrp()));


        String url = productModel.getProductImage();

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
        if (productModelList == null){
            return 0;
        }
        return productModelList.size();
    }

    protected class ProductListLandscapeViewHolder extends RecyclerView.ViewHolder {
        TextView expandedItemTextOne;
        TextView  expandedItemTextTwo, expandedItemTextThree, expandedItemTextFour;
        ImageView ivProduct;
        public ProductListLandscapeViewHolder(@NonNull View itemView) {
            super(itemView);

//            expandedItemTextOne = (TextView) itemView.findViewById(R.id.expandedItemTextOne);
            expandedItemTextTwo = (TextView) itemView.findViewById(R.id.expandedItemTextTwo);
            expandedItemTextThree = (TextView) itemView.findViewById(R.id.expandedItemTextThree);
            expandedItemTextFour = (TextView) itemView.findViewById(R.id.expandedItemTextFour);
            ivProduct = (ImageView) itemView.findViewById(R.id.ivProduct);
        }
    }
}
