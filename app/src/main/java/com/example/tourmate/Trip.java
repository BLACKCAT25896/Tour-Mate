package com.example.tourmate;

public class Trip {
    private String tripName;
    private String tripStartingLocation;
    private String tripDestination;
    private String tripStartDate;
    private String tripEndDate;
    private Double tripBudget;
    private String key;
    private int position;

    public Trip() {
    }

    public Trip(String tripName, String tripStarting, String tripDestination, String tripStartDate, String tripEndDate, Double tripBudget) {
        this.tripName = tripName;
        this.tripStartingLocation = tripStarting;
        this.tripDestination = tripDestination;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripBudget = tripBudget;
    }

    public Trip(String tripName, String tripStartingLocation, String tripDestination, String tripStartDate, String tripEndDate, Double tripBudget, String key) {
        this.tripName = tripName;
        this.tripStartingLocation = tripStartingLocation;
        this.tripDestination = tripDestination;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripBudget = tripBudget;
        this.key = key;
    }

    public Trip(String tripName, String tripStartingLocation, String tripDestination, String tripStartDate, String tripEndDate, Double tripBudget, String key, int position) {
        this.tripName = tripName;
        this.tripStartingLocation = tripStartingLocation;
        this.tripDestination = tripDestination;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripBudget = tripBudget;
        this.key = key;
        this.position = position;
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

    public Double getTripBudget() {
        return tripBudget;
    }

    public String getKey() {
        return key;
    }

    public int getPosition() {
        return position;
    }
}
