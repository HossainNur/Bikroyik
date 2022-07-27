// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MyRowBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView dateId;

  @NonNull
  public final TextView discountTxt;

  @NonNull
  public final TextView orderIdTxt;

  @NonNull
  public final TextView totalPriceTxt;

  private MyRowBinding(@NonNull CardView rootView, @NonNull TextView dateId,
      @NonNull TextView discountTxt, @NonNull TextView orderIdTxt,
      @NonNull TextView totalPriceTxt) {
    this.rootView = rootView;
    this.dateId = dateId;
    this.discountTxt = discountTxt;
    this.orderIdTxt = orderIdTxt;
    this.totalPriceTxt = totalPriceTxt;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static MyRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MyRowBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.my_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MyRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dateId;
      TextView dateId = rootView.findViewById(id);
      if (dateId == null) {
        break missingId;
      }

      id = R.id.discountTxt;
      TextView discountTxt = rootView.findViewById(id);
      if (discountTxt == null) {
        break missingId;
      }

      id = R.id.orderIdTxt;
      TextView orderIdTxt = rootView.findViewById(id);
      if (orderIdTxt == null) {
        break missingId;
      }

      id = R.id.totalPriceTxt;
      TextView totalPriceTxt = rootView.findViewById(id);
      if (totalPriceTxt == null) {
        break missingId;
      }

      return new MyRowBinding((CardView) rootView, dateId, discountTxt, orderIdTxt, totalPriceTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}