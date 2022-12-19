package com.utkarshnew.android.Intro.Fragment;

import java.lang.System;

/**
 * A simple [Fragment] subclass.
 * Use the [LanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 +2\u00020\u0001:\u0001+B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u001d\u001a\u00020\u001eJ\u0012\u0010\u001f\u001a\u00020\u001e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u0012\u0010\"\u001a\u00020\u001e2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J&\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010%\u001a\u00020&2\b\u0010\'\u001a\u0004\u0018\u00010(2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016J\u001a\u0010)\u001a\u00020\u001e2\u0006\u0010*\u001a\u00020$2\b\u0010 \u001a\u0004\u0018\u00010!H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001e\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\u0011\u001a\u0004\b\f\u0010\u000e\"\u0004\b\u000f\u0010\u0010R*\u0010\u0012\u001a\u0012\u0012\u0004\u0012\u00020\u00140\u0013j\b\u0012\u0004\u0012\u00020\u0014`\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/LanguageFragment;", "Landroidx/fragment/app/Fragment;", "()V", "english_img", "Landroid/widget/ImageView;", "getEnglish_img", "()Landroid/widget/ImageView;", "setEnglish_img", "(Landroid/widget/ImageView;)V", "hindi_img", "getHindi_img", "setHindi_img", "is_lang_select", "", "()Ljava/lang/Boolean;", "set_lang_select", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "langlistdata", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/table/LanguagesTable;", "Lkotlin/collections/ArrayList;", "getLanglistdata", "()Ljava/util/ArrayList;", "setLanglistdata", "(Ljava/util/ArrayList;)V", "param1", "", "param2", "checkLang", "", "onActivityCreated", "savedInstanceState", "Landroid/os/Bundle;", "onCreate", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "onViewCreated", "view", "Companion", "app_debug"})
public final class LanguageFragment extends androidx.fragment.app.Fragment {
    private java.lang.String param1;
    private java.lang.String param2;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView english_img;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.LanguagesTable> langlistdata;
    @org.jetbrains.annotations.Nullable()
    private android.widget.ImageView hindi_img;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Boolean is_lang_select = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.utkarshnew.android.Intro.Fragment.LanguageFragment.Companion Companion = null;
    
    public LanguageFragment() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getEnglish_img() {
        return null;
    }
    
    public final void setEnglish_img(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.LanguagesTable> getLanglistdata() {
        return null;
    }
    
    public final void setLanglistdata(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.LanguagesTable> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ImageView getHindi_img() {
        return null;
    }
    
    public final void setHindi_img(@org.jetbrains.annotations.Nullable()
    android.widget.ImageView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Boolean is_lang_select() {
        return null;
    }
    
    public final void set_lang_select(@org.jetbrains.annotations.Nullable()
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
    
    public final void checkLang() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.jvm.JvmStatic()
    public static final com.utkarshnew.android.Intro.Fragment.LanguageFragment newInstance(@org.jetbrains.annotations.NotNull()
    java.lang.String param1, @org.jetbrains.annotations.NotNull()
    java.lang.String param2) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007\u00a8\u0006\b"}, d2 = {"Lcom/utkarshnew/android/Intro/Fragment/LanguageFragment$Companion;", "", "()V", "newInstance", "Lcom/utkarshnew/android/Intro/Fragment/LanguageFragment;", "param1", "", "param2", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @kotlin.jvm.JvmStatic()
        public final com.utkarshnew.android.Intro.Fragment.LanguageFragment newInstance(@org.jetbrains.annotations.NotNull()
        java.lang.String param1, @org.jetbrains.annotations.NotNull()
        java.lang.String param2) {
            return null;
        }
    }
}