package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class CommentAdapterBindingImpl extends CommentAdapterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.cvrRight, 6);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public CommentAdapterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds));
    }
    private CommentAdapterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[4]
            , (android.widget.RelativeLayout) bindings[6]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[5]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            );
        this.approval.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.profileImage2.setTag(null);
        this.rightmessage.setTag(null);
        this.righttexttime.setTag(null);
        this.userNameright.setTag(null);
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
        if (BR.commentdata == variableId) {
            setCommentdata((com.utkarshnew.android.feeds.dataclass.comment.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setCommentdata(@Nullable com.utkarshnew.android.feeds.dataclass.comment.Data Commentdata) {
        this.mCommentdata = Commentdata;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.commentdata);
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
        com.utkarshnew.android.feeds.dataclass.comment.Data commentdata = mCommentdata;
        java.lang.String commentdataProfilePicture = null;
        java.lang.String commentdataName = null;
        java.lang.String commentdataComment = null;
        java.lang.String commentdataCreated = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (commentdata != null) {
                    // read commentdata.profile_picture
                    commentdataProfilePicture = commentdata.getProfile_picture();
                    // read commentdata.name
                    commentdataName = commentdata.getName();
                    // read commentdata.comment
                    commentdataComment = commentdata.getComment();
                    // read commentdata.created
                    commentdataCreated = commentdata.getCreated();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.ExtensionFucationKt.viewVisible(this.approval, commentdata);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage2, commentdataProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.rightmessage, commentdataComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.date(this.righttexttime, commentdataCreated);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userNameright, commentdataName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): commentdata
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}