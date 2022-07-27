package com.inovex.bikroyik.interfaces;

import com.inovex.bikroyik.data.model.Tax;

public interface TaxCallBack {
    void callback(Tax tax, boolean shouldApplyTax);
}
