package com.inovex.bikroyik.interfaces;

import android.content.Context;

import com.inovex.bikroyik.data.model.ProductModel;

public interface CallbackOrderItem {
    void onCallback(Context context, ProductModel productModel, int quantity);
}
