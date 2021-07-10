package com.example.courseregistration.Activity.StudentActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class UnenrollDialog extends AppCompatDialogFragment {

    String courseCode, name;
    DatabaseReference coursesDbRef = FirebaseDatabase.getInstance().getReference("courses");

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setMessage("Do you want to un-enroll from "  + courseCode + " ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeStudent(name);
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

    public void removeStudent(String username) {
        // create a new node and add the student if students node doesn't exist yet
        DatabaseReference studentRef = coursesDbRef.child(courseCode);
        studentRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<String> students = (ArrayList<String>) snapshot.child("students").getValue();
                students.remove(username);
                coursesDbRef.child(courseCode).child("students").setValue(students);
                Toast.makeText(getActivity(), "You are un-enrolled from " + courseCode, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });
    }



}
