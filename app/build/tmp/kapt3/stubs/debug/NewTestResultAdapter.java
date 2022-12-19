
import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001eB\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u001c\u0010\u0013\u001a\u00020\u00142\n\u0010\u0015\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0016\u001a\u00020\u0012H\u0017J\u001c\u0010\u0017\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0012H\u0016J\u001e\u0010\u001b\u001a\u00020\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\u0010\u001d\u001a\u0004\u0018\u00010\u0005R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001f"}, d2 = {"LNewTestResultAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "LNewTestResultAdapter$NewCourseVH;", "()V", "context", "Landroid/content/Context;", "getContext", "()Landroid/content/Context;", "setContext", "(Landroid/content/Context;)V", "testResult", "", "Lcom/utkarshnew/android/feeds/dataclass/TestResult;", "getTestResult", "()Ljava/util/List;", "setTestResult", "(Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "updateItems", "testResultlist", "ctx", "NewCourseVH", "app_debug"})
public final class NewTestResultAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<NewTestResultAdapter.NewCourseVH> {
    @org.jetbrains.annotations.Nullable()
    private android.content.Context context;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.utkarshnew.android.feeds.dataclass.TestResult> testResult;
    
    public NewTestResultAdapter() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.content.Context getContext() {
        return null;
    }
    
    public final void setContext(@org.jetbrains.annotations.Nullable()
    android.content.Context p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.utkarshnew.android.feeds.dataclass.TestResult> getTestResult() {
        return null;
    }
    
    public final void setTestResult(@org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.feeds.dataclass.TestResult> p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public NewTestResultAdapter.NewCourseVH onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"UseCompatLoadingForDrawables", "SetTextI18n"})
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    NewTestResultAdapter.NewCourseVH holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    public final void updateItems(@org.jetbrains.annotations.NotNull()
    java.util.List<com.utkarshnew.android.feeds.dataclass.TestResult> testResultlist, @org.jetbrains.annotations.Nullable()
    android.content.Context ctx) {
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"LNewTestResultAdapter$NewCourseVH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "newTestResultAdapterBinding", "Lcom/utkarshnew/android/databinding/NewTestResultAdapterBinding;", "(LNewTestResultAdapter;Lcom/utkarshnew/android/databinding/NewTestResultAdapterBinding;)V", "bind", "", "testResultdata", "Lcom/utkarshnew/android/feeds/dataclass/TestResult;", "app_debug"})
    public final class NewCourseVH extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final com.utkarshnew.android.databinding.NewTestResultAdapterBinding newTestResultAdapterBinding = null;
        
        public NewCourseVH(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.databinding.NewTestResultAdapterBinding newTestResultAdapterBinding) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull()
        com.utkarshnew.android.feeds.dataclass.TestResult testResultdata) {
        }
    }
}