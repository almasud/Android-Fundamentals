package com.example.almasud.fundamental.room_library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.databinding.ActivityRoomLibraryBinding;

import java.util.List;

/**
 * The Room persistence library provides an abstraction layer over SQLite to allow
 * for more robust database access while harnessing the full power of SQLite.
 */
public class RoomLibraryActivity extends AppCompatActivity implements StudentAdapter.OnLongClickListener {
    private ActivityRoomLibraryBinding binding;
    private Student student;
    private StudentAdapter adapter;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_room_library);
        student = new Student();
        students = StudentDatabase.getInstance(this).getStudentDao().getAllStudents();
        adapter = new StudentAdapter(this, students);

        binding.recylerViewStudentList.setAdapter(adapter);
        binding.setSt(student);
    }

    public void saveStudent(View view) {
        long insertedRow = StudentDatabase.getInstance(this).getStudentDao().insertStudent(student);
        if (insertedRow > 0) {
            students = StudentDatabase.getInstance(this).getStudentDao().getAllStudents();
            adapter.updateData(students);
            binding.editTextRoomSName.setText("");
            binding.editTextRoomSCity.setText("");
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLongClick(Student student) {
        Toast.makeText(this, student.getName() + " is deleted", Toast.LENGTH_SHORT).show();
    }
}
