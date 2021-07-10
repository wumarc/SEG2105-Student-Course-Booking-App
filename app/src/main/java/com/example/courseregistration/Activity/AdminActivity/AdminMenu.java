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

    private Button create, edit, deleteCourse, deleteUser;
    private EditText course,user;
    private TextView logout;
    private FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    private DatabaseReference reference = rootNode.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        create = findViewById((R.id.newcourseb));
        edit = findViewById(R.id.editcourse4);
        deleteCourse = findViewById(R.id.deletecourse);
        deleteUser = findViewById(R.id.deleteuser);
        logout = findViewById(R.id.logoutadmin);
        course = findViewById(R.id.coursenameee);
        user = findViewById(R.id.username21);

        // Create a course
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMenu.this, AddCourseAsAdmin.class));
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
                        if (snapshot.exists()) {
                            String codeFromDb = snapshot.child(givenCourseCode).child("code").getValue(String.class);
                            String nameFromDb = snapshot.child(givenCourseCode).child("name").getValue(String.class);
                            if (codeFromDb != null && codeFromDb.equals(givenCourseCode)){
                                Intent intent5 = new Intent(AdminMenu.this, EditCourseAsAdmin.class);
                                intent5.putExtra("code", codeFromDb);
                                intent5.putExtra("name", nameFromDb);
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
        deleteCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {}
                });
            }
        });

        // Delete user
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user2 = user.getText().toString();
                Query checkCourse2 = reference.orderByChild("username").equalTo(user2);
                checkCourse2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String usernamefromdb = snapshot.child(user2).child("username").getValue(String.class);
                            if (usernamefromdb != null && usernamefromdb.equals(user2)) {
                                reference.child(user2).removeValue();
                                Toast.makeText(AdminMenu.this, "User successfully deleted", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            user.setError("User does not exist");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {}
                });
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMenu.this, MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        return;
    }

}
