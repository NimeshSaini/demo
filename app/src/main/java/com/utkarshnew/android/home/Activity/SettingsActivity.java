package com.utkarshnew.android.home.Activity;

import static com.utkarshnew.android.Utils.Helper.gotoActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Intro.Activity.IntroActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.AddressTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Webview.WebViewActivty;
import com.utkarshnew.android.address.adapter.AddressAdapter;
import com.utkarshnew.android.address.interfaces.onItemSelected;
import com.utkarshnew.android.address.model.Address;
import com.utkarshnew.android.address.model.Data;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCities;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCitiesData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener, NetworkCall.MyNetworkCallBack,onItemSelected,  PopupMenu.OnMenuItemClickListener {

    private UtkashRoom utkashRoom;

    com.utkarshnew.android.address.model.Data addressdata;
    ArrayList<Data> addresslist = new ArrayList<>();
    private TextView mPrivacyPolicy,change_prefence, addresslayout,mOpenSourceLicenses, mTermsConditionsText, mWriteRview, mLogout, mTeamPage;
    private Toolbar mSettingsToolbar;
    private Button clearCacheButton;
    RecyclerView recyclerViewaddress;
    BottomSheetDialog watchlist = null;

    long mLastClickTime_frame5;

    boolean is_update = false;

    StatesCities states;
    StatesCities cities;
    String state_id = "", state_name = "";
    String deleivery_charges = "0", city_name = "",city_id="";
    EncryptionData address = null;
    TextView state, city;

    String clickType="";

    private SwitchCompat makeBetterSwitch;
    private FirebaseAnalytics mFirebaseAnalytics;
    private ImageButton screenMirroring;
    private TextView versionTV;
    NetworkCall networkCall;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.settings_activity);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.black));
        }
        activity =this;
        networkCall =new NetworkCall(this,this);

        utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

        init();
    }

    private void init() {
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mPrivacyPolicy = findViewById(R.id.privacypolicytext);
        change_prefence = findViewById(R.id.change_prefence);
        addresslayout = findViewById(R.id.address);

        mOpenSourceLicenses = findViewById(R.id.opensourcelicensetext);
        mTermsConditionsText = findViewById(R.id.termsofservicetext);
        mSettingsToolbar = findViewById(R.id.settingsactionbar);
        mWriteRview = findViewById(R.id.enterreviewtext);
        clearCacheButton = findViewById(R.id.clearcachebtn);
        clearCacheButton.setOnClickListener(view -> deleteCache());
        mLogout = findViewById(R.id.logouttext);
        mTeamPage = findViewById(R.id.builtbytext);
        versionTV = findViewById(R.id.versionTV);

        makeBetterSwitch = findViewById(R.id.makebettertoggle);
        makeBetterSwitch.setChecked(true);
        screenMirroring = findViewById(R.id.screencast);

        versionTV.setText(Html.fromHtml("<b>App Version- </b>" + Helper.getVersionName(SettingsActivity.this)));

        screenMirroring.setOnClickListener(v -> {
            Helper.startCast(this);
        });

        addresslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (addresslist.size() > 0) {
                    if (recyclerViewaddress!=null)
                    {
                        if (recyclerViewaddress.getAdapter() != null && recyclerViewaddress.getAdapter() instanceof AddressAdapter) {
                            for (com.utkarshnew.android.address.model.Data addressdats : addresslist) {
                                addressdats.setSelected(false);
                            }
                            bottomsheetAddress(activity, addresslist);
                        } else {
                            AddressAdapter addressAdapter = new AddressAdapter(activity, addresslist, SettingsActivity.this);
                            recyclerViewaddress.setAdapter(addressAdapter);
                        }
                    }else {
                        bottomsheetAddress(activity,addresslist);

                    }

                } else {
                    if (utkashRoom.getAddress().getAllAddress()!=null && utkashRoom.getAddress().getAllAddress().size()>0)
                    {
                        List<AddressTable> addressTables =utkashRoom.getAddress().getAllAddress();
                        for (AddressTable addressTable:addressTables)
                        {
                            Address address =new Address(addressTable.getAddress_one(), addressTable.getAddress_two(), addressTable.getCity(), "INDIA", addressTable.getName(), addressTable.getPhone(), addressTable.getPincode(), addressTable.getState(), addressTable.getDelivery_price(),addressTable.getState_id(),addressTable.getCity_id());
                            com.utkarshnew.android.address.model.Data data  =new com.utkarshnew.android.address.model.Data(address,addressTable.getAddress_id(),false);
                            addresslist.add(data);
                        }
                        bottomsheetAddress(activity,addresslist);
                    }else
                    networkCall.NetworkAPICall(API.get_my_addresses,"",true,false);
                }
            }
        });


        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Settings_screen");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "screen");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //setup toolbar
        if (getSupportActionBar() == null) {
            setSupportActionBar(mSettingsToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.settings_activity);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black);
            } else {
                return;
            }
        }

        mPrivacyPolicy.setOnClickListener(this);
        change_prefence.setOnClickListener(this);
        mOpenSourceLicenses.setOnClickListener(this);
        mTermsConditionsText.setOnClickListener(this);
        mWriteRview.setOnClickListener(this);
        mLogout.setOnClickListener(this);
        mTeamPage.setOnClickListener(this);

    }


    public void bottomsheetAddress(Context context, ArrayList<com.utkarshnew.android.address.model.Data> addresslist) {
        try {
            if (watchlist == null) {
                watchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
                watchlist.setContentView(R.layout.address_recyclerview);
            }
            Objects.requireNonNull(watchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            watchlist.setCancelable(true);
            FrameLayout bottomSheet = watchlist.findViewById(R.id.design_bottom_sheet);

            BottomSheetBehavior.from(Objects.requireNonNull(bottomSheet)).setState(BottomSheetBehavior.STATE_EXPANDED);
            BottomSheetBehavior.from(bottomSheet).setDraggable(false);

            RelativeLayout layout_address = watchlist.findViewById(R.id.layout_address);

            recyclerViewaddress = watchlist.findViewById(R.id.address_reyccler);
            Button raddaddress = watchlist.findViewById(R.id.addaddress);
            Button proceed = watchlist.findViewById(R.id.proceed);
            proceed.setVisibility(View.GONE);
            if (recyclerViewaddress != null) {
                Objects.requireNonNull(recyclerViewaddress).setHasFixedSize(true);
            }
            AddressAdapter addressAdapter = new AddressAdapter(context, addresslist, this);
            recyclerViewaddress.setAdapter(addressAdapter);

            Objects.requireNonNull(raddaddress).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressdata = null;
                    if (addresslist.size() > 0 && addresslist.size() >= 7) {
                        Toast.makeText(activity, "No More add address", Toast.LENGTH_SHORT).show();
                    } else {
                        for (com.utkarshnew.android.address.model.Data addressdats : addresslist) {
                            addressdats.setSelected(false);
                        }
                        if (recyclerViewaddress.getAdapter() == null) {
                            AddressAdapter addressAdapter = new AddressAdapter(context, addresslist, (onItemSelected) context);
                            recyclerViewaddress.setAdapter(addressAdapter);
                        } else {
                            AddressAdapter addressAdapter = (AddressAdapter) recyclerViewaddress.getAdapter();
                            addressAdapter.updateItem(addresslist);
                        }
                        watchlist.dismiss();
                        watchlist.cancel();
                        addressDialog(addressdata);
                    }

                }
            });
            Objects.requireNonNull(layout_address).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    watchlist.dismiss();
                    watchlist.cancel();
                }
            });

            watchlist.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    watchlist.dismiss();
                    watchlist.cancel();
                }
            });

            if (!watchlist.isShowing()) {
                if(!isFinishing() && watchlist != null) {
                    watchlist.show();
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteCache() {
        try {
            Toast.makeText(this, "Removing application cache", Toast.LENGTH_SHORT).show();
            // customSnackBar.show("Removing application cache", CustomSnackBar.LENGTH_SHORT);
            File dir = getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            Log.e("Settings", "********** ERROR AT UTKARSH CACHE DELETE ***********", e);
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Handle Close Button CLicks
        finish(); //close this activity
        return false;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.privacypolicytext:
                Intent intent3 = new Intent(this, WebViewActivty.class);
                intent3.putExtra("type", "Privacy Policy");
                intent3.putExtra("url", API.privacy_policy);
                Helper.gotoActivity(intent3, SettingsActivity.this);
                break;

                case R.id.change_prefence:
                    Intent intent =new Intent(this, IntroActivity.class);
                    gotoActivity(intent, this);
                break;
            case R.id.opensourcelicensetext:
                Intent libUsedIntent = new Intent(SettingsActivity.this, LibrariesUsedActivity.class);
                Helper.gotoActivity(libUsedIntent, SettingsActivity.this);

                break;
            case R.id.termsofservicetext:
                Intent intent4 = new Intent(this, WebViewActivty.class);
                intent4.putExtra("type", "Terms of Service");
                intent4.putExtra("url", API.TERMS_AND_CONDITIONS);
                Helper.gotoActivity(intent4, SettingsActivity.this);
                break;
            case R.id.enterreviewtext:
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(myAppLinkToMarket);
                } catch (ActivityNotFoundException e) {
                    //  customSnackBar.showActionSnackBar("Unable to find market app", "OK", CustomSnackBar.LENGTH_INDEFINITE, () -> customSnackBar.dismiss());
                }
                break;
            case R.id.logouttext:
                getLogoutDialog(this, getString(R.string.logout_title), getString(R.string.logout_confirmation_message));

                break;
            case R.id.builtbytext:
                String url3 = "https://fundamentalias.com/";
                openWebBrowser(url3);
                break;
            default:
                break;
        }


    }

    public void getLogoutDialog(final Activity ctx, final String title, final String message) {
        DialogUtils.makeDialog(this, title, message,
                getResources().getString(R.string.yes), getResources().getString(R.string.no), true, new DialogUtils.onDialogUtilsOkClick() {
                    @Override
                    public void onOKClick() {
                        Helper.SignOutUser(SettingsActivity.this);
                    }
                }, new DialogUtils.onDialogUtilsCancelClick() {
                    @Override
                    public void onCancelClick() {

                    }
                });
    }

    private void showLogoutDialog() {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:

                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    dialog.dismiss();
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(String.format("Logout %s?", getResources().getString(R.string.app_name)));
        builder.setMessage("Are you sure you want to logout from the application?").setPositiveButton("YES", dialogClickListener)
                .setNegativeButton("NO", dialogClickListener).show();
    }


    @SuppressLint("QueryPermissionsNeeded")
    private void openWebBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {

            case API.API_CITY:
                EncryptionData cityencryptionData = new EncryptionData();
                cityencryptionData.setState_id(state_id);
                String cityencryptionDatadoseStr = new Gson().toJson(cityencryptionData);
                String citydoseStrScr = AES.encrypt(cityencryptionDatadoseStr);
                return service.GetCity(citydoseStrScr);


            case API.API_STATE:
                EncryptionData stateencryptionData = new EncryptionData();
                stateencryptionData.setUser_id(MakeMyExam.getUserId());
                String stateencryptionDatadoseStr = new Gson().toJson(stateencryptionData);
                String statedoseStrScr = AES.encrypt(stateencryptionDatadoseStr);
                return service.GetState(statedoseStrScr);

            case API.get_my_addresses:
                EncryptionData encryptionData = new EncryptionData();
                encryptionData.setUser_id("");
                return service.get_my_addresses(AES.encrypt(new Gson().toJson(encryptionData)));

            case API.delete_my_address:
                EncryptionData encryptionDatadelete = new EncryptionData();
                encryptionDatadelete.setId(addressdata.getId());
                return service.delete_my_address(AES.encrypt(new Gson().toJson(encryptionDatadelete)));

            case API.add_update_address:
                EncryptionData encryptionData_new = new EncryptionData();
                encryptionData_new.setAddress(new Gson().toJson(address));
                encryptionData_new.setId(address.getId());
                String address_dataencrypt = AES.encrypt(new Gson().toJson(encryptionData_new));
                return service.add_update_address(address_dataencrypt);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {

            case API.delete_my_address:
                if (jsonobject.getBoolean("status")) {
                    utkashRoom.getAddress().delete_address(addressdata.getId());
                    addresslist.remove(addressdata);
                    addressdata=null;
                    if (recyclerViewaddress.getAdapter() == null) {
                        AddressAdapter addressAdapter = new AddressAdapter(activity, addresslist, this);
                        recyclerViewaddress.setAdapter(addressAdapter);
                    } else {
                        AddressAdapter addressAdapter = (AddressAdapter) recyclerViewaddress.getAdapter();
                        addressAdapter.updateItem(addresslist);
                    }

                }
                break;
            case API.API_STATE:
                if (jsonobject.getBoolean("status")) {
                    states = new Gson().fromJson(jsonobject.toString(), StatesCities.class);
                    if (!state_name.equals("")){
                        networkCall.NetworkAPICall(API.API_CITY,"",true,false);
                    }
                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                }
                break;
            case API.API_CITY:
                if (jsonobject.getBoolean("status")) {
                    cities = new Gson().fromJson(jsonobject.toString(), StatesCities.class);
                    if (city_id.equals(""))
                    {
                        city.setText(cities.getData().get(0).getName());
                        city_id  =cities.getData().get(0).getId();
                        city_name =cities.getData().get(0).getName();
                    }

                } else {
                    RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);

                }
                break;
            case API.add_update_address:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {

                        Toast.makeText(activity,jsonobject.getString(Const.MESSAGE),Toast.LENGTH_SHORT).show();
                        JSONObject data = jsonobject.getJSONObject(Const.DATA);
                        com.utkarshnew.android.address.model.Data address = new Gson().fromJson(data.toString(), com.utkarshnew.android.address.model.Data.class);
                        if (is_update) {
                            for (int i = 0; i < addresslist.size(); i++) {
                                if (addresslist.get(i).getId().equals(addressdata.getId())) {
                                    addresslist.set(i, address);

                                    utkashRoom.getAddress().update_address(addressdata.getId(),
                                            address.getAddress().getAddressOne(),
                                            address.getAddress().getAddressTwo(),
                                            address.getAddress().getState(),
                                            address.getAddress().getCity(),
                                            address.getAddress().getName(),
                                            address.getAddress().getPincode(),
                                            address.getAddress().getPhone(),
                                            address.getAddress().getDelivery_price(),
                                            address.getAddress().getState_id(),
                                            address.getAddress().getCity_id());
                                    break;
                                }
                            }
                            is_update = false;
                        } else {
                            AddressTable addressTable = new AddressTable();
                            addressTable.setAddress_id(address.getId()==null ?"":address.getId());
                            addressTable.setAddress_one(address.getAddress().getAddressOne()==null ?"":address.getAddress().getAddressOne());
                            addressTable.setAddress_two(address.getAddress().getAddressTwo()==null ?"":address.getAddress().getAddressTwo());
                            addressTable.setName(address.getAddress().getName()==null ?"":address.getAddress().getName());
                            addressTable.setCity(address.getAddress().getCity()==null ?"":address.getAddress().getCity());
                            addressTable.setState(address.getAddress().getState()==null ?"":address.getAddress().getState());
                            addressTable.setPhone(address.getAddress().getPhone()==null ?"":address.getAddress().getPhone());
                            addressTable.setCountry("India");
                            addressTable.setCity_id(address.getAddress().getCity_id()==null ?"":address.getAddress().getCity_id());
                            addressTable.setState_id(address.getAddress().getState_id()==null ?"":address.getAddress().getState_id());
                            addressTable.setPincode(address.getAddress().getPincode()==null ?"":address.getAddress().getPincode());
                            addressTable.setDelivery_price(address.getAddress().getDelivery_price().equals("") ? "0":address.getAddress().getDelivery_price());
                            utkashRoom.getAddress().addAddress(addressTable);
                            addresslist.add(address);
                        }
                        state_id = "";
                        city_id = "";
                        city_name = "";
                        state_name="";

                    } else {
                        RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case API.get_my_addresses:
                try {
                    if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                        JSONArray jsonArray = jsonobject.getJSONArray(Const.DATA);
                        addresslist.clear();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            com.utkarshnew.android.address.model.Data address = new Gson().fromJson(jsonArray.get(i).toString(), com.utkarshnew.android.address.model.Data.class);
                            addresslist.add(address);

                            if (!utkashRoom.getAddress().is_address_exit(address.getId())) {
                                AddressTable addressTable = new AddressTable();
                                addressTable.setAddress_id(address.getId());
                                addressTable.setAddress_one(address.getAddress().getAddressOne());
                                addressTable.setAddress_two(address.getAddress().getAddressTwo());
                                addressTable.setName(address.getAddress().getName());
                                addressTable.setCity(address.getAddress().getCity());
                                addressTable.setState(address.getAddress().getState());
                                addressTable.setPhone(address.getAddress().getPhone());
                                addressTable.setCountry("India");
                                addressTable.setPincode(address.getAddress().getPincode());
                                addressTable.setCity_id(address.getAddress().getCity_id());
                                addressTable.setState_id(address.getAddress().getState_id());
                                addressTable.setDelivery_price(address.getAddress().getDelivery_price().equals("") ? "0":address.getAddress().getDelivery_price());
                                utkashRoom.getAddress().addAddress(addressTable);
                            }
                        }
                        bottomsheetAddress(activity, addresslist);

                    } else {
                        if (jsonobject.has(Const.AUTH_CODE))
                        {
                            RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                        }else {
                            RetrofitResponse.GetApiData(activity, jsonobject.has(Const.AUTH_CODE) ? jsonobject.getString(Const.AUTH_CODE) : "", jsonobject.getString(Const.MESSAGE), false);
                            addressDialog(addressdata);
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }


    @Override
    public void itemSelect(int pos, @NonNull com.utkarshnew.android.address.model.Data data) {
        addressdata = data;
    }

    @Override
    public void itemClick(int pos, @NonNull com.utkarshnew.android.address.model.Data data) {
        is_update = true;
        addressdata = data;
        if (watchlist != null) {
            watchlist.dismiss();
            watchlist.cancel();
        }
        addressDialog(data);
    }
    private void addressDialog(com.utkarshnew.android.address.model.Data data) {
        try {
            Dialog addressdailog;

            if (SystemClock.elapsedRealtime() - mLastClickTime_frame5 < 1000) {
                return;
            }
            mLastClickTime_frame5 = SystemClock.elapsedRealtime();

            addressdailog = new Dialog(activity);
            addressdailog.setContentView(R.layout.address_dailog);
            ImageView cancel = addressdailog.findViewById(R.id.cancel);
            TextView btn_submit = addressdailog.findViewById(R.id.btn_submit);
            EditText address1 = addressdailog.findViewById(R.id.address1);
            EditText name = addressdailog.findViewById(R.id.name);
            EditText address2 = addressdailog.findViewById(R.id.address2);
            city = addressdailog.findViewById(R.id.city);
            state = addressdailog.findViewById(R.id.state);
            EditText pincode = addressdailog.findViewById(R.id.pincode);
            EditText mobile = addressdailog.findViewById(R.id.mobile);


            state.setOnClickListener(view -> {
                if (states != null && states.getData().size() > 0) {
                    clickType="1";
                    PopupMenu popupMenu = new PopupMenu(activity, state, Gravity.LEFT);
                    for (int i = 0; i < states.getData().size(); i++) {
                        popupMenu.getMenu().add(states.getData().get(i).getName());
                    }
                    popupMenu.setOnMenuItemClickListener(SettingsActivity.this);
                    popupMenu.show();
                } else {
                    networkCall.NetworkAPICall(API.API_STATE,"",true,false);


                }
            });
            city.setOnClickListener(view -> {
                if (cities != null && cities.getData().size() > 0) {
                    clickType="2";
                    PopupMenu popupMenu = new PopupMenu(activity, city, Gravity.LEFT);
                    for (int i = 0; i < cities.getData().size(); i++) {
                        popupMenu.getMenu().add(cities.getData().get(i).getName());
                    }
                    popupMenu.setOnMenuItemClickListener(this);
                    popupMenu.show();
                }else {
                    if (!state_id.equals(""))
                    {
                        networkCall.NetworkAPICall(API.API_CITY,"",true,false);

                    }

                }
            });

            addressdailog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            addressdailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
            addressdailog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            addressdailog.getWindow().setGravity(Gravity.CENTER);
            addressdailog.setCancelable(false);
            addressdailog.setCanceledOnTouchOutside(false);

            if (data != null) {
                if (data.getAddress().getAddressOne() != null) {
                    address1.setText(data.getAddress().getAddressOne());

                }
                if (data.getAddress().getName() != null) {
                    name.setText(data.getAddress().getName());

                }
                if (data.getAddress().getAddressTwo() != null) {
                    address2.setText(data.getAddress().getAddressTwo());

                }
                if (data.getAddress().getCity() != null && !data.getAddress().getCity().equals("")) {
                    city_name =   data.getAddress().getCity();
                     city.setText(data.getAddress().getCity());
                }
                if (data.getAddress().getState() != null && !data.getAddress().getState().equals("")) {
                    state_name = data.getAddress().getState();
                    state.setText(data.getAddress().getState());
                }
                if (data.getAddress().getCity_id() != null && !data.getAddress().getCity_id().equals("")) {
                    city_id = data.getAddress().getCity_id();
                }    if (data.getAddress().getState_id() != null && !data.getAddress().getState_id().equals("")) {
                    state_id = data.getAddress().getState_id();
                }

                if (data.getAddress().getPincode() != null) {
                    pincode.setText(data.getAddress().getPincode());
                }
                if (data.getAddress().getPhone() != null && !data.getAddress().getPhone().equals("null")) {
                    mobile.setText(data.getAddress().getPhone());
                }

            }
            networkCall.NetworkAPICall(API.API_STATE,"",true,false);


            Objects.requireNonNull(btn_submit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (TextUtils.isEmpty(name.getText().toString())) {
                        Toast.makeText(activity, "Name field is required", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(address1.getText().toString())) {
                        Toast.makeText(activity, "Address field is required", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(state_id)) {
                        Toast.makeText(activity, "State field is required", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(city_id)) {
                        Toast.makeText(activity, "City field is required", Toast.LENGTH_SHORT).show();

                    } else if (TextUtils.isEmpty(pincode.getText().toString())) {
                        Toast.makeText(activity, "Pincode field is required", Toast.LENGTH_SHORT).show();
                    }
                    else if (pincode.getText().toString().length()<6) {
                        Toast.makeText(activity, "Please enter valid pincode", Toast.LENGTH_SHORT).show();
                    }
                    else if (TextUtils.isEmpty(mobile.getText().toString().trim())) {
                        Toast.makeText(activity, "Mobile field is required", Toast.LENGTH_SHORT).show();

                    } else if (!Helper.isValidMobile(mobile.getText().toString().trim())) {
                        Toast.makeText(activity, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    } else {
                        addressdailog.dismiss();
                        addressdailog.cancel();
                        address = new EncryptionData();
                        address.setCity(city.getText().toString().trim());
                        address.setPhone(mobile.getText().toString().trim());
                        address.setName(name.getText().toString().trim());
                        address.setState(state.getText().toString().toLowerCase(Locale.ROOT));
                        address.setPincode(pincode.getText().toString());
                        address.setAddressOne(address1.getText().toString().trim());
                        address.setAddressTwo(address2.getText().toString().trim());
                        address.setDelivery_price(deleivery_charges);
                        if (data != null && !data.getId().equals("")) {
                            address.setId(data.getId());
                        } else {
                            address.setId("");
                        }
                        address.setState_id(state_id);
                        address.setCity_id(city_id);
                        address.setCountry("INDIA");
                        networkCall.NetworkAPICall(API.add_update_address,"",true,false);

                    }


                }
            });
            Objects.requireNonNull(cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    is_update =false;
                    city_name ="";
                    state_name="";
                    deleivery_charges ="";
                    addressdailog.dismiss();
                    addressdailog.cancel();
                }
            });

            addressdailog.show();
            addressdailog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    addressdailog.dismiss();
                    addressdailog.cancel();
                }
            });
        } catch (Exception e) {
            is_update = false;
            e.printStackTrace();
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item != null) {
            if (clickType.equals("1")) {
                for (StatesCitiesData statesCitiesData:states.getData())
                {
                    if (item.getTitle().equals(statesCitiesData.getName()))
                    {
                        state_id = "" + statesCitiesData.getId();
                        state_name = "" + statesCitiesData.getName();
                        state.setText(state_name);
                        city.setText("");
                        city_name="";
                        city_id ="";
                        networkCall.NetworkAPICall(API.API_CITY,"",true,false);
                        break;
                    }
                }

            } else if (clickType.equals("2")) {
                for (StatesCitiesData statesCitiesData:cities.getData())
                {
                    if (item.getTitle().equals(statesCitiesData.getName()))
                    {
                        city_name = "" + statesCitiesData.getName();
                        city_id = "" + statesCitiesData.getId();
                        city.setText(city_name);
                        break;
                    }
                }
            }

        }
        return false;
    }

    @Override
    public void delete_address(int pos, @NonNull Data data) {
        addressdata = data;
        networkCall.NetworkAPICall(API.delete_my_address,"",true,false);

    }
}
