package com.vinay.wizdem.mynote.DataBase;

import android.provider.BaseColumns;

/**
 * Created by vinay_1 on 12/28/2017.
 */

public final class NoteReaderContract {

    private NoteReaderContract(){

    }

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE" + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + "INTEGER PRIMARY KEY," +
                    FeedEntry._COUNT + "INTEGER, " +
                    FeedEntry.NOTE_DATA + " TEXT," +
                    FeedEntry.TIME_STAMP + " TEXT)";

    //Inner class to define table content
    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "note_table";
        public static final String NOTE_DATA = "note_content";
        public static final String TIME_STAMP = "note_entry_time";
    }
}
