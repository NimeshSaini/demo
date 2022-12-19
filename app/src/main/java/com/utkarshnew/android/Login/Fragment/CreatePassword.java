package com.utkarshnew.android.Login.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.DialogUtils;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.Network.retrofit.RetrofitResponse;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.home.Activity.HomeActivity;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.pojo.Userinfo.Data;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePassword extends Fragment implements NetworkCall.MyNetworkCallBack {


    EditText newpasswordET,retrypasswordET,oldpasswordET;
    Button submitBtn;
    String retrypassword, newpassword,oldpassword;
    NetworkCall networkCall;
    ImageView iv_back;
    Boolean isreset;
    TextInputLayout oldpasswordlay;
    TextView title;
    Data data;

    public CreatePassword() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static CreatePassword newInstance(Boolean isreset) {
        CreatePassword fragment = new CreatePassword();
        Bundle args = new Bundle();
        args.putBoolean("isreset",isreset);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isreset=getArguments().getBoolean("isreset");
        }
        data = SharedPreference.getInstance().getLoggedInUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initviews(view);
        networkCall=new NetworkCall(this,getContext());
    }

    private void initviews(View view) {
        newpasswordET=view.findViewById(R.id.newpasswordET);
        retrypasswordET=view.findViewById(R.id.retrypasswordET);
        oldpasswordET=view.findViewById(R.id.oldpasswordET);
        submitBtn=view.findViewById(R.id.submitBtn);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        oldpasswordlay =  view.findViewById(R.id.oldpasswordlay);
        title =  view.findViewById(R.id.title);

        if(isreset)
        {
            oldpasswordlay.setVisibility(View.VISIBLE);
            title.setText("Change Password");
        }
        else
        {
            oldpasswordlay.setVisibility(View.GONE);
        }

        submitBtn.setOnClickListener(new OnSingleClickListener(()->{
            CheckValidations();
            return null;
        }));


        iv_back.setOnClickListener(new OnSingleClickListener(() -> {
            getActivity().finish();
            return null;
        }));
    }


    public void CheckValidations() {
        newpassword = Helper.GetText(newpasswordET);
        retrypassword = Helper.GetText(retrypasswordET);


        boolean isDataValid = true;

        if(isreset)
        {
            oldpassword=Helper.GetText(oldpasswordET);
            if(TextUtils.isEmpty(oldpassword))
            {
                isDataValid = false;
                Toast.makeText(getContext(), "Please fill old password", Toast.LENGTH_SHORT).show();
            }
        }

        if (TextUtils.isEmpty(newpassword)) {
            isDataValid = false;
            Toast.makeText(getContext(), "Please fill new password", Toast.LENGTH_SHORT).show();
        } else if (newpassword.length() < 8 || newpassword.length() > 13) {
            isDataValid = false;
            Toast.makeText(getContext(), "New password must vary from 8 to 13 characters", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(retrypassword)) {
            isDataValid = false;
            Toast.makeText(getContext(), "Please fill confirm password", Toast.LENGTH_SHORT).show();
        } else if (retrypassword.length() <= 8 && retrypassword.length() >= 13) {
            isDataValid = false;
            Toast.makeText(getContext(), "Change password must vary from 8 to 13 characters", Toast.LENGTH_SHORT).show();
        } else if (!newpassword.equals(retrypassword)) {
            isDataValid = false;
            Toast.makeText(getContext(), "Password does not match", Toast.LENGTH_SHORT).show();
        }
        if (isDataValid)
        {
            networkCall.NetworkAPICall(API.changePassword, "", true, false);
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service)
    {
        switch (apitype)
        {
            case API.changePassword:
                EncryptionData updatepasswordencryptionData = new EncryptionData();
                updatepasswordencryptionData.setMobile(SharedPreference.getInstance().getLoggedInUser().getMobile());
                if(isreset)
                {
                    updatepasswordencryptionData.setOldpassword(oldpasswordET.getText().toString());
                }
                updatepasswordencryptionData.setPassword(retrypasswordET.getText().toString());
                String profileencryptionDatadoseStr = new Gson().toJson(updatepasswordencryptionData);
                String profiledoseStrScr = AES.encrypt(profileencryptionDatadoseStr);
                return service.changePassword(profiledoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonObject, String apitype, String typeApi, boolean showprogress) throws JSONException
    {
        switch (apitype) {
            case API.changePassword:
                if (jsonObject.optString(Const.STATUS).equals(Const.TRUE)) {
                    data.setHave_psw("1");
                    SharedPreference.getInstance().setLoggedInUserr(data);
                    DialogUtils.makeCompleteCPDialog(getContext(), "Congratulations !", "you have successfully create your password", "Go Back To Home",
                            getResources().getString(R.string.close), false, new DialogUtils.onDialogUtilsOkClick() {
                                @Override
                                public void onOKClick() {
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                } else {
                    RetrofitResponse.GetApiData(getContext(), jsonObject.optString(Const.AUTH_CODE), jsonObject.optString(Const.MESSAGE), false);
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi)
    {
        switch (apitype) {
            case API.changePassword:
                Toast.makeText(getContext(), jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    @Override
    public void onStop()
    {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}