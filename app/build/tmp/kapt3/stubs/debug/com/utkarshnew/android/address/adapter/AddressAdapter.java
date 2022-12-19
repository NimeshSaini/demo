package com.utkarshnew.android.address.adapter;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\'B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u001d\u001a\u00020\u0018H\u0016J\u001c\u0010\u001e\u001a\u00020\u001f2\n\u0010 \u001a\u00060\u0002R\u00020\u00002\u0006\u0010!\u001a\u00020\u0018H\u0017J\u001c\u0010\"\u001a\u00060\u0002R\u00020\u00002\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\u0018H\u0016J\u0014\u0010&\u001a\u00020\u001f2\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001c\u00a8\u0006("}, d2 = {"Lcom/utkarshnew/android/address/adapter/AddressAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/utkarshnew/android/address/adapter/AddressAdapter$AddressAdapterholder;", "context", "Landroid/content/Context;", "addresslist", "", "Lcom/utkarshnew/android/address/model/Data;", "itemSelected", "Lcom/utkarshnew/android/address/interfaces/onItemSelected;", "(Landroid/content/Context;Ljava/util/List;Lcom/utkarshnew/android/address/interfaces/onItemSelected;)V", "getAddresslist", "()Ljava/util/List;", "setAddresslist", "(Ljava/util/List;)V", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "getItemSelected", "()Lcom/utkarshnew/android/address/interfaces/onItemSelected;", "setItemSelected", "(Lcom/utkarshnew/android/address/interfaces/onItemSelected;)V", "oldpos", "", "getOldpos", "()I", "setOldpos", "(I)V", "getItemCount", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateItem", "AddressAdapterholder", "app_debug"})
public final class AddressAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.utkarshnew.android.address.adapter.AddressAdapter.AddressAdapterholder> {
    @org.jetbrains.annotations.NotNull()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.utkarshnew.android.address.model.Data> addresslist;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.address.interfaces.onItemSelected itemSelected;
    private int oldpos = -1;
    
    public AddressAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.address.model.Data> addresslist, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.address.interfaces.onItemSelected itemSelected) {
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
    public final java.util.List<com.utkarshnew.android.address.model.Data> getAddresslist() {
        return null;
    }
    
    public final void setAddresslist(@org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.address.model.Data> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.address.interfaces.onItemSelected getItemSelected() {
        return null;
    }
    
    public final void setItemSelected(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.address.interfaces.onItemSelected p0) {
    }
    
    public final int getOldpos() {
        return 0;
    }
    
    public final void setOldpos(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public com.utkarshnew.android.address.adapter.AddressAdapter.AddressAdapterholder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.address.adapter.AddressAdapter.AddressAdapterholder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void updateItem(@org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.address.model.Data> addresslist) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000b"}, d2 = {"Lcom/utkarshnew/android/address/adapter/AddressAdapter$AddressAdapterholder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "addressAdapterBinding", "Lcom/utkarshnew/android/databinding/AddressAdapterBinding;", "(Lcom/utkarshnew/android/address/adapter/AddressAdapter;Lcom/utkarshnew/android/databinding/AddressAdapterBinding;)V", "getAddressAdapterBinding", "()Lcom/utkarshnew/android/databinding/AddressAdapterBinding;", "bind", "", "coursesCoupon", "Lcom/utkarshnew/android/address/model/Data;", "app_debug"})
    public final class AddressAdapterholder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.utkarshnew.android.databinding.AddressAdapterBinding addressAdapterBinding = null;
        
        public AddressAdapterholder(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.AddressAdapterBinding addressAdapterBinding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.utkarshnew.android.databinding.AddressAdapterBinding getAddressAdapterBinding() {
            return null;
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.address.model.Data coursesCoupon) {
        }
    }
}