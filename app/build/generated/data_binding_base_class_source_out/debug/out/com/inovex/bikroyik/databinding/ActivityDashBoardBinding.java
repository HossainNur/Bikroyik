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

public final class ActivityDashBoardBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView currentMonth;

  @NonNull
  public final TextView currentYear;

  @NonNull
  public final TextView monthDeposit;

  @NonNull
  public final TextView monthDue;

  @NonNull
  public final TextView monthExpense;

  @NonNull
  public final TextView monthSell;

  @NonNull
  public final TextView todayDeposit;

  @NonNull
  public final TextView todayDue;

  @NonNull
  public final TextView todayExpense;

  @NonNull
  public final TextView todayExpenseTotal;

  @NonNull
  public final TextView todaySale;

  @NonNull
  public final ToolbarDashboard1Binding toolbar1;

  @NonNull
  public final TextView totalBill;

  @NonNull
  public final TextView totalDeposit;

  @NonNull
  public final TextView totalDue;

  @NonNull
  public final TextView totalExpense;

  @NonNull
  public final TextView totalSell;

  @NonNull
  public final TextView yearDeposit;

  @NonNull
  public final TextView yearDue;

  @NonNull
  public final TextView yearExpense;

  @NonNull
  public final TextView yearSell;

  private ActivityDashBoardBinding(@NonNull LinearLayout rootView, @NonNull TextView currentMonth,
      @NonNull TextView currentYear, @NonNull TextView monthDeposit, @NonNull TextView monthDue,
      @NonNull TextView monthExpense, @NonNull TextView monthSell, @NonNull TextView todayDeposit,
      @NonNull TextView todayDue, @NonNull TextView todayExpense,
      @NonNull TextView todayExpenseTotal, @NonNull TextView todaySale,
      @NonNull ToolbarDashboard1Binding toolbar1, @NonNull TextView totalBill,
      @NonNull TextView totalDeposit, @NonNull TextView totalDue, @NonNull TextView totalExpense,
      @NonNull TextView totalSell, @NonNull TextView yearDeposit, @NonNull TextView yearDue,
      @NonNull TextView yearExpense, @NonNull TextView yearSell) {
    this.rootView = rootView;
    this.currentMonth = currentMonth;
    this.currentYear = currentYear;
    this.monthDeposit = monthDeposit;
    this.monthDue = monthDue;
    this.monthExpense = monthExpense;
    this.monthSell = monthSell;
    this.todayDeposit = todayDeposit;
    this.todayDue = todayDue;
    this.todayExpense = todayExpense;
    this.todayExpenseTotal = todayExpenseTotal;
    this.todaySale = todaySale;
    this.toolbar1 = toolbar1;
    this.totalBill = totalBill;
    this.totalDeposit = totalDeposit;
    this.totalDue = totalDue;
    this.totalExpense = totalExpense;
    this.totalSell = totalSell;
    this.yearDeposit = yearDeposit;
    this.yearDue = yearDue;
    this.yearExpense = yearExpense;
    this.yearSell = yearSell;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityDashBoardBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityDashBoardBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_dash_board, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityDashBoardBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.currentMonth;
      TextView currentMonth = rootView.findViewById(id);
      if (currentMonth == null) {
        break missingId;
      }

      id = R.id.currentYear;
      TextView currentYear = rootView.findViewById(id);
      if (currentYear == null) {
        break missingId;
      }

      id = R.id.monthDeposit;
      TextView monthDeposit = rootView.findViewById(id);
      if (monthDeposit == null) {
        break missingId;
      }

      id = R.id.monthDue;
      TextView monthDue = rootView.findViewById(id);
      if (monthDue == null) {
        break missingId;
      }

      id = R.id.monthExpense;
      TextView monthExpense = rootView.findViewById(id);
      if (monthExpense == null) {
        break missingId;
      }

      id = R.id.monthSell;
      TextView monthSell = rootView.findViewById(id);
      if (monthSell == null) {
        break missingId;
      }

      id = R.id.todayDeposit;
      TextView todayDeposit = rootView.findViewById(id);
      if (todayDeposit == null) {
        break missingId;
      }

      id = R.id.todayDue;
      TextView todayDue = rootView.findViewById(id);
      if (todayDue == null) {
        break missingId;
      }

      id = R.id.todayExpense;
      TextView todayExpense = rootView.findViewById(id);
      if (todayExpense == null) {
        break missingId;
      }

      id = R.id.todayExpenseTotal;
      TextView todayExpenseTotal = rootView.findViewById(id);
      if (todayExpenseTotal == null) {
        break missingId;
      }

      id = R.id.todaySale;
      TextView todaySale = rootView.findViewById(id);
      if (todaySale == null) {
        break missingId;
      }

      id = R.id.toolbar1;
      View toolbar1 = rootView.findViewById(id);
      if (toolbar1 == null) {
        break missingId;
      }
      ToolbarDashboard1Binding binding_toolbar1 = ToolbarDashboard1Binding.bind(toolbar1);

      id = R.id.totalBill;
      TextView totalBill = rootView.findViewById(id);
      if (totalBill == null) {
        break missingId;
      }

      id = R.id.totalDeposit;
      TextView totalDeposit = rootView.findViewById(id);
      if (totalDeposit == null) {
        break missingId;
      }

      id = R.id.totalDue;
      TextView totalDue = rootView.findViewById(id);
      if (totalDue == null) {
        break missingId;
      }

      id = R.id.totalExpense;
      TextView totalExpense = rootView.findViewById(id);
      if (totalExpense == null) {
        break missingId;
      }

      id = R.id.totalSell;
      TextView totalSell = rootView.findViewById(id);
      if (totalSell == null) {
        break missingId;
      }

      id = R.id.yearDeposit;
      TextView yearDeposit = rootView.findViewById(id);
      if (yearDeposit == null) {
        break missingId;
      }

      id = R.id.yearDue;
      TextView yearDue = rootView.findViewById(id);
      if (yearDue == null) {
        break missingId;
      }

      id = R.id.yearExpense;
      TextView yearExpense = rootView.findViewById(id);
      if (yearExpense == null) {
        break missingId;
      }

      id = R.id.yearSell;
      TextView yearSell = rootView.findViewById(id);
      if (yearSell == null) {
        break missingId;
      }

      return new ActivityDashBoardBinding((LinearLayout) rootView, currentMonth, currentYear,
          monthDeposit, monthDue, monthExpense, monthSell, todayDeposit, todayDue, todayExpense,
          todayExpenseTotal, todaySale, binding_toolbar1, totalBill, totalDeposit, totalDue,
          totalExpense, totalSell, yearDeposit, yearDue, yearExpense, yearSell);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
