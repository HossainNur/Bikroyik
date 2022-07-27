// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPaymentBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView addExpenseImage;

  @NonNull
  public final Button btPickDate;

  @NonNull
  public final Button datePickerButton;

  @NonNull
  public final Button employeeBtn;

  @NonNull
  public final TextView employeeSelect;

  @NonNull
  public final TextView employeeText;

  @NonNull
  public final EditText etExpenseAmount;

  @NonNull
  public final EditText etExpenseNote;

  @NonNull
  public final TextView expenseAmount;

  @NonNull
  public final TextView expenseAmount1;

  @NonNull
  public final TextView expenseNote;

  @NonNull
  public final TextView expenseNote1;

  @NonNull
  public final Button savebtnId;

  @NonNull
  public final TextView textView;

  @NonNull
  public final ToolbarPaymentBinding toolbar1;

  @NonNull
  public final TextView tvDate;

  private ActivityPaymentBinding(@NonNull RelativeLayout rootView,
      @NonNull ImageView addExpenseImage, @NonNull Button btPickDate,
      @NonNull Button datePickerButton, @NonNull Button employeeBtn,
      @NonNull TextView employeeSelect, @NonNull TextView employeeText,
      @NonNull EditText etExpenseAmount, @NonNull EditText etExpenseNote,
      @NonNull TextView expenseAmount, @NonNull TextView expenseAmount1,
      @NonNull TextView expenseNote, @NonNull TextView expenseNote1, @NonNull Button savebtnId,
      @NonNull TextView textView, @NonNull ToolbarPaymentBinding toolbar1,
      @NonNull TextView tvDate) {
    this.rootView = rootView;
    this.addExpenseImage = addExpenseImage;
    this.btPickDate = btPickDate;
    this.datePickerButton = datePickerButton;
    this.employeeBtn = employeeBtn;
    this.employeeSelect = employeeSelect;
    this.employeeText = employeeText;
    this.etExpenseAmount = etExpenseAmount;
    this.etExpenseNote = etExpenseNote;
    this.expenseAmount = expenseAmount;
    this.expenseAmount1 = expenseAmount1;
    this.expenseNote = expenseNote;
    this.expenseNote1 = expenseNote1;
    this.savebtnId = savebtnId;
    this.textView = textView;
    this.toolbar1 = toolbar1;
    this.tvDate = tvDate;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPaymentBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPaymentBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_payment, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPaymentBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_expense_image;
      ImageView addExpenseImage = rootView.findViewById(id);
      if (addExpenseImage == null) {
        break missingId;
      }

      id = R.id.btPickDate;
      Button btPickDate = rootView.findViewById(id);
      if (btPickDate == null) {
        break missingId;
      }

      id = R.id.datePickerButton;
      Button datePickerButton = rootView.findViewById(id);
      if (datePickerButton == null) {
        break missingId;
      }

      id = R.id.employeeBtn;
      Button employeeBtn = rootView.findViewById(id);
      if (employeeBtn == null) {
        break missingId;
      }

      id = R.id.employeeSelect;
      TextView employeeSelect = rootView.findViewById(id);
      if (employeeSelect == null) {
        break missingId;
      }

      id = R.id.employeeText;
      TextView employeeText = rootView.findViewById(id);
      if (employeeText == null) {
        break missingId;
      }

      id = R.id.etExpenseAmount;
      EditText etExpenseAmount = rootView.findViewById(id);
      if (etExpenseAmount == null) {
        break missingId;
      }

      id = R.id.etExpenseNote;
      EditText etExpenseNote = rootView.findViewById(id);
      if (etExpenseNote == null) {
        break missingId;
      }

      id = R.id.expenseAmount;
      TextView expenseAmount = rootView.findViewById(id);
      if (expenseAmount == null) {
        break missingId;
      }

      id = R.id.expense_amount;
      TextView expenseAmount1 = rootView.findViewById(id);
      if (expenseAmount1 == null) {
        break missingId;
      }

      id = R.id.expenseNote;
      TextView expenseNote = rootView.findViewById(id);
      if (expenseNote == null) {
        break missingId;
      }

      id = R.id.expense_note;
      TextView expenseNote1 = rootView.findViewById(id);
      if (expenseNote1 == null) {
        break missingId;
      }

      id = R.id.savebtnId;
      Button savebtnId = rootView.findViewById(id);
      if (savebtnId == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.toolbar1;
      View toolbar1 = rootView.findViewById(id);
      if (toolbar1 == null) {
        break missingId;
      }
      ToolbarPaymentBinding binding_toolbar1 = ToolbarPaymentBinding.bind(toolbar1);

      id = R.id.tvDate;
      TextView tvDate = rootView.findViewById(id);
      if (tvDate == null) {
        break missingId;
      }

      return new ActivityPaymentBinding((RelativeLayout) rootView, addExpenseImage, btPickDate,
          datePickerButton, employeeBtn, employeeSelect, employeeText, etExpenseAmount,
          etExpenseNote, expenseAmount, expenseAmount1, expenseNote, expenseNote1, savebtnId,
          textView, binding_toolbar1, tvDate);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
