// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewpager.widget.ViewPager;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomescreenHrBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout viewPagerCountDots;

  @NonNull
  public final RelativeLayout viewPagerIndicator;

  @NonNull
  public final ViewPager vpTopViewPager;

  private ActivityHomescreenHrBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout viewPagerCountDots, @NonNull RelativeLayout viewPagerIndicator,
      @NonNull ViewPager vpTopViewPager) {
    this.rootView = rootView;
    this.viewPagerCountDots = viewPagerCountDots;
    this.viewPagerIndicator = viewPagerIndicator;
    this.vpTopViewPager = vpTopViewPager;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomescreenHrBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomescreenHrBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_homescreen_hr, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomescreenHrBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.viewPagerCountDots;
      LinearLayout viewPagerCountDots = rootView.findViewById(id);
      if (viewPagerCountDots == null) {
        break missingId;
      }

      id = R.id.viewPagerIndicator;
      RelativeLayout viewPagerIndicator = rootView.findViewById(id);
      if (viewPagerIndicator == null) {
        break missingId;
      }

      id = R.id.vpTopViewPager;
      ViewPager vpTopViewPager = rootView.findViewById(id);
      if (vpTopViewPager == null) {
        break missingId;
      }

      return new ActivityHomescreenHrBinding((LinearLayout) rootView, viewPagerCountDots,
          viewPagerIndicator, vpTopViewPager);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}