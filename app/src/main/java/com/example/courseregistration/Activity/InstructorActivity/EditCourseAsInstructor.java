package com.example.courseregistration.Activity.InstructorActivity;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.Class.Lecture;
import com.example.courseregistration.Adapter.LectureAdapter;
import com.example.courseregistration.R;
import com.example.courseregistration.Adapter.StudentAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EditCourseAsInstructor extends AppCompatActivity {

    TextView courseCode, courseName;
    EditText capacity, lecturesDay, lecturesHours, description;
    CheckBox teachThisCourseCheckbox;
    Button addLecture, saveBtn;
    RecyclerView lectureRecyclerView;
    RecyclerView studentsRecyclerView;
    LectureAdapter lectureAdapter;
    StudentAdapter studentsAdapter;
    ArrayList<Lecture> lectureList;
    ArrayList<String> studentsList;

    DatabaseReference listCoursesDb = FirebaseDatabase.getInstance().getReference("courses");  // Connect database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_as_instructor);

        // Create variables for UI components
        courseCode = findViewById(R.id.course_code_detail);
        courseName = findViewById(R.id.course_name_detail);
        teachThisCourseCheckbox = findViewById(R.id.checkBox);
        lecturesDay = findViewById(R.id.lectures_days);
        lecturesHours = findViewById(R.id.lectures_hours);
        capacity = findViewById(R.id.capacity_detail);
        description = findViewById(R.id.editTextTextMultiLine);
        saveBtn = findViewById(R.id.button);
        addLecture = findViewById(R.id.addLecture);
        lectureRecyclerView = findViewById(R.id.recycleViewLectures);
        studentsRecyclerView = findViewById(R.id.student_recyclerView);

        // Adapter set up
        lectureList = new ArrayList<>();
        lectureAdapter = new LectureAdapter(lectureList, this);
        studentsList = new ArrayList<>();
        studentsAdapter = new StudentAdapter(studentsList, this);

        // Set up recycler view
        lectureRecyclerView.setHasFixedSize(true);
        lectureRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lectureRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // Item divider
        lectureRecyclerView.setAdapter(lectureAdapter);
        studentsRecyclerView.setHasFixedSize(true);
        studentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // Item divider
        studentsRecyclerView.setAdapter(studentsAdapter);

        // Pass course information
        String courseCodeStr = getIntent().getStringExtra("COURSE CODE");
        courseCode.setText(courseCodeStr);

        // Check if the instructor is assigned to this course yet on start
        listCoursesDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // get the rest of the data from firebase
                    Course course = snapshot.child(courseCodeStr).getValue(Course.class);
                    String instructorName = course.getInstructor();
                    String courseNameStr = course.getName();
                    String descriptionStr = course.getDescription();
                    Integer capacityStr = course.getCapacity();
                    ArrayList<Lecture> lectures = course.getLectures();
                    ArrayList<String> students = course.getStudents();

                    courseName.setText(courseNameStr); // Show the data
                    String nameInstructor = getIntent().getStringExtra("INSTRUCTOR NAME");
                    if (instructorName.equals(nameInstructor)) {
                        teachThisCourseCheckbox.setChecked(true);
                        capacity.setText(String.valueOf(capacityStr));
                        description.setText(descriptionStr);
                        if (lectures != null) { lectureList.addAll(lectures); }
                        if (students != null) { studentsList.addAll(students); }
                    }
                    lectureAdapter.notifyDataSetChanged();
                    studentsAdapter.notifyDataSetChanged();
                    checkBox();
                }
            };
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(EditCourseAsInstructor.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        teachThisCourseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = checkBox();
                if (!checked) {
                    openDialog(courseCodeStr); // open only when they un-assign themselves
                    checkBox(); // Disable EditText field
                } else {
                    saveBtn.setEnabled(true);
                }
            }
        });

        ArrayList<Lecture> lectures = new ArrayList<Lecture>(); // Array to temporarily hold the lectures before saving TODO need to add to the existing lectures list and not delete the old one when updating updating
        addLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lecturesHours.getText().toString().isEmpty() || lecturesDay.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Missing information", Toast.LENGTH_SHORT).show();
                } else {
                    Lecture lecture = new Lecture(lecturesHours.getText().toString(), lecturesDay.getText().toString());
                    lectures.add(lecture);
                    lecturesDay.setText("");
                    lecturesHours.setText("");
                    Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
                    lectureAdapter.notifyDataSetChanged();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descriptionStr = description.getText().toString();
                String instructorName = getIntent().getStringExtra("INSTRUCTOR NAME");

                if (!isNumeric(capacity.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Number(s) not detected, please enter a valid number", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(capacity.getText().toString()) <= 1 || (Integer.parseInt(capacity.getText().toString()) >= 200)) {
                        Toast.makeText(getApplicationContext(), "Please enter a valid capacity between 1 and 200 included", Toast.LENGTH_SHORT).show();
                } else if (lectures.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the lecture details", Toast.LENGTH_SHORT).show();
                } else if (descriptionStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Missing description", Toast.LENGTH_SHORT).show();
                } else {
                    listCoursesDb.child(courseCodeStr).child("instructor").setValue(instructorName);
                    listCoursesDb.child(courseCodeStr).child("capacity").setValue(Integer.parseInt(capacity.getText().toString()));
                    listCoursesDb.child(courseCodeStr).child("description").setValue(descriptionStr);
                    listCoursesDb.child(courseCodeStr).child("lectures").setValue(lectures);
                    finish();
                    Toast.makeText(EditCourseAsInstructor.this, "Course successfully assigned to you", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void openDialog(String courseCode) {

        // Create the object of AlertDialog Builder class
        AlertDialog.Builder builder = new AlertDialog.Builder(EditCourseAsInstructor.this);
        builder.setMessage("Do you want to un-assign yourself from this course?");
        builder.setTitle("Alert!");
        builder.setCancelable(false); // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show

        // Set the positive button with yes name OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // When the user click yes button then app will close
                teachThisCourseCheckbox.setChecked(false);
                checkBox();
                // Remove the data of the course from the database
                listCoursesDb.child(courseCode).child("instructor").setValue("TBD");
                listCoursesDb.child(courseCode).child("capacity").setValue(0);
                listCoursesDb.child(courseCode).child("description").setValue("");
                listCoursesDb.child(courseCode).child("lectures").setValue(null);
                finish();
            }
        });

        // Set the Negative button with No name OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // If user click no then dialog box is canceled.
                teachThisCourseCheckbox.setChecked(true);
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create(); // Create the Alert dialog
        alertDialog.show(); // Show the Alert Dialog box

    }

    private boolean checkBox() {
        if (teachThisCourseCheckbox.isChecked()) {
            capacity.setEnabled(true);
            description.setEnabled(true);
            lecturesDay.setEnabled(true);
            lecturesHours.setEnabled(true);
            return true;
        } else {
            capacity.setEnabled(false);
            description.setEnabled(false);
            lecturesDay.setEnabled(false);
            lecturesHours.setEnabled(false);
            saveBtn.setEnabled(false);
            return false;
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


}