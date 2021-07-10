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
    private Button signUp;
    private TextView signIn;
    private ProgressDialog progressdialog;
    private final FirebaseAuth firebaseauth = FirebaseAuth.getInstance();
    private final FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.namesignup);
        username = findViewById(R.id.usernamesu);
        email = findViewById(R.id.emailsignup);
        password = findViewById(R.id.passwordsignup);
        usertype = findViewById(R.id.typesignup);
        signUp = findViewById(R.id.signupbutton);
        signIn = findViewById(R.id.logintvsu);
        progressdialog = new ProgressDialog(this);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                reference = rootNode.getReference("users");

                String usernameStr = username.getText().toString();
                String nameStr = name.getText().toString();
                String emailStr = email.getText().toString();
                String passwordStr = password.getText().toString();
                String usertypeStr = usertype.getText().toString();

                Register(nameStr, emailStr, passwordStr, usertypeStr);

                if (validUserType(usertypeStr)==true){
                    System.out.println("Valid User type");
                }
                else{
                    usertype.setError("Invalid user type");
                    return;
                }

                User user = new User(nameStr,usernameStr,emailStr,passwordStr,usertypeStr);
                reference.child(usernameStr).setValue(user);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void Register(String nameInput, String emailInput, String passwordInput, String usertypeInput) {
        if (TextUtils.isEmpty(emailInput)) {
            email.setError("Enter email please");
            return;
        }
        if (TextUtils.isEmpty((nameInput)) || isValidName(nameInput)==false) {
            name.setError("Enter valid name please");
            return;
        }
        if (TextUtils.isEmpty((passwordInput)) && !validPassword(passwordInput)) {
            password.setError("Enter password please");
            return;
        }
        if (TextUtils.isEmpty((usertypeInput))) {
            usertype.setError("Enter user type please");
            return;
        }
        if (!usertypeInput.equalsIgnoreCase("Instructor")  && !usertypeInput.equalsIgnoreCase("Student")) {
            usertype.setError("Usertype must be: Instructor or Student");
            return;
        }
        if (passwordInput.length() < 6 && !validPassword(passwordInput)) {
            password.setError("Password must have more than 5 characters");
            return;
        }
        if(!isValidEmail(emailInput)){
            email.setError("Invalid email");
            return;
        }

        progressdialog.setMessage("Do not close the app please...");
        progressdialog.show();
        progressdialog.setCanceledOnTouchOutside(false);

        firebaseauth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "User successfully signed up", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                    finish();
                } else{
                    Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
                }
                progressdialog.dismiss();
            }
        });

    }

    public static Boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static boolean isValidName(String name){
        return name.matches("[a-zA-Z][a-zA-Z ]+");
    }

    public static boolean validUserType(String userType){
        return userType.equalsIgnoreCase("student") || userType.equalsIgnoreCase("instructor");
    }

    public static boolean validPassword(String password){
        return password.length()>5;
    }



}

