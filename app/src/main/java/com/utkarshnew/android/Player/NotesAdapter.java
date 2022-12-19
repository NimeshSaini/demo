package com.utkarshnew.android.Player;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.BuildConfig;
import com.utkarshnew.android.JWextractor.JWVideoPlayer;
import com.utkarshnew.android.Model.PlayerPojo.Pdf;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Progress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.Viewholder> {
    private Activity con;

    private List<Pdf> pdf;
    String course_id = "",pdf_id="";
    String name="";
    boolean is_download=false;
    Progress progress;
    public File filepath;


    public NotesAdapter(Activity con, List<Pdf> pdf, String course_id) {
        this.con = con;
        this.pdf = pdf;
        this.course_id = course_id;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(con);
        View view = layoutInflater.inflate(R.layout.activity_cardview_notes, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.pdftext.setText(pdf.get(position).getPdfTitle());
        if (!TextUtils.isEmpty(pdf.get(position).getPdfThumbnail())) {
            Helper.setThumbnailImage(con, pdf.get(position).getPdfThumbnail(), con.getResources().getDrawable(R.drawable.square_thumbnail), holder.iv_pdf);
        } else {
            holder.iv_pdf.setImageResource(R.drawable.square_thumbnail);
        }
        holder.rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   boolean isDownload = false;
                if (!TextUtils.isEmpty(pdf.get(position).getIsDownloadable()) && pdf.get(position).getIsDownloadable().equalsIgnoreCase("1")) {
                    isDownload = true;
                } else {
                    isDownload = false;
                }
                Helper.GoToWebViewPDFActivity(con, pdf.get(position).getId(), pdf.get(position).getPdfUrl(), isDownload, pdf.get(position).getPdfTitle(), course_id);
*/

                if (con instanceof Liveawsactivity || con instanceof JWVideoPlayer)
                {
                    name =pdf.get(position).getPdfTitle();
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
                    pdf_id = pdf.get(position).getId();
                    openwatchlist_dailog_resource(con,pdf.get(position));

                }else {
                    boolean isDownload = false;
                    if (!TextUtils.isEmpty(pdf.get(position).getIsDownloadable()) && pdf.get(position).getIsDownloadable().equalsIgnoreCase("1")) {
                        isDownload = true;
                    } else {
                        isDownload = false;
                    }
                    Helper.GoToWebViewPDFActivity(con, pdf.get(position).getId(), pdf.get(position).getPdfUrl(), isDownload, pdf.get(position).getPdfTitle(), course_id);

                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return pdf.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView textpdf, iv_pdf;
        TextView pdftext;
        RelativeLayout rl3;

        public Viewholder(View itemView) {
            super(itemView);
            textpdf = itemView.findViewById(R.id.textpdf);
            pdftext = itemView.findViewById(R.id.pdftext);
            iv_pdf = itemView.findViewById(R.id.iv_pdf);
            rl3 = itemView.findViewById(R.id.rl3);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void openwatchlist_dailog_resource(Activity context, Pdf course) {
        try {
            if (con.isFinishing() || con.isDestroyed())
                return;
            if (context instanceof  Liveawsactivity)
            {
                ((Liveawsactivity)con).pdf_view_layout.setVisibility(View.VISIBLE);
                ((Liveawsactivity)con).recyclerChat.setVisibility(View.GONE);

            }else if (context instanceof JWVideoPlayer)
            {
                ((JWVideoPlayer)con).pdf_view_layout.setVisibility(View.VISIBLE);
                ((JWVideoPlayer)con).recyclerChat.setVisibility(View.GONE);

            }

            progress =new Progress(context);
            progress.setCancelable(true);
            if (!progress.isShowing())
            {
                progress.show();
            }


            LOADURL loadurl = new LOADURL(con);
            loadurl.execute(course.getPdfUrl());

            if (context instanceof  Liveawsactivity)
            {
                ((Liveawsactivity)con).cross.setOnClickListener(v -> {
                    if (filepath!=null)
                    {
                        ((Liveawsactivity)con).webView.fromFile(filepath).show();
                        ((Liveawsactivity)con).pdf_view_layout.setVisibility(View.GONE);
                        ((Liveawsactivity)con).recyclerChat.setVisibility(View.VISIBLE);
                    }



                });

            }else if (context instanceof JWVideoPlayer)
            {

                ((JWVideoPlayer)con).cross.setOnClickListener(v -> {
                    if (filepath!=null)
                    {
                        ((JWVideoPlayer)con).webView.fromFile(filepath).show();
                        ((JWVideoPlayer)con).pdf_view_layout.setVisibility(View.GONE);
                        ((JWVideoPlayer)con).recyclerChat.setVisibility(View.VISIBLE);
                    }



                });

            }

            if (context instanceof  Liveawsactivity)
            {
                ((Liveawsactivity)con).expand.setOnClickListener(v -> {
                    if (filepath!=null)
                    {
                        ((Liveawsactivity)con).webView.fromFile(filepath).show();
                        ((Liveawsactivity)con).pdf_view_layout.setVisibility(View.GONE);
                        ((Liveawsactivity)con).recyclerChat.setVisibility(View.VISIBLE);

                        boolean isDownload = false;
                        if (!TextUtils.isEmpty(course.getIsDownloadable()) && course.getIsDownloadable().equalsIgnoreCase("1")) {
                            isDownload = true;
                        } else {
                            isDownload = false;
                        }
                        Helper.GoToWebViewPDFActivity(con, course.getId(), course.getPdfUrl(), isDownload, course.getPdfTitle(), course_id);
                    }

                });
            }else if (context instanceof JWVideoPlayer)
            {
                ((JWVideoPlayer)con).expand.setOnClickListener(v -> {
                    if (filepath!=null)
                    {
                        ((JWVideoPlayer)con).webView.fromFile(filepath).show();
                        ((JWVideoPlayer)con).pdf_view_layout.setVisibility(View.GONE);
                        ((JWVideoPlayer)con).recyclerChat.setVisibility(View.VISIBLE);

                        boolean isDownload = false;
                        if (!TextUtils.isEmpty(course.getIsDownloadable()) && course.getIsDownloadable().equalsIgnoreCase("1")) {
                            isDownload = true;
                        } else {
                            isDownload = false;
                        }
                        Helper.GoToWebViewPDFActivity(con, course.getId(), course.getPdfUrl(), isDownload, course.getPdfTitle(), course_id);
                    }

                });
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    public class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // When user clicks a hyperlink, load in the existing WebView
            if (!progress.isShowing()) {
                progress.show();
            }
            view.loadUrl(url);
            return true;
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request,
                                    WebResourceError error) {

            if (!con.isFinishing()) {
                try {
                    ((Liveawsactivity)con).webView.stopLoading();
                } catch (Exception e) {
                }

                if (((Liveawsactivity)con).webView.canGoBack()) {
                    ((Liveawsactivity)con).webView.goBack();
                }
                if (progress != null)
                    if (progress.isShowing())
                        progress.dismiss();
                ((Liveawsactivity)con).webView.loadUrl("about:blank");
            }


            super.onReceivedError(view, request, error);

        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceivedHttpError(WebView view,
                                        WebResourceRequest request, WebResourceResponse errorResponse) {

            super.onReceivedHttpError(view, request, errorResponse);
        }


        @Override
        public void onPageFinished(WebView view, String url) {
            if (progress != null)
                if (progress.isShowing())
                    progress.dismiss();
        }
    }
*/


    public void openPDF(Context context, File localUri, Progress progreebar) {
        if (con instanceof  Liveawsactivity)
        {
            ((Liveawsactivity)con).webView.fromFile(localUri).show();

        }else if (con instanceof  JWVideoPlayer)
        {
            ((JWVideoPlayer)con).webView.fromFile(localUri).show();

        }
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (localUri.exists()) {
                localUri.delete();
            }
            progreebar.dismiss();

        }, 2000);

    }

    @SuppressLint("StaticFieldLeak")
    public class LOADURL extends AsyncTask<String, Integer, String> {
        String response = "";
        HttpURLConnection urlConnection = null;
        InputStream input = null;
        OutputStream output = null;

        public LOADURL(Activity con) {
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    cancel(true);
                    progress.dismiss();
                    if (con instanceof JWVideoPlayer)
                    {
                        ((JWVideoPlayer)con).pdf_view_layout.setVisibility(View.GONE);
                        ((JWVideoPlayer)con).recyclerChat.setVisibility(View.VISIBLE);
                    }else if (con instanceof  Liveawsactivity)
                    {
                        ((Liveawsactivity)con).pdf_view_layout.setVisibility(View.GONE);
                        ((Liveawsactivity)con).recyclerChat.setVisibility(View.VISIBLE);
                    }

                   // Toast.makeText(con, "Pdf loading error", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            response = "error";

            if (isCancelled()) {
                try {
                    if (output!=null)
                    {
                        output.flush();
                        output.close();
                    }
                    if (input!=null)
                        input.close();
                    if (urlConnection != null)
                        urlConnection.disconnect();
                    if (filepath!=null  && filepath.exists()) {
                        filepath.delete();
                    }
                    onPostExecute(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                            fileName = "/" + name + "_" + course_id.replace("#", "_")+"_"+pdf_id+".pdf";
                        }else {
                            fileName = "/" + name + "_" + course_id.replace("#", "_")+pdf_id+".pdf";
                        }
                    }else {
                        fileName = "/" + name + "_" + course_id+"_"+pdf_id+".pdf";
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
            if (response != null && response.equalsIgnoreCase("success")) {
                openPDF(con,filepath,progress);
            }else {
                progress.dismiss();
                if (con instanceof JWVideoPlayer)
                {

                    if(((JWVideoPlayer)con).pdf_view_layout.getVisibility()==View.VISIBLE)
                    {
                        ((JWVideoPlayer)con).pdf_view_layout.setVisibility(View.GONE);
                        ((JWVideoPlayer)con).recyclerChat.setVisibility(View.VISIBLE);

                    }
                }else if (con instanceof  Liveawsactivity)
                {

                    if(((Liveawsactivity)con).pdf_view_layout.getVisibility()==View.VISIBLE)
                    {
                        ((Liveawsactivity)con).pdf_view_layout.setVisibility(View.GONE);
                        ((Liveawsactivity)con).recyclerChat.setVisibility(View.VISIBLE);

                    }
                }





            }
        }
    }

}
