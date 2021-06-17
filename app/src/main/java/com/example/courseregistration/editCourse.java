package com.example.courseregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class editCourse extends Activity {
    private TextView code, name;
    private Button back,edit;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editcourse);

        code = findViewById(R.id.coursecodeedit);
        name = findViewById(R.id.coursename1edit);
        back = findViewById(R.id.backbedit);
        edit = findViewById(R.id.editbutt);

        showData();

        String code2 = code.getText().toString();
        String name2 = name.getText().toString();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editCourse.this, AdminLogin.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("courses");
                String code3 = code.getText().toString();
                String name3 = name.getText().toString();

                reference.child(code2).removeValue();
                reference.child(code3).child("code").setValue(code3);
                reference.child(code3).child("name").setValue(name3);


                Toast.makeText(editCourse.this, "Course successfully edited", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(editCourse.this, AdminLogin.class);
                startActivity(intent);
                finish();


            }
        });



    }

    private void showData() {
        Intent intent = getIntent();
        String code5 = intent.getStringExtra("code");
        String name5 = intent.getStringExtra("name");

        code.setText(code5);
        name.setText(name5);
    }

}
