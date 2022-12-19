package com.utkarshnew.android.Login.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.utkarshnew.android.Login.Activity.LoginWithOtp;
import com.utkarshnew.android.Login.Activity.SignInActivity;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.CustomContextWrapper;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.SharedPreference;

/**
 * Created by appsquadz on 4/16/2018.
 */

public class ChooseLanguageIBT extends Fragment {

    Activity activity;
    String frag_type = "";
    String languageDecider = "";
    int languageCode;
    LinearLayout englishRL, hindiRL;
    Button continue_btn;
    RadioButton engRadio, hindiRadio;

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    public static ChooseLanguageIBT newInstance(int type) {
        ChooseLanguageIBT chooseLanguageIBT = new ChooseLanguageIBT();
        Bundle args = new Bundle();
        args.putInt(Const.TYPE, type);
        chooseLanguageIBT.setArguments(args);
        return chooseLanguageIBT;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            frag_type = getArguments().getString(Const.TYPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ibt_fragment_choose_language, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        initView(view);
    }

    private void initView(View view) {
        englishRL = view.findViewById(R.id.englishRL);
        hindiRL = view.findViewById(R.id.hindiRL);
        continue_btn = view.findViewById(R.id.continue_btn);
        engRadio = view.findViewById(R.id.engRadio);
        hindiRadio = view.findViewById(R.id.hindiRadio);

        hindiRadio.setChecked(false);
        engRadio.setChecked(false);

        englishRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hindiRadio.setChecked(false);
                engRadio.setChecked(true);
                languageDecider = Const.ENGLISH;
            }
        });

        hindiRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hindiRadio.setChecked(true);
                engRadio.setChecked(false);
                languageDecider = Const.HINDI;
            }
        });

        engRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hindiRadio.setChecked(false);
                engRadio.setChecked(true);
                languageDecider = Const.ENGLISH;
            }
        });

        hindiRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hindiRadio.setChecked(true);
                engRadio.setChecked(false);
                languageDecider = Const.HINDI;
            }
        });


        continue_btn.setOnClickListener(new OnSingleClickListener(() -> {
            if (languageDecider.equalsIgnoreCase(Const.HINDI)) {
                nextActivity(Const.HINDI_CODE, Const.HINDI);
            } else if (languageDecider.equalsIgnoreCase(Const.ENGLISH)) {
                nextActivity(Const.ENGLISH_CODE, Const.ENGLISH);
            } else {
                Helper.showSnackBar(getView(), "Please choose a language");
            }
            return null;
        }));
    }


    private void nextActivity(int lngCode, String language) {
        SharedPreference.getInstance().putInt(Const.LANGUAGE, lngCode);
        SharedPreference.getInstance().putString(Const.APP_LANGUAGE, language);
        CustomContextWrapper.wrap(activity, language);
        Helper.changeLang(SharedPreference.getInstance().getString(Const.APP_LANGUAGE), activity);
        if(SharedPreference.getInstance().getString("is_otp_login")!=null && SharedPreference.getInstance().getString("is_otp_login").equals("0"))
        {
            Intent intent = new Intent(activity, SignInActivity.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            activity.finish();
            startActivity(intent);
            Helper.activityAnimation(activity);

        }
        else
        {
            Intent intent = new Intent(activity, LoginWithOtp.class);
            intent.putExtra(Const.TYPE, Const.SIGNIN);
            intent.putExtra(Const.OPEN_WITH, Const.USER_OPEN);
            intent.putExtra(Const.SOCIAL_TYPE, Const.SOCIAL_TYPE_GMAIL);
            activity.finish();
            startActivity(intent);
            Helper.activityAnimation(activity);
        }
    }
}