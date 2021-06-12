package com.example.deliverable1;

public class User {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String userType;

    // Constructor
    public User(String email, String password, String firstName, String lastName, String userType) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
    }

    public String getName() {
        return (this.firstName + this.lastName);
    }

    public boolean login(String username, String password) {
        return true;
    }

    public boolean validEmail(String email) {
        return true;
    }

}
