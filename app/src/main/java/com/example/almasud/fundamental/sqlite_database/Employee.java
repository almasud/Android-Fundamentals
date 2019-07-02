package com.example.almasud.fundamental.sqlite_database;

public class Employee {
    private int empId;
    private String empName;
    private String empDesignation;

    public Employee(String empName, String empDesignation) {
        this.empName = empName;
        this.empDesignation = empDesignation;
    }

    public Employee(int empId, String empName, String empDesignation) {
        this.empId = empId;
        this.empName = empName;
        this.empDesignation = empDesignation;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }
}
