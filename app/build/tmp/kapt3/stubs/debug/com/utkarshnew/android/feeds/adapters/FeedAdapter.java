package com.utkarshnew.android.feeds.adapters;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00de\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u00032\u00020\u00042\u00020\u0005:\u0018\u0096\u0001\u0097\u0001\u0098\u0001\u0099\u0001\u009a\u0001\u009b\u0001\u009c\u0001\u009d\u0001\u009e\u0001\u009f\u0001\u00a0\u0001\u00a1\u0001B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ&\u0010U\u001a\u00020V2\b\u0010W\u001a\u0004\u0018\u00010\"2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"H\u0016J.\u0010Z\u001a\u00020V2\b\u0010W\u001a\u0004\u0018\u00010[2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"2\u0006\u0010\\\u001a\u00020\u0010H\u0016J\u000e\u0010]\u001a\u00020V2\u0006\u0010^\u001a\u00020[J\u0014\u0010_\u001a\u00020V2\f\u0010`\u001a\b\u0012\u0004\u0012\u0002060(J\u0018\u0010a\u001a\u00020V2\u0006\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020eH\u0002J\u0006\u0010f\u001a\u00020VJ\u0006\u0010g\u001a\u00020VJ\u0010\u0010h\u001a\u00020V2\u0006\u0010i\u001a\u00020\"H\u0002J,\u0010j\u001a\b\u0012\u0004\u0012\u00020\"0k2\b\u0010X\u001a\u0004\u0018\u00010\"2\b\u0010Y\u001a\u0004\u0018\u00010\"2\b\u0010l\u001a\u0004\u0018\u00010mH\u0016J\u000e\u0010n\u001a\u00020V2\u0006\u0010o\u001a\u00020pJ\u0012\u0010q\u001a\u00020r2\b\u0010s\u001a\u0004\u0018\u00010\"H\u0017J\b\u0010t\u001a\u000200H\u0016J\u0010\u0010u\u001a\u0002002\u0006\u0010v\u001a\u000200H\u0016J\b\u0010w\u001a\u00020VH\u0002J \u0010x\u001a\u00020c2\u0006\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u0002002\u0006\u0010|\u001a\u00020\"H\u0007J\"\u0010}\u001a\u00020V2\u0006\u0010~\u001a\u00020\u007f2\u0007\u0010\u0080\u0001\u001a\u0002002\u0007\u0010\u0081\u0001\u001a\u000200H\u0016J\u0018\u0010\u0082\u0001\u001a\u00020V2\u0006\u0010I\u001a\u00020J2\u0007\u0010\u0083\u0001\u001a\u00020\"J\u001a\u0010\u0084\u0001\u001a\u00020V2\u0007\u0010\u0085\u0001\u001a\u00020\u00022\u0006\u0010v\u001a\u000200H\u0017J\u001c\u0010\u0086\u0001\u001a\u00020\u00022\b\u0010\u0087\u0001\u001a\u00030\u0088\u00012\u0007\u0010\u0089\u0001\u001a\u000200H\u0016J\u0017\u0010\u008a\u0001\u001a\u00020V2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010v\u001a\u000200J\u0007\u0010\u008b\u0001\u001a\u00020VJ\u001b\u0010\u008c\u0001\u001a\u00020V2\b\u0010\u008d\u0001\u001a\u00030\u008e\u00012\b\u0010\u008f\u0001\u001a\u00030\u0090\u0001J\u0011\u0010\u0091\u0001\u001a\u00020V2\u0006\u0010d\u001a\u00020eH\u0003J\u0007\u0010\u0092\u0001\u001a\u00020VJ\u0011\u0010\u0093\u0001\u001a\u00020V2\b\u0010\u0094\u0001\u001a\u00030\u008e\u0001J\u0011\u0010\u0095\u0001\u001a\u00020V2\b\u0010\u0094\u0001\u001a\u00030\u008e\u0001R\u0014\u0010\t\u001a\u00020\nX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\nX\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001a\u0010!\u001a\u00020\"X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u0017\u0010\'\u001a\b\u0012\u0004\u0012\u00020)0(\u00a2\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010\bR\u001a\u0010/\u001a\u000200X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u00102\"\u0004\b3\u00104R \u00105\u001a\b\u0012\u0004\u0012\u0002060(X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010+\"\u0004\b8\u00109R\u001a\u0010:\u001a\u000200X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u00102\"\u0004\b<\u00104R\u001a\u0010=\u001a\u000200X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u00102\"\u0004\b?\u00104R\u001c\u0010@\u001a\u0004\u0018\u00010AX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u000200X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u00102\"\u0004\bH\u00104R\u001c\u0010I\u001a\u0004\u0018\u00010JX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bK\u0010L\"\u0004\bM\u0010NR\u001c\u0010O\u001a\u0004\u0018\u00010PX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010R\"\u0004\bS\u0010T\u00a8\u0006\u00a2\u0001"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/text/Html$ImageGetter;", "Lcom/utkarshnew/android/Utils/Network/NetworkCall$MyNetworkCallBack;", "Lcom/utkarshnew/android/feeds/OptionItem;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "DELAY_MS", "", "getDELAY_MS", "()J", "PERIOD_MS", "getPERIOD_MS", "booleanlike", "", "getBooleanlike", "()Z", "setBooleanlike", "(Z)V", "commentAdapter", "Lcom/utkarshnew/android/feeds/adapters/CommentAdapter;", "getCommentAdapter", "()Lcom/utkarshnew/android/feeds/adapters/CommentAdapter;", "setCommentAdapter", "(Lcom/utkarshnew/android/feeds/adapters/CommentAdapter;)V", "comment_recyerler", "Landroidx/recyclerview/widget/RecyclerView;", "getComment_recyerler", "()Landroidx/recyclerview/widget/RecyclerView;", "setComment_recyerler", "(Landroidx/recyclerview/widget/RecyclerView;)V", "comment_txt", "", "getComment_txt", "()Ljava/lang/String;", "setComment_txt", "(Ljava/lang/String;)V", "commentlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/feeds/dataclass/comment/Data;", "getCommentlist", "()Ljava/util/ArrayList;", "getContext", "()Landroid/content/Context;", "setContext", "currentPage", "", "getCurrentPage", "()I", "setCurrentPage", "(I)V", "feedatalist", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "getFeedatalist", "setFeedatalist", "(Ljava/util/ArrayList;)V", "item_pos", "getItem_pos", "setItem_pos", "lang", "getLang", "setLang", "networkCall", "Lcom/utkarshnew/android/Utils/Network/NetworkCall;", "getNetworkCall", "()Lcom/utkarshnew/android/Utils/Network/NetworkCall;", "setNetworkCall", "(Lcom/utkarshnew/android/Utils/Network/NetworkCall;)V", "option_index", "getOption_index", "setOption_index", "textView", "Landroid/widget/TextView;", "getTextView", "()Landroid/widget/TextView;", "setTextView", "(Landroid/widget/TextView;)V", "timer", "Ljava/util/Timer;", "getTimer", "()Ljava/util/Timer;", "setTimer", "(Ljava/util/Timer;)V", "ErrorCallBack", "", "jsonstring", "apitype", "typeApi", "SuccessCallBack", "Lorg/json/JSONObject;", "showprogress", "addComment", "jsonObject1", "addFeed", "datalist", "addSectionView", "sectionListLL", "Landroid/widget/LinearLayout;", "instructionData", "Lcom/utkarshnew/android/testmodule/model/InstructionData;", "attempt_mcq", "change_posiiton", "createBodyData", "type", "getAPIB", "Lretrofit2/Call;", "service", "Lcom/utkarshnew/android/Utils/Network/APIInterface;", "getCommentList", "dataJsonObject", "Lorg/json/JSONArray;", "getDrawable", "Landroid/graphics/drawable/Drawable;", "s", "getItemCount", "getItemViewType", "position", "getintotestseries", "initSectionListView", "testSectionInst", "Lcom/utkarshnew/android/testmodule/model/TestSectionInst;", "tag", "hide_inst_time", "itemSelect", "option", "Lcom/utkarshnew/android/feeds/dataclass/Option;", "index", "feedlistpos", "makeLinks", "link", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "open_comment_layout", "pinPost", "showPopMenuForLangauge", "v", "Landroid/view/View;", "testBasicInst", "Lcom/utkarshnew/android/testmodule/model/TestBasicInst;", "showPopUp", "unPinPost", "viewAllTest", "view", "viewAllclass", "ArticleVm", "AudioVM", "Banner_Vm", "ImageVm", "LinkVM", "NewCourseVm", "NewLiveTestVm", "NewLiveclassVm", "NewTestResultVm", "QuestionVM", "QuizVM", "VideoVm", "app_debug"})
public final class FeedAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder> implements android.text.Html.ImageGetter, com.utkarshnew.android.Utils.Network.NetworkCall.MyNetworkCallBack, com.utkarshnew.android.feeds.OptionItem {
    @org.jetbrains.annotations.NotNull()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> feedatalist;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Utils.Network.NetworkCall networkCall;
    private int item_pos = 0;
    private int lang = 0;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String comment_txt = "";
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.feeds.adapters.CommentAdapter commentAdapter;
    private int option_index = 0;
    private boolean booleanlike = false;
    private int currentPage = 0;
    @org.jetbrains.annotations.Nullable()
    private java.util.Timer timer;
    private final long DELAY_MS = 500L;
    private final long PERIOD_MS = 3000L;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView textView;
    @org.jetbrains.annotations.Nullable()
    private androidx.recyclerview.widget.RecyclerView comment_recyerler;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.comment.Data> commentlist = null;
    
    public FeedAdapter(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
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
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> getFeedatalist() {
        return null;
    }
    
    public final void setFeedatalist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Utils.Network.NetworkCall getNetworkCall() {
        return null;
    }
    
    public final void setNetworkCall(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Utils.Network.NetworkCall p0) {
    }
    
    public final int getItem_pos() {
        return 0;
    }
    
    public final void setItem_pos(int p0) {
    }
    
    public final int getLang() {
        return 0;
    }
    
    public final void setLang(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getComment_txt() {
        return null;
    }
    
    public final void setComment_txt(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.feeds.adapters.CommentAdapter getCommentAdapter() {
        return null;
    }
    
    public final void setCommentAdapter(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.adapters.CommentAdapter p0) {
    }
    
    public final int getOption_index() {
        return 0;
    }
    
    public final void setOption_index(int p0) {
    }
    
    public final boolean getBooleanlike() {
        return false;
    }
    
    public final void setBooleanlike(boolean p0) {
    }
    
    public final int getCurrentPage() {
        return 0;
    }
    
    public final void setCurrentPage(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Timer getTimer() {
        return null;
    }
    
    public final void setTimer(@org.jetbrains.annotations.Nullable()
    java.util.Timer p0) {
    }
    
    public final long getDELAY_MS() {
        return 0L;
    }
    
    public final long getPERIOD_MS() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getTextView() {
        return null;
    }
    
    public final void setTextView(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.recyclerview.widget.RecyclerView getComment_recyerler() {
        return null;
    }
    
    public final void setComment_recyerler(@org.jetbrains.annotations.Nullable()
    androidx.recyclerview.widget.RecyclerView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.comment.Data> getCommentlist() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "SimpleDateFormat"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
    }
    
    private final void createBodyData(java.lang.String type) {
    }
    
    public final void open_comment_layout(@org.jetbrains.annotations.NotNull()
    android.content.Context context, int position) {
    }
    
    public final void makeLinks(@org.jetbrains.annotations.NotNull()
    android.widget.TextView textView, @org.jetbrains.annotations.NotNull()
    java.lang.String link) {
    }
    
    @java.lang.Override()
    public int getItemViewType(int position) {
        return 0;
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public retrofit2.Call<java.lang.String> getAPIB(@org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi, @org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Utils.Network.APIInterface service) {
        return null;
    }
    
    @java.lang.Override()
    public void SuccessCallBack(@org.jetbrains.annotations.Nullable()
    org.json.JSONObject jsonstring, @org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi, boolean showprogress) {
    }
    
    public final void showPopMenuForLangauge(@org.jetbrains.annotations.NotNull()
    android.view.View v, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.testmodule.model.TestBasicInst testBasicInst) {
    }
    
    private final void getintotestseries() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void showPopUp(com.utkarshnew.android.testmodule.model.InstructionData instructionData) {
    }
    
    private final void addSectionView(android.widget.LinearLayout sectionListLL, com.utkarshnew.android.testmodule.model.InstructionData instructionData) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    public final android.widget.LinearLayout initSectionListView(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.testmodule.model.TestSectionInst testSectionInst, int tag, @org.jetbrains.annotations.NotNull()
    java.lang.String hide_inst_time) {
        return null;
    }
    
    @java.lang.Override()
    public void ErrorCallBack(@org.jetbrains.annotations.Nullable()
    java.lang.String jsonstring, @org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi) {
    }
    
    @java.lang.Override()
    public void itemSelect(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.feeds.dataclass.Option option, int index, int feedlistpos) {
    }
    
    public final void addFeed(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> datalist) {
    }
    
    public final void change_posiiton() {
    }
    
    public final void getCommentList(@org.jetbrains.annotations.NotNull()
    org.json.JSONArray dataJsonObject) {
    }
    
    public final void addComment(@org.jetbrains.annotations.NotNull()
    org.json.JSONObject jsonObject1) {
    }
    
    public final void attempt_mcq() {
    }
    
    public final void viewAllclass(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    public final void viewAllTest(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables"})
    @java.lang.Override()
    public android.graphics.drawable.Drawable getDrawable(@org.jetbrains.annotations.Nullable()
    java.lang.String s) {
        return null;
    }
    
    public final void pinPost() {
    }
    
    public final void unPinPost() {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$NewCourseVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "newCourseVmBinding", "Lcom/utkarshnew/android/databinding/NewCourseVmBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/NewCourseVmBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class NewCourseVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.NewCourseVmBinding newCourseVmBinding = null;
        
        public NewCourseVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.NewCourseVmBinding newCourseVmBinding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$Banner_Vm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "bannerViewBinding", "Lcom/utkarshnew/android/databinding/BannerViewBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/BannerViewBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class Banner_Vm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.BannerViewBinding bannerViewBinding = null;
        
        public Banner_Vm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.BannerViewBinding bannerViewBinding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$NewTestResultVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "newlivetestresult", "Lcom/utkarshnew/android/databinding/NewTestresultVmBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/NewTestresultVmBinding;)V", "bind", "", "testResult", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class NewTestResultVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.NewTestresultVmBinding newlivetestresult = null;
        
        public NewTestResultVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.NewTestresultVmBinding newlivetestresult) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data testResult) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$NewLiveTestVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "liveTestVmBinding", "Lcom/utkarshnew/android/databinding/LiveTestVmBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/LiveTestVmBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class NewLiveTestVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private com.utkarshnew.android.databinding.LiveTestVmBinding liveTestVmBinding;
        
        public NewLiveTestVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.LiveTestVmBinding liveTestVmBinding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$NewLiveclassVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "liveClassVmBinding", "Lcom/utkarshnew/android/databinding/LiveClassVmBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/LiveClassVmBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class NewLiveclassVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.LiveClassVmBinding liveClassVmBinding = null;
        
        public NewLiveclassVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.LiveClassVmBinding liveClassVmBinding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$ArticleVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "articleVm", "Lcom/utkarshnew/android/databinding/ArticleVmBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/ArticleVmBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class ArticleVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.ArticleVmBinding articleVm = null;
        
        public ArticleVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.ArticleVmBinding articleVm) {
            super(null);
        }
        
        @android.annotation.SuppressLint(value = {"SetTextI18n", "ClickableViewAccessibility"})
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$LinkVM;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "linkvm", "Lcom/utkarshnew/android/databinding/LinkViewBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/LinkViewBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class LinkVM extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.LinkViewBinding linkvm = null;
        
        public LinkVM(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.LinkViewBinding linkvm) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$ImageVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "imageVm", "Lcom/utkarshnew/android/databinding/PostImageBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/PostImageBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class ImageVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.PostImageBinding imageVm = null;
        
        public ImageVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.PostImageBinding imageVm) {
            super(null);
        }
        
        @android.annotation.SuppressLint(value = {"SetTextI18n"})
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$VideoVm;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "videoVm", "Lcom/utkarshnew/android/databinding/VideoPostBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/VideoPostBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class VideoVm extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.VideoPostBinding videoVm = null;
        
        public VideoVm(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.VideoPostBinding videoVm) {
            super(null);
        }
        
        @android.annotation.SuppressLint(value = {"SetTextI18n"})
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0087\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$AudioVM;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "audioVM", "Lcom/utkarshnew/android/databinding/AudioPostBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/AudioPostBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class AudioVM extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.AudioPostBinding audioVM = null;
        
        public AudioVM(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.AudioPostBinding audioVM) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$QuestionVM;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "questionVM", "Lcom/utkarshnew/android/databinding/QuestionViewBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/QuestionViewBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class QuestionVM extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.QuestionViewBinding questionVM = null;
        
        public QuestionVM(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.QuestionViewBinding questionVM) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/utkarshnew/android/feeds/adapters/FeedAdapter$QuizVM;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "quizVM", "Lcom/utkarshnew/android/databinding/QuizViewBinding;", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;Lcom/utkarshnew/android/databinding/QuizViewBinding;)V", "bind", "", "data", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "app_debug"})
    public final class QuizVM extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.QuizViewBinding quizVM = null;
        
        public QuizVM(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.QuizViewBinding quizVM) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.Data data) {
        }
    }
}