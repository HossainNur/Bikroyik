// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOrderDetailsBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText etOrderConfirmCollection;

  @NonNull
  public final EditText etOrderConfirmDueAmount;

  @NonNull
  public final EditText etOrderConfirmTotalPrice;

  @NonNull
  public final ToolbarMeetingBinding tbToolbarMeeting;

  @NonNull
  public final TextView tvOrderConfirmOrderAmount;

  @NonNull
  public final TextView tvOrderConfirmOrderDate;

  @NonNull
  public final TextView tvOrderConfirmOrderDueAmount;

  @NonNull
  public final TextView tvOrderConfirmOrderId;

  @NonNull
  public final TextView tvOrderConfirmRetailerAddress;

  @NonNull
  public final TextView tvOrderConfirmRetailerContact;

  @NonNull
  public final TextView tvOrderConfirmRetailerName;

  @NonNull
  public final TextView tvOrderConfirmSubmit;

  private ActivityOrderDetailsBinding(@NonNull LinearLayout rootView,
      @NonNull EditText etOrderConfirmCollection, @NonNull EditText etOrderConfirmDueAmount,
      @NonNull EditText etOrderConfirmTotalPrice, @NonNull ToolbarMeetingBinding tbToolbarMeeting,
      @NonNull TextView tvOrderConfirmOrderAmount, @NonNull TextView tvOrderConfirmOrderDate,
      @NonNull TextView tvOrderConfirmOrderDueAmount, @NonNull TextView tvOrderConfirmOrderId,
      @NonNull TextView tvOrderConfirmRetailerAddress,
      @NonNull TextView tvOrderConfirmRetailerContact, @NonNull TextView tvOrderConfirmRetailerName,
      @NonNull TextView tvOrderConfirmSubmit) {
    this.rootView = rootView;
    this.etOrderConfirmCollection = etOrderConfirmCollection;
    this.etOrderConfirmDueAmount = etOrderConfirmDueAmount;
    this.etOrderConfirmTotalPrice = etOrderConfirmTotalPrice;
    this.tbToolbarMeeting = tbToolbarMeeting;
    this.tvOrderConfirmOrderAmount = tvOrderConfirmOrderAmount;
    this.tvOrderConfirmOrderDate = tvOrderConfirmOrderDate;
    this.tvOrderConfirmOrderDueAmount = tvOrderConfirmOrderDueAmount;
    this.tvOrderConfirmOrderId = tvOrderConfirmOrderId;
    this.tvOrderConfirmRetailerAddress = tvOrderConfirmRetailerAddress;
    this.tvOrderConfirmRetailerContact = tvOrderConfirmRetailerContact;
    this.tvOrderConfirmRetailerName = tvOrderConfirmRetailerName;
    this.tvOrderConfirmSubmit = tvOrderConfirmSubmit;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOrderDetailsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOrderDetailsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_order_details, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOrderDetailsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.etOrderConfirmCollection;
      EditText etOrderConfirmCollection = rootView.findViewById(id);
      if (etOrderConfirmCollection == null) {
        break missingId;
      }

      id = R.id.etOrderConfirmDueAmount;
      EditText etOrderConfirmDueAmount = rootView.findViewById(id);
      if (etOrderConfirmDueAmount == null) {
        break missingId;
      }

      id = R.id.etOrderConfirmTotalPrice;
      EditText etOrderConfirmTotalPrice = rootView.findViewById(id);
      if (etOrderConfirmTotalPrice == null) {
        break missingId;
      }

      id = R.id.tbToolbarMeeting;
      View tbToolbarMeeting = rootView.findViewById(id);
      if (tbToolbarMeeting == null) {
        break missingId;
      }
      ToolbarMeetingBinding binding_tbToolbarMeeting = ToolbarMeetingBinding.bind(tbToolbarMeeting);

      id = R.id.tvOrderConfirmOrderAmount;
      TextView tvOrderConfirmOrderAmount = rootView.findViewById(id);
      if (tvOrderConfirmOrderAmount == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmOrderDate;
      TextView tvOrderConfirmOrderDate = rootView.findViewById(id);
      if (tvOrderConfirmOrderDate == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmOrderDueAmount;
      TextView tvOrderConfirmOrderDueAmount = rootView.findViewById(id);
      if (tvOrderConfirmOrderDueAmount == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmOrderId;
      TextView tvOrderConfirmOrderId = rootView.findViewById(id);
      if (tvOrderConfirmOrderId == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmRetailerAddress;
      TextView tvOrderConfirmRetailerAddress = rootView.findViewById(id);
      if (tvOrderConfirmRetailerAddress == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmRetailerContact;
      TextView tvOrderConfirmRetailerContact = rootView.findViewById(id);
      if (tvOrderConfirmRetailerContact == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmRetailerName;
      TextView tvOrderConfirmRetailerName = rootView.findViewById(id);
      if (tvOrderConfirmRetailerName == null) {
        break missingId;
      }

      id = R.id.tvOrderConfirmSubmit;
      TextView tvOrderConfirmSubmit = rootView.findViewById(id);
      if (tvOrderConfirmSubmit == null) {
        break missingId;
      }

      return new ActivityOrderDetailsBinding((LinearLayout) rootView, etOrderConfirmCollection,
          etOrderConfirmDueAmount, etOrderConfirmTotalPrice, binding_tbToolbarMeeting,
          tvOrderConfirmOrderAmount, tvOrderConfirmOrderDate, tvOrderConfirmOrderDueAmount,
          tvOrderConfirmOrderId, tvOrderConfirmRetailerAddress, tvOrderConfirmRetailerContact,
          tvOrderConfirmRetailerName, tvOrderConfirmSubmit);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
