// Generated by data binding compiler. Do not edit!
package com.utkarshnew.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.makeramen.roundedimageview.RoundedImageView;
import com.utkarshnew.android.R;
import com.utkarshnew.android.feeds.adapters.Banner_ViewPager;
import com.utkarshnew.android.feeds.dataclass.BannerData;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class BannerAdapterImageviewBinding extends ViewDataBinding {
  @NonNull
  public final RoundedImageView imgeView;

  @Bindable
  protected BannerData mBannerdata;

  @Bindable
  protected Banner_ViewPager mBannerviewadapter;

  protected BannerAdapterImageviewBinding(Object _bindingComponent, View _root,
      int _localFieldCount, RoundedImageView imgeView) {
    super(_bindingComponent, _root, _localFieldCount);
    this.imgeView = imgeView;
  }

  public abstract void setBannerdata(@Nullable BannerData bannerdata);

  @Nullable
  public BannerData getBannerdata() {
    return mBannerdata;
  }

  public abstract void setBannerviewadapter(@Nullable Banner_ViewPager bannerviewadapter);

  @Nullable
  public Banner_ViewPager getBannerviewadapter() {
    return mBannerviewadapter;
  }

  @NonNull
  public static BannerAdapterImageviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.banner_adapter_imageview, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static BannerAdapterImageviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<BannerAdapterImageviewBinding>inflateInternal(inflater, R.layout.banner_adapter_imageview, root, attachToRoot, component);
  }

  @NonNull
  public static BannerAdapterImageviewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.banner_adapter_imageview, null, false, component)
   */
  @NonNull
  @Deprecated
  public static BannerAdapterImageviewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<BannerAdapterImageviewBinding>inflateInternal(inflater, R.layout.banner_adapter_imageview, null, false, component);
  }

  public static BannerAdapterImageviewBinding bind(@NonNull View view) {
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
  public static BannerAdapterImageviewBinding bind(@NonNull View view, @Nullable Object component) {
    return (BannerAdapterImageviewBinding)bind(component, view, R.layout.banner_adapter_imageview);
  }
}