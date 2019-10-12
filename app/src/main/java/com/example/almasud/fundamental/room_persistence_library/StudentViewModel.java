package com.example.almasud.fundamental.room_persistence_library;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private StudentDao studentDao;
    private StudentDatabase studentDatabase;
    private LiveData<List<Student>> allStudents;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentDatabase = StudentDatabase.getInstance(application);
        studentDao = studentDatabase.getStudentDao();
        allStudents = studentDao.getAllStudents();
    }

    public void insert(Student student) {
        new InsertAsyncTask(studentDao).execute(student);
    }

    public void update(Student student) {
        new UpdateAsyncTask(studentDao).execute(student);
    }

    public void delete(Student student) {
        new DeleteAsyncTask(studentDao).execute(student);
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public LiveData<Student> getStudent(int id) {
        return studentDao.getStudent(id);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG, "View model destroyed");
    }

    private class InsertAsyncTask extends AsyncTask<Student, Void, Long> {
        private StudentDao studentDao;

        public InsertAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Long doInBackground(Student... students) {
            return studentDao.insert(students[0]);
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            Log.i(TAG, "In onPostExecute insertedId: " + aLong);
        }
    }

    private class UpdateAsyncTask extends AsyncTask<Student, Void, Integer> {
        private StudentDao studentDao;

        public UpdateAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Integer doInBackground(Student... students) {
            return studentDao.update(students[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.i(TAG, "In onPostExecute update: " + integer);
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Student, Void, Integer> {
        private StudentDao studentDao;

        public DeleteAsyncTask(StudentDao studentDao) {
            this.studentDao = studentDao;
        }

        @Override
        protected Integer doInBackground(Student... students) {
            return studentDao.delete(students[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.i(TAG, "In onPostExecute delete: " + integer);
        }
    }
}
