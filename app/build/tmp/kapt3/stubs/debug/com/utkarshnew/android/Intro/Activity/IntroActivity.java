package com.utkarshnew.android.Intro.Activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u00d2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J+\u0010\u008e\u0001\u001a\u00030\u008f\u00012\t\u0010\u0090\u0001\u001a\u0004\u0018\u00010A2\t\u0010\u0091\u0001\u001a\u0004\u0018\u00010A2\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010AH\u0016J6\u0010\u0093\u0001\u001a\u00030\u008f\u00012\n\u0010\u0094\u0001\u001a\u0005\u0018\u00010\u0095\u00012\t\u0010\u0091\u0001\u001a\u0004\u0018\u00010A2\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010A2\b\u0010\u0096\u0001\u001a\u00030\u0097\u0001H\u0016J4\u0010\u0098\u0001\u001a\r\u0012\u0006\u0012\u0004\u0018\u00010A\u0018\u00010\u0099\u00012\t\u0010\u0091\u0001\u001a\u0004\u0018\u00010A2\t\u0010\u0092\u0001\u001a\u0004\u0018\u00010A2\b\u0010\u009a\u0001\u001a\u00030\u009b\u0001H\u0016J\b\u0010\u009c\u0001\u001a\u00030\u008f\u0001J\u001a\u0010\u009d\u0001\u001a\u00030\u008f\u00012\u0006\u00104\u001a\u0002052\b\u0010\u009e\u0001\u001a\u00030\u0097\u0001J\n\u0010\u009f\u0001\u001a\u00030\u008f\u0001H\u0016J\u0015\u0010\u00a0\u0001\u001a\u00030\u008f\u00012\t\u0010\u00a1\u0001\u001a\u0004\u0018\u00010\u0017H\u0015J\n\u0010\u00a2\u0001\u001a\u00030\u008f\u0001H\u0003J\b\u0010\u00a3\u0001\u001a\u00030\u008f\u0001R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR \u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b\'\u0010(R*\u0010)\u001a\u0012\u0012\u0004\u0012\u00020*0\u001dj\b\u0012\u0004\u0012\u00020*`+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010 \"\u0004\b-\u0010\"R\u001c\u0010.\u001a\u0004\u0018\u00010/X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001c\u00104\u001a\u0004\u0018\u000105X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001c\u0010:\u001a\u0004\u0018\u00010;X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b@\u0010B\"\u0004\bC\u0010DR*\u0010E\u001a\u0012\u0012\u0004\u0012\u00020F0\u001dj\b\u0012\u0004\u0012\u00020F`+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010 \"\u0004\bH\u0010\"R*\u0010I\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u001dj\b\u0012\u0004\u0012\u00020\u0005`+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010 \"\u0004\bK\u0010\"R*\u0010L\u001a\u0012\u0012\u0004\u0012\u00020M0\u001dj\b\u0012\u0004\u0012\u00020M`+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010 \"\u0004\bO\u0010\"R\u001c\u0010P\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bQ\u0010\u0007\"\u0004\bR\u0010\tR\u001c\u0010S\u001a\u0004\u0018\u00010TX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010V\"\u0004\bW\u0010XR*\u0010Y\u001a\u0012\u0012\u0004\u0012\u00020T0\u001dj\b\u0012\u0004\u0012\u00020T`+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010 \"\u0004\b[\u0010\"R\u001c\u0010\\\u001a\u0004\u0018\u00010AX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b]\u0010B\"\u0004\b^\u0010DR\u001c\u0010_\u001a\u0004\u0018\u00010`X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\ba\u0010b\"\u0004\bc\u0010dR\u001c\u0010e\u001a\u0004\u0018\u00010fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bg\u0010h\"\u0004\bi\u0010jR\u001c\u0010k\u001a\u0004\u0018\u00010fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bl\u0010h\"\u0004\bm\u0010jR\u001c\u0010n\u001a\u0004\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bo\u0010\u0013\"\u0004\bp\u0010\u0015R\u001a\u0010q\u001a\u00020AX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\br\u0010B\"\u0004\bs\u0010DR\u0017\u0010t\u001a\b\u0012\u0004\u0012\u00020u0\u001d\u00a2\u0006\b\n\u0000\u001a\u0004\bv\u0010 R\u001c\u0010w\u001a\u0004\u0018\u00010xX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\by\u0010z\"\u0004\b{\u0010|R\u0014\u0010}\u001a\b\u0012\u0004\u0012\u00020M0\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010~\u001a\b\u0012\u0004\u0012\u00020M0\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u007f\u001a\u0004\u0018\u00010\u0011X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0001\u0010\u0013\"\u0005\b\u0081\u0001\u0010\u0015R\u001f\u0010\u0082\u0001\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0083\u0001\u0010\u0007\"\u0005\b\u0084\u0001\u0010\tR\u001d\u0010\u0085\u0001\u001a\u00020AX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0086\u0001\u0010B\"\u0005\b\u0087\u0001\u0010DR\"\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001\"\u0006\b\u008c\u0001\u0010\u008d\u0001\u00a8\u0006\u00a4\u0001"}, d2 = {"Lcom/utkarshnew/android/Intro/Activity/IntroActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/utkarshnew/android/Utils/Network/NetworkCall$MyNetworkCallBack;", "()V", "allsubCat", "Lcom/utkarshnew/android/Intro/SubCat;", "getAllsubCat", "()Lcom/utkarshnew/android/Intro/SubCat;", "setAllsubCat", "(Lcom/utkarshnew/android/Intro/SubCat;)V", "backPressed", "", "getBackPressed", "()J", "setBackPressed", "(J)V", "back_text", "Landroid/widget/TextView;", "getBack_text", "()Landroid/widget/TextView;", "setBack_text", "(Landroid/widget/TextView;)V", "bundle", "Landroid/os/Bundle;", "getBundle", "()Landroid/os/Bundle;", "setBundle", "(Landroid/os/Bundle;)V", "cardsArrayList", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/Model/Courses/Cards;", "getCardsArrayList", "()Ljava/util/ArrayList;", "setCardsArrayList", "(Ljava/util/ArrayList;)V", "container", "Landroid/widget/FrameLayout;", "getContainer", "()Landroid/widget/FrameLayout;", "setContainer", "(Landroid/widget/FrameLayout;)V", "courseTypeMasterTables", "Lcom/utkarshnew/android/table/CourseTypeMasterTable;", "Lkotlin/collections/ArrayList;", "getCourseTypeMasterTables", "setCourseTypeMasterTables", "data", "Lcom/utkarshnew/android/pojo/Userinfo/Data;", "getData", "()Lcom/utkarshnew/android/pojo/Userinfo/Data;", "setData", "(Lcom/utkarshnew/android/pojo/Userinfo/Data;)V", "fragment", "Landroidx/fragment/app/Fragment;", "getFragment", "()Landroidx/fragment/app/Fragment;", "setFragment", "(Landroidx/fragment/app/Fragment;)V", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "getFragmentManager", "()Landroidx/fragment/app/FragmentManager;", "setFragmentManager", "(Landroidx/fragment/app/FragmentManager;)V", "is_lang", "", "()Ljava/lang/String;", "set_lang", "(Ljava/lang/String;)V", "langlist", "Lcom/utkarshnew/android/table/LanguagesTable;", "getLanglist", "setLanglist", "maincatlist", "getMaincatlist", "setMaincatlist", "masterAllCatTables", "Lcom/utkarshnew/android/table/MasteAllCatTable;", "getMasterAllCatTables", "setMasterAllCatTables", "master_main_sub", "getMaster_main_sub", "setMaster_main_sub", "mastercat", "Lcom/utkarshnew/android/Intro/Mastercat;", "getMastercat", "()Lcom/utkarshnew/android/Intro/Mastercat;", "setMastercat", "(Lcom/utkarshnew/android/Intro/Mastercat;)V", "mastercatlist", "getMastercatlist", "setMastercatlist", "masterjsonlist", "getMasterjsonlist", "setMasterjsonlist", "networkCall", "Lcom/utkarshnew/android/Utils/Network/NetworkCall;", "getNetworkCall", "()Lcom/utkarshnew/android/Utils/Network/NetworkCall;", "setNetworkCall", "(Lcom/utkarshnew/android/Utils/Network/NetworkCall;)V", "next_back", "Landroid/widget/LinearLayout;", "getNext_back", "()Landroid/widget/LinearLayout;", "setNext_back", "(Landroid/widget/LinearLayout;)V", "next_layout", "getNext_layout", "setNext_layout", "next_text", "getNext_text", "setNext_text", "prefence", "getPrefence", "setPrefence", "prefencelist", "Lcom/utkarshnew/android/pojo/Userinfo/Data$Preferences;", "getPrefencelist", "progressBar", "Landroid/widget/ProgressBar;", "getProgressBar", "()Landroid/widget/ProgressBar;", "setProgressBar", "(Landroid/widget/ProgressBar;)V", "selected_master_cat", "selectedsub_all_cat", "step", "getStep", "setStep", "subCat", "getSubCat", "setSubCat", "subids", "getSubids", "setSubids", "utkashRoom", "Lcom/utkarshnew/android/Room/UtkashRoom;", "getUtkashRoom", "()Lcom/utkarshnew/android/Room/UtkashRoom;", "setUtkashRoom", "(Lcom/utkarshnew/android/Room/UtkashRoom;)V", "ErrorCallBack", "", "jsonstring", "apitype", "typeApi", "SuccessCallBack", "jsonObject", "Lorg/json/JSONObject;", "showprogress", "", "getAPIB", "Lretrofit2/Call;", "service", "Lcom/utkarshnew/android/Utils/Network/APIInterface;", "hit_api_master_data", "loadFragment", "isaddbackstack", "onBackPressed", "onCreate", "savedInstanceState", "onCustomBackPress", "prefencemanage", "app_debug"})
public final class IntroActivity extends androidx.appcompat.app.AppCompatActivity implements com.utkarshnew.android.Utils.Network.NetworkCall.MyNetworkCallBack {
    @org.jetbrains.annotations.Nullable()
    private android.widget.ProgressBar progressBar;
    @org.jetbrains.annotations.Nullable()
    private android.widget.FrameLayout container;
    private long backPressed = 0L;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> maincatlist;
    @org.jetbrains.annotations.NotNull()
    private final java.util.ArrayList<com.utkarshnew.android.pojo.Userinfo.Data.Preferences> prefencelist = null;
    @org.jetbrains.annotations.Nullable()
    private androidx.fragment.app.FragmentManager fragmentManager;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Utils.Network.NetworkCall networkCall;
    @org.jetbrains.annotations.Nullable()
    private androidx.fragment.app.Fragment fragment;
    @org.jetbrains.annotations.Nullable()
    private android.widget.LinearLayout next_layout;
    @org.jetbrains.annotations.Nullable()
    private android.widget.LinearLayout next_back;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView step;
    @org.jetbrains.annotations.Nullable()
    private android.os.Bundle bundle;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String prefence = "";
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView back_text;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView next_text;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Room.UtkashRoom utkashRoom;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.CourseTypeMasterTable> courseTypeMasterTables;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> masterAllCatTables;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.LanguagesTable> langlist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> mastercatlist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.Model.Courses.Cards> cardsArrayList;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String masterjsonlist = "";
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.pojo.Userinfo.Data data;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.Mastercat mastercat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCat subCat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCat allsubCat;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.Intro.SubCat master_main_sub;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String is_lang = "";
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> selected_master_cat;
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> selectedsub_all_cat;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String subids = "";
    
    public IntroActivity() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.ProgressBar getProgressBar() {
        return null;
    }
    
    public final void setProgressBar(@org.jetbrains.annotations.Nullable()
    android.widget.ProgressBar p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.FrameLayout getContainer() {
        return null;
    }
    
    public final void setContainer(@org.jetbrains.annotations.Nullable()
    android.widget.FrameLayout p0) {
    }
    
    public final long getBackPressed() {
        return 0L;
    }
    
    public final void setBackPressed(long p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> getMaincatlist() {
        return null;
    }
    
    public final void setMaincatlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.SubCat> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.pojo.Userinfo.Data.Preferences> getPrefencelist() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.fragment.app.FragmentManager getFragmentManager() {
        return null;
    }
    
    public final void setFragmentManager(@org.jetbrains.annotations.Nullable()
    androidx.fragment.app.FragmentManager p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Utils.Network.NetworkCall getNetworkCall() {
        return null;
    }
    
    public final void setNetworkCall(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Utils.Network.NetworkCall p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final androidx.fragment.app.Fragment getFragment() {
        return null;
    }
    
    public final void setFragment(@org.jetbrains.annotations.Nullable()
    androidx.fragment.app.Fragment p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.LinearLayout getNext_layout() {
        return null;
    }
    
    public final void setNext_layout(@org.jetbrains.annotations.Nullable()
    android.widget.LinearLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.LinearLayout getNext_back() {
        return null;
    }
    
    public final void setNext_back(@org.jetbrains.annotations.Nullable()
    android.widget.LinearLayout p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getStep() {
        return null;
    }
    
    public final void setStep(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.os.Bundle getBundle() {
        return null;
    }
    
    public final void setBundle(@org.jetbrains.annotations.Nullable()
    android.os.Bundle p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrefence() {
        return null;
    }
    
    public final void setPrefence(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getBack_text() {
        return null;
    }
    
    public final void setBack_text(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getNext_text() {
        return null;
    }
    
    public final void setNext_text(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Room.UtkashRoom getUtkashRoom() {
        return null;
    }
    
    public final void setUtkashRoom(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Room.UtkashRoom p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.CourseTypeMasterTable> getCourseTypeMasterTables() {
        return null;
    }
    
    public final void setCourseTypeMasterTables(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.CourseTypeMasterTable> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> getMasterAllCatTables() {
        return null;
    }
    
    public final void setMasterAllCatTables(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.table.LanguagesTable> getLanglist() {
        return null;
    }
    
    public final void setLanglist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.LanguagesTable> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> getMastercatlist() {
        return null;
    }
    
    public final void setMastercatlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Intro.Mastercat> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.Model.Courses.Cards> getCardsArrayList() {
        return null;
    }
    
    public final void setCardsArrayList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.Model.Courses.Cards> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getMasterjsonlist() {
        return null;
    }
    
    public final void setMasterjsonlist(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.pojo.Userinfo.Data getData() {
        return null;
    }
    
    public final void setData(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.pojo.Userinfo.Data p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.Mastercat getMastercat() {
        return null;
    }
    
    public final void setMastercat(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.Mastercat p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCat getSubCat() {
        return null;
    }
    
    public final void setSubCat(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCat p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCat getAllsubCat() {
        return null;
    }
    
    public final void setAllsubCat(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCat p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.utkarshnew.android.Intro.SubCat getMaster_main_sub() {
        return null;
    }
    
    public final void setMaster_main_sub(@org.jetbrains.annotations.Nullable()
    com.utkarshnew.android.Intro.SubCat p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String is_lang() {
        return null;
    }
    
    public final void set_lang(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSubids() {
        return null;
    }
    
    public final void setSubids(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    public final void loadFragment(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment, boolean isaddbackstack) {
    }
    
    @java.lang.Override()
    public void onBackPressed() {
    }
    
    public final void hit_api_master_data() {
    }
    
    public final void prefencemanage() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void onCustomBackPress() {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public retrofit2.Call<java.lang.String> getAPIB(@org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi, @org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Utils.Network.APIInterface service) {
        return null;
    }
    
    @java.lang.Override()
    public void SuccessCallBack(@org.jetbrains.annotations.Nullable()
    org.json.JSONObject jsonObject, @org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi, boolean showprogress) {
    }
    
    @java.lang.Override()
    public void ErrorCallBack(@org.jetbrains.annotations.Nullable()
    java.lang.String jsonstring, @org.jetbrains.annotations.Nullable()
    java.lang.String apitype, @org.jetbrains.annotations.Nullable()
    java.lang.String typeApi) {
    }
}