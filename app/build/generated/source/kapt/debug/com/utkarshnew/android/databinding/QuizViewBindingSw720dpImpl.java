package com.utkarshnew.android.databinding;
import com.utkarshnew.android.R;
import com.utkarshnew.android.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class QuizViewBindingSw720dpImpl extends QuizViewBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.post_subject, 12);
        sViewsWithIds.put(R.id.quiz_layout, 13);
        sViewsWithIds.put(R.id.img, 14);
        sViewsWithIds.put(R.id.test_info, 15);
        sViewsWithIds.put(R.id.like_comment_count_layout, 16);
        sViewsWithIds.put(R.id.view, 17);
        sViewsWithIds.put(R.id.like_comment_layout, 18);
        sViewsWithIds.put(R.id.post_comment, 19);
        sViewsWithIds.put(R.id.postShare, 20);
        sViewsWithIds.put(R.id.view1, 21);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public QuizViewBindingSw720dpImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private QuizViewBindingSw720dpImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[14]
            , (android.widget.TextView) bindings[11]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[12]
            , (android.widget.TextView) bindings[4]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[1]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.Button) bindings[8]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[2]
            , (android.view.View) bindings[17]
            , (android.view.View) bindings[21]
            );
        this.like.setTag(null);
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pinIV.setTag(null);
        this.postCommentCount.setTag(null);
        this.postLikeCount.setTag(null);
        this.postTime.setTag(null);
        this.profileImage.setTag(null);
        this.startQuiz.setTag(null);
        this.testName.setTag(null);
        this.totalMin.setTag(null);
        this.totalQuestion.setTag(null);
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
        if (BR.quizbind == variableId) {
            setQuizbind((com.utkarshnew.android.feeds.dataclass.Data) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setQuizbind(@Nullable com.utkarshnew.android.feeds.dataclass.Data Quizbind) {
        this.mQuizbind = Quizbind;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.quizbind);
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
        java.lang.String quizbindName = null;
        java.lang.String quizbindTotalComments = null;
        java.lang.String quizbindJsonTotalQuestions = null;
        java.lang.String quizbindJsonStateEqualsIgnoreCaseJavaLangString1JavaLangStringViewResultJavaLangStringStartQUIZ = null;
        java.lang.String quizbindMyLike = null;
        boolean quizbindJsonStateEqualsIgnoreCaseJavaLangString1 = false;
        java.lang.String quizbindJsonTestSeriesName = null;
        boolean quizbindJsonTestSeriesNameEmpty = false;
        java.lang.String quizbindTotalCommentsJavaLangStringComment = null;
        java.lang.String quizbindJsonTimeInMinsJavaLangStringMinutes = null;
        int quizbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = 0;
        com.utkarshnew.android.feeds.dataclass.Data quizbind = mQuizbind;
        java.lang.String quizbindMyPinned = null;
        java.lang.String quizbindJsonTimeInMins = null;
        java.lang.String quizbindTotalLikes = null;
        java.lang.String quizbindJsonState = null;
        java.lang.String quizbindJsonTotalQuestionsJavaLangStringQuestions = null;
        boolean quizbindMyPinnedEqualsJavaLangString1 = false;
        java.lang.String quizbindCreated = null;
        java.lang.String quizbindProfilePicture = null;
        com.utkarshnew.android.feeds.dataclass.Json quizbindJson = null;
        int quizbindJsonTestSeriesNameEmptyViewGONEViewVISIBLE = 0;

        if ((dirtyFlags & 0x3L) != 0) {



                if (quizbind != null) {
                    // read quizbind.name
                    quizbindName = quizbind.getName();
                    // read quizbind.total_comments
                    quizbindTotalComments = quizbind.getTotal_comments();
                    // read quizbind.my_like
                    quizbindMyLike = quizbind.getMy_like();
                    // read quizbind.my_pinned
                    quizbindMyPinned = quizbind.getMy_pinned();
                    // read quizbind.total_likes
                    quizbindTotalLikes = quizbind.getTotal_likes();
                    // read quizbind.created
                    quizbindCreated = quizbind.getCreated();
                    // read quizbind.profile_picture
                    quizbindProfilePicture = quizbind.getProfile_picture();
                    // read quizbind.json
                    quizbindJson = quizbind.getJson();
                }


                // read (quizbind.total_comments) + (" Comment")
                quizbindTotalCommentsJavaLangStringComment = (quizbindTotalComments) + (" Comment");
                if (quizbindMyPinned != null) {
                    // read quizbind.my_pinned.equals("1")
                    quizbindMyPinnedEqualsJavaLangString1 = quizbindMyPinned.equals("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(quizbindMyPinnedEqualsJavaLangString1) {
                        dirtyFlags |= 0x20L;
                }
                else {
                        dirtyFlags |= 0x10L;
                }
            }
                if (quizbindJson != null) {
                    // read quizbind.json.total_questions
                    quizbindJsonTotalQuestions = quizbindJson.getTotal_questions();
                    // read quizbind.json.test_series_name
                    quizbindJsonTestSeriesName = quizbindJson.getTest_series_name();
                    // read quizbind.json.time_in_mins
                    quizbindJsonTimeInMins = quizbindJson.getTime_in_mins();
                    // read quizbind.json.state
                    quizbindJsonState = quizbindJson.getState();
                }


                // read quizbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
                quizbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned = ((quizbindMyPinnedEqualsJavaLangString1) ? (com.utkarshnew.android.R.drawable.pinned) : (com.utkarshnew.android.R.drawable.unpinned));
                // read (quizbind.json.total_questions) + (" Questions")
                quizbindJsonTotalQuestionsJavaLangStringQuestions = (quizbindJsonTotalQuestions) + (" Questions");
                // read (quizbind.json.time_in_mins) + (" Minutes")
                quizbindJsonTimeInMinsJavaLangStringMinutes = (quizbindJsonTimeInMins) + (" Minutes");
                if (quizbindJsonTestSeriesName != null) {
                    // read quizbind.json.test_series_name.empty
                    quizbindJsonTestSeriesNameEmpty = quizbindJsonTestSeriesName.isEmpty();
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(quizbindJsonTestSeriesNameEmpty) {
                        dirtyFlags |= 0x80L;
                }
                else {
                        dirtyFlags |= 0x40L;
                }
            }
                if (quizbindJsonState != null) {
                    // read quizbind.json.state.equalsIgnoreCase("1")
                    quizbindJsonStateEqualsIgnoreCaseJavaLangString1 = quizbindJsonState.equalsIgnoreCase("1");
                }
            if((dirtyFlags & 0x3L) != 0) {
                if(quizbindJsonStateEqualsIgnoreCaseJavaLangString1) {
                        dirtyFlags |= 0x8L;
                }
                else {
                        dirtyFlags |= 0x4L;
                }
            }


                // read quizbind.json.test_series_name.empty ? View.GONE : View.VISIBLE
                quizbindJsonTestSeriesNameEmptyViewGONEViewVISIBLE = ((quizbindJsonTestSeriesNameEmpty) ? (android.view.View.GONE) : (android.view.View.VISIBLE));
                // read quizbind.json.state.equalsIgnoreCase("1") ? "View Result" : "Start QUIZ"
                quizbindJsonStateEqualsIgnoreCaseJavaLangString1JavaLangStringViewResultJavaLangStringStartQUIZ = ((quizbindJsonStateEqualsIgnoreCaseJavaLangString1) ? ("View Result") : ("Start QUIZ"));
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            com.utkarshnew.android.feeds.dataclass.DataKt.viewlike(this.like, quizbindMyLike);
            this.pinIV.setImageResource(quizbindMyPinnedEqualsJavaLangString1ComUtkarshnewAndroidRDrawablePinnedComUtkarshnewAndroidRDrawableUnpinned);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.postCommentCount, quizbindTotalCommentsJavaLangStringComment);
            com.utkarshnew.android.feeds.ExtensionFucationKt.like(this.postLikeCount, quizbindTotalLikes);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.postTime, quizbindCreated);
            com.utkarshnew.android.feeds.dataclass.DataKt.loadImage(this.profileImage, quizbindProfilePicture);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.startQuiz, quizbindJsonStateEqualsIgnoreCaseJavaLangString1JavaLangStringViewResultJavaLangStringStartQUIZ);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.testName, quizbindJsonTestSeriesName);
            this.testName.setVisibility(quizbindJsonTestSeriesNameEmptyViewGONEViewVISIBLE);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.totalMin, quizbindJsonTimeInMinsJavaLangStringMinutes);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.totalQuestion, quizbindJsonTotalQuestionsJavaLangStringQuestions);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.userName, quizbindName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): quizbind
        flag 1 (0x2L): null
        flag 2 (0x3L): quizbind.json.state.equalsIgnoreCase("1") ? "View Result" : "Start QUIZ"
        flag 3 (0x4L): quizbind.json.state.equalsIgnoreCase("1") ? "View Result" : "Start QUIZ"
        flag 4 (0x5L): quizbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 5 (0x6L): quizbind.my_pinned.equals("1") ? com.utkarshnew.android.R.drawable.pinned : com.utkarshnew.android.R.drawable.unpinned
        flag 6 (0x7L): quizbind.json.test_series_name.empty ? View.GONE : View.VISIBLE
        flag 7 (0x8L): quizbind.json.test_series_name.empty ? View.GONE : View.VISIBLE
    flag mapping end*/
    //end
}