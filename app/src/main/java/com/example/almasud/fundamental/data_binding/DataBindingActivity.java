package com.example.almasud.fundamental.data_binding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.databinding.ActivityDataBindingBinding;

/**
 * Data Binding: The Data Binding Library is a support library that allows you to bind UI
 * components in your layouts to data sources in your app using a declarative format rather
 * than programmatically.
 */

public class DataBindingActivity extends AppCompatActivity {
    // This class name is created form LayoutXMLFileNameBinding
    private ActivityDataBindingBinding binding;
    private Student student;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_object_binding);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        student = new Student();
        binding.setSt(student);
        message = new Message();
        binding.setMsg(message);
    }

    public void saveStudentData(View view) {
        // No need findViewById method
//        binding.textViewObjBindSName.setText(binding.editTextObjBindSName.getText().toString());
//        binding.textViewObjBindSCity.setText(binding.editTextObjBindSCity.getText().toString());
//        student = new Student(binding.editTextObjBindSName.getText().toString(),
//                binding.editTextObjBindSCity.getText().toString());
        binding.setSt(student);
    }
}
