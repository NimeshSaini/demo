package com.utkarshnew.android.feeds.dataclass;

import java.lang.System;

@androidx.room.Entity(tableName = "feedData")
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\f\"\u0004\b#\u0010\u000e\u00a8\u0006$"}, d2 = {"Lcom/utkarshnew/android/feeds/dataclass/FeedParentData;", "", "()V", "cd_time", "", "getCd_time", "()J", "setCd_time", "(J)V", "id", "", "getId", "()I", "setId", "(I)V", "interval", "getInterval", "setInterval", "limit", "getLimit", "setLimit", "message", "", "getMessage", "()Ljava/lang/String;", "setMessage", "(Ljava/lang/String;)V", "status", "", "getStatus", "()Z", "setStatus", "(Z)V", "time", "getTime", "setTime", "app_debug"})
public final class FeedParentData {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private int id = 0;
    private boolean status = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String message = "";
    private int time = 0;
    private int interval = 0;
    private int limit = 0;
    private long cd_time = 0L;
    
    public FeedParentData() {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    public final void setId(int p0) {
    }
    
    public final boolean getStatus() {
        return false;
    }
    
    public final void setStatus(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessage() {
        return null;
    }
    
    public final void setMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getTime() {
        return 0;
    }
    
    public final void setTime(int p0) {
    }
    
    public final int getInterval() {
        return 0;
    }
    
    public final void setInterval(int p0) {
    }
    
    public final int getLimit() {
        return 0;
    }
    
    public final void setLimit(int p0) {
    }
    
    public final long getCd_time() {
        return 0L;
    }
    
    public final void setCd_time(long p0) {
    }
}