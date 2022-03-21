package com.cloudisk.demo1.entity;

public class User{
    private int id;
    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
}
