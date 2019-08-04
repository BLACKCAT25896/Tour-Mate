package com.example.tourmate;

public class Memory {
    private String image;
    private String description;

    public Memory() {
    }

    public Memory(String image) {
        this.image = image;
    }

    public Memory(String image, String description) {
        this.image = image;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
