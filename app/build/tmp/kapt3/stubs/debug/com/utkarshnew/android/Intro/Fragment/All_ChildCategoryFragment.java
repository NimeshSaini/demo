package com.utkarshnew.android.Intro.Fragment;

import java.lang.System;

/**
 * A simple [Fragment] subclass.
 * Use the [All_ChildCategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 92\u00020\u0001:\u00019B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u0012\u00100\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J&\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\b\u00105\u001a\u0004\u0018\u0001062\b\u0010.\u001a\u0004\u0018\u00010/H\u0016J\u001a\u00107\u001a\u00020-2\u0006\u00108\u001a\u0002022\b\u0010.\u001a\u0004\u0018\u00010/H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010 \u001a\u0004\u0018\u00010!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010%R\u001c\u0010&\u001a\u0004\u0018\u00010\'X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+\u00a8\u0006:"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/All_ChildCategoryFragment;", "Landroidx/fragment/app/Fragment;", "()V", "allSubCategoryAdapter", "Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;", "getAllSubCategoryAdapter", "()Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;", "setAllSubCategoryAdapter", "(Lcom/utkarshnew/android/Intro/Adaoter/AllSubCategoryAdapter;)V", "allsubCat", "Lcom/utkarshnew/android/Intro/SubCat;", "getAllsubCat", "()Lcom/utkarshnew/android/Intro/SubCat;", "setAllsubCat", "(Lcom/utkarshnew/android/Intro/SubCat;)V", "chose_txt", "Landroid/widget/TextView;", "getChose_txt", "()Landroid/widget/TextView;", "setChose_txt", "(Landroid/widget/TextView;)V", "is_select_allsubcat", "", "()Ljava/lang/Boolean;", "set_select_allsubcat", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "param1", "", "param2", "selectedsub_all_cat", "Ljava/util/ArrayList;", "subCatItemSelected", "Lcom/utkarshnew/android/Intro/SubCatItemSelected;", "getSubCatItemSelected", "()Lcom/utkarshnew/android/Intro/SubCatItemSelected;", "setSubCatItemSelected", "(Lcom/utkarshnew/android/Intro/SubCatItemSelected;)V", "sub_cat_recyclerview", "Landroidx/recyclerview/widget/RecyclerView;", "getSub_cat_recyclerview", "()Landroidx/recyclerview/widget/RecyclerView;", "setSub_cat_recyclerview", "(Landroidx/recyclerview/widget/RecyclerView;)V", "onActivityCreated", "", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "Companion", "app_debug"})
public final class All_ChildCategoryFragment extends androidx.fragment.app.Fragment {
    private java.lang.String param1;
    private java.lang.String param2;
    @org.jetbrains.annotations.Nullable()
    private androidx.recyclerview.widget.RecyclerView sub_cat_recyclerview;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView chose_txt;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCatItemSelected subCatItemSelected;
    private java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> selectedsub_all_cat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCat allsubCat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter allSubCategoryAdapter;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean is_select_allsubcat = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.utkarshnew.android.Intro.Fragment.All_ChildCategoryFragment.Companion Companion = null;
    
    public All_ChildCategoryFragment() {
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
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCatItemSelected getSubCatItemSelected() {
        return null;
    }
    
    public final void setSubCatItemSelected(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCatItemSelected p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCat getAllsubCat() {
        return null;
    }
    
    public final void setAllsubCat(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCat p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter getAllSubCategoryAdapter() {
        return null;
    }
    
    public final void setAllSubCategoryAdapter(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.Adaoter.AllSubCategoryAdapter p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean is_select_allsubcat() {
        return null;
    }
    
    public final void set_select_allsubcat(@org.jetbrains.annotations.Nullable()
    java.lang.Boolean p0) {
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
    public static final com.utkarshnew.android.Intro.Fragment.All_ChildCategoryFragment newInstance(@org.jetbrains.annotations.NotNull()
    java.lang.String param1, @org.jetbrains.annotations.NotNull()
    java.lang.String param2) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007\u00a8\u0006\b"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/All_ChildCategoryFragment$Companion;", "", "()V", "newInstance", "Lcom/utkarshnew/android/Intro/Fragment/All_ChildCategoryFragment;", "param1", "", "param2", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.JvmStatic()
        public final com.utkarshnew.android.Intro.Fragment.All_ChildCategoryFragment newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String param1, @org.jetbrains.annotations.NotNull()
        java.lang.String param2) {
            return null;
        }
    }
}