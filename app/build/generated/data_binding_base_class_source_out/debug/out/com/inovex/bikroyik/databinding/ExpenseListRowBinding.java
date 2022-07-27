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

public final class ExpenseListRowBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout expenseLinearLayout;

  @NonNull
  public final TextView tvExpenseAmount;

  @NonNull
  public final TextView tvExpenseAmountApproved;

  @NonNull
  public final TextView tvExpenseDateTime;

  @NonNull
  public final TextView tvExpenseNote;

  @NonNull
  public final TextView tvExpenseStatus;

  @NonNull
  public final TextView tvExpenseType;

  private ExpenseListRowBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout expenseLinearLayout, @NonNull TextView tvExpenseAmount,
      @NonNull TextView tvExpenseAmountApproved, @NonNull TextView tvExpenseDateTime,
      @NonNull TextView tvExpenseNote, @NonNull TextView tvExpenseStatus,
      @NonNull TextView tvExpenseType) {
    this.rootView = rootView;
    this.expenseLinearLayout = expenseLinearLayout;
    this.tvExpenseAmount = tvExpenseAmount;
    this.tvExpenseAmountApproved = tvExpenseAmountApproved;
    this.tvExpenseDateTime = tvExpenseDateTime;
    this.tvExpenseNote = tvExpenseNote;
    this.tvExpenseStatus = tvExpenseStatus;
    this.tvExpenseType = tvExpenseType;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ExpenseListRowBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ExpenseListRowBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.expense_list_row, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ExpenseListRowBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout expenseLinearLayout = (LinearLayout) rootView;

      id = R.id.tvExpenseAmount;
      TextView tvExpenseAmount = rootView.findViewById(id);
      if (tvExpenseAmount == null) {
        break missingId;
      }

      id = R.id.tvExpenseAmountApproved;
      TextView tvExpenseAmountApproved = rootView.findViewById(id);
      if (tvExpenseAmountApproved == null) {
        break missingId;
      }

      id = R.id.tvExpenseDateTime;
      TextView tvExpenseDateTime = rootView.findViewById(id);
      if (tvExpenseDateTime == null) {
        break missingId;
      }

      id = R.id.tvExpenseNote;
      TextView tvExpenseNote = rootView.findViewById(id);
      if (tvExpenseNote == null) {
        break missingId;
      }

      id = R.id.tvExpenseStatus;
      TextView tvExpenseStatus = rootView.findViewById(id);
      if (tvExpenseStatus == null) {
        break missingId;
      }

      id = R.id.tvExpenseType;
      TextView tvExpenseType = rootView.findViewById(id);
      if (tvExpenseType == null) {
        break missingId;
      }

      return new ExpenseListRowBinding((LinearLayout) rootView, expenseLinearLayout,
          tvExpenseAmount, tvExpenseAmountApproved, tvExpenseDateTime, tvExpenseNote,
          tvExpenseStatus, tvExpenseType);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
