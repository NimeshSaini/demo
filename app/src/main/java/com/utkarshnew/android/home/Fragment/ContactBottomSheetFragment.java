package com.utkarshnew.android.home.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import java.util.Calendar;

public class ContactBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    final String TAG = "ContactBottomSheet";
    private LinearLayout btnCall, btnEmail, btnChat/*, btnWhatsapp*/;
    private Context sContext;

    public ContactBottomSheetFragment() {
        //Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contactus_bottom_sheet, container, false);

        init(view);

        return view;

    }

    private void init(View view) {
        sContext = getContext();

        btnCall = view.findViewById(R.id.bottomSheetBtnCall);
        btnEmail = view.findViewById(R.id.bottomSheetBtnEmail);
        btnChat = view.findViewById(R.id.bottomSheetBtnChat);

        btnCall.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnChat.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.bottomSheetBtnCall:
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                //show a dialog depending on time
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(sContext);
                if (hour >= 6) {
                    //creating dialog for displaying customer support numbers
                    alertDialogBuilder.setTitle(getResources().getString(R.string.customer_support_select_title));
                    String[] numbers = {getResources().getString(R.string.support_call1),
                            getResources().getString(R.string.support_call2)};
                    alertDialogBuilder.setItems(numbers, (dialog, which) -> {
                        String contactNo = numbers[which];
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactNo));
                        dialog.dismiss();
                        sContext.startActivity(intent);
                    });
                } else {
                    //creating a alert dialog
                    alertDialogBuilder.setMessage(getResources().getString(R.string.customer_support_message));
                    alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                }
                //showing a dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                dismiss();
                break;

            case R.id.bottomSheetBtnEmail:
                if (Helper.isNetworkConnected(getActivity())) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getResources().getString(R.string.support_email), null));
                    sContext.startActivity(Intent.createChooser(emailIntent, "Send email..."));

                } else {
                    Toast.makeText(sContext, getResources().getString(R.string.internet_connection_offline_text_short), Toast.LENGTH_SHORT).show();

                }
                dismiss();
                break;


        }

    }

    private boolean isAppInstalledOrNot(String uri) {
        PackageManager pm = sContext.getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }
}
