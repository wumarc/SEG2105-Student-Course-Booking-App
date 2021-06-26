package com.example.courseregistration.Class;
import java.util.ArrayList;

public class Course {

    String name, code, description;
    int capacity;
    String instructor;
    public ArrayList<Student> students;
    public ArrayList<Lecture> lectures;

    public Course() {
    }

    public Course(String name, String code, String description, int capacity, String instructor, ArrayList<Student> students, ArrayList<Lecture> lectures) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.capacity = capacity;
        this.instructor = instructor;
        this.students = students;
        this.lectures = lectures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

}
