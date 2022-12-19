package com.utkarshnew.android.feeds.dataclass;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 2, d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0007\u001a\u001a\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0007H\u0007\u001a\u001a\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007\u001a\u001a\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\nH\u0007\u001a\u001a\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000e2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007\u001a\u001a\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u000f2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007\u001a\u001a\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0007\u001a\u001a\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0007\u001a\u001e\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018H\u0007\u001a\u001e\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0018H\u0007\u001a\u001e\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\u0018H\u0007\u001a\u001a\u0010 \u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\f2\b\u0010!\u001a\u0004\u0018\u00010\nH\u0007\u00a8\u0006\""}, d2 = {"imagedetailsfeedPost", "", "view", "Landroid/widget/ImageView;", "data", "Lcom/utkarshnew/android/table/PostDataTable;", "imagefeedPost", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "imagepost", "url", "", "loadImage", "Landroid/widget/TextView;", "date", "Lcom/makeramen/roundedimageview/RoundedImageView;", "Lde/hdodenhof/circleimageview/CircleImageView;", "setAdapter", "recyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "optionAdapter", "Lcom/utkarshnew/android/feeds/adapters/OptionAdapter;", "optionWebAdapter", "Lcom/utkarshnew/android/feeds/adapters/OptionWebAdapter;", "testResult", "", "Lcom/utkarshnew/android/feeds/dataclass/TestResult;", "setliveclassadapter", "liveclasslist", "Lcom/utkarshnew/android/home/liveclasses/Datum;", "setlivetestadapter", "liveTest", "Lcom/utkarshnew/android/home/livetest/LiveTestData;", "viewlike", "mylike", "app_debug"})
public final class DataKt {
    
    @androidx.databinding.BindingAdapter(value = {"imageUrl"})
    public static final void loadImage(@org.jetbrains.annotations.NotNull()
    de.hdodenhof.circleimageview.CircleImageView view, @org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"optionadapter"})
    public static final void setAdapter(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recyclerView, @org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.adapters.OptionAdapter optionAdapter) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"optionwebadapter"})
    public static final void setAdapter(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recyclerView, @org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.adapters.OptionWebAdapter optionWebAdapter) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"livetestresult"})
    public static final void setAdapter(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recyclerView, @org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.feeds.dataclass.TestResult> testResult) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"livetest"})
    public static final void setlivetestadapter(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recyclerView, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.utkarshnew.android.home.livetest.LiveTestData> liveTest) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"liveclass"})
    public static final void setliveclassadapter(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView recyclerView, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.utkarshnew.android.home.liveclasses.Datum> liveclasslist) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"linkimage"})
    public static final void loadImage(@org.jetbrains.annotations.NotNull()
    com.makeramen.roundedimageview.RoundedImageView view, @org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"likeview"})
    public static final void viewlike(@org.jetbrains.annotations.NotNull()
    android.widget.TextView view, @org.jetbrains.annotations.Nullable()
    java.lang.String mylike) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"imageposturl"})
    public static final void imagepost(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView view, @org.jetbrains.annotations.Nullable()
    java.lang.String url) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"imagefeedPost"})
    public static final void imagefeedPost(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView view, @org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.dataclass.Data data) {
    }
    
    @androidx.databinding.BindingAdapter(value = {"imagedetailsfeedPost"})
    public static final void imagedetailsfeedPost(@org.jetbrains.annotations.NotNull()
    android.widget.ImageView view, @org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.table.PostDataTable data) {
    }
    
    @android.annotation.SuppressLint(value = {"SimpleDateFormat"})
    @androidx.databinding.BindingAdapter(value = {"date"})
    public static final void loadImage(@org.jetbrains.annotations.NotNull()
    android.widget.TextView view, @org.jetbrains.annotations.Nullable()
    java.lang.String date) {
    }
}