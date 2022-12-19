package com.utkarshnew.android.feeds.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0090\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J+\u0010\u00cf\u0001\u001a\u00030\u00d0\u00012\t\u0010\u00d1\u0001\u001a\u0004\u0018\u00010F2\t\u0010\u00d2\u0001\u001a\u0004\u0018\u00010F2\t\u0010\u00d3\u0001\u001a\u0004\u0018\u00010FH\u0016J4\u0010\u00d4\u0001\u001a\u00030\u00d0\u00012\t\u0010\u00d1\u0001\u001a\u0004\u0018\u00010\u001d2\t\u0010\u00d2\u0001\u001a\u0004\u0018\u00010F2\t\u0010\u00d3\u0001\u001a\u0004\u0018\u00010F2\u0007\u0010\u00d5\u0001\u001a\u000205H\u0016J\u001a\u0010\u00d6\u0001\u001a\u00030\u00d0\u00012\u000e\u0010\u00d7\u0001\u001a\t\u0012\u0004\u0012\u00020 0\u00d8\u0001H\u0002J\b\u0010\u00d9\u0001\u001a\u00030\u00d0\u0001J\n\u0010\u00da\u0001\u001a\u00030\u00d0\u0001H\u0002J4\u0010\u00db\u0001\u001a\u000b\u0012\u0004\u0012\u00020F\u0018\u00010\u00dc\u00012\t\u0010\u00d2\u0001\u001a\u0004\u0018\u00010F2\t\u0010\u00d3\u0001\u001a\u0004\u0018\u00010F2\n\u0010\u00dd\u0001\u001a\u0005\u0018\u00010\u00de\u0001H\u0016J\b\u0010\u00df\u0001\u001a\u00030\u00d0\u0001J\n\u0010\u00e0\u0001\u001a\u00030\u00d0\u0001H\u0002J\n\u0010\u00e1\u0001\u001a\u00030\u00d0\u0001H\u0002J\n\u0010\u00e2\u0001\u001a\u00030\u00d0\u0001H\u0002J\u0013\u0010\u00e3\u0001\u001a\u00030\u00d0\u00012\u0007\u0010\u00e4\u0001\u001a\u000205H\u0002J\b\u0010\u00e5\u0001\u001a\u00030\u00d0\u0001J\n\u0010\u00e6\u0001\u001a\u00030\u00d0\u0001H\u0016J\u0016\u0010\u00e7\u0001\u001a\u00030\u00d0\u00012\n\u0010\u00e8\u0001\u001a\u0005\u0018\u00010\u00e9\u0001H\u0014J\u0015\u0010\u00ea\u0001\u001a\u0002052\n\u0010\u00eb\u0001\u001a\u0005\u0018\u00010\u00ec\u0001H\u0016J\n\u0010\u00ed\u0001\u001a\u00030\u00d0\u0001H\u0014J\n\u0010\u00ee\u0001\u001a\u00030\u00d0\u0001H\u0014J\n\u0010\u00ef\u0001\u001a\u00030\u00d0\u0001H\u0014J\n\u0010\u00f0\u0001\u001a\u00030\u0087\u0001H\u0003J\b\u0010\u00f1\u0001\u001a\u00030\u00d0\u0001J\u001a\u0010\u00f2\u0001\u001a\u00030\u00d0\u00012\u000e\u0010\u00d7\u0001\u001a\t\u0012\u0004\u0012\u00020 0\u00d8\u0001H\u0002R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR \u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000f\"\u0004\b\u0015\u0010\u0011R\u001c\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R \u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001b\u0010%\u001a\u00020&8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b\'\u0010(R \u0010+\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u000f\"\u0004\b-\u0010\u0011R\u001c\u0010.\u001a\u0004\u0018\u00010/X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001a\u00104\u001a\u000205X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u00106\"\u0004\b7\u00108R\u001a\u00109\u001a\u000205X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u00106\"\u0004\b:\u00108R\u001a\u0010;\u001a\u00020<X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R \u0010A\u001a\b\u0012\u0004\u0012\u00020B0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010\u000f\"\u0004\bD\u0010\u0011R\u001a\u0010E\u001a\u00020FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010H\"\u0004\bI\u0010JR \u0010K\u001a\b\u0012\u0004\u0012\u00020L0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bM\u0010\u000f\"\u0004\bN\u0010\u0011R\u001a\u0010O\u001a\u00020FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010H\"\u0004\bQ\u0010JR\u001a\u0010R\u001a\u000205X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u00106\"\u0004\bT\u00108R\u001a\u0010U\u001a\u00020VX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bW\u0010X\"\u0004\bY\u0010ZR\u001a\u0010[\u001a\u00020FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010H\"\u0004\b]\u0010JR\u001a\u0010^\u001a\u00020FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010H\"\u0004\b`\u0010JR\u001c\u0010a\u001a\u0004\u0018\u00010bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010d\"\u0004\be\u0010fR \u0010g\u001a\b\u0012\u0004\u0012\u00020h0\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bi\u0010\"\"\u0004\bj\u0010$R\u001a\u0010k\u001a\u00020FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bl\u0010H\"\u0004\bm\u0010JR\u001a\u0010n\u001a\u00020FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bo\u0010H\"\u0004\bp\u0010JR \u0010q\u001a\b\u0012\u0004\u0012\u00020r0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bs\u0010\u000f\"\u0004\bt\u0010\u0011R \u0010u\u001a\b\u0012\u0004\u0012\u00020v0\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bw\u0010\u000f\"\u0004\bx\u0010\u0011R\u001c\u0010y\u001a\u0004\u0018\u00010zX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b{\u0010|\"\u0004\b}\u0010~R\u001c\u0010\u007f\u001a\u00020<X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0080\u0001\u0010>\"\u0005\b\u0081\u0001\u0010@R\u000f\u0010\u0082\u0001\u001a\u00020FX\u0082\u000e\u00a2\u0006\u0002\n\u0000R#\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0084\u0001\u0010\u000f\"\u0005\b\u0085\u0001\u0010\u0011R\"\u0010\u0086\u0001\u001a\u0005\u0018\u00010\u0087\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001\"\u0006\b\u008a\u0001\u0010\u008b\u0001R#\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020\u00130\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u008d\u0001\u0010\u000f\"\u0005\b\u008e\u0001\u0010\u0011R\u001d\u0010\u008f\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0090\u0001\u0010H\"\u0005\b\u0091\u0001\u0010JR$\u0010\u0092\u0001\u001a\t\u0012\u0005\u0012\u00030\u0093\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0094\u0001\u0010\u000f\"\u0005\b\u0095\u0001\u0010\u0011R\u001d\u0010\u0096\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u0097\u0001\u0010H\"\u0005\b\u0098\u0001\u0010JR\"\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009a\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u009b\u0001\u0010\u009c\u0001\"\u0006\b\u009d\u0001\u0010\u009e\u0001R$\u0010\u009f\u0001\u001a\t\u0012\u0005\u0012\u00030\u00a0\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a1\u0001\u0010\u000f\"\u0005\b\u00a2\u0001\u0010\u0011R\u000f\u0010\u00a3\u0001\u001a\u00020<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u00a4\u0001\u001a\u000205X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a5\u0001\u00106\"\u0005\b\u00a6\u0001\u00108R\u001d\u0010\u00a7\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00a8\u0001\u0010H\"\u0005\b\u00a9\u0001\u0010JR#\u0010\u00aa\u0001\u001a\b\u0012\u0004\u0012\u00020h0\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00ab\u0001\u0010\u000f\"\u0005\b\u00ac\u0001\u0010\u0011R#\u0010\u00ad\u0001\u001a\b\u0012\u0004\u0012\u00020h0\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00ae\u0001\u0010\u000f\"\u0005\b\u00af\u0001\u0010\u0011R\u001d\u0010\u00b0\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00b1\u0001\u0010H\"\u0005\b\u00b2\u0001\u0010JR\u001d\u0010\u00b3\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00b4\u0001\u0010H\"\u0005\b\u00b5\u0001\u0010JR\u001d\u0010\u00b6\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00b7\u0001\u0010H\"\u0005\b\u00b8\u0001\u0010JR\u001d\u0010\u00b9\u0001\u001a\u00020FX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00ba\u0001\u0010H\"\u0005\b\u00bb\u0001\u0010JR\"\u0010\u00bc\u0001\u001a\u0005\u0018\u00010\u009a\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00bd\u0001\u0010\u009c\u0001\"\u0006\b\u00be\u0001\u0010\u009e\u0001R$\u0010\u00bf\u0001\u001a\t\u0012\u0005\u0012\u00030\u00c0\u00010\fX\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00c1\u0001\u0010\u000f\"\u0005\b\u00c2\u0001\u0010\u0011R\u001d\u0010\u00c3\u0001\u001a\u000205X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00c4\u0001\u00106\"\u0005\b\u00c5\u0001\u00108R\u001d\u0010\u00c6\u0001\u001a\u000205X\u0086\u000e\u00a2\u0006\u0010\n\u0000\u001a\u0005\b\u00c7\u0001\u00106\"\u0005\b\u00c8\u0001\u00108R \u0010\u00c9\u0001\u001a\u00030\u00ca\u0001X\u0086\u000e\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00cb\u0001\u0010\u00cc\u0001\"\u0006\b\u00cd\u0001\u0010\u00ce\u0001\u00a8\u0006\u00f3\u0001"}, d2 = {"Lcom/utkarshnew/android/feeds/activity/FeedsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/utkarshnew/android/Utils/Network/NetworkCall$MyNetworkCallBack;", "Landroidx/appcompat/widget/PopupMenu$OnMenuItemClickListener;", "()V", "backBtn", "Landroid/widget/Button;", "getBackBtn", "()Landroid/widget/Button;", "setBackBtn", "(Landroid/widget/Button;)V", "bannert_list", "Ljava/util/ArrayList;", "Lcom/utkarshnew/android/feeds/dataclass/BannerData;", "getBannert_list", "()Ljava/util/ArrayList;", "setBannert_list", "(Ljava/util/ArrayList;)V", "datalist", "Lcom/utkarshnew/android/feeds/dataclass/Data;", "getDatalist", "setDatalist", "feedAdapter", "Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;", "getFeedAdapter", "()Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;", "setFeedAdapter", "(Lcom/utkarshnew/android/feeds/adapters/FeedAdapter;)V", "feedJsonObject", "Lorg/json/JSONObject;", "feedParentData", "", "Lcom/utkarshnew/android/table/PostDataTable;", "getFeedParentData", "()Ljava/util/List;", "setFeedParentData", "(Ljava/util/List;)V", "feedViewModel", "Lcom/utkarshnew/android/feeds/viewmodel/FeedViewModel;", "getFeedViewModel", "()Lcom/utkarshnew/android/feeds/viewmodel/FeedViewModel;", "feedViewModel$delegate", "Lkotlin/Lazy;", "feedlist", "getFeedlist", "setFeedlist", "feedsBinding", "Lcom/utkarshnew/android/databinding/ActivityFeedsBinding;", "getFeedsBinding", "()Lcom/utkarshnew/android/databinding/ActivityFeedsBinding;", "setFeedsBinding", "(Lcom/utkarshnew/android/databinding/ActivityFeedsBinding;)V", "isPullToRefresh", "", "()Z", "setPullToRefresh", "(Z)V", "is_filterbutton", "set_filterbutton", "limitdata", "", "getLimitdata", "()I", "setLimitdata", "(I)V", "liveClassData", "Lcom/utkarshnew/android/home/liveclasses/Datum;", "getLiveClassData", "setLiveClassData", "liveClassStatus", "", "getLiveClassStatus", "()Ljava/lang/String;", "setLiveClassStatus", "(Ljava/lang/String;)V", "liveTestData", "Lcom/utkarshnew/android/home/livetest/LiveTestData;", "getLiveTestData", "setLiveTestData", "liveTestStatus", "getLiveTestStatus", "setLiveTestStatus", "loading", "getLoading", "setLoading", "locale_time", "", "getLocale_time", "()J", "setLocale_time", "(J)V", "main_cat", "getMain_cat", "setMain_cat", "main_cat_name", "getMain_cat_name", "setMain_cat_name", "manager", "Landroidx/recyclerview/widget/LinearLayoutManager;", "getManager", "()Landroidx/recyclerview/widget/LinearLayoutManager;", "setManager", "(Landroidx/recyclerview/widget/LinearLayoutManager;)V", "masterAllCatTables", "Lcom/utkarshnew/android/table/MasteAllCatTable;", "getMasterAllCatTables", "setMasterAllCatTables", "master_cat", "getMaster_cat", "setMaster_cat", "master_cat_name", "getMaster_cat_name", "setMaster_cat_name", "mastercatlist", "Lcom/utkarshnew/android/table/MasterCat;", "getMastercatlist", "setMastercatlist", "newCourseData", "Lcom/utkarshnew/android/feeds/dataclass/NewCourseData;", "getNewCourseData", "setNewCourseData", "no_data_found_RL", "Landroid/widget/RelativeLayout;", "getNo_data_found_RL", "()Landroid/widget/RelativeLayout;", "setNo_data_found_RL", "(Landroid/widget/RelativeLayout;)V", "page", "getPage", "setPage", "pinnedPost", "pinnedPostList", "getPinnedPostList", "setPinnedPostList", "popUp", "Landroid/widget/PopupWindow;", "getPopUp", "()Landroid/widget/PopupWindow;", "setPopUp", "(Landroid/widget/PopupWindow;)V", "posiitonwiselist", "getPosiitonwiselist", "setPosiitonwiselist", "posttypeid", "getPosttypeid", "setPosttypeid", "posttypelist", "Lcom/utkarshnew/android/feeds/dataclass/PostType;", "getPosttypelist", "setPosttypelist", "posttypename", "getPosttypename", "setPosttypename", "posttypeytext", "Landroid/widget/TextView;", "getPosttypeytext", "()Landroid/widget/TextView;", "setPosttypeytext", "(Landroid/widget/TextView;)V", "preferencesArrayList", "Lcom/utkarshnew/android/pojo/Userinfo/Data$Preferences;", "getPreferencesArrayList", "setPreferencesArrayList", "refreshCount", "response_booelan", "getResponse_booelan", "setResponse_booelan", "section_posiiton", "getSection_posiiton", "setSection_posiiton", "selected_master_cat", "getSelected_master_cat", "setSelected_master_cat", "selectedsub_all_cat", "getSelectedsub_all_cat", "setSelectedsub_all_cat", "sub_cat", "getSub_cat", "setSub_cat", "sub_cat_filter", "getSub_cat_filter", "setSub_cat_filter", "sub_cat_name", "getSub_cat_name", "setSub_cat_name", "sub_cat_name_filter", "getSub_cat_name_filter", "setSub_cat_name_filter", "subcatspinner", "getSubcatspinner", "setSubcatspinner", "testResultList", "Lcom/utkarshnew/android/feeds/dataclass/TestResult;", "getTestResultList", "setTestResultList", "type_posttypefilter", "getType_posttypefilter", "setType_posttypefilter", "type_subcatfilter", "getType_subcatfilter", "setType_subcatfilter", "utkashRoom", "Lcom/utkarshnew/android/Room/UtkashRoom;", "getUtkashRoom", "()Lcom/utkarshnew/android/Room/UtkashRoom;", "setUtkashRoom", "(Lcom/utkarshnew/android/Room/UtkashRoom;)V", "ErrorCallBack", "", "jsonstring", "apitype", "typeApi", "SuccessCallBack", "showprogress", "catregoryPost", "postData", "", "createApiBodyData", "filterDailog", "getAPIB", "Lretrofit2/Call;", "service", "Lcom/utkarshnew/android/Utils/Network/APIInterface;", "hideProgressView", "hitApiForLiveClass", "hitApiForLiveTest", "hit_api_for_feed_dot", "local_data", "filter", "observer", "onBackPressed", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onMenuItemClick", "item", "Landroid/view/MenuItem;", "onPause", "onRestart", "onStop", "popupWindowPart", "showProgressView", "updatePostData", "app_debug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class FeedsActivity extends androidx.appcompat.app.AppCompatActivity implements com.utkarshnew.android.Utils.Network.NetworkCall.MyNetworkCallBack, androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener {
    private int refreshCount = 0;
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
    private boolean response_booelan = false;
    private int limitdata = 0;
    private boolean type_subcatfilter = false;
    private boolean type_posttypefilter = false;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView subcatspinner;
    @org.jetbrains.annotations.Nullable()
    private android.widget.TextView posttypeytext;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String posttypename = "All";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String posttypeid = "0";
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.feeds.adapters.FeedAdapter feedAdapter;
    private boolean loading = true;
    @org.jetbrains.annotations.Nullable()
    private android.widget.RelativeLayout no_data_found_RL;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy feedViewModel$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String main_cat = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String main_cat_name = "";
    @org.jetbrains.annotations.Nullable()
    private androidx.recyclerview.widget.LinearLayoutManager manager;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sub_cat = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sub_cat_name = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sub_cat_filter = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String sub_cat_name_filter = "";
    private boolean is_filterbutton = false;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String master_cat = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String master_cat_name = "";
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> selected_master_cat;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.table.MasteAllCatTable> selectedsub_all_cat;
    @org.jetbrains.annotations.NotNull()
    private com.utkarshnew.android.Room.UtkashRoom utkashRoom;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> datalist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.PostType> posttypelist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> posiitonwiselist;
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
    private java.util.ArrayList<com.utkarshnew.android.table.MasterCat> mastercatlist;
    @org.jetbrains.annotations.NotNull()
    private java.util.ArrayList<com.utkarshnew.android.pojo.Userinfo.Data.Preferences> preferencesArrayList;
    @org.jetbrains.annotations.Nullable()
    private android.widget.PopupWindow popUp;
    private long locale_time = 0L;
    private int page = 1;
    @org.jetbrains.annotations.Nullable()
    private com.utkarshnew.android.databinding.ActivityFeedsBinding feedsBinding;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.utkarshnew.android.table.PostDataTable> feedParentData;
    
    public FeedsActivity() {
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
    
    public final boolean getType_subcatfilter() {
        return false;
    }
    
    public final void setType_subcatfilter(boolean p0) {
    }
    
    public final boolean getType_posttypefilter() {
        return false;
    }
    
    public final void setType_posttypefilter(boolean p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getSubcatspinner() {
        return null;
    }
    
    public final void setSubcatspinner(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.TextView getPosttypeytext() {
        return null;
    }
    
    public final void setPosttypeytext(@org.jetbrains.annotations.Nullable()
    android.widget.TextView p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPosttypename() {
        return null;
    }
    
    public final void setPosttypename(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPosttypeid() {
        return null;
    }
    
    public final void setPosttypeid(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMain_cat_name() {
        return null;
    }
    
    public final void setMain_cat_name(@org.jetbrains.annotations.NotNull()
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
    public final java.lang.String getSub_cat_name() {
        return null;
    }
    
    public final void setSub_cat_name(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSub_cat_filter() {
        return null;
    }
    
    public final void setSub_cat_filter(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSub_cat_name_filter() {
        return null;
    }
    
    public final void setSub_cat_name_filter(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    public final boolean is_filterbutton() {
        return false;
    }
    
    public final void set_filterbutton(boolean p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMaster_cat() {
        return null;
    }
    
    public final void setMaster_cat(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMaster_cat_name() {
        return null;
    }
    
    public final void setMaster_cat_name(@org.jetbrains.annotations.NotNull()
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
    public final com.utkarshnew.android.Room.UtkashRoom getUtkashRoom() {
        return null;
    }
    
    public final void setUtkashRoom(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Room.UtkashRoom p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> getDatalist() {
        return null;
    }
    
    public final void setDatalist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.PostType> getPosttypelist() {
        return null;
    }
    
    public final void setPosttypelist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.PostType> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.feeds.dataclass.Data> getPosiitonwiselist() {
        return null;
    }
    
    public final void setPosiitonwiselist(@org.jetbrains.annotations.NotNull()
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
    public final java.util.ArrayList<com.utkarshnew.android.table.MasterCat> getMastercatlist() {
        return null;
    }
    
    public final void setMastercatlist(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.table.MasterCat> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.ArrayList<com.utkarshnew.android.pojo.Userinfo.Data.Preferences> getPreferencesArrayList() {
        return null;
    }
    
    public final void setPreferencesArrayList(@org.jetbrains.annotations.NotNull()
    java.util.ArrayList<com.utkarshnew.android.pojo.Userinfo.Data.Preferences> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.widget.PopupWindow getPopUp() {
        return null;
    }
    
    public final void setPopUp(@org.jetbrains.annotations.Nullable()
    android.widget.PopupWindow p0) {
    }
    
    public final long getLocale_time() {
        return 0L;
    }
    
    public final void setLocale_time(long p0) {
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
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.utkarshnew.android.table.PostDataTable> getFeedParentData() {
        return null;
    }
    
    public final void setFeedParentData(@org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.table.PostDataTable> p0) {
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void filterDailog() {
    }
    
    private final void local_data(boolean filter) {
    }
    
    public final void createApiBodyData() {
    }
    
    public final void observer() {
    }
    
    private final void updatePostData(java.util.List<com.utkarshnew.android.table.PostDataTable> postData) {
    }
    
    private final void catregoryPost(java.util.List<com.utkarshnew.android.table.PostDataTable> postData) {
    }
    
    private final void hitApiForLiveClass() {
    }
    
    private final void hitApiForLiveTest() {
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
    
    @java.lang.Override()
    protected void onPause() {
    }
    
    @java.lang.Override()
    protected void onRestart() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "UseCompatLoadingForDrawables"})
    private final android.widget.PopupWindow popupWindowPart() {
        return null;
    }
    
    @java.lang.Override()
    protected void onStop() {
    }
    
    private final void hit_api_for_feed_dot() {
    }
    
    @org.jetbrains.annotations.Nullable()
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
}