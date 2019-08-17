package com.example.tourmate;

public class Memory {
    private String image;
    private String title;
    private String description;
    private String key;

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
}
