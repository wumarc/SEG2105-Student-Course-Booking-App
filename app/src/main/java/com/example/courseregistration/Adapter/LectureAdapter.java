package com.example.courseregistration.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courseregistration.Class.Lecture;
import com.example.courseregistration.R;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class LectureAdapter extends RecyclerView.Adapter<LectureAdapter.MyViewHolder> {

    ArrayList<Lecture> lectureList;
    ArrayList<Lecture> lectureListAll;
    Context context;
    OnItemClickListener mListener;

    public LectureAdapter(ArrayList<Lecture> lectureList, Context context ) {
        this.lectureList = lectureList;
        this.lectureListAll = new ArrayList<>(lectureList);
        this.context = context;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.lecture_item_display, parent, false);
        return new MyViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LectureAdapter.MyViewHolder holder, int position) {
        Lecture lecture = lectureList.get(position);
        holder.day.setText(lecture.getDay());
        holder.time.setText(lecture.getTime());
    }

    @Override
    public int getItemCount() {
        return lectureList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void SetOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView day, time;

        public MyViewHolder(@NonNull @NotNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            day = itemView.findViewById(R.id.display_lecture_day);
            time = itemView.findViewById(R.id.display_lecture_hours);

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
