package com.utkarshnew.android.Intro.Adaoter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\n\u0012\b\u0018\u00010\u0002R\u00020\u00000\u0001:\u0001%B=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\b\u0010\u001c\u001a\u00020\u000eH\u0016J\u001e\u0010\u001d\u001a\u00020\u001e2\f\b\u0001\u0010\u001f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010 \u001a\u00020\u000eH\u0016J\u001e\u0010!\u001a\u00060\u0002R\u00020\u00002\b\b\u0001\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u000eH\u0017R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006&"}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter$SubjectItemHolder;", "context", "Landroid/content/Context;", "maincatlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Intro/SubCat;", "Lkotlin/collections/ArrayList;", "itemSelected", "Lcom/utkarshnew/android/Intro/ItemSelected;", "categoryAdapter", "Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "catpos", "", "(Landroid/content/Context;Ljava/util/ArrayList;Lcom/utkarshnew/android/Intro/ItemSelected;Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;I)V", "getCategoryAdapter", "()Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "getCatpos", "()I", "getContext", "()Landroid/content/Context;", "getItemSelected", "()Lcom/utkarshnew/android/Intro/ItemSelected;", "setItemSelected", "(Lcom/utkarshnew/android/Intro/ItemSelected;)V", "getMaincatlist", "()Ljava/util/ArrayList;", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "SubjectItemHolder", "app_debug"})
public final class MainCatAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.Intro.Adaoter.MainCatAdapter.SubjectItemHolder> {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist = null;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Intro.ItemSelected itemSelected;
    @org.jetbrains.annotations.NotNull()
    private final com.utkarshnew.android.Intro.Adaoter.CategoryAdapter categoryAdapter = null;
    private final int catpos = 0;
    
    public MainCatAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.ItemSelected itemSelected, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.Adaoter.CategoryAdapter categoryAdapter, int catpos) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> getMaincatlist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Intro.ItemSelected getItemSelected() {
        return null;
    }
    
    public final void setItemSelected(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.ItemSelected p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Intro.Adaoter.CategoryAdapter getCategoryAdapter() {
        return null;
    }
    
    public final int getCatpos() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    @java.lang.Override()
    public com.utkarshnew.android.Intro.Adaoter.MainCatAdapter.SubjectItemHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    com.utkarshnew.android.Intro.Adaoter.MainCatAdapter.SubjectItemHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J&\u0010\u000f\u001a\u00020\u00102\u0016\u0010\u0011\u001a\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter$SubjectItemHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter;Landroid/view/View;)V", "category_count", "Landroid/widget/TextView;", "category_name", "dropDownIV", "Landroid/widget/ImageView;", "parentLL", "Landroid/widget/LinearLayout;", "sub_cat_recycler", "Landroidx/recyclerview/widget/RecyclerView;", "view", "setSingleFAQData", "", "maincatlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Intro/SubCat;", "Lkotlin/collections/ArrayList;", "pos", "", "app_debug"})
    public final class SubjectItemHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final android.widget.TextView category_name = null;
        private final android.widget.ImageView dropDownIV = null;
        private final android.widget.LinearLayout parentLL = null;
        private final android.view.View view = null;
        private final android.widget.TextView category_count = null;
        private final androidx.recyclerview.widget.RecyclerView sub_cat_recycler = null;
        
        public SubjectItemHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void setSingleFAQData(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist, int pos) {
        }
    }
}