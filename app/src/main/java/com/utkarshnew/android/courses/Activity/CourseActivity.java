package com.utkarshnew.android.courses.Activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.utkarshnew.android.courses.Fragment.CommonFragForList;
import com.utkarshnew.android.courses.Fragment.ExamPrepLayer1;
import com.utkarshnew.android.courses.Fragment.ExamPrepLayer2;
import com.utkarshnew.android.courses.Fragment.SingleStudy;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.courses.modal.TopicData;
import com.utkarshnew.android.courses.modal.UnitData;
import com.utkarshnew.android.home.Activity.BaseABNoNavActivity;
import com.utkarshnew.android.Login.Fragment.CommonWebViewFragment;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetail;
import com.utkarshnew.android.Model.COURSEDETAIL.CourseDetailData;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.Courses.Course;
import com.utkarshnew.android.Model.Courses.CourseCategory;
import com.utkarshnew.android.Model.Courses.ExamPrepItem;
import com.utkarshnew.android.Model.Courses.Lists;
import com.utkarshnew.android.Model.Courses.Reviews;
import com.utkarshnew.android.Model.Courses.SingleCourseData;
import com.utkarshnew.android.Model.Courses.SinglestudyModel;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.home.Constants;

import java.util.ArrayList;

public class CourseActivity extends BaseABNoNavActivity {
    public Lists lists;
    public String total;
    public boolean testThird = false;
    public boolean isPurchased;
    public int isrelated = 0;
    public Courselist courselist;
    public ExamPrepItem examPrepItem;
    public String title, contentType;
    public String unit_id = "", chapter_id = "", topic_id = "", subtopic_id = "";
    CourseCategory courseCategory;
    SingleCourseData singleCourseData;
    Reviews[] reviews;
    ArrayList<Video> videoArrayList;
    ArrayList<UnitData> unitData;
    ArrayList<TopicData> topicData;
    Course course;
    int position;
    public Lists listSubject;
    CourseDetailData courseDetailData;
    public OnSuccessListner onSuccessListner;

    private String frag_type = "", emi_type = "", path = "", strUrl = "", searchQuery = "", serarch_title = "";
    private ArrayList<SinglestudyModel> singleStudy;
    public CourseDetail singlestudyModel;

    String skip_unit = "";
    String skip_chapter = "";
    String image = "";
    public String mainCourseId = "", valid_to = "";
    String parentCourseId = "";

    String tileTypeAPI;
    String tileIdAPI;
    String revertAPI;
    public boolean isCombo = false, share_type = false, is_coupon = false;

    public String pos_txn_id = "";
    public String courseName = "", course_name = "";

    public boolean isMoved = false;

    @Override
    protected void initViews() {


        if (getIntent().getExtras() != null) {
            isCombo = getIntent().getExtras().getBoolean(Const.IS_COMBO);
            is_coupon = getIntent().getExtras().getBoolean(Const.is_coupon);
            mainCourseId = getIntent().getExtras().getString(Const.COURSE_ID_MAIN);
            valid_to = getIntent().getExtras().getString("valid_to");
            course_name = getIntent().getExtras().getString("course_name");
            courseName = course_name == null ? "Details" : course_name;

            parentCourseId = getIntent().getExtras().getString(Const.COURSE_PARENT_ID);
            frag_type = getIntent().getExtras().getString(Const.FRAG_TYPE);
            Log.e("sdfgjkl", "initViews: " + frag_type);
            emi_type = getIntent().getExtras().getString(Const.EMI_TYPE);
            searchQuery = getIntent().getExtras().getString(Const.SEARCH_QUERY);

            skip_unit = getIntent().getExtras().getString(Const.SKIP_UNIT);
            skip_chapter = getIntent().getExtras().getString(Const.SKIP_CHAPTER);
            listSubject = (Lists) getIntent().getExtras().getSerializable(Const.LIST_SUBJECT);

            if (getIntent().hasExtra(Const.ISMOVED)) {
                isMoved = true;
            } else {
                isMoved = false;
            }

            image = getIntent().getExtras().getString(Const.IMAGE);
            serarch_title = getIntent().getExtras().getString("serach_title");
            //  courseName = getIntent().getExtras().getString("courseName");


            type = getIntent().getExtras().getString(Const.TYPE);
            courseCategory = (CourseCategory) getIntent().getExtras().getSerializable(Const.COURSE_CATEGORY);
            course = (Course) getIntent().getExtras().getSerializable(Const.COURSES);
            courseDetailData = (CourseDetailData) getIntent().getExtras().getSerializable("courseDetailData");
            testThird = getIntent().getExtras().getBoolean(Const.TEST_THIRD);
            contentType = getIntent().getExtras().getString(Const.CONTENT_TYPE);
            courselist = (Courselist) getIntent().getExtras().getSerializable(Const.COURSESLIST);
            lists = (Lists) getIntent().getExtras().getSerializable(Const.LIST);

            singleCourseData = (SingleCourseData) getIntent().getExtras().getSerializable(Const.COURSE_DES);
            singleStudy = (ArrayList<SinglestudyModel>) getIntent().getExtras().getSerializable(Const.SINGLE_STUDY);
            reviews = (Reviews[]) getIntent().getExtras().getSerializable(Const.REVIEWS);
            title = getIntent().getExtras().getString(Const.TITLE);
            /*contentType = getIntent().getExtras().getString(Const.CONTENT_TYPE);*/
          //  examPrepItem = (ExamPrepItem) getIntent().getExtras().getSerializable(Const.EXAMPREP);


          /*     if (getIntent().getStringExtra("study_data")!=null && !TextUtils.isEmpty(getIntent().getStringExtra("study_data")))
               {
                   singlestudyModel =new Gson().fromJson(getIntent().getStringExtra("study_data"),CourseDetail.class);
               }else {
                   singlestudyModel = (CourseDetail) getIntent().getExtras().getSerializable(Const.SINGLE_STUDY_DATA);
               }
               */


            path = getIntent().getExtras().getString(Const.PATH);

            unitData = (ArrayList<UnitData>) getIntent().getExtras().getSerializable(Const.UNIT_DATA);
            topicData = (ArrayList<TopicData>) getIntent().getExtras().getSerializable(Const.TOPIC_DATA);

            unit_id = getIntent().getExtras().getString(Const.UNIT_ID);
            chapter_id = getIntent().getExtras().getString(Const.CHAPTER_ID);
            topic_id = getIntent().getExtras().getString(Const.TOPIC_ID);
            subtopic_id = getIntent().getExtras().getString(Const.SUBTOPIC_ID);


            videoArrayList = (ArrayList<Video>) getIntent().getExtras().getSerializable(Const.DATA);
            position = getIntent().getExtras().getInt(Const.POSITION);
            strUrl = getIntent().getExtras().getString(Const.PRIVACYURL);

            tileIdAPI = getIntent().getExtras().getString(Const.TILE_ID);
            tileTypeAPI = getIntent().getExtras().getString(Const.TILE_TYPE);
            revertAPI = getIntent().getExtras().getString(Const.REVERT_API);



            if (frag_type.equalsIgnoreCase(Const.EXAMPREP) || frag_type.equalsIgnoreCase(Const.EXAMPREPLAST)) {
                examPrepItem = Constants.examPrepItemNew;
                if (getIntent().getStringExtra("study_data") != null && !TextUtils.isEmpty(getIntent().getStringExtra("study_data"))) {
                    singlestudyModel = new Gson().fromJson(getIntent().getStringExtra("study_data"), CourseDetail.class);
                } else {
                    singlestudyModel = Constants.courseDetail;
                }
                Constants.examPrepItemNew=null;
                Constants.courseDetail=null;
            } else {
                examPrepItem = (ExamPrepItem) getIntent().getExtras().getSerializable(Const.EXAMPREP);

                if (getIntent().getStringExtra("study_data") != null && !TextUtils.isEmpty(getIntent().getStringExtra("study_data"))) {
                    singlestudyModel = new Gson().fromJson(getIntent().getStringExtra("study_data"), CourseDetail.class);
                } else {
                    singlestudyModel = (CourseDetail) getIntent().getExtras().getSerializable(Const.SINGLE_STUDY_DATA);
                }
            }

        }

        if (frag_type.equals(Const.SEEALL_COURSE) || frag_type.equals(Const.ALLCOURSES)) {
            searchView.setVisibility(View.VISIBLE);
        } else searchView.setVisibility(View.GONE);

        if (contentType != null || !TextUtils.isEmpty(contentType)) {
            total = getIntent().getExtras().getString(Const.TEST_TYPE);
        }


    }

    @Override
    protected boolean addBackButton() {
        return true;
    }

    @Override
    protected Fragment getFragment() {
        switch (frag_type) {
            case Const.ALLCOURSES:
            case Const.MYCART:
                setToolbarTitle(getString(R.string.mycart));
                return CommonFragForList.newInstance(frag_type);

            case Const.MYCOURSES:
                setToolbarTitle(getString(R.string.mycourse));
                return CommonFragForList.newInstance(frag_type, courseCategory);
            case Const.MYEMICOURSES:
                setToolbarTitle(getString(R.string.mycourse));
                return CommonFragForList.newInstance(frag_type, mainCourseId);
            case Const.SEARCH_COURSE:
                setToolbarTitle(getString(R.string.course));
                return CommonFragForList.newInstance(frag_type, courseCategory, searchQuery);
            case Const.LEADERBOARD:
                setToolbarTitle(Const.LEADERBOARD);
                return CommonFragForList.newInstance(frag_type, courseCategory);
            case Const.SEEALL_COURSE:
                return CommonFragForList.newInstance(frag_type, courseCategory);
            case Const.FAQ:
                setToolbarTitle(getString(R.string.faq_title));
                return CommonFragForList.newInstance(frag_type, course);
            case Const.SINGLE_STUDY:
                if (isMoved) moveFromPayment();
                setToolbarTitle(courseName);
                return SingleStudy.newInstance(mainCourseId, isCombo, parentCourseId, courseName, valid_to);
            case Const.EXAMPREP:
                return ExamPrepLayer1.newInstance(examPrepItem, lists, contentType, title, singlestudyModel, isCombo, tileIdAPI, tileTypeAPI, revertAPI);
            case Const.EXAMPREPLAST:
                return ExamPrepLayer2.newInstance(examPrepItem, lists, listSubject, contentType, title, total, singlestudyModel, isCombo, tileIdAPI, tileTypeAPI, revertAPI, serarch_title);
            case Const.PRIVACY_POLICY:
                setToolbarTitle(getString(R.string.privacy_policy));
                return CommonWebViewFragment.newInstance(strUrl, true);
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
      /*  if (myDBClass == null) {
            myDBClass = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
            myDBClass.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
