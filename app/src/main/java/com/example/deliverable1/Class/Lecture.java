package com.example.deliverable1.Class;

public class Lecture {

    private String hour;
    private String day;

    private Lecture(String hour, String day) {
        this.hour = hour;
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public String getDay() {
        return day;
    }


}
