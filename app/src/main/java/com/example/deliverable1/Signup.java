package com.example.deliverable1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.deliverable1.Class.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signup extends AppCompatActivity {

    // Make each UI component a workable object
    EditText email, password, firstName, lastName, userType;
    Button createUserBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        fAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        firstName = findViewById(R.id.editTextTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        userType = findViewById(R.id.editTextUserType);

        fAuth = FirebaseAuth.getInstance();

        createUserBtn = findViewById(R.id.createAccountBtn);

        createUserBtn.setOnClickListener(new View.OnClickListener() { //this function will run when the create account button is clicked
            @Override
            public void onClick(View v) {
                //extract the data from the form

                String emailData = email.getText().toString();
                String passwordData = password.getText().toString();
                String firstNameData = firstName.getText().toString();
                String lastNameData = lastName.getText().toString();
                String userTypeData = userType.getText().toString();

                if(emailData.isEmpty()) {
                    email.setError("Email is required");
                    return;
                }

                if(passwordData.isEmpty()) {
                    password.setError("Password is required");
                    return;
                }

                if(firstNameData.isEmpty()) {
                    firstName.setError("First name is required");
                    return;
                }

                if(lastNameData.isEmpty()) {
                    lastName.setError("Last name is required");
                    return;
                }

                if(userTypeData.isEmpty()) {
                    userType.setError("User type is required");
                    return;
                }

                //data is validated, now register the user with firebase
                fAuth.createUserWithEmailAndPassword(emailData, passwordData).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // send user to the next page
                        startActivity(new Intent(getApplicationContext(), StudentLoggedIn.class));
                        finish(); // this will terminate all previous activities (user won't be able to go back to the login page)
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }

        });

    }



}