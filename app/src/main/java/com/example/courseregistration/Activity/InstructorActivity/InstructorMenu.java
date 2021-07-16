package com.example.courseregistration.Activity.InstructorActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.courseregistration.Activity.MainActivity;
import com.example.courseregistration.Class.Course;
import com.example.courseregistration.Adapter.CourseAdapter;
import com.example.courseregistration.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class InstructorMenu extends Activity {

    // Database
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    Button logoutBtn, showAssignedCoursesBtn;
    TextView welcomeText;
    RecyclerView coursesRecyclerView;
    SearchView searchBar;
    CourseAdapter courseAdapter;
    ArrayList<Course> courseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_menu);

        // Connect to courses root
        DatabaseReference listCoursesDb = FirebaseDatabase.getInstance().getReference("courses");

        // Create objects for UI components
        logoutBtn = findViewById(R.id.instructorSignoutBtn);
        searchBar = findViewById(R.id.searchBar);
        welcomeText = findViewById(R.id.welcome);
        showAssignedCoursesBtn = findViewById(R.id.showAssignedCoursesBtn);
        coursesRecyclerView = findViewById(R.id.coursesRecyclerView);

        // Hello message
        String welcomeStr = "Welcome, " + getIntent().getStringExtra("NAME") + ". You are signed in as an instructor";
        welcomeText.setText(welcomeStr);

        // Adapter set up
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(courseList, this);

        // Set up / display recycler view
        coursesRecyclerView.setHasFixedSize(true);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coursesRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)); // Item divider
        coursesRecyclerView.setAdapter(courseAdapter);

        courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (courseList.get(position).getInstructor().equals(getIntent().getStringExtra("NAME")) || courseList.get(position).getInstructor().equals("TBD")) {
                    Intent instructorEditCourse = new Intent(InstructorMenu.this, EditCourseAsInstructor.class);
                    instructorEditCourse.putExtra("COURSE CODE", courseList.get(position).getCode());
                    instructorEditCourse.putExtra("CAPACITY", courseList.get(position).getCapacity());
                    instructorEditCourse.putExtra("DESCRIPTION", courseList.get(position).getDescription());
                    instructorEditCourse.putExtra("INSTRUCTOR NAME", getIntent().getStringExtra("NAME"));
                    startActivity(instructorEditCourse);
                } else {
                    Toast.makeText(getApplicationContext(), "This course is already assigned to someone else", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Load all the courses in the recycler view
        listCoursesDb.addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(InstructorMenu.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        showAssignedCoursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent assignedCourses = new Intent(getApplicationContext(), AssignedCourses.class);
                String name = getIntent().getStringExtra("NAME");
                assignedCourses.putExtra("INSTRUCTOR NAME", name);
                startActivity(assignedCourses);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(InstructorMenu.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void search (String str) {
        ArrayList<Course> filteredList = new ArrayList<>();
        for (Course object : courseList) {
            if (object.getName().toUpperCase().contains(str.toUpperCase()) || object.getCode().toUpperCase().contains(str.toUpperCase())) {
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
