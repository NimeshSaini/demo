package com.utkarshnew.android.CreateTest.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.utkarshnew.android.CreateTest.Activity.TestCreateActivity;
import com.utkarshnew.android.CreateTest.Model.CreateTestQuesData;
import com.utkarshnew.android.CreateTest.Model.CreateTestSubject;
import com.utkarshnew.android.CreateTest.Model.QuestionCount;
import com.utkarshnew.android.CreateTest.Model.QuestionCountData;
import com.utkarshnew.android.CreateTest.Model.TypeTest;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.testmodule.model.Data;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.Questions2;
import com.utkarshnew.android.testmodule.model.TestBasic;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class CreateTestFragmentThree extends MainFragment implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    RelativeLayout parentLL;
    RelativeLayout spinnerLL;
    TextView totalquestion, lowTV, mediumTV, highTV, maxTV;
    EditText et_max;
    Button buttonProceed;
    Activity activity;
    String frag_type,LANG="";
    String subjectIds = "",courseids="";
    ImageView downarrowIV;
    String enterQues;
    TextView typeSpinner;
    int totalQues;
    String type = "0";
    String type_total_question = "0";
    QuestionCount questionCount;
    ArrayList<CreateTestSubject> createTestSubjects = new ArrayList<>();
    BottomSheetDialog bottomSheetDialog;

    public CreateTestFragmentThree() {
        // Required empty public constructor
    }

    public static CreateTestFragmentThree newInstance(String frag_type, ArrayList<CreateTestSubject> createTestSubjects, String LANG, String courseids) {
        CreateTestFragmentThree examPrepLayer1 = new CreateTestFragmentThree();
        Bundle args = new Bundle();
        args.putString(Const.FRAG_TYPE, frag_type);
        args.putString(Const.LANG, LANG);
        args.putString("courseids", courseids);
        args.putSerializable(Const.CREATE_COURSE_SUBJECT_DATA, createTestSubjects);
        examPrepLayer1.setArguments(args);
        return examPrepLayer1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            LANG = getArguments().getString(Const.LANG);
            courseids = getArguments().getString("courseids");
            createTestSubjects = (ArrayList<CreateTestSubject>) getArguments().getSerializable(Const.CREATE_COURSE_SUBJECT_DATA);
            List<String> myCourseIds = new ArrayList<>();
            for (CreateTestSubject createTestSubject : createTestSubjects) {
                myCourseIds.add(createTestSubject.getId());
            }
            subjectIds = TextUtils.join(",", myCourseIds);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        spinnerLL.setOnClickListener(this);
        downarrowIV.setOnClickListener(this);
        typeSpinner.setOnClickListener(this);

        buttonProceed.setOnClickListener(new OnSingleClickListener(() -> {
            CheckValidation();
            return null;
        }));


    }

    public void CheckValidation() {
        enterQues = et_max.getText().toString().trim();

        if (typeSpinner.getText().toString().equalsIgnoreCase("Type")) {
            Toast.makeText(activity, "Please select test type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(enterQues) || enterQues.equalsIgnoreCase("0")) {
            Toast.makeText(activity, "Question count should be 1 to 100", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Integer.parseInt(enterQues) > 100) {
            Toast.makeText(activity, "Question count should be less then " + "100", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (typeSpinner.getText().toString().equalsIgnoreCase("Low") && Integer.parseInt(enterQues) > Integer.parseInt(questionCount.getEasy())) {
                Toast.makeText(activity, "Low question count should be max " + Integer.parseInt(questionCount.getEasy()), Toast.LENGTH_SHORT).show();
                return;
            }

            if (typeSpinner.getText().toString().equalsIgnoreCase("Medium") && Integer.parseInt(enterQues) > Integer.parseInt(questionCount.getMedium())) {
                Toast.makeText(activity, "Medium question count should be max " + Integer.parseInt(questionCount.getMedium()), Toast.LENGTH_SHORT).show();
                return;
            }

            if (typeSpinner.getText().toString().equalsIgnoreCase("High") && Integer.parseInt(enterQues) > Integer.parseInt(questionCount.getHard())) {
                Toast.makeText(activity, "High question count should be max " + Integer.parseInt(questionCount.getHard()), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        createTestDialog();
        NetworkAPICall(API.API_CREATE_TEST_GET_TEST, "", false, false, false);
    }

    private void initView(View view) {
        parentLL = view.findViewById(R.id.parentLL);
        spinnerLL = view.findViewById(R.id.spinnerLL);
        totalquestion = view.findViewById(R.id.totalquestion);
        lowTV = view.findViewById(R.id.lowTV);
        mediumTV = view.findViewById(R.id.mediumTV);
        highTV = view.findViewById(R.id.highTV);
        maxTV = view.findViewById(R.id.maxTV);
        et_max = view.findViewById(R.id.et_max);
        typeSpinner = view.findViewById(R.id.typeSpinner);
        downarrowIV = view.findViewById(R.id.downarrowIV);
        buttonProceed = view.findViewById(R.id.buttonProceed);
        RefreshDataList();
    }

    private void RefreshDataList() {
        Helper.showProgressDialog(activity);
        NetworkAPICall(API.API_CREATE_TEST_GET_QUES_COUNT, "", false, false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_test_three, container, false);

    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_CREATE_TEST_GET_QUES_COUNT:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setSubject_ids(subjectIds);
                getcoursedataencryptionData.setCourse_ids(courseids);
                getcoursedataencryptionData.setLang(LANG);
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.API_CREATE_TEST_GET_QUES_COUNT(getcoursedatadoseStrScr);

            case API.API_CREATE_TEST_GET_TEST:
                EncryptionData getcoursedataencryptionData1 = new EncryptionData();
                getcoursedataencryptionData1.setSubject_ids(subjectIds);
                getcoursedataencryptionData1.setLimit(String.valueOf(enterQues));
                getcoursedataencryptionData1.setCourse_ids(courseids);
                getcoursedataencryptionData1.setType(type);
                getcoursedataencryptionData1.setLang(LANG);
                getcoursedataencryptionData1.setQue_count(type_total_question);
                String getcoursedataencryptionDatadoseStr1 = new Gson().toJson(getcoursedataencryptionData1);
                String getcoursedatadoseStrScr1 = AES.encrypt(getcoursedataencryptionDatadoseStr1);
                return service.API_CREATE_TEST_GET_TEST(getcoursedatadoseStrScr1);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Helper.dismissProgressDialog();
        switch (apitype) {
            case API.API_CREATE_TEST_GET_QUES_COUNT:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        QuestionCountData questionCountData = new Gson().fromJson(jsonobject.toString(), QuestionCountData.class);
                        questionCount = questionCountData.getData();
                        setData();
                    } else {
                        RetrofitResponse.GetApiData(getActivity(), jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.API_CREATE_TEST_GET_TEST:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                                    bottomSheetDialog.dismiss();
                                }
                                CreateTestQuesData createTestQuesData = new Gson().fromJson(jsonobject.toString(), CreateTestQuesData.class);
                                if (createTestQuesData != null && createTestQuesData.getData() != null) {
                                    Intent quizView = new Intent(activity, TestCreateActivity.class);
                                    Data data = new Data();
                                    TestBasic testBasic = new TestBasic();
                                    testBasic.setTestSeriesName("My Test");
                                    data.setTestBasic(testBasic);

                                    if (LANG.equalsIgnoreCase("3"))
                                    {
                                        if (createTestQuesData.getData().getQuestions_hindi()!=null && createTestQuesData.getData().getData()!=null)
                                        {
                                            if (createTestQuesData.getData().getData().size()>=createTestQuesData.getData().getQuestions_hindi().size())
                                            {
                                                ArrayList<Question> questiondata =  createTestQuesData.getData().getData();

                                                ArrayList<Question> questiondata_hindi =  createTestQuesData.getData().getQuestions_hindi();

                                                ArrayList<Question> hindiQuestion = new ArrayList<>();
                                                ArrayList<Question> englishquestion = new ArrayList<>();

                                                for (Question question :questiondata) {
                                                    boolean is_question =false;
                                                    for (Question question1:questiondata_hindi)
                                                    {
                                                        if (question.getConfigId().equalsIgnoreCase(question1.getConfigId()))
                                                        {
                                                            is_question =true;
                                                            question.setIsCorrect("0");
                                                            question1.setIsCorrect("0");

                                                            hindiQuestion.add(question1);
                                                            englishquestion.add(question);

                                                            break;
                                                        }
                                                    }
                                                    if (!is_question)
                                                    {
                                                        question.setIsCorrect("0");
                                                        hindiQuestion.add(question);
                                                        englishquestion.add(question);
                                                    }
                                                }
                                                if (hindiQuestion.size()>0 &&englishquestion.size()>0 )
                                                {
                                                    data.setQuestionsHindi(hindiQuestion);
                                                    data.setQuestions(englishquestion);

                                                }else {
                                                    Toast.makeText(activity, "No Question Found", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                            }
                                            else if (createTestQuesData.getData().getQuestions_hindi().size()>=createTestQuesData.getData().getData().size())
                                            {
                                                ArrayList<Question> questiondata =  createTestQuesData.getData().getQuestions_hindi();
                                                ArrayList<Question> questionnormal = new ArrayList<>();
                                                ArrayList<Question> questionhindi = new ArrayList<>();

                                                for (Question question :questiondata) {
                                                    boolean is_question=false;
                                                    for (Question question1:createTestQuesData.getData().getData())
                                                    {
                                                        if (question.getConfigId().equalsIgnoreCase(question1.getConfigId()))
                                                        {
                                                            is_question =true;
                                                            question.setIsCorrect("0");
                                                            question1.setIsCorrect("0");

                                                            questionnormal.add(question1);
                                                            questionhindi.add(question);

                                                            break;
                                                        }
                                                    }


                                                    if (!is_question)
                                                    {
                                                        question.setIsCorrect("0");
                                                        questionnormal.add(question);
                                                        questionhindi.add(question);
                                                    }
                                                }


                                                if (questionnormal.size()>0 && questionhindi.size()>0)
                                                {
                                                    data.setQuestions(questionnormal);
                                                    data.setQuestionsHindi(questionhindi);

                                                }else {
                                                    Toast.makeText(activity, "No Quiestion Found", Toast.LENGTH_SHORT).show();
                                                    return;
                                                }


                                            }
                                        }
                                    }else {
                                        if (LANG.equalsIgnoreCase("2"))
                                        {
                                            if (createTestQuesData.getData().getQuestions_hindi()!=null && createTestQuesData.getData().getQuestions_hindi().size()>0)
                                            {
                                                data.setQuestions(createTestQuesData.getData().getQuestions_hindi());

                                            }else {
                                                Toast.makeText(activity, "No Hindi Question found", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }else {
                                            if (createTestQuesData.getData().getData()!=null && createTestQuesData.getData().getData().size()>0)
                                            {
                                                data.setQuestions(createTestQuesData.getData().getData());
                                            }else {
                                                Toast.makeText(activity, "No English Question found", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    }

                                    TestseriesBase testseriesBase = new TestseriesBase();
                                    testseriesBase.setData(data);
                                    if (data.getQuestions()!=null && data.getQuestions().size()>0)
                                    {
                                        List<Questions2> questions2List = new ArrayList<>();
                                        for (Question question : data.getQuestions()) {
                                            Questions2 questions2 = new Questions2();
                                            questions2.setId(question.getId());
                                            questions2.setQuestionType(question.getQuestionType());
                                            questions2.setQuestion(question.getQuestion());
                                            questions2.setOption1(question.getOption1());
                                            questions2.setOption2(question.getOption2());
                                            questions2.setOption3(question.getOption3());
                                            questions2.setOption4(question.getOption4());
                                            questions2.setOption5(question.getOption5());
                                            questions2.setAnswer(question.getAnswer());
                                            questions2.setIsCorrect(0);
                                            questions2List.add(questions2);

                                        }
                                        data.setQuestion_response(questions2List);

                                    }else {
                                        Toast.makeText(activity, "No Question Foumd", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    List<Questions2> questions2List = new ArrayList<>();
                                    for (Question question : data.getQuestions()) {
                                        Questions2 questions2 =new Questions2();
                                        questions2.setId(question.getId());
                                        questions2.setQuestionType(question.getQuestionType());
                                        questions2.setQuestion(question.getQuestion());
                                        questions2.setOption1(question.getOption1());
                                        questions2.setOption2(question.getOption2());
                                        questions2.setOption3(question.getOption3());
                                        questions2.setOption4(question.getOption4());
                                        questions2.setOption5(question.getOption5());
                                        questions2.setAnswer(question.getAnswer());
                                        questions2.setIsCorrect(0);
                                        questions2List.add(questions2);
                                    }
                                    data.setQuestion_response(questions2List);

                                    quizView.putExtra(Const.STATUS, false);
                                    quizView.putExtra(Const.TEST_SERIES_ID, "");
                                    quizView.putExtra(Const.TEST_SERIES_Name, "My Test");

                                    MakeMyExam.object =new Gson().toJson(testseriesBase);

                                  //  quizView.putExtra(Const.TESTSERIES, testseriesBase);

                                    quizView.putExtra("TOTAL_QUESTIONS", data.getQuestions().size());
                                    quizView.putExtra("first_attempt", "1");
                                    quizView.putExtra("via", "create");
                                    quizView.putExtra(Const.LANG, Integer.parseInt(LANG));
                                    startActivity(quizView);
                                    getActivity().finish();
                                } else {
                                    Toast.makeText(activity, "No question found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                                    bottomSheetDialog.dismiss();
                                }
                                RetrofitResponse.GetApiData(getActivity(), jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                            }
                        } catch (Exception e) {
                            if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                                bottomSheetDialog.dismiss();
                            }
                            e.printStackTrace();
                        }
                    }
                }, 1000);
                break;
        }
    }

    private void setData() {
        totalQues = Integer.parseInt(questionCount.getEasy()) + Integer.parseInt(questionCount.getHard()) + Integer.parseInt(questionCount.getMedium());
        totalquestion.setText("Total Question " + String.valueOf(totalQues));
        lowTV.setText(questionCount.getEasy());
        mediumTV.setText(questionCount.getMedium());
        highTV.setText(questionCount.getHard());
        //maxTV.setText("*Maximum "+totalQues);
        maxTV.setText("*Maximum 100");
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Helper.dismissProgressDialog();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
                    bottomSheetDialog.dismiss();
                }
            }
        }, 1000);
    }

    public void createTestDialog() {
        try {
            bottomSheetDialog = new BottomSheetDialog(activity, R.style.videosheetDialogTheme);
            bottomSheetDialog.setContentView(R.layout.create_test_loader);
            Objects.requireNonNull(bottomSheetDialog.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            bottomSheetDialog.setCancelable(false);
            bottomSheetDialog.setCanceledOnTouchOutside(false);

            if (!bottomSheetDialog.isShowing()) {
                bottomSheetDialog.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        typeSpinner.setText(item.getTitle());
        if (item.getTitle().toString().equalsIgnoreCase("Low")) {
            type = "1";
            type_total_question =questionCount.getEasy();
        } else if (item.getTitle().toString().equalsIgnoreCase("Medium")) {
            type = "2";
            type_total_question =questionCount.getMedium();

        } else if (item.getTitle().toString().equalsIgnoreCase("High")) {
            type = "3";
            type_total_question =questionCount.getHard();

        } else {
            type = "0";
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinnerLL:
            case R.id.typeSpinner:
            case R.id.downarrowIV:

                ArrayList<TypeTest> typeTests = new ArrayList<>();
                typeTests.add(new TypeTest("Low", "1"));
                typeTests.add(new TypeTest("Medium", "2"));
                typeTests.add(new TypeTest("High", "3"));

                PopupMenu popupMenu = new PopupMenu(activity, typeSpinner, Gravity.LEFT);
                for (int i = 0; i < typeTests.size(); i++) {
                    popupMenu.getMenu().add(typeTests.get(i).getName());
                }
                popupMenu.setOnMenuItemClickListener(CreateTestFragmentThree.this);
                popupMenu.show();
                break;
        }
    }
}
