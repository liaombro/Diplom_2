package org.example.model;

import java.util.Random;

public class User {
    private String email;

    private String password;

    private String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User(boolean isRandom) {
        if (isRandom) {
            this.setRandomEmail();
            this.setRandomPassword();
            this.setRandomName();
        }
    }

    public User setRandomEmail() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        String email = String.format("user-%d@gmail.com", randomNumber);
        this.setEmail(email);
        return this;
    }

    public User setRandomPassword() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        String password = String.format("user-%d@gmail.com", randomNumber);
        this.setPassword(password);
        return this;
    }

    public User setRandomName() {
        Random random = new Random();
        int randomNumber = random.nextInt();
        String name = String.format("user-%d", randomNumber);
        this.setName(name);
        return this;
    }


    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String toString() {
        return String.format("User with parameters: \n email: \"%s\", password: \"%s\", name: \"%s\"",
                email, password, name);
    }
    public User copy(){
        return new User(email, password, name);
    }
}
