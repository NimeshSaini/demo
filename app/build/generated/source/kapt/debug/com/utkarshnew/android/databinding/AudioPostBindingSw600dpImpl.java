package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class AudioPostBindingSw600dpImpl extends AudioPostBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 8);
        sViewsWithIds.put(R.id.auido_layout, 9);
        sViewsWithIds.put(R.id.play_icon, 10);
        sViewsWithIds.put(R.id.audio_text, 11);
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

    public AudioPostBindingSw600dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds));
    }
    private AudioPostBindingSw600dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[11]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.TextView) bindings[7]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.ImageView) bindings[10]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[14]
            , (android.view.View) bindings[18]
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
        if (BR.audiobind == variableId) {
            setAudiobind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAudiobind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Audiobind) {
        this.mAudiobind = Audiobind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.audiobind);
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
        int audiobindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        java.lang.String audiobindTotalComments = null;
        java.lang.String audiobindName = null;
        java.lang.String audiobindCreated = null;
        java.lang.String audiobindMyLike = null;
        com.utkarshnew.android.feeds.dataclass.Data audiobind = mAudiobind;
        java.lang.String audiobindMyPinned = null;
        boolean audiobindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String audiobindTotalCommentsJavaLangStringComment = null;
        java.lang.String audiobindProfilePicture = null;
        java.lang.String audiobindTotalLikes = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (audiobind != null) {
                    // read audiobind.total_comments
                    audiobindTotalComments = audiobind.getTotal_comments();
                    // read audiobind.name
                    audiobindName = audiobind.getName();
                    // read audiobind.created
                    audiobindCreated = audiobind.getCreated();
                    // read audiobind.my_like
                    audiobindMyLike = audiobind.getMy_like();
                    // read audiobind.my_pinned
                    audiobindMyPinned = audiobind.getMy_pinned();
                    // read audiobind.profile_picture
                    audiobindProfilePicture = audiobind.getProfile_picture();
                    // read audiobind.total_likes
                    audiobindTotalLikes = audiobind.getTotal_likes();
                }


                // read (audiobind.total_comments) + (" Comment")
                audiobindTotalCommentsJavaLangStringComment = (audiobindTotalComments) + (" Comment");
                if (audiobindMyPinned != null) {
                    // read audiobind.my_pinned.equals("1")
                    audiobindMyPinnedEqualsJavaLangString1 = audiobindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(audiobindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }


                // read audiobind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                audiobindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((audiobindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, audiobindMyLike);
            this.pinIV.setImageResource(audiobindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, audiobindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, audiobindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, audiobindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, audiobindProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, audiobindName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): audiobind
        flag 1 (0x2L): null
        flag 2 (0x3L): audiobind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 3 (0x4L): audiobind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
    flag mapping end*/
    //end
}