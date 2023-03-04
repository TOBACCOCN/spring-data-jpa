package com.example.springdatajpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    private int age;

    private String address;

}
