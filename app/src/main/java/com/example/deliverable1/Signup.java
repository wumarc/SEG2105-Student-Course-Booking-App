package com.example.deliverable1;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Signup extends AppCompatActivity {

    EditText email, password, firstName, lastName, userType;
    Button createUserBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        firstName = findViewById(R.id.editTextTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        userType = findViewById(R.id.editTextUserType);
        createUserBtn = findViewById(R.id.createAccountBtn);

        fAuth = FirebaseAuth.getInstance();

        createUserBtn.setOnClickListener(new View.OnClickListener()  {  //this function is ran when the create account button is clicked
            @Override
            public void onClick(View v) {
                //extract the data from the form

                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String firstNameValue = firstName.getText().toString();
                String lastNameValue = lastName.getText().toString();
                String userTypeValue = userType.getText().toString();



            }




        });

    }


}