// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class PopupPermissionGuidelineBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button btnClosePermissionGuide;

  @NonNull
  public final ScrollView scrollPermissionGuideline;

  @NonNull
  public final TextView tvGuideline;

  private PopupPermissionGuidelineBinding(@NonNull RelativeLayout rootView,
      @NonNull Button btnClosePermissionGuide, @NonNull ScrollView scrollPermissionGuideline,
      @NonNull TextView tvGuideline) {
    this.rootView = rootView;
    this.btnClosePermissionGuide = btnClosePermissionGuide;
    this.scrollPermissionGuideline = scrollPermissionGuideline;
    this.tvGuideline = tvGuideline;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static PopupPermissionGuidelineBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static PopupPermissionGuidelineBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.popup_permission_guideline, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static PopupPermissionGuidelineBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_close_permissionGuide;
      Button btnClosePermissionGuide = rootView.findViewById(id);
      if (btnClosePermissionGuide == null) {
        break missingId;
      }

      id = R.id.scroll_permission_guideline;
      ScrollView scrollPermissionGuideline = rootView.findViewById(id);
      if (scrollPermissionGuideline == null) {
        break missingId;
      }

      id = R.id.tv_guideline;
      TextView tvGuideline = rootView.findViewById(id);
      if (tvGuideline == null) {
        break missingId;
      }

      return new PopupPermissionGuidelineBinding((RelativeLayout) rootView, btnClosePermissionGuide,
          scrollPermissionGuideline, tvGuideline);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}