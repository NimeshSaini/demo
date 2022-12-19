package com.utkarshnew.android.Coupon.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.utkarshnew.android.Coupon.Activity.CouponActivity;
import com.utkarshnew.android.Coupon.Adapter.CouponTypeAdapter;
import com.utkarshnew.android.Coupon.Models.Available;
import com.utkarshnew.android.Coupon.Models.CouponPojo;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;

import java.util.ArrayList;
import java.util.List;


public class ExpiredCouponFragment extends Fragment {

    private RecyclerView upcomingrecycler;
    RelativeLayout no_data_found_RL;
    Button backBtn;
    SwipeRefreshLayout pullto_referesh;
    CouponTypeAdapter couponTypeAdapter;

    CouponPojo couponPojo;

    List<Available> availables;

    public ExpiredCouponFragment() {
        // Required empty public constructor
    }

    public static ExpiredCouponFragment newInstance(String param1, String param2) {
        ExpiredCouponFragment fragment = new ExpiredCouponFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void getBundleData() {
        if (getArguments() != null) {
            couponPojo = ((CouponActivity) getContext()).couponPojo;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expired_coupon, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getBundleData();
        intiViews(view);
        setData();
    }

    private void setData() {
        availables = new ArrayList<>();
        if (couponPojo != null) {
            if (couponPojo.getExpired() != null && couponPojo.getExpired().size() > 0) {
                availables.addAll(couponPojo.getExpired());
            } else {
                no_data_found_RL.setVisibility(View.VISIBLE);
                upcomingrecycler.setVisibility(View.GONE);
            }
        } else {
            no_data_found_RL.setVisibility(View.VISIBLE);
            upcomingrecycler.setVisibility(View.GONE);
        }

        couponTypeAdapter = new CouponTypeAdapter(getContext(), availables, 3);

        upcomingrecycler.setAdapter(couponTypeAdapter);
    }

    private void intiViews(View view) {
        upcomingrecycler = view.findViewById(R.id.Liveclassesrecycler);
        pullto_referesh = view.findViewById(R.id.pullto_referesh);
        pullto_referesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((CouponActivity) getActivity()).fetchData();
                ((CouponActivity) getContext()).stackPlace = 2;
            }
        });
        upcomingrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setVisibility(View.GONE);


    }
}