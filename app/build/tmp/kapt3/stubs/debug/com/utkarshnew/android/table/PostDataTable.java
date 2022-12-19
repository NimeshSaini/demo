package com.utkarshnew.android.table;

import java.lang.System;

@androidx.room.Entity(tableName = "PostDataTable")
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\bA\n\u0002\u0010\b\n\u0002\b)\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001a\u0010\u000f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001a\u0010\u0015\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001a\u0010\u0018\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001a\u0010\u001b\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001a\u0010$\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\bR\u001a\u0010\'\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0006\"\u0004\b)\u0010\bR\u001a\u0010*\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001a\u0010-\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\bR\u001a\u00100\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u0006\"\u0004\b2\u0010\bR\u001a\u00103\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0006\"\u0004\b5\u0010\bR\u001a\u00106\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\u0006\"\u0004\b8\u0010\bR\u001a\u00109\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u0006\"\u0004\b;\u0010\bR\u001a\u0010<\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010\u0006\"\u0004\b>\u0010\bR\u001a\u0010?\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\u0006\"\u0004\bA\u0010\bR\u001a\u0010B\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u0006\"\u0004\bD\u0010\bR\u001e\u0010E\u001a\u00020F8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR\u001a\u0010K\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\u0006\"\u0004\bM\u0010\bR\u001a\u0010N\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0006\"\u0004\bP\u0010\bR\u001a\u0010Q\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010\u0006\"\u0004\bS\u0010\bR\u001a\u0010T\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010\u0006\"\u0004\bV\u0010\bR\u001a\u0010W\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010\u0006\"\u0004\bY\u0010\bR\u001a\u0010Z\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b[\u0010\u0006\"\u0004\b\\\u0010\bR\u001a\u0010]\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b^\u0010\u0006\"\u0004\b_\u0010\bR\u001a\u0010`\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\ba\u0010\u0006\"\u0004\bb\u0010\bR\u001a\u0010c\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bd\u0010\u0006\"\u0004\be\u0010\bR\u001a\u0010f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bg\u0010\u0006\"\u0004\bh\u0010\bR\u001a\u0010i\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bj\u0010\u0006\"\u0004\bk\u0010\bR\u001a\u0010l\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bm\u0010\u0006\"\u0004\bn\u0010\b\u00a8\u0006o"}, d2 = {"Lcom/utkarshnew/android/table/PostDataTable;", "", "()V", "bannerlist", "", "getBannerlist", "()Ljava/lang/String;", "setBannerlist", "(Ljava/lang/String;)V", "created", "getCreated", "setCreated", "description", "getDescription", "setDescription", "id", "getId", "setId", "image_type", "getImage_type", "setImage_type", "iscommentenable", "getIscommentenable", "setIscommentenable", "json", "getJson", "setJson", "limit", "getLimit", "setLimit", "liveClassStatus", "getLiveClassStatus", "setLiveClassStatus", "liveTestStatus", "getLiveTestStatus", "setLiveTestStatus", "liveclass", "getLiveclass", "setLiveclass", "livetest", "getLivetest", "setLivetest", "masterCat", "getMasterCat", "setMasterCat", "meta_url", "getMeta_url", "setMeta_url", "modified", "getModified", "setModified", "my_like", "getMy_like", "setMy_like", "my_pinned", "getMy_pinned", "setMy_pinned", "name", "getName", "setName", "newCourseData", "getNewCourseData", "setNewCourseData", "page", "getPage", "setPage", "parentId", "getParentId", "setParentId", "postId", "", "getPostId", "()I", "setPostId", "(I)V", "post_type", "getPost_type", "setPost_type", "profile_picture", "getProfile_picture", "setProfile_picture", "sectionposiiton", "getSectionposiiton", "setSectionposiiton", "status", "getStatus", "setStatus", "sub_cat_id", "getSub_cat_id", "setSub_cat_id", "testResult", "getTestResult", "setTestResult", "text", "getText", "setText", "thumbnail", "getThumbnail", "setThumbnail", "total_comments", "getTotal_comments", "setTotal_comments", "total_likes", "getTotal_likes", "setTotal_likes", "url", "getUrl", "setUrl", "user_id", "getUser_id", "setUser_id", "app_debug"})
public final class PostDataTable {
    @org.jetbrains.annotations.NotNull()
    private java.lang.String created = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String page = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String masterCat = "";
    @androidx.room.PrimaryKey(autoGenerate = true)
    private int postId = 0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String json = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String meta_url = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String thumbnail = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String url = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String modified = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String my_like = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String name = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String post_type = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String profile_picture = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String status = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sub_cat_id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String text = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String total_comments = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String total_likes = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String user_id = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String newCourseData = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String livetest = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String liveclass = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String testResult = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String bannerlist = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String liveClassStatus = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String liveTestStatus = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String iscommentenable = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sectionposiiton = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String limit = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String my_pinned = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String parentId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String description = "";
    @org.jetbrains.annotations.Nullable()
    private java.lang.String image_type = "";
    
    public PostDataTable() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCreated() {
        return null;
    }
    
    public final void setCreated(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPage() {
        return null;
    }
    
    public final void setPage(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMasterCat() {
        return null;
    }
    
    public final void setMasterCat(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final int getPostId() {
        return 0;
    }
    
    public final void setPostId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getId() {
        return null;
    }
    
    public final void setId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getJson() {
        return null;
    }
    
    public final void setJson(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMeta_url() {
        return null;
    }
    
    public final void setMeta_url(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getThumbnail() {
        return null;
    }
    
    public final void setThumbnail(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUrl() {
        return null;
    }
    
    public final void setUrl(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getModified() {
        return null;
    }
    
    public final void setModified(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMy_like() {
        return null;
    }
    
    public final void setMy_like(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPost_type() {
        return null;
    }
    
    public final void setPost_type(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getProfile_picture() {
        return null;
    }
    
    public final void setProfile_picture(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getStatus() {
        return null;
    }
    
    public final void setStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSub_cat_id() {
        return null;
    }
    
    public final void setSub_cat_id(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getText() {
        return null;
    }
    
    public final void setText(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTotal_comments() {
        return null;
    }
    
    public final void setTotal_comments(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTotal_likes() {
        return null;
    }
    
    public final void setTotal_likes(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getUser_id() {
        return null;
    }
    
    public final void setUser_id(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNewCourseData() {
        return null;
    }
    
    public final void setNewCourseData(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLivetest() {
        return null;
    }
    
    public final void setLivetest(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLiveclass() {
        return null;
    }
    
    public final void setLiveclass(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTestResult() {
        return null;
    }
    
    public final void setTestResult(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getBannerlist() {
        return null;
    }
    
    public final void setBannerlist(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLiveClassStatus() {
        return null;
    }
    
    public final void setLiveClassStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLiveTestStatus() {
        return null;
    }
    
    public final void setLiveTestStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getIscommentenable() {
        return null;
    }
    
    public final void setIscommentenable(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSectionposiiton() {
        return null;
    }
    
    public final void setSectionposiiton(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLimit() {
        return null;
    }
    
    public final void setLimit(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMy_pinned() {
        return null;
    }
    
    public final void setMy_pinned(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getParentId() {
        return null;
    }
    
    public final void setParentId(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescription() {
        return null;
    }
    
    public final void setDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getImage_type() {
        return null;
    }
    
    public final void setImage_type(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
}