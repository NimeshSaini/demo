package com.utkarshnew.android.purchasehistory;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.razorpay.PaymentResultListener;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.purchasehistory.model.Data;
import com.utkarshnew.android.purchasehistory.model.PurchaseHistoryModel;
import com.utkarshnew.android.purchasehistory.adapter.PurchaseHistoryAdapter;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.GenericUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class PurchaseHistory extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, PaymentResultListener {

    private ImageView image_back;
    TextView toolbarTitleTV;
    RecyclerView purchase_history;
    PurchaseHistoryAdapter purchaseHistoryAdapter;
    NetworkCall networkCall;
    ArrayList<Data> history_list = new ArrayList<>();
    PurchaseHistoryModel pucPurchaseHistoryModel;
    public static String razorkey = "";
    private int mPage = 1;
    private boolean loading = false;
    private SwipeRefreshLayout pullToReferesh;
    private NestedScrollView nestedScrollView;

    boolean status;
    private boolean isPaginationAvailable = true;
    ProgressBar paginationLoader;
    int server_time;

    RelativeLayout no_data_found_RL;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.purchase_history_activity);
        try {
            Window window = this.getWindow();

            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            toolbarTitleTV = findViewById(R.id.toolbarTitleTV);
            purchase_history = findViewById(R.id.purchase_history);
            purchase_history.setLayoutManager(new LinearLayoutManager(this));
            purchase_history.setNestedScrollingEnabled(false);
            image_back = findViewById(R.id.image_back);

            networkCall = new NetworkCall(this, this);
            pullToReferesh = findViewById(R.id.pullto_referesh);
            nestedScrollView = findViewById(R.id.nested_scroll);
            paginationLoader = findViewById(R.id.progressBar);

            no_data_found_RL = findViewById(R.id.no_data_found_RL);
            backBtn = findViewById(R.id.backBtn);

            backBtn.setOnClickListener(new OnSingleClickListener(() -> {
                finish();
                return null;
            }));

            if (history_list.size() == 0) {
                hit_api_for_data(true);
            } else {
                no_data_found_RL.setVisibility(View.GONE);
                purchase_history.setVisibility(View.VISIBLE);
                purchaseHistoryAdapter = new PurchaseHistoryAdapter(this, history_list, server_time);
                purchase_history.setAdapter(purchaseHistoryAdapter);
            }


            pullToReferesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initialState();
                    status = false;
                    hit_api_for_data(true);
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


            image_back.setOnClickListener(new OnSingleClickListener(() -> {
                finish();
                return null;
            }));

        } catch (Exception e) {

        }
    }


    public void refreshdata() {
        initialState();
        status = false;
        hit_api_for_data(true);
    }

    private void hit_api_for_data(boolean showProgress) {
        networkCall.NetworkAPICall(API.get_my_orders, "", showProgress, false);

    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.payment_gateway_credentials:
                EncryptionData encryptionData1 = new EncryptionData();
                encryptionData1.setUser_id(MakeMyExam.userId);

                String datajson = new Gson().toJson(encryptionData1);
                String getweay = AES.encrypt(datajson);
                return service.payment_gateway_credentials(getweay);


            case API.get_my_orders:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setPage("" + mPage);
                getcoursedataencryptionData.setType("0");
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.get_my_orders(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.payment_gateway_credentials:
                try {
                    if (jsonstring.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONObject data = jsonstring.getJSONObject(Const.DATA);
                        JSONObject rzpdata = data.getJSONObject("rzp");
                        razorkey = rzpdata.optString("key");
                        SharedPreference.getInstance().putString("key", razorkey);

                    } else {
                        if (!GenericUtils.isEmpty(jsonstring.getString(Const.AUTH_CODE)))
                            RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.get_my_orders:
                try {
                    if (jsonstring.getString("status").equalsIgnoreCase("true")) {
                        server_time = Integer.parseInt(jsonstring.optString("time"));

                        isPaginationAvailable = true;
                        if (status) {
                            pucPurchaseHistoryModel = new Gson().fromJson(jsonstring.toString(), PurchaseHistoryModel.class);
                            if (pucPurchaseHistoryModel.getData() != null) {
                                int oldarrysize = history_list.size();
                                if (history_list.size() > 0) {
                                    history_list.addAll(pucPurchaseHistoryModel.getData());
                                    purchaseHistoryAdapter.change_time(server_time);
                                    purchaseHistoryAdapter.notifyItemRangeInserted(history_list.size() - 1, history_list.size() - oldarrysize);
                                }
                            }
                        } else {
                            initialState();
                            pucPurchaseHistoryModel = new Gson().fromJson(jsonstring.toString(), PurchaseHistoryModel.class);
                            if (history_list != null && history_list.size() != 0) {
                                history_list.clear();
                            }
                            if (pucPurchaseHistoryModel.getData() != null) {
                                history_list.addAll(pucPurchaseHistoryModel.getData());
                                if (history_list.size() > 0) {
                                    no_data_found_RL.setVisibility(View.GONE);
                                    purchase_history.setVisibility(View.VISIBLE);
                                    purchaseHistoryAdapter = new PurchaseHistoryAdapter(this, history_list, server_time);
                                    purchase_history.setAdapter(purchaseHistoryAdapter);
                                    purchaseHistoryAdapter.notifyDataSetChanged();
                                } else {
                                    no_data_found_RL.setVisibility(View.VISIBLE);
                                    purchase_history.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }
                    } else {
                        server_time = (int) MakeMyExam.getTime_server();
                        if (purchaseHistoryAdapter != null) {
                            purchaseHistoryAdapter.change_time(server_time);
                        }

                        isPaginationAvailable = false;
                        if (paginationLoader != null && paginationLoader.isShown()) {
                            paginationLoader.setVisibility(View.GONE);
                        }

                        if (!status){
                            if (jsonstring.getString("message").equalsIgnoreCase("No Course Found.")) {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                purchase_history.setVisibility(View.GONE);
                            } else {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                purchase_history.setVisibility(View.GONE);
                            }
                        }else{
                            if (jsonstring.getString("message").equalsIgnoreCase("No Course Found.")) {

                            } else {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                purchase_history.setVisibility(View.GONE);
                            }
                        }

                        if (jsonstring.has(Const.AUTH_CODE))
                            if (!GenericUtils.isEmpty(jsonstring.getString(Const.AUTH_CODE)))
                                RetrofitResponse.GetApiData(this, jsonstring.has(Const.AUTH_CODE) ? jsonstring.getString(Const.AUTH_CODE) : "", jsonstring.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void initialState() {
        mPage = 1;
        loading = true;
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        switch (apitype) {
            case API.get_my_orders:
                if (paginationLoader != null && paginationLoader.isShown()) {
                    paginationLoader.setVisibility(View.GONE);
                }
                // Toast.makeText(this, jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        try {
            purchaseHistoryAdapter.onSuccessListner.onSuccess(s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        try {
            purchaseHistoryAdapter.onSuccessListner.onFailure("" + i + "~!@#$%^&" + s);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreference.getInstance().getString("key") != null && !SharedPreference.getInstance().getString("key").equalsIgnoreCase("")) {
            razorkey = SharedPreference.getInstance().getString("key");
        } else {
            networkCall.NetworkAPICall(API.payment_gateway_credentials, "", false, false);

        }

    }
}