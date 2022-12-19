package com.utkarshnew.android.Utils.Network;

import static com.utkarshnew.android.Utils.Helper.getMaintanaceDialog;
import static com.utkarshnew.android.Utils.Helper.isvisible;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.utkarshnew.android.BuildConfig;
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
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Cbc-03 on 05/26/17.
 */

public class NetworkCall {

    MyNetworkCallBack myCBI;
    Context context;
    String id;
    UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());

    public NetworkCall(MyNetworkCallBack callBackInterface, Context context) {
        myCBI = callBackInterface;
        this.context = context;
    }

    public void NetworkAPICall(final String apiType, final String typeApi, final boolean showprogress, boolean ismultipleapi) {
        //  Log.e(((Activity) context).getLocalClassName(), "++++++++++++++++" + apiType);
        APIInterface service = MakeMyExam.getRetrofitInstance().create(APIInterface.class);
        if (Helper.isConnected(context)) {
            if (showprogress)
                Helper.showProgressDialog(context);

            Call<String> call;
            if (apiType.equals("changedetecter")) {
                EncryptionData metaindexencryptionData = new EncryptionData();
                metaindexencryptionData.setUser_id("1");
                String metaindexencryptionDatadoseStr = new Gson().toJson(metaindexencryptionData);
                String metaindexdoseStrScr = AES.encrypt(metaindexencryptionDatadoseStr);
                call = service.getchangedetector(metaindexdoseStrScr);
            } else {
                call = myCBI.getAPIB(apiType, typeApi, service);
            }

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (!ismultipleapi) {
                        Helper.dismissProgressDialog();
                    }
                    if (response.body() != null && response.isSuccessful()) {
                        //String jsonString = response.body();
                        String jsonString = null;
                        try {
                            if (apiType != API.user_logout) {
                                Log.d("prince", "" + response.body());
                                jsonString = AES.decrypt(response.body(), AES.generatekeyAPI(), AES.generateVectorAPI());

                                Log.d("onResponse: ", "API_RES" + jsonString);
                            }
                        } catch (Exception e) {
                            Log.d("prince", "" + e.toString());
                            jsonString = response.body();
                        }
                        try {
                            if (apiType.equalsIgnoreCase("changedetecter")) {
                                if (jsonString != null && !jsonString.isEmpty()) {
                                    JSONObject jsonObject = new JSONObject(jsonString);
                                    MakeMyExam.setTime_server((Long.parseLong(jsonObject.optString("time"))) * 1000);

                                    if (!MakeMyExam.userId.equalsIgnoreCase("0")) {
                                        Log.d("ayush", "" + jsonString);
                                        Log.d("ayush", "" + BuildConfig.VERSION_CODE);
                                        checkandupdateversion(jsonObject);
                                    }
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    if (data.has("version_control")) {
                                        String androidv = data.getJSONObject("version_control").optString("version");
                                        String androidmv = data.getJSONObject("version_control").optString("min_version");
                                        String androidType = data.getJSONObject("version_control").optString("force_update");
                                        String breakFrom = data.getJSONObject("version_control").optString("break_from");
                                        String breakTo = data.getJSONObject("version_control").optString("break_to");
                                        if (data.getJSONObject("version_control").has("feeds")) {
                                            String feeds = data.getJSONObject("version_control").optString("feeds");
                                            MakeMyExam.setFeeds(feeds);
                                        }
                                        int aCode = Integer.parseInt(androidv);
                                        int minVersion = Integer.parseInt(androidmv);
                                        if (Long.parseLong(breakFrom) < System.currentTimeMillis() && Long.parseLong(breakTo) > System.currentTimeMillis()) {
                                            getMaintanaceDialog(((Activity) context), breakFrom, breakTo);
                                        } else {
                                            if (androidType.equalsIgnoreCase("0")) {
                                                if (isvisible.equalsIgnoreCase("0")) {
                                                    if (aCode > Helper.getVersionCode(((Activity) context)) || Helper.getVersionCode(((Activity) context)) < minVersion) {
                                                        Helper.getVersionUpdateDialog(((Activity) context), (Helper.getVersionCode(((Activity) context)) < minVersion ? "1" : androidType));
                                                    }
                                                }
                                            } else {
                                                isvisible = "0";
                                                if (aCode > Helper.getVersionCode(((Activity) context)) || Helper.getVersionCode(((Activity) context)) < minVersion) {
                                                    Helper.getVersionUpdateDialog(((Activity) context), (Helper.getVersionCode(((Activity) context)) < minVersion ? "1" : androidType));
                                                }
                                            }

                                        }
                                    }
                                } else {
                                    Toast.makeText(context, context.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                                    myCBI.ErrorCallBack(context.getResources().getString(R.string.jsonparsing_error_message), apiType, typeApi);
                                }
                            }else if (apiType.equalsIgnoreCase(API.MOBILE_CALCULATOR)){
                                if (jsonString.isEmpty()){
                                    Toast.makeText(context, context.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                                    myCBI.ErrorCallBack(context.getResources().getString(R.string.jsonparsing_error_message), apiType, typeApi);
                                }else {
                                    myCBI.SuccessCallBack(new JSONObject(),apiType,jsonString,showprogress);
                                }
                            }
                            else if (apiType != API.user_logout) {

                                if (jsonString != null && !jsonString.isEmpty()) {
                                    JSONObject jsonObject = new JSONObject(jsonString);
                                    MakeMyExam.setTime_server((Long.parseLong(jsonObject.optString("time"))) * 1000);
                                    if (!MakeMyExam.userId.equalsIgnoreCase("0")) {
                                        if (jsonObject.has("auth_code") && jsonObject.optString("auth_code").equalsIgnoreCase(Const.EXPIRY_AUTH_CODE)) {
                                            RetrofitResponse.GetApiData(context, jsonObject.getString(Const.AUTH_CODE), jsonObject.getString(Const.MESSAGE), false);
                                        } else {
                                            AsyncTask.execute(() -> {
                                                try {
                                                    if (!utkashRoom.isOpen()) {
                                                        utkashRoom = UtkashRoom.getAppDatabase(MakeMyExam.getAppContext());
                                                        utkashRoom.getOpenHelper().getWritableDatabase();
                                                        utkashRoom.getOpenHelper().getReadableDatabase();
                                                        utkashRoom.getOpenHelper().setWriteAheadLoggingEnabled(true);
                                                    }
                                                } catch (Exception e) {
                                                    Log.d("abcd", "" + e.toString());
                                                    e.printStackTrace();
                                                }
                                                Log.e("onResponse: ", String.valueOf(utkashRoom.isOpen()));
                                                if (utkashRoom.getpigibag().isRecordExistsUserId(MakeMyExam.userId)) {
                                                    if ((Long.parseLong(utkashRoom.getpigibag().getpigidetail(MakeMyExam.userId).getCdtimestamp())) < (jsonObject.optLong("cd_time"))) {
                                                        NetworkAPICall("changedetecter", "", false, false);
                                                    }
                                                    utkashRoom.getpigibag().updaterecord(MakeMyExam.userId, String.valueOf(jsonObject.optLong("cd_time")));
                                                } else {
                                                    PigibagTable pigibag = new PigibagTable();
                                                    pigibag.setUser_id(MakeMyExam.getUserId());
                                                    pigibag.setCdtimestamp(String.valueOf(jsonObject.optLong("cd_time")));
                                                    NetworkAPICall("changedetecter", "", false, false);
                                                    utkashRoom.getpigibag().addApiedata(pigibag);
                                                }
                                            });
                                        }


                                    }
                                    myCBI.SuccessCallBack(jsonObject, apiType, typeApi, showprogress);
                                } else {
                                    Toast.makeText(context, context.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                                    myCBI.ErrorCallBack(context.getResources().getString(R.string.jsonparsing_error_message), apiType, typeApi);
                                }
                            }
                        } catch (JSONException e1) {
                            Toast.makeText(context, context.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                            myCBI.ErrorCallBack(context.getResources().getString(R.string.exception_api_error_message), apiType, typeApi);
                        }
                    } else {
                        Toast.makeText(context, context.getResources().getString(R.string.exception_api_error_message), Toast.LENGTH_SHORT).show();
                        myCBI.ErrorCallBack(context.getResources().getString(R.string.exception_api_error_message), apiType, typeApi);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Toast.makeText(context, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                   /* Toast.makeText(context, context.getResources().getString(R.string.jsonparsing_error_message), Toast.LENGTH_SHORT).show();
                    myCBI.ErrorCallBack(context.getResources().getString(R.string.exception_api_error_message), apiType, typeApi);*/
                }
            });
        } else {
            if (context != null)
                myCBI.ErrorCallBack(context.getString(R.string.internet_error_message), apiType, typeApi);
        }
    }

    private void checkandupdateversion(JSONObject jsonObject) {

        try {
            if (jsonObject.has("auth_code")) {
                if (jsonObject.optString("auth_code").equals("100100")) {
                    RetrofitResponse.GetApiData(context, jsonObject.has(Const.AUTH_CODE) ? jsonObject.getString(Const.AUTH_CODE) : "", jsonObject.getString(Const.MESSAGE), false);
                }
            } else {
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
                    String version_code = SharedPreference.getInstance().getString("version_code");
                    float my_course_version = Float.parseFloat(jsonObject.getJSONObject("data").getJSONObject("master").getString("ut_012"));

                    if (version_code == null || version_code.equalsIgnoreCase("") || Integer.parseInt(version_code) < BuildConfig.VERSION_CODE) {
                        SharedPreference.getInstance().putString("version_code", "" + BuildConfig.VERSION_CODE);
                        my_course_version = Float.parseFloat(String.valueOf(my_course_version + 0.001));
                    }
                    if (utkashRoom.getapidao().is_api_code_exits(MakeMyExam.userId, "ut_012")) {
                        if (Float.parseFloat(utkashRoom.getapidao().getapidetail("ut_012", MakeMyExam.userId).getVersion()) < my_course_version) {
                            utkashRoom.getapidao().updateversion("" + my_course_version, MakeMyExam.userId, "ut_012");
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
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSessionExpired() {
        Helper.SignOutUser(context);
    }

    public interface MyNetworkCallBack {
        Call<String> getAPIB(String apitype, String typeApi, APIInterface service);

        void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException;

        void ErrorCallBack(String jsonstring, String apitype, String typeApi);
    }

}
