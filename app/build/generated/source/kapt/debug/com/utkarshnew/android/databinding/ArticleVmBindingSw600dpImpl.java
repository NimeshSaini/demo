package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ArticleVmBindingSw600dpImpl extends ArticleVmBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 8);
        sViewsWithIds.put(R.id.article_txt_top, 9);
        sViewsWithIds.put(R.id.article_txt, 10);
        sViewsWithIds.put(R.id.read_more, 11);
        sViewsWithIds.put(R.id.like_comment_count_layout, 12);
        sViewsWithIds.put(R.id.view, 13);
        sViewsWithIds.put(R.id.like_comment_layout, 14);
        sViewsWithIds.put(R.id.post_comment, 15);
        sViewsWithIds.put(R.id.postShare, 16);
        sViewsWithIds.put(R.id.view1, 17);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ArticleVmBindingSw600dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private ArticleVmBindingSw600dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[7]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.ImageView) bindings[4]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[3]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[13]
            , (android.view.View) bindings[17]
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
        if (BR.articlebind == variableId) {
            setArticlebind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setArticlebind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Articlebind) {
        this.mArticlebind = Articlebind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.articlebind);
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
        java.lang.String articlebindProfilePicture = null;
        java.lang.String articlebindCreated = null;
        java.lang.String articlebindMyLike = null;
        java.lang.String articlebindMyPinned = null;
        java.lang.String articlebindTotalLikes = null;
        java.lang.String articlebindTotalComments = null;
        boolean articlebindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String articlebindTotalCommentsJavaLangStringComment = null;
        int articlebindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        com.utkarshnew.android.feeds.dataclass.Data articlebind = mArticlebind;
        java.lang.String articlebindName = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (articlebind != null) {
                    // read articlebind.profile_picture
                    articlebindProfilePicture = articlebind.getProfile_picture();
                    // read articlebind.created
                    articlebindCreated = articlebind.getCreated();
                    // read articlebind.my_like
                    articlebindMyLike = articlebind.getMy_like();
                    // read articlebind.my_pinned
                    articlebindMyPinned = articlebind.getMy_pinned();
                    // read articlebind.total_likes
                    articlebindTotalLikes = articlebind.getTotal_likes();
                    // read articlebind.total_comments
                    articlebindTotalComments = articlebind.getTotal_comments();
                    // read articlebind.name
                    articlebindName = articlebind.getName();
                }


                if (articlebindMyPinned != null) {
                    // read articlebind.my_pinned.equals("1")
                    articlebindMyPinnedEqualsJavaLangString1 = articlebindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(articlebindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }
                // read (articlebind.total_comments) + (" Comment")
                articlebindTotalCommentsJavaLangStringComment = (articlebindTotalComments) + (" Comment");


                // read articlebind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                articlebindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((articlebindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, articlebindMyLike);
            this.pinIV.setImageResource(articlebindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, articlebindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, articlebindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, articlebindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, articlebindProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, articlebindName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): articlebind
        flag 1 (0x2L): null
        flag 2 (0x3L): articlebind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 3 (0x4L): articlebind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
    flag mapping end*/
    //end
}