package com.example.almasud.fundamental.checkbox_radio_button_spinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

public class EmployeeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekbox_radio_button_spinner_employee_info);

        TextView empInfo = findViewById(R.id.empInfo);
        Intent intent = getIntent();
        Employee employee = (Employee) intent.getSerializableExtra(CheckboxRadioButtonSpinnerActivity.EMPLOYEE_EXTRA);

        StringBuilder skills = new StringBuilder();
        for(String skill: employee.getLanguageSkills()) {
            if (skill.equals(employee.getLanguageSkills().get(employee.getLanguageSkills().size() - 1)))
                skills.append(skill);
            else
                skills.append(skill).append(", ");
        }
        empInfo.setText(new StringBuilder().append("Name: ")
                .append(employee.getName())
                .append("\nEmail: ").append(employee.getEmail())
                .append("\nGender: ").append(employee.getGender())
                .append("\nSkill: ").append(skills)
                .append("\nPlatform: ").append(employee.getPlatform())
                .append("\nCountry: ").append(employee.getCountry())
                .toString());
    }
}
