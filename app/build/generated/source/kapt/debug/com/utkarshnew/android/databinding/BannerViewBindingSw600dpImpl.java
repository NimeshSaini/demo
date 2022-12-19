package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class BannerViewBindingSw600dpImpl extends BannerViewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public BannerViewBindingSw600dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }
    private BannerViewBindingSw600dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.viewpager.widget.ViewPager) bindings[1]
            );
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.viewPager.setTag(null);
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
        if (BR.bannerdata == variableId) {
            setBannerdata((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setBannerdata(@Nullable com.utkarshnew.android.feeds.dataclass.Data Bannerdata) {
        this.mBannerdata = Bannerdata;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.bannerdata);
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
        com.utkarshnew.android.feeds.dataclass.Data bannerdata = mBannerdata;
        java.util.List<com.utkarshnew.android.feeds.dataclass.BannerData> bannerdataBannerlist = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (bannerdata != null) {
                    // read bannerdata.bannerlist
                    bannerdataBannerlist = bannerdata.getBannerlist();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.ExtensionFucationKt.load(this.viewPager, bannerdataBannerlist);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): bannerdata
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}