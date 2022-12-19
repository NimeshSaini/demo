package com.utkarshnew.android.Login.Fragment;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.Login.Adapter.SubCategoryExamsAdapter;
import com.utkarshnew.android.Model.Registration;
import com.utkarshnew.android.Model.User;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Response.Registration.SubStreamResponse;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamsSubCategory extends Fragment {
    Activity activity;
    String type, subCat;
    User user;
    Registration registration;
    ArrayList<SubStreamResponse> specializationResponseArrayList;
    RecyclerView subcategoryExamsRV;
    GridLayoutManager gridLayoutManager;
    SubCategoryExamsAdapter subCategoryExamsAdapter;

    public ExamsSubCategory() {
        // Required empty public constructor
    }

    public static ExamsSubCategory newInstance(int type, String subcat) {
        ExamsSubCategory examsSubCategory = new ExamsSubCategory();
        Bundle args = new Bundle();
        args.putInt(Const.TYPE, type);
        args.putString(Const.EXAM_CATEGORY, subcat);
        examsSubCategory.setArguments(args);
        return examsSubCategory;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subCat = getArguments().getString(Const.EXAM_CATEGORY);
        }
        user = User.getInstance();
        activity = getActivity();
        registration = user.getUser_registration_info();
        specializationResponseArrayList = new ArrayList();
        specializationResponseArrayList = getSpecializationList(SharedPreference.getInstance().getRegistrationResponse().getMain_sub_category());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        gridLayoutManager = new GridLayoutManager(activity, 2);
        subcategoryExamsRV.setLayoutManager(gridLayoutManager);

//        subcategoryExamsRV.addItemDecoration(new ItemDecorationAlbumColumns(
//                getResources().getDimensionPixelSize(R.dimen.dp5),
//                getResources().getInteger(R.integer.dp5)));

        subCategoryExamsAdapter = new SubCategoryExamsAdapter(activity, specializationResponseArrayList);
        subcategoryExamsRV.setAdapter(subCategoryExamsAdapter);
    }

    private void initViews(View view) {
        subcategoryExamsRV = view.findViewById(R.id.subcategoryExamsRV);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exams_subcategory, container, false);
    }

    public ArrayList<SubStreamResponse> getSpecializationList(ArrayList<SubStreamResponse> list) {
        ArrayList<SubStreamResponse> subStreamResponseArrayList = new ArrayList<>();
        for (SubStreamResponse subStreamResponse : list) {
            if (subStreamResponse.getParent_id().equalsIgnoreCase(subCat)) {
                subStreamResponseArrayList.add(subStreamResponse);
            }
        }

        return subStreamResponseArrayList;
    }
}
