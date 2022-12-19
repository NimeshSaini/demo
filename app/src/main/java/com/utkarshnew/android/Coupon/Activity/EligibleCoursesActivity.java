package com.utkarshnew.android.Coupon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.utkarshnew.android.Coupon.Adapter.EligibleCoursesAdapter;
import com.utkarshnew.android.Coupon.Models.CoursesCoupon;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class EligibleCoursesActivity extends AppCompatActivity {

    RecyclerView eligible_recyclerView;
    EligibleCoursesAdapter eligibleCoursesAdapter;
    List<CoursesCoupon> coursesCouponList;
    String discount, id;
    ImageView image_back;
    List<String> mycourseTables = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_eligible_courses);
        String userid = SharedPreference.getInstance().getLoggedInUser().getId();
        if (userid != null && !userid.equalsIgnoreCase("0") && !TextUtils.isEmpty(userid)) {
            MakeMyExam.setUserId(userid);
            MakeMyExam.userId = userid;
            if (UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getMyCourseDao().isRecordExists(userid)) {
                mycourseTables = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext()).getMyCourseDao().getAllcourseid(userid);
            }
        }

        getIntentData();
        initViews();
        setAdapterData();

    }

    private void setAdapterData() {
        if (mycourseTables != null && mycourseTables.size() > 0) {
            Log.d("prince", "" + new Gson().toJson(mycourseTables));
            Log.d("prince", "" + new Gson().toJson(coursesCouponList));

            for (CoursesCoupon coursesCoupon : coursesCouponList) {

                for (String mycourseTable : mycourseTables) {
                    if (mycourseTable.equalsIgnoreCase(coursesCoupon.getId())) {
                        coursesCoupon.setIs_purchased("1");
                        break;
                    }
                }

            }

        }

        eligibleCoursesAdapter = new EligibleCoursesAdapter(this, coursesCouponList, discount, id);

        eligible_recyclerView.setAdapter(eligibleCoursesAdapter);
        image_back = findViewById(R.id.image_back);


        image_back.setOnClickListener(new OnSingleClickListener(() -> {
            finish();
            return null;
        }));
    }

    private void getIntentData() {
        if (getIntent() != null) {
            if (getIntent().hasExtra(Const.ELIGIBLE_COURSES)) {
                coursesCouponList = (List<CoursesCoupon>) getIntent().getSerializableExtra(Const.ELIGIBLE_COURSES);
                discount = getIntent().getStringExtra(Const.DISCOUNT);
                id = getIntent().getStringExtra(Const.ID);
            }
        }
    }

    private void initViews() {
        eligible_recyclerView = findViewById(R.id.eligible_recyclerView);
        eligible_recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}