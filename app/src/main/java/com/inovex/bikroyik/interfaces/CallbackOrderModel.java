package com.inovex.bikroyik.interfaces;

import com.inovex.bikroyik.data.model.OrderJsonModel;

public interface CallbackOrderModel {
    void callback(OrderJsonModel orderJsonModel);
}
