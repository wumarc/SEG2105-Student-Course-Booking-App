package com.example.courseregistration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class instructorLogin extends Activity {
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
                Intent intent = new Intent(instructorLogin.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
