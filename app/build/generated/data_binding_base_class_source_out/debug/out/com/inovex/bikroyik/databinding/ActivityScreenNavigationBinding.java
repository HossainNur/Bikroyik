// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityScreenNavigationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TabLayout tabLayoutId;

  @NonNull
  public final ToolbarScreenNavigationBinding toolbar1;

  @NonNull
  public final ViewPager viewPagerId;

  private ActivityScreenNavigationBinding(@NonNull LinearLayout rootView,
      @NonNull TabLayout tabLayoutId, @NonNull ToolbarScreenNavigationBinding toolbar1,
      @NonNull ViewPager viewPagerId) {
    this.rootView = rootView;
    this.tabLayoutId = tabLayoutId;
    this.toolbar1 = toolbar1;
    this.viewPagerId = viewPagerId;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityScreenNavigationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityScreenNavigationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_screen_navigation, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityScreenNavigationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tabLayoutId;
      TabLayout tabLayoutId = rootView.findViewById(id);
      if (tabLayoutId == null) {
        break missingId;
      }

      id = R.id.toolbar1;
      View toolbar1 = rootView.findViewById(id);
      if (toolbar1 == null) {
        break missingId;
      }
      ToolbarScreenNavigationBinding binding_toolbar1 = ToolbarScreenNavigationBinding.bind(toolbar1);

      id = R.id.viewPagerId;
      ViewPager viewPagerId = rootView.findViewById(id);
      if (viewPagerId == null) {
        break missingId;
      }

      return new ActivityScreenNavigationBinding((LinearLayout) rootView, tabLayoutId,
          binding_toolbar1, viewPagerId);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
