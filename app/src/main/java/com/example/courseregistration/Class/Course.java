package com.example.courseregistration.Class;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course {

    String name, code, description;
    int capacity;
    String instructor;
    public ArrayList<String> students;
    public ArrayList<Lecture> lectures;

    public Course() {}

    public Course(String name, String code, String description, int capacity, String instructor, ArrayList<String> students, ArrayList<Lecture> lectures) {
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

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(ArrayList<Lecture> lectures) {
        this.lectures = lectures;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        result.put("code", code);
        result.put("description", description);
        result.put("capacity", capacity);
        result.put("instructor", instructor);
        result.put("students", students);
        result.put("lectures", lectures);
        return result;
    }


}
