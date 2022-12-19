package com.utkarshnew.android.courses.Interfaces;

import com.utkarshnew.android.courses.modal.NotesPDF.NoteData;

public interface OnMyNotesItemListener {
    void onMyNotesItemClick(NoteData myNotes);

    void onMyNotesEditClick(NoteData myNotes, int position);

    void onMyNotesDeleteClick(NoteData myNotes, int position);
}
