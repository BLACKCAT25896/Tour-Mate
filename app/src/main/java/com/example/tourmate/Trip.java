package com.example.tourmate;

public class Trip {
    private String tripName;
    private String tripStartingLocation;
    private String tripDestination;
    private String tripStartDate;
    private String tripEndDate;
    private double tripBudget;

    public Trip() {
    }

    public Trip(String tripName, String tripStarting, String tripDestination, String tripStartDate, String tripEndDate, double tripBudget) {
        this.tripName = tripName;
        this.tripStartingLocation = tripStarting;
        this.tripDestination = tripDestination;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripBudget = tripBudget;
    }

    public String getTripName() {
        return tripName;
    }

    public String getTripStartingLocation() {
        return tripStartingLocation;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public double getTripBudget() {
        return tripBudget;
    }
}
