package com.example.almasud.fundamental.sqlite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class EmployeeDatabase {
    private EmployeeDatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public EmployeeDatabase(Context context) {
        databaseHelper = new EmployeeDatabaseHelper(context);
    }

    public void open() {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public Boolean insertEmployee(Employee employee) {
        this.open();
        /*
          ContentValues are used to update/insert data into a databases.
          It is important to use ContentValues to prevent SQL injections.
         */
        ContentValues values = new ContentValues();
        values.put(EmployeeDatabaseHelper.COL_EMPLOYEE_NAME, employee.getEmpName());
        values.put(EmployeeDatabaseHelper.COL_EMPLOYEE_DESIGNATION, employee.getEmpDesignation());
        long insertedRow = database.insert(EmployeeDatabaseHelper.TABLE_EMPLOYEE, null, values);
        this.close();
        return insertedRow > 0;
    }

    public Boolean updateEmployee(Employee employee) {
        this.open();
        ContentValues values = new ContentValues();
        values.put(EmployeeDatabaseHelper.COL_EMPLOYEE_NAME, employee.getEmpName());
        values.put(EmployeeDatabaseHelper.COL_EMPLOYEE_DESIGNATION, employee.getEmpDesignation());
        int updatedRow = database.update(EmployeeDatabaseHelper.TABLE_EMPLOYEE, values,
                EmployeeDatabaseHelper.COL_EMPLOYEE_ID + "=" + employee.getEmpId(),
                null);
        return updatedRow > 0;
    }

    public ArrayList<Employee> getAllEmployees() {
        this.open();
        ArrayList<Employee> employees = new ArrayList<>();
        Cursor cursor = database.query(EmployeeDatabaseHelper.TABLE_EMPLOYEE, null,
                null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                int empId = cursor.getInt(cursor.getColumnIndex(EmployeeDatabaseHelper.COL_EMPLOYEE_ID));
                String empName = cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHelper.COL_EMPLOYEE_NAME));
                String empDesignation = cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHelper.COL_EMPLOYEE_DESIGNATION));
                employees.add(new Employee(empId, empName, empDesignation));
            } while (cursor.moveToNext());
            cursor.close();
        }
        this.close();
        return employees;
    }

    public Employee getEmployeeById(int id) {
        this.open();
        Employee employee = null;
        Cursor cursor = database.query(EmployeeDatabaseHelper.TABLE_EMPLOYEE,
                null, EmployeeDatabaseHelper.COL_EMPLOYEE_ID + "=" + id,
                null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            int empId = cursor.getInt(cursor.getColumnIndex(EmployeeDatabaseHelper.COL_EMPLOYEE_ID));
            String empName = cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHelper.COL_EMPLOYEE_NAME));
            String empDesignation = cursor.getString(cursor.getColumnIndex(EmployeeDatabaseHelper.COL_EMPLOYEE_DESIGNATION));
            employee = new Employee(empId, empName, empDesignation);
            cursor.close();
        }
        this.close();
        return employee;
    }

    public Boolean deleteEmployee(int id) {
        this.open();
        int deletedRow = database.delete(EmployeeDatabaseHelper.TABLE_EMPLOYEE,
                EmployeeDatabaseHelper.COL_EMPLOYEE_ID + "=" + id, null);
        this.close();
        return deletedRow > 0;
    }
}
