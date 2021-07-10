package com.example.courseregistration.Activity.AdminActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

public class AddCourseAsAdmin extends Activity {

    private Button addCourse, back;
    private EditText name, code;
    private FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    private DatabaseReference reference = rootNode.getReference("courses");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        name = findViewById(R.id.coursename11);
        code = findViewById(R.id.coursecode12);
        addCourse = findViewById(R.id.addcoursebb);
        back = findViewById(R.id.addcourseback);

        // Return to the previous page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddCourseAsAdmin.this, AdminMenu.class));
                finish();
            }
        });

        // Create a course
        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseName = name.getText().toString();
                String courseCode = code.getText().toString().toUpperCase();
                Course course = new Course(courseName, courseCode, "", 0, "TBD", null, null);

                // Check for unique course code and create course
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(courseCode)) {
                            Toast.makeText(AddCourseAsAdmin.this, "Course code already used, please provide a unique code", Toast.LENGTH_LONG).show();
                        } else {
                            reference.child(courseCode).setValue(course);
                            Toast.makeText(AddCourseAsAdmin.this, "Course successfully added", Toast.LENGTH_LONG).show();

                            // Return to admin page once it's been added
                            startActivity(new Intent(AddCourseAsAdmin.this, AdminMenu.class));
                            finish();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {}
                });
            }
        });

    }

}
