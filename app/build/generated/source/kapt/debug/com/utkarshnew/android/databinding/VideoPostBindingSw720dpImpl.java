package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class VideoPostBindingSw720dpImpl extends VideoPostBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 9);
        sViewsWithIds.put(R.id.image_constraint_layout, 10);
        sViewsWithIds.put(R.id.play_video, 11);
        sViewsWithIds.put(R.id.video_text, 12);
        sViewsWithIds.put(R.id.read_more, 13);
        sViewsWithIds.put(R.id.like_comment_count_layout, 14);
        sViewsWithIds.put(R.id.view, 15);
        sViewsWithIds.put(R.id.like_comment_layout, 16);
        sViewsWithIds.put(R.id.post_comment, 17);
        sViewsWithIds.put(R.id.postShare, 18);
        sViewsWithIds.put(R.id.view1, 19);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public VideoPostBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private VideoPostBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[10]
            , (android.widget.TextView) bindings[8]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[12]
            , (android.widget.ImageView) bindings[5]
            , (android.view.View) bindings[15]
            , (android.view.View) bindings[19]
            );
        this.like.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pinIV.setTag(null);
        this.postCommentCount.setTag(null);
        this.postLikeCount.setTag(null);
        this.postTime.setTag(null);
        this.profileImage.setTag(null);
        this.userName.setTag(null);
        this.videoThumbnail.setTag(null);
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
        if (BR.videopostbind == variableId) {
            setVideopostbind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setVideopostbind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Videopostbind) {
        this.mVideopostbind = Videopostbind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.videopostbind);
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
        java.lang.String videopostbindName = null;
        boolean videopostbindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String videopostbindTotalCommentsJavaLangStringComment = null;
        java.lang.String videopostbindTotalLikes = null;
        java.lang.String videopostbindTotalComments = null;
        java.lang.String videopostbindMyLike = null;
        com.utkarshnew.android.feeds.dataclass.Data videopostbind = mVideopostbind;
        java.lang.String videopostbindCreated = null;
        java.lang.String videopostbindThumbnail = null;
        java.lang.String videopostbindMyPinned = null;
        int videopostbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        java.lang.String videopostbindProfilePicture = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (videopostbind != null) {
                    // read videopostbind.name
                    videopostbindName = videopostbind.getName();
                    // read videopostbind.total_likes
                    videopostbindTotalLikes = videopostbind.getTotal_likes();
                    // read videopostbind.total_comments
                    videopostbindTotalComments = videopostbind.getTotal_comments();
                    // read videopostbind.my_like
                    videopostbindMyLike = videopostbind.getMy_like();
                    // read videopostbind.created
                    videopostbindCreated = videopostbind.getCreated();
                    // read videopostbind.thumbnail
                    videopostbindThumbnail = videopostbind.getThumbnail();
                    // read videopostbind.my_pinned
                    videopostbindMyPinned = videopostbind.getMy_pinned();
                    // read videopostbind.profile_picture
                    videopostbindProfilePicture = videopostbind.getProfile_picture();
                }


                // read (videopostbind.total_comments) + (" Comment")
                videopostbindTotalCommentsJavaLangStringComment = (videopostbindTotalComments) + (" Comment");
                if (videopostbindMyPinned != null) {
                    // read videopostbind.my_pinned.equals("1")
                    videopostbindMyPinnedEqualsJavaLangString1 = videopostbindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(videopostbindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }


                // read videopostbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                videopostbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((videopostbindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, videopostbindMyLike);
            this.pinIV.setImageResource(videopostbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, videopostbindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, videopostbindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, videopostbindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, videopostbindProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, videopostbindName);
            com.utkarshnew.android.feeds.dataclass.DataKt.imagepost(this.videoThumbnail, videopostbindThumbnail);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): videopostbind
        flag 1 (0x2L): null
        flag 2 (0x3L): videopostbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 3 (0x4L): videopostbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
    flag mapping end*/
    //end
}