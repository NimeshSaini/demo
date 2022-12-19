package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LinkViewBindingSw720dpImpl extends LinkViewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 9);
        sViewsWithIds.put(R.id.link_txt, 10);
        sViewsWithIds.put(R.id.like_comment_count_layout, 11);
        sViewsWithIds.put(R.id.view, 12);
        sViewsWithIds.put(R.id.like_comment_layout, 13);
        sViewsWithIds.put(R.id.post_comment, 14);
        sViewsWithIds.put(R.id.postShare, 15);
        sViewsWithIds.put(R.id.view1, 16);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LinkViewBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds));
    }
    private LinkViewBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[8]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[13]
            , (com.makeramen.roundedimageview.RoundedImageView) bindings[5]
            , (android.widget.TextView) bindings[10]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[12]
            , (android.view.View) bindings[16]
            );
        this.like.setTag(null);
        this.linkImage.setTag(null);
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
        if (BR.linkbind == variableId) {
            setLinkbind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setLinkbind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Linkbind) {
        this.mLinkbind = Linkbind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.linkbind);
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
        java.lang.String linkbindMyLike = null;
        boolean linkbindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String linkbindProfilePicture = null;
        java.lang.String linkbindTotalCommentsJavaLangStringComment = null;
        java.lang.String linkbindCreated = null;
        int linkbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        java.lang.String linkbindTotalLikes = null;
        java.lang.String linkbindTotalComments = null;
        java.lang.String linkbindName = null;
        java.lang.String linkbindMyPinned = null;
        com.utkarshnew.android.feeds.dataclass.Data linkbind = mLinkbind;
        java.lang.String linkbindThumbnail = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (linkbind != null) {
                    // read linkbind.my_like
                    linkbindMyLike = linkbind.getMy_like();
                    // read linkbind.profile_picture
                    linkbindProfilePicture = linkbind.getProfile_picture();
                    // read linkbind.created
                    linkbindCreated = linkbind.getCreated();
                    // read linkbind.total_likes
                    linkbindTotalLikes = linkbind.getTotal_likes();
                    // read linkbind.total_comments
                    linkbindTotalComments = linkbind.getTotal_comments();
                    // read linkbind.name
                    linkbindName = linkbind.getName();
                    // read linkbind.my_pinned
                    linkbindMyPinned = linkbind.getMy_pinned();
                    // read linkbind.thumbnail
                    linkbindThumbnail = linkbind.getThumbnail();
                }


                // read (linkbind.total_comments) + (" Comment")
                linkbindTotalCommentsJavaLangStringComment = (linkbindTotalComments) + (" Comment");
                if (linkbindMyPinned != null) {
                    // read linkbind.my_pinned.equals("1")
                    linkbindMyPinnedEqualsJavaLangString1 = linkbindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(linkbindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }


                // read linkbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                linkbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((linkbindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, linkbindMyLike);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.linkImage, linkbindThumbnail);
            this.pinIV.setImageResource(linkbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, linkbindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, linkbindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, linkbindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, linkbindProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, linkbindName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): linkbind
        flag 1 (0x2L): null
        flag 2 (0x3L): linkbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 3 (0x4L): linkbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
    flag mapping end*/
    //end
}