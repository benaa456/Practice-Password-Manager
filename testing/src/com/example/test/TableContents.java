package com.example.test;

public class TableContents {
    private String website;
    private String username;
    private String password;
    private String email;

    public TableContents(String website, String username, String password, String email) {
        this.website = website;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public TableContents() {
        this.website = "website";
        this.username = "username";
        this.password = "0000";
        this.email = "email";
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

