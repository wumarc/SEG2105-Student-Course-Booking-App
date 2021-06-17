package com.example.courseregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addCourse extends Activity {
    private Button addc, back;
    private EditText name,code;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcourse);
        name = findViewById(R.id.coursename11);
        code = findViewById(R.id.coursecode12);
        addc = findViewById(R.id.addcoursebb);
        back = findViewById(R.id.addcourseback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addCourse.this, AdminLogin.class);
                startActivity(intent);
                finish();
            }
        });
        addc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("courses");

                String name2 = name.getText().toString();
                String code2 = code.getText().toString();

                courseHelper course = new courseHelper(name2, code2);
                reference.child(code2).setValue(course);
                Toast.makeText(addCourse.this, "Course successfully added", Toast.LENGTH_LONG).show();
                Intent intent2 = new Intent(addCourse.this, AdminLogin.class);
                startActivity(intent2);
                finish();
            }
        });
    }
}
