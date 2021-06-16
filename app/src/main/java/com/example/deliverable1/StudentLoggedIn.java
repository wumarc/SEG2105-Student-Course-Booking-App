package com.example.deliverable1;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;



public class StudentLoggedIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_logged_in);

        Button logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplication(), MainActivity.class));
            }
        });
    }


}