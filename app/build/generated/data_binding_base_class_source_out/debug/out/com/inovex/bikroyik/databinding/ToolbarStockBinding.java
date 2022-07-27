// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ToolbarStockBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final LinearLayout btnItemsLlSales;

  @NonNull
  public final ImageView imgBackArrow;

  @NonNull
  public final TextView tittleEtt;

  @NonNull
  public final TextView totalSalesItems;

  private ToolbarStockBinding(@NonNull ConstraintLayout rootView,
      @NonNull LinearLayout btnItemsLlSales, @NonNull ImageView imgBackArrow,
      @NonNull TextView tittleEtt, @NonNull TextView totalSalesItems) {
    this.rootView = rootView;
    this.btnItemsLlSales = btnItemsLlSales;
    this.imgBackArrow = imgBackArrow;
    this.tittleEtt = tittleEtt;
    this.totalSalesItems = totalSalesItems;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ToolbarStockBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ToolbarStockBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.toolbar_stock, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ToolbarStockBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_items_ll_sales;
      LinearLayout btnItemsLlSales = rootView.findViewById(id);
      if (btnItemsLlSales == null) {
        break missingId;
      }

      id = R.id.img_backArrow;
      ImageView imgBackArrow = rootView.findViewById(id);
      if (imgBackArrow == null) {
        break missingId;
      }

      id = R.id.tittle_ett;
      TextView tittleEtt = rootView.findViewById(id);
      if (tittleEtt == null) {
        break missingId;
      }

      id = R.id.total_sales_items;
      TextView totalSalesItems = rootView.findViewById(id);
      if (totalSalesItems == null) {
        break missingId;
      }

      return new ToolbarStockBinding((ConstraintLayout) rootView, btnItemsLlSales, imgBackArrow,
          tittleEtt, totalSalesItems);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
