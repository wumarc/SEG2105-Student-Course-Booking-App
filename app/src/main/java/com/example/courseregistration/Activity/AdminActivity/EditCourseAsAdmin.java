package com.example.courseregistration.Activity.AdminActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseregistration.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditCourseAsAdmin extends Activity {

    private TextView codeTView, nameTView;
    private Button backBtn,editBtn;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course_as_admin);

        codeTView = findViewById(R.id.coursecodeedit);
        nameTView = findViewById(R.id.coursename1edit);
        backBtn = findViewById(R.id.backbedit);
        editBtn = findViewById(R.id.editbutt);

        showData();

        String codeStr = codeTView.getText().toString();


        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("courses");
                String code3 = codeTView.getText().toString();
                String name3 = nameTView.getText().toString();

                // Update course
                reference.child(codeStr).removeValue();
                reference.child(code3).child("code").setValue(code3);
                reference.child(code3).child("name").setValue(name3);


                // Send uer back to admin page
                Toast.makeText(EditCourseAsAdmin.this, "Course successfully edited", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(EditCourseAsAdmin.this, AdminMenu.class);
                startActivity(intent);
                finish();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditCourseAsAdmin.this, AdminMenu.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void showData() {

        Intent intent = getIntent();
        String courseCode = intent.getStringExtra("code");
        String courseName = intent.getStringExtra("name");

        codeTView.setText(courseCode);
        nameTView.setText(courseName);
    }


}
