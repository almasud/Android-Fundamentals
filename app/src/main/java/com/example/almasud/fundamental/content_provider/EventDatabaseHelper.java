package com.example.almasud.fundamental.content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EventDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "event_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_EVENT = "tbl_event";
    public static final String COL_EVENT_ID = "event_id";
    public static final String COL_EVENT_NAME = "event_name";
    public static final String COL_EVENT_DESTINATION = "destination";

    public static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EVENT + "(" +
            COL_EVENT_ID + " INTEGER PRIMARY KEY, " +
            COL_EVENT_NAME + " TEXT, " +
            COL_EVENT_DESTINATION + " TEXT);";

    public EventDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // If we want to drop previous table and create again.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENT);
        onCreate(sqLiteDatabase);
    }
}
