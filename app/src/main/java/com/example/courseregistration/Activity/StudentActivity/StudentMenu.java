package com.example.courseregistration.Activity.StudentActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.courseregistration.Activity.MainActivity;
import com.example.courseregistration.R;
import com.google.firebase.auth.FirebaseAuth;

public class StudentMenu extends Activity {

    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        logout = findViewById(R.id.logoutbs);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StudentMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        });

    }

}
