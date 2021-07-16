package com.example.courseregistration.Activity.StudentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courseregistration.Activity.MainActivity;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.Adapter.CourseAdapter;
import com.example.courseregistration.Class.Lecture;
import com.example.courseregistration.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class StudentMenu extends AppCompatActivity {

    Button logout, coursesEnrolledIn;
    RecyclerView coursesRecyclerView;
    SearchView searchBar;
    TextView welcomeText;
    ArrayList<Course> courseList;
    CourseAdapter courseAdapter;
    DatabaseReference coursesDbRef = FirebaseDatabase.getInstance().getReference("courses");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_menu);

        logout = findViewById(R.id.studentSignoutBtn);
        coursesEnrolledIn = findViewById(R.id.student_showAssignedCoursesBtn);
        coursesRecyclerView = findViewById(R.id.student_coursesRecyclerView);
        searchBar = findViewById(R.id.student_searchBar);
        welcomeText = findViewById(R.id.student_welcome);

        // Hello message
        String welcomeStr = "Welcome, " + getIntent().getStringExtra("NAME") + ". You are signed in as a student";
        welcomeText.setText(welcomeStr);

        // Adapter set up
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(courseList, this);

        // Set up / display recycler view
        coursesRecyclerView.setHasFixedSize(true);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coursesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // Item divider
        coursesRecyclerView.setAdapter(courseAdapter);

        // Load the courses in the recycler view
        coursesDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                // Clear the list of old data first before reloading the new ones (this basically needs to be cleared after the user goes back to admin page from the edit page)
                courseList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Converting each child back to the object Course
                    Course course = dataSnapshot.getValue(Course.class);
                    courseList.add(course);
                }
                courseAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(StudentMenu.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Item listener
        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Open a dialog and ask the student if they wish to enroll in the course
                openDialog(courseList.get(position).getCode(), getIntent().getStringExtra("USERNAME"));
            }
        });

        coursesEnrolledIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnrolledCourses.class);
                intent.putExtra("USERNAME", getIntent().getStringExtra("USERNAME"));
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(StudentMenu.this, MainActivity.class));
                finish();
            }

        });

        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBar.setIconified(false);
            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBar.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

    }

    public void openDialog(String courseCode, String name) {
        EnrollDialog enrollDialog = new EnrollDialog();
        enrollDialog.setCourseCode(courseCode);
        enrollDialog.setStudentName(name);
        enrollDialog.show(getSupportFragmentManager(), "dialog");
    }

    private void search (String str) {
        ArrayList<Course> filteredList = new ArrayList<>();
        for (Course object : courseList) {
            ArrayList<Lecture> lectures = object.getLectures();
            for (Lecture lecture : lectures) {
                if (lecture.getDay().equals(str)) {
                    filteredList.add(object);
                }
                break;
                }
            if (object.getName().toUpperCase().contains(str.toUpperCase()) || object.getCode().toUpperCase().contains(str.toUpperCase())) { // search by day of the week
                filteredList.add(object);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No course found...", Toast.LENGTH_SHORT).show();
        } else {
            CourseAdapter filteredCourseAdapter = new CourseAdapter(filteredList, this);
            coursesRecyclerView.setAdapter(filteredCourseAdapter);
            courseAdapter.notifyDataSetChanged();
        }
    }


}
