package com.app.workerpool.models;

public class Worker {
    String firstName;
    String lastName;
    String specialization;
    int rating;

    public Worker(String firstName, String lastName, String specialization, int rating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.rating = rating;
    }

    public String getFirstName() {
        return firstName;
    }

    private void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    private void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    private void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getRating() {
        return rating;
    }

    private void setRating(int rating) {
        this.rating = rating;
    }
}
