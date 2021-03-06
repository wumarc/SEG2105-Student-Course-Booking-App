package com.example.courseregistration.Class;

public class User {

    String name, username, email, password, usertype;

    public User() {}

    public User(String name, String username, String email, String password, String usertype) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.usertype = usertype;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }
}
