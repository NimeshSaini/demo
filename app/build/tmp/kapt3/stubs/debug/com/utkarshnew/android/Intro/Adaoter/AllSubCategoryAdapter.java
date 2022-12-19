package com.utkarshnew.android.Intro.Adaoter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001-B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\b\u0010$\u001a\u00020\u001bH\u0016J\u001c\u0010%\u001a\u00020&2\n\u0010\'\u001a\u00060\u0002R\u00020\u00002\u0006\u0010(\u001a\u00020\u001bH\u0017J\u001c\u0010)\u001a\u00060\u0002R\u00020\u00002\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u001bH\u0016R\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R*\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#\u00a8\u0006."}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter$CategoryAdapterVh;", "context", "Landroid/content/Context;", "mastercatlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Intro/SubCat;", "Lkotlin/collections/ArrayList;", "subCatItemSelected", "Lcom/utkarshnew/android/Intro/SubCatItemSelected;", "(Landroid/content/Context;Ljava/util/ArrayList;Lcom/utkarshnew/android/Intro/SubCatItemSelected;)V", "colorchnage", "", "getColorchnage", "()Z", "setColorchnage", "(Z)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getMastercatlist", "()Ljava/util/ArrayList;", "setMastercatlist", "(Ljava/util/ArrayList;)V", "randomnumber", "", "getRandomnumber", "()I", "setRandomnumber", "(I)V", "getSubCatItemSelected", "()Lcom/utkarshnew/android/Intro/SubCatItemSelected;", "setSubCatItemSelected", "(Lcom/utkarshnew/android/Intro/SubCatItemSelected;)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "CategoryAdapterVh", "app_debug"})
public final class AllSubCategoryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter.CategoryAdapterVh> {
    @org.jetbrains.annotations.NotNull()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> mastercatlist;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Intro.SubCatItemSelected subCatItemSelected;
    private int randomnumber = 0;
    private boolean colorchnage = false;
    
    public AllSubCategoryAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> mastercatlist, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.SubCatItemSelected subCatItemSelected) {
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
    public final java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> getMastercatlist() {
        return null;
    }
    
    public final void setMastercatlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Intro.SubCatItemSelected getSubCatItemSelected() {
        return null;
    }
    
    public final void setSubCatItemSelected(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.SubCatItemSelected p0) {
    }
    
    public final int getRandomnumber() {
        return 0;
    }
    
    public final void setRandomnumber(int p0) {
    }
    
    public final boolean getColorchnage() {
        return false;
    }
    
    public final void setColorchnage(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter.CategoryAdapterVh onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter.CategoryAdapterVh holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter$CategoryAdapterVh;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;Landroid/view/View;)V", "category_name", "Landroid/widget/TextView;", "getCategory_name", "()Landroid/widget/TextView;", "setCategory_name", "(Landroid/widget/TextView;)V", "school_cat", "Landroid/widget/RelativeLayout;", "getSchool_cat", "()Landroid/widget/RelativeLayout;", "setSchool_cat", "(Landroid/widget/RelativeLayout;)V", "app_debug"})
    public final class CategoryAdapterVh extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private android.widget.RelativeLayout school_cat;
        @org.jetbrains.annotations.NotNull()
        private android.widget.TextView category_name;
        
        public CategoryAdapterVh(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.RelativeLayout getSchool_cat() {
            return null;
        }
        
        public final void setSchool_cat(@org.jetbrains.annotations.NotNull()
        android.widget.RelativeLayout p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getCategory_name() {
            return null;
        }
        
        public final void setCategory_name(@org.jetbrains.annotations.NotNull()
        android.widget.TextView p0) {
        }
    }
}