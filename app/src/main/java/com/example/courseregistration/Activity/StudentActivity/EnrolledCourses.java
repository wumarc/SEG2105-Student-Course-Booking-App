package com.example.courseregistration.Activity.StudentActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.CourseAdapter;
import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;


public class EnrolledCourses extends AppCompatActivity {

    DatabaseReference coursesDbRef = FirebaseDatabase.getInstance().getReference("courses");
    RecyclerView recyclerView;
    ArrayList<Course> coursesList = new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrolled_courses);

        recyclerView = findViewById(R.id.enrolled_courses_students);
        CourseAdapter courseAdapter = new CourseAdapter(coursesList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set Adapter
        recyclerView.setAdapter(courseAdapter);
        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL); // Item divider
        recyclerView.addItemDecoration(divider);

        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Throw a dialog and ask the user if he/she wishes to un-enroll from the course
                String names = getIntent().getStringExtra("USERNAME");
                openDialog(coursesList.get(position).getCode(), getIntent().getStringExtra("USERNAME"));
            }
        });

        coursesDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                coursesList.clear();
                String name = getIntent().getStringExtra("USERNAME");
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ArrayList<String> studentsList = (ArrayList<String>) dataSnapshot.child("students").getValue();
                    if (studentsList != null) {
                        if (studentsList.contains(name)) {
                            Course course = dataSnapshot.getValue(Course.class);
                            coursesList.add(course);
                        }
                    }
                }
                courseAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });
    }

    public void openDialog(String courseCode, String name) {
        UnenrollDialog unenrollDialog = new UnenrollDialog();
        unenrollDialog.setCourseCode(courseCode);
        unenrollDialog.setStudentName(name);
        unenrollDialog.show(getSupportFragmentManager(), "dialog");
    }


}