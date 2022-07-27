// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentDueBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView addExpenseImage;

  @NonNull
  public final Button datePickerButton;

  @NonNull
  public final EditText depositAmount;

  @NonNull
  public final LinearLayout depositTotalLayout;

  @NonNull
  public final EditText dueAmount;

  @NonNull
  public final EditText dueDescription;

  @NonNull
  public final LinearLayout dueLayout;

  @NonNull
  public final RelativeLayout dueLayoutId;

  @NonNull
  public final TextView dueMobileNumber;

  @NonNull
  public final TextView dueName;

  @NonNull
  public final EditText paidAmount;

  @NonNull
  public final RadioButton radioCustomer;

  @NonNull
  public final RadioGroup radioGroupId;

  @NonNull
  public final RadioButton radioSupplier;

  @NonNull
  public final Button savebtnId;

  @NonNull
  public final Spinner spinner3;

  @NonNull
  public final TextView textView;

  @NonNull
  public final LinearLayout totalDueAmountId;

  private FragmentDueBinding(@NonNull RelativeLayout rootView, @NonNull ImageView addExpenseImage,
      @NonNull Button datePickerButton, @NonNull EditText depositAmount,
      @NonNull LinearLayout depositTotalLayout, @NonNull EditText dueAmount,
      @NonNull EditText dueDescription, @NonNull LinearLayout dueLayout,
      @NonNull RelativeLayout dueLayoutId, @NonNull TextView dueMobileNumber,
      @NonNull TextView dueName, @NonNull EditText paidAmount, @NonNull RadioButton radioCustomer,
      @NonNull RadioGroup radioGroupId, @NonNull RadioButton radioSupplier,
      @NonNull Button savebtnId, @NonNull Spinner spinner3, @NonNull TextView textView,
      @NonNull LinearLayout totalDueAmountId) {
    this.rootView = rootView;
    this.addExpenseImage = addExpenseImage;
    this.datePickerButton = datePickerButton;
    this.depositAmount = depositAmount;
    this.depositTotalLayout = depositTotalLayout;
    this.dueAmount = dueAmount;
    this.dueDescription = dueDescription;
    this.dueLayout = dueLayout;
    this.dueLayoutId = dueLayoutId;
    this.dueMobileNumber = dueMobileNumber;
    this.dueName = dueName;
    this.paidAmount = paidAmount;
    this.radioCustomer = radioCustomer;
    this.radioGroupId = radioGroupId;
    this.radioSupplier = radioSupplier;
    this.savebtnId = savebtnId;
    this.spinner3 = spinner3;
    this.textView = textView;
    this.totalDueAmountId = totalDueAmountId;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentDueBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentDueBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_due, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentDueBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.add_expense_image;
      ImageView addExpenseImage = rootView.findViewById(id);
      if (addExpenseImage == null) {
        break missingId;
      }

      id = R.id.datePickerButton;
      Button datePickerButton = rootView.findViewById(id);
      if (datePickerButton == null) {
        break missingId;
      }

      id = R.id.depositAmount;
      EditText depositAmount = rootView.findViewById(id);
      if (depositAmount == null) {
        break missingId;
      }

      id = R.id.depositTotalLayout;
      LinearLayout depositTotalLayout = rootView.findViewById(id);
      if (depositTotalLayout == null) {
        break missingId;
      }

      id = R.id.dueAmount;
      EditText dueAmount = rootView.findViewById(id);
      if (dueAmount == null) {
        break missingId;
      }

      id = R.id.dueDescription;
      EditText dueDescription = rootView.findViewById(id);
      if (dueDescription == null) {
        break missingId;
      }

      id = R.id.dueLayout;
      LinearLayout dueLayout = rootView.findViewById(id);
      if (dueLayout == null) {
        break missingId;
      }

      RelativeLayout dueLayoutId = (RelativeLayout) rootView;

      id = R.id.dueMobileNumber;
      TextView dueMobileNumber = rootView.findViewById(id);
      if (dueMobileNumber == null) {
        break missingId;
      }

      id = R.id.dueName;
      TextView dueName = rootView.findViewById(id);
      if (dueName == null) {
        break missingId;
      }

      id = R.id.paidAmount;
      EditText paidAmount = rootView.findViewById(id);
      if (paidAmount == null) {
        break missingId;
      }

      id = R.id.radioCustomer;
      RadioButton radioCustomer = rootView.findViewById(id);
      if (radioCustomer == null) {
        break missingId;
      }

      id = R.id.radioGroupId;
      RadioGroup radioGroupId = rootView.findViewById(id);
      if (radioGroupId == null) {
        break missingId;
      }

      id = R.id.radioSupplier;
      RadioButton radioSupplier = rootView.findViewById(id);
      if (radioSupplier == null) {
        break missingId;
      }

      id = R.id.savebtnId;
      Button savebtnId = rootView.findViewById(id);
      if (savebtnId == null) {
        break missingId;
      }

      id = R.id.spinner3;
      Spinner spinner3 = rootView.findViewById(id);
      if (spinner3 == null) {
        break missingId;
      }

      id = R.id.textView;
      TextView textView = rootView.findViewById(id);
      if (textView == null) {
        break missingId;
      }

      id = R.id.totalDueAmountId;
      LinearLayout totalDueAmountId = rootView.findViewById(id);
      if (totalDueAmountId == null) {
        break missingId;
      }

      return new FragmentDueBinding((RelativeLayout) rootView, addExpenseImage, datePickerButton,
          depositAmount, depositTotalLayout, dueAmount, dueDescription, dueLayout, dueLayoutId,
          dueMobileNumber, dueName, paidAmount, radioCustomer, radioGroupId, radioSupplier,
          savebtnId, spinner3, textView, totalDueAmountId);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}