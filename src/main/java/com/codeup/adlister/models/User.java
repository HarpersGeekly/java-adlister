package com.codeup.adlister.models;

import com.codeup.adlister.util.Password;

import java.sql.Date;
import java.time.LocalDateTime;

public class User {
    private long id;
    private String username;
    private String email;
    private String password;

    public User(){}

    // used for creating a user
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
//        this.password = password;
        setPassword(password);
    }

    // used for retrieving a user from database
    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
//        this.password = password;
        this.password = Password.hash(password); // neat place to hash a password!
    }
}
