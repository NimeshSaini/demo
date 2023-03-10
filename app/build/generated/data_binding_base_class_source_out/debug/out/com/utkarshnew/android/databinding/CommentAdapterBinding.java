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
import com.utkarshnew.android.R;
import com.utkarshnew.android.feeds.dataclass.comment.Data;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class CommentAdapterBinding extends ViewDataBinding {
  @NonNull
  public final TextView approval;

  @NonNull
  public final RelativeLayout cvrRight;

  @NonNull
  public final CircleImageView profileImage2;

  @NonNull
  public final TextView rightmessage;

  @NonNull
  public final TextView righttexttime;

  @NonNull
  public final TextView userNameright;

  @Bindable
  protected Data mCommentdata;

  protected CommentAdapterBinding(Object _bindingComponent, View _root, int _localFieldCount,
      TextView approval, RelativeLayout cvrRight, CircleImageView profileImage2,
      TextView rightmessage, TextView righttexttime, TextView userNameright) {
    super(_bindingComponent, _root, _localFieldCount);
    this.approval = approval;
    this.cvrRight = cvrRight;
    this.profileImage2 = profileImage2;
    this.rightmessage = rightmessage;
    this.righttexttime = righttexttime;
    this.userNameright = userNameright;
  }

  public abstract void setCommentdata(@Nullable Data commentdata);

  @Nullable
  public Data getCommentdata() {
    return mCommentdata;
  }

  @NonNull
  public static CommentAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.comment_adapter, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static CommentAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<CommentAdapterBinding>inflateInternal(inflater, R.layout.comment_adapter, root, attachToRoot, component);
  }

  @NonNull
  public static CommentAdapterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.comment_adapter, null, false, component)
   */
  @NonNull
  @Deprecated
  public static CommentAdapterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<CommentAdapterBinding>inflateInternal(inflater, R.layout.comment_adapter, null, false, component);
  }

  public static CommentAdapterBinding bind(@NonNull View view) {
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
  public static CommentAdapterBinding bind(@NonNull View view, @Nullable Object component) {
    return (CommentAdapterBinding)bind(component, view, R.layout.comment_adapter);
  }
}
