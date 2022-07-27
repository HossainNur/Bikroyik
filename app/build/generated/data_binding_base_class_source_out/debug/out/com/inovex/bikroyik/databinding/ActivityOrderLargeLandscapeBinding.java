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
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOrderLargeLandscapeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final View horizontalLine;

  @NonNull
  public final ImageView imgListGridSales;

  @NonNull
  public final ImageView imgQuickSales;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final LinearLayout llBtnListGridSales;

  @NonNull
  public final LinearLayout llBtnSalesCalSales;

  @NonNull
  public final RecyclerView productRecyclerLandscape;

  @NonNull
  public final RecyclerView productRecyclerLandscapeGrid;

  @NonNull
  public final ToolbarSalesLargeLandscapeBinding toolbarLargeLandscape;

  @NonNull
  public final TextView tvListGridProductsSales;

  @NonNull
  public final TextView tvSalesCalSales;

  private ActivityOrderLargeLandscapeBinding(@NonNull ConstraintLayout rootView,
      @NonNull View horizontalLine, @NonNull ImageView imgListGridSales,
      @NonNull ImageView imgQuickSales, @NonNull LinearLayout linearLayout2,
      @NonNull LinearLayout llBtnListGridSales, @NonNull LinearLayout llBtnSalesCalSales,
      @NonNull RecyclerView productRecyclerLandscape,
      @NonNull RecyclerView productRecyclerLandscapeGrid,
      @NonNull ToolbarSalesLargeLandscapeBinding toolbarLargeLandscape,
      @NonNull TextView tvListGridProductsSales, @NonNull TextView tvSalesCalSales) {
    this.rootView = rootView;
    this.horizontalLine = horizontalLine;
    this.imgListGridSales = imgListGridSales;
    this.imgQuickSales = imgQuickSales;
    this.linearLayout2 = linearLayout2;
    this.llBtnListGridSales = llBtnListGridSales;
    this.llBtnSalesCalSales = llBtnSalesCalSales;
    this.productRecyclerLandscape = productRecyclerLandscape;
    this.productRecyclerLandscapeGrid = productRecyclerLandscapeGrid;
    this.toolbarLargeLandscape = toolbarLargeLandscape;
    this.tvListGridProductsSales = tvListGridProductsSales;
    this.tvSalesCalSales = tvSalesCalSales;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOrderLargeLandscapeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOrderLargeLandscapeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_order_large_landscape, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOrderLargeLandscapeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.horizontalLine;
      View horizontalLine = rootView.findViewById(id);
      if (horizontalLine == null) {
        break missingId;
      }

      id = R.id.img_listGrid_sales;
      ImageView imgListGridSales = rootView.findViewById(id);
      if (imgListGridSales == null) {
        break missingId;
      }

      id = R.id.img_quick_sales;
      ImageView imgQuickSales = rootView.findViewById(id);
      if (imgQuickSales == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = rootView.findViewById(id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.ll_btn_listGrid_sales;
      LinearLayout llBtnListGridSales = rootView.findViewById(id);
      if (llBtnListGridSales == null) {
        break missingId;
      }

      id = R.id.ll_btn_salesCal_sales;
      LinearLayout llBtnSalesCalSales = rootView.findViewById(id);
      if (llBtnSalesCalSales == null) {
        break missingId;
      }

      id = R.id.product_recycler_landscape;
      RecyclerView productRecyclerLandscape = rootView.findViewById(id);
      if (productRecyclerLandscape == null) {
        break missingId;
      }

      id = R.id.product_recycler_landscape_grid;
      RecyclerView productRecyclerLandscapeGrid = rootView.findViewById(id);
      if (productRecyclerLandscapeGrid == null) {
        break missingId;
      }

      id = R.id.toolbar_large_landscape;
      View toolbarLargeLandscape = rootView.findViewById(id);
      if (toolbarLargeLandscape == null) {
        break missingId;
      }
      ToolbarSalesLargeLandscapeBinding binding_toolbarLargeLandscape = ToolbarSalesLargeLandscapeBinding.bind(toolbarLargeLandscape);

      id = R.id.tv_listGrid_products_sales;
      TextView tvListGridProductsSales = rootView.findViewById(id);
      if (tvListGridProductsSales == null) {
        break missingId;
      }

      id = R.id.tv_salesCal_sales;
      TextView tvSalesCalSales = rootView.findViewById(id);
      if (tvSalesCalSales == null) {
        break missingId;
      }

      return new ActivityOrderLargeLandscapeBinding((ConstraintLayout) rootView, horizontalLine,
          imgListGridSales, imgQuickSales, linearLayout2, llBtnListGridSales, llBtnSalesCalSales,
          productRecyclerLandscape, productRecyclerLandscapeGrid, binding_toolbarLargeLandscape,
          tvListGridProductsSales, tvSalesCalSales);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}