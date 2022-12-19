package com.utkarshnew.android.home.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import com.utkarshnew.android.home.adapters.LibUsedAdapter;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;

public class LibrariesUsedActivity extends AppCompatActivity {

    private Toolbar mLibUsedToolbar;
    private RecyclerView libUsedList;
    private LibUsedAdapter mAdapter;
    private static RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.opensourcelibrarylayout);
        init();
    }


    private void init() {
        mLibUsedToolbar = findViewById(R.id.oslibactionbar);
        libUsedList = findViewById(R.id.libusedlist);

        //setup toolbar
        if (getSupportActionBar() == null) {
            setSupportActionBar(mLibUsedToolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(R.string.open_source_library_activity);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back_black);
            }
        }

        mLayoutManager = new LinearLayoutManager(LibrariesUsedActivity.this) {
            @Override
            public boolean isAutoMeasureEnabled() {
                return true;
            }
        };
        libUsedList.setHasFixedSize(true);

        libUsedList.setLayoutManager(mLayoutManager);
        mAdapter = new LibUsedAdapter(LibrariesUsedActivity.this);
        libUsedList.setAdapter(mAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //Handle Close Button CLicks
        finish(); //close this activity
        return false;
    }
}
