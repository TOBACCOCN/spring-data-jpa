package com.example.springdatajpa.repository;

import com.example.springdatajpa.domain.Gender;
import com.example.springdatajpa.domain.NameAddressKey;
import com.example.springdatajpa.domain.User;
import com.example.springdatajpa.domain.User1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface User1Repository extends JpaRepository<User1, NameAddressKey> {

    List<User1> findByGender(Gender gender);

}
