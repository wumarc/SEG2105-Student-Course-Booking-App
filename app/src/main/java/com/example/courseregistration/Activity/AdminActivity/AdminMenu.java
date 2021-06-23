package com.example.courseregistration.Activity.AdminActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.courseregistration.Activity.MainActivity;
import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class AdminMenu extends Activity {

    private Button create, edit, deletec, deleteu;
    private EditText course,user;
    private TextView logout;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

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
                Intent intent = new Intent(AdminMenu.this, AddCourseAsAdmin.class);
                startActivity(intent);
                finish();
            }

        });

        // Update a course
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference coursesDb = FirebaseDatabase.getInstance().getReference("courses");

                String givenCourseCode = course.getText().toString().toUpperCase();

                Query checkCourse = coursesDb.orderByChild("code").equalTo(givenCourseCode);
                checkCourse.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            String codefromdb = snapshot.child(givenCourseCode).child("code").getValue(String.class);
                            String namefromdb = snapshot.child(givenCourseCode).child("name").getValue(String.class);

                                if (codefromdb != null && codefromdb.equals(givenCourseCode)){
                                    Intent intent5 = new Intent(AdminMenu.this, EditCourseAsAdmin.class);

                                    intent5.putExtra("code", codefromdb);
                                    intent5.putExtra("name", namefromdb);

                                    startActivity(intent5);
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
                                Toast.makeText(AdminMenu.this, "Course successfully deleted", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(AdminMenu.this, "User successfully deleted", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(AdminMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }

}
