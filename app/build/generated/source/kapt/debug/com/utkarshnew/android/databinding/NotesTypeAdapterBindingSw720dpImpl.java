package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class NotesTypeAdapterBindingSw720dpImpl extends NotesTypeAdapterBinding  {

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
    // values
    // listeners
    // Inverse Binding Event Handlers

    public NotesTypeAdapterBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds));
    }
    private NotesTypeAdapterBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[1]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.TextView) bindings[2]
            );
        this.imageView.setTag(null);
        this.notesLayout.setTag(null);
        this.physicalNote.setTag(null);
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
        if (BR.notestype == variableId) {
            setNotestype((com.utkarshnew.android.courses.modal.NotesType) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setNotestype(@Nullable com.utkarshnew.android.courses.modal.NotesType Notestype) {
        this.mNotestype = Notestype;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.notestype);
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
        java.lang.String notestypeNotesIcon = null;
        com.utkarshnew.android.courses.modal.NotesType notestype = mNotestype;
        java.lang.String notestypeNotesName = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (notestype != null) {
                    // read notestype.notes_icon
                    notestypeNotesIcon = notestype.getNotes_icon();
                    // read notestype.notes_name
                    notestypeNotesName = notestype.getNotes_name();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.ExtensionFucationKt.imageurl(this.imageView, notestypeNotesIcon);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.physicalNote, notestypeNotesName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): notestype
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}