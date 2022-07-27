// Generated by view binder compiler. Do not edit!
package com.inovex.bikroyik.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.inovex.bikroyik.R;
import com.jaredrummler.materialspinner.MaterialSpinner;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class NewMessagingDialogBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final EditText etMessageBody;

  @NonNull
  public final EditText etMessageSubject;

  @NonNull
  public final MaterialSpinner sendMessageSpinner;

  @NonNull
  public final TextView tvSendCancel;

  @NonNull
  public final TextView tvSendMessage;

  private NewMessagingDialogBinding(@NonNull LinearLayout rootView, @NonNull EditText etMessageBody,
      @NonNull EditText etMessageSubject, @NonNull MaterialSpinner sendMessageSpinner,
      @NonNull TextView tvSendCancel, @NonNull TextView tvSendMessage) {
    this.rootView = rootView;
    this.etMessageBody = etMessageBody;
    this.etMessageSubject = etMessageSubject;
    this.sendMessageSpinner = sendMessageSpinner;
    this.tvSendCancel = tvSendCancel;
    this.tvSendMessage = tvSendMessage;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static NewMessagingDialogBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static NewMessagingDialogBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.new_messaging_dialog, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static NewMessagingDialogBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.etMessageBody;
      EditText etMessageBody = rootView.findViewById(id);
      if (etMessageBody == null) {
        break missingId;
      }

      id = R.id.etMessageSubject;
      EditText etMessageSubject = rootView.findViewById(id);
      if (etMessageSubject == null) {
        break missingId;
      }

      id = R.id.sendMessageSpinner;
      MaterialSpinner sendMessageSpinner = rootView.findViewById(id);
      if (sendMessageSpinner == null) {
        break missingId;
      }

      id = R.id.tvSendCancel;
      TextView tvSendCancel = rootView.findViewById(id);
      if (tvSendCancel == null) {
        break missingId;
      }

      id = R.id.tvSendMessage;
      TextView tvSendMessage = rootView.findViewById(id);
      if (tvSendMessage == null) {
        break missingId;
      }

      return new NewMessagingDialogBinding((LinearLayout) rootView, etMessageBody, etMessageSubject,
          sendMessageSpinner, tvSendCancel, tvSendMessage);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}