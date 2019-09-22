package com.example.almasud.fundamental.content_provider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

public class EventProviderActivity extends AppCompatActivity {
    private TextView showEventsTV;
    private static final Uri sCONTENT_URI = Uri.parse(EventProvider.CONTENT_STRING + "/"
            + EventDatabaseHelper.TABLE_EVENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_provider);
        showEventsTV = findViewById(R.id.textViewCproviderEList);
        showEvents();
    }

    private void showEvents() {
        Cursor cursor = getContentResolver().query(sCONTENT_URI, null, null,
                null, null);
        StringBuilder stringBuilder = new StringBuilder();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                String name = cursor.getString(cursor.getColumnIndex(
                        EventDatabaseHelper.COL_EVENT_NAME));
                String destination = cursor.getString(cursor.getColumnIndex(
                        EventDatabaseHelper.COL_EVENT_DESTINATION));
                stringBuilder.append(name).append("\n").append(destination).append("\n\n");
            } while (cursor.moveToNext());
        }
        showEventsTV.setText(stringBuilder.toString());
    }

    public void saveEvent(View view) {
        TourEvent event = new TourEvent("Event" + System.currentTimeMillis(),
                "Dinajpur");
        ContentValues values = new ContentValues();
        values.put(EventDatabaseHelper.COL_EVENT_NAME, event.getEventName());
        values.put(EventDatabaseHelper.COL_EVENT_DESTINATION, event.getDestination());
        Uri insertedUri = getContentResolver().insert(sCONTENT_URI, values);
        Log.e("Event CP", "saveEvent" + insertedUri.toString());
        showEvents();
    }
}
