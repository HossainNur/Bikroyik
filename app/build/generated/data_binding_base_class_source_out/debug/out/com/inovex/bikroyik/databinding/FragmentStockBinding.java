// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentStockBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final RecyclerView productListRecyclerProducts;

  @NonNull
  public final RecyclerView productListRecyclerProductsGrid;

  @NonNull
  public final LinearLayout searchBox;

  @NonNull
  public final SearchableSpinner spinnerId;

  @NonNull
  public final MaterialSpinner spinnerSales;

  private FragmentStockBinding(@NonNull FrameLayout rootView,
      @NonNull RecyclerView productListRecyclerProducts,
      @NonNull RecyclerView productListRecyclerProductsGrid, @NonNull LinearLayout searchBox,
      @NonNull SearchableSpinner spinnerId, @NonNull MaterialSpinner spinnerSales) {
    this.rootView = rootView;
    this.productListRecyclerProducts = productListRecyclerProducts;
    this.productListRecyclerProductsGrid = productListRecyclerProductsGrid;
    this.searchBox = searchBox;
    this.spinnerId = spinnerId;
    this.spinnerSales = spinnerSales;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentStockBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentStockBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_stock, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentStockBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.productList_recycler_products;
      RecyclerView productListRecyclerProducts = rootView.findViewById(id);
      if (productListRecyclerProducts == null) {
        break missingId;
      }

      id = R.id.productList_recycler_products_grid;
      RecyclerView productListRecyclerProductsGrid = rootView.findViewById(id);
      if (productListRecyclerProductsGrid == null) {
        break missingId;
      }

      id = R.id.search_box;
      LinearLayout searchBox = rootView.findViewById(id);
      if (searchBox == null) {
        break missingId;
      }

      id = R.id.spinnerId;
      SearchableSpinner spinnerId = rootView.findViewById(id);
      if (spinnerId == null) {
        break missingId;
      }

      id = R.id.spinner_sales;
      MaterialSpinner spinnerSales = rootView.findViewById(id);
      if (spinnerSales == null) {
        break missingId;
      }

      return new FragmentStockBinding((FrameLayout) rootView, productListRecyclerProducts,
          productListRecyclerProductsGrid, searchBox, spinnerId, spinnerSales);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
