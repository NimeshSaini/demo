package com.utkarshnew.android.Intro.Fragment;

import java.lang.System;

/**
 * A simple [Fragment] subclass.
 * Use the [ChildCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 @2\u00020\u0001:\u0001@B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u000106H\u0017J\u0012\u00107\u001a\u0002042\b\u00105\u001a\u0004\u0018\u000106H\u0016J&\u00108\u001a\u0004\u0018\u0001092\u0006\u0010:\u001a\u00020;2\b\u0010<\u001a\u0004\u0018\u00010=2\b\u00105\u001a\u0004\u0018\u000106H\u0016J\u001a\u0010>\u001a\u0002042\u0006\u0010?\u001a\u0002092\b\u00105\u001a\u0004\u0018\u000106H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0014\u001a\u0004\b\u000f\u0010\u0011\"\u0004\b\u0012\u0010\u0013R*\u0010\u0015\u001a\u0012\u0012\u0004\u0012\u00020\u00170\u0016j\b\u0012\u0004\u0012\u00020\u0017`\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\"\u001a\u0004\u0018\u00010!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001c\u0010\'\u001a\u0004\u0018\u00010(X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102\u00a8\u0006A"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/ChildCategoryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "allSubCategoryAdapter", "Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;", "getAllSubCategoryAdapter", "()Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;", "setAllSubCategoryAdapter", "(Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;)V", "chose_txt", "Landroid/widget/TextView;", "getChose_txt", "()Landroid/widget/TextView;", "setChose_txt", "(Landroid/widget/TextView;)V", "is_select_course", "", "()Ljava/lang/Boolean;", "set_select_course", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "masterAllCatTables", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/table/MasteAllCatTable;", "Lkotlin/collections/ArrayList;", "getMasterAllCatTables", "()Ljava/util/ArrayList;", "setMasterAllCatTables", "(Ljava/util/ArrayList;)V", "param1", "", "param2", "selected_master_cat", "Lcom/utkarshnew/android/Intro/SubCat;", "subCat", "getSubCat", "()Lcom/utkarshnew/android/Intro/SubCat;", "setSubCat", "(Lcom/utkarshnew/android/Intro/SubCat;)V", "subCatItemSelected", "Lcom/utkarshnew/android/Intro/SubCatItemSelected;", "getSubCatItemSelected", "()Lcom/utkarshnew/android/Intro/SubCatItemSelected;", "setSubCatItemSelected", "(Lcom/utkarshnew/android/Intro/SubCatItemSelected;)V", "sub_cat_recyclerview", "Landroidx/recyclerview/widget/RecyclerView;", "getSub_cat_recyclerview", "()Landroidx/recyclerview/widget/RecyclerView;", "setSub_cat_recyclerview", "(Landroidx/recyclerview/widget/RecyclerView;)V", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "Companion", "app_debug"})
public final class ChildCategoryFragment extends androidx.fragment.app.Fragment {
    private java.lang.String param1;
    private java.lang.String param2;
    @org.jetbrains.annotations.Nullable()
    private androidx.recyclerview.widget.RecyclerView sub_cat_recyclerview;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView chose_txt;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> masterAllCatTables;
    private java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> selected_master_cat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter allSubCategoryAdapter;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean is_select_course = false;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCat subCat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCatItemSelected subCatItemSelected;
    @org.jetbrains.annotations.NotNull()
    public static final com.utkarshnew.android.Intro.Fragment.ChildCategoryFragment.Companion Companion = null;
    
    public ChildCategoryFragment() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.recyclerview.widget.RecyclerView getSub_cat_recyclerview() {
        return null;
    }
    
    public final void setSub_cat_recyclerview(@org.jetbrains.annotations.Nullable()
    androidx.recyclerview.widget.RecyclerView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getChose_txt() {
        return null;
    }
    
    public final void setChose_txt(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> getMasterAllCatTables() {
        return null;
    }
    
    public final void setMasterAllCatTables(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter getAllSubCategoryAdapter() {
        return null;
    }
    
    public final void setAllSubCategoryAdapter(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean is_select_course() {
        return null;
    }
    
    public final void set_select_course(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCat getSubCat() {
        return null;
    }
    
    public final void setSubCat(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCat p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCatItemSelected getSubCatItemSelected() {
        return null;
    }
    
    public final void setSubCatItemSelected(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCatItemSelected p0) {
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
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @java.lang.Override()
    public void onActivityCreated(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChildCategoryFragment.
     */
    @org.jetbrains.annotations.NotNull()
    @kotlin.jvm.JvmStatic()
    public static final com.utkarshnew.android.Intro.Fragment.ChildCategoryFragment newInstance(@org.jetbrains.annotations.NotNull()
    java.lang.String param1, @org.jetbrains.annotations.NotNull()
    java.lang.String param2) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007\u00a8\u0006\b"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/ChildCategoryFragment$Companion;", "", "()V", "newInstance", "Lcom/utkarshnew/android/Intro/Fragment/ChildCategoryFragment;", "param1", "", "param2", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChildCategoryFragment.
         */
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.JvmStatic()
        public final com.utkarshnew.android.Intro.Fragment.ChildCategoryFragment newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String param1, @org.jetbrains.annotations.NotNull()
        java.lang.String param2) {
            return null;
        }
    }
}