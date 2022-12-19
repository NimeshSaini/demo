package com.utkarshnew.android.CreateTest.Fragment;

import static com.utkarshnew.android.Utils.HelperProgress.getScreenWidth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.utkarshnew.android.CreateTest.Activity.CreateTestActivity;
import com.utkarshnew.android.CreateTest.Model.TypeTest;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.model.MyCourse;
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

import retrofit2.Call;

public class CreateTestFragmentOne extends MainFragment implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    RecyclerView createTestRV;
    RelativeLayout parentLL;
    RelativeLayout btnpcd;
    RelativeLayout no_data_found_RL;
    Button backBtn;
    TextView maxCount;
    TextView topText;
    Button buttonProceed;
    Activity activity;
    String frag_type;
    private int pagecount = 1;
    ArrayList<Courselist> courselists = new ArrayList<>();
    TileDataAdapter tileDataAdapter;

    RelativeLayout spinnerLL;
    TextView typeSpinner;
    String type;
    public CreateTestFragmentOne() {
        // Required empty public p
    }

    public static CreateTestFragmentOne newInstance(String frag_type) {
        CreateTestFragmentOne examPrepLayer1 = new CreateTestFragmentOne();
        Bundle args = new Bundle();
        args.putString(Const.FRAG_TYPE, frag_type);
        examPrepLayer1.setArguments(args);
        return examPrepLayer1;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==null || type.equalsIgnoreCase(""))
                {
                    Toast.makeText(activity, "Please select language first", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<Courselist> courselistsSelected = new ArrayList<>();
                    for (Courselist courselist : courselists) {
                        if (courselist.isSelect()) {
                            courselistsSelected.add(courselist);
                        }
                    }

                    if (courselistsSelected != null && courselistsSelected.size() > 0) {
                        Intent createCourse = new Intent(activity, CreateTestActivity.class);
                        createCourse.putExtra(Const.FRAG_TYPE, Const.CREATE_TEST_FRAG_TWO);
                        createCourse.putExtra(Const.LANG, type);
                        createCourse.putExtra(Const.CREATE_COURSE_DATA, courselistsSelected);
                        startActivity(createCourse);
                    } else {
                        if (courselists.size() == 0) {
                            Snackbar.make(view, "No Course Found", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(view, "Please select atleast one course", Snackbar.LENGTH_SHORT).show();

                        }
                    }
                }

            }
        });
    }

    private void initView(View view) {
        spinnerLL = view.findViewById(R.id.spinnerLL);
        spinnerLL.setOnClickListener(this);
        typeSpinner = view.findViewById(R.id.typeSpinner);

        typeSpinner.setOnClickListener(this);



        btnpcd = view.findViewById(R.id.btnpcd);
        parentLL = view.findViewById(R.id.parentLL);
        maxCount = view.findViewById(R.id.maxCount);
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
        Helper.showProgressDialog(getActivity());
        NetworkAPICall(API.API_CREATE_TEST_RETRIVE_COURSE, "", false, false, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_test_one, container, false);

    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_CREATE_TEST_RETRIVE_COURSE:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.API_CREATE_TEST_RETRIVE_COURSE(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_CREATE_TEST_RETRIVE_COURSE:
                try {
                    Helper.dismissProgressDialog();
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        MyCourse myCourse = new Gson().fromJson(jsonobject.toString(), MyCourse.class);
                        if (myCourse.getData().size() > 0) {
                            courselists = new ArrayList<>();
                            courselists.clear();
                            int oldarrysize = 0;
                            createTestRV.setVisibility(View.VISIBLE);
                            no_data_found_RL.setVisibility(View.GONE);
                            btnpcd.setVisibility(View.VISIBLE);

                            if (myCourse.getData().size() > 0) {
                                if (pagecount == 1) {
                                    courselists.clear();
                                    courselists.addAll(myCourse.getData());
                                } else {
                                    oldarrysize = courselists.size();
                                    courselists.addAll(myCourse.getData());
                                }
                                if (pagecount == 1) {
                                    tileDataAdapter = new TileDataAdapter(getActivity(), courselists);
                                    createTestRV.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
                                    createTestRV.setAdapter(tileDataAdapter);
                                    createTestRV.setNestedScrollingEnabled(false);
                                } else {
                                    tileDataAdapter.notifyItemRangeInserted(oldarrysize, courselists.size() - oldarrysize);
                                }
                            }
                        } else {
                            if (courselists != null && pagecount == 1) {
                                courselists.clear();
                                spinnerLL.setVisibility(View.GONE);
                                createTestRV.setVisibility(View.GONE);
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                btnpcd.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (courselists != null && pagecount == 1) {
                            courselists.clear();
                            spinnerLL.setVisibility(View.GONE);
                            createTestRV.setVisibility(View.GONE);
                            no_data_found_RL.setVisibility(View.VISIBLE);
                            btnpcd.setVisibility(View.GONE);
                        }
                        if (jsonobject.has(Const.AUTH_CODE)) {
                            RetrofitResponse.GetApiData(getActivity(), jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                        }
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
            btnpcd.setVisibility(View.GONE);
        }
    }

    public class TileDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Activity activity;
        ArrayList<Courselist> courseDataArrayList;

        public TileDataAdapter(Activity activity, ArrayList<Courselist> courseDataArrayList) {
            this.activity = activity;
            this.courseDataArrayList = courseDataArrayList;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHodler(LayoutInflater.from(activity).inflate(R.layout.create_test_course_item_adapter, null));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((MyViewHodler) holder).setData(courseDataArrayList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return courseDataArrayList.size();
        }

        @SuppressLint("SetTextI18n")
        private void checkProceedButton(Button buttonProceed) {
            boolean changeButton = false;
            for (Courselist courselist : courselists) {
                if (courselist.isSelect()) {
                    changeButton = true;
                    break;
                }
            }

            ArrayList<Courselist> createTestSubjectsSelected = new ArrayList<>();
            for (Courselist createTestSubject : courselists) {
                if (createTestSubject.isSelect()) {
                    createTestSubjectsSelected.add(createTestSubject);
                }
            }

            if (createTestSubjectsSelected.size() > 0) {
                topText.setText(createTestSubjectsSelected.size() + " Courses Selected");
//                topText.setTypeface(topText.getTypeface(), Typeface.BOLD);
            } else {
                topText.setText("Select single or multiple Courses(S)");
//                topText.setTypeface(topText.getTypeface(), Typeface.NORMAL);
            }

            if (changeButton) {
                buttonProceed.setBackground(activity.getResources().getDrawable(R.drawable.bg_round_corners_button));
            } else {
                buttonProceed.setBackground(activity.getResources().getDrawable(R.drawable.bg_round_corners_button_fade));
            }
        }

        public class MyViewHodler extends RecyclerView.ViewHolder {

            ImageView videoImage;
            ImageView checkIV;
            LinearLayout tileRL;
            RelativeLayout videoplayerRL;
            TextView titleTV;

            public MyViewHodler(View itemView) {
                super(itemView);
                videoImage = itemView.findViewById(R.id.ibt_single_vd_iv);
                checkIV = itemView.findViewById(R.id.checkIV);
                titleTV = itemView.findViewById(R.id.title);
                videoplayerRL = itemView.findViewById(R.id.videoplayerRL);
                tileRL = itemView.findViewById(R.id.tileRL);
            }

            public void setData(final Courselist course, int position) {
                int width = getScreenWidth() / 2;
                int height;
                boolean xlarge = ((activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
                boolean large = ((activity.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
                if (large) {
                    height = (int) ((getScreenWidth() / 2));
                } else if (xlarge) {
                    height = (int) ((getScreenWidth() / 2) + 300);
                } else {
                    height = (int) ((getScreenWidth() / 2) + 100);
                }
                RelativeLayout.LayoutParams layoutParamsa = new RelativeLayout.LayoutParams(width, height);
                videoImage.setLayoutParams(layoutParamsa);
                videoImage.setClipToOutline(true);


                maxCount.setText("(Max. " + courseDataArrayList.size() + ")");
                if (course.isSelect()) {
                    checkIV.setVisibility(View.VISIBLE);
                    tileRL.setBackground(activity.getResources().getDrawable(R.drawable.border_select_course));
                    checkIV.setImageResource(R.mipmap.check_act);
                } else {
                    tileRL.setBackground(null);
                    checkIV.setVisibility(View.INVISIBLE);
                    checkIV.setImageResource(R.mipmap.check_def);
                }

                if (!TextUtils.isEmpty(course.getColorCode())) {
                    videoplayerRL.setBackgroundColor(Color.parseColor(course.getColorCode()));
                }

                if (!TextUtils.isEmpty(course.getCover_image())) {
                    Helper.setThumbnailImage(activity, course.getCover_image(), activity.getResources().getDrawable(R.mipmap.book_placeholder), videoImage);
                } else {
                    videoImage.setImageResource(R.mipmap.book_placeholder);
                }

                titleTV.setText(course.getTitle());
                tileRL.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (course.isSelect()) {
                            course.setSelect(false);
                            checkIV.setVisibility(View.VISIBLE);
                            tileRL.setBackground(activity.getResources().getDrawable(R.drawable.border_select_course));
                            checkIV.setImageResource(R.mipmap.check_act);
                        } else {
                            course.setSelect(true);
                            tileRL.setBackground(null);
                            checkIV.setVisibility(View.INVISIBLE);
                            checkIV.setImageResource(R.mipmap.check_def);
                        }
                        notifyDataSetChanged();

                        checkProceedButton(buttonProceed);
                    }
                });
            }
        }
    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        typeSpinner.setText(item.getTitle());
        if (item.getTitle().toString().equalsIgnoreCase("English")) {
            type = "1";
        } else if (item.getTitle().toString().equalsIgnoreCase("Hindi")) {
            type = "2";

        } else if (item.getTitle().toString().equalsIgnoreCase("Both")) {
            type = "3";

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
                typeTests.add(new TypeTest("Both", "3"));

                PopupMenu popupMenu = new PopupMenu(activity, typeSpinner, Gravity.LEFT);
                for (int i = 0; i < typeTests.size(); i++) {
                    popupMenu.getMenu().add(typeTests.get(i).getName());
                }
                popupMenu.setOnMenuItemClickListener(CreateTestFragmentOne.this);
                popupMenu.show();
                break;
        }
    }

}
