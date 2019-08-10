package com.example.tourmate;

public class Memory {
    private String image;
    private String description;
    private String id;

    public Memory() {
    }

    public Memory(String image) {
        this.image = image;
    }

    public Memory(String image, String description) {
        this.image = image;
        this.description = description;
    }

    public Memory(String image, String description, String id) {
        this.image = image;
        this.description = description;
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }
}
