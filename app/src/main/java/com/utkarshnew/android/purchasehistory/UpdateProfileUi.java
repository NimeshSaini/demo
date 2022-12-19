package com.utkarshnew.android.purchasehistory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.Payment.PurchaseActivity;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCities;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCitiesData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;

public class UpdateProfileUi implements View.OnClickListener , NetworkCall.MyNetworkCallBack {

    Context context;
    Data data;
    UpdateProfileData updateProfileDatalister;

    public UpdateProfileUi(Context context,UpdateProfileData updateProfileData)
    {
        this.context=context;
        this.updateProfileDatalister=updateProfileData;
        networkCall = new NetworkCall(this, context);
        data = SharedPreference.getInstance().getLoggedInUser();
    }

    public UpdateProfileUi(Context context)
    {
        this.context=context;
    }




    public void ShowContactLayout()
    {
        try {
            profilewatchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            profilewatchlist.setContentView(R.layout.fragment_contactus_bottom_sheet2);
            Objects.requireNonNull(profilewatchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            profilewatchlist.setCancelable(false);
            profilewatchlist.setCanceledOnTouchOutside(true);

        LinearLayout bottomSheetBtnEmail= profilewatchlist.findViewById(R.id.bottomSheetBtnEmail);
        LinearLayout bottomSheetBtnCall= profilewatchlist.findViewById(R.id.bottomSheetBtnCall);
        TextView mobileno= profilewatchlist.findViewById(R.id.mobileno);
        TextView email= profilewatchlist.findViewById(R.id.email);

            email.setText(context.getResources().getString(R.string.support_email));
            mobileno.setText(context.getResources().getString(R.string.support_call1) +"," + context.getResources().getString(R.string.support_call2));

            bottomSheetBtnCall.setOnClickListener(new OnSingleClickListener(()->{
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                //show a dialog depending on time
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                if (hour >= 6) {
                    //creating dialog for displaying customer support numbers
                    alertDialogBuilder.setTitle(context.getResources().getString(R.string.customer_support_select_title));
                    String[] numbers = {context.getResources().getString(R.string.support_call1),
                            context.getResources().getString(R.string.support_call2)};
                    alertDialogBuilder.setItems(numbers, (dialog, which) -> {
                        String contactNo = numbers[which];
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contactNo));
                        dialog.dismiss();
                        context.startActivity(intent);
                    });
                } else {
                    //creating a alert dialog
                    alertDialogBuilder.setMessage(context.getResources().getString(R.string.customer_support_message));
                    alertDialogBuilder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
                }
                //showing a dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                profilewatchlist.dismiss();
                return null;
            }));

            bottomSheetBtnEmail.setOnClickListener(new OnSingleClickListener(()->{
                if (Helper.isNetworkConnected(context)) {
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", context.getResources().getString(R.string.support_email), null));
                    context.startActivity(Intent.createChooser(emailIntent, "Send email..."));

                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.internet_connection_offline_text_short), Toast.LENGTH_SHORT).show();

                }
                return null;
            }));



            if (!profilewatchlist.isShowing()) {
                profilewatchlist.show();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    NetworkCall networkCall;
    StatesCities states;
    StatesCities cities;
    String clicktype = "";
    String stateindex = "";
    String cityindex = "";
    String SelectedStateid = "";
    String SelectedCityid = "";
    TextView stateSpinner, districtSpinner;
    EditText nameTV, emailTV, et_mobile;
    Button submitBtn;
    BottomSheetDialog profilewatchlist;
    ImageView downarrowIV,downarrowIV2;

    public void ShowLayout()
    {
        try {
            profilewatchlist = new BottomSheetDialog(context, R.style.videosheetDialogTheme);
            profilewatchlist.setContentView(R.layout.update_profile);
            Objects.requireNonNull(profilewatchlist.getWindow()).getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            stateSpinner= profilewatchlist.findViewById(R.id.stateSpinner);
            districtSpinner= profilewatchlist.findViewById(R.id.districtSpinner);
            nameTV= profilewatchlist.findViewById(R.id.nameTV);
            emailTV= profilewatchlist.findViewById(R.id.emailTV);
            downarrowIV= profilewatchlist.findViewById(R.id.downarrowIV);
            downarrowIV2= profilewatchlist.findViewById(R.id.downarrowIV2);
            et_mobile= profilewatchlist.findViewById(R.id.et_mobile);
            submitBtn= profilewatchlist.findViewById(R.id.submitBtn);
            et_mobile.setText(data.getMobile());
            profilewatchlist.setCancelable(false);
            profilewatchlist.setCanceledOnTouchOutside(true);

            stateSpinner.setOnClickListener(this);
            districtSpinner.setOnClickListener(this);
           // submitBtn.setOnClickListener(this);
            downarrowIV.setOnClickListener(this);
            downarrowIV2.setOnClickListener(this);

            if (!profilewatchlist.isShowing()) {
                profilewatchlist.show();
            }
            hit_api_to_get_state();



            submitBtn.setOnClickListener(new OnSingleClickListener(() -> {
                checkvalidation();
                return null;
            }));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.State:
            case R.id.stateSpinner:
            case R.id.downarrowIV:

                if (states == null || states.getData().size() == 0) {
                    Toast.makeText(context, "No State available", Toast.LENGTH_SHORT).show();
                } else {
                    clicktype = "1";
                    filterList(clicktype, states);
                }
                break;

            case R.id.city:
            case R.id.districtSpinner:
            case R.id.downarrowIV2:
                if ((cities == null || cities.getData().size() == 0)) {
                    Toast.makeText(context, "Please select State First", Toast.LENGTH_SHORT).show();
                    return;
                }

                clicktype = "2";
                filterList(clicktype, cities);

                break;

            case R.id.submitBtn:
                checkvalidation();
                break;
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype)
        {
            case API.API_STATE:
                EncryptionData stateencryptionData = new EncryptionData();
                stateencryptionData.setUser_id(MakeMyExam.getUserId());
                String stateencryptionDatadoseStr = new Gson().toJson(stateencryptionData);
                String statedoseStrScr = AES.encrypt(stateencryptionDatadoseStr);
                return service.GetState(statedoseStrScr);
            case API.API_CITY:
                EncryptionData cityencryptionData = new EncryptionData();
                cityencryptionData.setState_id(SelectedStateid);
                String cityencryptionDatadoseStr = new Gson().toJson(cityencryptionData);
                String citydoseStrScr = AES.encrypt(cityencryptionDatadoseStr);
                return service.GetCity(citydoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype)
        {
            case API.API_STATE:
                if (jsonobject.getBoolean("status") == true) {
                    states = new Gson().fromJson(jsonobject.toString(), StatesCities.class);
                }
                break;

            case API.API_CITY:
                if (jsonobject.getBoolean("status") == true) {
                    cities = new Gson().fromJson(jsonobject.toString(), StatesCities.class);
                }
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(context, "" + jsonstring, Toast.LENGTH_SHORT).show();
    }


    //searchable popup
    EditText etSearch;
    ImageView ivClearSearch;
    RecyclerView searchRecyclerview;
  StateCityAdapter stateCityAdapter;

    public void filterList(String searchType, StatesCities countryArrayList) {
        Dialog searchDialog = new Dialog(context);
        searchDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        searchDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        searchDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        searchDialog.setContentView(R.layout.state_city_dialog);
        searchDialog.setCancelable(true);
        etSearch = searchDialog.findViewById(R.id.et_search);
        if (searchType.equalsIgnoreCase("1")) {
            etSearch.setHint("Search State");
        } else if (searchType.equalsIgnoreCase("2")) {
            etSearch.setHint("Search District");
        }

        ivClearSearch = searchDialog.findViewById(R.id.iv_clear_search);
        TextView tvCancel = searchDialog.findViewById(R.id.tv_cancel);
        ivClearSearch.setOnClickListener(v -> etSearch.setText(""));
        tvCancel.setOnClickListener(v -> searchDialog.cancel());
        searchRecyclerview = searchDialog.findViewById(R.id.search_recyclerview);
        searchRecyclerview.setHasFixedSize(true);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        stateCityAdapter = new StateCityAdapter(context, countryArrayList.getData(), searchType, searchDialog);
        searchRecyclerview.setAdapter(stateCityAdapter);

        textWatcher(searchType);
        searchDialog.show();
    }

    public class StateCityAdapter extends RecyclerView.Adapter<UpdateProfileUi.StateCityAdapter.MyViewHolder> {

        Context context;
        List<StatesCitiesData> countryArrayList;
        String searchType;
        Dialog searchDialog;

        public StateCityAdapter(Context context, List<StatesCitiesData> countryArrayList, String searchType, Dialog searchDialog) {
            this.context = context;
            this.countryArrayList = countryArrayList;
            this.searchType = searchType;
            this.searchDialog = searchDialog;
        }

        @NonNull
        @Override
        public UpdateProfileUi.StateCityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.state_city_dialog_adapter_item, viewGroup, false);
            return new UpdateProfileUi.StateCityAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull UpdateProfileUi.StateCityAdapter.MyViewHolder myViewHolder, final int i) {
            StatesCitiesData country = countryArrayList.get(i);
            myViewHolder.tvName.setText(country.getName());

            myViewHolder.tvName.setOnClickListener(v -> {
                if (searchDialog != null) {
                    searchDialog.dismiss();
                }
                switch (searchType) {
                    case "1":
                        onStateCityClick(searchType, country);
                        break;
                    case "2":
                        onStateCityClick(searchType, country);
                        break;
                    default:
                        break;
                }
            });
        }

        @Override
        public int getItemCount() {
            return countryArrayList.size();
        }

        public void filterCountryList(List<StatesCitiesData> newCountryArrayList) {
            countryArrayList = newCountryArrayList;
            notifyDataSetChanged();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tvName;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.nameTv);
            }
        }
    }


    public void onStateCityClick(String searchType, StatesCitiesData country) {
        if (searchType.equalsIgnoreCase("1")) {
            if (!country.getName().equals(stateindex)) {
                for (StatesCitiesData states : states.getData()) {
                    if (states.getName().equals(country.getName())) {
                        stateindex = country.getName();
                        SelectedStateid = states.getId();
                        stateSpinner.setText(country.getName());
                        districtSpinner.setText("District");
                        hit_api_to_get_city();
                        break;
                    }
                }
            }
        } else if (searchType.equalsIgnoreCase("2")) {
            if (!country.getName().equals(cityindex)) {
                for (StatesCitiesData city : cities.getData()) {
                    if (city.getName().equals(country.getName())) {
                        cityindex = country.getName();
                        SelectedCityid = city.getId();
                        districtSpinner.setText(country.getName());
                        break;
                    }
                }
            }
        }
    }

    private void hit_api_to_get_city() {
        networkCall.NetworkAPICall(API.API_CITY, "", true, false);
    }

    private void hit_api_to_get_state() {
        networkCall.NetworkAPICall(API.API_STATE, "", true, false);
    }


    public void textWatcher(String searchType) {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    ivClearSearch.setVisibility(View.VISIBLE);
                } else {
                    ivClearSearch.setVisibility(View.GONE);
                }
                //after the change calling the method and passing the search input
                filter(editable.toString(), searchType);
            }
        });
    }

    ArrayList<StatesCitiesData> statesCitiesArrayList = new ArrayList<>();

    private void filter(String text, String searchType) {
        statesCitiesArrayList.clear();
        if (searchType.equalsIgnoreCase("1")) {
            for (StatesCitiesData country : states.getData()) {
                if (country.getName().toLowerCase().contains(text.toLowerCase())) {
                    statesCitiesArrayList.add(country);
                }
            }
        } else if (searchType.equalsIgnoreCase("2")) {
            for (StatesCitiesData country : cities.getData()) {
                if (country.getName().toLowerCase().contains(text.toLowerCase())) {
                    statesCitiesArrayList.add(country);
                }
            }
        }
        if (!statesCitiesArrayList.isEmpty()) {
            searchRecyclerview.setVisibility(View.VISIBLE);
            stateCityAdapter.filterCountryList(statesCitiesArrayList);
        } else {
            searchRecyclerview.setVisibility(View.INVISIBLE);
        }
    }

    private void checkvalidation() {
        if (!Helper.isStringValid(nameTV.getText().toString().trim())) {
            Toast.makeText(context, "name is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.isStringValid(emailTV.getText().toString().trim())) {
            Toast.makeText(context, "email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(emailTV.getText().toString().trim()).matches() == false) {
            Toast.makeText(context, "email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.isStringValid(stateSpinner.getText().toString())) {
            Toast.makeText(context, "Please select state", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.isStringValid(districtSpinner.getText().toString()) || districtSpinner.getText().toString().equalsIgnoreCase("District")) {
            Toast.makeText(context, "Please select district", Toast.LENGTH_SHORT).show();
            return;
        }

        if(updateProfileDatalister!=null)
        {
            if(profilewatchlist.isShowing())
            {
                profilewatchlist.cancel();
            }
            updateProfileDatalister.getUpdatedData(nameTV.getText().toString().trim()
                    ,emailTV.getText().toString().trim()
                            ,et_mobile.getText().toString()
            ,stateSpinner.getText().toString()
                    ,districtSpinner.getText().toString()
            );
        }
    }
}
