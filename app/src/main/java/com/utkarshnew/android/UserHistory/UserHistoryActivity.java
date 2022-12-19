package com.utkarshnew.android.UserHistory;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.utkarshnew.android.OnSingleClickListener;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Room.UtkashRoom;
import com.utkarshnew.android.table.UserHistroyTable;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.SharedPreference;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryActivity extends AppCompatActivity {

    RecyclerView download_recycler;
    private UtkashRoom utkashRoom;
    private ImageView image_back;

    private List<UserHistroyTable> histroyTableArrayList = new ArrayList<>();

    private HistoryAdapter historyAdapter;
    RelativeLayout no_data_found_RL;
    Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_user_histroy);
        try {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
            utkashRoom = UtkashRoom.getAppDatabase(UserHistoryActivity.this);
            utkashRoom.getOpenHelper().getWritableDatabase().enableWriteAheadLogging();


            download_recycler = findViewById(R.id.download_recycler);
            no_data_found_RL = findViewById(R.id.no_data_found_RL);
            backBtn = findViewById(R.id.backBtn);
            image_back = findViewById(R.id.image_back);

            image_back.setOnClickListener(new OnSingleClickListener(() -> {
                finish();
                return null;
            }));
            backBtn.setOnClickListener(new OnSingleClickListener(() -> {
                onBackPressed();
                return null;
            }));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean status = SharedPreference.getInstance().getBoolean("sign_url");
        if (status)
        {
            AsyncTask.execute(() -> {
                try {
                    if (!utkashRoom.getOpenHelper().getWritableDatabase().isDbLockedByCurrentThread()) {
                        utkashRoom.getuserhistorydao().delete();
                        histroyTableArrayList = utkashRoom.getuserhistorydao().getallhistory(MakeMyExam.userId);
                    }
                    runOnUiThread(() -> {
                        if (historyAdapter != null && histroyTableArrayList.size() > 0) {
                            historyAdapter.chnagelist(histroyTableArrayList);
//                        Log.e("zxcvbnm", "onResume: " + histroyTableArrayList.size());
                        } else {
                            if (histroyTableArrayList.size() > 0) {
                                no_data_found_RL.setVisibility(View.GONE);
                                download_recycler.setVisibility(View.VISIBLE);
                                historyAdapter = new HistoryAdapter(UserHistoryActivity.this, histroyTableArrayList, utkashRoom);
                                download_recycler.setAdapter(historyAdapter);
                            } else {
                                no_data_found_RL.setVisibility(View.VISIBLE);
                                download_recycler.setVisibility(View.GONE);
                            }
//                        Log.e("zxcvbnm", "onResume Else: " + histroyTableArrayList.size());
                        }
                    });
                } catch (Exception e) {
//                Log.e("zxcvbnm", "onResume: " + histroyTableArrayList.size());
                    e.printStackTrace();
                }
            });

        }else {

            SharedPreference.getInstance().putBoolean("sign_url",true);

            utkashRoom.getuserhistorydao().delete();
            if (histroyTableArrayList.size() > 0) {
                no_data_found_RL.setVisibility(View.GONE);
                download_recycler.setVisibility(View.VISIBLE);
                historyAdapter = new HistoryAdapter(UserHistoryActivity.this, histroyTableArrayList, utkashRoom);
                download_recycler.setAdapter(historyAdapter);
            } else {
                no_data_found_RL.setVisibility(View.VISIBLE);
                download_recycler.setVisibility(View.GONE);
            }

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        utkashRoom = null;
    }


}