package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class BannerAdapterImageviewBindingSw600dpImpl extends BannerAdapterImageviewBinding implements com.utkarshnew.android.generated.callback.OnClickListener.Listener {

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
    @Nullable
    private final android.view.View.OnClickListener mCallback3;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public BannerAdapterImageviewBindingSw600dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds));
    }
    private BannerAdapterImageviewBindingSw600dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[1]
            );
        this.imgeView.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        mCallback3 = new com.utkarshnew.android.generated.callback.OnClickListener(this, 1);
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
        if (BR.bannerdata == variableId) {
            setBannerdata((com.utkarshnew.android.feeds.dataclass.BannerData) variable);
        }
        else if (BR.bannerviewadapter == variableId) {
            setBannerviewadapter((com.utkarshnew.android.feeds.adapters.Banner_ViewPager) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setBannerdata(@Nullable com.utkarshnew.android.feeds.dataclass.BannerData Bannerdata) {
        this.mBannerdata = Bannerdata;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.bannerdata);
        super.requestRebind();
    }
    public void setBannerviewadapter(@Nullable com.utkarshnew.android.feeds.adapters.Banner_ViewPager Bannerviewadapter) {
        this.mBannerviewadapter = Bannerviewadapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.bannerviewadapter);
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
        com.utkarshnew.android.feeds.dataclass.BannerData bannerdata = mBannerdata;
        java.lang.String bannerdataUrl = null;
        com.utkarshnew.android.feeds.adapters.Banner_ViewPager bannerviewadapter = mBannerviewadapter;

        if ((dirtyFlags & 0x5L) != 0) {



                if (bannerdata != null) {
                    // read bannerdata.url
                    bannerdataUrl = bannerdata.getUrl();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x4L) != 0) {
            // api target 1

            this.imgeView.setOnClickListener(mCallback3);
        }
        if ((dirtyFlags & 0x5L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.imgeView, bannerdataUrl);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // bannerdata
        com.utkarshnew.android.feeds.dataclass.BannerData bannerdata = mBannerdata;
        // bannerdata.target_meta
        java.lang.String bannerdataTargetMeta = null;
        // bannerviewadapter
        com.utkarshnew.android.feeds.adapters.Banner_ViewPager bannerviewadapter = mBannerviewadapter;
        // bannerdata != null
        boolean bannerdataJavaLangObjectNull = false;
        // bannerviewadapter != null
        boolean bannerviewadapterJavaLangObjectNull = false;



        bannerviewadapterJavaLangObjectNull = (bannerviewadapter) != (null);
        if (bannerviewadapterJavaLangObjectNull) {



            bannerdataJavaLangObjectNull = (bannerdata) != (null);
            if (bannerdataJavaLangObjectNull) {


                bannerdataTargetMeta = bannerdata.getTarget_meta();

                bannerviewadapter.clickBanner(bannerdataTargetMeta);
            }
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): bannerdata
        flag 1 (0x2L): bannerviewadapter
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}