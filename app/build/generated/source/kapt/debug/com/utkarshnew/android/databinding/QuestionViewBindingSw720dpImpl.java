package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class QuestionViewBindingSw720dpImpl extends QuestionViewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 12);
        sViewsWithIds.put(R.id.constraint, 13);
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

    public QuestionViewBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds));
    }
    private QuestionViewBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.constraintlayout.widget.ConstraintLayout) bindings[13]
            , (android.widget.TextView) bindings[11]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[18]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (com.ahmadnemati.clickablewebview.ClickableWebView) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[7]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[15]
            , (android.view.View) bindings[19]
            );
        this.like.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pinIV.setTag(null);
        this.postAttempt.setTag(null);
        this.postCommentCount.setTag(null);
        this.postLikeCount.setTag(null);
        this.postTime.setTag(null);
        this.profileImage.setTag(null);
        this.questionTxt.setTag(null);
        this.recyerclerView.setTag(null);
        this.recyerclerViewWebview.setTag(null);
        this.userName.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x8L;
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
        if (BR.questionbind == variableId) {
            setQuestionbind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else if (BR.optionadapter == variableId) {
            setOptionadapter((com.utkarshnew.android.feeds.adapters.OptionAdapter) variable);
        }
        else if (BR.optionwebadapter == variableId) {
            setOptionwebadapter((com.utkarshnew.android.feeds.adapters.OptionWebAdapter) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setQuestionbind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Questionbind) {
        this.mQuestionbind = Questionbind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.questionbind);
        super.requestRebind();
    }
    public void setOptionadapter(@Nullable com.utkarshnew.android.feeds.adapters.OptionAdapter Optionadapter) {
        this.mOptionadapter = Optionadapter;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.optionadapter);
        super.requestRebind();
    }
    public void setOptionwebadapter(@Nullable com.utkarshnew.android.feeds.adapters.OptionWebAdapter Optionwebadapter) {
        this.mOptionwebadapter = Optionwebadapter;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.optionwebadapter);
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
        boolean questionbindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String questionbindJsonTotalAttempt = null;
        java.lang.String questionbindCreated = null;
        java.lang.String questionbindName = null;
        com.utkarshnew.android.feeds.dataclass.Data questionbind = mQuestionbind;
        java.lang.String questionbindMyLike = null;
        java.lang.String questionbindTotalCommentsJavaLangStringComment = null;
        java.lang.String questionbindTotalComments = null;
        java.lang.String questionbindMetaUrl = null;
        com.utkarshnew.android.feeds.dataclass.Json questionbindJson = null;
        int questionbindMetaUrlEmptyViewGONEViewVISIBLE = 0;
        java.lang.String questionbindMyPinned = null;
        com.utkarshnew.android.feeds.adapters.OptionAdapter optionadapter = mOptionadapter;
        int questionbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        com.utkarshnew.android.feeds.adapters.OptionWebAdapter optionwebadapter = mOptionwebadapter;
        java.lang.String questionbindProfilePicture = null;
        java.lang.String questionbindTotalLikes = null;
        boolean questionbindMetaUrlEmpty = false;

        if ((dirtyFlags & 0x9L) != 0) {



                if (questionbind != null) {
                    // read questionbind.created
                    questionbindCreated = questionbind.getCreated();
                    // read questionbind.name
                    questionbindName = questionbind.getName();
                    // read questionbind.my_like
                    questionbindMyLike = questionbind.getMy_like();
                    // read questionbind.total_comments
                    questionbindTotalComments = questionbind.getTotal_comments();
                    // read questionbind.meta_url
                    questionbindMetaUrl = questionbind.getMeta_url();
                    // read questionbind.json
                    questionbindJson = questionbind.getJson();
                    // read questionbind.my_pinned
                    questionbindMyPinned = questionbind.getMy_pinned();
                    // read questionbind.profile_picture
                    questionbindProfilePicture = questionbind.getProfile_picture();
                    // read questionbind.total_likes
                    questionbindTotalLikes = questionbind.getTotal_likes();
                }


                // read (questionbind.total_comments) + (" Comment")
                questionbindTotalCommentsJavaLangStringComment = (questionbindTotalComments) + (" Comment");
                if (questionbindMetaUrl != null) {
                    // read questionbind.meta_url.empty
                    questionbindMetaUrlEmpty = questionbindMetaUrl.isEmpty();
                }
            if((dirtyFlags & 0x9L) != 0) {
                if(questionbindMetaUrlEmpty) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }
                if (questionbindJson != null) {
                    // read questionbind.json.total_attempt
                    questionbindJsonTotalAttempt = questionbindJson.getTotal_attempt();
                }
                if (questionbindMyPinned != null) {
                    // read questionbind.my_pinned.equals("1")
                    questionbindMyPinnedEqualsJavaLangString1 = questionbindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x9L) != 0) {
                if(questionbindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x80L;
                }
                else {
                        dirtyFlags |= 0x40L;
                }
            }


                // read questionbind.meta_url.empty ? View.GONE : View.VISIBLE
                questionbindMetaUrlEmptyViewGONEViewVISIBLE = ((questionbindMetaUrlEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                // read questionbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                questionbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((questionbindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
        }
        if ((dirtyFlags & 0xaL) != 0) {
        }
        if ((dirtyFlags & 0xcL) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x9L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, questionbindMyLike);
            this.pinIV.setImageResource(questionbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            com.utkarshnew.android.feeds.ExtensionFucationKt.attempts(this.postAttempt, questionbindJsonTotalAttempt);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, questionbindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, questionbindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, questionbindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, questionbindProfilePicture);
            this.questionTxt.setVisibility(questionbindMetaUrlEmptyViewGONEViewVISIBLE);
            com.utkarshnew.android.feeds.ExtensionFucationKt.setwebview(this.questionTxt, questionbindMetaUrl);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, questionbindName);
        }
        if ((dirtyFlags & 0xaL) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.setAdapter(this.recyerclerView, optionadapter);
        }
        if ((dirtyFlags & 0xcL) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.setAdapter(this.recyerclerViewWebview, optionwebadapter);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): questionbind
        flag 1 (0x2L): optionadapter
        flag 2 (0x3L): optionwebadapter
        flag 3 (0x4L): null
        flag 4 (0x5L): questionbind.meta_url.empty ? View.GONE : View.VISIBLE
        flag 5 (0x6L): questionbind.meta_url.empty ? View.GONE : View.VISIBLE
        flag 6 (0x7L): questionbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 7 (0x8L): questionbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
    flag mapping end*/
    //end
}