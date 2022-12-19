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
import androidx.viewpager.widget.ViewPager;
import com.utkarshnew.android.R;
import com.utkarshnew.android.feeds.dataclass.Data;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class BannerViewBinding extends ViewDataBinding {
  @NonNull
  public final ViewPager viewPager;

  @Bindable
  protected Data mBannerdata;

  protected BannerViewBinding(Object _bindingComponent, View _root, int _localFieldCount,
      ViewPager viewPager) {
    super(_bindingComponent, _root, _localFieldCount);
    this.viewPager = viewPager;
  }

  public abstract void setBannerdata(@Nullable Data bannerdata);

  @Nullable
  public Data getBannerdata() {
    return mBannerdata;
  }

  @NonNull
  public static BannerViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.banner_view, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static BannerViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<BannerViewBinding>inflateInternal(inflater, R.layout.banner_view, root, attachToRoot, component);
  }

  @NonNull
  public static BannerViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.banner_view, null, false, component)
   */
  @NonNull
  @Deprecated
  public static BannerViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<BannerViewBinding>inflateInternal(inflater, R.layout.banner_view, null, false, component);
  }

  public static BannerViewBinding bind(@NonNull View view) {
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
  public static BannerViewBinding bind(@NonNull View view, @Nullable Object component) {
    return (BannerViewBinding)bind(component, view, R.layout.banner_view);
  }
}