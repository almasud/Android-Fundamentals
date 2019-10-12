package com.example.almasud.fundamental.room_persistence_library;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.databinding.ActivityStudentFormBinding;

/**
 * The Room persistence library provides an abstraction layer over SQLite to allow
 * for more robust database access while harnessing the full power of SQLite.
 */
public class StudentFormActivity extends AppCompatActivity {
    private ActivityStudentFormBinding studentFormBinding;
    private Student student;
    private StudentViewModel studentViewModel;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        studentFormBinding = DataBindingUtil.setContentView(this, R.layout.activity_student_form);
        studentViewModel = ViewModelProviders.of(this).get(StudentViewModel.class);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            studentId = bundle.getInt("updateStudentId");
            studentViewModel.getStudent(studentId).observe(this, new Observer<Student>() {
                @Override
                public void onChanged(Student student) {
                    studentFormBinding.setSt(student);
                }
            });
            studentFormBinding.buttonRoomSSave.setText(getString(R.string.update));
        } else {
            // Initialize student form fields for insert
            student = new Student();
            studentFormBinding.setSt(student);
        }
    }

    public void saveStudent(View view) {
        Intent resultIntent = new Intent();
        String name = studentFormBinding.editTextRoomSName.getText().toString();
        String city = studentFormBinding.editTextRoomSCity.getText().toString();
        String phone = studentFormBinding.editTextRoomSPhone.getText().toString();

        if (TextUtils.isEmpty(name) &&
                TextUtils.isEmpty(city) &&
                TextUtils.isEmpty(phone)) {
            setResult(RESULT_CANCELED, resultIntent);
        } else {
            Bundle bundle = new Bundle();
            if (studentFormBinding.buttonRoomSSave.getText().toString().equals(getString(R.string.update)))
                bundle.putSerializable("student", new Student(studentId, name, city, phone));
            else
                bundle.putSerializable("student", student);
            resultIntent.putExtras(bundle);
            setResult(RESULT_OK, resultIntent);
        }
        finish();
    }

    public void cancelSave(View view) {
        finish();
    }
}
