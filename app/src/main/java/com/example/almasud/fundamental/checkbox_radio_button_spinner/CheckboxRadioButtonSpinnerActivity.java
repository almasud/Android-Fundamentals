package com.example.almasud.fundamental.checkbox_radio_button_spinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class CheckboxRadioButtonSpinnerActivity extends AppCompatActivity {
    public static final String EMPLOYEE_EXTRA = "employeeInfo";
    TextView skillText;
    Spinner empPlatform, empCountry;
    ArrayList<String> languageSkills;
    String gender, plateForm, country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_radio_button_spinner);

        final EditText employeeName = findViewById(R.id.employeeName);
        final EditText employeeEmail = findViewById(R.id.employeeEmail);
        skillText = findViewById(R.id.skillText);
        RadioGroup genderGroup = findViewById(R.id.employeeGender);
        RadioButton radioButton = findViewById(R.id.radioMale);
        // Set default gender.
        radioButton.setChecked(true);
        gender = "Male";

        Button submit = findViewById(R.id.empSubmitBtn);
        languageSkills = new ArrayList<>();

        // Dynamic Spinner
        empPlatform = findViewById(R.id.spinnerPlatform);
        ArrayAdapter<String> platformAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getPlatforms());
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        empPlatform.setAdapter(platformAdapter);

        empPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                plateForm = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Static Spinner
        empCountry = findViewById(R.id.spinnerCountry);
        empCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                gender = rb.getText().toString();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = employeeName.getText().toString();
                String email = employeeEmail.getText().toString();

                if (name.isEmpty()) {
                    employeeName.setError("Enter your name.");
                } else if (email.isEmpty()){
                    employeeEmail.setError("Enter an email address.");
                }  else if (languageSkills.isEmpty()){
                    skillText.setError("At least one skill is required");
                } else if(empPlatform.getSelectedItemPosition() <= 0) {
                    TextView errorText = (TextView) empPlatform.getSelectedView();
                    errorText.setError("Select a platform");
                } else if(empCountry.getSelectedItemPosition() <= 0) {
                    TextView errorText = (TextView) empCountry.getSelectedView();
                    errorText.setError("Select a country");
                } else {
                    Employee employee = new Employee();
                    employee.setName(name);
                    employee.setEmail(email);
                    employee.setGender(gender);
                    employee.setLanguageSkills(languageSkills);
                    employee.setPlatform(plateForm);
                    employee.setCountry(country);

                    Intent intent = new Intent(CheckboxRadioButtonSpinnerActivity.this, EmployeeInfoActivity.class);
                    intent.putExtra(EMPLOYEE_EXTRA, employee);
                    startActivity(intent);
                }
            }
        });
    }

    // Programming language skill
    public void selectedLanguage(View view) {
        CheckBox cb = (CheckBox) view;
        boolean checked = cb.isChecked();
        String langCheckBoxText = cb.getText().toString();

        switch (view.getId()) {
            case R.id.checkJava:
                if (checked) {
                    languageSkills.add(langCheckBoxText);
                } else {
                    languageSkills.remove(langCheckBoxText);
                }
                break;
            case R.id.checkPHP:
                if (checked) {
                    languageSkills.add(langCheckBoxText);
                } else {
                    languageSkills.remove(langCheckBoxText);
                }
                break;
            case  R.id.checkPython:
                if (checked) {
                    languageSkills.add(langCheckBoxText);
                } else {
                    languageSkills.remove(langCheckBoxText);
                }
                break;
        }
    }

    // Platforms based on programming language skill
    public ArrayList<String> getPlatforms() {
        ArrayList<String> platforms = new ArrayList<>();

        platforms.add("Choose one");
        platforms.add("Mobile");
        platforms.add("Web");
        platforms.add("Desktop");
        platforms.add("Web & Mobile");
        platforms.add("Web & Desktop");
        platforms.add("Desktop & Mobile");
        platforms.add("Web, Desktop and Mobile");

        return  platforms;
    }
}
