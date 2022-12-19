package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class NewTestResultAdapterBindingImpl extends NewTestResultAdapterBinding implements com.utkarshnew.android.generated.callback.OnClickListener.Listener {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    // variables
    @Nullable
    private final android.view.View.OnClickListener mCallback5;
    // values
    // listeners
    // Inverse Binding Event Handlers

    public NewTestResultAdapterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private NewTestResultAdapterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.TextView) bindings[3]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[4]
            );
        this.parentLayout.setTag(null);
        this.testAttempts.setTag(null);
        this.testImage.setTag(null);
        this.testName.setTag(null);
        this.testResult.setTag(null);
        setRootTag(root);
        // listeners
        mCallback5 = new com.utkarshnew.android.generated.callback.OnClickListener(this, 1);
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
        if (BR.livetestresult == variableId) {
            setLivetestresult((com.utkarshnew.android.feeds.dataclass.TestResult) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLivetestresult(@Nullable com.utkarshnew.android.feeds.dataclass.TestResult Livetestresult) {
        this.mLivetestresult = Livetestresult;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.livetestresult);
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
        java.lang.String livetestresultTestSeriesName = null;
        java.lang.String livetestresultImage = null;
        java.lang.String livetestresultAttempts = null;
        com.utkarshnew.android.feeds.dataclass.TestResult livetestresult = mLivetestresult;

        if ((dirtyFlags & 0x3L) != 0) {



                if (livetestresult != null) {
                    // read livetestresult.test_series_name
                    livetestresultTestSeriesName = livetestresult.getTest_series_name();
                    // read livetestresult.image
                    livetestresultImage = livetestresult.getImage();
                    // read livetestresult.attempts
                    livetestresultAttempts = livetestresult.getAttempts();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.testAttempts, livetestresultAttempts);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.testImage, livetestresultImage);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.testName, livetestresultTestSeriesName);
        }
        if ((dirtyFlags & 0x2L) != 0) {
            // api target 1

            this.testResult.setOnClickListener(mCallback5);
        }
    }
    // Listener Stub Implementations
    // callback impls
    public final void _internalCallbackOnClick(int sourceId , android.view.View callbackArg_0) {
        // localize variables for thread safety
        // livetestresult != null
        boolean livetestresultJavaLangObjectNull = false;
        // livetestresult
        com.utkarshnew.android.feeds.dataclass.TestResult livetestresult = mLivetestresult;



        livetestresultJavaLangObjectNull = (livetestresult) != (null);
        if (livetestresultJavaLangObjectNull) {




            livetestresult.click(callbackArg_0, livetestresult);
        }
    }
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): livetestresult
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}