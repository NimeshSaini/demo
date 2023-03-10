// Generated by data binding compiler. Do not edit!
package com.utkarshnew.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.makeramen.roundedimageview.RoundedImageView;
import com.utkarshnew.android.R;
import com.utkarshnew.android.home.livetest.LiveTestData;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class FeedLiveTestAdapterBinding extends ViewDataBinding {
  @NonNull
  public final TextView courseName;

  @NonNull
  public final RoundedImageView imageTest;

  @NonNull
  public final RelativeLayout layoutTestInfo;

  @NonNull
  public final RelativeLayout parentLayout;

  @NonNull
  public final TextView testName;

  @NonNull
  public final TextView time;

  @Bindable
  protected LiveTestData mLivetestdata;

  protected FeedLiveTestAdapterBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView courseName, RoundedImageView imageTest, RelativeLayout layoutTestInfo,
      RelativeLayout parentLayout, TextView testName, TextView time) {
    super(_bindingComponent, _root, _localFieldCount);
    this.courseName = courseName;
    this.imageTest = imageTest;
    this.layoutTestInfo = layoutTestInfo;
    this.parentLayout = parentLayout;
    this.testName = testName;
    this.time = time;
  }

  public abstract void setLivetestdata(@Nullable LiveTestData livetestdata);

  @Nullable
  public LiveTestData getLivetestdata() {
    return mLivetestdata;
  }

  @NonNull
  public static FeedLiveTestAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.feed_live_test_adapter, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static FeedLiveTestAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<FeedLiveTestAdapterBinding>inflateInternal(inflater, R.layout.feed_live_test_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static FeedLiveTestAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.feed_live_test_adapter, null, false, component)
   */
  @NonNull
  @Deprecated
  public static FeedLiveTestAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<FeedLiveTestAdapterBinding>inflateInternal(inflater, R.layout.feed_live_test_adapter, null, false, component);
  }

  public static FeedLiveTestAdapterBinding bind(@NonNull View view) {
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
  public static FeedLiveTestAdapterBinding bind(@NonNull View view, @Nullable Object component) {
    return (FeedLiveTestAdapterBinding)bind(component, view, R.layout.feed_live_test_adapter);
  }
}
