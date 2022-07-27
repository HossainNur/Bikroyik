package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ProductModel;
import com.inovex.bikroyik.interfaces.CallbackOrderItem;
import com.inovex.bikroyik.interfaces.ProductItemCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewProductGridAdapter extends RecyclerView.Adapter<NewProductGridAdapter.ProductListGridViewHolder> implements Filterable {
    private static NewProductGridAdapter adapter = null;
    private Context mContext;
    private List<ProductModel> productModelList,tempArray;
    private List<ProductModel> productModelListFilter;
    private boolean isListView;
    private ProductItemCallback callback;
    private CallbackOrderItem callbackOrderItem;
    private SharedPreference sharedPreference;


    public static NewProductGridAdapter getInstance(){
        if (adapter == null){
            adapter = new NewProductGridAdapter();
            return adapter;
        }
        return adapter;
    }
    public void init(Context context, List<ProductModel> productModelList, boolean isListView,
                     ProductItemCallback callback, CallbackOrderItem callbackOrderItem){
        this.mContext = context;
        this.productModelList = productModelList;
        this.productModelListFilter = productModelListFilter;
        this.isListView = isListView;
        this.callback = callback;
        this.callbackOrderItem = callbackOrderItem;

        sharedPreference = SharedPreference.getInstance(context);
    }
    public NewProductGridAdapter(){}

    @NonNull
    @Override
    public ProductListGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.child_product_list, parent, false);
        return new ProductListGridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListGridViewHolder holder, int position) {
        ProductModel productModel = productModelList.get(position);



        holder.expandedItemTextTwo.setText(productModel.getProductName());
        holder.expandedItemTextThree.setText(String.valueOf(productModel.getOnHand()));
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
                productModel.setOnHand(onHand);
                holder.expandedItemTextThree.setText(String.valueOf(onHand-1));

                callback.success(productModel, 1);
                callbackOrderItem.onCallback(mContext, productModel, 1);
            }
        });


    }

    @Override
    public int getItemCount() {

        if(productModelList != null)
        {
            return  productModelList.size();
        }

        return 0;

    }

    /*public void setFilter(ArrayList<ProductModel> productArrayList) {
        productModelList.clear();
        productModelList.addAll(productArrayList);
        notifyDataSetChanged();
    }*/
/*
    public void filterList(List<ProductModel> filteredList) {
        productModelList = filteredList;
        notifyDataSetChanged();
    }*/

   /* public void setFilter(ArrayList<ProductModel> newList) {
        productModelList = new ArrayList<>();
        productModelList.addAll(newList);
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<ProductModel> filteredList) {
    }
*/
    @Override
    public Filter getFilter() {
        return null;
    }



    /*public void filterList(List<ProductModel> filteredList) {
        productModelList = filteredList;
        notifyDataSetChanged();
    }
*/
    /*public Filter getFilter() {

        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                FilterResults filterResults = new FilterResults();
                if(charSequence == null || charSequence.length() == 0)
                {
                    filterResults.count = productModelList.size();
                    filterResults.values = productModelList;
                }
                else{
                    String searchStr = charSequence.toString().toLowerCase();
                    List<ProductModel> resultData = new ArrayList<>();

                    for(ProductModel productModel:productModelList)
                    {
                        if(productModel.getProductName().contains(searchStr))
                        {
                            resultData.add(productModel);
                        }
                        filterResults.count = resultData.size();
                        filterResults.values = resultData;
                    }

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    productModelListFilter = (List<ProductModel>) filterResults.values;
                    notifyDataSetChanged();
            }
        };
        return filter;
    }*/

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
