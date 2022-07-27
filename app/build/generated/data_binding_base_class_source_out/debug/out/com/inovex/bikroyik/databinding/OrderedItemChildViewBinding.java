// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class OrderedItemChildViewBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout horizontalLine;

  @NonNull
  public final LinearLayout horizontalLine2;

  @NonNull
  public final TextView tvOfferProductName;

  @NonNull
  public final TextView tvOfferProductQuantity;

  @NonNull
  public final TextView tvProductName;

  @NonNull
  public final TextView tvProductTotalPrice;

  @NonNull
  public final TextView tvQuantity;

  private OrderedItemChildViewBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout horizontalLine, @NonNull LinearLayout horizontalLine2,
      @NonNull TextView tvOfferProductName, @NonNull TextView tvOfferProductQuantity,
      @NonNull TextView tvProductName, @NonNull TextView tvProductTotalPrice,
      @NonNull TextView tvQuantity) {
    this.rootView = rootView;
    this.horizontalLine = horizontalLine;
    this.horizontalLine2 = horizontalLine2;
    this.tvOfferProductName = tvOfferProductName;
    this.tvOfferProductQuantity = tvOfferProductQuantity;
    this.tvProductName = tvProductName;
    this.tvProductTotalPrice = tvProductTotalPrice;
    this.tvQuantity = tvQuantity;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static OrderedItemChildViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OrderedItemChildViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.ordered_item_child_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OrderedItemChildViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.horizontalLine;
      LinearLayout horizontalLine = rootView.findViewById(id);
      if (horizontalLine == null) {
        break missingId;
      }

      id = R.id.horizontalLine2;
      LinearLayout horizontalLine2 = rootView.findViewById(id);
      if (horizontalLine2 == null) {
        break missingId;
      }

      id = R.id.tv_offer_productName;
      TextView tvOfferProductName = rootView.findViewById(id);
      if (tvOfferProductName == null) {
        break missingId;
      }

      id = R.id.tv_offer_productQuantity;
      TextView tvOfferProductQuantity = rootView.findViewById(id);
      if (tvOfferProductQuantity == null) {
        break missingId;
      }

      id = R.id.tv_product_name;
      TextView tvProductName = rootView.findViewById(id);
      if (tvProductName == null) {
        break missingId;
      }

      id = R.id.tv_product_totalPrice;
      TextView tvProductTotalPrice = rootView.findViewById(id);
      if (tvProductTotalPrice == null) {
        break missingId;
      }

      id = R.id.tv_quantity;
      TextView tvQuantity = rootView.findViewById(id);
      if (tvQuantity == null) {
        break missingId;
      }

      return new OrderedItemChildViewBinding((LinearLayout) rootView, horizontalLine,
          horizontalLine2, tvOfferProductName, tvOfferProductQuantity, tvProductName,
          tvProductTotalPrice, tvQuantity);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
