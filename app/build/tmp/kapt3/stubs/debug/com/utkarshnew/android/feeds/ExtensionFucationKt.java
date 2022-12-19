package com.utkarshnew.android.feeds;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 2, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u0004\u0018\u00010\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u0016\u0010\u0004\u001a\u00020\u0005*\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\u0001H\u0007\u001a\u001c\u0010\b\u001a\u00020\u0005*\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bH\u0007\u001a\u0014\u0010\r\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\r\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\u000e\u001a\u00020\u0005*\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0001H\u0007\u001a\u0016\u0010\u0011\u001a\u00020\u0005*\u00020\u00062\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u0007\u001a\u001a\u0010\u0012\u001a\u00020\u0005*\u00020\u00132\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00140\u000bH\u0007\u001a\u0016\u0010\u0015\u001a\u00020\u0005*\u00020\u00162\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0007\u001a\u0014\u0010\u0017\u001a\u00020\u0005*\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0001H\u0007\u001a\u0012\u0010\u001a\u001a\u00020\u0005*\u00020\u001b2\u0006\u0010\u0019\u001a\u00020\u0001\u001a\u0014\u0010\u001c\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u001d\u001a\u00020\u0001H\u0007\u001a\u0014\u0010\u001e\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u001f\u001a\u00020 H\u0007\u00a8\u0006!"}, d2 = {"formatNumber", "", "count", "", "attempts", "", "Landroid/widget/TextView;", "attempt", "courseadapter", "Landroidx/recyclerview/widget/RecyclerView;", "data", "", "Lcom/utkarshnew/android/feeds/dataclass/NewCourseData;", "date", "imageurl", "Landroid/widget/ImageView;", "url", "like", "load", "Landroidx/viewpager/widget/ViewPager;", "Lcom/utkarshnew/android/feeds/dataclass/BannerData;", "loadImage", "Lcom/makeramen/roundedimageview/RoundedImageView;", "setwebview", "Lcom/ahmadnemati/clickablewebview/ClickableWebView;", "message", "showToast", "Landroid/content/Context;", "transactionstatus", "status", "viewVisible", "commentdata", "Lcom/utkarshnew/android/feeds/dataclass/comment/Data;", "app_debug"})
public final class ExtensionFucationKt {
    
    public static final void showToast(@org.jetbrains.annotations.NotNull()
    android.content.Context $this$showToast, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    @android.annotation.SuppressLint(value = {"SetJavaScriptEnabled"})
    @androidx.databinding.BindingAdapter(value = {"setwebview"})
    public static final void setwebview(@org.jetbrains.annotations.NotNull()
    com.ahmadnemati.clickablewebview.ClickableWebView $this$setwebview, @org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"coursedata"})
    public static final void courseadapter(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView $this$courseadapter, @org.jetbrains.annotations.Nullable()
    java.util.List<com.utkarshnew.android.feeds.dataclass.NewCourseData> data) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"viewpager"})
    public static final void load(@org.jetbrains.annotations.NotNull()
    androidx.viewpager.widget.ViewPager $this$load, @org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.feeds.dataclass.BannerData> data) {
    }
    
    @android.annotation.SuppressLint(value = {"SimpleDateFormat"})
    @androidx.databinding.BindingAdapter(value = {"datecomment"})
    public static final void date(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$date, @org.jetbrains.annotations.NotNull()
    java.lang.String date) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"attempts"})
    public static final void attempts(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$attempts, @org.jetbrains.annotations.Nullable()
    java.lang.String attempt) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"like"})
    public static final void like(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$like, @org.jetbrains.annotations.Nullable()
    java.lang.String date) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public static final java.lang.String formatNumber(long count) {
        return null;
    }
    
    @androidx.databinding.BindingAdapter(value = {"viewVisbile"})
    public static final void viewVisible(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$viewVisible, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.feeds.dataclass.comment.Data commentdata) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"coruseimage"})
    public static final void loadImage(@org.jetbrains.annotations.NotNull()
    com.makeramen.roundedimageview.RoundedImageView $this$loadImage, @org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"imageurl"})
    public static final void imageurl(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView $this$imageurl, @org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"transactionstatus"})
    public static final void transactionstatus(@org.jetbrains.annotations.NotNull()
    android.widget.TextView $this$transactionstatus, @org.jetbrains.annotations.NotNull()
    java.lang.String status) {
    }
}