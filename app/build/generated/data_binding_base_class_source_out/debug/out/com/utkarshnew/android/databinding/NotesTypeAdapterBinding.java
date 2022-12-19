// Generated by data binding compiler. Do not edit!
package com.utkarshnew.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.courses.modal.NotesType;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class NotesTypeAdapterBinding extends ViewDataBinding {
  @NonNull
  public final ImageView imageView;

  @NonNull
  public final LinearLayout notesLayout;

  @NonNull
  public final TextView physicalNote;

  @Bindable
  protected NotesType mNotestype;

  protected NotesTypeAdapterBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ImageView imageView, LinearLayout notesLayout, TextView physicalNote) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imageView = imageView;
    this.notesLayout = notesLayout;
    this.physicalNote = physicalNote;
  }

  public abstract void setNotestype(@Nullable NotesType notestype);

  @Nullable
  public NotesType getNotestype() {
    return mNotestype;
  }

  @NonNull
  public static NotesTypeAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.notes_type_adapter, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static NotesTypeAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<NotesTypeAdapterBinding>inflateInternal(inflater, R.layout.notes_type_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static NotesTypeAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.notes_type_adapter, null, false, component)
   */
  @NonNull
  @Deprecated
  public static NotesTypeAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<NotesTypeAdapterBinding>inflateInternal(inflater, R.layout.notes_type_adapter, null, false, component);
  }

  public static NotesTypeAdapterBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static NotesTypeAdapterBinding bind(@NonNull View view, @Nullable Object component) {
    return (NotesTypeAdapterBinding)bind(component, view, R.layout.notes_type_adapter);
  }
}
