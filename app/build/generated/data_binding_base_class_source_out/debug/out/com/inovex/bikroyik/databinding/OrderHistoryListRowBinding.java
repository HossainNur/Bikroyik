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

public final class OrderHistoryListRowBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView tvOrderHistoryAmount;

  @NonNull
  public final TextView tvOrderHistoryDate;

  @NonNull
  public final TextView tvOrderHistoryId;

  @NonNull
  public final TextView tvOrderHistoryRetailer;

  @NonNull
  public final TextView tvOrderHistoryStatus;

  private OrderHistoryListRowBinding(@NonNull LinearLayout rootView,
      @NonNull TextView tvOrderHistoryAmount, @NonNull TextView tvOrderHistoryDate,
      @NonNull TextView tvOrderHistoryId, @NonNull TextView tvOrderHistoryRetailer,
      @NonNull TextView tvOrderHistoryStatus) {
    this.rootView = rootView;
    this.tvOrderHistoryAmount = tvOrderHistoryAmount;
    this.tvOrderHistoryDate = tvOrderHistoryDate;
    this.tvOrderHistoryId = tvOrderHistoryId;
    this.tvOrderHistoryRetailer = tvOrderHistoryRetailer;
    this.tvOrderHistoryStatus = tvOrderHistoryStatus;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static OrderHistoryListRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static OrderHistoryListRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.order_history_list_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static OrderHistoryListRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tvOrderHistoryAmount;
      TextView tvOrderHistoryAmount = rootView.findViewById(id);
      if (tvOrderHistoryAmount == null) {
        break missingId;
      }

      id = R.id.tvOrderHistoryDate;
      TextView tvOrderHistoryDate = rootView.findViewById(id);
      if (tvOrderHistoryDate == null) {
        break missingId;
      }

      id = R.id.tvOrderHistoryId;
      TextView tvOrderHistoryId = rootView.findViewById(id);
      if (tvOrderHistoryId == null) {
        break missingId;
      }

      id = R.id.tvOrderHistoryRetailer;
      TextView tvOrderHistoryRetailer = rootView.findViewById(id);
      if (tvOrderHistoryRetailer == null) {
        break missingId;
      }

      id = R.id.tvOrderHistoryStatus;
      TextView tvOrderHistoryStatus = rootView.findViewById(id);
      if (tvOrderHistoryStatus == null) {
        break missingId;
      }

      return new OrderHistoryListRowBinding((LinearLayout) rootView, tvOrderHistoryAmount,
          tvOrderHistoryDate, tvOrderHistoryId, tvOrderHistoryRetailer, tvOrderHistoryStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
