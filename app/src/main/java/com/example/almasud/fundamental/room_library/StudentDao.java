package com.example.almasud.fundamental.room_library;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * DAO -> Data Access Objects
 * To access your app's data using the Room persistence library, you work with data access
 * objects, or DAOs. This set of Dao objects forms the main component of Room, as each DAO
 * includes methods that offer abstract access to your app's database. By accessing a database
 * using a DAO class instead of query builders or direct queries, you can separate different
 * components of your database architecture.
 * A DAO can be either an interface or an abstract class. If it's an abstract class, it can
 * optionally have a constructor that takes a RoomDatabase as its only parameter. Room creates
 * each DAO implementation at compile time.
 */

@Dao
public interface StudentDao {
    @Insert
    long insertStudent(Student student);

    @Insert
    long[] insertStudents(Student... students);

    @Query("SELECT * FROM tbl_student")
    List<Student> getAllStudents();

    @Query("SELECT * FROM tbl_student WHERE studentId LIKE:id")
    Student getStudentById(int id);

    @Update
    int updateStudent(Student student);

    @Delete
    int deleteStudent(Student student);
}
