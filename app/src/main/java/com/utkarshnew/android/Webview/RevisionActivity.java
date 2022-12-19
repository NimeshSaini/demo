package com.utkarshnew.android.Webview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.Model.FlashData;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Webview.Model.FlashDataDescription;
import com.utkarshnew.android.Webview.Model.FlashDataTerm;
import com.utkarshnew.android.testmodule.model.Data;
import com.utkarshnew.android.testmodule.model.Question;
import com.utkarshnew.android.testmodule.model.Questions2;
import com.utkarshnew.android.testmodule.model.TestBasic;
import com.utkarshnew.android.testmodule.model.TestseriesBase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import retrofit2.Call;

public class RevisionActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    private WebView webView;
    TextView add_new_terms_edittext, add_new_description;
    private ImageView image_back;

    TextView add_revision_dialog, toolbarTitleTV;
    long mLastClickTime_frame5;
    int count = 0;
    JSONArray array = new JSONArray();
    private JSONObject jsonObject = new JSONObject();
    private String tile_id, f_id, course_id, video_id = "";
    private ArrayList<Video> seleced_tile_list = new ArrayList<>();
    private ArrayList<Video> videoArrayList = new ArrayList<>();

    private RelativeLayout question_layout;
    private TextView addonother, save_revision;
    private LinearLayout frame;
    EditText terms_edittext, description_edittext;
    private String title, url = "";
    private String via;
    int postion;
    CardView flashCards, mcqCards;
    View holderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.revisin_activity);
        try {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

            add_revision_dialog = findViewById(R.id.add_revision_dialog);
            flashCards = findViewById(R.id.flashCards);
            holderView = findViewById(R.id.holderView);
            mcqCards = findViewById(R.id.mcqCards);
            toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
            frame = findViewById(R.id.frame);
            image_back = findViewById(R.id.image_back);
            addonother = findViewById(R.id.add_aonther);
            terms_edittext = findViewById(R.id.terms_edittext);
            description_edittext = findViewById(R.id.description_edittext);
            save_revision = findViewById(R.id.save_revision);
            question_layout = findViewById(R.id.question_layout);
            LayoutInflater layoutInfralte = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            tile_id = getIntent().getStringExtra("t_id");
            postion = getIntent().getIntExtra("postion", 0);
            video_id = getIntent().getStringExtra("video_id");
            video_id = video_id == null ? "" : video_id;
            videoArrayList = (ArrayList<Video>) getIntent().getSerializableExtra("v_list");
            Log.d("prince", "" + videoArrayList.size());
            Log.d("prince", "" + postion);
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            via = getIntent().getStringExtra("via");

            if (url != null && !url.equalsIgnoreCase("")) {
                if (via != null && via.equalsIgnoreCase("Notes")) {
                    flashCards.setVisibility(View.GONE);
                    holderView.setVisibility(View.GONE);
                    mcqCards.setVisibility(View.GONE);
                } else {
                    flashCards.setVisibility(View.VISIBLE);
                    holderView.setVisibility(View.VISIBLE);
                    mcqCards.setVisibility(View.VISIBLE);
                }

                add_revision_dialog.setVisibility(View.GONE);
                question_layout.setVisibility(View.VISIBLE);
                frame.setVisibility(View.VISIBLE);
                addonother.setVisibility(View.VISIBLE);
                save_revision.setVisibility(View.VISIBLE);
                JSONArray jsonArray = new JSONArray(url);

                toolbarTitleTV.setText(title);
                for (int i = 0; i < jsonArray.length(); i++) {
                    if (i == 0) {
                        JSONObject actor = jsonArray.getJSONObject(i);
                        String terms = actor.getString("terms");
                        String description = actor.getString("description");
                        terms_edittext.setText(terms);
                        description_edittext.setText(description);

                        jsonObject.put("terms", terms_edittext.getText().toString());
                        jsonObject.put("description", description_edittext.getText().toString());
                        jsonObject.put("Q_id", actor.getString("Q_id"));


                        array.put(jsonObject);

                    } else if (i > 0) {
                        count++;
                        View view1 = layoutInfralte.inflate(R.layout.addonther_view, frame, false);
                        JSONObject actor = jsonArray.getJSONObject(i);
                        String terms = actor.getString("terms");
                        String description = actor.getString("description");
                        add_new_terms_edittext = view1.findViewById(R.id.terms_edittext);
                        add_new_terms_edittext.setTag(R.id.terms_edittext, count);
                        add_new_description = view1.findViewById(R.id.description);
                        add_new_description.setTag(R.id.description, count);
                        add_new_terms_edittext.setText(terms);
                        add_new_description.setText(description);
                        frame.setOrientation(LinearLayout.VERTICAL);
                        frame.addView(view1);
                    }


                }

            }
            image_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            course_id = getIntent().getStringExtra("c_id");
            f_id = getIntent().getStringExtra("f_id");

            add_revision_dialog.setOnClickListener(v ->
                    show_revision_bottomsheet_popup()
            );

            addonother.setOnClickListener(v -> {
                count++;
                View view1 = layoutInfralte.inflate(R.layout.addonther_view, frame, false);
                add_new_terms_edittext = view1.findViewById(R.id.terms_edittext);
                add_new_terms_edittext.setTag(R.id.terms_edittext, count);
                add_new_description = view1.findViewById(R.id.description);
                add_new_description.setTag(R.id.description, count);
                frame.setOrientation(LinearLayout.VERTICAL);
                frame.addView(view1);
            });

            flashCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent(RevisionActivity.this, FlashcardActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            mcqCards.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        List<Question> questions = new ArrayList<>();
                        List<Questions2> questions2s = new ArrayList<>();
                        JSONArray jsonArray;
                        jsonArray = new JSONArray(url);


                        ArrayList<FlashDataTerm> flashDataTerms = new ArrayList<>();
                        ArrayList<FlashDataDescription> flashDataDescriptions = new ArrayList<>();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            FlashData flashData = new Gson().fromJson(jsonArray.get(i).toString(), FlashData.class);
                            flashDataTerms.add(new FlashDataTerm(flashData.getTerms(), flashData.getQ_id()));
                            flashDataDescriptions.add(new FlashDataDescription(flashData.getDescription(), flashData.getQ_id()));
                        }

                        for (FlashDataDescription flashDataDescription : flashDataDescriptions) {
                            List<String> optionList = new ArrayList<>();
                            String optionStr = "";
                            for (FlashDataTerm flashDataTerm : flashDataTerms) {
                                if (flashDataTerm.getQ_id().equalsIgnoreCase(flashDataDescription.getQ_id())) {
                                    optionList.add(0, flashDataTerm.getTerms());
                                } else {
                                    optionList.add(flashDataTerm.getTerms());
                                }
                                optionStr = TextUtils.join(",", optionList);
                            }
                            Question question = new Question();
                            Questions2 questions2 = new Questions2();


                            String options[] = optionStr.split(",");
                            question.setRight_answer(options[0].trim());
                            questions2.setRight_answer(options[0].trim());

                            ArrayList<String> optionstring = new ArrayList<>();
                            for (int l = 0; l < options.length; l++) {
                                optionstring.add(options[l]);
                                if (optionstring.size() > 3) {
                                    break;
                                }
                            }
                            Collections.shuffle(optionstring);
                            for (int k = 0; k < optionstring.size(); k++) {
                                if (question.getOption1() == null || question.getOption1().isEmpty()) {
                                    question.setOption1(optionstring.get(k));
                                    questions2.setOption1(optionstring.get(k));
                                } else if (question.getOption2() == null || question.getOption2().isEmpty()) {
                                    question.setOption2(optionstring.get(k));
                                    questions2.setOption2(optionstring.get(k));

                                } else if (question.getOption3() == null || question.getOption3().isEmpty()) {
                                    question.setOption3(optionstring.get(k));
                                    questions2.setOption3(optionstring.get(k));

                                } else if (question.getOption4() == null || question.getOption4().isEmpty()) {
                                    question.setOption4(optionstring.get(k));
                                    questions2.setOption3(optionstring.get(k));

                                    break;
                                }
                            }
                            question.setQuestion(flashDataDescription.getDescription());
                            question.setQuestionType("SC");
                            question.setId(flashDataDescription.getQ_id());
                            questions.add(question);
                            questions2.setQuestion(flashDataDescription.getDescription());
                            questions2.setQuestionType("SC");
                            questions2.setId(flashDataDescription.getQ_id());
                            questions2s.add(questions2);
                        }


                        if (questions.size() > 0) {
                            Intent quizView = new Intent(RevisionActivity.this, RevisionTest.class);
                            Data data = new Data();
                            TestBasic testBasic = new TestBasic();
                            testBasic.setTestSeriesName(title);
                            data.setTestBasic(testBasic);
                            data.setQuestions(questions);
                            data.setQuestion_response(questions2s);
                            TestseriesBase testseriesBase = new TestseriesBase();
                            testseriesBase.setData(data);
                            quizView.putExtra(Const.STATUS, false);
                            quizView.putExtra(Const.TEST_SERIES_ID, tile_id);
                            quizView.putExtra(Const.TEST_SERIES_Name, title);
                            quizView.putExtra(Const.TESTSERIES, testseriesBase);
                            quizView.putExtra("TOTAL_QUESTIONS", questions.size());
                            quizView.putExtra("first_attempt", "1");
                            quizView.putExtra(Const.LANG, "1");
                            startActivity(quizView);

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                  /*
                    Intent intent =new Intent(RevisionActivity.this,FlashcardActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);*/
                }
            });


            save_revision.setOnClickListener(new OnSingleClickListener(() -> {

                try {
                    if (array.length() == 0) {
                        array = new JSONArray();
                        if (terms_edittext.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(this, "Enter terms", Toast.LENGTH_SHORT).show();
                        } else if (description_edittext.getText().toString().equalsIgnoreCase(""))
                            Toast.makeText(this, "Enter description", Toast.LENGTH_SHORT).show();
                        else {
                            if (count >= 1) {
                                jsonObject.put("terms", terms_edittext.getText().toString());
                                jsonObject.put("description", description_edittext.getText().toString());
                                jsonObject.put("Q_id", "" + gen());
                                Log.d("prince", "" + "" + gen());
                                array.put(jsonObject);
                                get_allrevision(frame);

                            } else {
                                Toast.makeText(this, "At least two revision must be added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (terms_edittext.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(this, "Enter terms", Toast.LENGTH_SHORT).show();
                        } else if (description_edittext.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(this, "Enter description", Toast.LENGTH_SHORT).show();

                        } else {
                            if (via.equalsIgnoreCase("main")) {
                                array.remove(0);
                                jsonObject.put("terms", terms_edittext.getText().toString());
                                jsonObject.put("description", description_edittext.getText().toString());
                                jsonObject.put("Q_id", "" + gen());
                                Log.d("prince", "" + "" + gen());
                                array.put(jsonObject);
                                get_allrevision(frame);
                            } else {
                                show_revision_bottomsheet_popup();
                            }
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void show_revision_bottomsheet_popup() {
        try {
            BottomSheetDialog watchlist;
            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();

            watchlist = new BottomSheetDialog(RevisionActivity.this, R.style.videosheetDialogTheme);
            watchlist.setContentView(R.layout.revision_dialog);
            watchlist.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(false);
            watchlist.setCanceledOnTouchOutside(false);
            RelativeLayout calcpager = watchlist.findViewById(R.id.calcpagerl);
            TextView cancel = watchlist.findViewById(R.id.btn__cancel);
            EditText revision_title = watchlist.findViewById(R.id.writeCouponET);
            TextView submit = watchlist.findViewById(R.id.btn_submit);

            submit.setOnClickListener(v -> {
                if (!revision_title.getText().toString().trim().equalsIgnoreCase("")) {
                    toolbarTitleTV.setText(revision_title.getText().toString().trim());
                    if (via.equalsIgnoreCase("notes")) {
                        try {
                            dismissCalculatorDialog(watchlist);

                            array.remove(0);
                            jsonObject.put("terms", terms_edittext.getText().toString());
                            jsonObject.put("description", description_edittext.getText().toString());
                            jsonObject.put("Q_id", "" + gen());
                            array.put(jsonObject);
                            get_allrevision(frame);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        add_revision_dialog.setVisibility(View.GONE);
                        addonother.setVisibility(View.VISIBLE);
                        save_revision.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.VISIBLE);
                        question_layout.setVisibility(View.VISIBLE);
                        dismissCalculatorDialog(watchlist);

                    }

                } else {
                    Toast.makeText(RevisionActivity.this, "Enter revision title", Toast.LENGTH_SHORT).show();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissCalculatorDialog(watchlist);
                }
            });

            Objects.requireNonNull(calcpager).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissCalculatorDialog(watchlist);
                }
            });
            watchlist.show();
            watchlist.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    watchlist.dismiss();
                    watchlist.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void get_allrevision(LinearLayout linearLayout) {
        try {
            boolean valid = false;
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                View child = linearLayout.getChildAt(i);
                JSONObject jsonObject = new JSONObject();

                if (child instanceof RelativeLayout) {
                    View view = ((RelativeLayout) child).getChildAt(1);
                    View view1 = ((RelativeLayout) child).getChildAt(3);
                    if (view instanceof EditText) {
                        AppCompatEditText textView = (AppCompatEditText) ((RelativeLayout) child).getChildAt(1);
                        if (textView.getText().toString().trim().equalsIgnoreCase("")) {
                            valid = false;
                            Toast.makeText(this, "Enter terms", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            valid = true;
                            jsonObject.put("terms", textView.getText().toString());
                        }
                    }
                    if (view1 instanceof EditText) {
                        AppCompatEditText textView = (AppCompatEditText) ((RelativeLayout) child).getChildAt(3);
                        if (textView.getText().toString().trim().equalsIgnoreCase("")) {
                            valid = false;
                            Toast.makeText(this, "Enter description", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            valid = true;
                            jsonObject.put("description", textView.getText().toString());
                            jsonObject.put("Q_id", gen());
                            Log.d("prince", "" + "" + gen());
                            array.put(jsonObject);
                        }
                    }
                }
            }
            if (valid) {
                NetworkCall networkCall = new NetworkCall(this, this);
                networkCall.NetworkAPICall(API.video_link, "", true, false);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void show_revison_dialog() {
        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.revision_dialog);
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (this.getResources().getDisplayMetrics().widthPixels * 0.90);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            RelativeLayout calcpager = dialog.findViewById(R.id.calcpagerl);
            TextView cancel = dialog.findViewById(R.id.btn__cancel);
            EditText revision_title = dialog.findViewById(R.id.writeCouponET);
            TextView submit = dialog.findViewById(R.id.btn_submit);

            submit.setOnClickListener(v -> {
                if (!revision_title.getText().toString().trim().equalsIgnoreCase("")) {
                    toolbarTitleTV.setText(revision_title.getText().toString().trim());
                    if (via.equalsIgnoreCase("notes")) {
                        try {
                            dismissCalculatorDialog(dialog);

                            array.remove(0);
                            jsonObject.put("terms", terms_edittext.getText().toString());
                            jsonObject.put("description", description_edittext.getText().toString());
                            jsonObject.put("Q_id", "" + gen());
                            array.put(jsonObject);
                            get_allrevision(frame);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        add_revision_dialog.setVisibility(View.GONE);
                        addonother.setVisibility(View.VISIBLE);
                        save_revision.setVisibility(View.VISIBLE);
                        frame.setVisibility(View.VISIBLE);
                        question_layout.setVisibility(View.VISIBLE);
                        dismissCalculatorDialog(dialog);

                    }

                } else {
                    Toast.makeText(RevisionActivity.this, "Enter revision title", Toast.LENGTH_SHORT).show();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissCalculatorDialog(dialog);
                }
            });

            Objects.requireNonNull(calcpager).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissCalculatorDialog(dialog);
                }
            });
            dialog.show();
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    dialog.dismiss();
                    dialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void dismissCalculatorDialog(Dialog watchlist) {
        if (watchlist != null && watchlist.isShowing()) {
            watchlist.dismiss();
            watchlist.cancel();
            watchlist = null;
        }
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.video_link:
                EncryptionData masterdataencryptionData = new EncryptionData();
                masterdataencryptionData.setId(video_id);
                masterdataencryptionData.setCourse_id(course_id);
                masterdataencryptionData.setType("3");
                masterdataencryptionData.setFile_id(f_id);
                masterdataencryptionData.setTile_id(tile_id);
                masterdataencryptionData.setLink(array.toString());
                masterdataencryptionData.setTitle(toolbarTitleTV.getText().toString().trim());
                String masterdataencryptionDatadoseStr = new Gson().toJson(masterdataencryptionData);
                String masterdatadoseStrScr = AES.encrypt(masterdataencryptionDatadoseStr);
                return service.video_link(masterdatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

        switch (apitype) {
            case API.video_link:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        Constants.revison_set = true;
                        Video video = new Gson().fromJson(jsonstring.optJSONObject(Const.DATA).toString(), Video.class);

                        if (via.equalsIgnoreCase("main")) {
                            if (video_id.equalsIgnoreCase("")) {
                                videoArrayList.add(videoArrayList.size(), video);
                                Log.d("prince", "" + videoArrayList.size());
                            } else {
                                videoArrayList.set(postion, video);
                            }
                            MakeMyExam.videoArrayList = videoArrayList;
                            Toast.makeText(this, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            if (video_id.equalsIgnoreCase("")) {
                                video_id = video.getId();
                                videoArrayList.add(videoArrayList.size(), video);
                            } else {
                                videoArrayList.set(videoArrayList.size() - 1, video);
                            }
                            MakeMyExam.videoArrayList = videoArrayList;
                            url = video.getFile_url();
                            Toast.makeText(this, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                            flashCards.setVisibility(View.VISIBLE);
                            holderView.setVisibility(View.VISIBLE);
                            mcqCards.setVisibility(View.VISIBLE);
                        }


                    } else {
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception ex) {
                    this.ErrorCallBack(ex.getMessage() + " : " + ex.getLocalizedMessage(), apitype, typeApi);
                    ex.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        // Toast.makeText(this, ""+jsonstring, Toast.LENGTH_SHORT).show();
        //   this.ErrorCallBack(jsonstring, apitype, typeApi);

    }

    public int gen() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}