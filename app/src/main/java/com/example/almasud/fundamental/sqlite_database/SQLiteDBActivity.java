package com.example.almasud.fundamental.sqlite_database;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

// SQLiteOpenHelper --> Create Database and Table
// SQLiteDatabase --> CRUD --> insert, update, delete, query
// ContentValues --> insert and update
// Cursor --> Put query data

public class SQLiteDBActivity extends AppCompatActivity {
    private EmployeeDatabase employeeDatabase;
    private EditText empNameET, empDesignationET;
    private Button empSubmitBtn;
    private int empId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        empNameET = findViewById(R.id.flowerName);
        empDesignationET = findViewById(R.id.flowerPrice);
        empSubmitBtn = findViewById(R.id.empSubmitBtn);

        employeeDatabase = new EmployeeDatabase(this);
        empId = getIntent().getIntExtra("empId", 0);
        if (empId > 0) {
            Employee employee = employeeDatabase.getEmployeeById(empId);
            empNameET.setText(employee.getEmpName());
            empDesignationET.setText(employee.getEmpDesignation());
            empSubmitBtn.setText("Update");
        }
    }

    public void saveEmployee(View view) {
        Boolean status;
        if (empId > 0) {
            String name = empNameET.getText().toString();
            String designation = empDesignationET.getText().toString();
            Employee employee = new Employee(empId, name, designation);
            status = employeeDatabase.updateEmployee(employee);
        } else {
            String name = empNameET.getText().toString();
            String designation = empDesignationET.getText().toString();
            Employee employee = new Employee(name, designation);
            status = employeeDatabase.insertEmployee(employee);
        }

        if (status) {
            // Clear input fields after submission
            empNameET.setText("");
            empDesignationET.setText("");
            Toast.makeText(this, "Data is saved.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data is not saved.", Toast.LENGTH_LONG).show();
        }
    }

    public void viewEmployees(View view) {
        startActivity(new Intent(this, EmployeeListActivity.class));

    }
}
