package com.utkarshnew.android.Intro.Adaoter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u000e\u0012\n\u0012\b\u0018\u00010\u0002R\u00020\u00000\u0001:\u0001-BM\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\u000e\u0012\u0006\u0010\u000f\u001a\u00020\u0010\u0012\u0006\u0010\u0011\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0012J\b\u0010$\u001a\u00020\fH\u0016J\u001e\u0010%\u001a\u00020&2\f\b\u0001\u0010\'\u001a\u00060\u0002R\u00020\u00002\u0006\u0010(\u001a\u00020\fH\u0016J\u001e\u0010)\u001a\u00060\u0002R\u00020\u00002\b\b\u0001\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\fH\u0017R\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0016\"\u0004\b\"\u0010#\u00a8\u0006."}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/SubCatAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/Intro/Adaoter/SubCatAdapter$SubjectItemHolder;", "context", "Landroid/content/Context;", "maincatlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Intro/SubCat;", "Lkotlin/collections/ArrayList;", "itemSelected", "Lcom/utkarshnew/android/Intro/ItemSelected;", "maincatpos", "", "mainCatAdapter", "Lcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter;", "categoryAdapter", "Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "catposition", "(Landroid/content/Context;Ljava/util/ArrayList;Lcom/utkarshnew/android/Intro/ItemSelected;ILcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter;Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;I)V", "getCategoryAdapter", "()Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "getCatposition", "()I", "getContext", "()Landroid/content/Context;", "getItemSelected", "()Lcom/utkarshnew/android/Intro/ItemSelected;", "setItemSelected", "(Lcom/utkarshnew/android/Intro/ItemSelected;)V", "getMainCatAdapter", "()Lcom/utkarshnew/android/Intro/Adaoter/MainCatAdapter;", "getMaincatlist", "()Ljava/util/ArrayList;", "getMaincatpos", "setMaincatpos", "(I)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "SubjectItemHolder", "app_debug"})
public final class SubCatAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.Intro.Adaoter.SubCatAdapter.SubjectItemHolder> {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist = null;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Intro.ItemSelected itemSelected;
    private int maincatpos;
    @org.jetbrains.annotations.NotNull()
    private final com.utkarshnew.android.Intro.Adaoter.MainCatAdapter mainCatAdapter = null;
    @org.jetbrains.annotations.NotNull()
    private final com.utkarshnew.android.Intro.Adaoter.CategoryAdapter categoryAdapter = null;
    private final int catposition = 0;
    
    public SubCatAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.ItemSelected itemSelected, int maincatpos, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.Adaoter.MainCatAdapter mainCatAdapter, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.Adaoter.CategoryAdapter categoryAdapter, int catposition) {
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
    
    public final int getMaincatpos() {
        return 0;
    }
    
    public final void setMaincatpos(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Intro.Adaoter.MainCatAdapter getMainCatAdapter() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Intro.Adaoter.CategoryAdapter getCategoryAdapter() {
        return null;
    }
    
    public final int getCatposition() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    @java.lang.Override()
    public com.utkarshnew.android.Intro.Adaoter.SubCatAdapter.SubjectItemHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    @androidx.annotation.NonNull()
    com.utkarshnew.android.Intro.Adaoter.SubCatAdapter.SubjectItemHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J&\u0010\u000b\u001a\u00020\f2\u0016\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000f0\u000ej\b\u0012\u0004\u0012\u00020\u000f`\u00102\u0006\u0010\u0011\u001a\u00020\u0012R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/SubCatAdapter$SubjectItemHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/utkarshnew/android/Intro/Adaoter/SubCatAdapter;Landroid/view/View;)V", "count", "Landroid/widget/TextView;", "selectCB", "Landroid/widget/CheckBox;", "sub_cat_name", "view", "setSingleFAQData", "", "maincatlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Intro/SubCat;", "Lkotlin/collections/ArrayList;", "pos", "", "app_debug"})
    public final class SubjectItemHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final android.widget.TextView sub_cat_name = null;
        private final android.widget.TextView count = null;
        private final android.widget.CheckBox selectCB = null;
        private final android.view.View view = null;
        
        public SubjectItemHolder(@org.jetbrains.annotations.NotNull()
        android.view.View itemView) {
            super(null);
        }
        
        public final void setSingleFAQData(@org.jetbrains.annotations.NotNull()
        java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist, int pos) {
        }
    }
}