package com.example.almasud.fundamental.firebase_service;

import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasud.fundamental.MainActivity;
import com.example.almasud.fundamental.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class EventActivity extends AppCompatActivity implements EventAdapter.OnClickListener {
    private EditText nameET, budgetET;
    private Button createUpdateBtn;
    private RecyclerView eventRV;
    private DatabaseReference dbRef;
    private ArrayList<Event> events;
    private EventAdapter adapter;
    private FirebaseUser mUser;
    private String updatedEventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbasedbevnet);
        // This line should execute first for save database locally
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        nameET = findViewById(R.id.editTextFbDbEventName);
        budgetET = findViewById(R.id.editTextFbDbBudget);
        eventRV = findViewById(R.id.recycleViewFbDbEvents);
        createUpdateBtn = findViewById(R.id.buttonFbDbEventAdd);

        events = new ArrayList<>();
        adapter = new EventAdapter(this, events);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        eventRV.setLayoutManager(layoutManager);
        eventRV.setAdapter(adapter);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        dbRef = FirebaseDatabase.getInstance().getReference("Event");
        dbRef.keepSynced(true);  // For also saved database locally;
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Event event = data.getValue(Event.class);
                    events.add(event);
                    Collections.reverse(events);
                    adapter.updateView(events);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void saveEvent(View view) {
        String name = nameET.getText().toString();
        double budget = Double.parseDouble(budgetET.getText().toString());
        String userId = mUser.getUid();
        // Code for update
        if (updatedEventId != null) {
            Event event = new Event(updatedEventId, userId, name, budget);
            dbRef.child(updatedEventId).setValue(event);
            updatedEventId = null;
            createUpdateBtn.setText("Create Event");
        } else {
            // Code for create
            String eventId = dbRef.push().getKey(); // Generates a unique key
            Event event = new Event(eventId, userId, name, budget);
            dbRef.child(eventId).setValue(event);
        }

        // Clear input fields after submit
        nameET.setText("");
        budgetET.setText("");
    }

    @Override
    public void onItemClick(Event event, View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_ud_operation, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_option_edit:
                        updatedEventId = event.getEventId();
                        nameET.setText(event.getEventName());
                        budgetET.setText(String.valueOf(event.getBudget()));
                        createUpdateBtn.setText("Update Event");
                        break;
                    case R.id.menu_option_delete:
                        MainActivity.actionDialogBuilder(EventActivity.this, "Are you sure?", "Want to delete the item", new MainActivity.OnActionListener() {
                            @Override
                            public void onAction() {
                                dbRef.child(event.getEventId()).removeValue();
                            }
                        }).show();
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }
}
