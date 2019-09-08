package com.example.almasud.fundamental.list_and_recycler_view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class EmployeeAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Employee> employees;

    public EmployeeAdapter(@NonNull Context context, ArrayList<Employee> employees) {
        super(context, R.layout.single_employee_row, employees);
        this.context = context;
        this.employees = employees;
    }

    // Return a single employee view
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (inflater != null) {
            convertView = inflater.inflate(R.layout.single_employee_row, parent, false);

            ImageView empImage = convertView.findViewById(R.id.flowerImage);
            TextView empName = convertView.findViewById(R.id.flowerName);
            TextView empDesignation = convertView.findViewById(R.id.flowerPrice);

            empImage.setImageResource(employees.get(position).getEmployeeImage());
            empName.setText(employees.get(position).getEmployeeName());
            empDesignation.setText(employees.get(position).getEmployeeDesignation());

            return convertView;
        }

        return super.getView(position, convertView, parent);
    }

    // Add a an employee
    public void addNewEmployee(Employee employee) {
        employees.add(employee);
        // Data has been changed and refresh itself.
        notifyDataSetChanged();
    }

    // Remove a an employee
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        notifyDataSetChanged();
    }
}
