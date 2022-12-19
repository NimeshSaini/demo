package com.utkarshnew.android.Notification;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.NotificationModel.Datum;
import com.utkarshnew.android.Model.NotificationModel.NotificationdataModel;
import com.utkarshnew.android.Notification.Adapter.NotificationAdapter;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;
import retrofit2.Call;

public class Notification extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {


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
    ImageView image_back;
    TextView markasread;

    private boolean isPaginationAvailable = true;
    ProgressBar paginationLoader;
    public static long server_time = 0l;

    RelativeLayout no_data_found_RL;
    Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_notification);
        if (notificationlist == null) {
            notificationlist = new ArrayList<>();
        }

        notificationrecyceler = findViewById(R.id.notification);
        notificationrecyceler.setLayoutManager(new LinearLayoutManager(this));
        notificationrecyceler.setNestedScrollingEnabled(false);
        pullToReferesh = findViewById(R.id.pullto_referesh);
        nestedScrollView = findViewById(R.id.nested_scroll);
        image_back = findViewById(R.id.image_back);
        markasread = findViewById(R.id.markasread);
        no_data_found_RL = findViewById(R.id.no_data_found_RL);
        backBtn = findViewById(R.id.backBtn);
        paginationLoader = findViewById(R.id.progressBar);


        markasread.setOnClickListener(new OnSingleClickListener(() -> {
            hit_api_for_all_read();
            return null;
        }));

        backBtn.setOnClickListener(new OnSingleClickListener(() -> {
            finish();
            return null;
        }));

        image_back.setOnClickListener(new OnSingleClickListener(() -> {
            finish();
            return null;
        }));


        networkCall = new NetworkCall(this, this);

        if (notificationlist.size() == 0) {
            hit_api_for_notificationdata(true);
        } else {
            no_data_found_RL.setVisibility(View.GONE);
            notificationrecyceler.setVisibility(View.VISIBLE);
            notificationAdapter = new NotificationAdapter(this, notificationlist);
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

    private void hit_api_for_all_read() {
        networkCall.NetworkAPICall(API.mark_as_allread, "", true, false);
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
                EncryptionData notification = new EncryptionData();
                notification.setPage("" + mPage);
                String getnotificationdataencryptionDatadoseStr = new Gson().toJson(notification);
                String getnotificationdatadoseStrScr = AES.encrypt(getnotificationdataencryptionDatadoseStr);
                return service.getNotification(getnotificationdatadoseStrScr);


            case API.mark_as_allread:
                EncryptionData notificationallread = new EncryptionData();
                notificationallread.setPage("" + mPage);
                String getnotificationalldataencryptionDatadoseStr = new Gson().toJson(notificationallread);
                String getnotificationalldatadoseStrScr = AES.encrypt(getnotificationalldataencryptionDatadoseStr);
                return service.MarkAllRead(getnotificationalldatadoseStrScr);

        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.get_notification_data:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        server_time = jsonstring.optLong("time") * 1000;
                        isPaginationAvailable = true;
                        if (status) {
                            notificationdata = new Gson().fromJson(jsonstring.toString(), NotificationdataModel.class);

                            if (notificationdata.getData() != null) {
                                int oldarrysize = notificationdata.getData().size();
                                if (notificationdata.getData().size() > 0) {
                                    notificationlist.addAll(notificationdata.getData());
                                    notificationAdapter.notifyItemRangeInserted(notificationlist.size(), notificationlist.size() - oldarrysize);
                                }
                            } else {
                                Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
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
                                    no_data_found_RL.setVisibility(View.GONE);
                                    notificationrecyceler.setVisibility(View.VISIBLE);
                                    notificationAdapter = new NotificationAdapter(this, notificationlist);
                                    new ItemTouchHelper(itemtouchHelperCallback).attachToRecyclerView(notificationrecyceler);
                                    notificationrecyceler.setAdapter(notificationAdapter);
                                    notificationAdapter.notifyDataSetChanged();
                                } else {
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                    notificationrecyceler.setVisibility(View.GONE);
                                }
                            } else {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                notificationrecyceler.setVisibility(View.GONE);
                            }
                        }
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                    } else {

                        if (!status) {
                            no_data_found_RL.setVisibility(View.VISIBLE);
                            notificationrecyceler.setVisibility(View.GONE);
                        }

                        isPaginationAvailable = false;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }

//                        if (jsonstring.getString("message").equalsIgnoreCase("No Notification Found.")) {
//
//                        } else {
//                            no_data_found_RL.setVisibility(View.VISIBLE);
//                            notificationrecyceler.setVisibility(View.GONE);
//                        }
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case API.mark_as_allread:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        //status=false;
                        for (int i = 0; i < notificationlist.size(); i++) {
                            notificationlist.get(i).setViewState("1");
                        }
                        notificationAdapter.notifyDataSetChanged();
                        SharedPreference.getInstance().putInt(Const.NOTIFICATION_COUNT, 0);
                        ShortcutBadger.applyCount(getApplicationContext(), SharedPreference.getInstance().getInt(Const.NOTIFICATION_COUNT));


//                        hit_api_for_notificationdata();
                    } else {
                        RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
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
            case API.get_notification_data:
                if (paginationLoader != null && paginationLoader.isShown()) {
                    paginationLoader.setVisibility(View.GONE);
                }
                // Toast.makeText(this, jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    ItemTouchHelper.SimpleCallback itemtouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            /*notificationlist.remove(viewHolder.getAdapterPosition());
            notificationAdapter.notifyDataSetChanged();*/

            int position = viewHolder.getAdapterPosition();
            notificationAdapter.selectedpositionid = notificationlist.get(position).getId();
            notificationAdapter.selectedposition = position;
            notificationAdapter.delete_notification_api();
        }
    };


}