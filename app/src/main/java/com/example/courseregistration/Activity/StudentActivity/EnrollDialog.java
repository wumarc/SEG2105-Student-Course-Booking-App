package com.example.courseregistration.Activity.StudentActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.Class.Lecture;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EnrollDialog extends AppCompatDialogFragment {

    String courseCode, name;
    DatabaseReference coursesDbRef = FirebaseDatabase.getInstance().getReference("courses");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage("Do you want to enroll in "  + courseCode + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Check for time conflict
                        coursesDbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            Course coursesStudentWishesToBeEnrolledIn = new Course();
                            ArrayList<Course> assignedCourses = new ArrayList<Course>();
                            boolean canEnroll = true;
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                coursesStudentWishesToBeEnrolledIn = snapshot.child(courseCode).getValue(Course.class);

                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    Course course = dataSnapshot.getValue(Course.class);
                                    if (course.getStudents() != null) {
                                        if (course.getStudents().contains(name) && !course.getCode().equals(coursesStudentWishesToBeEnrolledIn.getCode())) {
                                            assignedCourses.add(course);
                                        }
                                    }
                                }

                                // Compares the lectures of the courseCode to assignedCourses
                                for (int i=0; i<assignedCourses.size(); i++) { //get the course
                                    if (assignedCourses.get(i).getLectures() != null) {
                                        for (int j = 0; j<assignedCourses.get(i).getLectures().size(); j++) { //get the lectures of the course
                                            for (Lecture singleLecture : coursesStudentWishesToBeEnrolledIn.getLectures()) { //compare each lecture
                                                if (singleLecture.equals(assignedCourses.get(i).getLectures().get(j))) {
                                                    canEnroll = false;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (canEnroll) {
                                    addStudent(name);
                                } else {
                                    Toast.makeText(getContext(), "You have a conflict with another course", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        return builder.create();
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setStudentName(String name) {
        this.name = name;
    }

    public void addStudent(String username) {
        DatabaseReference studentRef = coursesDbRef.child(courseCode);
        studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<String> studentsList = (ArrayList<String>) snapshot.child("students").getValue();
                if (studentsList == null) { // if the course still doesn't have a list, create a new one
                    ArrayList<String> students = new ArrayList<String>();
                    students.add(username);
                    coursesDbRef.child(courseCode).child("students").setValue(students);
                    Toast.makeText(getContext(), "Enrolled!", Toast.LENGTH_SHORT).show();
                } else if (!studentsList.contains(username)) { // else if there is already an existing list but student is not enrolled yet
                    studentsList.add(username);
                    coursesDbRef.child(courseCode).child("students").setValue(studentsList);
                } else {
                    Toast.makeText(getActivity(), "You are already enrolled in this course, please go to assigned courses to un-enroll", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });

    }

    public static boolean enrolledSuccessfully(String courseCode, String username){
        ArrayList<String> courseList = new ArrayList<String>();
        ArrayList<String> studentList = new ArrayList<String>();
        courseList.add(courseCode);
        studentList.add(username);

        if (studentList.contains(username)){
            return true;

        }else{
            return false;
        }

    }

    public static boolean notEnrolledSuccessfully(String courseCode, String username) {
        ArrayList<String> courseList = new ArrayList<String>();
        ArrayList<String> studentList = new ArrayList<String>();
        courseList.add(courseCode);
        studentList.add(username);

        if (!(studentList.contains(username))) {
            return true;

        } else {
            return false;

        }
    }

    public static boolean fullCapacity(String CourseCode){
        int capacity = 0;
        if (capacity>=200){
            return true;
        }else{
            return false;
        }
    }
}
