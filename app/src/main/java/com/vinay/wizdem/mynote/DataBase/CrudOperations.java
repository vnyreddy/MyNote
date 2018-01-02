package com.vinay.wizdem.mynote.DataBase;

import android.database.Cursor;

import java.util.List;

/**
 * Created by vinay_1 on 12/29/2017.
 */

public interface CrudOperations {

    void createNote(Note note);
    List<Note> readNote();
    int updateNote(Note note);
    void deleteNote(Note note);
}
