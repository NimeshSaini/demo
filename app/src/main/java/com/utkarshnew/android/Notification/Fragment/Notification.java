package com.utkarshnew.android.Notification.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.NotificationModel.Datum;
import com.utkarshnew.android.Model.NotificationModel.NotificationdataModel;
import com.utkarshnew.android.Notification.Adapter.NotificationAdapter;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class Notification extends Fragment implements NetworkCall.MyNetworkCallBack {


    private int mPage = 1;
    private boolean loading = false;
    private SwipeRefreshLayout pullToReferesh;
    private NestedScrollView nestedScrollView;
    boolean status;
    private RecyclerView notificationrecyceler;
    private NetworkCall networkCall;
    NotificationdataModel notificationdata;
    List<Datum> notificationlist;
    NotificationAdapter notificationAdapter;
    private boolean isPaginationAvailable = true;
    ProgressBar paginationLoader;


    public Notification() {
        // Required empty public constructor
    }


    public static Notification newInstance(String param1, String param2) {
        Notification fragment = new Notification();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (notificationlist == null) {
            notificationlist = new ArrayList<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        notificationrecyceler = view.findViewById(R.id.notification);
        pullToReferesh = view.findViewById(R.id.pullto_referesh);
        nestedScrollView = view.findViewById(R.id.nested_scroll);
        paginationLoader = view.findViewById(R.id.progressBar);
        networkCall = new NetworkCall(this, getContext());

        if (notificationlist.size() == 0) {
            hit_api_for_notificationdata(true);
        } else {
            notificationrecyceler.setVisibility(View.VISIBLE);
            notificationAdapter = new NotificationAdapter(getActivity(), notificationlist);
            notificationrecyceler.setAdapter(notificationAdapter);
        }

        pullToReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initialState();
                status = false;
                hit_api_for_notificationdata(true);
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
                                hit_api_for_notificationdata(false);
                            }
                        }
                    }
                }
            }
        });
    }

    private void hit_api_for_notificationdata(boolean showProgress) {
        networkCall.NetworkAPICall(API.get_notification_data, "", showProgress, false);
    }

    public void initialState() {
        mPage = 1;
        loading = true;
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.get_notification_data:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setPage("" + mPage);
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.getNotification(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.get_notification_data:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        isPaginationAvailable = true;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                        if (status) {
                            notificationdata = new Gson().fromJson(jsonstring.toString(), NotificationdataModel.class);

                            if (notificationdata.getData() != null) {
                                int oldarrysize = notificationdata.getData().size();
                                if (notificationdata.getData().size() > 0) {
                                    notificationlist.addAll(notificationdata.getData());
                                    notificationAdapter.notifyItemRangeInserted(notificationlist.size() - 1, notificationlist.size() - oldarrysize);
                                }

                            } else {
                                Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            initialState();
                            if (notificationlist != null && notificationlist.size() != 0) {
                                notificationlist.clear();
                            }
                            notificationdata = new Gson().fromJson(jsonstring.toString(), NotificationdataModel.class);

                            if (notificationdata.getData() != null) {
                                notificationlist.addAll(notificationdata.getData());
                                if (notificationlist.size() > 0) {
                                    //  notificationlist.addAll(notificationdata.getData());
                                    notificationAdapter = new NotificationAdapter(getActivity(), notificationlist);
                                    notificationrecyceler.setAdapter(notificationAdapter);
                                }
                            } else {
                                Toast.makeText(getContext(), "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
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
        if (paginationLoader != null && paginationLoader.isShown()) {
            paginationLoader.setVisibility(View.GONE);
        }
    }
}