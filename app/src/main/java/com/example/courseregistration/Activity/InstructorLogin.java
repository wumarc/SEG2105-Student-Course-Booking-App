package com.example.courseregistration.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.courseregistration.R;
import com.google.firebase.auth.FirebaseAuth;

public class InstructorLogin extends Activity {
    private Button logout;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instructorlogin);

        logout = findViewById(R.id.logoutbi);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(InstructorLogin.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });
    }
}
