package com.example.messenger.models;

public class PhoneContact {

    private String name;
    private String number;

    public PhoneContact() {
        super();
    }

    public PhoneContact(String name, String number) {
        super();
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneContact{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
