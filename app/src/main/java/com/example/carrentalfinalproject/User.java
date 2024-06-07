package com.example.carrentalfinalproject;

public class User {
    private String fullName;
    private String phoneNumber;
    private String email;
    private int id;

    // No-argument constructor required for Firebase
    public User() {
    }

    // Parameterized constructor
    public User(String fullName, String phoneNumber, String email, int id) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = id;
    }

    // Getter and setter methods
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
