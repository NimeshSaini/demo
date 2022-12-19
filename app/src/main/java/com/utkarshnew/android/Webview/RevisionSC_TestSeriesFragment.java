package com.utkarshnew.android.Webview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.bumptech.glide.Glide;
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
import com.utkarshnew.android.testmodule.adapter.AdapterMatchingListDrag;
import com.utkarshnew.android.testmodule.adapter.AdapterMatchingListNormal;
import com.utkarshnew.android.testmodule.interfaces.MachingOnDrag;
import com.utkarshnew.android.testmodule.model.Social;
import com.utkarshnew.android.testmodule.model.mcSelection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.utkarshnew.android.Utils.Helper.*;

public class RevisionSC_TestSeriesFragment extends MainFragment implements View.OnClickListener, Html.ImageGetter, MachingOnDrag {

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
    List<View> LinearLayoutList, parentList, tvList;
    int position;
    String questionType;
    LinearLayout explanationLL;
    int selectedAnswerposition;
    RelativeLayout question_layout1;
    TextView explanationTV;
    ClickableWebView tvQuestion;
    private TextView tv_uid, tvEmail;
    private TextView markForReview, tvQuestionFib, remove_mark_for_review, save_mark_for_review, tvReportError, unmarkForReview;
    private boolean status;
    private boolean status1;
    private ImageView imgBookmark;
    ArrayList<Integer> selectedValue = new ArrayList();
    ArrayList<mcSelection> selected = new ArrayList();
    ArrayList<mcSelection> selected2 = new ArrayList();
    private CheckBox checkBoxGuess;
    private Drawable empty;
    private String isCorrect, guess;

    ArrayList tags = new ArrayList();
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!((RevisionTest) getActivity()).questionBankList.get(position).isIssaveMarkForReview()) {

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
        int j = 0;
        View myView;
        myView = view;
        selectedAnswerposition = (int) view.getTag();
        j = LinearLayoutList.size();

        if (tags != null) tags.clear();
        if (!((RevisionTest) getActivity()).questionBankList.get(position).isanswer() && ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() == 0 || ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() == -1) {
            for (int i = 0; i < LinearLayoutList.size(); i++) {
                if (selectedAnswerposition == i) {
                    tags.add(i, "1");
                    LinearLayout linearLayout = (LinearLayout) ((LinearLayout) LinearLayoutList.get(i)).getChildAt(0);
                    AppCompatTextView appCompatTextView = (AppCompatTextView) linearLayout.getChildAt(2);
                    ((RevisionTest) getActivity()).questionBankList.get(position).setUser_answer(appCompatTextView.getText().toString());
                } else {
                    tags.add("0");
                }
            }
            ((RevisionTest) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
        } else if (((RevisionTest) getActivity()).questionBankList.get(position).isanswer() && ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0 && ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() != -1)
            if (((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() == selectedAnswerposition + 1) {
                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    ((RevisionTest) getActivity()).questionBankList.get(position).setUser_answer("");
                    tags.add(i, "0");
                }
                ((RevisionTest) getActivity()).questionBankList.get(position).setIsanswer(false, 0);
            } else {
                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    if (selectedAnswerposition == i) {
                        tags.add(i, "1");
                        LinearLayout linearLayout = (LinearLayout) ((LinearLayout) LinearLayoutList.get(i)).getChildAt(0);
                        AppCompatTextView appCompatTextView = (AppCompatTextView) linearLayout.getChildAt(2);
                        ((RevisionTest) getActivity()).questionBankList.get(position).setUser_answer(appCompatTextView.getText().toString());
                    } else {
                        tags.add("0");
                    }
                }
                ((RevisionTest) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
            }


        // markForReview.setVisibility(View.VISIBLE);
        explanationLL.setVisibility(View.GONE);
        for (int k = 0; k < j; k++) {
            if (selectedAnswerposition == k) {
                if (LinearLayoutList.get(k).isSelected())
                    LinearLayoutList.get(k).setSelected(false);
                else
                    LinearLayoutList.get(k).setSelected(true);
            } else LinearLayoutList.get(k).setSelected(false);
        }

    }


    private void MC_Clicked(View view) {

        View myView;
        myView = view;
        selectedAnswerposition = (int) view.getTag();
        //j = LinearLayoutList.size();

        String answerPosition = "";
        boolean isSelected = false;
        String[] answers = ((RevisionTest) activity).questionBankList.get(position).getAnswer().split(",");
        //if (((RevisionTest) activity).questionBankList.get(position).getCount() < answers.length) {

        if (LinearLayoutList.get(selectedAnswerposition).isSelected()) {

            for (int i = 0; i < selectedValue.size(); i++) {
                if (selectedAnswerposition == selectedValue.get(i)) {
                    LinearLayoutList.get(selectedAnswerposition).setSelected(false);
                    selectedValue.remove(i);
                    break;
                }
            }

            ((RevisionTest) activity).questionBankList.get(position).setSelectedValue(selectedValue);


        } else {
            // if (((RevisionTest) activity).questionBankList.get(position).getCount() < answers.length) {
            LinearLayoutList.get(selectedAnswerposition).setSelected(true);
            if (!selectedValue.contains(selectedAnswerposition))
                selectedValue.add(selectedAnswerposition);

            ((RevisionTest) activity).questionBankList.get(position).setSelectedValue(selectedValue);

        }


        for (int j = 0; j < LinearLayoutList.size(); j++) {
            if (LinearLayoutList.get(j).isSelected()) {
                isSelected = true;

            }
        }

        if (isSelected) {
            for (int i = 0; i < ((RevisionTest) activity).questionBankList.get(position).getSelectedValue().size(); i++) {
                answerPosition = answerPosition + ((RevisionTest) activity).questionBankList.get(position).getSelectedValue().get(i) + ",";
            }
            tags.clear();
            for (int i = 0; i < LinearLayoutList.size(); i++) {
                if (answerPosition.contains(String.valueOf(i)))
                    tags.add("1");
                else tags.add("0");
            }

            ((RevisionTest) activity).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, tags);
        } else {

            ((RevisionTest) activity).questionBankList.get(position).setIsanswer(false, 0);
        }

        Log.e("IS_SELECTED", String.valueOf(isSelected));
    }


    private NetworkCall networkCall;

    public static RevisionSC_TestSeriesFragment newInstance(int position, String questionType) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("questionType", questionType);
        RevisionSC_TestSeriesFragment fragment = new RevisionSC_TestSeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_series_revision, null);
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
            questionType = bundle.getString("questionType", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        NetworkAPICall(API.API_GET_LANDING_PAGE_DATA_EXAM, true);
    }

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

        switch (questionType) {
            default:
                mcqoptionsLL.setVisibility(View.VISIBLE);
                LLmatchinquestion.setVisibility(View.GONE);
                String htmlAsString1 = ((RevisionTest) getActivity()).questionBankList.get(position).getQuestion();// used by WebView

                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, question_layout1));
                showWebData(getActivity(), getHtmlUpdatedData("Q-" + (position + 1) + ". " + htmlAsString1), tvQuestion);
                addQuestionOption();
        }
        if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getParagraphText())) {
            tvQuestionFib.setVisibility(View.GONE);
        } else {
            tvQuestionFib.setVisibility(View.VISIBLE);
            tvQuestionFib.setText(Html.fromHtml(((RevisionTest) getActivity()).questionBankList.get(position).getParagraphText()));
        }

        tv_uid.setText("Utkarsh QUID " + ((RevisionTest) getActivity()).questionBankList.get(position).getId());
        if (((RevisionTest) getActivity()).questionBankList.get(position).isMarkForReview()) {
            markForReview.setVisibility(View.GONE);
            unmarkForReview.setVisibility(View.VISIBLE);
        } else {
            markForReview.setVisibility(View.VISIBLE);
            unmarkForReview.setVisibility(View.GONE);
        }


        checkBoxGuess.setOnClickListener(this);
        markForReview.setOnClickListener(this);
        remove_mark_for_review.setOnClickListener(this);
        save_mark_for_review.setOnClickListener(this);
        unmarkForReview.setOnClickListener(this);
        tvReportError.setOnClickListener(this);
        imgBookmark.setVisibility(View.GONE);

       /* if (((RevisionTest) getActivity()).questionBankList.get(position).getIs_bookmarked().equals("1")) {
            imgBookmark.setImageResource(R.mipmap.bookmark_selected);
        } else {
            imgBookmark.setImageResource(R.mipmap.bookmark_unselected);
        }*/
        if (((RevisionTest) getActivity()).questionBankList.get(position).getIsguess().equals("1"))
            checkBoxGuess.setChecked(true);
        else
            checkBoxGuess.setChecked(false);
        // tvEmail.setText(((RevisionTest) getActivity()).data.getUserDetails().getEmail());
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

    private void
    addQuestionOption() {

        for (int j = 1; j <= 10; j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption1())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("1", ((RevisionTest) getActivity()).questionBankList.get(position).getOption1(),
                            "11", false, j - 1));
                    // tags.add("0");
                    break;
                case 2:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption2())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("2", ((RevisionTest) getActivity()).questionBankList.get(position).getOption2(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 3:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption3())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("3", ((RevisionTest) getActivity()).questionBankList.get(position).getOption3(),
                            "11", false, j - 1));
                    //    tags.add("0");
                    break;
                case 4:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption4())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("4", ((RevisionTest) getActivity()).questionBankList.get(position).getOption4(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 5:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption5())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("5", ((RevisionTest) getActivity()).questionBankList.get(position).getOption5(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 6:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption6())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("6", ((RevisionTest) getActivity()).questionBankList.get(position).getOption6(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 7:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption7())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("7", ((RevisionTest) getActivity()).questionBankList.get(position).getOption7(),
                            "11", false, j - 1));
                    //  tags.add("0");
                    break;
                case 8:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption8())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("8", ((RevisionTest) getActivity()).questionBankList.get(position).getOption8(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 9:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption9())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("9", ((RevisionTest) getActivity()).questionBankList.get(position).getOption9(),
                            "11", false, j - 1));
                    //  tags.add("0");
                    break;
                case 10:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption10())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("10", ((RevisionTest) getActivity()).questionBankList.get(position).getOption10(),
                            "11", false, j - 1));
                    // tags.add("0");
                    break;

            }
        }

        //  ((RevisionTest) getActivity()).questionBankList.get(position).setAnswers(tags);

        status = false;
    }


    private void addMachingQuestionOption() {
        if (((RevisionTest) getActivity()).items1 != null)
            ((RevisionTest) getActivity()).items1.clear();
        else ((RevisionTest) getActivity()).items1 = new ArrayList<>();
        if (((RevisionTest) getActivity()).items2 != null)
            ((RevisionTest) getActivity()).items2.clear();
        else ((RevisionTest) getActivity()).items2 = new ArrayList<>();

        for (int j = 1; j <= 10; j++) {
            if (status1)
                break;
            switch (j) {

                case 1:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption1())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("1", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("1", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).get(0), j - 1));
                    }

                    break;
                case 2:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption2())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("2", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("2", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption3())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("3", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("3", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 4:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption4())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("4", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("4", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 5:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption5())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("5", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("5", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 6:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption6())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("6", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("6", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 7:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption7())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("7", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("7", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 8:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption8())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("8", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("8", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 9:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption9())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("9", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("9", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 10:
                    if (TextUtils.isEmpty(((RevisionTest) getActivity()).questionBankList.get(position).getOption10())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).size() == 2) {
                        ((RevisionTest) getActivity()).items1.add(new Social("10", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).get(1), j - 1));
                        ((RevisionTest) getActivity()).items2.add(new Social("10", Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;

            }
        }

        if (!status1) {
            int[] androidColors = getResources().getIntArray(R.array.mdcolor_random);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

            //set data and list2 adapter
            adapterMatchingListDrag = new AdapterMatchingListDrag(activity, ((RevisionTest) getActivity()).items1, position);
            rvmatchinquestion2.setAdapter(adapterMatchingListDrag);

            //set data and list1 adapter
            adapterMatchingListNormal = new AdapterMatchingListNormal(activity, ((RevisionTest) getActivity()).items2, androidColors, position);
            rvmatchinquestion1.setAdapter(adapterMatchingListNormal);

            // on item list clicked
            adapterMatchingListDrag.setOnItemClickListener(new AdapterMatchingListDrag.OnItemClickListener() {
                @Override
                public void onItemClick(View view, Social obj, int p, int bg, int circle, boolean select) {
                    boolean isSelected = false;
                    //    Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    if (select) {

                        selected.add(new mcSelection(p, bg, circle, select));

                        ((RevisionTest) getActivity()).questionBankList.get(position).setSelcted(selected);

                        ((RevisionTest) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                    } else {
                        for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                            if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() == p) {
                                ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().remove(i);
                                break;
                            }
                        }
                    }

                    for (int j = 0; j < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
                        if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
                            isSelected = true;

                        }
                    }

                    if (isSelected) {

                        tags.clear();
                        for (int k = 0; k < ((RevisionTest) getActivity()).items1.size(); k++) {

                            for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {

                                if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).isSelect()) {

                                    for (int j = 0; j < ((RevisionTest) getActivity()).SAMPLE_CIRCLE.length; j++) {
                                        if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).getCirclecolor_code() == ((RevisionTest) getActivity()).SAMPLE_CIRCLE[j]) {
                                            tags.add(((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() + 1);
                                            break;
                                        }
                                    }

                                } else {
                                    tags.add(k, 0);
                                    break;
                                }
                            }

                        }
                       /* for (int j = 0; j < ((RevisionTest) getActivity()).items1.size(); j++) {

                        }*/

                        ((RevisionTest) activity).questionBankList.get(position).setIsanswer(true, 1, tags);
                    } else {
                        ((RevisionTest) activity).questionBankList.get(position).setIsanswer(false, 0);
                    }
                }
            });
            adapterMatchingListDrag.setMachingOnDrag(new AdapterMatchingListDrag.MachingOnDrag() {
                @Override
                public void sendOnclickInd(int p) {
                    if (adapterMatchingListNormal != null) {
                        for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                            if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                                ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
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
                    ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().remove(p);
                    if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size() == 0)
                        RevisionTest.nestselected = false;
                    RevisionTest.sameselected = false;
                    adapterMatchingListDrag.notifyDataSetChanged();

                }
            });
            adapterMatchingListNormal.setOnItemClickListener(new AdapterMatchingListNormal.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int p, Social obj, int bg, int circle, boolean select) {
                    // Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    boolean isSelected = false;
                    /*if (adapterMatchingListNormal!=null){
                        for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                            if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() == p) {

                                ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().remove(i);
                                break;
                            }
                        }

                        adapterMatchingListDrag.notifyDataSetChanged();
                    }*/
                    if (select) {
                        selected2.add(new mcSelection(p, bg, circle, select));
                        ((RevisionTest) getActivity()).questionBankList.get(position).setSelcted2(selected2);
                    } else {

                        for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                            if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                                ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
                                break;
                            }

                        }
                    }


//                    for (int j = 0; j < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
//                        if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
//                            isSelected = true;
//
//                        }
//                    }
////
//                    if (isSelected) {
//
//                        tags.clear();
//                        for (int k = 0; k < ((RevisionTest) getActivity()).items1.size(); k++) {
//
//                            for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
//
//                                if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).isSelect()) {
//
//                                    for (int j = 0; j < ((RevisionTest) getActivity()).SAMPLE_CIRCLE.length; j++) {
//                                        if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).getCirclecolor_code() == ((RevisionTest) getActivity()).SAMPLE_CIRCLE[j]) {
//                                            tags.add(((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() + 1);
//                                            break;
//                                        }
//                                    }
//
//                                } else {
//                                    tags.add(k,0);
//                                    break;
//                                }
//                            }
//
//                        }
//                       /* for (int j = 0; j < ((RevisionTest) getActivity()).items1.size(); j++) {
//
//                        }*/
//
//                        ((RevisionTest) activity).questionBankList.get(position).setIsanswer(true,1,tags);
//                    }else {
//                        ((RevisionTest) activity).questionBankList.get(position).setIsanswer(false,0);
//                    }
//                            selected2.add(new mcSelection(p,bg,circle,select));
//                            ((RevisionTest) getActivity()).questionBankList.get(position).setSelcted2(selected2);
//

                    //adapterMatchingListNormal.notifyDataSetChanged();

                }
            });

   /* adapterMatchingListDrag.setDragListener(new AdapterMatchingListDrag.OnStartDragListener() {
        @Override
        public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
            mItemTouchHelper.startDrag(viewHolder);
        }
    });

    ItemTouchHelper.Callback callback = new DragItemTouchHelper(adapterMatchingListDrag,adapterMatchingListDrag);
    mItemTouchHelper = new ItemTouchHelper(callback);
    mItemTouchHelper.attachToRecyclerView(rvmatchinquestion2);*/
        }
        status1 = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public LinearLayout initMCQOptionView(String text1, String text2, final String pollValue, boolean pollVisiblity, int tag) {
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_test_view_revision, null);
        TextView tv = (TextView) v.findViewById(R.id.optionIconTV);
        TextView text = (TextView) v.findViewById(R.id.optionTextTV);
        RadioButton radioRB = (RadioButton) v.findViewById(R.id.radioRB);
        ImageView imgOption = v.findViewById(R.id.imgOption);

        parentLL = v.findViewById(R.id.viewLL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);

        if (pollVisiblity) {
            radioRB.setVisibility(View.GONE);
        }
        v.setLayoutParams(lp);
        tv.setText(text1);
        tv.setGravity(Gravity.CENTER);
        v.setTag(tag);

        if (text2.contains("<img src=")) {
            imgOption.setVisibility(View.VISIBLE);
            String imgRegex = "(?i)<img[^>]+?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
            Pattern p = Pattern.compile(imgRegex);
            Matcher m = p.matcher(text2);

            while (m.find()) {
                String imgSrc = m.group(1);
                Glide.with(activity).load(imgSrc).into(imgOption);
            }

            String optionvalue = (String) stripHtml(removeImageSpanObjects(text2).toString());


        } else {
            text.setVisibility(View.VISIBLE);
            imgOption.setVisibility(View.GONE);
            text2.trim();
            text.setText(text2.replaceAll("\\<.*?\\>", ""));
        }

        switch (questionType) {

            case "TF":
                if (((RevisionTest) getActivity()).questionBankList.get(position).isanswer() && ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
                    if (((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
                        v.setSelected(true);
                break;
            case "MC":
                // if (((RevisionTest) getActivity()).questionBankList.get(position).isanswer() && ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0) {
                  /*  ((RevisionTest) getActivity()).items1 = Arrays.asList(((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttions().split("\\s*,\\s*"));
                    for (int i = 0; i < ((RevisionTest) getActivity()).items1.size(); i++) {
                        if (!((RevisionTest) getActivity()).items1.get(i).equalsIgnoreCase(""))
                        if (Integer.parseInt(((RevisionTest) getActivity()).items1.get(i)) - 1 == tag) {
                            v.setSelected(true);
                        }
                    }*/

                if (((RevisionTest) activity).questionBankList.get(position).getSelectedValue() != null) {
                    if (((RevisionTest) activity).questionBankList.get(position).getSelectedValue().contains(tag)) {
                        v.setSelected(true);
                        selectedValue = ((RevisionTest) activity).questionBankList.get(position).getSelectedValue();
                    }
                }

                break;
            case "SC":
                if (((RevisionTest) getActivity()).questionBankList.get(position).isanswer() && ((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
                    if (((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
                        v.setSelected(true);

                break;
            case "FIB":
                break;

        }

//            if (((RevisionTest) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
//                v.setSelected(true);

        LinearLayoutList.add(v);
        parentList.add(parentLL);
        tvList.add(tv);
        v.setOnClickListener(onClickListener);


        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.checkBox:
                if (((CompoundButton) view).isChecked()) {
                    System.out.println("Checked");
                    guess = "1";
                    ((RevisionTest) getActivity()).questionBankList.get(position).setIsguess(guess);
                } else {
                    guess = "0";
                    ((RevisionTest) getActivity()).questionBankList.get(position).setIsguess(guess);
                }
                break;
            case R.id.mark_for_review:
                Toast.makeText(activity, "Marked for review.", Toast.LENGTH_SHORT).show();
                ((RevisionTest) getActivity()).questionBankList.get(position).setMarkForReview(true);
                ((RevisionTest) getActivity()).questionBankList.get(position).setIssaveMarkForReview(false);
                ((RevisionTest) getActivity()).notifynumberApater();
                markForReview.setVisibility(View.GONE);
                unmarkForReview.setVisibility(View.VISIBLE);
                //  save_mark_for_review.setVisibility(View.VISIBLE);
                // remove_mark_for_review.setVisibility(View.GONE);
                break;
            case R.id.unmark_for_review:
                Toast.makeText(activity, "Unmarked for review.", Toast.LENGTH_SHORT).show();
                ((RevisionTest) getActivity()).questionBankList.get(position).setMarkForReview(false);
                ((RevisionTest) getActivity()).notifynumberApater();
                markForReview.setVisibility(View.VISIBLE);
                unmarkForReview.setVisibility(View.GONE);
                break;
            case R.id.save_mark_for_review:
                if (((RevisionTest) getActivity()).questionBankList.get(position).isanswer()) {
                    Toast.makeText(activity, "Save & Marked for review.", Toast.LENGTH_SHORT).show();
                    ((RevisionTest) getActivity()).questionBankList.get(position).setIssaveMarkForReview(true);
                    ((RevisionTest) getActivity()).questionBankList.get(position).setMarkForReview(false);
                    ((RevisionTest) getActivity()).notifynumberApater();
                    //  remove_mark_for_review.setVisibility(View.VISIBLE);
                    //    save_mark_for_review.setVisibility(View.GONE);
                    markForReview.setVisibility(View.VISIBLE);
                    unmarkForReview.setVisibility(View.GONE);
                } else {
                    Toast.makeText(activity, "Please give answer.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.remove_mark_for_review:
                Toast.makeText(activity, "Remove & Marked for review.", Toast.LENGTH_SHORT).show();
                ((RevisionTest) getActivity()).questionBankList.get(position).setIssaveMarkForReview(false);
                ((RevisionTest) getActivity()).notifynumberApater();
                //   save_mark_for_review.setVisibility(View.VISIBLE);
                //  remove_mark_for_review.setVisibility(View.GONE);

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
                            MakeMyExam.setTime_server((Long.parseLong(jsonResponse.optString("time")))*1000);

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
            for (int j = 0; j < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
                if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
                    ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted().clear();

                }
            }
            adapterMatchingListDrag.notifyDataSetChanged();
            for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {

                ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().clear();
            }
            adapterMatchingListNormal.notifyDataSetChanged();
        } else {
            ((RevisionTest) getActivity()).questionBankList.get(position).setIsanswer(false, 0);
            checkBoxGuess.setChecked(false);
            guess = "0";
            for (int k = 0; k < LinearLayoutList.size(); k++) {
                LinearLayoutList.get(k).setSelected(false);
            }
        }
    }

/*    @Override
    public void sendOnclickInd(int fromposition, int toposition) {
*//*

        for (int i = 0; i <((RevisionTest) getActivity()).items1.size() ; i++) {
                 if (i==toposition){
                     ((RevisionTest) getActivity()).items1.get(i).setSelcted(((RevisionTest) getActivity()).items1.get(i).getSelcted()+","+i);
                 }
        }
        adapterMatchingListNormal.notifyDataSetChanged();

        for (int i = 0; i <((RevisionTest) getActivity()).items2.size() ; i++) {
            if (i==toposition){
                ((RevisionTest) getActivity()).items2.get(i).setSelcted(((RevisionTest) getActivity()).items2.get(i).getSelcted()+","+i);
            }
        }
        adapterMatchingListDrag.notifyDataSetChanged();
*//*

    }*/

    @Override
    public void sendOnclickInd(int p) {
        if (adapterMatchingListNormal != null) {
            for (int i = 0; i < ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                if (((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                    ((RevisionTest) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
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

}
