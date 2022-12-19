package com.utkarshnew.android.courses.Activity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import com.pdfview.PDFView;
import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.courses.Interfaces.AsyncTaskCompleteListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class PdfDetailScreen extends AppCompatActivity implements AsyncTaskCompleteListener {
    private ProgressBar progressbar_pdf;
    private String url, name, pdf_id = "", course_id = "", valid_to = "";
    InputStream input = null;
    OutputStream output = null;
    private ImageView downarrowFB, back;
    private ProgressDialog mProgressDialog;
    private TextView pdf_name;
    private boolean isDownload;
    private boolean isDownloadComplete;
    HttpURLConnection urlConnection = null;
    public ProgressDialog progressBar;
    public PDFView pdfViewPager;
    private String unzipLocation, zipFile;
    RelativeLayout toolbar_layout;
    String fname=".pdf";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.pdf_viewer);
        pdf_name = findViewById(R.id.pdf_name);
        progressbar_pdf = findViewById(R.id.progressbar_pdf);
        toolbar_layout = findViewById(R.id.toolbar_layout);
        downarrowFB = findViewById(R.id.download_pdf);
        back = findViewById(R.id.back);
        pdfViewPager = findViewById(R.id.pdfViewPager);
        url = getIntent().getStringExtra(Const.URL);
        course_id = getIntent().getStringExtra("course_id");
        pdf_id = getIntent().getStringExtra(Const.TITLE);
        name = getIntent().getStringExtra("pdf_name");
        pdf_name.setText(TextUtils.isEmpty(name) ? getString(R.string.app_name) : name);
        valid_to = getIntent().getStringExtra("valid_to");
        if (name != null && !name.equalsIgnoreCase("")) {
            if (name.matches("[a-zA-Z]+")) {
                name = name.replaceAll("[^a-zA-Z0-9]", "");
            } else if (name.contains("||")) {
                name = name.replaceAll("\\|\\|", "");
            }
            if (name.contains("||")) {
                name = name.replaceAll("[|?*<\":>+\\[\\]/']", "_");
            }
            name = name.replaceAll("[\t\n\r]", "");
            name = name.replaceAll(" ", "");
            name = name.replaceAll("\\|", "");
            name = name.replaceAll("[-+^]*", "");
            name = name.replaceAll("[;\\\\/:*?\"<>|&']", "");
            if (BuildConfig.VERSION_CODE >= 130) {
                name = name.replaceAll("[^a-zA-Z0-9]", "");
            }
        }
        Log.d("prince", "" + name);
        isDownload = getIntent().getBooleanExtra("is_download", false);
        if (url.contains("\\/")) {
            url = url.replace("\\/", "/");
        }
        try {
            String filename =  URLUtil.guessFileName(url, null, MimeTypeMap.getFileExtensionFromUrl(url));
            String query = URLEncoder.encode(filename, "utf-8");
            url =url.subSequence(0,url.lastIndexOf('/')+1)+query;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("abc",""+url);
        if (Helper.isNetworkConnected(PdfDetailScreen.this)) {
            if (url != null && !url.isEmpty()) {
                if (isDownload) {
                    downarrowFB.setVisibility(View.VISIBLE);
                    if (getFileExist().exists()) {
                        Helper.showProgressDialog(this);
                        isDownloadComplete = true;
                        downarrowFB.setImageResource(R.drawable.ic_downloaded_chapter);
                        pdfViewPager.fromFile(getFileExist().getPath()).show();
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(Helper::dismissProgressDialog, 2000);
                    } else {
                        isDownloadComplete = false;
                        downarrowFB.setImageResource(R.drawable.ic_menu_download);
                        File path;
                        path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
                        String fileName="";
                        if (course_id.contains("#"))
                        {
                            if (course_id.split("#").length==2)
                            {
                                fileName = "/" + name + "_" + course_id.replace("#", "_")+"_"+pdf_id+fname;
                            }else {
                                fileName = "/" + name + "_" + course_id.replace("#", "_")+pdf_id+fname;
                            }
                        }else {
                            fileName = "/" + name + "_" + course_id+"_"+pdf_id+fname;
                        }
                        fileName = path + fileName;
                        if (new File(fileName).exists()) {
                            openPDF(this, new File(fileName));
                            return;
                        }
                        final ProgressDialog progress = ProgressDialog.show(this, "PdfLoading Please wait...", name, false);
                        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
                        String downloadfile ;
                        if (course_id.contains("#"))
                        {
                            if (course_id.split("#").length==2)
                            {
                                downloadfile = "/" + name + "_" + course_id.replace("#", "_")+"_"+pdf_id+fname;
                            }else {
                                downloadfile = "/" + name + "_" + course_id.replace("#", "_")+pdf_id+fname;
                            }
                        }else {
                            downloadfile = "/" + name + "_" + course_id+"_"+pdf_id+fname;
                        }
                        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadfile);
                        final DownloadManager dm = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                        String finalFileName = fileName;
                        BroadcastReceiver onComplete = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                try {
                                    if (!progress.isShowing()) {
                                        return;
                                    }
                                    context.unregisterReceiver(this);
                                    progress.dismiss();
                                    long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                                    Cursor c = dm.query(new DownloadManager.Query().setFilterById(downloadId));
                                    if (c.moveToFirst()) {
                                        int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                            openPDF(context, new File(finalFileName));
                                        }
                                    }
                                    c.close();
                                } catch (Exception e) {
                                }
                            }
                        };
                        this.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
                        dm.enqueue(r);
                   /*     Helper.showProgressDialog(this);
                        LOADURL loadurl = new LOADURL(this);
                        loadurl.execute(url);*/
                    }
                } else {
                    isDownloadComplete = false;
                    downarrowFB.setVisibility(View.GONE);
                    Helper.showProgressDialog(this);
                    LOADURL loadurl = new LOADURL(this);
                    loadurl.execute(url);
                }
            }
        }
        downarrowFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isDownloadComplete) {
                    showDialog(PdfDetailScreen.this);
                }
            }
        });
        back.setOnClickListener(v -> onBackPressed());
    }
    public void openPDF(Context context, File localUri) {
        try {
            UserHistroyTable userHistroyTable = new UserHistroyTable();
            userHistroyTable.setVideo_id(pdf_id);
            userHistroyTable.setPdf_name(name);
            userHistroyTable.setType("PDF");
            userHistroyTable.setPdf_url(url);
            userHistroyTable.setUser_id(MakeMyExam.userId);
            userHistroyTable.setCourse_id(course_id);
            userHistroyTable.setValid_to(valid_to);
            userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
            UtkashRoom.getAppDatabase(this).getuserhistorydao().addUser(userHistroyTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pdfViewPager.fromFile(localUri).show();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (!isDownload) {
                if (localUri.exists()) {
                    isDownloadComplete = false;
                    localUri.delete();
                }
            }
            Helper.dismissProgressDialog();
        }, 2000);
    }
    public File getFileExist() {
        String fileName = "";
        File filepath;
        if (course_id.contains("#"))
        {
            if (course_id.split("#").length==2)
            {
                fileName = "/" + name + "_" + course_id.replace("#", "_")+"_"+pdf_id+fname;
            }else {
                fileName = "/" + name + "_" + course_id.replace("#", "_")+pdf_id+fname;
            }
        }else {
            fileName = "/" + name + "_" + course_id+"_"+pdf_id+fname;
        }
        File path;
        path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
        if (!path.exists()) {
            path.mkdirs();
        }
        filepath = new File(path + "/" + fileName);
        return filepath;
    }
    @Override
    public void onBackPressed() {
        Helper.dismissProgressDialog();
//        if (progressbar_pdf.isShown())
//        {
//            progressbar_pdf.setVisibility(View.GONE);
//        }
        super.onBackPressed();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public void onTaskComplete(String inputStream, File file) {
        try {
            if (inputStream.equalsIgnoreCase("success")) {
                try {
                    UserHistroyTable userHistroyTable = new UserHistroyTable();
                    userHistroyTable.setVideo_id(pdf_id);
                    userHistroyTable.setPdf_name(name);
                    userHistroyTable.setType("PDF");
                    userHistroyTable.setPdf_url(url);
                    userHistroyTable.setUser_id(MakeMyExam.userId);
                    userHistroyTable.setCourse_id(course_id);
                    userHistroyTable.setValid_to(valid_to);
                    userHistroyTable.setCurrent_time("" + MakeMyExam.getTime_server());
                    UtkashRoom.getAppDatabase(this).getuserhistorydao().addUser(userHistroyTable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pdfViewPager.fromFile(file).show();
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    if (!isDownload) {
                        if (file.exists()) {
                            isDownloadComplete = false;
                            file.delete();
                        }
                    }
                    Helper.dismissProgressDialog();
                }, 2000);
            } else {
                Helper.dismissProgressDialog();
                downarrowFB.setVisibility(View.GONE);
                Toast.makeText(this, "Pdf loading error please wait.", Toast.LENGTH_SHORT).show();
                //   progressbar_pdf.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class LOADURL extends AsyncTask<String, Integer, String> {
        private AsyncTaskCompleteListener callback;
        public File filepath;
        String response = "";
        public LOADURL(AsyncTaskCompleteListener cb) {
            this.callback = cb;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection)
                        url.openConnection();
                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    response = "error";
                    return "Server returned HTTP " + urlConnection.getResponseCode()
                            + " " + urlConnection.getResponseMessage();
                }
                if (urlConnection.getResponseCode() == 200) {
                    int fileLength = urlConnection.getContentLength();
                    input = urlConnection.getInputStream();
                    String fileName;
                    if (course_id.contains("#"))
                    {
                        if (course_id.split("#").length==2)
                        {
                            fileName = "/" + name + "_" + course_id.replace("#", "_")+"_"+pdf_id+fname;
                        }else {
                            fileName = "/" + name + "_" + course_id.replace("#", "_")+pdf_id+fname;
                        }
                    }else {
                        fileName = "/" + name + "_" + course_id+"_"+pdf_id+fname;
                    }
                    File path;
                    path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
                    if (!path.exists()) {
                        path.mkdirs();
                    }
                    filepath = new File(path + "/" + fileName);
                    if (filepath.exists()) {
                        filepath.delete();
                    }
                    output = new FileOutputStream(filepath, false);
                    byte[] data = new byte[8192];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        if (isCancelled()) {
                            input.close();
                            response = "error";
                            return null;
                        }
                        if (fileLength > 0) {
                            response = "success";
                            total += count;
                            //   publishProgress((int)((total*100)/fileLength));
                            output.write(data, 0, count);
                        }
                    }
                }
            } catch (IOException e) {
                response = "error";
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (Exception ignored) {
                }
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String response) {
            if (callback != null) {
                callback.onTaskComplete(response, filepath);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isDownload && !isDownloadComplete) {
            if (getFileExist().exists()) {
                getFileExist().delete();
            }
        }
        try {
            if (output != null)
                output.close();
            if (input != null)
                input.close();
        } catch (Exception ignored) {
        }
        if (urlConnection != null)
            urlConnection.disconnect();
        if (PdfDetailScreen.this.isFinishing()) {
            Helper.dismissProgressDialog();
           /* if (progressbar_pdf.getVisibility()==View.VISIBLE)
            {
                progressbar_pdf.setVisibility(View.GONE);
            }*/
        }
        finish();
    }
    int status = 0;
    Handler handler = new Handler();
    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        final ProgressBar text = (ProgressBar) dialog.findViewById(R.id.progress_horizontal);
        final TextView text2 = dialog.findViewById(R.id.value123);
        final TextView pathy_name = dialog.findViewById(R.id.text);
        if (BuildConfig.VERSION_CODE >= 130) {
            pathy_name.setText("Download Pdf- \n Pdf Download Path - Internal Storage/Download/");
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status < 100) {
                    status += 1;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setProgress(status);
                            text2.setText(String.valueOf(status));
                            if (status == 100) {
                                isDownloadComplete = true;
                                downarrowFB.setImageResource(R.drawable.ic_downloaded_chapter);
                                dialog.dismiss();
                                //showDownloadNotification();
                            }
                        }
                    });
                }
            }
        }).start();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        try {
            if (newConfig.orientation == 1) {
                toolbar_layout.setVisibility(View.VISIBLE);
            } else {
                toolbar_layout.setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
    }
}
