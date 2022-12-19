package com.utkarshnew.android.home.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.utkarshnew.android.courses.Interfaces.OnSuccessListner;
import com.utkarshnew.android.home.Activity.MyLibraryActivty;
import com.utkarshnew.android.home.adapters.PaidCourseAdapter;
import com.utkarshnew.android.home.model.MyCourse;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreeFragment extends Fragment implements OnSuccessListner {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private View view;
    private RecyclerView free_recyclerview;
    private String mParam2;
    private MyCourse myCourse;
    RelativeLayout no_data_found_RL;
    Button backBtn;
    public static PaidCourseAdapter paidCourseAdapter;

    public FreeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FreeFragment newInstance(String param1, String param2) {
        FreeFragment fragment = new FreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MyLibraryActivty) activity).onSuccessListner = this;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_free, container, false);
        free_recyclerview = view.findViewById(R.id.free_course_recycler);
        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setVisibility(View.GONE);

        myCourse = ((MyLibraryActivty) Objects.requireNonNull(getActivity())).myCourse;

        ((MyLibraryActivty) Objects.requireNonNull(getActivity())).updatesortlistner_free((courselists, tiitle) -> {
            myCourse.setFreecourse(courselists);
            paidCourseAdapter.notifidata(courselists, tiitle);
        });

        //Bundle bundle = getArguments();
        if (myCourse != null && myCourse.getFreecourse() != null) {
            //myCourse = (MyCourse) bundle.getSerializable("courselist");
            if (myCourse.getFreecourse().size() > 0) {
                no_data_found_RL.setVisibility(View.GONE);
                free_recyclerview.setVisibility(View.VISIBLE);
                paidCourseAdapter = new PaidCourseAdapter(getActivity(), myCourse.getFreecourse(), myCourse.getTime());
                free_recyclerview.setAdapter(paidCourseAdapter);
            } else {
                no_data_found_RL.setVisibility(View.VISIBLE);
                free_recyclerview.setVisibility(View.GONE);
            }

        } else {
            no_data_found_RL.setVisibility(View.VISIBLE);
            free_recyclerview.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onSuccess(String pot_txt_id) {
        paidCourseAdapter.update_payment(pot_txt_id);
    }

    @Override
    public void onFailure(String pot_txt_id) {
        paidCourseAdapter.update_payment(pot_txt_id);

    }

    public void updatedata(List<Courselist> freecourse) {
        Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(this).attach(this).commit();
        paidCourseAdapter.notifidata(freecourse);
    }

    public void beginSearch(String newText) {

        ArrayList<Courselist> free_course = new ArrayList<>();
        for (Courselist item : myCourse.getFreecourse()) {
            if (item.getTitle().toLowerCase().contains(newText.toLowerCase()) || item.getTitle().toUpperCase().contains(newText.toUpperCase())) {
                free_course.add(item);
            }
        }
        if (free_course.isEmpty()) {
            Snackbar.make(view, "No Course Found", Snackbar.LENGTH_SHORT).show();
            //  Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        } else {
            if (paidCourseAdapter != null)
                paidCourseAdapter.filterList(free_course);
        }
    }
}