package com.example.springdatajpa.domain;

public enum Gender {
    MALE("男"),FEMALE("女");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
