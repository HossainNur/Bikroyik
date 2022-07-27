// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityBakirKhataBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView addButtonText;

  @NonNull
  public final ConstraintLayout addNewContactLayout;

  @NonNull
  public final LinearLayout btnAddCustomer;

  @NonNull
  public final Button btnAllContact;

  @NonNull
  public final Button btnCustomer;

  @NonNull
  public final ImageView btnSearch;

  @NonNull
  public final LinearLayout btnSeeReportLl;

  @NonNull
  public final Button btnSupplier;

  @NonNull
  public final CardView cardView;

  @NonNull
  public final ConstraintLayout dueListHeader;

  @NonNull
  public final RecyclerView dueListRecycler;

  @NonNull
  public final EditText dueListSearch;

  @NonNull
  public final ImageView rightArrow;

  @NonNull
  public final TextView seeReportText;

  @NonNull
  public final TextView text;

  @NonNull
  public final ToolbarBakirKhataBinding toolbar;

  @NonNull
  public final CardView totalDueCard;

  @NonNull
  public final TextView totalDueText;

  @NonNull
  public final TextView tvTotalBaki;

  @NonNull
  public final TextView tvTotalReceive;

  @NonNull
  public final TextView tvTotalReceiveAmount;

  private ActivityBakirKhataBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView addButtonText, @NonNull ConstraintLayout addNewContactLayout,
      @NonNull LinearLayout btnAddCustomer, @NonNull Button btnAllContact,
      @NonNull Button btnCustomer, @NonNull ImageView btnSearch,
      @NonNull LinearLayout btnSeeReportLl, @NonNull Button btnSupplier, @NonNull CardView cardView,
      @NonNull ConstraintLayout dueListHeader, @NonNull RecyclerView dueListRecycler,
      @NonNull EditText dueListSearch, @NonNull ImageView rightArrow,
      @NonNull TextView seeReportText, @NonNull TextView text,
      @NonNull ToolbarBakirKhataBinding toolbar, @NonNull CardView totalDueCard,
      @NonNull TextView totalDueText, @NonNull TextView tvTotalBaki,
      @NonNull TextView tvTotalReceive, @NonNull TextView tvTotalReceiveAmount) {
    this.rootView = rootView;
    this.addButtonText = addButtonText;
    this.addNewContactLayout = addNewContactLayout;
    this.btnAddCustomer = btnAddCustomer;
    this.btnAllContact = btnAllContact;
    this.btnCustomer = btnCustomer;
    this.btnSearch = btnSearch;
    this.btnSeeReportLl = btnSeeReportLl;
    this.btnSupplier = btnSupplier;
    this.cardView = cardView;
    this.dueListHeader = dueListHeader;
    this.dueListRecycler = dueListRecycler;
    this.dueListSearch = dueListSearch;
    this.rightArrow = rightArrow;
    this.seeReportText = seeReportText;
    this.text = text;
    this.toolbar = toolbar;
    this.totalDueCard = totalDueCard;
    this.totalDueText = totalDueText;
    this.tvTotalBaki = tvTotalBaki;
    this.tvTotalReceive = tvTotalReceive;
    this.tvTotalReceiveAmount = tvTotalReceiveAmount;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityBakirKhataBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityBakirKhataBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_bakir_khata, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityBakirKhataBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addButtonText;
      TextView addButtonText = rootView.findViewById(id);
      if (addButtonText == null) {
        break missingId;
      }

      id = R.id.add_newContact_layout;
      ConstraintLayout addNewContactLayout = rootView.findViewById(id);
      if (addNewContactLayout == null) {
        break missingId;
      }

      id = R.id.btn_addCustomer;
      LinearLayout btnAddCustomer = rootView.findViewById(id);
      if (btnAddCustomer == null) {
        break missingId;
      }

      id = R.id.btn_allContact;
      Button btnAllContact = rootView.findViewById(id);
      if (btnAllContact == null) {
        break missingId;
      }

      id = R.id.btn_customer;
      Button btnCustomer = rootView.findViewById(id);
      if (btnCustomer == null) {
        break missingId;
      }

      id = R.id.btn_search;
      ImageView btnSearch = rootView.findViewById(id);
      if (btnSearch == null) {
        break missingId;
      }

      id = R.id.btn_seeReport_ll;
      LinearLayout btnSeeReportLl = rootView.findViewById(id);
      if (btnSeeReportLl == null) {
        break missingId;
      }

      id = R.id.btn_supplier;
      Button btnSupplier = rootView.findViewById(id);
      if (btnSupplier == null) {
        break missingId;
      }

      id = R.id.cardView;
      CardView cardView = rootView.findViewById(id);
      if (cardView == null) {
        break missingId;
      }

      id = R.id.dueList_header;
      ConstraintLayout dueListHeader = rootView.findViewById(id);
      if (dueListHeader == null) {
        break missingId;
      }

      id = R.id.dueList_recycler;
      RecyclerView dueListRecycler = rootView.findViewById(id);
      if (dueListRecycler == null) {
        break missingId;
      }

      id = R.id.dueListSearch;
      EditText dueListSearch = rootView.findViewById(id);
      if (dueListSearch == null) {
        break missingId;
      }

      id = R.id.rightArrow;
      ImageView rightArrow = rootView.findViewById(id);
      if (rightArrow == null) {
        break missingId;
      }

      id = R.id.seeReportText;
      TextView seeReportText = rootView.findViewById(id);
      if (seeReportText == null) {
        break missingId;
      }

      id = R.id.text;
      TextView text = rootView.findViewById(id);
      if (text == null) {
        break missingId;
      }

      id = R.id.toolbar;
      View toolbar = rootView.findViewById(id);
      if (toolbar == null) {
        break missingId;
      }
      ToolbarBakirKhataBinding binding_toolbar = ToolbarBakirKhataBinding.bind(toolbar);

      id = R.id.total_due_card;
      CardView totalDueCard = rootView.findViewById(id);
      if (totalDueCard == null) {
        break missingId;
      }

      id = R.id.totalDueText;
      TextView totalDueText = rootView.findViewById(id);
      if (totalDueText == null) {
        break missingId;
      }

      id = R.id.tv_totalBaki;
      TextView tvTotalBaki = rootView.findViewById(id);
      if (tvTotalBaki == null) {
        break missingId;
      }

      id = R.id.tv_totalReceive;
      TextView tvTotalReceive = rootView.findViewById(id);
      if (tvTotalReceive == null) {
        break missingId;
      }

      id = R.id.tv_totalReceiveAmount;
      TextView tvTotalReceiveAmount = rootView.findViewById(id);
      if (tvTotalReceiveAmount == null) {
        break missingId;
      }

      return new ActivityBakirKhataBinding((ConstraintLayout) rootView, addButtonText,
          addNewContactLayout, btnAddCustomer, btnAllContact, btnCustomer, btnSearch,
          btnSeeReportLl, btnSupplier, cardView, dueListHeader, dueListRecycler, dueListSearch,
          rightArrow, seeReportText, text, binding_toolbar, totalDueCard, totalDueText, tvTotalBaki,
          tvTotalReceive, tvTotalReceiveAmount);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
