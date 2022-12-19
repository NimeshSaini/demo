package com.utkarshnew.android.LiveTest.Fragment;

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
import com.utkarshnew.android.home.livetest.LiveTest;
import com.utkarshnew.android.home.livetest.LiveTestData;
import com.utkarshnew.android.LiveTest.Activity.LivetestActivity;
import com.utkarshnew.android.LiveTest.Adapter.Testclassadapter;
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
 * Use the {@link CompletedTest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompletedTest extends Fragment implements NetworkCall.MyNetworkCallBack {

    private View view;
    private RecyclerView completedrecycler;
    RelativeLayout no_data_found_RL;
    Button backBtn;
    public Testclassadapter testclassadapter;
    List<LiveTestData> livetestcompleted;
    NetworkCall networkCall;
    LiveTest livetest;

    private int mPage = 1;
    private boolean loading = false;
    private SwipeRefreshLayout pullToReferesh;
    private NestedScrollView nestedScrollView;
    boolean status, visibilty_status;
    private boolean isPaginationAvailable = true;
    ProgressBar paginationLoader;
    Long time;

    public CompletedTest() {
        // Required empty public constructor
    }

    public static CompletedTest newInstance(String param1, String param2) {
        CompletedTest fragment = new CompletedTest();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (livetestcompleted == null) {
            livetestcompleted = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        completedrecycler = view.findViewById(R.id.completedrecycler);
        networkCall = new NetworkCall(this, getContext());
        pullToReferesh = view.findViewById(R.id.pullto_referesh);
        nestedScrollView = view.findViewById(R.id.nested_scroll);
        paginationLoader = view.findViewById(R.id.progressBar);
        no_data_found_RL = view.findViewById(R.id.no_data_found_RL);
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setVisibility(View.GONE);

        if (livetestcompleted.size() == 0) {
            Helper.showProgressDialog(getActivity());
            hit_api_for_data(false);
        } else {
            no_data_found_RL.setVisibility(View.GONE);
            completedrecycler.setVisibility(View.VISIBLE);
            testclassadapter = new Testclassadapter(getActivity(), livetestcompleted, true, time);
            completedrecycler.setAdapter(testclassadapter);
        }

        pullToReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialState();
                status = false;
                visibilty_status = true;
                Helper.showProgressDialog(getActivity());
                hit_api_for_data(false);
                pullToReferesh.setRefreshing(false);
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

    public void initialState() {
        mPage = 1;
        loading = true;
    }

    public void chnagevisiblity(boolean visibilty_status) {
        this.visibilty_status = visibilty_status;
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_testclasses_data:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setPage("" + mPage);
                getcoursedataencryptionData.setType("2");
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.getLiveTestsData(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.get_testclasses_data:
                try {
                    Helper.dismissProgressDialog();
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        time = jsonstring.optLong("time");
                        isPaginationAvailable = true;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        if (status) {
                            livetest = new Gson().fromJson(jsonstring.toString(), LiveTest.class);

                            if (livetest.getData() != null) {
                                int oldarrysize = livetestcompleted.size();
                                if (livetestcompleted.size() > 0) {
                                    livetestcompleted.addAll(livetest.getData());
                                    testclassadapter.notifyItemRangeInserted(oldarrysize, livetestcompleted.size() - oldarrysize);
                                }
                            } else {
                                // Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            initialState();
                            if (livetestcompleted != null && livetestcompleted.size() != 0) {
                                livetestcompleted.clear();
                            }
                            livetest = new Gson().fromJson(jsonstring.toString(), LiveTest.class);

                            if (livetest.getData() != null) {
                                livetestcompleted.addAll(livetest.getData());
                                if (livetestcompleted.size() > 0) {
                                    no_data_found_RL.setVisibility(View.GONE);
                                    completedrecycler.setVisibility(View.VISIBLE);
                                    testclassadapter = new Testclassadapter(getActivity(), livetestcompleted, true, time);
                                    completedrecycler.setAdapter(testclassadapter);
                                } else {
                                    completedrecycler.setVisibility(View.GONE);
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                }
                            } else {
                                completedrecycler.setVisibility(View.GONE);
                                no_data_found_RL.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        if (!status) {
                            no_data_found_RL.setVisibility(View.VISIBLE);
                            completedrecycler.setVisibility(View.GONE);
                        }
                        isPaginationAvailable = false;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        if (!GenericUtils.isEmpty(jsonstring.getString(Const.AUTH_CODE)) || LivetestActivity.view_pager.getCurrentItem() == 2)
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
        Helper.dismissProgressDialog();

        switch (apitype) {
            case API.get_testclasses_data:
                if (paginationLoader != null && paginationLoader.isShown()) {
                    paginationLoader.setVisibility(View.GONE);
                }
                if (completedrecycler != null && no_data_found_RL != null) {
                    completedrecycler.setVisibility(View.GONE);
                    no_data_found_RL.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void hit_api_for_data(boolean showProgress) {
        networkCall.NetworkAPICall(API.get_testclasses_data, "", showProgress, false);
    }

    public void refresh_data() {
        initialState();
        status = false;
        visibilty_status = true;
        hit_api_for_data(true);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(this).attach(this).commit();
        }
    }
}