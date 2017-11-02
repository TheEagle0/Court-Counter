package com.example.theeagle.store.Data;

/**
 * Created by theeagle on 11/1/17.
 * this gets and sets the publisher info
 */

public class Publisher {
    private String name;
    private String address;
    private String email;
    private String id;
    private String imageUrl;

    public Publisher(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public Publisher() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
