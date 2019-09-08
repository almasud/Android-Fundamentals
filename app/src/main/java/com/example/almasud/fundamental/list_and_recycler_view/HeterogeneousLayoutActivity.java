package com.example.almasud.fundamental.list_and_recycler_view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class HeterogeneousLayoutActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Object> objects = new ArrayList<>();
    private ObjectAdapter objectAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heterogeneous_layout);
        recyclerView = findViewById(R.id.heterogeneous_LRV);

        objects.add(new Car(R.drawable.car, "Toyota Corolla", "Toyota"));
        objects.add(new Movie("Terminator", "Action"));
        objects.add(new Car(R.drawable.car, "BMW", "BMW"));
        objects.add(new Movie("Terminator 2", "Action"));
        objects.add(new Car(R.drawable.car, "Mercedes", "Mercedes Benz"));
        objects.add(new Movie("Terminator 3", "Action"));
        objects.add(new Car(R.drawable.car, "Toyota Corolla", "Toyota"));
        objects.add(new Movie("Terminator", "Action"));
        objects.add(new Car(R.drawable.car, "BMW", "BMW"));
        objects.add(new Movie("Terminator 2", "Action"));
        objects.add(new Car(R.drawable.car, "Mercedes", "Mercedes Benz"));
        objects.add(new Movie("Terminator 3", "Action"));

        objectAdapter = new ObjectAdapter(this, objects);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(objectAdapter);
    }
}
