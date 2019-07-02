package com.example.almasud.fundamental.list_and_recycler_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.almasud.fundamental.R;

public class ListAndRecyclerViewActivity extends AppCompatActivity {

    Button listViewBtn, recycleViewBtn, heterogeneousBtn;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_and_recycler_view);
        listViewBtn = findViewById(R.id.listViewBtn);
        recycleViewBtn = findViewById(R.id.recyclerViewBtn);
        heterogeneousBtn = findViewById(R.id.heterogeneousLayoutBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();

        listViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ListAndRecyclerViewActivity.this, ListViewActivity.class);
                startActivity(intent);
            }
        });

        recycleViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ListAndRecyclerViewActivity.this, RecyclerViewActivity.class);
                startActivity(intent);
            }
        });

        heterogeneousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ListAndRecyclerViewActivity.this, HeterogeneousLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
