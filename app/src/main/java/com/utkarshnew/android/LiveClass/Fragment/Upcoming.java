package com.utkarshnew.android.LiveClass.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.home.liveclasses.Datum;
import com.utkarshnew.android.home.liveclasses.LiveClassesData;
import com.utkarshnew.android.LiveClass.Adapter.Liveclassadapter;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Upcoming#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Upcoming extends Fragment implements NetworkCall.MyNetworkCallBack {

    private View view;
    private RecyclerView upcomingrecycler;
    RelativeLayout no_data_found_RL;
    Button backBtn;
    Liveclassadapter liveclassadapter;

    private int mPage = 1;
    private boolean loading = false;
    private SwipeRefreshLayout pullToReferesh;
    private NestedScrollView nestedScrollView;
    boolean status;
    NetworkCall networkCall;
    List<Datum> livevideocompleted;
    LiveClassesData liveClasses;
    boolean isPaginationAvailable = true;
    ProgressBar paginationLoader;
    Long time;

    public Upcoming() {
        // Required empty public constructor
    }


    public static Upcoming newInstance(String param1, String param2) {
        Upcoming fragment = new Upcoming();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (livevideocompleted == null) {
            livevideocompleted = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        upcomingrecycler = view.findViewById(R.id.upcomingrecycler);

        networkCall = new NetworkCall(this, getContext());
        pullToReferesh = view.findViewById(R.id.pullto_referesh);
        nestedScrollView = view.findViewById(R.id.nested_scroll);
        paginationLoader = view.findViewById(R.id.progressBar);
        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setVisibility(View.GONE);

        if (livevideocompleted.size() == 0) {
            if (Helper.isConnected(requireActivity()))
            {
                Helper.showProgressDialog(getActivity());
                hit_api_for_data(false);
            }
        } else {
            no_data_found_RL.setVisibility(View.GONE);
            upcomingrecycler.setVisibility(View.VISIBLE);
            liveclassadapter = new Liveclassadapter(getActivity(), livevideocompleted, time);
            upcomingrecycler.setAdapter(liveclassadapter);
        }

        pullToReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (Helper.isConnected(requireActivity()))
                {
                    initialState();
                    status = false;
                    Helper.showProgressDialog(getActivity());
                    livevideocompleted = new ArrayList<>();
                    hit_api_for_data(false);
                    pullToReferesh.setRefreshing(false);
                }else {
                    pullToReferesh.setRefreshing(false);

                }

            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {
                        if (loading) {
                            if (isPaginationAvailable) {
                                paginationLoader.setVisibility(View.VISIBLE);
                                ++mPage;
                                status = true;
                                hit_api_for_data(false);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_liveclasses_data:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setPage("" + mPage);
                getcoursedataencryptionData.setType("1");
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.getLiveClassesData(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.get_liveclasses_data:
                try {
                    Helper.dismissProgressDialog();

                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        time = jsonstring.optLong("time");
                        isPaginationAvailable = true;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        if (status) {
                            liveClasses = new Gson().fromJson(jsonstring.toString(), LiveClassesData.class);

                            if (liveClasses.getData() != null) {
                                int oldarrysize = livevideocompleted.size();
                                if (livevideocompleted.size() > 0) {
                                    livevideocompleted.addAll(liveClasses.getData());
                                    liveclassadapter.notifyItemRangeInserted(oldarrysize, livevideocompleted.size() - oldarrysize);
                                }
                            } else {
                            }
                        } else {
                            initialState();
                            if (livevideocompleted != null && livevideocompleted.size() != 0) {
                                livevideocompleted.clear();
                            }
                            liveClasses = new Gson().fromJson(jsonstring.toString(), LiveClassesData.class);

                            if (liveClasses.getData() != null) {
                                livevideocompleted.addAll(liveClasses.getData());
                                if (livevideocompleted.size() > 0) {
                                    no_data_found_RL.setVisibility(View.GONE);
                                    upcomingrecycler.setVisibility(View.VISIBLE);
                                    //  livevideocompleted.addAll(liveClasses.getData());
                                    liveclassadapter = new Liveclassadapter(getActivity(), livevideocompleted, time);
                                    upcomingrecycler.setAdapter(liveclassadapter);
                                } else {
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                    upcomingrecycler.setVisibility(View.GONE);
                                }
                            } else {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                upcomingrecycler.setVisibility(View.GONE);
                            }
                        }
                    } else {

                        if (!status) {
                            no_data_found_RL.setVisibility(View.VISIBLE);
                            upcomingrecycler.setVisibility(View.GONE);
                        }

                        isPaginationAvailable = false;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        if (!GenericUtils.isEmpty(jsonstring.getString(Const.AUTH_CODE)))
                            RetrofitResponse.GetApiData(getContext(), jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        switch (apitype) {
            case API.get_liveclasses_data:
                if (paginationLoader != null && paginationLoader.isShown()) {
                    paginationLoader.setVisibility(View.GONE);
                }
                if (upcomingrecycler != null && no_data_found_RL != null) {
                    upcomingrecycler.setVisibility(View.GONE);
                    no_data_found_RL.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void initialState() {
        mPage = 1;
        loading = true;
    }


    private void hit_api_for_data(boolean showProgress) {
        networkCall.NetworkAPICall(API.get_liveclasses_data, "", showProgress, false);
    }
}
