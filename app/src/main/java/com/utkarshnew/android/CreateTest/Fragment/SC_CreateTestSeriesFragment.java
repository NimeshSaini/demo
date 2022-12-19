package com.utkarshnew.android.CreateTest.Fragment;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
import com.bumptech.glide.Glide;
import com.utkarshnew.android.CreateTest.Activity.TestCreateActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Progress;
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
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.kexanie.library.MathView;
import retrofit2.Call;

public class SC_CreateTestSeriesFragment extends MainFragment implements View.OnClickListener, Html.ImageGetter, MachingOnDrag {

    Progress mProgress;
    LinearLayout mcqoptionsLL,LLmatchinquestion;
    RecyclerView rvmatchinquestion1,rvmatchinquestion2;
    View parent_view;
    int count = 0;
    private AdapterMatchingListDrag adapterMatchingListDrag;
    private AdapterMatchingListNormal adapterMatchingListNormal;
    private ItemTouchHelper mItemTouchHelper;
    LinearLayout parentLL;
    NestedScrollView nestedSV;
    List<View> LinearLayoutList, parentList;
    List<TextView> tvList;
    List<MathView> tvQuesList;
    int position;
    String questionType;
    int selectedAnswerposition;
    RelativeLayout question_layout1;
    ClickableWebView tvQuestion,tvQuestionFib;
    //    private TextView tvQuestionFib;
    private ImageView imgBookmark;
    private boolean status;
    private boolean status1;
    ArrayList<Integer> selectedValue=new ArrayList();
    ArrayList<mcSelection> selected=new ArrayList();
    ArrayList<mcSelection> selected2=new ArrayList();
    private Drawable empty;
    private String isCorrect, guess;

    ArrayList tags=new ArrayList();
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!((TestCreateActivity) getActivity()).questionBankList.get(position).isIssaveMarkForReview()) {

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
            }else {
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

        if (tags!=null)tags.clear();
        if (!((TestCreateActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() == 0 || ((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() == -1) {
            for (int i = 0; i < LinearLayoutList.size(); i++) {
                if (selectedAnswerposition == i) {
                    tags.add(i, "1");
                }else {
                    tags.add("0");
                }
            }
            if (((TestCreateActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
            } else if (((TestCreateActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, tags);
                ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
            }


            ((TestCreateActivity) getActivity()).questionBankList.get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
            ((TestCreateActivity) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1,tags );
        } else if (((TestCreateActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0 && ((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != -1)
            if (((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() == selectedAnswerposition + 1) {
                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    tags.add(i, "0");
                }

                if (((TestCreateActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
                    ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setUser_answer("");
                } else if (((TestCreateActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
                    ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setUser_answer("");
                }


                ((TestCreateActivity) getActivity()).questionBankList.get(position).setUser_answer("");
                ((TestCreateActivity) getActivity()).questionBankList.get(position).setIsanswer(false, 0);
            } else {
                for (int i = 0; i < LinearLayoutList.size(); i++) {
                    if (selectedAnswerposition == i) {
                        tags.add(i, "1");
                    }else {
                        tags.add("0");
                    }
                }

                if (((TestCreateActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1,tags);
                    ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
                } else if (((TestCreateActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1,tags);
                    ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
                }



                ((TestCreateActivity) getActivity()).questionBankList.get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
                ((TestCreateActivity) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1,tags);
            }


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
        String[] answers = ((TestCreateActivity) activity).questionBankList.get(position).getAnswer().split(",");
        //if (((TestCreateActivity) activity).questionBankList.get(position).getCount() < answers.length) {

        if (LinearLayoutList.get(selectedAnswerposition).isSelected()) {
            for(int i=0;i<selectedValue.size();i++) {
                if(selectedAnswerposition==selectedValue.get(i)) {
                    LinearLayoutList.get(selectedAnswerposition).setSelected(false);
                    selectedValue.remove(i);
                    break;
                }
            }
            ((TestCreateActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);

        } else {
            // if (((TestCreateActivity) activity).questionBankList.get(position).getCount() < answers.length) {
            LinearLayoutList.get(selectedAnswerposition).setSelected(true);
            if (!selectedValue.contains(selectedAnswerposition))
                selectedValue.add(selectedAnswerposition);

            ((TestCreateActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);
        }


        for (int j = 0; j < LinearLayoutList.size(); j++) {
            if (LinearLayoutList.get(j).isSelected()) {
                isSelected = true;
            }
        }

        if (isSelected) {
            for (int i = 0; i < ((TestCreateActivity) activity).questionBankList.get(position).getSelectedValue().size(); i++) {
                answerPosition = answerPosition + ((TestCreateActivity) activity).questionBankList.get(position).getSelectedValue().get(i) + ",";
            }
            tags.clear();
            for (int i = 0; i < LinearLayoutList.size(); i++) {
                if (answerPosition.contains(String.valueOf(i))) {
                    tags.add("1");
                }else {
                    tags.add("0");
                }
            }
            ((TestCreateActivity) getActivity()).questionBankList.get(position).setUser_answer(String.valueOf(selectedAnswerposition + 1));
            ((TestCreateActivity) activity).questionBankList.get(position).setIsanswer(true,selectedAnswerposition + 1, answerPosition,tags);
        }else {
            ((TestCreateActivity) getActivity()).questionBankList.get(position).setUser_answer("");
            ((TestCreateActivity) activity).questionBankList.get(position).setIsanswer(false,0);
        }

        Log.e("IS_SELECTED", String.valueOf(isSelected));
    }



    private NetworkCall networkCall;

    public static SC_CreateTestSeriesFragment newInstance(int position, String questionType) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("questionType", questionType);
        SC_CreateTestSeriesFragment fragment = new SC_CreateTestSeriesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_series_create, null);
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
        tvQuestionFib = view.findViewById(R.id.tv_question_fib);
        nestedSV = view.findViewById(R.id.nestedSV);
        parent_view = view.findViewById(android.R.id.content);
        imgBookmark = view.findViewById(R.id.img_bookmark);


        LinearLayoutList = new ArrayList<>();
        parentList = new ArrayList<>();
        tvList = new ArrayList<>();
        tvQuesList = new ArrayList<>();

        switch (questionType){
            case "MT":
                mcqoptionsLL.setVisibility(View.GONE);
                LLmatchinquestion.setVisibility(View.VISIBLE);

                String htmlAsString = ((TestCreateActivity) getActivity()).questionBankList.get(position).getQuestion();// used by WebView

                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                tvQuestion.setLongClickable(false);
                //tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString, "text/html", "UTF-8");
                //showWebData(getActivity(), getHtmlUpdatedData("Q-"+(position+1)+". "+htmlAsString), tvQuestion);
                Helper.TestWebHTMLLoad(tvQuestion, "Q-"+(position+1)+". "+htmlAsString);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, question_layout1));

                addMachingQuestionOption();


                break;
            case "FIB":
                break;

            default:
                mcqoptionsLL.setVisibility(View.VISIBLE);
                LLmatchinquestion.setVisibility(View.GONE);
                String htmlAsString1 = ((TestCreateActivity) getActivity()).questionBankList.get(position).getQuestion();// used by WebView

                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, question_layout1));
                //tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString1, "text/html", "UTF-8");
                //showWebData(getActivity(), getHtmlUpdatedData("Q-"+(position+1)+". "+htmlAsString1), tvQuestion);
                Helper.TestWebHTMLLoad(tvQuestion, "Q-"+(position+1)+". "+htmlAsString1);
                addQuestionOption();
        }
//        if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getParagraphText())){
//            tvQuestionFib.setVisibility(View.GONE);
//        }else {
//            tvQuestionFib.setVisibility(View.VISIBLE);
//            tvQuestionFib.setText(Html.fromHtml(((TestCreateActivity) getActivity()).questionBankList.get(position).getParagraphText()));
//        }

        if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getParagraphText())){
            tvQuestionFib.setVisibility(View.GONE);
        }else {
            tvQuestionFib.setVisibility(View.VISIBLE);
            Helper.TestWebHTMLLoad(tvQuestionFib, ((TestCreateActivity) getActivity()).questionBankList.get(position).getParagraphText());

//            tvQuestionFib.setText(Html.fromHtml(((TestCreateActivity) getActivity()).questionBankList.get(position).getParagraphText()));
        }

        imgBookmark.setOnClickListener(this);

        if (((TestCreateActivity) getActivity()).questionBankList.get(position).getIs_bookmarked().equals("1")) {
            imgBookmark.setImageResource(R.mipmap.bookmark_selected);
        } else {
            imgBookmark.setImageResource(R.mipmap.bookmark_unselected);
        }
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

    private void addQuestionOption() {

        for (int j = 1; j <= 10; j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption1())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("1", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption1(),
                            "11", false, j - 1));
                    // tags.add("0");
                    break;
                case 2:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption2())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("2", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption2(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 3:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption3())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("3", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption3(),
                            "11", false, j - 1));
                    //    tags.add("0");
                    break;
                case 4:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption4())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("4", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption4(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 5:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption5())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("5", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption5(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 6:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption6())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("6", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption6(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 7:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption7())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("7", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption7(),
                            "11", false, j - 1));
                    //  tags.add("0");
                    break;
                case 8:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption8())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("8", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption8(),
                            "11", false, j - 1));
                    //   tags.add("0");
                    break;
                case 9:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption9())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("9", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption9(),
                            "11", false, j - 1));
                    //  tags.add("0");
                    break;
                case 10:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption10())) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("10", ((TestCreateActivity) getActivity()).questionBankList.get(position).getOption10(),
                            "11", false, j - 1));
                    // tags.add("0");
                    break;

            }
        }

        //  ((TestCreateActivity) getActivity()).questionBankList.get(position).setAnswers(tags);

        status = false;
    }


    private void addMachingQuestionOption() {
        if (((TestCreateActivity) getActivity()).items1!=null)
            ((TestCreateActivity) getActivity()).items1.clear();
        else ((TestCreateActivity) getActivity()).items1=new ArrayList<>();
        if (((TestCreateActivity) getActivity()).items2!=null)
            ((TestCreateActivity) getActivity()).items2.clear();
        else ((TestCreateActivity) getActivity()).items2=new ArrayList<>();

        for (int j = 1; j <= 10; j++) {
            if (status1)
                break;
            switch (j) {

                case 1:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption1())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("1", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("1", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption1().split("\\s*##\\s*")).get(0), j - 1));
                    }

                    break;
                case 2:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption2())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("2", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("2", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption2().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 3:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption3())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("3", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("3", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption3().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 4:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption4())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("4", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("4", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption4().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 5:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption5())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("5", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("5", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption5().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 6:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption6())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("6", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("6", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption6().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 7:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption7())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("7", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("7", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption7().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 8:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption8())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("8", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("8", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption8().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 9:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption9())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("9", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("9", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption9().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;
                case 10:
                    if (TextUtils.isEmpty(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption10())) {
                        status1 = true;
                        break;
                    }
                    if (Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).size()==2) {
                        ((TestCreateActivity) getActivity()).items1.add(new Social("10", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).get(1), j - 1));
                        ((TestCreateActivity) getActivity()).items2.add(new Social("10", Arrays.asList(((TestCreateActivity) getActivity()).questionBankList.get(position).getOption10().split("\\s*##\\s*")).get(0), j - 1));
                    }
                    break;

            }
        }

        if (!status1) {
            int[] androidColors = getResources().getIntArray(R.array.mdcolor_random);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

            //set data and list2 adapter
            adapterMatchingListDrag = new AdapterMatchingListDrag(activity, ((TestCreateActivity) getActivity()).items1,position);
            rvmatchinquestion2.setAdapter(adapterMatchingListDrag);

            //set data and list1 adapter
            adapterMatchingListNormal = new AdapterMatchingListNormal(activity, ((TestCreateActivity) getActivity()).items2,androidColors,position);
            rvmatchinquestion1.setAdapter(adapterMatchingListNormal);

            // on item list clicked
            adapterMatchingListDrag.setOnItemClickListener(new AdapterMatchingListDrag.OnItemClickListener() {
                @Override
                public void onItemClick(View view,Social obj, int p,int bg, int circle,boolean select) {
                    boolean isSelected = false;
                    //    Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    if (select){

                        selected.add(new mcSelection(p,bg,circle,select));

                        ((TestCreateActivity) getActivity()).questionBankList.get(position).setSelcted(selected);

                        ((TestCreateActivity) getActivity()).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1,tags);
                    }else {
                        for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                            if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() == p) {
                                ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().remove(i);
                                break;
                            }
                        }
                    }

                    for (int j = 0; j < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
                        if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
                            isSelected = true;

                        }
                    }

                    if (isSelected) {

                        tags.clear();
                        for (int k = 0; k < ((TestCreateActivity) getActivity()).items1.size(); k++) {

                            for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {

                                if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).isSelect()) {

                                    for (int j = 0; j < ((TestCreateActivity) getActivity()).SAMPLE_CIRCLE.length; j++) {
                                        if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getCirclecolor_code() == ((TestCreateActivity) getActivity()).SAMPLE_CIRCLE[j]) {
                                            tags.add(((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() + 1);
                                            break;
                                        }
                                    }

                                } else {
                                    tags.add(k,0);
                                    break;
                                }
                            }

                        }
                       /* for (int j = 0; j < ((TestCreateActivity) getActivity()).items1.size(); j++) {

                        }*/

                        ((TestCreateActivity) activity).questionBankList.get(position).setIsanswer(true,1,tags);
                    }else {
                        ((TestCreateActivity) activity).questionBankList.get(position).setIsanswer(false,0);
                    }
                }
            });
            adapterMatchingListDrag.setMachingOnDrag(new AdapterMatchingListDrag.MachingOnDrag() {
                @Override
                public void sendOnclickInd(int p) {
                    if (adapterMatchingListNormal!=null){
                        for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                            if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                                ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
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
                    ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().remove(p);
                    if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().size()==0)
                        TestCreateActivity.nestselected=false;
                    TestCreateActivity.sameselected=false;
                    adapterMatchingListDrag.notifyDataSetChanged();

                }
            });
            adapterMatchingListNormal.setOnItemClickListener(new AdapterMatchingListNormal.OnItemClickListener() {
                @Override
                public void onItemClick(View view,int p, Social obj, int bg, int circle,boolean select) {
                    // Snackbar.make(parent_view, "Item " + obj.name + " clicked", Snackbar.LENGTH_SHORT).show();
                    boolean isSelected = false;
                    /*if (adapterMatchingListNormal!=null){
                        for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().size(); i++) {
                            if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(i).getPosition() == p) {

                                ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().remove(i);
                                break;
                            }
                        }

                        adapterMatchingListDrag.notifyDataSetChanged();
                    }*/
                    if (select){
                        selected2.add(new mcSelection(p,bg,circle,select));
                        ((TestCreateActivity) getActivity()).questionBankList.get(position).setSelcted2(selected2);
                    }else {

                        for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                            if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                                ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
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

    public LinearLayout initMCQOptionView(String text1, String text2, final String pollValue, boolean pollVisiblity, int tag) {
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_test_view, null);
        TextView tv = (TextView) v.findViewById(R.id.optionIconTV);
        TextView optionTextTV2 = (TextView) v.findViewById(R.id.optionTextTV2);
        MathView text = (MathView) v.findViewById(R.id.optionTextTV);
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


        if(text2.contains("<img src=") || text2.contains("math-tex") || text2.contains("https://") || text2.contains("http://"))
        {
            optionTextTV2.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
            text.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(text, text2);
        }
        else
        {
            optionTextTV2.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);

            if(text2.contains("&lt") || text2.contains("&gt"))
            {
                text2= String.valueOf(Html.fromHtml(text2));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                optionTextTV2.setText((Html.fromHtml( "<font color='black'>"+text2 +"</font>" , Html.FROM_HTML_MODE_COMPACT)));
            } else {
                optionTextTV2.setText((Html.fromHtml("<font color='black'>"+text2+"</font>")));
            }
        }



        switch (questionType){

            case "TF":
                if (((TestCreateActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
                    if (((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
                        v.setSelected(true);
                break;
            case "MC":


                if (((TestCreateActivity) activity).questionBankList.get(position).getSelectedValue() != null) {
                    if (((TestCreateActivity) activity).questionBankList.get(position).getSelectedValue().contains(tag)) {
                        v.setSelected(true);
                        selectedValue = ((TestCreateActivity) activity).questionBankList.get(position).getSelectedValue();
                    }
                }

                break;
            case "SC":
                if (((TestCreateActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
                    if (((TestCreateActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
                        v.setSelected(true);

                break;
            case "FIB":
                break;

        }

        LinearLayoutList.add(v);
        parentList.add(parentLL);
        tvList.add(tv);
        tvQuesList.add(text);
        v.setOnClickListener(onClickListener);


        return v;
    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bookmark:
                if (((TestCreateActivity) getActivity()).questionBankList.get(position).getIs_bookmarked().equals("1")) {
                    ((TestCreateActivity) getActivity()).questionBankList.get(position).setIs_bookmarked("0");
                    imgBookmark.setImageDrawable(activity.getResources().getDrawable(R.mipmap.bookmark_unselected));
                    Toast.makeText(activity, R.string.unboomark, Toast.LENGTH_SHORT).show();
                } else {
                    ((TestCreateActivity) getActivity()).questionBankList.get(position).setIs_bookmarked("1");
                    imgBookmark.setImageDrawable(activity.getResources().getDrawable(R.mipmap.bookmark_selected));
                    Toast.makeText(activity, R.string.boomark, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void refereshPage() {
        if (questionType.equalsIgnoreCase("MT")) {
            for (int j = 0; j < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().size(); j++) {
                if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().get(j).isSelect()) {
                    ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted().clear();

                }
            }
            adapterMatchingListDrag.notifyDataSetChanged();
            for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {

                ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().clear();
            }
            adapterMatchingListNormal.notifyDataSetChanged();
        }

        else {
            ((TestCreateActivity) getActivity()).questionBankList.get(position).setUser_answer("");



            ((TestCreateActivity) Objects.requireNonNull(getActivity())).questionBankList.get(position).setIsanswer(false, 0);
            if (((TestCreateActivity) getActivity()).changelang.getText().equals("H/E")) {
                ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
            } else if (((TestCreateActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
            }
            if (((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestionsHindi()!=null && ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestionsHindi().size()>0)
            {
                ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestionsHindi().get(position).setAnspositions("-1");
                ArrayList<Integer> selectedValue1 = new ArrayList<>();
                ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue1);
                ArrayList answers1 = new ArrayList();
                ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestionsHindi().get(position).setAnswers(answers1);

                ((TestCreateActivity) getActivity()).data.getQuestionsHindi().get(position).setUser_answer("");

            }

            if (((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestions()!=null && ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestions().size()>0)
            {
                ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestions().get(position).setAnspositions("-1");
                ArrayList<Integer> selectedValue2 = new ArrayList<>();
                ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestions().get(position).setSelectedValue(selectedValue2);
                ArrayList answers2 = new ArrayList();
                ((TestCreateActivity) Objects.requireNonNull(getActivity())).data.getQuestions().get(position).setAnswers(answers2);

                ((TestCreateActivity) getActivity()).data.getQuestions().get(position).setUser_answer("");

            }

            ((TestCreateActivity) Objects.requireNonNull(getActivity())).questionBankList.get(position).setAnspositions("-1");
            ArrayList<Integer> selectedValue = new ArrayList<>();
            ((TestCreateActivity) Objects.requireNonNull(getActivity())).questionBankList.get(position).setSelectedValue(selectedValue);
            ArrayList answers = new ArrayList();
            ((TestCreateActivity) Objects.requireNonNull(getActivity())).questionBankList.get(position).setAnswers(answers);
            guess = "0";
            for (int k = 0; k < LinearLayoutList.size(); k++) {
                LinearLayoutList.get(k).setSelected(false);
            }
        }
    }

    @Override
    public void sendOnclickInd(int p) {
        if (adapterMatchingListNormal!=null){
            for (int i = 0; i < ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().size(); i++) {
                if (((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().get(i).getPosition() == p) {
                    ((TestCreateActivity) getActivity()).questionBankList.get(position).getSelcted2().remove(i);
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
        if (!text.toString().isEmpty())
        {
            while (text.charAt(text.length() - 1) == '\n') {
                text = text.subSequence(0, text.length() - 1);
            }
        }

        return text;
    }

}