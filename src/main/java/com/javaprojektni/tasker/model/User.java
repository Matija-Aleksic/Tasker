package com.javaprojektni.tasker.model;

public class User extends Person {
    private String mail;


    public User(int userId, String name, String surname, String emailAddress, String mail) {
        super(userId, name, surname, emailAddress);
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
