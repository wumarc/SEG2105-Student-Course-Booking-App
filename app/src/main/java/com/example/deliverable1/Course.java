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

    public Course(String description, String courseCode, String courseName, Instructor instructor, ArrayList<Student> students, Integer capacity, ArrayList<Lecture> lectures) {
        this.description = description;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        this.students = students;
        this.capacity = capacity;
        this.lectures = lectures;
        this.students = new ArrayList<Student>();
        this.lectures = new ArrayList<Lecture>();
    }

    public void setLecture(Lecture lecture) {
        lectures.add(lecture);
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

    public void setStudent(Student student) {
        students.add(student);
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

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

}
