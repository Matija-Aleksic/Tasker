package com.javaprojektni.tasker.model;

import javafx.beans.InvalidationListener;
import javafx.collections.ArrayChangeListener;
import javafx.collections.ObservableArray;

public class User extends Person implements ObservableArray<User>, Comparable<User>{
    private String mail;


    public User(int userId, String name, String surname, String emailAddress, String mail) {
        super(userId, name, surname, emailAddress);
        this.mail = mail;
    }

    public User() {
        super();

    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public void addListener(ArrayChangeListener<User> arrayChangeListener) {

    }

    @Override
    public void removeListener(ArrayChangeListener<User> arrayChangeListener) {

    }

    @Override
    public void resize(int i) {

    }

    @Override
    public void ensureCapacity(int i) {

    }

    @Override
    public void trimToSize() {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }

    @Override
    public int compareTo(User otherUser) {
        return this.getUserId() - otherUser.getUserId();
    }
}
