package com.example.almasud.fundamental.room_library;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)
public abstract class StudentDatabase extends RoomDatabase {
    private static StudentDatabase database;

    public abstract StudentDao getStudentDao();

    public static StudentDatabase getInstance(Context context) {
        if (database != null) {
            return database;
        } else {
            database = Room.databaseBuilder(context, StudentDatabase.class, "student_db")
                    .allowMainThreadQueries().build();
            return database;
        }
    }
}
