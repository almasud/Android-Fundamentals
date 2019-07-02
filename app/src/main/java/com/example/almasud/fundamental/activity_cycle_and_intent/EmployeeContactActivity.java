package com.example.almasud.fundamental.activity_cycle_and_intent;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.almasud.fundamental.R;

public class EmployeeContactActivity extends AppCompatActivity {
    public static final String EMPLOYEE_INFO = "employeeInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_and_intent_employee_contact);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final EditText empPhoneNumber = findViewById(R.id.empPhoneNumber);
        final EditText empEmail = findViewById(R.id.empEmail);
        Button submitBtn = findViewById(R.id.submitBtn);

        Intent intent = getIntent();  // Get intent of previous activity.
        // Get previous instance of employee object.
        final Employee employee = (Employee) intent.getSerializableExtra(EmployeeBasicActivity.EMPLOYEE_BASIC);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String employeePhone= empPhoneNumber.getText().toString();
                String employeeEmail = empEmail.getText().toString();

                if (employeePhone.isEmpty()) {
                    empPhoneNumber.setError("Phone number is required");
                } else if (employeeEmail.isEmpty()) {
                    empEmail.setError("Email is required");
                } else {
                    employee.setPhoneNumber(employeePhone);
                    employee.setEmailAddress(employeeEmail);

                    Intent intent = new Intent(EmployeeContactActivity.this, EmployeeInfoActivity.class);
                    // Send instance of employee object via intent.
                    intent.putExtra(EMPLOYEE_INFO, employee);
                    startActivity(intent);
                }
            }
        });

        Log.d("SecondActivity", "---------- onCreate ----------");
        Toast.makeText(this, "onCreate called in EmployeeContactActivity", Toast.LENGTH_LONG).show();
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
        Log.d("SecondActivity", "---------- onStart ----------");
        Toast.makeText(this, "onStart called in EmployeeContactActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("SecondActivity", "---------- onResume ----------");
        Toast.makeText(this, "onResume called in EmployeeContactActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("SecondActivity", "---------- onPause ----------");
        Toast.makeText(this, "onPause called in EmployeeContactActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("SecondActivity", "---------- onStop ----------");
        Toast.makeText(this, "onStop called in EmployeeContactActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("SecondActivity", "---------- onRestart ----------");
        Toast.makeText(this, "onRestart called in EmployeeContactActivity", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SecondActivity", "---------- onDestroy ----------");
        Toast.makeText(this, "onDestroy called in SEmployeeContactActivity", Toast.LENGTH_LONG).show();
    }
}
