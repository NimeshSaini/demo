package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class PostImageBindingSw720dpImpl extends PostImageBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 9);
        sViewsWithIds.put(R.id.image_constraint_layout, 10);
        sViewsWithIds.put(R.id.image_text, 11);
        sViewsWithIds.put(R.id.read_more, 12);
        sViewsWithIds.put(R.id.like_comment_count_layout, 13);
        sViewsWithIds.put(R.id.view, 14);
        sViewsWithIds.put(R.id.like_comment_layout, 15);
        sViewsWithIds.put(R.id.post_comment, 16);
        sViewsWithIds.put(R.id.postShare, 17);
        sViewsWithIds.put(R.id.view1, 18);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public PostImageBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private PostImageBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[10]
            , (android.widget.TextView) bindings[11]
            , (android.widget.ImageView) bindings[5]
            , (android.widget.TextView) bindings[8]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[14]
            , (android.view.View) bindings[18]
            );
        this.imgeView.setTag(null);
        this.like.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pinIV.setTag(null);
        this.postCommentCount.setTag(null);
        this.postLikeCount.setTag(null);
        this.postTime.setTag(null);
        this.profileImage.setTag(null);
        this.userName.setTag(null);
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
        if (BR.imagebind == variableId) {
            setImagebind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setImagebind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Imagebind) {
        this.mImagebind = Imagebind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.imagebind);
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
        int imagebindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        java.lang.String imagebindName = null;
        java.lang.String imagebindProfilePicture = null;
        java.lang.String imagebindMyPinned = null;
        com.utkarshnew.android.feeds.dataclass.Data imagebind = mImagebind;
        java.lang.String imagebindTotalComments = null;
        boolean imagebindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String imagebindTotalCommentsJavaLangStringComment = null;
        java.lang.String imagebindCreated = null;
        java.lang.String imagebindMyLike = null;
        java.lang.String imagebindTotalLikes = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (imagebind != null) {
                    // read imagebind.name
                    imagebindName = imagebind.getName();
                    // read imagebind.profile_picture
                    imagebindProfilePicture = imagebind.getProfile_picture();
                    // read imagebind.my_pinned
                    imagebindMyPinned = imagebind.getMy_pinned();
                    // read imagebind.total_comments
                    imagebindTotalComments = imagebind.getTotal_comments();
                    // read imagebind.created
                    imagebindCreated = imagebind.getCreated();
                    // read imagebind.my_like
                    imagebindMyLike = imagebind.getMy_like();
                    // read imagebind.total_likes
                    imagebindTotalLikes = imagebind.getTotal_likes();
                }


                if (imagebindMyPinned != null) {
                    // read imagebind.my_pinned.equals("1")
                    imagebindMyPinnedEqualsJavaLangString1 = imagebindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(imagebindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
                // read (imagebind.total_comments) + (" Comment")
                imagebindTotalCommentsJavaLangStringComment = (imagebindTotalComments) + (" Comment");


                // read imagebind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                imagebindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((imagebindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.imagefeedPost(this.imgeView, imagebind);
            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, imagebindMyLike);
            this.pinIV.setImageResource(imagebindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, imagebindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, imagebindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, imagebindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, imagebindProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, imagebindName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): imagebind
        flag 1 (0x2L): null
        flag 2 (0x3L): imagebind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 3 (0x4L): imagebind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
    flag mapping end*/
    //end
}