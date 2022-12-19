package com.utkarshnew.android.CreateTest.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utkarshnew.android.CreateTest.Activity.CreateTestActivity;
import com.utkarshnew.android.CreateTest.Model.CreateTestData;
import com.utkarshnew.android.CreateTest.Model.CreateTestSubject;
import com.utkarshnew.android.CreateTest.Model.TypeTest;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class CreateTestFragmentTwo extends MainFragment implements  View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    RecyclerView createTestRV;
    RelativeLayout parentLL;
    TextView topText;
    RelativeLayout spinnerLL;
    TextView typeSpinner;
    String type;
    RelativeLayout no_data_found_RL;
    Button backBtn;
    Button buttonProceed;
    Activity activity;
    String frag_type;
    String courseIds = "";
    ArrayList<Courselist> courselists = new ArrayList<>();
    ArrayList<CreateTestSubject> createTestSubjects = new ArrayList<>();

    public CreateTestFragmentTwo() {
        // Required empty public constructor
    }

    public static CreateTestFragmentTwo newInstance(String frag_type, ArrayList<Courselist> courselists, String LANG) {
        CreateTestFragmentTwo examPrepLayer1 = new CreateTestFragmentTwo();
        Bundle args = new Bundle();
        args.putString(Const.FRAG_TYPE, frag_type);
        args.putString(Const.LANG, LANG);
        args.putSerializable(Const.CREATE_COURSE_DATA, courselists);
        examPrepLayer1.setArguments(args);
        return examPrepLayer1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            type = getArguments().getString(Const.LANG);
            courselists = (ArrayList<Courselist>) getArguments().getSerializable(Const.CREATE_COURSE_DATA);
            List<String> myCourseIds = new ArrayList<>();
            for (Courselist courselist : courselists) {
                myCourseIds.add(courselist.getId());
            }
            courseIds = TextUtils.join(",", myCourseIds);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Helper.isNetworkConnected(activity))
                {
                    Toast.makeText(activity, "No Internet Conenction", Toast.LENGTH_SHORT).show();
                }else {
                    ArrayList<CreateTestSubject> createTestSubjectsSelected = new ArrayList<>();
                    for (CreateTestSubject createTestSubject : createTestSubjects) {
                        if (createTestSubject.isSelect()) {
                            createTestSubjectsSelected.add(createTestSubject);
                        }
                    }

                    if (createTestSubjectsSelected != null && createTestSubjectsSelected.size() > 0) {
                        Intent createCourse = new Intent(activity, CreateTestActivity.class);
                        createCourse.putExtra(Const.FRAG_TYPE, Const.CREATE_TEST_FRAG_THREE);
                        createCourse.putExtra(Const.CREATE_COURSE_SUBJECT_DATA, createTestSubjectsSelected);
                        createCourse.putExtra("courseIds", courseIds);
                        createCourse.putExtra(Const.LANG, type);
                        startActivity(createCourse);
                    } else {
                        Toast.makeText(activity, "Please select atleast one subject", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private void initView(View view) {
        spinnerLL = view.findViewById(R.id.spinnerLL);
       // spinnerLL.setOnClickListener(this);
        typeSpinner = view.findViewById(R.id.typeSpinner);

        typeSpinner.setOnClickListener(this);



        parentLL = view.findViewById(R.id.parentLL);
        topText = view.findViewById(R.id.topText);
        createTestRV = view.findViewById(R.id.createTestRV);
        buttonProceed = view.findViewById(R.id.buttonProceed);
        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        RefreshDataList();
    }

    private void RefreshDataList() {
        Helper.showProgressDialog(activity);
        NetworkAPICall(API.API_CREATE_TEST_GET_SUBJECT, "", false, false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_test_two, container, false);

    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_CREATE_TEST_GET_SUBJECT:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setCourse_ids(courseIds);
                getcoursedataencryptionData.setType(type);
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.API_CREATE_TEST_GET_SUBJECT(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_CREATE_TEST_GET_SUBJECT:
                try {
                    Helper.dismissProgressDialog();
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        CreateTestData createTestData = new Gson().fromJson(jsonobject.toString(), CreateTestData.class);
                        if (createTestData.getData().size() > 0) {
                            createTestSubjects = new ArrayList<>();
                            createTestSubjects.clear();

                            createTestRV.setVisibility(View.VISIBLE);
                            no_data_found_RL.setVisibility(View.GONE);

                            createTestSubjects.addAll(createTestData.getData());
                            createTestRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                            createTestRV.setAdapter(new SubjectListRecyclerAdapter(createTestSubjects, courselists));
                            createTestRV.setNestedScrollingEnabled(false);

                        } else {
                            createTestRV.setVisibility(View.GONE);
                            no_data_found_RL.setVisibility(View.VISIBLE);
                        }
                    } else {
                        createTestRV.setVisibility(View.GONE);
                        no_data_found_RL.setVisibility(View.VISIBLE);
                        RetrofitResponse.GetApiData(getActivity(), jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Helper.dismissProgressDialog();

        if (createTestRV != null && no_data_found_RL != null) {
            createTestRV.setVisibility(View.GONE);
            no_data_found_RL.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        typeSpinner.setText(item.getTitle());
        if (item.getTitle().toString().equalsIgnoreCase("English")) {
            type = "1";
        } else if (item.getTitle().toString().equalsIgnoreCase("Hindi")) {
            type = "2";

        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinnerLL:
            case R.id.typeSpinner:
                ArrayList<TypeTest> typeTests = new ArrayList<>();
                typeTests.add(new TypeTest("English", "1"));
                typeTests.add(new TypeTest("Hindi", "2"));

                PopupMenu popupMenu = new PopupMenu(activity, typeSpinner, Gravity.LEFT);
                for (int i = 0; i < typeTests.size(); i++) {
                    popupMenu.getMenu().add(typeTests.get(i).getName());
                }
                popupMenu.setOnMenuItemClickListener(CreateTestFragmentTwo.this);
                popupMenu.show();
                break;
        }
    }


    public class SubjectListRecyclerAdapter extends RecyclerView.Adapter<SubjectListRecyclerAdapter.SubjectListHolder> {
        ArrayList<CreateTestSubject> createTestSubjects;
        ArrayList<Courselist> courselists;

        public SubjectListRecyclerAdapter(ArrayList<CreateTestSubject> createTestSubjects, ArrayList<Courselist> courselists) {
            this.courselists = courselists;
            this.createTestSubjects = createTestSubjects;
        }

        @NonNull
        @Override
        public SubjectListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_subject_data, parent, false);
            return new SubjectListHolder(view);
        }

        @Override
        public int getItemCount() {
            return courselists.size();
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectListHolder holder, int position) {
            holder.setSingleFAQData(courselists.get(position), courselists.get(position).getTitle(), position);
        }

        public class SubjectListHolder extends RecyclerView.ViewHolder {
            private TextView questiontextTV;
            private ImageView dropDownIV;
            private LinearLayout mainLL, parentLL;
            private RecyclerView subjectRV;

            public SubjectListHolder(View itemView) {
                super(itemView);
                questiontextTV = (TextView) itemView.findViewById(R.id.questiontextTV);
                dropDownIV = (ImageView) itemView.findViewById(R.id.dropDownIV);
                subjectRV = (RecyclerView) itemView.findViewById(R.id.subjectRV);
                mainLL = itemView.findViewById(R.id.lowerViewItem);
                parentLL = itemView.findViewById(R.id.parentLL);
            }

            public void setSingleFAQData(Courselist courselists, String singlefaqdata, int pos) {

                if (courselists.isExpand()) {
                    mainLL.setVisibility(View.VISIBLE);
                    dropDownIV.setImageResource(R.mipmap.up_black);
                } else {
                    mainLL.setVisibility(View.GONE);
                    dropDownIV.setImageResource(R.mipmap.down_black);
                }

                parentLL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (courselists.isExpand()) {
                            courselists.setExpand(false);
                            mainLL.setVisibility(View.GONE);
                            dropDownIV.setImageResource(R.mipmap.down_black);
                        } else {
                            courselists.setExpand(true);
                            mainLL.setVisibility(View.VISIBLE);
                            dropDownIV.setImageResource(R.mipmap.up_black);
                        }
                    }
                });

                questiontextTV.setText(singlefaqdata);
                subjectRV.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                subjectRV.setAdapter(new SubjectItemRecyclerAdapter(createTestSubjects, SubjectListRecyclerAdapter.this.courselists.get(pos).getId()));
                subjectRV.setNestedScrollingEnabled(false);
            }
        }
    }

    public class SubjectItemRecyclerAdapter extends RecyclerView.Adapter<SubjectItemRecyclerAdapter.SubjectItemHolder> {
        ArrayList<CreateTestSubject> createTestSubjects;
        ArrayList<CreateTestSubject> createTestSubjectsFinal = new ArrayList<>();
        String selectedCourseID;

        public SubjectItemRecyclerAdapter(ArrayList<CreateTestSubject> createTestSubjects, String selectedCourseID) {
            this.createTestSubjects = createTestSubjects;
            this.selectedCourseID = selectedCourseID;

            for (CreateTestSubject createTestSubject : createTestSubjects) {
                if (selectedCourseID.equalsIgnoreCase(createTestSubject.getCourseId())) {
                    createTestSubjectsFinal.add(createTestSubject);
                }
            }
        }

        @NonNull
        @Override
        public SubjectItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_subject_item, parent, false);
            return new SubjectItemHolder(view);
        }

        @Override
        public int getItemCount() {
            return createTestSubjectsFinal.size();
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectItemHolder holder, int position) {
            holder.setSingleFAQData(createTestSubjectsFinal, position);
        }

        public class SubjectItemHolder extends RecyclerView.ViewHolder {
            private TextView questiontextTV;
            private CheckBox selectCB;
            private LinearLayout parentLL;

            public SubjectItemHolder(View itemView) {
                super(itemView);
                questiontextTV = (TextView) itemView.findViewById(R.id.questiontextTV);
                selectCB = (CheckBox) itemView.findViewById(R.id.selectCB);
                parentLL = itemView.findViewById(R.id.parentLL);
            }

            public void setSingleFAQData(ArrayList<CreateTestSubject> createTestSubjectsFinal, int pos) {
                CreateTestSubject createTestSubject = createTestSubjectsFinal.get(pos);
                questiontextTV.setText(pos + 1 + ". " + ((TextUtils.isEmpty(createTestSubject.getTitle())) ? "N/A" : createTestSubject.getTitle()));

                if (createTestSubject.isSelect()) {
                    selectCB.setChecked(true);
                } else {
                    selectCB.setChecked(false);
                }

                selectCB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (createTestSubject.isSelect()) {
                            createTestSubject.setSelect(false);
                        } else {
                            createTestSubject.setSelect(true);
                        }
                        // notifyDataSetChanged();

                        checkProceedButton(buttonProceed);
                    }
                });
            }
        }

        private void checkProceedButton(Button buttonProceed) {
            boolean changeButton = false;
            for (CreateTestSubject createTestSubject : createTestSubjects) {
                if (createTestSubject.isSelect()) {
                    changeButton = true;
                    break;
                }
            }

            ArrayList<CreateTestSubject> createTestSubjectsSelected = new ArrayList<>();
            for (CreateTestSubject createTestSubject : createTestSubjects) {
                if (createTestSubject.isSelect()) {
                    createTestSubjectsSelected.add(createTestSubject);
                }
            }

            if (createTestSubjectsSelected.size() > 0) {
                topText.setText(createTestSubjectsSelected.size() + " Chapters Selected");
            } else {
                topText.setText("Select single or multiple Chapter(S)");
            }

            if (changeButton) {
                buttonProceed.setBackground(activity.getResources().getDrawable(R.drawable.bg_round_corners_button));
            } else {
                buttonProceed.setBackground(activity.getResources().getDrawable(R.drawable.bg_round_corners_button_fade));
            }
        }
    }
}
