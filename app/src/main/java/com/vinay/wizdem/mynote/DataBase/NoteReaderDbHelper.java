package com.vinay.wizdem.mynote.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by vinay_1 on 12/28/2017.
 */

public class NoteReaderDbHelper extends SQLiteOpenHelper implements CrudOperations {

    //must increment the data base version when you change the db schema
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "NoteReader.db";

    public NoteReaderDbHelper(Context context) {
        super(context, NoteReaderContract.FeedEntry.TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteReaderContract.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //CRUD operations

    @Override
    public void createNote(Note note) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(NoteReaderContract.FeedEntry.NOTE_DATA, note.getData());
        values.put(NoteReaderContract.FeedEntry.TIME_STAMP, note.getTime());

        long newRowId = db.insert(NoteReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

    @Override
    public List<Note> readNote() {
        return null;
    }

    @Override
    public int updateNote(Note note) {
        return note.get_id();
    }

    @Override
    public void deleteNote(Note note) {

    }
}
