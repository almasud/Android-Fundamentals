package com.example.almasud.fundamental.sqlite_database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.almasud.fundamental.R;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private Context context;
    private ArrayList<Employee> employees;
    private ClickListener clickListener;

    public interface ClickListener {
        void onItemLongClick(int position);
    }

    public EmployeeAdapter(Context context, ArrayList<Employee> employees) {
        this.context = context;
        this.employees = employees;
        this.clickListener = (ClickListener) context;
    }

    @NonNull
    @Override
    public EmployeeAdapter.EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_emp_row, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeAdapter.EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.empName.setText(employee.getEmpName());
        holder.empDesignation.setText(employee.getEmpDesignation());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView empName, empDesignation;
        public EmployeeViewHolder(View itemView) {
            super(itemView);
            empName = itemView.findViewById(R.id.flowerName);
            empDesignation = itemView.findViewById(R.id.flowerPrice);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onItemLongClick(getAdapterPosition());
                    return false;
                }
            });
        }
    }

}
