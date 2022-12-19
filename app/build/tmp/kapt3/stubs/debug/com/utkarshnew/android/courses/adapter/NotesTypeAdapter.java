package com.utkarshnew.android.courses.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001&B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u001d\u001a\u00020\u0018H\u0016J\u001c\u0010\u001e\u001a\u00020\u001f2\n\u0010 \u001a\u00060\u0002R\u00020\u00002\u0006\u0010!\u001a\u00020\u0018H\u0017J\u001c\u0010\"\u001a\u00060\u0002R\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0018H\u0016R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u0006\'"}, d2 = {"Lcom/utkarshnew/android/courses/adapter/NotesTypeAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/courses/adapter/NotesTypeAdapter$NotesTypeAdapterholder;", "context", "Landroid/content/Context;", "noteslist", "", "Lcom/utkarshnew/android/courses/modal/NotesType;", "notesTypeItemClick", "Lcom/utkarshnew/android/courses/Interfaces/NotesTypeItemClick;", "(Landroid/content/Context;Ljava/util/List;Lcom/utkarshnew/android/courses/Interfaces/NotesTypeItemClick;)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getNotesTypeItemClick", "()Lcom/utkarshnew/android/courses/Interfaces/NotesTypeItemClick;", "setNotesTypeItemClick", "(Lcom/utkarshnew/android/courses/Interfaces/NotesTypeItemClick;)V", "getNoteslist", "()Ljava/util/List;", "setNoteslist", "(Ljava/util/List;)V", "oldpos", "", "getOldpos", "()I", "setOldpos", "(I)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "NotesTypeAdapterholder", "app_debug"})
public final class NotesTypeAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.courses.adapter.NotesTypeAdapter.NotesTypeAdapterholder> {
    @org.jetbrains.annotations.NotNull()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<? extends com.utkarshnew.android.courses.modal.NotesType> noteslist;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.courses.Interfaces.NotesTypeItemClick notesTypeItemClick;
    private int oldpos = -1;
    
    public NotesTypeAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.utkarshnew.android.courses.modal.NotesType> noteslist, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.courses.Interfaces.NotesTypeItemClick notesTypeItemClick) {
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
    public final java.util.List<com.utkarshnew.android.courses.modal.NotesType> getNoteslist() {
        return null;
    }
    
    public final void setNoteslist(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.utkarshnew.android.courses.modal.NotesType> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.courses.Interfaces.NotesTypeItemClick getNotesTypeItemClick() {
        return null;
    }
    
    public final void setNotesTypeItemClick(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.courses.Interfaces.NotesTypeItemClick p0) {
    }
    
    public final int getOldpos() {
        return 0;
    }
    
    public final void setOldpos(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.utkarshnew.android.courses.adapter.NotesTypeAdapter.NotesTypeAdapterholder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.courses.adapter.NotesTypeAdapter.NotesTypeAdapterholder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/utkarshnew/android/courses/adapter/NotesTypeAdapter$NotesTypeAdapterholder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "notesTypeAdapterBinding", "Lcom/utkarshnew/android/databinding/NotesTypeAdapterBinding;", "(Lcom/utkarshnew/android/courses/adapter/NotesTypeAdapter;Lcom/utkarshnew/android/databinding/NotesTypeAdapterBinding;)V", "getNotesTypeAdapterBinding", "()Lcom/utkarshnew/android/databinding/NotesTypeAdapterBinding;", "bind", "", "noteslist", "Lcom/utkarshnew/android/courses/modal/NotesType;", "app_debug"})
    public final class NotesTypeAdapterholder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.utkarshnew.android.databinding.NotesTypeAdapterBinding notesTypeAdapterBinding = null;
        
        public NotesTypeAdapterholder(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.NotesTypeAdapterBinding notesTypeAdapterBinding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.utkarshnew.android.databinding.NotesTypeAdapterBinding getNotesTypeAdapterBinding() {
            return null;
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.courses.modal.NotesType noteslist) {
        }
    }
}