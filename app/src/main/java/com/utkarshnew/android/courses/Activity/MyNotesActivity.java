package com.utkarshnew.android.courses.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.utkarshnew.android.courses.adapter.MyNotesRecyclerAdapter;
import com.utkarshnew.android.courses.Interfaces.OnMyNotesItemListener;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteData;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteList;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class MyNotesActivity extends AppCompatActivity implements NetworkCall.MyNetworkCallBack, OnMyNotesItemListener {

    Context context;
    RecyclerView recycler_notes;
    TextView tv_no_data_found;
    NestedScrollView ll_main;
    ImageView imageIV;
    NetworkCall networkCall;
    LinearLayoutManager linearLayoutManager;
    MyNotesRecyclerAdapter myNotesRecyclerAdapter;
    FloatingActionButton floatingActionButton;
    Video video;
    String courseId;
    String tileId;
    ArrayList<NoteData> noteList = new ArrayList<>();
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MyNotesActivity.this;
        Helper.enableScreenShot(this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.activity_my_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myProgress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow_back_black);

        networkCall = new NetworkCall(this, this);
        video = (Video) getIntent().getExtras().getSerializable(Const.VIDEO);
        courseId = getIntent().getExtras().getString(Const.COURSE_ID);
        tileId = getIntent().getExtras().getString(Const.TILE_ID);
        if (getIntent().getExtras().getString("notes_data") != null && !getIntent().getExtras().getString("notes_data").equalsIgnoreCase("")) {
            noteList = new Gson().fromJson(getIntent().getExtras().getString("notes_data"), new TypeToken<ArrayList<NoteData>>() {
            }.getType());

        }

      /*  if (SharedPreference.getInstance().getNoteData() != null && SharedPreference.getInstance().getNoteData().getNoteList() != null && SharedPreference.getInstance().getNoteData().getNoteList().size()>0) {
            for (NoteData noteData: SharedPreference.getInstance().getNoteData().getNoteList()){
                if (noteData.getUserId().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId()) && noteData.getConceptId().equalsIgnoreCase(video.getId())) {
                    noteList.add(noteData);
                }
            }
        }
*/
        initView();
    }

    private void initView() {
        recycler_notes = (RecyclerView) findViewById(R.id.recycler_notes);
        recycler_notes.setNestedScrollingEnabled(false);
        tv_no_data_found = (TextView) findViewById(R.id.tv_no_data_found);
        ll_main = (NestedScrollView) findViewById(R.id.ll_main);
        imageIV = (ImageView) findViewById(R.id.imageIV);
        floatingActionButton = findViewById(R.id.floating_button);

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_notes.setLayoutManager(linearLayoutManager);

        createRecycler(noteList);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyNotesActivity.this, MyNotesSelectionActivity.class);
                intent.putExtra(Const.VIDEO, video);
                intent.putExtra(Const.NOTE_DATA, noteList);
                intent.putExtra(Const.COURSE_ID, courseId);
                intent.putExtra(Const.TILE_ID, tileId);
                Helper.gotoActivity_finish(intent, MyNotesActivity.this);
                //finish();
            }
        });
    }

    /*public ArrayList<String> getNoteList() {
        ArrayList<String> finalNoteData = new ArrayList<>();
        NoteList noteList = SharedPreference.getInstance().getNoteData();
        if (noteList != null) {
            ArrayList<NoteData> noteDataArrayList = SharedPreference.getInstance().getNoteData().getNoteList();
            if (noteDataArrayList != null && noteDataArrayList.size() > 0) {
                for (NoteData noteData : noteDataArrayList) {
                    if (noteData.getUserId().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId())) {
                        finalNoteData.add(noteData.getQueryData());
                    }
                }
            }
        }
        return finalNoteData;
    }*/

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
                myNotesRecyclerAdapter = new MyNotesRecyclerAdapter(context, myNotesArrayList);
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
    public void onMyNotesItemClick(NoteData myNotes) {

    }

    @Override
    public void onMyNotesEditClick(NoteData myNotes, int position) {
        if (myNotes.getType().equalsIgnoreCase("note")) {
            showDialogAddNote(myNotes, position);
        } else {
            editDeleteNoteList("delete", myNotes, position);
        }
    }

    @Override
    public void onMyNotesDeleteClick(NoteData myNotes, int position) {
        editDeleteNoteList("delete", myNotes, position);
    }

    public void showDialogAddNote(NoteData noteData, int position) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
        dialog.setContentView(R.layout.add_notes_dialog);
        final TextView TV1 = dialog.findViewById(R.id.TV1);
        final EditText noteTV = dialog.findViewById(R.id.noteTV);
        Button cancelBtn = dialog.findViewById(R.id.cancelBtn);
        Button submitBtn = dialog.findViewById(R.id.submitBtn);
        TV1.setText(getResources().getString(R.string.add_note_message));

        if (noteData != null && !TextUtils.isEmpty(noteData.getTitle())) {
            noteTV.setText(noteData.getTitle());
        }

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.hideSoftKeyboard(MyNotesActivity.this);
                try {
                    //dialog.dismiss();
                    dismissProgressDialog();
                } catch (Exception e) {
                    Log.d("Dialog", "Dismiss: " + e.getMessage());
                }
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.hideSoftKeyboard(MyNotesActivity.this);

                String noteTitle = noteTV.getText().toString().trim();
                noteData.setTitle(TextUtils.isEmpty(noteTitle) ? "" : noteTitle);

                editDeleteNoteList("edit", noteData, position);

                try {
                    dismissProgressDialog();
                } catch (Exception e) {
                    Log.d("Dialog", "Dismiss: " + e.getMessage());
                }
            }
        });

        if (!((Activity) context).isFinishing()) {
            dialog.show();
        } else {
            Log.d("Dialog", "Show");
        }
    }

    public void dismissProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void editDeleteNoteList(String noteType, NoteData noteData, int position) {
        try {
            if (noteType.equalsIgnoreCase("edit")) {
                noteList.set(position, noteData);
                SharedPreference.getInstance().setNoteData(new NoteList(noteList));
                if (myNotesRecyclerAdapter != null) {
                    myNotesRecyclerAdapter.notifyDataSetChanged();
                }
            } else {
                noteList.remove(position);
                SharedPreference.getInstance().setNoteData(new NoteList(noteList));
                if (myNotesRecyclerAdapter != null) {
                    myNotesRecyclerAdapter.notifyItemRemoved(position);
                    myNotesRecyclerAdapter.notifyItemRangeChanged(position, noteList.size());
                }
                if (SharedPreference.getInstance().getNoteData() != null && SharedPreference.getInstance().getNoteData().getNoteList() != null && SharedPreference.getInstance().getNoteData().getNoteList().size() > 0) {
                } else {
                    ll_main.setVisibility(View.GONE);
                    tv_no_data_found.setText(getResources().getString(R.string.no_data_found));
                    tv_no_data_found.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            Log.d("TAG", "updateSeekTime: " + e.getMessage());
        }
    }


    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}
