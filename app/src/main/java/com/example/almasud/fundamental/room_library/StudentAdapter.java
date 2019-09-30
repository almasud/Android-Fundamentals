package com.example.almasud.fundamental.room_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.almasud.fundamental.R;
import com.example.almasud.fundamental.databinding.SingleStudentRowBinding;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private Context context;
    private List<Student> students;
    private OnLongClickListener onLongClickListener;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
        this.onLongClickListener = (OnLongClickListener) context;
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
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private SingleStudentRowBinding studentRowBinding;

        public StudentViewHolder(@NonNull SingleStudentRowBinding studentRowBinding) {
            super(studentRowBinding.getRoot());
            this.studentRowBinding = studentRowBinding;

            studentRowBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    // Pass Student to Activity before Delete
                    onLongClickListener.onLongClick(students.get(getAdapterPosition()));
                    // Delete Student from database
                    StudentDatabase.getInstance(context).getStudentDao().deleteStudent(
                            students.get(getAdapterPosition()));
                    // Get Student from database
                    students = StudentDatabase.getInstance(context).getStudentDao()
                            .getAllStudents();
                    notifyDataSetChanged();
                    return true;
                }
            });
        }

        public void bind(Student student) {
            studentRowBinding.setSt(student);
        }
    }

    // Update recycler view after any add or remove
    public void updateData(List<Student> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    public interface OnLongClickListener {
        void onLongClick(Student student);
    }
}
