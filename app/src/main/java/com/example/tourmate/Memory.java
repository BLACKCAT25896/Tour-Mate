package com.example.tourmate;

public class Memory {
    private String image;
    private String title;
    private String description;
    private String key;
    private String tourName;
    private String tourKey;

    public Memory() {
    }

    public Memory(String image) {
        this.image = image;
    }

    public Memory(String image, String description) {
        this.image = image;
        this.description = description;
    }

    public Memory(String image, String description, String key) {
        this.image = image;
        this.description = description;
        this.key = key;
    }

    public Memory(String image, String title, String description, String id) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.key = key;
    }

    public Memory(String image, String title, String description, String key, String tourName, String tourKey) {
        this.image = image;
        this.title = title;
        this.description = description;
        this.key = key;
        this.tourName = tourName;
        this.tourKey = tourKey;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public String getTourName() {
        return tourName;
    }

    public String getTourKey() {
        return tourKey;
    }
}
