package com.utkarshnew.android.Intro.Adaoter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u00015B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\b\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\b\u0010(\u001a\u00020#H\u0016J&\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020#2\u0016\u0010,\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0006j\b\u0012\u0004\u0012\u00020\u0015`\bJ.\u0010-\u001a\u00020*2\u0006\u0010+\u001a\u00020#2\u0016\u0010,\u001a\u0012\u0012\u0004\u0012\u00020\u00150\u0006j\b\u0012\u0004\u0012\u00020\u0015`\b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u001c\u0010.\u001a\u00020*2\n\u0010/\u001a\u00060\u0002R\u00020\u00002\u0006\u00100\u001a\u00020#H\u0017J\u001c\u00101\u001a\u00060\u0002R\u00020\u00002\u0006\u00102\u001a\u0002032\u0006\u00104\u001a\u00020#H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R \u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R*\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00020\u00070\u0006j\b\u0012\u0004\u0012\u00020\u0007`\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0017\"\u0004\b\u001b\u0010\u0019R\u001a\u0010\u001c\u001a\u00020\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001a\u0010\"\u001a\u00020#X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'\u00a8\u00066"}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter$CategoryAdapterVh;", "context", "Landroid/content/Context;", "mastercatlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Intro/Mastercat;", "Lkotlin/collections/ArrayList;", "itemSelected", "Lcom/utkarshnew/android/Intro/ItemSelected;", "(Landroid/content/Context;Ljava/util/ArrayList;Lcom/utkarshnew/android/Intro/ItemSelected;)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getItemSelected", "()Lcom/utkarshnew/android/Intro/ItemSelected;", "setItemSelected", "(Lcom/utkarshnew/android/Intro/ItemSelected;)V", "list", "Lcom/utkarshnew/android/Intro/SubCat;", "getList", "()Ljava/util/ArrayList;", "setList", "(Ljava/util/ArrayList;)V", "getMastercatlist", "setMastercatlist", "pid", "", "getPid", "()Ljava/lang/String;", "setPid", "(Ljava/lang/String;)V", "randomnumber", "", "getRandomnumber", "()I", "setRandomnumber", "(I)V", "getItemCount", "notifyadd", "", "catposition", "maincatlist", "notifyremove", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "CategoryAdapterVh", "app_debug"})
public final class CategoryAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.Intro.Adaoter.CategoryAdapter.CategoryAdapterVh> {
    @org.jetbrains.annotations.NotNull()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> mastercatlist;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Intro.ItemSelected itemSelected;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> list;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String pid = "";
    private int randomnumber = 0;
    
    public CategoryAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> mastercatlist, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.ItemSelected itemSelected) {
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
    public final java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> getMastercatlist() {
        return null;
    }
    
    public final void setMastercatlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Intro.ItemSelected getItemSelected() {
        return null;
    }
    
    public final void setItemSelected(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.ItemSelected p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> getList() {
        return null;
    }
    
    public final void setList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPid() {
        return null;
    }
    
    public final void setPid(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getRandomnumber() {
        return 0;
    }
    
    public final void setRandomnumber(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.utkarshnew.android.Intro.Adaoter.CategoryAdapter.CategoryAdapterVh onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables", "NotifyDataSetChanged"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Intro.Adaoter.CategoryAdapter.CategoryAdapterVh holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void notifyadd(int catposition, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist) {
    }
    
    public final void notifyremove(int catposition, @org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist, @org.jetbrains.annotations.NotNull()
    java.lang.String pid) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019R\u001a\u0010#\u001a\u00020$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(\u00a8\u0006)"}, d2 = {"Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter$CategoryAdapterVh;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemView", "Landroid/view/View;", "(Lcom/utkarshnew/android/Intro/Adaoter/CategoryAdapter;Landroid/view/View;)V", "category_count", "Landroid/widget/TextView;", "getCategory_count", "()Landroid/widget/TextView;", "setCategory_count", "(Landroid/widget/TextView;)V", "category_name", "getCategory_name", "setCategory_name", "dropDownIV", "Landroid/widget/ImageView;", "getDropDownIV", "()Landroid/widget/ImageView;", "setDropDownIV", "(Landroid/widget/ImageView;)V", "lowerViewItem_maincat", "Landroid/widget/LinearLayout;", "getLowerViewItem_maincat", "()Landroid/widget/LinearLayout;", "setLowerViewItem_maincat", "(Landroid/widget/LinearLayout;)V", "main_cat_recycler", "Landroidx/recyclerview/widget/RecyclerView;", "getMain_cat_recycler", "()Landroidx/recyclerview/widget/RecyclerView;", "setMain_cat_recycler", "(Landroidx/recyclerview/widget/RecyclerView;)V", "parentLL", "getParentLL", "setParentLL", "school_cat", "Landroid/widget/RelativeLayout;", "getSchool_cat", "()Landroid/widget/RelativeLayout;", "setSchool_cat", "(Landroid/widget/RelativeLayout;)V", "app_debug"})
    public final class CategoryAdapterVh extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private android.widget.RelativeLayout school_cat;
        @org.jetbrains.annotations.NotNull()
        private android.widget.LinearLayout parentLL;
        @org.jetbrains.annotations.NotNull()
        private android.widget.LinearLayout lowerViewItem_maincat;
        @org.jetbrains.annotations.NotNull()
        private android.widget.ImageView dropDownIV;
        @org.jetbrains.annotations.NotNull()
        private android.widget.TextView category_name;
        @org.jetbrains.annotations.NotNull()
        private android.widget.TextView category_count;
        @org.jetbrains.annotations.NotNull()
        private androidx.recyclerview.widget.RecyclerView main_cat_recycler;
        
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
        public final android.widget.LinearLayout getParentLL() {
            return null;
        }
        
        public final void setParentLL(@org.jetbrains.annotations.NotNull()
        android.widget.LinearLayout p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.LinearLayout getLowerViewItem_maincat() {
            return null;
        }
        
        public final void setLowerViewItem_maincat(@org.jetbrains.annotations.NotNull()
        android.widget.LinearLayout p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.ImageView getDropDownIV() {
            return null;
        }
        
        public final void setDropDownIV(@org.jetbrains.annotations.NotNull()
        android.widget.ImageView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getCategory_name() {
            return null;
        }
        
        public final void setCategory_name(@org.jetbrains.annotations.NotNull()
        android.widget.TextView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getCategory_count() {
            return null;
        }
        
        public final void setCategory_count(@org.jetbrains.annotations.NotNull()
        android.widget.TextView p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.recyclerview.widget.RecyclerView getMain_cat_recycler() {
            return null;
        }
        
        public final void setMain_cat_recycler(@org.jetbrains.annotations.NotNull()
        androidx.recyclerview.widget.RecyclerView p0) {
        }
    }
}