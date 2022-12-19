package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LiveClassVmBindingImpl extends LiveClassVmBinding implements com.utkarshnew.android.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.new_course_txt, 3);
        sViewsWithIds.put(R.id.view1, 4);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback11;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LiveClassVmBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private LiveClassVmBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.view.View) bindings[4]
            , (android.widget.TextView) bindings[1]
            );
        this.liveClassRecycler.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.viewAll.setTag(null);
        setRootTag(root);
        // listeners
        mCallback11 = new com.utkarshnew.android.generated.callback.OnClickListener(this, 1);
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
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
        if (BR.feedadapter == variableId) {
            setFeedadapter((com.utkarshnew.android.feeds.adapters.FeedAdapter) variable);
        }
        else if (BR.liveclass == variableId) {
            setLiveclass((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setFeedadapter(@Nullable com.utkarshnew.android.feeds.adapters.FeedAdapter Feedadapter) {
        this.mFeedadapter = Feedadapter;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.feedadapter);
        super.requestRebind();
    }
    public void setLiveclass(@Nullable com.utkarshnew.android.feeds.dataclass.Data Liveclass) {
        this.mLiveclass = Liveclass;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.liveclass);
        super.requestRebind();
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
        com.utkarshnew.android.feeds.adapters.FeedAdapter feedadapter = mFeedadapter;
        java.util.List<com.utkarshnew.android.home.liveclasses.Datum> liveclassLiveclass = null;
        com.utkarshnew.android.feeds.dataclass.Data liveclass = mLiveclass;

        if ((dirtyFlags & 0x6L) != 0) {



                if (liveclass != null) {
                    // read liveclass.liveclass
                    liveclassLiveclass = liveclass.getLiveclass();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x6L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.setliveclassadapter(this.liveClassRecycler, liveclassLiveclass);
        }
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.viewAll.setOnClickListener(mCallback11);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // feedadapter
        com.utkarshnew.android.feeds.adapters.FeedAdapter feedadapter = mFeedadapter;
        // feedadapter != null
        boolean feedadapterJavaLangObjectNull = false;



        feedadapterJavaLangObjectNull = (feedadapter) != (null);
        if (feedadapterJavaLangObjectNull) {



            feedadapter.viewAllclass(callbackArg_0);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): feedadapter
        flag 1 (0x2L): liveclass
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}