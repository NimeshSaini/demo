package com.utkarshnew.android.courses.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteData;
import com.utkarshnew.android.courses.modal.NotesPDF.NoteList;
import com.utkarshnew.android.EncryptionModel.EncryptionData;
import com.utkarshnew.android.Model.Video;
import com.utkarshnew.android.R;
import com.utkarshnew.android.Utils.AES;
import com.utkarshnew.android.Utils.Const;
import com.utkarshnew.android.Utils.Helper;
import com.utkarshnew.android.Utils.ImageGetter;
import com.utkarshnew.android.Utils.MakeMyExam;
import com.utkarshnew.android.Utils.Network.API;
import com.utkarshnew.android.Utils.Network.APIInterface;
import com.utkarshnew.android.Utils.Network.NetworkCall;
import com.utkarshnew.android.Utils.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;


public class ConceptScreen extends AppCompatActivity implements NetworkCall.MyNetworkCallBack {
    private static final String TAG = "ConceptScreen";
    private TextView textView;
    Video video;
    TextView concept_name;
    ImageView back;
    ImageView notesIV;
    NetworkCall networkCall;
    Spannable WordtoSpan = null;
    ClipboardManager clipboard;
    String noteString;
    String noteTitle;
    Activity context;
    Dialog dialog;
    String courseId;
    String tileId;

    ArrayList<NoteData> noteList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Helper.enableScreenShot(this);
        setContentView(R.layout.concept_screen);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        context = ConceptScreen.this;
        courseId = getIntent().getExtras().getString(Const.COURSE_ID);
        networkCall = new NetworkCall(this, this);
        video = (Video) getIntent().getExtras().getSerializable(Const.VIDEO);
        courseId = getIntent().getExtras().getString(Const.COURSE_ID);
        tileId = getIntent().getExtras().getString(Const.TILE_ID);

        if (MakeMyExam.userId.equalsIgnoreCase("") || MakeMyExam.userId.equalsIgnoreCase("0")) {
            MakeMyExam.userId = SharedPreference.getInstance().getLoggedInUser().getId();
            MakeMyExam.setUserId(SharedPreference.getInstance().getLoggedInUser().getId());
        }

        intitalizeView();
    }

    private void intitalizeView() {
        textView = findViewById(R.id.txt);
        concept_name = findViewById(R.id.concept_name);
        notesIV = findViewById(R.id.notesIV);
        back = findViewById(R.id.back);
        textView.setBackgroundColor(getResources().getColor(R.color.white));

        //TextView
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(video.getDescription().trim(), Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(video.getDescription().trim()));
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setLinkTextColor(getResources().getColor(R.color.blue));
        textView.setTextIsSelectable(true);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);

        concept_name.setText(video.getTitle());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        notesIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedPreference.getInstance().getNoteData() != null && SharedPreference.getInstance().getNoteData().getNoteList() != null && SharedPreference.getInstance().getNoteData().getNoteList().size() > 0) {
                    Intent intent = new Intent(ConceptScreen.this, MyNotesActivity.class);
                    intent.putExtra(Const.VIDEO, video);
                    intent.putExtra(Const.COURSE_ID, courseId);
                    intent.putExtra(Const.TILE_ID, tileId);
                    startActivity(intent);
                    //finish();
                } else {
                    Toast.makeText(ConceptScreen.this, "No notes found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void setData() {

        String data = Html.fromHtml(video.getDescription(), new ImageGetter(ConceptScreen.this), null).toString();

        noteList = new ArrayList<>();
        if (SharedPreference.getInstance().getNoteData() != null && SharedPreference.getInstance().getNoteData().getNoteList() != null && SharedPreference.getInstance().getNoteData().getNoteList().size() > 0) {
            for (NoteData noteData : SharedPreference.getInstance().getNoteData().getNoteList()) {
                if (noteData.getUserId().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId()) && noteData.getConceptId().equalsIgnoreCase(video.getId())) {
                    noteList.add(noteData);
                }
            }
        }

        try {
            if (noteList != null && noteList.size() > 0) {
                WordtoSpan = new SpannableString(data);
                for (NoteData note : noteList) {
                    int start = note.getStart();
                    int end = note.getEnd();
                    if (note.getType().equalsIgnoreCase("note")) {
                        WordtoSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } else {
                        WordtoSpan.setSpan(new BackgroundColorSpan(0xFF6cd2ff), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                textView.setText(WordtoSpan);
            } else {
                WordtoSpan = new SpannableString(data);
                textView.setText(WordtoSpan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            final CharSequence[] selectedText = {""};
            textView.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    menu.removeItem(android.R.id.selectAll);
                    menu.removeItem(android.R.id.cut);
                    menu.removeItem(android.R.id.copy);
                    menu.removeItem(android.R.id.shareText);
                    return true;
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    menu.add(0, 1, 0, "Highlight");
                    menu.add(0, 2, 0, "Notes");
                    menu.add(0, 3, 0, "Search");
                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    try {
                        switch (item.getItemId()) {
                            case 1:
                                int min = 0;
                                int max = textView.getText().length();
                                if (textView.isFocused()) {
                                    final int selStart = textView.getSelectionStart();
                                    final int selEnd = textView.getSelectionEnd();
                                    if (selStart > 0 || selEnd > 0) {
                                        min = Math.max(0, Math.min(selStart, selEnd));
                                        max = Math.max(0, Math.max(selStart, selEnd));
                                    }

                                }
                                selectedText[0] = textView.getText().subSequence(min, max);
                                WordtoSpan.setSpan(new BackgroundColorSpan(0xFF6cd2ff), min, max, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                textView.setText(WordtoSpan);
                                setNoteList(selectedText[0].toString(), "", "highlight", min, max);
                                mode.finish();
                                break;
                            case 2:
                                int min_2 = 0;
                                int max_2 = textView.getText().length();
                                if (textView.isFocused()) {
                                    final int selStart = textView.getSelectionStart();
                                    final int selEnd = textView.getSelectionEnd();
                                    if (selStart > 0 || selEnd > 0) {
                                        min_2 = Math.max(0, Math.min(selStart, selEnd));
                                        max_2 = Math.max(0, Math.max(selStart, selEnd));
                                    }

                                }
                                selectedText[0] = textView.getText().subSequence(min_2, max_2);
                                showDialogAddNote(selectedText[0].toString(), min_2, max_2);
                                mode.finish();
                                break;
                            case 3:
                                int min_3 = 0;
                                int max_3 = textView.getText().length();
                                if (textView.isFocused()) {
                                    final int selStart = textView.getSelectionStart();
                                    final int selEnd = textView.getSelectionEnd();

                                    if (selStart > 0 || selEnd > 0) {
                                        min_3 = Math.max(0, Math.min(selStart, selEnd));
                                        max_3 = Math.max(0, Math.max(selStart, selEnd));
                                    }
                                }
                                selectedText[0] = textView.getText().subSequence(min_3, max_3);
                                Helper.GoWebViewPDFActivity(context, "Web Search", "https://www.google.com/search?q=" + selectedText[0]);
                                mode.finish();
                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }

            });


        } catch (Exception e) {
            Log.d(TAG, "error: " + e);
        }
    }

    public void showDialogAddNote(String note, int start, int end) {
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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.hideSoftKeyboard(context);
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
                Helper.hideSoftKeyboard(context);
                boolean isDataValid = true;
                /*if (TextUtils.isEmpty(noteTV.getText().toString().trim())) {
                    isDataValid = Helper.DataNotValid(noteTV);
                }*/
                if (isDataValid) {
                    WordtoSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    textView.setText(WordtoSpan);

                    noteString = note;
                    noteTitle = noteTV.getText().toString().trim();

                    setNoteList(noteString, TextUtils.isEmpty(noteTitle) ? "" : noteTitle, "note", start, end);
                    //networkCall.NetworkAPICall(API.API_ADD_MY_NOTES, "", true,false);

                    try {
                        dismissProgressDialog();
                    } catch (Exception e) {
                        Log.d("Dialog", "Dismiss: " + e.getMessage());
                    }
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

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    @Override
    public Call<String> getAPIB(String apitype, String typeApi, APIInterface service) {
        switch (apitype) {
            case API.API_ADD_MY_NOTES:
                EncryptionData getcoursedataencryptionData = new EncryptionData();
                getcoursedataencryptionData.setUser_id(SharedPreference.getInstance().getLoggedInUser().getId());
                getcoursedataencryptionData.setNote_data(noteString);
                getcoursedataencryptionData.setConcept_id(video.getId());
                String getcoursedataencryptionDatadoseStr = new Gson().toJson(getcoursedataencryptionData);
                String getcoursedatadoseStrScr = AES.encrypt(getcoursedataencryptionDatadoseStr);
                return service.addMyNotesData(getcoursedatadoseStrScr);
        }
        return null;
    }

    @Override
    public void SuccessCallBack(JSONObject jsonobject, String apitype, String typeApi, boolean showprogress) throws JSONException {
        switch (apitype) {
            case API.API_ADD_MY_NOTES:
                if (jsonobject.optString(Const.STATUS).equals(Const.TRUE)) {
                    //JSONObject data = jsonstring.getJSONObject(Const.DATA);
                    Toast.makeText(context, jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, jsonobject.optString(Const.MESSAGE), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void ErrorCallBack(String jsonstring, String apitype, String typeApi) {
        Toast.makeText(context, jsonstring, Toast.LENGTH_SHORT).show();
    }

    public void setNoteList(String queryData, String title, String type, int start, int end) {
        try {
            if (!TextUtils.isEmpty(queryData)) {
                NoteData noteData = new NoteData(MakeMyExam.userId, video.getId(), queryData, title, type, start, end);

                NoteList noteList = SharedPreference.getInstance().getNoteData();
                if (noteList != null) {
                    ArrayList<NoteData> noteDataArrayList = SharedPreference.getInstance().getNoteData().getNoteList();
                    if (noteDataArrayList != null && noteDataArrayList.size() > 0) {
                        boolean isUpdate = false;
                        for (int i = 0; i < noteDataArrayList.size(); i++) {
                            if (noteDataArrayList.get(i).getUserId().equalsIgnoreCase(SharedPreference.getInstance().getLoggedInUser().getId()) && noteDataArrayList.get(i).getConceptId().equalsIgnoreCase(video.getId()) && noteDataArrayList.get(i).getQueryData().equalsIgnoreCase(queryData) && noteDataArrayList.get(i).getStart() == start && noteDataArrayList.get(i).getEnd() == end) {
                                isUpdate = true;
                                noteDataArrayList.set(i, noteData);
                            }
                        }
                        if (!isUpdate) {
                            noteDataArrayList.add(noteData);
                            SharedPreference.getInstance().setNoteData(new NoteList(noteDataArrayList));
                        } else {
                            SharedPreference.getInstance().setNoteData(new NoteList(noteDataArrayList));
                        }
                    } else {
                        ArrayList<NoteData> noteDataArrayList1 = new ArrayList<>();
                        noteDataArrayList1.add(noteData);
                        SharedPreference.getInstance().setNoteData(new NoteList(noteDataArrayList1));
                    }
                } else {
                    ArrayList<NoteData> noteDataArrayList = new ArrayList<>();
                    noteDataArrayList.add(noteData);
                    SharedPreference.getInstance().setNoteData(new NoteList(noteDataArrayList));
                }
            }
        } catch (Exception e) {
            Log.d("TAG", "updateSeekTime: " + e.getMessage());
        }
    }


}

