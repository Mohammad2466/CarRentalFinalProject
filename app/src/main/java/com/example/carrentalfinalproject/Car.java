package com.example.carrentalfinalproject;

public class Car {
    private int Id;
    private String color;
    private String category;
    private String brand;
    private String model;
    private int madeYear;
    private int maxSpeed;
    private double enginePower;
    private double costPerDay;
    private int seats;
    private String gearType;
    private boolean isAvailable;
    private String carDetails;
    private String image;

    public Car(int id, String color, String category, String brand, String model, int madeYear, int maxSpeed, double enginePower, double costPerDay, int seats, String gearType, boolean isAvailable, String carDetails, String image) {
        Id = id;
        this.color = color;
        this.category = category;
        this.brand = brand;
        this.model = model;
        this.madeYear = madeYear;
        this.maxSpeed = maxSpeed;
        this.enginePower = enginePower;
        this.costPerDay = costPerDay;
        this.seats = seats;
        this.gearType = gearType;
        this.isAvailable = isAvailable;
        this.carDetails = carDetails;
        this.image = image;
    }

    public int getId() {
        return Id;
    }

    public String getColor() {
        return color;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getMadeYear() {
        return madeYear;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public double getEnginePower() {
        return enginePower;
    }

    public double getCostPerDay() {
        return costPerDay;
    }

    public int getSeats() {
        return seats;
    }

    public String getGearType() {
        return gearType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getCarDetails() {
        return carDetails;
    }

    public String getImage() {
        return image;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setMadeYear(int madeYear) {
        this.madeYear = madeYear;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setEnginePower(double enginePower) {
        this.enginePower = enginePower;
    }

    public void setCostPerDay(double costPerDay) {
        this.costPerDay = costPerDay;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setCarDetails(String carDetails) {
        this.carDetails = carDetails;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

