package com.example.almasud.fundamental.room_library;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Automatically generate SQLite database depend of each annotation.
 */

@Entity(tableName = "tbl_student")
public class Student {
    @PrimaryKey(autoGenerate = true)
    private int studentId;
    @ColumnInfo(name = "col_student_name")
    private String name;
    @ColumnInfo(name = "col_student_city")
    private String city;
    @ColumnInfo(name = "col_student_phone")
    private String phone;
    @Ignore
    private int count;

    @Ignore
    public Student() {
    }

    public Student(String name, String city, String phone) {
        this.name = name;
        this.city = city;
        this.phone = phone;
    }

    @Ignore
    public Student(int studentId, String name, String city, String phone) {
        this.studentId = studentId;
        this.name = name;
        this.city = city;
        this.phone = phone;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
