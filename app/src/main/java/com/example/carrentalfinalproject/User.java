package com.example.carrentalfinalproject;

public class User {
     String fullName;
     String phoneNumber;

     String email;

     int id;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String fullName, String phoneNumber, String email, int id) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.id = id;
    }
}
