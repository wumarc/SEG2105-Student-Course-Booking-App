package com.example.deliverable1.Class;

public class Admin extends User {

    public Admin(String email, String password, String firstName, String lastName, String userType) {
        super("admin", "admin123", "admin", "admin", "Administrator");
    }

    public boolean createCourse(String courseCode, String courseName) {
        return true;
    }

    public void editCourseCode(String courseName) {
    }

    public void editCourseName(String courseCode) {
    }

    public boolean deleteCourse(Course course) {
        return true;
    }

    public boolean deleteInstructor(Instructor instructor) {
        return true;
    }

    public boolean deleteStudent(Student student) {
        return true;
    }

}
