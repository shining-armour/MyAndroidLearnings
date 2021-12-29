// Generated by view binder compiler. Do not edit!
package com.example.dogglers.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.example.dogglers.R;
import com.google.android.material.card.MaterialCardView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class VerticalHorizontalListItemBinding implements ViewBinding {
  @NonNull
  private final MaterialCardView rootView;

  @NonNull
  public final View border;

  @NonNull
  public final TextView dogAgeTextView;

  @NonNull
  public final TextView dogHobbiesTextView;

  @NonNull
  public final ImageView dogImageView;

  @NonNull
  public final TextView dogNameTextView;

  private VerticalHorizontalListItemBinding(@NonNull MaterialCardView rootView,
      @NonNull View border, @NonNull TextView dogAgeTextView, @NonNull TextView dogHobbiesTextView,
      @NonNull ImageView dogImageView, @NonNull TextView dogNameTextView) {
    this.rootView = rootView;
    this.border = border;
    this.dogAgeTextView = dogAgeTextView;
    this.dogHobbiesTextView = dogHobbiesTextView;
    this.dogImageView = dogImageView;
    this.dogNameTextView = dogNameTextView;
  }

  @Override
  @NonNull
  public MaterialCardView getRoot() {
    return rootView;
  }

  @NonNull
  public static VerticalHorizontalListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static VerticalHorizontalListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.vertical_horizontal_list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static VerticalHorizontalListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.border;
      View border = rootView.findViewById(id);
      if (border == null) {
        break missingId;
      }

      id = R.id.dogAgeTextView;
      TextView dogAgeTextView = rootView.findViewById(id);
      if (dogAgeTextView == null) {
        break missingId;
      }

      id = R.id.dogHobbiesTextView;
      TextView dogHobbiesTextView = rootView.findViewById(id);
      if (dogHobbiesTextView == null) {
        break missingId;
      }

      id = R.id.dogImageView;
      ImageView dogImageView = rootView.findViewById(id);
      if (dogImageView == null) {
        break missingId;
      }

      id = R.id.dogNameTextView;
      TextView dogNameTextView = rootView.findViewById(id);
      if (dogNameTextView == null) {
        break missingId;
      }

      return new VerticalHorizontalListItemBinding((MaterialCardView) rootView, border,
          dogAgeTextView, dogHobbiesTextView, dogImageView, dogNameTextView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
