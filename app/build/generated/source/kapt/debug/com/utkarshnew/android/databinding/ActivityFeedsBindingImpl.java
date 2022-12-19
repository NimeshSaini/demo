package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityFeedsBindingImpl extends ActivityFeedsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.feeds_toolbar, 1);
        sViewsWithIds.put(R.id.image_back, 2);
        sViewsWithIds.put(R.id.titleinnerRL, 3);
        sViewsWithIds.put(R.id.toolbartitleTV, 4);
        sViewsWithIds.put(R.id.pined_post, 5);
        sViewsWithIds.put(R.id.filter, 6);
        sViewsWithIds.put(R.id.pullto_referesh, 7);
        sViewsWithIds.put(R.id.nested_scroll, 8);
        sViewsWithIds.put(R.id.feed_recyerlview, 9);
        sViewsWithIds.put(R.id.progressBar, 10);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityFeedsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private ActivityFeedsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (androidx.appcompat.widget.Toolbar) bindings[1]
            , (android.widget.ImageView) bindings[6]
            , (android.widget.ImageView) bindings[2]
            , (androidx.core.widget.NestedScrollView) bindings[8]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.ProgressBar) bindings[10]
            , (androidx.swiperefreshlayout.widget.SwipeRefreshLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.TextView) bindings[4]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.feedbind == variableId) {
            setFeedbind((com.utkarshnew.android.feeds.viewmodel.FeedViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setFeedbind(@Nullable com.utkarshnew.android.feeds.viewmodel.FeedViewModel Feedbind) {
        this.mFeedbind = Feedbind;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): feedbind
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}