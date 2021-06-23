package com.example.courseregistration.Activity.InstructorActivity;
import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

public class EditCourseAsInstructor extends AppCompatActivity {

    TextView courseCode, courseName;
    EditText capacity, students, lectures, description;
    CheckBox teachThisCourseCheckbox;
    Button saveBtn;
    FirebaseDatabase rootDb;
    DatabaseReference coursesNode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_as_instructor);

        // Connect database
        rootDb = FirebaseDatabase.getInstance();
        coursesNode = rootDb.getReference("courses");

        // Create objects for UI components
        courseCode = findViewById(R.id.course_code_detail);
        courseName = findViewById(R.id.course_name_detail);
        teachThisCourseCheckbox = findViewById(R.id.checkBox);
        lectures = findViewById(R.id.lectures_detail);
        capacity = findViewById(R.id.capacity_detail);
        description = findViewById(R.id.editTextTextMultiLine);
        saveBtn = findViewById(R.id.button);

        // Pass course information
        String courseCodeStr = getIntent().getStringExtra("COURSE CODE");
        String courseNameStr = getIntent().getStringExtra("COURSE NAME");
        int courseCapacity = getIntent().getIntExtra("CAPACITY", 0);
        String descriptionStr = getIntent().getStringExtra("DESCRIPTION");
        courseCode.setText(courseCodeStr);
        courseName.setText(courseNameStr);
        capacity.setText(String.valueOf(courseCapacity));
        description.setText(descriptionStr);

        // Check if the instructor is assigned to this course yet on start
        coursesNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String assigned = snapshot.child(courseCodeStr).child("instructor").getValue(String.class);
                    // Set initial state
                    if (assigned.equals(getIntent().getStringExtra("INSTRUCTOR NAME"))) {
                        teachThisCourseCheckbox.setChecked(true);
                    }
                    checkBox();
                }
            };
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {}
        });

        teachThisCourseCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean checked = checkBox();
                if (checked) {
                    saveBtn.setEnabled(true);
                } else {
                    openDialog(courseCodeStr); // open only when they un-assign themselves
                    checkBox(); // Disable EditText field
//                    if (!checkBox()) {
//                        capacity.setText(0);
//                        description.setText("");
//                        saveBtn.setEnabled(false);
//                    }
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int capacityInt = Integer.parseInt(capacity.getText().toString());
                String descriptionStr = description.getText().toString();

                if (capacityInt == 0) {
                    Toast.makeText(getApplicationContext(), "Please enter the capacity", Toast.LENGTH_SHORT).show();
                }
                if (descriptionStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Description missing", Toast.LENGTH_SHORT).show();
                }

                if (checkBox() && capacityInt != 0 && !descriptionStr.isEmpty()) {
                    coursesNode.child(courseCodeStr).child("instructor").setValue(getIntent().getStringExtra("INSTRUCTOR NAME"));
                    coursesNode.child(courseCodeStr).child("capacity").setValue(capacityInt);
                    coursesNode.child(courseCodeStr).child("description").setValue(descriptionStr);
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
        builder.setTitle("Alert !");
        builder.setCancelable(false); // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show

        // Set the positive button with yes name OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { // When the user click yes button then app will close
                teachThisCourseCheckbox.setChecked(false);
                checkBox();
                // Remove the data of the course from the database
                coursesNode.child(courseCode).child("instructor").setValue("To be assigned");
                coursesNode.child(courseCode).child("capacity").setValue(0);
                coursesNode.child(courseCode).child("description").setValue("");
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
            lectures.setEnabled(true);
            return true;
        } else {
            capacity.setEnabled(false);
            description.setEnabled(false);
            lectures.setEnabled(false);
            saveBtn.setEnabled(false);
            return false;
        }
    }

}