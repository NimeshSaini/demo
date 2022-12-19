package com.utkarshnew.android.CourseTransfer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.model.MyCourse;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class CourseTransferActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {


    RecyclerView course_name_recycler;
    private ImageView image_back, refresh;
    Button buttonProceed;
    RelativeLayout no_data_found_RL;

    RelativeLayout proceed_layout;
    LinearLayoutManager linearLayoutManager;
    CourseSelectionAdapter courseSelectionAdapter;
    EditText m_no;
    private MyCourse myCourse;
    NetworkCall networkCall;
    Dialog dialog;
    private long mLastClickTime_frame5;
    private Button backBtn;
    ArrayList<Courselist> course_list = new ArrayList<>();
    private ArrayList<Courselist> noteDataArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_course_treansfer);

        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
                course_name_recycler = findViewById(R.id.course_name_recycler);

                image_back = findViewById(R.id.image_back);
                refresh = findViewById(R.id.refresh);
                no_data_found_RL = findViewById(R.id.no_data_found_RL);
                backBtn = findViewById(R.id.backBtn);
                buttonProceed = findViewById(R.id.buttonProceed);

                proceed_layout = findViewById(R.id.proceed);

                m_no = findViewById(R.id.m_no);
                linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                course_name_recycler.setLayoutManager(linearLayoutManager);
                networkCall = new NetworkCall(this, this);
                networkCall.NetworkAPICall(API.get_my_courses, "", true, false);

                buttonProceed.setOnClickListener(v -> {
                    if (buttonProceed.getText().toString().equalsIgnoreCase("Course Transfer")) {
                        noteDataArrayList = new ArrayList<>();
                        for (Courselist noteData : course_list) {
                            if (noteData.isSelect()) {
                                try {
                                    noteDataArrayList.add(noteData);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (noteDataArrayList != null && noteDataArrayList.size() > 0) {
                            alert_dialog();
                        } else {
                            Toast.makeText(CourseTransferActivity.this, "Please select atleast one course", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (m_no.getText().toString().length() == 10) {
                            if (m_no.getText().toString().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getMobile())) {
                                Snackbar.make(v, "Sorry, You  can't tranfer the course to your own number", Snackbar.LENGTH_SHORT).show();
                            } else {
                                networkCall.NetworkAPICall(API.fetch_user, "", true, false);
                            }
                        } else {
                            Snackbar.make(v, "Please enter valid mobile number", Snackbar.LENGTH_SHORT).show();

                        }
                    }
                });

                image_back.setOnClickListener(new OnSingleClickListener(() -> {
                    Helper.backButtonClick(CourseTransferActivity.this);
                    return null;
                }));
                backBtn.setOnClickListener(new OnSingleClickListener(() -> {
                    Helper.backButtonClick(CourseTransferActivity.this);
                    return null;
                }));
                refresh.setOnClickListener(v -> {
                    if (course_list.size() > 0) {
                        m_no.setEnabled(true);
                        m_no.setText("");
                        for (Courselist course_list : course_list) {
                            course_list.setSelect(false);
                        }
                        refresh.setVisibility(View.GONE);
                        buttonProceed.setText("Fetch user");
                        no_data_found_RL.setVisibility(View.GONE);
                        course_name_recycler.setVisibility(View.GONE);
                        buttonProceed.setBackground(getResources().getDrawable(R.drawable.bg_round_corners_button));
                    } else {
                        no_data_found_RL.setVisibility(View.VISIBLE);
                        course_name_recycler.setVisibility(View.GONE);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void alert_dialog() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Course Transfer");
        alertDialogBuilder.setMessage("Are you sure, you want to transfer \nthe selected course to below Mobile Number?:- " + m_no.getText().toString() + "?");
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
            networkCall.NetworkAPICall(API.transfer_course, "", true, false);

            dialog.dismiss();
            dialog.cancel();
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void showCouponCodePopup(JSONObject jsonObject) {
        try {

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();
            dialog = new Dialog(CourseTransferActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.monile_number_detlais);
            dialog.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            TextView cancel = dialog.findViewById(R.id.btn__cancel);
            TextView user_name = dialog.findViewById(R.id.user_name);
            TextView user_email = dialog.findViewById(R.id.user_email);
            TextView user_mobile = dialog.findViewById(R.id.user_mobile);
            CircleImageView circleImageView = dialog.findViewById(R.id.profileImage);
            TextView submit = dialog.findViewById(R.id.btn_submit);

            user_name.setText(jsonObject.optString("name"));
            user_email.setText(jsonObject.optString("email"));
            user_mobile.setText(m_no.getText().toString());
            Glide.with(CourseTransferActivity.this).load(jsonObject.optString("profile_picture")).apply(new RequestOptions().placeholder(R.mipmap.course_placeholder))
                    .into(circleImageView);

            submit.setOnClickListener(v -> {
                dismissCalculatorDialog(dialog);
                refresh.setVisibility(View.VISIBLE);
                m_no.setEnabled(false);
                m_no.setTag(jsonObject.optString("id"));
                course_name_recycler.setVisibility(View.VISIBLE);
                buttonProceed.setText("Course Transfer");
                courseSelectionAdapter = new CourseSelectionAdapter(course_list);
                course_name_recycler.setAdapter(courseSelectionAdapter);
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissCalculatorDialog(dialog);
                }
            });

         /*   Objects.requireNonNull(calcpager).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissCalculatorDialog(dialog);
                }
            });*/

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
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_my_courses:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setIs_paid("1");
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.get_my_courses(getcoursedatadoseStrScr);

            case API.fetch_user:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setMobile(m_no.getText().toString());
                String encryptionData_json = new Gson().toJson(encryptionData);
                String encryptionData_encrypot = AES.encrypt(encryptionData_json);
                return service.fetch_user(encryptionData_encrypot);

            case API.transfer_course:
                List<String> mStrings = new ArrayList<>();

                for (int i = 0; i < noteDataArrayList.size(); i++) {
                    mStrings.add(noteDataArrayList.get(i).getTxn_id());
                }
                String[] strings = new String[mStrings.size()];
                strings = mStrings.toArray(strings);

                EncryptionData data = new EncryptionData();
                data.setTxn_ids(strings);
                data.setTransfer_to_id(m_no.getTag().toString());
                data.setTransfer_to_mobile(m_no.getText().toString());
                data.setResend("0");
                data.setOtp("");
                String data_json = new Gson().toJson(data);
                String encryptiondata_encrypot = AES.encrypt(data_json);
                return service.transfer_course(encryptiondata_encrypot);

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.get_my_courses:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        myCourse = new Gson().fromJson(jsonstring.toString(), MyCourse.class);
                        if (myCourse.getData().size() > 0) {
                            course_list = new ArrayList<>();
                            course_list.addAll(myCourse.getData());
                            if (course_list.size() > 0) {
                                no_data_found_RL.setVisibility(View.GONE);
                                course_name_recycler.setVisibility(View.GONE);
                                buttonProceed.setBackground(getResources().getDrawable(R.drawable.bg_round_corners_button));

                            } else {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                course_name_recycler.setVisibility(View.GONE);
                                m_no.setEnabled(false);
                                buttonProceed.setEnabled(false);
                            }
                        }
                    } else {
                        no_data_found_RL.setVisibility(View.VISIBLE);
                        course_name_recycler.setVisibility(View.GONE);
                        m_no.setEnabled(false);
                        buttonProceed.setEnabled(false);
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.fetch_user:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONArray dataArray = jsonstring.getJSONArray(Const.DATA);
                        if (dataArray.length() > 0)
                            showCouponCodePopup(dataArray.getJSONObject(0));
                    } else {
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case API.transfer_course:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        //Toast.makeText(this, ""+jsonstring.optString(Const.MESSAGE) ,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, OtpActivity.class);

                        List<String> mStrings = new ArrayList<>();
                        List<String> course_id = new ArrayList<>();

                        for (int i = 0; i < noteDataArrayList.size(); i++) {

                            mStrings.add(noteDataArrayList.get(i).getTxn_id());
                            course_id.add(noteDataArrayList.get(i).getId());

                        }
                        String[] strings = new String[mStrings.size()];

                        String[] c_ids = new String[course_id.size()];

                        strings = mStrings.toArray(strings);

                        c_ids = course_id.toArray(c_ids);


                        intent.putExtra("coursestring", strings);
                        intent.putExtra("Transfer_to_id", m_no.getTag().toString());
                        intent.putExtra("Transfer_to_mobile", m_no.getText().toString());
                        intent.putExtra("c_ids", c_ids);

                        Helper.gotoActivity_finish(intent, this);


                    } else {
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    public class CourseSelectionAdapter extends RecyclerView.Adapter<CourseSelectionAdapter.SubjectItemHolder> {
        ArrayList<Courselist> myNotesArrayList;

        public CourseSelectionAdapter(ArrayList<Courselist> myNotesArrayList) {
            this.myNotesArrayList = myNotesArrayList;
        }

        @NonNull
        @Override
        public CourseSelectionAdapter.SubjectItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_selection, parent, false);
            return new CourseSelectionAdapter.SubjectItemHolder(view);
        }

        @Override
        public int getItemCount() {
            return myNotesArrayList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull CourseSelectionAdapter.SubjectItemHolder holder, int position) {
            holder.setSingleFAQData(myNotesArrayList, position);
        }

        public class SubjectItemHolder extends RecyclerView.ViewHolder {
            private final TextView questiontextTV;
            private final CheckBox selectCB;
            private final LinearLayout parentLL;

            public SubjectItemHolder(View itemView) {
                super(itemView);
                questiontextTV = itemView.findViewById(R.id.questiontextTV);
                selectCB = itemView.findViewById(R.id.selectCB);
                parentLL = itemView.findViewById(R.id.parentLL);
            }

            @SuppressLint("SetTextI18n")
            public void setSingleFAQData(ArrayList<Courselist> createTestSubjectsFinal, int pos) {
                Courselist noteData = createTestSubjectsFinal.get(pos);
                questiontextTV.setText(pos + 1 + ". " + noteData.getTitle());
                if (noteData.isSelect()) {
                    selectCB.setChecked(true);
                } else {
                    selectCB.setChecked(false);
                }
                selectCB.setOnClickListener(view -> {
                    noteData.setSelect(!noteData.isSelect());
                    checkProceedButton();
                });
            }
        }

        private void checkProceedButton() {
            for (Courselist noteData : myNotesArrayList) {
                if (noteData.isSelect()) {
                    break;
                }
            }

        }
    }


}