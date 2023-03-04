package com.example.springdatajpa.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NameAddressKey implements Serializable {

    private String name;
    private String address;

}
