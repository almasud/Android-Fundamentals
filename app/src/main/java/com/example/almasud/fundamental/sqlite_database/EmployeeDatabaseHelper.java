package com.example.almasud.fundamental.sqlite_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class EmployeeDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "employee_db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_EMPLOYEE = "tbl_employee";
    public static final String COL_EMPLOYEE_ID = "emp_id";
    public static final String COL_EMPLOYEE_NAME = "emp_name";
    public static final String COL_EMPLOYEE_DESIGNATION = "emp_designation";

    /*
        Whenever you create a table without specifying the ROWID option, you
        get an implicit auto increment column called rowid. The rowid column store
        64-bit signed integer that uniquely identify a row within the table. SQLite
        automatically creates an implicit column named rowid and automatically assigns
        an integer value whenever you insert a new row. If you create a table that has
        an INTEGER PRIMARY KEY column, this column will points to the rowid column.

        SQLite AUTOINCREMENT attribute:
        SQLite recommends that you should not use AUTOINCREMENT attribute because:
        The AUTOINCREMENT keyword imposes extra CPU, memory, disk space, and disk I/O
        overhead and should be avoided if not strictly needed. It is usually not needed.
        In addition, the way SQLite assigns a value for the AUTOINCREMENT column is
        slightly different from the way it used for rowid column. The maximum value of
        column is 9223372036854775807. If you reach this value and insert a new row,
        This time, SQLite issued an error message: [Err] 13 - database or disk is full
        Because it does not reuse the number that has not been used. The main purpose
        of using AUTOINCREMENT attribute is:
        To prevent SQLite to reuse value that has not been used or from the previously
        deleted row. If you don’t have any requirements like this, you should not use
        SQLite AUTOINCREMENT attribute in the primary key.
     */

    public static final String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE + "(" +
        COL_EMPLOYEE_ID + " INTEGER PRIMARY KEY, " +
        COL_EMPLOYEE_NAME + " TEXT, " +
        COL_EMPLOYEE_DESIGNATION + " TEXT);";

    public EmployeeDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMPLOYEE);
    }

    // This method is called only when we up to the DATABASE_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If we want to drop previous table and create again.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(db);
    }
}
