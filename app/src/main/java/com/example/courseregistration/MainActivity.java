package com.example.courseregistration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
    //private ProgressDialog progressdialog;
   // private FirebaseAuth firebaseauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //firebaseauth = FirebaseAuth.getInstance();
        //email = findViewById(R.id.emaillogin);
        username = findViewById(R.id.usernamelogin);
        password = findViewById(R.id.passwordlogin);
        login = findViewById(R.id.loginb);
        //progressdialog = new ProgressDialog(this);
        signup = findViewById(R.id.signupli);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void isUser(){
        String username2 = username.getText().toString();
        String password2 = password.getText().toString();

        if (TextUtils.isEmpty(username2)) {
            username.setError("Enter username please");
            return;
        }
        if (TextUtils.isEmpty((password2))) {
            password.setError("Enter password please");
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(username2);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                   // username.setError(null);


                    String passwordfromdb = snapshot.child(username2).child("password").getValue(String.class);
                    String emailfromdb = snapshot.child(username2).child("email").getValue(String.class);
                    String namefromdb = snapshot.child(username2).child("name").getValue(String.class);
                    String usernamefromdb = snapshot.child(username2).child("username").getValue(String.class);

                    if(passwordfromdb!= null &&passwordfromdb.equals(password2)){

                      //  username.setError(null);



                        String usertypefromdb = snapshot.child(username2).child("usertype").getValue(String.class);
                        Intent intent = new Intent(MainActivity.this, AdminLogin.class);
                        Intent intent2 = new Intent(MainActivity.this, instructorLogin.class);
                        Intent intent3 = new Intent(MainActivity.this, StudentLogin.class);

                        intent.putExtra("name", namefromdb);
                        intent.putExtra("username", usernamefromdb);
                        intent.putExtra("email", emailfromdb);
                        intent.putExtra("password", passwordfromdb);
                        intent.putExtra("usertype", usertypefromdb);

                        intent2.putExtra("name", namefromdb);
                        intent2.putExtra("username", usernamefromdb);
                        intent2.putExtra("email", emailfromdb);
                        intent2.putExtra("password", passwordfromdb);
                        intent2.putExtra("usertype", usertypefromdb);

                        intent3.putExtra("name", namefromdb);
                        intent3.putExtra("username", usernamefromdb);
                        intent3.putExtra("email", emailfromdb);
                        intent3.putExtra("password", passwordfromdb);
                        intent3.putExtra("usertype", usertypefromdb);

                        if(usertypefromdb != null &&usertypefromdb.equalsIgnoreCase("student")){
                            startActivity(intent3);
                        }else if(usertypefromdb != null && usertypefromdb.equalsIgnoreCase("instructor")){
                            startActivity(intent2);
                        }else{
                        startActivity(intent);
                        }


                    }
                    else if(passwordfromdb!=null && !passwordfromdb.equals(password2)){
                        password.setError("Wrong password");
                        password.requestFocus();
                    }
                }
                else{
                    username.setError("username does not exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    }
