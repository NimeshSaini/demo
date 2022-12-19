package com.utkarshnew.android.testmodule.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.adapter.AdapterMatchingListDrag;
import com.utkarshnew.android.testmodule.adapter.AdapterMatchingListNormal;
import com.utkarshnew.android.testmodule.interfaces.MachingOnDrag;
import com.utkarshnew.android.testmodule.interfaces.WebTextSize;
import com.utkarshnew.android.testmodule.model.SectioHashMap;
import com.utkarshnew.android.testmodule.model.Social;
import com.utkarshnew.android.testmodule.model.mcSelection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import io.github.kexanie.library.MathView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SC_TestSeriesFragment extends MainFragment implements View.OnClickListener, Html.ImageGetter, MachingOnDrag, WebTextSize {
    Progress mProgress;
    LinearLayout mcqoptionsLL, LLmatchinquestion;
    RecyclerView rvmatchinquestion1, rvmatchinquestion2;
    View parent_view;
    int count = 0;
    private AdapterMatchingListDrag adapterMatchingListDrag;
    private AdapterMatchingListNormal adapterMatchingListNormal;
    private ItemTouchHelper mItemTouchHelper;
    LinearLayout parentLL;
    NestedScrollView nestedSV;
    List<View> LinearLayoutList;
    List<View> parentList, tvList;
    int position;
    String questionType;
    LinearLayout explanationLL;
    int selectedAnswerposition;
    RelativeLayout question_layout1;
    TextView explanationTV;
    ClickableWebView tvQuestion, tvQuestionFib;
    private TextView tv_uid, tvEmail;
    private TextView markForReview, remove_mark_for_review, save_mark_for_review, tvReportError, unmarkForReview;
    private boolean status;
    private boolean status1;
    private ImageView imgBookmark;
    ArrayList<Integer> selectedValue = new ArrayList();
    ArrayList<mcSelection> selected = new ArrayList();
    ArrayList<mcSelection> selected2 = new ArrayList();
    private CheckBox checkBoxGuess;
    private Drawable empty;
    private String isCorrect, guess;
    public static final String TAG_START = "<\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[^'\">\\s]+))?)+\\s*|\\s*)>";
    public static final String TAG_END = "</\\w+>";
    public static final String TAG_SELF_CLOSING = "<\\w+((\\s+\\w+(\\s*=\\s*(?:\".*?\"|'.*?'|[^'\">\\s]+))?)+\\s*|\\s*)/>";
    public static final String HTML_ENTITY = "&[a-zA-Z][a-zA-Z0-9]+;";
    public static final Pattern htmlPattern = Pattern
            .compile("(" + TAG_START + ".*" + TAG_END + ")|(" + TAG_SELF_CLOSING + ")|(" + HTML_ENTITY + ")", Pattern.DOTALL);
    ArrayList tags = new ArrayList();
    private int textSize=0;
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!((TestBaseActivity) requireActivity()).questionBankList.get(position).isIssaveMarkForReview()) {
                switch (questionType) {
                    case "TF":
                        SC_Clicked(view);
                        //mFragmentList.add(SC_TestSeriesFragment.newInstance(i));
                        break;
                    case "MC":
                        MC_Clicked(view);
                        break;
                    case "MT":
                        break;
                    case "SC":

                        SC_Clicked(view);

                        break;
                    case "FIB":
                        break;
                    default:
                        SC_Clicked(view);
                }
            } else {
                Toast.makeText(activity, "select remove & mark for review.", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void SC_Clicked(View view) {
        SectioHashMap sectioHashMap = ((TestBaseActivity) requireActivity()).sectioncount.get(((TestBaseActivity) requireActivity()).PartId);
        if (Objects.requireNonNull(sectioHashMap).getSectioncount() < sectioHashMap.getOptionCount()) {
            setAnswer(sectioHashMap, view);
        } else if (((TestBaseActivity) requireActivity()).questionBankList.get(position).isanswer()) {
            setAnswer(sectioHashMap, view);
        } else {
            Toast.makeText(activity, "You Can't Attempt More Questions in this Section", Toast.LENGTH_SHORT).show();
        }

    }


    private void setQuestionAndAnswer() {
        switch (questionType) {
            case "MT":
                addMachingQuestionOption();
                break;
            case "FIB":
                break;
            default:

                try {
                    parentList.clear();
                    mcqoptionsLL.removeAllViews();
                    LinearLayoutList.clear();
                    tvList.clear();
                    addQuestionOption();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        }
    }

    private void setHtmlSize(int type){
        if (type==2){
            tvQuestion.getSettings().setTextZoom(200);
            tvQuestion.setVerticalScrollBarEnabled(false);
            tvQuestion.setHorizontalScrollBarEnabled(true);
            tvQuestion.setHorizontalFadingEdgeEnabled(true);
            tvQuestionFib.getSettings().setTextZoom(200);
            tvQuestionFib.setVerticalScrollBarEnabled(false);
            tvQuestionFib.setHorizontalScrollBarEnabled(true);
            tvQuestionFib.setHorizontalFadingEdgeEnabled(true);
        }else if (type==1){
            tvQuestion.getSettings().setTextZoom(150);
            tvQuestion.setVerticalScrollBarEnabled(false);
            tvQuestion.setHorizontalScrollBarEnabled(true);
            tvQuestion.setHorizontalFadingEdgeEnabled(true);
            tvQuestionFib.getSettings().setTextZoom( 150);
            tvQuestionFib.setVerticalScrollBarEnabled(false);
            tvQuestionFib.setHorizontalScrollBarEnabled(true);
            tvQuestionFib.setHorizontalFadingEdgeEnabled(true);
        }else {
            tvQuestion.getSettings().setTextZoom(100);
            tvQuestion.setVerticalScrollBarEnabled(false);
            tvQuestion.setHorizontalScrollBarEnabled(false);
            tvQuestion.setHorizontalFadingEdgeEnabled(false);
            tvQuestionFib.getSettings().setTextZoom(100);
            tvQuestionFib.setVerticalScrollBarEnabled(false);
            tvQuestionFib.setHorizontalScrollBarEnabled(false);
            tvQuestionFib.setHorizontalFadingEdgeEnabled(false);
        }

    }

    private void setAnswer(SectioHashMap sectioHashMap, View view) {
        int j = 0;
        selectedAnswerposition = (int) view.getTag();
        j = LinearLayoutList.size();
        if (tags != null) tags.clear();
        if (!((TestBaseActivity) requireActivity()).questionBankList.get(position).isanswer() && ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() == 0 || ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() == -1) {
            for (int i = 0; i < LinearLayoutList.size(); i++) {
                if (selectedAnswerposition == i)
                    tags.add(i, "1");
                else tags.add("0");
            }
            ((TestBaseActivity) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
            }
        } else if (((TestBaseActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0 && ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != -1)
            if (((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() == selectedAnswerposition + 1) {
                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    tags.add(i, "0");
                }
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setIsanswer(false, 0);
                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
                }
            } else {
                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    if (selectedAnswerposition == i)
                        tags.add(i, "1");
                    else tags.add("0");
                }
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                }
            }
        explanationLL.setVisibility(View.GONE);
        for (int k = 0; k < j; k++) {
            if (selectedAnswerposition == k) {
                if (LinearLayoutList.get(k).isSelected()) {
                    Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() > 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                    ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                    Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                    Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);

                    LinearLayoutList.get(k).setSelected(false);
                } else {
                    LinearLayoutList.get(k).setSelected(true);
                    Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() >= 0 ? sectioHashMap.getSectioncount() + 1 : 0);
                    ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                    Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                    Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
                }
            } else {
                if (LinearLayoutList.get(k).isSelected()) {
                    Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() > 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                    ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                    Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                    Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
                }
                LinearLayoutList.get(k).setSelected(false);
            }
        }
    }


    void set_MC_Answer(View view, SectioHashMap sectioHashMap) {
        selectedAnswerposition = (int) view.getTag();
        String answerPosition = "";
        boolean isSelected = false;
        if (LinearLayoutList.get(selectedAnswerposition).isSelected()) {
            for (int i = 0; i < selectedValue.size(); i++) {
                if (selectedAnswerposition == selectedValue.get(i)) {
                    LinearLayoutList.get(selectedAnswerposition).setSelected(false);
                    selectedValue.remove(i);
                    break;
                }
            }
            ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue);
            }
            if (selectedValue.size() == 0) {
                Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() >= 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
            }
        } else {

            LinearLayoutList.get(selectedAnswerposition).setSelected(true);
            if (LinearLayoutList.get(selectedAnswerposition).isSelected()) {
                if (selectedValue.size() == 0) {
                    Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() >= 0 ? sectioHashMap.getSectioncount() + 1 : 0);
                    ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                    Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                    Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
                }
            }
            if (!selectedValue.contains(selectedAnswerposition))
                selectedValue.add(selectedAnswerposition);
            ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue);
            }
        }
        for (int j = 0; j < LinearLayoutList.size(); j++) {
            if (LinearLayoutList.get(j).isSelected()) {
                isSelected = true;
            }
        }
        if (isSelected) {
            for (int i = 0; i < ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().size(); i++) {
                answerPosition = answerPosition + ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().get(i) + ",";
            }
            tags.clear();
            for (int i = 0; i < LinearLayoutList.size(); i++) {
                if (answerPosition.contains(String.valueOf(i)))
                    tags.add("1");
                else tags.add("0");
            }
            ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, tags);
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, tags);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, tags);
            }
        } else {
            ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(false, 0);
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
            }
        }
        Log.e("IS_SELECTED", String.valueOf(isSelected));
    }

    private void MC_Clicked(View view) {
        SectioHashMap sectioHashMap = ((TestBaseActivity) requireActivity()).sectioncount.get(((TestBaseActivity) getActivity()).PartId);
        if (Objects.requireNonNull(sectioHashMap).getSectioncount() < sectioHashMap.getOptionCount()) {
            set_MC_Answer(view, sectioHashMap);
        } else if (selectedValue.size() > 0) {
            set_MC_Answer(view, sectioHashMap);
        } else {
            Toast.makeText(activity, "You Can't Attempt More Questions in this Section", Toast.LENGTH_SHORT).show();
        }

    }

    public static SC_TestSeriesFragment newInstance(int position, String questionType,int textSize) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("textSize", textSize);
        args.putString("questionType", questionType);
        SC_TestSeriesFragment fragment = new SC_TestSeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_series, null);
        activity = getActivity();
        getBundleData();
        initView(view);
        mProgress = new Progress(activity);
        return view;
    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position", 0);
            textSize = bundle.getInt("textSize", 0);
            questionType = bundle.getString("questionType", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView(View view) {
        mcqoptionsLL = view.findViewById(R.id.mcqoptions);
        LLmatchinquestion = view.findViewById(R.id.LLmatchinquestion);
        rvmatchinquestion1 = view.findViewById(R.id.rvmatchinquestion1);
        rvmatchinquestion2 = view.findViewById(R.id.rvmatchinquestion2);
        rvmatchinquestion1.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setHasFixedSize(true);
        tvQuestion = view.findViewById(R.id.tv_question);
        question_layout1 = view.findViewById(R.id.question_layout1);
        imgBookmark = view.findViewById(R.id.img_bookmark);
        checkBoxGuess = view.findViewById(R.id.checkBox);
        markForReview = view.findViewById(R.id.mark_for_review);
        tvQuestionFib = view.findViewById(R.id.tv_question_fib);
        save_mark_for_review = view.findViewById(R.id.save_mark_for_review);
        remove_mark_for_review = view.findViewById(R.id.remove_mark_for_review);
        unmarkForReview = view.findViewById(R.id.unmark_for_review);
        tvReportError = view.findViewById(R.id.tv_report_error);
        tv_uid = view.findViewById(R.id.tv_uid);
        tvEmail = view.findViewById(R.id.tv_email);
        explanationLL = view.findViewById(R.id.explanationLL);
        explanationTV = view.findViewById(R.id.explanationTV);
        nestedSV = view.findViewById(R.id.nestedSV);
        parent_view = view.findViewById(android.R.id.content);
        LinearLayoutList = new ArrayList<>();
        parentList = new ArrayList<>();
        tvList = new ArrayList<>();
        // ((TestBaseActivity) getActivity()).questionBankList.get(position) = ((TestBaseActivity) getActivity()).questionBankList.get(position);
        switch (questionType) {
            case "MT":
                mcqoptionsLL.setVisibility(View.GONE);
                LLmatchinquestion.setVisibility(View.VISIBLE);
                String htmlAsString = ((TestBaseActivity) requireActivity()).questionBankList.get(position).getQuestion();// used by WebView
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                tvQuestion.setLongClickable(false);
                Log.d("jdjdjdj",htmlAsString);
                //tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString, "text/html", "UTF-8");
                //showWebData(getActivity(), getHtmlUpdatedData("Q-"+(position+1)+". "+htmlAsString), tvQuestion);
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + htmlAsString);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, question_layout1));
                break;
            case "FIB":
                break;
            default:
                mcqoptionsLL.setVisibility(View.VISIBLE);
                LLmatchinquestion.setVisibility(View.GONE);
                String htmlAsString1 = ((TestBaseActivity) getActivity()).questionBankList.get(position).getQuestion();// used by WebView
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + htmlAsString1);
                tvQuestion.setLongClickable(false);
                Log.d("jdjdjdj",htmlAsString1);
        }
        setQuestionAndAnswer();
        if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getParagraphText())) {
            tvQuestionFib.setVisibility(View.GONE);
        } else {
            tvQuestionFib.setVisibility(View.VISIBLE);
            Helper.TestWebHTMLLoad(tvQuestionFib, ((TestBaseActivity) getActivity()).questionBankList.get(position).getParagraphText());
//            tvQuestionFib.setText(Html.fromHtml(((TestBaseActivity) getActivity()).questionBankList.get(position).getParagraphText()));
        }
        tv_uid.setText("Utkarsh QUID " + ((TestBaseActivity) getActivity()).questionBankList.get(position).getId());
        if (((TestBaseActivity) getActivity()).questionBankList.get(position).isMarkForReview()) {
            markForReview.setVisibility(View.GONE);
            unmarkForReview.setVisibility(View.VISIBLE);
        } else {
            markForReview.setVisibility(View.VISIBLE);
            unmarkForReview.setVisibility(View.GONE);
        }
        imgBookmark.setOnClickListener(this);
        checkBoxGuess.setOnClickListener(this);
        markForReview.setOnClickListener(this);
        remove_mark_for_review.setOnClickListener(this);
        save_mark_for_review.setOnClickListener(this);
        unmarkForReview.setOnClickListener(this);
        tvReportError.setOnClickListener(this);
        if (((TestBaseActivity) getActivity()).questionBankList.get(position).getIs_bookmarked().equals("1")) {
            imgBookmark.setImageResource(R.mipmap.bookmark_selected);
        } else {
            imgBookmark.setImageResource(R.mipmap.bookmark_unselected);
        }
        if (((TestBaseActivity) getActivity()).questionBankList.get(position).getIsguess().equals("1"))
            checkBoxGuess.setChecked(true);
        else
            checkBoxGuess.setChecked(false);
        if (((TestBaseActivity) getActivity()).data != null && ((TestBaseActivity) getActivity()).data.getUserDetails() != null) {
            tvEmail.setText(((TestBaseActivity) getActivity()).data.getUserDetails().getEmail());
        } else {
            tvEmail.setText(SharedPreference.getInstance().getLoggedInUser().getEmail());
        }

        setHtmlSize(textSize);
    }

    @Override
    public Drawable getDrawable(String s) {
        LevelListDrawable d = new LevelListDrawable();
        empty = getResources().getDrawable(R.mipmap.course_placeholder);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        new LoadImage().execute(s, d);
        return d;
    }

    @Override
    public void onTextSizeChanged(int size) {
        textSize=size;
        setHtmlSize(size);
        setQuestionAndAnswer();
    }

    private void addQuestionOption() throws UnsupportedEncodingException {
        for (int j = 1; j <= 10; j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (TextUtils.isEmpty(((TestBaseActivity) requireActivity()).questionBankList.get(position).getOption1())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("1", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption1(),
                            "11", false, j - 1));
                    // tags.add("0");
                    break;
                case 2:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption2())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("2", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption2(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 3:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption3())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("3", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption3(),
                            "11", false, j - 1));
                    //    tags.add("0");
                    break;
                case 4:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption4())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("4", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption4(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 5:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption5())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("5", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption5(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 6:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption6())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("6", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption6(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 7:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption7())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("7", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption7(),
                            "11", false, j - 1));
                    //  tags.add("0");
                    break;
                case 8:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption8())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("8", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption8(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 9:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption9())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("9", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption9(),
                            "11", false, j - 1));
                    //  tags.add("0");
                    break;
                case 10:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption10())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("10", ((TestBaseActivity) getActivity()).questionBankList.get(position).getOption10(),
                            "11", false, j - 1));
                    // tags.add("0");
                    break;
            }
        }
        //  ((TestBaseActivity) getActivity()).questionBankList.get(position).setAnswers(tags);
        status = false;
    }

    private void addMachingQuestionOption() {
        if (((TestBaseActivity) getActivity()).items1 != null)
            ((TestBaseActivity) getActivity()).items1.clear();
        else ((TestBaseActivity) getActivity()).items1 = new ArrayList<>();
        if (((TestBaseActivity) getActivity()).items2 != null)
            ((TestBaseActivity) getActivity()).items2.clear();
        else ((TestBaseActivity) getActivity()).items2 = new ArrayList<>();
        for (int j = 1; j <= 10; j++) {
            if (status1)
                break;
            switch (j) {
                case 1:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption1())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("1", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("1", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 2:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption2())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("2", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("2", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption3())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("3", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("3", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 4:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption4())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("4", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("4", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 5:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption5())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("5", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("5", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 6:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption6())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("6", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("6", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 7:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption7())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("7", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("7", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 8:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption8())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("8", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("8", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 9:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption9())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("9", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("9", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 10:
                    if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption10())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).size() == 2) {
                        ((TestBaseActivity) getActivity()).items1.add(new Social("10", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestBaseActivity) getActivity()).items2.add(new Social("10", Arrays.asList(((TestBaseActivity) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
            }
        }
        if (!status1) {
            int[] androidColors = getResources().getIntArray(R.array.mdcolor_random);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            //set data and list2 adapter
            adapterMatchingListDrag = new AdapterMatchingListDrag(activity, ((TestBaseActivity) getActivity()).items1, position);
            rvmatchinquestion2.setAdapter(adapterMatchingListDrag);
            //set data and list1 adapter
            adapterMatchingListNormal = new AdapterMatchingListNormal(activity, ((TestBaseActivity) getActivity()).items2, androidColors, position);
            rvmatchinquestion1.setAdapter(adapterMatchingListNormal);
            // on item list clicked
            adapterMatchingListDrag.setOnItemClickListener(new AdapterMatchingListDrag.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Social obj, int p, int bg, int circle, boolean select) {
                    boolean isSelected = false;
                    //    Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    if (select) {
                        selected.add(new mcSelection(p, bg, circle, select));
                        ((TestBaseActivity) getActivity()).questionBankList.get(position).setSelcted(selected);
                        ((TestBaseActivity) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                        if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setSelcted(selected);
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setSelcted(selected);
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                        }
                    } else {
                        for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                            if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() == p) {
                                ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().remove(i);
                                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).getSelcted().remove(i);
                                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).getSelcted().remove(i);
                                }
                                break;
                            }
                        }
                    }
                    for (int j = 0; j < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
                        if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
                            isSelected = true;
                        }
                    }
                    if (isSelected) {
                        tags.clear();
                        for (int k = 0; k < ((TestBaseActivity) getActivity()).items1.size(); k++) {
                            for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                                if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).isSelect()) {
                                    for (int j = 0; j < ((TestBaseActivity) getActivity()).SAMPLE_CIRCLE.length; j++) {
                                        if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getCirclecolor_code() == ((TestBaseActivity) getActivity()).SAMPLE_CIRCLE[j]) {
                                            tags.add(((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() + 1);
                                            break;
                                        }
                                    }
                                } else {
                                    tags.add(k, 0);
                                    break;
                                }
                            }
                        }
                       /* for (int j = 0; j < ((TestBaseActivity) getActivity()).items1.size(); j++) {
                        }*/
                        if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, 1, tags);
                        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, 1, tags);
                        }
                        ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(true, 1, tags);
                    } else {
                        ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(false, 0);
                        if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
                        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
                        }
                    }
                }
            });
            adapterMatchingListDrag.setMachingOnDrag(new AdapterMatchingListDrag.MachingOnDrag() {
                @Override
                public void sendOnclickInd(int p) {
                    if (adapterMatchingListNormal != null) {
                        for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                            if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                                ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
                                break;
                            }
                        }
                        adapterMatchingListNormal.notifyDataSetChanged();
                    }
                }
            });
            adapterMatchingListNormal.setMachingOnDrag(new AdapterMatchingListNormal.MachingOnDrag() {
                @Override
                public void sendOnclickInd(int p) {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().remove(p);
                    if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().size() == 0)
                        TestBaseActivity.nestselected = false;
                    TestBaseActivity.sameselected = false;
                    adapterMatchingListDrag.notifyDataSetChanged();
                }
            });
            adapterMatchingListNormal.setOnItemClickListener(new AdapterMatchingListNormal.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int p, Social obj, int bg, int circle, boolean select) {
                    // Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    boolean isSelected = false;
                    /*if (adapterMatchingListNormal!=null){
                        for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                            if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() == p) {
                                ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().remove(i);
                                break;
                            }
                        }
                        adapterMatchingListDrag.notifyDataSetChanged();
                    }*/
                    if (select) {
                        selected2.add(new mcSelection(p, bg, circle, select));
                        ((TestBaseActivity) getActivity()).questionBankList.get(position).setSelcted2(selected2);
                    } else {
                        for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                            if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                                ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
                                break;
                            }
                        }
                    }
                }
            });
        }
        status1 = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public LinearLayout initMCQOptionView(String text1, String text2, final String pollValue, boolean pollVisiblity, int tag) throws UnsupportedEncodingException {
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_test_view, null);
        TextView tv = (TextView) v.findViewById(R.id.optionIconTV);
        TextView optionTextTV2 = (TextView) v.findViewById(R.id.optionTextTV2);
        MathView text = (MathView) v.findViewById(R.id.optionTextTV);
        RadioButton radioRB = (RadioButton) v.findViewById(R.id.radioRB);
        ImageView imgOption = v.findViewById(R.id.imgOption);
        parentLL = v.findViewById(R.id.viewLL);
        float size= textSize==0?1:  textSize == 1 ? 1.5f : 2;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);
        if (pollVisiblity) {
            radioRB.setVisibility(View.GONE);
        }
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        v.setLayoutParams(lp);
        tv.setText(text1);
        tv.setHorizontalScrollBarEnabled(false);
        tv.setVerticalScrollBarEnabled(false);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,(int) (size*14));
        tv.setHeight((int) (metrics.scaledDensity*(size*26)));
        tv.setWidth((int) (metrics.scaledDensity*(size*26)));
        tv.setGravity(Gravity.CENTER);
        v.setTag(tag);
        if (text2.contains("<img src=") || text2.contains("math-tex") || text2.contains("https://") || text2.contains("http://") || text2.contains("<img style")) {
            optionTextTV2.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
            text.getSettings().setTextZoom((int) (size*100));
            text.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(text, text2);
        } else {
            optionTextTV2.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
            if (text2.contains("&lt") || text2.contains("&gt")) {
                text2 = String.valueOf(Html.fromHtml(text2));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                optionTextTV2.setText((Html.fromHtml("<font color='black'>" + text2 + "</font>", Html.FROM_HTML_MODE_COMPACT)));
            } else {
                optionTextTV2.setText((Html.fromHtml("<font color='black'>" + text2 + "</font>")));
            }
            optionTextTV2.setTextSize(TypedValue.COMPLEX_UNIT_SP,(int) (size*13));

        }

        switch (questionType) {
            case "TF":
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
                    if (((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
                        v.setSelected(true);
                break;
            case "MC":
                if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue() != null) {
                    if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().contains(tag)) {
                        v.setSelected(true);
                        selectedValue = ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue();
                    }
                }
                break;
            case "SC":
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
                    if (((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
                        v.setSelected(true);
                break;
            case "FIB":
                break;
        }
        LinearLayoutList.add(v);
        parentList.add(parentLL);
        tvList.add(tv);
        v.setOnClickListener(onClickListener);
        return v;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bookmark:
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).getIs_bookmarked().equals("1")) {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIs_bookmarked("0");
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIs_bookmarked("0");
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIs_bookmarked("0");
                    }
                    imgBookmark.setImageDrawable(activity.getResources().getDrawable(R.mipmap.bookmark_unselected));
                    Toast.makeText(activity, R.string.unboomark, Toast.LENGTH_SHORT).show();
                } else {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIs_bookmarked("1");
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIs_bookmarked("1");
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIs_bookmarked("1");
                    }
//                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIs_bookmarked("1");
                    imgBookmark.setImageDrawable(activity.getResources().getDrawable(R.mipmap.bookmark_selected));
                    Toast.makeText(activity, R.string.boomark, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.checkBox:
                if (((CompoundButton) view).isChecked()) {
                    System.out.println("Checked");
                    guess = "1";
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIsguess(guess);
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsguess(guess);
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsguess(guess);
                    }
                } else {
                    guess = "0";
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIsguess(guess);
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsguess(guess);
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsguess(guess);
                    }
                }
                break;
            case R.id.mark_for_review:
                Toast.makeText(activity, "Marked for review.", Toast.LENGTH_SHORT).show();
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setMarkForReview(true);
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setIssaveMarkForReview(false);
                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setMarkForReview(true);
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIssaveMarkForReview(false);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setMarkForReview(true);
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIssaveMarkForReview(false);
                }
                ((TestBaseActivity) getActivity()).notifynumberApater();
                markForReview.setVisibility(View.GONE);
                unmarkForReview.setVisibility(View.VISIBLE);
                break;
            case R.id.unmark_for_review:
                Toast.makeText(activity, "Unmarked for review.", Toast.LENGTH_SHORT).show();
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setMarkForReview(false);
                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setMarkForReview(false);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setMarkForReview(false);
                }
                ((TestBaseActivity) getActivity()).notifynumberApater();
                markForReview.setVisibility(View.VISIBLE);
                unmarkForReview.setVisibility(View.GONE);
                break;
            case R.id.save_mark_for_review:
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).isanswer()) {
                    Toast.makeText(activity, "Save & Marked for review.", Toast.LENGTH_SHORT).show();
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIssaveMarkForReview(true);
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setMarkForReview(false);
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIssaveMarkForReview(true);
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setMarkForReview(false);
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIssaveMarkForReview(true);
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setMarkForReview(false);
                    }
                    ((TestBaseActivity) getActivity()).notifynumberApater();
                    markForReview.setVisibility(View.VISIBLE);
                    unmarkForReview.setVisibility(View.GONE);
                } else {
                    Toast.makeText(activity, "Please give answer.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.remove_mark_for_review:
                Toast.makeText(activity, "Remove & Marked for review.", Toast.LENGTH_SHORT).show();
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setIssaveMarkForReview(false);
                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIssaveMarkForReview(false);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIssaveMarkForReview(false);
                }
                ((TestBaseActivity) getActivity()).notifynumberApater();
                break;
            case R.id.tv_report_error:
                showPopupErrorTest();
                break;
        }
    }

    private void showPopupErrorTest() {
        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.dialog_report_error, null, false);
        final Dialog quizBasicInfoDialog = new Dialog(activity, R.style.CustomAlertDialog);
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizBasicInfoDialog.setCancelable(true);
        quizBasicInfoDialog.setCanceledOnTouchOutside(false);
        quizBasicInfoDialog.setContentView(v);
        quizBasicInfoDialog.show();
        Button btnCancel, btnSubmit;
        final EditText feedbackET;
        final RadioGroup radioGroup;
        final RadioButton[] radioButton = new RadioButton[1];
        feedbackET = v.findViewById(R.id.feedbackET);
        radioGroup = v.findViewById(R.id.radioError);
        btnSubmit = v.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDataValid = true;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton[0] = (RadioButton) v.findViewById(selectedId);
                if (TextUtils.isEmpty(feedbackET.getText().toString().trim()))
                    isDataValid = Helper.DataNotValid(getActivity(), feedbackET);
                if (isDataValid) {
                    networkcallForSubmitquery(radioButton[0].getText().toString().trim(), feedbackET.getText().toString().trim(), quizBasicInfoDialog);
                }
/*                Toast.makeText(activity,
                        radioButton[0].getText(), Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    public void networkcallForSubmitquery(String error, String feedback, final Dialog quizBasicInfoDialog) {
        if (Helper.isNetworkConnected(getActivity())) {
            showProgress();
            APIInterface Service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
            EncryptionData masterdatadetailencryptionData = new EncryptionData();
            masterdatadetailencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
            masterdatadetailencryptionData.setTitle(tv_uid.getText().toString().trim());
            masterdatadetailencryptionData.setDescription(feedback);
            masterdatadetailencryptionData.setCategory(error);
            masterdatadetailencryptionData.setFile("");
            String doseStr = new Gson().toJson(masterdatadetailencryptionData);
            String doseStrScr = AES.encrypt(doseStr);
            Call<String> response = null;
            response = Service.getresponseofsubmitquery(doseStrScr);
            assert response != null;
            response.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    hideProgress();
                    if (response.body() != null) {
                        JSONObject jsonResponse = null;
                        String jsonString;
                        try {
                            jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                        } catch (Exception e) {
                            jsonString = response.body();
                        }
                        try {
                            jsonResponse = new JSONObject(jsonString);
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time"))) * 1000);
                            if (jsonResponse.optString(Const.STATUS).equals(Const.TRUE)) {
                                quizBasicInfoDialog.dismiss();
                                Toast.makeText(activity, jsonResponse.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            } else {
                                quizBasicInfoDialog.dismiss();
                                RetrofitResponse.GetApiData(activity, jsonResponse.optString(Const.AUTH_CODE), jsonResponse.optString(Const.MESSAGE), false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getActivity(), R.string.something_went_wrong, Toast.LENGTH_SHORT).show();
                    hideProgress();
                    quizBasicInfoDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(getActivity(), R.string.Retry_with_Internet_connection, Toast.LENGTH_LONG).show();
        }
    }

    public void refereshPage() {
        if (questionType.equalsIgnoreCase("MT")) {
            for (int j = 0; j < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted().clear();
                }
            }
            adapterMatchingListDrag.notifyDataSetChanged();
            for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().clear();
            }
            adapterMatchingListNormal.notifyDataSetChanged();
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                for (int j = 0; j < ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).getSelcted().size(); j++) {
                    if (((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).getSelcted().get(j).isSelect()) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).getSelcted().clear();
                    }
                }
                adapterMatchingListDrag.notifyDataSetChanged();
                for (int i = 0; i < ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).getSelcted2().size(); i++) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).getSelcted2().clear();
                }
                adapterMatchingListNormal.notifyDataSetChanged();
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                for (int j = 0; j < ((TestBaseActivity) getActivity()).data.getQuestions().get(position).getSelcted().size(); j++) {
                    if (((TestBaseActivity) getActivity()).data.getQuestions().get(position).getSelcted().get(j).isSelect()) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).getSelcted().clear();
                    }
                }
                adapterMatchingListDrag.notifyDataSetChanged();
                for (int i = 0; i < ((TestBaseActivity) getActivity()).data.getQuestions().get(position).getSelcted2().size(); i++) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).getSelcted2().clear();
                }
                adapterMatchingListNormal.notifyDataSetChanged();
            }
        } else {
            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setIsanswer(false, 0);
            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
            }
            if (((TestBaseActivity) requireActivity()).data.getQuestionsHindi() != null && ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().size() > 0) {
                ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setAnspositions("-1");
                ArrayList<Integer> selectedValue1 = new ArrayList<>();
                ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue1);
                ArrayList answers1 = new ArrayList();
                ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setAnswers(answers1);
            }

            if (((TestBaseActivity) requireActivity()).data.getQuestions() != null && ((TestBaseActivity) requireActivity()).data.getQuestions().size() > 0) {
                ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setAnspositions("-1");
                ArrayList<Integer> selectedValue2 = new ArrayList<>();
                ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue2);
                ArrayList answers2 = new ArrayList();
                ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setAnswers(answers2);
            }


            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setAnspositions("-1");
            ArrayList<Integer> selectedValue1 = new ArrayList<>();
            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setSelectedValue(selectedValue1);
            ArrayList answers = new ArrayList();
            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setAnswers(answers);
            checkBoxGuess.setChecked(false);
            guess = "0";

            if (((TestBaseActivity) requireActivity()).data.getQuestions().get(position).getQuestionType().equalsIgnoreCase("SC")) {
                for (int k = 0; k < LinearLayoutList.size(); k++) {
                    if (LinearLayoutList.get(k).isSelected()) {
                        SectioHashMap sectioHashMap = ((TestBaseActivity) getActivity()).sectioncount.get(((TestBaseActivity) getActivity()).PartId);
                        Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() > 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                        ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                        Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                        Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
                    }
                    LinearLayoutList.get(k).setSelected(false);
                }
            }else {
                boolean clear =false;
                for (int k = 0; k < LinearLayoutList.size(); k++) {
                    if (LinearLayoutList.get(k).isSelected() && !clear)
                    {
                        selectedValue.clear();
                        clear =true;
                        SectioHashMap sectioHashMap = ((TestBaseActivity) getActivity()).sectioncount.get(((TestBaseActivity) getActivity()).PartId);
                        Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() > 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                        ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                        Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                        Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
                    }
                    LinearLayoutList.get(k).setSelected(false);
                }



            }


        }

    }

    @Override
    public void sendOnclickInd(int p) {
        if (adapterMatchingListNormal != null) {
            for (int i = 0; i < ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
                    break;
                }
            }
            adapterMatchingListNormal.notifyDataSetChanged();
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d("TAG", "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
//                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                mDrawable.setLevel(1);
                //  CharSequence t = tvQuestion.getText();
                //  tvQuestion.setText(t);
            }
        }
    }

    private static class WebViewClickListener_option implements View.OnLongClickListener {
        WebView web;
        LinearLayout vgg;

        WebViewClickListener_option(WebView option1_webview, LinearLayout ll_option1) {
            web = option1_webview;
            vgg = ll_option1;
            web.setLongClickable(false);
            web.setHapticFeedbackEnabled(false);
        }

        @Override
        public boolean onLongClick(View v) {
            v.setLongClickable(false);
            v.setHapticFeedbackEnabled(false);
            return true;
        }
    }

    private static class WebViewClickListener implements View.OnLongClickListener {
        WebView web;
        RelativeLayout vgg;

        WebViewClickListener(WebView option1_webview, RelativeLayout ll_option1) {
            web = option1_webview;
            vgg = ll_option1;
            web.setLongClickable(false);
            web.setHapticFeedbackEnabled(false);
        }

        @Override
        public boolean onLongClick(View v) {
            v.setLongClickable(false);
            v.setHapticFeedbackEnabled(false);
            return true;
        }
    }

    private void setHtmlinWebView(WebView webView, String html) {
        try {
            String str = "<div onClick=\"showAndroidToast('Hello Android!')\" >" + html + "</div>\n" +
                    "\n" +
                    "<script type=\"text/javascript\">\n" +
                    "    function showAndroidToast(toast) {\n" +
                    "        Android.showToast(toast);\n" +
                    "    }\n" +
                    "</script>";
            webView.loadDataWithBaseURL("",
                    "<html>" +
                            "<body>" +
                            str +
                            "</body>" +
                            "</html>", "text/html; charset=UTF-8", null, "");
            webView.setBackgroundColor(Color.TRANSPARENT);
            webView.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });
            webView.setLongClickable(false);
            webView.setHapticFeedbackEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Spanned removeImageSpanObjects(String inStr) {
        SpannableStringBuilder spannedStr = (SpannableStringBuilder) Html
                .fromHtml(inStr.trim());
        Object[] spannedObjects = spannedStr.getSpans(0, spannedStr.length(),
                Object.class);
        for (int i = 0; i < spannedObjects.length; i++) {
            if (spannedObjects[i] instanceof ImageSpan) {
                ImageSpan imageSpan = (ImageSpan) spannedObjects[i];
                spannedStr.replace(spannedStr.getSpanStart(imageSpan),
                        spannedStr.getSpanEnd(imageSpan), "");
            }
        }
        return spannedStr;
    }

    public CharSequence stripHtml(String s) {
        return Html.fromHtml(s).toString().replace('\n', (char) 32)
                .replace((char) 160, (char) 32).replace((char) 65532, (char) 32).trim();
    }

    private CharSequence noTrailingwhiteLines(CharSequence text) {
        if (!text.toString().trim().isEmpty()) {
            while (text.charAt(text.length() - 1) == '\n') {
                text = text.subSequence(0, text.length() - 1);
            }
        }
        return text;
    }
}
