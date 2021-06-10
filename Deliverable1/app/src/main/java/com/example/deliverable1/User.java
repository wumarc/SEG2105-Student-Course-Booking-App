package com.example.deliverable1;

public class User {

    private String username;
    private String password;
    private boolean isStudent;
    private String name;

    // Constructor
    public User(String username, String password, boolean isStudent) {
        this.username = username;
        this.password = password;
        this.isStudent = isStudent;
    }

    public String getName() {
        return name;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public boolean login(String username, String password) {
        return true;
    }

    public void setName(String name) {
        this.name = name;
    }

}
