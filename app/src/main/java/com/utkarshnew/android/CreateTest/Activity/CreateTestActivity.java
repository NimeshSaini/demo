package com.utkarshnew.android.CreateTest.Activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.utkarshnew.android.CreateTest.Fragment.CreateTestFragmentOne;
import com.utkarshnew.android.CreateTest.Fragment.CreateTestFragmentThree;
import com.utkarshnew.android.CreateTest.Fragment.CreateTestFragmentTwo;
import com.utkarshnew.android.CreateTest.Model.CreateTestSubject;
import com.utkarshnew.android.home.Activity.BaseABNoNavActivity;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;

import java.util.ArrayList;

public class CreateTestActivity extends BaseABNoNavActivity {

    String frag_type,courseIds,LANG;
    ArrayList<Courselist> courselists;
    ArrayList<CreateTestSubject> createTestSubjects;

    @Override
    protected void initViews() {
        if (getIntent().getExtras() != null) {
            frag_type = getIntent().getExtras().getString(Const.FRAG_TYPE);
            courseIds = getIntent().getExtras().getString("courseIds");
            LANG = getIntent().getExtras().getString(Const.LANG);
            if (LANG==null){
                LANG="1";
            }
            courselists = (ArrayList<Courselist>) getIntent().getExtras().getSerializable(Const.CREATE_COURSE_DATA);
            createTestSubjects = (ArrayList<CreateTestSubject>) getIntent().getExtras().getSerializable(Const.CREATE_COURSE_SUBJECT_DATA);
        }
    }

    @Override
    protected boolean addBackButton() {
        return true;
    }

    @Override
    protected Fragment getFragment() {
        switch (frag_type) {
            case Const.CREATE_TEST_FRAG_ONE:
                setToolbarTitle(getString(R.string.create_test));
                return CreateTestFragmentOne.newInstance(frag_type);

            case Const.CREATE_TEST_FRAG_TWO:
                setToolbarTitle(getString(R.string.create_test));
                return CreateTestFragmentTwo.newInstance(frag_type, courselists,LANG);

            case Const.CREATE_TEST_FRAG_THREE:
                setToolbarTitle(getString(R.string.create_test));
                return CreateTestFragmentThree.newInstance(frag_type, createTestSubjects,LANG,courseIds);

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
