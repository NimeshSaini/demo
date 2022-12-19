// Generated by data binding compiler. Do not edit!
package com.utkarshnew.android.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.utkarshnew.android.R;
import com.utkarshnew.android.feeds.dataclass.Data;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class NewCourseVmBinding extends ViewDataBinding {
  @NonNull
  public final RecyclerView newCourseRecycler;

  @NonNull
  public final TextView newCourseTxt;

  @NonNull
  public final View view1;

  @NonNull
  public final TextView viewAll;

  @Bindable
  protected Data mCoursedata;

  protected NewCourseVmBinding(Object _bindingComponent, View _root, int _localFieldCount,
      RecyclerView newCourseRecycler, TextView newCourseTxt, View view1, TextView viewAll) {
    super(_bindingComponent, _root, _localFieldCount);
    this.newCourseRecycler = newCourseRecycler;
    this.newCourseTxt = newCourseTxt;
    this.view1 = view1;
    this.viewAll = viewAll;
  }

  public abstract void setCoursedata(@Nullable Data coursedata);

  @Nullable
  public Data getCoursedata() {
    return mCoursedata;
  }

  @NonNull
  public static NewCourseVmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.new_course_vm, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static NewCourseVmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<NewCourseVmBinding>inflateInternal(inflater, R.layout.new_course_vm, root, attachToRoot, component);
  }

  @NonNull
  public static NewCourseVmBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.new_course_vm, null, false, component)
   */
  @NonNull
  @Deprecated
  public static NewCourseVmBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<NewCourseVmBinding>inflateInternal(inflater, R.layout.new_course_vm, null, false, component);
  }

  public static NewCourseVmBinding bind(@NonNull View view) {
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
  public static NewCourseVmBinding bind(@NonNull View view, @Nullable Object component) {
    return (NewCourseVmBinding)bind(component, view, R.layout.new_course_vm);
  }
}