package com.utkarshnew.android.Intro.Fragment;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 P2\u00020\u0001:\u0001PB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010C\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J\u0012\u0010G\u001a\u00020D2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J&\u0010H\u001a\u0004\u0018\u00010I2\u0006\u0010J\u001a\u00020K2\b\u0010L\u001a\u0004\u0018\u00010M2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016J\u001a\u0010N\u001a\u00020D2\u0006\u0010O\u001a\u00020I2\b\u0010E\u001a\u0004\u0018\u00010FH\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001c\u0010!\u001a\u0004\u0018\u00010\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010\'\u001a\u0004\u0018\u00010(X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R*\u0010-\u001a\u0012\u0012\u0004\u0012\u00020(0.j\b\u0012\u0004\u0012\u00020(`/X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001c\u00104\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u0012\"\u0004\b6\u0010\u0014R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010:\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u0012\"\u0004\b<\u0010\u0014R\u001c\u0010=\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\u0012\"\u0004\b?\u0010\u0014R\u001c\u0010@\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bA\u0010\u0012\"\u0004\bB\u0010\u0014\u00a8\u0006Q"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/MainCategoryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "categoryAdapter", "Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "getCategoryAdapter", "()Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "setCategoryAdapter", "(Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;)V", "chose_txt", "Landroid/widget/TextView;", "getChose_txt", "()Landroid/widget/TextView;", "setChose_txt", "(Landroid/widget/TextView;)V", "competate_cat", "Landroid/widget/RelativeLayout;", "getCompetate_cat", "()Landroid/widget/RelativeLayout;", "setCompetate_cat", "(Landroid/widget/RelativeLayout;)V", "is_select_main_cat", "", "()Ljava/lang/Boolean;", "set_select_main_cat", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "itemSelected", "Lcom/utkarshnew/android/Intro/ItemSelected;", "getItemSelected", "()Lcom/utkarshnew/android/Intro/ItemSelected;", "setItemSelected", "(Lcom/utkarshnew/android/Intro/ItemSelected;)V", "main_cat_recyclerview", "Landroidx/recyclerview/widget/RecyclerView;", "getMain_cat_recyclerview", "()Landroidx/recyclerview/widget/RecyclerView;", "setMain_cat_recyclerview", "(Landroidx/recyclerview/widget/RecyclerView;)V", "mastercat", "Lcom/utkarshnew/android/Intro/Mastercat;", "getMastercat", "()Lcom/utkarshnew/android/Intro/Mastercat;", "setMastercat", "(Lcom/utkarshnew/android/Intro/Mastercat;)V", "mastercatlist", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "getMastercatlist", "()Ljava/util/ArrayList;", "setMastercatlist", "(Ljava/util/ArrayList;)V", "neet_cat", "getNeet_cat", "setNeet_cat", "param1", "", "param2", "school_cat", "getSchool_cat", "setSchool_cat", "tick_neet", "getTick_neet", "setTick_neet", "tick_school", "getTick_school", "setTick_school", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "Companion", "app_debug"})
public final class MainCategoryFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout school_cat;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean is_select_main_cat = false;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.Mastercat mastercat;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout tick_school;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout tick_neet;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout competate_cat;
    @org.jetbrains.annotations.Nullable()
    private androidx.recyclerview.widget.RecyclerView main_cat_recyclerview;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.ItemSelected itemSelected;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout neet_cat;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView chose_txt;
    private java.lang.String param1;
    private java.lang.String param2;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> mastercatlist;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.Adaoter.CategoryAdapter categoryAdapter;
    @org.jetbrains.annotations.NotNull()
    public static final com.utkarshnew.android.Intro.Fragment.MainCategoryFragment.Companion Companion = null;
    
    public MainCategoryFragment() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getSchool_cat() {
        return null;
    }
    
    public final void setSchool_cat(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean is_select_main_cat() {
        return null;
    }
    
    public final void set_select_main_cat(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.Mastercat getMastercat() {
        return null;
    }
    
    public final void setMastercat(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.Mastercat p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getTick_school() {
        return null;
    }
    
    public final void setTick_school(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getTick_neet() {
        return null;
    }
    
    public final void setTick_neet(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getCompetate_cat() {
        return null;
    }
    
    public final void setCompetate_cat(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.recyclerview.widget.RecyclerView getMain_cat_recyclerview() {
        return null;
    }
    
    public final void setMain_cat_recyclerview(@org.jetbrains.annotations.Nullable()
    androidx.recyclerview.widget.RecyclerView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.ItemSelected getItemSelected() {
        return null;
    }
    
    public final void setItemSelected(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.ItemSelected p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getNeet_cat() {
        return null;
    }
    
    public final void setNeet_cat(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getChose_txt() {
        return null;
    }
    
    public final void setChose_txt(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> getMastercatlist() {
        return null;
    }
    
    public final void setMastercatlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.Adaoter.CategoryAdapter getCategoryAdapter() {
        return null;
    }
    
    public final void setCategoryAdapter(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.Adaoter.CategoryAdapter p0) {
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public void onActivityCreated(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.jvm.JvmStatic()
    public static final com.utkarshnew.android.Intro.Fragment.MainCategoryFragment newInstance(@org.jetbrains.annotations.NotNull()
    java.lang.String param1, @org.jetbrains.annotations.NotNull()
    java.lang.String param2) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007\u00a8\u0006\b"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/MainCategoryFragment$Companion;", "", "()V", "newInstance", "Lcom/utkarshnew/android/Intro/Fragment/MainCategoryFragment;", "param1", "", "param2", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.JvmStatic()
        public final com.utkarshnew.android.Intro.Fragment.MainCategoryFragment newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String param1, @org.jetbrains.annotations.NotNull()
        java.lang.String param2) {
            return null;
        }
    }
}