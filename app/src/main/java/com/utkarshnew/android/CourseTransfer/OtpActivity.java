package com.utkarshnew.android.CourseTransfer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.Sms.SmsBroadcastReceiver;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.table.VideosDownload;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.pojo.Userinfo.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;


public class OtpActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnKeyListener, TextWatcher, NetworkCall.MyNetworkCallBack {

    Button verifyBtn;
    String otp = "";
    boolean isresend = false;
    String otptext = "";
    String userID = "";
    String isReg = "0";
    int type;
    Data user;
    String[] strings;
    String[] c_ids;
    private boolean resetPass = false;
    private boolean isChangePass = false;
    private boolean isphone = false;
    private EditText mPinFirstDigitEditText;
    private EditText mPinSecondDigitEditText;
    private EditText mPinThirdDigitEditText;
    private EditText mPinForthDigitEditText;
    private EditText mPinfifthDigitEditText;
    private EditText mPinsixthDigitEditText;
    private EditText mPinHiddenEditText;
    private TextView mresendotpTV, mresendotpTV1;
    private TextView verifyTextTV;
    private TextView tvTimer;
    ImageView iv_back;
    BroadcastReceiver broadcastReceiver;

    boolean status = false;

    private CountDownTimer countDownTimer = null;
    long leftmillisUntilFinished;
    public DatabaseReference firebaseDatabase;

    private static final int REQ_USER_CONSENT = 200;
    SmsBroadcastReceiver smsBroadcastReceiver;
    NetworkCall networkCall;
    String transfer_to = "", transfer_from = "";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.otp_activity);
        try {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
                networkCall = new NetworkCall(OtpActivity.this, OtpActivity.this);
                SmsRetrieverClient client = SmsRetriever.getClient(this);
                client.startSmsUserConsent(null);

            }

            strings = getIntent().getStringArrayExtra("coursestring");
            c_ids = getIntent().getStringArrayExtra("c_ids");

            transfer_to = getIntent().getStringExtra("Transfer_to_id");
            transfer_from = getIntent().getStringExtra("Transfer_to_mobile");
            ;

            mPinFirstDigitEditText = (EditText) findViewById(R.id.OPT1ET);
            mPinSecondDigitEditText = (EditText) findViewById(R.id.OPT2ET);
            mPinThirdDigitEditText = (EditText) findViewById(R.id.OPT3ET);
            mPinForthDigitEditText = (EditText) findViewById(R.id.OPT4ET);
            mPinfifthDigitEditText = (EditText) findViewById(R.id.OPT5ET);
            mPinsixthDigitEditText = (EditText) findViewById(R.id.OPT6ET);
            mPinHiddenEditText = (EditText) findViewById(R.id.pin_hidden_edittext);
            mresendotpTV1 = (TextView) findViewById(R.id.resendotpTV1);
            mresendotpTV = (TextView) findViewById(R.id.resendotpTV);
            verifyTextTV = (TextView) findViewById(R.id.verifyTextTV);
            iv_back = findViewById(R.id.iv_back);
            verifyBtn = (Button) findViewById(R.id.verifyBtn);
            tvTimer = (TextView) findViewById(R.id.tv_timer);
            // firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("DeleteVideo/" +MakeMyExam.userId);

            setPINListeners();

            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            verifyBtn.setOnClickListener(new OnSingleClickListener(() -> {
                otptext = mPinHiddenEditText.getText().toString().trim();
                if (TextUtils.isEmpty(otptext)) {
                    Toast.makeText(this, "OTP does not blank.", Toast.LENGTH_SHORT).show();
                    return null;
                }
                otp = otptext;
                networkCall.NetworkAPICall(API.transfer_course, "", true, false);

                return null;
            }));


            mresendotpTV.setOnClickListener(new OnSingleClickListener(() -> {

                isresend = true;

                otp = "";
                networkCall.NetworkAPICall(API.transfer_course, "", true, false);
                return null;
            }));

            tvTimer.setPaintFlags(tvTimer.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            setTimer();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void alert_dailog() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Course Transfer");
        alertDialogBuilder.setMessage("Are you sure, you want to transfer \nthe selected course to below Mobile Number?:- " + transfer_from + "?");
        alertDialogBuilder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        alertDialogBuilder.setPositiveButton("Yes", (dialog, which) -> {
            networkCall.NetworkAPICall(API.transfer_course, "", true, false);

            dialog.dismiss();
            dialog.cancel();
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {

            case API.transfer_course:
                EncryptionData data = new EncryptionData();
                data.setTxn_ids(strings);
                data.setTransfer_to_id(transfer_to);
                data.setTransfer_to_mobile(transfer_from);
                if (isresend) {
                    data.setOtp(otp);
                    data.setResend("1");
                } else {
                    data.setOtp(otp);
                    data.setResend("0");
                }

                String data_json = new Gson().toJson(data);
                String encryptiondata_encrypot = AES.encrypt(data_json);
                return service.transfer_course(encryptiondata_encrypot);
        }

        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonString, String apitype, String typeApi, boolean showprogress) throws JSONException {
        Log.e("json", jsonString.toString());
        switch (apitype) {

            case API.transfer_course:
                try {
                    if (jsonString.optString(Const.STATUS).equals(Const.TRUE)) {
                        Toast.makeText(this, "" + jsonString.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                        if (isresend) {
                            claerdata();
                            setTimer();
                            isresend = false;

                        } else {
                            if (countDownTimer != null) {
                                countDownTimer.cancel();
                            }
                            UtkashRoom utkashRoom = UtkashRoom.getAppDatabase(OtpActivity.this);


                            for (String courseid : c_ids) {
                                utkashRoom.getuserhistorydao().delete(courseid + "#", MakeMyExam.userId);

                                List<VideosDownload> videosDownloads = utkashRoom.getvideoDownloadao().getallcourse_id(courseid + "#", MakeMyExam.userId);
                                if (videosDownloads != null && videosDownloads.size() > 0) {
                                    for (VideosDownload videosDownload : videosDownloads) {

                                       /* EncryptionData encryptionData =new EncryptionData();
                                        encryptionData.setCourse_id(videosDownload.getCourse_id());
                                        encryptionData.setVideo_id(videosDownload.getVideo_id());
                                        encryptionData.setUser_id(MakeMyExam.userId);
                                        encryptionData.setServercourseid(courseid);
                                        encryptionData.setType("coursetransfer");
                                        firebaseDatabase.push().setValue(new Gson().toJson(encryptionData));

*/

                                        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.Videos/" + videosDownload.getVideo_history() + ".mp4");
                                        File file1 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/.processing/" + videosDownload.getVideo_history() + ".mp4");
                                        if (file.exists()) {
                                            file.delete();
                                        }
                                        if (file1.exists()) {
                                            file1.delete();
                                        }
                                        utkashRoom.getvideoDownloadao().delete(videosDownload.getVideo_id(), videosDownload.getCourse_id(), MakeMyExam.userId);
                                    }
                                }

                            }


                            finish();
                        }

                    } else {
                        RetrofitResponse.GetApiData(this, jsonString.has(Const.AUTH_CODE) ? jsonString.getString(Const.AUTH_CODE) : "", jsonString.getString(Const.MESSAGE), false);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

        }
    }

    private void claerdata() {
        runOnUiThread(() -> {
            mPinHiddenEditText.getText().clear();

            mPinFirstDigitEditText.getText().clear();
            mPinSecondDigitEditText.getText().clear();
            mPinThirdDigitEditText.getText().clear();
            mPinForthDigitEditText.getText().clear();
            mPinfifthDigitEditText.getText().clear();
            mPinsixthDigitEditText.getText().clear();


            mPinFirstDigitEditText.setEnabled(true);
            mPinSecondDigitEditText.setEnabled(true);
            mPinThirdDigitEditText.setEnabled(true);
            mPinForthDigitEditText.setEnabled(true);
            mPinfifthDigitEditText.setEnabled(true);
            mPinsixthDigitEditText.setEnabled(true);
        });

    }


    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        //Toast.makeText(activity, jsonstring, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        if (s.length() == 0) {
            mPinFirstDigitEditText.setText("");
        } else if (s.length() == 1) {
            mPinFirstDigitEditText.setText(s.charAt(0) + "");
            mPinSecondDigitEditText.setText("");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 2) {
            mPinSecondDigitEditText.setText(s.charAt(1) + "");
            mPinThirdDigitEditText.setText("");
            mPinForthDigitEditText.setText("");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 3) {
            mPinThirdDigitEditText.setText(s.charAt(2) + "");
            mPinForthDigitEditText.setText("");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 4) {
            mPinForthDigitEditText.setText(s.charAt(3) + "");
            mPinfifthDigitEditText.setText("");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 5) {
            mPinfifthDigitEditText.setText(s.charAt(4) + "");
            mPinsixthDigitEditText.setText("");
        } else if (s.length() == 6) {
            mPinsixthDigitEditText.setText(s.charAt(5) + "");
            hideSoftKeyboard(mPinsixthDigitEditText);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    /**
     * Sets listeners for EditText fields.
     */
    public void hideSoftKeyboard(EditText editText) {
        if (editText == null)
            return;
        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    private void setPINListeners() {
        mPinHiddenEditText.addTextChangedListener(this);
        mPinFirstDigitEditText.setOnFocusChangeListener(this);
        mPinSecondDigitEditText.setOnFocusChangeListener(this);
        mPinThirdDigitEditText.setOnFocusChangeListener(this);
        mPinForthDigitEditText.setOnFocusChangeListener(this);
        mPinfifthDigitEditText.setOnFocusChangeListener(this);
        mPinsixthDigitEditText.setOnFocusChangeListener(this);
        mPinFirstDigitEditText.setOnKeyListener(this);
        mPinSecondDigitEditText.setOnKeyListener(this);
        mPinThirdDigitEditText.setOnKeyListener(this);
        mPinForthDigitEditText.setOnKeyListener(this);
        mPinfifthDigitEditText.setOnKeyListener(this);
        mPinsixthDigitEditText.setOnKeyListener(this);
        mPinHiddenEditText.setOnKeyListener(this);
    }

    public void showSoftKeyboard(EditText editText) {
        if (editText == null)
            return;

        InputMethodManager imm = (InputMethodManager) getSystemService(Service.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        final int id = view.getId();
        switch (id) {
            case R.id.OPT1ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT2ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT3ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT4ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT5ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            case R.id.OPT6ET:
                if (hasFocus) {
                    setFocus(mPinHiddenEditText);
                    showSoftKeyboard(mPinHiddenEditText);
                }
                break;

            default:
                break;
        }
    }


    public static void setFocus(EditText editText) {
        if (editText == null)
            return;
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
    }


    @Override
    public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            final int id = view.getId();
            switch (id) {
                case R.id.pin_hidden_edittext:
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (mPinHiddenEditText.getText().length() == 6)
                            mPinsixthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 5)
                            mPinfifthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 4)
                            mPinForthDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 3)
                            mPinThirdDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 2)
                            mPinSecondDigitEditText.setText("");
                        else if (mPinHiddenEditText.getText().length() == 1)
                            mPinFirstDigitEditText.setText("");
                    }

                    break;

                default:
                    return false;
            }
        }

        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_USER_CONSENT) {
            if ((resultCode == RESULT_OK) && (data != null)) {
                String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);

                getOtpFromMessage(message);
            } else {
                mPinFirstDigitEditText.setEnabled(true);
                mPinSecondDigitEditText.setEnabled(true);
                mPinThirdDigitEditText.setEnabled(true);
                mPinForthDigitEditText.setEnabled(true);
                mPinfifthDigitEditText.setEnabled(true);
                mPinsixthDigitEditText.setEnabled(true);
            }
        }
    }

    private void getOtpFromMessage(String message) {
        if (message != null && !message.equalsIgnoreCase("")) {
            Pattern otpPattern = Pattern.compile("(|^)\\d{6}");
            Matcher matcher = otpPattern.matcher(message);
            if (matcher.find()) {
                otptext = parseCode(message);
                Log.e("OTP MESSAGE", otptext);
                mPinHiddenEditText.setText(otptext);
                mPinFirstDigitEditText.setText(String.valueOf(otptext.charAt(0)));
                mPinSecondDigitEditText.setText(String.valueOf(otptext.charAt(1)));
                mPinThirdDigitEditText.setText(String.valueOf(otptext.charAt(2)));
                mPinForthDigitEditText.setText(String.valueOf(otptext.charAt(3)));
                mPinfifthDigitEditText.setText(String.valueOf(otptext.charAt(4)));
                mPinsixthDigitEditText.setText(String.valueOf(otptext.charAt(5)));
                mPinFirstDigitEditText.setEnabled(true);
                mPinSecondDigitEditText.setEnabled(true);
                mPinThirdDigitEditText.setEnabled(true);
                mPinForthDigitEditText.setEnabled(true);
                mPinfifthDigitEditText.setEnabled(true);
                mPinsixthDigitEditText.setEnabled(true);
                otp = otptext;

            }
        }
    }


    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{6}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    public void setTimer() {
        status = true;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(60000, 1000) {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            public void onTick(long millisUntilFinished) {
                try {
                    tvTimer.setVisibility(View.VISIBLE);
                    mresendotpTV1.setVisibility(View.GONE);
                    mresendotpTV.setVisibility(View.GONE);
                    tvTimer.setText("0" + String.format("%d:%d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    leftmillisUntilFinished = millisUntilFinished;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                try {
                    status = false;
                    tvTimer.setText("");
                    tvTimer.setVisibility(View.GONE);
                    mresendotpTV.setVisibility(View.VISIBLE);
                    mresendotpTV1.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    Log.d("TAG", "onFinish: " + e.getMessage());
                }
            }
        };
        countDownTimer.start();
    }

    private void registerBroadcastReceiver() {

        smsBroadcastReceiver = new SmsBroadcastReceiver();

        smsBroadcastReceiver.smsBroadcastReceiverListener = new SmsBroadcastReceiver.SmsBroadcastReceiverListener() {
            @Override
            public void onSuccess(Intent intent) {
                otplauncher.launch(intent);

             //   startActivityForResult(intent, REQ_USER_CONSENT);
            }

            @Override
            public void onFailure() {

            }
        };

        IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
        registerReceiver(smsBroadcastReceiver, intentFilter);

    }

    ActivityResultLauncher<Intent> otplauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if ((result.getResultCode() == RESULT_OK) && (data != null)) {
                        String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);

                        getOtpFromMessage(message);
                    } else {
                        mPinFirstDigitEditText.setEnabled(true);
                        mPinSecondDigitEditText.setEnabled(true);
                        mPinThirdDigitEditText.setEnabled(true);
                        mPinForthDigitEditText.setEnabled(true);
                        mPinfifthDigitEditText.setEnabled(true);
                        mPinsixthDigitEditText.setEnabled(true);
                    }

                }
            });



    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        mPinFirstDigitEditText.setEnabled(true);
        mPinSecondDigitEditText.setEnabled(true);
        mPinThirdDigitEditText.setEnabled(true);
        mPinForthDigitEditText.setEnabled(true);
        mPinfifthDigitEditText.setEnabled(true);
        mPinsixthDigitEditText.setEnabled(true);
    }


}