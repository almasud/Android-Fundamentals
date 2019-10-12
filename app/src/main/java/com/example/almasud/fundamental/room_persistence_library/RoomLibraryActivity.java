package com.example.almasud.fundamental.room_persistence_library;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasud.fundamental.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RoomLibraryActivity extends AppCompatActivity implements StudentListAdapter.OnDeleteClickListener {
    public static final int NEW_STUDENT_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_STUDENT_ACTIVITY_REQUEST_CODE = 2;
    private RecyclerView mRecyclerView;
    private StudentViewModel studentViewModel;
    private Student student;
    private StudentListAdapter studentListAdapter;
    private List<Student> students;
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_library);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomLibraryActivity.this, StudentFormActivity.class);
                startActivityForResult(intent, NEW_STUDENT_ACTIVITY_REQUEST_CODE);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerViewRoomStudentList);
        student = new Student();
        students = new ArrayList<>();
        studentListAdapter = new StudentListAdapter(this, students);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(studentListAdapter);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(this, new Observer<List<Student>>() {
            @Override
            public void onChanged(List<Student> students) {
                studentListAdapter.setStudents(students);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == NEW_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                if (data.getSerializableExtra("student") != null) {
                    student = (Student) data.getSerializableExtra("student");
                    // Code to insert Student
                    Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
                    studentViewModel.insert(student);
                } else {
                    Log.e(TAG, "Serialized data is null");
                }
            } else if (requestCode == UPDATE_STUDENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                if (data.getSerializableExtra("student") != null) {
                    student = (Student) data.getSerializableExtra("student");
                    // Code to update Student
                    Toast.makeText(this, "Data updated", Toast.LENGTH_SHORT).show();
                    studentViewModel.update(student);
                } else {
                    Log.e(TAG, "Serialized data is null");
                }
            } else {
                Toast.makeText(this, "No Operation execute", Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onDeleteClick(Student student) {
        // Code for delete operation
        studentViewModel.delete(student);
    }
}
