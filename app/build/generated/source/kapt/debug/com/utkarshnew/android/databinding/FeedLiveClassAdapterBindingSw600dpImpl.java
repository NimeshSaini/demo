package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FeedLiveClassAdapterBindingSw600dpImpl extends FeedLiveClassAdapterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.layout_test_info, 5);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FeedLiveClassAdapterBindingSw600dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds));
    }
    private FeedLiveClassAdapterBindingSw600dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[2]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[1]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[0]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[4]
            );
        this.courseName.setTag(null);
        this.imageTest.setTag(null);
        this.parentLayout.setTag(null);
        this.testName.setTag(null);
        this.time.setTag(null);
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
        if (BR.liveclassdata == variableId) {
            setLiveclassdata((com.utkarshnew.android.home.liveclasses.Datum) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLiveclassdata(@Nullable com.utkarshnew.android.home.liveclasses.Datum Liveclassdata) {
        this.mLiveclassdata = Liveclassdata;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.liveclassdata);
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
        java.lang.String liveclassdataTitle = null;
        java.lang.String liveclassdataCourseName = null;
        java.lang.String liveclassdataThumbnailUrl = null;
        com.utkarshnew.android.home.liveclasses.Datum liveclassdata = mLiveclassdata;

        if ((dirtyFlags & 0x3L) != 0) {



                if (liveclassdata != null) {
                    // read liveclassdata.title
                    liveclassdataTitle = liveclassdata.getTitle();
                    // read liveclassdata.course_name
                    liveclassdataCourseName = liveclassdata.getCourse_name();
                    // read liveclassdata.thumbnailUrl
                    liveclassdataThumbnailUrl = liveclassdata.getThumbnailUrl();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.courseName, liveclassdataCourseName);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.imageTest, liveclassdataThumbnailUrl);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.testName, liveclassdataTitle);
            com.utkarshnew.android.home.liveclasses.Datum.time(this.time, liveclassdata);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): liveclassdata
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}