package com.example.courseregistration.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.courseregistration.Class.User;
import com.example.courseregistration.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText name, email, password, usertype, username;
    private Button Signup;
    private TextView signin;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseauth;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseauth = FirebaseAuth.getInstance();
        name = findViewById(R.id.namesignup);
        username = findViewById(R.id.usernamesu);
        email = findViewById(R.id.emailsignup);
        password = findViewById(R.id.passwordsignup);
        usertype = findViewById(R.id.typesignup);
        Signup = findViewById(R.id.signupbutton);
        progressdialog = new ProgressDialog(this);
        signin = findViewById(R.id.logintvsu);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                Register();
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                String username3 = username.getText().toString();
                String name3 = name.getText().toString();
                String email3 = email.getText().toString();
                String password3 = password.getText().toString();
                String usertype3 = usertype.getText().toString();

                User user = new User(name3,username3,email3,password3,usertype3);
                reference.child(username3).setValue(user);

              //  UserHelper admin = new UserHelper("admin", "admin123");
               // reference.child("admin").setValue(admin);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Register() {

        String name2 = name.getText().toString();
        String email2 = email.getText().toString();
        String password2 = password.getText().toString();
        String usertype2 = usertype.getText().toString();

        if (TextUtils.isEmpty(email2)) {
            email.setError("Enter email please");
            return;
        }
        if (TextUtils.isEmpty((name2))) {
            name.setError("Enter name please");
            return;
        }
        if (TextUtils.isEmpty((password2))) {
            password.setError("Enter password please");
            return;
        }
        if (TextUtils.isEmpty((usertype2))) {
            usertype.setError("Enter user type please");
            return;
        }
        if (!usertype2.equalsIgnoreCase("Instructor")  && !usertype2.equalsIgnoreCase("Student")) {
            usertype.setError("Usertype must be: Instructor or Student");
            return;
        }
        if (password2.length() < 6) {
        password.setError("Password must have more than 5 characters");
        return;
        }
        if(!isValidemail(email2)){
            email.setError("Invalid email");
            return;
        }

        progressdialog.setMessage("Do not close the app please...");
        progressdialog.show();
        progressdialog.setCanceledOnTouchOutside(false);
        firebaseauth.createUserWithEmailAndPassword(email2,password2).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "User successfully signed up", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }
        });

    }

    private Boolean isValidemail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

