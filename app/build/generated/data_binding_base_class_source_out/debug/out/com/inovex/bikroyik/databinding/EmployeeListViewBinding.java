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
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EmployeeListViewBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CircleImageView profileImage;

  @NonNull
  public final TextView tvContactNumber;

  @NonNull
  public final TextView tvName;

  private EmployeeListViewBinding(@NonNull CardView rootView, @NonNull CircleImageView profileImage,
      @NonNull TextView tvContactNumber, @NonNull TextView tvName) {
    this.rootView = rootView;
    this.profileImage = profileImage;
    this.tvContactNumber = tvContactNumber;
    this.tvName = tvName;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static EmployeeListViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EmployeeListViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.employee_list_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EmployeeListViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.profile_image;
      CircleImageView profileImage = rootView.findViewById(id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.tv_contactNumber;
      TextView tvContactNumber = rootView.findViewById(id);
      if (tvContactNumber == null) {
        break missingId;
      }

      id = R.id.tv_Name;
      TextView tvName = rootView.findViewById(id);
      if (tvName == null) {
        break missingId;
      }

      return new EmployeeListViewBinding((CardView) rootView, profileImage, tvContactNumber,
          tvName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
