package com.example.courseregistration.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.courseregistration.Activity.AdminActivity.AdminMenu;
import com.example.courseregistration.Activity.InstructorActivity.InstructorMenu;
import com.example.courseregistration.Activity.StudentActivity.StudentMenu;
import com.example.courseregistration.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private Button login;
    private TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create variables for UI components
        username = findViewById(R.id.usernamelogin);
        password = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.loginb);
        signup = findViewById(R.id.signupli);

        // User login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });

        // User signup
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void isUser(){

        String enteredUsername = username.getText().toString();
        String enteredPassword = password.getText().toString();

        // Verify the username and password are not empty
        if (TextUtils.isEmpty(enteredUsername)) {
            username.setError("Enter username please");
            return;
        }
        if (TextUtils.isEmpty((enteredPassword))) {
            password.setError("Enter password please");
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String passwordFromDb = snapshot.child(enteredUsername).child("password").getValue(String.class);
                    String nameFromDb = snapshot.child(enteredUsername).child("name").getValue(String.class);
                    String usernameFromDb = snapshot.child(enteredUsername).child("username").getValue(String.class);
                    String userTypeFromDb = snapshot.child(enteredUsername).child("usertype").getValue(String.class);

                    if (passwordFromDb != null && passwordFromDb.equals(enteredPassword)) {
                        if(userTypeFromDb != null && userTypeFromDb.equalsIgnoreCase("student")) {
                            Intent studentIntent = new Intent(MainActivity.this, StudentMenu.class);
                            studentIntent.putExtra("NAME", nameFromDb);
                            studentIntent.putExtra("USERNAME", usernameFromDb);
                            startActivity(studentIntent);
                            finish();
                        } else if (userTypeFromDb != null && userTypeFromDb.equalsIgnoreCase("instructor")) {
                            Intent instructorIntent = new Intent(MainActivity.this, InstructorMenu.class);
                            instructorIntent.putExtra("NAME", nameFromDb);
                            startActivity(instructorIntent);
                            finish();
                        } else {
                            Intent adminIntent = new Intent(MainActivity.this, AdminMenu.class);
                            startActivity(adminIntent);
                            finish();
                        }
                    } else if (passwordFromDb != null && !passwordFromDb.equals(enteredPassword)){
                        password.setError("Wrong password");
                        password.requestFocus();
                    }

                } else {
                    username.setError("username does not exist.");
                    username.requestFocus();
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) { }
        });
    }

}
