package com.example.carrentalfinalproject;

public class User {
    private int id;
    private String uid;
     private String name;
    private String userName;

    private  String phoneNumber;

    private String email;

    private String creditCardInfo;

    public User(int id, String uid, String name,String userName, String phoneNumber, String email, String creditCardInfo) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.creditCardInfo = creditCardInfo;
    }
}
