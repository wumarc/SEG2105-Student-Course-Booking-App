package com.example.deliverable1;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Course {

    private String description;
    private String courseCode;
    private String courseName;
    private Instructor instructor;
    private ArrayList<Student> students;
    private Integer capacity;
    private ArrayList<Lecture> lectures;


    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public Integer getCapacity() {
        return capacity;
    }

}
