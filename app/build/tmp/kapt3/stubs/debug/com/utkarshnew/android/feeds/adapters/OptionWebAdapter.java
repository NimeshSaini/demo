package com.utkarshnew.android.feeds.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001,B5\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u00a2\u0006\u0002\u0010\u000eJ\b\u0010#\u001a\u00020\u000bH\u0016J\u001c\u0010$\u001a\u00020%2\n\u0010&\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\'\u001a\u00020\u000bH\u0017J\u001c\u0010(\u001a\u00060\u0002R\u00020\u00002\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020\u000bH\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"\u00a8\u0006-"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/OptionWebAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/feeds/adapters/OptionWebAdapter$OptionHolder;", "context", "Landroid/content/Context;", "optionList", "", "Lcom/utkarshnew/android/feeds/dataclass/Option;", "optionItem", "Lcom/utkarshnew/android/feeds/OptionItem;", "feedlistPos", "", "json", "Lcom/utkarshnew/android/feeds/dataclass/Json;", "(Landroid/content/Context;Ljava/util/List;Lcom/utkarshnew/android/feeds/OptionItem;ILcom/utkarshnew/android/feeds/dataclass/Json;)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getFeedlistPos", "()I", "setFeedlistPos", "(I)V", "getJson", "()Lcom/utkarshnew/android/feeds/dataclass/Json;", "setJson", "(Lcom/utkarshnew/android/feeds/dataclass/Json;)V", "getOptionItem", "()Lcom/utkarshnew/android/feeds/OptionItem;", "setOptionItem", "(Lcom/utkarshnew/android/feeds/OptionItem;)V", "getOptionList", "()Ljava/util/List;", "setOptionList", "(Ljava/util/List;)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "OptionHolder", "app_debug"})
public final class OptionWebAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.feeds.adapters.OptionWebAdapter.OptionHolder> {
    @org.jetbrains.annotations.NotNull()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.utkarshnew.android.feeds.dataclass.Option> optionList;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.feeds.OptionItem optionItem;
    private int feedlistPos;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.feeds.dataclass.Json json;
    
    public OptionWebAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.feeds.dataclass.Option> optionList, @org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.OptionItem optionItem, int feedlistPos, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.feeds.dataclass.Json json) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    public final void setContext(@org.jetbrains.annotations.NotNull()
    android.content.Context p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.utkarshnew.android.feeds.dataclass.Option> getOptionList() {
        return null;
    }
    
    public final void setOptionList(@org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.feeds.dataclass.Option> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.feeds.OptionItem getOptionItem() {
        return null;
    }
    
    public final void setOptionItem(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.OptionItem p0) {
    }
    
    public final int getFeedlistPos() {
        return 0;
    }
    
    public final void setFeedlistPos(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.feeds.dataclass.Json getJson() {
        return null;
    }
    
    public final void setJson(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.feeds.dataclass.Json p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.utkarshnew.android.feeds.adapters.OptionWebAdapter.OptionHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables", "SetTextI18n", "SetJavaScriptEnabled"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.feeds.adapters.OptionWebAdapter.OptionHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\u0012X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\u00020\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"\u00a8\u0006#"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/OptionWebAdapter$OptionHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/utkarshnew/android/feeds/adapters/OptionWebAdapter;Landroid/view/View;)V", "activeProgress", "Landroid/widget/ProgressBar;", "getActiveProgress", "()Landroid/widget/ProgressBar;", "setActiveProgress", "(Landroid/widget/ProgressBar;)V", "layout_option", "Landroid/widget/RelativeLayout;", "getLayout_option", "()Landroid/widget/RelativeLayout;", "setLayout_option", "(Landroid/widget/RelativeLayout;)V", "optionTextTV", "Lio/github/kexanie/library/MathView;", "getOptionTextTV", "()Lio/github/kexanie/library/MathView;", "setOptionTextTV", "(Lio/github/kexanie/library/MathView;)V", "option_a", "Landroid/widget/ImageView;", "getOption_a", "()Landroid/widget/ImageView;", "setOption_a", "(Landroid/widget/ImageView;)V", "option_txt", "Landroid/widget/TextView;", "getOption_txt", "()Landroid/widget/TextView;", "setOption_txt", "(Landroid/widget/TextView;)V", "app_debug"})
    public final class OptionHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private android.widget.ImageView option_a;
        @org.jetbrains.annotations.NotNull()
        private android.widget.TextView option_txt;
        @org.jetbrains.annotations.NotNull()
        private android.widget.RelativeLayout layout_option;
        @org.jetbrains.annotations.NotNull()
        private io.github.kexanie.library.MathView optionTextTV;
        @org.jetbrains.annotations.NotNull()
        private android.widget.ProgressBar activeProgress;
        
        public OptionHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getOption_a() {
            return null;
        }
        
        public final void setOption_a(@org.jetbrains.annotations.NotNull()
        android.widget.ImageView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getOption_txt() {
            return null;
        }
        
        public final void setOption_txt(@org.jetbrains.annotations.NotNull()
        android.widget.TextView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.RelativeLayout getLayout_option() {
            return null;
        }
        
        public final void setLayout_option(@org.jetbrains.annotations.NotNull()
        android.widget.RelativeLayout p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final io.github.kexanie.library.MathView getOptionTextTV() {
            return null;
        }
        
        public final void setOptionTextTV(@org.jetbrains.annotations.NotNull()
        io.github.kexanie.library.MathView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ProgressBar getActiveProgress() {
            return null;
        }
        
        public final void setActiveProgress(@org.jetbrains.annotations.NotNull()
        android.widget.ProgressBar p0) {
        }
    }
}