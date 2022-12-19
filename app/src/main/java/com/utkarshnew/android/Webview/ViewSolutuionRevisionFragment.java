package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.google.gson.Gson;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.testmodule.model.Answers;
import com.utkarshnew.android.testmodule.model.FIBEdit;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class ViewSolutuionRevisionFragment extends MainFragment implements Html.ImageGetter {

    private static final String TAG = "ViewSolutionFragment";
    Progress mProgress;
    LinearLayout mcqoptionsLL;
    int count = 0;
    LinearLayout parentLL;
    Answers answers;
    TestseriesBase viewSolutionData;
    String questionType;
    NestedScrollView nestedSV;
    List<View> LinearLayoutList, parentList, tvList;
    int position;

    int selectedAnswerposition;
    Question result;
    Question questionDump;
    TextView userEmailTV, tv_uid, your_ans_text, question_status;
    private ClickableWebView tvQuestion;
    RelativeLayout questionsol_layout1;
    LinearLayout LLmatchinquestion;
    RecyclerView rvmatchinquestion1, rvmatchinquestion2;
    private boolean status;
    ArrayList<WebView> textViews = new ArrayList();
    private Drawable empty, empty2;

    public static ViewSolutuionRevisionFragment newInstance(int position, TestseriesBase testseriesBase, String questionType, Answers answers) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("questionType", questionType);
        args.putSerializable("testseriesBase", testseriesBase);
        args.putSerializable("answers", answers);
        ViewSolutuionRevisionFragment fragment = new ViewSolutuionRevisionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_revision_solution, null);
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
            viewSolutionData = (TestseriesBase) bundle.getSerializable("testseriesBase");
            answers = (Answers) bundle.getSerializable("answers");

            questionDump = viewSolutionData.getData().getQuestions().get(position);
            questionType = bundle.getString("questionType", "");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView(View view) {
        mcqoptionsLL = view.findViewById(R.id.mcqoptions);
        tvQuestion = view.findViewById(R.id.tv_question);
        questionsol_layout1 = view.findViewById(R.id.questionsol_layout1);
        LLmatchinquestion = view.findViewById(R.id.LLmatchinquestion);
        rvmatchinquestion1 = view.findViewById(R.id.rvmatchinquestion1);
        rvmatchinquestion2 = view.findViewById(R.id.rvmatchinquestion2);
        rvmatchinquestion1.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setLayoutManager(new LinearLayoutManager(activity));
        rvmatchinquestion2.setHasFixedSize(true);
      /*  explanationLL = view.findViewById(R.id.explanationLL);
        explanationTV = view.findViewById(R.id.explanationTV);*/
        tv_uid = view.findViewById(R.id.tv_uid);
        your_ans_text = view.findViewById(R.id.your_ans);
        question_status = view.findViewById(R.id.question_status);
        nestedSV = view.findViewById(R.id.nestedSV);
        userEmailTV = view.findViewById(R.id.userEmailTV);
        LinearLayoutList = new ArrayList<>();
        parentList = new ArrayList<>();
        tvList = new ArrayList<>();

        result = viewSolutionData.getData().getQuestions().get(position);
        tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);


        switch (questionType) {
            default:
                mcqoptionsLL.setVisibility(View.VISIBLE);
                LLmatchinquestion.setVisibility(View.GONE);
                String htmlAsString1 = viewSolutionData.getData().getQuestions().get(position).getQuestion();
                tvQuestion.setBackgroundColor(Color.TRANSPARENT);
                tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
                tvQuestion.getSettings().setJavaScriptEnabled(true);
                tvQuestion.getSettings().setGeolocationEnabled(true);
                //tvQuestion.loadData("Q-"+(position+1)+". "+htmlAsString1, "text/html", "UTF-8");
                Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + htmlAsString1);
                tvQuestion.setLongClickable(false);
                tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, questionsol_layout1));


                addSolutionOption();
        }

        displaySolution();

    }

    public int countFIBWords(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.substring(i).startsWith("FIB")) {
                count++;
            }
        }
        return count;
    }

    @SuppressLint("SetTextI18n")
    private void displaySolution() {
        if (answers.getState().equalsIgnoreCase("answered") && answers.getAnswers() != null) {
            String your_ans = "";
            for (int i = 0; i < answers.getAnswers().size(); i++) {
                if (answers.getAnswers().get(i).toString().equalsIgnoreCase("1")) {
                    switch (i) {
                        case 0:
                            your_ans = your_ans + "A,";

                            break;
                        case 1:

                            your_ans = your_ans + "B,";

                            break;
                        case 2:

                            your_ans = your_ans + "C,";

                            break;
                        case 3:

                            your_ans = your_ans + "D,";

                            break;


                    }
                }
            }
            your_ans_text.setText(your_ans);
        } else {
            if (answers.getState().equalsIgnoreCase("not_visited")) {
                your_ans_text.setText("Not Visited");
            } else {
                your_ans_text.setText(answers.getState());
            }
        }
        switch (questionType) {
            case "":
            case "SC":
                if (answers.getState().equalsIgnoreCase("answered")) {
                    String your_ans = "", status = "";
                    for (int i = 0; i < answers.getAnswers().size(); i++) {
                        if (answers.getAnswers().get(i).toString().equalsIgnoreCase("1")) {
                            switch (i) {
                                case 0:
                                    your_ans = "A";
                                    if (result.getIsCorrect().equalsIgnoreCase("0")) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect().equalsIgnoreCase("1")) {
                                        status = "Correct";
                                        LinearLayoutList.get(i).setSelected(true);

                                    }

                                    break;
                                case 1:

                                    your_ans = "B";
                                    if (result.getIsCorrect().equalsIgnoreCase("0")) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect().equalsIgnoreCase("1")) {
                                        status = "Correct";
                                        LinearLayoutList.get(i).setSelected(true);

                                    }
                                    break;
                                case 2:

                                    your_ans = "C";
                                    if (result.getIsCorrect().equalsIgnoreCase("0")) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect().equalsIgnoreCase("1")) {
                                        status = "Correct";
                                        LinearLayoutList.get(i).setSelected(true);

                                    }
                                    break;
                                case 3:

                                    your_ans = "D";
                                    if (result.getIsCorrect().equalsIgnoreCase("0")) {
                                        parentList.get(i).setBackground(getResources().getDrawable(R.drawable.bg_mcq_selected4));
                                        TextView tv = (TextView) ((LinearLayout) parentList.get(i)).getChildAt(1);
                                        tv.setBackgroundResource(R.drawable.circle4);
                                        tv.setTextColor(getResources().getColor(R.color.white));
                                        status = "Incorrect";
                                    } else if (result.getIsCorrect().equalsIgnoreCase("1")) {
                                        status = "Correct";
                                        LinearLayoutList.get(i).setSelected(true);

                                    }
                                    break;


                            }
                        }
                    }
                    question_status.setVisibility(View.VISIBLE);
                    your_ans_text.setText(your_ans);
                    question_status.setText("|| " + status);
                } else {
                    if (answers.getAnswers().equals("not_visited")) {
                        your_ans_text.setText("Not Visited");
                    } else {
                        your_ans_text.setText(answers.getState());
                    }


                }
                if (questionDump.getOption1() != null && questionDump.getOption1().equalsIgnoreCase(questionDump.getRight_answer())) {
                    LinearLayoutList.get(0).setSelected(true);
                } else if (questionDump.getOption2() != null && questionDump.getOption2().equalsIgnoreCase(questionDump.getRight_answer())) {
                    LinearLayoutList.get(1).setSelected(true);


                } else if (questionDump.getOption3() != null && questionDump.getOption3().equalsIgnoreCase(questionDump.getRight_answer())) {
                    LinearLayoutList.get(2).setSelected(true);

                } else if (questionDump.getOption4() != null && questionDump.getOption4().equalsIgnoreCase(questionDump.getRight_answer())) {
                    LinearLayoutList.get(3).setSelected(true);

                }

               /* for (int i = 0; i < LinearLayoutList.size(); i++) {
                    if (!TextUtils.isEmpty(result.getAnswer()))
                    {
                        if (i+1==Integer.parseInt(result.getAnswer()))
                            LinearLayoutList.get(i).setSelected(true);
                    }
                }*/
                break;

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

    private void addSolutionOption() {
        for (int j = 1; j <= 10; j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (result.getOption1().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("A", result.getOption1(),
                            "11", false, j - 1));
                    break;
                case 2:
                    if (result.getOption2().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("B", result.getOption2(),
                            "11", false, j - 1));
                    break;
                case 3:
                    if (result.getOption3() == null || result.getOption3().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("C", result.getOption3(),
                            "11", false, j - 1));
                    break;
                case 4:
                    if (result.getOption4() == null || result.getOption4().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("D", result.getOption4(),
                            "11", false, j - 1));
                    break;


            }
        }
        status = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @SuppressLint("CutPasteId")
    public LinearLayout initMCQOptionView(String text1, String text2, final String pollValue, boolean pollVisiblity, int tag) {

        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_test_view_solution, null);
        TextView tv = v.findViewById(R.id.optionIconTV);
        LinearLayout option_layout = v.findViewById(R.id.viewLL);
        WebView text = v.findViewById(R.id.optionTextTV);
        RadioButton radioRB = v.findViewById(R.id.radioRB);
        parentLL = v.findViewById(R.id.viewLL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 0);

        if (pollVisiblity) {
            radioRB.setVisibility(View.GONE);
        }
        v.setLayoutParams(lp);
        tv.setText(text1);
        tv.setGravity(Gravity.CENTER);
        if (questionType.equalsIgnoreCase("FIB")) {
            FIBEdit fibEdit = new Gson().fromJson(text2, FIBEdit.class);
            if (fibEdit != null) {

                if (fibEdit.getType().equalsIgnoreCase("number")) {
                    //text.loadData("Min value "+fibEdit.getMin() + " Max value " +fibEdit.getMax(), "text/html", "UTF-8");
                    text.getSettings().setJavaScriptEnabled(true);
                    Helper.TestWebHTMLLoad(text, "Min value " + fibEdit.getMin() + " Max value " + fibEdit.getMax());
                    v.setSelected(true);
                } else if (fibEdit.getType().equalsIgnoreCase("character")) {
                    //text.loadData("Min text length "+fibEdit.getMin() + " Max text length " +fibEdit.getMax(), "text/html", "UTF-8");
                    text.getSettings().setJavaScriptEnabled(true);
                    Helper.TestWebHTMLLoad(text, "Min text length " + fibEdit.getMin() + " Max text length " + fibEdit.getMax());
                    v.setSelected(true);
                } else {
                    //text.loadData(fibEdit.getAnswer(), "text/html", "UTF-8");
                    text.getSettings().setJavaScriptEnabled(true);
                    Helper.TestWebHTMLLoad(text, fibEdit.getAnswer());
                    v.setSelected(true);
                }

                //    textViews.add(text);
            }
        } else {
            //text.loadData(text2, "text/html", "UTF-8");
            text.getSettings().setJavaScriptEnabled(true);
            Helper.TestWebHTMLLoad(text, text2);
        }
        text.setLongClickable(false);
        text.setOnLongClickListener(new WebViewClickListener_option(text, option_layout));
        v.setTag(tag);
        textViews.add(text);

        LinearLayoutList.add(v);
        parentList.add(parentLL);
        tvList.add(tv);
        return v;
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
                mDrawable.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
                mDrawable.setLevel(1);

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

}
