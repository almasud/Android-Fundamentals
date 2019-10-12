package com.example.almasud.fundamental.room_persistence_library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.databinding.SingleStudentRowBinding;

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder> {
    public interface OnDeleteClickListener {
        void onDeleteClick(Student student);
    }

    private Context context;
    private List<Student> students;
    private OnDeleteClickListener delteClickListener;

    public StudentListAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
        this.delteClickListener = (OnDeleteClickListener) context;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleStudentRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(
                parent.getContext()), R.layout.single_student_row, parent, false);
        return new StudentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.setData(student);
        holder.setListeners();
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        private SingleStudentRowBinding studentRowBinding;

        StudentViewHolder(@NonNull SingleStudentRowBinding studentRowBinding) {
            super(studentRowBinding.getRoot());
            this.studentRowBinding = studentRowBinding;
        }

        void setData(Student student) {
            studentRowBinding.setSt(student);
        }

        public void setListeners() {
            studentRowBinding.ivStudentRowEdit.setOnClickListener(view -> {
                Intent intent = new Intent(context, StudentFormActivity.class);
                intent.putExtra("updateStudentId", students.get(getAdapterPosition()).getStudentId());
                ((Activity) context).startActivityForResult(intent, RoomLibraryActivity.UPDATE_STUDENT_ACTIVITY_REQUEST_CODE);
            });

            studentRowBinding.ivStudentRowDelete.setOnClickListener(view -> {
                delteClickListener.onDeleteClick(students.get(getAdapterPosition()));
            });
        }
    }

    // Update recycler view after any add or remove
    void setStudents(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }
}
