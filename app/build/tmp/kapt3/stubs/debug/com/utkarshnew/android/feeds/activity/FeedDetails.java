package com.utkarshnew.android.feeds.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00ec\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u00032\u00020\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J&\u0010F\u001a\u00020G2\b\u0010H\u001a\u0004\u0018\u00010\u00172\b\u0010I\u001a\u0004\u0018\u00010\u00172\b\u0010J\u001a\u0004\u0018\u00010\u0017H\u0016J.\u0010K\u001a\u00020G2\b\u0010H\u001a\u0004\u0018\u00010L2\b\u0010I\u001a\u0004\u0018\u00010\u00172\b\u0010J\u001a\u0004\u0018\u00010\u00172\u0006\u0010M\u001a\u00020\rH\u0016J\u0018\u0010N\u001a\u00020G2\u0006\u0010O\u001a\u00020P2\u0006\u0010Q\u001a\u00020RH\u0002J\u000e\u0010S\u001a\u00020G2\u0006\u0010T\u001a\u00020AJ\u0006\u0010U\u001a\u00020GJ\u0010\u0010V\u001a\u00020G2\u0006\u0010W\u001a\u00020\u0017H\u0002J,\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00170Y2\b\u0010I\u001a\u0004\u0018\u00010\u00172\b\u0010J\u001a\u0004\u0018\u00010\u00172\b\u0010Z\u001a\u0004\u0018\u00010[H\u0016J\u0012\u0010\\\u001a\u00020]2\b\u0010^\u001a\u0004\u0018\u00010\u0017H\u0017J\u0006\u0010_\u001a\u00020GJ\b\u0010`\u001a\u00020GH\u0002J\"\u0010a\u001a\u0004\u0018\u00010P2\u0006\u0010b\u001a\u00020c2\u0006\u0010d\u001a\u00020(2\u0006\u0010e\u001a\u00020\u0017H\u0007J \u0010f\u001a\u00020G2\u0006\u0010g\u001a\u00020h2\u0006\u0010i\u001a\u00020(2\u0006\u0010j\u001a\u00020(H\u0016J\u0016\u0010k\u001a\u00020G2\u0006\u0010l\u001a\u00020m2\u0006\u0010n\u001a\u00020\u0017J\u0006\u0010o\u001a\u00020GJ\u0012\u0010p\u001a\u00020G2\b\u0010q\u001a\u0004\u0018\u00010rH\u0015J\u000e\u0010s\u001a\u00020G2\u0006\u0010t\u001a\u00020uJ\u000e\u0010v\u001a\u00020G2\u0006\u0010T\u001a\u00020AJ\u0010\u0010w\u001a\u00020G2\u0006\u0010T\u001a\u00020AH\u0002J\u0010\u0010x\u001a\u00020G2\u0006\u0010T\u001a\u00020AH\u0002J\u0010\u0010y\u001a\u00020G2\u0006\u0010T\u001a\u00020AH\u0003J\u0010\u0010z\u001a\u00020G2\u0006\u0010T\u001a\u00020AH\u0003J\u0010\u0010{\u001a\u00020G2\u0006\u0010T\u001a\u00020AH\u0002J\u0010\u0010|\u001a\u00020G2\u0006\u0010T\u001a\u00020AH\u0002J\u0018\u0010}\u001a\u00020G2\u0006\u0010~\u001a\u00020\u007f2\b\u0010\u0080\u0001\u001a\u00030\u0081\u0001J\u0011\u0010\u0082\u0001\u001a\u00020G2\u0006\u0010Q\u001a\u00020RH\u0003J\t\u0010\u0083\u0001\u001a\u00020GH\u0002R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0019\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001d\u001a\u00020\u001e8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b\u001f\u0010 R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u000f\"\u0004\b&\u0010\u0011R\u001a\u0010\'\u001a\u00020(X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,R\u001c\u0010-\u001a\u0004\u0018\u00010.X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u00100\"\u0004\b1\u00102R\u001c\u00103\u001a\u0004\u0018\u000104X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u00106\"\u0004\b7\u00108R\u000e\u00109\u001a\u00020(X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010:\u001a\u0004\u0018\u00010;X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u0010\u0010@\u001a\u0004\u0018\u00010AX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u0004\u0018\u00010\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010D\u001a\u0004\u0018\u00010EX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0084\u0001"}, d2 = {"Lcom/utkarshnew/android/feeds/activity/FeedDetails;", "Landroidx/appcompat/app/AppCompatActivity;", "Landroid/text/Html$ImageGetter;", "Lcom/utkarshnew/android/feeds/OptionItem;", "Lcom/utkarshnew/android/Utils/Network/NetworkCall$MyNetworkCallBack;", "()V", "backBtn", "Landroid/widget/Button;", "getBackBtn", "()Landroid/widget/Button;", "setBackBtn", "(Landroid/widget/Button;)V", "booleanlike", "", "getBooleanlike", "()Z", "setBooleanlike", "(Z)V", "commentAdapter", "Lcom/utkarshnew/android/feeds/adapters/CommentAdapter;", "comment_recyerler", "Landroidx/recyclerview/widget/RecyclerView;", "comment_txt", "", "commentlist", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/feeds/dataclass/comment/Data;", "getCommentlist", "()Ljava/util/ArrayList;", "feedDetailViewModel", "Lcom/utkarshnew/android/feeds/viewmodel/FeedDetailViewModel;", "getFeedDetailViewModel", "()Lcom/utkarshnew/android/feeds/viewmodel/FeedDetailViewModel;", "feedDetailViewModel$delegate", "Lkotlin/Lazy;", "feedDetailsBinding", "Lcom/utkarshnew/android/databinding/ActivityFeedDetailsBinding;", "is_postexist", "set_postexist", "lang", "", "getLang", "()I", "setLang", "(I)V", "networkCall", "Lcom/utkarshnew/android/Utils/Network/NetworkCall;", "getNetworkCall", "()Lcom/utkarshnew/android/Utils/Network/NetworkCall;", "setNetworkCall", "(Lcom/utkarshnew/android/Utils/Network/NetworkCall;)V", "no_data_found_RL", "Landroid/widget/RelativeLayout;", "getNo_data_found_RL", "()Landroid/widget/RelativeLayout;", "setNo_data_found_RL", "(Landroid/widget/RelativeLayout;)V", "option_index", "optiondata", "Lcom/utkarshnew/android/feeds/dataclass/Json;", "getOptiondata", "()Lcom/utkarshnew/android/feeds/dataclass/Json;", "setOptiondata", "(Lcom/utkarshnew/android/feeds/dataclass/Json;)V", "postDataTable", "Lcom/utkarshnew/android/table/PostDataTable;", "postId", "test_id", "utkashRoom", "Lcom/utkarshnew/android/Room/UtkashRoom;", "ErrorCallBack", "", "jsonstring", "apitype", "typeApi", "SuccessCallBack", "Lorg/json/JSONObject;", "showprogress", "addSectionView", "sectionListLL", "Landroid/widget/LinearLayout;", "instructionData", "Lcom/utkarshnew/android/testmodule/model/InstructionData;", "bind", "data", "createApiBodyData", "createBodyData", "type", "getAPIB", "Lretrofit2/Call;", "service", "Lcom/utkarshnew/android/Utils/Network/APIInterface;", "getDrawable", "Landroid/graphics/drawable/Drawable;", "s", "getPostData", "getintotestseries", "initSectionListView", "testSectionInst", "Lcom/utkarshnew/android/testmodule/model/TestSectionInst;", "tag", "hide_inst_time", "itemSelect", "option", "Lcom/utkarshnew/android/feeds/dataclass/Option;", "index", "feedlistpos", "makeLinks", "textView", "Landroid/widget/TextView;", "link", "observer", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "open_comment_layout", "context", "Landroid/content/Context;", "setArticle", "setImage", "setLink", "setQuestion", "setQuiz", "setaudio", "setvideo", "showPopMenuForLangauge", "v", "Landroid/view/View;", "testBasicInst", "Lcom/utkarshnew/android/testmodule/model/TestBasicInst;", "showPopUp", "visibileViewType", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class FeedDetails extends androidx.appcompat.app.AppCompatActivity implements android.text.Html.ImageGetter, com.utkarshnew.android.feeds.OptionItem, com.utkarshnew.android.Utils.Network.NetworkCall.MyNetworkCallBack {
    private com.utkarshnew.android.databinding.ActivityFeedDetailsBinding feedDetailsBinding;
    private com.utkarshnew.android.Room.UtkashRoom utkashRoom;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Utils.Network.NetworkCall networkCall;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.feeds.dataclass.Json optiondata;
    private int lang = 0;
    private boolean booleanlike = false;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout no_data_found_RL;
    @org.jetbrains.annotations.Nullable()
    private android.widget.Button backBtn;
    private boolean is_postexist = false;
    private java.lang.String postId;
    private com.utkarshnew.android.table.PostDataTable postDataTable;
    private java.lang.String comment_txt = "";
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy feedDetailViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.comment.Data> commentlist = null;
    private com.utkarshnew.android.feeds.adapters.CommentAdapter commentAdapter;
    private androidx.recyclerview.widget.RecyclerView comment_recyerler;
    private int option_index = 0;
    private java.lang.String test_id = "";
    
    public FeedDetails() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Utils.Network.NetworkCall getNetworkCall() {
        return null;
    }
    
    public final void setNetworkCall(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Utils.Network.NetworkCall p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.feeds.dataclass.Json getOptiondata() {
        return null;
    }
    
    public final void setOptiondata(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.feeds.dataclass.Json p0) {
    }
    
    public final int getLang() {
        return 0;
    }
    
    public final void setLang(int p0) {
    }
    
    public final boolean getBooleanlike() {
        return false;
    }
    
    public final void setBooleanlike(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.RelativeLayout getNo_data_found_RL() {
        return null;
    }
    
    public final void setNo_data_found_RL(@org.jetbrains.annotations.Nullable()
    android.widget.RelativeLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.Button getBackBtn() {
        return null;
    }
    
    public final void setBackBtn(@org.jetbrains.annotations.Nullable()
    android.widget.Button p0) {
    }
    
    public final boolean is_postexist() {
        return false;
    }
    
    public final void set_postexist(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.utkarshnew.android.feeds.viewmodel.FeedDetailViewModel getFeedDetailViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.comment.Data> getCommentlist() {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"NotifyDataSetChanged"})
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void createApiBodyData() {
    }
    
    public final void observer() {
    }
    
    public final void getPostData() {
    }
    
    private final void visibileViewType() {
    }
    
    public final void bind(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.table.PostDataTable data) {
    }
    
    public final void open_comment_layout(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    private final void setaudio(com.utkarshnew.android.table.PostDataTable data) {
    }
    
    private final void setvideo(com.utkarshnew.android.table.PostDataTable data) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void setQuiz(com.utkarshnew.android.table.PostDataTable data) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void setQuestion(com.utkarshnew.android.table.PostDataTable data) {
    }
    
    private final void setLink(com.utkarshnew.android.table.PostDataTable data) {
    }
    
    public final void makeLinks(@org.jetbrains.annotations.NotNull()
    android.widget.TextView textView, @org.jetbrains.annotations.NotNull()
    java.lang.String link) {
    }
    
    private final void setImage(com.utkarshnew.android.table.PostDataTable data) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables"})
    @java.lang.Override()
    public android.graphics.drawable.Drawable getDrawable(@org.jetbrains.annotations.Nullable()
    java.lang.String s) {
        return null;
    }
    
    public final void setArticle(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.table.PostDataTable data) {
    }
    
    @java.lang.Override()
    public void itemSelect(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.feeds.dataclass.Option option, int index, int feedlistpos) {
    }
    
    private final void createBodyData(java.lang.String type) {
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
    
    @java.lang.Override()
    public void ErrorCallBack(@org.jetbrains.annotations.Nullable()
    java.lang.String jsonstring, @org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void showPopUp(com.utkarshnew.android.testmodule.model.InstructionData instructionData) {
    }
    
    private final void addSectionView(android.widget.LinearLayout sectionListLL, com.utkarshnew.android.testmodule.model.InstructionData instructionData) {
    }
    
    public final void showPopMenuForLangauge(@org.jetbrains.annotations.NotNull()
    android.view.View v, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.testmodule.model.TestBasicInst testBasicInst) {
    }
    
    private final void getintotestseries() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    public final android.widget.LinearLayout initSectionListView(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.testmodule.model.TestSectionInst testSectionInst, int tag, @org.jetbrains.annotations.NotNull()
    java.lang.String hide_inst_time) {
        return null;
    }
}