package com.utkarshnew.android.Profile;

import static android.os.Build.VERSION.SDK_INT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Login.Activity.LoginCatActivity;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.AmazonUpload.AmazonCallBack;
import com.utkarshnew.android.Utils.AmazonUpload.s3ImageUploading;
import com.utkarshnew.android.Utils.AppPermissionsRunTime;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.SharedPreference;
import com.utkarshnew.android.home.Constants;
import com.utkarshnew.android.pojo.Userinfo.Data;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCities;
import com.utkarshnew.android.pojo.Userinfo.StatesCities.StatesCitiesData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class ProfileActivity extends AppCompatActivity implements AmazonCallBack, NetworkCall.MyNetworkCallBack, View.OnClickListener {
    String str_imgTypeClick = "";
    int cropresult = 0;
    EditText nameTV, emailTV, et_phone;
    public final int REQUEST_CODE_PERMISSION_MULTIPLE = 123;
    public ArrayList<AppPermissionsRunTime.MyPermissionConstants> myPermissionConstantsArrayList;
    private s3ImageUploading s3IU;
    CircleImageView profileImage;
    NetworkCall networkCall;

    RelativeLayout uploadImg;
    RelativeLayout City, State;
    StatesCities states;
    StatesCities cities;
    TextView stateSpinner, districtSpinner, change_pass;
    String clicktype = "";
    String stateindex = "";
    String cityindex = "";
    String SelectedStateid = "";
    String SelectedCityid = "";
    ImageView image_back;
    ImageView downarrowIV, downarrowIV2;
    Button submit;
    String profilepic = "";
    boolean removephoto = false;
    Data data;
    String firsttime = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_profile);
        nameTV = findViewById(R.id.nameTV);
        emailTV = findViewById(R.id.emailTV);
        et_phone = findViewById(R.id.et_phone);
        stateSpinner = findViewById(R.id.stateSpinner);
        change_pass = findViewById(R.id.change_pass);
        districtSpinner = findViewById(R.id.districtSpinner);
        profileImage = findViewById(R.id.profileImage);
        uploadImg = findViewById(R.id.uploadImg);
        image_back = findViewById(R.id.image_back);
        downarrowIV = findViewById(R.id.downarrowIV);
        downarrowIV2 = findViewById(R.id.downarrowIV2);
        submit = findViewById(R.id.submit);
        City = findViewById(R.id.city);
        State = findViewById(R.id.State);

        State.setOnClickListener(this);
        City.setOnClickListener(this);
        stateSpinner.setOnClickListener(this);
        districtSpinner.setOnClickListener(this);

        networkCall = new NetworkCall(this, this);
        data = SharedPreference.getInstance().getLoggedInUser();
        if (data.getUsername() != null) {
            nameTV.setText(data.getName());
        }

        if (data.getEmail() != null && !data.getEmail().isEmpty()) {
            emailTV.setText(data.getEmail());
            emailTV.setEnabled(false);
        }


        if (data.getMobile() != null) {
            et_phone.setText(data.getMobile());
        }
        et_phone.setEnabled(false);

        submit.setOnClickListener(new OnSingleClickListener(() -> {
            checkvalidation();
            return null;
        }));

        image_back.setOnClickListener(new OnSingleClickListener(() -> {
            finish();
            return null;
        }));

        uploadImg.setOnClickListener(new OnSingleClickListener(() -> {
            checkStoragePermission();
            return null;
        }));


        if(data.getHave_psw()!=null && data.getHave_psw().equals("0"))
        {
            change_pass.setText("Create Password");
        }
        else
        {
            change_pass.setText("Change Password");
        }


        change_pass.setOnClickListener(new OnSingleClickListener(() ->
        {
            Intent intent = new Intent(ProfileActivity.this, LoginCatActivity.class);
            if(data.getHave_psw()!=null && data.getHave_psw().equals("0"))
            {
                intent.putExtra(Const.RESET_PASS, false);
            }
            else
            {
                intent.putExtra(Const.RESET_PASS, true);
            }
            intent.putExtra(Const.FRAG_TYPE, Const.CREATEPASSWORD);
            startActivity(intent);
            return null;
        }));


        hit_api_to_get_state();

        if (data.getState() != null) {
            stateSpinner.setText(data.getState());
        }

        if (data.getCity() != null) {
            districtSpinner.setText(data.getCity());
        }

        if (data.getProfilePicture() != null) {
            profilepic = data.getProfilePicture();
            Glide.with(ProfileActivity.this).load(profilepic).apply(new RequestOptions().placeholder(R.mipmap.course_placeholder))
                    .into(profileImage);
        }
    }

    private void checkvalidation() {
//        if(!Helper.isStringValid(profilepic)) {
//            Toast.makeText(this, "profile picture is not valid", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if (!Helper.isStringValid(nameTV.getText().toString().trim())) {
            Toast.makeText(this, "name is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.isStringValid(emailTV.getText().toString().trim())) {
            Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Patterns.EMAIL_ADDRESS.matcher(emailTV.getText().toString().trim()).matches() == false) {
            Toast.makeText(this, "email is not valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.isStringValid(stateSpinner.getText().toString())) {
            Toast.makeText(this, "Please select state", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.isStringValid(districtSpinner.getText().toString()) || districtSpinner.getText().toString().equalsIgnoreCase("District")) {
            Toast.makeText(this, "Please select district", Toast.LENGTH_SHORT).show();
            return;
        }

        hit_api_to_update_profile();
    }

    private void hit_api_to_update_profile() {
        networkCall.NetworkAPICall(API.update_profile, "", false, false);
    }

    @Override
    public void onS3UploadData(ArrayList<MediaFile> images) {
        cropresult = 0;
        if (images != null && !images.isEmpty()) {
            profilepic = images.get(0).getFile();
            Glide.with(ProfileActivity.this).load(images.get(0).getFile()).apply(new RequestOptions().placeholder(R.mipmap.course_placeholder))
                    .into(profileImage);
        }
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {

        switch (apitype) {
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

            case API.update_profile:
                EncryptionData updateprofile = new EncryptionData();
                updateprofile.setName(nameTV.getText().toString());
                updateprofile.setProfile_picture(profilepic);
                updateprofile.setState(stateSpinner.getText().toString());
                updateprofile.setEmail(emailTV.getText().toString());
                updateprofile.setCity(districtSpinner.getText().toString());
                String updateprofileencryptionDatadoseStr = new Gson().toJson(updateprofile);
                String updateprofilestatedoseStrScr = AES.encrypt(updateprofileencryptionDatadoseStr);
                return service.updateprofile(updateprofilestatedoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_STATE:
                try {
                    if (jsonstring.getBoolean("status") == true) {
                        states = new Gson().fromJson(jsonstring.toString(), StatesCities.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case API.API_CITY:
                try {
                    if (jsonstring.getBoolean("status") == true) {
                        cities = new Gson().fromJson(jsonstring.toString(), StatesCities.class);
                        if (firsttime.equalsIgnoreCase("1")) {
                            clicktype = "2";
                            filterList(clicktype, cities);
                            firsttime = "0";
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case API.update_profile:
                try {
                    if (jsonstring.getBoolean("status")) {
                        data.setProfilePicture(profilepic);
                        data.setName(nameTV.getText().toString().trim());
                        data.setState(stateSpinner.getText().toString().trim());
                        data.setEmail(emailTV.getText().toString().trim());
                        data.setCity(districtSpinner.getText().toString().trim());
                        SharedPreference.getInstance().setLoggedInUserr(data);
                        MakeMyExam.username = nameTV.getText().toString().trim();
                        MakeMyExam.setUsername(nameTV.getText().toString().trim());
                        Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show();
                        if (!removephoto) {
                            finish();
                        } else {
                            Glide.with(ProfileActivity.this).load(profilepic).apply(new RequestOptions().placeholder(R.mipmap.course_placeholder)).error(R.mipmap.course_placeholder).into(profileImage);
                            removephoto = false;
                        }

                    } else {
                        Toast.makeText(this, "" + jsonstring.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
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
            case API.API_STATE:
            case API.API_CITY:
            case API.update_profile:

                Toast.makeText(this, jsonstring, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            // Marshmallow+
            myPermissionConstantsArrayList = new ArrayList<>();
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_READ_EXTERNAL_STORAGE);
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_WRITE_EXTERNAL_STORAGE);
            myPermissionConstantsArrayList.add(AppPermissionsRunTime.MyPermissionConstants.PERMISSION_CAMERA);
            if (AppPermissionsRunTime.checkPermission(ProfileActivity.this, myPermissionConstantsArrayList, REQUEST_CODE_PERMISSION_MULTIPLE)) {
                //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
                imgClick();
            }
        } else {
            // Pre-Marshmallow
            //takeImage.getImagePickerDialog(ProfileActivity.this, getString(R.string.app_name), getString(R.string.choose_image));
            imgClick();
        }
    }

    private void imgClick() {
        final CharSequence[] options;
        if (data != null && data.getProfilePicture() != null && !data.getProfilePicture().equalsIgnoreCase("")) {
            options = new CharSequence[]{"Take Photo", "Choose from Gallery", "Remove Photo", "Cancel"};

        } else {
            options = new CharSequence[]{"Take Photo", "Choose from Gallery", "Cancel"};

        }
        // options = {"Take Photo", "Choose from Gallery", "Cancel"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "temp_image.jpg");
                    Uri photoURI;
                    if (SDK_INT >= 24) {
                        photoURI = FileProvider.getUriForFile(ProfileActivity.this,
                                BuildConfig.APPLICATION_ID + ".provider", f);
                    } else {
                        photoURI = Uri.fromFile(f);
                    }
                    str_imgTypeClick = "PhotoCameraRequest";
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    imageorResult.launch(intent);
                    //  startActivityForResult(intent, 10000);
                } catch (Exception e) {
                    //DebugLogger.Write("Error in Main Activity Profile Take Photo Button " +e);
                }
            } else if (options[item].equals("Choose from Gallery")) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                File f = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "temp_gallery.jpg");
                Uri photoURI;
                if (SDK_INT >= 24) {
                    photoURI = FileProvider.getUriForFile(ProfileActivity.this,
                            BuildConfig.APPLICATION_ID + ".provider", f);
                } else {
                    photoURI = Uri.fromFile(f);
                }
                str_imgTypeClick = "PhotoGalleryRequest";
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                // startActivityForResult(intent, 20000);
                imageorResult.launch(intent);
            } else if (options[item].equals("Remove Photo")) {
                dialog.dismiss();
                new AlertDialog.Builder(this)
                        .setTitle("Remove profile")
                        .setMessage("Are you sure you want to Remove profile picture?")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                profilepic = "";
                                removephoto = true;
                                hit_api_to_update_profile();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    ActivityResultLauncher<Intent> imageorResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (cropresult == 0 && result.getResultCode() == Activity.RESULT_OK && str_imgTypeClick.equalsIgnoreCase("PhotoCameraRequest")) {
                    try {
                        File f = new File(String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
                        for (File temp : Objects.requireNonNull(f.listFiles())) {
                            if (temp.getName().equals("temp_image.jpg")) {
                                f = temp;
                                break;
                            }
                        }

                        Uri photoURI;
                        if (SDK_INT >= 24) {
                            photoURI = FileProvider.getUriForFile(this,
                                    BuildConfig.APPLICATION_ID + ".provider", f);
                        } else {
                            photoURI = Uri.fromFile(f);
                        }
                        cropresult = 223;
                        CropImage.activity(photoURI).setGuidelines(CropImageView.Guidelines.ON).start(ProfileActivity.this);
                    } catch (Exception e) {
                        cropresult = 0;
                        e.printStackTrace();
                    }

                } else if (cropresult == 0 && result.getResultCode() == Activity.RESULT_OK && str_imgTypeClick.equalsIgnoreCase("PhotoGalleryRequest")) {
                    try {
                        Intent data = result.getData();

                        Uri selectedImage = data.getData();
                        cropresult = 223;
                        CropImage.activity(selectedImage)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .start(ProfileActivity.this);
                    } catch (Exception e) {
                        cropresult = 0;
                        e.printStackTrace();
                    }
                } else {
                    cropresult = 0;
                }

            });


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String image_path = "";
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (str_imgTypeClick.equalsIgnoreCase("PhotoCameraRequest")) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            + "/Utkarsh/ProfileImage/";
                    File newdir = new File(path);
                    newdir.mkdirs();
                    OutputStream outFile;
                    image_path = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                    File file = new File(path + File.separator + image_path);
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
                    outFile.flush();
                    outFile.close();
                    s3IU = new s3ImageUploading("", Const.AMAZON_S3_BUCKET_PROFILE, ProfileActivity.this, ProfileActivity.this, null);
                    ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setFile_type(Const.IMAGE);
                    mediaFile.setImage(bitmap);
                    mediaFileArrayList.add(mediaFile);
                    s3IU.execute(mediaFileArrayList);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (str_imgTypeClick.equalsIgnoreCase("PhotoGalleryRequest")) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                    String path = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            + "/utkarsh/ProfileImage/";
                    File newdir = new File(path);
                    newdir.mkdirs();
                    OutputStream outFile = null;
                    image_path = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + ".jpg";
                    File file = new File(path + File.separator + image_path);
                    outFile = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outFile);
                    outFile.flush();
                    outFile.close();

                    s3IU = new s3ImageUploading("", Const.AMAZON_S3_BUCKET_PROFILE, ProfileActivity.this, ProfileActivity.this, null);
                    ArrayList<MediaFile> mediaFileArrayList = new ArrayList<>();
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setFile_type(Const.IMAGE);
                    mediaFile.setImage(bitmap);
                    mediaFileArrayList.add(mediaFile);
                    s3IU.execute(mediaFileArrayList);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void hit_api_to_get_state() {
        networkCall.NetworkAPICall(API.API_STATE, "", false, false);
    }

    private void hit_api_to_get_city() {
        networkCall.NetworkAPICall(API.API_CITY, "", false, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.State:
            case R.id.stateSpinner:
                if (states == null || states.getData().size() == 0) {
                    Toast.makeText(ProfileActivity.this, "No State available", Toast.LENGTH_SHORT).show();
                } else {
                    clicktype = "1";
                    filterList(clicktype, states);
                }
                break;

            case R.id.city:
            case R.id.districtSpinner:
                if ((cities == null || cities.getData().size() == 0)) {
                    if (states != null)
                        if (states.getData().size() > 0) {
                            for (StatesCitiesData statesCitiesData : states.getData()) {
                                if (data.getState().equalsIgnoreCase(statesCitiesData.getName())) {
                                    firsttime = "1";
                                    SelectedStateid = statesCitiesData.getId();
                                    hit_api_to_get_city();
                                }
                            }
                        } else {
                            Toast.makeText(ProfileActivity.this, "Please select State First", Toast.LENGTH_SHORT).show();
                        }

                    return;
                }

                clicktype = "2";
                filterList(clicktype, cities);

                break;
        }

    }

    //searchable popup
    EditText etSearch;
    ImageView ivClearSearch;
    RecyclerView searchRecyclerview;
    StateCityAdapter stateCityAdapter;

    public void filterList(String searchType, StatesCities countryArrayList) {
        Dialog searchDialog = new Dialog(ProfileActivity.this);
        Objects.requireNonNull(searchDialog.getWindow()).setBackgroundDrawableResource(R.color.transparent_background);
        searchDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
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
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
        stateCityAdapter = new StateCityAdapter(ProfileActivity.this, countryArrayList.getData(), searchType, searchDialog);
        searchRecyclerview.setAdapter(stateCityAdapter);

        textWatcher(searchType);
        searchDialog.show();
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

    public void onStateCityClick(String searchType, StatesCitiesData country) {
        if (searchType.equalsIgnoreCase("1")) {
            if (!country.getName().equals(stateindex)) {
                for (StatesCitiesData states : states.getData()) {
                    if (states.getName().equals(country.getName())) {
                        stateindex = country.getName();
                        SelectedStateid = states.getId();
                        stateSpinner.setText(country.getName());
                        firsttime = "0";
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

    public class StateCityAdapter extends RecyclerView.Adapter<StateCityAdapter.MyViewHolder> {

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
        public StateCityAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.state_city_dialog_adapter_item, viewGroup, false);
            return new StateCityAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StateCityAdapter.MyViewHolder myViewHolder, final int i) {
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
}