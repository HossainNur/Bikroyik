// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPopUpWindowActivitiyBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final Button buttonAllow;

  @NonNull
  public final RelativeLayout popupWindowLayout;

  private ActivityPopUpWindowActivitiyBinding(@NonNull RelativeLayout rootView,
      @NonNull Button buttonAllow, @NonNull RelativeLayout popupWindowLayout) {
    this.rootView = rootView;
    this.buttonAllow = buttonAllow;
    this.popupWindowLayout = popupWindowLayout;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPopUpWindowActivitiyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPopUpWindowActivitiyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_pop_up_window_activitiy, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPopUpWindowActivitiyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.buttonAllow;
      Button buttonAllow = rootView.findViewById(id);
      if (buttonAllow == null) {
        break missingId;
      }

      RelativeLayout popupWindowLayout = (RelativeLayout) rootView;

      return new ActivityPopUpWindowActivitiyBinding((RelativeLayout) rootView, buttonAllow,
          popupWindowLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
