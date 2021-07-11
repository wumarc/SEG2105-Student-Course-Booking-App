package com.example.courseregistration;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.courseregistration.Activity.StudentActivity.StudentMenu;
import com.example.courseregistration.Class.Lecture;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>{

    ArrayList<String> studentsList;
    Context context;
    OnItemClickListener mListener;

    public StudentAdapter(ArrayList<String> studentsList, Context context ) {
        this.studentsList = studentsList;
        this.context = context;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_item_display, parent, false);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull StudentAdapter.MyViewHolder holder, int position) {
        String student = studentsList.get(position);
        holder.name.setText(student);
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void SetOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public MyViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.display_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

    }

}
