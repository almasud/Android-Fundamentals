package com.example.almasud.fundamental.room_library;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class}, version = 2)
public abstract class StudentDatabase extends RoomDatabase {
    private static StudentDatabase database;
    public abstract StudentDao getStudentDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tbl_student ADD COLUMN col_student_phone TEXT");
        }
    };

    public static StudentDatabase getInstance(Context context) {
        if (database != null) {
            return database;
        } else {
            database = Room.databaseBuilder(context, StudentDatabase.class, "student_db")
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_1_2)
                    .build();
            return database;
        }
    }
}
