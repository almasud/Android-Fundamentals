package com.example.almasud.fundamental.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class EventProvider extends ContentProvider {
    private EventDatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public static final String AUTHORITY = "com.example.almasud.fundamental.content_provider";
    public static final String CONTENT_STRING = "content://" + AUTHORITY;

    private static final UriMatcher sURI_MATCHER;

    static {
        // Static block load always first
        sURI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        sURI_MATCHER.addURI(AUTHORITY, EventDatabaseHelper.TABLE_EVENT, 1);
        sURI_MATCHER.addURI(AUTHORITY, EventDatabaseHelper.TABLE_EVENT + "/#", 2);
    }

    public EventProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        long insertedRowId = sqLiteDatabase.insert(EventDatabaseHelper.TABLE_EVENT, null, values);
        Uri insertedUri = Uri.parse(CONTENT_STRING + "/" + EventDatabaseHelper.TABLE_EVENT + "/" + insertedRowId);
        return insertedUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        databaseHelper = new EventDatabaseHelper(getContext());
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        switch (sURI_MATCHER.match(uri)) {
            case 1:
                return sqLiteDatabase.query(EventDatabaseHelper.TABLE_EVENT, projection,
                        null, null, null, null, sortOrder);
            case 2:
                String rowId = uri.getLastPathSegment();
                return sqLiteDatabase.query(EventDatabaseHelper.TABLE_EVENT, projection,
                        selection + " = " + rowId, null, null,
                        null, sortOrder);
                default:
                    throw new IllegalArgumentException("Uri is invalid!");
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
