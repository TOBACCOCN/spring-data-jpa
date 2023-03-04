package com.example.springdatajpa.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@IdClass(NameAddressKey.class)
public class User1 {

    @Id
    @Column
    private String name;

    private int age;

    @Id
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

}
