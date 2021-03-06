package com.example.courseregistration.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.R;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.MyViewHolder> {

    ArrayList<Course> courseList;
    ArrayList<Course> FilteredCourseList;
    Context context;
    OnItemClickListener mListener;

    public CourseAdapter(ArrayList<Course> courseList, Context context) {
        this.courseList = courseList;
        this.FilteredCourseList = courseList;
        this.context = context;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.course_item_display, parent, false);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CourseAdapter.MyViewHolder holder, int position) {
        Course course = courseList.get(position);
        holder.courseCode.setText(course.getCode());
        holder.courseName.setText(course.getName());
        holder.instructorName.setText(course.getInstructor());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView courseCode, courseName, instructorName;

        public MyViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            courseCode = itemView.findViewById(R.id.display_course_code);
            courseName = itemView.findViewById(R.id.display_course_name);
            instructorName = itemView.findViewById(R.id.display_instructor_name);

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
