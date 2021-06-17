package com.example.courseregistration.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AdminLogin extends Activity {

    private Button create, edit, deletec, deleteu;
    private EditText course,user;
    private TextView logout;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpage);

        create = findViewById((R.id.newcourseb));
        edit = findViewById(R.id.editcourse4);
        deletec = findViewById(R.id.deletecourse);
        deleteu = findViewById(R.id.deleteuser);
        logout = findViewById(R.id.logoutadmin);
        course = findViewById(R.id.coursenameee);
        user = findViewById(R.id.username21);

        // Create a course
        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this, AddCourse.class);
                startActivity(intent);
                finish();
            }

        });

        // Update a course
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String coursecode2 = course.getText().toString();
                DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("courses");

                Query checkCourse = reference2.orderByChild("code").equalTo(coursecode2);
                checkCourse.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){
                            String codefromdb = snapshot.child(coursecode2).child("code").getValue(String.class);
                            String namefromdb = snapshot.child(coursecode2).child("name").getValue(String.class);

                                if (codefromdb != null && codefromdb.equals(coursecode2)){
                                    Intent intent5 = new Intent(AdminLogin.this, EditCourse.class);

                                    intent5.putExtra("code", codefromdb);
                                    intent5.putExtra("name", namefromdb);

                                    startActivity(intent5);
                                    //finish();
                                }

                        } else {
                            course.setError("Course does not exist");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }

                });

            }

        });

        // Delete course
        deletec.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("courses");

                String code2 = course.getText().toString();
                Query checkCourse2 = reference.orderByChild("code").equalTo(code2);
                checkCourse2.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            String codefromdb = snapshot.child(code2).child("code").getValue(String.class);
                            String namefromdb = snapshot.child(code2).child("name").getValue(String.class);

                            if(codefromdb != null && codefromdb.equals(code2)) {
                                reference.child(code2).removeValue();
                                Toast.makeText(AdminLogin.this, "Course successfully deleted", Toast.LENGTH_LONG).show();
                            }

                        } else {
                            course.setError("Course does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) { }

                });

            }

        });

        // Delete user
        deleteu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");
                String user2 = user.getText().toString();
                Query checkCourse2 = reference.orderByChild("username").equalTo(user2);
                checkCourse2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String usernamefromdb = snapshot.child(user2).child("username").getValue(String.class);


                            if(usernamefromdb != null && usernamefromdb.equals(user2)) {
                                reference.child(user2).removeValue();
                                Toast.makeText(AdminLogin.this, "User successfully deleted", Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            user.setError("User does not exist");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
        });

        // Logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
