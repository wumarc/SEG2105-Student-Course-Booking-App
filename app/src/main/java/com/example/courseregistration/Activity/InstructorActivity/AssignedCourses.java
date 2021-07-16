package com.example.courseregistration.Activity.InstructorActivity;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.Adapter.CourseAdapter;
import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AssignedCourses extends AppCompatActivity {

    CourseAdapter courseAdapter;
    ArrayList<Course> courseList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assigned_courses);

        DatabaseReference coursesNode = FirebaseDatabase.getInstance().getReference("courses");

        recyclerView = findViewById(R.id.instructorAssignedRecyclerView);
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(courseList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); // Item divider

        recyclerView.setAdapter(courseAdapter);
        recyclerView.addItemDecoration(divider);

        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent instructorEditCourse = new Intent(AssignedCourses.this, EditCourseAsInstructor.class);
                instructorEditCourse.putExtra("COURSE CODE", courseList.get(position).getCode());
                instructorEditCourse.putExtra("COURSE NAME", courseList.get(position).getName());
                instructorEditCourse.putExtra("INSTRUCTOR NAME", getIntent().getStringExtra("INSTRUCTOR NAME"));
                if (courseList.get(position).getInstructor().equals(getIntent().getStringExtra("NAME"))) {
                    instructorEditCourse.putExtra("CAPACITY", courseList.get(position).getCapacity());
                    instructorEditCourse.putExtra("DESCRIPTION", courseList.get(position).getDescription());
                }
                startActivity(instructorEditCourse);
            }
        });

        // Load the courses
        coursesNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String name = getIntent().getStringExtra("INSTRUCTOR NAME");
                courseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // only take courses that are taught by the instructor
                    if (dataSnapshot.child("instructor").getValue().toString().equals(name)) {
                        Course course = dataSnapshot.getValue(Course.class);
                        courseList.add(course);
                    }
                }
                courseAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(AssignedCourses.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}