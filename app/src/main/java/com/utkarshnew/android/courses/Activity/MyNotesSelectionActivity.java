package com.utkarshnew.android.courses.Activity;

import static com.utkarshnew.android.courses.Fragment.ExamPrepLayer2.actual_videolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteData;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Webview.RevisionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;

public class MyNotesSelectionActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {

    Context context;
    RecyclerView recycler_notes;
    TextView tv_no_data_found;
    NestedScrollView ll_main;
    ImageView imageIV;
    NetworkCall networkCall;
    LinearLayoutManager linearLayoutManager;
    NoteItemRecyclerAdapter myNotesRecyclerAdapter;
    Button buttonProceed;
    ArrayList<NoteData> myNotesArrayList = new ArrayList<>();
    Video video;
    String courseId;
    String tileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MyNotesSelectionActivity.this;
        Helper.enableScreenShot(this);
        setContentView(R.layout.activity_my_notes_selection);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Toolbar toolbar = (Toolbar) findViewById(R.id.myProgress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);

        networkCall = new NetworkCall(this, this);
        video = (Video) getIntent().getExtras().getSerializable(Const.VIDEO);
        myNotesArrayList = (ArrayList<NoteData>) getIntent().getExtras().getSerializable(Const.NOTE_DATA);
        courseId = getIntent().getExtras().getString(Const.COURSE_ID);
        tileId = getIntent().getExtras().getString(Const.TILE_ID);

        initView();
    }

    private void initView() {
        recycler_notes = (RecyclerView) findViewById(R.id.recycler_notes);
        recycler_notes.setNestedScrollingEnabled(false);
        tv_no_data_found = (TextView) findViewById(R.id.tv_no_data_found);
        ll_main = (NestedScrollView) findViewById(R.id.ll_main);
        imageIV = (ImageView) findViewById(R.id.imageIV);
        buttonProceed = findViewById(R.id.buttonProceed);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_notes.setLayoutManager(linearLayoutManager);

        createRecycler(myNotesArrayList);

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                ArrayList<NoteData> noteDataArrayList = new ArrayList<>();
                for (NoteData noteData : myNotesArrayList) {
                    if (noteData.isSelect()) {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("terms", "");
                            jsonObject.put("description", noteData.getQueryData());
                            jsonObject.put("Q_id", gen());
                            jsonArray.put(jsonObject);
                            noteDataArrayList.add(noteData);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                if (noteDataArrayList != null && noteDataArrayList.size() >= 2) {
                    Intent intent = new Intent(MyNotesSelectionActivity.this, RevisionActivity.class);
                    intent.putExtra("t_id", tileId);
                    intent.putExtra("v_list", actual_videolist);
                    intent.putExtra("f_id", video.getId());
                    intent.putExtra("c_id", courseId);
                    intent.putExtra("title", video.getTitle());
                    intent.putExtra("url", jsonArray.toString());
                    intent.putExtra("via", "notes");
                    Helper.gotoActivity_finish(intent, MyNotesSelectionActivity.this);

                    //finish();
                } else {
                    Snackbar.make(v, "Please select atleast two notes", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        checkOnBackPressAction();
    }

    @Override
    public boolean onSupportNavigateUp() {
        checkOnBackPressAction();
        return true;
    }

    public void checkOnBackPressAction() {
        supportFinishAfterTransition();
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

    private void createRecycler(ArrayList<NoteData> myNotesArrayList) {
        if (myNotesArrayList != null) {
            if (myNotesArrayList.size() > 0) {
                ll_main.setVisibility(View.VISIBLE);
                tv_no_data_found.setVisibility(View.GONE);
                myNotesRecyclerAdapter = new NoteItemRecyclerAdapter(myNotesArrayList);
                recycler_notes.setAdapter(myNotesRecyclerAdapter);
            } else {
                ll_main.setVisibility(View.GONE);
                tv_no_data_found.setText(getResources().getString(R.string.no_data_found));
                tv_no_data_found.setVisibility(View.VISIBLE);
            }
        } else {
            ll_main.setVisibility(View.GONE);
            tv_no_data_found.setText(getResources().getString(R.string.no_data_found));
            tv_no_data_found.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public class NoteItemRecyclerAdapter extends RecyclerView.Adapter<NoteItemRecyclerAdapter.SubjectItemHolder> {
        ArrayList<NoteData> myNotesArrayList;

        public NoteItemRecyclerAdapter(ArrayList<NoteData> myNotesArrayList) {
            this.myNotesArrayList = myNotesArrayList;
        }

        @NonNull
        @Override
        public SubjectItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_note_item, parent, false);
            return new SubjectItemHolder(view);
        }

        @Override
        public int getItemCount() {
            return myNotesArrayList.size();
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectItemHolder holder, int position) {
            holder.setSingleFAQData(myNotesArrayList, position);
        }

        public class SubjectItemHolder extends RecyclerView.ViewHolder {
            private TextView questiontextTV;
            private CheckBox selectCB;
            private LinearLayout parentLL;

            public SubjectItemHolder(View itemView) {
                super(itemView);
                questiontextTV = (TextView) itemView.findViewById(R.id.questiontextTV);
                selectCB = (CheckBox) itemView.findViewById(R.id.selectCB);
                parentLL = itemView.findViewById(R.id.parentLL);
            }

            public void setSingleFAQData(ArrayList<NoteData> createTestSubjectsFinal, int pos) {
                NoteData noteData = createTestSubjectsFinal.get(pos);
                questiontextTV.setText(pos + 1 + ". " + ((TextUtils.isEmpty(noteData.getQueryData())) ? "N/A" : noteData.getQueryData()));
                if (noteData.isSelect()) {
                    selectCB.setChecked(true);
                } else {
                    selectCB.setChecked(false);

                }
                selectCB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (noteData.isSelect()) {
                            noteData.setSelect(false);
                        } else {
                            noteData.setSelect(true);
                        }
                        checkProceedButton(buttonProceed);
                    }
                });
            }
        }

        private void checkProceedButton(Button buttonProceed) {
            boolean changeButton = false;
            for (NoteData noteData : myNotesArrayList) {
                if (noteData.isSelect()) {
                    changeButton = true;
                    break;
                }
            }

            ArrayList<NoteData> createNoteSelected = new ArrayList<>();
            for (NoteData noteData : myNotesArrayList) {
                if (noteData.isSelect()) {
                    createNoteSelected.add(noteData);
                }
            }

            if (changeButton) {
                buttonProceed.setBackground(getResources().getDrawable(R.drawable.bg_round_corners_button));
            } else {
                buttonProceed.setBackground(getResources().getDrawable(R.drawable.bg_round_corners_button_fade));
            }
        }
    }

    public int gen() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }

}
