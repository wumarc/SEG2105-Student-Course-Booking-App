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
//  private ProgressDialog progressdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Assign UI elements to workable objects
        username = findViewById(R.id.usernamelogin);
        password = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.loginb);
        signup = findViewById(R.id.signupli);
//      progressdialog = new ProgressDialog(this);

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
                if(snapshot.exists()){
                    // username.setError(null);
                    String passwordfromdb = snapshot.child(enteredUsername).child("password").getValue(String.class);
                    String emailfromdb = snapshot.child(enteredUsername).child("email").getValue(String.class);
                    String namefromdb = snapshot.child(enteredUsername).child("name").getValue(String.class);
                    String usernamefromdb = snapshot.child(enteredUsername).child("username").getValue(String.class);

                    if (passwordfromdb != null && passwordfromdb.equals(enteredPassword)){
                        //  username.setError(null);
                        String usertypefromdb = snapshot.child(enteredUsername).child("usertype").getValue(String.class);

                        Intent adminIntent = new Intent(MainActivity.this, AdminMenu.class);
                        Intent instructorIntent = new Intent(MainActivity.this, InstructorMenu.class);
                        Intent studentIntent = new Intent(MainActivity.this, StudentMenu.class);

                        if(usertypefromdb != null && usertypefromdb.equalsIgnoreCase("student")){
                            studentIntent.putExtra("NAME", namefromdb);
                            startActivity(studentIntent);
//                            finish();
                        } else if(usertypefromdb != null && usertypefromdb.equalsIgnoreCase("instructor")){
                            instructorIntent.putExtra("NAME", namefromdb);
                            startActivity(instructorIntent);
//                            finish();
                        } else {
                            startActivity(adminIntent);
//                            finish();
                        }
                    } else if (passwordfromdb != null && !passwordfromdb.equals(enteredPassword)){
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
