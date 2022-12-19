package com.utkarshnew.android.feeds.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00c6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u008c\u0001\u001a\u00030\u008d\u0001J\b\u0010\u008e\u0001\u001a\u00030\u008d\u0001J\b\u0010\u008f\u0001\u001a\u00030\u008d\u0001J\n\u0010\u0090\u0001\u001a\u00030\u008d\u0001H\u0016J\u0016\u0010\u0091\u0001\u001a\u00030\u008d\u00012\n\u0010\u0092\u0001\u001a\u0005\u0018\u00010\u0093\u0001H\u0014J\u0015\u0010\u0094\u0001\u001a\u00020-2\n\u0010\u0095\u0001\u001a\u0005\u0018\u00010\u0096\u0001H\u0016J\b\u0010\u0097\u0001\u001a\u00030\u008d\u0001R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R \u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000e\"\u0004\b\u0014\u0010\u0010R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u001d\u001a\u00020\u001e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 R \u0010#\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u000e\"\u0004\b%\u0010\u0010R\u001c\u0010&\u001a\u0004\u0018\u00010\'X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020-X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010.\"\u0004\b/\u00100R\u001a\u00101\u001a\u000202X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R \u00107\u001a\b\u0012\u0004\u0012\u0002080\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u000e\"\u0004\b:\u0010\u0010R\u001a\u0010;\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R \u0010A\u001a\b\u0012\u0004\u0012\u00020B0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u000e\"\u0004\bD\u0010\u0010R\u001a\u0010E\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bF\u0010>\"\u0004\bG\u0010@R\u001a\u0010H\u001a\u00020-X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bI\u0010.\"\u0004\bJ\u00100R\u001a\u0010K\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010>\"\u0004\bM\u0010@R\u001c\u0010N\u001a\u0004\u0018\u00010OX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR \u0010T\u001a\b\u0012\u0004\u0012\u00020V0UX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010>\"\u0004\b]\u0010@R \u0010^\u001a\b\u0012\u0004\u0012\u00020_0UX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b`\u0010X\"\u0004\ba\u0010ZR \u0010b\u001a\b\u0012\u0004\u0012\u00020c0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bd\u0010\u000e\"\u0004\be\u0010\u0010R\u001c\u0010f\u001a\u0004\u0018\u00010gX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001a\u0010l\u001a\u000202X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bm\u00104\"\u0004\bn\u00106R\u000e\u0010o\u001a\u00020<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R \u0010p\u001a\b\u0012\u0004\u0012\u00020\u00120\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bq\u0010\u000e\"\u0004\br\u0010\u0010R\u001a\u0010s\u001a\u00020-X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bt\u0010.\"\u0004\bu\u00100R\u001a\u0010v\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bw\u0010>\"\u0004\bx\u0010@R \u0010y\u001a\b\u0012\u0004\u0012\u00020V0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bz\u0010\u000e\"\u0004\b{\u0010\u0010R \u0010|\u001a\b\u0012\u0004\u0012\u00020V0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b}\u0010\u000e\"\u0004\b~\u0010\u0010R\u001c\u0010\u007f\u001a\u00020<X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0001\u0010>\"\u0005\b\u0081\u0001\u0010@R$\u0010\u0082\u0001\u001a\t\u0012\u0005\u0012\u00030\u0083\u00010\u000bX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u000e\"\u0005\b\u0085\u0001\u0010\u0010R \u0010\u0086\u0001\u001a\u00030\u0087\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001\"\u0006\b\u008a\u0001\u0010\u008b\u0001\u00a8\u0006\u0098\u0001"}, d2 = {"Lcom/utkarshnew/android/feeds/activity/PinnedPostActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroidx/appcompat/widget/PopupMenu$OnMenuItemClickListener;", "()V", "backBtn", "Landroid/widget/Button;", "getBackBtn", "()Landroid/widget/Button;", "setBackBtn", "(Landroid/widget/Button;)V", "bannert_list", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/feeds/dataclass/BannerData;", "getBannert_list", "()Ljava/util/ArrayList;", "setBannert_list", "(Ljava/util/ArrayList;)V", "datalist", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "getDatalist", "setDatalist", "feedAdapter", "Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;", "getFeedAdapter", "()Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;", "setFeedAdapter", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;)V", "feedJsonObject", "Lorg/json/JSONObject;", "feedViewModel", "Lcom/utkarshnew/android/feeds/viewmodel/FeedViewModel;", "getFeedViewModel", "()Lcom/utkarshnew/android/feeds/viewmodel/FeedViewModel;", "feedViewModel$delegate", "Lkotlin/Lazy;", "feedlist", "getFeedlist", "setFeedlist", "feedsBinding", "Lcom/utkarshnew/android/databinding/ActivityFeedsBinding;", "getFeedsBinding", "()Lcom/utkarshnew/android/databinding/ActivityFeedsBinding;", "setFeedsBinding", "(Lcom/utkarshnew/android/databinding/ActivityFeedsBinding;)V", "isPullToRefresh", "", "()Z", "setPullToRefresh", "(Z)V", "limitdata", "", "getLimitdata", "()I", "setLimitdata", "(I)V", "liveClassData", "Lcom/utkarshnew/android/home/liveclasses/Datum;", "getLiveClassData", "setLiveClassData", "liveClassStatus", "", "getLiveClassStatus", "()Ljava/lang/String;", "setLiveClassStatus", "(Ljava/lang/String;)V", "liveTestData", "Lcom/utkarshnew/android/home/livetest/LiveTestData;", "getLiveTestData", "setLiveTestData", "liveTestStatus", "getLiveTestStatus", "setLiveTestStatus", "loading", "getLoading", "setLoading", "main_cat", "getMain_cat", "setMain_cat", "manager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getManager", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "setManager", "(Landroidx/recyclerview/widget/LinearLayoutManager;)V", "masterAllCatTables", "", "Lcom/utkarshnew/android/table/MasteAllCatTable;", "getMasterAllCatTables", "()Ljava/util/List;", "setMasterAllCatTables", "(Ljava/util/List;)V", "master_cat", "getMaster_cat", "setMaster_cat", "mastercatlist", "Lcom/utkarshnew/android/table/MasterCat;", "getMastercatlist", "setMastercatlist", "newCourseData", "Lcom/utkarshnew/android/feeds/dataclass/NewCourseData;", "getNewCourseData", "setNewCourseData", "no_data_found_RL", "Landroid/widget/RelativeLayout;", "getNo_data_found_RL", "()Landroid/widget/RelativeLayout;", "setNo_data_found_RL", "(Landroid/widget/RelativeLayout;)V", "page", "getPage", "setPage", "pinnedPost", "pinnedPostList", "getPinnedPostList", "setPinnedPostList", "response_booelan", "getResponse_booelan", "setResponse_booelan", "section_posiiton", "getSection_posiiton", "setSection_posiiton", "selected_master_cat", "getSelected_master_cat", "setSelected_master_cat", "selectedsub_all_cat", "getSelectedsub_all_cat", "setSelectedsub_all_cat", "sub_cat", "getSub_cat", "setSub_cat", "testResultList", "Lcom/utkarshnew/android/feeds/dataclass/TestResult;", "getTestResultList", "setTestResultList", "utkashRoom", "Lcom/utkarshnew/android/Room/UtkashRoom;", "getUtkashRoom", "()Lcom/utkarshnew/android/Room/UtkashRoom;", "setUtkashRoom", "(Lcom/utkarshnew/android/Room/UtkashRoom;)V", "createApiBodyData", "", "hideProgressView", "observer", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMenuItemClick", "item", "Landroid/view/MenuItem;", "showProgressView", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class PinnedPostActivity extends androidx.appcompat.app.AppCompatActivity implements androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener {
    @org.jetbrains.annotations.Nullable()
    private android.widget.Button backBtn;
    private org.json.JSONObject feedJsonObject;
    private boolean isPullToRefresh = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String liveClassStatus = "0";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String liveTestStatus = "0";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String section_posiiton = "0";
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Room.UtkashRoom utkashRoom;
    private boolean response_booelan = false;
    private int limitdata = 0;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.feeds.adapters.FeedAdapter feedAdapter;
    private boolean loading = true;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout no_data_found_RL;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy feedViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String main_cat = "";
    @org.jetbrains.annotations.Nullable()
    private androidx.recyclerview.widget.LinearLayoutManager manager;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sub_cat = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String master_cat = "";
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> selected_master_cat;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> selectedsub_all_cat;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> datalist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> pinnedPostList;
    private java.lang.String pinnedPost = "0";
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> feedlist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.NewCourseData> newCourseData;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.TestResult> testResultList;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.home.livetest.LiveTestData> liveTestData;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.home.liveclasses.Datum> liveClassData;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.BannerData> bannert_list;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<? extends com.utkarshnew.android.table.MasteAllCatTable> masterAllCatTables;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<? extends com.utkarshnew.android.table.MasterCat> mastercatlist;
    private int page = 1;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.databinding.ActivityFeedsBinding feedsBinding;
    
    public PinnedPostActivity() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.Button getBackBtn() {
        return null;
    }
    
    public final void setBackBtn(@org.jetbrains.annotations.Nullable()
    android.widget.Button p0) {
    }
    
    public final boolean isPullToRefresh() {
        return false;
    }
    
    public final void setPullToRefresh(boolean p0) {
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
    public final java.lang.String getSection_posiiton() {
        return null;
    }
    
    public final void setSection_posiiton(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.Room.UtkashRoom getUtkashRoom() {
        return null;
    }
    
    public final void setUtkashRoom(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Room.UtkashRoom p0) {
    }
    
    public final boolean getResponse_booelan() {
        return false;
    }
    
    public final void setResponse_booelan(boolean p0) {
    }
    
    public final int getLimitdata() {
        return 0;
    }
    
    public final void setLimitdata(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.feeds.adapters.FeedAdapter getFeedAdapter() {
        return null;
    }
    
    public final void setFeedAdapter(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.adapters.FeedAdapter p0) {
    }
    
    public final boolean getLoading() {
        return false;
    }
    
    public final void setLoading(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getNo_data_found_RL() {
        return null;
    }
    
    public final void setNo_data_found_RL(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.feeds.viewmodel.FeedViewModel getFeedViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMain_cat() {
        return null;
    }
    
    public final void setMain_cat(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.recyclerview.widget.LinearLayoutManager getManager() {
        return null;
    }
    
    public final void setManager(@org.jetbrains.annotations.Nullable()
    androidx.recyclerview.widget.LinearLayoutManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSub_cat() {
        return null;
    }
    
    public final void setSub_cat(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMaster_cat() {
        return null;
    }
    
    public final void setMaster_cat(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> getSelected_master_cat() {
        return null;
    }
    
    public final void setSelected_master_cat(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> getSelectedsub_all_cat() {
        return null;
    }
    
    public final void setSelectedsub_all_cat(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> getDatalist() {
        return null;
    }
    
    public final void setDatalist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> getPinnedPostList() {
        return null;
    }
    
    public final void setPinnedPostList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> getFeedlist() {
        return null;
    }
    
    public final void setFeedlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.NewCourseData> getNewCourseData() {
        return null;
    }
    
    public final void setNewCourseData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.NewCourseData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.TestResult> getTestResultList() {
        return null;
    }
    
    public final void setTestResultList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.TestResult> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.home.livetest.LiveTestData> getLiveTestData() {
        return null;
    }
    
    public final void setLiveTestData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.home.livetest.LiveTestData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.home.liveclasses.Datum> getLiveClassData() {
        return null;
    }
    
    public final void setLiveClassData(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.home.liveclasses.Datum> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.BannerData> getBannert_list() {
        return null;
    }
    
    public final void setBannert_list(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.BannerData> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.utkarshnew.android.table.MasteAllCatTable> getMasterAllCatTables() {
        return null;
    }
    
    public final void setMasterAllCatTables(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.utkarshnew.android.table.MasteAllCatTable> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.utkarshnew.android.table.MasterCat> getMastercatlist() {
        return null;
    }
    
    public final void setMastercatlist(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends com.utkarshnew.android.table.MasterCat> p0) {
    }
    
    public final int getPage() {
        return 0;
    }
    
    public final void setPage(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.databinding.ActivityFeedsBinding getFeedsBinding() {
        return null;
    }
    
    public final void setFeedsBinding(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.databinding.ActivityFeedsBinding p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void createApiBodyData() {
    }
    
    public final void observer() {
    }
    
    public final void showProgressView() {
    }
    
    public final void hideProgressView() {
    }
    
    @java.lang.Override()
    public boolean onMenuItemClick(@org.jetbrains.annotations.Nullable()
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
}