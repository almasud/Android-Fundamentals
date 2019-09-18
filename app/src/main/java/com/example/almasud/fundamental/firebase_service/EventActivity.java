package com.example.almasud.fundamental.firebase_service;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class EventActivity extends AppCompatActivity implements EventAdapter.OnClickListener {
    private EditText nameET, budgetET;
    private RecyclerView eventRV;
    private DatabaseReference dbRef;
    private ArrayList<Event> events;
    private EventAdapter adapter;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbasedbevnet);
        nameET = findViewById(R.id.editTextFbDbEventName);
        budgetET = findViewById(R.id.editTextFbDbBudget);
        eventRV = findViewById(R.id.recycleViewFbDbEvents);

        events = new ArrayList<>();
        adapter = new EventAdapter(this, events);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        eventRV.setLayoutManager(layoutManager);
        eventRV.setAdapter(adapter);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        dbRef = FirebaseDatabase.getInstance().getReference("Event");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                events.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    Event event = data.getValue(Event.class);
                    events.add(event);
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

        String eventId = dbRef.push().getKey(); // Generates a unique key
        String userId = mUser.getUid();
        Event event = new Event(eventId, userId, name, budget);
        dbRef.child(eventId).setValue(event);

        // Clear input fields after submit
        nameET.setText("");
        budgetET.setText("");
    }

    @Override
    public void onItemClick(Event event) {
        MainActivity.actionDialogBuilder(this, "Are you sure?", "Want to delete the item", new MainActivity.OnActionListener() {
            @Override
            public void onAction() {
                dbRef.child(event.getEventId()).removeValue();
            }
        }).show();
    }
}
