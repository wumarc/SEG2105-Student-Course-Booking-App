package com.example.courseregistration.Class;

public class Lecture {

    private String time, day;

    public Lecture() {};

    public Lecture(String time, String day) {
        this.time = time;
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean equals(Lecture lecture) {
        return this.time.equalsIgnoreCase(lecture.getTime()) && this.day.equalsIgnoreCase(lecture.getDay());
    }

}