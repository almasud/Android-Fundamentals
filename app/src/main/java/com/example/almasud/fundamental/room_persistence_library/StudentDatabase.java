package com.example.almasud.fundamental.room_persistence_library;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class}, version = 3)
public abstract class StudentDatabase extends RoomDatabase {
    private static volatile StudentDatabase database;

    public abstract StudentDao getStudentDao();

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE tbl_student ADD COLUMN col_student_phone TEXT");
        }
    };

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            /*
              Since SQLite does not fully support ALTER TABLE statements. The only thing we can
              do is rename a table and/or add columns. If we want to rename a column, our best
              option is to create a new table with the new columns data type/names, and to drop
              the old table in order to rename the new one.
             */
            // Rename the exist table to tmp
            database.execSQL("ALTER TABLE tbl_student RENAME TO tmp");
            // Create a new table to modified column with the name of exist table "tbl_student"
            database.execSQL("CREATE TABLE tbl_student (" +
                    "col_student_id INTEGER NOT NULL PRIMARY KEY, " +
                    "col_student_name TEXT NOT NULL," +
                    "col_student_city TEXT NOT NULL," +
                    "col_student_phone TEXT NOT NULL)"
            );
            // Insert all data into newly created table "tbl_student" from old table "tmp"
            // in order to keep all data safe
            database.execSQL("INSERT INTO tbl_student (" +
                    "col_student_id, col_student_name, col_student_city, col_student_phone)" +
                    "SELECT studentId, col_student_name, col_student_city, col_student_phone " +
                    "FROM tmp"
            );
            // Finally drop the old table "tmp"
            database.execSQL("DROP TABLE tmp");
        }
    };

    public static StudentDatabase getInstance(final Context context) {
        synchronized (StudentDatabase.class) {
            if (database == null) {
                database = Room.databaseBuilder(context, StudentDatabase.class, "student_db")
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                        .build();
            }
        }
        return database;
    }
}
