package com.utkarshnew.android.courses.modal.NotesPDF;

import java.io.Serializable;
import java.util.ArrayList;

public class NoteList implements Serializable {
    ArrayList<NoteData> noteList = null;

    public NoteList(ArrayList<NoteData> noteList) {
        this.noteList = noteList;
    }

    public ArrayList<NoteData> getNoteList() {
        return noteList;
    }

    public void setNoteList(ArrayList<NoteData> noteList) {
        this.noteList = noteList;
    }
}