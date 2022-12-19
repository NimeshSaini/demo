package com.utkarshnew.android.Webview;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.utkarshnew.android.Model.FlashData;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class FlashcardActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {


    ImageView back_view, move_view;

    ImageView image_back;
    private Banner_ViewPager bannerViewPager;
    //    private PageIndicatorView pageIndicatorView;
    ArrayList<FlashData> data = new ArrayList<>();
    ViewPager viewPager_Banner;
    TextView setProgress;
    ProgressBar progress_value;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.flash_acivity);
        try {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));

            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());

          /*  pageIndicatorView = findViewById(R.id.pageIndicatorView);
            pageIndicatorView.setVisibility(View.GONE);*/
            viewPager_Banner = findViewById(R.id.viewPager_Banner);
            back_view = findViewById(R.id.back_view);
            move_view = findViewById(R.id.move_view);
            image_back = findViewById(R.id.image_back);
            setProgress = findViewById(R.id.setProgress);
            progress_value = findViewById(R.id.progress_value);

            JSONArray jsonArray = new JSONArray(getIntent().getStringExtra("url"));
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject actor = jsonArray.getJSONObject(i);
                    FlashData flashData = new FlashData();
                    flashData.setTerms(actor.getString("terms"));
                    flashData.setDescription(actor.getString("description"));
                    data.add(flashData);
                }


                bannerViewPager = new Banner_ViewPager(this, data);
                viewPager_Banner.setAdapter(bannerViewPager);
                viewPager_Banner.setOffscreenPageLimit(0);
//                viewPager_Banner.beginFakeDrag();

            }

            viewPager_Banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Banner_ViewPager.is_front = false;
                    setProgress.setText((position + 1) + " of " + jsonArray.length());
                    long pos = (position + 1) * 100 / (jsonArray.length());
                    progress_value.setProgress((int) pos);
                }

                @Override
                public void onPageSelected(int position) {
                    Banner_ViewPager.is_front = false;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            back_view.setOnClickListener(v -> {
                if (viewPager_Banner.getCurrentItem() == 0) {
                    Toast.makeText(FlashcardActivity.this, "first item", Toast.LENGTH_SHORT).show();
                } else {
                    int pos = viewPager_Banner.getCurrentItem();
                    if (Banner_ViewPager.is_front) {
                        bannerViewPager = null;
                        bannerViewPager = new Banner_ViewPager(FlashcardActivity.this, data);
                        viewPager_Banner.setAdapter(bannerViewPager);
                    }
                    viewPager_Banner.setCurrentItem(pos - 1);

                }
            });
            move_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.size() - 1 == viewPager_Banner.getCurrentItem()) {
                        Toast.makeText(FlashcardActivity.this, "last item", Toast.LENGTH_SHORT).show();
                    } else {
                        int pos = viewPager_Banner.getCurrentItem();
                        if (Banner_ViewPager.is_front) {
                            bannerViewPager = null;
                            bannerViewPager = new Banner_ViewPager(FlashcardActivity.this, data);
                            viewPager_Banner.setAdapter(bannerViewPager);
                        }
                        viewPager_Banner.setCurrentItem(pos + 1);

                    }
                }
            });

            image_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonstring, String apitype, String typeApi, boolean showprogress) throws JSONException {

    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {

    }

}