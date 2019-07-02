package com.example.almasud.fundamental.activity_cycle_and_intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

/**
 * This program is shows the activity life cycle.
 * @author: Abdullah Almasud.
 */
public class EmployeeBasicActivity extends AppCompatActivity {
    public static final String EMPLOYEE_BASIC = "employeeBasic";
    Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_and_intent_employee_basic);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        nextBtn = findViewById(R.id.nextBtn);
        final EditText empFirstName = findViewById(R.id.flowerName);
        final EditText empLastName = findViewById(R.id.empLastName);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = empFirstName.getText().toString();
                String lastName = empLastName.getText().toString();

                if (firstName.isEmpty() && lastName.isEmpty()) {
                    empFirstName.setError("Name is required");
                    empLastName.setError("Name is required");
                } else {
                    Employee employee = new Employee();
                    employee.setFirstName(firstName);
                    employee.setLastName(lastName);

                    Intent intent = new Intent(EmployeeBasicActivity.this, EmployeeContactActivity.class);
                    // Send instance of employee object via intent.
                    intent.putExtra(EMPLOYEE_BASIC, employee);
                    startActivity(intent);
                }
            }
        });

        Log.d("MainActivity", "---------- onCreate ----------");
        Toast.makeText(this, "onCreate called in EmployeeBasicActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "----------onStart----------");
        Toast.makeText(this, "onStart called in EmployeeBasicActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "----------onResume----------");
        Toast.makeText(this, "onResume called in EmployeeBasicActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "----------onPause----------");
        Toast.makeText(this, "onPause called in EmployeeBasicActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "----------onStop----------");
        Toast.makeText(this, "onStop called in EmployeeBasicActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity", "----------onRestart----------");
        Toast.makeText(this, "onRestart called in Main activity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "----------onDestroy----------");
        Toast.makeText(this, "onDestroy called in EmployeeBasicActivity", Toast.LENGTH_LONG).show();
    }
}
