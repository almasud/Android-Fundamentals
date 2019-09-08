package com.example.almasud.fundamental.sqlite_database;

import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class EmployeeListActivity extends AppCompatActivity implements EmployeeAdapter.ClickListener {
    private RecyclerView employeeRV;
    private EmployeeDatabase employeeDatabase;
    private EmployeeAdapter employeeAdapter;
    private ArrayList<Employee> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);

        employeeRV = findViewById(R.id.employeeRV);
        employeeDatabase = new EmployeeDatabase(this);
        employees = employeeDatabase.getAllEmployees();
        employeeAdapter = new EmployeeAdapter(this, employees);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        employeeRV.setLayoutManager(layoutManager);
        employeeRV.setAdapter(employeeAdapter);
    }

    // This callback method is calling from EmployeeAdapter
    @Override
    public void onItemLongClick(final int position) {
        final CharSequence[] items = {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Delete")) {
                    new AlertDialog.Builder(EmployeeListActivity.this)
                    .setTitle("Confirm")
                    .setMessage("Are you sure want to delete this item?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Delete Item and reload activity
                            employeeDatabase.deleteEmployee(employees.get(position).getEmpId());
                            // Reload Activity
                            Intent intent = new Intent(EmployeeListActivity.this, EmployeeListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Toast.makeText(EmployeeListActivity.this, employees.get(position).getEmpName() + " is deleted", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
                } else if (items[which].equals("Edit")){
                    int empId = employees.get(position).getEmpId();
                    Intent intent = new Intent(EmployeeListActivity.this, SQLiteDBActivity.class);
                    intent.putExtra("empId", empId);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }

}
