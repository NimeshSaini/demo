package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class NewCourseVmBindingSw600dpImpl extends NewCourseVmBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.new_course_txt, 2);
        sViewsWithIds.put(R.id.view_all, 3);
        sViewsWithIds.put(R.id.view1, 4);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public NewCourseVmBindingSw600dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private NewCourseVmBindingSw600dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.recyclerview.widget.RecyclerView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[4]
            , (android.widget.TextView) bindings[3]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.newCourseRecycler.setTag(null);
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
        if (BR.coursedata == variableId) {
            setCoursedata((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCoursedata(@Nullable com.utkarshnew.android.feeds.dataclass.Data Coursedata) {
        this.mCoursedata = Coursedata;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.coursedata);
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
        java.util.List<com.utkarshnew.android.feeds.dataclass.NewCourseData> coursedataNewCourseData = null;
        com.utkarshnew.android.feeds.dataclass.Data coursedata = mCoursedata;

        if ((dirtyFlags & 0x3L) != 0) {



                if (coursedata != null) {
                    // read coursedata.newCourseData
                    coursedataNewCourseData = coursedata.getNewCourseData();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.ExtensionFucationKt.courseadapter(this.newCourseRecycler, coursedataNewCourseData);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): coursedata
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}