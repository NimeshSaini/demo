// Generated by data binding compiler. Do not edit!
package com.utkarshnew.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.address.model.Data;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class AddressAdapterBinding extends ViewDataBinding {
  @NonNull
  public final TextView address;

  @NonNull
  public final RelativeLayout addressItem;

  @NonNull
  public final ImageView delete;

  @NonNull
  public final ImageView edit;

  @NonNull
  public final TextView name;

  @Bindable
  protected Data mAddressadapter;

  protected AddressAdapterBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView address, RelativeLayout addressItem, ImageView delete, ImageView edit,
      TextView name) {
    super(_bindingComponent, _root, _localFieldCount);
    this.address = address;
    this.addressItem = addressItem;
    this.delete = delete;
    this.edit = edit;
    this.name = name;
  }

  public abstract void setAddressadapter(@Nullable Data addressadapter);

  @Nullable
  public Data getAddressadapter() {
    return mAddressadapter;
  }

  @NonNull
  public static AddressAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.address_adapter, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static AddressAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<AddressAdapterBinding>inflateInternal(inflater, R.layout.address_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static AddressAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.address_adapter, null, false, component)
   */
  @NonNull
  @Deprecated
  public static AddressAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<AddressAdapterBinding>inflateInternal(inflater, R.layout.address_adapter, null, false, component);
  }

  public static AddressAdapterBinding bind(@NonNull View view) {
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
  public static AddressAdapterBinding bind(@NonNull View view, @Nullable Object component) {
    return (AddressAdapterBinding)bind(component, view, R.layout.address_adapter);
  }
}
