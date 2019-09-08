package com.example.almasud.fundamental.list_and_recycler_view;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ListView employeeListView;
    private ArrayList<Employee> employees;
    private Employee employee;
    private EmployeeAdapter employeeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
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

        employeeListView = findViewById(R.id.employeeListView);
        employee = new Employee();
        employees = employee.getAllEmployees();
        employeeAdapter = new EmployeeAdapter(this, employees);
        employeeListView.setAdapter(employeeAdapter);

        employeeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this, employees.get(position).getEmployeeName(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
