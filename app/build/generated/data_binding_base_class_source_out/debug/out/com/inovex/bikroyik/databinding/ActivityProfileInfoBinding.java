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

public final class ActivityProfileInfoBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView organizationName;

  @NonNull
  public final ToolbarProfileBinding toolbar1;

  @NonNull
  public final TextView tvMobileNumber;

  @NonNull
  public final TextView tvProfileName;

  @NonNull
  public final TextView tvUserAddress;

  private ActivityProfileInfoBinding(@NonNull LinearLayout rootView,
      @NonNull TextView organizationName, @NonNull ToolbarProfileBinding toolbar1,
      @NonNull TextView tvMobileNumber, @NonNull TextView tvProfileName,
      @NonNull TextView tvUserAddress) {
    this.rootView = rootView;
    this.organizationName = organizationName;
    this.toolbar1 = toolbar1;
    this.tvMobileNumber = tvMobileNumber;
    this.tvProfileName = tvProfileName;
    this.tvUserAddress = tvUserAddress;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileInfoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileInfoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile_info, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileInfoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.organizationName;
      TextView organizationName = rootView.findViewById(id);
      if (organizationName == null) {
        break missingId;
      }

      id = R.id.toolbar1;
      View toolbar1 = rootView.findViewById(id);
      if (toolbar1 == null) {
        break missingId;
      }
      ToolbarProfileBinding binding_toolbar1 = ToolbarProfileBinding.bind(toolbar1);

      id = R.id.tvMobileNumber;
      TextView tvMobileNumber = rootView.findViewById(id);
      if (tvMobileNumber == null) {
        break missingId;
      }

      id = R.id.tvProfileName;
      TextView tvProfileName = rootView.findViewById(id);
      if (tvProfileName == null) {
        break missingId;
      }

      id = R.id.tvUserAddress;
      TextView tvUserAddress = rootView.findViewById(id);
      if (tvUserAddress == null) {
        break missingId;
      }

      return new ActivityProfileInfoBinding((LinearLayout) rootView, organizationName,
          binding_toolbar1, tvMobileNumber, tvProfileName, tvUserAddress);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
