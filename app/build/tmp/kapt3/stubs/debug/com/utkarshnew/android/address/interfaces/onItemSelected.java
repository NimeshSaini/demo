package com.utkarshnew.android.address.interfaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&J\u0018\u0010\t\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\n"}, d2 = {"Lcom/utkarshnew/android/address/interfaces/onItemSelected;", "", "delete_address", "", "pos", "", "data", "Lcom/utkarshnew/android/address/model/Data;", "itemClick", "itemSelect", "app_debug"})
public abstract interface onItemSelected {
    
    public abstract void itemClick(int pos, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.address.model.Data data);
    
    public abstract void itemSelect(int pos, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.address.model.Data data);
    
    public abstract void delete_address(int pos, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.address.model.Data data);
}