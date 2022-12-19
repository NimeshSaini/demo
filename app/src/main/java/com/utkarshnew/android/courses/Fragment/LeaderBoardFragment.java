package com.utkarshnew.android.courses.Fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.utkarshnew.android.Model.Courses.quiz.LeaderBoardUserModel;
import com.utkarshnew.android.Model.Courses.quiz.ResultTestSeries;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.MainFragment;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderBoardFragment extends MainFragment {
    public String frag_type = "", errorMessage, status;
    Activity activity;
    ResultTestSeries resultTestSeries;
    LeaderBoardAdapter leaderBoardAdapter;
    private RecyclerView leaderBoardRecyclerView;
    private TextView errorTV, rankvalueTv, totalUserTV;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    public static LeaderBoardFragment newInstance(String frag_type, String resultTestSeries) {
        LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        args.putString(Const.FRAG_TYPE, frag_type);
        args.putString(Const.RESULT_SCREEN, resultTestSeries);
        leaderBoardFragment.setArguments(args);
        return leaderBoardFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.FRAG_TYPE);
            status = getArguments().getString(Const.RESULT_SCREEN);
//            resultTestSeries = (ResultTestSeries) getArguments().getSerializable(Const.RESULT_SCREEN);
        }
        activity = getActivity();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        return null;
    }


    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Gson gson = new Gson();
        JSONArray dataArray;
        switch (apitype) {
            case API.API_GET_USER_RESULT:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    JSONObject data = jsonobject.getJSONObject(Const.DATA);
                    resultTestSeries = gson.fromJson(data.toString(), ResultTestSeries.class);
                    generateData();
                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.optString(Const.AUTH_CODE), jsonobject.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        NetworkAPICall(API.API_GET_USER_RESULT, "", true, false, false);
    }

    public void generateData() {
        totalUserTV.setText(resultTestSeries.getTotal_user_attempt());
        rankvalueTv.setText(resultTestSeries.getUser_rank());
        leaderBoardAdapter = new LeaderBoardAdapter(activity, resultTestSeries.getTop_list());
        leaderBoardRecyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        leaderBoardRecyclerView.setAdapter(leaderBoardAdapter);
    }

    private void initViews(View view) {
        rankvalueTv = view.findViewById(R.id.rankValueTV);
        totalUserTV = view.findViewById(R.id.userValueTV);
        leaderBoardRecyclerView = view.findViewById(R.id.leaderListRV);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leader_board, container, false);
    }


    // TODO calling adapter to set the View at runtime.
    public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
        Context context;
        ArrayList<LeaderBoardUserModel> leaderBoardUserModelArrayList;

        public LeaderBoardAdapter(Context activity, ArrayList<LeaderBoardUserModel> top_ten_list) {
            this.context = activity;
            this.leaderBoardUserModelArrayList = top_ten_list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_people, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.nameTV.setText(leaderBoardUserModelArrayList.get(position).getName());
            holder.specialisationTV.setText(String.format("%s %s", "Rank ", leaderBoardUserModelArrayList.get(position).getRank()));

            // * To set the Image on Profile * //
            if (!TextUtils.isEmpty(leaderBoardUserModelArrayList.get(position).getProfile_picture())) {
                holder.userImageIV.setVisibility(View.VISIBLE);
                holder.userImageIVText.setVisibility(View.GONE);
                /*Ion.with(activity).load(leaderBoardUserModelArrayList.get(position).getProfile_picture())
                        .asBitmap()
                        .setCallback(new FutureCallback<Bitmap>() {
                            @Override
                            public void onCompleted(Exception e, Bitmap result) {
                                if (result != null)
                                    holder.userImageIV.setImageBitmap(result);
                                else
                                    holder.userImageIV.setImageResource(R.mipmap.default_pic);
                            }
                        });*/
                Helper.setThumbnailImage(activity, leaderBoardUserModelArrayList.get(position).getProfile_picture(), activity.getResources().getDrawable(R.mipmap.default_pic), holder.userImageIV);
            } else {
                Drawable dr = Helper.GetDrawable(leaderBoardUserModelArrayList.get(position).getName(), activity, leaderBoardUserModelArrayList.get(position).getUser_id());
                if (dr != null) {
                    holder.userImageIV.setVisibility(View.GONE);
                    holder.userImageIVText.setVisibility(View.VISIBLE);
                    holder.userImageIVText.setImageDrawable(dr);
                } else {
                    holder.userImageIV.setVisibility(View.VISIBLE);
                    holder.userImageIVText.setVisibility(View.GONE);
                    holder.userImageIV.setImageResource(R.mipmap.default_pic);
                }
            }

        }

        @Override
        public int getItemCount() {
            return leaderBoardUserModelArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private FrameLayout followButtonRl;
            private ImageView userImageIV, userImageIVText;
            private TextView nameTV, specialisationTV;

            public ViewHolder(View itemView) {
                super(itemView);
                nameTV = itemView.findViewById(R.id.nameTV);
                specialisationTV = itemView.findViewById(R.id.specialisationTV);
                userImageIV = itemView.findViewById(R.id.imageIV);
                userImageIVText = itemView.findViewById(R.id.imageIVText);
                followButtonRl = itemView.findViewById(R.id.followBtnRL);
                if (followButtonRl.getVisibility() == View.VISIBLE)
                    followButtonRl.setVisibility(View.GONE);
                if (specialisationTV.getVisibility() == View.GONE)
                    specialisationTV.setVisibility(View.VISIBLE);
            }
        }
    }

}
