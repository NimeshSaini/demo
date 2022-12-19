package com.utkarshnew.android.Utils.AmazonUpload;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ProgressEvent;
import com.amazonaws.services.s3.model.ProgressListener;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.utkarshnew.android.Model.MediaFile;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.SharedPreference;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class s3ImageUploading extends AsyncTask<ArrayList<MediaFile>, Integer, ArrayList<MediaFile>> {
    ArrayList<MediaFile> imagearrayStr;
    private CognitoCachingCredentialsProvider credentialsProvider = null;
    String MY_OBJECT_KEY;
    String amazonFileUploadLocationOriginal;
    AmazonCallBack amazonCallBack;
    //Progress mprogress;
    Context context;
    Long contentLength;
    String chatnode = "";
    String message ="";
    private static final String TAG = "s3ImageUploading";

    public static ProgressDialog progressBar;
    public static String test_file_name;

    public s3ImageUploading(String chatnode, String amazonFileUploadLocationOriginal, Context context, AmazonCallBack amazonCallBack, ProgressBar progressBars) {
        this.amazonFileUploadLocationOriginal = amazonFileUploadLocationOriginal;
        this.amazonCallBack = amazonCallBack;
        this.context = context;
        this.chatnode = chatnode;
        //mprogress = new Progress(context);
        //mprogress.setCancelable(false);
        progressBar = new ProgressDialog(context);
        progressBar.setCancelable(false);
        progressBar.setMessage("Uploading...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setProgressDrawable(context.getResources().getDrawable(R.drawable.progress_bar_download));

        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                Const.CONGNITO_POOL_ID, // Identity pool ID
                Regions.AP_SOUTH_1 // Region
        );
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressBar.setProgress(values[0] / values[1]);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imagearrayStr = new ArrayList<>();
        try {
            progressBar.show();
        } catch (Exception e) {
            Log.d("progressBar", "show: " + e.getMessage());
        }

    }

    @Override
    protected ArrayList<MediaFile> doInBackground(ArrayList<MediaFile>... params) {
        try {
            byte[] content = null;
            PutObjectRequest putObjectRequest = null;
            String finalimageurl = "";

            AmazonS3Client amazonClient = new AmazonS3Client(credentialsProvider);
//            AmazonS3Client amazonClient = new AmazonS3Client(new BasicAWSCredentials(Const.AMAZON_S3_ACCESS_KEY, Const.AMAZON_S3_SECRET_KEY));
            amazonClient.setEndpoint(Const.AMAZON_S3_END_POINT);

            if (params[0].size() > 0) {
                for (MediaFile file : params[0]) {
                    int repeat = 1;
                    int i = 0;
                    if (file.getFile_type().equals(Const.VIDEO)) repeat = 2;
                    else repeat = 1;

                    while (i < repeat) {
                        if (file.getFile_type().equals(Const.VIDEO) && i == 0) {
                            file.setFile_name(Const.AMAZON_S3_FILE_NAME_CREATION);
                            MY_OBJECT_KEY = file.getFile_name() + ".mp4";
                        } else if (file.getFile_type().equals(Const.VIDEO) && i == 1) {
                            MY_OBJECT_KEY = file.getFile_name() + ".png";
                            amazonFileUploadLocationOriginal = Const.AMAZON_S3_BUCKET_NAME_VIDEO_IMAGES;
                        } else if (file.getFile_type().equals(Const.IMAGE) && amazonFileUploadLocationOriginal.equals(Const.AMAZON_S3_BUCKET_NAME_FEEDBACK))
                            MY_OBJECT_KEY = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + "_image.png";
                        else if (file.getFile_type().equals(Const.IMAGE))
                            MY_OBJECT_KEY = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + "_image.jpg";
                        else if (file.getFile_type().equals(Const.PDF))
                            MY_OBJECT_KEY = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + "_pdf.pdf";
                        else if (file.getFile_type().equals(Const.DOC))
                            MY_OBJECT_KEY = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + "_doc.docx";
                        else if (file.getFile_type().equals(Const.XLS))
                            MY_OBJECT_KEY = SharedPreference.getInstance().getLoggedInUser().getId() + "_" + Calendar.getInstance().getTimeInMillis() + "_xls.xlsx";
                        else if (file.getFile_type().equals(Const.json))
                            MY_OBJECT_KEY = test_file_name;

                        Log.e("Etag:", " MY_OBJECT_KEY: " + MY_OBJECT_KEY);

                        if (file.getFile_type().equals(Const.PDF) ||
                                file.getFile_type().equals(Const.DOC) ||
                                file.getFile_type().equals(Const.json) ||
                                file.getFile_type().equals(Const.XLS) ||
                                (file.getFile_type().equals(Const.IMAGE) && amazonFileUploadLocationOriginal.equals(Const.AMAZON_S3_BUCKET_NAME_FEEDBACK)) ||
                                (file.getFile_type().equals(Const.VIDEO) && i == 0)) {
                            content = Helper.FileToByteArray(file.getFile());

                        } else {
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            file.getImage().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            content = stream.toByteArray();
                        }
                        ByteArrayInputStream bs = new ByteArrayInputStream(content);
                        ObjectMetadata objectMetadata = new ObjectMetadata();
                        contentLength = Long.valueOf(content.length);
                        objectMetadata.setContentLength(contentLength);

                        if (amazonFileUploadLocationOriginal.equalsIgnoreCase(Const.AMAZON_S3_BUCKET_TEST)) {
                            amazonFileUploadLocationOriginal = amazonFileUploadLocationOriginal + chatnode;
                        } else if (amazonFileUploadLocationOriginal.equalsIgnoreCase(Const.AMAZON_S3_BUCKET_NAME_CHAT)) {
                            amazonFileUploadLocationOriginal = amazonFileUploadLocationOriginal + "/" + chatnode + "/" + MakeMyExam.userId;
                        } else if (amazonFileUploadLocationOriginal.equalsIgnoreCase(Const.AMAZON_S3_BUCKET_PROFILE)) {
                            amazonFileUploadLocationOriginal = amazonFileUploadLocationOriginal + MakeMyExam.userId;
                        }

                        if (amazonFileUploadLocationOriginal.equals(Const.AMAZON_S3_BUCKET_NAME_DOCUMENT)) {
                            amazonFileUploadLocationOriginal = "utkarsh-production/subjective_test_doc_user/" + SharedPreference.getInstance().getLoggedInUser().getId() + "/" + file.getId();
                            putObjectRequest = new PutObjectRequest(amazonFileUploadLocationOriginal, MY_OBJECT_KEY, bs, objectMetadata);
                        } else {
                            putObjectRequest = new PutObjectRequest(amazonFileUploadLocationOriginal, MY_OBJECT_KEY, bs, objectMetadata);
                        }
                        putObjectRequest.withCannedAcl(CannedAccessControlList.BucketOwnerFullControl);
                        putObjectRequest.setProgressListener(new ProgressListener() {
                            int total = 0;

                            @Override
                            public void progressChanged(ProgressEvent pv) {
                                Integer[] valuesProgress = new Integer[2];
                                valuesProgress[0] = Integer.parseInt(String.valueOf(contentLength)) * 100;
                                total += pv.getBytesTransfered();
                                if (total > 0) {
                                    Log.d("prince", "" + valuesProgress[0] / total);
                                    valuesProgress[1] = total;
                                    publishProgress(valuesProgress);
                                }
                            }
                        });
                        amazonClient.putObject(putObjectRequest);

                        if (amazonFileUploadLocationOriginal.equalsIgnoreCase((API.API_AMAZON_S3_BUCKET_NAME_JSON_IMAGE) + chatnode)) {
                            finalimageurl = "https://" + amazonFileUploadLocationOriginal.split("/")[0] + "." + "s3.ap-south-1.amazonaws.com/" + amazonFileUploadLocationOriginal.split("/")[1] + "/" + amazonFileUploadLocationOriginal.split("/")[2] + "/" + MY_OBJECT_KEY;
                        } else if (amazonFileUploadLocationOriginal.equalsIgnoreCase((Const.AMAZON_S3_BUCKET_PROFILE) + MakeMyExam.userId)) {
                            finalimageurl = "https://s3-prod.utkarshapp.com/" + amazonFileUploadLocationOriginal.split("/")[1] + "/" + amazonFileUploadLocationOriginal.split("/")[2] + "/" + amazonFileUploadLocationOriginal.split("/")[3] + "/" + MY_OBJECT_KEY;
                        } else
                            finalimageurl = "https://s3-prod.utkarshapp.com/" + amazonFileUploadLocationOriginal.split("/")[1] + "/" + amazonFileUploadLocationOriginal.split("/")[2] + "/" + amazonFileUploadLocationOriginal.split("/")[3] + "/" + amazonFileUploadLocationOriginal.split("/")[4] + "/" + MY_OBJECT_KEY;
                        i++;
                    }

                    Log.e("Etag:", " image URL: " + finalimageurl);
                    MediaFile mediaFile = new MediaFile();
                    mediaFile.setFile(finalimageurl);
                    mediaFile.setImage(file.getImage());

                    switch (amazonFileUploadLocationOriginal) {

                        case Const.AMAZON_S3_BUCKET_NAME_CHAT:
                        case Const.AMAZON_S3_BUCKET_PROFILE:
                            mediaFile.setFile_type(Const.IMAGE);
                            mediaFile.setFile_name(MY_OBJECT_KEY);
                            break;
                        case Const.AMAZON_S3_BUCKET_NAME_FANWALL_IMAGES:
                            mediaFile.setFile_type(Const.IMAGE);
                            mediaFile.setFile_name(MY_OBJECT_KEY);
                            break;
                        case Const.AMAZON_S3_BUCKET_NAME_VIDEO_IMAGES:
                        case Const.AMAZON_S3_BUCKET_NAME_VIDEO:
                            mediaFile.setFile_type(Const.VIDEO);//////=--------------
                            mediaFile.setFile_name(file.getFile_name());
                            break;

                        case Const.AMAZON_S3_BUCKET_NAME_DOCUMENT:
                            mediaFile.setFile_type(file.getFile_type());
                            mediaFile.setFile_name(file.getFile_name());
                            break;
                    }
                    imagearrayStr.add(mediaFile);
                }
            }
        } catch (Exception e) {
            if(e instanceof  AmazonS3Exception)
            {
                if (((AmazonS3Exception) e).getStatusCode() == 403)
               {
                   message =((AmazonS3Exception) e).getErrorMessage();
               }

            }
            Helper.dismissProgressDialog();
            e.printStackTrace();
            Log.e(TAG, "doInBackground: " + e);
        }
        return imagearrayStr;
    }

    @Override
    protected void onPostExecute(ArrayList<MediaFile> images) {
        super.onPostExecute(images);
        try {
            progressBar.dismiss();
        } catch (Exception e) {
            Log.d("progressBar", "dismiss: " + e.getMessage());
        }

        if (images.size() != 0) {
            amazonCallBack.onS3UploadData(images);
        } else {
            if (message!=null && !message.equalsIgnoreCase(""))
            {
                Toast toast = Toast.makeText(context, ""+message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }else {
                Toast toast = Toast.makeText(context, R.string.profile_image_error, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }

        }
    }
}