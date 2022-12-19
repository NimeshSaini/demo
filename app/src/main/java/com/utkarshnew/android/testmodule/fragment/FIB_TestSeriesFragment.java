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
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmadnemati.clickablewebview.ClickableWebView;
import com.google.gson.Gson;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Progress;
import com.utkarshnew.android.testmodule.activity.TestBaseActivity;
import com.utkarshnew.android.testmodule.adapter.FIB_Adapter;
import com.utkarshnew.android.testmodule.interfaces.WebTextSize;
import com.utkarshnew.android.testmodule.model.FIBEdit;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.SectioHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;

public class FIB_TestSeriesFragment extends MainFragment implements View.OnClickListener, Html.ImageGetter, WebTextSize {

    Progress mProgress;
    LinearLayout mcqoptionsLL, LLFIBquestion;
    RecyclerView rvfibquestion;
    FIB_Adapter fib_adapter;
    int count = 0;
    LinearLayout parentLL;
    NestedScrollView nestedSV;

    List<View> LinearLayoutList, parentList;
    List<TextView> tvList;
    List<FIBEdit> fibEdits = new ArrayList<>();
    int position;
    LinearLayout explanationLL;
    int selectedAnswerposition = -1;
    TextView explanationTV;
    RelativeLayout question_layout1;
    ClickableWebView tvQuestion, tvQuestionFib;
    private TextView tv_uid, tvEmail;
    private TextView markForReview, tvReportError, unmarkForReview, save_mark_for_review, remove_mark_for_review;
    private Question questionBank;
    private boolean status;
    private ImageView imgBookmark;
    private CheckBox checkBoxGuess;
    private Drawable empty;
    private String isCorrect, guess;
    ArrayList<Integer> selectedValue = new ArrayList();
    ArrayList<String> selectedValue1 = new ArrayList();
    ArrayList<TextView> textViews = new ArrayList();

    ArrayList tags = new ArrayList();
    // ArrayList<FIBSelectedTag> fibSelectedTags=new ArrayList();


    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            View myView;
            myView = view;
            selectedAnswerposition = (int) view.getTag();

            //j = LinearLayoutList.size();

            String answerPosition = "";
            boolean isSelected = false;
            String[] answers = ((TestBaseActivity) activity).questionBankList.get(position).getAnswer().split(",");
            //if (((TestBaseActivity) activity).questionBankList.get(position).getCount() < answers.length) {
            if (LinearLayoutList.get(selectedAnswerposition).isSelected()) {

                for (int i = 0; i < selectedValue.size(); i++) {
                    if (selectedAnswerposition == selectedValue.get(i)) {
                        selectedValue.remove(i);
                        selectedValue1.remove(i);
                        switch (fibEdits.get(selectedAnswerposition).getType()) {
                            case "number":
                                showEnterData(textViews.get(selectedAnswerposition), textViews.get(selectedAnswerposition).getText().toString(), fibEdits.get(selectedAnswerposition).getType(), fibEdits.get(selectedAnswerposition).getMax(), fibEdits.get(selectedAnswerposition).getMin());
                                break;
                            default:
                                showEnterData(textViews.get(selectedAnswerposition), textViews.get(selectedAnswerposition).getText().toString(), fibEdits.get(selectedAnswerposition).getType(), fibEdits.get(selectedAnswerposition).getMax(), fibEdits.get(selectedAnswerposition).getMin());
                        }
                        break;
                    }
                }
                // ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);


            } else {
//                selectedValue.remove(selectedAnswerposition);
//                selectedValue1.remove(selectedAnswerposition);
                switch (fibEdits.get(selectedAnswerposition).getType()) {
                    case "number":
                        showEnterData(textViews.get(selectedAnswerposition), "", fibEdits.get(selectedAnswerposition).getType(), fibEdits.get(selectedAnswerposition).getMax(), fibEdits.get(selectedAnswerposition).getMin());
                        break;
                    default:
                        showEnterData(textViews.get(selectedAnswerposition), "", fibEdits.get(selectedAnswerposition).getType(), fibEdits.get(selectedAnswerposition).getMax(), fibEdits.get(selectedAnswerposition).getMin());
                }
                //  ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);
                // if (((TestBaseActivity) activity).questionBankList.get(position).getCount() < answers.length) {

            }


            for (int j = 0; j < LinearLayoutList.size(); j++) {
                if (LinearLayoutList.get(j).isSelected()) {
                    isSelected = true;
                    break;
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

                ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
            } else {

                ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(false, 0);
            }

            Log.e("IS_SELECTED", String.valueOf(isSelected));

        }
    };

    private NetworkCall networkCall;
    int textSize = 0;


    @Override
    public void onTextSizeChanged(int size) {
        textSize=size;
        setHtmlSize(size);
    }

    public static FIB_TestSeriesFragment newInstance(int position, int textSize) {
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putInt("textSize", textSize);
        FIB_TestSeriesFragment fragment = new FIB_TestSeriesFragment();
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

    private void showEnterData(final TextView textView, String messege, String type, String max, String min) {
        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.enter_fib_layout, null, false);
        final Dialog quizBasicInfoDialog = new Dialog(activity);
        quizBasicInfoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        quizBasicInfoDialog.setCancelable(false);
        quizBasicInfoDialog.setCanceledOnTouchOutside(false);
        quizBasicInfoDialog.setContentView(v);
        quizBasicInfoDialog.show();
        Button btnSubmit, btn_cancel;
        final EditText message;

        btnSubmit = v.findViewById(R.id.btn_submit);
        message = v.findViewById(R.id.message);
        btn_cancel = v.findViewById(R.id.btn_cancel);

        message.setAllCaps(false);
        if (type.equalsIgnoreCase("number")) {
            message.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        } else {
            message.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        /*if (!min.equalsIgnoreCase("") && !min.equalsIgnoreCase("undefined"))
            message.setMinLines(Integer.parseInt(min));
        if (!max.equalsIgnoreCase("") && !max.equalsIgnoreCase("undefined"))
            message.setMaxLines(Integer.parseInt(max));*/
        if (!messege.equalsIgnoreCase("")) {
            message.setText(messege);
        } else {
            message.setHint("Enter Answer");
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {
                if (message.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(activity, "Please enter answer", Toast.LENGTH_SHORT).show();
                } else {
                    String answerPosition = "";
                    boolean isSelected = false;
                    quizBasicInfoDialog.dismiss();
                    SectioHashMap sectioHashMap = ((TestBaseActivity) requireActivity()).sectioncount.get(((TestBaseActivity) requireActivity()).PartId);
                    if (!(TextUtils.isEmpty(message.getText()))) {
                        if (Objects.requireNonNull(sectioHashMap).getSectioncount() < sectioHashMap.getOptionCount()) {
                            if ( LinearLayoutList.get(selectedAnswerposition).isSelected()) {
                                setFibAnswer(textView, type, message, sectioHashMap,"0");
                            }else
                                setFibAnswer(textView, type, message, sectioHashMap,"1");
                        } else if ( LinearLayoutList.get(selectedAnswerposition).isSelected()) {
                            setFibAnswer(textView, type, message, sectioHashMap,"0");
                        } else {
                            Toast.makeText(activity, "You Can't Attempt More Questions in this Section", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        textView.setText("Enter Answer");
                        LinearLayoutList.get(selectedAnswerposition).setSelected(false);
                        ((TestBaseActivity) activity).questionBankList.get(position).setSelectedString(selectedValue1);
                        ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);

                        if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue);
                            ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setSelectedString(selectedValue1);
                        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue);
                            ((TestBaseActivity) activity).data.getQuestions().get(position).setSelectedString(selectedValue1);
                        }
                    }

                    for (int j = 0; j < LinearLayoutList.size(); j++) {
                        if (LinearLayoutList.get(j).isSelected()) {
                            isSelected = true;
                            break;
                        }
                    }

                    if (isSelected) {
                        for (int i = 0; i < ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().size(); i++) {
                            answerPosition = answerPosition + ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().get(i) + ",";
                        }
                        if (answerPosition.startsWith("1")) {
                            answerPosition = "0" + "," + answerPosition;
                            selectedValue1.add(0, "");
                        }
                        ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
                        if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
                        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
                        }
                    } else {
                        ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(false, 0);
                        if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
                        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(false, 0);
                        }
                    }
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onClick(View view) {

                String answerPosition = "";
                boolean isSelected = false;
                quizBasicInfoDialog.dismiss();
                SectioHashMap sectioHashMap = ((TestBaseActivity) requireActivity()).sectioncount.get(((TestBaseActivity) requireActivity()).PartId);

                if (LinearLayoutList.get(selectedAnswerposition).isSelected()) {
                    Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() > 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                    ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                    Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                    //  Log.d("abcd", "" + ((TestBaseActivity) getActivity()));
                }

                textView.setText("Enter Answer");
                LinearLayoutList.get(selectedAnswerposition).setSelected(false);
                ((TestBaseActivity) activity).questionBankList.get(position).setSelectedString(selectedValue1);
                ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);

                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue);
                    ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setSelectedString(selectedValue1);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue);
                    ((TestBaseActivity) activity).data.getQuestions().get(position).setSelectedString(selectedValue1);
                }

                for (int j = 0; j < LinearLayoutList.size(); j++) {
                    if (LinearLayoutList.get(j).isSelected()) {
                        isSelected = true;
                        break;
                    }
                }

                if (isSelected) {
                    for (int i = 0; i < ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().size(); i++) {
                        answerPosition = answerPosition + ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().get(i) + ",";
                    }
                    if (answerPosition.startsWith("1")) {
                        answerPosition = "0" + "," + answerPosition;
                        selectedValue1.add(0, "");
                    }
                    ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIsanswer(true, selectedAnswerposition + 1, answerPosition, selectedValue1);
                    }
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
    }

    private void setFibAnswer(TextView textView, String type, EditText message, SectioHashMap sectioHashMap, String typeval) {
        String message_txt =  message.getText().toString();
        if (type.equalsIgnoreCase("number")) {
            double input = Double.parseDouble(message_txt);
            DecimalFormat df = new DecimalFormat("0.00");
            textView.setText(df.format(input));
            message_txt =textView.getText().toString();
        } else {
            textView.setText(message_txt);
        }

        LinearLayoutList.get(selectedAnswerposition).setSelected(true);
        selectedValue.add(selectedAnswerposition, selectedAnswerposition);
        selectedValue1.add(selectedAnswerposition, message_txt);
        selectedValue1.size();
        if (selectedValue1.size() == 3) {
            if (selectedValue1.get(2).equalsIgnoreCase("")) {
                selectedValue1.remove(2);
                selectedValue.remove(2);
            } else if (selectedValue1.get(1).equalsIgnoreCase("")) {
                selectedValue1.remove(1);
                selectedValue.remove(1);
            }
        }

        ((TestBaseActivity) activity).questionBankList.get(position).setSelectedValue(selectedValue);
        ((TestBaseActivity) activity).questionBankList.get(position).setSelectedString(selectedValue1);
        if (((TestBaseActivity) requireActivity()).changelang.getText().equals("H/E")) {
            ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue);
            ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setSelectedString(selectedValue1);
        } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
            ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue);
            ((TestBaseActivity) activity).data.getQuestions().get(position).setSelectedString(selectedValue1);
        }
        if (typeval.equalsIgnoreCase("1"))
        {
            Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() >= 0 ? sectioHashMap.getSectioncount() + 1 : 0);
            ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
            Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
            Log.d("abcd", "" + ((TestBaseActivity) getActivity()).PartId);
        }


    }


    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches())
                return "";
            return null;
        }

    }

    private void getBundleData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position", 0);
            textSize = bundle.getInt("textSize", 0);
        }
    }

    @SuppressLint("SetTextI18n")
    public void refereshPage() {
        try {

            ((TestBaseActivity) activity).questionBankList.get(position).setIsanswer(false, 0);

            if (((TestBaseActivity) requireActivity()).changelang.getText().equals("H/E")) {
                ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
                ((TestBaseActivity) activity).data.getQuestionsHindi().get(position).setIsanswer(false, 0);
            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                ((TestBaseActivity) activity).data.getQuestions().get(position).setIsanswer(false, 0);

                ((TestBaseActivity) activity).data.getQuestions().get(position).setIsanswer(false, 0);
            }


            ArrayList<Integer> selectedValue = new ArrayList<>();
            ArrayList<String> selectedstring = new ArrayList<>();
            ArrayList answers = new ArrayList();

            if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                if (((TestBaseActivity) requireActivity()).data.getQuestionsHindi() != null && ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().size() > 0) {

                    ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setAnspositions("-1");
                    ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setSelectedValue(selectedValue);
                    ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setSelectedString(selectedstring);
                    ((TestBaseActivity) requireActivity()).data.getQuestionsHindi().get(position).setAnswers(answers);
                }

            } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                if (((TestBaseActivity) requireActivity()).data.getQuestions() != null && ((TestBaseActivity) requireActivity()).data.getQuestions().size() > 0) {
                    ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setAnspositions("-1");
                    ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setSelectedValue(selectedValue);
                    ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setSelectedString(selectedstring);
                    ((TestBaseActivity) requireActivity()).data.getQuestions().get(position).setAnswers(answers);

                }
            }
            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setAnspositions("-1");
            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setSelectedValue(selectedValue);

            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setSelectedString(selectedstring);
            ((TestBaseActivity) requireActivity()).questionBankList.get(position).setAnswers(answers);

            checkBoxGuess.setChecked(false);
            guess = "0";
            for (int k = 0; k < LinearLayoutList.size(); k++) {
                if ( LinearLayoutList.get(k).isSelected())
                {
                    SectioHashMap sectioHashMap = ((TestBaseActivity) requireActivity()).sectioncount.get(((TestBaseActivity) requireActivity()).PartId);
                    Objects.requireNonNull(sectioHashMap).setSectioncount(sectioHashMap.getSectioncount() > 0 ? sectioHashMap.getSectioncount() - 1 : 0);
                    ((TestBaseActivity) getActivity()).sectioncount.put(((TestBaseActivity) getActivity()).PartId, sectioHashMap);
                    Log.d("abcd", "" + new Gson().toJson(sectioHashMap));
                }
                LinearLayoutList.get(k).setSelected(false);
                textViews.get(k).setText("Enter answer for ( " + tvList.get(k).getText() + " )");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView(View view) {
        mcqoptionsLL = view.findViewById(R.id.mcqoptions);
        tvQuestion = view.findViewById(R.id.tv_question);
        question_layout1 = view.findViewById(R.id.question_layout1);
        tvQuestionFib = view.findViewById(R.id.tv_question_fib);
        imgBookmark = view.findViewById(R.id.img_bookmark);
        checkBoxGuess = view.findViewById(R.id.checkBox);
        LLFIBquestion = view.findViewById(R.id.LLFIBquestion);
        rvfibquestion = view.findViewById(R.id.rvfibquestion1);
        markForReview = view.findViewById(R.id.mark_for_review);

        unmarkForReview = view.findViewById(R.id.unmark_for_review);
        save_mark_for_review = view.findViewById(R.id.save_mark_for_review);
        remove_mark_for_review = view.findViewById(R.id.remove_mark_for_review);
        tvReportError = view.findViewById(R.id.tv_report_error);
        tv_uid = view.findViewById(R.id.tv_uid);
        tvEmail = view.findViewById(R.id.tv_email);
        explanationLL = view.findViewById(R.id.explanationLL);
        explanationTV = view.findViewById(R.id.explanationTV);
        nestedSV = view.findViewById(R.id.nestedSV);
        LinearLayoutList = new ArrayList<>();
        parentList = new ArrayList<>();
        tvList = new ArrayList<>();


        questionBank = ((TestBaseActivity) getActivity()).questionBankList.get(position);
        String htmlAsString = ((TestBaseActivity) getActivity()).questionBankList.get(position).getQuestion();// used by WebView
        tv_uid.setText("Utkarsh QUID " + questionBank.getId());
        mcqoptionsLL.setVisibility(View.VISIBLE);
        LLFIBquestion.setVisibility(View.GONE);

        // Spanned spanned = Html.fromHtml("Q-" + (position + 1) + ". " + htmlAsString, this, null);
        String replaceFIB = htmlAsString.replace("FIB", ".....");
        //  tvQuestion.requestFocus();
        // tvQuestion.getSettings().setLightTouchEnabled(true);
        tvQuestion.setBackgroundColor(Color.TRANSPARENT);
        tvQuestion.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
        tvQuestion.getSettings().setJavaScriptEnabled(true);
        tvQuestion.getSettings().setGeolocationEnabled(true);
        //tvQuestion.setSoundEffectsEnabled(true);
        //tvQuestion.loadData("Q-" + (position + 1) + ". " + replaceFIB, "text/html", "UTF-8");
        Helper.TestWebHTMLLoad(tvQuestion, "Q-" + (position + 1) + ". " + replaceFIB);
        tvQuestion.setLongClickable(false);
        tvQuestion.setOnLongClickListener(new WebViewClickListener(tvQuestion, question_layout1));


        // tvQuestion.setText(replaceFIB);
        if (TextUtils.isEmpty(((TestBaseActivity) getActivity()).questionBankList.get(position).getParagraphText())) {
            tvQuestionFib.setVisibility(View.GONE);
        } else {
            tvQuestionFib.setVisibility(View.VISIBLE);
            Helper.TestWebHTMLLoad(tvQuestionFib, ((TestBaseActivity) getActivity()).questionBankList.get(position).getParagraphText());

//            tvQuestionFib.setText(Html.fromHtml(((TestBaseActivity) getActivity()).questionBankList.get(position).getParagraphText()));
        }

        addQuestionOption();
        if (((TestBaseActivity) getActivity()).questionBankList.get(position).isMarkForReview()) {
            markForReview.setVisibility(View.GONE);
            unmarkForReview.setVisibility(View.VISIBLE);
        } else {
            markForReview.setVisibility(View.VISIBLE);
            unmarkForReview.setVisibility(View.GONE);
        }
     /*    if (((TestBaseActivity) getActivity()).questionBankList.get(position).isIssaveMarkForReview()){
            save_mark_for_review.setVisibility(View.GONE);
            remove_mark_for_review.setVisibility(View.VISIBLE);
        }
         else {
             save_mark_for_review.setVisibility(View.VISIBLE);
             remove_mark_for_review.setVisibility(View.GONE);
         }
*/

        imgBookmark.setOnClickListener(this);
        checkBoxGuess.setOnClickListener(this);
        markForReview.setOnClickListener(this);
        unmarkForReview.setOnClickListener(this);
        remove_mark_for_review.setOnClickListener(this);
        save_mark_for_review.setOnClickListener(this);
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
        tvEmail.setText(((TestBaseActivity) getActivity()).data.getUserDetails().getEmail());

        setHtmlSize(textSize);
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
       /* if (selectedValue!=null)selectedValue.clear();
        if (selectedValue1!=null)selectedValue1.clear();*/
        if (textViews != null) textViews.clear();
        for (int j = 1; j <= countFIBWords(questionBank.getQuestion().trim()); j++) {
            if (status)
                break;
            switch (j) {
                case 1:
                    if (questionBank.getOption1().isEmpty()) {
                        status = true;
                        break;
                    }

                    mcqoptionsLL.addView(initMCQOptionView("1", questionBank.getOption1(),
                            "11", false, j - 1));
                    break;
                case 2:
                    if (questionBank.getOption2().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("2", questionBank.getOption2(),
                            "11", false, j - 1));
                    break;
                case 3:
                    if (questionBank.getOption3().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("3", questionBank.getOption3(),
                            "11", false, j - 1));
                    break;
                case 4:
                    if (questionBank.getOption4().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("4", questionBank.getOption4(),
                            "11", false, j - 1));
                    break;
                case 5:
                    if (questionBank.getOption5().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("5", questionBank.getOption5(),
                            "11", false, j - 1));
                    break;
                case 6:
                    if (questionBank.getOption6().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("6", questionBank.getOption6(),
                            "11", false, j - 1));
                    break;
                case 7:
                    if (questionBank.getOption7().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("7", questionBank.getOption7(),
                            "11", false, j - 1));
                    break;
                case 8:
                    if (questionBank.getOption8().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("8", questionBank.getOption8(),
                            "11", false, j - 1));
                    break;
                case 9:
                    if (questionBank.getOption9().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("9", questionBank.getOption9(),
                            "11", false, j - 1));
                    break;
                case 10:
                    if (questionBank.getOption10().isEmpty()) {
                        status = true;
                        break;
                    }
                    mcqoptionsLL.addView(initMCQOptionView("10", questionBank.getOption10(),
                            "11", false, j - 1));
                    break;
            }
        }
        /*tags = new ArrayList();
        for (int j = 0; j < countFIBWords(questionBank.getQuestion().trim()); j++) {
            tags.add(j, "");
        }*/
        // ((TestBaseActivity)getActivity()).questionBankList.get(position).setAnswers(tags);

      /*  rvfibquestion.setLayoutManager(new LinearLayoutManager(activity));
        rvfibquestion.setHasFixedSize(true);
        fib_adapter = new FIB_Adapter(activity,position ,((TestBaseActivity)getActivity()).questionBankList.get(position).getAnswers());
        rvfibquestion.setAdapter(fib_adapter);*/

        // Toast.makeText(activity, "Tag: "+tags, Toast.LENGTH_SHORT).show();

        status = false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public LinearLayout initMCQOptionView(String text1, String text2, final String pollValue, boolean pollVisiblity, int tag) {
        LinearLayout v = (LinearLayout) View.inflate(activity, R.layout.layout_option_test_view_fib, null);
        TextView tv = v.findViewById(R.id.optionIconTV);
        TextView text = v.findViewById(R.id.optionTextTV);
        text.setAllCaps(false);
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
        FIBEdit fibEdit = new Gson().fromJson(text2, FIBEdit.class);
        if (fibEdit != null && !fibEdit.getType().equalsIgnoreCase("")) {
            fibEdits.add(fibEdit);
        }
        textViews.add(text);
        v.setTag(tag);
        if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue() != null) {
            if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue().contains(tag)) {
                if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedString().size()>0)
                {
                    if (!TextUtils.isEmpty(((TestBaseActivity) activity).questionBankList.get(position).getSelectedString().get(tag))) {
                        v.setSelected(true);
                        text.setText(((TestBaseActivity) activity).questionBankList.get(position).getSelectedString().get(tag));
                        selectedValue1.add(((TestBaseActivity) activity).questionBankList.get(position).getSelectedString().get(tag));
                        selectedValue = ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue();
                    } else {
                        selectedValue1.add("");
                        text.setText("Enter answer for ( " + text1 + " )");
                    }
                }else {
                    text.setText("Enter answer for ( " + text1 + " )");
                    selectedValue1.add("");
                }


            } else {
                text.setText("Enter answer for ( " + text1 + " )");
                selectedValue.add(0);
                selectedValue1.add("");
            }
        }

/*
        if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedString() != null) {
            if (((TestBaseActivity) activity).questionBankList.get(position).getSelectedString().contains(((TestBaseActivity) activity).questionBankList.get(position).getSelectedString().get(tag))) {
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).getSelectedString().get(tag).toString().equalsIgnoreCase(""))
                    text.setText("Enter Answer");
                else
                    text.setText(((TestBaseActivity) getActivity()).questionBankList.get(position).getSelectedString().get(tag).toString());

            //    selectedValue = ((TestBaseActivity) activity).questionBankList.get(position).getSelectedValue();
            }else  text.setText("Enter Answer");
        }else text.setText("Enter Answer");*/
        /*if (((TestBaseActivity) getActivity()).questionBankList.get(position).isanswer() && ((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() != 0)
            if (((TestBaseActivity) getActivity()).questionBankList.get(position).getAnswerPosttion() - 1 == tag)
        */
        tvList.add(tv);
        LinearLayoutList.add(v);
        parentList.add(parentLL);
        tvList.add(tv);
        v.setOnClickListener(onClickListener);

        //v.setOnClickListener(onClickListener);

        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_bookmark:
                if (((TestBaseActivity) getActivity()).questionBankList.get(position).getIs_bookmarked().equals("1")) {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIs_bookmarked("0");
                    imgBookmark.setImageDrawable(activity.getResources().getDrawable(R.mipmap.bookmark_unselected));

                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIs_bookmarked("0");
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIs_bookmarked("0");
                    }

                    //Helper.show_Toast(activity,getResources().getString(R.string.unboomark));
                    Toast.makeText(activity, R.string.unboomark, Toast.LENGTH_SHORT).show();
                    //networkcallforremovebookmark(position, ((TestBaseActivity) getActivity()).questionBankList.get(position).getId());
                } else {
                    ((TestBaseActivity) getActivity()).questionBankList.get(position).setIs_bookmarked("1");
                    imgBookmark.setImageDrawable(activity.getResources().getDrawable(R.mipmap.bookmark_selected));
                    if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                        ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIs_bookmarked("1");
                    } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                        ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIs_bookmarked("1");
                    }
                    Toast.makeText(activity, R.string.boomark, Toast.LENGTH_SHORT).show();
                    //Helper.show_Toast(activity,getResources().getString(R.string.boomark));

                    // netoworkCallforAddbookmark(position, ((TestBaseActivity) getActivity()).questionBankList.get(position).getId(), ((TestBaseActivity) getActivity()).testseriesid);
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
                //Helper.show_Toast(activity,"Marked for review.");

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
                //save_mark_for_review.setVisibility(View.VISIBLE);
                //remove_mark_for_review.setVisibility(View.GONE);
                break;
            case R.id.unmark_for_review:
                //Helper.show_Toast(activity,"Unmarked for review");

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
                    //Helper.show_Toast(activity,"Save & Marked for review.");

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
                    //  remove_mark_for_review.setVisibility(View.VISIBLE);
                    // save_mark_for_review.setVisibility(View.GONE);
                    markForReview.setVisibility(View.VISIBLE);
                    unmarkForReview.setVisibility(View.GONE);
                } else {
                    //Helper.show_Toast(activity,"Please give answer.");

                    Toast.makeText(activity, "Please give answer.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.remove_mark_for_review:
                //Helper.show_Toast(activity,"Remove & Marked for review.");

                Toast.makeText(activity, "Remove & Marked for review.", Toast.LENGTH_SHORT).show();
                ((TestBaseActivity) getActivity()).questionBankList.get(position).setIssaveMarkForReview(false);
                if (((TestBaseActivity) getActivity()).changelang.getText().equals("H/E")) {
                    ((TestBaseActivity) getActivity()).data.getQuestionsHindi().get(position).setIssaveMarkForReview(false);
                } else if (((TestBaseActivity) getActivity()).changelang.getText().equals("E/H")) {
                    ((TestBaseActivity) getActivity()).data.getQuestions().get(position).setIssaveMarkForReview(false);
                }
                ((TestBaseActivity) getActivity()).notifynumberApater();
                //  save_mark_for_review.setVisibility(View.VISIBLE);
                // remove_mark_for_review.setVisibility(View.GONE);

                break;
            case R.id.tv_report_error:

                showPopupErrorTest();
                break;
        }
    }

    private void showPopupErrorTest() {

        LayoutInflater li = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View v = li.inflate(R.layout.dialog_report_error, null, false);
        final Dialog quizBasicInfoDialog = new Dialog(activity);
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
                radioButton[0] = v.findViewById(selectedId);
                if (TextUtils.isEmpty(feedbackET.getText().toString().trim()))
                    isDataValid = Helper.DataNotValid(getActivity(), feedbackET);
                if (isDataValid) {
                    //networkcallForSubmitquery(radioButton[0].getText().toString().trim(), feedbackET.getText().toString().trim(), quizBasicInfoDialog);
                }
/*                Toast.makeText(activity,
                        radioButton[0].getText(), Toast.LENGTH_SHORT).show();*/
            }
        });
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
                //    CharSequence t = tvQuestion.getText();
                //   tvQuestion.setText(t);
            }
        }
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

}
