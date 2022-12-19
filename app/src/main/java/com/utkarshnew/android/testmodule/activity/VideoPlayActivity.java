package com.utkarshnew.android.testmodule.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.testmodule.model.TestReportVideos;

public class VideoPlayActivity extends AppCompatActivity {
    VideoView videoView;
    AppCompatTextView video_title;
    Toolbar toolbar;

    TestReportVideos testReportVideos;
    ImageView backimag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_video_play);

        toolbar = findViewById(R.id.toolbar);
        videoView = (VideoView) findViewById(R.id.video_play);
        video_title = (AppCompatTextView) toolbar.findViewById(R.id.video_title);
        backimag = (ImageView) toolbar.findViewById(R.id.backimag);
        testReportVideos = (TestReportVideos) getIntent().getSerializableExtra("videodata");
        if (testReportVideos != null) {
            video_title.setText(testReportVideos.getVideo_title().toString());
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setVideoPath(testReportVideos.getVideo_url());
            videoView.requestFocus();
            videoView.start();
        }
        backimag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
