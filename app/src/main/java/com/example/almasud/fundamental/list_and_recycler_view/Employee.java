package com.example.almasud.fundamental.list_and_recycler_view;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class Employee {
    private int employeeImage;
    private String employeeName;
    private String employeeDesignation;

    public Employee() {
    }

    public Employee(int employeeImage, String employeeName, String employeeDesignation) {
        this.employeeImage = employeeImage;
        this.employeeName = employeeName;
        this.employeeDesignation = employeeDesignation;
    }

    public int getEmployeeImage() {
        return employeeImage;
    }

    public void setEmployeeImage(int employeeImage) {
        this.employeeImage = employeeImage;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeDesignation() {
        return employeeDesignation;
    }

    public void setEmployeeDesignation(String employeeDesignation) {
        this.employeeDesignation = employeeDesignation;
    }

    public ArrayList<Employee> getAllEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Ezaz", "Sr. Software Engineer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Abdullah Almasud", "Jr. Software Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));
        employees.add(new Employee(R.mipmap.ic_launcher, "Rashid", "Web Developer"));

        return employees;
    }
}
