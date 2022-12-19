package com.utkarshnew.android.Utils.Network;

import static com.utkarshnew.android.Utils.Helper.getMaintanaceDialog;
import static com.utkarshnew.android.Utils.Helper.isvisible;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.APITABLE;
import com.utkarshnew.android.table.PigibagTable;
import com.utkarshnew.android.table.UserWiseCourseTable;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MainFragment extends Fragment {
    public Activity activity;
    String id;
    UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        Helper.enableScreenShot(activity);
    }

    public void NetworkAPICall(final String apiType, final String typeApi, final boolean showprogress, final boolean multipleAPI, boolean isMiddle) {
        try {
            Log.e(((Activity) activity).getLocalClassName(), "++++++++++++++++" + apiType);
            APIInterface service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
            if (Helper.isConnected(activity)) {
                if (showprogress) {
                    Helper.showProgressDialog(activity);
                }


                Call<String> call;
                if (apiType.equals("changedetecter")) {
                    EncryptionData metaindexencryptionData = new EncryptionData();
                    metaindexencryptionData.setUser_id("1");
                    String metaindexencryptionDatadoseStr = new Gson().toJson(metaindexencryptionData);
                    String metaindexdoseStrScr = AES.encrypt(metaindexencryptionDatadoseStr);
                    call = service.getchangedetector(metaindexdoseStrScr);
                } else {
                    call = getAPIB(apiType, typeApi, service);
                }

//                Call<String> call = getAPIB(apiType, typeApi, service);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                        if (!multipleAPI) {
                            Helper.dismissProgressDialog();
                        }
                        if (response.body() != null && response.isSuccessful()) {
                            //String jsonString = EncryptUtils.decryptMsg(encryptedStr.getBytes("UTF-8"), secretKey));
                            String jsonString = null;
//                            try {
//                                jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
//                            }catch (Exception e){
//                                jsonString = response.body();
//                            }

                            try {
                                if (apiType != API.user_logout) {
                                    jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());
                                    Log.d("onResponse: ", "API_RES" + jsonString);
                                }
                            } catch (Exception e) {
                                jsonString = response.body();
                            }

                            //String jsonString = response.body();
                            try {

                                if (apiType.equalsIgnoreCase("changedetecter")) {
                                    if (jsonString != null && !jsonString.isEmpty()) {
                                        JSONObject jsonObject = new JSONObject(jsonString);
                                        MakeMyExam.setTime_server((Long.parseLong(jsonObject.optString("time")))*1000);

                                        if (!MakeMyExam.userId.equalsIgnoreCase("0")) {

                                            if (jsonObject.has("auth_code")) {
                                                if (jsonObject.optString("auth_code").equals("100100")) {
                                                    RetrofitResponse.GetApiData(getActivity(), jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                                                }
                                            } else
                                                checkandupdateversion(jsonObject);
                                        }
                                        JSONObject data = jsonObject.getJSONObject("data");
                                        if (data.has("version_control")) {
                                            String androidv = data.getJSONObject("version_control").optString("version");
                                            String androidmv = data.getJSONObject("version_control").optString("min_version");
                                            String androidType = data.getJSONObject("version_control").optString("force_update");
                                            String breakFrom = data.getJSONObject("version_control").optString("break_from");
                                            String breakTo = data.getJSONObject("version_control").optString("break_to");
                                            if (data.getJSONObject("version_control").has("feeds"))
                                            {
                                                String feeds = data.getJSONObject("version_control").optString("feeds");
                                                MakeMyExam.setFeeds(feeds);
                                            }


                                            int aCode = Integer.parseInt(androidv);
                                            int minVersion = Integer.parseInt(androidmv);
                                            if (Long.parseLong(breakFrom) < System.currentTimeMillis() && Long.parseLong(breakTo) > System.currentTimeMillis()) {
                                                getMaintanaceDialog(((Activity) activity), breakFrom, breakTo);
                                            } else {
                                                if (androidType.equalsIgnoreCase("0")) {
                                                    if (isvisible.equalsIgnoreCase("0")) {
                                                        if (aCode > Helper.getVersionCode(((Activity) activity)) || Helper.getVersionCode(((Activity) activity)) < minVersion) {
                                                            Helper.getVersionUpdateDialog(((Activity) activity), (Helper.getVersionCode(((Activity) activity)) < minVersion ? "1" : androidType));
                                                        }
                                                    }
                                                } else {
                                                    isvisible = "0";
                                                    if (aCode > Helper.getVersionCode(((Activity) activity)) || Helper.getVersionCode(((Activity) activity)) < minVersion) {
                                                        Helper.getVersionUpdateDialog(((Activity) activity), (Helper.getVersionCode(((Activity) activity)) < minVersion ? "1" : androidType));
                                                    }
                                                }

                                            }
                                        }
                                    } else {
                                        Toast.makeText(activity, activity.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                                        ErrorCallBack(activity.getResources().getString(R.string.jsonparsing_error_message), apiType, typeApi);
                                    }
                                }


                                if (jsonString != null && !jsonString.isEmpty()) {
                                    JSONObject jsonObject = new JSONObject(jsonString);
                                    MakeMyExam.setTime_server((Long.parseLong(jsonObject.optString("time")))*1000);

                                    if (jsonObject.has("auth_code") && jsonObject.optString("auth_code").equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                                        RetrofitResponse.GetApiData(activity, jsonObject.getString(Const.AUTH_CODE), jsonObject.getString(Const.MESSAGE), false);

                                    } else {
                                        if (!MakeMyExam.userId.equalsIgnoreCase("0")) {
                                            try {
                                                if (!utkashRoom.isOpen()) {
                                                    utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
                                                    utkashRoom.getOpenHelper().getWritableDatabase();
                                                    utkashRoom.getOpenHelper().getReadableDatabase();
                                                    utkashRoom.getOpenHelper().setWriteAheadLoggingEnabled(true);
                                                }
                                            } catch (Exception e) {

                                                e.printStackTrace();
                                            }

                                            if (utkashRoom.getpigibag().isRecordExistsUserId(MakeMyExam.userId)) {
                                                if ((Long.parseLong(utkashRoom.getpigibag().getpigidetail(MakeMyExam.userId).getCdtimestamp())) < (jsonObject.optLong("cd_time"))) {
                                                    NetworkAPICall("changedetecter", "", false, false, false);
                                                }
                                                utkashRoom.getpigibag().updaterecord(MakeMyExam.userId, String.valueOf(jsonObject.optLong("cd_time")));
                                            } else {
                                                PigibagTable pigibag = new PigibagTable();
                                                pigibag.setUser_id(MakeMyExam.getUserId());
                                                pigibag.setCdtimestamp(String.valueOf(jsonObject.optLong("cd_time")));
                                                NetworkAPICall("changedetecter", "", false, false, false);
                                                utkashRoom.getpigibag().addApiedata(pigibag);
                                            }
                                        }
                                    }
                                    SuccessCallBack(jsonObject, apiType, typeApi, showprogress);

                                } else {
                                    Toast.makeText(activity, activity.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                                    ErrorCallBack(activity.getResources().getString(R.string.jsonparsing_error_message), apiType, typeApi);
                                }
                            } catch (JSONException e1) {
                                Toast.makeText(activity, activity.getResources().getString(R.string.exception_api_error_message), Toast.LENGTH_SHORT).show();
                                ErrorCallBack(activity.getResources().getString(R.string.exception_api_error_message), apiType, typeApi);
                            }
                        } else {
                            Toast.makeText(activity, activity.getResources().getString(R.string.exception_api_error_message), Toast.LENGTH_SHORT).show();
                            ErrorCallBack(activity.getResources().getString(R.string.exception_api_error_message), apiType, typeApi);
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Helper.dismissProgressDialog();
                        Toast.makeText(activity, activity.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                        ErrorCallBack(activity.getResources().getString(R.string.exception_api_error_message), apiType, typeApi);
                    }
                });
            } else {
                if (getActivity() != null && !getActivity().isDestroyed())
                    Helper.dismissProgressDialog();
                ErrorCallBack(getResources().getString(R.string.internet_error_message), apiType, typeApi);
            }
        } catch (Exception e) {
            Log.d("ActivityNetwork", "NetworkAPICall: " + e.getMessage());
        }

    }

    public void onSessionExpired() {
        Helper.SignOutUser(activity);
    }

    public void showProgress() {
        Helper.showProgressDialog(activity);
    }

    public void hideProgress() {
        Helper.dismissProgressDialog();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Helper.dismissProgressDialog();
    }

    public abstract Call<String> getAPIB(String apitype, String typeApi, APIInterface service);

    public abstract void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException;

    public abstract void ErrorCallBack(String jsonstring, String apitype, String typeApi);


    private void checkandupdateversion(JSONObject jsonObject) {
        try {

            if (jsonObject.getJSONObject("data").getJSONObject("master").has("ut_009")) {
                if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_009")) {
                    if (Float.parseFloat(utkashRoom.getapidao().getapidetail("ut_009", MakeMyExam.userId).getVersion()) < Float.parseFloat(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_009"))) {
                        utkashRoom.getapidao().updateversion(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_009"), MakeMyExam.userId, "ut_009");
                        utkashRoom.getLaunguages().deletedata();
                        utkashRoom.getMasterAllCatDao().deletedata();
                        utkashRoom.getMastercatDao().deletedata();
                        utkashRoom.getcoursetypemaster().deletedata();
                    }
                } else {
                    APITABLE apiMangerTable = new APITABLE();
                    apiMangerTable.setApicode("ut_009");
                    apiMangerTable.setApiname("master_content");
                    apiMangerTable.setInterval("0");
                    apiMangerTable.setUser_id(MakeMyExam.userId);
                    apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                    apiMangerTable.setCdtimestamp("0");
                    apiMangerTable.setVersion("0.000");
                    utkashRoom.getapidao().addUser(apiMangerTable);
                }
            }

            if (jsonObject.getJSONObject("data").getJSONObject("master").has("ut_012")) {
                if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_012")) {
                    if (Float.parseFloat(utkashRoom.getapidao().getapidetail("ut_012", MakeMyExam.userId).getVersion()) < Float.parseFloat(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_012"))) {
                        utkashRoom.getapidao().updateversion(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_012"), MakeMyExam.userId, "ut_012");
                        utkashRoom.getMyCourseDao().deletedata();
                    }
                } else {
                    APITABLE apiMangerTable = new APITABLE();
                    apiMangerTable.setApicode("ut_012");
                    apiMangerTable.setApiname("get_my_courses");
                    apiMangerTable.setInterval("0");
                    apiMangerTable.setUser_id(MakeMyExam.userId);
                    apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                    apiMangerTable.setCdtimestamp("0");
                    apiMangerTable.setVersion("0.000");
                    utkashRoom.getapidao().addUser(apiMangerTable);
                }
            }

            if (jsonObject.getJSONObject("data").getJSONObject("master").has("ut_010")) {
                if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_010")) {
                    if (Float.parseFloat(utkashRoom.getapidao().getapidetail("ut_010", MakeMyExam.userId).getVersion()) < Float.parseFloat(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_010"))) {
                        utkashRoom.getapidao().updateversion(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_010"), MakeMyExam.userId, "ut_010");
                        utkashRoom.getCoursedata().deletedata();
                        utkashRoom.getHomeApiStatusdata().deletedata();
                    }
                } else {
                    APITABLE apiMangerTable = new APITABLE();
                    apiMangerTable.setApicode("ut_010");
                    apiMangerTable.setApiname("get_courses");
                    apiMangerTable.setInterval("0");
                    apiMangerTable.setUser_id(MakeMyExam.userId);
                    apiMangerTable.setTimestamp(String.valueOf(jsonObject.optLong("time")));
                    apiMangerTable.setCdtimestamp("0");
                    apiMangerTable.setVersion("0.000");
                    utkashRoom.getapidao().addUser(apiMangerTable);
                }
            }
            if (jsonObject.getJSONObject("data").has("uw_master")) {
                for (int i = 0; i < jsonObject.getJSONObject("data").getJSONArray("uw_master").length(); i++) {
                    UserWiseCourseTable userWiseCourseTable = new Gson().fromJson(jsonObject.getJSONObject("data").getJSONArray("uw_master").get(i).toString(), UserWiseCourseTable.class);

                    if (utkashRoom.getCourseDetaildata().isRecordExistsUserId(MakeMyExam.userId, userWiseCourseTable.getMeta_id())) {

                        if (Long.parseLong(userWiseCourseTable.getExp()) < jsonObject.optLong("time")) {
                            utkashRoom.getCourseDetaildata().deletecoursedetail(userWiseCourseTable.getMeta_id(), MakeMyExam.userId);
                        } else {
                            if (utkashRoom.getuserwisecourse().is_api_code_exits(MakeMyExam.userId, userWiseCourseTable.getMeta_id())) {
                                if (Float.parseFloat(utkashRoom.getuserwisecourse().getapidetail(userWiseCourseTable.getMeta_id(), MakeMyExam.userId).getVersion()) < Float.parseFloat(userWiseCourseTable.getVersion())) {
                                    utkashRoom.getCourseDetaildata().deletecoursedetail(userWiseCourseTable.getMeta_id(), MakeMyExam.userId);
                                }
                                utkashRoom.getuserwisecourse().update_api_version(userWiseCourseTable.getMeta_id(), MakeMyExam.userId, userWiseCourseTable.getVersion());
                            } else {
                                userWiseCourseTable.setUserid(MakeMyExam.userId);
                                utkashRoom.getuserwisecourse().addUser(userWiseCourseTable);
                            }
                        }
                    } else {
                        utkashRoom.getCourseDetaildata().deletecoursedetail(userWiseCourseTable.getMeta_id(), MakeMyExam.userId);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
